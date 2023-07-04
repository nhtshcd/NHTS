<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType")
					: "swithlayout"%>">
</head>
<script type="text/javascript">
var layOut='<%=request.getParameter("layoutType")%>';
var command = '<s:property value="command"/>';
	jQuery(document).ready(function() {
		$('#pbmObservArea').attr("disabled", true);
		url = 'scouting_';
		$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
		if(command=='create'){
			loadSprayFarmer();	
		}
		
		if(command=='create'){
			$('input[name="scouting.InsectsObserved"][value="' +0+ '"]').prop('checked', true);
			$('input[name="scouting.diseaseObserved"][value="' +0+ '"]').prop('checked', true);
			$('input[name="scouting.weedsObserveds"][value="' +0+ '"]').prop('checked', true);
			}
		
		$("#buttonAdd1").on('click', function (event) {  
			$("#deletestatus").val(0);
			$("#buttonAdd1").prop('disabled', true);
			var scoutingArray =  buildScoutingDetailInfoArray();
    		$('#scoutingTotalString').val(scoutingArray);
			if(!validateAndSubmit("target","scouting_")){
				$("#buttonAdd1").prop('disabled', false);
			}
		});
		$("#buttonAdd2").on('click', function (event) { 
			$("#deletestatus").val(3);
			$("#buttonAdd2").prop('disabled', true);
			var scoutingArray =  buildScoutingDetailInfoArray();
    		$('#scoutingTotalString').val(scoutingArray);
    		if(!validateAndSubmit("target","scouting_")){
				$("#buttonAdd2").prop('disabled', false);
			}
		});
		if(command=='update'){
			$('#country').change();
			loadFarmer('<s:property value="scouting.planting.farmCrops.farm.farmer.village.id"/>');
			var cropDet='<s:property value="selectedProduct" />';
			$('#cropName').val(cropDet).trigger('change');
			var varietyId='<s:property value="scouting.variety" />';
			 $("#varietyName option[value='"+ varietyId.trim() + "']").prop("selected", true).trigger('change');
			 
			 Insects_Observed('<s:property value="scouting.InsectsObserved"/>');

			Disease_Observed('<s:property value="scouting.diseaseObserved"/>')
			Weeds_Observed('<s:property value="scouting.weedsObserveds"/>')
			var nameOfWeeds=$.trim('<s:property value="scouting.nameOfWeeds"/>');
			$('#nameOfWeeds').val(JSON.parse(JSON.stringify(nameOfWeeds.replace(/\s/g,'').split(",")))).select2();
			$('#nameOfWeeds').change();
			
			var nameOfInsectsObserveds=$.trim('<s:property value="scouting.nameOfInsectsObserved"/>');
			$('#nameOfInsectsObserved').val(JSON.parse(JSON.stringify(nameOfInsectsObserveds.replace(/\s/g,'').split(",")))).select2();
			$('#nameOfInsectsObserved').change();
			
			var nameOfDiseases=$.trim('<s:property value="scouting.nameOfDisease"/>');
			$('#nameOfDisease').val(JSON.parse(JSON.stringify(nameOfDiseases.replace(/\s/g,'').split(",")))).select2();
			$('#nameOfDisease').change();
			
			var farmCropsIdForUpdate='<s:property value="scouting.planting.farmCrops.id"/>';
			if(farmCropsIdForUpdate != null && farmCropsIdForUpdate != ''){
			populatePlanting(farmCropsIdForUpdate);
			}
			
		}
		//loadDependancy('sup4');
	});	
	
	
	function onCancel() {
		window.location.href = "<s:property value="redirectContent" />";
	}
	
	function setFileName1(val) {
		
		if (val != null && val != '') {
			$('#fileNamePhoto').val(val);
			var estt = val.split('.').pop().toLowerCase();
			$('#fileType').val(estt);
			if (estt == 'PNG') {
				$('#fileType').val(1);
			} else if (estt == 'JPEG') {
				$('#fileType').val(2);
			}
		}
	}
		
	function popDownload(type) {
		document.getElementById("loadId").value = type;
		$('#fileDownload').submit();
	}
	
	function loadAjaxData(fid) {
	
		if (fid == ' ') {
			clearElement('farm');
			clearElement('varietyName');
			
		}
		$.ajax({
			type : "POST",
			async : false,
			url : "scouting_populateFarmerData.action",
			data : {
				selectedFarmer : fid
			},
			success : function(result) {
				
				$('#postalAddr').text(result.address);
				$('#mobNumber').text(result.mobileNo);
			}
		});
	}
	
	function populateBlockDetail(cropid){
		if (cropid == ' ') {
			 $('#varietyName option:not(:first)').remove();
		}
			$.ajax({
				type : "POST",
				async : false,
				url : "scouting_populateBlockDetailsForScoutingAndSpraying.action",
				data : {
					selectedFarmer : cropid
				},
				success : function(result) {
					$('#cropPlantingId').val(result.plantingId);
					$('#blockId').val(result.blockId);
					$('#cropVeriety').val(result.variety);
					$('#grade').val(result.grade);
					
				}
			});	
		
	}
    
	function listFarmsCropProduct(farmId){
		var selectedFarm=farmId;
		var selectedFamer=$('#farmer').val();
		
		clearElement('varietyName',true);
		
		if(!isEmpty(selectedFarm)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "scouting_populatBlockWithPlanting.action",
		        data: {selectedFarm : selectedFarm,selectedFarmer:selectedFamer},
		        success: function(result) {
		        	insertOptions("varietyName", $.parseJSON(result));
		        	
		        	var sllfar ='<s:property value="scouting.planting.farmCrops.id"/>';
		        	if(selectedFarm =='<s:property value="scouting.planting.farmCrops.farm.id"/>' &&  sllfar!=null && sllfar!='' && sllfar!='null'){
		        		$('#varietyName').val(sllfar).change();
		        	}
		        	
		        	
		        }
			});
		}else{
			  var ss= $("#varietyName").val();
			  clearElement('varietyName',true);
		}
	}
	
	function populatePlanting(val){
		var selectedBlock=val;
		clearElement('plantingId',true);
		if(!isEmpty(selectedBlock)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "scouting_populatePlanting.action",
		        data: {selectedBlock : selectedBlock},
		        success: function(result) {
		        	insertOptions("plantingId", $.parseJSON(result));
		        	if(command=='update'){
		        		var plantingfar ='<s:property value="scouting.planting.id"/>';
			        	if(plantingfar!=null && plantingfar!='' && plantingfar!='null'){
			        		$('#plantingId').val(plantingfar).change();
			        	}
		        	} 
		        }
			});
		}else{
			clearElement('plantingId',true);
		}
	}
	

	
	function listFarmsCrop(selectedProduct){
		var selectedFarm=$('#farm').val();
		clearElement('varietyName',true);
		
		if(!isEmpty(selectedFarm)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "scouting_populateFarmCropsVariety.action",
		        data: {selectedFarm : selectedFarm,selectedProduct:selectedProduct},
		        success: function(result) {
		        	insertOptions("varietyName", $.parseJSON(result));
		        	var sllfar ='<s:property value="scouting.planting.farmCrops.id"/>';
		        	if(selectedFarm === '<s:property value="scouting.planting.farmCrops.farm.id"/>' && sllfar!=null && sllfar!='' && sllfar!='null'){
		        		$('#varietyName').val(sllfar).change();
		        	}
		        }
			});
		}
	}
	
	function listFarm(call) {
		var selectedPro = call;	
		var insType=1;
		
		clearElement('farm',true);
		
			if(!isEmpty(selectedPro)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "scouting_populateFarmwithplanting.action",
		        data: {selectedFarmer : selectedPro,inspectionType:insType},
		        success: function(result) {
		       
		        	insertOptions("farm", $.parseJSON(result));
		        	
				   var ss= $("#varietyName").val();
				   clearElement('varietyName',true);
				    
		        	var sllfar ='<s:property value="scouting.planting.farmCrops.farm.id"/>';
		        	
		        	if(selectedPro =='<s:property value="scouting.planting.farmCrops.farm.farmer.id"/>'  && sllfar!=null && sllfar!='' && sllfar!='null'){
		        	
		        		$('#farm').val(sllfar).select2().change();
		        	
		        	}
		        }
			});
		}
	}	
	
	function otherPbmObservEnabler(){
		var selectedQuali = $("#pbmObserv option:selected").text();
		if(selectedQuali.trim()=="Others"){
			$('#pbmObservArea').attr("disabled", false);
		}
		else{
			$('#pbmObservArea').attr("disabled", true);
		}
	}	
