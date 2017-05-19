package com.framework.module.common.designPattern.strategy.strategyImpl;

import com.framework.module.common.designPattern.strategy.DiscountStrategy;

/**
 * 策略实现类
 * 
 * 旧书打折算法
 * 
 * @author qq
 *
 */
public class OldDiscount implements DiscountStrategy {
	@Override
	public double getDiscount(double originPrice) {
		System.out.println("使用旧书折扣...");
		return originPrice * 0.7;
	}

}
