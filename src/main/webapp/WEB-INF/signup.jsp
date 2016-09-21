<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.validate.min.js"></script>
<link type="text/css" href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript">
		$(function(){
			
			// 匹配密码，以字母开头，长度在6-12之间，必须包含数字和特殊字符。
			jQuery.validator.addMethod("isPwd", function(value, element) {
				var str = value;
				if (str.length < 6 || str.length > 12)
					return false;
				if (!/^[a-zA-Z]/.test(str))
					return false;
				if (!/[0-9]/.test(str))
					return false;
				return this.optional(element) || /[^A-Za-z0-9]/.test(str);
			}, "以字母开头，长度在6-12之间，必须包含数字和特殊字符。");

			$("#register-form").validate({
				errorElement : 'span',
				errorClass : 'help-block',
				rules : {
					username : "required",
					password : {
						required : true,
						isPwd : true
					},				
					nickname : {
						required : true,
						maxlength : 20
					},	
					email : {
						required : true,
						email : true
					}
				},
				messages : {
					username : "请输入账号",
					password : {
						required : "请输入密码",
						minlength : "密码不能小于6个字符"
					},
					nickname : {
						required : "请输入昵称",
						maxlength : "昵称不能大于20个字符"
					},
					email : {
						required : "请输入Email地址",
						email : "请输入正确的Email地址"
					}
				},
				errorPlacement : function(error, element) {
					element.next().remove();
					element.after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
					element.closest('.form-group').append(error);
				},
				highlight : function(element) {
					$(element).closest('.form-group').addClass('has-error has-feedback');
				},
				success : function(label) {
					var el=label.closest('.form-group').find("input");
					el.next().remove();
					el.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
					label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");
					label.remove();
				},
				submitHandler: function(form) { 
					form.submit();
				}

			});
		});
	</script>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<h3 class="text-center"></h3>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-4 column"></div>
			<div class="col-md-4 column">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">注册账号</h3>
					</div>
					<div class="panel-body">
						<form role="form" id="register-form" action="${contextPath}/manage/signup/submit.do" method="post">
							<input type="hidden" class="form-control" name="scu" value="${scu}"><input type="hidden" class="form-control" name="warn" value="${warn}">
							<div class="form-group">
								<label>账号</label><input type="text" class="form-control" name="username" placeholder="邮箱/手机号" />
							</div>
							<div class="form-group">
								<label>密码</label><input type="password" class="form-control" name="password" placeholder="账号密码" />
							</div>
							<div class="form-group">
								<label>昵称</label><input type="text" class="form-control" name="nickname" />
							</div>
							<div class="form-group">
								<label>邮箱</label><input type="email" class="form-control" name="email" placeholder="常用邮箱" />
								<p class="help-block">邮箱用来激活账号和找回密码，请正确填写。</p>
							</div>
							<p class="form-group">
								<span class="glyphicon glyphicon-warning-sign" style="color: red;"></span> 注册账号表示您同意服务条款和隐私政策。
							</p>
							<div class="form-group">
								<button type="submit" class="btn btn-primary btn-block">注册</button>
							</div>
							<div class="row">
								<p class="col-xs-6 col-md-6 text-center">
									<a href="${contextPath}/authen/login.do?warn=${warn}&scu=${scu}">已有账号直接登录</a>
								</p>
								<p class="col-xs-6 col-md-6 text-center">
									<a href="#">忘记密码找回账号</a>
								</p>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="col-md-4 column"></div>
		</div>
	</div>
</body>
</html>
