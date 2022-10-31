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
		 var newOption = new Option('C00004-kenya','C00004',false, false);
			$('#country').append(newOption).trigger('change');	    
			$('#country').val('Kenya').select2();   
			listState();//just these two lines
		
			if(command=='update'){///this is the corresponding code
				var newOption = new Option('<s:property value="productId"/>','<s:property value="farmer.cropName"/>',false, false);
				$('#procurementProductList').append(newOption).trigger('change');	    
				$('#procurementProductList').val('<s:property value="farmer.cropName"/>').select2();   
				
				listState();
				var newOption4 = new Option('<s:property value="selectedState"/>','<s:property value="farmer.county"/>',false, false);
				$('#state').append(newOption4).trigger('change');	    
				$('#state').val('<s:property value="farmer.county"/>').select2();
				listViallge();
				var newOption6 = new Option('<s:property value="selectedVillage"/>','<s:property value="farmer.subCounty"/>',false, false);
				$('#village').append(newOption4).trigger('change');	    
				$('#village').val('<s:property value="farmer.subCounty"/>').select2();
				listWard();
				var newOption7 = new Option('<s:property value="selectedWard"/>','<s:property value="farmer.ward"/>',false, false);
				$('#ward').append(newOption7).trigger('change');	    
				$('#ward').val('<s:property value="farmer.ward"/>').select2();
				 listWard1();
				
				 var newOption8 = new Option('<s:property value="selectedWard1"/>','<s:property value="farmer.village"/>',false, false);
				$('#ward1').append(newOption8).trigger('change');	    
				$('#ward1').val('<s:property value="farmer.village"/>').select2();
			
				/* $('.hscode').attr("disabled", true);
				$('.hscode').attr('readonly', true);
				 *///$('.inspDet').attr("disabled", true);
			
			
			}
		
		$('#buttonAdd1,#buttonUpdate').on('click', function (event) {  
			 event.preventDefault();
		    	$("#buttonAdd1").prop('disabled', true);
				$("#buttonUpdate").prop('disabled', true);
				var hit = true;
			if(!validateAndSubmit("target",url)){
			//	alert("At 67****");
				
				$("#buttonAdd1").prop('disabled', false);
			}
			$("#buttonAdd1").prop('disabled', false);
			$("#buttonUpdate").prop('disabled', false);
			});
		
		if (layOut == 'publiclayout') {
			$("#canBt").addClass('hide');
		}
	});
	 
	
	
	
	
function onCancel(){
	window.location.href="<s:property value="redirectContent" />";
}

function setFiles(val) {
	
	if (val != null && val != '') {
		$('#fphoto').val(val);
		//alert("At 89*******"+val);
		/*var estt = val.split('.').pop().toLowerCase();
		$('#fileType').val(estt);
		if (estt == 'PNG') {
			$('#fileType').val(1);
		} else if (estt == 'JPEG') {
			$('#fileType').val(2);
		}*/
	}
}function setFphoto1(val) {
	
	if (val != null && val != '') {
		$('#nidPhoto').val(val);
		//alert("At 89*******"+val);
		/*var estt = val.split('.').pop().toLowerCase();
		$('#fileType').val(estt);
		if (estt == 'PNG') {
			$('#fileType').val(1);
		} else if (estt == 'JPEG') {
			$('#fileType').val(2);
		}*/
	}
}

function popDownload(type) {
	document.getElementById("loadId").value = type;

	$('#fileDownload').submit();

}




function listState(){ 
	var countryName =$('#country').val();
	$.ajax({
		 type: "POST",
       async: false,
       url: "exporterRegistration_populateStateList.action",
       data: {countryName : countryName,command:command},
       success: function(result) {
       insertOptions("state", $.parseJSON(result));
    	return true;
       }
    });
}
function listViallge(){ 
	var stateName =$('#state').val();
		$.ajax({
		 type: "POST",
       async: false,
       url: "exporterRegistration_populatelocalitiesList.action",
       data: {stateName : stateName,command:command},
       success: function(result) {
       insertOptions("village", $.parseJSON(result));
    	return true;
       }
    });
}

