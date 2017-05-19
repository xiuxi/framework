package com.framework.module.member.dao.member;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.framework.module.common.dao.GeneralDao;
import com.framework.module.common.page.VPageRequest;
import com.framework.module.member.schema.TMember;
import com.framework.module.member.vo.VMemberQuery;

/**
 * 用户 RepositoryImpl
 * 
 * @author qq
 *
 */
public class MemberRepositoryImpl implements MemberCustomRepository {
	@Autowired
	private GeneralDao generalDao;

	/**
	 * 分页查找用户
	 * 
	 * @param query
	 *            查询条件
	 * @param page
	 *            分页查询请求
	 * @return
	 */
	@Override
	public Page<TMember> findMembersPage(VMemberQuery query, VPageRequest page) {
		StringBuffer hql = new StringBuffer("FROM TMember po WHERE 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();

		if (query != null) {
			// 搜索关键字(手机号/用户姓名)
			if (StringUtils.isNotBlank(query.getKey())) {
				hql.append("AND (po.mobile LIKE :key OR po.name LIKE :key) ");
				params.put("key", "%" + query.getKey() + "%");
			}
			// 性别
			if (query.getGender() != null) {
				hql.append("AND po.gender=:gender ");
				params.put("gender", query.getGender());
			}
			// 会员注册日期起始日期
			if (query.getBegin() != null) {
				hql.append("AND po.dateCreated>=:begin ");
				params.put("begin", query.getBegin());
			}
			// 会员注册日期截止日期
			if (query.getEnd() != null) {
				hql.append("AND po.dateCreated<=:end ");
				params.put("end", query.getEnd());
			}
		}

		if (page == null) {
			page = new VPageRequest(); // 默认第一页,每页显示15条
		}

		Page<TMember> response = generalDao.findByHqlPage(hql, params, page);
		return response;
	}

}
