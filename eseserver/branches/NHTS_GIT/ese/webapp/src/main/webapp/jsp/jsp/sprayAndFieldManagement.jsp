<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType")
					: "swithlayout"%>">
</head>
<script type="text/javascript">
	var command = '<s:property value="command"/>';
	jQuery(document)
			.ready(
					function() {
						$(".breadCrumbNavigation").find('li:last').find(
								'a:first').attr("href",
								'<s:property value="redirectContent" />');
						url = 'sprayAndFieldManagement_';
						jQuery('#sMphi').prop("readonly",true);
						if (command == 'create') {
							loadSprayFarmer();
						} 

						$("#buttonAdd1").on(
								'click',
								function(event) {
									$("#deletestatus").val(0);
									$("#buttonAdd1").prop('disabled', true);
									$('#sMuom').select2({
										disabled : false
									});

									
									
									
									if (!validateAndSubmit("target1",
											"sprayAndFieldManagement_")) {
										$("#buttonAdd1")
												.prop('disabled', false);
										/* $('#sMuom').select2({
											disabled : 'readonly'
										}); */
									}
								});
						$("#buttonAdd2").on(
								'click',
								function(event) {
									$("#deletestatus").val(3);
									$("#buttonAdd2").prop('disabled', true);
									if (!validateAndSubmit("target1",
											"sprayAndFieldManagement_")) {
										$("#buttonAdd2")
												.prop('disabled', false);
									}
								});
						if (command == 'update') {
							$('#country').change();
						
						//	listFarm('<s:property value="sprayAndFieldManagement.farmCrops.farm.farmer.id"/>')

							var cropDet = '<s:property value="selectedProduct" />';
							$('#crop').val(cropDet).trigger('change');
							var varietyId = '<s:property value="sprayAndFieldManagement.variety" />';
							$(
									"#varietyName option[value='"
											+ varietyId.trim() + "']").prop(
									"selected", true).trigger('change');
							var targt = $
									.trim('<s:property value="sprayAndFieldManagement.target"/>');
							$('#target').val(
									JSON.parse(JSON.stringify(targt.replace(
											/\s/g, '').split(",")))).select2();

							loadAjaxData($('#farmer').val());

							//var agName = '<s:property value="sprayAndFieldManagement.pcbp.id" />';
							//$('#agroChemicalNamea').val(agName).trigger('change');
							var farmCropsIdForUpdate='<s:property value="sprayAndFieldManagement.farmCrops.id"/>';
							if(farmCropsIdForUpdate != null && farmCropsIdForUpdate != ''){
							populatePlanting(farmCropsIdForUpdate);
							}
							var chamicalsForUpdate='<s:property value="selectedTradeNameId"/>';
							if(chamicalsForUpdate != null && chamicalsForUpdate != ''){
								populateChamicals(chamicalsForUpdate);
							}
							
						}
						 /* $('#sMuom').select2({
							disabled : 'readonly'
						}); */ 
						
					});

	function listFarmsCropProduct(farmId) {
		var selectedFarm = farmId;
		var selectedFamer = $('#farmer').val();
		clearElement('crop', true);
		if (!isEmpty(selectedFarm)) {
			$
					.ajax({
						type : "POST",
						async : false,
						//url: "sprayAndFieldManagement_populateFarmCropsCrop.action",
						url : "sprayAndFieldManagement_populateFarmCrops.action",
						//data: {selectedFarm : selectedFarm},
						data : {
							selectedFarm : selectedFarm,
							selectedFarmer : selectedFamer
						},
						success : function(result) {
							insertOptions("crop", $.parseJSON(result));
							var sllfar = '<s:property value="sprayAndFieldManagement.farmCrops.variety.procurementProduct.id"/>';
							if (sllfar != null && sllfar != ''
									&& sllfar != 'null') {
								$('#crop').val(sllfar).change();
							}
						}
					});

		}
	}

	function listFarmsCropProduct(farmId) {
		var selectedFarm = farmId;
		var selectedFamer = $('#farmer').val();
		clearElement('varietyName', true);
		if (!isEmpty(selectedFarm)) {

			$
					.ajax({
						type : "POST",
						async : false,
						url : "sprayAndFieldManagement_populatBlockWithPlanting.action",
						data : {
							selectedFarm : selectedFarm,
							selectedFarmer : selectedFamer
						},
						success : function(result) {
							insertOptions("varietyName", $.parseJSON(result));
							var sllfar = '<s:property value="sprayAndFieldManagement.farmCrops.id"/>';
							if (selectedFarm=='<s:property value="sprayAndFieldManagement.farmCrops.farm.id"/>'  && sllfar != null && sllfar != ''
									&& sllfar != 'null') {
								$('#varietyName').val(sllfar).change();
							}
						}
					});
		}
	}

	function listFarm(call) {
		var selectedPro = call;
		clearElement('farm', true);
		if (!isEmpty(selectedPro)) {
			$
					.ajax({
						type : "POST",
						async : false,
						//url: "sprayAndFieldManagement_populateFarm.action",
						url : "sprayAndFieldManagement_populateFarmwithplanting.action",
						// data: {selectedFarm : selectedPro},
						data : {
							selectedFarmer : selectedPro
						},
						success : function(result) {
							//alert("res"+result)
							insertOptions("farm", $.parseJSON(result));

							var sllfar = '<s:property value="sprayAndFieldManagement.farmCrops.farm.id"/>';

							if (selectedPro=='<s:property value="sprayAndFieldManagement.farmCrops.farm.farmer.id"/>'  && sllfar != null && sllfar != ''
									&& sllfar != 'null') {

								$('#farm').val(sllfar).select2().change();

							}
						}
					});

		}
		//resetSelect2();
	}

	function loadAjaxData(fid) {
		$('#address').val('');
		$('#mobileNo').val('');
		if (!isEmpty(fid)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "sprayAndFieldManagement_populateFarmerData.action",
			data : {
				selectedFarmer : fid
			},
			success : function(result) {
				$('#address').val(result.address);
				$('#mobileNo').val(result.mobileNo);
			}
		});
		}
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

	function onCancel() {
		window.location.href = "<s:property value="redirectContent" />";
	}

	function popDownload(type) {
		document.getElementById("loadId").value = type;
		$('#fileDownload').submit();
	}

	function isAlphaNumber(evt) {

		evt = (evt) ? evt : window.event;
		var charCode = (evt.which) ? evt.which : evt.keyCode;
		if ((charCode > 47 && charCode < 58)
				|| (charCode > 64 && charCode < 91)
				|| (charCode > 96 && charCode < 123)) {
			return true;
		}
		return false;
	}

	function populateBlockDetail(cropid) {
		$('#cropPlantingId').val('');
		$('#blockId').val('');
		$('#variety').val('');
		$('#grade').val('');
		if (!isEmpty(cropid)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "sprayAndFieldManagement_populateBlockDetailsForScoutingAndSpraying.action",
			data : {
				selectedFarmer : cropid
			},
			success : function(result) {
				$('#cropPlantingId').val(result.plantingId);
				$('#blockId').val(result.blockId);
				$('#variety').val(result.variety);
				$('#grade').val(result.grade);

				var ppadd = $('#agroChemicalNamea').val();
				populatePhiAndDosageDetail(ppadd);

			}
		});
		}

	}
