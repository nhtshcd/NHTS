<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css" />
<head>
<meta name="decorator" content="swithlayout">
</head>
<script type="text/javascript">

var command = '<s:property value="command"/>';
jQuery(document).ready(
			 
			function() {

				$("#buttonAdd1").on(
						"click",
						function(event) {
							event.preventDefault();
							var isDtl=getpackhouseIncomingDetail();
							$("#packhouseIncomingDtl").val(isDtl);
							$('#packhouse').select2({disabled:false});
							$("#buttonAdd1").prop("disabled", true);
							if (!validateAndSubmit("packhouseIncomingForm",
									"packhouseIncoming_")) {
								$("#buttonAdd1").prop("disabled", false);
							}
							$("#buttonAdd1").prop("disabled", false);
							
						});
				 
				if (command == 'update') {
			 
			 var packhouse='<s:property value="packhouseIncoming.packhouse.id"/>';
			 var offDate='<s:property value="offLoadingDate"/>';
			 $("#packhouse option[value='"+ packhouse.trim() + "']").prop("selected", true).trigger('change');
			 $('#packhouse').select2({disabled:'readonly'});
			 var isEdit='<s:property value="isEdit"/>';
			 if(isEdit == '0'){
				 $("#buttonAdd1").prop("disabled", true);
				 $("#validateError").text("Packing completed so you can't edit this record");
			 }else{
				 $("#buttonAdd1").prop("disabled", false);
				 $("#validateError").text("");
			 }
			/*  $("#offLoadingDate").datepicker({
				    maxDate: new Date(2017, 8, 29)  //you can provide any future date here
				  }); */
				$( "#offLoadingDate" ).datepicker(

						{
						
					  // format: 'mm/dd/yyyy',
			          startDate: offDate,
			           autoclose: true,
						beforeShow : function()
						{
						jQuery( this ).datepicker({ maxDate: offDate });
						},
						changeMonth: true,
						changeYear: true

						}

						);
				}else{
					$( "#offLoadingDate" ).datepicker(
							{
				           autoclose: true,
							beforeShow : function()
							{
							jQuery( this ).datepicker();
							},
							changeMonth: true,
							changeYear: true
							}
							);
				}
				
				
				
				$("#offLoadingDate").focusin(function(){ 
					 var createdDate = $(".createdDate").text();
					 var preDate = $(this).val();
					 var preDatew = $("#preDatew").val();
					 if(!isEmpty(createdDate) && command == 'create'){
						 if(confirm('<s:property value="%{getLocaleProperty('reset.resetListDateincomingshipment')}" />')){
				           $('.prodrow').remove();
				           $("#totalShipmentQty").val("");
				           $("#packhouse").val(" ").change();
				           $('#offLoadingDate').blur();
				     }  else{
				    	 $('#offLoadingDate').blur();
				    	 setTimeout(closeDatepickernew, 400);
				     }
					 }
					});
				
				
			});
	
	      

	function getpackhouseIncomingDetail(){
		var tableBody = $("#packhouseIncomingContent tr:nth-child(n + 2)");
		var packhouseIncomingDtl="";	
		$.each(tableBody, function(index, value) {
			packhouseIncomingDtl+=jQuery(this).find(".blockNo").text(); //0
			packhouseIncomingDtl+="#"+jQuery(this).find(".recievedUnits").text(); //1
			packhouseIncomingDtl+="#"+jQuery(this).find(".product").text(); //2
			packhouseIncomingDtl+="#"+jQuery(this).find(".variety").text(); //3
			packhouseIncomingDtl+="#"+jQuery(this).find(".transferWeight").text();  //4
			packhouseIncomingDtl+="#"+jQuery(this).find(".recievedWeight").text(); //5
			packhouseIncomingDtl+="#"+jQuery(this).find(".remainsWeight").text(); //6
			packhouseIncomingDtl+="#"+jQuery(this).find(".noUnits").text();		 //7	
			packhouseIncomingDtl+="#"+jQuery(this).find(".plantingId").text();//8
			packhouseIncomingDtl+="#"+jQuery(this).find(".qrCode").text(); //9
			packhouseIncomingDtl+="#"+jQuery(this).find(".detailId").text()+"@"; //10		
		});		
		return packhouseIncomingDtl;
	}

	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>";
	}
	function isDecimalPlaceTwo(evt){
		  
		  if ((evt.which != 46 || evt.value.indexOf('.') != -1) && (evt.which < 48 || evt.which > 57)) {
		        //event it's fine

		    }
		    var input = evt.value;
		    if ((input.indexOf('.') != -1) && (input.substring(input.indexOf('.')).length > 2)) {
		        return false;
		    }
		}
 


