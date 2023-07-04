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
							idd = '<s:property value="postRecallInspection.id"/>';
							var status = '<s:property value="postRecallInspection.status"/>';
							
							$(".breadCrumbNavigation").find('li:last').find(
							'a:first').attr("href",
							'<s:property value="redirectContent" />');
							});
							
							function onCancel() {
								window.location.href = "<s:property value="redirectContent" />";

							}
							
							</script>
	<s:form name="form">
		<s:hidden key="currentPage" />
		<s:hidden key="postRecallInspection.id" id="id" class='uId' />
		<s:hidden key="command" />
		<s:hidden key="redirectContent" />
		<!-- <div class="flex-view-layout">
			<div class="fullwidth">
				<div class="flexWrapper"> -->
					<div class="flexLeft appContentWrapper">
						<%-- <div class="formContainerWrapper dynamic-form-con">
							<h2>
								<s:property value="%{getLocaleProperty('info.postRecall')}" />
							</h2> --%>
							<div class="formContainerWrapper">
							<div class="aPanel farmer_info">
								<div class="aTitle">
									<h2>
										<s:property value="%{getLocaleProperty('info.postRecall')}" />
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
											value="%{getBranchName(postRecallInspection.branchId)}" />
									</p>
								</div>
							</s:if>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="inspDate" />
								</p>
								<p class="flexItem">
									<s:property value="inspectionDate" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									UCR Kentrade
								</p>
								<p class="flexItem">
									<s:property value="selectedLotNo" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									Recalling Batch Number
								</p>
								<p class="flexItem">
									<s:property value="postRecallInspection.recall.batchNo" />
								</p>
							</div>
<div class="" style="overflow: auto;">
	<table class="table table-bordered aspect-detail"
						id="shipmentInfoTable">
						<thead>
							<tr>
							<th>Farmer Name</th>
							<th>Lot Number</th>
								<th>Block Id</th>
								<th>Block Name</th>
								<th>Planting ID</th>
								<th><s:property
										value="%{getLocaleProperty('shipment.product')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.variety')}" /></th>
										<th><s:property
										value="%{getLocaleProperty('recallingDate.createdDate')}" /></th>
										<th>Unit</th>
								<th><s:property
										value="%{getLocaleProperty('shipment.lotQty')}" /></th>
							</tr>
						</thead>
						<tbody id="shipmentContent">

							<s:iterator value="recalling.recallDetails" status="incr" id="ShipmentDetailse">
								<tr id="row<s:property	value="#incr.index" />">
								<td class="lotNo"><s:property value="farmcrops.farm.farmer.firstName" /> - <s:property value="farmcrops.farm.farmer.farmerId" /></td>
								
								<td class="lotNo"><s:property value="lotNo" /></td>
									<td class="lotNo"><s:property value="batchNo" /></td>
									<td class="lotNo"><s:property value="farmcrops.blockName" /></td>
									<td class="plantingId"><s:property value="planting.plantingId" /></td>
									
									<td class="product"><s:property
											value="planting.variety.name" /></td>
									<td class="variety"><s:property
											value="planting.grade.name" /></td>
											
									<td class="createdDate"><s:date name="recall.recDate" format="dd-MM-yyyy"/></td>
									<td class="packingUnit"><s:property value="%{getCatalgueNameByCode(receivedUnits)}" /></td>
									<td class="packingQty"><s:property value="receivedWeight" /></td>
									
								
								</tr>
							</s:iterator>
						</tbody>
					</table>
					</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="postrecall.agencyName" />
								</p>
								<p class="flexItem">
									<s:property value="postRecallInspection.nameOfAgency" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="postrecall.inspector" />
								</p>
								<p class="flexItem">
									<s:property value="postRecallInspection.nameOfInspector" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="postrecall.inspmobile" />
								</p>
								<p class="flexItem">
									<s:property value="postRecallInspection.mobileNumber" />

								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="postrecall.oprator" />
								</p>
								<p class="flexItem">
									<s:property
										value='%{getCatlogueValueByCodeArray(postRecallInspection.operatorBeingInspected)}' />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="postrecall.nature" />
								</p>
								<p class="flexItem">
									<s:property value="postRecallInspection.natureOfRecall" />
								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="postrecall.manag" />
								</p>
								<p class="flexItem">
									<s:property
										value='%{getCatlogueValueByCodeArray(postRecallInspection.managementOfRecalled)}' />


								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="postrecall.recall" />
								</p>
								<p class="flexItem">
									<s:property value='postRecallInspection.recallReport' />

								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="postrecall.correc" />
								</p>
								<p class="flexItem">
									<s:property value='postRecallInspection.correctiveAction' />

								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="postrecall.recom" />
								</p>
								<p class="flexItem">
									<%-- <s:property value='%{getCatlogueValueByCodeArray(postRecallInspection.recommendation)}' /> --%>
									<s:property value="%{getCatalgueNameByCode(postRecallInspection.recommendation)}" />

								</p>
							</div>

						</div>
						</div>
<s:if test="roleID.trim().equalsIgnoreCase('2')">	
<s:iterator value="ex" var="innerList">
								<div class="aPanel audit_history">
									<div class="aTitle">
										<h2>
										<s:if
									test="#innerList[2].toString().trim().equalsIgnoreCase('ADD')">
									<s:property value="#innerList[0].createdUser" />
								</s:if>
								<s:else>
									<s:property value="#innerList[0].updatedUser" />
								</s:else>
								-

								<s:date name="#innerList[1].revisionDate"
									format="dd/MM/yyyy hh:mm:ss" />
								-
								<s:property
									value="%{getLocaleProperty('default'+#innerList[2])}" />
								<div class="pull-right">
									<a class="aCollapse "
										href="#<s:property value="#innerList[1].id" />
										"><i
										class="fa fa-chevron-right"></i></a>
								</div>
										</h2>
									</div>
									<div class="aContent dynamic-form-con"
										id="<s:property value="#innerList[1].id" />">
										<jsp:include page='/jsp/jsp/auditPostRecallInspectionDetail.jsp' />
									</div>
								</div>
							</s:iterator> 
							</s:if>
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
			<!-- 	</div>
			</div>
		</div> -->
	</s:form>
	<s:form id="fileDownload" action="agroChemicalDealer_populateDownload">
		<s:hidden id="loadId" name="idd" />
	
	</s:form>

</body>