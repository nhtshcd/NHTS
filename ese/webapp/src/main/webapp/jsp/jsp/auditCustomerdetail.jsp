<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>

<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
</head>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name='exporter' />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].exporter.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('customer.customerName')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].customerName" />
	</p>
</div>



<h2>
	<s:text name="info.location" />
</h2>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="country.name" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].country" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="county.name" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].county" />
	</p>

</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="subcountry.name" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].subCounty" />
	</p>

</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="ward.name" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].ward" />
	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('customer.customerAddress')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].customerAddress" />
	</p>
</div>


<h2>
	<s:text name="info.personal" />
</h2>
<!-- <div class="flexform"> -->
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('customer.personName')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].personName" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('customer.mobileNumber')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].mobileNo" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('customer.email')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].emailId" />
	</p>
</div>
