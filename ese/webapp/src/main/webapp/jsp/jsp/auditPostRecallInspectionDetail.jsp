<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>

<s:if test='branchId==null'>
	<div class="dynamic-flexItem">
		<p class="flexItem">
			<s:text name="app.branch" />
		</p>
		<p class="flexItem">
			<s:property value="%{getBranchName(#innerList[0].branchId)}" />
		</p>
	</div>
</s:if>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="inspDate" />
	</p>
	<p class="flexItem">
		<s:date name="#innerList[0].inspectionDate" format="dd-MM-yyyy" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">UCR Kentrade</p>
	<p class="flexItem">
<%-- 		<s:property value="getDataByTableFieldByRev('recalling','post_recall_inspection_aud','RECALL_ID',#innerList[1].id,'KENYA_TRACE_CODE')" /> --%>
		<s:property value="#innerList[0].recall.kenyaTraceCode" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">Recalling Batch Number</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('recalling','post_recall_inspection_aud','RECALL_ID',#innerList[1].id,'BATCH_NO')" /> --%>
		<s:property value="#innerList[0].recall.batchNo" />
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
				<th><s:property value="%{getLocaleProperty('shipment.lotQty')}" /></th>
			</tr>
		</thead>
		<tbody id="shipmentContent">
			<s:iterator value="findRecallingById(#innerList[0].recall.id).recallDetails" status="incr"
				id="ShipmentDetailse">
				<tr id="row<s:property	value="#incr.index" />">
					<td class="lotNo"><s:property
							value="farmcrops.farm.farmer.firstName" /> - <s:property
							value="farmcrops.farm.farmer.farmerId" /></td>
					<td class="lotNo"><s:property value="lotNo" /></td>
					<td class="lotNo"><s:property value="batchNo" /></td>
					<td class="lotNo"><s:property value="farmcrops.blockName" /></td>
					<td class="plantingId"><s:property value="planting.plantingId" /></td>
					<td class="product"><s:property value="planting.variety.name" /></td>
					<td class="variety"><s:property value="planting.grade.name" /></td>
					<td class="createdDate"><s:date name="recall.recDate"
							format="dd-MM-yyyy" /></td>
					<td class="packingUnit"><s:property
							value="%{getCatalgueNameByCode(receivedUnits)}" /></td>
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
		<s:property value="#innerList[0].nameOfAgency" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="postrecall.inspector" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].nameOfInspector" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="postrecall.inspmobile" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].mobileNumber" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="postrecall.oprator" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getCatlogueValueByCodeArray(#innerList[0].operatorBeingInspected)}' />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="postrecall.nature" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="#innerList[0].natureOfRecall" /> --%>
			<s:property value="%{getLocaleProperty('recNature'+#innerList[0].natureOfRecall)}" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="postrecall.manag" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getCatlogueValueByCodeArray(#innerList[0].managementOfRecalled)}' />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="postrecall.recall" />
	</p>
	<p class="flexItem">
		<s:property value='#innerList[0].recallReport' />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="postrecall.correc" />
	</p>
	<p class="flexItem">
		<s:property value='#innerList[0].correctiveAction' />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="postrecall.recom" />
	</p>
	<p class="flexItem">
		<s:property
			value="%{getCatalgueNameByCode(#innerList[0].recommendation)}" />
	</p>
</div>
