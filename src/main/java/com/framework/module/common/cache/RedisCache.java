package com.framework.module.common.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.framework.module.common.utils.JacksonUtils;

/**
 * Redis缓存
 * 
 * @author qq
 *
 */
@Component
public class RedisCache {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/*****************************************************************************************************
	 * 键(key)相关命令
	 *****************************************************************************************************/
	/*
	 * 删
	 */
	/**
	 * 删除指定key
	 * 
	 * @param keys
	 * @return
	 */
	public void delKey(final String... keys) {
		if (ArrayUtils.isEmpty(keys)) {
			return;
		}

		for (String item : keys) {
			redisTemplate.delete(item);
		}
	}

	/*
	 * 改
	 */
	/**
	 * 修改key名称(如果Redis中存在newKey将覆盖)
	 * 
	 * @param key
	 * @param newKey
	 */
	public void renameKey(final String key, final String newKey) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(newKey)) {
			return;
		}

		redisTemplate.rename(key, newKey);

	}

	/**
	 * 修改key名称(只有当newKey不存在时在修改)
	 * 
	 * @param key
	 * @param newKey
	 */
	public void renameNXKey(final String key, final String newKey) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(newKey)) {
			return;
		}

		redisTemplate.renameIfAbsent(key, newKey);
	}

	/*
	 * 查
	 */
	/**
	 * 是否存在key
	 * 
	 * @param key
	 */
	public Boolean existsKey(final String key) {
		if (StringUtils.isBlank(key)) {
			return false;
		}

		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
	}

	/**
	 * 查找符合给定模式(pattern)的key(默认查全部)
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		if (StringUtils.isBlank(pattern)) {
			pattern = "*";
		}

		return redisTemplate.keys(pattern);
	}

	/**
	 * 返回 key 所储存的值的类型
	 * 
	 * @param key
	 * @return
	 */
	public DataType keyType(final String key) {
		return redisTemplate.type(key);
	}

	/*
	 * 过期时间相关
	 */
	/**
	 * 设置key的过期时间(在设置过期时间后该key将被删除)
	 * 
	 * @param key
	 * @param timeout
	 *            过期时间
	 * @param unit
	 *            过期时间单位
	 * @return
	 */
	public Boolean setKeyExpire(final String key, long timeout, TimeUnit unit) {
		if (StringUtils.isBlank(key)) {
			return false;
		}

		return redisTemplate.expire(key, timeout, unit);
	}

	/**
	 * 设置key的过期时间
	 * 
	 * @param key
	 * @param date
	 *            过期时间
	 * @return
	 */
	public Boolean setKeyExpireAt(final String key, final Date date) {
		if (StringUtils.isBlank(key)) {
			return false;
		}

		return redisTemplate.expireAt(key, date);
	}

	/**
	 * 移除key的过期时间
	 * 
	 * @param key
	 * @return
	 */
	public Boolean persistKey(final String key) {
		if (StringUtils.isBlank(key)) {
			return false;
		}

		return redisTemplate.persist(key);
	}

	/**
	 * 返回key的剩余的过期时间(TTL, time to live)(默认：秒)
	 * 
	 * @param key
	 * @param timeUnit
	 * @return 小于0表示永久有效
	 */
	public Long getExpire(final String key, TimeUnit timeUnit) {
		if (StringUtils.isBlank(key)) {
			return -1L;
		}

		timeUnit = (timeUnit == null) ? TimeUnit.SECONDS : timeUnit;
		return redisTemplate.getExpire(key, timeUnit);
	}

	/*****************************************************************************************************
	 * 字符串(String)相关命令
	 *****************************************************************************************************/
	/*
	 * 增
	 */
	/**
	 * 设置值(不存在则新建)
	 * 
	 * @param key
	 * @param obj
	 * @param seconds
	 *            过期时间(单位: 秒)小于等于0则无过期时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> void set(final String key, final T obj, final long seconds) {
		if (StringUtils.isBlank(key) || obj == null) {
			return;
		}

		redisTemplate.execute(new RedisCallback() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				if (seconds > 0) {
					connection.setEx(key.getBytes(), seconds, JacksonUtils.objToBytes(obj));
				} else {
					connection.set(key.getBytes(), JacksonUtils.objToBytes(obj));
				}

				return null;
			}
		});
	}

	/**
	 * 批量设置值(不存在则新建)
	 * 
	 * @param tuple
	 *            键值对
	 */
	@SuppressWarnings("unchecked")
	public void mSet(final Map<String, String> tuple) {
		if (tuple == null || tuple.isEmpty()) {
			return;
		}

		final Map<byte[], byte[]> tupleByte = new HashMap<byte[], byte[]>();

		Set<Entry<String, String>> entrys = tuple.entrySet();
		for (Entry<String, String> item : entrys) {
			tupleByte.put(item.getKey().getBytes(), item.getValue().getBytes());
		}

		redisTemplate.execute(new RedisCallback() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				connection.mSet(tupleByte);
				return null;
			}
		});
	}

	/**
	 * 设置值(当key不存在时才设置)
	 * 
	 * 保存对象
	 * 
	 * @param key
	 * @param obj
	 * @param seconds
	 *            过期时间(单位: 秒)小于等于0则无过期时间
	 */
	@SuppressWarnings("unchecked")
	public <T> void setNX(final String key, final T obj, final long seconds) {
		if (StringUtils.isBlank(key) || obj == null) {
			return;
		}

		redisTemplate.execute(new RedisCallback() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				if (seconds > 0) {
					connection.setEx(key.getBytes(), seconds, JacksonUtils.objToBytes(obj));
				} else {
					connection.set(key.getBytes(), JacksonUtils.objToBytes(obj));
				}

				return null;
			}
		});
	}

	/**
	 * 批量设置值(当所有key都不存在时才设置)
	 * 
	 * @param tuple
	 */
	@SuppressWarnings("unchecked")
	public void mSetNX(final Map<String, String> tuple) {
		if (tuple == null || tuple.isEmpty()) {
			return;
		}

		final Map<byte[], byte[]> tupleByte = new HashMap<byte[], byte[]>();
		Set<Entry<String, String>> entrys = tuple.entrySet();
		for (Entry<String, String> item : entrys) {
			tupleByte.put(item.getKey().getBytes(), item.getValue().getBytes());
		}

		redisTemplate.execute(new RedisCallback() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				connection.mSetNX(tupleByte);

				return null;
			}
		});
	}

	/**
	 * 追加值到指定key中(不存在则新建)
	 * 
	 * @param key
	 * @param obj
	 */
	public Long append(final String key, final String value) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			return 0L;
		}

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.append(key.getBytes(), value.getBytes());
			}
		});
	}

	/*
	 * 查
	 */
	/**
	 * 获取指定key的值
	 * 
	 * @param key
	 * @param clazz
	 *            返回值Class
	 * @return
	 */
	public <T> T get(final String key, final Class<T> clazz) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		byte[] resultByte = redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});

		T result = null;
		if (resultByte != null && resultByte.length > 0) {
			result = JacksonUtils.bytesToObj(resultByte, clazz);
		}

		return result;
	}

	/*****************************************************************************************************
	 * 哈希(Hash)相关命令
	 *****************************************************************************************************/
	/*
	 * 增
	 */
	/**
	 * 设置哈希表key: field=value(有修改，没有添加)
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public <T> Boolean hSet(final String key, final String field, final T value) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(field) || value == null) {
			return false;
		}

		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hSet(key.getBytes(), field.getBytes(), JacksonUtils.objToBytes(value));
			}
		});
	}

	/**
	 * 设置哈希表key: field=value(有修改，没有添加)
	 * 
	 * @param key
	 * @param hashs
	 */
	public <T> void hMSet(final String key, final Map<String, T> hashs) {
		if (StringUtils.isBlank(key) || hashs == null || hashs.isEmpty()) {
			return;
		}

		final Map<byte[], byte[]> hashBytes = new HashMap<byte[], byte[]>();

		Set<Entry<String, T>> entrys = hashs.entrySet();
		for (Entry<String, T> item : entrys) {
			hashBytes.put(item.getKey().getBytes(), JacksonUtils.objToBytes(item.getValue()));
		}

		redisTemplate.execute(new RedisCallback<Void>() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				connection.hMSet(key.getBytes(), hashBytes);
				return null;
			}
		});
	}

	/**
	 * 设置哈希表key: field=value(当field不存在时才设置)
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public <T> Boolean hSetNX(final String key, final String field, final T value) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(field) || value == null) {
			return false;
		}

		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hSetNX(key.getBytes(), field.getBytes(), JacksonUtils.objToBytes(value));
			}
		});
	}

	/*
	 * 删
	 */
	/**
	 * 删除哈希表中的fields
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hDel(final String key, final String... fields) {
		if (StringUtils.isBlank(key) || fields == null || fields.length <= 0) {
			return 0L;
		}

		final byte[][] fieldsByte = new byte[fields.length][];

		for (int i = 0; i < fields.length; i++) {
			fieldsByte[i] = fields[i].getBytes();
		}

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hDel(key.getBytes(), fieldsByte);
			}
		});
	}

	/*
	 * 查
	 */
	/**
	 * 哈希表中是否存在field
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Boolean hExists(final String key, final String field) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
			return false;
		}

		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hExists(key.getBytes(), field.getBytes());
			}
		});
	}

	/**
	 * 获取给定字段的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public <T> T hGet(final String key, final String field, final Class<T> clazz) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
			return null;
		}

		byte[] resultByte = redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hGet(key.getBytes(), field.getBytes());
			}
		});

		T result = null;
		if (resultByte != null && resultByte.length > 0) {
			result = JacksonUtils.bytesToObj(resultByte, clazz);
		}

		return result;
	}

	/**
	 * 获取哈希表中的所有字段和字段对应的值
	 * 
	 * @param key
	 * @return
	 */
	public <T> Map<String, T> hGetAll(final String key, final Class<T> clazz) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		Map<String, T> result = new HashMap<String, T>();

		Map<byte[], byte[]> resultByte = redisTemplate.execute(new RedisCallback<Map<byte[], byte[]>>() {
			@Override
			public Map<byte[], byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hGetAll(key.getBytes());
			}
		});

		if (resultByte != null && !resultByte.isEmpty()) {
			Set<Entry<byte[], byte[]>> entrys = resultByte.entrySet();
			for (Entry<byte[], byte[]> item : entrys) {
				result.put(new String(item.getKey()), JacksonUtils.bytesToObj(item.getValue(), clazz));
			}
		}

		return result;
	}

	/**
	 * 获取哈希表中的所有field
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> hKeys(final String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		Set<String> result = new HashSet<String>();

		Set<byte[]> resultByte = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hKeys(key.getBytes());
			}
		});

		if (resultByte != null && !resultByte.isEmpty()) {
			for (byte[] item : resultByte) {
				result.add(new String(item));
			}
		}

		return result;
	}

	/**
	 * 获取哈希表中所有值
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> List<T> hVals(final String key, final Class<T> clazz) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		List<T> result = new ArrayList<T>();

		List<byte[]> resultByte = redisTemplate.execute(new RedisCallback<List<byte[]>>() {
			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hVals(key.getBytes());
			}
		});

		if (resultByte != null && !resultByte.isEmpty()) {
			for (byte[] item : resultByte) {
				result.add(JacksonUtils.bytesToObj(item, clazz));
			}
		}

		return result;
	}

	/**
	 * 获取哈希表中字段的数量
	 * 
	 * @param key
	 * @return
	 */
	public Long hLen(final String key) {
		if (StringUtils.isBlank(key)) {
			return 0L;
		}

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hLen(key.getBytes());
			}
		});
	}

	/*****************************************************************************************************
	 * 列表(List)相关命令
	 *****************************************************************************************************/
	/*
	 * 增
	 */
	/**
	 * 插入值到列表头部(若列表不存在则创建)
	 * 
	 * @param key
	 * @param objs
	 * @return
	 */
	public <T> Long lPush(final String key, final T... objs) {
		if (StringUtils.isBlank(key) || objs == null || objs.length <= 0) {
			return 0L;
		}

		int objSize = objs.length;
		final byte[][] valueBytes = new byte[objSize][];
		for (int i = 0; i < objSize; i++) {
			valueBytes[i] = JacksonUtils.objToBytes(objs[i]);
		}

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lPush(key.getBytes(), valueBytes);
			}
		});
	}

	/**
	 * 插入值到列表头部(若列表不存在则创建)(值存在则不插入)
	 * 
	 * @param key
	 * @param clazz
	 *            List中装的对象的Class
	 * @param objs
	 * @return
	 */
	public <T> Long lPushUnique(final String key, Class<T> clazz, final T... objs) {
		return this.lPush(key, this.lGetPushValue(key, clazz, objs).toArray());
	}

	/**
	 * 插入值到列表头部(列表存在才添加)
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public <T> Long lPushX(final String key, final T obj) {
		if (StringUtils.isBlank(key) || obj == null) {
			return 0L;
		}

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lPushX(key.getBytes(), JacksonUtils.objToBytes(obj));
			}
		});
	}

	/**
	 * 插入值到列表头部(列表存在才添加)(值存在则不插入)
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public <T> Long lPushXUnique(final String key, final T obj) {
		long result = 0L;

		if (!this.lExist(key, obj)) {
			result = this.lPushX(key, obj);
		}
		return result;
	}

	/**
	 * 插入值到列表尾部(若列表不存在则创建)
	 * 
	 * @param key
	 * @param objs
	 * @return
	 */
	public <T> Long rPush(final String key, final T... objs) {
		if (StringUtils.isBlank(key) || objs == null || objs.length <= 0) {
			return 0L;
		}

		int objSize = objs.length;
		final byte[][] valueBytes = new byte[objSize][];
		for (int i = 0; i < objSize; i++) {
			valueBytes[i] = JacksonUtils.objToBytes(objs[i]);
		}

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.rPush(key.getBytes(), valueBytes);
			}
		});
	}

	/**
	 * 插入值到列表尾部(若列表不存在则创建)(值存在则不插入)
	 * 
	 * @param key
	 * @param clazz
	 * @param objs
	 * @return
	 */
	public <T> Long rPushUnique(final String key, Class<T> clazz, final T... objs) {
		return this.rPush(key, this.lGetPushValue(key, clazz, objs).toArray());
	}

	/**
	 * 插入值到列表尾部(列表存在才添加)
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public <T> Long rPushX(final String key, final T obj) {
		if (StringUtils.isBlank(key) || obj == null) {
			return 0L;
		}

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.rPushX(key.getBytes(), JacksonUtils.objToBytes(obj));
			}
		});
	}

	/**
	 * 插入值到列表尾部(列表存在才添加)(值存在则不插入)
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public <T> Long rPushXUnique(final String key, final T obj) {
		long result = 0L;

		if (!this.lExist(key, obj)) {
			result = this.rPushX(key, obj);
		}
		return result;
	}

	/*
	 * 删
	 */
	/**
	 * 移除第一个元素，并返回该元素
	 * 
	 * @param key
	 * @return
	 */
	public void lPop(final String key) {
		if (StringUtils.isBlank(key)) {
			return;
		}

		redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lPop(key.getBytes());
			}
		});

	}

	/**
	 * 移除最后一个元素，并返回该元素
	 * 
	 * @param key
	 * @return
	 */
	public void rPop(final String key) {
		if (StringUtils.isBlank(key)) {
			return;
		}

		redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.rPop(key.getBytes());
			}
		});
	}

	/**
	 * 移除等于value的元素
	 * 
	 * count=0: 从表头开始查找，移除所有等于value的元素
	 * 
	 * count>0: 从表头开始查找，移除count个等于value的元素
	 * 
	 * count<0: 从表尾开始查找，移除|count|个等于value的元素
	 * 
	 * @param key
	 * @param values
	 * @param count
	 */
	public <T> void lRem(final String key, final int count, final T... values) {
		if (StringUtils.isBlank(key) || values == null || values.length <= 0) {
			return;
		}

		redisTemplate.execute(new RedisCallback<Void>() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				for (T item : values) {
					connection.lRem(key.getBytes(), count, JacksonUtils.objToBytes(item));
				}
				return null;
			}
		});
	}

	/**
	 * 列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除(前闭后闭)
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public void lTrim(final String key, final long start, final long end) {
		if (StringUtils.isBlank(key)) {
			return;
		}

		redisTemplate.execute(new RedisCallback<Void>() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				connection.lTrim(key.getBytes(), start, end);
				return null;
			}
		});
	}

	/*
	 * 改
	 */
	/**
	 * 修改指定索引的值(只能修改存在的索引)
	 * 
	 * @param key
	 * @param index
	 * @param obj
	 */
	public <T> void lSet(final String key, final long index, final T obj) {
		if (StringUtils.isBlank(key) || obj == null) {
			return;
		}

		redisTemplate.execute(new RedisCallback<Void>() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				connection.lSet(key.getBytes(), index, JacksonUtils.objToBytes(obj));
				return null;
			}
		});
	}

	/*
	 * 查
	 */
	/**
	 * 获取列表指定范围内的元素(从0开始)(前闭后闭)
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @param clazz
	 *            返回对象的Class
	 */
	public <T> List<T> lRange(final String key, final long start, final long end, Class<T> clazz) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		List<T> result = new ArrayList<T>();

		List<byte[]> resultBytes = redisTemplate.execute(new RedisCallback<List<byte[]>>() {
			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lRange(key.getBytes(), start, end);
			}
		});

		if (resultBytes != null && !resultBytes.isEmpty()) {
			for (byte[] item : resultBytes) {
				result.add(JacksonUtils.bytesToObj(item, clazz));
			}
		}

		return result;
	}

	/**
	 * 获取列表中指定索引的值(从0开始)
	 * 
	 * @param key
	 * @param index
	 * @param clazz
	 *            返回对象的Class
	 */
	public <T> T lIndex(final String key, final long index, Class<T> clazz) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		byte[] resultByte = redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lIndex(key.getBytes(), index);
			}
		});

		T result = null;
		if (resultByte != null && resultByte.length > 0) {
			result = JacksonUtils.bytesToObj(resultByte, clazz);
		}
		return result;
	}

	/**
	 * 获取列表长度
	 * 
	 * @param key
	 * @return
	 */
	public long lLen(final String key) {
		if (StringUtils.isBlank(key)) {
			return 0L;
		}

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lLen(key.getBytes());
			}
		});
	}

	/**
	 * 获取列表所有的元素
	 * 
	 * @param key
	 * @param clazz
	 *            返回对象的Class
	 * @return
	 */
	public <T> List<T> lGetAll(final String key, Class<T> clazz) {
		List<T> result = null;

		long length = this.lLen(key);
		if (length > 0) {
			result = this.lRange(key, 0, length - 1, clazz);
		}

		return result;
	}

	/**
	 * 是否存在指定值
	 * 
	 * @param key
	 * @param value
	 * @param clazz
	 * @return true: 存在;false: 不存在
	 */
	public <T> boolean lExist(final String key, final T value) {
		boolean result = false;
		if (StringUtils.isNotBlank(key) && value != null) {
			List<Object> lists = lGetAll(key, Object.class);
			if (lists != null && !lists.isEmpty()) {
				result = lists.contains(value);
			}
		}

		return result;
	}

	/*
	 * 辅助方法
	 */
	/**
	 * 返回objs中能插入到List中的值
	 * 
	 * @param key
	 * @param clazz
	 * @param objs
	 * @return
	 */
	private <T> Collection<T> lGetPushValue(final String key, Class<T> clazz, final T... objs) {
		Collection<T> result = new ArrayList<T>();

		if (StringUtils.isNotBlank(key) && objs != null && objs.length > 0) {
			List<T> oldList = this.lGetAll(key, clazz);
			if (oldList != null && !oldList.isEmpty()) {
				List<T> newList = Arrays.asList(objs);
				Collection<T> collections = CollectionUtils.subtract(newList, oldList);
				if (collections != null && !collections.isEmpty()) {
					result.addAll(collections);
				}
			} else {
				result = Arrays.asList(objs);
			}
		}

		return result;
	}

	/*****************************************************************************************************
	 * 集合(Set)相关命令
	 *****************************************************************************************************/
	/*
	 * 增
	 */
	/**
	 * 添加成员
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public <T> long sAdd(final String key, final T... values) {
		if (StringUtils.isBlank(key) || values == null || values.length <= 0) {
			return 0L;
		}

		final byte[][] valueBytes = new byte[values.length][];
		for (int i = 0; i < values.length; i++) {
			valueBytes[i] = JacksonUtils.objToBytes(values[i]);
		}

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.sAdd(key.getBytes(), valueBytes);
			}
		});
	}

	/*
	 * 删
	 */
	/**
	 * 移除成员
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public long sRem(final String key, final String... values) {
		if (StringUtils.isBlank(key) || values == null || values.length <= 0) {
			return 0L;
		}

		final byte[][] valueBytes = new byte[values.length][];

		for (int i = 0; i < values.length; i++) {
			valueBytes[i] = values[i].getBytes();
		}

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.sRem(key.getBytes(), valueBytes);
			}
		});
	}

	/*
	 * 查
	 */
	/**
	 * 返回集合中的所有成员
	 * 
	 * @param key
	 * @return
	 */
	public <T> Set<T> sMembers(final String key, Class<T> clazz) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		Set<T> result = new HashSet<T>();

		Set<byte[]> resultBytes = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.sMembers(key.getBytes());
			}
		});

		if (resultBytes != null && !resultBytes.isEmpty()) {
			for (byte[] item : resultBytes) {
				result.add(JacksonUtils.bytesToObj(item, clazz));
			}
		}

		return result;
	}

	/**
	 * 判断member 元素是否是集合key的成员(存在返回1，不存在返回0)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> Boolean sIsMember(final String key, final T value) {
		if (StringUtils.isBlank(key) || value == null) {
			return false;
		}

		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.sIsMember(key.getBytes(), JacksonUtils.objToBytes(value));
			}
		});
	}

	/*****************************************************************************************************
	 * 有序集合(Sorted Set)相关命令
	 *****************************************************************************************************/
	/*
	 * 增
	 */
	// zadd key score1 member1 [score2 member2]
	// 添加成员(或更新成员分数)

	/*
	 * 删
	 */
	// zrem key member [member ...] 
	// 移除成员

	// zremrangebylex key min max 
	// zremrangebyrank key start stop 
	// zremrangebyscore key min max 
	// 移除(字典、排名、分数)区间的成员

	/*
	 * 查
	 */
	// zcard key 
	// 获取集合的成员数
	//
	// zcount key minscore maxscore 
	// 返回分数区间的成员个数
	//
	// zscore key member 
	// 返回成员的分数值

	//
	//
	// zinterstore destination numkeys key [key ...] 
	// 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
	//
	// zlexcount key min max 
	// 在集合中计算指定字典区间内成员数量
	//
	// zrange key start stop [WITHSCORES] 
	// 通过索引区间返回有序集合成指定区间内的成员
	//
	// zrangebylex key min max [LIMIT offset count]
	// 通过字典区间返回有序集合的成员
	//
	// zrangebyscore key min max [WITHSCORES] [LIMIT] 
	// 通过分数返回有序集合指定区间内的成员
	//
	// zrank key member 
	// 返回有序集合中指定成员的索引
	//
	// zrevrange key start stop [WITHSCORES] 
	// 返回有序集中指定区间内的成员，通过索引，分数从高到底
	//
	// zrevrangebyscore key max min [WITHSCORES] 
	// 返回有序集中指定分数区间内的成员，分数从高到低排序
	//
	// zrevrank key member 
	// 返回有序集合中指定成员的排名，有序集成员按分数值递减(从大到小)排序
	//
	// zunionstore destination numkeys key [key ...] 
	// 计算集合的并集，并存储在新的 key 中
	//
	// zscan key cursor [MATCH pattern] [COUNT count] 
	// 迭代有序集合中的元素(包括元素成员和元素分值)

	/*****************************************************************************************************
	 * 
	 *****************************************************************************************************/

}
