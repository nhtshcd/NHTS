<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
</head>
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
</head>
<script type="text/javascript">

	$(document).ready(function() {
		url='landPreparation_';
		command='<s:property value="command"/>';
		if(command=='create'){
			loadSprayFarmer();	
		}else{
			$('#country').change();
			loadFarmer('<s:property value="landPreparation.farm.farmer.village.id"/>');
			
		}
		
		$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
		$("#buttonAdd1").on('click', function(event) {
		  	event.preventDefault();			
			var lpDtl=getLandPreparationDetail();
		      
			$("#landPreparationDtl").val(lpDtl);
			$("#buttonAdd1").prop('disabled', true);
			if(!validateAndSubmit("lanpPreparationForm","landPreparation_")){
				$("#buttonAdd1").prop('disabled', false);
			}
			$("#buttonAdd1").prop('disabled', false);
		});
		
	});

	
	
	function listFarm(selectedFarmer) {	
		
		var insType=1;
		clearElement('farm',true);
		clearElement('block',true);
		   
		
		if(!isEmpty(selectedFarmer)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "landPreparation_populateFarm.action",
		        data: {selectedFarmer : selectedFarmer,inspectionType:insType},
		        success: function(result) {
		        	insertOptions("farm", $.parseJSON(result));
		        	     var pFarm ='<s:property value="landPreparation.farm.id"/>';
		        	
		        	if(selectedFarmer == '<s:property value="landPreparation.farm.farmer.id"/>' && pFarm!=null && pFarm!='' && pFarm!='null' ){
		        		$('#farm').val(pFarm).select2().change();
		        	
		        	}
		        }
			});
		}
	}	
	
	function listFarmCropsBlock(farmId){
		var selectedFamer=$('#farmer').val();
		clearElement('block',true);
		if(!isEmpty(farmId)){
		
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "landPreparation_populatBlocks.action",
		        data: {selectedFarm : farmId,selectedFarmer:selectedFamer},
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
	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>"; 
	}
	
	function addRow(rowId){
		   var rowCounter = $('#landPreparationContent tr').length-1;
		   	if(rowId!=''){
		   		$('#'+rowId).remove();
		   	}
		   	$("#validateError").text("");		   
		   	var activity = $("#activity option:selected").text();	
		   	var activityMode=$("#activityMode option:selected").text();		
			var noOfLabourers= $("#noOfLabourers").val();
		 
			if(isEmpty(activity) || activity=='Select'){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.landPreparation.activity')}" />');
				return false;
			}
			/* else if(isEmpty(activityMode) || activityMode=='Select'){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.landPreparation.activityMode')}" />');
		   		return false;
		   	} */
			else if(isEmpty(noOfLabourers)){
				$("#validateError").text('<s:property value="%{getLocaleProperty('empty.landPreparation.noOfLabourers')}" />');
		   		return false;
		   	}	
			
		   	var tableRow="<tr id=row"+(++rowCounter)+">";
		   	tableRow+="<td class='activity'>"+activity+"</td>";		   	
		  	//tableRow+="<td class='activityMode'>"+activityMode+"</td>";
			tableRow+="<td class='noOfLabourers'>"+noOfLabourers+"</td>";
			tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i>&nbsp;&nbsp;&nbsp;&nbsp;<i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteDtl("+rowCounter+")'></td>";
		   	tableRow+="</tr>";
		   	$("#landPreparationContent").append(tableRow);
		  	resetData();
		  	$("#add").attr("onclick","addRow()");
	}
	function deleteDtl(rowCounter){
	   	var id="#row"+rowCounter;
	   	$(id).remove();
	}
	function editRow(rowCounter){
	 	var id="#row"+rowCounter;
		$(id).each(function(index, value) {
			 var activity = $(this).find(".activity").text().trim();
			 var activityMode = $(this).find(".activityMode").text();
			 var noOfLabourers = $(this).find(".noOfLabourers").text();
			 
			 var activityOption = $('#activity option');
			 var activityValue = $.map(activityOption ,function(option) {
				 	if(activity==option.text)
				    	return option.value;
				});			 
			 var activityModeOption = $('#activityMode option');	
			 var activityModeValue = $.map(activityModeOption ,function(option) {
				 if(activityMode==option.text)
				    	return option.value;				    
				}); 
		    $("#activity").val(activityValue).trigger('change');
		   	$("#activityMode").val(activityModeValue).trigger('change');
			$("#noOfLabourers").val(noOfLabourers);
			
		});
		$("#add").attr("onclick","addRow('row"+rowCounter+"')");
	}

	
	function resetData(){
		$("#activity").val(null).trigger('change');
		$("#activityMode").val(null).trigger('change');
		$("#noOfLabourers").val("");	
	}

	function getLandPreparationDetail(){
		var tableBody = $("#landPreparationContent tr:nth-child(n + 2)");
		var landPreparationDtl="";	
	
		$.each(tableBody, function(index, value) {
 
            
			var activity = $(this).find(".activity").text().trim();
		  
						var activityMode = $(this).find(".activityMode").text();
						var activityOption = $('#activity option');
						if(isEmpty(activity)){
							$("#validateError").text('<s:property value="%{getLocaleProperty('empty.landPreparation.activity')}" />');
							return false;
						}
						/* else if(isEmpty(activityMode)){
							$("#validateError").text('<s:property value="%{getLocaleProperty('empty.landPreparation.activityMode')}" />');
					   		return false;
					   	}  */
						 var activityValue = $.map(activityOption ,function(option) {
							 	if(activity==option.text)
							    	return option.value;
							});			 
						 var activityModeOption = $('#activityMode option');	
						 var activityModeValue = $.map(activityModeOption ,function(option) {
							 if(activityMode==option.text)
							    	return option.value;				    
							});
						landPreparationDtl+=activityValue; //0
						landPreparationDtl+="#"+jQuery(this).find(".noOfLabourers").text(); //2
						landPreparationDtl+="#"+activityModeValue+"@";		
 
		});		
	 
		return landPreparationDtl;
	}
	
