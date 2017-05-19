package com.framework.module.common.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.framework.module.common.dao.GeneralDao;
import com.framework.module.common.page.VPageRequest;

/**
 * 通用Dao Hibernate实现
 * 
 * 待测试...
 * 
 * @author qq
 * 
 */
// @Repository
public class GeneralDaoHibernateImpl extends HibernateDaoSupport implements GeneralDao {
	private final static Logger logger = LoggerFactory.getLogger(GeneralDaoHibernateImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	/*****************************************************************************************************
	 * 保存
	 *****************************************************************************************************/
	/**
	 * 保存实体
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public <T> T save(T entity) {
		this.getHibernateTemplate().save(entity);
		return entity;
	}

	/**
	 * 批量保存实体(默认缓存50条)
	 * 
	 * @param entitys
	 */
	@Override
	public <T> void batchSave(final List<T> entitys) {
		batchSave(entitys, 50);
	}

	/**
	 * 批量保存实体
	 * 
	 * @param entitys
	 * @param cacheNum
	 *            在session缓存中的条数,当缓存到cacheNum条数时才刷新(flush)到数据库中执行,以提高效率
	 */
	@Override
	public <T> void batchSave(final List<T> entitys, final int cacheNum) {
		Assert.isTrue(cacheNum > 0, "The cacheNum  must be greater than 0");

		if (CollectionUtils.isNotEmpty(entitys)) {
			this.getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
				public List<T> doInHibernate(Session session) throws HibernateException {
					int size = entitys.size();
					for (int i = 0; i < size; i++) {
						session.save(entitys.get(i));

						if ((i + 1) % cacheNum == 0) {
							session.flush(); // 只是将Hibernate缓存中的数据提交到数据库，保持与数据库数据的同步
							session.clear(); // 清除内部缓存的全部数据，及时释放出占用的内存
						}
					}

					return null;
				}
			});
		}
	}

	/*****************************************************************************************************
	 * 删除
	 *****************************************************************************************************/
	/**
	 * 删除实体
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public <T> T delete(T entity) {
		this.getHibernateTemplate().delete(entity);
		return entity;
	}

	/**
	 * 根据主键删除实体
	 * 
	 * @param entityClass
	 * @param primaryKey
	 *            主键
	 * @return
	 */
	@Override
	public <T> T deleteByPrimaryKey(Class<T> entityClass, Serializable primaryKey) {
		T entity = this.findByPrimaryKey(entityClass, primaryKey);
		this.delete(entity);
		return entity;
	}

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
	@Override
	public <T> void batchDeleteByPrimaryKey(Class<T> entityClass, String primaryKeyName,
			List<Serializable> primaryKeys) {
		if (CollectionUtils.isNotEmpty(primaryKeys)) {
			final StringBuffer hql = new StringBuffer(String.format("DELETE FROM %s po WHERE po.%s in(:primaryKeys)",
					entityClass.getSimpleName(), primaryKeyName));

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("primaryKeys", primaryKeys);
			this.executeHql(hql, params);
		}
	}

	/**
	 * 删除所有实体
	 * 
	 * @param entityClass
	 */
	@Override
	public <T> void deleteAll(Class<T> entityClass) {
		final StringBuffer hql = new StringBuffer(String.format("DELETE FROM %s", entityClass.getSimpleName()));
		this.executeHql(hql, null);
	}

	/*****************************************************************************************************
	 * 修改
	 *****************************************************************************************************/
	/**
	 * 修改实体
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public <T> T update(T entity) {
		this.getHibernateTemplate().update(entity);
		return entity;
	}

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
	@Override
	public <T> T findByPrimaryKey(Class<T> entityClass, Serializable primaryKey) {
		return this.getHibernateTemplate().get(entityClass, primaryKey);
	}

	/**
	 * 查询所有
	 * 
	 * @param entityClass
	 * @return
	 */
	@Override
	public <T> List<T> findEntitys(Class<T> entityClass) {
		return this.getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 根据HQL查询
	 * 
	 * @param hql
	 * @param params
	 *            参数
	 * @return
	 */
	@Override
	public List findByHql(final StringBuffer hql, final Map<String, Object> params) {
		return this.query(hql, params, QueryType.HQL);
	}

	/**
	 * 根据SQL查询
	 * 
	 * @param sql
	 * @param params
	 *            参数
	 * @return
	 */
	@Override
	public List findBySql(final StringBuffer sql, final Map<String, Object> params) {
		return this.query(sql, params, QueryType.SQL);
	}

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
	@Override
	public <T> Page<T> findEntitysPage(Class<T> entityClass, final VPageRequest page) {
		final StringBuffer queryString = new StringBuffer(String.format("FROM %s", entityClass.getName()));

		return this.queryPage(queryString, null, page, QueryType.HQL);
	}

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
	@Override
	public Page findByHqlPage(final StringBuffer hql, final Map<String, Object> params, final VPageRequest page) {
		return this.queryPage(hql, params, page, QueryType.HQL);
	}

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
	@Override
	public Page findBySqlPage(final StringBuffer sql, final Map<String, Object> params, final VPageRequest page) {
		return this.queryPage(sql, params, page, QueryType.SQL);
	}

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
	@Override
	public Integer executeHql(final StringBuffer hql, final Map<String, Object> params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException {
				Query query = buildQuery(session, hql, params, QueryType.HQL);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 执行sql(可用于增、删、改或DDL操作)
	 * 
	 * @param sql
	 * @param params
	 *            参数
	 * @return 返回影响的行数
	 */
	@Override
	public Integer executeSql(final StringBuffer sql, final Map<String, Object> params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException {
				Query query = buildQuery(session, sql, params, QueryType.SQL);
				return query.executeUpdate();
			}
		});
	}

	/*****************************************************************************************************
	 * 辅助方法
	 *****************************************************************************************************/
	/**
	 * 构建Query对象
	 * 
	 * @param session
	 * 
	 * @param queryString
	 *            查询字符串
	 * 
	 * @param params
	 *            查询参数
	 * 
	 * @param buildHqlQuery
	 *            true：构建HQLQuery;false：构建SQLQuery
	 * @return
	 */
	private Query buildQuery(Session session, StringBuffer queryString, Map<String, Object> params,
			QueryType queryType) {
		Assert.notNull(queryString, "The queryString is not null");
		Assert.notNull(queryType, "The queryType is not null");

		Query query = queryType == QueryType.SQL ? session.createSQLQuery(queryString.toString())
				: session.createQuery(queryString.toString());

		// 设置参数
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (value != null) {
					if (Collection.class.isAssignableFrom(value.getClass())) {
						query.setParameterList(key, (Collection) value);
					} else {
						query.setParameter(key, value);
					}
				}
			}
		}

		// 开启查询缓存
		query.setCacheable(true);
		return query;
	}

	/**
	 * 查询
	 * 
	 * @param queryString
	 *            查询字符串
	 * @param params
	 *            参数
	 * @param queryType
	 * @return
	 */
	private List query(final StringBuffer queryString, final Map<String, Object> params, final QueryType queryType) {
		Assert.notNull(queryString, "The queryString is not null");
		Assert.notNull(queryType, "The queryType is not null");

		return this.getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException {
				Query query = buildQuery(session, queryString, params, queryType);
				return query.list();
			}
		});
	}

	/**
	 * 分页查询
	 * 
	 * @param queryString
	 *            查询字符串
	 * @param params
	 *            参数
	 * @param page
	 *            分页请求参数
	 * @param queryType
	 * @return
	 */
	private Page queryPage(final StringBuffer queryString, final Map<String, Object> params, final VPageRequest page,
			final QueryType queryType) {
		Assert.notNull(queryString, "The queryString is not null");
		Assert.notNull(queryType, "The queryType is not null");

		return this.getHibernateTemplate().execute(new HibernateCallback<Page>() {
			public Page doInHibernate(Session session) throws HibernateException {
				StringBuffer countHql = new StringBuffer(buildSelectCountStatement(queryString.toString()));

				Query listQuery = buildQuery(session, queryString, params, queryType);
				Query countQuery = buildQuery(session, countHql, params, queryType);

				try {
					int firstResult = page.getPageNum() * page.getPageSize(); // Hibernate从0开始,Jdbc从1开始
					List lists = listQuery.setFirstResult(firstResult).setMaxResults(page.getPageSize()).list();
					Long count = (Long) countQuery.list().get(0);

					// PageRequest(0为第一页)
					return new PageImpl(lists, new PageRequest(page.getPageNum(), page.getPageSize()), count);
				} catch (Exception e) {
					logger.error("数据库操作异常[queryString={{}};msg={{}}]", queryString, e.getMessage());
					throw new IllegalArgumentException(e);
				}
			}
		});

	}

	/**
	 * 构建SLECT COUNT(*)语句
	 * 
	 * @param queryString
	 *            查询字符串
	 * @return
	 */
	private String buildSelectCountStatement(String queryString) {
		Assert.hasText(queryString, "The queryString is not null");

		String response = String.format("SELECT COUNT(*) %s", queryString);

		String trimUpperCase = queryString.trim().toUpperCase();
		if (trimUpperCase.indexOf("SELECT") == 0) { // 以SELECT开头
			int fromIndex = trimUpperCase.indexOf("FROM"); // FROM字符串所在下标
			response = String.format("SELECT COUNT(*) %s", queryString.trim().substring(fromIndex - 1));
		}
		return response;
	}

	/*****************************************************************************************************
	 * 
	 *****************************************************************************************************/
}
