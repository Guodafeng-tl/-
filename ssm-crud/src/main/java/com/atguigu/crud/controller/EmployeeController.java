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
* @author ���� Dafeng
* @version ����ʱ�䣺2019��1��30�� ����6:25:53 
* ��˵�� 
* ����CRUD����
*/
@Controller
public class EmployeeController {
	
	
	@Autowired
	EmployeeService employeeService;
	
	/*
	 * ������������һ
	 * ����ɾ��1-2-3
	 * ����ɾ��1 
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids") String ids) {
		//����ɾ��
		if (ids.contains("-")) {
			List<Integer> del_ids=new ArrayList<>();
			
			String[] str_ids = ids.split("-");
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
		    employeeService.deleteBatch(del_ids);
			
		} else {
			//����ɾ��
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}  
		return Msg.success(); 
	}
	
	/*
	 * Ա�����·���
	 * Tomcat����ֱ�ӷ�װPUT��������Ϊmap,���װPOST
	 * �������Ҫ֧��PUT֮������Ҫ��װ������������
	 * ��web.xml������HttpPutFormContentFilter
	 * �����ý������������ݷ�װΪmap
	 * request�����°�װ��request.getParam()����д���ͻ���Լ���װ��map��ȡ������
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	public Msg saveUpdateEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	/*
	 * ����id��ѯԱ��
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
		
	}
	
	//У��Ա���û���
	@ResponseBody
	@RequestMapping("/checkUser")
	public Msg checkUser(@RequestParam("empName")String empName) {
		//�ж��û��������ǺϷ��ı��ʽ
		String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.fail().add("name_msg", "�û�������6-16λ���ֺ���ĸ��ɵĻ�2-5λ���ĺ��� ");
		}
		
		boolean b=employeeService.checkUser(empName);
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail().add("name_msg", "�û���������");
		} 
	}
	
	/*
	 * ����Ա����Ϣ
	 * 1.֧��JSR303У��
	 * 2.����Hibernate Validator
	 */
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result) {
		if (result.hasErrors()) {
			//У��ʧ��Ӧ�÷���ʧ����Ϣ����ģ̬������ʾʧ����Ϣ
			Map<String,Object> map=new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("�����ֶ���"+fieldError.getField());
				System.out.println("������Ϣ��"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success(); 
		}
		
	}
	
	
	/*
	 * ��ѯԱ������(��ҳ��ѯ)
	 */
	@RequestMapping("/emps")
	/*
	 * @ResponseBody��������
	   *  ��Ҫ����jackson��
	  * ����PageInfoת�����ַ���
	 */
	@ResponseBody   //�Զ������ض����Ϊjson
	
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1") Integer pn) {
		PageHelper.startPage(pn,5);
		//startPage��������ľ���һ����ҳ��ѯ
		List<Employee> emps=employeeService.getAll();
		/*
		 * ʹ��pageInfo��װ��ѯ֮��Ľ��
		 * Ȼ��pageInfo����ҳ�����
		 * pageInfo��������ϸ��ҳ����Ϣ,�������ǲ�ѯ����������,������ʾ��ҳ��
		 */
		PageInfo page = new PageInfo(emps,5);
		return Msg.success().add("pageInfo", page);
	}
	
	
	
	/*
	 *  1.����index.jspҳ��
		2.Index.jspҳ�淢����ѯԱ���б�����
		3.EmployeeController���������󣬲��Ա������
		4.����list.jsp����չʾ
	 */
//	@RequestMapping("/emps")
//	//pn��Page Number����д
//	public String getEmps(@RequestParam(value="pn",defaultValue="1") Integer pn,
//			Model model) {
//		
//		/*
//		 * ��ҳ��ѯ������PageHelper���
//		 * �ڲ�ѯ����֮ǰֻ��Ҫ���ã�����ҳ�룬�Լ�ÿҳ��С(��ÿҳ����������)
//		 */
//		PageHelper.startPage(pn,5);
//		//startPage��������ľ���һ����ҳ��ѯ
//		List<Employee> emps=employeeService.getAll();
//		/*
//		 * ʹ��pageInfo��װ��ѯ֮��Ľ��
//		 * Ȼ��pageInfo����ҳ�����
//		 * pageInfo��������ϸ��ҳ����Ϣ,�������ǲ�ѯ����������,������ʾ��ҳ��
//		 */
//		PageInfo page = new PageInfo(emps,5);
//		
//		model.addAttribute("pageInfo", page);
//		return "list";
//	}
}
