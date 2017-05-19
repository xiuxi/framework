package com.framework.module.common.designPattern.factory.factoryMethod;

import com.framework.module.common.designPattern.factory.factoryMethod.product.Operation;

/**
 * 工厂接口
 * 
 * 用于生产Operation类
 * 
 * @author qq
 *
 */
public interface OperationFactory {
	/**
	 * 创建Operation类
	 * 
	 * @return
	 */
	public Operation createOperate();
}
