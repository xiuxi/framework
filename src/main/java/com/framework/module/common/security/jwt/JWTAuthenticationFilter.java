package com.framework.module.common.security.jwt;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import com.framework.module.common.security.ShiroUtils;
import com.framework.module.common.utils.JacksonUtils;

/**
 * JWT(JSON Web Token)认证过滤器
 * 
 * @author qq
 * 
 */
public class JWTAuthenticationFilter extends AuthenticatingFilter {
	/**
	 * 创建认证Token
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		String authenticationToken = this.getAuthorizationToken(request);

		if (authenticationToken != null) {
			return new JWTAuthenticationToken(authenticationToken, authenticationToken);
		} else {
			return new JWTAuthenticationToken(null, null);
		}
	}

	/**
	 * 获取Authorization请求头中的token信息
	 * 
	 * @param request
	 * @return
	 */
	private String getAuthorizationToken(ServletRequest request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authorizationToken = null;

		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			authorizationToken = authorizationHeader.substring("Bearer ".length());
		}

		return authorizationToken;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 调用Realm进行认证
		executeLogin(request, response);
		return true;
	}

	/**
	 * 登陆成功
	 * 
	 * @throws Exception
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		// 刷新JWT
		String jwt = JWTUtils.encryptJWT(JacksonUtils.objToJson(ShiroUtils.currentUser()));
		httpServletResponse.setHeader("WWW-Authenticate", jwt);
		return super.onLoginSuccess(token, subject, request, response);
	}

}
