package com.framework.module.common.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Assert;

/**
 * 加密解密 工具类
 * 
 * @author qq
 * 
 */
public class PasswordUtils {
	/*****************************************************************************************************
	 * base64加密解密
	 *****************************************************************************************************/
	/**
	 * base64加密
	 * 
	 * @param encryptContent
	 *            加密内容
	 * 
	 * @return 加密后的内容
	 */
	public static String base64Encrypt(String encryptContent) {
		Assert.hasText(encryptContent, "The encryptContent is not null");

		Base64 base64 = new Base64();
		try {
			return base64.encodeToString(encryptContent.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("加密失败", e);
		}
	}

	/**
	 * base64解密
	 * 
	 * @param decryptContent
	 *            解密内容
	 * 
	 * @return 解密后的内容
	 */
	public static String base64Decrypt(String decryptContent) {
		Assert.hasText(decryptContent, "The decryptContent is not null");

		byte[] bytes = new Base64().decodeBase64(decryptContent);
		return new String(bytes);
	}

	/*****************************************************************************************************
	 * Hex加密解密
	 *****************************************************************************************************/
	/**
	 * Hex加密
	 * 
	 * @param encryptContent
	 *            加密内容
	 * 
	 * @return 加密后的内容
	 */
	public static String hexEncrypt(String encryptContent) {
		Assert.hasText(encryptContent, "The encryptContent is not null");

		try {
			return Hex.encodeHexString(encryptContent.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("加密失败", e);
		}
	}

	/**
	 * Hex解密
	 * 
	 * @param decryptContent
	 *            解密内容
	 * 
	 * @return 解密后的内容
	 */
	public static String hexDecrypt(String decryptContent) {
		Assert.hasText(decryptContent, "The decryptContent is not null");

		Hex hex = new Hex();
		try {
			byte[] bytes = hex.decode(decryptContent.getBytes());
			return new String(bytes, "UTF-8");
		} catch (DecoderException e) {
			throw new IllegalArgumentException("解密失败", e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("解密失败", e);
		}
	}

	/*****************************************************************************************************
	 * MD5加密
	 *****************************************************************************************************/
	/**
	 * MD5加密
	 * 
	 * @param encryptContent
	 *            加密内容
	 * 
	 * @return 加密后的内容
	 */
	public static String md5Encrypt(String encryptContent) {
		Assert.hasText(encryptContent, "The encryptContent is not null");

		try {
			return DigestUtils.md5Hex(encryptContent.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("加密失败", e);
		}
	}

	/*****************************************************************************************************
	 * SHA加密
	 *****************************************************************************************************/
	/**
	 * SHA加密
	 * 
	 * @param encryptContent
	 *            加密内容
	 * 
	 * @return 加密后的内容
	 */
	public static String shaEncrypt(String encryptContent) {
		Assert.hasText(encryptContent, "The encryptContent is not null");

		try {
			return DigestUtils.shaHex(encryptContent.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("加密失败", e);
		}
	}

	/*****************************************************************************************************
	 * 
	 *****************************************************************************************************/
}
