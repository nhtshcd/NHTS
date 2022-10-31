<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>


<head>
<style type="text/css">
.alignTopLeft {
	float: left;
	width: 6em;
}
</style>
<!-- add this meta information to select layout  -->
<META name="decorator" content="swithlayout">
</head>


<s:form name="roleform">
	
	<div class="appContentWrapper marginBottom">
	<div class="error">
		<s:actionerror />
		<s:fielderror />
		<sup>*</sup>
		<s:text name="reqd.field" />
	</div>
		<div class="formContainerWrapper">
			<h2>
				<s:text name="info.roleMenuEnt" />
			</h2>

			<div class="flexiWrapper">
				<s:if test='branchId==null'>
					<div class="flexi flexi10">
						<s:label theme="simple">
							<s:text name="app.branch" />
							<sup> *</sup>
						</s:label>
						<div class="form-element">
							<s:select name="branchId_F" theme="simple" listKey="key"
								listValue="value" list="branchesMap" headerKey="-1"
								headerValue="%{getText('txt.select')}"
								onchange="populateRoles(this)"
								cssClass="form-control input-sm select2" />
						</div>
					</div>
				</s:if>
				<div class="flexi flexi10">
					<label class="alignTopLeft"> <s:text name="Role" /><sup>
							*</sup>
					</label>
					<div class="form-element">
						<s:select id="selectedRole" name="selectedRole" list="roles"
							listKey="id" listValue="name" theme="simple"
							onchange="listview()" headerKey="-1"
							headerValue="%{getText('txt.select')}"
							cssClass="form-control input-sm select2" />
					</div>
				</div>

				<div class="flexi flexi10">
					<label class="alignTopLeft"> <s:text name="parentMenu" /><sup>
							*</sup></label>
					<div class="form-element">
						<s:select name="selectedParentMenu" list="parentMenus"
							theme="simple" onchange="listview()" headerKey="-1"
							headerValue="%{getText('txt.select')}"
							cssClass="form-control input-sm select2" />
					</div>
				</div>
			</div>


		</div>
	</div>
	<div class="appContentWrapper marginBottom">
	<div class="formContainerWrapper" style="margin-top: 20px;">

		<div class="panel panel-default">

			<table
				class="table table-hover table-condonsed table-bordered aspect-detail fillform">
				<thead>

					<tr>
						<th><s:text name="roleMenu" /></th>
						<s:iterator status="stat" value="actionMap">
							<th
								style="text-align: center; padding-left: 0 !important; padding-right: 0 !important;"><s:text
									name="%{key}" /></th>
						</s:iterator>
					</tr>
				</thead>
				<tbody>
					<s:if test="listActionMenusEmpty!=null">
						<tr class="odd">
							<td colspan="<s:property value="actionMap.size()+1"/>"
								style="text-align: center; padding-left: 0 !important; padding-right: 0 !important;"><s:property
									value="listActionMenusEmpty" /></td>
						</tr>
					</s:if>
					<s:iterator status="stat" value="listActionMenus">
						<tr class="odd">
							<td style="min-width: 200px;"><label> 
							<%-- <s:property value="key" /> --%>
							<s:property value="%{getLocaleProperty(key)}" />
							
							</label></td>
							<s:iterator status="stat1" value="value">
								<td align="center"
									style="text-align: center; padding-left: 0 !important; padding-right: 0 !important; min-width: 50px;">
									<s:if test='value!=null && !("").equalsIgnoreCase(value)'>
										<s:if
											test='entitlements!=null && entitlements.size()>0 && entitlements.contains(value)'>
											<s:checkbox name="newEntitlements" fieldValue="%{value}"
												value="true" disabled="true" />
										</s:if>
										<s:else>
											<s:checkbox name="newEntitlements" fieldValue="%{value}"
												disabled="true" />
										</s:else>
									</s:if>
								</td>
							</s:iterator>
						</tr>
					</s:iterator>
				</tbody>
			</table>

		</div>
	</div>
	</div>
	<div id="EditDiv" class="yui-skin-sam">
		<sec:authorize ifAllGranted="profile.role.entitlement.update">
			<s:if test='"1".equalsIgnoreCase(selectedRole)'>
				<span id="button" class=""><span class="first-child">
						<button type="button" disabled="disabled"
							class="disableBtn btn btn-success">
							<FONT color="#FFFFFF"> <b><s:text name="Edit" /></b>
							</FONT>
						</button>
				</span></span>
			</s:if>
			<s:else>

				<span id="button" class=" "><span class="first-child">
						<button type="button" class="edit-btn btn btn-success">
							<FONT color="#FFFFFF"> <b><s:text name="Edit" /></b>
							</FONT>
						</button>
				</span></span>
			</s:else>
		</sec:authorize>
	</div>
</s:form>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('button')
								.on(
										'click',
										function(e) {
											var selectedRoleValue = document
													.getElementById('selectedRole');
											var strUser = selectedRoleValue.options[selectedRoleValue.selectedIndex].text;
											if (strUser != "Admin") {
												document.roleform.action = "roleEntitlement_update.action";
												document.roleform.submit();
											}
										});
					});

	function listview() {
		document.roleform.action = "roleEntitlement_list.action";
		document.roleform.submit();
	}

	function populateRoles(obj) {
		var branchId = $(obj).val();
		reloadSelect('#selectedRole', 'user_populateRoles.action?branchId_F='
				+ branchId);
	}
</script>
