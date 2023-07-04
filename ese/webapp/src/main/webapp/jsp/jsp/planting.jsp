<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/plotting.jsp"%>

<link rel="stylesheet"
	href="plugins/selectize/css/selectize.bootstrap3.css">
<script src="plugins/selectize/js/standalone/selectize.min.js?v=2.0"></script>

<script>	
	var tenant = '<s:property value="getCurrentTenantId()"/>';
	var command = '<s:property value="command"/>';
	var cropva = '<s:property value="cropva"/>';
	var cropgra = '<s:property value="cropgra"/>';
	var flightPath ;
	jQuery(document).ready(function() {
		if (navigator.geolocation) {
		      navigator.geolocation.getCurrentPosition(
		        (position) => {
		          const pos = {
		            lat: position.coords.latitude,
		            lng: position.coords.longitude,
		          }
		          $("#latitude").val(pos.lat);
		          $("#longitude").val(pos.lng);
		        },
		        () => {
		          handleLocationError(true, infoWindow, map.getCenter());
		        }
		      );
		    } else {
		      // Browser doesn't support Geolocation
		      handleLocationError(false, infoWindow, map.getCenter());
		    }
		
						$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
						$(".breadCrumbNavigation").find('li:nth-last-child(2)').find('a:first').attr("href",'<s:property value="redirectContent" />');
						
						$(".hideseedcheqty").hide();
						$("#buttonAdd1").on('click', function (event) {  
							$("#buttonAdd1").prop('disabled', true);
							var latLonArr=[];
						     $(coorArr).each(function(k, v) {
				                var latLon = v.split(",");

				                if (!isEmpty(latLon[0]) && !isEmpty(latLon[1])) {
				                  latLonArr.push({
				                    
				                    'latitude': latLon[0],
				                    'longitude': latLon[1],
				                    'orderNo': k + 1,

				                  });
				                }

				              });
				              $('#jsonLatLonStr').val(JSON.stringify(latLonArr));
							if(!validateAndSubmit("target","planting_")){
								$("#buttonAdd1").prop('disabled', false);
							}
							var f1=$('input:radio[name="farmer.house"]:checked').val();
							 var className = $('#button1').attr('class').split(' ')[1];	
							fun1(f1,className);					
							 
						});
						 if(command=='update'){
								
							 
							 
							 var crop='<s:property value="planting.variety.procurementProduct.id"/>';
							 var variety='<s:property value="planting.variety.id"/>';
							 var Grade='<s:property value="planting.grade.id"/>';
							 var farmerNam='<s:property value="planting.farmCrops.farm.farmer.id"/>';
							 var farmNam='<s:property value="planting.farmCrops.farm.id"/>';
							 var farmCropNam='<s:property value="planting.farmCrops.id"/>';
                   	$('#expHarvestQuantity').html('<s:property value="planting.expHarvestQty"/>');
						       	$('#expHarvestQuantity1').val('<s:property value="planting.expHarvestQty"/>');
						       
							 $("#gradeName option[value='"+ Grade.trim() + "']").prop("selected", true).select2().trigger('change');
							 listFarm('<s:property value="farmerId"/>');
							 $("#selectedFarmName option[value='"+ farmNam.trim() + "']").prop("selected", true).trigger('change');
						var farmerId='<s:property value="planting.farmCrops.farm.farmer"/>';	
						$("#selectedFarmCropName option[value='"+ farmCropNam.trim() + "']").prop("selected", true).select2().trigger('change');
				        $("#varietyName option[value='"+ variety.trim() + "']").prop("selected", true).select2().trigger('change');
						var blockArea='<s:property value="planting.cultiArea"/>';	
						//alert("blockArea:-"+blockArea);
						  $("#carea").val(blockArea);
							if("<s:property value='planting.plottingStatus' />"=='1'){
					 				loadFarmMap("<s:property value='planting.plotting.id' />",false);
					 			}else{
					 				loadFarmMap("<s:property value='planting.plotting.id' />",true);
					 			}
						
						
						 }else{
							 listFarm('<s:property value="farmerId"/>');
						 }
						 var grower=$('#cropName').val();
							if(grower!=null && grower!='' && grower!=undefined){
								loadScienNo(grower);
							}
							 var checmused='<s:property value="planting.chemUsed"/>';
							 hidechemical(checmused);
							 hidefields();
});

	function onCancel() {
       // document.listForm.submit();
       //alert(<s:property value='redirectContent'/>);
		window.location.href="<s:property value='redirectContent' />";
    }
	
	
	function listGrade(call) {
			var selectedVariety = call;
			var exporterGradeIds=$('#selectedFarmCropName').val();
			
			clearElement('gradeName',true);
			
		if(!isEmpty(selectedVariety)){
				$.ajax({
					 type: "POST",
			        async: false,
			        url: "planting_populateGrade.action",
			        data: {selectedVariety : selectedVariety,
			        	exporterGradeId:exporterGradeIds
			        },
			        success: function(result) {
			        	insertOptions("gradeName", $.parseJSON(result));
			        	if("<s:property value='planting.grade.id'/>"!=''){
			        		 var grade1='<s:property value="planting.grade.id"/>';
			        		$("#gradeName option[value='"+ grade1.trim() + "']").prop("selected", true).select2().trigger('change');
			        	}
			        }
				});
			}
			
			//resetSelect2();
		}
		
	function isDecimal(evt) {
		evt = (evt) ? evt : window.event;
		var charCode = (evt.which) ? evt.which : evt.keyCode;
		if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46) {
			return false;
		}
		return true;
	}
	
	


