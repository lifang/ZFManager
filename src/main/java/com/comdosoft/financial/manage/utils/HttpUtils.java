package com.comdosoft.financial.manage.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.CollectionUtils;

public class HttpUtils {
	
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
		HttpUriRequest request = request(builder,headers,params,null);
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
		HttpUriRequest request = request(builder,headers,params,fileParams);
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
		if(!CollectionUtils.isEmpty(headers)){
			headers.forEach((k,v)->{
				builder.addHeader(k, v);
			});
		};
		//是否需要上传文件
		if(!CollectionUtils.isEmpty(fileParams)){
			if(!CollectionUtils.isEmpty(params)) {
				params.forEach((k,v)->{
					builder.addParameter(k, v);
				});
			}
		}else {
			MultipartEntityBuilder multiBuilder = MultipartEntityBuilder.create();
			fileParams.forEach((k,v)->{
				multiBuilder.addBinaryBody(k, v);
			});
			if(!CollectionUtils.isEmpty(params)) {
				params.forEach((k,v)->{
					multiBuilder.addTextBody(k, v);
				});
			}
			builder.setEntity(multiBuilder.build());
		}
		return builder.build();
	}
}
