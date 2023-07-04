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

<%-- <script src="js/farmer.js?v=4.28"></script>
<script src="js/dynamicComponents.js?v=20.31"></script> --%>
<script type="text/javascript">

	function onCancel() {
		window.location.href = "<s:property value="redirectContent" />";
	}
	var url =<%out.print("'" + request.getParameter("url") + "'");%>;
	var layOut='<%=request.getParameter("layoutType")%>	';
	var status = '<s:property value="expRegis.status"/>';
	var command = '<s:property value="command"/>';
	var command = '<s:property value="command"/>';
	$(document)
			.ready(
					function() {
						$('#sessionid').val('<%=session.getId()%>');
					
						$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
						url =<%out.print("'" + request.getParameter("url") + "'");%>;
						if (url == null || url == undefined || url == ''
								|| url == 'null') {
							url = 'exporterRegistration_';
						}
						
						var tenant = '<s:property value="getCurrentTenantId()"/>';
						var branchId = '<s:property value="getBranchId()"/>';
					/* 	if(command=='update'){
							var whid=$.trim('<s:property value="expRegis.wareHouses"/>');
							$('#wareHouses').val(JSON.parse(JSON.stringify(whid.replace(/\s/g,'').split(",")))).select2();
							} */
						if(command=='update'){
							var selectedExporterStatusFlag = '<s:property value="selectedExporterStatusFlag"/>';
							$('#selectedExporterStatusFlag').val(selectedExporterStatusFlag);
							var exportersStatus = '<s:property value="selectedExporterStatus"/>';
							$('#exporterStatus > option[value='+exportersStatus+']').prop("selected","selected");
							 $("#exporterStatus").select2();
							if(exportersStatus=='2'){
								$("#exporterStatus").prop('disabled', true);
							}} 
						$("#bAdd,#bUpdate").on(
								'click',
								function(event) {
									event.preventDefault();
									$("#buttonAdd1").prop('disabled', true);
									
									$("#esIds").val($("#exporterStatus").val());
							
									if (!validateAndSubmit("target", url)) {
										$("#buttonAdd1")
												.prop('disabled', false);
										$("#buttonUpdate").prop('disabled',
												false);
									}
								});
						if (layOut == 'publiclayout') {
							$("#canBt").addClass('hide');
						}
						
						if (navigator.geolocation) {
						      navigator.geolocation.getCurrentPosition(
						        (position) => {
						          const pos = {
						            lat: position.coords.latitude,
						            lng: position.coords.longitude,
						          }
						          $("#lat").val(pos.lat+","+pos.lng);
						        },
						        () => {
						          handleLocationError(true, infoWindow, map.getCenter());
						        }
						      );
						    } else {
						      // Browser doesn't support Geolocation
						      handleLocationError(false, infoWindow, map.getCenter());
						    }
						
						var cropcat=$.trim('<s:property value="expRegis.ugandaExport"/>');
						$('#ugandaExport').val(JSON.parse(JSON.stringify(cropcat.replace(/\s/g,'').split(",")))).select2();
						$('#ugandaExport').change();
						
						var cropname=$.trim('<s:property value="expRegis.farmerHaveFarms"/>');
						$('#farmerHaveFarms').val(JSON.parse(JSON.stringify(cropname.replace(/\s/g,'').split(",")))).select2();
						$('#farmerHaveFarms').change();
						
						var cropvar=$.trim('<s:property value="expRegis.scattered"/>');
						$('#scattered').val(JSON.parse(JSON.stringify(cropvar.replace(/\s/g,'').split(",")))).select2();
						$('#scattered').change();
						loadDependancy();
						
						
					});
	
	
	function setRegProof(val) {
		//alert("dat1"+val);
		$('#proofFile1').val(val);
	}
	
	 
	
	function getLocation() {
		  if (navigator.geolocation) {
		    navigator.geolocation.getCurrentPosition(showPosition);
		  } else {
		    //alert("Geolocation is not supported by this browser.");
		  }
		}
		function showPosition(position) {
		  var lat = position.coords.latitude;
		  var lng = position.coords.longitude;
		  map.setCenter(new google.maps.LatLng(lat, lng));
		}

	
		function popDownload(type) {
			document.getElementById("loadId").value = type;

			$('#fileDownload').submit();

		}

		function loadWarehouses(soa){	
			var nameArr = soa.split(','); 	
			var i=0;
			while ( i < nameArr.length ) {
				//for (j = 0; j < nameArr.length; j++){
				if(nameArr[i] == 0){						
					$("#scopeOfAccreditation option[value='0']").attr("selected", "selected");
				}
				if(nameArr[i] == 1){						
					$("#scopeOfAccreditation option[value='1']").attr("selected", "selected");
				}
				if(nameArr[i] == 2){						
					$("#scopeOfAccreditation option[value='2']").attr("selected", "selected");
				}
				if(nameArr[i] == 3){						
					$("#scopeOfAccreditation option[value='3']").attr("selected", "selected");
				}
				i++;
				
		   }

		}
			
		function fieldHideUnhide(id1,hid){
			var e = document.getElementById(id1);
			var Qual= e.options[e.selectedIndex].text;
			if(Qual.trim()=="Others"){
				$("."+hid).removeClass("hide");
			}
			else{
				$("."+hid).addClass("hide");
				 resetFieldsTextBox(hid); 
			}
		}
		
		 function resetFieldsTextBox(className)
		{
			if(className=='sup4'){
				$('#otherScatteredArea').val('');
			}
		} 
		
		function loadDependancy(){
			var p1=$('#scatteredss').val();
			if( $('#otherScattered')!=null &&  $('#otherScattered').attr('class')!=undefined){
			var p1class = $('#otherScattered').attr('class').split(' ')[1];	
			loadDependancyFun(p1,p1class);
			}
		}
		
		function loadDependancyFun(id,idd){
			 if(id=='99'){
				 $('.'+idd).removeClass('hide');
			 } 
			 else{
				 $('.'+idd).addClass('hide');
			 }
		}
		
		function loadVariety(val){
			
			var selectedProduct=$("#ugandaExport").val();
			var rsdata="";
			var arr=new Array();
			var rat="";
			$('#farmerHaveFarms').val("").select2();
			$('#scattered').val("").select2();
			
			
			if (!isEmpty(val)) {

				for(var i=0;i<selectedProduct.length;i++)  //iterate through array of selected rows
			    {
			       var ret = selectedProduct[i];  
			       arr.push(ret);
			    }

			   rsdata=arr.join(",");
			   rsdata= rsdata.trim();
			
				$.ajax({
					type : "POST",
					async : false,
					url : "exporterRegistration_populateVariety.action",
					data : {
						selectedProduct : rsdata
					},
					success : function(result) {
						 
						insertOptionswithoutselect("farmerHaveFarms", $.parseJSON(result));
					},

				});

			}
			
		}
		
		function listProGrade(selectedvariety) {
			
			var selectedProduct=$("#farmerHaveFarms").val();
			var rsdata="";
			var arr=new Array();
			var rat="";
			$('#scattered').val("").select2();
			
		
			if(!isEmpty(selectedvariety)){
				for(var i=0;i<selectedProduct.length;i++)  //iterate through array of selected rows
			    {
			       var ret = selectedProduct[i];  
			       arr.push(ret);
			    }

			   rsdata=arr.join(",");
			   rsdata= rsdata.trim();
			   
				$.ajax({
					 type: "POST",
			        async: false,
			        url: "exporterRegistration_populateGrade.action",
				        data: {procurementVariety : rsdata},
			        success: function(result) {
			        	insertOptionswithoutselect("scattered", $.parseJSON(result));
			        }
				});
			}
 
			
		}
		
		function isAlphaNumber(evt) {

			evt = (evt) ? evt : window.event;
			var charCode = (evt.which) ? evt.which : evt.keyCode;
			if ((charCode > 47 && charCode < 58) || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123)) {
				return true;
			}
			return false;

		}
		
