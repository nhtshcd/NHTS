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
							url = 'productTransfer_';
						}

						var tenant = '<s:property value="getCurrentTenantId()"/>';
						var branchId = '<s:property value="getBranchId()"/>';

						
						//if(command=='update'){
							var exporterId=$.trim('<s:property value="productTransfer.exporter.id"/>');
							loadTransferFrom(exporterId);
						//}
						
						
						$("#bAdd,#bUpdate").on('click', function(event) {
							event.preventDefault();
							
							var productListArray=getProductTransferDetail();
							$("#productTransferListDetails").val(productListArray);
							
							$("#buttonAdd1").prop('disabled', true);

							if (!validateAndSubmit("target", url)) {
								$("#buttonAdd1").prop('disabled', false);
								$("#buttonUpdate").prop('disabled', false);
							}
						});
					});
	
	/* function loadTransferFrom(val){
		var selectedExporter=val;
		clearElement('transferFrom',true);
		clearElement('transferTo',true);
		if (!isEmpty(val)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "productTransfer_populatePackhouseByExporter.action",
				data : {
					selectedExporter : selectedExporter
				},
				success : function(result) {
					insertOptions("transferFrom", $.parseJSON(result));
					insertOptions("transferTo", $.parseJSON(result));
					
					if(command=='update'){
		        		var selectedTransferFrom ='<s:property value="selectedTransferFrom"/>';
		        		var selectedTransferTo ='<s:property value="selectedTransferTo"/>';
			        	if(selectedTransferFrom!=null && selectedTransferFrom!='' && selectedTransferFrom!='null' &&
			        	   selectedTransferTo!=null && selectedTransferTo!='' && selectedTransferTo!='null'	){
			        		$('#transferFrom').val(selectedTransferFrom).change();
			        		$('#transferTo').val(selectedTransferTo).change();
			        	}
		        	} 
				},
			});
		}
	} 
	function loadReceiptNoBasedOnPackhouse(val){
		var selectedPackhouse=val;
		clearElement('receptionBatchID',true);
		if (!isEmpty(val)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "productTransfer_populateReceiptNoBasedOnPackhouse.action",
				data : {
					selectedPackhouse : selectedPackhouse
				},
				success : function(result) {
					insertOptions("receptionBatchID", $.parseJSON(result));
				},
			});
		}
	}  */
	
	function loadBlockIDbyReceiptNo(val){
		var selectedReceptionBatchID=val;
		clearElement('blockID',true);
		if (!isEmpty(val)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "productTransfer_populateBlockIDByReceiptNo.action",
				data : {
					selectedReceptionBatchID : selectedReceptionBatchID,
					selectedPackhouse : $('#transferFrom').val()
				},
				success : function(result) {
					insertOptions("blockID", $.parseJSON(result));
				},
			});
		}
	} 
	
	function loadPlantingIDbyReceiptNo(val){
		var selectedBlockID=val;
		var selectedReceptionBatchID=$("#receptionBatchID option:selected").val();
		clearElement('plantingID',true);
		if (!isEmpty(val)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "productTransfer_populatePlanting.action",
				data : {
					selectedBlockID : selectedBlockID,
					selectedPackhouse : $('#transferFrom').val(),
					selectedReceptionBatchID : selectedReceptionBatchID
				},
				success : function(result) {
					insertOptions("plantingID", $.parseJSON(result));
				},
			});
		}
	} 

	function loadBlockData(val){
		var selectedPlantingID=val;
		var selectedReceptionBatchID=$("#receptionBatchID option:selected").val();
		$('#product').val('');
		$('#variety').val('');
		$('#availableWeight').val('');
		if (!isEmpty(val)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "productTransfer_populateBlockData.action",
				data : {
					selectedReceptionBatchID : selectedReceptionBatchID,
					selectedPlantingID : selectedPlantingID,
					selectedPackhouse : $('#transferFrom').val()
				},
				success : function(result) {
					$('#product').val(result.product);
					$('#variety').val(result.variety);
					$('#availableWeight').val(result.availableWeight);
				},
			});
		}
	} 
	
	
	var arr=[];
	function addRow(rowId){
		   var rowCounter = $('#productTransferContent tr').length-1;
		   	if(rowId!=''){
		   		$('#'+rowId).remove();
		   	}
		   	$("#validateError").text("");
			var receptionBatchIDTxt = $("#receptionBatchID option:selected").text();	
		   	var receptionBatchID=$("#receptionBatchID").val();	
			var blockIDTxt = $("#blockID option:selected").text();
			var blockID = $("#blockID").val();	
			var plantingIDTxt = $("#plantingID option:selected").text();
			var plantingID = $("#plantingID").val();	
		   	var product=$("#product").val();	
		   	var variety=$("#variety").val();
		   	var availableWeight=$("#availableWeight").val();
		   	var transferredWeight=$("#transferredWeight").val();
			arr.push(plantingID);
			arr.pop();		
			//if(plantingID!=null && plantingID!='' && $('.'+plantingID).attr("id")!=null && $('.'+plantingID).attr("id")!=undefined && $('.'+plantingID).attr("id")!=''){
			if(plantingID!=null && plantingID!='' && receptionBatchIDTxt!=null && receptionBatchIDTxt!='' && $('.'+plantingID+receptionBatchIDTxt).attr("id")!=null && $('.'+plantingID+receptionBatchIDTxt).attr("id")!=undefined && $('.'+plantingID+receptionBatchIDTxt).attr("id")!=''){
		   		alert("Product details already exists. Please update the list");
		   	}else{
			arr.push(plantingID);
			 if(isEmpty(receptionBatchID)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.receptionBatchID')}" />');
				return false;
			}if(isEmpty(blockID)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.blockID')}" />');
				return false;
			}if(isEmpty(plantingID)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.plantingID')}" />');
				return false;
			}if(isEmpty(product)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.product')}" />');
				return false;
			}else if(isEmpty(variety)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.variety')}" />');
		   		return false;
		   	}else if(isEmpty(availableWeight)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.availableWeight')}" />');
		   		return false;
		   	}
		   	else if(isEmpty(transferredWeight)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.transferredWeight')}" />');
		   		return false;
		   	}else if(parseFloat(transferredWeight)<=0){
				$("#validateError").text('<s:property value="%{getLocaleProperty('producttransfer.transferredWeight.zero')}" />');
				return false;
		    }else if(parseFloat(availableWeight) < parseFloat(transferredWeight)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.transferredWeightNavailableWeight')}" />');
				return false;
		   	}
			
			
			var tableRow="<tr id=row"+(++rowCounter)+" class='prodrow "+plantingID+receptionBatchIDTxt+"'>";
			tableRow+="<td class='receptionBatchIDTxt' >"+receptionBatchIDTxt+"</td>";		
			tableRow+="<td class='receptionBatchID hide'>"+receptionBatchID+"</td>";
			
		 	tableRow+="<td class='blockIDTxt'>"+blockIDTxt+"</td>";
		 	tableRow+="<td class='blockID hide'>"+blockID+"</td>";
		 	
		 	tableRow+="<td class='plantingIDTxt'>"+plantingIDTxt+"</td>";
		 	tableRow+="<td class='plantingID hide'>"+plantingID+"</td>";
		 	
			tableRow+="<td class='product'>"+product+"</td>";		
			tableRow+="<td class='variety'>"+variety+"</td>";		
			tableRow+="<td class='availableWeight'>"+availableWeight+"</td>";		
			tableRow+="<td class='transferredWeight'>"+transferredWeight+"</td>";		
			
			tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i>&nbsp;&nbsp;&nbsp;&nbsp;<i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteDtl("+rowCounter+")'></td>";
		   	tableRow+="</tr>";
		   	$("#productTransferContent").append(tableRow);
		  	resetData();
			$("#add").attr("onclick","addRow()");
		   	}
	}
	
	function deleteDtl(rowCounter){
	   	var id="#row"+rowCounter;
	   	$(id).remove();
	}
	
	function editRow(rowCounter){
	 	var id="#row"+rowCounter;
		$(id).each(function(index, value) {
			 var receptionBatchID = $(this).find(".receptionBatchID").text().trim();
			 var receptionBatchLabel = $(this).find(".receptionBatchIDTxt").text().trim();
			 
			 var blockID = $(this).find(".blockID").text().trim();
			 var blockIDTxt = $(this).find(".blockIDTxt").text().trim();
			 
			 var plantingID = $(this).find(".plantingID").text().trim();
			 var plantingIDTxt = $(this).find(".plantingIDTxt").text().trim();
			 
			 var product = $(this).find(".product").text();
			 var variety = $(this).find(".variety").text();
			 var availableWeight = $(this).find(".availableWeight").text();
			 var transferredWeight = $(this).find(".transferredWeight").text();
			 
				$("#receptionBatchID").val(receptionBatchID).trigger('change');
			   	if($("#receptionBatchID").val()==null || $("#receptionBatchID").val()=='' ){
			    	 $('#receptionBatchID').append(new Option(receptionBatchLabel, receptionBatchID));
			 	 	$("#receptionBatchID").val(receptionBatchID).trigger('change');	
			   	} 
			   	
				$("#blockID").val(blockID).trigger('change');
			   	if($("#blockID").val()==null || $("#blockID").val()=='' ){
			    	 $('#blockID').append(new Option(blockIDTxt, blockID));
			 	 	$("#blockID").val(blockID).trigger('change');	
			   	} 
			   	
				$("#plantingID").val(plantingID).trigger('change');
			   	if($("#plantingID").val()==null || $("#plantingID").val()=='' ){
			    	 $('#plantingID').append(new Option(plantingIDTxt, plantingID));
			 	 	$("#plantingID").val(plantingID).trigger('change');	
			   	} 
			   	
			$("#product").val(product);
			$("#variety").val(variety);
			$("#availableWeight").val(availableWeight);
			$("#transferredWeight").val(transferredWeight);
		});
		$("#add").attr("onclick","addRow('row"+rowCounter+"')");
	}

	function resetData(){
		$("#receptionBatchID").val(null).trigger('change');
		$("#blockID").val(null).trigger('change');
		$("#plantingID").val(null).trigger('change');
		$("#product").val("");	
		$("#variety").val("");	
		$("#availableWeight").val("");	
		$("#transferredWeight").val("");	
	}

	function getProductTransferDetail(){
		var tableBody = $("#productTransferContent tr:nth-child(n + 2)");
		var productTransferDtl="";	
		$.each(tableBody, function(index, value) {
			productTransferDtl+=jQuery(this).find(".receptionBatchID").text(); //0
			productTransferDtl+="#"+jQuery(this).find(".blockID").text(); //1
			productTransferDtl+="#"+jQuery(this).find(".plantingID").text(); //2
			productTransferDtl+="#"+jQuery(this).find(".product").text(); //3
			productTransferDtl+="#"+jQuery(this).find(".variety").text(); //4
			productTransferDtl+="#"+jQuery(this).find(".availableWeight").text();  //5
			productTransferDtl+="#"+jQuery(this).find(".transferredWeight").text()+"@"; //6	
		});		
		return productTransferDtl;
	}
	
	
	function loadReceiptNoBasedOnPackhouse(val){
		var selectedPackhouse=val;
		//clearElement('receptionBatchID',true);
			var pid= val;
			var rows= $('#productTransferContent .prodrow').length;
			if(rows>0 && pid!='<s:property value="selectedTransferFrom"/>'){
				if(confirm("Product details already exist. Are you sure to change the Transfer From?")){
				  	$('.prodrow').remove();
					var rows= $('#productTransferContent .prodrow').length;
						$('#transferFormval').val(pid);
						
						if (!isEmpty(val)) {
							$.ajax({
								type : "POST",
								async : false,
								url : "productTransfer_populateReceiptNoBasedOnPackhouse.action",
								data : {
									selectedPackhouse : selectedPackhouse
								},
								success : function(result) {
									insertOptions("receptionBatchID", $.parseJSON(result));
								},
							});
						}
						resetSelect2();
						
				}else{$('#transferFrom').val($('#transferFormval').val()).select2();}
			}else{
				resetData();
			var pid= val;
			var rows= $('#productTransferContent .prodrow').length;
				$('#transferFormval').val(pid);
				
				if (!isEmpty(val)) {
					$.ajax({
						type : "POST",
						async : false,
						url : "productTransfer_populateReceiptNoBasedOnPackhouse.action",
						data : {
							selectedPackhouse : selectedPackhouse
						},
						success : function(result) {
							insertOptions("receptionBatchID", $.parseJSON(result));
						},
					});
				}
				resetSelect2();
			}
		}
	

	function loadTransferFrom(val){
		var selectedExporter=val;
		//clearElement('transferFrom',true);
		//clearElement('transferTo',true);
			var pid= val;
			var rows= $('#productTransferContent .prodrow').length;
			if(rows>0 && pid!='<s:property value="productTransfer.exporter.id"/>'){
				if(confirm("Product details already exist. Are you sure to change the Exporter?")){
				  	$('.prodrow').remove();
					var rows= $('#productTransferContent .prodrow').length;
						$('#exporterVal').val(pid);
						
						if (!isEmpty(val)) {
							$.ajax({
								type : "POST",
								async : false,
								url : "productTransfer_populatePackhouseByExporter.action",
								data : {
									selectedExporter : selectedExporter
								},
								success : function(result) {
									insertOptions("transferFrom", $.parseJSON(result));
									insertOptions("transferTo", $.parseJSON(result));
									
									if(command=='update'){
						        		var selectedTransferFrom ='<s:property value="selectedTransferFrom"/>';
						        		var selectedTransferTo ='<s:property value="selectedTransferTo"/>';
							        	if(selectedTransferFrom!=null && selectedTransferFrom!='' && selectedTransferFrom!='null' &&
							        	   selectedTransferTo!=null && selectedTransferTo!='' && selectedTransferTo!='null'	){
							        		$('#transferFrom').val(selectedTransferFrom).change();
							        		$('#transferTo').val(selectedTransferTo).change();
							        	}
						        	} 
								},
							});
						}
						resetSelect2();
						
				}else{$('#exporter').val($('#exporterVal').val()).select2();}
			}else{
				resetData();
			var pid= val;
			var rows= $('#productTransferContent .prodrow').length;
				$('#exporterVal').val(pid);
				
				if (!isEmpty(val)) {
					$.ajax({
						type : "POST",
						async : false,
						url : "productTransfer_populatePackhouseByExporter.action",
						data : {
							selectedExporter : selectedExporter
						},
						success : function(result) {
							insertOptions("transferFrom", $.parseJSON(result));
							insertOptions("transferTo", $.parseJSON(result));
							
							if(command=='update'){
				        		var selectedTransferFrom ='<s:property value="selectedTransferFrom"/>';
				        		var selectedTransferTo ='<s:property value="selectedTransferTo"/>';
					        	if(selectedTransferFrom!=null && selectedTransferFrom!='' && selectedTransferFrom!='null' &&
					        	   selectedTransferTo!=null && selectedTransferTo!='' && selectedTransferTo!='null'	){
					        		$('#transferFrom').val(selectedTransferFrom).change();
					        		$('#transferTo').val(selectedTransferTo).change();
					        	}
				        	} 
						},
					});
				}
				resetSelect2();
			}
		}
		
	function resetSelect2(){
		$(".select2").select2();
	}
	
