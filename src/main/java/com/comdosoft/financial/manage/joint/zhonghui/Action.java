package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.manage.joint.JointHandler;
import com.comdosoft.financial.manage.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

public abstract class Action implements ResponseHandler<Result>{
	
	private static final Logger LOG = LoggerFactory.getLogger(Action.class);
	
	private static final String BASE_URL = "http://zftdev.21er.net:15080";
//	private static final String BASE_URL = "http://wsposp.cnepay.net:80";
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final AtomicInteger req = new AtomicInteger();
	private JointHandler handler;
	
	public static void acts(Action action) throws IOException {
		String url = BASE_URL + action.url();
		LOG.debug("url:{}",url);
		Result result = null;
		if(action.getMethod() == Method.POST) {
			result = HttpUtils.post(url, action.headers(),
					action.params(), action.fileParams(), action);
		}
		if(action.getMethod() == Method.GET) {
			result = HttpUtils.get(url, action.headers(),
					action.params(), action);
		}
		if(action.handler!=null&&result!=null) {
			action.handler.handle(result);
		}
	}
	
	@Override
	public Result handleResponse(HttpResponse response)
			throws IOException {
        final StatusLine statusLine = response.getStatusLine();
        final HttpEntity entity = response.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }
        Header[] headers = response.getAllHeaders();
        Map<String,String> headMap = Maps.newHashMap();
        Arrays.asList(headers).stream().forEach((h)->{
        	headMap.put(h.getName(),h.getValue());
        });
        Result result = null;
        if(LOG.isDebugEnabled()) {
        	String res = EntityUtils.toString(entity);
        	LOG.debug("resp:{}",res);
        	InputStream stream = new ByteArrayInputStream(res.getBytes());
        	result = parseResult(headMap,stream);
        }else {
        	result = parseResult(headMap,entity.getContent());
        	EntityUtils.consume(entity);
        }
        return result;
	}

	public Action setHandler(JointHandler handler) {
		this.handler = handler;
		return this;
	}
	
	protected Result parseResult(Map<String,String> headers,InputStream stream){
		Result result = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.readValue(stream, getResultType());
		} catch (IOException e) {
			LOG.error("mapper error",e);
		}
		return result;
	}

	protected Map<String,String> createParams() {
		Map<String,String> params = Maps.newHashMap();
		String currentDate=formatter.format(LocalDateTime.now());
		params.put("reqTime",currentDate);
		params.put("reqNo",Integer.toString(getReqNo()));
		return params;
	}
	
	/**
	 * 获取下一个reqNo
	 * @return
	 */
	protected int getReqNo(){
		return req.getAndIncrement();
	}
	
	protected Map<String, String> params() {
		return createParams();
	}

	protected Map<String, String> headers() {
		return null;
	}

	protected Map<String, File> fileParams() {
		return null;
	}
	
	protected Method getMethod(){
		return Method.POST;
	}

	protected abstract String url();

	protected abstract Class<? extends Result> getResultType();
	
	protected static enum Method {
		POST,GET;
	}
}