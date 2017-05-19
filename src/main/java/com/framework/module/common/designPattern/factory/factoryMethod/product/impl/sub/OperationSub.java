package com.framework.module.common.designPattern.factory.factoryMethod.product.impl.sub;

import com.framework.module.common.designPattern.factory.factoryMethod.product.Operation;

/**
 * 减法操作
 * 
 * @author qq
 *
 */
public class OperationSub implements Operation {
	@Override
	public double excute(double numberA, double numberB) {
		return numberA - numberB;
	}
}
