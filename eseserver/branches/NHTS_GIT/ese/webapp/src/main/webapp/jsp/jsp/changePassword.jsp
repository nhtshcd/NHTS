<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
	<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css"> -->
	
<html>
	<head>
	<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType")
					: "swithlayout"%>">
			<style>
			.errorMessage {
				align: left;
				margin: 0 auto;
				padding: 0 auto;
				color: red;
				list-style: none;
			}
		</style>
		
	</head>
	<body>

			<div class="error">
				<div id="validateError" style="text-align: left; font-color: red"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<s:form id="form" name="form" cssClass="form" action="changePassword_update.action" method="post" >
				<s:hidden name="action" value="update"/>
				<s:hidden name="user.username" value="%{username}"/>
				<table class="table table-bordered aspect-detail">
		
					<tr class="odd">
						<td width="35%"><s:text name="changePassword.user.oldPassword"/><sup
							style="color: red;">*</sup></td>
						<td width="65%"><s:password name="user.oldPassword" theme="simple"  maxlength="45" id="oldPassword"/>
						 <i class="fa fa-eye" id="togglePassword" style="margin-left: -30px; cursor: pointer;" onclick="showOldPassword(this)"></i>
					    </td>
					</tr>
		
					<tr class="odd">
						<td width="35%"><s:text name="changePassword.user.password"/><sup
							style="color: red;">*</sup></td>
						<td width="65%"><s:password name="user.password" theme="simple"  maxlength="45" id="newPassword"/>
						<i class="fa fa-eye" id="togglePassword" style="margin-left: -30px; cursor: pointer;" onclick="showNewPassword(this)"></i>
					   
					</tr>
					
					<tr class="odd">
						<td width="35%"><s:text name="changePassword.user.confirmPassword"/><sup
							style="color: red;">*</sup></td>
						<td width="65%"><s:password name="user.confirmPassword" theme="simple"  maxlength="45" id="confirmPassword"/>
						<i class="fa fa-eye" id="togglePassword" style="margin-left: -30px; cursor: pointer;" onclick="showConfirmPassword(this)"></i>
					 
					</tr>
		
				</table>
				
					<br />

				<div class="yui-skin-sam">
					<span id="button" class=""><span class="first-child">
							<button  type="button" class="save-btn btn btn-success">
								<font color="#FFFFFF"> <b>Save</b>
								</font>
							</button>
					</span></span> <span id="cancel" class=""><span
						class="first-child"><button type="button" class="cancel-btn btn btn-sts">
								<b><FONT color="#FFFFFF">Cancel
								</font></b>
							</button></span></span>
				</div>

	</s:form>
	
		<s:form id="cancelForm" action="home_list">
		</s:form>
		<script>
			jQuery(document).ready(function(){
				jQuery(".save-btn").click(function(){
					var oldPassword=jQuery("#oldPassword").val();
					var newPassword=jQuery("#newPassword").val();
					var confirmPassword=jQuery("#confirmPassword").val();
					jQuery("#validateError").text('');
					$("#validateError").empty();
					if(oldPassword==null||oldPassword.trim()==''){
						jQuery("#validateError").text('<s:text name="empty.oldPassword"/>');
					}
					else if(newPassword==null||newPassword.trim()==''){
						jQuery("#validateError").text('<s:text name="empty.newPassword"/>');
					}
					else if(confirmPassword==null||confirmPassword.trim()==''){
						jQuery("#validateError").text('<s:text name="empty.confirmPassword"/>');
					}
					else 
						if(!validateAndSubmit("form",'changePassword_')){
							$(".save-btn").prop('disabled', false);
						}
					
				});	
				
				jQuery(".cancel-btn").click(function(){
					document.getElementById('cancelForm').submit();
				});	
			});
			
			function showOldPassword(obj) {
				var faEye="fa-eye"
				var faEyeSlash="fa-eye-slash"
				var x = document.getElementById("oldPassword");
				if (x.type === "password") {
					$(obj).removeClass(faEye);
					$(obj).addClass(faEyeSlash);
					x.type = "text";
					
				} else {
					 $(obj).addClass(faEye);
					  $(obj).removeClass(faEyeSlash);
					x.type = "password";
				}
			}
			function showNewPassword(obj) {
				var faEye="fa-eye"
				var faEyeSlash="fa-eye-slash"
				var x = document.getElementById("newPassword");
				if (x.type === "password") {
					$(obj).removeClass(faEye);
					$(obj).addClass(faEyeSlash);
					x.type = "text";
					
				} else {
					 $(obj).addClass(faEye);
					  $(obj).removeClass(faEyeSlash);
					x.type = "password";
				}
			}
				function showConfirmPassword(obj) {
					var faEye="fa-eye"
					var faEyeSlash="fa-eye-slash"
					var x = document.getElementById("confirmPassword");
					if (x.type === "password") {
						
						$(obj).removeClass(faEye);
						$(obj).addClass(faEyeSlash);
						x.type = "text";
						
					} else {
						 $(obj).addClass(faEye);
						  $(obj).removeClass(faEyeSlash);
						x.type = "password";
					}
				}
		</script>
	</body>
</html>
