package com.framework.module.common.designPattern.observer.custom.observer;

import com.framework.module.common.designPattern.observer.custom.subject.Observable;

/**
 * 观察name属性 观察者
 * 
 * @author qq
 *
 */
public class NameObserver implements Observer {
	@Override
	public void update(Observable obs, Object arg) {
		if (arg instanceof String) {
			System.out.println("名称观察者：" + obs + "物品名称已经改变为：" + arg);
		}
	}
}
