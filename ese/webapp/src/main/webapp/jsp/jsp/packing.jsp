<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<head>
<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType")
					: "swithlayout"%>">
</head>
<script type="text/javascript">
var tenant = '<s:property value="getCurrentTenantId()"/>';
var command = '<s:property value="command"/>';
var url =<%out.print("'" + request.getParameter("url") + "'");%>;
var layOut='<%=request.getParameter("layoutType")%>';
	jQuery(document).ready(function() {
		
		$('#bestBefore').datepicker('setDate', new Date());
		
						$(".breadCrumbNavigation").find('li:last').find(
								'a:first').attr("href",
								'<s:property value="redirectContent" />');
						if (url == null || url == undefined || url == ''
								|| url == 'null') {
							url = 'packing_';
						}
						
						if (command == 'update') {
							var packhouse='<s:property value="packing.packHouse.id"/>';
							$("#packhouse option[value='"+ packhouse.trim() + "']").prop("selected", true).trigger('change');
							$('#packhouse').select2({disabled:'readonly'});
							var packDate='<s:property value="packingDate"/>';
							$( "#date" ).datepicker(

									{
									
								  // format: 'mm/dd/yyyy',
						          startDate: packDate,
						           autoclose: true,
									beforeShow : function()
									{
									jQuery( this ).datepicker({ maxDate: packDate });
									},
									changeMonth: true,
									changeYear: true

									}

									);
								 
					}else{
						$( "#date" ).datepicker(

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
						$("#buttonAdd1")
								.on(
										'click',
										function(event) {
											event.preventDefault();
											var packingDetailInfoArray =  buildPackingDetailInfoArray();
											
									   		$('#packingTotalString').val(packingDetailInfoArray);
									   		 
											$("#buttonUpdate").prop('disabled',
													false);
											$('#packhouse').select2({disabled:false});

	                                 if (!validateAndSubmit("target1","packing_")) {

												$("#buttonAdd1").prop(
														'disabled', false);
 
											}
											$("#buttonAdd1").prop("disabled", false);

										});

						$("#inspdate").datepicker({
							changeMonth : true,
							changeYear : true,
							yearRange : "-100:+0",
							dateFormat : "dd/mm/yy",
							maxDate : '0',
						});
						 var isEdit='<s:property value="isEdit"/>';
						 if(isEdit == '0'){
							 $("#buttonAdd1").prop("disabled", true);
							 $("#validateError").text("Shipment done so you can't edit this record");
						 }else{
							 $("#buttonAdd1").prop("disabled", false);
							 $("#validateError").text("");
						 }
						 
						 $("#date").focusin(function(){ 
							 var createdDate = $(".createdDate").text();
							 if(!isEmpty(createdDate) && command == 'create'){
								 if(confirm('<s:property value="%{getLocaleProperty('reset.resetListDatePacking')}" />')){
					                 $('.prodrow').remove();
					                 $("#packhouse").val(" ").change();
					    			 $("#target1_packing_packerName").val("");
									 $('#date').blur();
									 
						      }   else{
						    	 $('#date').blur();
						    	 setTimeout(closeDatepickernew, 400);
						    	 
						     }
							 }
							});
						
					});
	
	function loadPackingFarmer(vall){
		resetProductData();
		var rows= $('#procurementDetailContent .prodrow').length;
		if(rows>0 && vall!='<s:property value="packing.packHouse.id"/>'){
			if(confirm("Product details already exist.Are you sure to change the Packhouse?")){
			
			  	$('.prodrow').remove();
				$.ajax({
					 type: "POST",
	        async: false,
	        url: "packing_populateFarmer.action",
	        data: {selectedBlock:vall},
	        success: function(result) {
	        	insertOptions("farmer", $.parseJSON(result));
	        }
		});

			}else{
				$('#packhouse').val($('#packinval').val()).select2();
				
			}
		}else{
			$('#packinval').val(vall);
			$.ajax({
				 type: "POST",
       async: false,
       url: "packing_populateFarmer.action",
       data: {selectedBlock:vall},
       success: function(result) {
       	insertOptions("farmer", $.parseJSON(result));
       }
	});
		}
	  
	  
	}
	function listFarm(call) {
		//$('#farm').empty();
		clearElement('farm',true);
		$('#farm').val('').select2().change();
		if(!isEmpty(call)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "packing_populateFarm.action",
		        data: {selectedFarmer : call,selectedBlock:$('#packhouse').val()},
		        success: function(result) {
		        	insertOptions("farm", $.parseJSON(result));
		        	/* var sllfar ='<s:property value="packing.farmCrops.farm.id"/>';
		        	
		        	if(sllfar!=null && sllfar!='' && sllfar!='null'){
		        	
		        		$('#farm').val(sllfar).select2().change();
		        	
		        	} */
		        }
			});
		}
	}	
	
	function listBlock(call) {
		//$('#selectedBlock').empty();
		clearElement('selectedBlock',true);
		$('#selectedBlock').val('').select2().change();
		if(!isEmpty(call)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "packing_populateBlocks.action",
		        data: {selectedFarm : call,selectedBlock:$('#packhouse').val()},
		        success: function(result) {
		        	insertOptions("selectedBlock", $.parseJSON(result));
		        	/* var sllfar ='<s:property value="packing.farmCrops.id"/>';
		        	
		        	if(sllfar!=null && sllfar!='' && sllfar!='null'){
		        	
		        		$('#selectedBlock').val(sllfar).select2().change();
		        	
		        	} */
		        }
			});
		}
	}
	
	function listBatchNo(call) {
		//$('#selectedBatchNo').empty();
		clearElement('selectedBatchNo',true);
		$('#selectedBatchNo').val('').select2().change();
		if(!isEmpty(call)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "packing_populateBatches.action",
		        data: {selectedFarm : call,selectedBlock:$('#packhouse').val()},
		        success: function(result) {
		        	insertOptions("selectedBatchNo", $.parseJSON(result));
		        	/* var sllfar ='<s:property value="packing.batchNo"/>';
		        	
		        	if(sllfar!=null && sllfar!='' && sllfar!='null'){
		        	
		        		$('#selectedBatchNo').val(sllfar).select2().change();
		        	
		        	} */
		        }
			});
		}
	}	
	
	function getExpiryDate(call) {
		//$('#selectedBatchNo').empty();
		clearElement('bestBefore',true);
		$('#bestBefore').val('');
		clearElement('expDate',true);
		$('#expDate').val('');
		if(!isEmpty(call)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "packing_populateExpiryDate.action",
		        data: {selectedFarm : call},
		        success: function(result) {
		        	$('#expDate').val(result.expDate);
		        }
			});
		}
	}
