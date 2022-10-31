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
	<s:form name="shipmentForm" cssClass="fillform" method="post"
		action="shipment_%{command}" id="shipmentForm">
		<s:hidden name="shipmentDtl" id="shipmentDtl" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="shipment.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.shipment" />
				</h2>
				<div class="flexform">
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('shipment.shipmentDate')}" />
						</p>
						<p class="flexItem">
							<s:property value="shipmentDate" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('shipment.packhouse')}" />
						</p>
						<p class="flexItem">
							<s:property value="shipment.packhouse.name" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('shipment.expLicNo')}" />
						</p>
						<p class="flexItem">
							<s:property value="shipment.packhouse.exporter.refLetterNo" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('shipment.buyer')}" />
						</p>
						<p class="flexItem">
							<s:property value="shipment.customer.customerName" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('shipment.pConsignmentNo')}" />
						</p>
						<p class="flexItem">
							<s:property value="selectedPConsignmentNo" />
						</p>
					</div>
					<div class="dynamic-flexItem" style="width: 100%;">
						<p style="width: 25%;">
							<s:property
								value="%{getLocaleProperty('shipment.tracecode')}" />
						</p>
						<p >
							<s:property value="shipment.kenyaTraceCode" />
						</p>
					</div>
					<div class="" style="overflow: auto;">
					<table class="table table-bordered aspect-detail"
						id="shipmentInfoTable">
						<thead>
							<tr>
								<th><s:property
										value="%{getLocaleProperty('shipment.lotNo')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.block')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('Planting ID')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.product')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.variety')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('packing.packingDate')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.packingUnit')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.packingQty')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.recallingStatus')}" /></th>
							</tr>
						</thead>
						<tbody id="shipmentContent">
							<s:iterator value="shipment.shipmentDetails" status="incr">
								<tr id="row<s:property	value="#incr.index" />">
									<td class="lotNo"><s:property
											value="cityWarehouse.batchNo" /></td>
									<%-- <td class="product"><s:property
											value="cityWarehouse.farmcrops.blockId" /></td> --%>


									<td class="product">
									<s:property value="cityWarehouse.farmcrops.blockId"/>
										-
									<s:property value="cityWarehouse.farmcrops.blockName"/>
										</td>

                                    <td class="plantingId"><s:property
											value="cityWarehouse.planting.plantingId" /></td>

									<td class="product"><s:property
											value="cityWarehouse.planting.variety.name" /></td>
									<td class="variety"><s:property
											value="cityWarehouse.planting.grade.name" /></td>
									<td class="createdDate">
									<s:date name="sr.packingDate" format="dd-MM-yyyy" /></td>
									<td class="packingUnit"><s:property
											value="%{getCatalgueNameByCode(packingUnit)}" /></td>
									<td class="packingQty"><s:property value="packingQty" /></td>
									
									<td class="recallingstatus">
									<s:if test='"1".equalsIgnoreCase(recallingstatus)'>
											<p class="flexItem" id="recallingstatus" style="font-weight: bold;">Withdrawn</p>
											
									</s:if> 
									<s:else>
											<p class="flexItem" id="recallingstatus" style="font-weight: bold;">Shipped</p>
									</s:else></td>

								</tr>
							</s:iterator>
						</tbody>
					</table>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('shipment.totalShipmentQty')}" />
						</p>
						<p class="flexItem">
							<s:property value="shipment.totalShipmentQty" />
						</p>
					</div>

				</div>
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
