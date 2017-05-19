package com.framework.module.common.utils;

import java.io.IOException;

import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * Jackson 工具类
 * 
 * @author qq
 * 
 */
public class JacksonUtils {
	private static ObjectMapper mapper = new ObjectMapper();

	/*****************************************************************************************************
	 * 对象=>其他
	 *****************************************************************************************************/
	/**
	 * 对象=>JSON字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String objToJson(Object obj) {
		String json = null;

		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 对象=>字节数组
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] objToBytes(Object obj) {
		byte[] result = null;

		try {
			result = mapper.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*****************************************************************************************************
	 * 其他=>对象
	 *****************************************************************************************************/
	/**
	 * JSON字符串=>对象
	 * 
	 * @param json
	 * @param clazz
	 *            转换对象的Class
	 * @return
	 */
	public static <T> T jsonToObj(String json, Class<T> clazz) {
		T obj = null;

		try {
			obj = mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 字节数组=>对象
	 * 
	 * @param bytes
	 * @param clazz
	 *            转换对象的Class
	 * @return
	 */
	public static <T> T bytesToObj(byte[] bytes, Class<T> clazz) {
		T result = null;

		try {
			result = mapper.readValue(bytes, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*****************************************************************************************************
	 * 其他
	 *****************************************************************************************************/
	/**
	 * Json字符串转换ObjectNode对象
	 * 
	 * @param json
	 *            Json字符串
	 * @return ObjectNode对象
	 */
	public static ObjectNode parseObjectNode(String json) {
		Assert.hasText(json, "参数不能为空");

		try {
			JsonFactory jsonFactory = mapper.getFactory();
			JsonParser jsonParser = jsonFactory.createParser(json);
			ObjectNode objectNode = mapper.readTree(jsonParser);
			return objectNode;
		} catch (JsonParseException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 从Json字符串中获取子对象
	 * 
	 * @param json
	 *            Json字符串
	 * @param subObjName
	 *            子对象名称
	 * @param subObjClazz
	 *            子对象Class
	 * @return 子对象
	 */
	public static <T> T getSubObj(String json, String subObjName, Class<T> subObjClazz) {
		T result = null;

		ObjectNode objectNode = parseObjectNode(json);
		JsonNode queryNode = objectNode.get(subObjName);
		if (queryNode != null) {
			result = jsonToObj(queryNode.toString(), subObjClazz);
		}

		return result;
	}
}
