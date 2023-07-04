<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>




<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('shipment.shipmentDate')}" />
	</p>
	<p class="flexItem">
	<s:date name="#innerList[0].shipmentDate" format="dd/MM/yyyy" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('shipment.packhouse')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].packhouse.name" />
	</p>
</div>
<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('shipment.expLicNo')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].packhouse.exporter.refLetterNo" />
	</p>
</div>
<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('shipment.buyer')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].customer.customerName" />
	</p>
</div>
<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('shipment.shipmentDestination')}" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="shipment.shipmentDestination" /> --%>
		<s:property value="findShipmentDestinationById(#innerList[0].customer.id)" />
	</p>
</div>
<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('shipment.pConsignmentNo')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].pConsignmentNo" />
	</p>
</div>
<div class="dynamic-flexItem" style="width: 100%;">
	<p style="width: 25%;">
		<s:property value="%{getLocaleProperty('shipment.tracecode')}" />
	</p>
	<p>
		<s:property value="#innerList[0].kenyaTraceCode" />
	</p>
</div>
 <div class="" style="overflow: auto;"> 
	<table class="table table-bordered aspect-detail"
		id="shipmentInfoTable">
		<thead>
			<tr>
				<th><s:property value="%{getLocaleProperty('shipment.lotNo')}" /></th>
				<th><s:property value="%{getLocaleProperty('shipment.block')}" /></th>
				<th><s:property value="%{getLocaleProperty('Planting ID')}" /></th>
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
			 <s:iterator value="#innerList[0].shipmentDetails" status="incr">
				<tr id="row<s:property	value="#incr.index" />">
					<td class="lotNo"><s:property value="cityWarehouse.batchNo" /></td>
					<td class="product"><s:property
							value="cityWarehouse.farmcrops.blockId" /> - <s:property
							value="cityWarehouse.farmcrops.blockName" /></td>

					<td class="plantingId"><s:property
							value="cityWarehouse.planting.plantingId" /></td>

					<td class="product"><s:property
							value="cityWarehouse.planting.variety.name" /></td>
					<td class="variety"><s:property
							value="cityWarehouse.planting.grade.name" /></td>
					<td class="createdDate"><s:date name="getPackingByBatchNo(cityWarehouse.batchNo).packingDate"
							format="dd-MM-yyyy" /></td>
					<td class="packingUnit"><s:property
							value="%{getCatalgueNameByCode(packingUnit)}" /></td>
					<td class="packingQty"><s:property value="packingQty" /></td>

					<td class="recallingstatus"><s:if
							test='"1".equalsIgnoreCase(recallingstatus)'>
							<p class="flexItem" id="recallingstatus"
								style="font-weight: bold;">Withdrawn</p>

						</s:if> <s:else>
							<p class="flexItem" id="recallingstatus"
								style="font-weight: bold;">Shipped</p>
						</s:else></td>

				</tr>
			</s:iterator> 
		</tbody>
	</table>
 </div> 
<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('shipment.totalShipmentQty')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].totalShipmentQty" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('Shipment Supporting Files')}" />
	</p>
	<s:if test="#innerList[0].shipmentSupportingFiles!=null">
		<p class="flexItem">
			<a class='fa fa-download pdfIc'
				href='shipment_downloadMultipleImagesBasedOnDocumentId?idd=<s:property value="#innerList[0].shipmentSupportingFiles"/>&ids=<s:property value="#innerList[0].id"/>'
				title='Download'></a>
		</p>
	</s:if>
</div>