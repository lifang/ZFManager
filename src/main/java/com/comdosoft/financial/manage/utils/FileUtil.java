package com.comdosoft.financial.manage.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
	
	public static String getPathFileName(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MMdd/");
		return getPath()+date.getTime();
	}

    public static String getPath(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MMdd/");
        return format.format(date);
    }

}
