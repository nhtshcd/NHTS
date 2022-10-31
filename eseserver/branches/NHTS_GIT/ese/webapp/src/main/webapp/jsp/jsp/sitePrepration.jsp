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
var layOut='<%=request.getParameter("layoutType")%>	';
	jQuery(document)
			.ready(
					function() {
						$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
						if (url == null || url == undefined || url == ''|| url == 'null') {
							url = 'sitePrepration_';
						}
						
						if(command=='create'){
							loadSprayFarmer();	
						}
						
						if(command=='create'){
						$('input[name="env"][value="' +0+ '"]').prop('checked', true);
						$('input[name="soc"][value="' +0+ '"]').prop('checked', true);
						$('input[name="soi"][value="' +0+ '"]').prop('checked', true);
						$('input[name="wat"][value="' +0+ '"]').prop('checked', true);
						}
						
						var f1=$('input:radio[name="sitePrepration.environmentalAssessment"]:checked').val();
						//var f1="";
						var className = $('#button1').attr('class').split(' ')[1];	
						fun1(f1,className);						
						$("#buttonAdd1").on('click', function (event) {  
							event.preventDefault();
							$("#buttonAdd1").prop('disabled', true);
							$("#buttonUpdate").prop('disabled', true);
							if(!validateAndSubmit("target1","sitePrepration_")){
								$("#buttonAdd1").prop('disabled', true);
							}
							$("#buttonAdd1").prop('disabled', false);
							$("#buttonUpdate").prop('disabled', false);
						
						});
						if(command=='update'){
							$('#country').change();
							
							loadSprayFarmer('<s:property value="selectedVillage"/>');
							var env='<s:property value="env"/>';
							var socr='<s:property value="soc"/>';
							var soia='<s:property value="soi"/>';
							var waterr1='<s:property value="wat"/>';
							var envr1='<s:property value="envr"/>';
							var socr2='<s:property value="socr"/>';
							var soir3='<s:property value="soir"/>';
							var watr4='<s:property value="watr"/>';
							 if(env=="1"){
								fun1(env,'partcondet1');	
							}
							 if(socr=="1"){
									fun1(socr,'partcondet2');	
								}  
							 if(soia=="1"){
									fun1(soia,'partcondet3');
								}
							
							  if(waterr1=="1"){
								 fun1(waterr1,'partcondet4');
								}  
							var newOption = new Option('<s:property value="selectedFarmer"/>','<s:property value="sitePrepration.farmer"/>',false, false);
							if($('#farmer').val()==null || $('#farmer').val().trim()==''){
								$('#farmer').append(newOption).trigger('change');	    
							}
						
						
							var newOption2 = new Option('<s:property value="selectedFarm"/>','<s:property value="sitePrepration.farm"/>',false, false);
							$('#farm').val('<s:property value="selectedFarm"/>').select2().trigger('change');	    
							if($('#farm').val()==null || $('#farm').val().trim()==''){
								$('#farm').append(newOption2).trigger('farm');	    
							}
						
							 var cropDet='<s:property value="selectedProduct" />';
								$('#crop').val(cropDet).trigger('change');
								
							listFarmCropsBlock("<s:property value='sitePrepration.farm.id'/>");
							$("#block").val("<s:property value='sitePrepration.id'/>").select2().change();
							
						}
						
});
	
	 function fun1(val,className) {
		if (val == "1"){
			$('.' + className).removeClass('hide');
		}
		else{
				$('.' + className).addClass('hide');
		}
	} 
	function loadSprayFarmer(village){
		clearElement('farmer',true);
	
		var insType=2;		
		$.ajax({
					 type: "POST",
			        async: false,
			        url: "sitePrepration_populateFarmerByAuditRequest.action",
			        data: {inspectionType:insType,selectedVillage:village},
			        success: function(result) {
			        	insertOptions("farmer", $.parseJSON(result));
			        	if('<s:property value="selectedFarmer"/>'!=null && '<s:property value="selectedFarmer"/>'!=''){
							if(village == '<s:property value="selectedVillage"/>'){
								
							$('#farmer').val('<s:property value="selectedFarmer"/>').select2().change();
							
							}
						}
			        }
				});
	
	}
		function listFarm(call) {
		var selectedPro = call;	
		clearElement('farm',true);
		if(!isEmpty(selectedPro)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "sitePrepration_populateFarm.action",  
		         data: {selectedFarmer : selectedPro},
		         success: function(result) {
		         insertOptions("farm", $.parseJSON(result));
		         if('<s:property value="selectedFarm"/>'!=null && '<s:property value="selectedFarm"/>'!=''){
						if(selectedPro == '<s:property value="selectedFarmer"/>'){
							
						$('#farm').val('<s:property value="selectedFarm"/>').select2().change();
						
						}
					}
		        }
			});
}
}	
		function setFiles(val) {
			if (val != null && val != '') {
				$('#photoear').val(val);
			}
		}

			function setFilesSocr(val) {
				if (val != null && val != '') {
					$('#photosocr').val(val);
					}
			}
			function setFilesSar(val) {
	
				if (val != null && val != '') {
					$('#photosar').val(val);
				}
	}
				function setFilesWar(val) {
						if (val != null && val != '') {
							$('#photowar').val(val);
}
}
			function onCancel() {
				window.location.href = "<s:property value="redirectContent" />";
}
			function popDownload(type) {
				document.getElementById("loadId").value = type;
				$('#fileDownload').submit();
}
			
			
			function listFarmCropsBlock(farmId){
				var selectedFamer=$('#farmer').val();
			
				if(!isEmpty(farmId)){
				
					$.ajax({
						 type: "POST",
				        async: false,
				        url: "sitePrepration_populatBlocks.action",
				        data: {selectedFarms : farmId,selectedFarmer:selectedFamer},
				        success: function(result) {
				        	insertOptions("block", $.parseJSON(result));
				        	var block ='<s:property value="editFarmCropId"/>';
				        	if(farmId == '<s:property value="farmCropfarmId"/>' && block!=null && block!='' && block!='null'){
				        		$('#block').val(block).select2().change();
				        	}
				        }
					});
				}else{
					$('#block').val('').change();
				}
			}

