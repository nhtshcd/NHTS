<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/ese-tags" prefix="e"%>
<head>
<s:if test="#session.user!=null">
	<META name="decorator" content="eselayout">
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
					<img src="img/expired.png" />
				</div>
				<div class="errStatusType">
					<h4>Your session has expired / Someone has Logged into your
						Account.!</h4>
					<p>Please return to login page and proceed again!... We regret
						the inconvenience.</p>
				</div>
				<div class="errbtn text-center">
					<a href="login_execute" id="btnHome" class="btn btn-sts"
						title="Home"><i class="fa fa-home"></i></a>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			if (typeof (Storage) !== "undefined") {
				if (localStorage.currentTenantId
						&& localStorage.currentTenantId != '') {
					$('#btnHome').attr('href',
							'login_execute_' + localStorage.currentTenantId);
				}
				else{
					$('#btnHome').attr('href',
							'login_execute');
				}
				
			}
		});
	</script>
</body>