function addrow(rowId){
		var rowCounter = $('#procurementDetailContent tr').length-1;
		if(!isEmpty(rowId)){
			var id="#row"+rowId;
			jQuery(id).remove();
		}
		jQuery("#validateError").text("");
		var scoutDate1 = jQuery("#scoutDate1").val();
		var areaScot1 = jQuery("#areaScot1").val();
		var noOfPlts1 = jQuery("#noOfPlts1").val();
		var pbmObserv = jQuery("#pbmObserv").val();
		//var otherPbmObserv = jQuery("#otherPbmObserv").val();
		var otherpbmObserv = jQuery("#pbmObservArea").val();
		
		var noOfpltWith = jQuery("#noOfpltWith").val();
		var sol1 = jQuery("#sol1").val();
		var scoutInit = jQuery("#scoutInit").val();
		//alert(pbmObserv)
		if(isEmpty(scoutDate1)){
			jQuery("#validateError").text('<s:property value="%{getLocaleProperty('empty.date')}" />');
			return false;
		}
		else if(isEmpty(areaScot1)){
			jQuery("#validateError").text('<s:property value="%{getLocaleProperty('empty.areaScoot')}" />');
			return false;
		}
		else if(isEmpty(noOfPlts1)){
			jQuery("#validateError").text('<s:property value="%{getLocaleProperty('empty.noOfPlt1')}" />');
			return false;
		}
		else if(isEmpty(pbmObserv)){
			jQuery("#validateError").text('<s:property value="%{getLocaleProperty('empty.pbmObserv1')}" />');
			return false;
		}	
		else if(pbmObserv=='99'){
			if(isEmpty(otherpbmObserv)){
			jQuery("#validateError").text('<s:property value="%{getLocaleProperty('empty.otherpbmObserv')}" />');
			return false;
			}
		}
		else if(isEmpty(noOfpltWith)){
			jQuery("#validateError").text('<s:property value="%{getLocaleProperty('empty.noOfpltWith1')}" />');
			return false;
		}
		else if(isEmpty(sol1)){
			jQuery("#validateError").text('<s:property value="%{getLocaleProperty('empty.sol12')}" />');
			return false;
		}

		else if(isEmpty(scoutInit)){
			jQuery("#validateError").text('<s:property value="%{getLocaleProperty('empty.scoutInit1')}" />');
			return false;
		}
		
		
		var tableRow="<tr id=row"+(++rowCounter)+">";
		tableRow+="<td class='scoDat'>"+jQuery("#scoutDate1").val()+"</td>";
		//tableRow.append($("<td>").addClass("date textAlignRight").html(scoutDate1).append($('<input>').attr("type","hidden").val(scoutDate1).attr("name","["+rowCounter+"].dateStr")));
		tableRow+="<td class='areaScot'>"+jQuery("#areaScot1").val()+"</td>";
		tableRow+="<td class='noofPlts2'>"+jQuery("#noOfPlts1").val()+"</td>";
		tableRow+="<td class='prObserved hide'>"+jQuery("#pbmObserv").val()+"</td>";
		 var selText;
		$("#pbmObserv option:selected").each(function () {
			   var $this = $(this);
			   if ($this.length) {
			   if(!isEmpty(selText) && selText!=undefined){
				   selText = selText+","+$this.text();
			   }else{
				   selText = $this.text();
			   }
			   }
			});
		
		 
		 tableRow+="<td class='prObservedStr'>"+selText+"</td>"; 
		 tableRow+="<td class='otherPrObserved'>"+otherpbmObserv+"</td>"; 
		tableRow+="<td class='nopbmPlt'>"+jQuery("#noOfpltWith").val()+"</td>";
		tableRow+="<td class='solo'>"+jQuery("#sol1").val()+"</td>";
		tableRow+="<td class='scoutInita'>"+jQuery("#scoutInit").val()+"</td>";
		
		tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i>&nbsp;&nbsp;&nbsp;&nbsp;<i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteProduct("+rowCounter+")'></td>";
			tableRow+="</tr>";
			jQuery("#procurementDetailContent").append(tableRow);
			resetProductData();
}	


