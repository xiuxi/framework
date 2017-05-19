package com.framework.module.member.dao.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.framework.module.member.schema.TMember;

/**
 * 用户 Repository
 * 
 * @author qq
 * 
 */
public interface MemberRepository extends JpaRepository<TMember, String>, MemberCustomRepository {
	/**
	 * 根据手机号查找用户
	 * 
	 * @param mobile
	 *            手机号
	 * @return
	 */
	TMember findByMobile(@Param("mobile") String mobile);
}