package com.comdosoft.financial.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

@Service
public class PushNotificationService {
	@Value("${push.apiKeyUserAndphone}")
	private String apiKeyUserAndphone; //0:用户安卓手机
	@Value("${push.secretKeyUserAndphone}")
	private String secretKeyUserAndphone; //0:用户安卓手机
	@Value("${push.apiKeyUserIphone}")
	private String apiKeyUserIphone; //4:用户iphone
	@Value("${push.secretKeyUserIphone}")
	private String secretKeyUserIphone; //4:用户iphone
	/*
	@Value("${push.apiKeyUserAndpad}")
	private String apiKeyUserAndpad; //2：用户安卓pad
	@Value("${push.secretKeyUserAndpad}")
	private String secretKeyUserAndpad; //2：用户安卓pad
	@Value("${push.apiKeyUserIpad}")
	private String apiKeyUserIpad; //6：用户ipad
	@Value("${push.secretKeyUserIpad}")
	private String secretKeyUserIpad; //6：用户ipad
	@Value("${push.apiKeyAgentAndphone}")
	private String apiKeyAgentAndphone; //1：代理商安卓手机
	@Value("${push.secretKeyAgentAndphone}")
	private String secretKeyAgentAndphone; //1：代理商安卓手机
	@Value("${push.apiKeyAgentIphone}")
	private String apiKeyAgentIphone; //5：代理商iphone
	@Value("${push.secretKeyAgentIphone}")
	private String secretKeyAgentIphone; //5：代理商iphone
	@Value("${push.apiKeyAgentAndpad}")
	private String apiKeyAgentAndpad; //3：代理商安卓pad
	@Value("${push.secretKeyAgentAndpad}")
	private String secretKeyAgentAndpad; //3：代理商安卓pad
	@Value("${push.apiKeyAgentIpad}")
	private String apiKeyAgentIpad; //7：代理商ipad
	@Value("${push.secretKeyAgentIpad}")
	private String secretKeyAgentIpad; //7：代理商ipad
	*/
	
	/**
	 * 
	 * @param title
	 * @param message
	 * @param channelId
	 * @param deviceType 0:用户安卓手机 1：代理商安卓手机 2：用户安卓pad 3：代理商安卓pad 4:用户iphone 5：代理商iphone 6：用户ipad 7：代理商ipad
	 * @throws PushClientException
	 * @throws PushServerException
	 */
	public void pushMsgToSingleDevice (String title,String message,String channelId,
			String deviceType,Integer messageId) throws PushClientException, PushServerException{
		/*1. 创建PushKeyPair
         *用于app的合法身份认证
         *apikey和secretKey可在应用详情中获取
         */
		String apiKey = "";
		String secretKey = "";
		String flg = "";
		if("0".equals(deviceType)){
			apiKey = apiKeyUserAndphone;
			secretKey = secretKeyUserAndphone;
			flg = "android";
		}
		/*
		if("1".equals(deviceType)){
			apiKey = apiKeyAgentAndphone;
			secretKey = secretKeyAgentAndphone;
			flg = "android";
		}
		if("2".equals(deviceType)){
			apiKey = apiKeyUserAndpad;
			secretKey = secretKeyUserAndpad;
			flg = "android";
		}
		if("3".equals(deviceType)){
			apiKey = apiKeyAgentAndpad;
			secretKey = secretKeyAgentAndpad;
			flg = "android";
		}*/
		if("4".equals(deviceType)){
			apiKey = apiKeyUserIphone;
			secretKey = secretKeyUserIphone;
			flg = "ios";
		}/*
		if("5".equals(deviceType)){
			apiKey = apiKeyAgentIphone;
			secretKey = secretKeyAgentIphone;
			flg = "ios";
		}
		if("6".equals(deviceType)){
			apiKey = apiKeyUserIpad;
			secretKey = secretKeyUserIpad;
			flg = "ios";
		}
		if("7".equals(deviceType)){
			apiKey = apiKeyAgentIpad;
			secretKey = secretKeyAgentIpad;
			flg = "ios";
		}*/
        PushKeyPair pair = new PushKeyPair(apiKey,secretKey);
        // 2. 创建BaiduPushClient，访问SDK接口
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);
        // 3. 注册YunLogHandler，获取本次请求的交互信息
        pushClient.setChannelLogHandler (new YunLogHandler () {
            @Override
            public void onHandle (YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });
        try {
        // 4. 设置请求参数，创建请求实例
        	PushMsgToSingleDeviceRequest request = null;
        	if("ios".equals(flg)){
        		request = new PushMsgToSingleDeviceRequest().
        				addChannelId(channelId).
        				addMsgExpires(new Integer(3600)).   //设置消息的有效时间,单位秒,默认3600*5.
        				addMessageType(1).              //设置消息类型,0表示透传消息,1表示通知,默认为0.
        				addMessage("{\"aps\":{\"alert\":\"" + title +"\",\"badge\":1,\"sound\":\"default\"},"+
        						"\"msgId\":\"" + messageId +"\",\"title\":\"" + title + "\",\"description\":\"" 
        						+ message + "\"}").
        				addDeviceType(4)
        				.addDeployStatus(1);
        	}else if("android".equals(flg)){
        		request = new PushMsgToSingleDeviceRequest().
        				addChannelId(channelId).
        				addMsgExpires(new Integer(3600)).   //设置消息的有效时间,单位秒,默认3600*5.
        				addMessageType(1).              //设置消息类型,0表示透传消息,1表示通知,默认为0.
        				addMessage("{\"title\":\"" + title + "\",\"description\":\"" + message 
        						+ "\",\"custom_content\":{\"msgId\":\""+messageId+"\"}}");
        	}
        	
        // 5. 执行Http请求
            PushMsgToSingleDeviceResponse response = pushClient.
                pushMsgToSingleDevice(request);
        // 6. Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId()
                    + ",sendTime: " + response.getSendTime());
        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) { 
                throw e;
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }
    }

}
