package com.framework.module.common.designPattern.singleton.threadSafetySingleton;

/**
 * 线程安全单例
 * 
 * @author qq
 *
 */
public class ThreadSafetySingleton {
	private static class ThreadSafetySingletonInstance {
		private static ThreadSafetySingleton instance = new ThreadSafetySingleton();
	}

	private ThreadSafetySingleton() {
	}

	public static ThreadSafetySingleton getInstance() {
		return ThreadSafetySingletonInstance.instance;
	}
}
