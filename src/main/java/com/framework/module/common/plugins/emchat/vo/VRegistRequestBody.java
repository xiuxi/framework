package com.framework.module.common.plugins.emchat.vo;

/**
 * 环信注册用户请求实体 VO
 * 
 * @author qq
 *
 */
public class VRegistRequestBody {
	/**
	 * 用户
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/*
	 * constructor
	 */
	public VRegistRequestBody(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/*
	 * getter、setter
	 */
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
