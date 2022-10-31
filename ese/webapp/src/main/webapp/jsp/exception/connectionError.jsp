<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:if test="#session.user!=null">
	<META name="decorator" content="swithlayout">
</s:if>
<s:else>
	<META name="decorator" content="loginlayout">
</s:else>
</head>
<body>
	<h1 style="text-align: center">
		<b>Data Base Connection Error</b>
	</h1>
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
					<h4>Data Base Connection Error</h4>
				</div>
			</div>
		</div>
	</div>
</body>
</html>