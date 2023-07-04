<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType")
					: "swithlayout"%>">

</head>


<script type="text/javascript">

	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>";
	}
	var url =<%out.print("'" + request.getParameter("url") + "'");%>;
	var status = '<s:property value="productTransfer.status"/>';
	var command = '<s:property value="command"/>';
	$(document).ready(function() {
						$(".breadCrumbNavigation").find('li:last').find(
								'a:first').attr("href",
								'<s:property value="redirectContent" />');
						url =<%out.print("'" + request.getParameter("url") + "'");%>;
						if (url == null || url == undefined || url == '' || url == 'null') {
							url = 'productReception_';
						}

						if(command=='update'){
							var transferReceiptID=$.trim('<s:property value="selectedTransferReceiptID"/>');
							//loadProductTransferData(transferReceiptID);
							 $('#transferReceiptID > option[value="'+transferReceiptID+'"]').prop("selected","selected");
						      $("#transferReceiptID").select2();
						      $("#transferReceiptID").change();
						      
						}
						
						var tenant = '<s:property value="getCurrentTenantId()"/>';
						var branchId = '<s:property value="getBranchId()"/>';
						
						$("#bAdd,#bUpdate").on('click', function(event) {
							event.preventDefault();
							
							var productListArray=getProductReceptionDetail();
							$("#productTransferListDetails").val(productListArray);
							 $("#buttonAdd1").prop('disabled', true);
							if (!validateAndSubmit("target", url)) {
								$("#buttonAdd1").prop('disabled', false);
								$("#buttonUpdate").prop('disabled', false);
							}
						});
					});
	
	
	function loadProductTransferData(val){
		var selectedTransferReceiptID=val;
		    $('#date').val('');
			$('#transferFrom').val('');
			$('#transferTo').val('');
			$('#truckNo').val('');
			$('#driverName').val('');
			$('#driverLicenseNumber').val(''); 
		if (!isEmpty(val)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "productReception_populateProductTransferData.action",
				data : {
					selectedTransferReceiptID : selectedTransferReceiptID,
					command : command
				},
				success : function(result) {
					$("#productReceptionContent").empty();
				    $('#date').val(result.date);
					$('#transferFrom').val(result.transferFrom);
					$('#transferFromId').val(result.transferFromId);
					$('#transferTo').val(result.transferTo);
					$('#transferToId').val(result.transferToId);
					$('#truckNo').val(result.truckNo);
					$('#driverName').val(result.driverName);
					$('#driverLicenseNumber').val(result.driverLicenseNumber); 
					
					for(var i=0;i<result.productTransferDetails.length;i++){
						var rowCounter = $('#cultiCharBody tr').length-1;
						var columnCounter = $('#cultiCharBody tr').length-1;
						var tableRow="<tr id=row"+(++rowCounter)+" class=impr "+ result.productTransferDetails[i].id +">";
					tableRow+="<td class='receptionBatchID'>"+result.productTransferDetails[i].receptionBatchID+"</td>";
					tableRow+="<td class='blockID'>"+result.productTransferDetails[i].blockID+"</td>";
					tableRow+="<td class='plantingID'>"+result.productTransferDetails[i].plantingID+"</td>";
					tableRow+="<td class='product'>"+result.productTransferDetails[i].product+"</td>";
					tableRow+="<td class='variety'>"+result.productTransferDetails[i].variety+"</td>";
					tableRow+="<td class='transferredWeight'>"+result.productTransferDetails[i].transferredWeight+"</td>";
					tableRow+="<td class='cid hide'>"+result.productTransferDetails[i].cid+"</td>";
					tableRow+="<td class='plantId hide'>"+result.productTransferDetails[i].plantId+"</td>";
					tableRow+="<td><input type='text' class='rw' name=rw_"+result.productTransferDetails[i].cid+" id=rw_"+result.productTransferDetails[i].cid+" value='"+result.productTransferDetails[i].receivedWeight+"'></td>";
					//tableRow+="<td><input type='text' class='rw' name=rw_"+result.productTransferDetails[i].cid+" id=rw_"+result.productTransferDetails[i].cid+"  onkeyup='checkReceivedWeightvalidation("+result.productTransferDetails[i].transferredWeight+",this);' value='"+result.productTransferDetails[i].receivedWeight+"'></td>";
					tableRow+="</tr>";
					
					/* if(parseFloat(result.productTransferDetails[i].receivedWeight)<=0){
					$("#bAdd").prop('disabled', true);
					$("#validateError").text('<s:property value="%{getLocaleProperty('Received Weight should be greater than Zero')}" />');
					} */
					
						jQuery("#productReceptionContent").append(tableRow);
						}
				},
			});
		}else{
			$("#productReceptionContent").empty();
		}
	}
	
	function getProductReceptionDetail(){
		var tableBody = $("#productReceptionContent tr:nth-child(n + 1)");
		var productTransferDtl="";	
		$.each(tableBody, function(index, value) {
			productTransferDtl+=jQuery(this).find(".receptionBatchID").text(); //0
			productTransferDtl+="#"+jQuery(this).find(".cid").text(); //1
			productTransferDtl+="#"+jQuery(this).find(".blockID").text(); //2
			productTransferDtl+="#"+jQuery(this).find(".plantId").text(); //3
			productTransferDtl+="#"+jQuery(this).find(".product").text(); //4
			productTransferDtl+="#"+jQuery(this).find(".variety").text(); //5
			productTransferDtl+="#"+jQuery(this).find(".transferredWeight").text();  //6
			productTransferDtl+="#"+jQuery(this).find(".rw").val()+"@"; //7
		});		
		return productTransferDtl;
	}
	
	/* function checkReceivedWeightvalidation(tw,rw){
		$("#validateError").text('');
		if(!isEmpty(tw) && !isEmpty(rw.value)){
		//if((parseFloat(tw) < parseFloat(rw.value)) || parseFloat(rw.value)<=0){
		if((parseFloat(tw) < parseFloat(rw.value))){
			$("#validateError").text('<s:property value="%{getLocaleProperty('Less than or equal to transferred weight')}" />');
			$('#'+rw.id).val('');
			$("#bAdd").prop('disabled', true);
		}else if(parseFloat(rw.value)<=0){
			$("#validateError").text('<s:property value="%{getLocaleProperty('Received Weight should be greater than Zero')}" />');
		}else{
			$("#bAdd").prop('disabled', false);
		}
	 }
	} */
	

