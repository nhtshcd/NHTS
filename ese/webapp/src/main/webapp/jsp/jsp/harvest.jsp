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
	$(document)
			.ready(
					function() {
						url='harvest_';
						var command = '<s:property value="command"/>';
						
						if(command=='create'){
							loadSprayFarmer();	
						}
						$("#buttonAdd1").on('click', function(event) {
							event.preventDefault();
							$("#buttonAdd1").prop('disabled', true);
							if (!validateAndSubmit("harvestForm", "harvest_")) {
								$("#buttonAdd1").prop('disabled', false);
							}
							$("#buttonAdd1").prop('disabled', false);
						});

						if ("<s:property value='command'/>" == "update") {
							
							$('#country').change();
						
							 populateNOFandNOS("<s:property value='harvest.harvestType'/>",false)
							
							
						}
					});


	function listFarm(selectedFarmer) {
		var insType = 1;
			
			clearElement('farm',true);
	
		if (!isEmpty(selectedFarmer)) {
			$.ajax({
						type : "POST",
						async : false,
						url : "harvest_populateFarmwithplanting.action",
						data : {
							selectedFarmer : selectedFarmer,
							inspectionType : insType
						},
						success : function(result) {
							insertOptions("farm", $.parseJSON(result));
						
							var pFarm = '<s:property value="harvest.planting.farmCrops.farm.id"/>';
							var pFarmer = '<s:property value="harvest.planting.farmCrops.farm.farmer.id"/>';
							if (pFarm != null && pFarm != '' && pFarm != 'null' && pFarmer==selectedFarmer) {
								$('#farm').val(pFarm).select2().change();
							
							}
							
						}
					});
		}
	}

	function listFarmCropsBlock(farmId) {
		var selectedFamer = $('#farmer').val();
		clearElement('block',true);
		//$('#cropPlanting').val("");			
		$('#produceId').val("");
		$('#cropName').val("");			
		$('#variety').val("");
		if (!isEmpty(farmId)) {
			$.ajax({
				type : "POST",
				async : false,
				url : "harvest_populatBlockWithPlanting.action",
				data : {
					selectedFarm : farmId,
					selectedFarmer : selectedFamer
				},
				success : function(result) {
					insertOptions("block", $.parseJSON(result));
					var block = '<s:property value="harvest.planting.farmCrops.id"/>';
					var blockFarm = '<s:property value="harvest.planting.farmCrops.farm.id"/>';
					if (block != null && block != '' && block != 'null' && blockFarm==farmId) {
						$('#block').val(block).select2().change();
					}
			
					
			    	
				}
			});
		}
	}

	function populateBlockDetail(id){
			
		$('#produceId').val('');	
		if (!isEmpty(id)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "harvest_populateBlockDetails.action",
			data : {
				selectedFarmer : id
			},
			success : function(result) {
						
				$('#produceId').val(result.blockId);	
			}
		});
		}
		
	}
	
	function populatePlanting(id){	
		clearElement('cropPlanting',true);
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
				
				var block = '<s:property value="harvest.planting.id"/>';
				var blockFarm = '<s:property value="harvest.planting.farmCrops.id"/>';
				if (block != null && block != '' && block != 'null' && blockFarm==id) {
					$('#cropPlanting').val(block).select2().change();
				}
			}
		});
		}
		
	}
	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>";
	}
	function populateNOFandNOS(value,isEdit){
		 $(".nowaight").addClass('hide');
		 $(".noStems").addClass('hide');
		 $(".harType").addClass('hide');
		 if(isEdit){
			 $('#noStems').val('');
		 }

		 if(value=='1'){
			 $(".harType").removeClass('hide');
			$('#noStems').unbind();
			 $(".nowaight").addClass('hide');
			 $(".noStems").removeClass('hide');
				$( "#noStems" ).keypress(function( evt ) {
					evt = (evt) ? evt : window.event;
					var charCode = (evt.which) ? evt.which : evt.keyCode;
					if (charCode > 31 && (charCode < 48 || charCode > 57)) {
						return false;
					}
					return true;
				});
			 
		 }else  if(value=='0'){
			 $(".harType").removeClass('hide');
			 $("#noStems").unbind();
			 $(".noStems").addClass('hide');
			 $(".nowaight").removeClass('hide');
			 $( "#noStems" ).keypress(function( evt ) {
				 
				 var charCode = (evt.which) ? evt.which : event.keyCode;
				 var vardec =0;
				 var varLenh =$(this).val().split('.')[0].length;
				 if($(this).val().indexOf(".")>=0){
					 vardec =$(this).val().split('.')[1].length;
				 }
					        if (
					              // Check minus and only once.
					            (charCode != 46 ||  $(this).val().indexOf('.') != -1  ) &&    // Check for dots and only once.
					            (charCode < 48 || charCode > 57)){
					        	return false;
					        	
					        }else if(charCode != 46){
					        	varLenh=varLenh+1;
					        	vardec=vardec+1;
					        }
				 if((varLenh > 7 && $(this).val().indexOf(".")<0) || ($(this).val().indexOf(".")>=0 &&  vardec > 2) ){
					 return false;
				 }
			  return true;
			  
	
				});
		 }
	}
	
	
	function populatePlantingDetail(id){
		//$('#cropPlanting').val('');		
		$('#cropName').val("");			
		$('#variety').val("");
		$('#expctdYieldsVolume').val('');
		var date = '<s:property value="date"/>';
		if (date != null && date != '' && date != 'null') {
			$('#date').val(date);
		}else{
			$('#date').val('');
		}
			
		$('#expctdYieldsVolume').val('');	
		if (!isEmpty(id)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "harvest_populatePlantingDetails.action",
			data : {
				selectedFarmer : id
			},
			success : function(result) {
				//$('#cropPlanting').val(result.plantingId);			
				$('#expctdYieldsVolume').val(result.expYe);		
				$('#cropName').val(result.variety);			
				$('#variety').val(result.grade);			
			}
		});
		}
		
	}
	
	function populateValidateMaxSprayingPHIDate(val){
		var selectedFarmCrops = $('#cropPlanting').val();
			if(!isEmpty(selectedFarmCrops) && !isEmpty(val)){
				$.ajax({
					type : "POST",
					async : false,
					url : "harvest_populateValidateMaxSprayingPHIDate.action",
					data : {
						selectedFarmCropsId : selectedFarmCrops,
						date : val
					},
					success : function(result) {
						if(!isEmpty(result.maxDateVal)){
						$('#validateError').text(result.maxDateVal);
						}else{
							$('#validateError').text("");
						}
					}
				});
				}
		}

	
