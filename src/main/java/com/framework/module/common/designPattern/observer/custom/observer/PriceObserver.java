package com.framework.module.common.designPattern.observer.custom.observer;

import com.framework.module.common.designPattern.observer.custom.subject.Observable;

/**
 * 观察price属性 观察者
 * 
 * @author qq
 *
 */
public class PriceObserver implements Observer {
	@Override
	public void update(Observable obs, Object arg) {
		if (arg instanceof Double) {
			System.out.println("价格观察者：" + obs + "物品价格已经改变为：" + arg);
		}
	}

}
