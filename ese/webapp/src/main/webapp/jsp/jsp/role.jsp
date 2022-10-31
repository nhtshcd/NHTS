<%@ include file="/jsp/common/form-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<META name="decorator" content="swithlayout">
</head>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('button').on('click', function(e) {
			<s:if test="command =='create'">
			document.roleform.action = "role_create.action";
			</s:if>
			<s:else>
			document.roleform.action = "role_update.action";
			</s:else>
			document.roleform.submit();
		});
	});

	function populateSubBranch(obj) {
		var branchId = $(obj).val();
		jQuery.post("user_populateSubBranches.action", {
			branchId_F : branchId
		}, function(result) {
			insertOptions("subBranchSelect", $.parseJSON(result));
		});

	}

	function populateRoleMulti(obj) {
		var branchId = $(obj).val();
		if (isEmpty(branchId)) {
			branchId = $("#branchSelect").val();
		}
		reloadSelect('#selectRole', 'user_populateRoles.action?branchId_F='
				+ branchId);
	}
</script>

<div id="rolcr">
	<s:form name="roleform" action="role" cssClass="fillform">
		<s:hidden key="id" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden key="role.id" />
		</s:if>
		<s:hidden key="command" />
		<s:hidden name="role.filter.id" value="1" />
				<div class="appContentWrapper marginBottom">
			<div class="error">
				<s:actionerror />
				<s:fielderror />


				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.role" />
				</h2>
				<div class="flexiWrapper">
					<s:if test='isMultiBranch==1'>
				
				
							<s:if test='branchId==null'>
								<s:if test='"create".equalsIgnoreCase(command)'>
									<s:label theme="simple">
										<s:text name="app.branch" />
									</s:label>
									<div class="form-element">
										<s:select name="branchId_F" id="branchSelect" theme="simple"
											listKey="key" listValue="value" list="parentBranches"
											headerKey="" headerValue="%{getText('txt.select')}"
											onchange="populateSubBranch(this)"
											cssClass="form-control input-sm select2" />
									</div>
								</s:if>
								<s:if test='"update".equalsIgnoreCase(command)'>
									<div class="flexi flexi10">
										<label for="txt"><s:text name="app.subBranch" /></label>
										<div class="form-element">
											<s:property value="%{getBranchName(role.parentBranchId)}" />
										</div>
									</div>
								</s:if>
							</s:if>

						
						
					
					<%-- <s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
						<div class="flexi flexi10">
						<s:if test='"create".equalsIgnoreCase(command)'>
							<label for="txt"><s:text name="app.subBranch" /></label>
							<div class="form-element">
								<s:select name="subBranchId_F" id="subBranchSelect" theme="simple"
											listKey="key" listValue="value" list="subBranchesMap"
											headerKey="" headerValue="%{getText('txt.select')}"
											onchange="populateRoleMulti(this)"
											cssClass="form-control input-sm select2" />
							</div>
						</s:if>	
						  <s:if test='"update".equalsIgnoreCase(command)'>
								<div class="flexi flexi10">
									<label for="txt">	<s:text name="app.subBranch" /></label>
									<div class="form-element">
											<s:property value="%{getBranchName(role.branchId)}" /><s:hidden name="role.branchId" />
									</div>
								</div>
							</s:if>
						</div>
					</s:if> --%>
					<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
						<div class="flexi flexi10">
							<s:if test='"create".equalsIgnoreCase(command)'>
							<label for="txt"><s:text name="app.subBranch" /></label>
							<div class="form-element">
								<s:select name="subBranchId_F" id="subBranchSelect" theme="simple"
											listKey="key" listValue="value" list="subBranchesMap"
											headerKey="" headerValue="%{getText('txt.select')}"
											onchange="populateRoleMulti(this)"
											cssClass="form-control input-sm select2" />
							</div>
							</s:if>
							 <s:if test='"update".equalsIgnoreCase(command)'>
							 	<div class="flexi flexi10">
										<label for="txt">	<s:text name="app.subBranch" /></label>
										<div class="form-element">
										<s:property value="%{getBranchName(role.branchId)}" /><s:hidden name="role.branchId" />
										</div>
									</div>
							 </s:if>
						</div>
					</s:if>
				
				</s:if>
				<s:else>
					<s:if test='branchId==null'>
						<s:if test='"create".equalsIgnoreCase(command)'>
						
							<div class="flexi flexi10">
									<label for="txt"><s:text name="app.branch" /><sup> *</sup></label>
									<div class="form-element">
										<s:select name="branchId_F" theme="simple" listKey="key"
										listValue="value" list="branchesMap" headerKey="-1"
										headerValue="%{getText('txt.select')}"
										cssClass="form-control input-sm select2" />
									</div>
								</div>
						</s:if>
						<s:if test='"update".equalsIgnoreCase(command)'>
							<s:hidden name="branchId_F" value="%{role.branchId}" />
							<div class="flexi flexi10">
									<label for="txt">	<s:text name="app.branch" /></label>
									<div class="form-element">
										<s:property value="%{getBranchName(role.branchId)}" />
									</div>
								</div>
						</s:if>
					</s:if>
					
				</s:else>
				<div class="flexi flexi10">
										<label for="txt"><s:text name="roleName" /><sup> *</sup></label>
										<div class="form-element"><s:textfield size="23" maxlength="45" name="role.name"
							theme="simple" cssClass="form-control input-sm" />
										</div>
									</div>
									
											<div class="flexi flexi10">
										<label for="txt"><s:text name="isAdmin" /></label>
										<div class="form-element"><s:checkbox name="role.isAdminUser" theme="simple" />
										</div>
									</div>
									</div>
									
										<div>
		<s:if test="command =='create'">
			<span id="button" class=""><span class="first-child">
					<button type="button" class="save-btn btn btn-success">
						<FONT color="#FFFFFF"> <b><s:text name="save.button" /></b>
						</FONT>
					</button>
			</span></span>
			<span class=""><span class="first-child"><a
					id="cancel.link" href="role_list.action"
					class="cancel-btn btn btn-sts"> <FONT color="#FFFFFF"> <s:text
								name="cancel.button" />
					</FONT>
				</a></span></span>
		</s:if>
		<s:else>
			<span id="button" class=""><span class="first-child">
					<button type="button" class="save-btn btn btn-success">
						<FONT color="#FFFFFF"> <b><s:text name="update.button" /></b>
						</FONT>
					</button>
			</span></span>
			<span class=""><span class="first-child"><a
					id="cancel.link" href="role_list.action"
					class="cancel-btn btn btn-sts"> <FONT color="#FFFFFF"> <s:text
								name="cancel.button" />
					</FONT>
				</a></span></span>
		</s:else>
	</div>
									
					
				</div>
			</div>
			</s:form>
		</div>
			
		
		
	

<script type="text/javascript">
	$(document).ready(function() {
		$('#update').on('click', function(e) {
			document.roleform.action = "role_update.action";

			document.roleform.submit();
		});

	});
</script>