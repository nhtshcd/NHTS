<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<script>
var rowCounter=0;
	var tenant = '<s:property value="getCurrentTenantId()"/>';
	var command = '<s:property value="command"/>';
	var status='<s:property value="approvalStatus"/>';
	var fileAvailable = '<s:property value="fileAvailable"/>';
	var loggedDealer = '<s:property value="loggedDealer"/>';
	var isAdmin ="<%=(String) request.getSession().getAttribute("isAdmin")%>";
	var inspId ='<%=session.getAttribute("inspId")%>';
	var Pro = '<s:property value="agroChemicalDealer.product"/>';
	var idd = '';
	var apprType='<%=request.getParameter("approveType")%>';
	var insType='<s:property value="inspType"/>';
	jQuery(document).ready(function() {
		if(status.trim()==''){
			$(".adminLogin").addClass('hide');
		}else{
			$(".adminLogin").removeClass('hide');
		}
		
		if(apprType == 'inspector'){
			$(".userDiv").addClass('hide');
		}else{
			$(".userDiv").removeClass('hide');
		}
				if(insType=='4'){
					$(".agroDeal").addClass('hide');
				}else{
					$(".agroDeal").removeClass('hide');
				}
				
				status = '<s:property value="approvalStatus"/>';
						$(".dealerUpdate").addClass('hide');
						$(".dealerUpdate1").addClass('hide');
						
							if (isAdmin == 'true') {
							if (status == 0 || status == 3) {
								$('.admindealer').addClass('hide');
								$(".userDiv").addClass('hide');
							} else {
								$('.inspDet').attr('readonly', true);
								$('.inspDet').attr("disabled", true);
							}
						}
						if (inspId != null && inspId != '' && isAdmin != 'true') {
							$('.selectInsp').attr("disabled", true);

						}
						if (inspId != null && inspId != '' && isAdmin != 'true'
								&& status == 1) {
							$(".dealerUpdate").removeClass('hide');
							$("#pwd").addClass('hide');
							$("#pwd1").addClass('hide');
						} 
						var docApp='<s:property value="docApp"/>';
if(isAdmin== 'true' && status== '1'||status=='2' ){
	$("#regStatus").attr("disabled",true);
	$("#reason").attr("readonly",true)
}

enableRole( '<s:property value="statusDetail.status"/>');

					});

	function enableRole(val) {

		//alert(val);
		var val1 = val;
		if (val1 == 1) {
			$(".dealerUpdate").removeClass('hide');
			$(".dealerUpdate1").addClass('hide');
		} else if (val1 == 2) {
			$(".dealerUpdate1").removeClass('hide');
			$(".dealerUpdate").addClass('hide');
		} else {
			$(".dealerUpdate1").addClass('hide');
			$(".dealerUpdate").addClass('hide');
		}
	}
	function onCancel() {
		window.location.href = "<s:property value="redirectContent" />";
	}


	function setfilename(val) {
		$('#fileName').val(val);
	}
	function setInspfilename(val) {
		$('#inspFileName').val(val);
	}
	function setProoffilename(val){
		$("#proofFile1").val(val);
	}
	
	function setUrsbfilename(val){
		$("#ursbFile1").val(val);
	}
	
	function popDownload(type) {
		document.getElementById("loadId").value = type;

		$('#fileDownload').submit();

	}
	function showPass(element) {
		var group = document.getElementsByName(element);
		if (group[0].checked) {
			$("#pwd").removeClass('hide');
			$("#pwd1").removeClass('hide');

		} else {

			$("#pwd").addClass('hide');
			$("#pwd1").addClass('hide');
		}
	}
		

	</script>


		
	<div class="appContentWrapper marginBottom">
				<s:if test='#session.isAdmin =="true" || #session.inspId!=null'>
					<div class="formContainerWrapper insp adminLogin" id="inspDivv">
						<h2>
							<s:text name="info.inspection" />
						</h2>
						<div class="flexform">

							<div class="flexform-item ">
								<label for="txt"><s:text name="Inspector" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element selectInsp">
									<s:select
										class="form-control  select2 selectInsp inspDet isRole"
										list="getUserList(getInspType())" headerKey="" theme="simple"
										name="inspDetail.user.id"
										headerValue="%{getText('txt.select')}" Key="id" Value="name"
										id="selectedUser" />
								</div>
							</div>

							<div class="flexform-item admindealer">
								<label for="txt"><s:text name="inspComments" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:textarea name="inspDetail.comments"
										theme="simple" class="inspDet" id="inspComment" maxlength="255"/>
								</div>
							</div>
							<s:if test='#session.isAdmin =="false"'>
								<div class="flexform-item admindealer action">
									<label for="txt"><s:text
											name="agroChemicalDealer.inspFile" /> <font color="red"><s:text
												name="%{getLocaleProperty('imageValidation')}" /></font> </label>
									<div class="form-element">
										<s:file name="inspFiles" id="inspFile" cssClass="form-control"
											onchange="setInspfilename(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF'])"
											class="inspDet" />
											<s:if test="inspDetail.doc!=null && inspDetail.doc!=''">
											<button type="button" class="fa fa-download"
												onclick="popDownload(<s:property value="inspDetail.doc"/>)">
												</s:if>
									</div>
								</div>
							</s:if>
							<s:else>
								
									<div class="flexform-item admindealer down">
										<label for="txt"><s:property value="%{getLocaleProperty('agroChemicalDealer.File')}" /></label>
										<div class="form-element">
										<s:if test="inspDetail.doc!=null && inspDetail.doc!=''">
											<button type="button" class="fa fa-download"
												onclick="popDownload(<s:property value="inspDetail.doc"/>)">
												</s:if>
										</div>
									</div>
								
							</s:else>
							<div class="flexform-item admindealer  agroDeal">
								<label for="txt"><s:text
										name="%{getLocaleProperty('agroChemicalDealer.suitabilityPremises')}" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:radio id="sutable" list="yesNoList"
										name="inspDetail.suitabilityPremises" class="inspDet" />
								</div>
							</div>
							<div class="flexform-item admindealer partAppin">
								<label for="partAppintxt"><s:text
										name="%{getLocaleProperty('agroChemicalDealer.partApplInfo')}" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:radio id="applninfo" list="yesNoList"
										name="inspDetail.partApplInfo" class="inspDet" />
								</div>
							</div>

						</div>
					</div>
				</s:if>
				<s:if
					test='#session.isAdmin =="true" && status !=0 && status !=3'>
					<div class="formContainerWrapper userDiv">
						<h2>
							<s:text name="info.userCred" />
						</h2>
						<div class="flexform" >
							<div class="flexform-item">
								<label for="txt"><s:text
										name="agroChemicalDealer.partReg" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select class="form-control  select2"
										list="applicantStatusList" headerKey="" theme="simple"
										name="statusDetail.status"
										headerValue="%{getText('txt.select')}" Key="id" Value="name"
										id="regStatus" onchange="enableRole(this.value)" />
								</div>
							</div>
							<div class="flexform-item dealerUpdate1">
								<label for="txt"><s:text
										name="agroChemicalDealer.partReason" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:textarea name="statusDetail.reason" id="reason" maxlength="255"
										theme="simple" />
								</div>
							</div>
							<s:if test="statusDetail.doc!=null  && approvalStatus==1">
								<div class="flexform-item dealerUpdate">
									<label for="txt"><s:text name="agroChemicalDealer.file" />
									</label>
									<div class="form-element">
										<s:if test="statusDetail.doc!=''">
											<button type="button" class="fa fa-download"
												onclick="popDownload(<s:property value="%{statusDetail.doc}"/>)" ></button>
										</s:if>
									</div>
								</div>
							</s:if>
							<s:else>
								<div class="flexform-item dealerUpdate">
									<label for="txt"><s:text name="agroChemicalDealer.file" />
										<font color="red"><s:text
												name="%{getLocaleProperty('imageValidation')}" /></font> </label>
									<div class="form-element">
										<s:file name="approvalFiles" id="file1" cssClass="form-control"
											onchange="setfilename(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF'])" />
											
											
									</div>
								</div>
							</s:else>

						</div>
					</div>
				</s:if>
				
				</div>



			
			


			 

