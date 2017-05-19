package com.framework.module.common.designPattern.factory.factoryMethod.product.impl.add;

import com.framework.module.common.designPattern.factory.factoryMethod.product.Operation;

/**
 * 加法操作
 * 
 * @author qq
 *
 */
public class OperationAdd implements Operation {
	@Override
	public double excute(double numberA, double numberB) {
		return numberA + numberB;
	}

}
