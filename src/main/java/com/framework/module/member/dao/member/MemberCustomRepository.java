package com.framework.module.member.dao.member;

import org.springframework.data.domain.Page;

import com.framework.module.common.page.VPageRequest;
import com.framework.module.member.schema.TMember;
import com.framework.module.member.vo.VMemberQuery;

/**
 * 用户 扩展接口
 * 
 * @author qq
 *
 */
public interface MemberCustomRepository {
	/**
	 * 分页查找用户
	 * 
	 * @param query
	 *            查询条件
	 * @param page
	 *            分页查询请求
	 * @return
	 */
	public Page<TMember> findMembersPage(VMemberQuery query, VPageRequest page);
}
