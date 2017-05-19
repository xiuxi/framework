package com.framework.module.common.vo;

import java.util.Map;

/**
 * Json对象 VO
 * 
 * 封装Controller返回Json对象
 * 
 * @author qq
 *
 */
public class VJson {
	/*
	 * 成功相关
	 */
	/**
	 * 成功返回的数据
	 */
	private Object datas;

	/*
	 * 错误相关
	 */
	/**
	 * 错误类型
	 */
	private String errorType;

	/**
	 * 错误信息
	 */
	private String error;

	/*
	 * 其他
	 */
	/**
	 * 是否成功
	 * 
	 * true: 成功
	 */
	private boolean success;

	/**
	 * 扩展信息
	 */
	private Map<String, Object> ext;

	/*
	 * constructor
	 * 
	 */
	/**
	 * 用于构建成功响应
	 * 
	 * @param datas
	 */
	public VJson(Object datas) {
		super();
		this.datas = datas;

		this.success = true;
	}

	/**
	 * 用于构建成功响应
	 * 
	 * @param datas
	 * @param ext
	 */
	public VJson(Object datas, Map<String, Object> ext) {
		super();
		this.datas = datas;
		this.ext = ext;

		this.success = true;
	}

	/**
	 * 用于构建失败响应
	 * 
	 * @param errorType
	 * @param error
	 */
	public VJson(String errorType, String error) {
		super();
		this.errorType = errorType;
		this.error = error;

		this.success = false;
	}

	/**
	 * 用于构建失败响应
	 * 
	 * @param errorType
	 * @param error
	 * @param ext
	 */
	public VJson(String errorType, String error, Map<String, Object> ext) {
		super();
		this.errorType = errorType;
		this.error = error;
		this.ext = ext;

		this.success = false;
	}

	/*
	 * getter
	 */
	public Map<String, Object> getExt() {
		return ext;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getErrorType() {
		return errorType;
	}

	public String getError() {
		return error;
	}

	public Object getDatas() {
		return datas;
	}

}
