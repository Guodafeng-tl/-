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
* @author ���� Dafeng
* @version ����ʱ�䣺2019��1��30�� ����10:40:03 
* ��˵�� 
* 1.����springtestģ��
* 2.@ContextConfigurationָ��spring�����ļ���λ��
* 3ֱ��AutowiredҪʹ�õ����
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
		
		//1.���벿����Ϣ
		//departmentMapper.insertSelective(new Department(null, "�����������"));
		//departmentMapper.insertSelective(new Department(null, "������Բ���"));
		
		//2.����Ա����Ϣ
		//employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "www.com", 1));
		
		//3.ʹ��sqlSession������������
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i=0;i<1000;i++) {
			String uuid = UUID.randomUUID().toString().substring(0, 5)+i;
			mapper.insertSelective(new Employee(null, uuid, "M", uuid+"@qq.com", 1));
		}
		System.out.println("����ִ�гɹ�");
	}
}
