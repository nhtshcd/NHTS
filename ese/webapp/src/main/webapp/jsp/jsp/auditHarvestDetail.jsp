<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.farmer')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farmCrops.farm.farmer.firstName" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.fcode')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farmCrops.farm.farmer.farmerId" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmId')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farmCrops.farm.farmCode" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.farm')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farmCrops.farm.farmName" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.block')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farmCrops.blockName" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.cropPlanting')}" />
	</p>
	<p class="flexItem">
	    <s:property value="#innerList[0].planting.plantingId" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.produceId')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].produceId" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.crop')}" />
	</p>
	<p class="flexItem">
	    <s:property value="findPlantingById(#innerList[0].planting.id).variety.name" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.variety')}" />
	</p>
	<p class="flexItem">
	<s:property value="findPlantingById(#innerList[0].planting.id).grade.name" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.date')}" />
	</p>
	<p class="flexItem">
		<s:date name="#innerList[0].date" format="dd-MM-yyyy" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.harvestType')}" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("harvestType"+#innerList[0].harvestType)}' />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("harvestTypeLabel"+harvest.harvestType)}' />
	</p>
	<p class="flexItem">
		<s:if test="#innerList[0].harvestType==0">
			<s:property
				value="%{getText('{0,number,#,##0.00}',{#innerList[0].noStems})}" />
		</s:if>
		<s:else>
			<s:property value="%{getText('{0,number,#,##0}',{#innerList[0].noStems})}" />
		</s:else>
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.qtyHarvested')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].qtyHarvested" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.yieldsHarvested')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].yieldsHarvested" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.expctdYieldsVolume')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].expctdYieldsVolume" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.nameHarvester')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].nameHarvester" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.harvestEquipment')}" />
	</p>
	<p class="flexItem">
		<s:property value="%{getCatalgueNameByCode(#innerList[0].harvestEquipment)}" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.noUnits')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].noUnits" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.packingUnit')}" />
	</p>
	<p class="flexItem">
		<s:property value="%{getCatalgueNameByCode(#innerList[0].packingUnit)}" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('harvest.observationPhi')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].observationPhi" />
	</p>
</div>
