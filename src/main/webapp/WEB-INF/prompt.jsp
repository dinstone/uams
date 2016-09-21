<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>授权成功</title>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.15.1/jquery.validate.min.js"></script>
<link type="text/css" href="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript">
	function redirect(url) {
		window.location.href = url;
	}
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
						<h3 class="panel-title">授权成功</h3>
					</div>
					<div class="panel-body">
						<div class="alert alert-info alert-dismissable">请确认授权以下信息：获取你的公开信息（昵称，头像等）</div>
						<c:if test="${not empty scu && 'true' eq warn}">
							<button type="button" class="btn btn-default btn-success btn-block" onclick="redirect('${scu}')">按钮</button>
						</c:if>
					</div>
				</div>
			</div>
			<div class="col-md-4 column"></div>
		</div>
	</div>
	<c:if test="${not empty scu && 'true' ne warn}">
		<script type="text/javascript">
			setTimeout("redirect('${scu}')", 1000);
		</script>
	</c:if>
</body>
</html>