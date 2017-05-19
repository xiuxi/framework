package com.framework.module.common.designPattern.singleton.hungrySingleton;

/**
 * 饿汉式单例
 * 
 * @author qq
 *
 */
public class HungrySingleton {
	private static HungrySingleton instance = new HungrySingleton();

	private HungrySingleton() {
	}

	public static HungrySingleton getInstance() {
		return instance;
	}

}