</script>
<script type="text/javascript">
function sendSms() {


    var mobNo = jQuery("#mobNo").val();
  $('#butSend').prop('disabled', true);
   if(!isEmpty(mobNo)){
		 
		$.ajax({
			 type: "POST",
	        async: false,
	        url: "generalPop_sendOTP.action",
		        data: {mobNo : mobNo,sessionId:'<%=session.getId()%>'},
	        success: function(result) {
	        	var jsonData =JSON.parse(result);
	        	timer(300);
	        	 	 alert(jsonData.msg);
	        	 	 if(jsonData.code == 100 || jsonData.code == 101 || jsonData.code == 102){
	        		 	$('#tmr').removeClass("hide");
	        	 	 }else{
	        	 		$('#tmr').addClass("hide");
	        	 		 $('#butSend').prop('disabled', false);}
	        	 	 
	        	 
	        	 }
		        
	        
		});
	}else{
		 $('#butSend').prop('disabled', false);
		 alert('Please enter Mobile Number');
	}
     
}

let timerOn = true;

function timer(remaining) {
  var m = Math.floor(remaining / 60);
  var s = remaining % 60;
  
  m = m < 10 ? '0' + m : m;
  s = s < 10 ? '0' + s : s;
  document.getElementById('timer').innerHTML = m + ':' + s;
  remaining -= 1;
  
  if(remaining >= 0 && timerOn) {
    setTimeout(function() {
        timer(remaining);
    }, 1000);
    return;
  }

  if(!timerOn) {
    // Do validate stuff here
    return;
  }
 
  $('#butSend').prop('disabled', false);
  alert('Timeout for otp');
}

