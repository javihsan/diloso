package com.diloso.app.negocio.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
	
	public static String formatDateICS = "yyyyMMdd'T'HHmmss";
	public static String formatDateText = "EEEE, dd MMMM yyyy HH:mm";
	public static String formatDateJava = "yyyy-MM-dd";

	public static String getFormat(Date date, String format, Locale locale) {
		String result = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
			result = formatter.format(date);
		} catch (Exception e) {
		}
		return result;
	}
	
	public static String getFormatICS(Date date, Locale locale) {
		return getFormat(date, formatDateICS, locale);
	}
	
	public static String getFormatText(Date date, Locale locale) {
		return getFormat(date, formatDateText, locale);
	}
	
	public static Date getDate(String date, String format, Locale locale) {
		Date result = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
			result = formatter.parse(date);
		} catch (Exception e) {

		}
		return result;
	}
	
	public static Date getDate(String date, Locale locale) {
		return getDate(date, formatDateJava, locale);
	}
}
