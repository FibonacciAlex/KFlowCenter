package com.kola.kmp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilTools {

	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>(){

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyMMdd HH:mm:ss");
		}
		
	};
	
	
	public static long paresDateStringToLong(String date){
		
		try {
			return threadLocal.get().parse(date).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 转换日期
	 * @param data
	 * @param format
	 * @return
	 */
	public static long paresDateStringToLong(String data,String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		long time = 0;
		try {
			Date date = sf.parse(data);
			time = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	

	/**
	 * 检查当前时间是否在目标时间内
	 * @param startTime  开始时间 时分秒
	 * @param endTime 结束时间 时分秒
	 * @return
	 */
	public static boolean isInPriod(String startTime, String endTime){
		boolean inTime = false;
		
		int nowDate = DateUtil.getNowDate();
		long start = paresDateStringToLong(nowDate + " " + startTime, "yyyyMMdd HH:mm:ss");
		long end = paresDateStringToLong(nowDate + " " + endTime, "yyyyMMdd HH:mm:ss");
		
		inTime = nowInPriod(System.currentTimeMillis(), start, end);
		return inTime;
		
	}
	
	public static boolean nowInPriod(long targetTime, long startTime, long endTime){
		if(targetTime <= endTime && targetTime >= startTime){
			return true;
		}else{
			return false;
		}
	}

}
