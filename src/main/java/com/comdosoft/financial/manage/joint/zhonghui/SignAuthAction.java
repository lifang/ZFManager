package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import com.comdosoft.financial.manage.joint.JointResult;
import com.google.common.collect.Maps;

public class SignAuthAction extends Action {
	
	private File signature;

	public SignAuthAction(File signature) {
		this.signature = signature;
	}

	@Override
	public Map<String, File> fileParams() {
		Map<String, File> fileParams = Maps.newHashMap();
		fileParams.put("signature", signature);
		return fileParams;
	}

	@Override
	public String url() {
		return "/user/signatureAuth";
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
