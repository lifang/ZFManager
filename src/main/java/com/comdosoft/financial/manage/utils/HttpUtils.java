package com.comdosoft.financial.manage.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

public class HttpUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
	
	private HttpUtils(){}
	
	private static HttpClient client = HttpClients.createDefault();

	/**
	 * get请求
	 * @param url
	 * @param headers
	 * @param params
	 * @param handler
	 * @return
	 * @throws IOException
	 */
	public static <T> T get(String url,Map<String,String> headers,
			Map<String,String> params,ResponseHandler<T> handler) throws IOException{
		RequestBuilder builder = RequestBuilder.get();
		builder.setUri(url);
		HttpUriRequest request = request(builder,headers,params,null);
		LOG.debug("request:{}",request);
		return response(request, handler);
	}
	
	/**
	 * post 请求
	 * @param url
	 * @param headers
	 * @param params
	 * @param fileParams
	 * @param handler
	 * @return
	 * @throws IOException
	 */
	public static <T> T post(String url,Map<String,String> headers,
			Map<String,String> params,Map<String,File> fileParams,ResponseHandler<T> handler) throws IOException{
		RequestBuilder builder = RequestBuilder.post();
		builder.setUri(url);
		HttpUriRequest request = request(builder,headers,params,fileParams);
		LOG.debug("request:{}",request);
		return response(request, handler);
	}
	
	//处理返回
	private static <T> T response(HttpUriRequest request,ResponseHandler<T> handler) throws IOException {
		HttpResponse response = client.execute(request);
		return handler.handleResponse(response);
	}
	
	//创建request
	private static HttpUriRequest request(RequestBuilder builder,
			Map<String,String> headers,Map<String,String> params,Map<String,File> fileParams) {
		//添加head
		if(!CollectionUtils.isEmpty(headers)){
			headers.forEach((k,v)->{
				builder.addHeader(k, v);
			});
		}
		if(HttpGet.METHOD_NAME.equalsIgnoreCase(builder.getMethod())){
			return requestGet(builder, params);
		}
		if(HttpPost.METHOD_NAME.equalsIgnoreCase(builder.getMethod())){
			return requestPost(builder, params, fileParams);
		}
		return builder.build();
	}
	
	private static HttpUriRequest requestGet(RequestBuilder builder,
			Map<String,String> params){
		if(!CollectionUtils.isEmpty(params)) {
			params.forEach((k,v)->{
				builder.addParameter(k, v);
			});
		}
		return builder.build();
	}
	
	private static HttpUriRequest requestPost(RequestBuilder builder,
			Map<String,String> params, Map<String,File> fileParams) {
		HttpEntity entity = null;
		//是否需要上传文件
		if(CollectionUtils.isEmpty(fileParams)){
			List<NameValuePair> parameters = Lists.newArrayList();
			if(params!=null){
				params.forEach((k,v)->{
					parameters.add(new BasicNameValuePair(k, v));
				});
			}
			entity = new UrlEncodedFormEntity(parameters, Consts.UTF_8);
		}else {
			MultipartEntityBuilder multiBuilder = MultipartEntityBuilder.create().setCharset(Consts.UTF_8);
			fileParams.forEach((k,v)->{
				multiBuilder.addBinaryBody(k, v);
			});
			if(!CollectionUtils.isEmpty(params)) {
				params.forEach((k,v)->{
					multiBuilder.addTextBody(k, v);
				});
			}
			entity = multiBuilder.build();
		}
		builder.setEntity(entity);
		return builder.build();
	}
}
