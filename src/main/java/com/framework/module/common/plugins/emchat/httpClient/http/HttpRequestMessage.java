package com.framework.module.common.plugins.emchat.httpClient.http;

import org.springframework.util.Assert;

import com.framework.module.common.plugins.emchat.constant.HttpRequestMethod;

/**
 * http请求报文
 * 
 * @author qq
 *
 */
public class HttpRequestMessage extends HttpMessage {
	/**
	 * 请求方法
	 */
	private HttpRequestMethod method;

	/**
	 * 请求路径
	 */
	private String url;

	/*
	 * constructor
	 */
	private HttpRequestMessage() {

	}

	public HttpRequestMessage(HttpRequestMethod method, String url) {
		Assert.hasText(url, "请求路径不能为空");

		this.method = (method == null) ? HttpRequestMethod.GET : method;
		this.url = url;
	}

	public HttpRequestMethod getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}
	
	/*
	 * getter
	 */
	
}
