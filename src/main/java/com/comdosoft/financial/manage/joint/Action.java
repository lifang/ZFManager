package com.comdosoft.financial.manage.joint;

import java.io.File;
import java.util.Map;

public interface Action {

	Map<String,String> params();
	
	Map<String,String> headers();
	
	Map<String,File> fileParams();
	
	AbstractHandler handler();
	
	void action();
	
	String url();
	
	Method method();
	
	enum Method{
		GET,POST
	}
}
