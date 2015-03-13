package com.comdosoft.financial.manage.joint.hanxin;

import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.manage.joint.JointResult;

/**
 * response 对象
 * @author wu
 *
 */
public class ResponseBean implements JointResult {
	
	public static final Logger LOG = LoggerFactory.getLogger(ResponseBean.class);
	
	private int result;
	private String application;
	private String version;
	private String respCode;
	private String respDesc;

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
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
	@Override
	public int getResult() {
		return result;
	}
	
	@Override
	public boolean isSuccess() {
		return getResult()==RESULT_SUCCESS;
	}
	
	@Override
	public String getMsg() {
		return getRespDesc();
	}
	
	/**
	 * 解析返回数据
	 * @param body
	 * @param request
	 * @return
	 */
	public static ResponseBean parseBody(String body,RequestBean request){
		String[] resDataArr = body.trim().split("|");
		LOG.debug("resDataArr length......{}",resDataArr.length);
		String code = null;
		String msg = null;
		String content = null;
		switch(resDataArr.length){
		case 3:
			content = resDataArr[2];
		case 2:
			msg = resDataArr[1];
		case 1:
			code = resDataArr[0];
		}
		ResponseBean bean = null;
		if("0".equals(code)){
			bean = parseFalse(msg, content);
		}else if("1".equals(code)){
			bean = parseSuccess(msg, content,request);
		}
		return bean;
	}
	
	private static ResponseBean parseFalse(String msg,String content){
		ResponseBean bean = new ResponseBean();
		bean.result = RESULT_FAIL;
		bean.respCode = msg;
		byte[] bytes = Base64.decodeBase64(content);
		bean.respDesc = String.valueOf(bytes);
		return bean;
	}
	
	private static ResponseBean parseSuccess(String msg,String content,RequestBean request){
		byte[] desResData=Base64.decodeBase64(msg);
		byte[] respCheckValue = Base64.decodeBase64(content);
		try {
			byte[] resDataByte = DesUtil.decrypt(desResData,request.getDesKey().getBytes());
			String resData = String.valueOf(resDataByte);
			LOG.debug("resp string .....  {}",resData);
			String checkValue = DesUtil.md5Des(resData);
			if(checkValue.equals(String.valueOf(respCheckValue))){
				Reader reader = new StringReader(resData);
				ResponseBean resp = JAXB.unmarshal(reader, request.getResponseClass());
				resp.result = RESULT_SUCCESS;
				return resp;
			}
		} catch (Exception e) {
			LOG.error("",e);
		}
		ResponseBean bean = new ResponseBean();
		bean.result = RESULT_FAIL;
		bean.respCode = msg;
		bean.respDesc = "消息解析出错！";
		return bean;
	}
}
