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
	<s:form name="form" cssClass="fillform" method="post"
		action="productTransfer_%{command}" id="form">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="productTransfer.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<%-- <h2>
					<s:text name="info.productTransfer" />
				</h2> --%>
							<div class="aPanel farmer_info">
								<div class="aTitle">
									<h2>
										<s:property value="%{getLocaleProperty('info.productTransfer')}" />
										<div class="pull-right">
											<a class="aCollapse" href="#"><i
												class="fa fa-chevron-right"></i></a>
										</div>
									</h2>
								</div>
						<div class="aContent dynamic-form-con">
				
				<div class="flexform">
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('Transfer Receipt ID')}" />
						</p>
						<p class="flexItem">
							 <s:property value="productTransfer.batchNo" /> 
						</p>
					</div>
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('productTransfer.date')}" />
						</p>
						<p class="flexItem">
							<s:date name="productTransfer.date" format="dd-MM-yyyy" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('productTransfer.exporter')}" />
						</p>
						<p class="flexItem">
							<s:property value="productTransfer.exporter.name" />
						</p>
					</div>
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('productTransfer.transferFrom')}" />
						</p>
						<p class="flexItem">
							<s:property value="productTransfer.transferFrom.name" />
						</p>
					</div>
				
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('productTransfer.transferTo')}" />
						</p>
						<p class="flexItem">
							<s:property value="productTransfer.transferTo.name" />
						</p>
					</div>
					
					
					<table class="table table-bordered aspect-detail"
						id="productTransferInfoTable">
						<thead>
							<tr>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.receptionBatchID')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.blockID')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.plantingID')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.product')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.variety')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.availableWeight')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.transferredWeight')}" /></th>
							</tr>
						</thead>
						<tbody id="productTransferContent">
							<s:iterator value="productTransfer.productTransferDetails"
								status="incr">
								<tr id="row<s:property	value="#incr.index" />">
									<td class="receptionBatchIDTxt"><s:property value="ctt.batchNo" /></td>
								    <td class="receptionBatchID hide"><s:property value="ctt.id" /></td>
								     
									<td class="blockIDTxt"><s:property value="blockId.blockId" /> - <s:property value="blockId.blockName" /></td>
									<td class="blockID hide"><s:property value="blockId.id" /></td>
								     
									<td class="plantingIDTxt"><s:property value="planting.plantingId" /></td>
									<td class="plantingID hide"><s:property value="planting.id" /></td>
									
									<%-- <td class="product"><s:property value="product" /></td>
									<td class="variety"><s:property value="variety" /></td> --%>
									<td class="product"><s:property value="planting.variety.name" /></td>
									<td class="variety"><s:property value="planting.grade.name" /></td>
									<td class="availableWeight"><s:property value="ctt.sortedWeight" /></td>
									<td class="transferredWeight"><s:property value="transferredWeight" /></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					
					
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('productTransfer.truckNo')}" />
						</p>
						<p class="flexItem">
							<s:property value="productTransfer.truckNo" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('productTransfer.driverName')}" />
						</p>
						<p class="flexItem">
							<s:property value="productTransfer.driverName" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('productTransfer.driverLicenseNumber')}" />
						</p>
						<p class="flexItem">
							<s:property value="productTransfer.driverLicenseNumber" />
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
										href="#<s:property value="#innerList[1].id" />
										"><i
										class="fa fa-chevron-right"></i></a>
								</div>
							</h2>
									</div>
									<div class="aContent dynamic-form-con"
										id="<s:property value="#innerList[1].id" />">
										<jsp:include page='/jsp/jsp/auditProductTransferDetails.jsp' />
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
