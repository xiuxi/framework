package com.framework.module.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.framework.module.member.schema.TRole;

/**
 * 角色 Repository
 * 
 * @author qq
 * 
 */
public interface RoleRepository extends JpaRepository<TRole, String> {
}