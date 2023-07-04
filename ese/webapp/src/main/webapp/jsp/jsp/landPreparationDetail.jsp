<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
</head>
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
</head>
<script type="text/javascript">
	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>";
	}
</script>
<body>
	<s:form id="fileDownload" action="generalPop_populateDownload">
		<s:hidden id="loadId" name="idd" />
	</s:form>
	<s:form name="lanpPreparationForm" cssClass="fillform" method="post"
		action="lanpPreparation_%{command}" id="lanpPreparationForm">
		<s:hidden name="landPreparationDtl" id="landPreparationDtl" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="landPreparation.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="error">
				<div id="validateError" style="text-align: left;"></div>


			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:property value='%{getLocaleProperty("info.landPreparation")}' />

				</h2>
				<div class="flexform">
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('landPreparation.date')}" />
						</p>
						<p class="flexItem">
							<s:property value="date" />
						</p>
					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="farmer.fcode" />
						</p>
						<p class="flexItem">
							<s:property value="landPreparation.farm.farmer.farmerId" />
						</p>
					</div>


					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('landPreparation.farmer')}" />
						</p>
						<p class="flexItem">
							<s:property value="landPreparation.farm.farmer.firstName" />
						</p>
					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="farmCode" />
						</p>
						<p class="flexItem">
							<s:property value="landPreparation.farm.farmCode" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('landPreparation.farm')}" />
						</p>
						<p class="flexItem">
							<s:property value="landPreparation.farm.farmName" />
						</p>
					</div>

					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('landPreparation.block')}" />
						</p>
						<p class="flexItem">
							<s:property value="farmCropfarmId" />
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
							<s:iterator value="landPreparation.landPreparationDetails"
								status="incr">
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
				</div>

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
											href="#<s:property value="#innerList[1].id" />
										"><i
											class="fa fa-chevron-right"></i></a>
									</div>
								</h2>
							</div>
							<div class="aContent dynamic-form-con"
								id="<s:property value="#innerList[1].id" />">

								<jsp:include page='/jsp/jsp/auditLandDetail.jsp' />

							</div>


						</div>
					</s:iterator>
				</s:if>
			</div>
			<div class="flex-layout flexItemStyle">
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
</body>
