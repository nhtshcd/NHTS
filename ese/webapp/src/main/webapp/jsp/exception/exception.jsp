<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Checks whether user is logged in, If user logged in then will choose swithlayout else choose loginlayout -->
<s:if test="#session.user!=null">
	<META name="decorator" content="swithlayout">
</s:if>
<s:else>
	<META name="decorator" content="loginlayout">
</s:else>
</head>
<body>
	<div class="error"></div>
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
					<h4>An Error has occurred!</h4>
					<p>
						<s:fielderror />
						<s:actionerror />
					</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>