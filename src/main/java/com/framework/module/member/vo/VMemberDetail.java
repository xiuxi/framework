package com.framework.module.member.vo;

import com.framework.module.common.constant.Gender;

/**
 * 用户详情 VO
 * 
 * @author qq
 *
 */
public class VMemberDetail {
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
	 * 性别
	 */
	private Gender gender;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getGenderValue() {
		return genderValue;
	}

	public void setGenderValue(String genderValue) {
		this.genderValue = genderValue;
	}

}
