<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>


<s:if test='branchId==null'>
	<div class="dynamic-flexItem">
		<p class="flexItem">
			<s:text name="app.branch" />
		</p>
		<p class="flexItem">
			<s:property
				value="%{getBranchName(recalling.branchId)}" />
		</p>
	</div>
</s:if>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.treacibilitycode" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].kenyaTraceCode" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
Recalling Batch Number
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].batchNo" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
Buyer Name
	</p>
	<p class="flexItem">
		 <%-- <s:property value="getDataByTableFieldByRev('CustomerFromShipment','recalling_aud','SHIPMENT_ID',#innerList[1].id,'CUSTOMER_NAME')" /> --%> 
	 	<s:property value="#innerList[0].shipment.customer.customerName" /> 
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">	
Shipment Destination
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].shipmentDestination" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
Exporter
	</p>
	<p class="flexItem">
<%-- <s:property value="getDataByTableFieldByRev('CustomerFromShipment','recalling_aud','SHIPMENT_ID',#innerList[1].id,'COMPANY_NAME')" /> --%>
<s:property value="#innerList[0].shipment.packhouse.exporter.name" /> 
	</p>
</div>

	<div class="" style="overflow: auto;">
				<table class="table table-bordered aspect-detail"
						id="shipmentInfoTable">
						<thead>
							<tr>
							<th>Farmer Name</th>
							
								<th><s:property
										value="%{getLocaleProperty('shipment.lotNo')}" /></th>
								<th>Block Id</th>
								<th>Block Name</th>
								<th>Planting</th>
								<th><s:property
										value="%{getLocaleProperty('shipment.product')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.variety')}" /></th>
								<th><s:property value="%{getLocaleProperty('incomingDate')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.packingUnit')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.packingQty')}" /></th>
								</tr>
						</thead>
					<tbody id="shipmentContent">
							
							<s:iterator value="#innerList[0].recallDetails" status="incr" id="ShipmentDetailse">
								<tr id="row<s:property	value="#incr.index" />">
								<td class="lotNo"><s:property value="farmcrops.farm.farmer.firstName" /> - <s:property value="farmcrops.farm.farmer.farmerId" /></td>
								
								<td class="lotNo"><s:property value="lotNo" /></td>
									<td class="lotNo"><s:property value="batchNo" /></td>
									<td class="lotNo"><s:property value="farmcrops.blockName" /></td>
									 <td class="planting"><s:property value="planting.plantingId" /></td>
									<td class="product"><s:property
											value="findPlantingById(planting.id).variety.name" /></td>
									<td class="variety"><s:property
											value="findPlantingById(planting.id).grade.name" /></td>
									<td class="createdDate">
									<s:date name="shipmentdetail.shipment.shipmentDate" format="dd-MM-yyyy"/></td>
									<td class="packingUnit"><s:property value="%{getCatalgueNameByCode(receivedUnits)}" /></td>
									<td class="packingQty"><s:property value="receivedWeight" /></td>
									
								
								</tr>
							</s:iterator>
						</tbody>
					</table>
					</div>

 <div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.recDate" />
	</p>
	<p class="flexItem">
<s:date name="#innerList[0].recDate" format="dd/MM/yyyy" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.recEntity" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="#innerList[0].recEntity" /> --%>
		<s:property value="%{getLocaleProperty('grwer'+#innerList[0].recEntity)}" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.recCoordinatorName" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].recCoordinatorName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.recCoordinatorContact" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].recCoordinatorContact" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.recNature" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="#innerList[0].recNature" /> --%>
		<s:property value="%{getLocaleProperty('recNature'+#innerList[0].recNature)}" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.recReason" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].recReason" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		Recalling Evidences
	</p>
	<p class="flexItem">
		<s:if test="#innerList[0].attachment!=null">
				<button type="button" class="fa fa-download" style="background-color: transparent"
					onclick="popDownload(<s:property value="#innerList[0].attachment"/>)">
				</s:if>
	</p>
</div>

		<h2>
		<s:text name="info.operatorInfo" />
		</h2>
				
	<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.operatorName" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].operatorName" />
	</p>
</div>
				
	<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.contactNo" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].contactNo" />
	</p>
</div>
				
<h2>
	<s:text name="info.shippingInfo" />
</h2>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.po" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].po" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.invoiceNo" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].invoiceNo" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.carrierNo" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].carrierNo" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.actionByRecaller" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].actionByRecaller" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="recalling.actionByStakeholders" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].actionByStakeholders" />
	</p>
</div>