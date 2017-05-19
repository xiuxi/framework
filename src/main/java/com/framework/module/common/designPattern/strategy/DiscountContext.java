package com.framework.module.common.designPattern.strategy;

import com.framework.module.common.designPattern.strategy.strategyImpl.OldDiscount;

/**
 * 用于为客户端代码选择合适折扣策略，当然也允许用户自由选择折扣策略
 * 
 * @author qq
 *
 */
public class DiscountContext {
	private DiscountStrategy strategy;

	public DiscountContext(DiscountStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * 根据实际所使用的DiscountStrategy对象得到折扣
	 * 
	 * @param price
	 * @return
	 */
	public double getDiscountPrice(double price) {
		if (strategy == null) {
			strategy = new OldDiscount();
		}
		return this.strategy.getDiscount(price);
	}

	/**
	 * 提供切换算法的方法
	 * 
	 * @param strategy
	 */
	public void changeDiscount(DiscountStrategy strategy) {
		this.strategy = strategy;
	}

}
