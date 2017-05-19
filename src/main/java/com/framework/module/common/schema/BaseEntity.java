package com.framework.module.common.schema;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * 抽象 Entity，用于联合主键表
 * 
 * @author qq
 * 
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -625171662675655338L;

	/**
	 * 创建时间
	 */
	@Column(name = "date_created_", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	// @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	protected Date dateCreated;

	/**
	 * 最后更新时间
	 */
	@Column(name = "last_updated_", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	// @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	protected Date lastUpdated;

	/**
	 * 是否无效（默认是有效）
	 */
	@Column(name = "disabled_", columnDefinition = "decimal(1,0)", nullable = false)
	protected Boolean disabled;

	/**
	 * 数据库版本号（用于乐观锁）
	 */
	@Version
	@Column(name = "version_", nullable = false)
	protected Integer version;

	@PrePersist
	public void prePersist() {
		if (dateCreated == null) {
			this.dateCreated = new Date();
		}
		if (disabled == null) {
			this.disabled = false;
		}
		if (lastUpdated == null) {
			this.lastUpdated = this.dateCreated;
		}
		if (version == null) {
			version = 0;
		}
	}

	@PreUpdate
	public void preUpdate() {
		this.lastUpdated = new Date();
	}

	/*
	 * getter、setter
	 */
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
