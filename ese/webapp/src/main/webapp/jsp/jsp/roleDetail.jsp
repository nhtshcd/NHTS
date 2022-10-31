<%@ include file="/jsp/common/form-assets.jsp"%>
<head>
<META name="decorator" content="swithlayout">
</head>
<div class="error" style="margin-bottom: 0px;">
	<s:fielderror />
</div>
<div id="warn" class="error" style="margin-bottom: 0px;">
	<s:actionerror />
</div>
<div id="rolde">
	<s:form name="roleform" action="role_update" cssClass="fillform">
	<div class="flex-view-layout">
	<div class="fullwidth">
		<div class="flexWrapper">
			<div class="flexLeft appContentWrapper">
				<div class="formContainerWrapper dynamic-form-con">
					<h2><s:text name="info.role" /></h2>
					 <s:if test='branchId==null'>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="app.branch" /></p>
						<p class="flexItem"><s:property
							value="%{getBranchName(role.branchId)}" /></p>
					</div>
				</s:if>	
					<div class="dynamic-flexItem">
						<p class="flexItem">	<s:text name="roleName" /></p>
						<p class="flexItem"><s:property value="role.name" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="isAdmin" /></p>
						<p class="flexItem"><s:if test="role.isAdminUser == 'true'">YES</s:if><s:else>
    					NO
				</s:else> </p>
					</div>

				</div>
				
				<div class="flexItem flex-layout flexItemStyle">
					<div class="button-group-container">
					<s:if test='"Admin".equalsIgnoreCase(role.name)'>
							<a id="update" class="btn btn-success" class="disableBtn"><s:text name="edit.button" /></a>
						</s:if>
						<s:else>
							<a id="update" class="btn btn-success" ><s:text name="edit.button" /></a>
							<a id="delete" class="btn btn-danger"><s:text name="delete.button" /></a>
						</s:else>
						
						<a id="cancel" class="btn btn-sts" href="role_list.action"><s:text name="cancel.button" /></a>
					</div>
				</div>
				<%-- <div class="yui-skin-sam">
						<sec:authorize ifAllGranted="profile.role.update">
				<span id="update" class=""><span class="first-child">
						<s:if test='"Admin".equalsIgnoreCase(role.name)'>
							<button type="button" disabled="disabled" class="disableBtn">
								<FONT color="#FFFFFF"> <b><s:text name="edit.button" /></b>
								</FONT>
							</button>
						</s:if> <s:else>
							<button type="button" class="edit-btn btn btn-success">
								<FONT color="#FFFFFF"> <b><s:text name="edit.button" /></b>
								</FONT>
							</button>
						</s:else>

				</span></span>
			</sec:authorize>
			<sec:authorize ifAllGranted="profile.role.delete">
				<span id="delete" class=""><span class="first-child">
						<s:if test='"Admin".equalsIgnoreCase(role.name)'>
							<button type="button" disabled="disabled" class="disableBtn">
								<FONT color="#FFFFFF"> <b><s:text
											name="delete.button" /></b>
								</font>
							</button>
						</s:if> <s:else>
							<button type="button" class="delete-btn btn btn-warning">
								<FONT color="#FFFFFF"> <b><s:text
											name="delete.button" /></b>
								</font>
							</button>
						</s:else>
				</span></span>
			</sec:authorize>
			<span id="cancel" class=""><span class="first-child"><a
					href="role_list.action" class="back-btn btn btn-sts"> <FONT
						color="#FFFFFF"> <s:text name="back.button" />
					</FONT>
				</a></span></span>
				</div> --%>
		</div>
	</div>
</div>
</div>
	</s:form>
</div>
<s:form name="deleteform" action="role_delete.action">
	<s:hidden key="id" />
</s:form>
<s:form name="updateform" action="role_update.action">
	<s:hidden key="id" />
</s:form>
<script type="text/javascript">

$(document).ready(function () {
    $('#update').on('click', function (e) {
    	 document.updateform.action = "role_update.action";
    	
         document.updateform.submit();
    });

    $('#delete').on('click', function (e) {
    	 if(confirm( '<s:text name="confirm.delete"/> ')){
    		 
 			document.deleteform.submit();
 		}
    });
});


</script>