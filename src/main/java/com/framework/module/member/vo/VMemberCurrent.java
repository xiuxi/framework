package com.framework.module.member.vo;

/**
 * 当前登录用户 VO
 * 
 * @author qq
 *
 */
public class VMemberCurrent {
	/**
	 * 用户ID
	 */
	private String id;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 昵称
	 */
	private String nickName;

	/*
	 * getter、setter
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
