<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/plotting.jsp"%>
<link rel="stylesheet"
	href="plugins/selectize/css/selectize.bootstrap3.css">
<script src="plugins/selectize/js/standalone/selectize.min.js?v=2.0"></script>
<head>
<META name="decorator" content="swithlayout">
</head>
<style>
.datepicker-dropdown {
	width: 300px;
}
</style>
<body>
	<div class="error"></div>
	<s:form id="fileDownload" action="farm_populateDownload">
		<s:hidden id="loadId" name="idd" />
	</s:form>
	<s:form name="form" cssClass="fillform" method="post"
		enctype="multipart/form-data" id="target">
		<s:hidden key="currentPage" />
		<s:hidden id="jsonLatLonStr" name="latLonJsonString" />
		<s:hidden id="farmLatLon" name="midLatLon" />
		<s:hidden class="area" name="plotArea" />
		<s:hidden id="farmerId" name="farmerId" />
		<s:hidden id="farmerUniqueId" name="farmerUniqueId" />
		<s:hidden id="farmerName" name="farmerName" />
		<s:hidden name="tabIndex" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden key="farm.id" class="uId" name="farm.id"/>
		</s:if>
		<s:if test='"updateActPlan".equalsIgnoreCase(command)'>
			<s:hidden key="farm.id" class="uId" name="farm.id"/>
		</s:if>
		<s:hidden key="farm.farmer.id" id="farmerUniqueId"  value="%{farmerUniqueId}"/>
		<s:hidden key="command" id="command" />
		<s:hidden id="treeDetailToJson" name="treeDetailToJson" />
		<div class="appContentWrapper marginBottom farmInfo">
			<div style="float: right; color: #3CB371">
				<table class="table table-bordered aspect-detail"
					style="background-color: #3CB371;">
					<tr>
						<td style="padding-right: 5px; text-align: right;"><b><s:property
									value="%{getLocaleProperty('farmer.name')}" /></b> <s:if
								test='"update".equalsIgnoreCase(command)'>
							 <s:property
									value="farm.farmer.firstName"  />
							</s:if> <s:else>
								<s:property value="farmerName" />
							</s:else></td>
					</tr>
				</table>
			</div>
			<br>
			<div class="formContainerWrapper">
				<div class="error">
					<div id="validateError" style="text-align: left; font-color: red"></div>
					<sup>*</sup>
					<s:text name="reqd.field" />
				</div>
				<h2>
					<s:text name="info.farm" />
				</h2>
			</div>

			<div id="farmInfo" class="panel-collapse collapse in">
				<div class="flexform">
					
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.farmName')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="farm.farmName" theme="simple" maxlength="20"
								id="farmName"  cssClass="form-control" />
						</div>
					</div>
				

					<div class="flexform-item plantArea">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.totalLandHolding')}" /> <sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="farm.totalLandHolding" id="totalLandHolding"
								cssClass="form-control area" theme="simple" maxlength="10"
								onkeypress="return isDecimalfarm(event,this);"  readonly="true"/>
						</div>
					</div>
				
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.proposedPlanting')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="farm.proposedPlanting" theme="simple" maxlength="10"
								cssClass="form-control" id="proposedPlanting" onkeypress="return isDecimalfarm(event,this);"  />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.landOwnership')}" />
						</label>
						<div class="form-element">
							<s:select name="farm.landOwnership"
								list="getCatList(getLocaleProperty('landOwnership'))"
								listKey="key" listValue="value" theme="simple" maxlength="100"
								id="landOwnership" cssClass="form-control input-sm select2"
								headerKey="" headerValue="%{getText('txt.select')}" />
						</div>
					</div>
				<div class="flexform-item">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.isAddressSame')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:radio list="yesNoList" name="farm.isAddressSame" onclick="getLocH(this.value)"
								id="isAddressSame" theme="simple" size="20" />
						</div>
					</div> 
					<%-- <div class="flexform-item">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.address')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textarea id="address" name="farm.address" maxlength="50"
								cssClass="form-control input-sm">
							</s:textarea>
						</div>
					</div> --%>
					
					<div class="flexform-item">
					<label for="txt"><s:text name="country.name" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2 loc" list="countries" 							 
							theme="simple" name="selectedCountry" headerKey="" 
							headerValue="%{getText('txt.select')}" Key="id" Value="name"
							onchange="listState(this,'country','state','localities','city','panchayath','village')"
							id="country" />
					</div>
				</div>
 
				<div class="flexform-item">
					<label for="txt"><s:text name="county.name" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2 loc" list="states"
							theme="simple" name="selectedState" headerKey="" 
							headerValue="%{getText('txt.select')}" Key="id" Value="name"
							id="state"
							onchange="listLocality(this,'state','localities','city','panchayath','village')" />
					</div>
				</div>

				<div class="flexform-item">
					<label for="txt"><s:text name="locality.name" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2 loc" list="localities"
							headerKey="" theme="simple" name="selectedLocality"
							headerValue="%{getText('txt.select')}" Key="id" Value="name"
							id="localities"
							onchange="listMunicipalities(this,'localities','city','panchayath','village')" />
					</div>
				</div>

				<div class="flexform-item">
					<label for="txt"><s:text name="ward.name" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2 loc"  list="city" headerKey=""
							theme="simple" name="selectedCity"
							headerValue="%{getText('txt.select')}" Key="id" Value="name"
							id="city" onchange="listVillage(this,'city','village')" />
					</div>
				</div>
				<div class="flexform-item">
					<label for="txt"><s:text name="village.name" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2 loc" list="villages"
							headerKey="" theme="simple" name="selectedVillage"
							headerValue="%{getText('txt.select')}" id="village" />
					</div>
				</div>
					<div class="flexform-item">
						<!--<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.photo')}" />
						</label>  -->
						
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.photo')}" />&nbsp;&nbsp;<font color="red"><s:text
									name="%{getLocaleProperty('photoValidationFarm')}" /></font></label>
						
						<div class="form-element">
							<s:file id="photoFile" name="photoFile"
								onchange="validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG']);setFileName(this.value);">
							</s:file>
							<s:if test="command=='update' && farm.photo!=null">
								<button type="button" class="fa fa-download"
									onclick="popDownload(<s:property value="farm.photo"/>)"></button>
							</s:if>
							<s:hidden name="photoFileName" id="photoFileName" />
							<s:hidden name="photoFileType" id="photoFileType" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="farm.landRegNo" />
						<!-- <sup style="color: red;">*</sup> --> </label>
						<div class="form-element">
							<s:textfield name="farm.landRegNo" class="form-control input-sm"
								size="20" cssClass="form-control " maxlength="50" theme="simple" id="landRegNo" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.landTopography')}" />
						</label>
						<div class="form-element">
							<s:select name="farm.landTopography"
								list="getCatList(getLocaleProperty('landTopography'))"
								listKey="key" listValue="value" theme="simple"
								id="landTopography" maxlength="20" cssClass="form-control input-sm select2"
								headerKey="" headerValue="%{getText('txt.select')}" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.landGradient')}" />
						</label>
						<div class="form-element">
							<s:select name="farm.landGradient"
								list="getCatList(getLocaleProperty('landGradientList'))"
								listKey="key" listValue="value" theme="simple" id="landGradient"
								cssClass="form-control input-sm select2" maxlength="20" headerKey=""
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>
					
					<div class="flexform-item">
						<!--<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.landRegDocs')}" />
						</label>  -->
						
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.landRegDocs')}" />&nbsp;&nbsp;<font color="red"><s:text
									name="%{getLocaleProperty('imageValidationFarm')}" /></font></label>
						
						
						<div class="form-element">
							<s:file id="landRegDocsFile" name="landRegDocsFile"
								onchange="validateImage(this.id,['pdf','PDF']);setLandRegDocsFileName(this.value);">
							</s:file>
							<s:if test="command=='update' && farm.landRegDocs!=null">
								<button type="button" class="fa fa-download"
									onclick="popDownload(<s:property value="farm.landRegDocs"/>)"></button>
							</s:if>
							<s:hidden name="landRegDocsFileName" id="landRegDocsFileName" />
							<s:hidden name="landRegDocsFileType" id="landRegDocsFileType" />
						</div>
					</div>
					
				

				</div>
			</div>
		</div>
