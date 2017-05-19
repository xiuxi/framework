package com.framework.module.member.vo;

/**
 * 用户列表 VO
 * 
 * @author qq
 *
 */
public class VMemberItem {
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
	 * 性别value
	 */
	private String genderValue;

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

	public String getGenderValue() {
		return genderValue;
	}

	public void setGenderValue(String genderValue) {
		this.genderValue = genderValue;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
