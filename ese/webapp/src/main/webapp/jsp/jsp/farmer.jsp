<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<!-- <script src="js/farmer.js?v=4.19"></script> -->
<head>
<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType")
					: "swithlayout"%>">

</head>
<script>
	var tenant = '<s:property value="getCurrentTenantId()"/>';
	var command = '<s:property value="command"/>';
	var url =<%out.print("'" + request.getParameter("url") + "'");%>;
	var layOut='<%=request.getParameter("layoutType")%>	';
	jQuery(document).ready(function() {
		$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
		url =<%out.print("'" + request.getParameter("url") + "'");%>;
		if (url == null || url == undefined || url == ''|| url == 'null') {
			url = 'farmer_';
		}
		
		 
			if(command=='update'){
				var pbmType=$.trim('<s:property value="farmer.ownership"/>');
				$('#ownership').val(JSON.parse(JSON.stringify(pbmType.replace(/\s/g,'').split(",")))).select2();
				var asset=$.trim('<s:property value="farmer.asset"/>');
				$('#asset').val(JSON.parse(JSON.stringify(asset.replace(/\s/g,'').split(",")))).select2();
		
				var cropcat=$.trim('<s:property value="farmer.cropCategory"/>');
				$('#cropcategory').val(JSON.parse(JSON.stringify(cropcat.replace(/\s/g,'').split(",")))).select2();
				loadVarety(cropcat);
				var cropName=$.trim('<s:property value="productId"/>');
				$('#procurementProductList').val(JSON.parse(JSON.stringify(cropName.replace(/\s/g,'').split(",")))).select2();
				listProGrade(cropName);
				var varietyId=$.trim('<s:property value="varietyId"/>');
				$('#scattered').val(JSON.parse(JSON.stringify(varietyId.replace(/\s/g,'').split(",")))).select2();
				loadHsCode(varietyId);
				/*  var farmNam='<s:property value="farmer.exporter.id"/>';
				$("#exporter option[value='"+ farmNam.trim() + "']").prop("selected", true).trigger('change'); */
				
				/* var farmNam=$.trim('<s:property value="farmer.exporters"/>');
				$('#exporter').val(JSON.parse(JSON.stringify(farmNam.replace(/\s/g,'').split(",")))).select2();
				$('#exporter').change(); */
				
				fun1('<s:property value="farmer.house"/>','partcondet');
				
				hideAndShowCompany('<s:property value="farmer.farmerRegType"/>');
			}
			
		
		$('#buttonAdd1,#buttonUpdate').on('click', function (event) {  
			 event.preventDefault();
		    	$("#buttonAdd1").prop('disabled', true);
				$("#buttonUpdate").prop('disabled', true);
				var hit = true;
			if(!validateAndSubmit("target",url)){
				$("#buttonAdd1").prop('disabled', false);
			}
			$("#buttonAdd1").prop('disabled', false);
			$("#buttonUpdate").prop('disabled', false);
			});
		
		if (layOut == 'publiclayout') {
			$("#canBt").addClass('hide');
		}
		
		
		$("#calendar").change(function(v){
			calculateAge();
		});
		
		  $("#calender").datepicker(
					{
							dateFormat: 'dd-mm-yy',
						changeMonth: true,
						changeYear: true
					}
					); 
		
	});
	
function onCancel(){
	window.location.href="<s:property value="redirectContent" />";
}

function setFiles(val) {
	
	if (val != null && val != '') {
		$('#fphoto').val(val);
		
	}
}

/* function setRegCrt(val) {
	
	if (val != null && val != '') {
		$('#refPhoto').val(val);
		
	}
} */

function setFphoto1(val) {
	
	if (val != null && val != '') {
		$('#nidPhoto').val(val);
		
	}
}

function popDownload(type) {
	document.getElementById("loadId").value = type;

	$('#fileDownload').submit();

}

function loadVarety(val){
	
	var selectedProduct=$("#cropcategory").val();
	var rsdata="";
	var arr=new Array();
	var rat="";
	var esist =$('#procurementProductList').val();
	$('#procurementProductList').empty().trigger("change");
	$('#procurementProductList').val("").select2();
	if(selectedProduct!=null && selectedProduct!=''){
		for(var i=0;i<selectedProduct.length;i++)  //iterate through array of selected rows
	    {
	       var ret = selectedProduct[i];  
	       arr.push(ret);
	    }
	
	   	rsdata=arr.join(", ");
	   	rsdata= rsdata.trim();
		
		if (!isEmpty(val)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "farmer_populatevarity.action",
				data : {
					selProd : rsdata
				},
				success : function(result) {
					//alert("res"+result)
					insertOptionswithoutselect("procurementProductList", $.parseJSON(result));
					$('#procurementProductList').val(JSON.parse(JSON.stringify(esist.replace(/\s/g,'').split(",")))).select2();
					$('#procurementProductList').trigger('change');
				
				},
	
			});
	
		}
	}else{
		//$('#procurementProductList').empty().trigger("change");
		$('#procurementProductList').val("").select2();
	}
	
}

