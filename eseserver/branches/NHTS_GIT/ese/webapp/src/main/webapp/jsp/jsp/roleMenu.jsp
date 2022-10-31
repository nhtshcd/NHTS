<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<style type="text/css">
.alignTopLeft {
	float: left;
	width: 6em;
}

select {
	width: 240px !important;
}
</style>


<!-- add this meta information to select layout  -->
<META name="decorator" content="swithlayout">
</head>
<s:head />
<div id="">
	<s:form name="roleform">
		<s:hidden key="id" id="id" />


<div class="appContentWrapper marginBottom">
		<div class="formContainerWrapper">
			<h2>
				<s:text name="info.roleMenu" />
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
					<label class="alignTopLeft"> <s:text name="Role" />
					</label>

					<%-- <div class="form-element">
									<s:property value="roleName" />
							</div> --%>
					<div class="form-element">
						<s:select name="selectedRole" list="roles" listKey="id"
							listValue="name" theme="simple" cssStyle="display:none"
							cssClass="form-control input-sm select2" />
					</div>
				</div>

				<div class="flexi flexi10">
					<label class="alignTopLeft"> <s:text name="parentMenu" />
					</label>
					<div class="form-element">
						<s:select name="selectedParentMenu" list="parentMenus"
							theme="simple" onchange="listview()"
							cssClass="form-control input-sm select2" />
					</div>
				</div>

			</div>


			<div class="flexiWrapper" style="margin-top: 10px;">
				<s:text name="avilabelmenu" var="availableTitle" />
				<s:text name="selectedmenu" var="selectedTitle" />
				<div class="row col-sm-12">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<s:text name="submenu" />
							</div>
							<div class="panel-body" style="margin-left: 15%" id="dynOpt">
								<s:text name="RemoveAll" var="rmvall" />
								<s:text name="Remove" var="rmv" />
								<s:text name="Add" var="add" />
								<s:text name="AddAll" var="addall" />
								<s:optiontransferselect cssClass="form-control"
									cssStyle="width:300px;height:400px;overflow-x:auto;"
									doubleCssStyle="width:300px;height:400px;overflow-x:auto;"
									doubleCssClass="form-control" buttonCssClass="optTrasel"
									allowSelectAll="false" allowUpDownOnLeft="false"
									labelposition="top" allowUpDownOnRight="false" name="available"
									list="availableMenu" listKey="id" leftTitle="%{availableTitle}"
									rightTitle="%{selectedTitle}" listValue="name"
									headerKey="headerKey" doubleName="selected" doubleId="select"
									doubleList="selectedMenu" doubleListKey="id"
									doubleListValue="name" doubleHeaderKey="doubleHeaderKey"
									addAllToLeftLabel="%{rmvall}" addAllToRightLabel="%{addall}"
									addToLeftLabel="%{rmv}" addToRightLabel="%{add}" />

							</div>
							<div style="padding-left:20px; ">
								<span id="button" class=""><span class="first-child">
										<button type="button" class="save-btn btn btn-success">
											<FONT color="#FFFFFF"> <b><s:text
														name="save.button" /></b>
											</FONT>
										</button>
								</span></span><span id="cancel" class=""><span class="first-child"><a
										href="roleMenu_list.action" class="cancel-btn btn btn-sts">
											<FONT color="#FFFFFF"> <s:text name="cancel" />
										</FONT>
									</a></span></span>
							</div>

						</div>

					</div>

				</div>


			</div>


		</div>
</div>

	</s:form>
</div>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#dynOpt")
								.find(
										"input[type='button'][value='<s:text name="RemoveAll"/>']")
								.addClass("btn btn-warning");

						$("#dynOpt")
								.find(
										"input[type='button'][value='<s:text name="Add"/>']")
								.addClass(
										"btn btn-small btn-success fa fa-step-forward");
						$("#dynOpt")
								.find(
										"input[type='button'][value='<s:text name="Remove"/>']")
								.addClass("btn btn-danger");
						$("#dynOpt")
								.find(
										"input[type='button'][value='<s:text name="AddAll"/>']")
								.addClass("btn btn-sts");
					})

	function listview() {
		document.roleform.action = "roleMenu_update.action";
		document.getElementById("id").value = "";
		document.roleform.submit();
	}

	$(document)
			.ready(
					function() {
						$('button')
								.on(
										'click',
										function(e) {
											document.getElementById('select').multiple = true; //to enable all option to be selected
											for (var x = 0; x < document
													.getElementById('select').options.length; x++)//count the option amount in selection box
											{
												document
														.getElementById('select').options[x].selected = true;
											}//select all option when u click save button
											document.roleform.action = "roleMenu_update.action";
											document.getElementById("id").value = "1";
											document.roleform.submit();
										});
					});
</script>
