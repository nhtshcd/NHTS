<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
<title>Insert title here</title>
</head>
<style>
@media print {
    body * {
        visibility: hidden;
    }

    @page {size: portrait}

    #printableArea {
        visibility: visible;
        width : 100%;
        height : auto;
    }
}
</style>
<body>
	<script src="js/dynamicComponents.js?v=18.32"></script>
	<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
	<script>
		var idd = '';
		jQuery(document)
				.ready(
						function() {

							$(".breadCrumbNavigation").find('li:last').find(
									'a:first').attr("href",
									'<s:property value="redirectContent" />');
							var page = "<s:property value="redirectContent" />";
							const string = page;
							const substring = "nhts/dynamicViewReportDT_list.action?id=16";

							if(string.indexOf(substring) !== -1){
								$("#printBut").show();
							}else{
								$("#printBut").hide();
							}
							function onCancel() {
								window.location.href = "<s:property value="redirectContent" />";

							}

						});
		
		
			
	</script>
	<s:form name="form">
		<s:hidden key="currentPage" />
		<s:hidden key="packing.id" id="id" class='uId' />
		<s:hidden key="command" />
		<s:hidden key="redirectContent" />
		<div style="text-align:right;">
		<input type="button" id="printBut" onclick="printDiv('printableArea')" value="Print" />
		</div>
		<!-- <div class="flex-view-layout">
			<div class="fullwidth">
				<div class="flexWrapper"> -->
					<div class="flexLeft appContentWrapper" id="printableArea">
						<div class="formContainerWrapper dynamic-form-con">
							<h2>
								<s:property value="%{getLocaleProperty('info.packing')}" />
							</h2>
							<s:if test='branchId==null'>
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="app.branch" />
									</p>
									<p class="flexItem">
										<s:property value="%{getBranchName(packing.branchId)}" />
									</p>
								</div>
							</s:if>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="packing.packingDate" />
								</p>
								<p class="flexItem">
									<s:property value="packingDate" />
								</p>
							</div>
						

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="packing.packhouse" />
								</p>
								<p class="flexItem">
									<s:property value="selectedPackHouse" />
								</p>
							</div>

							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="packing.packerName" />
								</p>
								<p class="flexItem">
									<s:property value="packing.packerName" />
								</p>
							</div>
<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="packing.lotNo" />
								</p>
								<p class="flexItem">
									<s:property value="packing.batchNo" />
								</p>
							</div>
							<!-- <table class="table table-bordered aspect-detail"
								id="procurementDetailTable" style=""> -->
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
										<%-- <th><s:property
												value="%{getLocaleProperty('packing.rejectWt')}" /></th> --%>
										<th><s:property
												value="%{getLocaleProperty('packing.bestBefore')}" /></th>
										<th><s:property
												value="%{getLocaleProperty('packing.country')}" /></th>

									</tr>
								</thead>

								<tbody id="procurementDetailContent">
									<s:iterator value="packing.packingDetails" status="incr">
										<tr id="row<s:property 	value="#incr.index" />">
											<td><s:property value="blockId.farm.farmer.firstName" /><s:text name=" - " /><s:property value="blockId.farm.farmer.farmerId" /></td>
											<td><s:property value="blockId.farm.farmName" /><s:text name=" - " /><s:property value="blockId.farm.farmCode" /></td>
											<td><s:property value="blockId.blockId" /><s:text name=" - " /> <s:property value="blockId.blockName" /></td>
											<td><s:property value="planting.plantingId" /></td>
											<td><s:property value="batchNo" /></td>
											<td><s:property value="planting.variety.name" /></td>
											<td><s:property value="planting.grade.name" /></td>
											<td class="createdDate"><s:date name="ctt.createdDate" format="dd-MM-yyyy" /></td>
											<td class=" pquantity"><s:property value="quantity" /></td>
											<td class="rejectWt"><s:property value="rejectWt" /></td>
											<td class=" price"><s:property value="price" /></td>
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
						</div>
						<div class="margin-top-10">

							<span id="cancel" class=""><span class="first-child"><button
										type="button" class="back-btn btn btn-sts"
										onclick="onCancel()">
										<b><FONT color="#FFFFFF"><s:text name="back.button" />
										</font></b>
									</button></span></span>
						</div>
					</div>
			<!-- 	</div>
			</div>
		</div> -->
	</s:form>
	<s:form id="fileDownload" action="agroChemicalDealer_populateDownload">
		<s:hidden id="loadId" name="idd" />

	</s:form>

</body>