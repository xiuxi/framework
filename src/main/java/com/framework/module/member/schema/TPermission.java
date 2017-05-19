package com.framework.module.member.schema;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.framework.module.common.schema.UUIdEntity;

/**
 * 权限 Entity
 * 
 * @author qq
 * 
 */
@Entity
@Table(indexes = {}, name = "t_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "non-lazy")
// @SelectBeforeUpdate(true)
@DynamicUpdate
@DynamicInsert
public class TPermission extends UUIdEntity {
	/**
	 * 权限编码
	 */
	@Column(unique = true, nullable = false)
	private String code;

	/**
	 * 权限名称
	 */
	@Column
	private String name;

	/**
	 * 权限描述
	 */
	@Column
	private String mark;

	/**
	 * 角色
	 */
	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "permissions")
	private List<TRole> roles;

	/*
	 * getter、setter
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public List<TRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TRole> roles) {
		this.roles = roles;
	}

}
