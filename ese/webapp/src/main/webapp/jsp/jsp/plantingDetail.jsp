<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/plotting.jsp"%>
<link rel="stylesheet"
	href="plugins/selectize/css/selectize.bootstrap3.css">
<script src="plugins/selectize/js/standalone/selectize.min.js?v=2.0"></script>
<head>
<meta name="decorator" content="swithlayout">
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">

</head>

<body>
	<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
	<script>
		
	</script>
	<s:form name="form">
		<s:hidden key="currentPage" />
		<s:hidden key="command" />
		<s:hidden key="id" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<div class="flex-view-layout">
			<div class="fullwidth">
				<div class="flexWrapper">
					<div class="flexLeft appContentWrapper">



						<s:hidden key="planting.id" class='uId' />
						<s:hidden name="farmId" value="%{farmId}" />

						<div class="error verror">
							<s:actionerror />
							<s:fielderror />
						</div>

						<div class="formContainerWrapper dynamic-form-con">
							<h2>
								<s:property value="%{getLocaleProperty('info.planting')}" />
							</h2>
							<s:if test='branchId==null'>
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="app.branch" />
									</p>
									<p class="flexItem">
										<s:property value="%{getBranchName(planting.branchId)}" />
									</p>
								</div>
							</s:if>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="profile.farmer" />
								</p>
								<p class="flexItem">
									<s:property value="planting.farmCrops.farm.farmer.firstName" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="profile.farm" />
								</p>
								<p class="flexItem">
									<s:property value="planting.farmCrops.farm.farmName" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="blockName" />
								</p>
								<p class="flexItem">
									<s:property value="planting.farmCrops.blockName" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="Planting Area (Acre)" />
								</p>
								<p class="flexItem">
									<s:property value="planting.cultiArea" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="blockIds" />
								</p>
								<p class="flexItem">
									<s:property value="planting.farmCrops.blockId" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="harvest.cropPlanting" />
								</p>
								<p class="flexItem">
									<s:property value="planting.plantingId" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="%{getLocaleProperty('farmer.plantDate')}" />
								</p>
								<p class="flexItem">

									<s:date name="planting.plantingDate" format="dd-MM-yyyy" />
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="%{getLocaleProperty('plantingmaterial')}" />
								</p>
								<p class="flexItem">
									<s:property
										value="%{getCatalgueNameByCode(planting.seedSource)}" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="%{getLocaleProperty('planting.fieldType')}" />
								</p>
								<p class="flexItem">
									<s:property
										value="%{getCatalgueNameByCode(planting.fieldType)}" />
								</p>
							</div>

							<%-- <div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="farmcrops.cropCategory.prop" />
								</p>
								<p class="flexItem">
									<s:property
										value="%{getCatalgueNameByCode(farmCrops.cropCategory)}" />
								</p>
							</div> 

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="cropCommonName" />
								</p>
								<p class="flexItem">
									<s:property value="selectedProduct" />
								</p>

							</div>--%>


							<%-- <div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="cropScientName" />
								</p>
								<p class="flexItem">
									<s:property value="scientificName" />
								</p>

							</div> --%>




							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="cropPlanted" />
								</p>
								<p class="flexItem">
									<s:property value="selectedVariety" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="cropVarity" />
								</p>
								<p class="flexItem">
									<s:property value="selectedGradename" />
								</p>
							</div>






							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="seedlotnum" />
								</p>
								<p class="flexItem">
									<s:property value="planting.lotNo" />
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="bordercrop" />
								</p>
								<p class="flexItem">
									<s:property
										value="%{getCatalgueNameByCode(planting.cropCategory)}" />
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="seedtreatment" />
								</p>
								<p class="flexItem">
									<s:if
										test="planting.chemUsed!='' && planting.chemUsed!=null && planting.chemUsed!='-1'">
										<s:property
											value='%{getLocaleProperty("yesNoStatus"+planting.chemUsed)}' />
									</s:if>
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="seedcheQuty" />
								</p>
								<p class="flexItem">
									<s:property value="planting.chemQty" />
								</p>
							</div>



							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="seedqtyplanted" />
								</p>
								<p class="flexItem">
									<s:property value="planting.seedQtyPlanted" />

								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="plantingweek" />
								</p>
								<p class="flexItem">
									<s:property value="planting.seedWeek" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="fertilizerused" />
								</p>

								<p class="flexItem">
									<s:if
										test="planting.fertiliser!='' && planting.fertiliser!=null && planting.fertiliser!='-1'">
										<s:property
											value='%{getLocaleProperty("yesNoStatus"+planting.fertiliser)}' />
									</s:if>

								</p>

							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="typeoffertizer" />
								</p>

								<p class="flexItem">
									<s:property
										value="%{getCatalgueNameByCode(planting.typeOfFert)}" />
								</p>

							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="fertilizerlotnumber" />
								</p>

								<p class="flexItem">
									<s:property value="planting.fLotNo" />
								</p>

							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="fertilizerQTYused" />
								</p>

								<p class="flexItem">
									<s:property value="planting.fertQty" />
								</p>

							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="units" />
								</p>

								<p class="flexItem">
									<s:property value="%{getCatalgueNameByCode(planting.unit)}" />
								</p>

							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="modeofapp" />
								</p>
								<p class="flexItem">
									<s:property value="%{getCatalgueNameByCode(planting.modeApp)}" />
								</p>
							</div>


							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="expweekharv" />
								</p>
								<p class="flexItem">
									<s:property value="planting.expHarvestWeek" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="expqtyharinkgs" />
								</p>
								<p class="flexItem">
									<s:property value="planting.expHarvestQty" />
								</p>
							</div>
						</div>
						<div class="formContainerWrapper">


							<div class="clear"></div>
							<br></br>
							<s:hidden id="farmCordinates" value="%{jsonObjectList}" />


							<div class="flexItem flex-layout flexItemStyle">
								<b><s:text name="farm.latitude" />: &nbsp;</b>
								<s:property value="planting.plotting.midLatitude" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b><s:text
										name="farm.longitude" />: &nbsp;</b>
								<s:property value="planting.plotting.midLongitude" />
							</div>
							<div id="map" class="smallmap map" style="height: 500px;"></div>
						</div>

						<div class="formContainerWrapper">
							<s:if test='#session.isAdmin =="true"'>
								<s:iterator value="ex" var="innerList">
									<div class="aPanel audit_history">
										<div class="aTitle">
											<h2>
												<s:if
													test="#innerList[2].toString().trim().equalsIgnoreCase('ADD')">
													<s:property value="#innerList[0].createdUser" />
												</s:if>
												<s:else>
													<s:property value="#innerList[0].updatedUser" />
												</s:else>
												-

												<s:date name="#innerList[1].revisionDate"
													format="dd/MM/yyyy hh:mm:ss" />
												-
												<s:property
													value="%{getLocaleProperty('default'+#innerList[2])}" />
												<div class="pull-right">
													<a class="aCollapse "
														href="#<s:property value="#innerList[1].id" />"><i
														class="fa fa-chevron-right"></i></a>
												</div>
											</h2>
										</div>
										<div class="aContent dynamic-form-con"
											id="<s:property value="#innerList[1].id" />">

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="profile.farmer" />
												</p>
												<p class="flexItem">
													<s:property
														value="#innerList[0].farmCrops.farm.farmer.firstName" />
													<%-- <s:property value="findPlantingById(#innerList[0].id).farmCrops.farm.farmer.firstName" /> --%>
												</p>
											</div>
											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="profile.farm" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].farmCrops.farm.farmName" />
												</p>
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="blockName" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].farmCrops.blockName" />
												</p>
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="Planting Area (Acre)" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].cultiArea" />
												</p>
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="blockIds" />
												</p>
												<p class="flexItem">
													<%-- <s:property value="findPlantingById(#innerList[0].id).farmCrops.blockId" /> --%>
													<s:property value="#innerList[0].farmCrops.blockId" />
												</p>
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="harvest.cropPlanting" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].plantingId" />
												</p>
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="%{getLocaleProperty('farmer.plantDate')}" />
												</p>
												<p class="flexItem">

													<s:date name="#innerList[0].plantingDate"
														format="dd-MM-yyyy" />
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="%{getLocaleProperty('plantingmaterial')}" />
												</p>
												<p class="flexItem">
													<s:property
														value="%{getCatalgueNameByCode(#innerList[0].seedSource)}" />
												</p>
											</div>
											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="%{getLocaleProperty('planting.fieldType')}" />
												</p>
												<p class="flexItem">
													<s:property
														value="%{getCatalgueNameByCode(#innerList[0].fieldType)}" />
												</p>
											</div>
											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="cropPlanted" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].variety.name" />
												</p>
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="cropVarity" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].grade.name" />
												</p>
											</div>






											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="seedlotnum" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].lotNo" />
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="bordercrop" />
												</p>
												<p class="flexItem">
													<s:property
														value="%{getCatalgueNameByCode(#innerList[0].cropCategory)}" />
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="seedtreatment" />
												</p>
												<p class="flexItem">
													<s:if
														test="#innerList[0].chemUsed!='' && #innerList[0].chemUsed!=null && #innerList[0].chemUsed!='-1'">
														<s:property
															value='%{getLocaleProperty("yesNoStatus"+#innerList[0].chemUsed)}' />
													</s:if>
												</p>
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="seedcheQuty" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].chemQty" />
												</p>
											</div>



											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="seedqtyplanted" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].seedQtyPlanted" />

												</p>
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="plantingweek" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].seedWeek" />
												</p>
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="fertilizerused" />
												</p>

												<p class="flexItem">
													<s:if
														test="#innerList[0].fertiliser!='' && #innerList[0].fertiliser!=null && #innerList[0].fertiliser!='-1'">
														<s:property
															value='%{getLocaleProperty("yesNoStatus"+#innerList[0].fertiliser)}' />
													</s:if>

												</p>

											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="typeoffertizer" />
												</p>

												<p class="flexItem">
													<s:property
														value="%{getCatalgueNameByCode(#innerList[0].typeOfFert)}" />
												</p>

											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="fertilizerlotnumber" />
												</p>

												<p class="flexItem">
													<s:property value="#innerList[0].fLotNo" />
												</p>

											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="fertilizerQTYused" />
												</p>

												<p class="flexItem">
													<s:property value="#innerList[0].fertQty" />
												</p>

											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="units" />
												</p>

												<p class="flexItem">
													<s:property
														value="%{getCatalgueNameByCode(#innerList[0].unit)}" />
												</p>

											</div>


											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="modeofapp" />
												</p>
												<p class="flexItem">
													<s:property
														value="%{getCatalgueNameByCode(#innerList[0].modeApp)}" />
												</p>
											</div>


											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="expweekharv" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].expHarvestWeek" />
												</p>
											</div>

											<div class="dynamic-flexItem">
												<p class="flexItem">
													<s:text name="expqtyharinkgs" />
												</p>
												<p class="flexItem">
													<s:property value="#innerList[0].expHarvestQty" />
												</p>
											</div>
											<div class="dynamic-flexItem"></div>
											<div class="formContainerWrapper">
												<div class="flexItem flex-layout flexItemStyle">
													<b><s:text name="farm.latitude" />: &nbsp;</b>
													<s:property value="#innerList[0].plotting.midLatitude" />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b><s:text
															name="farm.longitude" />: &nbsp;</b>
													<s:property value="#innerList[0].plotting.midLongitude" />
												</div>
											</div>


										</div>
									</div>
								</s:iterator>
							</s:if>
						</div>

					</div>
				</div>

				<!-- <div class="flexItem flex-layout flexItemStyle"> -->
				<div class="margin-top-10">
					<div class="">


						<span id="cancelBtn" class=""><span class="first-child">
								<button type="button" class="back-btn btn btn-sts"
									onclick="javascript:window.close('','_parent','');">
									<b><FONT color="#FFFFFF"><s:text name="back.button" />
									</font></b>
								</button>
						</span></span>
					</div>
				</div>

			</div>
		</div>

	</s:form>
	<s:form id="fileDownload" action="generalPop_populateDownload">
		<s:hidden id="loadId" name="idd" />
	</s:form>
	<script>
var id='<s:property value="id"/>';


function onCancel() {
	window.location.href="<s:property value='redirectContent' />";
}


</script>
	<script>
$(document).ready(function() {	 
	if (navigator.geolocation) {
	      navigator.geolocation.getCurrentPosition(
	        (position) => {
	          const pos = {
	            lat: position.coords.latitude,
	            lng: position.coords.longitude,
	          }
	          $("#latitude").val(pos.lat);
	          $("#longitude").val(pos.lng);
	        },
	        () => {
	          handleLocationError(true, infoWindow, map.getCenter());
	        }
	      );
	    } else {
	      // Browser doesn't support Geolocation
	      handleLocationError(false, infoWindow, map.getCenter());
	    }		


$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
$(".breadCrumbNavigation").find('li:nth-last-child(2)').find('a:first').attr("href",'<s:property value="redirectContent" />');
loadFarmMap("<s:property value='planting.plotting.id' />",false)
});
	function popDownload(type) {
		document.getElementById("loadId").value = type;

		$('#fileDownload').submit();

	}
</script>

</body>