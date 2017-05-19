package com.framework.module.common.plugins.emchat.vo;

/**
 * 环信获取Web IM token请求实体 VO
 * 
 * @author qq
 *
 */
public class VWebIMTokenRequestBody {
	/**
	 * 授权类型
	 */
	private String grant_type;

	/**
	 * 环信用户名
	 */
	private String username;

	/**
	 * 登录密码
	 */
	private String password;

	/*
	 * constractor
	 */
	public VWebIMTokenRequestBody(String username, String password) {
		super();
		this.grant_type = "password";
		this.username = username;
		this.password = password;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
