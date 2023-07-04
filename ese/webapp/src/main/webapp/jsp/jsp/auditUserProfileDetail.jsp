<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ page import="java.util.Base64"%>
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
		<s:text name="userProfile.username" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].username" />
	</p>
</div>

<h2>
	<s:text name="info.personal" />
</h2>
<div class="dynamic-flexItem">
	<s:if test='#innerList[0].persInfo.image!=null && #innerList[0].persInfo.image!=""'>
		<img width="50" height="50" border="0"
			 src="<s:property value='populateDetailImage(#innerList[0].persInfo.image)' />">
	</s:if>
	<s:else>
		<img align="middle" width="150" height="100" border="0"
			src="img/no-image.png">
	</s:else>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="personalInfo.firstName" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].persInfo.firstName" />
	</p>
</div>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="personalInfo.lastName" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].persInfo.lastName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="user.lang" />
	</p>
	<p class="flexItem">
		<s:text name='%{#innerList[0].language}' />
	</p>
</div>

<h2>
	<s:text name="info.contact" />
</h2>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="contactInfo.address" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].contInfo.address1" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="contactInfo.phoneNumber" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].contInfo.phoneNumber" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="contactInfo.mobileNumber" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].contInfo.mobileNumbere" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="contactInfo.email" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].contInfo.email" />
	</p>
</div>

<h2>
	<s:text name="info.userCred" />
</h2>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="user.role" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].role.name" />
	</p>
</div>
<s:if test="#innerList[0].agroChDealer!=null">
	<div class="dynamic-flexItem">
		<p class="flexItem">
			<s:text name="agent.exporter" />
		</p>
		<p class="flexItem">
			<s:property
				value="findRExporterRegistrationById(#innerList[0].agroChDealer).name" />
		</p>
	</div>
</s:if>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="user.status" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="#innerList[0].isEnabled" /> --%>
		<s:property
			value="getObjectIdFromTableByFieldIdAndRevIdForUser('ese_user_aud','ENABLE',#innerList[1].id)" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="%{getLocaleProperty('Signature')}" />
	</p>
	<s:if test="#innerList[0].signature!=null">
		<p class="flexItem">
			<button type="button" class="fa fa-download"
				style="background-color: transparent"
				onclick="popDownload(<s:property value="#innerList[0].signature"/>)"></button>
		</p>
	</s:if>
</div>
