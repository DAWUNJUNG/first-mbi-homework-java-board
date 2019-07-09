package com.happytalk.common;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.happytalk.model.*;

/**
 * �����Լ� ����
 * @author LC
 *
 */
public class CommonUtil {	
	private static String newLine = System.lineSeparator();
	/**
	 * String ��ȯ
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		String result; 
		try {
			result = String.valueOf(obj).trim();
			if (result.toUpperCase().equals("NULL")) result = "";			
		}catch(Exception ex) {
			result = "";
		}
		return result;		
	}
	
	/**
	 * String ��ȯ
	 * @param obj
	 * @param nvlValue
	 * @return
	 */
	public static String getString(Object obj, String nvlValue) {
		String result; 
		try {
			result = String.valueOf(obj).trim();
			if (result.toUpperCase().equals("NULL")) result = "";			
		}catch(Exception ex) {
			result = "";
		}
		if (result.equals("")) result = nvlValue;
		return result;		
	}
	
	/**
	 * Int ��ȯ
	 * @param obj
	 * @return
	 */
	public static int getInt(Object obj) {
		int result = 0; 
		try {
			if (obj == null) return 0;
			result = Integer.valueOf(obj.toString());				
		}catch(Exception ex) {
			result = 0;
		}
		return result;		
	}
	
	/**
	 * �ڹ� Exception ���� �޽��� ó��
	 * @param logger
	 * @param e
	 * @param message
	 */
	public static void printErrorLog(Logger logger, Exception e, String message) {
		String error = e.toString();		
		StackTraceElement stackTraceElement[] = e.getStackTrace();
		String returnString = "";
		for (StackTraceElement element : stackTraceElement) {
			String elementString = "\tat " + element.toString();			
				returnString += elementString + newLine;			
		}		
		 logger.error(message +  newLine + error + newLine + returnString ) ;
	}
	
	/**
	 * �ڹ� Exception ���� �޽��� ó��
	 * @param logger
	 * @param e
	 */
	public static void printErrorLog(Logger logger, Exception e) {
		String error = e.toString();		
		StackTraceElement stackTraceElement[] = e.getStackTrace();
		String returnString = "";
		for (StackTraceElement element : stackTraceElement) {
			String elementString = "\tat " + element.toString();			
				returnString += elementString + newLine;			
		}		
		 logger.error(error + newLine + returnString ) ;
	}	
	
	/**
	 * ���� ��¥
	 * @return
	 */
	public static String getToday() {
		Calendar c1 = new GregorianCalendar();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String d = sdf.format(c1.getTime()); 
		return d;
	}
	
	/**
	 * ���� ��¥ �ð�
	 * @return
	 */
	public static String getTodayDateTime() {
		Calendar c1 = new GregorianCalendar();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String d = sdf.format(c1.getTime()); 
		return d;
	}
	
	/**
	 * ���� ��¥ 
	 * @return
	 */
	public static String getYesterDay() {
		Calendar c1 = new GregorianCalendar();
		c1.add(Calendar.DATE, -1); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String d = sdf.format(c1.getTime()); 
		return d;
	}
	
	
}
