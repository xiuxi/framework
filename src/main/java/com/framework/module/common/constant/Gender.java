package com.framework.module.common.constant;

/**
 * 性别 enum
 * 
 * @author qq   
 * 
 */
public enum Gender {
	SECRECY("SECRECY", "保密"), MAILE("MALE", "男"), FEMALE("FEMALE", "女");

	private String key;
	private String value;

	/*
	 * constructor
	 */
	private Gender(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/*
	 * getter、setter
	 */
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