function listWard(){ 
	
	
	
	var village =$('#village').val();
	//alert("In list ward village==>"+village);
	
	$.ajax({
		 type: "POST",
       async: false,
       url: "exporterRegistration_populateWardList.action",
       data: {village : village,command:command},
       success: function(result) {
       insertOptions("ward", $.parseJSON(result));
    	return true;
       }
    });
}

function listWard1(){ 
	
	
	var selectedWard1 =$('#ward').val();
	//alert("listWard1==>"+selectedWard1);
	
	$.ajax({
		 type: "POST",
       async: false,
       url: "exporterRegistration_populateVillage2.action",
       data: {selectedWard1 : selectedWard1,command:command},
       success: function(result) {
       insertOptions("ward1", $.parseJSON(result));
    	return true;
       }
    });
}
function checkDuplicatePhone(){ //id="natiId"
	var phn =$('#phn').val();
///	alert("At 189*****"+phn);
	$.ajax({
		 type: "POST",
       async: false,
       url: "farmer_checkDuplicatePhone.action",
       data: {phn : phn,command:command},
       success: function(result) {
    	//var es   
       
       alert("At 195*****"+result.phn+"######"+result);
       //if('<s:property value="farmer.phoneNo"/>'!=null && '<s:property value="farmer.phoneNo"/>'==''){
    	   if(result.phn==phn){
    		   alert("DuplicTE PHONE NO");
       }else{
    	   
    	   alert("New PHONE NO");
       }
    	return true;
       }
    });
}

</script>


<body>

	
	<s:form id="fileDownload" action="user_populateDownload">
	<s:hidden id="loadId" name="idd" />
</s:form>
	
	

	<s:form name="form" cssClass="fillform" method="post" id="target"
		enctype="multipart/form-data">

		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden key="farmer.id" class="uId" />
			<s:hidden id="fphoto" name="fphoto" />
			<s:hidden id="fphoto1" name="fphoto1" />
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
					<label for="txt"> <s:property
							value="%{getLocaleProperty('farmer.fname')}" /> <sup
						style="color: red;">*</sup>
					</label>
					<div class="form-element">
						<s:textfield id="firstName" name="farmer.farmerName" theme="simple"
							maxlength="50" cssClass="upercls form-control"  />
					</div>
				</div>
			<s:if test="command =='update'">
				<div class="flexform-item">
					<label for="txt"> <s:property
							value="%{getLocaleProperty('farmer.fcode')}" /> <sup
						style="color: red;">*</sup>
					</label>
					<div class="form-element">
						<s:textfield id="firstName" name="farmer.fcode" theme="simple"
							maxlength="50" cssClass="upercls form-control" readonly="true" />
					</div>
				</div>
			 </s:if>
			 
			 	<div class="flexform-item">
	<label for="txt"><s:text name="exporterRegistr.gender" /> <sup
		style="color: red;">*</sup></label>
	<div class="form-element">
		<s:radio list="genderStatusMap" id="quality"
			name="farmer.gender" />
	</div>
</div>


<div class="flexform-item">
					<label for="txt"> <s:property
							value="%{getLocaleProperty('farmer.fage')}" /> <sup
						style="color: red;">*</sup>
					</label>
					<div class="form-element">
						<s:textfield id="firstName" name="farmer.age" theme="simple"
							maxlength="50" cssClass="upercls form-control" 
							onkeypress="return isNumber(event)" />
					</div>
				</div>
				
				<div class="flexform-item">
		<label for="txt"><s:text name="export.nationalId" /></label>
		<div class="form-element">
			<s:textfield name="farmer.nid" theme="simple" id="natiId"
				cssClass="lowercase form-control" maxlength="25"  onkeypress="return isNumber(event)" />
		</div>
	</div>
				
