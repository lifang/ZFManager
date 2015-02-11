package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
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

import com.comdosoft.financial.manage.joint.JointHandler;
import com.comdosoft.financial.manage.joint.JointResult;
import com.comdosoft.financial.manage.utils.HttpUtils;
import com.google.common.collect.Maps;

public abstract class Action implements ResponseHandler<JointResult>,JointHandler{
	
	private static final String BASE_URL = "http://106.37.206.154:13002";
//	private static final String BASE_URL = "http://wsposp.cnepay.net:80";
	
	private static final AtomicInteger req = new AtomicInteger();
	
	public static void acts(Action action) throws IOException {
		String url = BASE_URL + action.url();
		JointResult result = HttpUtils.post(url, action.headers(),
				action.params(), action.fileParams(), action);
		action.handle(result);
	}
	
	@Override
	public JointResult handleResponse(HttpResponse response)
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
        });;
        JointResult result = parseResult(headMap,entity.getContent());
        EntityUtils.consume(entity);
        return result;
	}
	
	protected abstract JointResult parseResult(Map<String,String> headers,InputStream stream);

	protected Map<String,String> createParams() {
		Map<String,String> params = Maps.newHashMap();
		String currentDate=DateTimeFormatter.ofPattern("yyyyMMddhh24mmss").format(LocalDate.now());
		params.put("reqTime",currentDate);
		params.put("reqNo",Integer.toString(req.getAndIncrement()));
//		params.put("position","460,0,6157,55153");
//		params.put("agencyCode","ZF2001");
//		params.put("appVersion","zf.zfmini.1.3.1111");
//		params.put("product","SHZF");
		return params;
	}

	public Map<String, String> params() {
		return createParams();
	}

	public Map<String, String> headers() {
		return null;
	}

	public Map<String, File> fileParams() {
		return null;
	}

	protected abstract String url();
	
	protected static void setReqNo(int reqNo) {
		req.set(reqNo);
	}
	
}
