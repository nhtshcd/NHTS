var numberRegx = /^\d+$/;
var alphabetsRegx = /^[a-zA-Z]*$/;
var alphabetsWithSpaceRegx = /^[a-zA-Z ]*$/;
var alphaNumericRegx = /^[a-zA-Z0-9]*$/;
var alphaNumericWithSpaceRegx = /^[a-zA-Z0-9/@#()* ]*$/;

var tenant = '<s:property value="getCurrentTenantId()"/>';

function changeInsuranceDiv() {
	var cropInsval = $("input[name='farmer.isCropInsured']:checked").val();
	var hIns = $('input:radio[name="farmer.healthInsurance"]:checked').val();
	var lIns = $('input:radio[name="farmer.lifeInsurance"]:checked').val();
	if (cropInsval == 1) {
		$(".cropInsDiv").show();

	} else {
		$(".cropInsDiv").hide();
	}
	if (hIns == 1) {
		$(".healthDiv").show();
	} else {
		$(".healthDiv").hide();
	}
	if (lIns == 1) {
		$(".lifeDiv").show();
	} else {
		$(".lifeDiv").hide();
	}
}

function processEnrollmentPlace(data) {
	if (data == 99) {
		jQuery(".enrollmentOtherClass").show();
	} else {
		jQuery("#enrollmentPlaceOther").val("");
		jQuery(".enrollmentOtherClass").hide();

	}
}

function processOtherValue(val, obj, reset) {
	var id = $(obj).attr("id");

	if (val == 99) {
		jQuery("#" + id + "other").show();
	} else {
		if (reset) {
			jQuery("#" + id + "other").val('');
		}
		jQuery("#" + id + "other").hide();
	}
}

/*
 * function isNumber(str) { return numberRegx.test(str); }
 */

function isNumber(evt) {

	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}

function isAlphabets(str) {
	return alphabetsRegx.test(str);
}

function isAlphabetsWithSpace(str) {
	return alphabetsWithSpaceRegx.test(str);
}

function isAlphaNumeric(str) {
	return alphaNumericRegx.test(str);
}

function isAlphaNumericWithSpace(str) {
	return alphaNumericWithSpaceRegx.test(str);
}

function refreshPopup() {
	// $(".close").hide();
	var accountNo = $('#accNo').val("");
	var sortCode = $('#sortCode').val("");
	// var swiftCode= $('#swiftCode').val("");
	var accType = $('#accountType').val(-1);
	jQuery('#accountType').select2();

	if (tenant == "chetna") {
		var bankNameVal = $('#bankNameList').val(-1);
		jQuery('#bankNameList').select2();
		var branchNameVal = $('#branchNameList').val(-1);
		jQuery('#branchNameList').select2();
	} else {
		var bankName = $('#bankName').val("");
		var branchName = $('#branchName').val("");
	}
}

function validateImage() {
    
	var file = document.getElementById('farmerImage').files[0];
	var filename = document.getElementById('farmerImage').value;
	var fileExt = filename.split('.').pop();
    // alert(tenant);
	if (file != undefined) {
		
		if(tenant=='wilmar'){
		if (fileExt == 'jpg' || fileExt == 'jpeg' || fileExt == 'png'
				|| fileExt == 'JPG' || fileExt == 'JPEG' || fileExt == 'PNG') {
			if (file.size > 6000000) {
				alert('File Size is Exceeds');
				hit = false;
				enableButton();
				return false;
			}
		} else {

			alert('Invalid File Extension')
			file.focus();
			return false;
		}
	}
		else{
			if (fileExt == 'jpg' || fileExt == 'jpeg' || fileExt == 'png'
				|| fileExt == 'JPG' || fileExt == 'JPEG' || fileExt == 'PNG') {
			if (file.size > 51200) {
				alert('File Size is Exceeds');
				hit = false;
				enableButton();
				return false;
			}
		} else {

			alert('Invalid File Extension')
			file.focus();
			return false;
		}
		}
		
	}
	return true;
}

/*
 * function onSubmit() { var hit = true; beforeSave(); if (validateFarmer()) {
 * if(tenant!='nei' && document.getElementById("calendar")!=null ){
 * document.getElementById("dateOfBirth").value =
 * document.getElementById("calendar").value; } $("#target").submit(); } else {
 * $('html,body').animate({ scrollTop: $("#errorDiv").offset().top }, 'slow'); } }
 */

function beforeSave() {
	// alert("in before save");
	processBank();
	// alert("inside process bank");
}

function processBank() {
	$('#jsonString').val("");
	var jsonObj = [];
	$('#bankTable tbody tr').each(function() {
		var $td = $(this).children('td');
		var accountNo = $td.eq(1).text();
		var bankName = $td.eq(2).text();
		var branchName = $td.eq(3).text();
		var sortCode = $td.eq(4).text();
		// var swiftCode= $td.eq(5).text();
		var accType = $td.eq(0).text();
		var accTypeCode = $td.eq(5).text();
		var bankInformation = {};
		if (!isEmpty(accountNo) || !isEmpty(bankName)) {
			bankInformation["accNo"] = accountNo;
			bankInformation["bankName"] = bankName;
			bankInformation["branchName"] = branchName;
			bankInformation["sortCode"] = sortCode;
			bankInformation["accType"] = accType;
			bankInformation["accTypeCode"] = accTypeCode;

			jsonObj.push(bankInformation);
		}
	});
	$('#jsonString').val(JSON.stringify(jsonObj));
}

function benificiaryDiv(evt) {
	var val = $(evt).val();
	if (val == 1) {
		jQuery(".beneficiaryScheme").show();
	} else {
		jQuery(".beneficiaryScheme").hide();
	}
}

function processCellPhone(evt){
	var val = $(evt).val();
	if(val==1){
		//jQuery(".cellPhone").show();
		$('.cellPhone').removeClass("hide");
	}else{
		//jQuery(".cellPhone").hide();
		$('.cellPhone').addClass("hide");
	}
}

function lifeInsureAmt(val) {
	if (val == 1) {
		$('.lifeDiv').show();
	} else {
		$('#liftAmt').val("");
		$('.lifeDiv').hide();

	}
}

function healthInsureAmt(val) {
	
	if (tenant == "efk") {
		
		if (val == "NHIF") {
				$('.healthDiv').show();
			} 
			if(val == "Other") {
				$('.healthDiv').show();
			}
		}else{
			if (val == 1) {
				$('.healthDiv').show();
			} else {
				$('#healthAmt').val("");
				$('.healthDiv').hide();

			}
		}
}

function cropOnChange(val) {
	if (val == 1) {
		$(".cropInsDiv").show();

	} else {
		$(".cropInsDiv").hide();
	}
}

function toiletAvailable(evt) {
	var val = $(evt).val();
	if (val == 1) {
		//jQuery(".toiletAvailable").show();
		$('.toiletAvailable').removeClass("hide");
	} else {
		$("#ifToiletAvailable1").prop("checked", false);
		$("#ifToiletAvailable2").prop("checked", false);
		//jQuery(".toiletAvailable").hide();
		$('.toiletAvailable').addClass("hide");
	}
}

/** LOCATION */

function listState(obj, countryId, stateId, disId, cityId,gpId, vId) {

	if (!isEmpty(obj)) {
		var selectedCountry = $('#' + countryId).val();
		jQuery.post("farmer_populateState.action", {
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
		jQuery.post("farmer_populateLocality.action", {
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
			jQuery.post("farmer_populateCity.action", {
				id1 : obj.value,
				dt : new Date(),
				selectedLocality : obj.value
			}, function(result) {
				insertOptions(cityId, $.parseJSON(result));
				listPanchayat(document.getElementById(cityId),cityId,gpId,vId);
			});
			
			$('#' + gpId).select2();
		}
		
	
}

function listPanchayat(obj, cityId, gpId, vId) {
	if (!isEmpty(obj)) {
		try {
			var selectedCity = $('#' + cityId).val();

			jQuery.post("farmer_populatePanchayath.action", {
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
}

function listVillage(obj,cityId,gpId, vId) {
	if (!isEmpty(obj)) {
			var selectedPanchayat = $('#' + gpId).val();
			jQuery.post("farmer_populateVillage.action", {
				id1 : obj.value,
				dt : new Date(),
				selectedPanchayat : obj.value
			}, function(result) {
					insertOptions(vId, $.parseJSON(result));
			});
	}
}



function listVillageByCity(obj, cityId,vId) {
	if (!isEmpty(obj)) {
		var selectedCity = $('#' + cityId).val();
		jQuery.post("farmer_populateVillageByCity.action", {
			id : obj.value,
			dt : new Date(),
			selectedCity : obj.value
		}, function(result) {
				insertOptions(vId, $.parseJSON(result));
				listFarmer(document.getElementById(vId).value);
			// listSamithi(document.getElementById("village"));
		});
		
		$('#' + vId).select2();
	}
	
	
}

function listFarmer(obj,value) {
//	alert(value);
	if (!isEmpty(obj)) {
		$.post("farmerLocation_populateFarmer", {
			selectedVillage : obj.value
		}, function(data) {
			insertOptions("farmer",$.parseJSON(data));
		});
		$("#farmer").val("").trigger("change");
	}
	//alert("1"+value);
	$("#farmer").val("").trigger("change");

}

function listSamithi(obj) {
	$('select').select2();
	var selectedVillage = $('#village').val();
	jQuery.post("farmer_populateSamithi.action", {
		id : obj.value,
		dt : new Date(),
		selectedVillage : obj.value
	}, function(result) {

		insertOptions("samithii", $.parseJSON(result));
	});
}

/*
 * function processIdProof(value) { alert("A"); if (value == "-1") {
 * jQuery(".idType").hide(); jQuery(".idTypeOther").hide(); } else if (value ==
 * "99") { jQuery(".idType").hide(); jQuery(".idTypeOther").show(); } else {
 * jQuery(".idType").show(); jQuery(".idTypeOther").hide(); } }
 */

function resetTaulkInfo() {
	jQuery("#ccountry").val('');
	jQuery("#cstate").val('');
	jQuery("#clocalities").val('');
	jQuery("#cityName").val('');

	jQuery("#ccountry").select2();
	jQuery("#cstate").select2();
	jQuery("#clocalities").select2();

	jQuery(".cerror").empty();

	$('#talukModal').modal('toggle');
}

function resetPanchaytInfo() {
	jQuery("#vpcountry").val('');
	jQuery("#pstate").val('');
	jQuery("#plocalities").val('');
	jQuery("#pcity").val('');
	jQuery("#panchayatName").val('');

	jQuery("#pcountry").select2();
	jQuery("#pstate").select2();
	jQuery("#plocalities").select2();
	jQuery("#pcity").select2();

	
	$('#PanchayatModal').modal('toggle');
	jQuery(".perror").empty();
}

function resetSamithiInfo() {
	jQuery("#createSamithi").val('');
	$('#samithiModal').modal('toggle');
	jQuery(".gerror").empty();
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

function loanTakenFromDiv(evt) {
	var val = $(evt).val();
	if (val == 1) {
		jQuery(".loanTakenFromRow").show();
	} else {
		document.getElementById('loanTakenFrom').selectedIndex = "";
		jQuery(".loanTakenFromRow").hide();
	}

}

function assistiveDeviceDiv(evt) {
	var val = $(evt).val();
	if (isEmpty(val)) {
		return false;
	} else if (val == 0) {
		jQuery(".assistiveDeviceRow").show();
		jQuery(".assistiveDeviceReqRow").hide();
	} else {
		document.getElementById('assistiveDeivceName').selectedIndex = "";
		jQuery(".assistiveDeviceRow").hide();
		jQuery(".assistiveDeviceReqRow").show();
	}
}

function healthIssueDiv(evt) {
	var val = $(evt).val();
	if (val == 0) {
		jQuery(".healthIssueRow").show();

	} else {
		document.getElementById('healthIssueDescribe').selectedIndex = "";
		jQuery(".healthIssueRow").hide();
	}
}

function calculateAge() {
	if ($('#calendar').val() != null && $('#calendar').val() != 'undefined'
			&& $('#calendar').val() != '') {
		/*
		 * var dob = $('#calendar').val().split('-')[2]; var today = new Date();
		 * alert(dob); $('#age').text(parseInt(today.getFullYear()) -
		 * parseInt(dob)); alert(parseInt(today.getFullYear()) - parseInt(dob));
		 * $('#ageHide').val(parseInt(today.getFullYear()) - parseInt(dob));
		 */

		var age = $('#calendar').val().replace(/[^\w\s]/gi, '');
		dob = age.substring(4, 8);

		var today = new Date();
		$('#age').text(parseInt(today.getFullYear()) - parseInt(dob));
		$('#ageHide').val(parseInt(today.getFullYear()) - parseInt(dob));

	} else {
		$('#age').text(0);
		// alert(parseInt(today.getFullYear()) - parseInt(dob));
		$('#ageHide').val(0);
	}

}

function hideByEleName(name) {
	$('[name="' + name + '"]').closest(".flexform-item").addClass("hide");
}

function showByEleName(name) {
	$('[name="' + name + '"]').closest(".flexform-item").removeClass("hide");
}

function hideByEleClass(className) {
	$("." + className).closest(".flexform-item").addClass("hide");
}

function showByEleClass(className) {
	$("." + className).closest(".flexform-item").removeClass("hide");
}

function hideByEleId(id) {
	$("#" + id).closest(".flexform-item").addClass("hide");
}

function showByEleId(id) {
	$("#" + id).closest(".flexform-item").removeClass("hide");
}

function onCancel() {
	// document.listForm.submit();
	window.location.href = "farmer_list.action";
}

function editBank(evt) {
	var $td = $(evt).closest('tr').children('td');
	var hiddenId = $(evt).attr('id');
	var accountNo = $td.eq(1).text();
	var bankName = $td.eq(2).text();
	var branchName = $td.eq(3).text();
	var sortCode = $td.eq(4).text();
	var accType = $td.eq(0).text();
	if (tenant == "chetna") {
		$('#bankNameList').val(hiddenId.split(",")[2]);
		$('#bankNameList').select2();
		$('#branchNameList').val(hiddenId.split(",")[3]);
		$('#branchNameList').select2();
	} else {
		$('#branchName').val(branchName);
		$('#bankName').val(bankName);

	}
	console.log("***********************" + hiddenId);

	$('#accNo').val(accountNo);

	$('#sortCode').val(sortCode);
	// alert(hiddenId.split(",")[1]);
	$('#accountType').val(hiddenId.split(",")[1]);
	$('#accountType').select2();

	$('#hidId').val(hiddenId.split(",")[0]);

	document.getElementById("editBankDetailModal").click();

}

function deleteBank(evt) {
	// var result = confirm("Do you want to delete the selected record?");
	var result = confirm('<s:text name="txt.delete"/>');
	if (result) {
		var $tr = $(evt).closest('tr');
		$tr.remove();
		evt.preventDefault();
	}

}

function processDrinkingWS(val) {
	var selectedDrinkingWs = $("#drinkingWS").val();
	if (selectedDrinkingWs != null && selectedDrinkingWs != "") {
		var drknkingTrim = selectedDrinkingWs.toString().trim();
		if (drknkingTrim.includes(',')) {
			var values = drknkingTrim.toString().split(",");
			$.each(values, function(i, e) {
				if (e.trim() == 99) {
					jQuery(".drinkingWSOther").removeClass("hide");
				} else {
					jQuery("#drinkingWSOtherVal").val("");
					jQuery(".drinkingWSOther").addClass("hide");
				}

			});

		} else {
			if (val == 99) {
				jQuery(".drinkingWSOther").removeClass("hide");
			} else {
				jQuery("#drinkingWSOtherVal").val("");
				jQuery(".drinkingWSOther").addClass("hide");
			}
		}
	} else {
		jQuery(".drinkingWSOther").addClass("hide");
	}

}

function processConsumerElec(val) {
	var selectedElectronics = $("#consumerElectronics").val();
	if (selectedElectronics != null && selectedElectronics != "") {
		var electronicTrim = selectedElectronics.toString().trim();
		if (electronicTrim.includes(',')) {
			var values = electronicTrim.toString().split(",");
			$.each(values, function(i, e) {
				if (e.trim() == 99) {
					jQuery(".consumerElecOther").removeClass("hide");
				} else {
					jQuery("#consumerElecOtherVal").val("");
					jQuery(".consumerElecOther").addClass("hide");
				}

			});

		} else {

			if (val == 99) {
				jQuery(".consumerElecOther").removeClass("hide");
			} else {
				jQuery("#consumerElecOtherVal").val("");
				jQuery(".consumerElecOther").addClass("hide");
			}
		}

	} else {
		jQuery(".consumerElecOther").addClass("hide");
	}

}

function processConsumerElectronic(val) {

	var selectedConsumerElec = $("#consumerElectronics").val();
	if (selectedConsumerElec != null && selectedConsumerElec != "") {
		var consumerElecTrim = selectedConsumerElec.toString().trim();
		if (consumerElecTrim.includes(',')) {
			var values = consumerElecTrim.toString().split(",");
			$.each(values, function(i, e) {
				if (e.trim() == 99) {

					jQuery(".consumerElecOther").removeClass("hide");
				} else {
					jQuery("#consumerElecOtherVal").val("");
					jQuery(".consumerElecOther").addClass("hide");
				}

			});

		} else {
			if (val == 99) {
				jQuery(".consumerElecOther").removeClass("hide");
			} else {
				jQuery("#consumerElecOtherVal").val("");
				jQuery(".consumerElecOther").addClass("hide");
			}
		}

	} else {
		jQuery(".consumerElecOther").hide();
	}

}

function processVehicle(val) {

	var selectedVehicle = $("#vehicle").val();
	if (selectedVehicle != null && selectedVehicle != "") {
		var vechicleTrim = selectedVehicle.toString().trim();
		if (vechicleTrim.includes(',')) {
			var values = vechicleTrim.toString().split(",");
			$.each(values, function(i, e) {
				if (e.trim() == 99) {
					jQuery(".vehicleOther").removeClass("hide");
				} else {
					jQuery("#vehicleOtherVal").val("");
					jQuery(".vehicleOther").addClass("hide");
				}

			});

		} else {

			if (val == 99) {
				jQuery(".vehicleOther").removeClass("hide");
			} else {
				jQuery("#vehicleOtherVal").val("");
				jQuery(".vehicleOther").addClass("hide");
			}
		}

	} else {
		jQuery(".vehicleOther").hide();
	}

}

function processFamilyMember(val) {

	var selectedVehicle = $("#familyMember").val();
	if (selectedVehicle != null && selectedVehicle != "") {
		var vechicleTrim = selectedVehicle.toString().trim();
		if (vechicleTrim.includes('99')) {

			jQuery(".familyMemberOther").show();

		} else {
			jQuery(".familyMemberOther").hide();
		}

	} else {
		jQuery(".familyMemberOther").hide();
	}

}

function processCookingFuel(val) {
	var selectedCookFuel = $("#cookFuel").val();
	if (selectedCookFuel != null && selectedCookFuel != "") {
		var cookFuelTrim = selectedCookFuel.toString().trim();
		if (cookFuelTrim.includes(',')) {
			var values = cookFuelTrim.toString().split(",");
			$.each(values, function(i, e) {

				if (e.trim() == 99) {
					jQuery(".cookingFuelOther").removeClass("hide");
				} else {
					jQuery("#cookingFuelOtherVal").val("");
					jQuery(".cookingFuelOther").addClass("hide");
				}

			});
		} else {
			if (val == 99) {
				jQuery(".cookingFuelOther").removeClass("hide");
			} else {
				jQuery("#cookingFuelOtherVal").val("");
				jQuery(".cookingFuelOther").addClass("hide");
			}
		}
	} else {
		jQuery(".cookingFuelOther").addClass("hide");
	}
}

function processDisType(obj) {
	var val = $(obj).val();
	if (val == "1") {
		$(".farmerHealthAssesDiv").addClass("hide");
		resetAssesment();
	} else if (val == "0") {
		$(".farmerHealthAssesDiv").removeClass("hide");
	}
}

