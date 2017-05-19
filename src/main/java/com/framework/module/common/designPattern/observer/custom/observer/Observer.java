package com.framework.module.common.designPattern.observer.custom.observer;

import com.framework.module.common.designPattern.observer.custom.subject.Observable;

/**
 * 观察者接口
 * 
 * JDK有提供，无须自己实现
 * 
 * @author qq
 *
 */
public interface Observer {
	/**
	 * @param obs
	 *            主题(被观察对象)
	 * @param arg
	 *            观察的对象
	 */
	void update(Observable obs, Object arg);
}
