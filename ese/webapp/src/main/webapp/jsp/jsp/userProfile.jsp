<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css"> -->
<head>
<META name="decorator" content="swithlayout">
</head>

<s:form name="form" action="user_%{command}" method="post"
	cssClass="fillform" enctype="multipart/form-data">
	<s:hidden key="currentPage" />
	<s:hidden key="id" id="id" />
	<s:if test='"update".equalsIgnoreCase(command)'>
		<s:hidden key="user.id" id="id" />
	</s:if>

	<s:hidden name="redirectContent" id="redirectContent" />
	<s:hidden key="command" />
	<s:hidden key="selecteddropdwon" id="listname" />
	<s:hidden id="signature" name="signature" />
	<s:hidden key="temp" id="temp" />
	<s:hidden key="user.signature" id="user.signature" />
	<div class="appContentWrapper marginBottom">

		<div class="error">
			<font color="red"> <s:actionerror /> <s:fielderror /></font> <sup>*</sup>
			<s:text name="reqd.field" />
		</div>
		<div class="formContainerWrapper">
			<h2>
				<s:text name="info.user" />
			</h2>
			<div class="flexform">
				<s:if test='isMultiBranch==1'>
					<div class="flexform-item">
						<s:if test='branchId==null'>
							<s:if test='"create".equalsIgnoreCase(command)'>
								<s:label theme="simple">
									<s:text name="app.branch" />
								</s:label>
								<div class="form-element">
									<s:select name="branchId_F" id="branchSelect" theme="simple"
										listKey="key" listValue="value" list="parentBranches"
										headerKey="-1" headerValue="%{getText('txt.select')}"
										onchange="populateSubBranch(this);populateRoleMulti(this)"
										cssClass="form-control input-sm select2" />
								</div>
							</s:if>
							<s:if test='"update".equalsIgnoreCase(command)'>
								<div class="flexform-item">
									<label for="txt"><s:text name="app.branch" /></label>
									<div class="form-element">
										<s:property value="%{getBranchName(user.parentBranchId)}" />
									</div>
								</div>
							</s:if>
						</s:if>
						<s:if
							test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<s:if test='"create".equalsIgnoreCase(command)'>
								<div class="flexform-item">
									<label for="txt"><s:text name="app.subBranch" /></label>
									<div class="form-element">
										<s:select name="subBranchId_F" id="subBranchSelect"
											theme="simple" listKey="key" listValue="value"
											list="subBranchesMap" headerKey=""
											headerValue="%{getText('txt.select')}"
											onchange="populateRoleMulti(this)"
											cssClass="form-control input-sm select2" />
									</div>
								</div>
							</s:if>
							<s:if test='"update".equalsIgnoreCase(command)'>
								<div class="flexform-item">
									<label for="txt"><s:text name="app.subBranch" /></label>
									<div class="form-element">
										<s:property value="%{getBranchName(user.branchId)}" />
										<s:hidden name="user.branchId" />
									</div>
								</div>
							</s:if>
						</s:if>
					</div>
				</s:if>
				<s:else>
					<s:if test='branchId==null'>
						<s:if test='"create".equalsIgnoreCase(command)'>
							<div class="flexform-item">
								<label for="txt"><s:text name="app.subBranch" /></label>
								<div class="form-element">
									<s:select name="branchId_F" id="branchSelect" theme="simple"
										listKey="key" listValue="value" list="branchesMap"
										headerKey="-1" headerValue="%{getText('txt.select')}"
										onchange="populateRoles(this)"
										cssClass="form-control input-sm select2" />
								</div>
							</div>
						</s:if>
						<s:if test='"update".equalsIgnoreCase(command)'>
							<div class="flexform-item">
								<label for="txt"><s:text name="app.branch" /></label>
								<div class="form-element">
									<s:property value="%{getBranchName(user.branchId)}" />
									<s:hidden name="user.branchId" />
								</div>
							</div>
						</s:if>
					</s:if>
				</s:else>
				<div class="flexform-item">
					<label> <s:text name="userProfile.username" /><sup
						style="color: red;"> * </sup></label>
					<s:if test='"update".equalsIgnoreCase(command)'>
						<div class="form-element">
							<s:textfield size="25" readonly="true" name="user.username"
								theme="simple" cssClass="form-control input-sm" maxlength="45" />
						</div>
					</s:if>
					<s:else>
						<div class="form-element">
							<s:textfield name="user.username" theme="simple" size="25"
								maxlength="45" cssClass="form-control input-sm" />
						</div>
					</s:else>
				</div>

				<div class="flexform-item">
					<label for="txt"><s:text name="personalInfo.firstName" />
						<sup style="color: red;">*</sup></label>
					<div class="form-element">
						<s:textfield id="firstName" name="user.persInfo.firstName"
							theme="simple" size="25" maxlength="50"
							cssClass="upercls form-control input-sm" />
					</div>
				</div>

				<div class="flexform-item">
					<label for="txt"><s:text name="personalInfo.lastName" /></label>
					<div class="form-element">
						<s:textfield id="lastName" name="user.persInfo.lastName"
							theme="simple" size="25" maxlength="35"
							cssClass="upercls form-control input-sm" />
					</div>
				</div>

				<div class="flexform-item">
					<label for="txt"><s:text name="user.lang" /> <sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select id="language" headerKey=""
							headerValue="%{getText('txt.select')}" name="user.language"
							list="languageMap" listKey="key" listValue="value" theme="simple"
							cssClass="form-control input-sm select2" />
					</div>
				</div>

				<div class="flexform-item">
					<label for="txt"> <s:property
							value="%{getLocaleProperty('photo')}" /> <font color="red">Allowed
							Formats : jpeg ,jpg ,png and pdf ( Size should be less than 100KB
							)</font>
					</label>
					<div class="form-element">
						<s:if test='userImageString!=null && userImageString!=" "'>
							<s:file name="userImage" id="userImage"
								onchange="checkImgHeightAndWidth(this);validateImages(this.id)" />
						</s:if>
						<s:else>
							<s:file name="userImage" id="userImage"
								onchange="checkImgHeightAndWidth(this);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF']);validateImages(this.id)"
								cssClass="form-control" />
						</s:else>
						<button type="button" class="aButtonClsWbg" id="remImg"
							onclick='deleteFile(<s:property value="user.id" />)'>
							<!-- <i class="fas fa-trash-alt" aria-hidden="true"></i> -->
							<i class="fa fa-trash-o" aria-hidden="true"></i>
						</button>
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
					<label for="txt"><s:text name="contactInfo.address" /></label>

					<div class="form-element">
						<s:textarea name="user.contInfo.address1" theme="simple"
							maxlength="255" cssClass="form-control input-sm"
							style="height:40px;" />
					</div>
				</div>


				<div class="flexform-item">
					<label for="txt"><s:text name="contactInfo.phoneNumber" /></label>

					<div class="form-element">
						<s:textfield name="user.contInfo.phoneNumber" theme="simple"
							maxlength="20" cssClass="form-control input-sm"
							onkeypress="return isNumber(event)" />
					</div>
				</div>



				<div class="flexform-item">
					<label for="txt"><s:text name="contactInfo.mobileNumber" />
					</label>
					<div class="form-element">
						<s:textfield name="user.contInfo.mobileNumbere" theme="simple"
							maxlength="15" cssClass="form-control input-sm"
							onkeypress="return isNumber(event)" />
					</div>
				</div>

				<div class="flexform-item">
					<label for="txt"><s:text name="contactInfo.email" /> <sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:textfield name="user.contInfo.email" theme="simple"
							maxlength="45" cssClass="form-control input-sm" />
					</div>
				</div>

			</div>
		</div>
	</div>


	<div class="appContentWrapper marginBottom">
		<div class="formContainerWrapper">
			<h2>
				<s:text name="info.userCred" />
			</h2>
			<div class="flexform">
				<s:if test='"update".equalsIgnoreCase(command)'>
					<div class="flexform-item" id='passwordOptionDiv'>
						<label for="txt"><s:text name="user.changePassword" /></label>
						<div class="form-element">
							<s:checkbox key="user.changePassword" theme="simple"
								onclick="javascript:showPass(this.name);" />
						</div>
					</div>
				</s:if>
				<s:else>
					<div class="flexform-item" id='passwordOptionDiv'>
						<label for="txt"><s:text name="user.setPassword" /></label>
						<div class="form-element">
							<s:checkbox key="user.changePassword" theme="simple"
								onclick="javascript:showPass(this.name);" />
						</div>
					</div>
				</s:else>
				<div class="flexform-item">
					<label for="txt"><s:text name="user.enabled" /></label>
					<div class="form-element">
						<s:checkbox name="user.enabled" id="userDealerDisable"
							theme="simple" />
					</div>
				</div>
				<div class="flexform-item">
					<label for="txt"><s:text name="user.select.role" /> <sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select name="seletedRole" id="selectRole" list="roles"
							listKey="key" listValue="value" theme="simple" headerKey=""
							headerValue="%{getText('txt.select')}"
							onchange="populateExporter(this.value)"
							cssClass="form-control input-sm select2" />
					</div>
				</div>
				<s:if test="getLoggedInDealer()<=0">
					<div class="flexform-item hide" id="export">
						<label for="txt"><s:text name="agent.exporter" /> <sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select name="user.agroChDealer" id="exporter"
								list="exporterList" listKey="key" listValue="value"
								theme="simple" headerKey=""
								headerValue="%{getText('txt.select')}"
								cssClass="form-control input-sm select2" />
						</div>
					</div>
				</s:if>
				<div class="flexform-item">
					<label for="txt"><s:property
							value="%{getLocaleProperty('agroChemicalDealer.signature')}" /><font
						color="red">Allowed Formats : png, jpg, jpeg and pdf( Size
							should be less than 100KB )</font> </label>
					<div class="form-element">
						<s:file name="file1" id="file1" cssClass="form-control"
							onchange="setFileTrainingCert(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF']);validateImages(this.id)" />
						<s:if
							test="command=='update' && user.signature!=null && user.signature!=''">
							<button type="button" class="fa fa-download"
								onclick="popDownload(<s:property value="user.signature"/>)">
						</s:if>
					</div>
				</div>
				<div class="flexform-item" id='pwd'>
					<label for="txt"> <s:text name="user.password" /> <sup
						style="color: red;"> *</sup></label>
					<div class="form-element">
						<s:password id="p" name="user.password" theme="simple"
							title="%{getPasswordToolTip()}" cssClass="form-control input-sm" />
						<i class="fa fa-eye" id="togglePassword"
							style="margin-left: -30px; cursor: pointer;"
							onclick="showPassword(this)"></i>
					</div>
				</div>

				<div class="flexform-item" id="pwd1">
					<label for="txt"> <s:text name="user.confirmpassword" /> <sup
						style="color: red;"> *</sup></label>
					<div class="form-element">
						<s:password id="p1" name="user.confirmPassword" theme="simple"
							cssClass="form-control input-sm" />
						<i class="fa fa-eye" id="togglePassword"
							style="margin-left: -30px; cursor: pointer;"
							onclick="showPassword1(this)"></i>
						<!--  <i class="fa fa-eye-slash" id="togglePassword" style="margin-left: -30px; cursor: pointer;" onclick="showPassword1(this)"></i> -->
					</div>
				</div>
			</div>
		</div>
		<div class="margin-top-10">
			<s:if test="command =='create'">
				<span id="button1" class=""><span class="first-child">
						<button type="submit" class="save-btn btn btn-success"
							id="sucessbtn" onclick="onSubmit();">
							<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
							</font>
						</button>
				</span></span>
				<span id="cancel" class=""><span class="first-child">
						<button type="button" class="cancel-btn btn btn-sts"
							onclick="onCancel()">
							<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
							</font></b>
						</button>
				</span></span>
			</s:if>
			<s:else>
				<span id="button1" class=""><span class="first-child">
						<button type="button" class="save-btn btn btn-success"
							id="sucessbtn" onclick="onSubmit();">
							<font color="#FFFFFF"> <b><s:text name="update.button" /></b>
							</font>
						</button>
				</span></span>
				<span id="cancel" class=""><span class="first-child">
						<button type="button" class="cancel-btn btn btn-sts"
							onclick="onCancel()">
							<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
							</font></b>
						</button>
				</span></span>
			</s:else>
		</div>
	</div>


	<script>
		function checkImgHeightAndWidth(val) {

			var _URL = window.URL || window.webkitURL;
			var img;
			var file = document.getElementById('userImage').files[0];

			if (file) {

				img = new Image();
				img.onload = function() {
					imgHeight = this.height;
					imgWidth = this.width;
				};
				img.src = _URL.createObjectURL(file);
			}
			$("#userImageExist").val("0");			
		}
		
		function populateExporter(role){
			var roleTyoe =role.split("|")[1];
			
			if(roleTyoe=='1' || roleTyoe=='2'){
				
				$('#export').removeClass("hide");
			}else{
				
				$('#export').addClass("hide");
			}
		}

