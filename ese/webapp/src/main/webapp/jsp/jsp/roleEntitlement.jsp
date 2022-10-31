<%@ include file="/jsp/common/form-assets.jsp"%>
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
	<s:hidden key="id" id="id" />
	<s:select name="selectedRole" list="roles" listKey="id"
		listValue="name" theme="simple" cssStyle="display:none" />

	
		<div class="appContentWrapper marginBottom">

			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.roleMenuEnt" />
				</h2>

				<div class="flexiWrapper">
					<s:if test='branchId==null'>
						<div class="flexi flexi10">
							<label for="txt"><s:text name="app.branch" /></label>
							<div class="form-element">
								<s:property value="%{getBranchName(branchId_F)}" />
							</div>
						</div>
					</s:if>

					<div class="flexi flexi10">
						<label for="txt"><s:text name="Role" /></label>
						<div class="form-element">
							<s:property value="roleName" />
						</div>
					</div>
					<div class="flexi flexi10">
						<label for="txt"> <s:text name="parentMenu" /></label>
						<div class="form-element">
							<s:select name="selectedParentMenu" list="parentMenus"
								theme="simple" onchange="listview()"
								cssClass="form-control input-sm select2" />
						</div>
					</div>
				</div>
			</div>
			<div class="formContainerWrapper">
				<div class="panel panel-default">
					<table Class="fillform table table-bordered aspect-detail">
						<thead>
							<tr>
								<th colspan="<s:property value="actionMap.size()+1"/>">
									<div style="float: left">
										<s:text name="roleentitlements" />
									</div>
									<div style="float: right">
										<s:text name="select" />
										&nbsp;<a href="javascript:void(0)"
											onclick="javascript:Select(true)"><s:text name="all" /></a>
										&nbsp;| <a href="javascript:void(0)"
											onclick="javascript:Select(false)"><s:text name="none" /></a>
									</div>
								</th>
							</tr>
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
									<td style="min-width: 200px;"><label> <s:property
												value="key" />
									</label></td>
									<s:iterator status="stat1" value="value">
										<td align="center"
											style="text-align: center; padding-left: 0 !important; padding-right: 0 !important; min-width: 50px;">
											<s:if test='value!=null && !("").equalsIgnoreCase(value)'>
												<s:if
													test='entitlements!=null && entitlements.size()>0 && entitlements.contains(value)'>
													<s:if
														test='"list".equalsIgnoreCase(value.substring(value.lastIndexOf(".")+1))'>
														<s:checkbox name="entitlements" fieldValue="%{value}"
															value="true" disabled="true" />
														<s:hidden name="entitlements" value="%{value}" />
													</s:if>
													<s:else>
														<s:checkbox name="entitlements" fieldValue="%{value}"
															value="true" cssClass="ckbox" />
													</s:else>
												</s:if>
												<s:else>
													<s:checkbox name="entitlements" fieldValue="%{value}"
														value="false" cssClass="ckbox" />
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
			<div class="yui-skin-sam">
				<span id="button" class=""><span class="first-child">
						<button type="button" class="save-btn btn btn-success">
							<FONT color="#FFFFFF"> <b><s:text name="Save" /></b>
							</FONT>
						</button>
				</span></span><span id="cancel" class=""><span class="first-child"><a
						href="roleEntitlement_list.action" class="cancel-btn btn btn-sts">
							<FONT color="#FFFFFF"> <s:text name="cancel" />
						</FONT>
					</a></span></span>
			</div>
		</div>
	






</s:form>
<script type="text/javascript">
	$(document).ready(function() {
		$('button').on('click', function(e) {
			document.roleform.action = "roleEntitlement_update.action";
			document.getElementById("id").value = "1";
			document.roleform.submit();
		});
	});

	function listview() {
		document.roleform.action = "roleEntitlement_update.action";
		document.roleform.submit();
	}

	function Select(Select) {
		//alert("chl");
		/*        jQuery(".ckbox").attr("checked",Select); */
		$(".ckbox").prop("checked", Select);
		/*  $(".ckbox").prop("checked", false); */
	}
</script>
