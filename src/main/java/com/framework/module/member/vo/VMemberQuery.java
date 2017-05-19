package com.framework.module.member.vo;

import java.util.Date;

import com.framework.module.common.constant.Gender;

/**
 * 用户查询条件 VO
 * 
 * @author qq
 *
 */
public class VMemberQuery {
	/**
	 * 搜索关键字(手机号/用户姓名)
	 */
	private String key;

	/**
	 * 性别
	 */
	private Gender gender;

	/**
	 * 会员注册日期起始日期
	 */
	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date begin;

	/**
	 * 会员注册日期截止日期
	 */
	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date end;

	/*
	 * getter、setter
	 */
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
