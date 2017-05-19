package com.framework.module.common.plugins.emchat.exception;

/**
 * 用户未注册异常
 * 
 * @author qq
 *
 */
public class UserUnRegistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserUnRegistException() {

	}

	public UserUnRegistException(String message) {
		super(message);
	}
}