</script>

</s:form>
<s:form id="fileDownload" action="user_populateDownload">
	<s:hidden id="loadId" name="idd" />
</s:form>

<%-- <s:form name="cancelform" action="user_list.action">
	<s:hidden key="currentPage" />
</s:form> --%>

<script type="text/javascript">
var userDealer = '<s:property value="getUserDealerId()"/>';
var command = '<s:property value="command"/>';

<s:if test="user.changePassword==true">

	$(".hidePass").show();
	</s:if>
	<s:else>
	$(".hidePass").hide();
	</s:else>

	function showPass(element) {
		var group = document.getElementsByName(element);
		if (group[0].checked) {

			$(".hidePass").show();
		} else {
			$(".hidePass").hide();
		}
	}

<s:if test="user.changePassword==true">
	document.getElementById('pwd').className = "flexform-item";
	document.getElementById('pwd1').className = "flexform-item";
	</s:if>
	<s:else>
	document.getElementById('pwd').className = "hide";
	document.getElementById('pwd1').className = "hide";
	</s:else>

	function showPass(element) {
		var group = document.getElementsByName(element);
		if (group[0].checked) {
			document.getElementById('pwd').className = "flexform-item";
			document.getElementById('pwd1').className = "flexform-item";
		} else {
			document.getElementById('pwd').className = "hide";
			document.getElementById('pwd1').className = "hide";
		}
	}

	function onSubmit() {
		 
			
			$("#target").submit();
			 document.form.submit();
		
		 $("#sucessbtn").prop('disabled', true);
	}
	
	
	 function enableButton(){
		jQuery(".save-btn").prop('disabled',false);
		} 


	function capitalizeName() {
		var arr = [ "firstName", "lastName", "middleName" ];
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

	function populateRoles(obj) {
		var branchId = $(obj).val();
		reloadSelect('#selectRole', 'user_populateRoles.action?branchId_F='
				+ branchId);
	}
	
	function populateRoleMulti(obj) {
		var branchId = $(obj).val();
		if(isEmpty(branchId)){
			branchId = $("#branchSelect").val();
		}
		reloadSelect('#selectRole', 'user_populateRoles.action?branchId_F='
				+ branchId);
	}
	
	function populateSubBranch(obj){
		var branchId = $(obj).val();
		jQuery.post("user_populateSubBranches.action",{branchId_F:branchId},function(result){
				insertOptions("subBranchSelect",$.parseJSON(result));
		});
		
	}
	function deleteFile(id) {
		
		 if (confirm('<s:text name="confirmToDelete"/>')) {
			 $("#userImageExist").val("1");
			 $("#userImageString").val("");
			 $("#userImage").val("");
			 $('#image').attr('src', 'img/no-image.png');
		 }
		 
	}
	jQuery(document)
	.ready(function() {
				$(".breadCrumbNavigation").find('li:last').find(
						'a:first').attr("href",
						'<s:property value="redirectContent" />');
								
				
				populateExporter('<s:property value="seletedRole" />')
				
				if(command=='update'){
					$('#exporter').prop('disabled', true);
				}
				});
	function onCancel() {
		window.location.href = "<s:property value="redirectContent" />";
	}
	
	function setFileTrainingCert(val) {
		$('#signature').val(val);
	}
	
	function validateImages(idd) {
	    //alert("hi");
		var file = document.getElementById(idd).files[0];
		var filename = document.getElementById(idd).value;
		var fileExt = filename.split('.').pop();
		//alert("file:"+file);
		var result=true;
	 	if (file != undefined) {
	 		var s="#"+idd ;
	 		if (fileExt == 'jpg' || fileExt == 'jpeg' || fileExt == 'png'
				|| fileExt == 'JPG' || fileExt == 'JPEG' || fileExt == 'PNG' || fileExt == 'pdf' || fileExt == 'PDF') {
			if (file.size > 100000) {
				//alert(idd)
				alert('File Size Exceeds');
				$(s).replaceWith($(s).val('').clone(true));
				hit = false;
				if (idd.trim() === "file1") {
				    document.getElementById("signature").value = "";
				}
				enableButton();
				result=false;
				
			}
		} else {
			result=false;
			alert('Invalid File Extension')
			$(s).replaceWith($(s).val('').clone(true));
			file.focus();
		}
		}
		return result;
	}
	
	function popDownload(type) {
		document.getElementById("loadId").value = type;
		$('#fileDownload').submit();

	}
	
	function showPassword(obj) {
		var faEye="fa-eye"
		var faEyeSlash="fa-eye-slash"
		var x = document.getElementById("p");
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
		var x = document.getElementById("p1");
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