function resetProductData(){			
	
	jQuery("#scoutDate1").val("");
	jQuery("#areaScot1").val("");
	jQuery("#noOfPlts1").val("");
	jQuery("#pbmObserv").val("-1");
	jQuery('#pbmObserv').trigger('change');
	jQuery("#noOfpltWith").val("");
	jQuery("#sol1").val("");
	jQuery("#scoutInit").val("");
}

function deleteProduct(rowCounter){
	var id="#row"+rowCounter;
	jQuery(id).remove();
}

function editRow(rowCounter){
	
	var id="#row"+rowCounter;
	$.each(jQuery(id), function(index, value) {
		var scoDat = jQuery(this).find(".scoDat").text().trim();
		var areaScot = jQuery(this).find(".areaScot").text().trim();
		var noofPlts2 = jQuery(this).find(".noofPlts2").text().trim();
		var prObserved=$.trim(jQuery(this).find(".prObserved").text());
		var nopbmPlt = jQuery(this).find(".nopbmPlt").text().trim();
		var solo= jQuery(this).find(".solo").text().trim();
		var scoutInita = jQuery(this).find(".scoutInita").text().trim();
		jQuery("#scoutDate1").val(scoDat);
		jQuery("#areaScot1").val(areaScot);
		jQuery("#noOfPlts1").val(noofPlts2);
		jQuery('#pbmObserv').val(JSON.parse(JSON.stringify(prObserved.replace(/\s/g,'').split(",")))).select2();
		jQuery("#noOfpltWith").val(nopbmPlt.trim());
		jQuery("#sol1").val(solo);
		jQuery("#scoutInit").val(scoutInita);
		$("#add1").attr("onclick","addrow("+rowCounter+")");
	});
	
}


