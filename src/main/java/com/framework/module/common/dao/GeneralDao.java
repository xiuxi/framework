package com.framework.module.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.framework.module.common.page.VPageRequest;

/**
 * 通用Dao Interface
 * 
 * @author qq
 */
public interface GeneralDao {
	/*****************************************************************************************************
	 * 保存
	 *****************************************************************************************************/
	/**
	 * 保存实体
	 * 
	 * @param entity
	 * @return
	 */
	public <T> T save(T entity);

	/**
	 * 批量保存实体(默认缓存50条)
	 * 
	 * @param entitys
	 */
	public <T> void batchSave(List<T> entitys);

	/**
	 * 批量保存实体
	 * 
	 * @param entitys
	 * @param cacheNum
	 *            在session缓存中的条数,当缓存到cacheNum条数时才刷新(flush)到数据库中执行,以提高效率
	 */
	public <T> void batchSave(List<T> entitys, int cacheNum);

	/*****************************************************************************************************
	 * 删除
	 *****************************************************************************************************/
	/**
	 * 删除实体
	 * 
	 * @param entity
	 * @return
	 */
	public <T> T delete(T entity);

	/**
	 * 根据主键删除实体
	 * 
	 * @param entityClass
	 * @param primaryKey
	 *            主键
	 * @return
	 */
	public <T> T deleteByPrimaryKey(Class<T> entityClass, Serializable primaryKey);

	/**
	 * 根据主键删除实体(批量)
	 * 
	 * @param entityClass
	 * @param primaryKeyName
	 *            主键名称
	 * @param primaryKeys
	 *            多个主键
	 * @return
	 */
	public <T> void batchDeleteByPrimaryKey(Class<T> entityClass, String primaryKeyName,
			List<Serializable> primaryKeys);

	/**
	 * 删除所有实体
	 * 
	 * @param entityClass
	 */
	public <T> void deleteAll(Class<T> entityClass);

	/*****************************************************************************************************
	 * 修改
	 *****************************************************************************************************/
	/**
	 * 修改实体
	 * 
	 * @param entity
	 * @return
	 */
	public <T> T update(T entity);

	/*****************************************************************************************************
	 * 查询
	 *****************************************************************************************************/
	/**
	 * 根据主键查找实体
	 * 
	 * @param entityClass
	 * @param primaryKey
	 *            主键
	 * @return
	 */
	public <T> T findByPrimaryKey(Class<T> entityClass, Serializable primaryKey);

	/**
	 * 查询所有
	 * 
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> findEntitys(Class<T> entityClass);

	/**
	 * 根据HQL查询
	 * 
	 * @param hql
	 * @param params
	 *            参数
	 * @return
	 */
	public List findByHql(StringBuffer hql, Map<String, Object> params);

	/**
	 * 根据SQL查询
	 * 
	 * @param sql
	 * @param params
	 *            参数
	 * @return
	 */
	public List findBySql(StringBuffer sql, Map<String, Object> params);

	/*****************************************************************************************************
	 * 分页查询
	 *****************************************************************************************************/
	/**
	 * 分页查询
	 * 
	 * @param entityClass
	 * 
	 * @param page
	 *            分页请求参数
	 * @return
	 */
	public <T> Page<T> findEntitysPage(Class<T> entityClass, VPageRequest page);

	/**
	 * HQL分页查询
	 * 
	 * @param hql
	 *            hql查询语句
	 * 
	 * @param params
	 *            参数
	 * 
	 * @param page
	 *            分页请求参数
	 * @return
	 */
	public Page findByHqlPage(StringBuffer hql, Map<String, Object> params, VPageRequest page);

	/**
	 * SQL分页查询
	 * 
	 * @param sql
	 *            SQL查询语句
	 * @param params
	 *            参数
	 * @param page
	 *            分页请求参数
	 * @return
	 */
	public Page findBySqlPage(StringBuffer sql, Map<String, Object> params, VPageRequest page);

	/*****************************************************************************************************
	 * 其他
	 *****************************************************************************************************/
	/**
	 * 执行hql(可用于增、删、改或DDL操作)
	 * 
	 * @param hql
	 * @param params
	 *            参数
	 * 
	 * @return 返回影响的行数
	 */
	public Integer executeHql(StringBuffer hql, Map<String, Object> params);

	/**
	 * 执行sql(可用于增、删、改或DDL操作)
	 * 
	 * @param sql
	 * @param params
	 *            参数
	 * @return 返回影响的行数
	 */
	public Integer executeSql(StringBuffer sql, Map<String, Object> params);

	/*****************************************************************************************************
	 * 
	 *****************************************************************************************************/
	/**
	 * 查询类型 enum
	 */
	public enum QueryType {
		JPQL, HQL, SQL
	}
}