<div class="flexform-item">
		<label for="txt"><s:text name="exporterRegistr.phn" /><sup
			style="color: red;">*</sup> </label>
		<div class="form-element">
			<s:textfield name="farmer.phoneNo" theme="simple" id="phn"
				cssClass="lowercase form-control" maxlength="10"
				onkeypress="return isNumber(event)"  onblur="checkDuplicatePhone(this.value)"/>
			
		</div>
	</div>
	<div class="flexform-item">
		<label for="txt"><s:text name="export.cropcat" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
			<s:select class="form-control  select2 " 
			id="cropcategory"
				name="farmer.cropCategory" listKey="key" listValue="value"
				list="cropCat"  />
		</div>
	</div>
		<div class="flexform-item">
		<label for="txt"><s:text name="farmer.fcropName" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
			<s:select name="productId" list="productList"
				 listKey="id" headerKey="" headerValue="%{getText('txt.select')}"
				listValue="name" id="procurementProductList"
				class="form-control  select2 "  />
				
				
		</div>
	</div>
	
	
	
	
	<s:if test="command =='update'">
			
	<div class="flexform-item">
		<label for="txt"><s:text name="export.crophs" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
			<s:textfield name="cropHsCode" theme="simple"  id="hscode"
				cssClass="lowercase form-control" maxlength="25"  readonly="true" />  
		</div>
	</div>
	</s:if>
	<div class="flexform-item">
			<label for="txt"><s:text name="country.name" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
			<s:select class="form-control  select2" list="countries"
				headerKey="" theme="simple" name="selectedCountry"
				headerValue="%{getText('txt.select')}"
				onchange="listState(this.value)"
				id="country" />
					</div>
</div>
			

			

	 		<div class="flexform-item">
		<label for="txt"><s:text name="county.name" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
			<s:select class="form-control  select2" list="{}"
				headerKey="" theme="simple" name="selectedState"
				headerValue="%{getText('txt.select')}" Key="id" Value="name"
				id="state"
				onchange="listViallge(this.value)"/>
				
		</div>
	</div>
	
	
	
	
<div class="flexform-item">
		<label for="txt"><s:text name="subcountry.name" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
			<s:select class="form-control  select2" list="{}"
				headerKey="" theme="simple" name="selectedLocality"
				headerValue="%{getText('txt.select')}" Key="id" Value="name"
				id="village"
				onchange="listWard(this.value)" />
		</div>
	</div>
