package com.framework.module.common.designPattern.strategy;

import com.framework.module.common.designPattern.strategy.strategyImpl.VipDiscount;

/**
 * 策略模式 测试
 * 
 * @author qq
 *
 */
public class Test {
	public static void main(String[] args) throws Exception {
		DiscountContext discountContext = new DiscountContext(null);

		// 使用默认的打折策略
		double price1 = 79;
		System.out.println("79元的书默认打折后的价格是：" + discountContext.getDiscountPrice(price1));

		// 客户端选择合适的VIP打折策略
		discountContext.changeDiscount(new VipDiscount());
		double price2 = 89;
		System.out.println("89元的书对VIP用户的价格是：" + discountContext.getDiscountPrice(price2));
	}
}
