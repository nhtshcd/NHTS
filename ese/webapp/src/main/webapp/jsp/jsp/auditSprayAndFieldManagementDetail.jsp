<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>

<s:if test='#innerList[0].branchId==null'>
	<div class="dynamic-flexItem">
		<p class="flexItem">
			<s:text name="app.branch" />
		</p>
		<p class="flexItem">
			<s:property
				value="%{getBranchName(#innerList[0].branchId)}" />
		</p>
	</div>
</s:if>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmer.fcode" />
	</p>
	<p class="flexItem">
			<s:property value="#innerList[0].planting.farmCrops.farm.farmer.farmerId" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="profiles.farmers" />
	</p>
	<p class="flexItem">
	    <s:property value="#innerList[0].planting.farmCrops.farm.farmer.firstName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmCode" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.farmCrops.farm.farmCode" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="profile.farm" />
	</p>
	<p class="flexItem">
	    <s:property value="#innerList[0].planting.farmCrops.farm.farmName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="block" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.farmCrops.blockName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="blockIds" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.farmCrops.blockId" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="Planting ID" />
	</p>
	<p class="flexItem">
	    <s:property value="#innerList[0].planting.plantingId" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="CropVariety" />
	</p>
	<p class="flexItem">
	    <s:property value="#innerList[0].planting.variety.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.grade" />
	</p>
	<p class="flexItem">
	    <s:property value="#innerList[0].planting.grade.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.dateSpray" />
	</p>
	<p class="flexItem">
		<s:date name="#innerList[0].dateOfSpraying" format="dd/MM/yyyy" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.endDateSpray" />
	</p>
	<p class="flexItem">
		<s:date name="#innerList[0].endDateSpray" format="dd/MM/yyyy" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.pcbp" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].pcbp.tradeName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.dosage" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].dosage" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.uom" />
	</p>
	<p class="flexItem">
		<s:property
			value="%{getCatalgueNameByCode(#innerList[0].uom)}" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.activeIngredient" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].activeIngredient" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('sprayAndFieldManagement.recommen')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].recommen" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.NameOfOperator" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].NameOfOperator" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.sprayeMobileNumber" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].sprayMobileNumber" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.operatorMedicalReport" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].operatorMedicalReport" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.typeApplicationEquipment" />
	</p>
	<p class="flexItem">
		<s:property
			value="%{getCatlogueValueByCodeArray(#innerList[0].typeApplicationEquipment)}" />
	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.methodOfApplication" />
	</p>
	<p class="flexItem">
		<s:property
			value="%{getCatlogueValueByCodeArray(#innerList[0].methodOfApplication)}" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.phi" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].phi" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.TrainingStatusOfSprayOperator" />
	</p>
	<p class="flexItem">
		<s:property
			value="%{getCatlogueValueByCodeArray(#innerList[0].trainingStatusOfSprayOperator)}" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.AgrovetOrSupplierOfTheChemical" />
	</p>
	<p class="flexItem">
		<s:property
			value="#innerList[0].agrovetOrSupplierOfTheChemical" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.LastDateOfCalibration" />
	</p>
	<p class="flexItem">
		<s:date name="#innerList[0].lastDateOfCalibration" format="dd/MM/yyyy" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sprayAndFieldManagement.insectTargeted" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].insectTargeted" />
	</p>
</div>
