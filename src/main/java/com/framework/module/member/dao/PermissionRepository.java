package com.framework.module.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.framework.module.member.schema.TPermission;

/**
 * 权限 Repository
 * 
 * @author qq
 * 
 */
public interface PermissionRepository extends JpaRepository<TPermission, String> {
}