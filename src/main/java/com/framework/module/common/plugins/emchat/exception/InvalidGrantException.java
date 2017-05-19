package com.framework.module.common.plugins.emchat.exception;

/**
 * 无效授权异常(用户不存在或密码不正确)
 * 
 * @author qq
 *
 */
public class InvalidGrantException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidGrantException() {

	}

	public InvalidGrantException(String message) {
		super(message);
	}
}
