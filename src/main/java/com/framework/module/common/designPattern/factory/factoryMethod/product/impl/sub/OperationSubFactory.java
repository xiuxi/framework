package com.framework.module.common.designPattern.factory.factoryMethod.product.impl.sub;

import com.framework.module.common.designPattern.factory.factoryMethod.OperationFactory;
import com.framework.module.common.designPattern.factory.factoryMethod.product.Operation;

public class OperationSubFactory implements OperationFactory {
	@Override
	public Operation createOperate() {
		return new OperationSub();
	}

}
