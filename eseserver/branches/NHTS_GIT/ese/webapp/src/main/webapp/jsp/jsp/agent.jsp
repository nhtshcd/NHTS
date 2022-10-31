<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css"> -->

<head>
<META name="decorator" content="swithlayout">
<style>
.psss {
	padding-top: 15px;
	padding-bottom: 15px;
}
</style>
</head>
<script>
	var command = '<s:property value="command"/>';
	jQuery(document).ready(function() {

		$(".select2").select2();

		if (command == 'update') {
			$("#selectRole").attr("disabled", true);
			listpassword('agent.changePassword');
		}
		packhouseSelectM('<s:property value="agent.agentType.id" />');
		populatePackhouse('<s:property value="agent.exporter.id" />');
		//$("input[name=agent.status][value='"+'<s:property value="agent.status" />'+"']").prop("checked",true);
	});
	function onCancel() {
		var redir = '<s:property value="redirectContent" />';
		window.location.href = '<s:property value="redirectContent" />';
	}

	function packhouseSelectM(value) {
		if (value == '2' || value == '3') {
			$(".packhouse").removeClass('hide');
		} else {
			$(".packhouse").addClass('hide');
		}
	}
	function populatePackhouse(val) {
		//$('#packhouse').empty().trigger("change");
		$('#packhouse option:not(:first)').remove();
		$('#packhouse').val("").select2();
		if (!isEmpty(val)) {
			$
					.ajax({
						type : "POST",
						async : false,
						url : "fieldStaff_populatePackhouse.action",
						data : {
							selectedAgentType : val
						},
						success : function(result) {
							//alert("res"+result)
							insertOptions("packhouse", $.parseJSON(result));
							if ('<s:property value="agent.packhouse.id" />' != null
									&& '<s:property value="agent.packhouse.id" />' != '') {
								$('#packhouse')
										.val(
												'<s:property value="agent.packhouse.id" />')
										.select2();
							}

						},

					});

		}
	}
</script>

