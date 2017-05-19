package com.framework.module.member.schema;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.framework.module.common.constant.Gender;
import com.framework.module.common.schema.UUIdEntity;

/**
 * 用户 Entity
 * 
 * @author qq
 * 
 */
@Entity
@Table(indexes = {}, name = "t_member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "non-lazy")
// @SelectBeforeUpdate(true)
@DynamicUpdate
@DynamicInsert
public class TMember extends UUIdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7640431581185268023L;

	/**
	 * 手机号
	 */
	@Column(length = 11, nullable = false, unique = true)
	private String mobile;

	/**
	 * 用户姓名
	 */
	@Column
	private String name;

	/**
	 * 昵称
	 */
	@Column(name = "nick_name")
	private String nickName;

	/**
	 * 性别(默认：保密)
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 7)
	private Gender gender = Gender.SECRECY;

	/**
	 * 密码
	 */
	@Column
	private String password;

	/**
	 * 角色
	 */
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "non-lazy")
	@ManyToMany(cascade = CascadeType.REFRESH)
	private List<TRole> roles;

	/*
	 * getter、setter
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public List<TRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TRole> roles) {
		this.roles = roles;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
