package com.framework.module.common.plugins.emchat.exception;

/**
 * 服务未找到异常
 * 
 * @author qq
 *
 */
public class ServiceResourceNotFound extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceResourceNotFound() {

	}

	public ServiceResourceNotFound(String message) {
		super(message);
	}
}
