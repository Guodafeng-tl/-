package com.atguigu.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.EmployeeMapper;

/** 
* @author 作者 Dafeng
* @version 创建时间：2019年1月30日 上午10:40:03 
* 类说明 
* 1.导入springtest模板
* 2.@ContextConfiguration指定spring配置文件的位置
* 3直接Autowired要使用的组件
*/


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void testCRUD() {
		System.out.println(departmentMapper);
		
		//1.插入部门信息
		//departmentMapper.insertSelective(new Department(null, "软件开发部门"));
		//departmentMapper.insertSelective(new Department(null, "软件测试部门"));
		
		//2.插入员工信息
		//employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "www.com", 1));
		
		//3.使用sqlSession批量插入数据
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i=0;i<1000;i++) {
			String uuid = UUID.randomUUID().toString().substring(0, 5)+i;
			mapper.insertSelective(new Employee(null, uuid, "M", uuid+"@qq.com", 1));
		}
		System.out.println("批量执行成功");
	}
}
