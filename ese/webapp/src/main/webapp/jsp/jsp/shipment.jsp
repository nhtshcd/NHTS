<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css" />
<head>
<meta name="decorator" content="swithlayout">
</head>
<script type="text/javascript">
$(document).ready(			
		function() {
		  
			$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
			var command = '<s:property value="command"/>';
			if(command=='create'){
			var pidClass= $('.packingQty').text();
			$('#totalShipmentQty').val(pidClass);
			$( "#shipmentDate" ).datepicker(

					{
					
				  // format: 'mm/dd/yyyy',
						autoclose: true,
						beforeShow : function()
						{
						jQuery( this ).datepicker();
						},
						changeMonth: true,
						changeYear: true

					}

					);
			}else if(command=='update'){
				var valss='<s:property value="shipment.totalShipmentQty"/>';
			 
					$('#totalShipmentQty').val(valss);
					$('#packhouse').select2({disabled:'readonly'});
					$('#packhouse').select2({disabled:'readonly'});
					var shipmentDate='<s:property value="shipmentDate"/>';
					$( "#shipmentDate" ).datepicker(

							{
							
						  // format: 'mm/dd/yyyy',
				          startDate: shipmentDate,
				           autoclose: true,
							beforeShow : function()
							{
							jQuery( this ).datepicker({ maxDate: shipmentDate });
							},
							changeMonth: true,
							changeYear: true

							}

							);
					var selectedBuyer='<s:property value="selectedBuyer"/>';
					getCountry(selectedBuyer);
			}
			$("#buttonAdd1").on(
					"click",
					function(event) {
						event.preventDefault();
						var shipmentDtl=getShipmentDetail();
						$("#shipmentDtl").val(shipmentDtl);
						$('#packhouse').select2({disabled:false});
						$("#buttonAdd1").prop("disabled", true);
						
						var shipmentSupportingFilesName='';
						var fileList = document.getElementById("shipmentSupportingFiles").files;
						for(var i = 0; i < fileList.length; i++) {
							shipmentSupportingFilesName += fileList[i].name + ", ";
						}
						$("#shipmentSupportingFileName").val(shipmentSupportingFilesName);
						
						if (!validateAndSubmit("shipmentForm",
								"shipment_")) {
							$("#buttonAdd1").prop("disabled", false);
						}
						$("#buttonAdd1").prop("disabled", false);
					});
			loadLotNumbers();
			var isEdit='<s:property value="isEdit"/>';
			 if(isEdit == '0'){
				 $("#buttonAdd1").prop("disabled", true);
				 $("#validateError").text("Shipment recalled so you can't edit this record");
			 }else{
				 $("#buttonAdd1").prop("disabled", false);
				 $("#validateError").text("");
			 }
			 
			 $("#shipmentDate").focusin(function(){ 
				 var createdDate = $(".createdDate").text();
				 if(!isEmpty(createdDate) && command == 'create'){
					 if(confirm('<s:property value="%{getLocaleProperty('reset.resetListDateshipment')}" />')){
				            $('.prodrow').remove();
				            $("#totalShipmentQty").val("");
				            $("#packhouse").val(" ").change();
						 $('#shipmentDate').blur();
			      }   else{
			    	 $('#shipmentDate').blur();
			    	 setTimeout(closeDatepickernew, 400);
			     }
				 }
				});
			 
			 $("#shipmentSupportingFiles").on("change", function() {
				 $("#validateError").text("");
				    if ($("#shipmentSupportingFiles")[0].files.length > 3) {
				        $("#validateError").text("You can select only 3 files");
				        $('#shipmentSupportingFiles').val('');
				    }
				});
			 
		});