function buildScoutingDetailInfoArray(){
	
	var tableBody = jQuery("#procurementDetailContent tr:nth-child(n + 2)");
	var productInfo="";
	$.each(tableBody, function(index, value) {
		productInfo+=jQuery(this).find(".scoDat").text(); //0
		productInfo+="#"+jQuery(this).find(".areaScot").text(); //1
		productInfo+="#"+jQuery(this).find(".noofPlts2").text();
		productInfo+="#"+jQuery(this).find(".prObserved").text();
		productInfo+="#"+jQuery(this).find(".otherPrObserved").text();
		productInfo+="#"+jQuery(this).find(".nopbmPlt").text();
		productInfo+="#"+jQuery(this).find(".solo").text();
		productInfo+="#"+jQuery(this).find(".scoutInita").text()+"@"; //5
	});
	//alert(productInfo)
	return productInfo;
}

function Insects_Observed(value){
	
	
	 if(value=='1'){
		 $(".perOrNumberInsects").removeClass('hide');
		 $(".nameOfInsectsObserved").removeClass('hide');
		 //$("#").val('');
	 } 
	 else{
		 $(".perOrNumberInsects").addClass('hide');
		 $(".nameOfInsectsObserved").addClass('hide');
	 }
}

function Disease_Observed(value){
	 if(value=='1'){
		 $(".nameOfDisease").removeClass('hide');
		 $(".perInfection").removeClass('hide');
	 } 
	 else{
		 $(".nameOfDisease").addClass('hide');
		 $(".perInfection").addClass('hide');
	 }
}