function deleteDtl(rowCounter){
   	var id="#row"+rowCounter;
   	$(id).remove();
   	
	totalShipmentQty=0;
	$('.prodrow').each(function(index, value) {		
		 var packingQty = $(this).find(".recievedWeight").text();
		 	totalShipmentQty+=parseFloat(packingQty);
	});
	
 	$("#totalShipmentQty").val(totalShipmentQty.toFixed(2));
}

function editRow(rowCounter){
 	var id="#row"+rowCounter;
	$(id).each(function(index, value) {
		
		 var blockNo = $(this).find(".blockNo").text();
		 var plantingId = $(this).find(".plantingId").text();
		 var plantingName = $(this).find(".plantingIdLabel").text();
	var editid = $(this).find(".incoId").val();
		 var recievedUnits = $(this).find(".recievedUnits").text();
		 var blockName = $(this).find(".blockName").text();
		
		 
		 var product = $(this).find(".product").text();
		 var blockId =$(this).find(".blockId").text();
		 var variety = $(this).find(".variety").text();
		 var transferWeight = $(this).find(".transferWeight").text();
		 var recievedWeight = $(this).find(".recievedWeight").text();
		 var noUnits = $(this).find(".noUnits").text();
		 var remainsWeight = $(this).find(".remainsWeight").text();
		 var qrCode = $(this).find(".qrCode").text();
		 var detailId = $(this).find(".detailId").text();
		 var transferWeightSortedWeight = $("#transferWeightSortedWeight").val();
		 
	   	$("#blockNo").val(blockNo).trigger('change');
	   	if($("#blockNo").val()==null || $("#blockNo").val()=='' ){
	    	 $('#blockNo').append(new Option(blockName, blockNo));
	 	 	$("#blockNo").val(blockNo).trigger('change');	
	   	}
		 
		
		
	   	$("#plantingId").val(plantingId).trigger('change');
	   	if($("#plantingId").val()==null || $("#plantingId").val()=='' ){
	    	 $('#plantingId').append(new Option(plantingName,plantingId));
	 	 	$("#plantingId").val(plantingId).trigger('change');	
	   	}
	   	
	   	if($("#qrCode").val()==null || $("#qrCode").val().trim()==''){
		   	 $('#qrCode').append(new Option(qrCode, qrCode));
		 	$("#qrCode").val(qrCode).trigger('qrCode');	
		   	}
	   	$("#qrCode").val(qrCode).trigger('change');
	   	
	   	$("#recievedUnits").val(recievedUnits).trigger('change');
		$("#transferWeight").val(transferWeight);
		if(editid!=undefined && editid!=''){
			$("#transferWeightSortedWeight").val(parseFloat(transferWeight));
		}else{
			$("#transferWeightSortedWeight").val(transferWeightSortedWeight);
		}
		
		if(editid!=undefined && editid!=''){
			$("#remainingWeight").val((parseFloat(transferWeight)-parseFloat(recievedWeight)).toFixed(2));
		}else{
			$("#remainingWeight").val(remainsWeight.toFixed(2));
		}
		
		
		$("#recievedWeight").val(recievedWeight);
		$("#noUnits").val(noUnits);
		 
		 $("#detailId").val(detailId);
	});
	$("#add").attr("onclick","addRow('row"+rowCounter+"')");
}


function loadBlocks(vall) {
	var insType = 1;
	clearElement('blockNo',true);
	
	$.ajax({
		type : "POST",
		async : false,
		url : "packhouseIncoming_populateBlock.action",
		data : {
			selectedPackhouse : vall
		},
		success : function(result) {
			insertOptions("blockNo", $.parseJSON(result));
			if(command == 'create'){
			resetData();
			$('.prodrow').remove();
			}
		}
	});
}

function populatePlanting(val){
	var selectedBlock=val;
	clearElement('plantingId',true);
	if(!isEmpty(selectedBlock)){
		$.ajax({
			 type: "POST",
	        async: false,
	        url: "packhouseIncoming_populatePlanting.action",
	        data: {selectedBlock : selectedBlock},
	        success: function(result) {
	        	insertOptions("plantingId", $.parseJSON(result));
	        }
		});
	}else{
		clearElement('plantingId',true);
	}
}

