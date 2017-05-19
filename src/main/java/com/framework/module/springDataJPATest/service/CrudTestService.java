package com.framework.module.springDataJPATest.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.module.springDataJPATest.dao.CrudTestRepository;
import com.framework.module.springDataJPATest.schema.TCrudTest;

/**
 * 增删改查测试 Service
 * 
 * @author qq
 * 
 */
@Service
@Transactional
public class CrudTestService {
	private final static Logger logger = LoggerFactory.getLogger(CrudTestService.class);

	@Autowired
	private CrudTestRepository crudTestRepository;

	/**
	 * 保存TCrudTest
	 * 
	 * @param crudTest
	 */
	public void save(TCrudTest crudTest) {
		crudTestRepository.save(crudTest);
		logger.info("save TCrudTest success");
	}

	/**
	 * 根据ID删除TCrudTest
	 * 
	 * @param id
	 */
	public void delete(String crudTestId) {
		if (StringUtils.isNotBlank(crudTestId)) {
			boolean exists = crudTestRepository.exists(crudTestId);
			if (exists) {
				crudTestRepository.delete(crudTestId);
				logger.info("delete TCrudTest success");
			}
		}
	}

	/**
	 * 修改TCrudTest
	 * 
	 */
	public void update(TCrudTest crudTest) {
		if (StringUtils.isNotBlank(crudTest.getId())) {
			TCrudTest oldCrudTest = crudTestRepository.findOne(crudTest.getId());
			if (oldCrudTest != null) {
				oldCrudTest.setAge(crudTest.getAge());
				oldCrudTest.setName(crudTest.getName());
				crudTestRepository.save(oldCrudTest);

				logger.info("update TCrudTest success");
			}
		}
	}

	/**
	 * 根据ID获取TCrudTest
	 * 
	 * @param crudTestId
	 * @return
	 */
	public TCrudTest getCrudTest(String crudTestId) {
		return crudTestRepository.getOne(crudTestId);
	}

}