package com.framework.module.common.designPattern.factory.simpleFactory;

import com.framework.module.common.designPattern.factory.simpleFactory.product.Operation;
import com.framework.module.common.designPattern.factory.simpleFactory.product.impl.OperationAdd;
import com.framework.module.common.designPattern.factory.simpleFactory.product.impl.OperationSub;

/**
 * 工厂类
 * 
 * 用于生产Operation类
 * 
 * @author qq
 *
 */
public class OperationFactory {
	/**
	 * 创建Operation类
	 * 
	 * @param operate
	 * @return
	 */
	public static Operation createOperate(String operate) {
		Operation result = null;
		switch (operate) {
		case "+":
			result = new OperationAdd();
			break;
		case "-":
			result = new OperationSub();
			break;
		}

		return result;
	}
}
