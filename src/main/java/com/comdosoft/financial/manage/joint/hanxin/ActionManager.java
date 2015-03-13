package com.comdosoft.financial.manage.joint.hanxin;

import org.apache.http.client.protocol.HttpClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.manage.joint.JointHandler;
import com.comdosoft.financial.manage.utils.HttpUtils;

public class ActionManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(ActionManager.class);
	
	private HttpClientContext context = HttpClientContext.create();
	private String url;
	private String rsaKey;
	
	public void acts(RequestBean request,JointHandler handler){
		try {
			String sendData = request.generateBody(this);
			ResponseBean response = HttpUtils.post(url, sendData, context, request);
			handler.handle(response);
		} catch (Exception e) {
			LOG.error("",e);
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setRsaKey(String rsaKey) {
		this.rsaKey = rsaKey;
	}

	public String getRsaKey() {
		return rsaKey;
	}
	
}
