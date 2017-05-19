package com.framework.module.common.designPattern.observer.jdk;

import com.framework.module.common.designPattern.observer.jdk.observer.NameObserver;
import com.framework.module.common.designPattern.observer.jdk.observer.PriceObserver;
import com.framework.module.common.designPattern.observer.jdk.subject.Product;

/**
 * 观察者模式 测试(使用JDK)
 * 
 * @author qq
 *
 */
public class Test {
	public static void main(String[] args) {
		// 创建一个主题
		Product product = new Product("电视机", 176);

		// 创建两个观察者对象
		NameObserver nameObserver = new NameObserver();
		PriceObserver priceObserver = new PriceObserver();

		// 向主题上注册两个观察者对象
		product.addObserver(nameObserver);
		product.addObserver(priceObserver);

		// 程序调用setter方法来改变Product的name和price属性
		product.setName("书桌");
		product.setPrice(345F);
	}

}
