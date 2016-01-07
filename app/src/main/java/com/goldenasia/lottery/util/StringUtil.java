package com.goldenasia.lottery.util;

import android.annotation.SuppressLint;
import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtil {
	
	/**
	 * ����'.'
	 * */
	public static final String DOT = "-";
	public static final String OTHER_COUNT = "count";
	
	/**
	 * һ�����ַ���
	 * **/
	public static final String IMAGE_PATH = Environment.getExternalStorageDirectory().toString() + "/certTemp/images";
	public static final String FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/certTemp";
	public static final String CERT_PATH = Environment.getExternalStorageDirectory().toString() + "/certTemp/certs";
	
	public static final String COMM_STR_NOSET = "δ����";
	public static final int COM_FROM_TIMELINE=1;
	public static final int COM_FROM_SHELF=2;
	public static final String COM_STR_SCHOOL_1 = "Сѧ������";
	public static final String COM_STR_SCHOOL_2 = "����";
	public static final String COM_STR_SCHOOL_3 = "����";
	public static final String COM_STR_SCHOOL_4 = "��ѧ����";
	public static final String COM_STR_SCHOOL_5 = "˶ʿ������";
	
	/**
	 * ��ǰ���ڸ�ʽ����:2013-8-01
	 * */
	@SuppressLint("SimpleDateFormat")
	public static String dateFormatNow(){
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");     
		Date date = new Date();
		return formatter.format(date);
	}
	/**
	 * ��ǰ���ڸ�ʽ����:2013��8��01��  12ʱ12��
	 * */
	@SuppressLint("SimpleDateFormat")
	public static String dateFormatNow2(){
		
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd HH:mm");     
		Date date = new Date();
		return formatter.format(date);
	}
	/**
	 * ��ǰ���ڸ�ʽ����:2013��8��01��  12ʱ12��
	 * */
	@SuppressLint("SimpleDateFormat")
	public static String dateFormatWithoutYear(String str){
		if(!isEmpty(str)){
			str = str.substring(str.indexOf("-")+1);
			str = str.replace("-", "��");
			str+="��";
			return str;
		}
		return null;
	}
	/**
	 * ��ǰ���ڸ�ʽ����:2013-8-01
	 * */
	@SuppressLint("SimpleDateFormat")
	public static String dateFormatArg(Date date){
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");     
		if(date!=null){
			return formatter.format(date);
		}
		Date nowdate = new Date();
		return formatter.format(nowdate);
	}
	@SuppressLint("SimpleDateFormat")
	public static String dateTimeFormatNow(){
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyyMMddhhmmss");     
		Date date = new Date();
		synchronized(formatter) {
			return formatter.format(date);
		}
		
	}
	public static String yearNow(){
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);//��ȡ���
		return String.valueOf(year);
	}
	public static boolean isEmpty(String str){
		if(str==null){
			return true;
		}else if("".equals(str.trim())){
			return true;
		}
		return false;
	}
}