function loadLotNumbers() {
	var pid= $('#packhouse').val();
	
	var rows= $('#shipmentContent .prodrow').length;
	if(rows>0 && pid!='<s:property value="selectedPackhouse"/>'){
		if(confirm("Product details already exist.Are you sure to change the Packhouse?")){
		
		  	$('.prodrow').remove();
		  
			
			var rows= $('#shipmentContent .prodrow').length;

				$('#packinval').val(pid);
				$.ajax({
					type : "POST",
					async : false,
					url : "shipment_populateLotNumbers.action",
					data:{ pid:pid,id:$('#sid').val() },
					success : function(result) {
						insertOptions("lotNo", $.parseJSON(result));
					}
				});

		}else{
			$('#packhouse').val($('#packinval').val()).select2();
			
		}
	}else{
		
	
	resetData();
	var pid= $('#packhouse').val();
	
	var rows= $('#shipmentContent .prodrow').length;

		$('#packinval').val(pid);
		$.ajax({
			type : "POST",
			async : false,
			url : "shipment_populateLotNumbers.action",
			data:{ pid:pid,id:$('#sid').val() },
			success : function(result) {
				insertOptions("lotNo", $.parseJSON(result));
			}
		});
	
	}
	
}

function loadExportLicenseNo(pId){
	if(!isEmpty(pId)){
		$.ajax({
			type : "POST",
			async : false,
			url : "shipment_populateExportLicenseNo.action",
			data:{
				expLicNo:pId	
			},
			success : function(result) {
				$('#expLicNo').val(result.expLicNo);
			}
		});
	}else{
		$('#expLicNo').val("");
	}
	var command = '<s:property value="command"/>';
	if(command=='create'){
	var totalShipmentQty=0;
 	$("#totalShipmentQty").val(totalShipmentQty);
	}
}
function loadProductVariety(lotNo){
	$('#product').val('');
	$('#variety').val('');
	$('#lotQty').val('');
	$('#packingQty').val('');
	$('#qrCode').val('');
	$('#createdDate').val('');
	var batchNo= $('#lotNo').val();
	if(!isEmpty(lotNo)){
		$.ajax({
			type : "POST",
			async : false,
			url : "shipment_populateProductVariety.action",
			data:{
				selectedLotNo:lotNo,
				lotNos:batchNo
			},
			success : function(result) {
				$('#product').val(result.product);
				$('#variety').val(result.variety);
				$('#lotQty').val(result.lotQty);
				$('#packingQty').val(result.lotQty);
				$('#qrCode').val(result.qrCode);
				$('#createdDate').val(result.createdDate);
				$('#warId').val(result.ctyId);
			}
		});
	}
}
var totalShipmentQty=0;
function addRow(rowId){
	
	
	var tableBody = $(".prodrow");
	var shipmentDtls="";	
	
	
	   var rowCounter = $('#shipmentContent tr').length-1;
	   	if(rowId!=''){
	   		$('#'+rowId).remove();
	   	}
	   
	   	$("#validateError").text("");		   
	   	var blck =  $("#block option:selected").text();
	 	var block=$("#block").val();	
	 	var plantingId=$("#plantingId").val();	
	   	var lotNo = $("#lotNo option:selected").text();
	  	var lotNoVal = $("#lotNo").val();
	  	lotNoVal =lotNoVal!=null ? lotNoVal.trim() : "";
	  	block =block!=null ? block.trim() : "";
	  	plantingId =plantingId!=null ? plantingId.trim() : "";
		//if(block.trim()!=null && block.trim()!='' && lotNoVal!=null && lotNoVal!='' && $('.'+block+lotNoVal).attr("id")!=null && $('.'+block+lotNoVal).attr("id")!=undefined && $('.'+block+lotNoVal).attr("id")!=''){ 
		if(plantingId.trim()!=null && plantingId.trim()!='' && block.trim()!=null && block.trim()!='' && lotNoVal!=null && lotNoVal!='' && $('.'+block+lotNoVal+plantingId).attr("id")!=null && $('.'+block+lotNoVal+plantingId).attr("id")!=undefined && $('.'+block+lotNoVal+plantingId).attr("id")!=''){ 
	  
	   		alert("Lot and Block already in the list");
	   	}else{
	   	var product=$("#product").val();		
			
		var blockVal=$("#block option:selected").text();	
		var plantingIdLabel=$("#plantingId option:selected").text();	
	   	var variety=$("#variety").val();			 
	   	var lotQty=$("#lotQty").val();			 
	   	var packingQty=$("#packingQty").val();
	   	var packingUnit = $("#packingUnitM option:selected").text();
	 	var packingUnitVal = $("#packingUnitM").val();
	 	let createdDate = $("#createdDate").val();
	 	let shipmentDate = $("#shipmentDate").val();
	 	let parts_of_date = createdDate.split("-");
	 	var qrCode=$("#qrCode").val();
	 	var detailId=$("#detailId").val();
	 	var warId=$("#warId").val();
	 	
	 	let output = new Date(+parts_of_date[2], parts_of_date[1] - 1, +parts_of_date[0]);
	 	
	 	let parts_of_date1 = shipmentDate.split("-");

	 	let output1 = new Date(+parts_of_date1[2], parts_of_date1[1] - 1, +parts_of_date1[0]);

		if(isEmpty(lotNoVal)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.shipment.lotNo')}" />');
			return false;
		}
		if(isEmpty(block)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.shipment.block')}" />');
			return false;
		}if(isEmpty(plantingId)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.planting')}" />');
			return false;
		}else if(isEmpty(product)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.shipment.product')}" />');
	   		return false;
	   	}else if(isEmpty(variety)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.shipment.variety')}" />');
	   		return false;
	   	}else if(isEmpty(lotQty)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.shipment.lotQty')}" />');
	   		return false;
	   	}else if(isEmpty(packingQty)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.shipment.packingQty')}" />');
	   		return false;
	   	}else if(isEmpty(packingUnitVal)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.shipment.packingUnit')}" />');
	   		return false;	
	   	}else if(parseFloat(packingQty) > parseFloat(lotQty)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.packingqty')}" />');
			return false;
	 }else if (output > output1){
			$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.createdNpackingdateshipment')}" />');
			return false;
	 } 
		
		var tableRow="<tr id=row"+(++rowCounter)+" class='prodrow "+block+lotNoVal+plantingId+"'>";
	 	tableRow+="<td class='batcNo'>"+lotNo+"</td>";		
		tableRow+="<td class='lotNo hide'>"+block+"</td>";	
		tableRow+="<td class='blck'>"+blockVal+"</td>";
		tableRow+="<td class='plantingIdLabel'>"+jQuery("#plantingId option:selected").text()+"</td>";
	 	tableRow+="<td class='hide plantingId'>"+plantingId+"</td>";
	  	tableRow+="<td class='product'>"+product+"</td>";
		tableRow+="<td class='variety'>"+variety+"</td>";
		tableRow+="<td class='createdDate'>"+createdDate+"</td>";
		tableRow+="<td class='lotQty'>"+lotQty+"</td>";
		tableRow+="<td class='packingUnit'>"+packingUnit+"</td>";
		tableRow+="<td class='packingQty'>"+packingQty+"</td>";		
		tableRow+="<td class='qrCode hide'>"+qrCode+"</td>";
		tableRow+="<td class='detailId hide'>"+detailId+"</td>";
		tableRow+="<td class='hide warId'>"+warId+"</td>";
		tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i>&nbsp;&nbsp;&nbsp;&nbsp;<i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteDtl("+rowCounter+")'></td>";
	   	tableRow+="</tr>";
	   	$("#shipmentContent").append(tableRow);
	  	resetData();
		$('#add').attr("onclick","addRow()");
		totalShipmentQty=0;
		$('.prodrow').each(function(index, value) {		
			 var packingQty = $(this).find(".packingQty").text();
			 	totalShipmentQty+=parseFloat(packingQty);
		});
		$("#add").attr("onclick","addRow()");
	 	$("#totalShipmentQty").val(totalShipmentQty.toFixed(2));
	 
	 /* else if(!isEmpty(qrCode)){
			$.ajax({
				type : "POST",
				async : false,
				url : "shipment_populateqrCodecheck.action",
				data : {
					qrCode : qrCode
				},
				success : function(result) {
					var check = result.QRCheck;
					if(!isEmpty(check) && check == 1){
						$("#validateError").text('<s:property value="%{getLocaleProperty('empty.shipment.sortQr')}" />');
						return false;
					}else{
						var tableRow="<tr id=row"+(++rowCounter)+" class='prodrow "+block+lotNoVal+"'>";
					 	tableRow+="<td class='batcNo'>"+lotNo+"</td>";		
						tableRow+="<td class='lotNo hide'>"+block+"</td>";	
						tableRow+="<td class='blck'>"+blockVal+"</td>";
						tableRow+="<td class='plantingIdLabel'>"+jQuery("#plantingId option:selected").text()+"</td>";
					 	tableRow+="<td class='hide plantingId'>"+plantingId+"</td>";
					  	tableRow+="<td class='product'>"+product+"</td>";
						tableRow+="<td class='variety'>"+variety+"</td>";
						tableRow+="<td class='createdDate'>"+createdDate+"</td>";
						tableRow+="<td class='lotQty'>"+lotQty+"</td>";
						tableRow+="<td class='packingUnit'>"+packingUnit+"</td>";
						tableRow+="<td class='packingQty'>"+packingQty+"</td>";		
						tableRow+="<td class='qrCode hide'>"+qrCode+"</td>";
						tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i>&nbsp;&nbsp;&nbsp;&nbsp;<i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteDtl("+rowCounter+")'></td>";
					   	tableRow+="</tr>";
					   	$("#shipmentContent").append(tableRow);
					  	resetData();
						$('#add').attr("onclick","addRow()");
						totalShipmentQty=0;
						$('.prodrow').each(function(index, value) {		
							 var packingQty = $(this).find(".packingQty").text();
							 	totalShipmentQty+=parseFloat(packingQty);
						});
						$("#add").attr("onclick","addRow()");
					 	$("#totalShipmentQty").val(totalShipmentQty.toFixed(2));
					}
				}
			});
		}else if(isEmpty(qrCode)){
			var tableRow="<tr id=row"+(++rowCounter)+" class='prodrow "+block+lotNoVal+"'>";
		 	tableRow+="<td class='batcNo'>"+lotNo+"</td>";		
			tableRow+="<td class='lotNo hide'>"+block+"</td>";	
			tableRow+="<td class='blck'>"+blockVal+"</td>";
			tableRow+="<td class='plantingIdLabel'>"+jQuery("#plantingId option:selected").text()+"</td>";
		 	tableRow+="<td class='hide plantingId'>"+plantingId+"</td>";
		  	tableRow+="<td class='product'>"+product+"</td>";
			tableRow+="<td class='variety'>"+variety+"</td>";
			tableRow+="<td class='createdDate'>"+createdDate+"</td>";
			tableRow+="<td class='lotQty'>"+lotQty+"</td>";
			tableRow+="<td class='packingUnit'>"+packingUnit+"</td>";
			tableRow+="<td class='packingQty'>"+packingQty+"</td>";		
			tableRow+="<td class='qrCode hide'>"+qrCode+"</td>";
			tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i>&nbsp;&nbsp;&nbsp;&nbsp;<i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteDtl("+rowCounter+")'></td>";
		   	tableRow+="</tr>";
		   	$("#shipmentContent").append(tableRow);
		  	resetData();
			$('#add').attr("onclick","addRow()");
			totalShipmentQty=0;
			$('.prodrow').each(function(index, value) {		
				 var packingQty = $(this).find(".packingQty").text();
				 	totalShipmentQty+=parseFloat(packingQty);
			});
			$("#add").attr("onclick","addRow()");
		 	$("#totalShipmentQty").val(totalShipmentQty.toFixed(2));
		} */

		 
	   	}
	}
