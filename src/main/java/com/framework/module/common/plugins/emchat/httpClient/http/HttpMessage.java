package com.framework.module.common.plugins.emchat.httpClient.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * http报文
 * 
 * @author qq
 *
 */
public abstract class HttpMessage {
	/**
	 * 首部
	 */
	private Map<String, String> headers = new HashMap<String, String>();

	/**
	 * 实体
	 */
	private Object body;

	/*
	 * ***********************************************************************
	 * 首部相关方法
	 * ***********************************************************************
	 */
	/**
	 * 添加首部
	 * 
	 * @param key
	 * @param value
	 */
	public HttpMessage addHeader(String key, String value) {
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
			this.headers.put(key.trim(), value.trim());
		}

		return this;
	}

	/**
	 * 批量添加首部
	 * 
	 * @param keyValues
	 *            格式：key1,value1,key2,value2
	 */
	public HttpMessage addHeaders(String... keyValues) {
		int length = 0;
		if (keyValues != null && (length = keyValues.length) > 0) {
			if (length % 2 != 0) { // 奇数个，去掉最后一个
				--length;
			}

			for (int i = 0; i < length; i += 2) {
				this.addHeader(keyValues[i], keyValues[i + 1]);
			}
		}

		return this;
	}

	/**
	 * 批量添加首部
	 * 
	 * @param headers
	 */
	public HttpMessage addHeaders(Map<String, String> headers) {
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			this.addHeader(key, headers.get(key));
		}

		return this;
	}

	/**
	 * 获取首部
	 * 
	 * @param key
	 * @return
	 */
	public String getHeader(String key) {
		return (key == null) ? null : headers.get(key.trim());
	}

	/**
	 * 获取所有头部
	 * 
	 * @return
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * 删除首部
	 * 
	 * @param key
	 */
	public void removeHeader(String key) {
		if (key != null) {
			headers.remove(key.trim());
		}
	}

	/**
	 * 批量删除首部
	 * 
	 * @param keys
	 */
	public void removeHeaders(String... keys) {
		int length = 0;

		if (keys != null && (length = keys.length) > 0) {
			for (int i = 0; i < length; i++) {
				this.removeHeader(keys[i]);
			}
		}
	}

	/**
	 * 是否有首部
	 * 
	 * @return true:无首部
	 */
	public boolean headerIsEmpty() {
		return (headers == null) ? true : headers.isEmpty();
	}

	public HttpMessage addJsonContentHeader() {
		return this.addHeader("Content_Type", "application/json");
	}

	public HttpMessage addMediaAccept() {
		return this.addHeader("Accept", "application/octet-stream");
	}

	public HttpMessage addAuthorization(String token) {
		return this.addHeader("Authorization", "Bearer " + token);
	}

	public HttpMessage addRestrictAccess() {
		return this.addHeader("Restrict_Access", "true");
	}

	public HttpMessage addThumbnail() {
		return this.addHeader("Thumbnail", "true");
	}

	public HttpMessage addShareSecret(String secret) {
		return this.addHeader("Share_Secret", secret);
	}

	/**
	 * 添加默认首部
	 * 
	 * @return
	 */
	public HttpMessage addDefaultHeader() {
		return this.addJsonContentHeader();
	}

	/**
	 * 添加包含token的默认首部
	 * 
	 * @param token
	 * @return
	 */
	public HttpMessage addDefaultHeaderWithToken(String token) {
		return this.addDefaultHeader().addAuthorization(token);
	}

	// public HttpMessage addUploadHeaderWithToken(String token) {
	// return this.addAuthorization(token).addRestrictAccess();
	// }
	//
	// public HttpMessage addDownloadHeaderWithToken(String token, String
	// shareSecret, Boolean isThumb) {
	// this.addAuthorization(token).addMediaAccept().addShareSecret(shareSecret);
	// if (isThumb) {
	// this.addThumbnail();
	// }
	// return this;
	// }

	/*
	 * ***********************************************************************
	 * 实体相关方法
	 * ***********************************************************************
	 */
	/**
	 * 获取实体
	 * 
	 * @return
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * 设置实体
	 * 
	 * @param body
	 */
	public void setBody(Object body) {
		this.body = body;
	}

}
