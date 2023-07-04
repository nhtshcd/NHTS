<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
</head>
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
</head>
<body>
	<s:form name="packhouseIncomingForm" cssClass="fillform" method="post"
		action="packhouseIncoming_%{command}" id="packhouseIncomingForm">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="packhouseIncoming.id" />
		</s:if>

		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<%-- <h2>
					<s:text name="info.packhouseIncoming" />
				</h2> --%>
				<div class="aPanel farmer_info">
					<div class="aTitle">
						<h2>
							<s:property
								value="%{getLocaleProperty('info.packhouseIncoming')}" />
							<div class="pull-right">
								<a class="aCollapse" href="#"><i class="fa fa-chevron-right"></i></a>
							</div>
						</h2>
					</div>
					<div class="aContent">
						<div class="flexform">
							<div class="dynamic-flexItem ">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('packhouseIncoming.offLoadingDate')}" />
								</p>
								<p class="flexItem">
									<s:property value="offLoadingDate" />
								</p>
							</div>
							<div class="dynamic-flexItem ">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('packhouseIncoming.packhouse')}" />
								</p>
								<p class="flexItem">
									<s:property value="packhouseIncoming.packhouse.name" />
								</p>
							</div>

							<div class="" style="overflow: auto;">
								<table class="table table-bordered aspect-detail"
									id="packhouseIncomingInfoTable">
									<thead>
										<tr>
											<th><s:property
													value="%{getLocaleProperty('packhouseIncoming.blockNo')}" /><sup
												style="color: red;">*</sup></th>
											<th><s:property
													value="%{getLocaleProperty('packhouseIncoming.blockId')}" /><sup
												style="color: red;">*</sup></th>
											<th><s:property
													value="%{getLocaleProperty('Planting ID')}" /><sup
												style="color: red;">*</sup></th>
											<th><s:property
													value="%{getLocaleProperty('Sorting ID')}" /><sup
												style="color: red;">*</sup></th>
											<th><s:property
													value="%{getLocaleProperty('packhouseIncoming.product')}" /><sup
												style="color: red;">*</sup></th>
											<th><s:property
													value="%{getLocaleProperty('packhouseIncoming.variety')}" /><sup
												style="color: red;">*</sup></th>
											<th><s:property
													value="%{getLocaleProperty('sort.sortDate')}" /></th>
											<th><s:property
													value="%{getLocaleProperty('transwieghkg')}" /><sup
												style="color: red;">*</sup></th>
											<th><s:property
													value="%{getLocaleProperty('packhouseIncoming.recievedWeight')}" /><sup
												style="color: red;">*</sup></th>
											<th><s:property
													value="%{getLocaleProperty('losswightkg')}" /><sup
												style="color: red;">*</sup></th>
											<th><s:property
													value="%{getLocaleProperty('packhouseIncoming.recievedUnits')}" /><sup
												style="color: red;">*</sup></th>
											<th><s:property
													value="%{getLocaleProperty('packhouseIncoming.noOfUnits')}" /><sup
												style="color: red;">*</sup></th>
										</tr>
									</thead>
									<tbody id="packhouseIncomingContent">
										<s:iterator value="packhouseIncoming.packhouseIncomingDetails"
											status="incr">
											<tr id="row<s:property	value="#incr.index" />">
												<s:hidden class="incoId" value="%{id}" />
												<td class="blockNo hide"><s:property
														value="planting.id" /></td>
												<td class="product hide"><s:property
														value="cw.planting.farmCrops.variety.procurementProduct.id" /></td>
												<td class="variety hide"><s:property
														value="cw.planting.farmCrops.variety.id" /></td>

												<td class="product"><s:property
														value="planting.farmCrops.blockName" /></td>
												<td class="blockId"><s:property
														value="cw.planting.farmCrops.blockId" /></td>
												<td class="blockId"><s:property
														value="cw.planting.plantingId" /></td>
												<td class="qrCodeId"><s:property value="qrCodeId" /></td>
												<td class="variety"><s:property
														value="cw.planting.variety.name" /></td>
												<td class=""><s:property value="cw.planting.grade.name" /></td>
												<td class=""><s:date name="sr.createdDate"
														format="dd-MM-yyyy" /></td>
												<td class="transferWeight"><s:property
														value="transferWeight" /></td>
												<td class="recievedWeight"><s:property
														value="receivedWeight" /></td>
												<td class="remainsWeight"><s:property
														value="totalWeight" /></td>
												<td class="recievedUnits hide"><s:property
														value="receivedUnits" /></td>
												<td class=""><s:property
														value="%{getCatalgueNameByCode(receivedUnits)}" /></td>
												<td class="noUnits"><s:property value="noUnits" /></td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
							<div class="dynamic-flexItem ">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('packhouseIncoming.totalWeight')}" />
								</p>
								<p class="flexItem">
									<s:property value="packhouseIncoming.totalWeight" />
								</p>
							</div>
							<div class="dynamic-flexItem ">
								<p class="flexItem">
									<s:property value="%{getLocaleProperty('packing.BatchNo')}" />
								</p>
								<p class="flexItem">
									<s:property value="packhouseIncoming.batchNo" />
								</p>
							</div>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('packhouseIncoming.truckType')}" />
								</p>
								<p class="flexItem">
									<%-- <s:property value="packhouseIncoming.truckType" /> --%>
									<s:property
										value='%{getCatlogueValueByCodeArray(packhouseIncoming.truckType)}' />
								</p>
							</div>
							<div class="dynamic-flexItem ">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('packhouseIncoming.truckNumber')}" />
								</p>
								<p class="flexItem">
									<s:property value="packhouseIncoming.truckNo" />
								</p>
							</div>
							<div class="dynamic-flexItem ">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('packhouseIncoming.driverName')}" />
								</p>
								<p class="flexItem">
									<s:property value="packhouseIncoming.driverName" />
								</p>
							</div>
							<div class="dynamic-flexItem ">
								<p class="flexItem">
									<s:property
										value="%{getLocaleProperty('packhouseIncoming.driverContact')}" />
								</p>
								<p class="flexItem">
									<s:property value="packhouseIncoming.driverCont" />
								</p>
							</div>

						</div>
					</div>
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
											href="#<s:property value="#innerList[1].id" />"><i
											class="fa fa-chevron-right"></i></a>
									</div>
								</h2>
							</div>
							<div class="aContent dynamic-form-con"
								id="<s:property value="#innerList[1].id" />">
								<jsp:include page='/jsp/jsp/auditPackhouseIncomingDetail.jsp' />
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
								<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
								</font></b>
							</button></span></span>
				</div>
			</div>
		</div>
		</div>
	</s:form>

</body>
<script type="text/javascript">
	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>";
	}
</script>