function isNumber1(evt) {

	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 49 || charCode > 57)) {
		return false;
	}
	return true;
}

function isAlphaNumber(evt) {

	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if ((charCode > 47 && charCode < 58) || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123)) {
		return true;
	}
	return false;
}



function loadScienNo(val){
	var selGrower=val;
  	$('#seleScientificName').html('');
	if(selGrower!=''){
	$.ajax({
		 type: "POST",
       async: false,
       url: "planting_populateScientificName.action",
       data: {selGrower:selGrower},
       success: function(result) {
       	$('#seleScientificName').html(result.sciName);
       }
	});
	}
}

function listFarm(val){
	 if(!isEmpty(val)){
		$.ajax({
			 type: "POST",
	        async: false,
	        url: "planting_populateFarm.action",
	        data: {selectedFarm : val},
	        success: function(result) {
	        	insertOptions("selectedFarmName", $.parseJSON(result));
	        	$('#selectedFarmCropName').trigger('change');
	        }
		});
	} 
}

function loadFarmCrop(val){
	clearElement('varietyName',true);
	 if(!isEmpty(val)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "planting_populateCropNames.action",
		        data: {selectedFarmCrops : val},
		        success: function(result) {
		        	insertOptions("varietyName", $.parseJSON(result));
		        	$('#varietyName').trigger('change');
		        }
			});
		} 
	}

 function hidechemical(val){
	if(val == '1'){
		$(".hideseedcheqty").show();
	}else{
		$(".hideseedcheqty").hide();
	}
}

 
 function hidefields(){
	 
	 var val=$('#fertilizerUsed').val();
	// alert("At 171  "+val);
	 if(val=='0' || val=='-1'){
			$('.h8').addClass('hide'); 
			$('.h2').addClass('hide'); 
			$('.h3').addClass('hide'); 
			$('.h48').addClass('hide'); 
			$('.hh5').addClass('hide'); 
			$('.h6').addClass('hide'); 
			$('.h7').addClass('hide'); 
			 
	 }else{
		//    $('.h8').addClass('hide'); 
		    $('.h8').removeClass('hide'); 
			$('.h2').removeClass('hide'); 
			$('.h3').removeClass('hide'); 
			$('.h48').removeClass('hide'); 
			$('.hh5').removeClass('hide'); 
			$('.h6').removeClass('hide'); 
			$('.h7').removeClass('hide'); 
			 
		 
	 }
	 
	 
 }
