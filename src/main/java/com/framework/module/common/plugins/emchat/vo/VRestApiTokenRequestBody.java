package com.framework.module.common.plugins.emchat.vo;

/**
 * 环信获取Rest Api token请求实体 VO
 * 
 * @author qq
 *
 */
public class VRestApiTokenRequestBody {
	/**
	 * 授权类型
	 */
	private String grant_type;

	/**
	 * 
	 */
	private String client_id;

	/**
	 * 
	 */
	private String client_secret;

	/*
	 * constractor
	 */
	public VRestApiTokenRequestBody(String client_id, String client_secret) {
		super();
		this.grant_type = "client_credentials";
		this.client_id = client_id;
		this.client_secret = client_secret;
	}

	/*
	 * getter、setter
	 */
	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

}
