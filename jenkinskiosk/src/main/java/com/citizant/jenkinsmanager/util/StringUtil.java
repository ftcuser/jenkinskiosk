package com.citizant.jenkinsmanager.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StringUtil {
	

    public static final String EMPTY_STRING = "";
	
	public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
	}

	public static String getStandardDate(Date d)
	{
		return (new SimpleDateFormat("MM/dd/yyyy")).format(d);
	}

}