function isBefore(date1, date2) {
	  return date1 < date2;
	}
function deleteDtl(rowCounter){
	var id="#row"+rowCounter;
	$(id).remove();
	totalShipmentQty=0;
	$('.prodrow').each(function(index, value) {		
		 var packingQty = $(this).find(".packingQty").text();
		 	totalShipmentQty+=parseFloat(packingQty);
		 	
	});
 	$("#totalShipmentQty").val(totalShipmentQty.toFixed(2));
}
function editRow(rowCounter){
	
	
	var id="#row"+rowCounter;
	$(id).each(function(index, value) {		
		var editid = $(this).find(".incoId").val();
	
		 var lotNo = $(this).find(".lotNo").text();
		 var blck = $(this).find(".blck").text();
		 var product = $(this).find(".product").text();
		 var variety = $(this).find(".variety").text();
		 var lotQty = $(this).find(".lotQty").text();
		 var packingUnit = $(this).find(".packingUnit").text();		 
		 var batcNo = $(this).find(".batcNo").text();
		 var packingQty = $(this).find(".packingQty").text();
		 var packingUnitOption = $('#packingUnitM option');
		 var qrCode = $(this).find(".qrCode").text();
		 var detailId = $(this).find(".detailId").text();
		 var plantingId = $(this).find(".plantingId").text();
		 var plantingIdLabel = jQuery(this).find(".plantingIdLabel").text();
		 var warId = jQuery(this).find(".warId").text();
		 var packingUnitValue = $.map(packingUnitOption ,function(option) {
			 	if(packingUnit.trim()==option.text.trim())
			    	return option.value;
		 });	
		
	   	$("#lotNo").val(batcNo).trigger('change');	
	   	if($("#lotNo").val()==null || $("#lotNo").val().trim()==''){
	   	 $('#lotNo').append(new Option(batcNo, batcNo));
	 	$("#lotNo").val(batcNo).trigger('change');	
	   	}
		$("#block").val(lotNo).trigger('change');	 
		if($("#block").val()==null || $("#block").val().trim()==''){
		   	 $('#block').append(new Option(blck, lotNo));
		 	$("#block").val(lotNo).trigger('change');	
		   	}
		
		$("#plantingId").val(plantingId).trigger('change');
	   	if($("#plantingId").val()==null || $("#plantingId").val()=='' ){
	    	 $('#plantingId').append(new Option(plantingIdLabel, plantingId));
	 	 	$("#plantingId").val(plantingId).trigger('change');	
	   	} 
	    $("#qrCode").val(qrCode);
	    $("#detailId").val(detailId);
		$("#product").val(product);
		$("#variety").val(variety);
		$("#packingUnitM").val(packingUnitValue).trigger('change');
		$("#packingQty").val(packingQty);
		$("#warId").val(packingQty);
		if(editid!=undefined && editid!=''){
			$("#lotQty").val((parseFloat(lotQty)+parseFloat(packingQty)).toFixed(2));
		}else{
			$("#lotQty").val(lotQty);	
		}
		
		
	});
	$("#add").attr("onclick","addRow('row"+rowCounter+"')");
}

