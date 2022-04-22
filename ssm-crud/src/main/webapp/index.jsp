<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工列表信息</title>

<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!-- APP_PATH 获取服务器的路径 以/开始  但是不以/结尾 -->

<!-- web路径问题总结 -->
<!-- 不以/开始的相对路径,找资源以当前资源的路径为基准,经常容易出问题 -->
<!-- 以/开始的相对路径,找资源以服务器的路径为基准(http://localhost:3306) 需要加上项目名-->

<!-- 引入jquery -->
<script type="text/javascript" src="${APP_PATH}/static/js/jquery.min.js"></script>
<!-- 引入样式 bootstrap -->
<link href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

</head>
<body>

	<!-- 员工添加模态框 -->
	<div  style="background-color: blue" class="modal fade" id="empAddModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
	  <div class="modal-dialog" role="document" >
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">员工添加</h4>
	      </div>
	      <div class="modal-body">
	        	<form class="form-horizontal">
				  <div class="form-group">
				    <label   class="col-sm-2 control-label">empName</label>
				    <div class="col-sm-10">
				      <input type="text" name="empName" class="form-control" id="empName_add_input" placeholder="empName" >
				      <span class="help-block"></span>
				    </div>
				  </div>
				  <div class="form-group">
				    <label   class="col-sm-2 control-label">email</label>
				    <div class="col-sm-10">
				      <input type="text" name="email" class="form-control" id="email_add_input" placeholder="dafeng.guo@foxmail.com" >
				      <span class="help-block"></span>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label   class="col-sm-2 control-label">gender</label>
				    <div class="col-sm-10">
				        <label class="radio-inline">
						  <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
						</label>
						<label class="radio-inline">
						  <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
						</label>
				    </div>
				  </div>
				  
				   <div class="form-group">
				    <label   class="col-sm-2 control-label">deptName</label>
				    <div class="col-sm-6">
				        <!-- 部门提交部门id即可 -->
					    <select class="form-control" name="dId" id="dept_add_select">
						 	
						</select>
				    </div>
				  </div>
				</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<!-- 员工修改模态框 -->
	<div  style="background-color: blue" class="modal fade" id="empUpdateModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
	  <div class="modal-dialog" role="document" >
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">员工修改</h4>
	      </div>
	      <div class="modal-body">
	        	<form class="form-horizontal">
				  <div class="form-group">
				    <label   class="col-sm-2 control-label">empName</label>
				    <div class="col-sm-10">
				      <p class="form-control-static" id="empName_update_static"></p>
				    </div>
				  </div>
				  <div class="form-group">
				    <label   class="col-sm-2 control-label">email</label>
				    <div class="col-sm-10">
				      <input type="text" name="email" class="form-control" id="email_update_input" placeholder="dafeng.guo@foxmail.com" >
				      <span class="help-block"></span>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label   class="col-sm-2 control-label">gender</label>
				    <div class="col-sm-10">
				        <label class="radio-inline">
						  <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked"> 男
						</label>
						<label class="radio-inline">
						  <input type="radio" name="gender" id="gender2_update_input" value="F"> 女
						</label>
				    </div>
				  </div>
				  
				   <div class="form-group">
				    <label   class="col-sm-2 control-label">deptName</label>
				    <div class="col-sm-6">
				        <!-- 部门提交部门id即可 -->
					    <select class="form-control" name="dId" id="dept_update_select">
						 	
						</select>
				    </div>
				  </div>
				</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<!-- 搭建显示页面 -->
	<div class="container">
			<!-- 显示项目标题 -->
			<div class="row">
				<div class=".col-md-12">
					<h1>SSM-CRUD</h1>
				</div>
			</div>
			<!-- 显示增加和删除按钮 -->
			<div class="row">
				<div class="col-md-3 col-md-offset-9 ">
					<button class="btn btn-success" id="emp_add_model_btn">新增</button>
					<button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
				</div>
			</div>
			<!-- 显示表格数据 -->
			<div class="row">
				<div class=".col-md-12">
					<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>
								<input type="checkbox" id="check_all"/>
							</th>
							<th>#</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>deptName</th>
							<th>操作</th>
						</tr>
					</thead>	
					<tbody>
						
					</tbody>
					</table>
				</div>
			</div>
			<!-- 显示分页信息 -->
			<div class="row">
				<!-- 分页文字信息 -->
				<div class="col-md-6" id="page_info_area">
					
				</div>
				<!-- 分页条信息 -->
					<div class="col-md-6" id="page_nav_area">
							
				    </div>
			</div>
	</div>
	
	<script type="text/javascript">
		//定义一个全局变量的总记录数
		var totalRecord,currentNumPage;
	
		//1.页面加载完成以后，直接去发送ajax请求，要到分页数据
		$(function(){
			to_page(1);
		});
		
		function to_page(pn){
			$.ajax({
				url:"${APP_PATH}/emps",
				data:"pn="+pn,
				type:"GET",
				success:function(result){
					//console.log(result);
					//1.解析并显示员工数据
					build_emps_table(result);
					//2解析并显示分页信息
					build_page_info(result);
					//3解析显示分页条信息
					build_page_nav(result);
				}
				
			});
		}
		
		//1.解析并显示员工数据
		function build_emps_table(result){
			//清空table表格
			$("#emps_table tbody").empty();
			var emps=result.extend.pageInfo.list;
			$.each(emps,function(index,item){
				var checkBoxTd=$("<td><input type='checkbox' class='check_item'/></td>");
				 
				var empIdTd=$("<td></td>").append(item.empId);
				var empNameTd=$("<td></td>").append(item.empName);
				//var gender=item.gender=='M'?"男":"女";
				var genderTd=$("<td></td>").append(item.gender=='M'?"男":"女");
				var emailTd=$("<td></td>").append(item.email);
				var deptNameTd=$("<td></td>").append(item.department.deptName);
				
				var editbtn=$("<button></button>").addClass("btn btn-success btn-sm edit-btn")
				.append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("编辑"));
				
				//为编辑按钮添加一个自定义属性，来表示当前员工的id,为修改作铺垫
				editbtn.attr("edit-id",item.empId);
				
				var delbtn=$("<button></button>").addClass("btn btn-danger btn-sm delete-btn")
				.append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
				
				//为删除按钮添加一个自定义属性，来表示当前员工的id,为删除作铺垫
				delbtn.attr("delete-id",item.empId);
				
				var btnTd=$("<td></td>").append(editbtn).append(" ").append(delbtn);
				//append方法执行完以后还是返回原来的元素
				$("<tr></tr>").append(checkBoxTd)
				.append(empIdTd)
				.append(empNameTd)
				.append(genderTd)
				.append(emailTd)
				.append(deptNameTd)
				.append(btnTd)
				.appendTo("#emps_table tbody");
				
			});
		}
		//2解析并显示分页信息
		function build_page_info(result){
			$("#page_info_area").empty();
			$("#page_info_area").append("当前第"+
					result.extend.pageInfo.pageNum+"页,总共"+
					result.extend.pageInfo.pages+"页,总共"+
					result.extend.pageInfo.total+"条记录");
			totalRecord=result.extend.pageInfo.total;
			currentNumPage=result.extend.pageInfo.pageNum;
		}
		
		//3解析显示分页条信息
		
	 function build_page_nav(result){
		    $("#page_nav_area").empty();
			var ul=$("<ul></ul>").addClass("pagination");
			//构建元素
			var firstPageLi=$("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
			var perPageLi=$("<li></li>").append($("<a></a>").append("&laquo;"));
			if(result.extend.pageInfo.hasPreviousPage==false){
				firstPageLi.addClass("disabled");
				perPageLi.addClass("disabled"); 
			}else{
				//为元素添加点击翻页事件
				firstPageLi.click(function(){
					to_page(1);
				});
				
				perPageLi.click(function(){
					to_page(result.extend.pageInfo.pageNum-1);
				});
			}
			
			
			var nextPageLi=$("<li></li>").append($("<a></a>").append("&raquo;"));
			var lastPageLi=$("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
			if(result.extend.pageInfo.hasNextPage==false){
				nextPageLi.addClass("disabled");
				lastPageLi.addClass("disabled"); 
			}else{
				//为元素添加点击翻页事件
				lastPageLi.click(function(){
					to_page(result.extend.pageInfo.pages);
				});
				nextPageLi.click(function(){
					to_page(result.extend.pageInfo.pageNum+1);
				});
			}
			
			
			
			//添加首页和前一页的提示
			ul.append(firstPageLi).append(perPageLi);
			//遍历给ul中添加页码提示
			$.each(result.extend.pageInfo.navigatepageNums,function(index,item){
				
				
				var numLi=$("<li></li>").append($("<a></a>").append(item));
				if(result.extend.pageInfo.pageNum==item){
					numLi.addClass("active"); 
				}
				numLi.click(function(){
					to_page(item);
				});
				ul.append(numLi);
			});
			
			//添加下一页和末页的提示
			ul.append(nextPageLi).append(lastPageLi);
			//将ul加入到nav标签
			var navEle=$("<nav></nav>").append(ul);
			//添加到div中
			
			$("#page_nav_area").append(navEle);  
		}
		//模态框表单重置方法
		function reset_form(ele){
			//清空表单内容
			$(ele)[0].reset();
			//清空格式
			$(ele).find("*").removeClass("has-error has-success");
			$(ele).find(".help-block").text("");
		}
		
		
		//点击新增按钮弹出模态框
		$("#emp_add_model_btn").click(function(){
			//点击事件先清除表单数据 (表单完全重置)
			reset_form("#empAddModel form");
			
			//发送ajax请求,查出部门信息,显示在下拉列表中
			getDepts("#empAddModel select");
			//弹出模态框
			$("#empAddModel").modal({
				backdrop:"static"
			});
		});
		
		//查出所有部门信息并显示在下拉列
		function getDepts(ele){
			$(ele).empty();
			 $.ajax({
				 url:"${APP_PATH}/depts",
				 type:"GET",
				 success:function(result){
					 //console.log(result);
					 //显示部门信息在下拉列表中
					
					 $.each(result.extend.depts,function(){
						 var optionEle=$("<option></option>").append(this.deptName).attr("value",this.deptId);
						 optionEle.appendTo(ele);
					 })
				 }
			 });
		}
		//校验表单数据
		function validate_add_form(){
			//1.拿到要校验的数据,使用正则表达式
			var empName=$("#empName_add_input").val();
			var regName=/(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/; 
			//alert(regName.test(empName)); 
			if(!regName.test(empName)){
				//alert("用户名可以是2-5位中文或6-16英文数字组合");
				show_validate_msg("#empName_add_input","error","用户名必须是2-5位中文或6-16英文数字组合");
				return false; 
			} else{
				show_validate_msg("#empName_add_input","success","");
			}
			
			//2.邮箱的校验
		    var email=$("#email_add_input").val();
			var regEmail=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!regEmail.test(email)){
				//alert("邮箱格式不正确");
				show_validate_msg("#email_add_input","error","邮箱格式不正确");
				return false;
			} else{
				show_validate_msg("#email_add_input","success","");
			}
			
			return true;
		}
		//显示校验结果的提示信息
		function show_validate_msg(ele,status,msg){
			//清除当前元素的校验状态
			 $(ele).parent().removeClass("has-success has-error");
			 $(ele).next("span").text("");
			 if ("success"==status) {
				 $(ele).parent().addClass("has-success");
				 $(ele).next("span").text(msg);
			}else if("error"==status){
				$(ele).parent().addClass("has-error");
				$(ele).next("span").text(msg);
			}
		}
		
		//校验用户名是否可用
		$("#empName_add_input").change(function(){
			var empName=this.value;
			//发送ajax请求校验用户名是否可用
			$.ajax({
				url:"${APP_PATH}/checkUser",
				data:"empName="+empName,
				type:"POST",
				success:function(result){
					//console.log(result);
					 if (result.code==100) {
						show_validate_msg("#empName_add_input","success","用户名可用");
						$("#emp_save_btn").attr("empName","success");
					 } else {
						show_validate_msg("#empName_add_input","error",result.extend.name_msg);
						$("#emp_save_btn").attr("empName","error");
					} 
				}
			});
		});
		
		//点击保存员工
		$("#emp_save_btn").click(function(){
			//将模态框的表单中添加的数据提交给服务器进行保存
			//1.1先对要提交给服务器的数据进行校验
			 if(!validate_add_form()){
				return false;
			};
			//1.2判断之前的ajax员工用户名校验是否成功
			if ($(this).attr("empName")=="error") {
				return false;
			}
			//2.发送ajax请求保存员工
			//alert($("#empAddModel form").serialize());
			 $.ajax({
				url:"${APP_PATH}/emp",
				type:"POST",
				data:$("#empAddModel form").serialize(),
				success:function(result){
					//console.log(result);
					//alert(result.msg);
					//员工数据保存成功
					if (result.code==100) {
						//1.关闭模态框
						$("#empAddModel").modal('hide');
						//2.来到最后一页，显示刚才保存的数据;发送ajax请求显示最后一页即可
						to_page(totalRecord);
					}else{
						//失败显示失败信息
						//console.log(result);
						if (undefined != result.extend.errorFields.email) {
							show_validate_msg("#email_add_input","error",result.extend.errorFields.email);
						} 
						if (undefined != result.extend.errorFields.empName) {
							show_validate_msg("#empName_add_input","error",result.extend.errorFields.empName);
						} 
					}
					
				}
			}); 
		});
		
		 
		//此时不能绑定按钮，因为此时ajax请求还没有发送获取到数据和编辑按钮
		/* $("#edit-btn").click(function () {
			alert("edit")
		}); */
		//即是我们在按钮创建之前绑定了click事件，所以绑定不上
		//解决方法:1.可以在按钮创建时绑定 
		//2.使用绑定点击live()方法 ，后来添加元素也能绑定 ,但是jquery新版live()已经失效，可以使用on替代
		$(document).on("click",".edit-btn",function () {
			//alert("edit");
			//1查出部门信息，显示部门列表
			getDepts("#empUpdateModel select");
			//2.查询出员工信息，显示员工信息
			getEmp($(this).attr("edit-id"));
			//3.把员工id给员工更新按钮
			$("#emp_update_btn").attr("edit-id",$(this).attr("edit-id"));
		    //弹出模态框
			$("#empUpdateModel").modal({
				backdrop:"static"
			});
		});
		
		function getEmp(id) {
			$.ajax({
				url:"${APP_PATH}/emp/"+id,
				type:"GET",
				success:function(result){
					 //console.log(result);
					var empData=result.extend.emp;
					$("#empName_update_static").text(empData.empName);
					$("#email_update_input").val(empData.email);
					$("#empUpdateModel input[name=gender]").val([empData.gender]);
					$("#empUpdateModel select").val([empData.dId]);
				}
			});
		}
		
		
		//点击更新，更新员工信息
		$("#emp_update_btn").click(function() {
			//1验证邮箱是否合法
			 var email=$("#email_update_input").val();
				var regEmail=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
				if(!regEmail.test(email)){
					//alert("邮箱格式不正确");
					show_validate_msg("#email_update_input","error","邮箱格式不正确");
					return false;
				} else{
					show_validate_msg("#email_update_input","success","");
				}
				
			//2发送ajax请求，保存更新的员工数据
			   $.ajax({
				  url:"${APP_PATH}/emp/"+$(this).attr("edit-id"),
				  type:"PUT",
				  data:$("#empUpdateModel form").serialize(),
				  success:function(result){
					  
					  $("#empUpdateModel").modal("hide");
					  //to_page(currentNumPage);
					  to_page(currentNumPage);
					  console.log(result);
				  }
				  
			   });
		});
		
		//点击单个删除
		$(document).on("click",".delete-btn",function () {
			//弹出是否确认删除对话框
			var empName=$(this).parents("tr").find("td:eq(2)").text();
			//alert($(this).parents("tr").find("td:eq(2)").text());
			var empId=$(this).attr("delete-id");
			if (confirm("确认删除【"+empName+"】吗？")) {
				$.ajax({
					url:"${APP_PATH}/emp/"+empId,
					type:"DELETE",
					success: function(result) {
						//弹出删除是否成功信息
						alert(result.msg);
						//回到本页
						to_page(currentNumPage);
					}
				});
			}
		});
		
		//全选中  全不选
		$("#check_all").click(function () {
			//attr获取checked是undefined
			//dom原生属性，attr获取自定义属性值
			//alert($(this).attr("checked"));
			//prop修改和读取dom原生属性值
			//alert($(this).prop("checked"));
			$(".check_item").prop("checked",$(this).prop("checked"));
		});
		
		$(document).on("click",".check_item",function(){
			//判断当前选中元素个数是否为当前页条数
			var flag=$(".check_item:checked").length==$(".check_item").length;
			$("#check_all").prop("checked",flag);
		});
		
		//点击全部删除，就批量删除
		$("#emp_delete_all_btn").click(function () {
			var empNames="";
			var del_idstr="";
			$.each($(".check_item:checked"),function(){
				 
				empNames+=$(this).parents("tr").find("td:eq(2)").text()+",";
				del_idstr+=$(this).parents("tr").find("td:eq(1)").text()+"-";
			});
			//去掉empNames最后一个逗号
			empNames=empNames.substring(0,empNames.length-1);
			//去掉ids最后一个-
			del_idstr=del_idstr.substring(0,del_idstr.length-1);
			if (empNames.length>0) {
				if (confirm("确认删除【"+empNames+"】?吗")) {
					//发送ajax请求删除员工
					$.ajax({
						url:"${APP_PATH}/emp/"+del_idstr,
						type:"DELETE",
						success: function (result) {
							alert(result.msg);
							//返回当前页面
							to_page(currentNumPage);
							$("#check_all").attr('checked',false);  //移除 checked 状态 
						}
					});
				}
			}else{
				alert("请勾选要删除的数据");
			}
		});
		
	</script>
		 
		
</body>
</html>