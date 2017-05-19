package com.framework.module.security.service;

import org.apache.shiro.authc.AuthenticationToken;

import com.framework.module.common.security.ShiroService;
import com.framework.module.common.security.ShiroUser;
import com.framework.module.common.security.swt.SWTAuthenticationToken;

/**
 * 给SWTRealm用
 * 
 * @author qq
 *
 */
public class SWTRealmShiroServiceImpl implements ShiroService {
	@Override
	public ShiroUser buildShiroUser(AuthenticationToken token) {
		SWTAuthenticationToken authenticationToken = (SWTAuthenticationToken) token;

		return new ShiroUser(authenticationToken.getUserName(), null);
	}
}