function resetData(){
	$("#lotNo").val("").select2().change();
	$("#block").val("").select2();
	$("#plantingId option[value='']").prop("selected", true).trigger('change');
	$("#packingUnitM").val("").select2();
	$("#product").val("");	
	$("#variety").val("");	
	$("#lotQty").val("");	
	$("#packingQty").val("");	
	$("#createdDate").val("");	
	$("#qrCode").val("");	
	$("#detailId").val("0");
	$("#warId").val("");	
}


function resetList(){
	 var createdDate = $(".createdDate").text();
	 if(!isEmpty(createdDate)){
		 if(confirm('<s:property value="%{getLocaleProperty('reset.resetListDateshipment')}" />')){
            $('.prodrow').remove();
            $("#totalShipmentQty").val("");
      }  
	 }
	}	
			

function getShipmentDetail(){
	var tableBody = $(".prodrow");
	var shipmentDtl="";	
	$.each(tableBody, function(index, value) {
		var lotNo = $(this).find(".warId").text();
		var packingUnit = $(this).find(".packingUnit").text();	
		
			 var packingUnitOption = $('#packingUnitM option');
		 var packingUnitValue = $.map(packingUnitOption ,function(option) {
			 	if(packingUnit==option.text)
			    	return option.value;
		 });	
		 
		shipmentDtl+=lotNo; //0
		shipmentDtl+="#"+packingUnitValue; //1
		shipmentDtl+="#"+jQuery(this).find(".packingQty").text(); //2
		shipmentDtl+="#"+jQuery(this).find(".plantingId").text();//3
		shipmentDtl+="#"+jQuery(this).find(".qrCode").text(); //4
		shipmentDtl+="#"+jQuery(this).find(".detailId").text()+"@"; //5
	});		
	return shipmentDtl;
}

