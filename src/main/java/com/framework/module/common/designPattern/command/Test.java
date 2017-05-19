package com.framework.module.common.designPattern.command;

/**
 * 命令模式 测试
 * 
 * @author qq
 *
 */
public class Test {
	public static void main(String[] args) {
		ProcessArray processArray = new ProcessArray();
		int[] target = { 3, -4, 6, 4 };

		/*
		 * 第一次处理数组，具体处理行为取决于Command对象
		 */
		processArray.each(target, new Command() {
			@Override
			public void process(int[] target) {
				for (int item : target) {
					System.out.println("迭代输出目标数组的元素：" + item);
				}
			}
		});

		/*
		 * 第二次处理数组，具体处理行为取决于Command对象
		 */
		processArray.each(target, new Command() {
			@Override
			public void process(int[] target) {
				int sum = 0;
				for (int item : target) {
					sum += item;
				}
				System.out.println("数组元素的总和是：" + sum);
			}
		});
	}
}
