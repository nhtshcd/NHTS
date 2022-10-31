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
		$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
			url = 'sorting_';
			command ="<s:property value='command'/>";
		
		if(command=='create'){
		
			loadSprayFarmer();	
		}
		
		$("#buttonAdd1").on('click', function(event) {
			event.preventDefault();
			$("#buttonAdd1").prop('disabled', true);
			if (!validateAndSubmit("sortingForm", "sorting_")) {
				$("#buttonAdd1").prop('disabled', false);
			}
			$("#buttonAdd1").prop('disabled', false);
		});
 
			
			if ("<s:property value='command'/>" == "update") {
				$('#country').change();
				loadFarmer('<s:property value="sorting.farmCrops.farm.farmer.village.id"/>');
				$("#farm")
						.val(
								"<s:property value='sorting.farmCrops.farm.id'/>")
						.select2();
				listFarmCropsBlock("<s:property value='sorting.farmCrops.farm.id'/>");
				$("#block")
						.val(
								"<s:property value='sorting.farmCrops.id'/>")
						.select2().change();
			}
		 
	});

	function listFarm(selectedFarmer) {
		var insType = 1;
		
		clearElement('farm',true);	
		 
		
		if (!isEmpty(selectedFarmer)) {
			$
					.ajax({
						type : "POST",
						async : false,
						url : "sorting_populateFarmwithplanting.action",
						data : {
							selectedFarmer : selectedFarmer,
							inspectionType : insType
						},
						success : function(result) {
							insertOptions("farm", $.parseJSON(result));
							var pFarm = '<s:property value="sorting.farmCrops.farm.id"/>';
							
							if (selectedFarmer== '<s:property value="sorting.farmCrops.farm.farmer.id"/>' && pFarm != null && pFarm != '' && pFarm != 'null') {
								$('#farm').val(pFarm).select2().change();
							}
							
						}
					});
		}
	}

	function listFarmCropsBlock(farmId) {
		
		var selectedFamer = $('#farmer').val();
		clearElement('block',true);
		$("#cCenter").val("");
		if (!isEmpty(farmId)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "sorting_populatBlockWithPlanting.action",
				data : {
					selectedFarm : farmId,
					selectedFarmer : selectedFamer
				},
				success : function(result) {
					insertOptions("block", $.parseJSON(result));
					var block = '<s:property value="sorting.farmCrops.id"/>';
					if (farmId=='<s:property value="sorting.farmCrops.farm.id"/>' && block != null && block != '' && block != 'null') {
						$('#block').val(block).change();
					}
	
				 
					
				}
			});
		}

		var farmName = $("#farm option:selected").text();
		if(farmName!='Select'){
		$("#cCenter").val(farmName);
		}
	}

	/* function populateBlockDetail(id) {
		$('#cropPlanting').val("");			
		$('#blockId').val("");
		$("#dateHarvested").val("");
		$("#qtyHarvested").val("");
		$('#cropName').val("");			
		$('#variety').val("");
		if (!isEmpty(id)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "sorting_populateBlockDetails.action",
				data : {
					selectedFarmer : id
				},
				success : function(result) {
						$('#cropPlanting').val(result.plantingId);
						$('#cropName').val(result.variety);			
						$('#variety').val(result.grade);		
					$('#blockId').val(result.blockId);
				}
			});
		}
	} */
	
	function populateBlockDetail(cropid){
		if (cropid == ' ') {
			 $('#varietyName option:not(:first)').remove();
		}
			$.ajax({
				type : "POST",
				async : false,
				url : "sorting_populateBlockDetailsForScoutingAndSpraying.action",
				data : {
					selectedFarmer : cropid
				},
				success : function(result) {
					$('#cropPlantingId').val(result.plantingId);
					$('#blockId').val(result.blockId);
					$('#cropName').val(result.variety);
					$('#variety').val(result.grade);
				}
			});	
		
	}
	
	function populateHarvestDetails(id) {
		$('#dateHarvested').val('');
		$('#qtyHarvested').val('');
		
		if (!isEmpty(id)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "sorting_populateHarvestDetails.action",
				data : {
					selectedFarmCropsId : id
				},
				success : function(result) {
					$('#dateHarvested').val(result.dateHarvested);
					$('#qtyHarvested').val(result.qtyHarvested);
					if("<s:property value='command' />"=="update"){
						if("<s:property value='sorting.planting.id'/>" == id){
							$("#qtyHarvested").val("");
					var harqty = parseFloat(result.qtyHarvested) ? parseFloat(result.qtyHarvested) : 0;
					var calcharqty = parseFloat(harqty) + parseFloat("<s:property value='netQty'/>") + parseFloat("<s:property value='qtyRejected'/>");
					$("#qtyHarvested").val(calcharqty);
					}
					}
				}
			});
		}
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
	