function Weeds_Observed(value){
	 if(value=='1'){
		 $(".weedsPresence").removeClass('hide');
		 $(".recommendations").removeClass('hide');
		 $(".nameOfWeeds").removeClass('hide');
	 }else {
		 $(".weedsPresence").addClass('hide');
		 $(".recommendations").addClass('hide');
		 $(".nameOfWeeds").addClass('hide');
	 }
}
function nameOfWeedsSelectAll(fieldVal){
	
	 if(fieldVal!='' && fieldVal=='-1'){
		    
	      $('#nameOfWeeds > option[value!="-1"]').prop("selected","selected");
	      $('#nameOfWeeds > option[value="-1"]').prop("selected",false);
	      $("#nameOfWeeds").select2();
	     
	    }
	}
function nameOfDiseaseSelectAll(fieldVal){
	
	 if(fieldVal!='' && fieldVal=='-1'){
		    
	      $('#nameOfDisease > option[value!="-1"]').prop("selected","selected");
	      $('#nameOfDisease > option[value="-1"]').prop("selected",false);
	      $("#nameOfDisease").select2();
	     
	    }
	}
function nameOfInsectsObservedSelectAll(fieldVal){
	
	 if(fieldVal!='' && fieldVal=='-1'){
		    
	      $('#nameOfInsectsObserved > option[value!="-1"]').prop("selected","selected");
	      $('#nameOfInsectsObserved > option[value="-1"]').prop("selected",false);
	      $("#nameOfInsectsObserved").select2();
	     
	    }
	}



