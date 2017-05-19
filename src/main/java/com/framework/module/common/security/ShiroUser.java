package com.framework.module.common.security;

import java.util.HashMap;
import java.util.Map;

/**
 * Shiro用户信息(存放当前登录用户信息在Shiro系统中)
 * 
 * @author qq
 * 
 */
public class ShiroUser {
	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户属性
	 */
	private Map<String, Object> attributes = new HashMap<String, Object>();

	/*
	 * contructor
	 */
	public ShiroUser() {
		super();
	}

	public ShiroUser(String userName, Map<String, Object> attributes) {
		super();
		this.userName = userName;
		this.attributes = attributes;
	}

	/*
	 * getter、setter
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
