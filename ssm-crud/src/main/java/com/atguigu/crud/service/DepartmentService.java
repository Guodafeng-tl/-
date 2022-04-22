package com.atguigu.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.dao.DepartmentMapper;

/** 
* @author 作者 Dafeng
* @version 创建时间：2019年2月14日 下午1:21:08 
* 类说明 
*/

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentMapper departmentMapper;
	
	public List<Department> getDepts() {
		//查出的所有部门信息
		List<Department> list = departmentMapper.selectByExample(null);
		return list;
	}
	
	
}
