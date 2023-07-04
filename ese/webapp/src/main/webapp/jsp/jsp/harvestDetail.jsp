<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
</head>
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
</head>
<script type="text/javascript">
	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>";
	}
</script>
<body>
	<s:form name="harvestForm" cssClass="fillform" method="post"
		action="harvest_%{command}" id="harvestForm">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="harvest.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<%-- <div class="formContainerWrapper">
				<h2>
					<s:text name="info.harvest" />
				</h2> --%>
				<div class="formContainerWrapper">
							<div class="aPanel farmer_info">
								<div class="aTitle">
									<h2>
										<s:property value="%{getLocaleProperty('info.harvest')}" />
										<div class="pull-right">
											<a class="aCollapse" href="#"><i
												class="fa fa-chevron-right"></i></a>
										</div>
									</h2>
								</div>
						<div class="aContent dynamic-form-con">
				<div class="flexform">
				
				<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.farmer')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.planting.farmCrops.farm.farmer.firstName" />
						</p>
					</div>
					
				<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farmer.fcode')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.planting.farmCrops.farm.farmer.farmerId" />
						</p>
					</div>
				
				
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farmId')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.planting.farmCrops.farm.farmCode" />
						</p>
					</div>
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.farm')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.planting.farmCrops.farm.farmName" />
						</p>
					</div>
					
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.block')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.planting.farmCrops.blockName" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.cropPlanting')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.planting.plantingId" />
						</p>
					</div>
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.produceId')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.produceId" />
						</p>
					</div>
				
						<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.crop')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.planting.variety.name" />
						</p>
					</div>
						<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.variety')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.planting.grade.name" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.date')}" />
						</p>
						<p class="flexItem">
							<s:property value="date" />
						</p>
					</div>
					
							<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('harvest.harvestType')}" />
								
						</p>
						<p class="flexItem">
						
						<s:property
															value='%{getLocaleProperty("harvestType"+harvest.harvestType)}' />
						
							
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
															value='%{getLocaleProperty("harvestTypeLabel"+harvest.harvestType)}' />
						</p>
						<p class="flexItem">
<s:if test="harvest.harvestType==0">
<s:property value="%{getText('{0,number,#,##0.00}',{harvest.noStems})}" />
</s:if>

<s:else>
<s:property value="%{getText('{0,number,#,##0}',{harvest.noStems})}" />

</s:else>
												
	</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.qtyHarvested')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.qtyHarvested" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('harvest.yieldsHarvested')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.yieldsHarvested" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('harvest.expctdYieldsVolume')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.expctdYieldsVolume" />
						</p>
					</div>
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.nameHarvester')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.nameHarvester" />
						</p>
					</div>
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('harvest.harvestEquipment')}" />
								
						</p>
						<p class="flexItem">
							<s:property value="%{getCatalgueNameByCode(harvest.harvestEquipment)}" />
						</p>
					</div>
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.noUnits')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.noUnits" />
						</p>
					</div>
					
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.packingUnit')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.packingUnit" />
						</p>
					</div>
					<%-- <div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.deliveryType')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.deliveryType" />
						</p>
					</div> --%>
					
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('harvest.observationPhi')}" />
						</p>
						<p class="flexItem">
							<s:property value="harvest.observationPhi" />
						</p>
					</div>
					
			
					
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
										<jsp:include page='/jsp/jsp/auditHarvestDetail.jsp' />
									</div>
								</div>
							</s:iterator> 
				</s:if>
			</div>
			<div class="flex-layout flexItemStyle">
				<div class="margin-top-10">
					<span id="cancel" class=""><span class="first-child"><button
								type="button" class="cancel-btn btn btn-sts"
								onclick="onCancel();">
								<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
								</font></b>
							</button></span></span>
				</div>
			</div>
		</div>
	</s:form>

</body>