<body>

	<s:form name="target" id="target" method="post"
		enctype="multipart/form-data" cssClass="fillform">
		<s:hidden key="currentPage" />
		<s:hidden key="id" id="id" />
		<s:hidden key="command" />
		<s:hidden key="selecteddropdwon" id="listname" />
		<s:hidden key="temp" id="temp" />
		<s:hidden key="type" id="type" />
		<s:hidden key="agent.id" id="agentId" />
		<s:hidden name="redirectContent" id="redirectContent" />

		<div class="appContentWrapper marginBottom">
			<div class="error">
				<div id="validateError" style="text-align: left; font-color: red"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name='%{"info"+type}' />
				</h2>
				<div class="flexform">
					<s:if test='"update".equalsIgnoreCase(command)'>
						<s:if test='branchId==null'>
							<div class="flexform-item">
								<label for="txt"><s:text name="app.branch" /> </label>
								<div class="form-element">
									<label><s:property
											value="%{getBranchName(agent.branchId)}" /></label>
								</div>
							</div>
						</s:if>
						<div class="flexform-item">
							<label><s:text name='%{"agentId"+type}' /></label>
							<div class="form-element">
								<s:textfield id="agentID" name="agent.profileId" readonly="true"
									cssClass="form-control " />
							</div>
						</div>
					</s:if>
					<s:else>
						<div class="flexform-item">
							<label><s:text name='%{"agentId"+type}' /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="agent.profileId" maxlength="12"
									cssClass="form-control" />
							</div>
						</div>
					</s:else>
					<div class="flexform-item">
						<label><s:text name="personalInfo.firstName" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield id="firstName" name="agent.personalInfo.firstName"
								theme="simple" size="25" cssClass="upercls form-control"
								maxlength="35" />
						</div>
					</div>
					<div class="flexform-item">
						<label><s:text name="personalInfo.lastName" /></label>
						<div class="form-element">
							<s:textfield id="lastName" name="agent.personalInfo.lastName"
								theme="simple" size="25" cssClass="upercls form-control "
								maxlength="35" />
						</div>
					</div>
					<%-- 	<div class="flexform-item ">
						<label><s:text name="personalInfo.identityType" /></label>
						<div class="form-element">
							<s:select id="identityType" headerKey=""
								headerValue="%{getText('txt.select')}"
								name="agent.personalInfo.identityType" list="identityTypeList"
								listKey="key" listValue="value" theme="simple"
								cssClass="form-control  select2" />
						</div>
					</div>
 --%>
					<div class="flexform-item ">
						<label><s:text name="personalInfo.identityNumber" /></label>
						<div class="form-element">
							<s:textfield name="agent.personalInfo.identityNumber"
								theme="simple" size="25" maxlength="16"
								cssClass="form-control  " />
						</div>
					</div>


					<div class="flexform-item">
						<label><s:text name="personalInfo.sex" /></label>
						<div class="form-element">
							<s:radio list="genderStatusMap" listKey="key" listValue="value"
								name="agent.personalInfo.sex" theme="simple" />
						</div>
					</div>

					<div class="flexform-item">
						<label><s:text name="personalInfo.dateOfBirth" /></label>
						<div class="form-element">
							<s:textfield name="dateOfBirth" theme="simple" size="25"
								readonly="true" id="calendar"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								cssClass="date-picker form-control input-sm" />
						</div>
					</div>

					<div class="flexform-item ">
						<label><s:text name="personalInfo.moUSertype" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select id="identityType" headerKey=""
								headerValue="%{getText('txt.select')}" name="agent.agentType.id"
								list="agentType" listKey="key" listValue="value" theme="simple"
								cssClass="form-control  select2"
								onchange="packhouseSelectM(this.value)" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="agent.exporterLs" /> <sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:if test="agent.exporter!=null && agent.exporter.id>0">
								<s:property value="agent.exporter.name" />
								<s:hidden name="agent.exporter.id"></s:hidden>
							</s:if>
							<s:else>
								<s:select name="agent.exporter.id" id="selectRole"
									list="exporterList" listKey="key" listValue="value"
									theme="simple" headerKey=""
									onchange="populatePackhouse(this.value)"
									headerValue="%{getText('txt.select')}"
									cssClass="form-control input-sm select2" />
							</s:else>
						</div>
					</div>

					<div class="flexform-item hide packhouse">
						<label><s:text name="personalInfo.packhouse" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select id="packhouse" headerKey=""
								headerValue="%{getText('txt.select')}" name="agent.packhouse.id"
								list="warehouseMap" listKey="key" listValue="value"
								theme="simple" cssClass="form-control  select2" />
						</div>
					</div>

				</div>
			</div>
		</div>

		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.contact" />
				</h2>
				<div class="flexform">
					<div class="flexform-item">
						<label><s:text name="contactInfo.address" /></label>
						<div class="form-element">
							<s:textarea name="agent.contInfo.address1" theme="simple"
								size="25" maxlength="150" cssClass="form-control "
								cssStyle="height:40px" />
						</div>
					</div>

					<div class="flexform-item">
						<label><s:text name="contactInfo.phoneNumber" /></label>
						<div class="form-element">
							<s:textfield name="agent.contInfo.phoneNumber" theme="simple"
								size="25" maxlength="12" cssClass="form-control "
								onkeypress="return isNumber(event)" />
						</div>
					</div>


					<div class="flexform-item">
						<label><s:text name="agentcontactInfo.mobileNumber" /></label>
						<div class="form-element">
							<s:textfield name="agent.contInfo.mobileNumbere" theme="simple"
								size="25" maxlength="15" cssClass="form-control "
								onkeypress="return isNumber(event)" />
						</div>
					</div>

					<div class="flexform-item">
						<label><s:text name="agentcontactInfo.email" /></label>
						<div class="form-element">
							<s:textfield name="agent.contInfo.email" theme="simple" size="25"
								maxlength="45" cssClass="form-control " />
						</div>
					</div>

				</div>
			</div>
		</div>

		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.credential" />
				</h2>
				<div class="flexform">
					<div class="flexform-item">
						<label><s:text name="agent.status.name" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:radio list="statuses" id="status" listKey="key"
								listValue="value" name="agent.status" theme="simple"
								value="StatusDefaultValue" />
						</div>
					</div>
					<s:if test='"update".equalsIgnoreCase(command)'>
						<div class="flexform-item" id='passwordOptionDiv'>
							<label for="txt"><s:text name="user.changePassword" /></label>
							<div class="form-element">
								<s:checkbox key="agent.changePassword" theme="simple"
									onclick="listpassword(this.name)" />
							</div>
						</div>
					</s:if>
					<s:else>
						<s:hidden name="agent.changePassword" value="true" />
					</s:else>
					<div class="flexform-item" id="passwordDiv">
						<label><s:text name="agent.passwordName" /> <sup
							style="color: red;">*</sup></label>

						<div class="form-element">
							<s:password showPassword="true" name="agent.password"
								title="%{getLocaleProperty('pwValid')}" id="password"
								theme="simple" size="15" value="%{agent.password}"
								cssClass="st form-control pr-form-control"  />
							<i class="fa fa-eye" id="togglePassword" style="margin-left: -30px; cursor: pointer;" onclick="showPassword(this)"></i>
						</div>
					</div>

					<div class="flexform-item" id="confPasswordDiv">
						<label><s:text name="agent.confPasswordName" /> <sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:password showPassword="true" name="agent.confirmPassword"
								id="password1" theme="simple" size="15"
								value="%{agent.confirmPassword}" cssClass="st form-control pr-form-control" />
								<i class="fa fa-eye" id="togglePassword" style="margin-left: -30px; cursor: pointer;" onclick="showPassword1(this)"></i>
						</div>
					</div>


				</div>
			</div>
		</div>
		<div class="yui-skin-sam">
			<s:if test="command =='create'">
				<span id="button" class=""><span class="first-child">
						<button class="btn btn-success" id="buttonAdd1">
							<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
							</font>
						</button>
				</span></span>
			</s:if>
			<s:else>
				<span id="button1233" class=""><span class="first-child">
						<button type="button" id="buttonUpdate"
							class="save-btn btn btn-success">
							<font color="#FFFFFF"> <b><s:text name="update.button" /></b>
							</font>
						</button>
				</span></span>
			</s:else>
			<span id="cancel" class=""><span class="first-child"><button
						type="button" class="cancel-btn btn btn-sts" onclick="onCancel();">
						<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
						</font></b>
					</button></span></span>
		</div>
	</s:form>

	<script type="text/javascript">
		function listpassword(call) {
			var group = document.getElementsByName(call);
			jQuery("#password").val("");
			jQuery("#password1").val("");
			if (group[0].checked) {
				jQuery("#passwordDiv").show();
				jQuery("#confPasswordDiv").show();
			} else {
				jQuery("#passwordDiv").hide();
				jQuery("#confPasswordDiv").hide();

			}

		}

		$('#buttonAdd1,#buttonUpdate').on('click', function(event) {
			event.preventDefault();
			$("#buttonAdd1").prop('disabled', true);
			$("#buttonUpdate").prop('disabled', true);
			var hit = true;
			if (!validateAndSubmit("target", "fieldStaff_")) {
				$("#buttonAdd1").prop('disabled', false);
			}
			$("#buttonAdd1").prop('disabled', false);
			$("#buttonUpdate").prop('disabled', false);
		});

		function capitalizeName() {
			var arr = [ "firstName", "lastName" ];
			for (var i = 0; i < arr.length; i++) {
				var txt1 = document.getElementById(arr[i]).value;
				if (txt1 != null || txt != "") {
					capital(arr[i], txt1);
				}
			}
		}
		function capital(id, txt) {
			$(document.getElementById(id)).val(
					txt.replace(/^(.)|\s(.)/g, function($1) {
						return $1.toUpperCase();
					}));
		}

		function onUpdateClick() {
			try {
				if (document.getElementById('select') != null) {
					document.getElementById('select').multiple = true; //to enable all option to be selected
					for (var x = 0; x < document.getElementById('select').options.length; x++)//count the option amount in selection box
					{
						document.getElementById('select').options[x].selected = true;
					}//select all option when u click save button
				}
				/* if(document.getElementById('optrnsfr') != null ){
					document.getElementById('optrnsfr').multiple = true; //to enable all option to be selected
				    for (var x = 0; x < document.getElementById('optrnsfr').options.length; x++)//count the option amount in selection box
				    {
				        document.getElementById('optrnsfr').options[x].selected = true;		       
				    }//select all option when u click save button
				} */
				//$('#selectedAgentType').prop('disabled',false);
				capitalizeName();

				$('#warehouseName').attr("disabled", false);
				$('#selectedAgentType').attr("disabled", false);

				document.form.submit();
			} catch (e) {
				console.log(e);
			}
		}

		$(function() {
			$("#dynOpt").find(
					"input[type='button'][value='<s:text name="RemoveAll"/>']")
					.addClass("btn btn-warning");
			//$("#dynOpt input[value='Remove']").addClass("btn btn-warning");
			$("#dynOpt").find(
					"input[type='button'][value='<s:text name="Add"/>']")
					.addClass("btn btn-small btn-success fa fa-step-forward");
			$("#dynOpt").find(
					"input[type='button'][value='<s:text name="Remove"/>']")
					.addClass("btn btn-danger");
			$("#dynOpt").find(
					"input[type='button'][value='<s:text name="AddAll"/>']")
					.addClass("btn btn-sts");
		})

		function showPassword(obj) {
			var faEye="fa-eye"
			var faEyeSlash="fa-eye-slash"
			var x = document.getElementById("password");
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
		function showPassword1(obj) {
			var faEye="fa-eye"
			var faEyeSlash="fa-eye-slash"
			var x = document.getElementById("password1");
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