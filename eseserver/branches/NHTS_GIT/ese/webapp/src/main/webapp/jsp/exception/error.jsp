<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/ese-tags" prefix="e"%>
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
		<div class="container">
			<div class="col-md-12">
				<div class="errStatusIcon">
					<img src="img/error.png" />
				</div>
				<div class="errStatusType">
					<h4>An Error has occurred!</h4>
					<p><s:text name="errormessage.access" /></p>
				</div>
			</div>
		</div>
	</div>
</body>