function getBlockData(blockid){
	$('#hquantity').html('');
	$('#product').html('');
	$('#variety').html('');
	$('#country').html('');
	$('#createdDate').html('');
	$('#qrCode').html('');
	if(blockid!=null && blockid!=''){
		$.ajax({
			type : "POST",
			async : false,
			url : "packing_populateBlockData.action",
			data: {selectedBlock : blockid},
			success : function(result) {
				$('#hquantity').html(result.hquantity);
				$('#product').html(result.product);
				$('#variety').html(result.variety);
				$('#country').html(result.country);
				$('#createdDate').html(result.createdDate);
				$('#qrCode').val(result.qrCode);
			}
		});
	}
}	

	function onCancel() {
		window.location.href = "<s:property value="redirectContent" />";
	}
	
	
	function addRow(rowId){
		var rowCounter = $('#procurementDetailContent tr').length-1;
		   	if(rowId!=''){
		   		$('#'+rowId).remove();
		   	}
		   	var pacdate = jQuery("#date").val();
		   	if(isEmpty(pacdate)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.date')}" />');
				return false;
		     }
		 
		   	jQuery("#validateError").text("");
		   	var farmer = $("#farmer").val();
		    var farm = $("#farm").val();
		    var selectedBlock = $("#selectedBlock").val();
		    var plantingId = $("#plantingId").val();
		    
		    var selectedBatchNo = $("#selectedBatchNo").val();
		 	if(selectedBatchNo!=null && selectedBatchNo!='' && plantingId!=null && plantingId!='' && $('.'+selectedBatchNo+plantingId).attr("id")!=null && $('.'+selectedBatchNo+plantingId).attr("id")!=undefined && $('.'+selectedBatchNo+plantingId).attr("id")!=''){
		   		alert("Product details already exists. Please update the list");
		   	}else{
		    var product = $("#product").html();
		    var variety = $("#variety").html();
		    var hquantity = $("#hquantity").html();
		    var createdDate = $("#createdDate").html();
		    var pquantity = jQuery("#pquantity").val();
		    var date = jQuery("#date").val();
		    var price = jQuery("#price").val();
		    var totalprice = jQuery("#totalprice").html();
		    var rejectWt = jQuery("#rejectWt").val();
		    var bestBefore = jQuery("#bestBefore").val();
		    var country = $("#country").html();
		    var expDaten = $("#expDate").val();
		    var qrCode = $("#qrCode").val();
		    
		    var createdDatesa;
		    var packingDatesa;
		    var flag;
		    var flagForSame;
		    if(!isEmpty(createdDate) && !isEmpty(date)){
		    	 createdDatesa = moment(createdDate, 'DD-MM-YYYY').valueOf();
		         packingDatesa = moment(date, 'DD-MM-YYYY').valueOf();
		         flag=moment(createdDatesa).isBefore(packingDatesa);
		         flagForSame=moment(createdDatesa).isSame(packingDatesa);
		    }
		    
		    var bbd;
		    var pd;
		    var flagBbdAndPd;
		    var flagForSameBbdAndPd;
		    if(!isEmpty(bestBefore) && !isEmpty(pacdate)){
		    	   bbd = moment(bestBefore, 'DD-MM-YYYY').valueOf();
			       pd = moment(pacdate, 'DD-MM-YYYY').valueOf();
			       flagBbdAndPd=moment(pd).isBefore(bbd);
			       flagForSameBbdAndPd=moment(bbd).isSame(pd);
		    }
		   
	        
	       var  pquantitySubprice=hquantity-pquantity;
	       
	   
	      if(isEmpty(plantingId)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.planting')}" />');
				return false;
		 }else if(isEmpty(product)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.product')}" />');
				return false;
		 }else if(isEmpty(variety)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packhouseIncoming.variety')}" />');
				return false;
		 }else if(isEmpty(hquantity)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packing.harvested')}" />');
				return false;
		 }else if(isEmpty(pquantity)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packing.packed')}" />');
				return false;
		 }
		 else if(isEmpty(price)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packing.price')}" />');
				return false;
		 }
		 else if(parseFloat(price)<=0){
				$("#validateError").text('<s:property value="%{getLocaleProperty('packing.price.zero')}" />');
				return false;
		 }
		 else if(isEmpty(rejectWt)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packing.rejectWt')}" />');
				return false;
		 }
		 else if(parseFloat(pquantitySubprice)<parseFloat(rejectWt)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('valid.packing.rejectWt')}" />');
				return false;
		 }
		 else if(parseFloat(pquantity)<=0){
				$("#validateError").text('<s:property value="%{getLocaleProperty('packing.pquantity.zero')}" />');
				return false;
		 }else if(isEmpty(bestBefore)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.packing.bestBefore')}" />');
				return false;
		 }
	    else if(!flagBbdAndPd && !flagForSameBbdAndPd){
		 /* else if(bestBefore < pacdate){ */
				$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.bestbeforedate')}" />');
				return false;
		 }
		 else if(parseFloat(pquantity) > parseFloat(hquantity)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.packingqty')}" />');
				return false;
		 }
		 else if(!flag && !flagForSame){
			 $("#validateError").text('<s:property value="%{getLocaleProperty('invalid.createdNpackingdate')}" />');
				return false;
			}
