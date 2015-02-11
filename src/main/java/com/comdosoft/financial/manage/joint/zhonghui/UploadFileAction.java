package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Map;

import com.comdosoft.financial.manage.joint.JointResult;
import com.google.common.collect.Maps;

public class UploadFileAction extends Action {
	
	private Type type;
	private File photo;

	public UploadFileAction(Type type, File photo) {
		this.type = type;
		this.photo = photo;
	}

	@Override
	public Map<String, File> fileParams() {
		Map<String, File> fileParams = Maps.newHashMap();
		fileParams.put("photo", photo);
		return fileParams;
	}

	@Override
	public String url() {
		return MessageFormat.format("/upload/{0}", type.getName());
	}

	public static enum Type{
		BUSINESS("business"),
		PERSONAL("personal"),
		PERSONAL_BACK("personalBack");
		
		private Type(String name) {
			this.name = name;
		}
		private String name;
		public String getName(){
			return name;
		}
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
