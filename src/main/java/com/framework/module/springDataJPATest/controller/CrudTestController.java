package com.framework.module.springDataJPATest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.module.springDataJPATest.schema.TCrudTest;
import com.framework.module.springDataJPATest.service.CrudTestService;
import com.framework.module.springDataJPATest.vo.VCrudTestDetail;

/**
 * 增删改查测试 Controller
 * 
 * @author qq
 * 
 */
@Controller
@RequestMapping("/CrudTestController")
public class CrudTestController {
	@Autowired
	private CrudTestService crudTestService;

	/**
	 * 保存crudTest
	 * 
	 */
	@ResponseBody
	@RequestMapping("/saveCrudTest")
	public void save() {
		TCrudTest crudTest = new TCrudTest();
		crudTest.setAge(10);
		crudTest.setName("王五");
		crudTestService.save(crudTest);
	}

	/**
	 * 删除crudTest
	 * 
	 */
	@ResponseBody
	@RequestMapping("/deleteCrudTest")
	public void delete() {
		crudTestService.delete("");
	}

	/**
	 * 修改crudTest
	 * 
	 */
	@ResponseBody
	@RequestMapping("/updateCrudTest")
	public void update() {
		TCrudTest crudTest = new TCrudTest();
		crudTest.setId("");
		crudTest.setAge(10);
		crudTest.setName("李四1111");
		crudTestService.update(crudTest);
	}

	/**
	 * crudTest详情
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/crudTestDetail")
	public VCrudTestDetail crudTestDetail() {
		VCrudTestDetail crudTestDetail = new VCrudTestDetail();
		crudTestDetail.setAge(10);
		crudTestDetail.setName("张三");

		TCrudTest crudTest = crudTestService.getCrudTest("");
		System.out.println(crudTest.getName());

		return crudTestDetail;
	}
}