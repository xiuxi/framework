package com.framework.module.common.plugins.emchat.httpClient.http;

/**
 * http响应报文
 * 
 * @author qq
 *
 */
public class HttpResponseMessage extends HttpMessage {
	private Integer status;

	/*
	 * getter、setter
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
