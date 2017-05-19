package com.framework.module.common.designPattern.factory.simpleFactory;

import com.framework.module.common.designPattern.factory.simpleFactory.product.Operation;

/**
 * 简单工厂模式 测试(非23种设计模式)
 * 
 * @author qq
 *
 */
public class Test {
	public static void main(String[] args) {
		// 通过工厂生产对象
		Operation operation = OperationFactory.createOperate("-");

		double result = operation.excute(3, 5);
		System.out.println(result);
	}
}