//****************

function loadHsCode(val){
	
	//var selectedProduct=$("#cropcategory").val();
	//alert("Selected Product***********"+selectedProduct);

	 var procUnit="";
 $($("#scattered").val()).each(function(k, v) {
procUnit += v + ",";
	});
	
	if (!isEmpty(procUnit)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "farmer_populateHsCode.action",
			data : {
				selectedProduct : procUnit
			},
			success : function(result) {
				$('#hscode').val(result.hscode);
			}

		});

	}else{
		$('#hscode').val('');
	}
	
}




 function fun1(val,className) {
	
	if (val == "1"){
		$('.' + className).removeClass('hide');
	}else if($('.' + className)!=null && $('.' + className)!=undefined){
		$('.' + className).addClass('hide');
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
 
 function hideAndShowCompany(value){
	 if(value=='1'){
		 $(".companySection").removeClass('hide');
		 $(".gendermo").addClass('hide');
	     $(".kraPin").removeClass('hide');
		 
	 }else {
		 $(".companySection").addClass('hide');
		 $(".gendermo").removeClass('hide');
		 $(".kraPin").removeClass('hide');
	 }
}
 
/*  function exporterSelectAll(fieldVal){
	
	 if(fieldVal!='' && fieldVal=='-1'){
		    
	      $('#exporter > option[value!="-1"]').prop("selected","selected");
	      $('#exporter > option[value="-1"]').prop("selected",false);
	      $("#exporter").select2();
	     
	    }
	} */
 
 
 function getFirstAndlastValCaps(id) {
		var val = $("#" + id).val();
		let result = val.toUpperCase();
		$("#" + id).val(result);
	}
 
	function calculateAge()
	{
		if($('#calendar').val()!=null&&$('#calendar').val()!='undefined'&&$('#calendar').val()!=''){
		      
	         var age =  $('#calendar').val().replace(/[^\w\s]/gi, '');
	         dob = age.substring(4,8);
	         var today = new Date(); 
	         $('#age').val(parseInt(today.getFullYear()) - parseInt(dob));
	         $('#farmerAge').text(parseInt(today.getFullYear()) - parseInt(dob));
	         $('#ageHide').val(parseInt(today.getFullYear()) - parseInt(dob));
	         
			}else{
					   $('#age').val(0);
					   $('#farmerAge').text(0);
				         $('#ageHide').val(0);
				}
	}
	
	function listProGrade(selectedvariety) {
		
		var selectedProduct=$("#procurementProductList").val();
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
		        url: "farmer_populateGrade.action",
			        data: {procurementVariety : rsdata},
		        success: function(result) {
		        	insertOptionswithoutselect("scattered", $.parseJSON(result));
		        }
			});
		}

		
	}

</script>


<body>


	<s:form id="fileDownload" action="farmer_populateDownload">
		<s:hidden id="loadId" name="idd" />
	</s:form>



	<s:form name="form" cssClass="fillform" method="post" id="target"
		enctype="multipart/form-data">

		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="farmer.id" class="uId" />
		</s:if>
		<s:hidden key="command" />
		<div class="appContentWrapper marginBottom">

			<div class="error">
				<div id="validateError" style="text-align: left; font-color: red"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.farmerInfn" />
				</h2>
			</div>

			<div class=flexform>

               <div class="flexform-item">
					<label for="txt"><s:text name="Farmer Category" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2" list="farmerCategoryList"
							headerKey="" theme="simple" name="farmer.fCat"
							headerValue="%{getText('txt.select')}" id="fCat" />
					</div>
				</div>
				
				<div class="flexform-item">
					<%-- <label for="txt"><s:text name="farmer.cat" /> --%>
					<label for="txt"><s:text name="Farm Ownership" />
					<sup style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2" list="farmerCatList"
							headerKey="" theme="simple" name="farmer.farmerCat"
							headerValue="%{getText('txt.select')}" id="fCategory" />
					</div>
				</div>
				
				<div class="flexform-item">
					<label for="txt"><s:text name="farmer.farmerRegType" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2"
							list="#{'0':'Individual','1':'Company'}" headerKey=""
							theme="simple" name="farmer.farmerRegType"
							headerValue="%{getText('txt.select')}" id="farmerRegType"
							onchange="hideAndShowCompany(this.value)" />
					</div>
				</div>


				<%-- 	<div class="formContainerWrapper companySection hide">
					<h2>
						<s:text name="info.company" />
					</h2>
