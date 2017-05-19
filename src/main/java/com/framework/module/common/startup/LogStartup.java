package com.framework.module.common.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日志启动器
 * 
 * 用于日志桥接
 * 
 * @author qq
 * 
 */
@Component
@Transactional
public class LogStartup implements ApplicationListener<ContextRefreshedEvent> {
	private final static Logger logger = LoggerFactory.getLogger(LogStartup.class);

	/**
	 * 是否已经初始化
	 */
	private boolean isInitialized;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event instanceof ContextRefreshedEvent && !isInitialized) {
			isInitialized = true;
			logger.info("---------- Log Initialized begin... ----------");
			if (!SLF4JBridgeHandler.isInstalled()) {
				SLF4JBridgeHandler.install();
			}
			logger.info("---------- Log Initialized end ----------");
		}
	}

	/*
	 * getter、setter
	 */
	public boolean isIsinitialized() {
		return isInitialized;
	}

	public void setIsinitialized(boolean isinitialized) {
		this.isInitialized = isinitialized;
	}

}