</script>
<body>
	<s:form id="fileDownload" action="generalPop_populateDownload">
		<s:hidden id="loadId" name="idd" />
	</s:form>

	<s:form name="form" cssClass="fillform" id="target"
		action="scouting_%{command}" enctype="multipart/form-data">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="scoutingTotalString" id="scoutingTotalString" />
		<s:hidden name="scouting.deleteStatus" id="deletestatus" />
		<s:hidden key="currentPage" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="scouting.id" />
		</s:if>
		<s:hidden key="command" />
		<s:hidden key="id" />
		<div class="appContentWrapper marginBottom">
			<div class="error">
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.scouting" />
				</h2>
				<div class="flexform">

					<div class="flexform-item">
						<label for="txt"><s:text name="scouting.receivedDate" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="scoutingStr" id="receivedDate" theme="simple"
								maxlength="10" readonly="true"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="date-picker form-control input-sm" />
						</div>
					</div>



					<div class="flexform-item">
						<label for="txt"><s:text name="country.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="countries"
								headerKey="" theme="simple" name="selectedCountry"
								maxlength="20" headerValue="%{getText('txt.select')}" Key="id"
								Value="name"
								onchange="listState(this,'country','state','localities','city','panchayath','village');"
								id="country" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="county.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="{}"
								headerKey="" theme="simple" name="selectedState"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="state" maxlength="20"
								onchange="listLocality(this,'state','localities','city','panchayath','village');" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="subcountry.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="{}"
								headerKey="" theme="simple" name="selectedLocality"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="localities" maxlength="20"
								onchange="listMunicipalities(this,'localities','city','panchayath','village');" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="ward.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="{}" headerKey=""
								theme="simple" name="selectedCity" maxlength="20"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="city" onchange="listVillage(this,'city','village')" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="village.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="{}"
								headerKey="" theme="simple" name="selectedVillage"
								maxlength="20" onchange="loadFarmer(this.value)"
								headerValue="%{getText('txt.select')}" id="village" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="scouting.farmer" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select name="scouting.planting.farmCrops.farm.farmer.id" list="{}"
								headerKey="" headerValue="%{getText('txt.select')}"
								listKey="key" listValue="value" theme="simple" id="farmer"
								onchange="listFarm(this.value);loadAjaxData(this.value)"
								cssClass="form-control select2" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="scouting.farm" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select name="scouting.planting.farmCrops.farm.id" list="{}"
								headerKey="" headerValue="%{getText('txt.select')}"
								listKey="key" listValue="value" theme="simple" id="farm"
								onchange="listFarmsCropProduct(this.value)"
								cssClass="form-control select2" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="block" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select id="varietyName" name="scouting.planting.farmCrops.id"
								listKey="key" listValue="value" list="{}" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="populatePlanting(this.value)"
								cssClass="form-control select2" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text name="Planting ID" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select id="plantingId" name="scouting.planting.id"
								listKey="key" listValue="value" list="{}" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="populateBlockDetail(this.value)"
								cssClass="form-control select2" />
						</div>
					</div>


					<%-- <div class="flexform-item">
						<label for="txt"><s:text name="scouting.blockId" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield readonly="true" id="blockId" theme="simple"
								maxlength="10" size="20" cssClass="form-control input-sm" />

						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="scouting.cropPlantingId" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield readonly="true" id="cropPlantingId" theme="simple"
								maxlength="10" size="20" cssClass="form-control input-sm" />

						</div>
					</div> --%>



					<div class="flexform-item">
						<label for="txt"><s:text name="scouting.cropPlanted" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield readonly="true" id="cropVeriety" theme="simple"
								maxlength="10" size="20" cssClass="form-control input-sm" />

						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="scouting.CropVariety" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield readonly="true" id="grade" theme="simple"
								maxlength="10" size="20" cssClass="form-control input-sm" />

						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><%-- <s:text name="scouting.sprayingRequired" /> --%>
						<s:property value="%{getLocaleProperty('scouting.sprayingRequired')}" />
						 <sup style="color: red;">*</sup> 
						</label>
						<div class="form-element">
							<s:select name="scouting.sprayingRequired" list="getCatList(getLocaleProperty('yesNo'))"
								headerKey="" headerValue="%{getText('txt.select')}"
								listKey="key" listValue="value" theme="simple"
								id="sourceOfWater" cssClass="form-control select2" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="scouting.sctRecommendation" /> <sup style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="scouting.sctRecommendation" id="sctRecommendation"
								theme="simple" size="20" cssClass="form-control input-sm"
								maxlength="250" />
						</div>
					</div>
				</div>


				<h2>
					<s:text name="info.insects" />
				</h2>

				<div class="flexform">

					<div class="flexform-item ">
						<label for="txt"><s:text name="scouting.InsectsObserved" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:radio list="insectsObservedMap"
								name="scouting.InsectsObserved" theme="simple"
								id="InsectsObserved" onchange="Insects_Observed(this.value)" />
						</div>
					</div>

					<div class="flexform-item nameOfInsectsObserved hide">
						<label for="txt"><s:text
								name="scouting.nameOfInsectsObserved" /></label>
						<div class="form-element">
							<s:select name="scouting.nameOfInsectsObserved"
								list="nameOfInsectsObserved" listKey="key" listValue="value"
								theme="simple" id="nameOfInsectsObserved"
								cssClass="form-control select2" multiple="true" headerKey="-1"
								headerValue="%{getText('txt.selectall')}"
								onchange="nameOfInsectsObservedSelectAll(this.value)" />
						</div>
					</div>


					<div class="flexform-item perOrNumberInsects hide">
						<label for="txt"><s:text
								name="scouting.perOrNumberInsects" /><sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="scouting.perOrNumberInsects"
								id="perOrNumberInsects" theme="simple" size="20"
								cssClass="form-control input-sm" maxlength="20" />
						</div>
					</div>
				</div>
				<h2>
					<s:text name="info.disease" />
				</h2>
				<div class="flexform">
					<div class="flexform-item ">
						<label for="txt"><s:text name="scouting.diseaseObserved" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:radio list="diseaseObservedMap"
								name="scouting.diseaseObserved" theme="simple"
								id="diseaseObserved" onchange="Disease_Observed(this.value)" />
						</div>
					</div>

					<div class="flexform-item nameOfDisease hide">
						<label for="txt"><s:text name="scouting.nameOfDisease" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select name="scouting.nameOfDisease" list="nameOfDisease"
								listKey="key" listValue="value" theme="simple"
								id="nameOfDisease" cssClass="form-control select2"
								multiple="true" headerKey="-1"
								headerValue="%{getText('txt.selectall')}"
								onchange="nameOfDiseaseSelectAll(this.value)" />
						</div>
					</div>


					<div class="flexform-item perInfection hide">
						<label for="txt"><s:text name="scouting.perInfection" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="scouting.perInfection" id="perInfection"
								theme="simple" size="20" cssClass="form-control input-sm"
								maxlength="20" />
						</div>
					</div>
					</div>
					<h2>
						<s:text name="info.weedsObserveds" />
					</h2>
					<div class="flexform">
					<div class="flexform-item ">
						<label for="txt"><s:text name="scouting.weedsObserveds" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:radio list="weedsObservedsMap" name="scouting.weedsObserveds"
								theme="simple" id="weedsObserveds"
								onchange="Weeds_Observed(this.value)" />
						</div>
					</div>

					<div class="flexform-item nameOfWeeds hide">
						<label for="txt"><s:text name="scouting.nameOfWeeds" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="nameOfWeeds"
								name="scouting.nameOfWeeds" listKey="key" listValue="value"
								list="nameOfWeeds" multiple="true" headerKey="-1"
								headerValue="%{getText('txt.selectall')}"
								onchange="nameOfWeedsSelectAll(this.value)" />
						</div>
					</div>

					<div class="flexform-item weedsPresence hide">
						<label for="txt"><s:text name="scouting.weedsPresence" /></label>
						<div class="form-element">
							<s:textfield name="scouting.weedsPresence" id="weedsPresence"
								theme="simple" size="20" cssClass="form-control input-sm"
								maxlength="20" />
						</div>
					</div>

					<div class="flexform-item recommendations hide">
						<label for="txt"><s:text name="scouting.recommendations" /></label>
						<div class="form-element">
							<s:textarea name="scouting.recommendations" theme="simple"
								id="recommend" maxlength="100" />
						</div>
					</div>
					</div>
					<h2>
						<s:text name="info.irrigation" />
					</h2>
					<div class="flexform">
					<div class="flexform-item">
						<label for="txt"><s:text name="scouting.sourceOfWater" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select name="scouting.sourceOfWater" list="sourceOfWater"
								headerKey="" headerValue="%{getText('txt.select')}"
								listKey="key" listValue="value" theme="simple"
								id="sourceOfWater" cssClass="form-control select2" />
						</div>
					</div>

					<%-- <div class="flexform-item">
						<label for="txt"><s:text name="scouting.irrigationType" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select name="scouting.irrigationType" list="irrigationtype"
								headerKey="" headerValue="%{getText('txt.select')}"
								listKey="key" listValue="value" theme="simple"
								id="irrigationType" cssClass="form-control select2" />
						</div>
					</div> --%>
					<div class="flexform-item">
						<label for="txt">
						<%-- <s:text name="scouting.irrigationType" /> --%>
						<s:property value="%{getLocaleProperty('scouting.irrigationType')}" />
						<sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="scouting.irrigationType" id="irrigationType"
								theme="simple" size="20" cssClass="form-control input-sm"
								maxlength="8" onkeypress="return isDecimal1(event,this)"/>
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="scouting.irrigationMethod" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select name="scouting.irrigationMethod"
								list="irrigationMethod" headerKey=""
								headerValue="%{getText('txt.select')}" listKey="key"
								listValue="value" theme="simple" id="irrigationMethod"
								cssClass="form-control select2" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt">
						<%-- <s:text name="scouting.areaIrrrigated" /> --%>
						<s:property value="%{getLocaleProperty('scouting.areaIrrrigated')}" />
						<sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="scouting.areaIrrrigated" id="areaIrrrigated"
								theme="simple" cssClass="form-control input-sm" maxlength="8" onkeypress="return isDecimal1(event,this)"/>
								<!-- onkeypress="return isNumber(event)" -->
								
						</div>
					</div>

				</div>
			</div>
		</div>

		<div class="margin-top-10">
			<!--<span class=""><span class="first-child">
					<button type="submit" id="buttonAdd2"
						onclick="event.preventDefault();" class="save-btn btn btn-sts">
						<font color="#FFFFFF"> <b> <s:text name="save" /></b>
						</font>
					</button>&nbsp;
			</span></span> -->
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