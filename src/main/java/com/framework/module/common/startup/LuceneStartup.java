package com.framework.module.common.startup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lucen启动器
 * 
 * 用于对数据库中已经存在的数据建立索引
 * 
 * @author qq
 * 
 */
@Component
@Transactional
public class LuceneStartup implements ApplicationListener<ContextRefreshedEvent> {
	private final static Logger logger = LoggerFactory.getLogger(LuceneStartup.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 是否已经初始化
	 */
	private boolean isInitialized;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event instanceof ContextRefreshedEvent && !isInitialized) {
			isInitialized = true;

			logger.info("---------- Lucene创建索引开始... ----------");
			try {
				FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
				fullTextEntityManager.createIndexer().startAndWait();
				logger.info("---------- Lucene索引创建成功  ----------");
			} catch (IllegalArgumentException e) {
				logger.warn("---------- Lucene找不到需要创建索引的Entity ----------");
			} catch (Exception e) {
				logger.error("---------- Lucene索引创建失败 ----------", e);
				throw new IllegalArgumentException(e);
			}
		}
	}

	/*
	 * getter、setter
	 */
	public boolean isIsinitialized() {
		return isInitialized;
	}

	public void setIsinitialized(boolean isinitialized) {
		this.isInitialized = isinitialized;
	}

}
