package com.comdosoft.financial.manage.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
	
	public static String getFilePath(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MMdd/");
		return format.format(date)+date.getTime();
	}

}
