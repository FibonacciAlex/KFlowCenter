package com.kola.kmp.flow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.kola.kmp.flowPara.FlowAbsInitTemplate;
import com.kola.kmp.flowPara.FlowDataKey;



/**
 * 流水文件加载类
 * @author Alex
 *
 */
public class FlowLoadFile {

	

    /**
     * 按行进行处理文件
     */
    public static void readFileByLines(String fileName, int zoneID) {
        File file = new File(fileName);
        BufferedReader reader = null;
        Class<?> clazz = null;

        
        try {
            System.out.println("加载流水文件");

            
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8"); //这里的编码格式应该有问题  应该使用utf-8
            
            reader = new BufferedReader(isr);
            String tempString = null;

            long beforeTime = System.currentTimeMillis();
            
            int line = 1;
            // 按行进行读取
            while ((tempString = reader.readLine()) != null) {
//                System.out.println("line " + line + ": " + tempString);
                line++;
                if(tempString.length() == 0){
                	continue;
                }
                //  根据数据类型处理文件分析
                int flowType = getFlowType(tempString);

                FlowDataKey key;
                
                Object obj = FlowCenter.processorMap.get(flowType);
                if(obj != null && obj instanceof Class){
                	try {
                		clazz = (Class) obj;
						FlowAbsInitTemplate abs = (FlowAbsInitTemplate)clazz.newInstance();

						abs.initTemplate(tempString);
						
						key = new FlowDataKey(flowType, zoneID);
						List<FlowAbsInitTemplate> list = FlowOutputToDB.recordMap.get(key);
						if(list != null){
							list.add(abs);
						}else {
							list = new ArrayList<FlowAbsInitTemplate>();
							list.add(abs);
							FlowOutputToDB.recordMap.put(key, list);
						}
						
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
                }
			}
			reader.close();
			
			//保存数据到DB
			FlowOutputToDB.processToDB();
			
			
			long costTime = System.currentTimeMillis() - beforeTime;
			System.out.println("文件加载完成，共用时：" + costTime + "  毫秒");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
            }
			
        }
    }


    /**
     * 分解字符串  获取流水类型
     * @param info
     * @return
     */
    private static int getFlowType(String info){
    	int flowType = -1;
    	if(!info.contains("logType=")){
    		return flowType;
    	}
    	String[] str = info.split(",");
    	String[] targetStrt = str[0].split("logType=");
    	flowType = Integer.parseInt(targetStrt[1].toString().trim());
    	return flowType;
    	
    } 
    
}
