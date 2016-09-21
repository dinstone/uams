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
<title>>登录系统</title>
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.validate.min.js"></script>
<link type="text/css" href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript">
	$(function() {

		$("#signin-form").validate({
			errorElement : 'span',
			errorClass : 'help-block',
			rules : {
				username : "required",
				password : "required"
			},
			messages : {
				username : "请输入账号",
				password : "请输入密码"
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
				var el = label.closest('.form-group').find("input");
				el.next().remove();
				el.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
				label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");
				label.remove();
			},
			submitHandler : function(form) {
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
						<h3 class="panel-title text-center">登录系统</h3>
					</div>
					<div class="panel-body">
						<c:if test="${not empty msg}">
							<div class="alert alert-danger alert-dismissable">
								<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
								${msg}
							</div>
						</c:if>
						<form role="form" id="signin-form" action="${contextPath}/authen/signin.do" method="post">
							<input type="hidden" class="form-control" name="scu" value="${scu}"><input type="hidden" class="form-control" name="warn" value="${warn}">
							<div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span><input type="text" class="form-control" name="username" placeholder="账号" style="border-top-right-radius: 4px; border-bottom-right-radius: 4px;">
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span><input type="password" class="form-control" name="password" placeholder="密码" style="border-top-right-radius: 4px; border-bottom-right-radius: 4px;">
								</div>
							</div>
							<div class="form-group">
								<label><input type="checkbox" /> 记住我</label>
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-primary btn-block">登录</button>
							</div>
							<div class="row">
								<p class="col-xs-6 col-md-6 text-center">
									<a href="#"> 忘记密码 </a>
								</p>
								<p class="col-xs-6 col-md-6 text-center">
									<a href="${contextPath}/manage/signup/init.do?warn=${warn}&scu=${scu}"> 注册账号 </a>
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