function populateSorting(val){
	var selectedBlock=val;
	clearElement('qrCode',true);
	if(!isEmpty(selectedBlock)){
		$.ajax({
			 type: "POST",
	        async: false,
	        url: "packhouseIncoming_populateSorting.action",
	        data: {selectedPlanting : selectedBlock},
	        success: function(result) {
	        	insertOptions("qrCode", $.parseJSON(result));
	        }
		});
	}else{
		clearElement('qrCode',true);
	}
	
}

function populateBlockDetail(id) {
	
	
	$('#product').val("");
	$('#variety').val("");
	$('#createdDate').val("");
	$('#transferWeightSortedWeight').val("");
	$('#transferWeight').val("");
	
	if (!isEmpty(id)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "packhouseIncoming_populateBlockDetails.action",
			data : {
				selectedBlock : id
			},
			success : function(result) {
				$('#blockId').val(result.blockId);
				$('#product').val(result.product);
				$('#variety').val(result.variety);
				$('#createdDate').val(result.createdDate);
				$('#transferWeightSortedWeight').val(result.transferedWeight);
				$('#transferWeight').val(result.transferedWeight);
				$('#recievedWeight').val("");
				$('#remainingWeight').val("");
				//$('#qrCode').val(result.qrCode);
				//$("#detailId").val("0");
			}
		});
	}else{
		$('#recievedWeight').val("");
		$('#remainingWeight').val("");
	}
}

function populateBlockIdByFarmCropsId(id) {
	$('#blockId').val("");
	if (!isEmpty(id)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "packhouseIncoming_populateBlockIdByFarmCropsId.action",
			data : {
				selectedBlock : id
			},
			success : function(result) {
				$('#blockId').val(result.blockId);
			}
		});
	}
}

/* function populateBlockDetail(id) {
	if (!isEmpty(id)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "packhouseIncoming_populateBlockDetails.action",
			data : {
				selectedBlock : id
			},
			success : function(result) {
				$('#blockId').val(result.blockId);
				$('#product').val(result.product);
				$('#variety').val(result.variety);
				$('#createdDate').val(result.createdDate);
				$('#transferWeightSortedWeight').val(result.transferedWeight);
			}
		});
	}
} */




