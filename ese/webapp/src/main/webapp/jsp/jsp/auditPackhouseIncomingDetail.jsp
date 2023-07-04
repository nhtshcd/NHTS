<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('packhouseIncoming.offLoadingDate')}" />
	</p>
	<p class="flexItem">
		<s:date name="#innerList[0].offLoadingDate" format="dd-MM-yyyy" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('packhouseIncoming.packhouse')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].packhouse.name"/>
	</p>
</div>

<div class="" style="overflow: auto;">
	<table class="table table-bordered aspect-detail"
		id="packhouseIncomingInfoTable">
		<thead>
			<tr>
				<th><s:property
						value="%{getLocaleProperty('packhouseIncoming.blockNo')}" /><sup
					style="color: red;">*</sup></th>
				<th><s:property
						value="%{getLocaleProperty('packhouseIncoming.blockId')}" /><sup
					style="color: red;">*</sup></th>
				<th><s:property value="%{getLocaleProperty('Planting ID')}" /><sup
					style="color: red;">*</sup></th>
				<th><s:property value="%{getLocaleProperty('Sorting ID')}" /><sup
					style="color: red;">*</sup></th>
				<th><s:property
						value="%{getLocaleProperty('packhouseIncoming.product')}" /><sup
					style="color: red;">*</sup></th>
				<th><s:property
						value="%{getLocaleProperty('packhouseIncoming.variety')}" /><sup
					style="color: red;">*</sup></th>
				<th><s:property value="%{getLocaleProperty('sort.sortDate')}" /></th>
				<th><s:property value="%{getLocaleProperty('transwieghkg')}" /><sup
					style="color: red;">*</sup></th>
				<th><s:property
						value="%{getLocaleProperty('packhouseIncoming.recievedWeight')}" /><sup
					style="color: red;">*</sup></th>
				<th><s:property value="%{getLocaleProperty('losswightkg')}" /><sup
					style="color: red;">*</sup></th>
				<th><s:property
						value="%{getLocaleProperty('packhouseIncoming.recievedUnits')}" /><sup
					style="color: red;">*</sup></th>
				<th><s:property
						value="%{getLocaleProperty('packhouseIncoming.noOfUnits')}" /><sup
					style="color: red;">*</sup></th>
			</tr>
		</thead>
		<tbody id="packhouseIncomingContent">
			<s:iterator value="#innerList[0].packhouseIncomingDetails"
				status="incr">
				<tr id="row<s:property	value="#incr.index" />">
					<s:hidden class="incoId" value="%{id}" />
					<td class="blockNo hide"><s:property value="farmcrops.id" /></td>
					<td class="product hide"><s:property value="cw.farmcrops.variety.procurementProduct.id" /></td>
					<td class="variety hide"><s:property value="cw.farmcrops.variety.id" /></td>

					<td class="product"><s:property value="farmcrops.blockName" /></td>
					<td class="blockId"><s:property value="farmcrops.blockId" /></td>
					<td class="blockId"><s:property value="planting.plantingId" /></td>
					<td class="qrCodeId"><s:property value="qrCodeId" /></td>
					<td class="variety"><s:property value="planting.variety.name" /></td>
					<td class=""><s:property value="planting.grade.name" /></td>
					<td class=""><s:date name="getSortingByQRCodeId(qrCodeId).createdDate" format="dd-MM-yyyy" /></td>
					<td class="transferWeight"><s:property value="transferWeight" /></td>
					<td class="recievedWeight"><s:property value="receivedWeight" /></td>
					<td class="remainsWeight"><s:property value="totalWeight" /></td>
					<td class="recievedUnits hide"><s:property value="receivedUnits" /></td>
					<td class=""><s:property value="%{getCatalgueNameByCode(receivedUnits)}" /></td>
					<td class="noUnits"><s:property value="noUnits" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('packhouseIncoming.totalWeight')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].totalWeight" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('packing.BatchNo')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].batchNo" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('packhouseIncoming.truckType')}" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="#innerList[0].truckType" /> --%>
		<s:property value='%{getCatlogueValueByCodeArray(#innerList[0].truckType)}' />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('packhouseIncoming.truckNumber')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].truckNo" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('packhouseIncoming.driverName')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].driverName" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('packhouseIncoming.driverContact')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].driverCont" />
	</p>
</div>
