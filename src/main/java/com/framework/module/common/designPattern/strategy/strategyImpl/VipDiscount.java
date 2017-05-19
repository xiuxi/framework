package com.framework.module.common.designPattern.strategy.strategyImpl;

import com.framework.module.common.designPattern.strategy.DiscountStrategy;

/**
 * 策略实现类
 * 
 * VIP打折的算法
 * 
 * @author qq
 *
 */
public class VipDiscount implements DiscountStrategy {

	@Override
	public double getDiscount(double originPrice) {
		System.out.println("使用VIP折扣...");
		return originPrice * 0.5;
	}

}