--%>
				<div class="flexform-item companySection hide">
					<label for="txt"><s:text name="farmer.companyName" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:textfield name="farmer.companyName" theme="simple"
							cssClass="lowercase form-control" />
					</div>
				</div>

				<div class="flexform-item companySection hide">
					<label for="txt"><s:text
							name="farmer.registrationCertificate" /><sup style="color: red;">*</sup></label>
					<div class="form-element">
						<s:textfield name="farmer.registrationCertificate" theme="simple"
							cssClass="lowercase form-control" />
					</div>
				</div>

				<%-- <div class="flexform-item companySection hide">
					<label for="txt"><s:text name="farmer.kraPin" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:textfield name="farmer.kraPin" theme="simple"
							cssClass="lowercase form-control"
							onkeypress="return isAlphaNumber(event)" />
					</div>
				</div> --%>
				
				<div class="flexform-item kraPin hide">
					<label for="txt"><s:text name="farmer.kraPin" /><!-- <sup
						style="color: red;">*</sup> --></label>
					<div class="form-element">
						<s:textfield name="farmer.kraPin" theme="simple"
							cssClass="lowercase form-control" id="kraPin"
							onkeypress="return isAlphaNumber(event);"  maxlength="11" onkeyup="getFirstAndlastValCaps(this.id)"/>
					</div>
				</div>
                 <%-- <div class="flexform-item companySection hide">
					<label for="txt"><s:text name="farmer.kraPin" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:textfield name="farmer.kraPin" theme="simple"
							cssClass="lowercase form-control" id="kraPin"
							onkeypress="return isAlphaNumber(event);"  maxlength="11" onkeyup="getFirstAndlastValCaps(this.id)"/>
					</div>
				</div> --%>


				<!-- </div>  -->



				<%-- <s:if
					test="getLoggedInDealer()==null  || getLoggedInDealer()=='' || getLoggedInDealer()=='null' ">
					
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('exporter')}" /></label>
						<div class="form-element">
							<s:select name="farmer.exporters"
								list="exporterList" listKey="key" listValue="value"
								theme="simple" id="exporter"
								cssClass="form-control select2" multiple="true" headerKey="-1"
								headerValue="%{getText('txt.selectall')}" onchange="exporterSelectAll(this.value)"/>
						</div>
					</div>
				</s:if>
				<s:else>
					<s:hidden name="farmer.exporter.id" />
				</s:else> --%>
				<div class="flexform-item">
					<label for="txt"> <s:property
							value="%{getLocaleProperty('farmer.fname')}" /> <sup
						style="color: red;">*</sup>
					</label>
					<div class="form-element">
						<s:textfield id="firstName" name="farmer.firstName" theme="simple"
							maxlength="30" cssClass="upercls form-control" />
					</div>
				</div>
				<s:if test="command =='update'">
					<div class="flexform-item">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farmer.fcode')}" />
						</label>
						<div class="form-element">
							<s:textfield id="firstName" name="farmer.farmerId" theme="simple"
								maxlength="50" cssClass="upercls form-control" readonly="true" />
						</div>
					</div>
				</s:if>

				<div class="flexform-item">
					<label for="txt"><s:text name="exporterRegistr.gender" />
						<sup style="color: red;" class="gendermo">*</sup></label>
					<div class="form-element">
						<s:radio list="genderStatusMap" id="quality" name="farmer.gender" />
					</div>
				</div>

               <div class="flexform-item">
					<label id="dateName" for="txt"> <s:property
							value="%{getLocaleProperty('farmer.dateOfBirth')}" /> <sup
						style="color: red;" class="gendermo">*</sup>
					</label>
					<div class="form-element">
						<s:textfield value='%{dateOfBirth}' readonly="true"
							name="dateOfBirth" onchange="calculateAge()" id="calendar"
							theme="simple"
							data-date-format="%{getGeneralDateFormat().toLowerCase()}"
							size="20" cssClass="date-picker form-control input-sm" />
					</div>
				</div>
                 

				<div class="flexform-item">
					<label for="txt"> <s:property
							value="%{getLocaleProperty('farmer.fage')}" /> <sup
						style="color: red;" class="gendermo">*</sup>
					</label>
					<div class="form-element">
						<s:textfield id="age" name="farmer.age" theme="simple"
							maxlength="4" cssClass="upercls form-control"
							onkeypress="return isNumber(event)" readonly="true"/>
					</div>
				</div>

				<div class="flexform-item">
					<label for="txt"><s:text name="export.nationalId" />
					<!-- <sup style="color: red;" class="gendermo">*</sup> -->
					</label>
					<div class="form-element">
						<s:textfield name="farmer.nid" theme="simple" id="natiId"
							cssClass="lowercase form-control" maxlength="10"
							onkeypress="return isNumber(event)" />
					</div>
				</div>

				<div class="flexform-item">
					<label for="txt"><s:text name="exporterRegistr.phn" /><sup
						style="color: red;">*</sup> </label>
					<div class="form-element">
						<s:textfield name="farmer.mobileNo" theme="simple" id="phn"
							cssClass="lowercase form-control" maxlength="10"
							onkeypress="return isNumber(event)" />

					</div>
				</div>
				<%-- <div class="flexform-item">
					<label for="txt"><s:text name="export.cropcat" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2 " id="cropcategory"
							name="farmer.cropCategory" listKey="id" listValue="name"
							list="productList" multiple="true"
							onchange="loadVarety(this.value);loadHsCode(this.value)" />


					</div>
				</div> 
				<div class="flexform-item">
					<label for="txt"><s:text name="farmer.fcropName" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select name="productId" list="{}" listKey="id" listValue="name"
							id="procurementProductList" class="form-control  select2 "
							multiple="true" />


					</div>
				</div>--%>
				<div class="flexform-item">
					<label for="txt"><s:text name="export.cropcat" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2 " id="cropcategory"
							name="farmer.cropCategory" listKey="id" listValue="name"
							list="productList" multiple="true"
							onchange="loadVarety(this.value);" />


					</div>
				</div>
				<div class="flexform-item">
					<label for="txt"><s:text name="farmer.fcropName" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select name="productId" list="{}" listKey="id" listValue="name"
							id="procurementProductList" class="form-control  select2 "
							multiple="true" onchange="listProGrade(this.value);"/>


					</div>
				</div>
				
				<div class="flexform-item">
					<label for="txt"><s:text name="export.cropvariety" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select name="varietyId" list="{}" listKey="id" listValue="name"
							id="scattered" class="form-control  select2 "
							multiple="true" onchange="loadHsCode(this.value)"/>


					</div>
				</div>


				<%-- <s:if test="command =='update'"> --%>

					<div class="flexform-item">
						<label for="txt"><s:text name="export.crophs" /></label>
						<div class="form-element">
							<s:textfield name="cropHsCode" theme="simple" id="hscode"
								cssClass="lowercase form-control" maxlength="25" readonly="true" />
						</div>
					</div>
				<%-- </s:if> --%>

				<div class="flexform-item">
					<label for="txt"><s:text name="country.name" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2" list="countries"
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
						<s:select class="form-control  select2" list="states"
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
						<s:select class="form-control  select2" list="localities"
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
						<s:select class="form-control  select2" list="city" headerKey=""
							theme="simple" name="selectedCity"
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
							headerValue="%{getText('txt.select')}" id="village" />
					</div>
				</div>


				<div class="flexform-item">
					<label for="txt"><s:text name="farmer.photo" />&nbsp;&nbsp;<font
						color="red"><s:text
								name="%{getLocaleProperty('imageValidation')}" /></font></label>
					<div class="form-element">
						<s:file name="files" id="files" cssClass="form-control"
							onchange="setFiles(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF'])" />
						<s:hidden name="fphoto" id="fphoto" />
						<s:if test="command=='update'">
							<s:if test="farmer.farmerPhoto!=null">

								<button type="button" class="fa fa-download"
									onclick="popDownload(<s:property value="farmer.farmerPhoto"/>)">
							</s:if>
						</s:if>


					</div>
				</div>


				<div class="flexform-item">
					<label for="txt"><s:text name="farmer.nid" />
					<!-- <sup style="color: red;">*</sup> -->
					<font color="red"><s:text
								name="%{getLocaleProperty('imageValidation')}" /></font></label>
					<div class="form-element">
						<s:file name="fphoto1" id="fphoto1" cssClass="form-control"
							onchange="setFphoto1(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF'])" />
						<s:hidden name="nidPhoto" id="nidPhoto" />
						<s:hidden name="fphoto1" id="fphoto1" />
						<s:hidden name="farmer.photoNid" />

						<s:if test="command=='update'">
							<s:if test="farmer.photoNid!=null && farmer.photoNid!=''">
								<button type="button" class="fa fa-download"
									onclick="popDownload(<s:property value="farmer.photoNid"/>)">
							</s:if>
						</s:if>
					</div>
				</div>

				<%-- 	<div class="flexform-item">
					<label for="txt"><s:text name="farmer.cat" /><sup
						style="color: red;">*</sup></label>
					<div class="form-element">
						<s:select class="form-control  select2" list="farmerCatList"
							headerKey="" theme="simple" name="farmer.farmerCat"
							headerValue="%{getText('txt.select')}" id="fCategory" />
					</div>
				</div> --%>

			</div>


			<div class="formContainerWrapper companyPart">
				<h2>
					<s:text name="info.geo" />
				</h2>
				<div class="flexform-item">
					<label for="txt"><s:text name="farmer.latitude" /> <!-- <sup style="color: red;">*</sup>  -->
					</label>
					<div class="form-element">
						<s:textfield name="farmer.latitude" theme="simple"
							cssClass="lowercase form-control" maxlength="10"
							onkeypress="return isDecimalWithDec(event,this,10,10);" />
					</div>
				</div>
				<div class="flexform-item">
					<label for="txt"><s:text name="farmer.longitude" /> <!-- <sup style="color: red;">*</sup>  -->
					</label>
					<div class="form-element">
						<s:textfield name="farmer.longitude" theme="simple"
							cssClass="lowercase form-control" maxlength="10"
							onkeypress="return  isDecimalWithDec(event,this,10,10);" />
					</div>
				</div>

				<div class="flexform-item">
					<label for="txt"><s:text name="exporterRegistr.email" /> </label>
					<div class="form-element">
						<s:textfield name="farmer.emailId" theme="simple" maxlength="50"
							cssClass="lowercase form-control" />
					</div>
				</div>

				<div class="formContainerWrapper companyPart">
					<h2>
						<s:text name="info.socio" />
					</h2>


					<div class="flexform-item">
						<label for="txt"><s:text name="farmer.family" /></label>
						<div class="form-element">
							<s:textfield name="farmer.noOfFamilyMember" theme="simple"
								cssClass="lowercase form-control" maxlength="10"
								onkeypress="return isNumber(event)" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="farmer.adult" /></label>
						<div class="form-element">
							<s:textfield name="farmer.adultAbove" theme="simple"
								cssClass="lowercase form-control" maxlength="10"
								onkeypress="return isNumber(event)" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="farmer.school" /> </label>
						<div class="form-element">
							<s:textfield name="farmer.schoolGoingChild" theme="simple"
								cssClass="lowercase form-control" maxlength="10"
								onkeypress="return isNumber(event)" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="farmer.childBelow" /> </label>
						<div class="form-element">
							<s:textfield name="farmer.childBelow" theme="simple"
								cssClass="lowercase form-control" maxlength="10"
								onkeypress="return isNumber(event)" />
						</div>
					</div>


					<div class="flexform-item" style="margin-top: 0px;">
						<label for="txt"><s:text name="farmer.edu" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="industry"
								name="farmer.hedu" listKey="key" listValue="value"
								list="getCatList(getLocaleProperty('heduc'))" headerKey=" "
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="farmer.house" /></label>
						<div class="form-element"
							style="padding-top: 9px; padding-bottom: 11px">
							<s:radio list="yesNoMap" id="house" name="farmer.house"
								onchange="fun1(this.value,'partcondet')" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="farmer.asset" /></label>
						<div class="form-element">

							<s:select class="form-control  select2 " id="asset"
								name="farmer.asset" listKey="key" listValue="value"
								list="getCatList(getLocaleProperty('asset'))" multiple="true" />



						</div>
					</div>
					<div class="flexform-item  partcondet hide " id="button1"
						style="margin-top: -7px">
						<label for="txt"><s:text name="farmer.owner" /></label>
						<div class="form-element">


							<s:select class="form-control  select2  " id="ownership"
								name="farmer.ownership" listKey="key" listValue="value"
								list="getCatList(getLocaleProperty('owner'))" multiple="true" />
						</div>

					</div>

				</div>
			</div>

			<s:if test='"update".equalsIgnoreCase(command)'>

			</s:if>
		</div>
		</div>
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
						type="button" class="cancel-btn btn btn-sts" onclick="onCancel();">
						<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
						</font></b>
					</button></span></span>
		</div>
	</s:form>


</body>