function setAutoValues(){
	  var grafe =$('#gradeName').val();
	  $("#Exp_week").val('');
      $("#plantingweeks").val('');
      $("#expHarvestQuantity").html('');
      $("#expHarvestQuantity1").val('');
      var plantinDare =$('#calendar1').val();
      if(plantinDare!=null && plantinDare!=''){
	   var plll = moment(plantinDare, "DD-MM-YYYY");
	   var plllweek = (plll.toDate()).getWeek();

      $("#plantingweeks").val("Week "+ plllweek+", "+ plll.year());
      }
	  if(grafe!=''){
	  $.ajax({
			 type: "POST",
	       async: false,
	       url: "planting_populateGradeData",
	       data: {selGrade:grafe},
	       success: function(result) {
	    	   var cc = result.cc;
	    	   var yield =result.yield;
	    	   var hdays =result.hDays;
	    	   var cltirea =$('#carea').val();
	    	   if(cltirea!=''){
	    		 	$('#expHarvestQuantity').html((parseFloat(yield)*parseFloat(cltirea)).toFixed(3));
	    	       	$('#expHarvestQuantity1').val((parseFloat(yield)*parseFloat(cltirea)).toFixed(3));
	    	   }
	    	 
	    	   if(plantinDare!=null && plantinDare!=''){
	    		   var mmo =moment(plantinDare, "DD-MM-YYYY").add('days',cc);
	    		 
	    		   var mmoweek = (mmo.toDate()).getWeek();
	    	
	    		    
	    		      $("#Exp_week").val("Week "+ mmoweek+", "+ mmo.year());
	    		      

	    	   }
		    }
		});
	  }else{
		  $("#Exp_week").val("");
	 
	  }
	  
}
function isDecimalfarmcrop(evt,element) {
	 var charCode = (evt.which) ? evt.which : event.keyCode;
	 var vardec =0;
	 var varLenh =$(element).val().split('.')[0].length;
	 if($(element).val().indexOf(".")>=0){
		 vardec =$(element).val().split('.')[1].length;
	 }
		        if (
		              // Check minus and only once.
		            (charCode != 46 ||  $(element).val().indexOf('.') != -1  ) &&    // Check for dots and only once.
		            (charCode < 48 || charCode > 57)){
		        	return false;
		        	
		        }else if(charCode != 46){
		        	varLenh=varLenh+1;
		        	vardec=vardec+1;
		        }
	 if((varLenh > 20 && $(element).val().indexOf(".")<0) || ($(element).val().indexOf(".")>=0 &&  vardec >10) ){
		 return false;
	 }
		        return true;
} 

function lettersNumbersCheck(name)
{
   var regEx = /^[0-9a-zA-Z]+$/;
   if(name.value.match(regEx))
     {
      return true;
     }
   else
     {
     return false;
     }
} 

function resetfield(){
	 $("#varietyName").val("").select2();
}

function boundMap(farmId){
	
	if(flightPath!=null){
		flightPath.setMap(null);
	}
	
	$.ajax({
		 type: "POST",
       async: false,
       url: "planting_populateFarmBound.action",
       data: {selectedFarm : farmId
       },
       success: function(resp) {
    	   var result = JSON.parse(resp);
    	
    	   var jssarr =[];
    	   if(result!=null && result.length>0 ){
    			var bounds = new google.maps.LatLngBounds();
    			kmmll=[];
    	      $(result).each(function(k, v) {
    	    	   var jss ={};
    			   var coordinatesLatLng = new google.maps.LatLng(
							parseFloat(v.latitude), parseFloat(v.longitude));
    			   kmmll.push(coordinatesLatLng);
    				bounds
    				.extend(coordinatesLatLng);
    				jss['lat']=v.latitude;
    				jss['lon']=v.longitude;
    				jssarr.push(jss);
    			   });
    	      kenyaPluy = new google.maps.Polygon({ paths: kmmll });
    	      var cords=[];
    	      $(jssarr).each(
  					function(k, v) {
  						cords.push({
  							lat : parseFloat(v.lat),
  							lng : parseFloat(v.lon)
  						});
  						var coordinatesLatLng = new google.maps.LatLng(
  								parseFloat(v.lat), parseFloat(v.lon));
  						//coorArr.push(v.lat + "," + v.lon);
  						bounds.extend(coordinatesLatLng);
  						map.setCenter({
  							lat : parseFloat(v.lat),
  							lng : parseFloat(v.lon)
  						});
  						map.setZoom(17);

  					});
cords.push(cords[0] );

  			 flightPath = new google.maps.Polyline({
    path: cords,
    geodesic: true,
    strokeColor: "#FF0000",
    strokeOpacity: 1.0,
    strokeWeight: 2,
  });

  flightPath.setMap(map);
map.fitBounds(bounds);
    	
    		   
    	   }else{
    		   initMap();
    	   }
       
       }
	});
	
}

