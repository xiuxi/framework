package com.framework.module.common.designPattern.observer.jdk.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察price属性 观察者
 * 
 * @author qq
 *
 */
public class PriceObserver implements Observer {
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Double) {
			System.out.println("价格观察者：" + o + "物品价格已经改变为：" + arg);
		}
	}
}
