<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<script>
	/* jQuery(document).ready(function() {
		var p1 = '<s:property value="#innerList[0].InsectsObserved" />';
		var p2 = '<s:property value="#innerList[0].diseaseObserved" />';
		var p3 = '<s:property value="scouting.weedsObserveds" />';
		if (p1 == 1) {
			$("#product1").text("Yes");
			$('.y1').removeClass('hide');
		} else if (p1 == 0) {
			$("#product1").text("No");
			$('.y1').addClass('hide');
		}
		if (p2 == 1) {
			$("#product2").text("Yes");
			$('.y2').removeClass('hide');
		} else if (p2 == 0) {
			$("#product2").text("No");
			$('.y2').addClass('hide');
		}
		if (p3 == 1) {
			$("#product3").text("Yes");
			$('.y3').removeClass('hide');
		} else if (p3 == 0) {
			$("#product3").text("No");
			$('.y3').addClass('hide');
		}
	}); */
</script>

<s:if test='#innerList[0].branchId==null'>
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
		<s:text name="scouting.receivedDate" />
	</p>
	<p class="flexItem">
		<s:date name="#innerList[0].receivedDate" format="dd/MM/yyyy" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmer.fcode" />
	</p>
	<p class="flexItem">
		<s:property
			value="#innerList[0].planting.farmCrops.farm.farmer.farmerId" />
	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="profiles.farmers" />
	</p>
	<p class="flexItem">
		<s:property
			value="#innerList[0].planting.farmCrops.farm.farmer.firstName" />
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
		<s:text name="blockId" />
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
		<s:text name="scouting.cropVariety" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].planting.grade.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('scouting.sprayingRequired')}" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getCatlogueValueByCodeArray(#innerList[0].sprayingRequired)}' />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="scouting.sctRecommendation" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].sctRecommendation" />
	</p>
</div>

<h2>
	<s:text name="info.insects" />
</h2>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="scouting.InsectsObserved" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("YesAndNo"+#innerList[0].InsectsObserved)}' />
		<%-- <s:property value="#innerList[0].InsectsObserved" /> --%>
	</p>
</div>

<s:if test="%{#innerList[0].InsectsObserved.equalsIgnoreCase('1')}">
	<div class="dynamic-flexItem">
		<p class="flexItem">
			<s:text name="scouting.nameOfInsectsObserved" />
		</p>
		<p class="flexItem">
			<s:property
				value='%{getCatlogueValueByCodeArray(#innerList[0].nameOfInsectsObserved)}' />
		</p>
	</div>

	<div class="dynamic-flexItem ">
		<p class="flexItem">
			<s:text name="scouting.perOrNumberInsects" />
		</p>
		<p class="flexItem">
			<s:property value="#innerList[0].perOrNumberInsects" />
		</p>
	</div>
</s:if>
<h2>
	<s:text name="info.disease" />
</h2>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="scouting.diseaseObserved" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("YesAndNo"+#innerList[0].diseaseObserved)}' />
	</p>
</div>
<s:if test="%{#innerList[0].diseaseObserved.equalsIgnoreCase('1')}">
	<div class="dynamic-flexItem ">
		<p class="flexItem">
			<s:text name="scouting.nameOfDisease" />
		</p>
		<p class="flexItem">
			<s:property
				value='%{getCatlogueValueByCodeArray(#innerList[0].nameOfDisease)}' />
		</p>
	</div>

	<div class="dynamic-flexItem ">
		<p class="flexItem">
			<s:text name="scouting.perInfection" />
		</p>
		<p class="flexItem">
			<s:property value="#innerList[0].perInfection" />
		</p>
	</div>
</s:if>
<h2>
	<s:text name="info.weedsObserveds" />
</h2>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="scouting.weedsObserveds" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("YesAndNo"+#innerList[0].weedsObserveds)}' />
	</p>
</div>
<s:if test="%{#innerList[0].weedsObserveds.equalsIgnoreCase('1')}">
	<div class="dynamic-flexItem ">
		<p class="flexItem">
			<s:text name="scouting.nameOfWeeds" />
		</p>
		<p class="flexItem">
			<s:property
				value='%{getCatlogueValueByCodeArray(#innerList[0].nameOfWeeds)}' />
		</p>
	</div>

	<div class="dynamic-flexItem ">
		<p class="flexItem">
			<s:text name="scouting.weedsPresence" />
		</p>
		<p class="flexItem">
			<s:property value="#innerList[0].weedsPresence" />
		</p>
	</div>

	<div class="dynamic-flexItem ">
		<p class="flexItem">
			<s:text name="scouting.recommendations" />
		</p>
		<p class="flexItem">
			<s:property value="#innerList[0].recommendations" />
		</p>
	</div>
</s:if>
<h2>
	<s:text name="info.irrigation" />
</h2>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="scouting.sourceOfWater" />
	</p>
	<p class="flexItem">
		<s:property
			value="%{getCatalgueNameByCode(#innerList[0].sourceOfWater)}" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<%-- <s:text name="scouting.irrigationType" /> --%>
		<s:property value="%{getLocaleProperty('scouting.irrigationType')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].irrigationType" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="scouting.irrigationMethod" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getCatlogueValueByCodeArray(#innerList[0].irrigationMethod)}' />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<%-- <s:text name="scouting.areaIrrrigated" /> --%>
		<s:property value="%{getLocaleProperty('scouting.areaIrrrigated')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].areaIrrrigated" />
	</p>
</div>