function loadPlating(val){
	//clearElement("selectedFarmCropName",true);	
	 if(!isEmpty(val)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "planting_populateFarmCropsByFarm.action",
		        data: {selectedFarmCrops : val},
		        success: function(result) {
		        	insertOptions("selectedFarmCropName", $.parseJSON(result));
		        	
		        }
			});
		}else{
			clearElement("selectedFarmCropName",true);
		} 
}

</script>

<head>
<meta name="decorator" content="swithlayout">
</head>
<body>

	<%-- <s:form name="listForm" id="listForm" action="farmer_detail.action">
		<s:hidden name="farmerId" value="%{farmerId}" />
		<s:hidden name="id" value="%{farmerUniqueId}" />
		<s:hidden name="tabIndexFarmer" />
		<s:hidden name="tabIndex" value="%{tabIndexFarmerZ}" />
		<s:hidden name="currentPage" />
	</s:form> --%>
	<s:form name="form" cssClass="fillform" action="planting_%{command}"
		method="post" id="target">

		<s:hidden id="jsonLatLonStr" name="latLonJsonString" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:hidden class="area" name="plotArea" />
		<s:hidden name="planting.farmCrops.farm.farmer.id" id="Selfarmer"
			value="%{farmerId}"></s:hidden>
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="planting.id" />
			<s:hidden name="farmerId" id="farmerId" />
		</s:if>
		<s:hidden key="command" />
		<div class="appContentWrapper marginBottom">
			<div class="error">
				<s:actionerror />
				<s:fielderror />
				<s:if test="hasActionErrors()">
					<div style="color: red;">
						<s:text name="cannotDeletePremisesHasTxn" />
						<s:actionerror />

					</div>
				</s:if>
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:property value="%{getLocaleProperty('info.planting')}" />
				</h2>
				<div class="flexform">

					<s:hidden name="farmCrops.farmerId" value="%{farmerId}" />


					<div class="flexform-item">
						<label for="txt"><s:text name="profile.farm" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="selectedFarmName"
								name="planting.farm.id" listKey="key" listValue="value"
								onchange="loadPlating(this.value)" list="{}" headerKey=" "
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text name="blockName" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="selectedFarmCropName"
								name="planting.farmCrops.id" listKey="key" listValue="value"
								list="{}" headerKey=""
								headerValue="%{getText('txt.select')}" onchange="loadFarmCrop(this.value);boundMap(this.value);"/>
						</div>
					</div>


					<%-- 
					<div class="flexform-item">
						<label for="txt"><s:text name="blockIds" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="farmCrops.blockId" maxlength="10"
								theme="simple" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="cropCommonName" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="cropName"
								name="selectedProduct" listKey="id" listValue="name"
								list="productList" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="listProVarierty(this.value);loadScienNo(this.value);" />
						</div>
					</div> --%>



					<%-- 
					<div class="flexform-item">
						<label for="txt"><s:text name="cropScientName" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<span id="seleScientificName"></span>
						</div>
					</div> --%>



					<div class="flexform-item">
						<label for="txt"><s:text name="cropPlanted" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="varietyName"
								name="selectedVariety" listKey="key" listValue="value"
								list="{}" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="listGrade(this.value)" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="cropVarity" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="gradeName"
								name="selectedGradename" listKey="key" listValue="value"
								onchange="setAutoValues()" list="{}" headerKey=""
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"> <s:text name="farmer.plantDate" /> <sup
							id="mandatory" style="color: red;">*</sup>

						</label>
						<div class="form-element">
							<s:textfield readonly="true" name="plantingDate" id="calendar1"
								theme="simple"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="date-picker form-control input-sm"
								onchange="setAutoValues()" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('plantingmaterial')}" /> <sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:select class="form-control  select2" id="seedSource"
								name="planting.seedSource" listKey="key" listValue="value"
								list="getCatList(getLocaleProperty('seedSourceTypes'))"
								headerKey="" headerValue="%{getText('txt.select')}" />
						</div>
					</div>
					
					<div class="flexform-item">
					<label for="txt">
						<s:property value="%{getLocaleProperty('planting.fieldType')}" />
						 <sup style="color: red;">*</sup> 
						</label>
						<div class="form-element">
							<s:select name="planting.fieldType" list="getCatList(getLocaleProperty('fieldType'))"
								headerKey="" headerValue="%{getText('txt.select')}"
								listKey="key" listValue="value" theme="simple"
								id="sourceOfWater" cssClass="form-control select2" />
						</div>
					</div>



				</div>

				<h2>
					<s:property value="%{getLocaleProperty('info.seed')}" />
				</h2>
				<div class="flexform">

					


					

				<div class="flexform-item">
						<label for="txt"><s:text name="plantingArea" /> <sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="planting.cultiArea" maxlength="20" id="carea"
								onkeypress="return isDecimalfarmcrop(event,this)"
								cssClass="form-control area" onkeyup="setAutoValues()"
								theme="simple" readonly="true"/>
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text name="seedlotnum" /></label>
						<div class="form-element">
							<s:textfield name="planting.lotNo" maxlength="20"
								onkeypress="return lettersNumbersCheck(this.val);"
								theme="simple" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="bordercrop" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="cropCategory"
								name="planting.cropCategory" listKey="key" listValue="value"
								list="getCatList(getLocaleProperty('bordercropType'))"
								headerKey=" " headerValue="%{getText('txt.select')}" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="seedtreatment" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="seedTreatment"
								name="planting.chemUsed" listKey="key" listValue="value"
								list="yesNoMap" headerKey=" "
								headerValue="%{getText('txt.select')}"
								onchange="hidechemical(this.value)" />
						</div>
					</div>


					<div class="flexform-item hideseedcheqty">
						<label for="txt"><s:text name="seedcheQuty" /></label>
						<div class="form-element">
							<s:textfield name="planting.chemQty" theme="simple" size="20"
								onkeypress="return isDecimalWithDec(event,this,20,10);" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="seedqtyplanted" /></label>
						<div class="form-element">
							<s:textfield name="planting.seedQtyPlanted" theme="simple"
								size="20"
								onkeypress="return isDecimalWithDec(event,this,20,10);" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="plantingweek" /></label>
						<div class="form-element">
							<s:textfield name="planting.seedWeek" theme="simple"
								id="plantingweeks" size="20" readonly="true" />
						</div>
					</div>
					<div class="flexform-item" style="margin-top: 0px;">
						<label for="txt"><s:text name="expweekharv" /></label>
						<div class="form-element">
							<s:textfield name="planting.expHarvestWeek" theme="simple"
								readonly="true" size="20" id="Exp_week" />
						</div>
					</div>

					<!--<div class="flexform-item hide h7" style="padding:7px;">
						<label for="txt"><s:text name="expqtyharinkgs" /></label>
						<div class="form-element">
							<span id="expHarvestQuantity"></span>
							<s:hidden name="farmCrops.expHarvestQty" id="expHarvestQuantity1"
								theme="simple" />
						</div>
					</div>  -->
					<div class="flexform-item">
						<label for="txt"><s:text name="expqtyharinkgs" /></label>
						<div class="form-element">
							<span id="expHarvestQuantity"></span>
							<s:hidden name="planting.expHarvestQty" id="expHarvestQuantity1"
								theme="simple" />
						</div>
					</div>
				</div>

				<h2>
					<s:property value="%{getLocaleProperty('info.fertilizer')}" />
				</h2>
				<div class="flexform">

					<div class="flexform-item">
						<label for="txt"><s:text name="fertilizerused" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="fertilizerUsed"
								name="planting.fertiliser" listKey="key" listValue="value"
								list="yesNoMap" headerKey="-1"
								headerValue="%{getText('txt.select')}" onchange="hidefields()" />
						</div>
					</div>

					<div class="flexform-item hide h8" style="margin-top: 0px;">
						<label for="txt"><s:text name="typeoffertizer" /></label>
						<div class="form-element">
							<s:select class="form-control  select2 " id="typeOfFertilizer"
								name="planting.typeOfFert" listKey="key" listValue="value"
								list="getCatList(getLocaleProperty('typeoffertizertyp'))"
								headerKey=" " headerValue="%{getText('txt.select')}" />
						</div>
					</div>

					<div class="flexform-item hide h2" style="margin-top: 0px;">
						<label for="txt"><s:text name="fertilizerlotnumber" /></label>
						<div class="form-element">
							<s:textfield name="planting.fLotNo" theme="simple" size="20"
								onkeypress="return isAlphaNumber(event,this);" />
						</div>
					</div>

					<div class="flexform-item hide h3" style="margin-top: 0px;">
						<label for="txt"><s:text name="fertilizerQTYused" /></label>
						<div class="form-element">
							<s:textfield name="planting.fertQty" theme="simple" size="20"
								onkeypress="return isDecimalWithDec(event,this,20,10);" />
						</div>
					</div>


					<div class="flexform-item hide h48" style="margin-top: 0px;">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('product.unit')}" /></label>
						<div class="form-element">
							<s:select name="planting.unit"
								list="getCatList(getLocaleProperty('umotypes'))" listKey="key"
								listValue="value" headerKey=""
								headerValue="%{getText('txt.select')}" theme="simple"
								cssClass="col-sm-4 form-control select2" />
						</div>
					</div>

					<div class="flexform-item hide hh5" style="margin-top: 0px;">
						<label for="txt"><s:text name="modeofapp" /></label>
						<div class="form-element">
							<s:select name="planting.modeApp"
								list="getCatList(getLocaleProperty('modeofapptype'))"
								listKey="key" listValue="value" headerKey=""
								headerValue="%{getText('txt.select')}" theme="simple"
								cssClass="col-sm-4 form-control select2" />
						</div>
					</div>

					<!-- <div class="flexform-item hide h6" style="margin-top:0px;">
						<label for="txt"><s:text name="expweekharv" /></label>
						<div class="form-element">
							<s:textfield name="farmCrops.expHarvestWeek" theme="simple"
								readonly="true" size="20" id="Exp_week" />
						</div>
					</div> -->

				</div>

				<div id="map" style="margin-top: 7px"></div>


				<div class="margin-top-10 ">
					<span class=""><span class="first-child">
							<button type="button" id="buttonAdd1"
								class="save-btn btn btn-success">
								<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
								</font>
							</button>
					</span></span><span class=""><span class="first-child"><button
								type="button" id="canBt" class="cancel-btn btn btn-sts"
								onclick="onCancel();">
								<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
								</font></b>
							</button></span></span>

				</div>

			</div>
		</div>
	</s:form>

	<!-- <s:form name="listForm" id="listForm" action="farmer_detail.action">
		<s:hidden name="farmerId" value="%{farmerId}" />
		<s:hidden name="id" value="%{farmerUniqueId}" />
		<s:hidden name="tabIndexFarmer" />
		<s:hidden name="tabIndex" value="%{tabIndexFarmerZ}" />
		<s:hidden name="currentPage" />
	</s:form> -->

	<s:form name="cancelform" action="farmer_detail.action%{tabIndexz}">
		<s:hidden name="farmId" value="%{farmId}" />
		<s:hidden name="farmerId" value="%{farmerId}" />
		<s:hidden name="sangham" value="%{planting.farmCrops.farm.farmer.sangham}" />
		<s:hidden name="tabIndex" value="#tabs-3" />
		<s:hidden key="id" value="%{planting.farm.farmer.id}" />
		<s:hidden key="currentPage" />
	</s:form>



</body>
