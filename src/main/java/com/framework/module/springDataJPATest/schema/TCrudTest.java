package com.framework.module.springDataJPATest.schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.framework.module.common.schema.UUIdEntity;

/**
 * 增删改查测试 Entity
 * 
 * @author qq
 * 
 */
@Entity
@Table(indexes = {}, name = "t_crud_test")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "non-lazy")
// @SelectBeforeUpdate(true)
@DynamicUpdate
@DynamicInsert
public class TCrudTest extends UUIdEntity {
	/**
	 * 年龄
	 */
	@Column
	private Integer age;

	/**
	 * 姓名
	 */
	@Column
	private String name;

	/*
	 * getter、setter
	 */
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
