package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import com.comdosoft.financial.manage.joint.JointResult;
import com.google.common.collect.Maps;

public class RealnameAuthAction extends Action {
	
	private String name;
	private String idNumber;
	private File personal;
	private File personalBack;

	public RealnameAuthAction(String name, String idNumber, File personal,
			File personalBack) {
		this.name = name;
		this.idNumber = idNumber;
		this.personal = personal;
		this.personalBack = personalBack;
	}

	@Override
	public Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("name",name);
		params.put("idNumber", idNumber);
		return params;
	}

	@Override
	public Map<String, File> fileParams() {
		Map<String, File> fileParams = Maps.newHashMap();
		fileParams.put("personal", personal);
		fileParams.put("personalBack", personalBack);
		return fileParams;
	}

	@Override
	public String url() {
		return "/user/realnameAuth";
	}

	@Override
	public void handle(JointResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	protected JointResult parseResult(Map<String, String> headers,
			InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

}
