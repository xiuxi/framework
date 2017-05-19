package com.framework.module.common.designPattern.strategy;

/**
 * 策略接口(打折算法接口)
 * 
 * @author qq
 *
 */
public interface DiscountStrategy {
	/**
	 * 定义一个用于计算打折的方法
	 * 
	 * @param originPrice
	 * @return
	 */
	double getDiscount(double originPrice);
}