</script>
<script type="text/javascript">
$(document).ready(function() {
	if("<s:property value='command'/>"=="update"){
		$("#farmer").val("<s:property value='landPreparation.farm.farmer.id'/>").select2();
		listFarm("<s:property value='landPreparation.farm.farmer.id'/>");
		$("#farm").val("<s:property value='landPreparation.farm.id'/>").select2().change();
		listFarmCropsBlock("<s:property value='landPreparation.farm.id'/>");
		$("#block").val("<s:property value='landPreparation.id'/>").select2().change();
	}
		
	
});
</script>
<body>
	<s:form id="fileDownload" action="generalPop_populateDownload">
		<s:hidden id="loadId" name="idd" />
	</s:form>
	<s:form name="lanpPreparationForm" cssClass="fillform" method="post"
		action="lanpPreparation_%{command}" id="lanpPreparationForm">
		<s:hidden name="landPreparationDtl" id="landPreparationDtl" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="landPreparation.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="error">
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:property
										value='%{getLocaleProperty("info.landPreparation")}' />
			
				</h2>
				<div class="flexform">
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('landPreparation.date')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="date" id="date" theme="simple" maxlength="10"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="date-picker form-control input-sm" />
						</div>
					</div>
					
					
					
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
							<s:select class="form-control  select2" list="states"
								headerKey="" theme="simple" name="selectedState"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="state" maxlength="20"
								onchange="listLocality(this,'state','localities','city','panchayath','village');" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="subcountry.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="localities"
								headerKey="" theme="simple" name="selectedLocality"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="localities" maxlength="20"
								onchange="listMunicipalities(this,'localities','city','panchayath','village');" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="ward.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="city" headerKey=""
								theme="simple" name="selectedCity" maxlength="20"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="city" onchange="listVillage(this,'city','village')" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="village.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="villages" onchange="loadFarmer(this.value)"
								headerKey="" theme="simple" name="selectedVillage" maxlength="20"
								headerValue="%{getText('txt.select')}" id="village" />
						</div>
					</div>

 

					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('landPreparation.farmer')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:select class="form-control select2" id="farmer"
								name="landPreparation.farm.farmer.id" listKey="key"
								listValue="value" list="{}" headerKey=" "
								headerValue="%{getText('txt.select')}"
								onchange="listFarm(this.value);" />
						</div>
					</div>

					<div class="flexform-item ">
						<label for="txt"><s:property
								value="%{getLocaleProperty('landPreparation.farm')}" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:select class="form-control select2" id="farm"
								name="landPreparation.farm.id" listKey="key"
								listValue="value" list="{}" headerKey=" "
								headerValue="%{getText('txt.select')}"
								onchange="listFarmCropsBlock(this.value)" />
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
								name="landPreparation.farmCrops.id" />
						</div>
					</div>
					
					
					
					
					<table class="table table-bordered aspect-detail"
						id="landPreparationInfoTable">
						<thead>
							<tr>
								<th><s:property
										value="%{getLocaleProperty('landPreparation.activity')}" /></th>
								<th class="hide"><s:property
										value="%{getLocaleProperty('landPreparation.activityMode')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('landPreparation.noOfLabourers')}" /></th>
								<th style="text-align: center"><s:property
										value="%{getLocaleProperty('action')}" /></th>
							</tr>
						</thead>
						<tbody id="landPreparationContent">
							<tr>
								<td><s:select
										list="getCatList(getLocaleProperty('activityList'))"
										headerKey="" headerValue="%{getText('txt.select')}"
										listKey="key" listValue="value" id="activity"
										class="form-control select2" name="activity" /></td>
								<td class="hide"><s:select
										list="getCatList(getLocaleProperty('activityModeList'))"
										headerKey="" headerValue="%{getText('txt.select')}"
										listKey="key" listValue="value" id="activityMode"
										class="form-control select2" name="activityMode" /></td>
								<td><s:textfield name="noOfLabourers" theme="simple"
										size="20" cssClass="lowercase form-control" id="noOfLabourers"
										onkeypress="return isNumber(event)" /></td>
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
							<s:iterator value="landPreparation.landPreparationDetails"
								status="incr">
								<tr id="row<s:property	value="#incr.index" />">
									<td class="activity"><s:property
											value="%{getCatalgueNameByCode(activity)}" /></td>
									<td class="activityMode hide"><s:property
											value="%{getCatalgueNameByCode(activityMode)}" /></td>
									<td class="noOfLabourers"><s:property
											value="noOfLabourers" /></td>
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