<div class="flexform-item">
		<label for="txt"><s:text name="ward.name" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
			
				<s:select class="form-control  select2" list="{}" headerKey=""
				theme="simple" name="selectedWard"
				headerValue="%{getText('txt.select')}" Key="id" Value="name"
				id="ward" onchange="listWard1(this.value)" />
		</div>
	</div>
 <div class="flexform-item">
		<label for="txt"><s:text name="village.name" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
			<s:select class="form-control  select2" list="{}" headerKey=""
				theme="simple" name="selectedWard1"
				headerValue="%{getText('txt.select')}" Key="id" Value="name"
				id="ward1" />
		</div>
	</div>
	
				<div class="flexform-item">
						<label for="txt"><s:text name="farmer.photo" /><sup
							style="color: red;">*</sup><font color="red"><s:text
									name="%{getLocaleProperty('imageValidation')}" /></font></label>
						<div class="form-element">
							<s:file name="files" id="files" cssClass="form-control"
								onchange="setFiles(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF'])" />
								<s:hidden name="fphoto"  id="fphoto"/>
							<s:if test="command=='update'">
								<button type="button" class="fa fa-download"
									onclick="popDownload(<s:property value="farmer.farmerPhoto"/>)">
							</s:if>
								
								
						</div>
					</div>
					
				
					
					
					
					<div class="flexform-item">
						<label for="txt"><s:text name="farmer.nid" /><sup
							style="color: red;">*</sup><font color="red"><s:text
									name="%{getLocaleProperty('imageValidation')}" /></font></label>
						<div class="form-element">
							<s:file name="fphoto1" id="fphoto1" cssClass="form-control"
								onchange="setFphoto1(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF'])" />
								<s:hidden name="nidPhoto"   id="nidPhoto"/>
							<s:if test="command=='update'">
								<button type="button" class="fa fa-download"
									onclick="popDownload(<s:property value="farmer.photoNid"/>)">
							</s:if>
						</div>
					</div>	
					
	<div class="formContainerWrapper companyPart">
				<h2>
					<s:text name="info.geo" />
				</h2>				
	<div class="flexform-item">
		<label for="txt"><s:text name="farmer.lat" /><sup
			style="color: red;">*</sup> </label>
		<div class="form-element">
			<s:textfield name="farmer.lat" theme="simple"
				cssClass="lowercase form-control" maxlength="10"
				onkeypress="return isNumber(event)" />
		</div>
	</div><div class="flexform-item">
		<label for="txt"><s:text name="farmer.long" /><sup
			style="color: red;">*</sup> </label>
		<div class="form-element">
			<s:textfield name="farmer.flong" theme="simple"
				cssClass="lowercase form-control" maxlength="10"
				onkeypress="return isNumber(event)" />
		</div>
	</div>				
					
	<div class="flexform-item">
		<label for="txt"><s:text name="exporterRegistr.email" /><sup
			style="color: red;">*</sup> </label>
		<div class="form-element">
			<s:textfield name="farmer.emailId" theme="simple"
				cssClass="lowercase form-control"  />
		</div>
	</div>	 
	
	<div class="formContainerWrapper companyPart">
				<h2>
					<s:text name="info.socio" />
				</h2>				

				
	<div class="flexform-item">
		<label for="txt"><s:text name="farmer.family" /><sup
			style="color: red;">*</sup> </label>
		<div class="form-element">
			<s:textfield name="farmer.noOfFamilyMember" theme="simple"
				cssClass="lowercase form-control" maxlength="10"
				onkeypress="return isNumber(event)" />
		</div>
	</div><div class="flexform-item">
		<label for="txt"><s:text name="farmer.adult" /><sup
			style="color: red;">*</sup> </label>
		<div class="form-element">
			<s:textfield name="farmer.adultAbove" theme="simple"
				cssClass="lowercase form-control" maxlength="10"
				onkeypress="return isNumber(event)" />
		</div>
	</div>	
	<div class="flexform-item">
		<label for="txt"><s:text name="farmer.school" /><sup
			style="color: red;">*</sup> </label>
		<div class="form-element">
			<s:textfield name="farmer.schoolGoingChild" theme="simple"
				cssClass="lowercase form-control"  onkeypress="return isNumber(event)" />
		</div>
	</div>			
					
	<div class="flexform-item">
		<label for="txt"><s:text name="farmer.childBelow" /><sup
			style="color: red;">*</sup> </label>
		<div class="form-element">
			<s:textfield name="farmer.childBelow" theme="simple"
				cssClass="lowercase form-control" onkeypress="return isNumber(event)" />
		</div>
	</div>	 
	
	
<div class="flexform-item">
		<label for="txt"><s:text name="farmer.edu" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
			<s:select class="form-control  select2" id="industry"
				name="farmer.hedu" listKey="key" listValue="value"
				list="educationList" headerKey=" "
				headerValue="%{getText('txt.select')}" />
		</div>
	</div>
<div class="flexform-item">
		<label for="txt"><s:text name="farmer.asset" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
			
			<s:select class="form-control  select2 " id="asset"
				name="farmer.asset" listKey="key" listValue="value"
				list="assetList"   />
				
				
		
		</div>
	</div>
			<div class="flexform-item">
	<label for="txt"><s:text name="farmer.house" /> <sup
		style="color: red;">*</sup></label>
	<div class="form-element">
		<s:radio list="yesNoMap" id="quality"
			name="farmer.house" />
	</div>
</div>

<div class="flexform-item">
		<label for="txt"><s:text name="farmer.owner" /><sup
			style="color: red;">*</sup></label>
		<div class="form-element">
		
		
			<s:select class="form-control  select2 " id="ownership"
				name="farmer.ownership" listKey="key" listValue="value"
				list="ownerTypeList"    />
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