/* 	function populateBlockDetail(cropid) {
		$('#cropPlantingId').val('');
		$('#blockId').val('');
		$('#variety').val('');
		$('#grade').val('');
		if (!isEmpty(cropid)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "sprayAndFieldManagement_populateBlockDetails.action",
			data : {
				selectedFarmer : cropid
			},
			success : function(result) {
				$('#cropPlantingId').val(result.plantingId);
				$('#blockId').val(result.blockId);
				$('#variety').val(result.variety);
				$('#grade').val(result.grade);

				var ppadd = $('#agroChemicalNamea').val();
				populatePhiAndDosageDetail(ppadd);

			}
		});
		}

	} */
	
	
	function populateChamicals(call) {
		var selectedPro = call;
		clearElement('agroChemicalNamea',true);
		
	if(!isEmpty(selectedPro)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url : "sprayAndFieldManagement_populateChamicals.action",
		        data: {selectedFarmer : selectedPro},
		        success: function(result) {
		        	insertOptions("agroChemicalNamea", $.parseJSON(result));
		        	
		        	//var agName = '<s:property value="sprayAndFieldManagement.pcbp.id" />';
		        	var agName='<s:property value="selectedTradeNameId"/>';
		        	if (selectedPro =='<s:property value="sprayAndFieldManagement.planting.id" />' &&  agName != null && agName != '' && agName != 'null')
		        	{
		        		$('#agroChemicalNamea > option[value='+agName+']').prop("selected","selected");
						 $("#agroChemicalNamea").select2();
						 $('#agroChemicalNamea').change();
		        		//$('#agroChemicalNamea').val(agName).trigger('change');
		        	}
					
		        }
			});
		}
		
	}
	
	function populatePlanting(val){
		var selectedBlock=val;
		clearElement('plantingId', true);
		if(!isEmpty(selectedBlock)){
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "sprayAndFieldManagement_populatePlanting.action",
		        data: {selectedBlock : selectedBlock},
		        success: function(result) {
		        	insertOptions("plantingId", $.parseJSON(result));
		        	
		        	if(command=='update'){
		        		var plantingfar ='<s:property value="sprayAndFieldManagement.planting.id"/>';
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
	
	
	function populatePhiAndDosageDetail(chamicalVal) {
		var variety = $('#grade').val();
		$('#sMdosage').val('');
		$('#sMphi').val('');
		//$('#sMuom').val('');//sMuom
		$("#sMuom").select2("val", '');
		if(!isEmpty(chamicalVal)){
		$.ajax({
			type : "POST",
			async : false,
			url : "sprayAndFieldManagement_populatePhiAndDosageDetails.action",
			data : {
				selectedChamical : chamicalVal,
				selectedVariety : variety
			},
			success : function(result) {
				if (command == 'create') {
				$('#sMdosage').val(result.sMdosage);
				if(isEmpty(result.sMphi) || result.sMphi==null || result.sMphi==''){
				jQuery('#sMphi').prop("readonly",false);
				var phi = '<s:property value="sprayAndFieldManagement.phi" />';
				$("#sMphi").val(phi);
				}else{
					jQuery('#sMphi').prop("readonly",true);
					$('#sMphi').val(result.sMphi);
				}
				
				  $('#sMuom').select2({
					disabled : false
				});  
				$('#sMuom').val(result.sMuom).select2().change();
				 $('#sMuom').select2({
					disabled : 'readonly'
				});  
				}else{
					
					var phi = '<s:property value="sprayAndFieldManagement.phi" />';
					var dose = '<s:property value="sprayAndFieldManagement.dosage" />';
					var muom = '<s:property value="sprayAndFieldManagement.uom" />';
					jQuery('#sMphi').prop("readonly",true);
					jQuery('#sMdosage').prop("readonly",false);
					$('#sMdosage').val(dose);
					$("#sMphi").val(phi);
					jQuery('#sMdosage').prop("readonly",true);
					  $('#sMuom').select2({
						disabled : false
					});  
					$('#sMuom').val(muom).select2().change();
					 $('#sMuom').select2({
						disabled : 'readonly'
					});  
			}
			}
		});
		}

	}
</script>
<body>

	<s:form id="fileDownload" action="generalPop_populateDownload">
		<s:hidden id="loadId" name="idd" />
	</s:form>

	<s:form name="form" cssClass="fillform" id="target1"
		action="sprayAndFieldManagement_%{command}" method="post"
		enctype="multipart/form-data">

		<s:hidden name="sprayAndFieldManagement.deleteStatus"
			id="deletestatus" />
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden key="currentPage" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="sprayAndFieldManagement.id" />
		</s:if>
		<s:hidden key="command" />
		<s:hidden key="id" />
		<div class="appContentWrapper marginBottom">
			<div class="error">
				<s:actionerror />
				<s:fielderror />
				<s:if test="hasActionErrors()">
					<div style="color: red;">
						<s:text name="cannotDeleteSprayAndFieldManagementHasTxn" />
						<s:actionerror />

					</div>
				</s:if>
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.sprayAndFieldManagement" />
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
								onchange="loadFarmer(this.value)" headerKey=""
								theme="simple" name="selectedVillage" maxlength="20"
								headerValue="%{getText('txt.select')}" id="village" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.farmer" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select name="sprayAndFieldManagement.farmCrops.farm.farmer.id"
								list="{}" headerKey="" headerValue="%{getText('txt.select')}"
								listKey="key" listValue="value" theme="simple" id="farmer"
								onchange="listFarm(this.value);loadAjaxData(this.value);"
								cssClass="form-control select2" />
						</div>
					</div>



					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.farm" /><sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select list="{}" headerKey=""
								name="sprayAndFieldManagement.farmCrops.farm.id"
								headerValue="%{getText('txt.select')}" listKey="key"
								listValue="value" theme="simple" id="farm"
								onchange="listFarmsCropProduct(this.value)"
								cssClass="form-control select2" />
						</div>
					</div>

					<%-- <div class="flexform-item">
						<label for="txt"><s:text name="block" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="varietyName"
								name="sprayAndFieldManagement.farmCrops.id" listKey="key"
								listValue="value" list="{}" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="populateBlockDetail(this.value);populateChamicals(this.value)" />
						</div>
					</div> --%>
					<div class="flexform-item">
						<label for="txt"><s:text name="block" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="varietyName"
								name="sprayAndFieldManagement.farmCrops.id" listKey="key"
								listValue="value" list="{}" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="populatePlanting(this.value);" />
						</div>
					</div>
					
				 <div class="flexform-item">
						<label for="txt"><s:text name="Planting ID" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="plantingId"
								name="sprayAndFieldManagement.planting.id" listKey="key"
								listValue="value" list="{}" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="populateBlockDetail(this.value);populateChamicals(this.value)" />
						</div>
					</div> 

					<%-- <div class="flexform-item">
						<label for="txt"><s:text name="blockIds" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield readonly="true" id="blockId" theme="simple"
								maxlength="20" size="20" cssClass="form-control input-sm" />

						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.cropPlantingId" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield readonly="true" id="cropPlantingId" theme="simple"
								maxlength="20" size="20" cssClass="form-control input-sm" />

						</div>
					</div> --%>



					<div class="flexform-item">
						<label for="txt"><s:text name="CropVariety" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield readonly="true" id="variety" theme="simple"
								maxlength="20" size="20" cssClass="form-control input-sm" />

						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="Grade" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield readonly="true" id="grade" theme="simple"
								maxlength="20" size="20" cssClass="form-control input-sm" />

						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.dateSpray" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="dateOfSpraying" id="date" theme="simple"
								maxlength="20"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="date-picker form-control input-sm" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.endDateSpray" />
								<!-- <sup style="color: red;">*</sup> --></label>
						<div class="form-element">
							<s:textfield name="endDateOfSpraying" id="endDateSpray" theme="simple"
								maxlength="20"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="date-picker form-control input-sm" />
						</div>
					</div>


					<%-- 	<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.agroChemicalName" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2 " id="agroChemicalNamea"
								name="sprayAndFieldManagement.agroChemicalName" Key="id"
								Value="name" list="chemicalList" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="populatePhiAndDosageDetail(this.value);" />
						</div>
					</div> --%>
					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.pcbp" /><sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2 " id="agroChemicalNamea"
								name="selectedTradeNameId" Key="id" Value="name"
								list="{}" headerKey=""
								headerValue="%{getText('txt.select')}"
								onchange="populatePhiAndDosageDetail(this.value);" />
						</div>
					</div>

					<%-- 	<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.dosage" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="sprayAndFieldManagement.dosage"
								id="agroChemicalQuantity" theme="simple" maxlength="20"
								cssClass="form-control input-sm" />
						</div>
					</div> --%>

					<%-- <div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.uom" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2 " id="agroChemicalName"
								name="sprayAndFieldManagement.uom" Key="id"
								Value="name" list="umoList" headerKey=""
								headerValue="%{getText('txt.select')}"
								 />
						</div>
					</div> --%>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.NameOfOperator" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="sprayAndFieldManagement.NameOfOperator"
								id="agroChemicalQuantity" theme="simple" maxlength="20"
								cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.sprayeMobileNumber" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="sprayAndFieldManagement.sprayMobileNumber"
								id="volumeOfWater" theme="simple" maxlength="11"
								onkeypress="return isNumber(event)"
								cssClass="form-control input-sm" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.operatorMedicalReport" /></label>
						<div class="form-element">
							<s:textfield name="sprayAndFieldManagement.operatorMedicalReport"
								id="operatorMedicalReport" theme="simple" maxlength="50"
								cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.typeApplicationEquipment" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2 " id="agroChemicalName"
								name="sprayAndFieldManagement.typeApplicationEquipment" Key="id"
								Value="name" list="applicationEquipment" headerKey=""
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.methodOfApplication" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2 " id="agroChemicalName"
								name="sprayAndFieldManagement.methodOfApplication" Key="id"
								Value="name" list="methodApplication" headerKey=""
								headerValue="%{getText('txt.select')}" />
						</div>
					</div>
					<%-- <div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.PHIChemical" /><sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="sprayAndFieldManagement.phi" id="phi"
								theme="simple" maxlength="20"
								cssClass="form-control input-sm" />
						</div>
					</div> --%>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.TrainingStatusOfSprayOperator" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="fertilizerUsed"
								name="trainingStatusOfSprayOperator" listKey="key"
								listValue="value"
								list="getCatList(getLocaleProperty('trainSTatus'))"
								headerKey=" " headerValue="%{getText('txt.select')}" />







						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.AgrovetOrSupplierOfTheChemical" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield
								name="sprayAndFieldManagement.agrovetOrSupplierOfTheChemical"
								id="noOfSprayPumpsApplied" theme="simple" maxlength="20"
								cssClass="form-control input-sm" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.LastDateOfCalibration" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="lastDateOfCalibration" id="harvestDate"
								theme="simple" maxlength="10"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="date-picker form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.insectTargeted" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="sprayAndFieldManagement.insectTargeted"
								id="volumeOfKnapsackSprayer" theme="simple" maxlength="20"
								cssClass="form-control input-sm" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.phi" /></label>
						<div class="form-element">
							<s:textfield  id="sMphi" theme="simple"
								name="sprayAndFieldManagement.phi" size="20"
								cssClass="form-control input-sm" />

						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.dosage" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield readonly="true" id="sMdosage" theme="simple"
								name="sprayAndFieldManagement.dosage" size="20"
								cssClass="form-control input-sm" />

						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text
								name="sprayAndFieldManagement.uom" /><sup style="color: red;">*</sup></label>
						<div class="form-element">

							<s:select class="form-control select2 " id="sMuom"
								name="sprayAndFieldManagement.uom" Key="id" Value="name"
								list="umoList" headerKey=""
								headerValue="%{getText('txt.select')}" />
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