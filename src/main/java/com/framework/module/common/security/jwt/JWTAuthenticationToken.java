package com.framework.module.common.security.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWT(JSON Web Token)认证Token
 * 
 * @author qq
 *
 */
public class JWTAuthenticationToken implements AuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2200366613025720520L;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * token
	 */
	private String token;

	/*
	 * constructor
	 */
	public JWTAuthenticationToken(String userName, String token) {
		super();
		this.userName = userName;
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return this.getUserName();
	}

	@Override
	public Object getCredentials() {
		return this.getToken();
	}

	/*
	 * getter、setter
	 */
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