</script>

<body>
	<s:form name="form" cssClass="fillform" method="post"
		action="productTransfer_%{command}" enctype="multipart/form-data"
		id="target">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:hidden id="proofFile1" name="proofFile1" />
		<s:hidden name="productTransferListDetails" id="productTransferListDetails" />

		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="productTransfer.id" />
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
					<s:property value="%{getLocaleProperty('info.productTransfer')}" />
				</h2>
				<div class="flexform">


					<div class="flexform-item">
						<label for="txt"><s:text name="productTransfer.date" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="selectedDate" id="selectedDate" theme="simple"
								maxlength="10" readonly="true"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="date-picker form-control input-sm" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="productTransfer.exporter" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:if
								test="productTransfer.exporter!=null && productTransfer.exporter.id>0">
								<s:property value="productTransfer.exporter.name" />
								<s:hidden name="productTransfer.exporter.id"></s:hidden>
							</s:if>
							<s:else>
								<s:select name="productTransfer.exporter.id" id="exporter"
									list="exporterList" listKey="key" listValue="value"
									theme="simple" headerKey=""
									headerValue="%{getText('txt.select')}"
									onchange="loadTransferFrom(this.value)"
									cssClass="form-control input-sm select2" />
									<s:hidden id="exporterVal" />
							</s:else>
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="productTransfer.transferFrom" /><sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="transferFrom"
								name="selectedTransferFrom" listKey="id" listValue="name"
								list="{}" maxlength="20" headerValue="%{getText('txt.select')}"
								onchange="loadReceiptNoBasedOnPackhouse(this.value)"
								headerKey="" />
							<s:hidden id="transferFormval" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="productTransfer.transferTo" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="transferTo"
								name="selectedTransferTo" listKey="id" listValue="name"
								list="{}" maxlength="20" headerValue="%{getText('txt.select')}"
								headerKey="" />
						</div>
					</div>

					<table class="table table-bordered aspect-detail"
						id="productTransferInfoTable">
						<thead>
							<tr>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.receptionBatchID')}" /><sup
									style="color: red;">*</sup></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.blockID')}" /><sup
									style="color: red;">*</sup></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.plantingID')}" /><sup
									style="color: red;">*</sup></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.product')}" /><sup
									style="color: red;">*</sup></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.variety')}" /><sup
									style="color: red;">*</sup></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.availableWeight')}" /><sup
									style="color: red;">*</sup></th>
								<th><s:property
										value="%{getLocaleProperty('productTransfer.transferredWeight')}" /><sup
									style="color: red;">*</sup></th>

								<th style="text-align: center"><s:property
										value="%{getLocaleProperty('action')}" /></th>
							</tr>
						</thead>
						<tbody id="productTransferContent">
							<tr>
								<td><s:select class="form-control  select2"
										id="receptionBatchID" name="selectedReceptionBatchID"
										listKey="id" listValue="name" list="{}" maxlength="20"
										onchange="loadBlockIDbyReceiptNo(this.value)"
										headerValue="%{getText('txt.select')}" headerKey="" /></td>
								<td><s:select class="form-control  select2"
										id="blockID" name="selectedBlockID"
										listKey="id" listValue="name" list="{}" maxlength="20"
										onchange="loadPlantingIDbyReceiptNo(this.value)"
										headerValue="%{getText('txt.select')}" headerKey="" /></td>
								<td><s:select class="form-control  select2"
										id="plantingID" name="selectedPlantingID"
										listKey="id" listValue="name" list="{}" maxlength="20"
										onchange="loadBlockData(this.value)"
										headerValue="%{getText('txt.select')}" headerKey="" /></td>
								<td><s:textfield name="product" theme="simple"
										cssClass="lowercase form-control" id="product"
										readOnly="true" /></td>
								<td><s:textfield name="variety" theme="simple"
										cssClass="lowercase form-control" id="variety"
										readOnly="true" /></td>
								<td><s:textfield name="availableWeight" theme="simple"
										cssClass="lowercase form-control" id="availableWeight"
										readOnly="true" /></td>
								<td><s:textfield name="transferredWeight" theme="simple"
										cssClass="lowercase form-control" id="transferredWeight"
										onkeypress="return isDecimal1(event,this)" /></td>
								<td style="text-align: center">
									<button type="button" id="add" class="btn btn-sm btn-success"
										aria-hidden="true" onclick="addRow()"
										title="<s:text name="Ok" />">
										<i class="fa fa-check"></i>
									</button>
									<button type="button" class="btn btn-sm btn-danger"
										aria-hidden="true " onclick="resetData()"
										title="<s:text name="Cancel" />">
										<i class="glyphicon glyphicon-remove-sign"></i>
									</button>
								</td>
							</tr>
							<s:iterator value="productTransfer.productTransferDetails"
								status="incr">
								<tr id="row<s:property	value="#incr.index" />" class="prodrow">
									<td class="receptionBatchIDTxt"><s:property value="ctt.batchNo" /></td>
								    <td class="receptionBatchID hide"><s:property value="ctt.batchNo" /></td>
								     
									<td class="blockIDTxt"><s:property value="blockId.blockId" /> - <s:property value="blockId.blockName" /></td>
									<td class="blockID hide"><s:property value="blockId.id" /></td>
								     
									<td class="plantingIDTxt"><s:property value="planting.plantingId" /></td>
									<td class="plantingID hide"><s:property value="planting.id" /></td>
									
									<%-- <td class="product"><s:property value="product" /></td>
									<td class="variety"><s:property value="variety" /></td> --%>
									<td class="product"><s:property value="planting.variety.name" /></td>
									<td class="variety"><s:property value="planting.grade.name" /></td>
									
									<%-- <td class="availableWeight"><s:property value="availableWeight" /></td> --%>
									<td class="availableWeight"><s:property value="ctt.sortedWeight" /></td>
									<td class="transferredWeight"><s:property value="transferredWeight" /></td>
									<td><i
										style="cursor: pointer; font-size: 150%; color: blue;"
										class="fa fa-pencil-square-o" aria-hidden="true"
										onclick='editRow("<s:property value="#incr.index" />")'></i>
										&nbsp;&nbsp;&nbsp;&nbsp;<i
										style="cursor: pointer; font-size: 150%; color: black;"
										class="fa fa-trash-o" aria-hidden="true"
										onclick='deleteDtl("<s:property value="#incr.index" />")'></i></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>


					<div class="flexform-item">
						<label for="txt">
						<%-- <s:text name="productTransfer.truckNo" /> --%>
						<s:property value="%{getLocaleProperty('productTransfer.truckNo')}" />
						<sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="productTransfer.truckNo" theme="simple"
								cssClass="lowercase form-control" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="productTransfer.driverName" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="productTransfer.driverName" theme="simple"
								cssClass="lowercase form-control" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="productTransfer.driverLicenseNumber" /> <sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="productTransfer.driverLicenseNumber"
								theme="simple" cssClass="lowercase form-control" />
						</div>
					</div>
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