function isAlphaNumber(evt) {

	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if ((charCode > 47 && charCode < 58) || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123)) {
		return true;
	}
	return false;

}

function saveVillage() {
    
	jQuery(".verror").empty();
    var vcountry = getElementValueById("vcountry");
    var vstate = getElementValueById("vstate");
    var vlocality = getElementValueById("vlocalities");
    var vcity = getElementValueById("vcity");
    var vname = getElementValueById("villageName");
    var vPanchayat =getElementValueById("vPanchayat"); 
    var vcode = getElementValueById("villageCode");
    /* if(isGramPanchayatEnable=='1'){
     if (isEmpty(vPanchayat)) {
      jQuery(".verror").text("<s:property value="%{getLocaleProperty('empty.gramPanchayat')}"/>");
          return false;
      }else if (isEmpty(vname)) {
         jQuery(".verror").text("<s:property value="%{getLocaleProperty('empty.villagename')}"/>");
         return false;
     }
    }
    else{ */
    if (isEmpty(vcountry)) {
          jQuery(".verror").text("<s:property value="%{getLocaleProperty('empty.country')}" />");
          return false;
      }else if (isEmpty(vstate)) {
          jQuery(".verror").text("<s:property value="%{getLocaleProperty('empty.state')}" />");
          return false;
      }else if (isEmpty(vlocality)) {
          jQuery(".verror").text("<s:property value="%{getLocaleProperty('empty.locality')}" />");
          return false;
      } else if (isEmpty(vcity)) {
          jQuery(".verror").text("<s:property value="%{getLocaleProperty('empty.city')}" />");
          return false;
      } else if (isEmpty(vname)) {
         jQuery(".verror").text("<s:property value="%{getLocaleProperty('error.village')}"/>");
         return false;
     }
    /* } */

    jQuery.post("exporterRegistration_populateVillageSave.action", {
        selectedVillage: vname,
        selectedCity: vcity,
        selectedPanchayat:vPanchayat,
        selectedVillageCode:vcode
    }, function(result) {
     var validateMsg=JSON.stringify(result);
     var validate=jQuery.parseJSON(validateMsg);
     var val = validate.msg;
     if(val=='0'){
       jQuery(".verror").text("<s:property value="%{getLocaleProperty('village.exist')}"/>");
     }else{
        //listVillage(document.getElementById('city'), 'city', 'panchayath', 'village');
        listVillage(document.getElementById('city'), 'city', 'village');
         //listVillage(this,'city','village')"
            resetVillageInfo();
     }
    });
}

