package com.framework.module.common.plugins.emchat.exception;

/**
 * 用户已注册异常
 * 
 * @author qq
 *
 */
public class UserAlreadyRegistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyRegistException() {

	}

	public UserAlreadyRegistException(String message) {
		super(message);
	}
}
