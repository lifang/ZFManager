package com.comdosoft.financial.manage.joint.hanxin;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.manage.utils.HttpUtils;
import com.comdosoft.financial.manage.utils.StringUtils;

public abstract class RequestBean implements ResponseHandler<ResponseBean> {
	
	private static final Logger LOG = LoggerFactory.getLogger(RequestBean.class);
	
	private String desKey;
	
	private String application;
	private String version;
	private String sendSeqId;
	private String sendTime;
	private String terminalId;
	
	public RequestBean(String application){
		setApplication(application);
		setVersion("1.0.0");
	}

	@XmlAttribute
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	
	@XmlAttribute
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@XmlAttribute
	public String getSendSeqId() {
		return sendSeqId;
	}
	public void setSendSeqId(String sendSeqId) {
		this.sendSeqId = sendSeqId;
	}
	
	@XmlAttribute
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getDesKey() {
		return desKey;
	}

	public abstract Class<? extends ResponseBean> getResponseClass();
	
	public String generateBody(ActionManager manager) throws Exception{
		desKey = StringUtils.randomString(32);
		String des3desKey=RSACoder.encryptByPublicKey(desKey,manager.getRsaKey());
		String baseTerminal=Base64.encodeBase64String(getTerminalId().getBytes());
		byte[] desContent=DesUtil.encrypt(toString().getBytes(),desKey.getBytes());
		String sendData=baseTerminal+"|"+des3desKey+"|"+Base64.encodeBase64String(desContent);
		return sendData;
	}

	@Override
	public ResponseBean handleResponse(HttpResponse res)
			throws IOException {
		final StatusLine statusLine = res.getStatusLine();
        final HttpEntity entity = res.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }
        String resString = EntityUtils.toString(entity,HttpUtils.DEFAULT_CHARSET);
        LOG.debug(resString);
        ResponseBean resp = ResponseBean.parseBody(resString, this);
		return resp;
	}

	@Override
	public String toString() {
		Writer writer = new StringWriter();
		JAXB.marshal(this, writer);
		return writer.toString();
	}

}
