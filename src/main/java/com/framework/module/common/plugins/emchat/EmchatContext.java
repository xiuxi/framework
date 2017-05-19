package com.framework.module.common.plugins.emchat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 环信配置信息上下文
 * 
 * @author qq
 *
 */
public class EmchatContext {
	private static final Logger log = LoggerFactory.getLogger(EmchatContext.class);

	/**
	 * 是否已初始化
	 */
	private Boolean initialized = Boolean.FALSE;

	/**
	 * 环信token信息
	 */
	private EmChatToken token;

	/**
	 * 环信配置信息
	 */
	private String protocal;
	private String password;
	private String host;
	private String org;
	private String app;
	private String client_Id;
	private String client_Secret;
	private String service_Url;

	/*
	 * 
	 */
	private static EmchatContext context;

	/*
	 * constructor
	 */
	private EmchatContext() {
	}

	/**
	 * newInstance
	 * 
	 * @return
	 */
	public static EmchatContext newInstance() {
		if (context == null) {
			context = new EmchatContext();
		}
		context.init();

		return context;
	}

	/**
	 * 初始化
	 */
	private void init() {
		if (initialized) {
			return;
		}

		log.info("环信初始化开始...");

		protocal = EmChatConfig.Protocal;
		password = EmChatConfig.Password;
		host = EmChatConfig.Host;
		org = EmChatConfig.Org;
		app = EmChatConfig.App;
		client_Id = EmChatConfig.Client_Id;
		client_Secret = EmChatConfig.Client_Secret;
		service_Url = protocal + "://" + host + "/" + org + "/" + app + "/";

		if (StringUtils.isBlank(protocal) || StringUtils.isBlank(password) || StringUtils.isBlank(host)
				|| StringUtils.isBlank(org) || StringUtils.isBlank(app) || StringUtils.isBlank(client_Id)
				|| StringUtils.isBlank(client_Secret)) {
			throw new IllegalArgumentException("环信配置信息不能为空");
		}

		token = new EmChatToken(context);
		initialized = Boolean.TRUE;

		log.info("环信配置信息：\nProtocal:{}\nPassword:{}\nHost:{}\nOrg:{}\nApp:{}\nClient_Id:{}\nClient_Secret:{}", protocal,
				password, host, org, app, client_Id, client_Secret);
		log.info("环信初始化结束.");
	}

	/**
	 * 是否初始化
	 * 
	 * @return
	 */
	public Boolean isInitialized() {
		return initialized;
	}

	/*
	 * getter
	 */
	public String getProtocal() {
		return protocal;
	}

	public String getPassword() {
		return password;
	}

	public String getHost() {
		return host;
	}

	public String getOrg() {
		return org;
	}

	public String getApp() {
		return app;
	}

	public String getClient_Id() {
		return client_Id;
	}

	public String getClient_Secret() {
		return client_Secret;
	}

	public String getService_Url() {
		return service_Url;
	}

	public EmChatToken getToken() {
		return token;
	}

}
