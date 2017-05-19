package com.framework.module.common.security.swt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * SWT(Simple Web Token)认证Token
 * 
 * @author qq
 *
 */
public class SWTAuthenticationToken implements AuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3510161037545545453L;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/*
	 * constructor
	 */
	public SWTAuthenticationToken(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	@Override
	public Object getPrincipal() {
		return this.getUserName();
	}

	@Override
	public Object getCredentials() {
		return this.getPassword();
	}

	/*
	 * getter、setter
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