/* 		 else if(!flag){
			 $("#validateError").text('<s:property value="%{getLocaleProperty('invalid.createdNpackingdate')}" />');
				return false;
			} */
		  /* else if(createdDate > date){
				$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.createdNpackingdate')}" />');
				return false;
		 } */
		 
		   	var tableRow="<tr id=row"+(++rowCounter)+"  class='prodrow "+selectedBatchNo+plantingId+"'>";
		   	tableRow+="<td class='hide farmer'>"+farmer +"</td>";
		   	tableRow+="<td class='hide farm'>"+ farm +"</td>";
			tableRow+="<td class='hide selectedBlock'>"+selectedBlock+"</td>";
		 	tableRow+="<td class='hide selectedBatchNo'>"+selectedBatchNo+"</td>";
		 	tableRow+="<td>"+jQuery("#farmer option:selected").text()+"</td>";
		 	tableRow+="<td>"+jQuery("#farm option:selected").text()+"</td>";
		 	tableRow+="<td class='exeblocks'>"+jQuery("#selectedBlock option:selected").text()+"</td>";
		 	
		 	tableRow+="<td class='plantingIdLabel'>"+jQuery("#plantingId option:selected").text()+"</td>";
		 	tableRow+="<td class='hide plantingId'>"+plantingId+"</td>";
		 	
		 	tableRow+="<td class='exeblocksno'>"+jQuery("#selectedBatchNo option:selected").text()+"</td>";
			tableRow+="<td class='product'>"+product+"</td>";
		   	tableRow+="<td class='variety'>"+variety+"</td>";
		   	tableRow+="<td class='createdDate'>"+createdDate+"</td>";
		   	tableRow+="<td class='hquantity'>"+hquantity+"</td>";
			tableRow+="<td class='pquantity'>"+pquantity+"</td>";
		 	tableRow+="<td class='rejectWt'>"+rejectWt+"</td>";
		   	tableRow+="<td class='price'>"+price+"</td>";
		   	tableRow+="<td class='totalprice'>"+totalprice+"</td>";
			tableRow+="<td class='bestBefore'>"+bestBefore+"</td>";
		   	tableRow+="<td class='country'>"+country+"</td>";
		   	tableRow+="<td class='hide qrCode'>"+qrCode+"</td>";
		   	tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i>&nbsp;&nbsp;&nbsp;&nbsp;<i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteProduct("+rowCounter+")'></td>";
		   	tableRow+="</tr>";
		   	jQuery("#procurementDetailContent").append(tableRow);
		  	resetProductData();
			$('#add').attr("onclick","addRow()");
		   	}
		   
		   	
	}

	function deleteProduct(rowCounter){
	   	var id="#row"+rowCounter;
	   	jQuery(id).remove();
	   }


	   function callTrigger(id){
	   	$("#"+id).trigger("change");
	   }



	function editRow(rowCounter){
		var id="#row"+rowCounter;
		 
		$.each(jQuery(id), function(index, value) {
			var editid = $(this).find(".incoId").val();
			var farmer = jQuery(this).find(".farmer").text();	
			var farmerTxt = jQuery(this).find(".farmer1").text();	
			var farm = jQuery(this).find(".farm").text();
			var farmTxt = jQuery(this).find(".farm1").text();
			var selectedBlock = jQuery(this).find(".selectedBlock").text();
			
			var plantingId = $(this).find(".plantingId").text();
			var plantingIdLabel = jQuery(this).find(".plantingIdLabel").text();
			
			var selectedBatchNo = jQuery(this).find(".selectedBatchNo").text();
			
			var selectedBlockTxt = jQuery(this).find(".exeblocks").text();
			var selectedBatchNoTxt = jQuery(this).find(".exeblocksno").text();
		
			
			
			var pquantity = jQuery(this).find(".pquantity").text();
			var hquantity = jQuery(this).find(".hquantity").text();
			var price = jQuery(this).find(".price").text();
			var totalprice = jQuery(this).find(".totalprice").text();
			var rejectWt = jQuery(this).find(".rejectWt").text();
			var bestBefore = jQuery(this).find(".bestBefore").text();
			jQuery("#farmer").val(farmer).select2().change();
				jQuery("#farm").val(farm).select2().change();
			product = jQuery(this).find(".productext").text();
			variety = jQuery(this).find(".varietyext").text();
			extcounrty = jQuery(this).find(".extcountry").text();
			var qrCode = jQuery(this).find(".qrCode").text();
			

			jQuery("#farmer").val(farmer).select2().change();
						
						if(jQuery("#farmer").val()==null || jQuery("#farmer").val()==''){
							 $('#farmer').append(new Option(farmerTxt, farmer));
							 	$("#farmer").val(farmer);
							
						}
						$("#farmer").trigger("change");
						 
jQuery("#farm").val(farm).select2().change();
						
						if(jQuery("#farm").val()==null || jQuery("#farm").val()==''){
							 $('#farm').append(new Option(farmTxt, farm));
							 	$("#farm").val(farm);
							
						}
						$("#farm").trigger("change");
			jQuery("#selectedBlock").val(selectedBlock).select2();
			
			if(jQuery("#selectedBlock").val()==null || jQuery("#selectedBlock").val()==''){
				
				   $('#selectedBlock').append(new Option(selectedBlockTxt, selectedBlock));
				 	$("#selectedBlock").val(selectedBlock);
			}
			$("#selectedBlock").trigger("change");
			
			
			 	$("#plantingId").val(plantingId).trigger('change');
		   	if($("#plantingId").val()==null || $("#plantingId").val()=='' ){
		    	 $('#plantingId').append(new Option(plantingIdLabel, plantingId));
		 	 	$("#plantingId").val(plantingId).trigger('change');	
		   	} 
			
			
			jQuery("#selectedBatchNo").val(selectedBatchNo).select2().change();
			
			if(jQuery("#selectedBatchNo").val()==null || jQuery("#selectedBatchNo").val()==''){
				 $('#selectedBatchNo').append(new Option(selectedBatchNoTxt, selectedBatchNo));
				 	$("#selectedBatchNo").val(selectedBatchNo);
				
			}
			$("#selectedBatchNo").trigger("change");
			
			
			
			jQuery("#pquantity").val(pquantity);
			jQuery("#price").val(price);
			jQuery("#totalprice").html(totalprice);
			jQuery("#rejectWt").val(rejectWt);
			jQuery("#bestBefore").val(bestBefore);
			jQuery("#qrCode").val(qrCode);
			if(editid!=undefined && editid!=''){
				$("#hquantity").html((parseFloat(pquantity)+parseFloat(hquantity)+parseFloat(rejectWt)).toFixed(2));
			}else{
				$("#hquantity").html(hquantity);
			}
		
		});
		$('#add').attr("onclick","addRow('row"+rowCounter+"')");
	}

	function resetProductData(){			

		   $("#farmer option[value='']").prop("selected", true).trigger('change');
		   $("#farm option[value='']").prop("selected", true).trigger('change');
		   $("#selectedBlock option[value='']").prop("selected", true).trigger('change');
		   $("#plantingId option[value='']").prop("selected", true).trigger('change');
		   $("#selectedBatchNo option[value='']").prop("selected", true).trigger('change');
		 	jQuery("#product").html("");
			jQuery("#variety").html("");
			jQuery("#hquantity").html("");
			jQuery("#createdDate").html("");
			jQuery("#pquantity").val("");
			jQuery("#price").val("");
			jQuery("#totalprice").html("");
			jQuery("#rejectWt").val("");
			jQuery("#qrCode").val("");
		 	var myDateVal = moment( new Date()).format("DD-MM-YYYY");
		 
 $('#bestBefore').val( myDateVal);
 $('#bestBefore').datepicker('update');
 jQuery("#add").show();
			jQuery("#edit").hide();
		}
	
	function resetList(){
		 var createdDate = $(".createdDate").text();
		 if(!isEmpty(createdDate)){
			 if(confirm('<s:property value="%{getLocaleProperty('reset.resetListDatePacking')}" />')){
                 $('.prodrow').remove();
                 $("#packhouse").val(" ").change();
    			 $("#target1_packing_packerName").val("");
	       }  
		 }
		}
	

	function buildPackingDetailInfoArray(){
		var tableBody = jQuery("#procurementDetailContent tr:nth-child(n + 2)");
		var productInfo="";
		
		/** 
			INFO :- 
				##=Splitter for td
				@@=Splitter for tr
		*/
		$.each(tableBody, function(index, value) {
			productInfo+="#"+jQuery(this).find(".farmer").text(); //0
			productInfo+="#"+jQuery(this).find(".farm").text(); //1
			productInfo+="#"+jQuery(this).find(".selectedBlock").text(); //2
			productInfo+="#"+jQuery(this).find(".selectedBatchNo").text(); //3
			productInfo+="#"+jQuery(this).find(".pquantity").text(); //4
			productInfo+="#"+jQuery(this).find(".price").text(); //5
			productInfo+="#"+jQuery(this).find(".rejectWt").text(); //6
			productInfo+="#"+jQuery(this).find(".bestBefore").text(); //7
			productInfo+="#"+jQuery(this).find(".plantingId").text();//8
			productInfo+="#"+jQuery(this).find(".qrCode").text(); //9
			productInfo+="#"+jQuery(this).find(".totalprice").text()+"@"; //10
		});
		return productInfo;
	}

	function isDecimal(evt) {
		
		 evt = (evt) ? evt : window.event;
		    var charCode = (evt.which) ? evt.which : evt.keyCode;
		    if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46) {
		        return false;
		    }
		    $('input').bind('keypress', function (event) {
		    	var regex = new RegExp("^\d*(\.\d{0,2})?$");
		    	var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		    	if (!regex.test(key)) {
		    	   event.preventDefault();
		    	   return false;
		    	}
		    return true;
	});}
	
	function closeDatepickernew(){
		 $(".datepicker").hide();
	}
	
	function populatePlanting(val){
		var selectedBlock=val;
		if(!isEmpty(selectedBlock)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "packing_populatePlanting.action",
		        data: {selectedBlock : selectedBlock, selectedFarm:$('#packhouse').val()},
		        success: function(result) {
		        	insertOptions("plantingId", $.parseJSON(result));
		        }
			});
		}else{
			clearElement('plantingId',true);
		}
	}
