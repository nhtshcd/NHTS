<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META name="decorator" content="swithlayout">
</head>
<body>
	<style>
.errStatusIcon, .errStatusType, .errStatusDesc {
	text-align: center;
}
</style>

	<div class="row">
		<div class="col-md-12">
			<div class="errStatusIcon">
				<img src="img/access-denied.png" />
			</div>
			<div class="errStatusType">
				<h4>Access denied!</h4>
				<p><s:text name="security.accessdenied" /></p>
			</div>
		</div>
	</div>
</body>
</html>