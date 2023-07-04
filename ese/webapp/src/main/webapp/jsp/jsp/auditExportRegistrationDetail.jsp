<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>


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
		<s:text name="exporterRegistr.cmpyName1" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="exporterRegistr.cemai" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].tin" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="export.associationMemb" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("associationMembertype"+#innerList[0].cmpyOrientation)}' />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="export.krapin" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].regNumber" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="KRApin.expDate" />
	</p>
	<p class="flexItem">
		<s:date name="#innerList[0].expireDate" format="dd-MM-yyyy" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="export.legalstatus" />
	</p>
	<p class="flexItem">
		<s:property
			value='%{getLocaleProperty("legalStatustype"+#innerList[0].packhouse)}' />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="export.license" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].refLetterNo" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="export.cropcat" />
	</p>
	<p class="flexItem">
		<s:property
			value="getCropHierarchyById('procurement_product',#innerList[0].ugandaExport)" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmer.fcropName" />
	</p>
	<p class="flexItem">
		<s:property
			value="getCropHierarchyById('procurement_variety',#innerList[0].farmerHaveFarms)" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="export.cropvariety" />
	</p>
	<p class="flexItem">
		<s:property
			value="getCropHierarchyById('procurement_grade',#innerList[0].scattered)" />
	</p>
</div>

<s:if test="#innerList[0].scattered=='Others'">
	<div class="dynamic-flexItem">
		<p class="flexItem">
			<s:text name="farmCrops.ifOthers" />
		</p>
		<p class="flexItem">
			<s:property value="#innerList[0].otherScattered" />
		</p>
	</div>
</s:if>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="export.crophs" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].packGpsLoc" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="premises.postalAddr" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].expTinNumber" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="country.name" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getLocationHierarchyById('country',#innerList[0].village.id)" /> --%>
		<s:property
			value="#innerList[0].village.city.locality.state.country.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="county.name" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getLocationHierarchyById('state',#innerList[0].village.id)" /> --%>
		<s:property value="#innerList[0].village.city.locality.state.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="subcountry.name" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getLocationHierarchyById('location_detail',#innerList[0].village.id)" /> --%>
		<s:property value="#innerList[0].village.city.locality.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="ward.name" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getLocationHierarchyById('city',#innerList[0].village.id)" /> --%>
		<s:property value="#innerList[0].village.city.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="village.name" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].village.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="export.nationalId" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].rexNo" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="export.applicantName" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farmToPackhouse" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="exporterRegistr.gender" />
	</p>
	<p class="flexItem" id="gender">
		<s:property
			value='%{getLocaleProperty("genderStatus"+#innerList[0].packToExitPoint)}' />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="exporterRegistr.phn" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].mobileNo" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="exporterRegistr.email" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].email" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">HCD Status</p>
	<p class="flexItem">
		<s:if test='#innerList[0].status==1'>
			<s:text name="statusValue1" />
		</s:if>
		<s:else>
			<s:text name="statusValue2" />
		</s:else>
	</p>
</div>
