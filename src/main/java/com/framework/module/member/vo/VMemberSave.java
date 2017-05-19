package com.framework.module.member.vo;

import com.framework.module.common.constant.Gender;

/**
 * 用户保存 VO
 * 
 * @author qq
 *
 */
public class VMemberSave {
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

	/**
	 * 性别
	 */
	private Gender gender;

	/**
	 * 密码
	 */
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

}
