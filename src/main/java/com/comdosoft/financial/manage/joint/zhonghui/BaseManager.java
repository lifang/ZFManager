package com.comdosoft.financial.manage.joint.zhonghui;

import com.comdosoft.financial.manage.joint.Manager;

public class BaseManager extends Manager {
	
	@Override
	public String baseUrl(){
		return "http://106.37.206.154:13002";
//		return "http://wsposp.cnepay.net:80";
	}

}
