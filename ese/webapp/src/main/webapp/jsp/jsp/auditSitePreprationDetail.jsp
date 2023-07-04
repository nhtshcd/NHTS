<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmer.fcode" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farm.farmer.farmerId" />
		<%--  <s:property value="getDataByTableFieldByRev('farmer','site_prepration_aud','FARM_ID',#innerList[1].id,'FARMER_ID')" /> --%>
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="profiles.farmers" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('farmer','site_prepration_aud','FARM_ID',#innerList[1].id,'FIRST_NAME')" /> --%>
		<s:property value="#innerList[0].farm.farmer.firstName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmCode" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('farm','site_prepration_aud','FARM_ID',#innerList[1].id,'FARM_CODE')" /> --%>
		<s:property value="#innerList[0].farm.farmCode" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="profile.farm" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('farm','site_prepration_aud','FARM_ID',#innerList[1].id,'FARM_NAME')" /> --%>
		<s:property value="#innerList[0].farm.farmName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="Block Name" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('farm_crops','site_prepration_aud','FARM_CROPS_ID',#innerList[1].id,'BLOCK_NAME')" /> --%>
		<s:property value="#innerList[0].farmCrops.blockName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="Block Id" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('farm_crops','site_prepration_aud','FARM_CROPS_ID',#innerList[1].id,'BLOCK_ID')" /> --%>
		<s:property value="#innerList[0].farmCrops.blockId" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sitePrepration.crop" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('procurement_variety','site_prepration_aud','CROP_ID',#innerList[1].id,'NAME')" /> --%>
		<s:property value="#innerList[0].previousCrop.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sitePrepration.env" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("YesAndNo"+#innerList[0].environmentalAssessment)}' />
	</p>
</div>
<s:if
	test="%{#innerList[0].environmentalAssessment.equalsIgnoreCase('1')}">
	<div class="dynamic-flexItem ">
		<p class="flexItem  ">
			<s:text name="%{getLocaleProperty('sitePrepration.env.report')}" />
		</p>
		<p class="flexItem">
			<s:if
				test="#innerList[0].environmentalAssessmentReport!=null && #innerList[0].environmentalAssessmentReport!=''">
				<button type="button" class="fa fa-download"
					style="background-color: transparent"
					onclick="popDownload(<s:property value="#innerList[0].environmentalAssessmentReport"/>)"></button>
			</s:if>
		</p>
	</div>
</s:if>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="sitePrepration.soc" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("YesAndNo"+#innerList[0].socialRiskAssessment)}' />
	</p>
</div>
<s:if test="%{#innerList[0].socialRiskAssessment.equalsIgnoreCase('1')}">
	<div class="dynamic-flexItem">
		<p class="flexItem">
			<s:text name="%{getLocaleProperty('sitePrepration.soc.report')}" />
		</p>
		<p class="flexItem">
			<s:if
				test="#innerList[0].socialRiskAssessmentReport!=null && #innerList[0].socialRiskAssessmentReport!=''">
				<button type="button" class="fa fa-download"
					style="background-color: transparent"
					onclick="popDownload(<s:property value="#innerList[0].socialRiskAssessmentReport"/>)"></button>
			</s:if>
		</p>
	</div>
</s:if>
<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:text name="sitePrepration.soil" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("YesAndNo"+#innerList[0].soilAnalysis)}' />
	</p>
</div>
<s:if test="%{#innerList[0].soilAnalysis.equalsIgnoreCase('1')}">
	<div class="dynamic-flexItem">
		<p class="flexItem">
			<s:text name="%{getLocaleProperty('sitePrepration.soil.report')}" />
		</p>
		<p class="flexItem">
			<s:if
				test="#innerList[0].soilAnalysisReport!=null && #innerList[0].soilAnalysisReport!=''">
				<button type="button" class="fa fa-download"
					style="background-color: transparent"
					onclick="popDownload(<s:property value="#innerList[0].soilAnalysisReport"/>)"></button>
			</s:if>
		</p>
	</div>
</s:if>
<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:text name="sitePrepration.water" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("YesAndNo"+#innerList[0].waterAnalysis)}' />
	</p>
</div>
<s:if test="%{#innerList[0].waterAnalysis.equalsIgnoreCase('1')}">
	<div class="dynamic-flexItem ">
		<p class="flexItem">
			<s:text name="%{getLocaleProperty('sitePrepration.water.report')}" />
		</p>
		<p class="flexItem">
			<s:if
				test="#innerList[0].waterAnalysisReport!=null && #innerList[0].waterAnalysisReport!=''">
				<button type="button" class="fa fa-download"
					style="background-color: transparent"
					onclick="popDownload(<s:property value="#innerList[0].waterAnalysisReport"/>)"></button>
			</s:if>
		</p>
	</div>
</s:if>
