package com.framework.module.common.schema;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.apache.commons.lang3.StringUtils;

/**
 * 抽象UUID Entity
 * 
 * @author qq
 * 
 */
@MappedSuperclass
public abstract class UUIdEntity extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6229015428051910443L;

	/**
	 * UUID主键
	 */
	@Id
	@Column(name = "id_", length = 32, nullable = false, updatable = false)
	protected String id;

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(id)) {
			this.id = UUID.randomUUID().toString().replace("-", "");
		}
		super.prePersist();
	}

	/*
	 * getter、setter
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
