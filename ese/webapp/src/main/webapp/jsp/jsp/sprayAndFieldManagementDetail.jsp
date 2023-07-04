<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType")
					: "swithlayout"%>">
</head>

<body>

	<script src="js/dynamicComponents.js?v=18.32"></script>
	<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
	<script>
		var idd = '';
		jQuery(document).ready(
				function() {
					var tenant = '<s:property value="getCurrentTenantId()"/>';
					var branchId = '<s:property value="getBranchId()"/>';

					idd = '<s:property value="sprayAndFieldManagement.id"/>';

					$(".breadCrumbNavigation").find('li:last').find('a:first')
							.attr("href",
									'<s:property value="redirectContent" />');
				});

		function onCancel() {
			window.location.href = "<s:property value="redirectContent" />";

		}
		function popDownload(id) {
			document.getElementById("loadId").value = id;
			$('#fileDownload').submit();

		}
	</script>

	<s:form name="form">
		<s:hidden key="currentPage" />
		<s:hidden key="sprayAndFieldManagement.id" id="id" class='uId' />
		<s:hidden key="command" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<div class="flex-view-layout">
			<div class="fullwidth">
				<div class="flexWrapper">
					<div class="flexLeft appContentWrapper">
						<%-- <div class="formContainerWrapper dynamic-form-con">
							<h2>
								<s:text name="info.sprayAndFieldManagementDetails" />
							</h2> --%>
							
							<div class="formContainerWrapper">
							<div class="aPanel farmer_info">
								<div class="aTitle">
									<h2>
										<s:property value="%{getLocaleProperty('info.sprayAndFieldManagementDetails')}" />
										<div class="pull-right">
											<a class="aCollapse" href="#"><i
												class="fa fa-chevron-right"></i></a>
										</div>
									</h2>
								</div>
						<div class="aContent dynamic-form-con">
							
							<s:if test='branchId==null'>
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="app.branch" />
									</p>
									<p class="flexItem">
										<s:property
											value="%{getBranchName(sprayAndFieldManagement.branchId)}" />
									</p>
								</div>
							</s:if>