<div id="map"></div>


		<div class="">
			<div class="flexItem flex-layout flexItemStyle">


				<div class="margin-top-10">
					<s:if test="command =='create'">
						<span class=""><span class="first-child">
								<button type="button" id="buttonAdd1"
									class="save-btn btn btn-success">
									<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
									</font>

								</button>
						</span></span>
					</s:if>
					<s:else>
						<span class=""><span class="first-child">
								<button type="button" id="buttonUpdate"
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



	</s:form>

	<script type="text/javascript">
	var command;
	var isSameAdd;
	 function funOther(id1,hid){
	     	var e = document.getElementById(id1);
	     	var Qual= e.options[e.selectedIndex].text;
	     	if(Qual.trim()=="Others"){
	     		$("."+hid).removeClass("hide");
	     	}
	     	else{
	     		$("."+hid).addClass("hide");
	     	}
	     		
	     }
	function popDownload(type) {
		document.getElementById("loadId").value = type;
		$('#fileDownload').submit();
	}
        function onCancel() {
           // document.listForm.submit();
        	window.location.href="<s:property value='redirectContent' />";
        }
        function setAutoValues(){
        	
        }
     function isNumber(evt) {
 	    evt = (evt) ? evt : window.event;
 	    var charCode = (evt.which) ? evt.which : evt.keyCode;
 	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
 	        return false;
 	    }
 	    return true;
 	}
    
     function setFileName(fileName){
    		if (fileName != null && fileName != '') {
    			$('#photoFileName').val(fileName);
    			var estt = fileName.split('.').pop().toLowerCase();
    			$('#photoFileType').val(estt);
    			if (estt == 'PNG') {
    				$('#photoFileType').val(1);
    			} else if (estt == 'JPEG') {
    				$('#photoFileType').val(2);
    			}
    		}
    	}
    	function setLandRegDocsFileName(fileName){
    		if (fileName != null && fileName != '') {
    			$('#landRegDocsFileName').val(fileName);
    			var estt = fileName.split('.').pop().toLowerCase();
    			$('#landRegDocsFileType').val(estt);
    			if (estt == 'PNG') {
    				$('#landRegDocsFileType').val(1);
    			} else if (estt == 'JPEG') {
    				$('#landRegDocsFileType').val(2);
    			}
    		}
    	}
    	$(document).ready(function() {	  
    		command ="<s:property value='command' />";
    		
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
    	if(command=='update'){
    		var id = document.getElementsByClassName("area")[0].value;
    		if(id==''){
    			$('#totalLandHolding').val('<s:property value="farm.totalLandHolding" />');
    		}
    	}
    	
    	var url =<%out.print("'" + request.getParameter("url") + "'");%>;
 		if (url == null || url == undefined || url == ''|| url == 'null') {
 			url = 'farm_';
 		}
 		$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
 		$('#buttonAdd1,#buttonUpdate').on('click', function (event) {  
			 event.preventDefault();
		    	$("#buttonAdd1").prop('disabled', true);
				$("#buttonUpdate").prop('disabled', true);
				var hit = true;
				 $('.loc').select2({disabled:false});
				
				 $("#Selfarmer").prop('disabled', false);
				 var latLonArr=[];
				  var bounds = new google.maps.LatLngBounds();
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
	            var porpare =$('#proposedPlanting').val();
	            var to =$('#totalLandHolding').val();
	              	 if(parseFloat(porpare) > parseFloat(to)){
	     				$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.proplan')}" />');
	     				
	     		 }else
	     			
			if(!validateAndSubmit("target",url)){
				$("#buttonAdd1").prop('disabled', false);
				 $("#Selfarmer").prop('disabled', true);
				
			}
			$("#buttonAdd1").prop('disabled', false);
			$("#buttonUpdate").prop('disabled', false);
			 
			});
 		if(command=='create'){
 		getLocH("<s:property value='farm.isAddressSame' />");
 		}
 		
 		if(command=='update'){
 			if("<s:property value='farm.plottingStatus' />"=='1'){
 				loadFarmMap("<s:property value='farm.plotting.id' />",false);
 			}else{
 				loadFarmMap("<s:property value='farm.plotting.id' />",true);
 			}
 			$('input:radio[name="farm.isAddressSame"][value=' + "<s:property value='farm.isAddressSame' />" + ']').prop('checked',true);
 		}
 	
 	
 		});
    	function isDecimalfarm(evt,element) {
    		 var charCode = (evt.which) ? evt.which : event.keyCode;
    		 var vardec =0;
    		 var varLenh =$(element).val().split('.')[0].length;
    		 if($(element).val().indexOf(".")>=0){
    			 vardec =$(element).val().split('.')[1].length;
    		 }
    			        if (
    			            (charCode != 46 ||  $(element).val().indexOf('.') != -1  ) &&    // Check for dots and only once.
    			            (charCode < 48 || charCode > 57)){
    			        	return false;
    			        }else if(charCode != 46){
    			        	varLenh=varLenh+1;
    			        	vardec=vardec+1;
    			        }
    		 if((varLenh > 10 && $(element).val().indexOf(".")<0) || ($(element).val().indexOf(".")>=0 &&  vardec >10) ){
    			 return false;
    		 }
    			        return true;
    	} 
      function getLocH(thisva){
    	  isSameAdd=thisva;
    	 if(thisva=='1'){
    		$('#country').val('<s:property value="farm.farmer.village.city.locality.state.country.name"/>').select2().change();
    		$('.loc').select2({disabled:'readonly'});
    	 }else if(thisva=='0'){
    		 $('#country').val("").select2().change();
    		 if(command=='update'){
    			//$('#country').val('<s:property value="farm.farmer.village.city.locality.state.country.name"/>').select2().change();
    			// $('#village').val('<s:property value="farm.farmer.village.id"/>').change();
    		 }
    		 $('.loc').select2({disabled:false});
    	 }else{
    		 $('#country').val('<s:property value="farm.farmer.village.city.locality.state.country.name"/>').select2().change();
    		 $('.loc').select2({disabled:false});
    		 
    		 //$('#country').val('').select2().change();
    	 }
    	 
     } 
     
     function listState(obj, countryId, stateId, disId, cityId, gpId, vId) {
    	/*  $('#'+ stateId+' option:not(:first)').remove();
    	 $('#'+ stateId).val(' ').select2(); 
    	 $('#'+ disId+' option:not(:first)').remove();
		 $('#'+ disId).val(' ').select2(); 
		  $('#'+ cityId+' option:not(:first)').remove();
		 $('#'+ cityId).val(' ').select2(); 
		  $('#'+ vId+' option:not(:first)').remove();
		 $('#'+ vId).val(' ').select2(); */
		 
		 clearElement(stateId,true);
		 clearElement(disId,true);
		 clearElement(cityId,true);
		 clearElement(vId,true);
		 
		 
		 
    		if (!isEmpty($(obj).val())) {
    			var selectedCountry = $('#' + countryId).val();
    			jQuery.post("farm_populateState.action", {
    				selectedCountry : $(obj).val()
    			}, function(result) {
    				insertOptions(stateId, $.parseJSON(result));			
    				if('<s:property value="farm.farmer.village.city.locality.state.id"/>'!=null && '<s:property value="farm.farmer.village.city.locality.state.id"/>'!=''){
    				/* if(selectedCountry == '<s:property value="farm.farmer.village.city.locality.state.country.name"/>' && isSameAdd=='1'){
    					$('#'+stateId).val('<s:property value="farm.farmer.village.city.locality.state.id"/>').select2();
    				} */
    				if(selectedCountry == '<s:property value="farm.farmer.village.city.locality.state.country.name"/>' && isSameAdd=='1'){
    					$('#'+stateId).val('<s:property value="farm.farmer.village.city.locality.state.id"/>').select2();
    				}else if(command=='update' && isSameAdd=='1'){
    					$('#'+stateId).val('<s:property value="farm.farmer.village.city.locality.state.id"/>').select2();
    				}
    				}
    				listLocality(document.getElementById(stateId), stateId, disId,
    						cityId, gpId, vId);
    			});
    			$('#' + disId).select2();
    		}
    	}
    	function listLocality(obj, stateId, disId, cityId, gpId, vId) {

    		/*  $('#'+ disId+' option:not(:first)').remove();
    		 $('#'+ disId).val(' ').select2();
    		 $('#'+ cityId+' option:not(:first)').remove();
    		 $('#'+ cityId).val(' ').select2();
    		 $('#'+ vId+' option:not(:first)').remove();
    		 $('#'+ vId).val(' ').select2(); */
    		 
    		 clearElement(disId,true);
    		 clearElement(cityId,true);
    		 clearElement(vId,true);
    		 
    		 
     		if (!isEmpty($(obj).val())) {
     	
    			var selectedState = $('#' + stateId).val();
    			jQuery.post("farm_populateLocality.action", {
    				id1 : $(obj).val(),
    				dt : new Date(),
    				selectedState : $(obj).val()
    			}, function(result) {
    				insertOptions(disId, $.parseJSON(result));
    				if('<s:property value="farm.farmer.village.city.locality.id"/>'!=null && '<s:property value="farm.farmer.village.city.locality.id"/>'!=''){
    					/* if(selectedState == '<s:property value="farm.farmer.village.city.locality.state.id"/>'){
    					$('#'+disId).val('<s:property value="farm.farmer.village.city.locality.id"/>').select2();
    					} */
    					 if(selectedState == '<s:property value="farm.farmer.village.city.locality.state.id"/>'  && isSameAdd=='1'){
    					   $('#'+disId).val('<s:property value="farm.farmer.village.city.locality.id"/>').select2();
    					}else if(command=='update' && isSameAdd=='1'){
    						 $('#'+disId).val('<s:property value="farm.farmer.village.city.locality.id"/>').select2();
    					} 
    				}
    					listMunicipalities(document.getElementById(disId), disId, cityId,gpId, vId);
    			});
    			$('#' + cityId).select2();
    		}
    	}

    	function listMunicipalities(obj,disId, cityId, gpId, vId) {
    		/*  $('#'+ cityId+' option:not(:first)').remove();
    		 $('#'+ cityId).val(' ').select2();
    		 $('#'+ vId+' option:not(:first)').remove();
    		 $('#'+ vId).val(' ').select2(); */
    		 
    		 clearElement(cityId,true);
    		 clearElement(vId,true);
    		 
    		 
      		if (!isEmpty($(obj).val())) {
      	
    			var selectedLocality = $('#' + disId).val();
    			jQuery.post("farm_populateMunicipality.action", {
    				id1 : $(obj).val(),
    				dt : new Date(),
    				selectedLocality : $(obj).val()
    			}, function(result) {
    				insertOptions(cityId, $.parseJSON(result));
    				
    				if('<s:property value="farm.farmer.village.city.id"/>'!=null && '<s:property value="farm.farmer.village.city.id"/>'!=''){
    					/* if(selectedLocality == '<s:property value="farm.farmer.village.city.locality.id"/>'){
    					$('#'+cityId).val('<s:property value="farm.farmer.village.city.id"/>').select2();
    					} */
    					 if(selectedLocality == '<s:property value="farm.farmer.village.city.locality.id"/>'  && isSameAdd=='1'){
    					$('#'+cityId).val('<s:property value="farm.farmer.village.city.id"/>').select2();
    					}else if(command=='update' && isSameAdd=='1'){
    					$('#'+cityId).val('<s:property value="farm.farmer.village.city.id"/>').select2();
    					} 
    				}
    				listVillage(document.getElementById(cityId), cityId, vId);
    			});
    			$('#' + vId).select2();
    		}
    	}
    	function listMunicipality(obj,disId, cityId, gpId, vId) {

    		/*  $('#'+ cityId+' option:not(:first)').remove();
    		 $('#'+ cityId).val(' ').select2();
    		 $('#'+ vId+' option:not(:first)').remove();
    		 $('#'+ vId).val(' ').select2(); */
    		 
    		 clearElement(cityId,true);
    		 clearElement(vId,true);
    		 
    		 
       		if (!isEmpty($(obj).val())) {
       		var selectedLocality = $('#' + disId).val();
    			jQuery.post("farm_populateMunicipality.action", {
    				id1 : $(obj).val(),
    				dt : new Date(),
    				selectedLocality : $(obj).val()
    			}, function(result) {
    				insertOptions(cityId, $.parseJSON(result));
    				
    				if('<s:property value="farm.farmer.village.city.id"/>'!=null && '<s:property value="farm.farmer.village.city.id"/>'!=''){
    					/* if(selectedLocality == '<s:property value="farm.farmer.village.city.locality.id"/>'){
    					$('#'+cityId).val('<s:property value="farm.farmer.village.city.id"/>').select2();
    					} */
    					 if(selectedLocality == '<s:property value="farm.farmer.village.city.locality.id"/>'  && isSameAdd=='1'){
    					$('#'+cityId).val('<s:property value="farm.farmer.village.city.id"/>').select2();
    					}else if(command=='update' && isSameAdd=='1'){
    					$('#'+cityId).val('<s:property value="farm.farmer.village.city.id"/>').select2();
    					} 
    				}
    				listVillage(document.getElementById(cityId), cityId, vId);
    			});
    			$('#' + vId).select2();
    		}
    	}
    	function listVillage(obj, CityId, vId) {
    		/*  $('#'+ vId+' option:not(:first)').remove();
    		 $('#'+ vId).val(' ').select2(); */
    		
    		 clearElement(vId,true);
    		 
        		if (!isEmpty($(obj).val())) {
    			var selectedCity = $('#' + CityId).val();
    			jQuery.post("farm_populateVillage.action", {
    				id1 : $(obj).val(),
    				dt : new Date(),
    				selectedCity : $(obj).val()
    			}, function(result) {
    				insertOptions(vId, $.parseJSON(result));
    				
    				if('<s:property value="farm.farmer.village.id"/>'!=null && '<s:property value="farm.farmer.village.id"/>'!=''){
    					/* if(selectedCity == '<s:property value="farm.farmer.village.city.id"/>'){
    					$('#'+vId).val('<s:property value="farm.farmer.village.id"/>').select2();
    					} */
    					if(selectedCity == '<s:property value="farm.farmer.village.city.id"/>'  && isSameAdd=='1'){
    					$('#'+vId).val('<s:property value="farm.farmer.village.id"/>').select2();
    					}else if(command=='update' && isSameAdd=='1'){
    					$('#'+vId).val('<s:property value="farm.farmer.village.id"/>').select2();
    					}
    				}
    				
    			});
    		
    		}
    	}

  </script>
</body>
