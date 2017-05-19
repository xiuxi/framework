package com.framework.module.springDataJPATest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.framework.module.springDataJPATest.schema.TCrudTest;

/**
 * 增删改查测试 Repository
 * 
 * @author qq
 * 
 */
public interface CrudTestRepository extends JpaRepository<TCrudTest, String> {
}