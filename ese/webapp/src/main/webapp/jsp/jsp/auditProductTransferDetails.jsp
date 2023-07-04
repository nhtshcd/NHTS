<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('Transfer Receipt ID')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].batchNo" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('productTransfer.date')}" />
	</p>
	<p class="flexItem">
		<s:date name="#innerList[0].date" format="dd-MM-yyyy" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('productTransfer.exporter')}" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('exporter_registration','PRODUCT_TRANSFER_aud','EXPORTER',#innerList[1].id,'COMPANY_NAME')" /> --%>
		<s:property value="#innerList[0].exporter.name" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('productTransfer.transferFrom')}" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('warehouse','PRODUCT_TRANSFER_aud','TRANSFER_FROM',#innerList[1].id,'NAME')" /> --%>
		<s:property value="#innerList[0].transferFrom.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('productTransfer.transferTo')}" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('warehouse','PRODUCT_TRANSFER_aud','TRANSFER_TO',#innerList[1].id,'NAME')" /> --%>
		<s:property value="#innerList[0].transferTo.name" />
	</p>
</div>

<table class="table table-bordered aspect-detail"
	id="productTransferInfoTable">
	<thead>
		<tr>
			<th><s:property
					value="%{getLocaleProperty('productTransfer.receptionBatchID')}" /></th>
			<th><s:property
					value="%{getLocaleProperty('productTransfer.blockID')}" /></th>
			<th><s:property
					value="%{getLocaleProperty('productTransfer.plantingID')}" /></th>
			<th><s:property
					value="%{getLocaleProperty('productTransfer.product')}" /></th>
			<th><s:property
					value="%{getLocaleProperty('productTransfer.variety')}" /></th>
			<th><s:property
					value="%{getLocaleProperty('productTransfer.availableWeight')}" /></th>
			<th><s:property
					value="%{getLocaleProperty('productTransfer.transferredWeight')}" /></th>
		</tr>
	</thead>
	<tbody id="productTransferContent">
		<s:iterator value="#innerList[0].productTransferDetails"
			status="incr">
			<tr id="row<s:property	value="#incr.index" />">
				<td class="receptionBatchIDTxt">
				<%-- <s:property value="ctt.batchNo" /> --%>
				<s:property value="findCitywarehouseByBatchNoPlantingIdAndCoOperative(batchNo,planting.id,#innerList[0].transferFrom.id)" />
				</td>
				<td class="receptionBatchID hide"><s:property value="ctt.id" /></td>
				<td class="blockIDTxt"><s:property value="blockId.blockId" />
					- <s:property value="blockId.blockName" /></td>
				<td class="blockID hide"><s:property value="blockId.id" /></td>
				<td class="plantingIDTxt"><s:property
						value="planting.plantingId" /></td>
				<td class="plantingID hide"><s:property value="planting.id" /></td>
				<td class="product"><s:property value="findPlantingById(planting.id).variety.name" /></td>
				<td class="variety"><s:property value="findPlantingById(planting.id).grade.name" /></td>
				<td class="availableWeight"><s:property
						value="availableWeight" /></td>
				<td class="transferredWeight"><s:property
						value="transferredWeight" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('productTransfer.truckNo')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].truckNo" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('productTransfer.driverName')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].driverName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('productTransfer.driverLicenseNumber')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].driverLicenseNumber" />
	</p>
</div>