function listState(obj, countryId, stateId, disId, cityId,gpId, vId) {

	if (!isEmpty(obj)) {
		var selectedCountry = $('#' + countryId).val();
		jQuery.post("exporterRegistration_populateState.action", {
			selectedCountry : obj.value
		}, function(result) {
			insertOptions(stateId, $.parseJSON(result));
			listLocality(document.getElementById(stateId), stateId, disId,
					cityId,gpId,vId);
		});
		$('#' + disId).select2();
	}
}

function listLocality(obj, stateId, disId, cityId,gpId, vId) {

	if (!isEmpty(obj)) {
		var selectedState = $('#' + stateId).val();
		jQuery.post("exporterRegistration_populateLocality.action", {
			id1 : obj.value,
			dt : new Date(),
			selectedState : obj.value
		}, function(result) {
				insertOptions(disId, $.parseJSON(result));
			listMunicipality(document.getElementById(disId),disId, cityId,gpId, vId);
		});
		$('#' + cityId).select2();
	}
}

function listMunicipality(obj, disId, cityId, gpId, vId) {
		if (!isEmpty(obj)) {
			var selectedLocality = $('#' + disId).val();
			jQuery.post("exporterRegistration_populateMunicipality.action", {
				id1 : obj.value,
				dt : new Date(),
				selectedLocality : obj.value
			}, function(result) {
				insertOptions(cityId, $.parseJSON(result));
				//listPanchayat(document.getElementById(cityId),cityId,gpId,vId);
				listVillage(document.getElementById(cityId), cityId, vId);
			});
			
			$('#' + gpId).select2();
		}
		
	
}

/* function listPanchayat(obj, cityId, gpId, vId) {
	if (!isEmpty(obj)) {
		try {
			var selectedCity = $('#' + cityId).val();

			jQuery.post("exporterRegistration_populatePanchayath.action", {
				id : obj.value,
				dt : new Date(),
				selectedCity : obj.value
			}, function(result) {
				insertOptions(gpId, $.parseJSON(result));
				listVillage(document.getElementById(gpId), cityId, gpId,vId);
			});
		} catch (e) {
		}
		$('#' + vId).select2();
	}
} */

function listVillage(obj, CityId, vId) {
	clearElement(vId,true);
		if (!isEmpty(obj.value)) {
		var selectedCity = $('#' + CityId).val();
		jQuery.post("exporterRegistration_populateVillage.action", {
			id1 : obj.value,
			dt : new Date(),
			selectedCity : obj.value
		}, function(result) {
			insertOptions(vId, $.parseJSON(result));
			if('<s:property value="selectedVillage"/>'!=null && '<s:property value="selectedVillage"/>'!=''){
				if(obj.value == '<s:property value="selectedCity"/>'){
					
				$('#'+vId).val('<s:property value="selectedVillage"/>').select2().change();
				
				}
			}
			
		});
	
	}
}

function resetVillageInfo() {
	jQuery("#vcountry").val('');
	jQuery("#vstate").val('');
	jQuery("#vlocalities").val('');
	jQuery("#vcity").val('');
	jQuery("#villageName").val('');

	jQuery("#vcountry").select2();
	jQuery("#vstate").select2();
	jQuery("#vlocalities").select2();
	jQuery("#vcity").select2();
	jQuery(".cerror").empty();
	$('#villageModal').modal('toggle');
	jQuery(".verror").empty();
}


