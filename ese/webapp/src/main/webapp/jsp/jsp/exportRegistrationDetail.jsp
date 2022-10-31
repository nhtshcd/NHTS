<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
<title>Insert title here</title>
</head>
<body>
	<script src="js/dynamicComponents.js?v=18.32"></script>
	<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
	<script>
		var idd = '';
		jQuery(document)
				.ready(
						function() {
							var tenant = '<s:property value="getCurrentTenantId()"/>';
							var branchId = '<s:property value="getBranchId()"/>';
							var inspDetails = '<s:property value="inspDetails"/>';
							idd = '<s:property value="expRegis.id"/>';
							var status = '<s:property value="expRegis.status"/>';
							
							$(".breadCrumbNavigation").find('li:last').find(
							'a:first').attr("href",
							'<s:property value="redirectContent" />');
							$('#expVarInc').addClass('hide');
							$('#apprdate').addClass('hide');
							
							var p = '<s:property value="expRegis.packToExitPoint" />';
							 
							if (p == 1)
								$("#gender").text("Male");
							else if (p == 0)
								$("#gender").text("Female");
							});
							
							function onCancel() {
								window.location.href = "<s:property value="redirectContent" />";

							}
							function popDownload(id) {
								document.getElementById("loadId").value = id;
								//document.getElementById("loadType").value=type;

								$('#fileDownload').submit();

							}
							
							</script>
	<s:form name="form">
		<s:hidden key="currentPage" />
		<s:hidden key="expRegis.id" id="id" class='uId' />
		<s:hidden key="command" />
		<s:hidden key="redirectContent" />
		<div class="flex-view-layout">
			<div class="fullwidth">
				<div class="flexWrapper">
					<div class="flexLeft appContentWrapper">
						<div class="formContainerWrapper dynamic-form-con">
							<h2>
								<s:property value="%{getLocaleProperty('info.export')}" />
							</h2>
							<s:if test='branchId==null'>
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="app.branch" />
									</p>
									<p class="flexItem">
										<s:property value="%{getBranchName(expRegis.branchId)}" />
									</p>
								</div>
							</s:if>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="exporterRegistr.cmpyName1" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.name" />
								</p>
							</div>
 
	                     <div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="exporterRegistr.cemai" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.tin" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="export.associationMemb" />
								</p>
								<p class="flexItem">
									<s:property
										value='%{getLocaleProperty(expRegis.cmpyOrientation)}' />

								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="export.krapin" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.regNumber" />
								</p>
							</div>

						<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="KRApin.expDate" />
								</p>
								<p class="flexItem">
								<s:date name="expRegis.expireDate" format="dd-MM-yyyy" />
								</p>
							</div>
							
                           <div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="export.legalstatus" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.packhouse" />
								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="export.license" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.refLetterNo" />
								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="export.cropcat" />
								</p>
								<p class="flexItem">
									<s:property
										value='expRegis.ugandaExport' />

								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="farmer.fcropName" />
								</p>
								<p class="flexItem">
									<s:property
										value='expRegis.farmerHaveFarms' />

								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="export.cropvariety" />
								</p>
								<p class="flexItem">
									<s:property
										value="expRegis.scattered" />
								</p>
							</div>
							
							<s:if test="expRegis.scattered=='Others'">
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="farmCrops.ifOthers" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.otherScattered" />
								</p>
							</div>
							</s:if>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="export.crophs" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.packGpsLoc" />
								</p>
							</div>

                                <div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="premises.postalAddr" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.expTinNumber" />
								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="country.name" />
								</p>
								<p class="flexItem">
									<s:property
										value="expRegis.village.city.locality.state.country.name" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="county.name" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.village.city.locality.state.name" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="subcountry.name" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.village.city.locality.name" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="ward.name" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.village.city.name" />
								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="village.name" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.village.name" />
								</p>
							</div>
 
 
							

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="export.nationalId" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.rexNo" />
								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="export.applicantName" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.farmToPackhouse" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="exporterRegistr.gender" />
								</p>
								<p class="flexItem" id="gender">
									<s:text name="expRegis.packToExitPoint"></s:text>
								</p>
							</div>




							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="exporterRegistr.phn" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.mobileNo" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="exporterRegistr.email" />
								</p>
								<p class="flexItem">
									<s:property value="expRegis.email" />
								</p>
							</div>
							 
							 <div class="dynamic-flexItem">
								<p class="flexItem">
									HCD Status
								</p>
								<p class="flexItem">
								<s:if test='expRegis.status==1'>
									<s:text name="statusValue1" />
									</s:if>
									<s:else>
									<s:text name="statusValue2" />
									</s:else>
								</p>
							</div>
						 
						</div>

				 
						<div class="margin-top-10">

							<span id="cancel" class=""><span class="first-child"><button
										type="button" class="back-btn btn btn-sts"
										onclick="onCancel()">
										<b><FONT color="#FFFFFF"><s:text name="back.button" />
										</font></b>
									</button></span></span>

						</div>
					</div>
				</div>
			</div>
		</div>
	</s:form>
	<s:form id="fileDownload" action="agroChemicalDealer_populateDownload">
		<s:hidden id="loadId" name="idd" />
		<%-- <s:hidden id="loadType" name="type" /> --%>

	</s:form>

</body>