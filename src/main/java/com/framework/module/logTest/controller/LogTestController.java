package com.framework.module.logTest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 日志测试 Controller
 * 
 * @author qq
 * 
 */
@Controller
@RequestMapping("/LogTestController")
public class LogTestController {
	private final static Logger logger = LoggerFactory.getLogger(LogTestController.class);

	/**
	 * 日志打印测试
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logPrintTest")
	public void logPrintTest() {
		logger.error("error log", new NullPointerException("空指针异常"));
		logger.warn("warn log");
		logger.info("info log");
		logger.debug("debug log,placeholder1: {},placeholder2: {}", "one", 2);
	}

	/**
	 * 日志桥接测试
	 * 
	 * 1、导入对应的桥接器依赖Jar包
	 * 
	 * 2、执行SLF4JBridgeHandler.install();（置于启动时）
	 * 
	 * 若非SLF4j实现框架的API打印的日志能在SLF4j实现框架配置的Appender中输出，即桥接成功
	 */
	@ResponseBody
	@RequestMapping("/logBridgTest")
	public void logBridgTest() {
		java.util.logging.Logger.getLogger(LogTestController.class.getName()).info("1.java.util.logging bridge");
		org.apache.log4j.Logger.getLogger(LogTestController.class).info("2.Log4J bridge");
		org.apache.commons.logging.LogFactory.getLog(LogTestController.class).info("3.commons-logging bridge");
		org.slf4j.LoggerFactory.getLogger(LogTestController.class).info("4.Logback via SLF4J bridge");
	}
}