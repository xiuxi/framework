package com.framework.module.common.designPattern.factory.factoryMethod.product.impl.add;

import com.framework.module.common.designPattern.factory.factoryMethod.OperationFactory;
import com.framework.module.common.designPattern.factory.factoryMethod.product.Operation;

public class OperationAddFactory implements OperationFactory{

	@Override
	public Operation createOperate() {
		return (Operation) new OperationAdd();
	}

}
