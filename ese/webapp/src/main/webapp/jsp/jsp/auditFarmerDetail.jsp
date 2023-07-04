
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>

<div class="dynamic-flexItem hide">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.farmerId')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farmerId" />
		&nbsp;
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.category')}" />
	</p>
	<p class="flexItem">
		<s:if test="#innerList[0].fCat!=null && #innerList[0].fCat!=''">
			<s:property value='%{getLocaleProperty("fcat"+#innerList[0].fCat)}' />
		</s:if>

	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('Farm Ownership')}" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("farmercat"+#innerList[0].farmerCat)}' />

	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.farmerRegType')}" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("farmerRegType"+#innerList[0].farmerRegType)}' />

	</p>
</div>
<div class="dynamic-flexItem companySection hide">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.companyName')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].companyName" />
	</p>
</div>
<div class="dynamic-flexItem companySection hide">
	<p class="flexItem">
		<s:property
			value="%{getLocaleProperty('farmer.registrationCertificate')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].registrationCertificate" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.kraPin')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].kraPin" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.fname')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].firstName" />
		&nbsp;
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.fcode')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farmerId" />
		&nbsp;
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('exporterRegistr.gender')}" />
	</p>
	<p class="flexItem">
		<s:if test="#innerList[0].gender!=null && #innerList[0].gender!=''">
			<s:property
				value='%{getLocaleProperty("genderStatus"+#innerList[0].gender)}' />
		</s:if>
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.dateOfBirth')}" />
	</p>
	<p class="flexItem">
		<%-- <s:property value='dateOfBirth' /> --%>
		<s:date name="#innerList[0].dateOfBirth" format="dd/MM/yyyy" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.fage')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].age" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('export.nationalId')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].nid" />
	</p>
</div>




<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('exporterRegistr.phn')}" />
	</p>
	<p class="flexItem" name="#innerList[0].phoneNo">
		<s:property value="#innerList[0].mobileNo" />
	</p>
</div>




<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('export.cropcat')}" />
	</p>
	<p class="flexItem">
		<s:property
			value="getCropHierarchyById('procurement_product',#innerList[0].cropCategory)" />
	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem ">
		<s:property value="%{getLocaleProperty('cropName')}" />
	</p>
	<p class="flexItem">
		<s:property
			value="getCropHierarchyById('procurement_variety',#innerList[0].CropName)" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem ">
		<s:property value="%{getLocaleProperty('export.cropvariety')}" />
	</p>
	<p class="flexItem">
		<s:property
			value="getCropHierarchyById('procurement_grade',#innerList[0].cropVariety)" />
	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem dateName">
		<s:property value="%{getLocaleProperty('export.crophs')}" />
	</p>
	<p class="flexItem" name="#innerList[0].cropHscode">
		<s:property
			value="getCropHsCodeByProcurementProductId('procurement_grade',#innerList[0].cropVariety)" />

	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="country.name" />
	</p>
	<p class="flexItem">
		<s:property
			value="getLocationHierarchyCodeById('country',#innerList[0].village.id)" />
		&nbsp-&nbsp
		<s:property
			value="getLocationHierarchyById('country',#innerList[0].village.id)" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="county.name" />
	</p>
	<p class="flexItem">
		<s:property
			value="getLocationHierarchyCodeById('state',#innerList[0].village.id)" />
		&nbsp-&nbsp
		<s:property
			value="getLocationHierarchyById('state',#innerList[0].village.id)" />
	</p>

</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="subcountry.name" />
	</p>
	<p class="flexItem">
		<s:property
			value="getLocationHierarchyCodeById('location_detail',#innerList[0].village.id)" />
		&nbsp-&nbsp
		<s:property
			value="getLocationHierarchyById('location_detail',#innerList[0].village.id)" />
	</p>

</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="ward.name" />
	</p>
	<p class="flexItem">
		<s:property
			value="getLocationHierarchyCodeById('city',#innerList[0].village.id)" />
		&nbsp-&nbsp
		<s:property
			value="getLocationHierarchyById('city',#innerList[0].village.id)" />

	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="village.name" />
	</p>
	<p class="flexItem" name="selectedVillage">
		<s:property
			value="getCropHierarchyCodeById('village',#innerList[0].village.id)" />
		&nbsp-&nbsp
		<s:property
			value="getCropHierarchyById('village',#innerList[0].village.id)" />
	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.photo')}" />
	</p>
	<s:if test="#innerList[0].farmerPhoto!=null">
		<p class="flexItem">
			<button type="button" class="fa fa-download"
				style="background-color: transparent"
				onclick="popDownload(<s:property value="#innerList[0].farmerPhoto"/>)">
		</p>
	</s:if>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.nid')}" />
	</p>
	<s:if test="#innerList[0].photoNid!=null && #innerList[0].photoNid!=''">
		<p class="flexItem" name="#innerList[0].proof">
			<button type="button" class="fa fa-download"
				style="background-color: transparent"
				onclick="popDownload(<s:property value="#innerList[0].photoNid"/>)">
		</p>
	</s:if>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.latitude')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].latitude" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.longitude')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].longitude" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('exporterRegistr.email')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].emailId" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.family')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].noOfFamilyMember" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.adult')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].adultAbove" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.school')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].schoolGoingChild" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('farmer.childBelow')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].childBelow" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmer.edu" />
	</p>
	<p class="flexItem" id="hideeduca">
		<s:property value='%{getCatlogueValueByCodeArray(#innerList[0].hedu)}' />


	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmer.asset" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getCatlogueValueByCodeArray(#innerList[0].asset)}' />

	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmer.house" />
	</p>
	<p class="flexItem" id="product">
	    <s:if test="#innerList[0].house!=null && #innerList[0].house!=''">
		<s:property value="%{getLocaleProperty('ownHouse'+#innerList[0].house)}" />
        </s:if>
	</p>
</div>


<div class="dynamic-flexItem y1 hide">
	<p class="flexItem">
		<s:text name="farmer.owner" />
	</p>
	<p class="flexItem">

		<s:property
			value='%{getCatlogueValueByCodeArray(#innerList[0].ownership)}' />
	</p>
</div>

