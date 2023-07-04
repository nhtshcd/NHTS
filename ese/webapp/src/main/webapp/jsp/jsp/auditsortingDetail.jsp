<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.farmer')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.farmCrops.farm.farmer.firstName" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.fcode')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.farmCrops.farm.farmer.farmerId" />
	</p>
</div>


<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmId')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.farmCrops.farm.farmCode" />
	</p>
</div>


<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.farm')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.farmCrops.farm.farmName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.block')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.farmCrops.blockName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.blockId')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.farmCrops.blockId" />
	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('Planting ID')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.plantingId" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.crop')}" />
	</p>
	<p class="flexItem">
		<%--  <s:property value="#innerList[0].planting.variety.name"/> --%>
		 <s:property value="findPlantingById(#innerList[0].planting.id).variety.name"/>
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.variety')}" />
	</p>
	<p class="flexItem">
	<%-- <s:property value="#innerList[0].planting.grade.name" /> --%>
	<s:property value="findPlantingById(#innerList[0].planting.id).grade.name" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('Sorting Id')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].qrCodeId" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.cCenter')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.farmCrops.farm.farmName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.dateHarvested')}" />
	</p>
	<p class="flexItem">
		<s:property
			value="findCitywarehouseByPlantingId('harvestdate',#innerList[1].id)" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.qtyHarvested')}" />
	</p>
	<p class="flexItem">
		<s:property
			value="findCitywarehouseByPlantingId('qtyHarvested',#innerList[1].id)" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.qtyRejected')}" />
	</p>
	<p class="flexItem">
		<s:property
			value="getObjectIdFromTableByFieldIdAndRevId('sorting_aud','REJ_QTY',#innerList[1].id)" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.netQty')}" />
	</p>
	<p class="flexItem">
		<s:property
			value="getObjectIdFromTableByFieldIdAndRevId('sorting_aud','NET_QTY',#innerList[1].id)" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.truckType')}" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="#innerList[0].truckType" /> --%>
		<s:property value='%{getCatlogueValueByCodeArray(#innerList[0].truckType)}' />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.truckNumber')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].truckNo" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.driverName')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].driverName" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('sorting.driverContact')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].driverCont" />
	</p>
</div>
