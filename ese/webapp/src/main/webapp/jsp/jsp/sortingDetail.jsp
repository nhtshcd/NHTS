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
	<s:form name="sortingForm" cssClass="fillform" method="post"
		action="sorting_%{command}" id="sortingForm">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="sorting.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.sorting" />
				</h2>
				<div class="flexform">
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('sorting.farmer')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.planting.farmCrops.farm.farmer.firstName" />
						</p>
					</div>
					
						<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farmer.fcode')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.planting.farmCrops.farm.farmer.farmerId" />
						</p>
					</div>
				
				
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farmId')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.planting.farmCrops.farm.farmCode" />
						</p>
					</div>
					

					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('sorting.farm')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.planting.farmCrops.farm.farmName" />
						</p>
					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('sorting.block')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.planting.farmCrops.blockName" />
						</p>
					</div>
					
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('sorting.blockId')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.planting.farmCrops.blockId" />
						</p>
					</div>
					
					
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('Planting ID')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.planting.plantingId" />
						</p>
					</div>
					
								<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.crop')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.planting.variety.name" />
						</p>
					</div>
						<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('harvest.variety')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.planting.grade.name" />
						</p>
					</div>
			
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('Sorting Id')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.qrCodeId" />
						</p>
					</div>
					
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('sorting.cCenter')}" />
						</p>
						<p class="flexItem">
							<s:property value="sorting.planting.farmCrops.farm.farmName" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('sorting.dateHarvested')}" />
						</p>
						<p class="flexItem">
							<s:property value="dateHarvested" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('sorting.qtyHarvested')}" />
						</p>
						<p class="flexItem">
							<s:property value="qtyHarvested" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('sorting.qtyRejected')}" />
						</p>
						<p class="flexItem">
							<s:property value="qtyRejected" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('sorting.netQty')}" />
						</p>
						<p class="flexItem">
							<s:property value="netQty" />
						</p>
					</div>
					
					<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('sorting.truckType')}" />
								</p>
								<p class="flexItem">
									<%-- <s:property value="sorting.truckType" /> --%>
									<s:property value='%{getCatlogueValueByCodeArray(sorting.truckType)}' />
								</p>
							</div>
							<div class="dynamic-flexItem ">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('sorting.truckNumber')}" />
								</p>
								<p class="flexItem">
									<s:property value="sorting.truckNo" />
								</p>
							</div>
							<div class="dynamic-flexItem ">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('sorting.driverName')}" />
								</p>
								<p class="flexItem">
									<s:property value="sorting.driverName" />
								</p>
							</div>
							<div class="dynamic-flexItem ">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('sorting.driverContact')}" />
								</p>
								<p class="flexItem">
									<s:property value="sorting.driverCont" />
								</p>
							</div>
					
				</div>
				
				<s:if test='#session.isAdmin =="true"'>
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

														<jsp:include page='/jsp/jsp/auditsortingDetail.jsp' />

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