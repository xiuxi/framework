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
 * 角色 Entity
 * 
 * @author qq
 * 
 */
@Entity
@Table(indexes = {}, name = "t_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "non-lazy")
// @SelectBeforeUpdate(true)
@DynamicUpdate
@DynamicInsert
public class TRole extends UUIdEntity {
	/**
	 * 角色编码
	 */
	@Column(unique = true, nullable = false)
	private String code;

	/**
	 * 角色名称
	 */
	@Column
	private String name;

	/**
	 * 角色描述
	 */
	@Column
	private String mark;

	/**
	 * 用户
	 */
	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "roles")
	private List<TMember> members;

	/**
	 * 权限
	 */
	@ManyToMany(cascade = CascadeType.REFRESH)
	private List<TPermission> permissions;

	/*
	 * getter、setter
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public List<TMember> getMembers() {
		return members;
	}

	public void setMembers(List<TMember> members) {
		this.members = members;
	}

	public List<TPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<TPermission> permissions) {
		this.permissions = permissions;
	}

}