/* 	function populatePlanting(val){
		var selectedBlock=val;
		if(!isEmpty(selectedBlock)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "packing_populatePlanting.action",
		        data: {selectedBlock : selectedBlock},
		        success: function(result) {
		        	insertOptions("plantingId", $.parseJSON(result));
		        }
			});
		}else{
			clearElement('plantingId',true);
		}
	} */
	
	function getTotalPrice(){
		var pquantity = $("#pquantity").val();	
		var price = $("#price").val();	
		
		var total= (pquantity*price).toFixed(2);
		$("#totalprice").text(total);
	
	}
</script>
<body>

	<s:form name="form" cssClass="fillform" id="target1" method="post">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:hidden name="packingTotalString" id="packingTotalString" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="packing.id" />

		</s:if>
		<s:hidden key="command" />
		<div class="appContentWrapper marginBottom">
			<div class="error">
				<s:actionerror />
				<s:fielderror />
				<s:if test="hasActionErrors()">
					<div style="color: red;">
						<s:actionerror />
					</div>
				</s:if>
				<div id="validateError" style="text-align: left;"></div>
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.packing" />
				</h2>
				<div class="flexform">
					<div class="flexform-item">
						<label for="txt"><s:text name="packing.packingDate" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="packingDate" id="date" theme="simple"
								maxlength="10"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="form-control input-sm" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="packing.packhouse" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">

							<s:select class="form-control  select2 " id="packhouse"
								name="selectedPackHouse" listKey="id" listValue="name"
								headerKey=" " headerValue="%{getText('txt.select')}"
								list="packhouseList" onchange="loadPackingFarmer(this.value)" />
							<s:hidden id="packinval" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="packing.packerName" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="packing.packerName" theme="simple"
								cssClass="upercls form-control" />
						</div>
					</div>

					<div class="" style="overflow: auto;">
						<table class="table table-bordered aspect-detail"
							id="procurementDetailTable" style="">
							<thead>
								<tr>
									<th><s:property
											value="%{getLocaleProperty('scouting.farmer')}" /><sup
										style="color: red;">*</sup></th>
									<th><s:property
											value="%{getLocaleProperty('scouting.farm')}" /><sup
										style="color: red;">*</sup></th>
									<th><s:property
											value="%{getLocaleProperty('packing.blockID')}" /><sup
										style="color: red;">*</sup></th>
									<th><s:property
											value="%{getLocaleProperty('Planting ID')}" /><sup
										style="color: red;">*</sup></th>
									<th><s:property
											value="%{getLocaleProperty('packing.receptionBatchNo')}" /><sup
										style="color: red;">*</sup></th>
									<th><s:property
											value="%{getLocaleProperty('packing.productName')}" /></th>
									<th><s:property
											value="%{getLocaleProperty('packing.variety')}" /></th>
									<th><s:property
											value="%{getLocaleProperty('incomingShipmentDate')}" /></th>
									<th><s:property
											value="%{getLocaleProperty('packing.harvested')}" /></th>
									<th><s:property
											value="%{getLocaleProperty('packing.packed')}" /><sup
										style="color: red;">*</sup></th>
									<th><s:property
											value="%{getLocaleProperty('packing.rejectWt')}" /><sup
										style="color: red;">*</sup></th>
									<th><s:property
											value="%{getLocaleProperty('packing.price')}" /><sup
										style="color: red;">*</sup></th>
									<th><s:property
											value="%{getLocaleProperty('packing.totalprice')}" /><sup
										style="color: red;">*</sup></th>
									<%-- <th><s:property
											value="%{getLocaleProperty('packing.rejectWt')}" /><sup
										style="color: red;">*</sup></th> --%>
									<th><s:property
											value="%{getLocaleProperty('packing.bestBefore')}" /><sup
										style="color: red;">*</sup></th>
									<th><s:property
											value="%{getLocaleProperty('packing.country')}" /></th>
									<th style="text-align: center"><s:property
											value="%{getLocaleProperty('action')}" /></th>
								</tr>
							</thead>

							<tbody id="procurementDetailContent">
								<tr>

									<td><s:select name="farmer" list="{}" headerKey=""
											headerValue="%{getText('txt.select')}" listKey="key"
											listValue="value" theme="simple" id="farmer"
											onchange="listFarm(this.value);"
											cssClass="form-control select2" /></td>
									<td><s:select name="farm" list="{}" headerKey=""
											headerValue="%{getText('txt.select')}" listKey="key"
											listValue="value" theme="simple" id="farm"
											onchange="listBlock(this.value);"
											cssClass="form-control select2" /></td>
									<td><s:select class="form-control  select2 "
											id="selectedBlock" name="selectedBlock" listKey="id"
											listValue="blockId" headerKey=""
											headerValue="%{getText('txt.select')}" list="{}"
											onchange="populatePlanting(this.value);getExpiryDate(this.value);" /></td>


									<td width="180"><s:select list="{}" headerKey=""
											headerValue="%{getText('txt.select')}" listKey="key"
											listValue="value" id="plantingId"
											class="form-control select2" name="plantingId"
											onchange="listBatchNo(this.value)" /></td>


									<td><s:select class="form-control  select2 "
											id="selectedBatchNo" name="selectedBatchNo" listKey="key"
											listValue="value" headerKey=""
											onchange="getBlockData(this.value)"
											headerValue="%{getText('txt.select')}" list="{}" /></td>
									<td><span id="product"></span></td>
									<td><span id="variety"></span></td>
									<td><span id="createdDate"></span></td>
									<td><span id="hquantity"></span></td>
									<td><s:textfield name="pquantity" theme="simple"
											cssClass="lowercase form-control" id="pquantity"
											onkeypress="return isDecimal1(event,this)"
											onkeyup="getTotalPrice()" /></td>
									<td><s:textfield name="rejectWt" theme="simple"
											cssClass="lowercase form-control" id="rejectWt"
											onkeypress="return isDecimal1(event,this)" /></td>
									<td><s:textfield name="price" theme="simple"
											cssClass="lowercase form-control" id="price"
											onkeypress="return isDecimal1(event,this)"
											onkeyup="getTotalPrice()" /></td>
									<td><span id="totalprice"></span></td>
									<td class="hide"><s:textfield name="expDate"
											theme="simple" cssClass="lowercase form-control" id="expDate" /></td>
									<td class="hide"><s:textfield name="qrCode" theme="simple"
											cssClass="lowercase form-control" id="qrCode" /></td>
									<td><s:textfield name="bestBefore" theme="simple"
											maxlength="10"
											data-date-format="%{getGeneralDateFormat().toLowerCase()}"
											size="20" cssClass="date-picker form-control input-sm"
											id="bestBefore" /></td>
									<td><span id="country"></span></td>
									<td style="text-align: center">
										<button type="button" id="add" class="btn btn-sm btn-success"
											aria-hidden="true" onclick="addRow()"
											title="<s:text name="Ok" />">
											<i class="fa fa-check"></i>
										</button>
										<button type="button" class="btn btn-sm btn-danger"
											aria-hidden="true " onclick="resetProductData()"
											title="<s:text name="Cancel" />">
											<i class="glyphicon glyphicon-remove-sign"></i>
										</button>
									</td>
								</tr>
								<s:iterator value="packing.packingDetails" status="incr">
									<tr id="row<s:property 	value="#incr.index"  />"
										class="prodrow <s:property value="ctt.id" /><s:property value="planting.id" />">
										<s:hidden class="incoId" value="%{id}" />
										<td class="hide farmer"><s:property
												value="blockId.farm.farmer.id" /></td>
										<td class="hide farm"><s:property value="blockId.farm.id" /></td>
										<td class="hide selectedBlock"><s:property
												value="blockId.id" /></td>
										<td class="hide selectedBatchNo"><s:property
												value="ctt.id" /></td>
										<%-- 	<td><s:property value="blockId.farm.farmer.firstName" /></td>
										<td><s:property value="blockId.farm.farmName" /></td> --%>
										<td class="farmer1"><s:property
												value="blockId.farm.farmer.firstName" /> - <s:property
												value="blockId.farm.farmer.farmerId" /></td>
										<td class="farm1"><s:property
												value="blockId.farm.farmName" /> - <s:property
												value="blockId.farm.farmCode" /></td>
										<td class="exeblocks"><s:property value="blockId.blockId" />
											- <s:property value="blockId.blockName" /></td>
										<td class="qrCode hide"><s:property value="qrCodeId" /></td>
										<td class="plantingIdLabel"><s:property
												value="planting.plantingId" /></td>

										<td class="plantingId hide"><s:property
												value="planting.id" /></td>

										<td class="exeblocksno"><s:property value="batchNo" /></td>

										<%-- <td class="productext"><s:property
												value="blockId.variety.name" /></td>
										<td class="varietyext"><s:property
												value="blockId.grade.name" /></td> --%>
										<td class="productext"><s:property
												value="planting.variety.name" /></td>
										<td class="varietyext"><s:property
												value="planting.grade.name" /></td>

										<%-- 	<td class="createdDate"><s:property value="ctt.createdDate" /></td> --%>
										<td class="createdDate"><s:date name="ctt.createdDate"
												format="dd-MM-yyyy" /></td>


										<td class="hquantity"><s:property
												value="ctt.sortedWeight" /></td>
										<td class="pquantity"><s:property value="quantity" /></td>
										<td class="rejectWt"><s:property value="rejectWt" /></td>
										<td class="price"><s:property value="price" /></td>
										<td class="totalprice"><s:property value="totalprice" /></td>
										<%-- <td class="rejectWt"><s:property value="rejectWt" /></td> --%>
										<td class="bestBefore"><s:date name="bestBefore"
												format="%{getGeneralDateFormat()}" /></td>
										<td class="extcountry"><s:property
												value="blockId.farm.farmer.village.city.locality.state.name" /></td>

										<td><i
											style="cursor: pointer; font-size: 150%; color: blue;"
											class="fa fa-pencil-square-o" aria-hidden="true"
											onclick='editRow("<s:property value="#incr.index" />")'></i>
											&nbsp;&nbsp;&nbsp;&nbsp;<i
											style="cursor: pointer; font-size: 150%; color: black;"
											class="fa fa-trash-o" aria-hidden="true"
											onclick='deleteProduct("<s:property value="#incr.index" />")'></i></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="margin-top-10">
			<span class=""><span class="first-child">
					<button type="submit" id="buttonAdd1"
						onclick="event.preventDefault();" class="save-btn btn btn-success">
						<font color="#FFFFFF"> <b> <s:text name="save.button" /></b>
						</font>
					</button>&nbsp;
			</span></span> <span class=""><span class="first-child"><button
						type="button" id="canBt" class="cancel-btn btn btn-sts"
						onclick="onCancel();">
						<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
						</font></b>
					</button></span></span>
		</div>
	</s:form>
</body>
</html>