function onCancel() {
	window.location.href = "<s:property value='redirectContent'/>";
}
function loadBlock(vall){
	clearElement('block',true);
	$("#packingUnitM").val("").select2();
   if(!isEmpty(vall)){
		$.ajax({
			type : "POST",
			async : false,
			url : "shipment_populateBlock.action",
			data:{
				selectedLotNo:vall
			},
			success : function(result) {
				insertOptions("block", $.parseJSON(result));
			}
		});
   }
}

function closeDatepickernew(){
	 $(".datepicker").hide();
}

function populatePlanting(val){
	var selectedBlock=val;
	if(!isEmpty(selectedBlock)){
		$.ajax({
			 type: "POST",
	        async: false,
	        url: "shipment_populatePlanting.action",
	        data: {selectedBlock : selectedBlock ,lotNos: $('#lotNo').val()},
	        success: function(result) {
	        	insertOptions("plantingId", $.parseJSON(result));
	        }
		});
	}else{
		clearElement('plantingId',true);
	}
}
/* function populatePlanting(val){
	var selectedBlock=val;
	if(!isEmpty(selectedBlock)){
		$.ajax({
			 type: "POST",
	        async: false,
	        url: "shipment_populatePlanting.action",
	        data: {selectedBlock : selectedBlock},
	        success: function(result) {
	        	insertOptions("plantingId", $.parseJSON(result));
	        }
		});
	}else{
		clearElement('plantingId',true);
	}
} */
function getCountry(val){
	var selectedBuyerId=val;
	if(!isEmpty(selectedBuyerId)){
		$.ajax({
			 type: "POST",
	        async: false,
	        url: "shipment_populateCountry.action",
	        data: {selectedBuyerId : selectedBuyerId},
	        success: function(result) {
	        	
	        	if(result!=""){
					/* document.getElementById('shipmentDestination').value = result.shipmentDestination; */
					document.getElementById('shipmentDestinationCode').value = result.shipmentDestinationCode;
					}
					else{
						/* document.getElementById('shipmentDestination').value = ""; */
						document.getElementById('shipmentDestinationCode').value = "";
					}
	        }
		});
	}else{
		/* document.getElementById('shipmentDestination').value = ""; */
		document.getElementById('shipmentDestinationCode').value = "";
	}
}
</script>
<body>
    <s:form id="fileDownload" action="generalPop_populateDownload" >
		<s:hidden id="loadId" name="idd" />
	</s:form>
	<s:form name="shipmentForm" cssClass="fillform" method="post"
		action="shipment_%{command}" id="shipmentForm" enctype="multipart/form-data">
		<s:hidden name="shipmentDtl" id="shipmentDtl" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:hidden name="shipment.id" id="sid" />
		<s:hidden name="shipmentSupportingFileName" id="shipmentSupportingFileName" />

		<div class="appContentWrapper marginBottom">
			<div class="error">
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.shipment" />
				</h2>
				<div class="flexform">
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('shipment.shipmentDate')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="shipmentDate" id="shipmentDate" theme="simple"
								maxlength="10"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('shipment.packhouse')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:select class="form-control select2" id="packhouse"
								name="selectedPackhouse" listKey="id" listValue="name"
								list="packhouseList" headerKey=" "
								onchange="loadLotNumbers();loadExportLicenseNo(this.value)"
								headerValue="%{getText('txt.select')}" />
							<s:hidden id="packinval" />
							<s:hidden id="warId" />
						</div>
					</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('shipment.expLicNo')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="selectedExpLicNo" theme="simple"
								readOnly="true" size="20" cssClass="lowercase form-control"
								id="expLicNo" />
						</div>
					</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('shipment.buyer')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:select class="form-control select2" id="buyer"
								name="selectedBuyer" Key="id" Value="name" list="buyerDynamic"
								headerKey=" " headerValue="%{getText('txt.select')}" onchange="getCountry(this.value)"/>
						</div>
					</div>
					<div class="flexform-item">
					<label for="txt"><s:text
							name="%{getLocaleProperty('shipment.shipmentDestination')}" /></label>
					<div class="form-element">
						<s:textfield name="shipmentDestinationCode" id="shipmentDestinationCode" 
							theme="simple" class="form-control" readonly="true" />
						<%-- <s:hidden name="shipmentDestinationCode" id="shipmentDestinationCode" /> --%>
					</div>
				</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('shipment.pConsignmentNo')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="selectedPConsignmentNo" theme="simple"
								size="20" cssClass="form-control" id="pConsignmentNo" />
						</div>
					</div>
