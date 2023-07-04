<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>

<s:if test='branchId==null'>
	<div class="dynamic-flexItem">
		<p class="flexItem">
			<s:text name="app.branch" />
		</p>
		<p class="flexItem">
			<s:property
				value="%{getBranchName(packing.branchId)}" />
		</p>
	</div>
</s:if>


<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="packing.packingDate" />
	</p>
	<p class="flexItem">
<s:date name="#innerList[0].packingDate" format="dd/MM/yyyy" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="packing.packhouse" />
	</p>
	<p class="flexItem">
		<%-- <s:property value="getDataByTableFieldByRev('warehouse','packing_aud','PACKHOUSE',#innerList[1].id,'NAME')" /> --%>
		<s:property value="#innerList[0].packHouse.name" />
		
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="packing.packerName" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].packerName"/>
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="packing.lotNo" />
	</p>
	<p class="flexItem">
<s:property value="#innerList[0].batchNo" />
	</p>
</div>

<div class="" style="overflow: auto;">
							<table class="table table-bordered aspect-detail"
						      id="procurementDetailTable">	
								
								<thead>
									<tr>
										<th><s:property
												value="%{getLocaleProperty('scouting.farmer')}" /></th>
										<th><s:property
												value="%{getLocaleProperty('scouting.farm')}" /></th>
										<th><s:property
												value="%{getLocaleProperty('packing.blockID')}" /> - <s:property
												value="%{getLocaleProperty('packing.blockName')}" /></th>
												
										<th><s:property
												value="%{getLocaleProperty('Planting ID')}" /></th>
												
										<th><s:property
												value="%{getLocaleProperty('packing.receptionBatchNo')}" /></th>
										<th><s:property
												value="%{getLocaleProperty('packing.productName')}" /></th>
										<th><s:property
												value="%{getLocaleProperty('packing.variety')}" /></th>
												<th><s:property
										value="%{getLocaleProperty('incomingShipmentDate')}" /></th>
										<th><s:property
												value="%{getLocaleProperty('packing.packed')}" /></th>
										<th><s:property
												value="%{getLocaleProperty('packing.rejectWt')}" /></th>
										<th><s:property
												value="%{getLocaleProperty('packing.price')}" /></th>
										<th><s:property
												value="%{getLocaleProperty('packing.totalprice')}" /></th>
										<%-- <th><s:property
												value="%{getLocaleProperty('packing.rejectWt')}" /></th> --%>
										<th><s:property
												value="%{getLocaleProperty('packing.bestBefore')}" /></th>
										<th><s:property
												value="%{getLocaleProperty('packing.country')}" /></th>

									</tr>
								</thead>

								<tbody id="procurementDetailContent">
									<s:iterator value="#innerList[0].packingDetails" status="incr">
										<tr id="row<s:property 	value="#incr.index" />">
											<td><s:property value="blockId.farm.farmer.firstName" /><s:text name=" - " /><s:property value="blockId.farm.farmer.farmerId" /></td>
											<td><s:property value="blockId.farm.farmName" /><s:text name=" - " /><s:property value="blockId.farm.farmCode" /></td>
											<td><s:property value="blockId.blockId" /><s:text name=" - " /> <s:property value="blockId.blockName" /></td>
											<td><s:property value="planting.plantingId" /></td>
											<td><s:property value="batchNo" /></td>
											<%-- <td><s:property value="planting.variety.name" /></td> --%>
											<td><s:property value="findPlantingById(planting.id).variety.name" /></td>
											<%-- <td><s:property value="planting.grade.name" /></td> --%>
											<td><s:property value="findPlantingById(planting.id).grade.name" /></td>
											<%-- <td class="createdDate"><s:date name="ctt.createdDate" format="dd-MM-yyyy" /></td> --%>
											<td class="createdDate"><s:date name="getCityWarehouseByQrUniquePlantingIdAndPackhouseId(qrUnique,planting.id,#innerList[0].packHouse.id).createdDate" format="dd-MM-yyyy" /></td>
											<td class=" pquantity"><s:property value="quantity" /></td>
											<td class="rejectWt"><s:property value="rejectWt" /></td>
											<td class=" price"><s:property value="price" /></td>
											<td class=" totalprice"><s:property value="totalprice" /></td>
											<%-- <td class="rejectWt"><s:property value="rejectWt" /></td> --%>
											<td class=" bestBefore"><p class="flexItem"> <s:date name="bestBefore" format="dd-MM-yyyy" />
												</p></td>
											<td><s:property
													value="blockId.farm.farmer.village.city.locality.state.name" /></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
							</div>
