package com.framework.module.common.designPattern.factory.simpleFactory.product.impl;

import com.framework.module.common.designPattern.factory.simpleFactory.product.Operation;

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
