<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/plotting.jsp"%>
<link rel="stylesheet"
	href="plugins/selectize/css/selectize.bootstrap3.css">
<script src="plugins/selectize/js/standalone/selectize.min.js?v=2.0"></script>
<head>
<meta name="decorator" content="swithlayout">
<!-- <link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css"> -->

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
				<!-- 	<div class="flexWrapper"> -->
				<div class="appContentWrapper">



					<s:hidden key="farmCrops.id" class='uId' />
					<s:hidden name="farmId" value="%{farmId}" />

					<div class="error verror">
						<s:actionerror />
						<s:fielderror />
					</div>

					<div class="formContainerWrapper dynamic-form-con">
						<h2>
							<s:property value="%{getLocaleProperty('info.farmCrops')}" />
						</h2>
						<s:if test='branchId==null'>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="app.branch" />
								</p>
								<p class="flexItem">
									<s:property value="%{getBranchName(farmCrops.branchId)}" />
								</p>
							</div>
						</s:if>


						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="profile.farmer" />
							</p>
							<p class="flexItem">
								<s:property value="farmCrops.farm.farmer.firstName" />
							</p>
						</div>
						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="profile.farm" />
							</p>
							<p class="flexItem">
								<s:property value="farmCrops.farm.farmName" />
							</p>
						</div>

						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="profile.exporter.name" />
							</p>
							<p class="flexItem">
								<s:property value="farmCrops.exporter.name" />
							</p>
						</div>

						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="blockName" />
							</p>
							<p class="flexItem">
								<s:property value="farmCrops.blockName" />
							</p>
						</div>

						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="blockArea" />
							</p>
							<p class="flexItem">
								<s:property value="farmCrops.cultiArea" />
							</p>
						</div>

						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="blockIds" />
							</p>
							<p class="flexItem">
								<s:property value="farmCrops.blockId" />
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



					</div>
					<div class="formContainerWrapper">


						<div class="clear"></div>
						<br></br>
						<s:hidden id="farmCordinates" value="%{jsonObjectList}" />


						<div class="flexItem flex-layout flexItemStyle">
							<b><s:text name="farm.latitude" />: &nbsp;</b>
							<s:property value="farmCrops.plotting.midLatitude" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b><s:text
									name="farm.longitude" />: &nbsp;</b>
							<s:property value="farmCrops.plotting.midLongitude" />
							<%-- <b><s:text name="farm.latitude" />: &nbsp;</b> <s:property value="farmCrops.latitude" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<b><s:text name="farm.longitude" />: &nbsp;</b> <s:property value="farmCrops.longitude" /> --%>
						</div>
						<div id="map" class="smallmap map" style="height: 500px;"></div>
					</div>
					<div></div>



					<s:if test='#session.isAdmin =="true"'>
						<div class="appContentWrapper marginBottom auditFarmCropInfo">

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

											
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="profile.farmer" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farm.farmer.firstName" />
	</p>
</div>
<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="profile.farm" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].farm.farmName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="profile.exporter.name" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].exporter.name" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="blockName" />
	</p>
	<p class="flexItem">
		<s:property value="#innerList[0].blockName" />
	</p>
</div>

<div class="dynamic-flexItem">
	<p class="flexItem">
		<s:text name="blockArea" />
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
		<s:property value="#innerList[0].blockId" />
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
loadFarmMap("<s:property value='farmCrops.plotting.id' />",false)
});
	function popDownload(type) {
		document.getElementById("loadId").value = type;

		$('#fileDownload').submit();

	}
</script>
</body>