<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/plotting.jsp"%>
<link rel="stylesheet"
	href="plugins/selectize/css/selectize.bootstrap3.css">
<script src="plugins/selectize/js/standalone/selectize.min.js?v=2.0"></script>
<head>
<META name="decorator" content="swithlayout">
</head>
<style>
.datepicker-dropdown {
	width: 300px;
}
</style>
<body>
	<div class="error"></div>
	<s:form id="fileDownload" action="farm_populateDownload">
		<s:hidden id="loadId" name="idd" />
	</s:form>
	<s:form name="form" cssClass="fillform" method="post"
		enctype="multipart/form-data" id="target">
		<s:hidden key="currentPage" />
		<s:hidden id="farmerId" name="farmerId" />
		<s:hidden id="farmerUniqueId" name="farmerUniqueId" />
		<s:hidden id="farmerName" name="farmerName" />
		<s:hidden name="tabIndex" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden key="farm.id" class="uId" />
		</s:if>
		<s:if test='"updateActPlan".equalsIgnoreCase(command)'>
			<s:hidden key="farm.id" class="uId" />
		</s:if>
		<%-- <s:hidden key="farm.farm.id" id="farmerUniqueId" /> --%>
		<s:hidden key="command" id="command" />
		<s:hidden id="treeDetailToJson" name="treeDetailToJson" />
		<div class="appContentWrapper marginBottom farmInfo">

			<div class="formContainerWrapper">
				<div class="error">
					<div id="validateError" style="text-align: left; font-color: red"></div>
					<div>&nbsp</div>
				</div>
				<h2>
					<s:text name="info.farm" />
				</h2>
			</div>

			<div id="farmInfo" class="panel-collapse collapse in">
				<div class="flexform">
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.farmer')}" />
						</p>
						<p class="flexItem">
							<s:property value="farm.farmer.firstName" />
						</p>
					</div>

					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.farmName')}" />
						</p>
						<p class="flexItem">
							<s:property value="farm.farmName" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.proposedPlanting')}" />
						</p>
						<p class="flexItem">
							<s:property value="farm.proposedPlanting" />
						</p>
					</div>

					<div class="dynamic-flexItem plantArea">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.totalLandHolding')}" />
						</p>
						<p class="flexItem">
							<s:property value="farm.totalLandHolding" />
						</p>
					</div>

					<div class="dynamic-flexItem ifOpenOther hide">
						<p class="flexItem">
							<s:text name="farm.ifOpenOther" />
						</p>
						<p class="flexItem">
							<s:property value="farm.ifOpenOther" />
						</p>
					</div>
					<div class="dynamic-flexItem protectedField hide">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.protectedField')}" />
						</p>
						<p class="flexItem">
							<s:property value="%{getCatalgueNameByCode(farm.protectedField)}" />
						</p>
					</div>
					<div class="dynamic-flexItem ifProtectedOther hide">
						<p class="flexItem">
							<s:text name="farmCrops.ifProtectedOther" />
						</p>
						<p class="flexItem">
							<s:property value="farm.ifProtectedOther" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.landOwnership')}" />
						</p>
						<p class="flexItem">
							<s:property value="%{getCatalgueNameByCode(farm.landOwnership)}" />
						</p>
					</div>



					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.isAddressSame')}" />
						</p>
						<!-- <p class="flexItem"> -->
						<%-- <s:property value="farm.isAddressSame" /> --%>
						<p class="flexItem" id="addr"></p>

						<!-- </p> -->
					</div>



					<%-- <div class="dynamic-flexItem">
							<p class="flexItem"> <s:property
								value="%{getLocaleProperty('farm.address')}" />
						</p>
						<p class="flexItem">
							<s:property value="farm.address" />
						</p>
					</div> --%>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="country.name" />
						</p>
						<p class="flexItem">
							<s:property value="farm.village.city.locality.state.country.code" />
							&nbsp-&nbsp
							<s:property value="farm.village.city.locality.state.country.name" />
						</p>
					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="county.name" />
						</p>
						<p class="flexItem">
							<s:property value="farm.village.city.locality.state.code" />
							&nbsp-&nbsp
							<s:property value="farm.village.city.locality.state.name" />
						</p>

					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="subcountry.name" />
						</p>
						<p class="flexItem">
							<s:property value="farm.village.city.locality.code" />
							&nbsp-&nbsp
							<s:property value="farm.village.city.locality.name" />
						</p>

					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="ward.name" />
						</p>
						<p class="flexItem">
							<!-- <p class="flexItem" name="selectedCity">
										 -->
							<s:property value="farm.village.city.code" />
							&nbsp-&nbsp
							<s:property value="farm.village.city.name" />

						</p>
					</div>


					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="village.name" />
						</p>
						<p class="flexItem" name="selectedVillage">
							<s:property value="farm.village.code" />
							&nbsp-&nbsp
							<s:property value="farm.village.name" />
						</p>
					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.photo')}" />
						</p>
						<p class="flexItem">
							<s:if test="command=='update' && farm.photo!=null">
								<button type="button" class="fa fa-download"
									onclick="popDownload(<s:property value="farm.photo"/>)"></button>
							</s:if>
							<s:hidden name="photoFileName" id="photoFileName" />
							<s:hidden name="photoFileType" id="photoFileType" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="farm.landRegNo" />
						</p>
						<p class="flexItem">
							<s:property value="farm.landRegNo" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.landTopography')}" />
						</p>
						<p class="flexItem">
							<s:property value="%{getCatalgueNameByCode(farm.landTopography)}" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.landGradient')}" />
						</p>
						<p class="flexItem">
							<s:property value="%{getCatalgueNameByCode(farm.landGradient)}" />
						</p>
					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('farm.landRegDocs')}" />
						</p>
						<p class="flexItem">
							<s:if test="command=='update' && farm.landRegDocs!=null">
								<button type="button" class="fa fa-download"
									onclick="popDownload(<s:property value="farm.landRegDocs"/>)"></button>
							</s:if>
							<s:hidden name="landRegDocsFileName" id="landRegDocsFileName" />
							<s:hidden name="landRegDocsFileType" id="landRegDocsFileType" />
						</p>
					</div>



				</div>
				<div class="formContainerWrapper">


					<div class="clear"></div>
					<br></br>
					<s:hidden id="farmCordinates" value="%{jsonObjectList}" />


					<div class="flexItem flex-layout flexItemStyle">
						<b><s:text name="farm.latitude" />: &nbsp;</b>
						<s:property value="farm.plotting.midLatitude" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b><s:text
								name="farm.longitude" />: &nbsp;</b>
						<s:property value="farm.plotting.midLongitude" />

						<%-- <b><s:text name="farm.latitude" />: &nbsp;</b>  <s:property value="farm.longitude" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<b><s:text name="farm.longitude" />: &nbsp;</b> <s:property value="farm.latitude" /> --%>
					</div>
					<div id="map" class="smallmap map" style="height: 500px"></div>
				</div>
			</div>
		</div>
		<s:if test='#session.isAdmin =="true"'>
			<div class="appContentWrapper marginBottom auditfarmInfo">

				<s:iterator value="ex" var="innerList">
					<div class="formContainerWrapper">
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

								<div class="dynamic-flexItem ">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('farm.farmer')}" />
									</p>
									<p class="flexItem">
										<s:property value="#innerList[0].farmer.firstName" />
									</p>
								</div>

								<div class="dynamic-flexItem ">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('farm.farmName')}" />
									</p>
									<p class="flexItem">
										<s:property value="#innerList[0].farmName" />
									</p>
								</div>
								<div class="dynamic-flexItem ">
									<p class="flexItem">
										<s:property
											value="%{getLocaleProperty('farm.proposedPlanting')}" />
									</p>
									<p class="flexItem">
										<s:property value="#innerList[0].proposedPlanting" />
									</p>
								</div>

								<div class="dynamic-flexItem plantArea">
									<p class="flexItem">
										<s:property
											value="%{getLocaleProperty('farm.totalLandHolding')}" />
									</p>
									<p class="flexItem">
										<s:property value="#innerList[0].totalLandHolding" />
									</p>
								</div>

								<div class="dynamic-flexItem ifOpenOther hide">
									<p class="flexItem">
										<s:text name="farm.ifOpenOther" />
									</p>
									<p class="flexItem">
										<s:property value="#innerList[0].ifOpenOther" />
									</p>
								</div>
								<div class="dynamic-flexItem protectedField hide">
									<p class="flexItem">
										<s:property
											value="%{getLocaleProperty('#innerList[0].protectedField')}" />
									</p>
									<p class="flexItem">
										<s:property
											value="%{getCatalgueNameByCode(#innerList[0].protectedField)}" />
									</p>
								</div>
								<div class="dynamic-flexItem ifProtectedOther hide">
									<p class="flexItem">
										<s:text name="farmCrops.ifProtectedOther" />
									</p>
									<p class="flexItem">
										<s:property value="#innerList[0].ifProtectedOther" />
									</p>
								</div>
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('farm.landOwnership')}" />
									</p>
									<p class="flexItem">
										<s:property
											value="%{getCatalgueNameByCode(#innerList[0].landOwnership)}" />
									</p>
								</div>



								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('farm.isAddressSame')}" />
									</p>
									<p class="flexItem">
										<s:property
											value="%{getLocaleProperty('YesAndNo'+#innerList[0].isAddressSame)}" />
										<!-- <p class="flexItem" id="addr1"></p> -->

									</p>
								</div>

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="country.name" />
									</p>
									<p class="flexItem">
										<s:property
											value="#innerList[0].village.city.locality.state.country.code" />
										&nbsp-&nbsp
										<s:property
											value="#innerList[0].village.city.locality.state.country.name" />
									</p>
								</div>

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="county.name" />
									</p>
									<p class="flexItem">
										<s:property
											value="#innerList[0].village.city.locality.state.code" />
										&nbsp-&nbsp
										<s:property
											value="#innerList[0].village.city.locality.state.name" />
									</p>
								</div>

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="subcountry.name" />
									</p>
									<p class="flexItem">
										<s:property value="#innerList[0].village.city.locality.code" />
										&nbsp-&nbsp
										<s:property value="#innerList[0].village.city.locality.name" />
									</p>
								</div>

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="ward.name" />
									</p>
									<p class="flexItem">
										<s:property value="#innerList[0].village.city.code" />
										&nbsp-&nbsp
										<s:property value="#innerList[0].village.city.name" />
									</p>
								</div>

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="village.name" />
									</p>
									<p class="flexItem" name="selectedVillage">
										<s:property value="#innerList[0].village.code" />
										&nbsp-&nbsp
										<s:property value="#innerList[0].village.name" />
									</p>
								</div>

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('farm.photo')}" />
									</p>
									<p class="flexItem">
										<s:if test="command=='update' && #innerList[0].photo!=null">
											<button type="button" class="fa fa-download"
												onclick="popDownload(<s:property value="#innerList[0].photo"/>)"></button>
										</s:if>
										<s:hidden name="photoFileName" id="photoFileName" />
										<s:hidden name="photoFileType" id="photoFileType" />
									</p>
								</div>
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="farm.landRegNo" />
									</p>
									<p class="flexItem">
										<s:property value="#innerList[0].landRegNo" />
									</p>
								</div>
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:property
											value="%{getLocaleProperty('farm.landTopography')}" />
									</p>
									<p class="flexItem">
										<s:property
											value="%{getCatalgueNameByCode(#innerList[0].landTopography)}" />
									</p>
								</div>
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('farm.landGradient')}" />
									</p>
									<p class="flexItem">
										<s:property
											value="%{getCatalgueNameByCode(#innerList[0].landGradient)}" />
									</p>
								</div>

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('farm.landRegDocs')}" />
									</p>
									<p class="flexItem">
										<s:if
											test="command=='update' && #innerList[0].landRegDocs!=null">
											<button type="button" class="fa fa-download"
												onclick="popDownload(<s:property value="#innerList[0].landRegDocs"/>)"></button>
										</s:if>
										<s:hidden name="landRegDocsFileName" id="landRegDocsFileName" />
										<s:hidden name="landRegDocsFileType" id="landRegDocsFileType" />
									</p>
								</div>



								<div class="formContainerWrapper">


									<div class="clear"></div>
									<br></br>
									<s:hidden id="farmCordinates" value="%{jsonObjectList}" />


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
					</div>
				</s:iterator>
			</div>
		</s:if>
		<div class="">
			<div class="flexItem flex-layout flexItemStyle">
				<div class="margin-top-10">
					<span id="cancel" class=""><span class="first-child"><button
								type="button" class="cancel-btn btn btn-sts"
								onclick="onCancel();">
								<b><FONT color="#FFFFFF"><s:text name="back.button" />
								</font></b>
							</button></span></span>
				</div>
			</div>
		</div>

	</s:form>

	<script type="text/javascript">
	 
	function popDownload(type) {
		document.getElementById("loadId").value = type;
		$('#fileDownload').submit();
	}
        function onCancel() {
           // document.listForm.submit();
        	window.location.href="<s:property value='redirectContent' />";
        }

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
 		loadFarmMap("<s:property value='farm.plotting.id' />",false)
 		
 		var addr = '<s:property value="farm.isAddressSame" />';
		if (addr == "")
			$("#addr").text("");
		else if (addr == 1)
			$("#addr").text("Yes");
		else if (addr == 0)
			$("#addr").text("No");
 		

 		});
     
  </script>
</body>
