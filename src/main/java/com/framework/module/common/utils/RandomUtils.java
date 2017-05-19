package com.framework.module.common.utils;

import java.util.Random;

/**
 * 随机数 工具类
 * 
 * @author qq
 * 
 */
public class RandomUtils {
	/**
	 * 验证码字符池
	 */
	public static final char[] codePool = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'n', 'm', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'N', 'M', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 验证码字符池长度
	 */
	public static final int codePoolSize = codePool.length;

	/**
	 * 随机生成0-bound间的数(前闭后开)
	 * 
	 * @param bound
	 * @return
	 */
	public static int randomInt(int bound) {
		Random random = new Random();

		return random.nextInt(bound);
	}

	/**
	 * 生成验证码
	 * 
	 * @param codeLength
	 *            验证码长度
	 * @return
	 */
	public static String randomCode(int codeLength) {
		codeLength = (codeLength <= 0) ? 1 : codeLength;
		StringBuffer result = new StringBuffer(codeLength);

		for (int i = 0; i < codeLength; i++) {
			int codePoolsIndex = randomInt(codePoolSize - 1);
			result.append(codePool[codePoolsIndex]);
		}

		return result.toString();
	}

}
