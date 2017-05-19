package com.framework.module.common.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Shiro Service
 * 
 * 需要实现其中的方法
 * 
 * @author qq
 *
 */
public interface ShiroService {
	/**
	 * 根据令牌构建ShiroUser
	 * 
	 * @param token
	 * @return
	 */
	public ShiroUser buildShiroUser(AuthenticationToken token);

}
