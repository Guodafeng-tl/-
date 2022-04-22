package com.atguigu.crud.service;
/** 
* @author ���� Dafeng
* @version ����ʱ�䣺2019��1��31�� ����10:10:01 
* ��˵�� 	����Dao��
*/

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.EmployeeExample;
import com.atguigu.crud.bean.EmployeeExample.Criteria;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	/*
	 * ��ѯ����Ա���Ͳ�����Ϣ
	 */
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}
	
	/*
	 * ��������Ա����Ϣ
	 */
	public Msg saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
		return Msg.success();
	}
	
	/*
	 * ��������Ա�����Ƿ����
	 * true�������   false��������
	 */
	public boolean checkUser(String empName) {
	 
		EmployeeExample example=new EmployeeExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count==0;
		
	}
	
	/*
	 * ����Ա��id��ȡԱ����Ϣ
	 */
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		
		return employee;
		
	}
	
	/*
	 * Ա������
	 */
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	
	/*
	 * ����idɾ��Ա����Ϣ
	 */
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}
	
	/*
	 * ����ɾ��Ա����Ϣ
	 */
	public void deleteBatch(List<Integer> ids) {
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//����delete from table_name where emp_id in ()
		criteria.andEmpIdIn(ids);
		
		employeeMapper.deleteByExample(example);
	}

	
}
