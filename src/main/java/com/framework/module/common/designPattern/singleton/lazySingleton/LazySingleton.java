package com.framework.module.common.designPattern.singleton.lazySingleton;

/**
 * 懒汉式单例
 * 
 * @author qq
 *
 */
public class LazySingleton {
	private static LazySingleton instance;

	private LazySingleton() {
	}

	public static synchronized LazySingleton getInstance() {
		if (instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}
}
