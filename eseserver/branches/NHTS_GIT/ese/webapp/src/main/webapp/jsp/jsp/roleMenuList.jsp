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

			<div class="error">
				<s:actionerror />
				<s:fielderror />
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.roleMenu" />
				</h2>

				<div class="flexiWrapper">
					<s:if test='branchId==null'>
						<div class="flexi flexi10">
							<label for="txt"><s:text name="app.branch" /></label>
							<div class="form-element">
								<s:select name="branchId_F" theme="simple" listKey="key"
									listValue="value" list="branchesMap" headerKey="-1"
									headerValue="%{getText('txt.select')}"
									onchange="populateRoles(this)"
									cssClass="form-control input-sm select2" />
							</div>
						</div>
					</s:if>
					<s:else>
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
					</s:else>
					<div class="flexi flexi10">
						<label class="alignTopLeft"> <s:text name="Role" /><sup>
								*</sup>
						</label>

						<div class="form-element">
							<s:select id="selectedRole" name="selectedRole" list="roles"
								listKey="id" listValue="name" theme="simple"
								onchange="listview()" headerKey="-1"
								headerValue="%{getText('txt.select')}"
								cssClass="col-sm-4 form-control select2" />
						</div>
					</div>

					<div class="flexi flexi10">
						<label class="alignTopLeft"> <s:text name="parentMenu" />
						</label>
						<div class="form-element">
							<s:select id="selectedParentMenu" name="selectedParentMenu"
								list="parentMenus" headerKey="-1"
								headerValue="%{getText('txt.select')}" theme="simple"
								onchange="listview()" cssClass="form-control input-sm select2" />
						</div>
					</div>

				</div>
				

			</div>
			<div class="appContentWrapper marginBottom">
				<div class="row">
					<div class="col-sm-12">
						<table id="tablediv" class="table table-hover aspect-detail">
						</table>
					</div>
				</div>
				<div id="EditDiv" class="yui-skin-sam">
				<sec:authorize ifAllGranted="profile.role.menu.update">
					<span id="button" class=""><span class="first-child">
							<button id="but2" type="button" class="save-btn btn btn-success">
								<FONT color="#FFFFFF"> <b><s:text name="Edit" /></b>
								</FONT>
							</button>
					</span></span>
				</sec:authorize>
			</div>
			</div>
			

</s:form>
<br>
<br>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						listview();
						$('button')
								.on(
										'click',
										function(e) {
											var selectedRoleValue = document
													.getElementById('selectedRole');
											var strUser = selectedRoleValue.options[selectedRoleValue.selectedIndex].text;
											//if(strUser!="Admin")
											{
												document.roleform.action = "roleMenu_update.action";
												document.roleform.submit();
											}
										});
					});

	function listview() {
		var selectedRoleValue = document.getElementById('selectedRole');
		var strUser = selectedRoleValue.options[selectedRoleValue.selectedIndex].text;

		$
				.post(
						"roleMenu_data",
						{
							selectedRole : $("#selectedRole").val(),
							selectedParentMenu : $("#selectedParentMenu").val()
						},
						function(data) {
							var result = data;
							var arry = populateValues(result);

							$("#tablediv tbody tr").each(function() {
								this.parentNode.removeChild(this);
							});

							var tab = document.getElementById('tablediv');
							var tbo = document.createElement('tbody');
							var headerrow = document.createElement('tr');
							var header = document.createElement('th');
							header
									.appendChild(document
											.createTextNode('<s:text name="submenu" />'));
							headerrow.appendChild(header);
							tbo.appendChild(headerrow);
							var row, cell;
							for (var i = 0; i < arry.length; i++) {
								row = document.createElement('tr')
								$(row).addClass('odd');
								cell = document.createElement('td');
								cell
										.appendChild(document
												.createTextNode(arry[i] == "" ? '<s:text name="records.empty" />'
														: arry[i]))
								row.appendChild(cell);
								tbo.appendChild(row);
							}

							tab.appendChild(tbo);
						});

		if (strUser != "Admin") {
			removeCls();
		} else {
			chngeCls();
		}
	}

	function chngeCls() {
		//alert("3");
		//document.getElementById('button-button').className += 'disableBtn';
		$("#button-button").addClass("disableBtn");
		$("#button-button > font").css("color", "#acacac!important");
	}

	function removeCls() {
		//alert("3");
		//document.getElementById('button-button').className += 'disableBtn';
		$("#button-button").removeClass("disableBtn");
		$("#button-button > font").css("color", "#fff!important");
	}

	function populateRoles(obj) {
		var branchId = $(obj).val();
		reloadSelect('#selectedRole', 'user_populateRoles.action?branchId_F='
				+ branchId);
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