</script>
<body>
	<s:form name="harvestForm" cssClass="fillform" method="post"
		action="harvest_%{command}" id="harvestForm">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="harvest.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="error">
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.harvest" />
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
								value="%{getLocaleProperty('harvest.farmer')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:select class="form-control select2" id="farmer"
								name="harvest.planting.farmCrops.farm.farmer.id" listKey="key"
								listValue="value" list="{}" headerKey=" "
								headerValue="%{getText('txt.select')}"
								onchange="listFarm(this.value);" />
						</div>
					</div>

					<div class="flexform-item ">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.farm')}" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:select class="form-control select2" id="farm"
								name="harvest.planting.farmCrops.farm.id" listKey="key" listValue="value"
								list="{}" headerKey="" headerValue="%{getText('txt.select')}"
								onchange="listFarmCropsBlock(this.value);" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.block')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select list="{}" headerKey=""
								headerValue="%{getText('txt.select')}" listKey="key"
								listValue="value" id="block" class="form-control select2"
								name="harvest.planting.farmCrops.id"
								onchange="populateBlockDetail(this.value);populatePlanting(this.value)" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.cropPlanting')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select list="{}" headerKey=""
								headerValue="%{getText('txt.select')}" listKey="key"
								listValue="value" id="cropPlanting" class="form-control select2"
								name="harvest.planting.id"
								onchange="populatePlantingDetail(this.value)" />
						</div>
					</div>
					
					<%-- <div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.cropPlanting')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="cropPlanting" theme="simple" readonly="true"
								cssClass="form-control" id="cropPlanting" />
						</div>
					</div> --%>

					<div class="flexform-item hide">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.produceId')}" /> <sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="harvest.produceId" theme="simple"
								readonly="true" cssClass="form-control" id="produceId" />
						</div>
					</div>

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
								value="%{getLocaleProperty('harvest.date')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="date" id="date" theme="simple" maxlength="10"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="date-picker form-control input-sm" onchange="populateValidateMaxSprayingPHIDate(this.value)"/>
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.harvestType')}" /> <sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:select class="form-control select2" id="harvestType"
								name="harvest.harvestType" listKey="key" listValue="value"
								list="harvestType" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="populateNOFandNOS(this.value,true);" />
						</div>
					</div>


					<div class="flexform-item harType hide">
						<label for="txt" class="noStems"> <s:property
								value="%{getLocaleProperty('harvest.noStems')}" /> <sup
							style="color: red;">*</sup>
						</label> <label for="txt" class="nowaight hide"> <s:property
								value="%{getLocaleProperty('harvest.numberOfWeights')}" /> <sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">

							<s:if test="harvest.harvestType==0">
								<s:textfield name="harvest.noStems" theme="simple" size="20"
									cssClass="lowercase form-control" id="noStems"
									value="%{getText('{0,number,#,##0.00}',{harvest.noStems})}" />
							</s:if>

							<s:else>
								<s:textfield name="harvest.noStems" theme="simple" size="20"
									cssClass="lowercase form-control" id="noStems"
									value="%{getText('{0,number,#,##0}',{harvest.noStems})}" />

							</s:else>



						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.qtyHarvested')}" /> <sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="qtyHarvested" theme="simple" size="20"
								cssClass="lowercase form-control" id="qtyHarvested"
								onkeypress="return isDecimal1(event,this)" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.yieldsHarvested')}" /> <sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="harvest.yieldsHarvested" theme="simple"
								size="20" cssClass="lowercase form-control" id="yieldsHarvested"
								onkeypress="return isDecimal1(event,this)" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.expctdYieldsVolume')}" /> <sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="harvest.expctdYieldsVolume" theme="simple"
								size="20" cssClass="lowercase form-control" readonly="true"
								id="expctdYieldsVolume" onkeypress="return isNumber(event)" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.nameHarvester')}" /> <sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="harvest.nameHarvester" theme="simple"
								size="20" cssClass="lowercase form-control" id="nameHarvester" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.harvestEquipment')}" /> <sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:select class="form-control  select2" id="harvestEquipment"
								name="harvest.harvestEquipment" listKey="key" listValue="value"
								list="getCatList(getLocaleProperty('harvestequipmentype'))"
								headerKey=" " headerValue="%{getText('txt.select')}" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.noUnits')}" /> <sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="harvest.noUnits" theme="simple" size="20"
								cssClass="lowercase form-control" id="noUnits"
								onkeypress="return isNumber(event)" />
						</div>
					</div>
					<%-- 	<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.deliveryType')}" />
								<sup style="color: red;">*</sup>
								</label>
						<div class="form-element">
							<s:textfield class="lowercase form-control" id="deliveryType"
								name="harvest.deliveryType"   />
						</div>
					</div> --%>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.packingUnit')}" /> <sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:select class="form-control select2" id="packingUnit"
								name="harvest.packingUnit" listKey="key" listValue="value"
								list="getCatList(getLocaleProperty('packingUnit'))" headerKey=""
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('harvest.observationPhi')}" /></label>
						<div class="form-element">
							<s:textfield name="harvest.observationPhi" theme="simple"
								size="20" cssClass="lowercase form-control" id="observationPhi" />
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