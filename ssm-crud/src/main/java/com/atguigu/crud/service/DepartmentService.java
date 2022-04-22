package com.atguigu.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.dao.DepartmentMapper;

/** 
* @author ���� Dafeng
* @version ����ʱ�䣺2019��2��14�� ����1:21:08 
* ��˵�� 
*/

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentMapper departmentMapper;
	
	public List<Department> getDepts() {
		//��������в�����Ϣ
		List<Department> list = departmentMapper.selectByExample(null);
		return list;
	}
	
	
}