</script>

<body>
	<s:form name="form" cssClass="fillform" method="post"
		action="productReception_%{command}" enctype="multipart/form-data"
		id="target">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:hidden id="proofFile1" name="proofFile1" />
		<s:hidden name="productTransferListDetails"
			id="productTransferListDetails" />

		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="productReception.id" />
		</s:if>
		<s:hidden key="command" />
		<div class="appContentWrapper marginBottom">

			<div class="error">
				<s:fielderror />
				<s:if test="hasActionErrors()">
					<div style="color: red;">
						<s:actionerror />
					</div>
				</s:if>
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>

			<div class="formContainerWrapper">
				<h2>
					<s:property value="%{getLocaleProperty('info.productReception')}" />
				</h2>
				<div class="flexform">

					<div class="flexform-item">
						<label for="txt"><s:text
								name="productReception.transferReceiptID" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="transferReceiptID"
								name="selectedTransferReceiptID" listKey="key" listValue="value" 
								list="transferReceiptIDList" maxlength="20"
								onchange="loadProductTransferData(this.value)"
								headerValue="%{getText('txt.select')}" headerKey="" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text
								name="productReception.date" /></label>
						<div class="form-element">
							<s:textfield name="selectedDate" theme="simple"
								cssClass="lowercase form-control" id="date" readOnly="true" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text
								name="productReception.transferFrom" /></label>
						<div class="form-element">
							<s:textfield name="transferFrom" theme="simple"
								cssClass="lowercase form-control" id="transferFrom" readOnly="true" />
								<s:hidden id="transferFromId" name="selectedTransferFrom" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text
								name="productReception.transferTo" /></label>
						<div class="form-element">
							<s:textfield name="transferTo" theme="simple"
								cssClass="lowercase form-control" id="transferTo" readOnly="true" />
								<s:hidden id="transferToId" name="selectedTransferTo" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt">
						<%-- <s:text name="productReception.truckNo" /> --%>
						<s:property value="%{getLocaleProperty('productReception.truckNo')}" />
						</label>
						<div class="form-element">
							<s:textfield name="productReception.truckNo" theme="simple"
								cssClass="lowercase form-control" id="truckNo" readOnly="true" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text
								name="productReception.driverName" /></label>
						<div class="form-element">
							<s:textfield name="productReception.driverName" theme="simple"
								cssClass="lowercase form-control" id="driverName" readOnly="true" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text
								name="productReception.driverLicenseNumber" /></label>
						<div class="form-element">
							<s:textfield name="productReception.driverLicenseNumber" theme="simple"
								cssClass="lowercase form-control" id="driverLicenseNumber" readOnly="true" />
						</div>
					</div>
					
				<table class="table table-bordered aspect-detail" id="productReceptionInfoTable">
					<thead>
							<tr>
								<th><s:property value="%{getLocaleProperty('productReception.receptionBatchID')}" /></th>
								<th><s:property value="%{getLocaleProperty('productTransfer.blockID')}" /></th>
								<th><s:property value="%{getLocaleProperty('productTransfer.plantingID')}" /></th>
								<th><s:property value="%{getLocaleProperty('productTransfer.product')}" /></th>
								<th><s:property value="%{getLocaleProperty('productTransfer.variety')}" /></th>
								<th><s:property value="%{getLocaleProperty('productTransfer.transferredWeight')}" /></th>
								<th><s:property value="%{getLocaleProperty('productReception.receivedWeight')}" /><sup style="color: red;">*</sup></th>
							</tr>
						</thead>
					<tbody id="productReceptionContent">
					
					<s:iterator value="productReception.productTransferDetails"
								status="incr">
								<tr id="row<s:property	value="#incr.index" />">
									<td class="receptionBatchID"><s:property value="ctt.batchNo" /></td>
								    <td class="cid hide"><s:property value="ctt.id" /></td>
									<td class="blockIDTxt"><s:property value="blockId.blockId" /> - <s:property value="blockId.blockName" /></td>
									<td class="blockID hide"><s:property value="blockId.id" /></td>
								     
									<td class="plantingIDTxt"><s:property value="planting.plantingId" /></td>
									<td class="plantId hide"><s:property value="planting.id" /></td>
									
									<%-- <td class="product"><s:property value="product" /></td>
									<td class="variety"><s:property value="variety" /></td> --%>
									<td class="product"><s:property value="planting.variety.name" /></td>
									<td class="variety"><s:property value="planting.grade.name" /></td>
									
									<td class="transferredWeight"><s:property value="ctt.sortedWeight" /></td>
									<td class="transferredWeight"><s:property value="transferredWeight" /></td>
								</tr>
							</s:iterator>
					
					</tbody>
				</table>

				</div>

				<div class="margin-top-10">
					<s:if test="command =='create'">
						<span class=""><span class="first-child">
								<button type="button" id="bAdd" class="save-btn btn btn-success">
									<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
									</font>
								</button>
						</span></span>
					</s:if>
					<s:else>
						<span class=""><span class="first-child">
								<button type="submit" id="bUpdate"
									class="save-btn btn btn-success">
									<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
									</font>
								</button>
						</span></span>
					</s:else>
					<span id="cancel" class=""><span class="first-child"><button
								type="button" class="cancel-btn btn btn-sts"
								onclick="onCancel();">
								<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
								</font></b>
							</button></span></span>
				</div>
			</div>
		</div>
		<br />
	</s:form>


</body>