var totalShipmentQty=0;
var arr=[];
function addRow(rowId){
	   var rowCounter = $('#packhouseIncomingContent tr').length-1;
	   	if(rowId!=''){
	   		$('#'+rowId).remove();
	   	}
	   	$("#validateError").text("");
	   	
	 
	   	var blockNo = $("#blockNo").val();	
	   	
	   	var plantingId = $("#plantingId").val();	
		var plantingIdLabel = $("#plantingId option:selected").text();	
		
		var blockNoLabel = $("#blockNo option:selected").text();	
	   	var product=$("#product").val();	
	   	var blockId=$("#blockId").val();
	   	var variety=$("#variety").val();
	   	var createdDate=$("#createdDate").val();	
	   	var qrCode=$("#qrCode").val();
	   	var detailId=$("#detailId").val();
	   	var offLoadingDate = $("#offLoadingDate").val();
	   	var transferWeightSortedWeight = $("#transferWeightSortedWeight").val();
	   	let parts_of_date = createdDate.split("-");

	 	let output = new Date(+parts_of_date[2], parts_of_date[1] - 1, +parts_of_date[0]);
	 	
	 	let parts_of_date1 = offLoadingDate.split("-");

	 	let output1 = new Date(+parts_of_date1[2], parts_of_date1[1] - 1, +parts_of_date1[0]);
	   	var transferWeight=$("#transferWeight").val();	
	 	var actTra=$("#actTra").val();			 
	   	var recievedWeight=$("#recievedWeight").val();
	   	var recievedUnits = $("#recievedUnits option:selected").val();
	   	var noUnits=$("#noUnits").val();
		var recievedUnitsLabs=$("#recievedUnits option:selected").text();	
		var remainsWeight = $("#remainingWeight").val();
		arr.push(qrCode);
		arr.pop();		
		if(qrCode!=null && qrCode!='' && $('.'+qrCode).attr("id")!=null && $('.'+qrCode).attr("id")!=undefined && $('.'+qrCode).attr("id")!=''){
	   		alert("Product details already exists. Please update the list");
	   	}else{
		arr.push(qrCode);
		 if(isEmpty(blockNo)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.blockNo')}" />');
			return false;
		}if(isEmpty(plantingId)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.planting')}" />');
			return false;
		}if(isEmpty(qrCode)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.qrCodeSorting')}" />');
			return false;
		}else if(isEmpty(product)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.product')}" />');
	   		return false;
	   	}else if(isEmpty(variety)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.variety')}" />');
	   		return false;
	   	}
	   	else if(isEmpty(transferWeight)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.transferWeight')}" />');
	   		return false;
	   	}
	   	else if(!isEmpty(transferWeight) && !isEmpty(transferWeightSortedWeight) && parseFloat(transferWeightSortedWeight)<parseFloat(transferWeight)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.packhouseIncoming.transferWeight')}" />');
	   		return false;
	   	}
	   	else if(isEmpty(recievedWeight)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.recievedWeight')}" />');
	   		return false;
	   	}else if(parseFloat(recievedWeight)<=0){
			$("#validateError").text('<s:property value="%{getLocaleProperty('recievedWeight.zeronetQty')}" />');
	   		return false;
	   	}else if(parseFloat(actTra) < parseFloat(recievedWeight)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.packhouseIncoming.recievedWeight')}" />');
	   		return false;
	   	}else if(isEmpty(recievedUnits)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.recievedUnits')}" />');
	   		return false;	
	   	}else if(parseFloat(recievedUnits)<=0){
			$("#validateError").text('<s:property value="%{getLocaleProperty('recievedUnits.zeronetQty')}" />');
	   		return false;
	   	}else if(isEmpty(noUnits)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.noUnits')}" />');
	   		return false;
	   	}else if(parseFloat(noUnits)<=0){
			$("#validateError").text('<s:property value="%{getLocaleProperty('noUnits.zeronetQty')}" />');
	   		return false;
	   	}else if(parseFloat(recievedWeight) > parseFloat(transferWeight)){
			$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.transferAndrecieved')}" />');
			return false;
	   	}else if (output > output1){
			$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.createdNpackingdateincomeshipment')}" />');
			return false;
		}
	   	
		var tableRow="<tr id=row"+(++rowCounter)+" class='prodrow "+qrCode+"'>";
		tableRow+="<td class='blockName' >"+blockNoLabel+"</td>";		
		tableRow+="<td class='blockId hide'>"+blockId+"</td>";
	 	tableRow+="<td class='blockNo hide'>"+blockNo+"</td>";	
	 	tableRow+="<td class='blockId'>"+blockId+"</td>";
		tableRow+="<td class='plantingIdLabel' >"+plantingIdLabel+"</td>";		
	 	tableRow+="<td class='plantingId hide'>"+plantingId+"</td>";	
		
		tableRow+="<td class='qrCode'>"+qrCode+"</td>";
	  	tableRow+="<td class='product'>"+product+"</td>";
		tableRow+="<td class='variety'>"+variety+"</td>";
		tableRow+="<td class='createdDate'>"+createdDate+"</td>";
		tableRow+="<td class='transferWeight'>"+transferWeight+"</td>";
		tableRow+="<td class='actwt hide'>"+actTra+"</td>";
		tableRow+="<td class='recievedWeight'>"+recievedWeight+"</td>";
		tableRow+="<td class='remainsWeight'>"+remainsWeight+"</td>";
		
		tableRow+="<td class='detailId hide'>"+detailId+"</td>";
		tableRow+="<td>"+recievedUnitsLabs+"</td>";
		tableRow+="<td class='hide recievedUnits'>"+recievedUnits+"</td>";
		tableRow+="<td class='noUnits'>"+noUnits+"</td>";
		tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i>&nbsp;&nbsp;&nbsp;&nbsp;<i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteDtl("+rowCounter+")'></td>";
	   	tableRow+="</tr>";
	   	$("#packhouseIncomingContent").append(tableRow);
	  	resetData();
	  	totalShipmentQty=0;
		$('.prodrow').each(function(index, value) {		
			 var packingQty = $(this).find(".recievedWeight").text();
			 	totalShipmentQty+=parseFloat(packingQty);
		});
		$("#add").attr("onclick","addRow()");
		$("#actTra").val("");	
	 	$("#totalShipmentQty").val(totalShipmentQty.toFixed(2));
	   	/* else if(!isEmpty(qrCode)){
			$.ajax({
				type : "POST",
				async : false,
				url : "packhouseIncoming_populateqrCodecheck.action",
				data : {
					qrCode : qrCode
				},
				success : function(result) {
					var check = result.QRCheck;
					if(!isEmpty(check) && check == 1){
						$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.sortQr')}" />');
						return false;
					}else{
						var tableRow="<tr id=row"+(++rowCounter)+" class='prodrow "+plantingId+"'>";
						tableRow+="<td class='blockName' >"+blockNoLabel+"</td>";		
					 	tableRow+="<td class='blockNo hide'>"+blockNo+"</td>";	
						tableRow+="<td class='plantingIdLabel' >"+plantingIdLabel+"</td>";		
					 	tableRow+="<td class='plantingId hide'>"+plantingId+"</td>";	
						tableRow+="<td class='blockId'>"+blockId+"</td>";
					  	tableRow+="<td class='product'>"+product+"</td>";
						tableRow+="<td class='variety'>"+variety+"</td>";
						tableRow+="<td class='createdDate'>"+createdDate+"</td>";
						tableRow+="<td class='transferWeight'>"+transferWeight+"</td>";
						tableRow+="<td class='actwt hide'>"+actTra+"</td>";
						tableRow+="<td class='recievedWeight'>"+recievedWeight+"</td>";
						tableRow+="<td class='remainsWeight'>"+remainsWeight+"</td>";
						tableRow+="<td class='qrCode hide'>"+qrCode+"</td>";
						tableRow+="<td>"+recievedUnitsLabs+"</td>";
						tableRow+="<td class='hide recievedUnits'>"+recievedUnits+"</td>";
						tableRow+="<td class='noUnits'>"+noUnits+"</td>";
						tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i>&nbsp;&nbsp;&nbsp;&nbsp;<i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteDtl("+rowCounter+")'></td>";
					   	tableRow+="</tr>";
					   	$("#packhouseIncomingContent").append(tableRow);
					  	resetData();
					  	totalShipmentQty=0;
						$('.prodrow').each(function(index, value) {		
							 var packingQty = $(this).find(".recievedWeight").text();
							 	totalShipmentQty+=parseFloat(packingQty);
						});
						$("#add").attr("onclick","addRow()");
						$("#actTra").val("");	
					 	$("#totalShipmentQty").val(totalShipmentQty.toFixed(2));
					}
				}
			});
		}else if(isEmpty(qrCode)){
			
		} */
		
	   
	   	}
}

