package com.framework.module.common.plugins.emchat.vo;

import com.framework.module.common.plugins.emchat.constant.ContentType;

/**
 * 环信发送消息扩展字段 VO
 * 
 * @author qq
 *
 */
public class VExt {
	/**
	 * 发送者
	 */
	private String from;

	/**
	 * 消息内容类型
	 */
	private ContentType contentType;

	/**
	 * 消息内容
	 */
	private String msg;

	/*
	 * getter、setter
	 */
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

}