function loadHsCode(val){
	
	//var selectedProduct=$("#cropcategory").val();
	//alert("Selected Product***********"+val);

	 var procUnit="";
 $($("#scattered").val()).each(function(k, v) {
procUnit += v + ",";
	});
	
	if (!isEmpty(procUnit)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "exporterRegistration_populateHsCode.action",
			data : {
				selectedProduct : procUnit
			},
			success : function(result) {
				$('#packGpsLoc').val(result.hscode);
			}

		});

	}else{
		$('#packGpsLoc').val('');
	}
	
}

function getFirstAndlastValCaps(id) {
	var val = $("#" + id).val();
	let result = val.toUpperCase();
	$("#" + id).val(result);
}

</script>

<body>
	<s:form name="form" cssClass="fillform" method="post"
		action="exporterRegistration_%{command}" enctype="multipart/form-data"
		id="target">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:hidden id="proofFile1" name="proofFile1" />
		<s:hidden name="sessionId" id="sessionid" />
		<s:hidden name="selectedExporterStatus" id="esIds" />
		<s:hidden name="selectedExporterStatusFlag" id="selectedExporterStatusFlag" />
		
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="expRegis.id" />
		</s:if>
		<s:hidden key="command" />
		<div class="appContentWrapper marginBottom">

			<div class="error">
				<%-- 	<s:actionerror /> --%>
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
					<!--<s:text name="info.export" /> -->
					<s:property value="%{getLocaleProperty('info.export')}" />
				</h2>
				<div class="flexform">


					<div class="flexform-item">
						<label for="txt"><s:text name="exporterRegistr.cmpyName1" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="expRegis.name" theme="simple"
								cssClass="lowercase form-control" maxlength="50" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="exporterRegistr.cemai" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:if test="command=='update'">
								<s:textfield name="expRegis.tin" theme="simple"
									cssClass="lowercase form-control" readonly="true" />
							</s:if>
							<s:else>
								<s:textfield name="expRegis.tin" theme="simple"
									cssClass="lowercase form-control" maxlength="50" />
							</s:else>
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="export.associationMemb" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="cmpyOrientation"
								name="expRegis.cmpyOrientation" listKey="key" listValue="value"
								list="associationMemberList" headerKey=" " maxlength="50"
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="export.legalstatus" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="packhouse"
								name="expRegis.packhouse" listKey="key" listValue="value"
								list="legalStatusList" headerKey=" " maxlength="20"
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="export.krapin" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="expRegis.regNumber" theme="simple"
								cssClass="lowercase form-control" maxlength="11" id="regNumber"
								onkeypress="return isAlphaNumber(event)" onkeyup="getFirstAndlastValCaps(this.id)" />
						</div>
					</div>
