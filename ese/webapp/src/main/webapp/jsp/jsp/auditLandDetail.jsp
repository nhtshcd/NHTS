<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('landPreparation.date')}" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="date" /> --%>
		<s:date name="#innerList[0].date" format="dd/MM/yyyy" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmer.fcode" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farm.farmer.farmerId" />
	</p>
</div>


<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('landPreparation.farmer')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farm.farmer.firstName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="farmCode" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farm.farmCode" />
	</p>
</div>
<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('landPreparation.farm')}" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farm.farmName" />
	</p>
</div>

<div class="dynamic-flexItem ">
	<p class="flexItem">
		<s:property value="%{getLocaleProperty('landPreparation.block')}" />
	</p>
	<p class="flexItem">
		<s:property
			value="getDataByTableFieldById('farm_crops',#innerList[0].farmCrops.id ,'BLOCK_ID')" />
		-
		<s:property
			value="getDataByTableFieldById('farm_crops',#innerList[0].farmCrops.id ,'BLOCK_NAME')" />
	</p>
</div>

<table class="table table-bordered aspect-detail"
	id="landPreparationInfoTable">
	<thead>
		<tr>
			<th><center>
					<s:property
						value="%{getLocaleProperty('landPreparation.activity')}" />
				</center></th>
			<th class="hide"><center>
					<s:property
						value="%{getLocaleProperty('landPreparation.activityMode')}" />
				</center></th>
			<th><center>
					<s:property
						value="%{getLocaleProperty('landPreparation.noOfLabourers')}" />
				</center></th>
		</tr>
	</thead>
	<tbody id="landPreparationContent">
		<s:iterator value="#innerList[0].landPreparationDetails" status="incr">
			<tr id="row<s:property	value="#incr.index" />">
				<td class="activity"><center>
						<s:property value="%{getCatalgueNameByCode(activity)}" />
					</center></td>
				<td class="activityMode hide"><center>
						<s:property value="%{getCatalgueNameByCode(activityMode)}" />
					</center></td>
				<td class="noOfLabourers"><center>
						<s:property value="noOfLabourers" />
					</center></td>
			</tr>
		</s:iterator>
	</tbody>
</table>