/* 	function populatePlanting(id){	
		if (!isEmpty(id)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "harvest_populatePlantinglist.action",
			data : {
				selectedFarm : id
			},
			success : function(result) {	
				insertOptions("cropPlanting", $.parseJSON(result));
			}
		});
		}
	} */
	
	function populatePlanting(val){
		clearElement('plantingId',true);
		var selectedBlock=val;
		if(!isEmpty(selectedBlock)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "sorting_populatePlantingWithHarvest.action",
		        data: {selectedBlock : selectedBlock},
		        success: function(result) {
		        	insertOptions("plantingId", $.parseJSON(result));
		        	if(command=='update'){
		        		var plantingfar ='<s:property value="sorting.planting.id"/>';
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
	
</script>
<body>
	<s:form name="sortingForm" cssClass="fillform" method="post"
		action="sorting_%{command}" id="sortingForm">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="sorting.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="error">
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.sorting" />
				</h2>
				<div class="flexform">

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
							<s:select class="form-control  select2" list="{}" headerKey=""
								theme="simple" name="selectedState"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="state" maxlength="20"
								onchange="listLocality(this,'state','localities','city','panchayath','village');" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="subcountry.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="{}" headerKey=""
								theme="simple" name="selectedLocality"
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
								id="city" onchange="listVillage(this,'city','village');" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="village.name" /></label>
						<div class="form-element">
							<s:select class="form-control  select2" list="{}" headerKey=""
								theme="simple" name="selectedVillage" maxlength="20"
								onchange="loadFarmer(this.value)"
								headerValue="%{getText('txt.select')}" id="village" />
						</div>
					</div>



					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('sorting.farmer')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:select class="form-control select2" id="farmer"
								name="sorting.farmCrops.farm.farmer.id" listKey="key"
								listValue="value" list="{}" headerKey=" "
								headerValue="%{getText('txt.select')}"
								onchange="listFarm(this.value);" />
						</div>
					</div>

					<div class="flexform-item ">
						<label for="txt"><s:property
								value="%{getLocaleProperty('sorting.farm')}" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:select class="form-control select2" id="farm"
								name="sorting.farmCrops.farm.id" listKey="key" listValue="value"
								list="{}" headerKey="" headerValue="%{getText('txt.select')}"
								onchange="listFarmCropsBlock(this.value)" />
						</div>
					</div>
					<%-- <div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('sorting.block')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select list="{}" headerKey=""
								headerValue="%{getText('txt.select')}" listKey="key"
								listValue="value" id="block" class="form-control select2"
								name="sorting.farmCrops.id"
								onchange="populateBlockDetail(this.value);populatePlanting(this.value);" />
						</div>
					</div> --%>
					<%-- <div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('sorting.cropPlanting')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select list="{}" headerKey=""
								headerValue="%{getText('txt.select')}" listKey="key"
								listValue="value" id="cropPlanting" class="form-control select2"
								name="harvest.planting.id"
								onchange="populateHarvestDetails(this.value)" />
						</div>
					</div> --%>

					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('sorting.block')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select list="{}" headerKey=""
								headerValue="%{getText('txt.select')}" listKey="key"
								listValue="value" id="block" class="form-control select2"
								name="sorting.farmCrops.id"
								onchange="populatePlanting(this.value);" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="Planting ID" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select id="plantingId" name="sorting.planting.id"
								listKey="key" listValue="value" list="{}" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="populateBlockDetail(this.value);populateHarvestDetails(this.value)"
								cssClass="form-control select2" />
						</div>
					</div>

					<%-- <div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('sorting.blockId')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="blockId" theme="simple" size="20"
								readonly="true" cssClass="lowercase form-control" id="blockId" />
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
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.crop')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield theme="simple" readonly="true"
								cssClass="form-control" id="cropName" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.variety')}" /> <sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield theme="simple" readonly="true"
								cssClass="form-control" id="variety" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('sorting.cCenter')}" /></label>
						<div class="form-element">
							<s:textfield name="sorting.farmCrops.farm.farmName"
								theme="simple" size="20" readonly="true"
								cssClass="lowercase form-control" id="cCenter" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('sorting.dateHarvested')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="dateHarvested" theme="simple" size="20"
								readonly="true" cssClass="lowercase form-control"
								id="dateHarvested" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('sorting.qtyHarvested')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="qtyHarvested" theme="simple" size="20"
								readonly="true" cssClass="lowercase form-control"
								id="qtyHarvested" />
						</div>
					</div>




					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('sorting.qtyRejected')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="qtyRejected" theme="simple" size="20"
								cssClass="lowercase form-control" id="qtyRejected"
								onkeypress="return isDecimal1(event,this)" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('sorting.netQty')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="netQty" theme="simple" size="20"
								cssClass="lowercase form-control" id="netQty"
								onkeypress="return isDecimal1(event,this)" />
						</div>
					</div>


					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('sorting.truckType')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="sorting.truckType" theme="simple" size="20"
								cssClass="lowercase form-control" id="truckType" maxlength="20" />
						</div>
					</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('sorting.truckNumber')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="sorting.truckNo" theme="simple" size="20"
								cssClass="lowercase form-control" id="truckNo" maxlength="20" />
						</div>
					</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('sorting.driverName')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="sorting.driverName" theme="simple" size="20"
								cssClass="lowercase form-control" id="driverName" maxlength="20" />
						</div>
					</div>
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('sorting.driverContact')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="sorting.driverCont" theme="simple" size="20"
								cssClass="lowercase form-control" maxlength="20" id="driverCont"
								onkeypress="return isNumber(event,this);" />
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