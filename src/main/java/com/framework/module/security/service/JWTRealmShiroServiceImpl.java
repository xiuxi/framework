package com.framework.module.security.service;

import org.apache.shiro.authc.AuthenticationToken;

import com.framework.module.common.security.ShiroService;
import com.framework.module.common.security.ShiroUser;

/**
 * 给JWTRealm用
 * 
 * @author qq
 *
 */
public class JWTRealmShiroServiceImpl implements ShiroService {
	@Override
	public ShiroUser buildShiroUser(AuthenticationToken token) {
		return null;
	}
}
