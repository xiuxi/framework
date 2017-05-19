package com.framework.module.common.designPattern.factory.factoryMethod;

import com.framework.module.common.designPattern.factory.factoryMethod.product.Operation;
import com.framework.module.common.designPattern.factory.factoryMethod.product.impl.add.OperationAddFactory;

/**
 * 工厂方法模式 测试
 * 
 * @author qq
 *
 */
public class Test {
	public static void main(String[] args) {
		// 直接new一个实现类不就可以了???
		OperationFactory operationFactory = new OperationAddFactory();
		Operation operation = operationFactory.createOperate();

		double result = operation.excute(2, 3);
		System.out.println(result);
	}
}