</script>
<body>
	<s:form id="fileDownload" action="user_populateDownload">
		<s:hidden id="loadId" name="idd" />
	</s:form>
	<s:form name="form" cssClass="fillform" id="target1" method="post"
		enctype="multipart/form-data">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="sitePrepration.id" class="uId" />
		</s:if>
		<s:hidden name="sitePrepration.socialRiskAssessmentReport" class="uId" />
		<s:hidden name="sitePrepration.soilAnalysisReport" class="uId" />
		<s:hidden name="sitePrepration.waterAnalysisReport" class="uId" />
		<s:hidden name="sitePrepration.environmentalAssessmentReport"
			class="uId" />
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
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.sitePrepration" />
				</h2>
				<div class="flexform">
				
				
							<div class="flexform-item">
						<label for="txt"><s:text name="country.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="countries" headerKey=""
								  theme="simple" name="selectedCountry" maxlength="20"	headerValue="%{getText('txt.select')}" Key="id" Value="name"	 						 
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
								onchange="listMunicipalities(this,'localities','city','panchayath','village');clearFarmer()" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="ward.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="{}" headerKey=""
								theme="simple" name="selectedCity" maxlength="20"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="city" onchange="listVillage(this,'city','village');clearFarmer()" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="village.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="{}" onchange="loadSprayFarmer(this.value)"
								headerKey="" theme="simple" name="selectedVillage" maxlength="20"
								headerValue="%{getText('txt.select')}" id="village" />
						</div>
					</div>
					
					
					
					
					<div class="flexform-item">
						<label for="txt"><s:text name="sitePrepration.farmer" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select name="selectedFarmer" list="{}" headerKey=""
								headerValue="%{getText('txt.select')}" listKey="key"
								listValue="value" theme="simple" id="farmer"
								onchange="listFarm(this.value);" cssClass="form-control select2" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="sitePrepration.farm" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select list="{}" headerKey="" name="selectedFarm"
								headerValue="%{getText('txt.select')}" listKey="key"
								listValue="value" theme="simple" id="farm"
								cssClass="form-control select2" onchange="listFarmCropsBlock(this.value)"/>
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('landPreparation.block')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select list="{}" headerKey=""
								headerValue="%{getText('txt.select')}" listKey="key"
								listValue="value" id="block" class="form-control select2"
								name="selectedFarmCrops" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text name="sitePrepration.crop" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select list="varietyList" headerKey="" name="selectedProduct"
								headerValue="%{getText('txt.select')}" listKey="id"
								listValue="name" theme="simple" id="crop"
								cssClass="form-control select2" />
						</div>
					</div>
					
					
					
					
					<div class="flexform-item">
						<label for="txt"><s:text name="sitePrepration.env" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:radio list="yesNoMap" id="env" name="env"
								onchange="fun1(this.value,'partcondet1')" />
						</div>
					</div>
					<div class="flexform-item partcondet1 hide " id="button1">
						<label for="txt"><s:text name="sitePrepration.reportFiles" /><sup
							style="color: red;">*</sup>&nbsp;&nbsp;<font color="red"><s:text
									name="%{getLocaleProperty('imageValidationSitepre')}" /></font></label>
						<div class="form-element">
							<s:file name="files" id="files" cssClass="form-control"
								onchange="setFiles(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF','txt','TXT','docx','DOCX','doc','DOC'])" />
							<s:hidden name="photoear" id="photoear" />
							<s:if test="command=='update'">
								<s:if test="envr!=null && envr!=''">
									<button type="button" class="fa fa-download"
										onclick="popDownload(<s:property value='envr'/>)"></button>
								</s:if>

							</s:if>
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="sitePrepration.soc" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:radio list="yesNoMap" id="social" name="soc"
								onchange="fun1(this.value,'partcondet2')" />
						</div>
					</div>
					<div class="flexform-item partcondet2  hide " id="button2">
						<label for="txt"><s:text
								name="sitePrepration.reportFilesocr" /><sup style="color: red;">*</sup>&nbsp;&nbsp;<font
							color="red"><s:text
									name="%{getLocaleProperty('imageValidationSitepre')}" /></font></label>
						<div class="form-element">
							<s:file name="filesocr" id="filesocr" cssClass="form-control"
								onchange="setFilesSocr(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF','txt','TXT','docx','DOCX','doc','DOC'])" />
							<s:hidden name="photosocr" id="photosocr" />
							<s:if test="command=='update'">
								<s:if test="socr!=null && socr!=''">
									<button type="button" class="fa fa-download"
										onclick="popDownload(<s:property value='socr'/>)"></button>
								</s:if>
							</s:if>
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="sitePrepration.soil" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:radio list="yesNoMap" id="soil" name="soi"
								onchange="fun1(this.value,'partcondet3')" />
						</div>
					</div>
					<div class="flexform-item  hide  partcondet3 " id="button3">
						<label for="txt"><s:text
								name="sitePrepration.reportFilesar" /><sup style="color: red;">*</sup>&nbsp;&nbsp;<font
							color="red"><s:text
									name="%{getLocaleProperty('imageValidationSitepre')}" /></font></label>
						<div class="form-element">
							<s:file name="filesar" id="filesar" cssClass="form-control"
								onchange="setFilesSar(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF','txt','TXT','docx','DOCX','doc','DOC']);setFilesSar(this.value);" />
							<s:hidden name="photosar" id="photosar" />
							<s:if test="command=='update'">
								<s:if test="soir!=null && soir!=''">
									<button type="button" class="fa fa-download"
										onclick="popDownload(<s:property value='soir'/>)"></button>
								</s:if>
							</s:if>
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="sitePrepration.water" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:radio list="yesNoMap" id="waterr" name="wat"
								onchange="fun1(this.value,'partcondet4')" />
						</div>
					</div>
					<div class="flexform-item   partcondet4 hide " id="button4">
						<label for="txt"><s:text
								name="sitePrepration.reportFilewar" /><sup style="color: red;">*</sup>&nbsp;&nbsp;<font
							color="red"><s:text
									name="%{getLocaleProperty('imageValidationSitepre')}" /></font></label>
						<div class="form-element">
							<s:file name="filewar" id="fileswar" cssClass="form-control"
								onchange="setFilesWar(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF','txt','TXT','docx','DOCX','doc','DOC']);setFileName(this.value)"
								value="watrfn" />
							<s:hidden name="photowar" id="photowar" />
							<s:if test="command=='update'">
								<s:if test="watr!=null && watr!=''">
									<button type="button" class="fa fa-download"
										onclick="popDownload(<s:property value='watr'/>)"></button>
								</s:if>
							</s:if>
						</div>
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