package miny.common.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {
	
	private SimpleDateFormat yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat yyyy_mm = new SimpleDateFormat("yyyy-MM");
	//yyyyMMddHHmmss
	
	/*
	 * G 기원 (텍스트) AD y 년 (수치) 1996 M 월 (텍스트와 수치) July & 07 d 일 (수치) 10 h 오전/오후때
	 * (1 ∼ 12) (수치) 12 H 하루에 있어서의 때 (0 ∼ 23) (수치) 0 m 분 (수치) 30 s 초 (수치) 55 S
	 * 밀리 세컨드 (수치) 978 E 요일 (텍스트) Tuesday D 해에 있어서의 날 (수치) 189 F 달에 있어서의 요일 (수치)
	 * 2 (7 월의 제 2 수요일) w 해에 있어서의 주 (수치) 27 W 달에 있어서의 주 (수치) 2 a 오전/오후 (텍스트) PM
	 * k 하루에 있어서의 때 (1 ∼ 24) (수치) 24 K 오전/오후때 (0 ∼ 11) (수치) 0 z 타임 존 (텍스트)
	 * Pacific Standard Time
	 */

	public Date getDate(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	public Date timestampTodate(String time) {
		long ts = Long.parseLong(time);
		TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(tz);
		
        Calendar cal = Calendar.getInstance(tz);
		cal.setTimeInMillis(ts);
		
		return toDate(df.format(cal.getTime()),"yyyy-MM-dd HH:mm:ss");
	}
	public String getWDN(Date date){
		String wds[] = {"일","월","화","수","목","금","토"};
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int wd = cal.get(Calendar.DAY_OF_WEEK);
		
		return wds[wd - 1];
	}
	public String toStr(Date date) {
		String dateFormat = "yyyyMMddHHmmss";
		
		return toStr(date, dateFormat);
	}
	public String toStr(Date date, String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(date);
	}
	public String toStringFromParserDate(String date) {
		if (date == null || date.equals("")) return "";
		int len = date.length();
		String dateFormat = "";
		switch (len) {
		case 19:
			dateFormat = "yyyy-MM-dd HH:mm:ss";
			break;
		case 16:
			dateFormat = "yyyy-MM-dd HH:mm";
			break;
		case 13:
			dateFormat = "yyyy-MM-dd HH";
			break;
		case 10:
			dateFormat = "yyyy-MM-dd";
			break;			
		}
		String newDateFormat = "yyyyMMddHHmmss";
		Date newDate = toDate(date,dateFormat);
		return toStr(newDate, newDateFormat);
	}
	public Date toDate(String date) {
		int len = date.length();
		String dateFormat = "";
		switch (len) {
		case 14:
			dateFormat = "yyyyMMddHHmmss";
			break;
		case 12:
			dateFormat = "yyyyMMddHHmm";
			break;
		case 10:
			dateFormat = "yyyyMMddHH";
			break;
		case 8:
			dateFormat = "yyyyMMdd";
			break;			
		}
		Date newDate = toDate(date,dateFormat);
		return newDate;
	}
	public Date toDate(String date, String dateFormat) {
		try { 
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			Date newDate = (Date) df.parse(date);
			return newDate;
		}catch (Exception e){
		    e.printStackTrace();;
		}
	    return null;
	}
	public String toStrMinCeil(String str){
		String resultStr = str;
		try { 
			Date date = toDate(str);
			String dateFormat = "mm";
			String mm = toStr(date, dateFormat); 
		
			if (mm.equals("00")){
				// 정각일때
			}else {
				// 아닐때
				date = addHour(date, 1);
			}
			String returnDateFormat = "yyyyMMddHH";
			return toStr(date, returnDateFormat);
		}catch (Exception e){
		    e.printStackTrace();;
		}
	    return str;
	}
	public Date addYear(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}
	
	public Date addMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	public Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
	public Date addHour (Date date, int param) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, param);
		return cal.getTime();
	}
	public Date addMin (Date date, int param) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, param);
		return cal.getTime();
	}
	public Date addSec(Date date, int sec) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, sec);
		return cal.getTime();
	}
	public Date firstDayofMonth(Date date) throws Exception{
		String str = yyyy_mm.format(date) + "-1";
		return yyyy_mm_dd.parse(str);
	}
	public Date lastDayofMonth(Date date) throws Exception{
		Date firstDay = firstDayofMonth(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(firstDay);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	public int diffSec(Date date1, Date date2){
		
		Long term0 = (date1.getTime() - date2.getTime()) / 1000;
		int term = new BigDecimal(term0).intValueExact();
		return term;
	}
	public int diffMin(Date date1, Date date2){
		
		Long term0 = (date1.getTime() - date2.getTime()) / (60 * 1000);
		int term = new BigDecimal(term0).intValueExact();
		return term;
	}
	public int diffHour(Date date1, Date date2){
		
		Long term0 = (date1.getTime() - date2.getTime()) / (60 * 60 * 1000);
		int term = new BigDecimal(term0).intValueExact();
		return term;
	}
	public int diffDay(Date date1, Date date2){
		
		Long term0 = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
		int term = new BigDecimal(term0).intValueExact();
		return term;
	}
	public int diffMonth(Date date1, Date date2){

		Long term0 = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
		int term = new BigDecimal(term0).intValueExact();
		return term;
	}
}
