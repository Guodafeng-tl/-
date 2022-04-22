package com.atguigu.crud.service;
/** 
* @author 作者 Dafeng
* @version 创建时间：2019年1月31日 上午10:10:01 
* 类说明 	调用Dao层
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
	 * 查询所有员工和部门信息
	 */
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}
	
	/*
	 * 新增保存员工信息
	 */
	public Msg saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
		return Msg.success();
	}
	
	/*
	 * 检验插入的员工名是否可用
	 * true代表可用   false代表不可用
	 */
	public boolean checkUser(String empName) {
	 
		EmployeeExample example=new EmployeeExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count==0;
		
	}
	
	/*
	 * 根据员工id获取员工信息
	 */
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		
		return employee;
		
	}
	
	/*
	 * 员工更新
	 */
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	
	/*
	 * 根据id删除员工信息
	 */
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}
	
	/*
	 * 批量删除员工信息
	 */
	public void deleteBatch(List<Integer> ids) {
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//类似delete from table_name where emp_id in ()
		criteria.andEmpIdIn(ids);
		
		employeeMapper.deleteByExample(example);
	}

	
}
