package com.framework.module.common.plugins.emchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.framework.module.common.plugins.emchat.constant.HttpRequestMethod;
import com.framework.module.common.plugins.emchat.exception.InvalidGrantException;
import com.framework.module.common.plugins.emchat.exception.UserUnRegistException;
import com.framework.module.common.plugins.emchat.httpClient.HttpClientUtil;
import com.framework.module.common.plugins.emchat.httpClient.http.HttpRequestMessage;
import com.framework.module.common.plugins.emchat.httpClient.http.HttpResponseMessage;
import com.framework.module.common.plugins.emchat.vo.VRestApiTokenRequestBody;
import com.framework.module.common.plugins.emchat.vo.VWebIMTokenRequestBody;

/**
 * 环信token
 * 
 * @author qq
 *
 */
public class EmChatToken {
	private static final Logger log = LoggerFactory.getLogger(EmChatToken.class);

	/**
	 * 用于后端Rest AIP调用
	 */
	private String restApiToken;

	/**
	 * restApiToken有效截止日期
	 */
	private Long restApiExpiredAt = -1L;

	/**
	 * 用于前端web-im调用
	 * 
	 * 如使用token登录
	 */
	private String webIMToken;

	private EmchatContext context;

	public EmChatToken(EmchatContext context) {
		super();
		this.context = context;
	}

	/**
	 * 获取Rest AIP token
	 * 
	 * @param force
	 *            是否强制获取(true：不管有没过期都重新获取)
	 * @return
	 */
	public String getRestApiToken(Boolean force) {
		force = (null == force) ? Boolean.FALSE : force;

		if (isExpiredOfRestApiToken() || force) {
			String url = context.getService_Url() + "token";
			HttpRequestMessage request = new HttpRequestMessage(HttpRequestMethod.POST, url);
			request.addDefaultHeader();
			request.setBody(new VRestApiTokenRequestBody(context.getClient_Id(), context.getClient_Secret()));

			HttpResponseMessage response = HttpClientUtil.sendHttpRequest(request);
			ObjectNode objectNode = EmChatApi.parseObjectNode(response.getBody().toString());
			String newToken = objectNode.get("access_token").asText();
			Long newTokenExpire = objectNode.get("expires_in").asLong() * 1000; // 有效时间,秒为单位,
																				// 默认是七天,在有效期内是不需要重复获取的
			restApiToken = newToken;
			restApiExpiredAt = System.currentTimeMillis() + newTokenExpire;

			log.info("New restApiToken: " + newToken);
			log.info("New restApiToken expires: " + newTokenExpire);
		}
		return restApiToken;
	}

	/**
	 * 获取webIM token
	 * 
	 * 不要频繁向服务器发送获取token的请求，同一账号发送此请求超过一定频率会被服务器封号
	 * 
	 * @param userName
	 *            用户名
	 * @return token
	 * @throws UserUnRegistException
	 *             用户未注册
	 */
	public String getWebIMToken(String userName) throws UserUnRegistException {
		String url = context.getService_Url() + "token";

		HttpRequestMessage request = new HttpRequestMessage(HttpRequestMethod.POST, url);
		request.addDefaultHeader();
		request.setBody(new VWebIMTokenRequestBody(userName, context.getPassword()));

		HttpResponseMessage response = HttpClientUtil.sendHttpRequest(request);
		try {
			EmChatApi.checkResponse(response);
		} catch (InvalidGrantException e) {
			throw new UserUnRegistException();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		ObjectNode objectNode = EmChatApi.parseObjectNode(response.getBody().toString());
		String newToken = objectNode.get("access_token").asText();
		Long newTokenExpire = objectNode.get("expires_in").asLong() * 1000; // 有效时间,秒为单位,
																			// 默认是七天,在有效期内是不需要重复获取的
		webIMToken = newToken;

		log.info("New webToken: " + newToken);
		log.info("New webToken expires: " + newTokenExpire);

		return webIMToken;
	}

	/**
	 * Rest Api token是否过期(true：已过期)
	 * 
	 * @return
	 */
	private Boolean isExpiredOfRestApiToken() {
		return System.currentTimeMillis() > restApiExpiredAt;
	}

	/**
	 * 获取Rest Api token有效期
	 * 
	 * @return
	 */
	public Long getRestApiExpiredAt() {
		return restApiExpiredAt;
	}

	public void setContext(EmchatContext context) {
		this.context = context;
	}

}