<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="farmer.fcode" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.planting.farmCrops.farm.farmer.farmerId" />
								</p>
							</div>
							
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="profiles.farmers" />
								</p>
								<p class="flexItem">
									<s:property
										value="sprayAndFieldManagement.planting.farmCrops.farm.farmer.firstName" />
								</p>
							</div>
							
								<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="farmCode" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.planting.farmCrops.farm.farmCode" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="profile.farm" />
								</p>
								<p class="flexItem">
									<s:property
										value="sprayAndFieldManagement.planting.farmCrops.farm.farmName" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="block" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.planting.farmCrops.blockName" />
								</p>
							</div>


							 <div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="blockIds" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.planting.farmCrops.blockId" />
								</p>

							</div>

							<%--<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="Planting" />
								</p>
								<p class="flexItem">
									<s:property
										value="sprayAndFieldManagement.planting.plantingId" />
								</p>
							</div> --%>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="Planting ID" />
								</p>
								<p class="flexItem">
									<s:property
										value="sprayAndFieldManagement.planting.plantingId" />
								</p>
							</div>





							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="CropVariety" />
								</p>
								<p class="flexItem">
									<s:property
										value="sprayAndFieldManagement.planting.variety.name" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.grade" />
								</p>
								<p class="flexItem">
									<s:property
										value="sprayAndFieldManagement.planting.grade.name" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.dateSpray" />
								</p>
								<p class="flexItem">
									<s:date name="sprayAndFieldManagement.dateOfSpraying"
										format="dd/MM/yyyy" />

								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.endDateSpray" />
								</p>
								<p class="flexItem">
									<s:date name="sprayAndFieldManagement.endDateSpray"
										format="dd/MM/yyyy" />

								</p>
							</div>

							<%-- <div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.agroChemicalName" />
								</p>
								<p class="flexItem">
									<s:property
										value="%{getCatlogueValueByCodeArray(sprayAndFieldManagement.agroChemicalName)}" />
								</p>
							</div> --%>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.pcbp" />
								</p>
								<p class="flexItem">
									<%-- 	<s:property value="%{getCatlogueValueByCodeArray(sprayAndFieldManagement.pcbp.id)}" /> --%>
									<s:property value="sprayAndFieldManagement.pcbp.tradeName" />
								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.dosage" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.dosage" />
								</p>

							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.uom" />
								</p>
								<p class="flexItem">
								
							 <s:property value="%{getCatalgueNameByCode(sprayAndFieldManagement.uom)}" /> 
								</p>

							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.activeIngredient" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.activeIngredient" />
								</p>

							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:property value="%{getLocaleProperty('sprayAndFieldManagement.recommen')}" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.recommen" />
								</p>

							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.NameOfOperator" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.NameOfOperator" />
								</p>

							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.sprayeMobileNumber" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.sprayMobileNumber" />
								</p>

							</div>
							
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.operatorMedicalReport" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.operatorMedicalReport" />
								</p>

							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.typeApplicationEquipment" />
								</p>
								<p class="flexItem">
									<s:property
										value="%{getCatlogueValueByCodeArray(sprayAndFieldManagement.typeApplicationEquipment)}" />
								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.methodOfApplication" />
								</p>
								<p class="flexItem">
									<s:property
										value="%{getCatlogueValueByCodeArray(sprayAndFieldManagement.methodOfApplication)}" />
								</p>

							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.phi" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.phi" />
								</p>

							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text
										name="sprayAndFieldManagement.TrainingStatusOfSprayOperator" />
								</p>
								<p class="flexItem">
									<!--<s:date name="sprayAndFieldManagement.trainingStatusOfSprayOperator"
										format="dd/MM/yyyy" />-->
									<s:property
										value="%{getCatlogueValueByCodeArray(sprayAndFieldManagement.trainingStatusOfSprayOperator)}" />

								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text
										name="sprayAndFieldManagement.AgrovetOrSupplierOfTheChemical" />
								</p>
								<p class="flexItem">
									<s:property
										value="sprayAndFieldManagement.agrovetOrSupplierOfTheChemical" />
								</p>

							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.LastDateOfCalibration" />
								</p>
								<p class="flexItem">
									<s:date name="sprayAndFieldManagement.lastDateOfCalibration"
										format="dd/MM/yyyy" />
								</p>

							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="sprayAndFieldManagement.insectTargeted" />
								</p>
								<p class="flexItem">
									<s:property value="sprayAndFieldManagement.insectTargeted" />
								</p>

							</div>

						</div>
						</div>
						<s:if test="roleID.trim().equalsIgnoreCase('2')">
						<s:iterator value="ex" var="innerList">
								<div class="aPanel audit_history">
									<div class="aTitle">
										<h2>
											<s:if test="#innerList[2].toString().trim().equalsIgnoreCase('ADD')">
													<s:property value="#innerList[0].createdUser" />
											</s:if>
											<s:else>
													 <s:property value="#innerList[0].updatedUser" />
											 </s:else>
											-
											<s:date name="#innerList[1].revisionDate" format="dd/MM/yyyy hh:mm:ss" />
											-
											<s:property value="%{getLocaleProperty('default'+#innerList[2])}" />
											<div class="pull-right">
												<a class="aCollapse "
													href="#<s:property value="#innerList[1].id" />"><i
													class="fa fa-chevron-right"></i></a>
											</div>
										</h2>
									</div>
									<div class="aContent dynamic-form-con"
										id="<s:property value="#innerList[1].id" />">
										<jsp:include page='/jsp/jsp/auditSprayAndFieldManagementDetail.jsp' />
									</div>
								</div>
							</s:iterator> 
							</s:if>
						</div>
						
						<div>
							<span id="cancel" class=""><span class="first-child"><button
										type="button" class="back-btn btn btn-sts"
										onclick="onCancel();">
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

	</s:form>

</body>

