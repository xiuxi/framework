package com.framework.module.common.designPattern.observer.jdk.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察name属性 观察者
 * 
 * @author qq
 *
 */
public class NameObserver implements Observer {
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			System.out.println("名称观察者：" + o + "物品名称已经改变为：" + arg);
		}
	}
}
