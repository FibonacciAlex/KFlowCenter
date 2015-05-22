package com.kola.kmp.flow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.kola.kmp.flow.db.dbconnectionpool.DBConnectionPoolAdapter;
import com.kola.kmp.util.KGameLogger;
import com.kola.kmp.util.UtilTools;
import com.kola.kmp.util.XmlUtil;
import com.kola.kmp.util.exception.KGameServerException;
import com.kola.kmp.util.timer.KGameTimeSignal;
import com.kola.kmp.util.timer.KGameTimer;
import com.kola.kmp.util.timer.KGameTimerTask;

/**
 * 流水管理
 * 
 * @author Alex
 * 
 */
public class FlowCenter {

	private static final Comparator<File> _FILE_SORTER = new Comparator<File>() {
		
		@Override
		public int compare(File o1, File o2) {
			if(o1.lastModified() > o2.lastModified()) {
				return 1;
			}
			return -1;
		}
		
	};
	private static final KGameLogger logger = KGameLogger.getLogger(FlowCenter.class);
	
	/** 每个流水类型对应的处理模板 */
	public static HashMap<Integer, Class<?>> processorMap = new HashMap<Integer, Class<?>>();

	private final static String SYSTEMCONFIG_PATH = "resource/config/systemConfig.xml";

	/** 已经扫描过的文件列表 */
	private static List<String> readFiles = new ArrayList<String>();

	/** 日志文件夹路径 */
	private static String FOLDERPATH;

	/** 已读文件名路径 */
	private static String READFILEPATH;

	/** 文件扫描时间间隔 */
	private static long SCANUNIT;
	
	/**文件扫描开始时间*/
	private static String SCANSTARTTIME;
	
	/**文件扫描结束时间*/
	private static String SCANENDTIME;

	/** 系统初始化标志 */
	public static AtomicBoolean systemStart = new AtomicBoolean(false);
	
	private static KGameTimer timer;

	private void init() {

		systemStart.set(true);

		try {

			Document doc = XmlUtil.openXml(SYSTEMCONFIG_PATH);
			Element root = doc.getRootElement();
			FOLDERPATH = root.getChildText("logFilePath");

			SCANUNIT = Long.parseLong(root.getChildText("scanFileUnit"));
			
			String[] timeStr = root.getChildText("scanTime").split(",");
			
			SCANSTARTTIME = timeStr[0];
			SCANENDTIME = timeStr[1];
			
			
			
			DBConnectionPoolAdapter.init();

			// 初始化已读文件列表
			READFILEPATH = root.getChildText("readFilePath");

			// 初始化已读文件名列表
			initReadFile();

			// 初始化流水处理器
			initFlowProcessor(root.getChildText("processorPath"));

			// 启动文件扫描时效
			timer = new KGameTimer(root.getChild("Timer"));
			timer.newTimeSignal(new FlowScanFileTask(), 0, TimeUnit.SECONDS);

			
			
			
			systemStart.set(false);
			
			System.out.println("--------------------System start finish---------------------------------------");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("---------------------------------System stop !!!!!!!");
			System.exit(1);
		}

	}

