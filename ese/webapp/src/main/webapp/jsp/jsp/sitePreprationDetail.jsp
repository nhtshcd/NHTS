<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">

</head>

<body>

	<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
	<script>
	jQuery(document).ready(
				function() {
					var tenant = '<s:property value="getCurrentTenantId()"/>';
					var branchId = '<s:property value="getBranchId()"/>';
					$(".breadCrumbNavigation").find('li:last').find('a:first')
							.attr("href",
									'<s:property value="redirectContent" />');
			var p='<s:property value="env"/>';
			var p1='<s:property value="soc"/>';
			var p2='<s:property value="soi"/>';
			var p3='<s:property value="wat"/>';
			var envr='<s:property value="envr"/>';
			var socr='<s:property value="socr"/>';
			var soir='<s:property value="soir"/>';
			var watr='<s:property value="watr"/>';
			if (p == 1)
			{	
			$("#product").text("Yes");
			$('.y1').removeClass('hide');
			}
			else if (p == 0)
			{
				$("#product").text("No");
				$('.y1').addClass('hide');
			}
			
			if (p1 == 1){
				$("#product1").text("Yes");
			$('.y2').removeClass('hide');
			}
			else if (p1 == 0)
			{
				$("#product1").text("No");
				$('.y2').addClass('hide');
			}
			if (p2 == 1)
			{	$("#product2").text("Yes");
			$('.y3').removeClass('hide');
			}
			else if (p2 == 0)
			{
				$("#product2").text("No");
				$('.y3').addClass('hide');
			}
			

			if (p3 == 1)
			{	$("#product3").text("Yes");
			$('.y4').removeClass('hide');
			}else if (p3 == 0)
			{
				$("#product3").text("No");
				$('.y4').addClass('hide');
			}
			
		});
		function onCancel() {
				window.location.href = "<s:property value="redirectContent" />";

			}
			function popDownload(id) {
				document.getElementById("loadId").value = id;
				$('#fileDownload').submit();
		}
	</script>

	<s:form name="form">
		<s:hidden key="currentPage" />
		<s:hidden key="command" />
		<s:hidden name="redirectContent" id="redirectContent" />


		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:hidden key="sitePrepration.id" />

		<div class="flex-view-layout">
			<div class="fullwidth">
				<div class="flexWrapper">
					<div class="flexLeft appContentWrapper">
						<%-- <div class="formContainerWrapper dynamic-form-con">
							<h2>
								<s:text name="info.sitePrepration" />
							</h2> --%>

						<div class="formContainerWrapper">
							<div class="aPanel farmer_info">
								<div class="aTitle">
									<h2>
										<s:property
											value="%{getLocaleProperty('info.sitePrepration')}" />
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
												<s:property
													value="%{getBranchName(sitePrepration.branchId)}" />
											</p>
										</div>
									</s:if>

									<div class="dynamic-flexItem">
										<p class="flexItem">
											<s:text name="farmer.fcode" />
										</p>
										<p class="flexItem">
											<s:property value="sitePrepration.farm.farmer.farmerId" />
										</p>
									</div>

									<div class="dynamic-flexItem">
										<p class="flexItem">
											<s:text name="profiles.farmers" />
										</p>
										<p class="flexItem">
											<s:property value="selectedFarmer" />
										</p>
									</div>

									<div class="dynamic-flexItem">
										<p class="flexItem">
											<s:text name="farmCode" />
										</p>
										<p class="flexItem">
											<s:property value="sitePrepration.farm.farmCode" />
										</p>
									</div>
									<div class="dynamic-flexItem">
										<p class="flexItem">
											<s:text name="profile.farm" />
										</p>
										<p class="flexItem">
											<s:property value="selectedFarm" />
										</p>
									</div>


									<div class="dynamic-flexItem">
										<p class="flexItem">
											<s:text name="Block Name" />
										</p>
										<p class="flexItem">
											<s:property value="sitePrepration.farmCrops.blockName" />
										</p>
									</div>
									<div class="dynamic-flexItem">
										<p class="flexItem">
											<s:text name="Block Id" />
										</p>
										<p class="flexItem">
											<s:property value="sitePrepration.farmCrops.blockId" />
										</p>
									</div>


									<div class="dynamic-flexItem">
										<p class="flexItem">
											<s:text name="sitePrepration.crop" />
										</p>
										<p class="flexItem">
											<s:property value="selectedProduct" />
										</p>

									</div>


									<div class="dynamic-flexItem">
										<p class="flexItem">
											<s:text name="sitePrepration.env" />
										</p>

										<p class="flexItem " id="product"></p>
									</div>
									<div class="dynamic-flexItem  y1 hide ">
										<p class="flexItem  ">
											<s:text
												name="%{getLocaleProperty('sitePrepration.env.report')}" />
										</p>
										<p class="flexItem">
											<s:if test="envr!=null && envr!=''">
												<button type="button" class="fa fa-download"
													style="background-color: transparent"
													onclick="popDownload(<s:property value="sitePrepration.environmentalAssessmentReport"/>)"></button>
											</s:if>
										</p>
									</div>

									<div class="dynamic-flexItem">
										<p class="flexItem">
											<s:text name="sitePrepration.soc" />
										</p>
										<p class="flexItem" id="product1"></p>
									</div>
									<div class="dynamic-flexItem y2 hide ">
										<p class="flexItem">
											<s:text
												name="%{getLocaleProperty('sitePrepration.soc.report')}" />
										</p>
										<p class="flexItem">
											<s:if test="socr!=null && socr!=''">
												<button type="button" class="fa fa-download"
													style="background-color: transparent"
													onclick="popDownload(<s:property value="socr"/>)"></button>
											</s:if>
										</p>
									</div>



									<div class="dynamic-flexItem ">
										<p class="flexItem">
											<s:text name="sitePrepration.soil" />
										</p>
										<p class="flexItem" id="product2"></p>
									</div>
									<div class="dynamic-flexItem y3 hide ">
										<p class="flexItem">
											<s:text
												name="%{getLocaleProperty('sitePrepration.soil.report')}" />
										</p>
										<p class="flexItem">
											<s:if test="soir!=null && soir!=''">
												<button type="button" class="fa fa-download"
													style="background-color: transparent"
													onclick="popDownload(<s:property value="soir"/>)"></button>
											</s:if>
										</p>
									</div>


									<div class="dynamic-flexItem ">
										<p class="flexItem">
											<s:text name="sitePrepration.water" />
										</p>
										<p class="flexItem " id="product3"></p>
									</div>
									<div class="dynamic-flexItem y4 hide ">
										<p class="flexItem">
											<s:text
												name="%{getLocaleProperty('sitePrepration.water.report')}" />
										</p>
										<p class="flexItem">
											<s:if test="watr!=null && watr!=''">
												<button type="button" class="fa fa-download"
													style="background-color: transparent"
													onclick="popDownload(<s:property value="watr"/>)"></button>
											</s:if>
										</p>
									</div>

								</div>
							</div>
							<s:if test="roleID.trim().equalsIgnoreCase('2')">
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
											<jsp:include page='/jsp/jsp/auditSitePreprationDetail.jsp' />
										</div>
									</div>
								</s:iterator>
							</s:if>
							<div>
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

	<s:form id="fileDownload" action="sitePrepration_populateDownload">
		<s:hidden id="loadId" name="idd" />

	</s:form>

</body>

