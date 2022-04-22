package com.atguigu.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;

/** 
* @author 作者 Dafeng
* @version 创建时间：2019年1月30日 下午6:25:53 
* 类说明 
* 处理CRUD请求
*/
@Controller
public class EmployeeController {
	
	
	@Autowired
	EmployeeService employeeService;
	
	/*
	 * 单个批量二合一
	 * 批量删除1-2-3
	 * 单个删除1 
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids") String ids) {
		//批量删除
		if (ids.contains("-")) {
			List<Integer> del_ids=new ArrayList<>();
			
			String[] str_ids = ids.split("-");
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
		    employeeService.deleteBatch(del_ids);
			
		} else {
			//单个删除
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}  
		return Msg.success(); 
	}
	
	/*
	 * 员工更新方法
	 * Tomcat不会直接封装PUT请求数据为map,会封装POST
	 * 如果我们要支持PUT之类请求还要封装请求体中数据
	 * 在web.xml中配置HttpPutFormContentFilter
	 * 其作用将请求体中数据封装为map
	 * request被重新包装，request.getParam()被重写，就会从自己封装的map中取出数据
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	public Msg saveUpdateEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	/*
	 * 根据id查询员工
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
		
	}
	
	//校验员工用户名
	@ResponseBody
	@RequestMapping("/checkUser")
	public Msg checkUser(@RequestParam("empName")String empName) {
		//判断用户名必须是合法的表达式
		String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.fail().add("name_msg", "用户必须是6-16位数字和字母组成的或2-5位中文汉字 ");
		}
		
		boolean b=employeeService.checkUser(empName);
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail().add("name_msg", "用户名不可用");
		} 
	}
	
	/*
	 * 保存员工信息
	 * 1.支持JSR303校验
	 * 2.导入Hibernate Validator
	 */
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result) {
		if (result.hasErrors()) {
			//校验失败应该返回失败信息，在模态框中显示失败信息
			Map<String,Object> map=new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误字段是"+fieldError.getField());
				System.out.println("错误信息是"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success(); 
		}
		
	}
	
	
	/*
	 * 查询员工数据(分页查询)
	 */
	@RequestMapping("/emps")
	/*
	 * @ResponseBody正常工作
	   *  需要导入jackson包
	  * 负责将PageInfo转换成字符串
	 */
	@ResponseBody   //自动将返回对象变为json
	
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1") Integer pn) {
		PageHelper.startPage(pn,5);
		//startPage后面紧跟的就是一个分页查询
		List<Employee> emps=employeeService.getAll();
		/*
		 * 使用pageInfo包装查询之后的结果
		 * 然后将pageInfo交给页面就行
		 * pageInfo包含了详细的页面信息,包括我们查询出来的数据,连续显示的页数
		 */
		PageInfo page = new PageInfo(emps,5);
		return Msg.success().add("pageInfo", page);
	}
	
	
	
	/*
	 *  1.访问index.jsp页面
		2.Index.jsp页面发出查询员工列表请求
		3.EmployeeController来接受请求，查出员工数据
		4.来到list.jsp进行展示
	 */
//	@RequestMapping("/emps")
//	//pn是Page Number的缩写
//	public String getEmps(@RequestParam(value="pn",defaultValue="1") Integer pn,
//			Model model) {
//		
//		/*
//		 * 分页查询，引入PageHelper插件
//		 * 在查询所有之前只需要调用，传入页码，以及每页大小(即每页的数据条数)
//		 */
//		PageHelper.startPage(pn,5);
//		//startPage后面紧跟的就是一个分页查询
//		List<Employee> emps=employeeService.getAll();
//		/*
//		 * 使用pageInfo包装查询之后的结果
//		 * 然后将pageInfo交给页面就行
//		 * pageInfo包含了详细的页面信息,包括我们查询出来的数据,连续显示的页数
//		 */
//		PageInfo page = new PageInfo(emps,5);
//		
//		model.addAttribute("pageInfo", page);
//		return "list";
//	}
}