	/**
	 * 初始化流水类型处理器
	 */
	private void initFlowProcessor(String path) {

		Document doc = XmlUtil.openXml(path);
		List<Element> list = doc.getRootElement().getChildren("processModel");
		Class<?> clazz;
		for (int i = 0; i < list.size(); i++) {
			Element child = list.get(i);
			try {
				clazz = Class.forName(child.getAttributeValue("clazz"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}

			String types = child.getAttributeValue("flowType");
			String[] str = types.split(",");
			int flowType;
			for (String subStr : str) {
				flowType = Integer.parseInt(subStr);
				if (processorMap.containsKey(flowType)) {
					continue;
				}
				processorMap.put(flowType, clazz);
			}

		}

		System.out.println("流水文件处理器初始化完成！");

	}

	/**
	 * 初始化已读文件名列表
	 */
	private void initReadFile() {

		Document doc = XmlUtil.openXml(READFILEPATH);
		Element root = doc.getRootElement();
		List<Element> children = root.getChildren("fileName");

		for (Element elem : children) {
			String textTrim = elem.getTextTrim();
			if (textTrim.length() > 0) {
				readFiles.add(textTrim);
			}
		}

		System.out.println("已读文件名初始化完成，已经初始化数量" + readFiles.size());
	}

	/**
	 * 检查是否有新文件 
	 * 
	 * @return
	 */
	public static boolean checkIsNewFile(String fileName) {
		if (systemStart.get()) {
			return false;
		}
		return !readFiles.contains(fileName);
	}

	public static KGameTimer getTimer(){
		return timer;
	}
	
	

	/**
	 * 获取日志文件所在分区
	 * @param fileName
	 * @return
	 */
    private static int getZoneID(String fileName){
    	int zoneID = -1;
    	
    	String[] subStr = fileName.split("_");
    	if(subStr.length < 2){
    		return zoneID;
    	}
    	zoneID = Integer.parseInt(subStr[1]);
    	return zoneID;
    }

	
	public static class FlowScanFileTask implements KGameTimerTask{

		@Override
		public String getName() {
			return FlowScanFileTask.class.getName();
		}

		@Override
		public Object onTimeSignal(KGameTimeSignal timeSignal)
				throws KGameServerException {
			run();
			return null;
		}

		@Override
		public void done(KGameTimeSignal timeSignal) {
			//间隔一段时间再进行扫描
			FlowCenter.getTimer().newTimeSignal(this, SCANUNIT, TimeUnit.SECONDS);	
		}

		@Override
		public void rejected(RejectedExecutionException e) {
			// TODO Auto-generated method stub
			
		}
		
		private static File FOLDER = null;

		public FlowScanFileTask() {
			// TODO Auto-generated constructor stub
			FOLDER = new File(FOLDERPATH);

		}

		public void run() {
			// TODO scanning logFile

			if (systemStart.get()) {
				return;
			}
			
			/**检查是否在文件扫描时间段内*/
			if(!UtilTools.isInPriod(SCANSTARTTIME, SCANENDTIME)){
				return;
			}
			
			System.out.println("Start scanning Log file------------------------");

			// 如果文件在扫描后不进行删除 则要检查文件个数
			if(!FOLDER.exists() && !FOLDER.isDirectory()){
				FOLDER.mkdir();
			}
			
			File[] listFiles = FOLDER.listFiles();

			if (listFiles.length > readFiles.size()) {
				// 存在新文件 进行判断读取
				List<File> fileList = Arrays.asList(listFiles);
				Collections.sort(fileList, _FILE_SORTER);
				
				Document doc = null;

//				for (File file : listFiles) {
				for (int i = 0; i < fileList.size(); i++) {
					File file = fileList.get(i);
					if (file.canRead() && file.getName().endsWith(".log")	&& checkIsNewFile(file.getName())) {

						System.out.println("Start reading log file ---------------------fileName:" + file.getName() + ",---file path:" + FOLDERPATH +"/"+file.getName());
						
						int zoneID = getZoneID(file.getName());
						if(zoneID  < 0){
							logger.warn("处理流水日志文件时，找不到日志文件对应的分区号，日志文件名：" + file.getName());
							continue;
						}
						
						// 进行文件分析处理
						FlowLoadFile.readFileByLines(FOLDERPATH +"/"+file.getName(), zoneID);

						// 保存已读文件名
						readFiles.add(file.getName());
						// 写入到XML
						if (doc == null) {
							doc = XmlUtil.openXml(READFILEPATH);
						}
						Element root = doc.getRootElement();
						root.addContent(new Element("fileName").setText(file.getName()));
						Format format = Format.getCompactFormat();
						format.setEncoding("UTf-8");
						format.setIndent("    ");
						XMLOutputter outer = new XMLOutputter(format);
						
						try {
							outer.output(doc,	new FileOutputStream(READFILEPATH));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}

			}

		}
		
	}
	
	public static void main(String[] args) {

		FlowCenter flow = new FlowCenter();
		flow.init();
		
	}
}
