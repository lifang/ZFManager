package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.comdosoft.financial.manage.joint.Action;
import com.google.common.collect.Maps;

public abstract class BaseAction implements Action {
	
	protected Map<String,String> createParams() {
		Map<String,String> params = Maps.newHashMap();
		params.put("position","460,0,6157,55153");
		params.put("agencyCode","ZF2001");
		params.put("appVersion","zf.zfmini.1.3.1111");
		String currentDate=DateTimeFormatter.ofPattern("yyyyMMddhh24mmss").format(LocalDate.now());
		params.put("reqTime",currentDate);
		params.put("reqNo","20130101");
		params.put("product","SHZF");
		return params;
	}

	@Override
	public Map<String, String> params() {
		return createParams();
	}

	@Override
	public Map<String, String> headers() {
		return null;
	}

	@Override
	public Map<String, File> fileParams() {
		return null;
	}

	@Override
	public Method method() {
		return Method.POST;
	}
}
