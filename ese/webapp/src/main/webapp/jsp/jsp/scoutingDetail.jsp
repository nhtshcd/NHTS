<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
<title>Insert title here</title>
</head>
<body>

	<script>
		var idd = '';
		jQuery(document).ready(
				function() {
					var tenant = '<s:property value="getCurrentTenantId()"/>';
					var branchId = '<s:property value="getBranchId()"/>';
					idd = '<s:property value="scouting.id"/>';

					$(".breadCrumbNavigation").find('li:last').find('a:first')
							.attr("href",
									'<s:property value="redirectContent" />');

					var p1 = '<s:property value="scouting.InsectsObserved" />';
					var p2 = '<s:property value="scouting.diseaseObserved" />';
					var p3 = '<s:property value="scouting.weedsObserveds" />';
					if (p1 == 1) {
						$("#product1").text("Yes");
						$('.y1').removeClass('hide');
					} else if (p1 == 0) {
						$("#product1").text("No");
						$('.y1').addClass('hide');
					}
					if (p2 == 1) {
						$("#product2").text("Yes");
						$('.y2').removeClass('hide');
					} else if (p2 == 0) {
						$("#product2").text("No");
						$('.y2').addClass('hide');
					}
					if (p3 == 1) {
						$("#product3").text("Yes");
						$('.y3').removeClass('hide');
					} else if (p3 == 0) {
						$("#product3").text("No");
						$('.y3').addClass('hide');
					}

				});
		function onCancel() {
			window.location.href = "<s:property value="redirectContent" />";

		}
		function popDownload(type1) {
			document.getElementById("loadId").value = type1;

			$('#fileDownload').submit();

		}
	</script>
	<s:form name="form">
		<s:hidden key="currentPage" />
		<s:hidden key="scouting.id" id="id" class='uId' />
		<s:hidden key="command" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<div class="flex-view-layout">
			<div class="fullwidth">
				<div class="flexWrapper">
					<div class="flexLeft appContentWrapper">
						<%-- <div class="formContainerWrapper dynamic-form-con">
							<h2>
								<s:property value="%{getLocaleProperty('info.scouting')}" />
							</h2> --%>
							
							<div class="formContainerWrapper">
							<div class="aPanel farmer_info">
								<div class="aTitle">
									<h2>
										<s:property value="%{getLocaleProperty('info.scouting')}" />
										<div class="pull-right">
											<a class="aCollapse" href="#"><i
												class="fa fa-chevron-right"></i></a>
										</div>
									</h2>
								</div>
						<div class="aContent dynamic-form-con">
							
							<s:if test='branchId==null'>
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="app.branch" />
									</p>
									<p class="flexItem">
										<s:property value="%{getBranchName(scouting.branchId)}" />
									</p>
								</div>
							</s:if>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="scouting.receivedDate" />
								</p>
								<p class="flexItem">
									<s:date name="scouting.receivedDate" format="dd/MM/yyyy" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="farmer.fcode" />
								</p>
								<p class="flexItem">
									<s:property value="scouting.planting.farmCrops.farm.farmer.farmerId" />
								</p>
							</div>
							
							
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="profiles.farmers" />
								</p>
								<p class="flexItem">
									<s:property value="scouting.farmerFullName" />
								</p>
							</div>
							
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="farmCode" />
								</p>
								<p class="flexItem">
									<s:property value="scouting.planting.farmCrops.farm.farmCode" />
								</p>
							</div>
							
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="profile.farm" />
								</p>
								<p class="flexItem">
									<s:property value="scouting.planting.farmCrops.farm.farmName" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="block" />
								</p>
								<p class="flexItem">
									<s:property value="scouting.planting.farmCrops.blockName" />
								</p>
							</div>

							 <div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="blockId" />
								</p>
								<p class="flexItem">
									<s:property value="scouting.planting.farmCrops.blockId" />
								</p>
							</div>
							
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="Planting ID" />
								</p>
								<p class="flexItem">
									<s:property value="scouting.planting.plantingId" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="CropVariety" />
								</p>
								<p class="flexItem">
									<s:property value="scouting.planting.variety.name" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="scouting.cropVariety" />
								</p>
								<p class="flexItem">
									<s:property value="scouting.planting.grade.name" />
								</p>
							</div>
							
							<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('scouting.sprayingRequired')}" />
									</p>
									<p class="flexItem">
										<s:property
											value='%{getCatlogueValueByCodeArray(scouting.sprayingRequired)}' />
									</p>
								</div>
								
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="scouting.sctRecommendation" />
								</p>
								<p class="flexItem">
									<s:property value="scouting.sctRecommendation" />
								</p>
							</div>
								
							<!-- </div>
							<div class="formContainerWrapper dynamic-form-con"> -->
							
								<h2>
									<s:text name="info.insects" />
								</h2>

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="scouting.InsectsObserved" />
									</p>
									<p class="flexItem" id="product1"></p>
								</div>
								
								<div class="dynamic-flexItem y1 hide">
									<p class="flexItem">
										<s:text name="scouting.nameOfInsectsObserved" />
									</p>
									<p class="flexItem">
										<s:property
											value='%{getCatlogueValueByCodeArray(scouting.nameOfInsectsObserved)}' />
									</p>
								</div>
								
								<div class="dynamic-flexItem y1 hide">
									<p class="flexItem">
										<s:text name="scouting.perOrNumberInsects" />
									</p>
									<p class="flexItem">
										<s:property value="scouting.perOrNumberInsects" />
									</p>
								</div>
								
								<!-- </div>
							<div class="formContainerWrapper dynamic-form-con"> -->
							
								<h2>
									<s:text name="info.disease" />
								</h2>
								
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="scouting.diseaseObserved" />
									</p>
									<p class="flexItem" id="product2"></p>
								</div>

								<div class="dynamic-flexItem y2 hide">
									<p class="flexItem">
										<s:text name="scouting.nameOfDisease" />
									</p>
									<p class="flexItem">
										<s:property
											value='%{getCatlogueValueByCodeArray(scouting.nameOfDisease)}' />
									</p>
								</div>
								
								<div class="dynamic-flexItem y2 hide">
									<p class="flexItem">
										<s:text name="scouting.perInfection" />
									</p>
									<p class="flexItem">
										<s:property value="scouting.perInfection" />
									</p>
								</div>
								
								<!-- </div>
							<div class="formContainerWrapper dynamic-form-con"> -->
							
								<h2>
									<s:text name="info.weedsObserveds" />
								</h2>
								

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="scouting.weedsObserveds" />
									</p>
									<p class="flexItem" id="product3"></p>
								</div>

								<div class="dynamic-flexItem y3 hide">
									<p class="flexItem">
										<s:text name="scouting.nameOfWeeds" />
									</p>
									<p class="flexItem">
										<s:property
											value='%{getCatlogueValueByCodeArray(scouting.nameOfWeeds)}' />
									</p>
								</div>
								
								<div class="dynamic-flexItem y3 hide">
									<p class="flexItem">
										<s:text name="scouting.weedsPresence" />
									</p>
									<p class="flexItem">
										<s:property value="scouting.weedsPresence" />
									</p>
								</div>
								
								<div class="dynamic-flexItem y3 hide">
									<p class="flexItem">
										<s:text name="scouting.recommendations" />
									</p>
									<p class="flexItem">
										<s:property value="scouting.recommendations" />
									</p>
								</div>
								
								<!-- </div>
							<div class="formContainerWrapper dynamic-form-con"> -->
							
								<h2>
									<s:text name="info.irrigation" />
								</h2>

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="scouting.sourceOfWater" />
									</p>
									<p class="flexItem">
										<s:property
											value="%{getCatalgueNameByCode(scouting.sourceOfWater)}" />
									</p>
								</div>

								<div class="dynamic-flexItem">
									<p class="flexItem">
										<%-- <s:text name="scouting.irrigationType" /> --%>
										<s:property value="%{getLocaleProperty('scouting.irrigationType')}" />
									</p>
									<p class="flexItem">
										<s:property value="scouting.irrigationType" />
									</p>
								</div>
								
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="scouting.irrigationMethod" />
									</p>
									<p class="flexItem">
										<s:property
											value='%{getCatlogueValueByCodeArray(scouting.irrigationMethod)}' />
									</p>
								</div>
								
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<%-- <s:text name="scouting.areaIrrrigated" /> --%>
										<s:property value="%{getLocaleProperty('scouting.areaIrrrigated')}" />
									</p>
									<p class="flexItem">
										<s:property value="scouting.areaIrrrigated" />
									</p>
								</div>
							</div>
							</div>
							<s:if test="roleID.trim().equalsIgnoreCase('2')">						
								<s:iterator value="ex" var="innerList">
								<div class="aPanel audit_history">
									<div class="aTitle">
										<h2>
											<s:if test="#innerList[2].toString().trim().equalsIgnoreCase('ADD')">
													<s:property value="#innerList[0].createdUser" />
											</s:if>
											<s:else>
													 <s:property value="#innerList[0].updatedUser" />
											 </s:else>
											-
											<s:date name="#innerList[1].revisionDate" format="dd/MM/yyyy hh:mm:ss" />
											-
											<s:property value="%{getLocaleProperty('default'+#innerList[2])}" />
											<div class="pull-right">
												<a class="aCollapse "
													href="#<s:property value="#innerList[1].id" />"><i
													class="fa fa-chevron-right"></i></a>
											</div>
										</h2>
									</div>
									<div class="aContent dynamic-form-con"
										id="<s:property value="#innerList[1].id" />">
										<jsp:include page='/jsp/jsp/auditScoutingDetail.jsp' />
									</div>
								</div>
							</s:iterator> 
							</s:if>
							</div>
							
							<div class="yui-skin-sam">
								<div class="margin-top-10">
									<span id="cancel" class=""><span class="first-child"><button
												type="button" class="back-btn btn btn-sts"
												onclick="onCancel();">
												<b><FONT color="#FFFFFF"><s:text
															name="back.button" /> </font></b>
											</button></span></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	</s:form>
	<s:form id="fileDownload" action="generalPop_populateDownload">
		<s:hidden id="loadId" name="idd" />


	</s:form>


</body>