<%-- 					<div class="flexform-item">
						<label for="txt"><s:text name="export.krapin" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="expRegis.regNumber" theme="simple"
								cssClass="lowercase form-control" maxlength="11"
								onkeypress="return isAlphaNumber(event)" style="text-transform: uppercase;" />
						</div>
					</div> --%>


					<div class="flexform-item">
						<label for="txt"><s:text name="export.license" />
						<%-- <sup style="color: red;">*</sup> --%>
						</label>
						<div class="form-element">
							<s:textfield name="expRegis.refLetterNo" theme="simple"
								cssClass="form-control" maxlength="20" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="export.cropcat" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="ugandaExport"
								name="expRegis.ugandaExport" listKey="id" listValue="name"
								list="productList" multiple="true" maxlength="20"
								onchange="loadVariety(this.value);" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="farmer.fcropName" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2" id="farmerHaveFarms"
								name="expRegis.farmerHaveFarms" maxlength="20" listKey="id"
								listValue="name" multiple="true" list="{}"
								onchange="listProGrade(this.value);"/>
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="export.cropvariety" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="scattered"
								name="expRegis.scattered" maxlength="20" listKey="id"
								listValue="name" multiple="true" list="{}" onchange="loadHsCode(this.value);" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="export.crophs" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<%--  <label for="txt"  id="lat"><s:text name="expRegis.packGpsLoc"/></label> --%>
							<s:textfield name="expRegis.packGpsLoc" theme="simple"
								cssClass="lowercase form-control" maxlength="15" id="packGpsLoc" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="premises.postalAddr" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textarea name="expRegis.expTinNumber" theme="simple"
								cssClass="lowercase form-control" maxlength="50" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="country.name" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="countries"
								headerKey="" theme="simple" name="selectedCountry"
								maxlength="20" headerValue="%{getText('txt.select')}" Key="id"
								Value="name"
								onchange="listState(this,'country','state','localities','city','panchayath','village')"
								id="country" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="county.name" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="states"
								headerKey="" theme="simple" name="selectedState"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="state" maxlength="20"
								onchange="listLocality(this,'state','localities','city','panchayath','village')" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="subcountry.name" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="localities"
								headerKey="" theme="simple" name="selectedLocality"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="localities" maxlength="20"
								onchange="listMunicipalities(this,'localities','city','panchayath','village')" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="ward.name" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="city" headerKey=""
								theme="simple" name="selectedCity" maxlength="20"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="city" onchange="listVillage(this,'city','village')" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="village.name" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="villages"
								headerKey="" theme="simple" name="selectedVillage"
								maxlength="20" headerValue="%{getText('txt.select')}"
								id="village" />
							<button type="button" id="addEduDetail"
								class="addBankInfo slide_open btn btn-sts" data-toggle="modal"
								data-target="#villageModal">
								<i class="fa fa-plus" aria-hidden="true"></i>
							</button>
						</div>
					</div>



					<div class="flexform-item">
						<label for="txt"><s:text name="export.nationalId" /></label>
						<div class="form-element">
							<s:textfield name="expRegis.rexNo" theme="simple"
								cssClass="lowercase form-control" maxlength="25" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="export.applicantName" /></label>
						<div class="form-element">
							<s:textfield name="expRegis.farmToPackhouse" theme="simple"
								cssClass="lowercase form-control" maxlength="30" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="exporterRegistr.gender" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:radio name="expRegis.packToExitPoint" list="genderStatusMap" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="exporterRegistr.email" />
						</label>
						<div class="form-element">
							<s:textfield name="expRegis.email" theme="simple" id="mail"
								maxlength="50" cssClass="lowercase form-control" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="exporterRegistr.phn" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="expRegis.mobileNo" theme="simple" id="mobNo"
								cssClass="lowercase form-control" maxlength="12"
								onkeypress="return isNumber(event)" title="254*********"/>
						</div>
					</div>

					 					 <div class="flexform-item">
					
                           <div class="form-element">
											<input type="button"  style="width:30%"
												class="btnSrch btn btn-small btn-success form-control"
												value="Send OTP" name="button.save" id="butSend"
												onclick="sendSms()">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<s:textfield  name="otp" placeholder="Enter your otp" theme="simple" id="otp"
								cssClass="lowercase form-control" maxlength="6" onkeypress="return isNumber(event)"  />
										</div>
									
										<div id="tmr" class="form-element">Time left = <span id="timer"></span></div>
										
										</div> 
					
					 

					 <s:if test='#session.isAdmin =="true" && command =="update"'>

						<div class="flexform-item">
							<label for="txt"><s:text name="agroChemicalDealer.active" /></label>
							<div class="form-element">
								<s:select class="form-control  select2" list="activeStatusList"
									headerKey="" theme="simple" name="expRegis.status"
									headerValue="%{getText('txt.select')}" Key="id" Value="name"
									id="active" />
							</div>
						</div>
					</s:if>
					<s:else>
					<s:hidden name="expRegis.status" />
					</s:else>


				</div>

				<div id="villageModal" class="modal fade" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" id="model-close-btn" class="close"
									data-dismiss="modal">&times;</button>
								<h4 style="border-bottom: solid 1px #567304;">
									<s:property value="%{getLocaleProperty('info.village')}" />
								</h4>
							</div>
							<div class="text-danger verror"></div>
							<table class="table table-bordered aspect-detail fixedTable">

								<tr>
									<td style="width: 17%;">
										<div>
											<s:property value="%{getLocaleProperty('country.name')}" />
										</div>
									</td>

									<td><div>
											<%-- <div class="inputCon">
												<s:select class="form-control input-sm select2"
													name="selectedCountry" id="vcountry" list="countries"
													headerKey="" headerValue="%{getText('txt.select')}"
													onchange="listState(this,'vcountry','vstate','vlocalities','')"
													cssStyle="width:380px" />
											</div> --%>
											<div class="inputCon">
												<s:select class="form-control input-sm select2"
													name="selectedCountry" id="vcountry" list="countries"
													headerKey="" headerValue="%{getText('txt.select')}"
													onchange="listState(this,'vcountry','vstate','vlocalities','vcity','panchayath','')"
													cssStyle="width:380px" />
											</div>
										</td>
								</tr>

								<tr>
									<td><s:property value="%{getLocaleProperty('state.name')}" />
									</td>

									<td>
										<div class="inputCon">
											<s:select class="form-control input-sm select2" list="states"
												headerKey="" theme="simple"
												headerValue="%{getText('txt.select')}" Key="id" Value="name"
												id="vstate"
												onchange="listLocality(this,'vstate','vlocalities','vcity','vPanchayat','')"
												cssStyle="width:380px" />
										</div>
									</td>
								</tr>

								<tr>
									<td><s:property
											value="%{getLocaleProperty('locality.name')}" /></td>

									<td>
										<div class="inputCon">
											<s:select cssClass="form-control  select2" id="vlocalities"
												list="localities" headerKey=""
												headerValue="%{getText('txt.select')}" Key="id" Value="name"
												theme="simple"
												onchange="listMunicipality(this,'vlocalities','vcity','vPanchayat','')" />
										</div>
									</td>
								</tr>

								<tr>
									<td><s:property value="%{getLocaleProperty('city.name')}" />
									</td>

									<td>
										<%-- <div class="inputCon">
											<s:select cssClass="form-control  select2" id="vcity"
												list="city" headerKey=""
												headerValue="%{getText('txt.select')}" Key="id" Value="name"
												theme="simple"
												onchange="listPanchayat(this,'vcity','vPanchayat','')" />
										</div> --%>
										<div class="inputCon">
											<s:select cssClass="form-control  select2" id="vcity"
												list="city" headerKey=""
												headerValue="%{getText('txt.select')}" Key="id" Value="name"
												theme="simple"
												onchange="listVillage(this,'vcity','villageName','')"
												/>
										</div>
									</td>
								</tr>

								<tr>
									<td><s:property
											value="%{getLocaleProperty('village.name')}" /></td>

									<td>
										<div class="inputCon">
											<input type="text" class="form-control" id="villageName"
												name="villageName">
										</div>
									</td>
								</tr>


							</table>
							<div class="modal-footer">
								<button type="button" class="btn btn-sts" id="villa"
									onclick="saveVillage()">
									<s:property value="%{getLocaleProperty('save.button')}" />
								</button>
								<button type="button" class="btn btn-primary" id="buttonCancel"
									onclick="resetVillageInfo()">
									<s:property value="%{getLocaleProperty('cancel')}" />
								</button>
							</div>
						</div>
					</div>
				</div>

                <s:if test='#session.isAdmin =="true" && command =="update"'>
                   <div class="flexform-item">
						<label for="txt"><s:text name="exporterRegistr.exporterStatus" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2" id="exporterStatus"
								name="selectedExporterStatus1" listKey="key"
								listValue="value" list="#{'0':'Applied','1':'Verified','2':'Approved'}"
								headerValue="%{getText('txt.select')}" headerKey="" />
						</div>
					</div>
					</s:if>


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

	<s:form id="fileDownload" action="generalPop_populateDownload">
		<s:hidden id="loadId" name="idd" />
		<%-- 	<s:hidden id="loadType" name="type" /> --%>

	</s:form>

</body>


-