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
	function popDownload(type) {
		document.getElementById("loadId").value = type;

		$('#fileDownload').submit();

	}
</script>
<body>
	<s:form name="recallingForm" cssClass="fillform" method="post"
		action="recalling_%{command}" id="recallingForm">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="recalling.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
			<div class="aPanel farmer_info">
				<div class="aTitle">
				<h2>
					<s:text name="info.recalling" />
					<div class="pull-right">
											<a class="aCollapse" href="#"><i
												class="fa fa-chevron-right"></i></a>
										</div>
				</h2>
				</div>
				<div class="aContent ">
				<div class="flexform">
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('recalling.treacibilitycode')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.kenyaTraceCode" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
						Recalling Batch Number
						</p>
							<p class="flexItem">
								<s:property value="recalling.batchNo" />
							</p>
						</div>
						</div>
					<div class="flexform">
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							Buyer Name
						</p>
						<p class="flexItem">
							<s:property value="custname" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('shipment.shipmentDestination')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.shipmentDestination" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							Exporter
						</p>
						<p class="flexItem">
							<s:property value="exportername" />
						</p>
					</div>
					</div>
					<div class="" style="overflow: auto;">
				<table class="table table-bordered aspect-detail"
						id="shipmentInfoTable">
						<thead>
							<tr>
							<th>Farmer Name</th>
							
								<th><s:property
										value="%{getLocaleProperty('shipment.lotNo')}" /></th>
								<th>Block Id</th>
								<th>Block Name</th>
								<th>Planting</th>
								<th><s:property
										value="%{getLocaleProperty('shipment.product')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.variety')}" /></th>
								<th><s:property value="%{getLocaleProperty('incomingDate')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.packingUnit')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.packingQty')}" /></th>
								</tr>
						</thead>
					<tbody id="shipmentContent">
							
							<s:iterator value="recalling.recallDetails" status="incr" id="ShipmentDetailse">
								<tr id="row<s:property	value="#incr.index" />">
								<td class="lotNo"><s:property value="farmcrops.farm.farmer.firstName" /> - <s:property value="farmcrops.farm.farmer.farmerId" /></td>
								
								<td class="lotNo"><s:property value="lotNo" /></td>
									<td class="lotNo"><s:property value="batchNo" /></td>
									<td class="lotNo"><s:property value="farmcrops.blockName" /></td>
									 <td class="planting"><s:property value="planting.plantingId" /></td>
									<td class="product"><s:property
											value="planting.variety.name" /></td>
									<td class="variety"><s:property
											value="planting.grade.name" /></td>
									<td class="createdDate">
									<s:date name="shipmentdetail.shipment.shipmentDate" format="dd-MM-yyyy"/></td>
									<td class="packingUnit"><s:property value="%{getCatalgueNameByCode(receivedUnits)}" /></td>
									<td class="packingQty"><s:property value="receivedWeight" /></td>
									
								
								</tr>
							</s:iterator>
						</tbody>
					</table>
					</div>
				<div class="flexform">
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('recalling.recDate')}" />
						</p>
						<p class="flexItem">
							<s:property value="recDate" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('recalling.recEntity')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.recEntity" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('recalling.recCoordinatorName')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.recCoordinatorName" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('recalling.recCoordinatorContact')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.recCoordinatorContact" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('recalling.recNature')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.recNature" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('recalling.recReason')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.recReason" />
						</p>
					</div>
					
					<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:text name="recall.evidence" />
													</p>
													<s:if test="recalling.attachment!=null">
														<p class="flexItem">
															<button type="button" class="fa fa-download"
																style="background-color: transparent"
																onclick="popDownload(<s:property value="recalling.attachment"/>)">
														</p>
													</s:if>
												</div>
				</div>
			</div>
			
			<div class="formContainerWrapper aContent ">
				<h2>
					<s:text name="info.operatorInfo" />
				</h2>
				<div class="flexform">
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('recalling.operatorName')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.operatorName" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('recalling.contactNo')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.contactNo" />
						</p>
					</div>
				</div>
			</div>
			<div class="formContainerWrapper aContent ">
				<h2>
					<s:text name="info.shippingInfo" />
				</h2>
				<div class="flexform">
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('recalling.po')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.po" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('recalling.invoiceNo')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.invoiceNo" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('recalling.carrierNo')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.carrierNo" />
						</p>
					</div>
					<%-- <div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('recalling.productId')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.procurementVariety.procurementProduct.id" />
						</p>
					</div> --%>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('recalling.actionByRecaller')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.actionByRecaller" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('recalling.actionByStakeholders')}" />
						</p>
						<p class="flexItem">
							<s:property value="recalling.actionByStakeholders" />
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
										<jsp:include page='/jsp/jsp/auditRecallingDetail.jsp' />
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
	<s:form id="fileDownload" action="recalling_populateDownload">
					<s:hidden id="loadId" name="idd" />


				</s:form>
</body>
