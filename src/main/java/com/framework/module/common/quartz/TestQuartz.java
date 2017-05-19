package com.framework.module.common.quartz;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Quartz测试
 * 
 * @author qq
 *
 */
//@Component
public class TestQuartz {
	private static int counter = 0;

	/**
	 * 调度执行方法
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	protected void execute() {
		System.out.println(counter++ + ": " + new Date());
	}

}
