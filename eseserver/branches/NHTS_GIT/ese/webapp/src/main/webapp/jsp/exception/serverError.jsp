<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/ese-tags" prefix="e"%>
<head>
<s:if test="#session.user!=null">
	<META name="decorator" content="swithlayout">
</s:if>
<s:else>
	<META name="decorator" content="loginlayout">
</s:else>
</head>
<body>
<style>
.errStatusIcon, .errStatusType, .errStatusDesc {
	text-align: center;
}
</style>
	<div class="row">
		<div class="container">
			<div class="col-md-12">
				<div class="errStatusIcon">
					<img src="img/error.png" />
				</div>
				<div class="errStatusType">
					<h4>Server Error!!!</h4>
				</div>
			</div>
		</div>
	</div>
</body>