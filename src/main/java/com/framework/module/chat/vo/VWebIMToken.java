package com.framework.module.chat.vo;

/**
 * WebIM Token VO
 * 
 * @author qq
 *
 */
public class VWebIMToken {
	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * WebIM Token
	 */
	private String token;

	/**
	 * 是否在线(true: 在线)
	 */
	private Boolean isOnLine;

	/*
	 * constructor
	 */
	public VWebIMToken(String userName, String token, boolean isOnLine) {
		super();
		this.userName = userName;
		this.token = token;
		this.isOnLine = isOnLine;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsOnLine() {
		return isOnLine;
	}

	public void setIsOnLine(Boolean isOnLine) {
		this.isOnLine = isOnLine;
	}

}