<div style="overflow: auto;">
					<table class="table table-bordered aspect-detail"
						id="shipmentInfoTable">
						<thead>
							<tr>
								<th><s:property
										value="%{getLocaleProperty('shipment.lotNo')}" /><sup
									style="color: red;">*</sup></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.block')}" /><sup
									style="color: red;">*</sup></th>

								<th><s:property value="%{getLocaleProperty('Planting ID')}" /><sup
									style="color: red;">*</sup></th>

								<th><s:property
										value="%{getLocaleProperty('shipment.product')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.variety')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('packing.packingDate')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.lotQty')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.packingUnit')}" /><sup
									style="color: red;">*</sup></th>
								<th><s:property
										value="%{getLocaleProperty('shipment.packingQty')}" /><sup
									style="color: red;">*</sup></th>
								<th style="text-align: center"><s:property
										value="%{getLocaleProperty('action')}" /></th>
							</tr>
						</thead>
						<tbody id="shipmentContent">
							<tr>
								<td><s:select list="{}" headerKey=""
										headerValue="%{getText('txt.select')}" listKey="key"
										listValue="value" id="lotNo" class="form-control select2"
										name="lotNo" onchange="loadBlock(this.value)" /></td>

								<%-- <td><s:select list="{}" headerKey=""
										headerValue="%{getText('txt.select')}" listKey="key"
										listValue="value" id="block" class="form-control select2"
										name="block" onchange="loadProductVariety(this.value)" /></td> --%>


								<td><s:select list="{}" headerKey=""
										headerValue="%{getText('txt.select')}" listKey="key"
										listValue="value" id="block" class="form-control select2"
										name="block" onchange="populatePlanting(this.value)" /></td>


								<td width="180"><s:select list="{}" headerKey=""
										headerValue="%{getText('txt.select')}" listKey="key"
										listValue="value" id="plantingId" class="form-control select2"
										name="plantingId" onchange="loadProductVariety(this.value)" /></td>


								<td><s:textfield name="product" theme="simple"
										readonly="true" size="20" cssClass="lowercase form-control"
										id="product" style="width: 95px;" /></td>
								<td><s:textfield name="variety" theme="simple"
										readonly="true" size="20" cssClass="lowercase form-control"
										id="variety" style="width: 95px;"/></td>
								<td><s:textfield name="createdDate" theme="simple"
										readonly="true" size="20" cssClass="lowercase form-control"
										id="createdDate" style="width: 95px;"/></td>
								<td><s:textfield name="lotQty" theme="simple"
										readonly="true" size="20" cssClass="lowercase form-control"
										id="lotQty" style="width: 95px;"/></td>
								<td class="hide"><s:textfield name="qrCode" theme="simple"
										cssClass="lowercase form-control" id="qrCode" />
										<s:textfield name="detailId" theme="simple"
										cssClass="lowercase form-control" id="detailId" value="0"/></td>
								<td><s:select
										list="getCatList(getLocaleProperty('packingUnitList'))"
										headerKey="" headerValue="%{getText('txt.select')}"
										listKey="key" listValue="value" id="packingUnitM"
										class="form-control select2" /></td>
								<%-- 	<td><s:textfield name="packingQty" theme="simple" size="20"
										cssClass="lowercase form-control" id="packingQty"
										onkeypress="return isNumber(event)" /></td> --%>
								<td><s:textfield name="packingQty" theme="simple"
										readonly="true" size="20" cssClass="lowercase form-control"
										id="packingQty" style="width: 95px;"/></td>
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
							<s:iterator value="shipment.shipmentDetails" status="incr">

								<tr id="row<s:property	value="#incr.index" />"
									class="prodrow <s:property	value="cityWarehouse.farmcrops.blockId" />">
									<s:hidden class="incoId" value="%{id}" />

									<td class="batcNo"><s:property
											value="cityWarehouse.batchNo" /></td>


									<td class="blck"><s:property
											value="cityWarehouse.farmcrops.blockId" /> <s:if
											test='"update".equalsIgnoreCase(command)'>
											-
											<s:property value="cityWarehouse.farmcrops.blockName" />
										</s:if></td>

									<td class="lotNo hide"><s:property
											value="cityWarehouse.id" /></td>
											
									<td class="warId hide"><s:property
											value="cityWarehouse.id" /></td>
											
									<td class="plantingIdLabel"><s:property
											value="planting.plantingId" /></td>

									<td class="plantingId hide"><s:property
											value="planting.id" /></td>


									<td class="product"><s:property
											value="planting.variety.name" /></td>

									<td class="variety"><s:property
											value="planting.grade.name" /></td>

									<%-- <td class="product"><s:property
											value="cityWarehouse.farmcrops.variety.name" /></td>
									<td class="variety"><s:property
											value="cityWarehouse.farmcrops.grade.name" /></td>  --%>

									<td class="createdDate"><s:date name="sr.packingDate"
											format="dd-MM-yyyy" /></td>
									<td class="qrCode hide"><s:property value="qrCodeId" /></td>
									<td class="detailId hide"><s:property value="id" /></td>
									<td class="lotQty"><s:property
											value="cityWarehouse.sortedWeight" /></td>

									<td class="packingUnit"><s:property
											value="%{getCatalgueNameByCode(packingUnit)}" /></td>

									<td class="packingQty"><s:property value="packingQty" /></td>

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
					</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('shipment.totalShipmentQty')}" />
						</label>
						<div class="form-element">
							<s:textfield name="totalShipmentQty" theme="simple"
								readonly="true" size="20" cssClass="lowercase form-control"
								id="totalShipmentQty" />
						</div>
					</div>
					
					<div class="flexform-item part">
					<label for="txt"><s:text
							name="Shipment Supporting Files" /></label>
					<div class="form-element">
						<s:file name="shipmentSupportingFiles" id="shipmentSupportingFiles"
							cssClass="form-control" multiple="multiple" />
								<s:hidden name="shipment.shipmentSupportingFiles"/>
						<s:if
							test="command=='update'&& shipment.shipmentSupportingFiles!=null && shipment.shipmentSupportingFiles!=''">
							<a class='fa fa-download pdfIc'
								href='shipment_downloadMultipleImagesBasedOnDocumentId?idd=<s:property value="shipment.id"/>'
								title='Download'></a>
						</s:if>
					</div>
				</div>
					
				</div>
			</div>
			<div class="flex-layout flexItemStyle">
				<div class="margin-top-10">

					<span class=""><span class="first-child">
							<button type="button" id="buttonAdd1"
								class="save-btn btn btn-success">
								<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
								</font>
							</button>
					</span></span> <span id="cancel" class=""><span class="first-child"><button
								type="button" class="cancel-btn btn btn-sts"
								onclick="onCancel();">
								<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
								</font></b>
							</button></span></span>
				</div>
			</div>
		</div>
	</s:form>
</body>
</html>