function resetData(){
	$("#blockNo").val("").select2();
	$("#plantingId").val("").select2();
	$("#recievedUnits").val("").select2();
	$("#blockId").val("");	
	$("#product").val("");	
	$("#variety").val("");	
	$("#createdDate").val("");
	$("#transferWeight").val("");	
	$("#recievedWeight").val("");	
	$("#remainingWeight").val("");	
	$("#noUnits").val("");	
	$("#qrCode").val("").select2();
	$("#detailId").val("0");
	$("#actTra").val("");	
	$("#add").attr("onclick","addRow()");
}

function closeDatepickernew(){
	 $(".datepicker").hide();
}

	function calculateRemain(val){
		var transferWeight=$("#transferWeight").val();
		var totalRemi = 0;
		 if(!isEmpty(transferWeight) && !isEmpty(val)){
			 totalRemi = parseFloat(transferWeight) - parseFloat(val)
			 $("#remainingWeight").val(parseFloat(totalRemi).toFixed(2));
		 }else{
			 $("#remainingWeight").val(parseFloat(totalRemi).toFixed(2));
		 }
	}


</script>
<body>
	<s:form name="packhouseIncomingForm" cssClass="fillform" method="post"
		action="packhouseIncoming_%{command}" id="packhouseIncomingForm">
		<s:hidden name="packhouseIncomingDtl" id="packhouseIncomingDtl" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="preDatew" id="preDatew" />
		<s:hidden name="command" id="command" />
		<s:hidden name="transferWeightSortedWeight"
			id="transferWeightSortedWeight" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="packhouseIncoming.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="error">
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.packhouseIncoming" />
				</h2>
				<div class="flexform">
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('packhouseIncoming.offLoadingDate')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="offLoadingDate" id="offLoadingDate"
								theme="simple" maxlength="10"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('packhouseIncoming.packhouse')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:select class="form-control select2" id="packhouse"
								name="packhouseIncoming.packhouse.id" listKey="id"
								listValue="name" list="packhouseList" headerKey=" "
								onchange="loadBlocks(this.value)"
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>
<div style="overflow: auto;">
					<table class="table table-bordered aspect-detail"
						id="packhouseIncomingInfoTable" >
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
								<%-- <th><s:property
										value="%{getLocaleProperty('packhouseIncoming.blockId')}" /><sup
									style="color: red;">*</sup></th> --%>
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
										value="%{getLocaleProperty('packhouseIncomnoUnitsing.recievedUnits')}" /><sup
									style="color: red;">*</sup></th>

								<th><s:property
										value="%{getLocaleProperty('packhouseIncoming.noOfUnits')}" /><sup
									style="color: red;">*</sup></th>
								<th style="text-align: center"><s:property
										value="%{getLocaleProperty('action')}" /></th>
							</tr>
						</thead>
						<tbody id="packhouseIncomingContent">
							<tr>
								<%-- <td width="180"><s:select list="{}" headerKey=""
										headerValue="%{getText('txt.select')}" listKey="key"
										listValue="value" id="blockNo" class="form-control select2"
										name="blockNo" onchange="populateBlockDetail(this.value)" /></td> --%>
								<td width="180"><s:select list="{}" headerKey=""
										headerValue="%{getText('txt.select')}" listKey="key"
										listValue="value" id="blockNo" class="form-control select2"
										name="blockNo" onchange="populatePlanting(this.value);populateBlockIdByFarmCropsId(this.value)" /></td>
								<td width="180"><s:textfield name="blockId" theme="simple"
										readonly="true" size="70" cssClass="lowercase form-control"
										id="blockId" style="width: 220px;"/></td>
								
								<td width="180"><s:select list="{}" headerKey=""
										headerValue="%{getText('txt.select')}" listKey="key"
										listValue="value" id="plantingId" class="form-control select2"
										name="plantingId" onchange="populateSorting(this.value);" /></td>
										
								<%-- <td><s:textfield name="blockId" theme="simple"
										readonly="true" size="70" cssClass="lowercase form-control"
										id="blockId" /></td> --%>
								<td width="180"><s:select list="{}" headerKey=""
										headerValue="%{getText('txt.select')}" listKey="key"
										listValue="value" id="qrCode" class="form-control select2"
										name="qrCode" onchange="populateBlockDetail(this.value)" /></td>
								<td><s:textfield name="product" theme="simple"
										readonly="true" size="20" cssClass="lowercase form-control"
										id="product" style="width: 95px;"/></td>
								<td><s:textfield name="variety" theme="simple"
										readonly="true" size="20" cssClass="lowercase form-control"
										id="variety" style="width: 95px;"/></td>
								<td><s:textfield name="createdDate" theme="simple"
										readonly="true" size="20" cssClass="lowercase form-control"
										id="createdDate"  style="width: 95px;"/></td>
								<td class="hide"><s:textfield name="qrCode" theme="simple"
										size="20" cssClass="lowercase form-control"
										id="qrCode" /><s:textfield name="detailId" theme="simple"
										cssClass="lowercase form-control" id="detailId" value="0"/></td>
								<td><s:textfield name="transferWeight" theme="simple"
										size="15" cssClass="lowercase form-control"
										id="transferWeight" readonly="true" /> <s:hidden id="actTra" /></td>
								<td><s:textfield name="recievedWeight" theme="simple"
										size="15" cssClass="lowercase form-control"
										id="recievedWeight" onkeypress="return isDecimal1(event,this)"
										onkeyup="calculateRemain(this.value)" /></td>

								<td><s:textfield name="remainingWeight" theme="simple"
										readonly="true" size="20" cssClass="lowercase form-control"
										id="remainingWeight" /></td>
								<%-- <s:select
										list="getCatList(getLocaleProperty('umoListType'))"
										headerKey="" headerValue="%{getText('txt.select')}"
										listKey="key" listValue="value" id="recievedUnits"
										class="form-control select2" name="recievedUnits" /> --%>
								<td><s:select
										list="getCatList(getLocaleProperty('packingUnitList'))"
										headerKey="" headerValue="%{getText('txt.select')}"
										listKey="key" listValue="value" id="recievedUnits"
										class="form-control select2" name="recievedUnits" /></td>

								<td><s:textfield name="noUnits" theme="simple" size="15"
										cssClass="lowercase form-control" id="noUnits"
										onkeypress="return isNumber(event)" maxlength="20" /></td>
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
							<s:iterator value="packhouseIncoming.packhouseIncomingDetails"
								status="incr">
								<tr id="row<s:property	value="#incr.index"/>"
									class="prodrow <s:property value="planting.id" />">
									<s:hidden class="incoId" value="%{id}" />
									<td class="blockNo hide"><s:property value="planting.id" /></td>
									<td class="product hide"><s:property
											value="cw.planting.variety.procurementProduct.id" /></td>
									<td class="variety hide"><s:property
											value="cw.planting.variety.id" /></td>
											
									<td class="blockName"><s:property
											value="planting.farmCrops.blockName" /></td>
									<td class="blockId"><s:property
											value="cw.planting.farmCrops.blockId" /></td>		
											
									<td class="plantingIdLabel"><s:property
											value="planting.plantingId" /></td>
											
									<td class="plantingId hide"><s:property
											value="planting.id" /></td>
											
									<td class="qrCode"><s:property
											value="qrCodeId" /></td>
											
									<%-- <td class="blockId"><s:property
											value="cw.farmcrops.blockId" /></td> --%>
											
									<%-- <td class="variety"><s:property
											value="cw.farmcrops.variety.name" /></td>

									<td class=""><s:property value="cw.farmcrops.grade.name" /></td> --%>
									
									<td class="variety"><s:property
											value="cw.planting.variety.name" /></td>

									<td class=""><s:property value="cw.planting.grade.name" /></td>
									
									<td class="createdDate">
										<%-- <s:property value="cityWarehouse.createdDate" /> --%> <s:date
											name="sr.createdDate" format="dd-MM-yyyy" />
									</td>
									
									<td class="detailId hide"><s:property value="id" /></td>
									<td class="transferWeight"><s:property
											value="transferWeight" /></td>
									<td class="recievedWeight"><s:property
											value="receivedWeight" /></td>
									<td class="remainsWeight"><s:property value="totalWeight" /></td>
									<td class="recievedUnits hide"><s:property
											value="receivedUnits" /></td>
									<td class=""><s:property
											value="%{getCatalgueNameByCode(receivedUnits)}" /></td>
									<td class="noUnits"><s:property value="noUnits" /></td>
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
								value="%{getLocaleProperty('packhouseIncoming.totalWeight')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="packhouseIncoming.totalWeight" theme="simple"
								readonly="true" size="20" cssClass="lowercase form-control"
								id="totalShipmentQty" />
						</div>
					</div>


					<s:if test='"update".equalsIgnoreCase(command)'>

						<div class="flexform-item ">
							<label for="txt"> <s:property
									value="%{getLocaleProperty('packing.BatchNo')}" /><sup
								style="color: red;">*</sup>
							</label>
							<div class="form-element">
								<s:property value="packhouseIncoming.batchNo" />
							</div>
						</div>
					</s:if>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('packhouseIncoming.truckType')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<%-- <s:textfield name="packhouseIncoming.truckType" theme="simple"
								size="20" cssClass="lowercase form-control" id="truckType"
								maxlength="20" /> --%>
							<s:select class="form-control  select2" id="truckType"
								name="packhouseIncoming.truckType" listKey="key" listValue="value"
								list="getCatList(getLocaleProperty('truckType'))" headerKey=" "
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('packhouseIncoming.truckNumber')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="packhouseIncoming.truckNo" theme="simple"
								size="20" cssClass="lowercase form-control" id="truckNo"
								maxlength="20" />
						</div>
					</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('packhouseIncoming.driverName')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="packhouseIncoming.driverName" theme="simple"
								size="20" cssClass="lowercase form-control" id="driverName"
								maxlength="20" />
						</div>
					</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('packhouseIncoming.driverContact')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="packhouseIncoming.driverCont" theme="simple"
								size="20" cssClass="lowercase form-control" maxlength="20"
								id="driverCont" onkeypress="return isNumber(event,this);" />
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

<script type="text/javascript">




</script>
