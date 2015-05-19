package com.comdosoft.financial.manage.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RootUrl {

    public static String localpath;


    public static String filepath;

    
    public  String getLocalpath() {
        return localpath;
    }

    @Autowired
    public  void setLocalpath(@Value("${localpath}")String localpath) {
        RootUrl.localpath = localpath;
    }

    public  String getFilepath() {
        return filepath;
    }

    @Autowired
    public  void setFilepath(@Value("${filePath}")String filepath) {
        RootUrl.filepath = filepath;
    }
    
}
