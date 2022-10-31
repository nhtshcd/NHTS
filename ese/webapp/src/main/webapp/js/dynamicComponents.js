﻿function renderDynamicFeilds() {
	var json = "";
	var dataa = {
		txnTypez : getTxnType(),
		branch:getBranchIdDyn()
		

	}

	$.ajax({
		type : "POST",
		async : false,
		url : "farmer_populateDynamicFields.action",
		data : dataa,
		success : function(result) {
			json = result;
		}
	});

	/**
	 * 
	 * 5.Text Area 6.Check Box 7.Label 8.List 9.Multiple Drop Down 10.Button
	 * 
	 * 
	 */

	$(json).each(
			function(k, v) {
				constants = v.constants;
				
				$(v.sections).each(
						function(key, val) {
							
							
							if(getTxnType()!='308'){
								var mainDiv = $("<div/>").addClass(
								"appContentWrapper marginBottom").attr(
								'id', val.secCode);
						var formContainerWrapper = $("<div/>").addClass(
								"formContainerWrapper");
						formContainerWrapper.append("<h2>" + val.secName
								+ "</h2>");
						var flexform = $("<div/>").addClass("flexform");
						mainDiv.append(formContainerWrapper);
						mainDiv.append(flexform);
						$(".dynamicFieldsRender").append(mainDiv);
						
							}else{
								var mainDiv = $("<div/>").addClass(
								"appContentWrapper marginBottom").attr(
								'id', val.secCode);
						var formContainerWrapper = $("<div/>").addClass(
								"formContainerWrapper");
						var formContainerWrapper1 = $("<div/>").addClass(
						"panel-collapse collapse").attr(
								'id', 'flexDiv'+val.secCode);
						formContainerWrapper.append("<h2> <a data-toggle='collapse' data-parent='#accordion' href='#flexDiv"+val.secCode+"' class='accrodianTxt collapsed'>"+val.secName+"</a></h2>");
					
							var flexform = $("<div/>").addClass("flexform ");
							formContainerWrapper1.append(flexform);
							formContainerWrapper.append(formContainerWrapper1);
						mainDiv.append(formContainerWrapper);
							}
								
							// mainDiv.append(formContainerWrapper1);
							if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){
								$(mainDiv).insertAfter('.'+val.afterInsert );
							}else{
							$(".dynamicFieldsRender").append(mainDiv);
							}
						});
				$(v.fields).each(function(key, val) {

					if (val.compoType == '1') {
						createTextBox(val);
					} else if (val.compoType == '2') {
						createRadio(val);
					} else if (val.compoType == '3') {
						createDatePicker(val);
					} else if (val.compoType == '4') {
						createDropDown(val);
					} else if (val.compoType == '5') {
						createTextArea(val);
					} else if (val.compoType == '6') {
						createCheckbox(val);
					} else if (val.compoType == '7') {
						// createLabel(val);
						createLabel(val);
					} else if (val.compoType == '8') {
						createList(val);
					} else if (val.compoType == '9') {
						createMultiDropDown(val);
					} else if (val.compoType == '12') {
						createPhoto(val);
					} else if (val.compoType == '13' || val.compoType == '11') {
						createPhoto(val);
					} else if (val.compoType == '14') {
						createWeatherInfo(val);
					}else if (val.compoType == '15') {
						createPlotting(val);
					}
				});

				$(v.groups).each(function(key, val) {

					if (val.compoType == '1') {
						createTextBoxList(val);
					} else if (val.compoType == '2') {
						createRadioList(val);
					} else if (val.compoType == '3') {
						createDatePickerList(val);
					} else if (val.compoType == '4') {
						createDropDownList(val);
					} else if (val.compoType == '5') {
						createTextAreaList(val);
					} else if (val.compoType == '6') {
						createCheckboxList(val);
					} else if (val.compoType == '7') {
						createLabelList(val);
					} else if (val.compoType == '9') {
						createMultiDropDownList(val);
					} else if (val.compoType == '10') {
						createButtonList(val);
					} else if (val.compoType == '14') {
						createWeatherInfoList(val);
					}
				});

				$(v.sections).each(
						function(key, val) {

							if ($('#' + val.secCode).find('.flexform')
									.children().length == 0) {

								$('#' + val.secCode).remove();
							}

						});
			});

	$('.radio').change(function() {
		var classz = ".depend" + $(this).attr("name");
		if ($(this).val() == '1') {
			$(classz).removeClass("hide");
		} else {
			$(classz).addClass("hide");
		}
	});

	$('.dropDownList').select2();
	$('.multiDropDownList').select2();
	$('.dropDown').select2();
	$('.multiDropDown').select2();



	var json = $("#dynamicFieldsArray").val();

	if (!isEmpty(json)) {
		var testJson = $.parseJSON(json);
		$(testJson)
				.each(
						function() {
							$($(this))
									.each(
											function(k, v) {
												var classN = v.name + "_"
														+ v.value;

												try {
													$('.' + classN)
															.removeClass("hide");
												} catch (e) {

												}
												if(v.otherValue!=null && v.otherValue!='' && v.otherValue!='undefined'){
													showOther($('[name="' + v.name + '"]'),v.name);
												}
												if (v.compoType == '1'
														|| v.compoType == '3'
														|| v.compoType == '4') {
													$('[name="' + v.name + '"]')
															.val(v.value);
												} else if (v.compoType == '2') {
													$(
															"input[name="
																	+ v.name
																	+ "][value="
																	+ v.value
																	+ "]")
															.prop('checked',
																	true);
												} else if (v.compoType == '7') {
													$('#' + v.name).text(
															v.value);
												} else if (v.compoType == '9') {
													var id = $(
															'[name="' + v.name
																	+ '"]')
															.attr("id");
													if (!isEmpty(v.value)) {
														var values = v.value
																.split(",");
														$
																.each(
																		values,
																		function(
																				i,
																				e) {
																			var classN = v.name
																					+ "_"
																					+ e
																							.trim();

																			$(
																					'.'
																							+ classN)
																					.removeClass(
																							"hide");
																			$(
																					"#"
																							+ id
																							+ " option[value='"
																							+ e
																									.trim()
																							+ "']")
																					.prop(
																							"selected",
																							true);
																		});
													}
													$('[name="' + v.name + '"]')
															.select2();
												} else if (v.compoType == "12" && !isNumeric(v.imageFile)) {
													var ids = v.imageFile;
													$(
															"input[name="
																	+ v.name
																	+ "]")
															.parent()
															.append(
																	"<button type='button' class='btn btn-sm pull-right photo photoModel' onclick=enablePhotoModaImg('"
																			+ 'img' + v.name
																			+ "')><i class='fa fa-picture-o' aria-hidden='true'></i></button>");
													$('#img' + v.name).attr(
															'src', ids);

												}
											})
						});

		$('.radio:checked').each(function() {
			var classz = ".depend" + $(this).attr("name");
			if ($(this).val() == '1') {
				$(classz).removeClass("hide");
			} else {
				$(classz).addClass("hide");
			}
		});

	}

	if (!isEmpty($("#dynamicListArray").val())) {
		var listArray = $("#dynamicListArray").val().split("###");
		var parentRow;
		$(listArray).each(function(k, v) {
			var tr = $("<tr/>");
			var colSize = (v.split("$$").length) - 1;
			$(v.split("$$")).each(function(key, value) {
				var objSplit = value.split("%%");
				if (key == 0) {
					parentRow = $("." + objSplit[0]).parent().parent();
				}
				if (key != colSize) {
					tr.append("<td>" + objSplit[2] + "</td>");
				}
			});

			button = $("<button>").attr({
				class : "btn btn-danger",
				onclick : 'removeRow(this)',
				type : 'button'
			});
			button.html('<i class="fa fa-trash">');
			tr.append($("<td>").append(button));

			var hidden = $('<input/>').attr({
				type : 'hidden',
				class : 'listFormation',
				value : v
			});
			tr.append($("<td>").addClass("hide").append(hidden));

			$(parentRow).append(tr);
		});

		$('.radio:checked').each(function() {
			var classz = ".depend" + $(this).attr("name");
			if ($(this).val() == '1') {
				$(classz).removeClass("hide");
			} else {
				$(classz).addClass("hide");
			}
		});
	}

}
function isNumeric(value) {
    return /^\d+$/.test(value);
}
﻿function renderDynamicFeildsWithActPlan() {
	var json = "";
	var dataa = {
			
		selectedObject : jQuery(".uId").val(),
		txnTypez : getTxnType(),
		branch:getBranchIdDyn()
		

	}

	$.ajax({
		type : "POST",
		async : false,
		// url : "farmer_populateDynamicFields.action",
		url : "farmer_populateDynamicFieldsWithActionPlan",
		data : dataa,
		success : function(result) {
			json = result;
		}
	});

	/**
	 * 
	 * 5.Text Area 6.Check Box 7.Label 8.List 9.Multiple Drop Down 10.Button
	 * 
	 * 
	 */

	$(json).each(
			function(k, v) {
				constants = v.constants;
				$(v.sections).each(
						function(key, val) {
							var mainDiv = $("<div/>").addClass(
							"appContentWrapper marginBottom").attr(
							'id', val.secCode);
					var formContainerWrapper = $("<div/>").addClass(
							"formContainerWrapper");
					var formContainerWrapper1 = $("<div/>").addClass(
					"panel-collapse collapse").attr(
							'id', 'flexDiv'+val.secCode);
					formContainerWrapper.append("<h2> <a data-toggle='collapse' data-parent='#accordion' href='#flexDiv"+val.secCode+"' class='accrodianTxt collapsed'>"+val.secName+"</a></h2>");
				
						var flexform = $("<div/>").addClass("flexform ");
						formContainerWrapper1.append(flexform);
					mainDiv.append(formContainerWrapper);
					mainDiv.append(formContainerWrapper1);
					if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){
						$(mainDiv).insertAfter('.'+val.afterInsert );
					}else{
					$(".dynamicFieldsRender").append(mainDiv);
					}
						});
				$(v.fieldsRender).each(function(key, val) {

					if (val.compoType == '1') {
						createTextBox(val);
					} else if (val.compoType == '2') {
						createRadio(val);
					} else if (val.compoType == '3') {
						createDatePicker(val);
					} else if (val.compoType == '4') {
						createDropDown(val);
					} else if (val.compoType == '5') {
						createTextArea(val);
					} else if (val.compoType == '6') {
						createCheckbox(val);
					} else if (val.compoType == '7') {
						// createLabel(val);
						createLabel(val);
					} else if (val.compoType == '8') {
						createList(val);
					} else if (val.compoType == '9') {
						createMultiDropDown(val);
					} else if (val.compoType == '12') {
						createPhoto(val);
					} else if (val.compoType == '13' || val.compoType == '11') {
						createPhoto(val);
					} else if (val.compoType == '14') {
						createWeatherInfo(val);
					}else if (val.compoType == '15') {
						createPlotting(val);
					}
				});

				$(v.groupsRender).each(function(key, val) {

					if (val.compoType == '1') {
						createTextBoxList(val);
					} else if (val.compoType == '2') {
						createRadioList(val);
					} else if (val.compoType == '3') {
						createDatePickerList(val);
					} else if (val.compoType == '4') {
						createDropDownList(val);
					} else if (val.compoType == '5') {
						createTextAreaList(val);
					} else if (val.compoType == '6') {
						createCheckboxList(val);
					} else if (val.compoType == '7') {
						createLabelList(val);
					} else if (val.compoType == '9') {
						createMultiDropDownList(val);
					} else if (val.compoType == '10') {
						createButtonList(val);
					} else if (val.compoType == '14') {
						createWeatherInfoList(val);
					}
				});

				$(v.sections).each(
						function(key, val) {

							if ($('#' + val.secCode).find('.flexform')
									.children().length == 0) {

								$('#' + val.secCode).remove();
							}

						});
			});

	$('.radio').change(function() {
		var classz = ".depend" + $(this).attr("name");
		if ($(this).val() == '1') {
			$(classz).removeClass("hide");
		} else {
			$(classz).addClass("hide");
		}
	});

	$('.dropDownList').select2();
	$('.multiDropDownList').select2();
	$('.dropDown').select2();
	$('.multiDropDown').select2();



	var jsonn = $("#dynamicFieldsArray").val();

	if (!isEmpty(jsonn)) {
		var testJson = $.parseJSON(jsonn);
		$(testJson)
				.each(
						function() {
							$($(this))
									.each(
											function(k, v) {
												var classN = v.name + "_"
														+ v.value;

												try {
													$('.' + classN)
															.removeClass("hide");
												} catch (e) {

												}
												if(v.otherValue!=null && v.otherValue!='' && v.otherValue!='undefined'){
													showOther($('[name="' + v.name + '"]'),v.name);
												}
												if (v.compoType == '1'
														|| v.compoType == '3'
														|| v.compoType == '4') {
													$('[name="' + v.name + '"]')
															.val(v.value);
												} else if (v.compoType == '2') {
													$(
															"input[name="
																	+ v.name
																	+ "][value="
																	+ v.value
																	+ "]")
															.prop('checked',
																	true);
												} else if (v.compoType == '7') {
													$('#' + v.name).text(
															v.value);
												} else if (v.compoType == '9') {
													var id = $(
															'[name="' + v.name
																	+ '"]')
															.attr("id");
													if (!isEmpty(v.value)) {
														var values = v.value
																.split(",");
														$
																.each(
																		values,
																		function(
																				i,
																				e) {
																			var classN = v.name
																					+ "_"
																					+ e
																							.trim();

																			$(
																					'.'
																							+ classN)
																					.removeClass(
																							"hide");
																			$(
																					"#"
																							+ id
																							+ " option[value='"
																							+ e
																									.trim()
																							+ "']")
																					.prop(
																							"selected",
																							true);
																		});
													}
													$('[name="' + v.name + '"]')
															.select2();
												}
											})
						});

		$('.radio:checked').each(function() {
			var classz = ".depend" + $(this).attr("name");
			if ($(this).val() == '1') {
				$(classz).removeClass("hide");
			} else {
				$(classz).addClass("hide");
			}
		});

	}

	if (!isEmpty($("#dynamicListArray").val())) {
		var listArray = $("#dynamicListArray").val().split("###");
		var parentRow;
		$(listArray).each(function(k, v) {
			var tr = $("<tr/>");
			var colSize = (v.split("$$").length) - 1;
			$(v.split("$$")).each(function(key, value) {
				var objSplit = value.split("%%");
				if (key == 0) {
					parentRow = $("." + objSplit[0]).parent().parent();
				}
				if (key != colSize) {
					tr.append("<td>" + objSplit[2] + "</td>");
				}
			});

			button = $("<button>").attr({
				class : "btn btn-danger",
				onclick : 'removeRow(this)',
				type : 'button'
			});
			button.html('<i class="fa fa-trash">');
			tr.append($("<td>").append(button));

			var hidden = $('<input/>').attr({
				type : 'hidden',
				class : 'listFormation',
				value : v
			});
			tr.append($("<td>").addClass("hide").append(hidden));

			$(parentRow).append(tr);
		});

		$('.radio:checked').each(function() {
			var classz = ".depend" + $(this).attr("name");
			if ($(this).val() == '1') {
				$(classz).removeClass("hide");
			} else {
				$(classz).addClass("hide");
			}
		});
	}
	
	
	
	var listFormation = "";
	$(json)
			.each(
					function(k, v) {
						$(v.fields)
								.each(
										function(key, val) {
											
											if(val.name.indexOf(",")<0){
												var classN = val.code + "_"
														+ val.name;

												try {
													$('.' + classN).removeClass(
															"hide");
												} catch (e) {

												}
												}else{
													$((val.name.split(","))).each(function(k, v) {
														var classN = val.code + "_"
														+ v.trim();

												try {
													$('.' + classN).removeClass(
															"hide");
												} catch (e) {

												}
													});
												}
											if (val.componentType == "1"
													|| val.componentType == "3") {
												$(
														'input[name="'
																+ val.code
																+ '"]').val(
														val.dispName);

											} else if (val.componentType == "4") {
												$('[name="' + val.code + '"]')
														.val(val.name).select2();

												var classN = val.parentDepenCode
														+ "_" + val.name;

												$('.' + classN).removeClass(
														"hide");
											} else if (val.componentType == "5") {

												$('[name="' + val.code + '"]')
														.text(val.name);
											} else if (val.componentType == "9") {

												var id = $(
														'[name="' + val.code
																+ '"]').attr(
														"id");
												// alert(val.name+"-"+id);
												if (!isEmpty(val.name)) {
													var values = val.name
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = val.parentDepenCode
																				+ "_"
																				+ e
																						.trim();

																		$(
																				'.'
																						+ classN)
																				.removeClass(
																						"hide");
																		$(
																				"#"
																						+ id
																						+ " option[value='"
																						+ e
																								.trim()
																						+ "']")
																				.prop(
																						"selected",
																						true);
																	});
												}
												$('[name="' + val.code + '"]')
														.select2();

												/*
												 * 
												 * $('[name="' + val.code +
												 * '"]') .val(val.name);
												 */

											} else if (val.componentType == "2") {
												$(
														"input[name="
																+ val.code
																+ "][value="
																+ val.name
																+ "]").prop({
														'checked': true});
												
												$(
														"input[name="
																+ val.code
																+ "]").prop({
														'disabled':true});
												if (val.name == "1") {
													$(".depend" + val.code)
															.removeClass("hide");
												}
											} else if (val.componentType == "12") {
												var ids = val.photoIds;
												$(
														"input[name="
																+ val.code
																+ "]")
														.parent()
														.append(
																"<button type='button' class='btn btn-sm pull-right photo photoModel' onclick=enablePhotoModal('"
																		+ ids
																		+ "')><i class='fa fa-picture-o' aria-hidden='true'></i></button>")
														.append(
																"<button type='button' class='btn btn-sm pull-right' onclick=deletePhoto('img"
																		+ val.code
																		+ "')><i class='fa fa-remove' aria-hidden='true'></i></button>");
												$('#img' + val.code).attr(
														'src', ids);

											} else if (val.componentType == "13"
													|| val.componentType == "11") {
												var ids = val.photoIds;

												$(
														"input[name="
																+ val.code
																+ "]").parent();

												$('#img' + val.code).addClass(
														"vidd");
												$('#img' + val.code).attr(
														'src', ids);

											} else if (val.componentType == "7") {

												$('#' + val.code)
														.text(val.name);
											} else if (val.componentType == "14") {

												$('#' + val.code).text(
														val.dispName);
												$('#weatherVal' + val.code)
														.val(val.name);
											}else if (val.componentType == "15") {

												$('#plotValue' + val.code).val(
														val.name);
												
											}
										});
						var tempRefId = new Array;
						v.groups.sort(GetSortOrder("typez"));
						$(v.groups)
								.each(
										function(key, val) {

											if (tempRefId.indexOf(val.refId
													+ "-" + val.typez) == '-1') {
												listFormation = "";
												tempRefId.push(val.refId + "-"
														+ val.typez);
												var rowId = ("#tDynmaicRow" + val.refId);
												var newRow = $("<tr/>");
												var rowSize = ($(rowId)
														.children('td').length);
												rowSize--;
												$(rowId)
														.children("td")
														.each(
																function(i, v) {
																	if (i < rowSize) {
																		var classzName = $(
																				this)
																				.attr('class').split(' ')[0]
																				+ "t"
																				+ val.typez;
																		var td = $(
																				"<td/>")
																				.attr(
																						{
																							class : classzName
																						});
																		newRow
																				.append(td);
																		listFormation += classzName
																				+ "$$";
																	}
																});

												newRow
														.append("<td class='hide'><input type='hidden' class='listFormation' value="
																+ listFormation
																+ "></td>");

												var nthCol = (newRow.find("td")
														.size());
												button = $("<button>")
														.attr(
																{
																	class : "btn btn-danger",
																	onclick : 'removeRow(this)',
																	type : 'button'
																});
												button
														.html('<i class="fa fa-trash">');
												newRow.append($("<td>").append(
														button));

												$(rowId).parent()
														.append(newRow);
											}
										});

					});
	$(json).each(function(k, v) {
		var classzName = "";
		v.groups.sort(GetSortOrder("typez"));
		$(v.groups).each(function(key, val) {
			classzName = "." + val.code + "t" + val.typez;
			$(classzName).text(val.dispName);

			$(".listFormation").each(function(ki, vi) {
				tmp = val.code + "%%" + val.name;
				classzName = (val.code + "t" + val.typez);
				tempp = $(this).val().replace(classzName, tmp);
				$(this).val(tempp.toString());
			});

			/*
			 * tmp=val.code+"%%"+val.name; classzName =
			 * (val.code+"t"+val.typez); listFormation =
			 * listFormation.replace(classzName,tmp);
			 */
		});
	});

	console.log(listFormation);
	

}

/*
 * function formDropDownComponent(json){
 * 
 * 
 * $(json).each(function(k, v) { $(v.fields).each(function(key, val) { if
 * (val.compoType == '4'){ if(!isEmpty(val.catalogueType)){ $.ajax({ type:
 * "POST", async: false, url: "farmer_populateCatalogueByType.action", data:
 * {selectedCatalogue : val.catalogueType}, success: function(result) { } }); } }
 * });
 * 
 * $(v.groups).each(function(key, val) { if (val.compoType == '4'){
 * if(!isEmpty(val.catalogueType)){ $.ajax({ type: "POST", async: false, url:
 * "farmer_populateCatalogueByType.action", data: {selectedCatalogue :
 * val.catalogueType}, success: function(result) { insertOptionz(val.id,
 * $.parseJSON(result)); } }); } } });
 * 
 * });
 * 
 * 
 * var arrayOfObjects = []; var catalogueTypeArray = []; var catalogueNameArray =
 * [];
 * 
 * $(json).each(function(k, v) { $(v.fields).each(function(key, val) { if
 * (val.compoType == '4'){
 * 
 * $(val).each(function(k,a){
 * 
 * for (i = 1; i == val.id; i++) { var xyz = "catalogueType"+val.id alert(xyz);
 * 
 * $(a.catalogueType).each(function(k,b){ alert("entered")
 * catalogueTypeArray.push(b); }); $(a.catalogueName).each(function(k,c){
 * catalogueNameArray.push(c); }); }
 * 
 * }); }
 * 
 * 
 * for (i = 0; i < catalogueTypeArray.length; i++) {
 * arrayOfObjects.push({"id":""+catalogueTypeArray[i]+"","name":""+catalogueNameArray[i]+""}); }
 * insertOptionz(val.id, arrayOfObjects);
 * 
 * }); }); }
 */

function insertOptionz(ctrlName, jsonArr) {

	document.getElementById(ctrlName).length = 0;
	addOption(document.getElementById(ctrlName), "Select", "");
	for (var i = 0; i < jsonArr.length; i++) {
		addOption(document.getElementById(ctrlName), jsonArr[i].name,
				jsonArr[i].id);
	}

}

function createPlotting(val) {
	
	var className = "map btn  form-control btn btn-sts " + val.compoCode;
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";

	}

	var cbtn = new Array();
	var btn = $('<button/>').attr({
		type : 'button',
		class : className,
		id:val.compoCode,
		onclick : "showFarmMap('" +  val.compoCode + "')"
	});
	$(btn).text("Plotting");
	cbtn.push(btn);
	var hidden = $('<input/>').attr({
		type : 'hidden',
		class : className,
		id : 'plotValue' + val.compoCode

	});
	cbtn.push(hidden);
		wrapToDivz(cbtn, val);
	
	}

function createPhoto(val) {
 	var className = "photo form-control " + val.compoType;
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}
	var i;
	var file = $('<input/>').attr({
		type : 'file',
		class : className,
		id : val.id,
		name : val.compoCode,
		maxLength : val.maxLen

	});
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		file.append($("<sup style='color: red;'/>").html("*")).append($("<label style='color: red;width:100%;font-size:10px'/>").html("[.JPG, .JPEG, .PNG ] Image size should be less than 1 MB"));
	}

	file.attr("onchange", "setSrc(this)");
	file.append("<img id='img" + val.compoCode + "' />");

	wrapToDiv(file, val);
}
function setSrc(obj) {
	var value = $('input[name=' + $(obj).attr("name") + ']')[0].files[0];
	var filename = $('input[name=' + $(obj).attr("name") + ']').val();
	var fileExt = filename.split('.').pop();
	var fReader = new FileReader();
	fReader.readAsDataURL(value);
	fReader.onloadend = function(event) {
		var img = document.getElementById("img" + $(obj).attr("name"));
		img.src = event.target.result;
		$("#img" + $(obj).attr("name")).addClass(fileExt);
	}

}
function createTextBox(val) {
	var className = "text form-control";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}
	var i;
	var textBox = $('<input/>').attr({
		type : 'text',
		class : className,
		id : val.id,
		name : val.compoCode,
		maxLength : val.maxLen

	});

	if (!isEmpty(val.validation)) {
		if (val.validation == 1) {
			textBox.attr("onkeypress", "return isAlphabet(event)");
		} else if (val.validation == 2) {
			textBox.attr("onkeypress", "return isNumber(event)");
		} else if (val.validation == 3) {
			textBox.attr("onkeypress", "return isEmail(event)");
		} else if (val.validation == 4) {
			textBox.attr("onkeypress", "return isDecimal(event)");
		} else if (val.validation == 5) {
			textBox.attr("onkeypress", "return isAlphaNumeric(event)");
		}
	}

	if (!isEmpty(val.dependencyKey)) {
		if (val.constExist == '1') {
			textBox.addClass(" consChange");
		}

		textBox.attr("onchange", "calcFormula(this,'" + val.formula + "','"
				+ val.dependencyKey + "')");
	}

	wrapToDiv(textBox, val);
}

function createWeatherInfo(val) {
	var className = "weather";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";

	}

	var cbtn = new Array();
	// var textt ='<s:text name="Temperature"/><br><s:text
	// name="Rain"/><br><s:text name="Humidity"/><br><s:text name="Wind
	// Speed"/><br>';
	var label = $('<label/>').attr({
		type : 'text',
		class : className,
		id : val.compoCode
	// html :textt
	});

	cbtn.push(label);
	var hidden = $('<input/>').attr({
		type : 'hidden',
		class : '',
		id : 'weatherVal' + val.compoCode

	});
	cbtn.push(hidden);

	wrapToDivz(cbtn, val);
	$('#' + val.compoCode).text(val.weatherLabel);
}

function createTextArea(val) {

	var className = "text form-control";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += "  isRequired";

	}
	var textBox = $('<textarea/>').attr({
		type : 'text',
		class : className,
		id : val.id,
		name : val.compoCode,
		maxLength : val.maxLen
	});

	if (!isEmpty(val.validation)) {
		if (val.validation == 1) {
			textBox.attr("onkeypress", "return isAlphabet(event)");
		} else if (val.validation == 2) {
			textBox.attr("onkeypress", "return isNumber(event)");
		} else if (val.validation == 3) {
			textBox.attr("onkeypress", "return isEmail(event)");
		} else if (val.validation == 4) {
			textBox.attr("onkeypress", "return isDecimal(event)");
		} else if (val.validation == 5) {
			textBox.attr("onkeypress", "return isAlphaNumeric(event)");
		}
	}

	wrapToDiv(textBox, val);
}

function createTextAreaList(val) {
	var className = "textAreaList form-control";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}
	var textArea = $('<textarea/>').attr({
		type : 'text',
		class : className,
		id : val.id,
		name : val.compoCode,
		maxLength : val.maxLen
	});

	if (!isEmpty(val.validation)) {
		if (val.validation == 1) {
			textArea.attr("onkeypress", "return isAlphabet(event)");
		} else if (val.validation == 2) {
			textArea.attr("onkeypress", "return isNumber(event)");
		} else if (val.validation == 3) {
			textArea.attr("onkeypress", "return isEmail(event)");
		} else if (val.validation == 4) {
			textArea.attr("onkeypress", "return isDecimal(event)");
		} else if (val.validation == 5) {
			textArea.attr("onkeypress", "return isAlphaNumeric(event)");
		}
	}

	var rowId = "#dList" + val.refId;
	var row = $(rowId).find("tr");
	var td = ($("<td/>"));
	td.addClass(val.compoCode);

	if (!isEmpty(val.isReq) && val.isReq == '1') {
		td
				.append("<label class='height40'><b>" + val.compoName
						+ "</b><label>");
		td.append($("<sup style='color: red;'/>").html("*"));
	} else {
		td
				.append("<label class='height40'><b>" + val.compoName
						+ "</b><label>");
	}

	if (val.parentDepenCode != '' && val.parentDepenKey != '') {
		var classN = val.parentDepenCode + "_" + val.parentDepenKey;
		td.addClass("hide" + " par" + val.parentDepenCode);

		td.addClass(classN);
	}
	td.append(textArea);
	row.append(td);

}

function createDropDown(val) {

	var catalogueTypeArray = [];
	var catalogueNameArray = [];

	$(val).each(function(k, a) {
		$(a.catalogueType).each(function(k, b) {
			catalogueTypeArray.push(b);
		});
		$(a.catalogueName).each(function(k, c) {
			catalogueNameArray.push(c);
		});

	});

	var className = "dropDown form-control select2";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}
	var select = $('<select/>').attr({
		class : className,
		id : val.id,
		name : val.compoCode
	});

	var option = "<option value=''>Select </option>";

	$(select).append(option);

	for (i = 0; i < catalogueTypeArray.length; i++) {
		var option = "<option value='" + catalogueTypeArray[i] + "'>"
				+ catalogueNameArray[i] + "</option>";

		$(select).append(option);
	}
	if (!isEmpty(val.dependencyKey)) {


		select.attr("onchange", select.attr("onchange")+";calcFormula(this,'" + val.formula + "','"
		+ val.dependencyKey + "')");
		}
	wrapToDiv(select, val);

}

function createMultiDropDown(val) {

	var catalogueTypeArray = [];
	var catalogueNameArray = [];

	$(val).each(function(k, a) {
		$(a.catalogueType).each(function(k, b) {
			catalogueTypeArray.push(b);
		});
		$(a.catalogueName).each(function(k, c) {
			catalogueNameArray.push(c);
		});

	});

	var className = "multiDropDown form-control input-sm select2";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}
	$("#" + val.id + "").select2();
	var select = $('<select/>').attr({
		class : className,
		id : val.id,
		name : val.compoCode,
		multiple : "multiple"
	});

	for (i = 0; i < catalogueTypeArray.length; i++) {
		var option = "<option value='" + catalogueTypeArray[i] + "'>"
				+ catalogueNameArray[i] + "</option>";
		$(select).append(option);
	}

	wrapToDiv(select, val);

}

function createTextBoxList(val) {

	var className = "textList form-control";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";

	}
	var textBox = $('<input/>').attr({
		type : 'text',
		class : className,
		id : val.id,
		name : val.compoCode,
		maxLength : val.maxLen,
	// readonly:true
	});

	if (!isEmpty(val.validation)) {
		if (val.validation == 1) {
			textBox.attr("onkeypress", "return isAlphabet(event)");
		} else if (val.validation == 2) {
			textBox.attr("onkeypress", "return isNumber(event)");
		} else if (val.validation == 3) {
			textBox.attr("onkeypress", "return isEmail(event)");
		} else if (val.validation == 4) {
			textBox.attr("onkeypress", "return isDecimal(event)");
		} else if (val.validation == 5) {
			textBox.attr("onkeypress", "return isAlphaNumeric(event)");
		}
	}

	var rowId = "#dList" + val.refId;
	var row = $(rowId).find("tr");
	var td = ($("<td/>"));
	td.addClass(val.compoCode);

	if (!isEmpty(val.isReq) && val.isReq == '1') {
		td
				.append("<label class='height40'><b>" + val.compoName
						+ "</b><label>");
		td.append($("<sup style='color: red;'/>").html("*"));
	} else {
		td
				.append("<label class='height40'><b>" + val.compoName
						+ "</b><label>");
	}

	if (!isEmpty(val.dependencyKey)) {

		textBox.attr("onchange", "calcFormula(this,'" + val.formula + "','"
				+ val.dependencyKey + "')");
	}

	if (val.parentDepenCode != '' && val.parentDepenKey != '') {
		var classN = val.parentDepenCode + "_" + val.parentDepenKey;
		td.addClass("hide" + " par" + val.parentDepenCode);
		td.addClass(classN);
	}
	
	td = createDependantList(val,td,textBox);
	td.append($("<div class='listErr"+val.compoCode+"' style='color: red;'/div>"));
	td.append(textBox);

	if (!isEmpty(val.dependencyKey)) {
		var hidden = $('<input/>').attr({
			type : 'hidden',
			class : 'formula',
			id : 'formula' + val.compoCode,
			value : val.formula

		});
		var hiddenDep = $('<input/>').attr({
			type : 'hidden',
			class : 'dependanyc',
			id : 'dependanyc' + val.compoCode,
			value : val.dependencyKey

		});

		td.append(hidden);
		td.append(hiddenDep);
	}
	
	row.append(td);
}

function createDropDownList(val) {
	/*
	 * var textBox = $('<select/>').attr({ class : 'dropDownList form-control ',
	 * id : val.id, name:val.compoCode }); var rowId = "#dList"+val.refId; var
	 * row = $(rowId).find("tr"); var td =($("<td/>"));
	 * td.addClass(val.compoCode); td.append("<label class='height40'><b>"+val.compoName+"</b><label>");
	 * td.append(textBox); row.append(td);
	 */

	var catalogueTypeArray = [];
	var catalogueNameArray = [];

	$(val).each(function(k, a) {
		$(a.catalogueType).each(function(k, b) {
			catalogueTypeArray.push(b);
		});
		$(a.catalogueName).each(function(k, c) {
			catalogueNameArray.push(c);
		});

	});

	var className = "dropDownList form-control select2";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}
	var select = $('<select/>').attr({
		class : className,
		id : val.id,
		name : val.compoCode
	});

	var option = "<option value=''>Select </option>";

	$(select).append(option);
	for (i = 0; i < catalogueTypeArray.length; i++) {
		var option = "<option value='" + catalogueTypeArray[i] + "'>"
				+ catalogueNameArray[i] + "</option>";
		$(select).append(option);
	}

	var rowId = "#dList" + val.refId;
	var row = $(rowId).find("tr");
	var td = ($("<td/>"));
	td.addClass(val.compoCode);
	// td.append("<label class='height40'><b>"+val.compoName+"</b><label>");
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		td
				.append("<label class='height40'><b>" + val.compoName
						+ "</b><label>");
		td.append($("<sup style='color: red;'/>").html("*"));
	} else {
		td
				.append("<label class='height40'><b>" + val.compoName
						+ "</b><label>");
	}

	
	td = createDependantList(val,td,select);
	td.append($("<div class='listErr"+val.compoCode+"' style='color: red;'/div>"));
	
	td.append(select);
	// alert(val.name + " -"+val.isOther);
	if (val.isOther == '1') {
		
		td = createOtherTextBox(val,td,select)
		select.attr("onchange", select.attr("onchange")+";hideOther(this,'"+val.compoCode+"')");
	}
	
	row.append(td);

}
function createDependantList(val,td,select){
	
	if (val.catDepKey != '') {
		td.addClass("parentDep");
		select.attr("onchange", "showDepField(this)");

	}
	
	if (val.valueDep == '2') {
		td.addClass("parentDep");
		if (select.attr("onchange") != '') {
			select.attr("onchange", "showDepField(this)");
			select.attr("data-valueDep",'2');
			select.attr("data-listMeth",val.catType);
		} else {
			var cha = select.attr("onchange");
			cha = cha + ";showDepField(this)";
			select.attr("onchange", cha);
			select.attr("data-valueDep",'2');
			select.attr("data-listMeth",val.catType);
		}
	}

	
	if (val.parentDepenCode != '' && val.parentDepenKey != '') {
		if (val.parentDepenKey.indexOf(",") >= 0
				&& val.parentDepenCode.indexOf(",") < 0) {
			var values = val.parentDepenKey.split(",");
			$.each(values, function(i, e) {
				var classN = val.parentDepenCode + "_" + e.trim();
				td.addClass("hide" + " par" + val.parentDepenCode);
				select.addClass("parF" + val.parentDepenCode);
				td.addClass(classN);
			});
		} else if (val.parentDepenKey.indexOf(",") >= 0
				&& val.parentDepenCode.indexOf(",") >= 0) {
			var values = val.parentDepenKey.split(",");
			$.each(values, function(i, e) {
				var depCode = val.parentDepenCode.split(",");
				$.each(depCode, function(j, f) {
					var classN = f.trim() + "_" + e.trim();
					td.addClass("hide" + " par" + f.trim());
					select.addClass("parF" + f.trim());
					td.addClass(classN);
				});
			});
		} else if (val.parentDepenKey.indexOf(",") < 0
				&& val.parentDepenCode.indexOf(",") >= 0) {
			var values = val.parentDepenCode.split(",");
			$.each(values, function(i, e) {
				var classN = e.trim() + "_" + val.parentDepenKey;
				td.addClass("hide" + " par" + e.trim());
				select.addClass("parF" + e.trim());
				td.addClass(classN);
			});
		} else {
			var classN = val.parentDepenCode + "_" + val.parentDepenKey;
			td.addClass("hide" + " par" + val.parentDepenCode);
			select.addClass("parF" + val.parentDepenCode);
			td.addClass(classN);
		}
	}else if(val.parentDepenCode != '' && val.valueDep=='1'){
		select.addClass(" parF" + val.parentDepenCode+" valueDep1");
		select.attr("data-valueDep",'1');
		select.attr("data-listMeth",val.catType);
		
	}
	return td;
}
function createOtherTextBox(val,td,conponent){


	var btn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-sts add'+val.compoCode,
		onclick : "showOther(this,'" +  val.compoCode + "')"
	});
	// $(btn).text("Add "+val.compoName );
	var delbtn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-danger hide dang'+val.compoCode,
		onclick : "hideOther(this,'"  + val.compoCode + "')"
	});
	btn.html('<i class="fa fa-plus">');
	delbtn.html('<i class="fa fa-trash">');

	var hidden = $('<input/>').attr({
		type : 'text',
		maxlength : 45,
		class : 'form-control hide oth' + val.compoCode,
		id : 'other' + val.compoCode

	});
		td.append(hidden);
	td.append(delbtn);
	td.append(btn);	
	
	return td;

}

function createMultiDropDownList(val) {

	var catalogueTypeArray = [];
	var catalogueNameArray = [];

	$(val).each(function(k, a) {
		$(a.catalogueType).each(function(k, b) {
			catalogueTypeArray.push(b);
		});
		$(a.catalogueName).each(function(k, c) {
			catalogueNameArray.push(c);
		});

	});

	var className = "multiDropDownList form-control select2";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}
	var select = $('<select/>').attr({
		class : className,
		id : val.id,
		name : val.compoCode,
		multiple : "multiple"
	});

	for (i = 0; i < catalogueTypeArray.length; i++) {
		var option = "<option value='" + catalogueTypeArray[i] + "'>"
				+ catalogueNameArray[i] + "</option>";
		$(select).append(option);
	}

	var rowId = "#dList" + val.refId;
	var row = $(rowId).find("tr");
	var td = ($("<td/>"));
	td.addClass(val.compoCode);
	// td.append("<label class='height40'><b>"+val.compoName+"</b><label>");
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		td
				.append("<label class='height40'><b>" + val.compoName
						+ "</b><label>");
		td.append($("<sup style='color: red;'/>").html("*"));
	} else {
		td
				.append("<label class='height40'><b>" + val.compoName
						+ "</b><label>");
	}

	td = createDependantList(val,td,select);
	td.append($("<div class='listErr"+val.compoCode+"' style='color: red;'/div>"));
	td.append(select);
	if (val.isOther == '1') {
		td = createOtherTextBox(val,td,select)
			
	}

	row.append(td);

}

function createButtonList(val) {
	var btn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-sts',
		onclick : 'addDynamicList(this)'
	});
	$(btn).text("Add");
	var rowId = "#dList" + val.refId;
	var row = $(rowId).find("tr");
	var td = ($("<td/>"));
	td.addClass(val.compoCode);
	td.append("<label class='height40' style=''><b>Action</b><label>");
	td.append("<br>");
	td.append(btn);

	row.append(td);
}

function createDatePickerList(val) {
	var className = "dateList form-control";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}
	var textBox = $('<input/>').attr({
		type : 'text',
		class : className,
		id : val.id,
		readonly : 'readonly',
		name : val.compoCode,
		'data-date-format' : val.dataFormat,
	});
	var rowId = "#dList" + val.refId;
	var row = $(rowId).find("tr");
	var td = ($("<td/>"));
	// td.append("<label class='height40'><b>"+val.compoName+"</b><label>");
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		td
				.append("<label class='height40'><b>" + val.compoName
						+ "</b><label>");
		td.append($("<sup style='color: red;'/>").html("*"));
	} else {
		td
				.append("<label class='height40'><b>" + val.compoName
						+ "</b><label>");
	}
	td.append($("<div class='listErr"+val.compoCode+"' style='color: red;'/div>"));
	td.append(textBox);
	td.addClass(val.compoCode);
	td = createDependantList(val,td,textBox);
	row.append(td);
	$("#" + val.id).datepicker({
		dateFormat : val.dataFormat,
		changeMonth : true,
		changeYear : true
	}).on('change', function(){
        $('.datepicker').hide();
        });

}

function createDatePicker(val) {

	var className = "date form-control";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}
	var textBox = $('<input/>').attr({
		type : 'text',
		class : className,
		id : val.id,
		readonly : 'readonly',
		'data-date-format' : val.dataFormat,
		name : val.compoCode
	});
	wrapToDiv(textBox, val);

	$("#" + val.id).datepicker({
		dateFormat : val.dataFormat,
		changeMonth : true,
		changeYear : true
	}).on('change', function(){
        $('.datepicker').hide();
    });

}

function createLabel(val) {

	var className = "label";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";

	}

	var cbtn = new Array();

	var label = $('<label/>').attr({
		type : 'text',
		class : className,
		id : val.compoCode
	});

	cbtn.push(label);
	// alert("val.dependencyKey :"+val.dependencyKey);
	if (!isEmpty(val.dependencyKey)) {
		var hidden = $('<input/>').attr({
			type : 'hidden',
			class : 'formulaLabel',
			id : 'formulaLabel' + val.compoCode

		});
		if (val.constExist == '1') {
			hidden.addClass(" consChange");
		}

		hidden.attr("onchange", "calcFormula(this,'" + val.formula + "','"
				+ val.dependencyKey + "')");
		/* alert("hidden : "+hidden); */
		cbtn.push(hidden);
	}
	wrapToDivz(cbtn, val);
}

function createLabelList(val) {

	var className = "labelList";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";

	}

	var label = $('<label/>').attr({
		type : 'text',
		class : className,
		id : val.compoCode
	});

	// $(btn).text("Add");
	var rowId = "#dList" + val.refId;

	var row = $(rowId).find("tr");
	var td = ($("<td/>"));
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		td.append("<label class='height40'><br>" + val.compoName
				+ "</br></label>");
		td.append($("<sup style='color: red;'/>").html("*"));
	} else {
		td.append("<label class='height40'><b>" + val.compoName
				+ "</b></label>");
	}
	td.addClass(val.compoCode);
	/*
	 * td.append("<label class='height40' style=''><b>" + val.compoName + "</b><label>"); //
	 * td.append("<label class='height40' style=''><b>Action</b><label>");
	 * td.append("<br>");
	 */
	td.append($("<div class='listErr"+val.compoCode+"' style='color: red;'/div>"));
	td.append(label);
	if (!isEmpty(val.dependencyKey)) {
		var hidden = $('<input/>').attr({
			type : 'hidden',
			class : 'formula',
			id : 'formula' + val.compoCode,
			value : val.formula

		});
		var hiddenDep = $('<input/>').attr({
			type : 'hidden',
			class : 'dependanyc',
			id : 'dependanyc' + val.compoCode,
			value : val.dependencyKey

		});

		td.append(hidden);
		td.append(hiddenDep);
	}

	row.append(td);
}

function createWeatherInfoList(val) {

	var className = "labelList";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";

	}

	// var textt ='<s:text name="Temperature"/><br><s:text
	// name="Rain"/><br><s:text name="Humidity"/><br><s:text name="Wind
	// Speed"/><br>';
	var label = $('<label/>').attr({
		type : 'text',
		class : className,
		id : val.compoCode,
		text : textt
	});

	// $(btn).text("Add");
	var rowId = "#dList" + val.refId;

	var row = $(rowId).find("tr");
	var td = ($("<td/>"));
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		td.append("<label class='height40'><br>" + val.compoName
				+ "</br></label>");
		td.append($("<sup style='color: red;'/>").html("*"));
	} else {
		td.append("<label class='height40'><b>" + val.compoName
				+ "</b></label>");
	}
	td.addClass(val.compoCode);
	td.append(label);

	row.append(td);
	$('#' + val.compoCode).text(val.weatherLabel);
}

function createList(val) {
	var className = "";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " listContainer isRequired";
	}

	var table = $("<table/>").addClass("table table-bordered" + className)
			.attr({
				id : "dList" + val.id
			});
var tbody  =  $("<tbody/>").addClass( val.compoName);

	var tr = $("<tr/>").attr({
		id : "tDynmaicRow" + val.id
	});
	tbody.append(tr);
	table.append(tbody);
	
	wrapToList(table, val);
}

function createRadio(val) {

	var catalogueTypeArray = [];
	var catalogueNameArray = [];
	var rbtn = new Array();
	var className = "radio";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}

	$(val).each(function(k, a) {
		$(a.catalogueType).each(function(k, b) {
			catalogueTypeArray.push(b);
		});
		$(a.catalogueName).each(function(k, c) {
			catalogueNameArray.push(c);
		});

	});

	for (i = 0; i < catalogueTypeArray.length; i++) {
		var radio = $('<input/>').attr({
			type : 'radio',
			class : className,
			name : val.compoCode,
			value : catalogueTypeArray[i]
		});

		if (val.catDepKey != '') {
			radio.attr("onchange", "showDepField(this)");

		}

		if (val.parentDepenCode != '' && val.parentDepenKey != '') {
			if (val.parentDepenKey.indexOf(",") >= 0
					&& val.parentDepenCode.indexOf(",") < 0) {
				var values = val.parentDepenKey.split(",");
				$.each(values, function(i, e) {
					var classN = val.parentDepenCode + "_" + e.trim();
					radio.addClass("parF" + val.parentDepenCode);
				});
			} else if (val.parentDepenKey.indexOf(",") >= 0
					&& val.parentDepenCode.indexOf(",") >= 0) {
				var values = val.parentDepenKey.split(",");
				$.each(values, function(i, e) {
					var depCode = val.parentDepenCode.split(",");
					$.each(depCode, function(j, f) {
						var classN = f.trim() + "_" + e.trim();
						radio.addClass("parF" + f.trim());
					});
				});
			} else if (val.parentDepenKey.indexOf(",") < 0
					&& val.parentDepenCode.indexOf(",") >= 0) {
				var values = val.parentDepenCode.split(",");
				$.each(values, function(i, e) {
					var classN = e.trim() + "_" + val.parentDepenKey;
					radio.addClass("parF" + val.parentDepenKey);
				});
			} else {
				var classN = val.parentDepenCode + "_" + val.parentDepenKey;
				radio.addClass("parF" + val.parentDepenCode);
			}
		}

		var label = $("<label>").text(catalogueNameArray[i]);
		rbtn.push(radio);
		rbtn.push(label);
	}
	wrapToDivz(rbtn, val);
}

function createCheckbox(val) {
	var catalogueTypeArray = [];
	var catalogueNameArray = [];

	var className = "checkbox";
	var cbtn = new Array();
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}

	$(val).each(function(k, a) {
		$(a.catalogueType).each(function(k, b) {
			catalogueTypeArray.push(b);
		});
		$(a.catalogueName).each(function(k, c) {
			catalogueNameArray.push(c);
		});

	});

	for (i = 0; i < catalogueTypeArray.length; i++) {
		var checkBox = $('<input/>').attr({
			type : 'checkbox',
			class : className,
			name : val.compoCode,
			value : catalogueTypeArray[i]
		});
		if (val.catDepKey != '') {
			checkBox.attr("onchange", "showDepField(this)");

		}

		if (val.parentDepenCode != '' && val.parentDepenKey != '') {
			if (val.parentDepenKey.indexOf(",") >= 0
					&& val.parentDepenCode.indexOf(",") < 0) {
				var values = val.parentDepenKey.split(",");
				$.each(values, function(i, e) {
					var classN = val.parentDepenCode + "_" + e.trim();
					checkBox.addClass("parF" + val.parentDepenCode);
				});
			} else if (val.parentDepenKey.indexOf(",") >= 0
					&& val.parentDepenCode.indexOf(",") >= 0) {
				var values = val.parentDepenKey.split(",");
				$.each(values, function(i, e) {
					var depCode = val.parentDepenCode.split(",");
					$.each(depCode, function(j, f) {
						var classN = f.trim() + "_" + e.trim();
						checkBox.addClass("parF" + f.trim());
					});
				});
			} else if (val.parentDepenKey.indexOf(",") < 0
					&& val.parentDepenCode.indexOf(",") >= 0) {
				var values = val.parentDepenCode.split(",");
				$.each(values, function(i, e) {
					var classN = e.trim() + "_" + val.parentDepenKey;
					checkBox.addClass("parF" + val.parentDepenKey);
				});
			} else {
				var classN = val.parentDepenCode + "_" + val.parentDepenKey;
				checkBox.addClass("parF" + val.parentDepenCode);
			}
		}

		var label = $("<label>").text(catalogueNameArray[i]);
		cbtn.push(checkBox);
		cbtn.push(label);
	}

	/*
	 * if(!isEmpty(val.defaultVal)){ var checkBox = val.defaultVal.split(",");
	 * $(checkBox).each(function(k,v){ var txtValue=v.split("="); var label =
	 * $("<label>").text(txtValue[1]); var cb = $('<input/>').attr({ type :
	 * 'checkbox', class : className, name:val.compoCode, value : txtValue[0]
	 * }); cbtn.push(cb); cbtn.push(label); }); }else{ }
	 */

	wrapToDivz(cbtn, val);
}

function createRadioList(val) {
	var catalogueTypeArray = [];
	var catalogueNameArray = [];

	var className = "radioList";
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}

	$(val).each(function(k, a) {
		$(a.catalogueType).each(function(k, b) {
			catalogueTypeArray.push(b);
		});
		$(a.catalogueName).each(function(k, c) {
			catalogueNameArray.push(c);
		});

	});

	var rowId = "#dList" + val.refId;
	var row = $(rowId).find("tr");

	var td = ($("<td />"));

	td.addClass(val.compoCode);
	td.append("<label class='height40'><b>" + val.compoName + "</b><label>");

	if (!isEmpty(val.isReq) && val.isReq == '1') {
		td.append($("<sup style='color: red;'/>").html("*"));
	}
	td.append($("<div class='listErr"+val.compoCode+"' style='color: red;'/div>"));
	// td.append("<br/>");

	for (i = 0; i < catalogueTypeArray.length; i++) {
		var radio = $('<input/>').attr({
			type : 'radio',
			class : className,
			name : val.compoCode,
			value : catalogueTypeArray[i]
		});

		// var td2 =($("<td/>"));
		// td2.append(radio);
		td.append("<br/>");

		td = createDependantList(val,td,radio);
	
		td.append(radio);
		var label = $(
				"<label id='" + val.compoCode + "-" + catalogueTypeArray[i]
						+ "'>").text(catalogueNameArray[i]);
		td.append(label);
		
		
		
	}
	if (val.isOther == '1') {
		td = createOtherTextBox(val,td,radio)
		radio.attr("onchange", select.attr("onchange")+";hideOther(this,'"+val.compoCode+"')");
	}
	row.append(td);
}

function createCheckboxList(val) {

	var catalogueTypeArray = [];
	var catalogueNameArray = [];

	var className = "checkboxList";
	var cbtn = new Array();
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		className += " isRequired";
	}

	$(val).each(function(k, a) {
		$(a.catalogueType).each(function(k, b) {
			catalogueTypeArray.push(b);
		});
		$(a.catalogueName).each(function(k, c) {
			catalogueNameArray.push(c);
		});

	});

	var rowId = "#dList" + val.refId;
	var row = $(rowId).find("tr");

	var td = ($("<td/>"));

	td.addClass(val.compoCode);
	// td.append("<label class='height40'><b>"+val.compoName+"</b><label>");
	/*
	 * if(!isEmpty(val.isReq)&&val.isReq=='1'){ td.append("<label
	 * class='height40'><b>"+val.compoName+"</b><label>"); td.append($("<sup
	 * style='color: red;'/>").html("*")); }else{ td.append("<label
	 * class='height40'><b>"+val.compoName+"</b><label>"); }
	 */
	td.append("<label class='height40'><b>" + val.compoName + "</b><label>");

	if (!isEmpty(val.isReq) && val.isReq == '1') {
		td.append($("<sup style='color: red;'/>").html("*"));
	}
	td.append($("<div class='listErr"+val.compoCode+"' style='color: red;'/div>"));
	for (i = 0; i < catalogueTypeArray.length; i++) {
		var checkBox = $('<input/>').attr({
			type : 'checkbox',
			class : className,
			name : val.compoCode,
			value : catalogueTypeArray[i]
		});
		td.append("<br/>");
		// var td2 =($("<td />"));

		if (val.catDepKey != '') {
			td.addClass("parentDep");
			checkBox.attr("onchange", "showDepField(this)");

		}
		if (val.parentDepenCode != '' && val.parentDepenKey != '') {
			if (val.parentDepenKey.indexOf(",") >= 0
					&& val.parentDepenCode.indexOf(",") < 0) {
				var values = val.parentDepenKey.split(",");
				$.each(values, function(i, e) {
					var classN = val.parentDepenCode + "_" + e.trim();
					td.addClass("hide" + " par" + val.parentDepenCode);
					checkBox.addClass("parF" + val.parentDepenCode);
					td.addClass(classN);
				});
			} else if (val.parentDepenKey.indexOf(",") >= 0
					&& val.parentDepenCode.indexOf(",") >= 0) {
				var values = val.parentDepenKey.split(",");
				$.each(values, function(i, e) {
					var depCode = val.parentDepenCode.split(",");
					$.each(depCode, function(j, f) {
						var classN = f.trim() + "_" + e.trim();
						td.addClass("hide" + " par" + f.trim());
						checkBox.addClass("parF" + f.trim());
						td.addClass(classN);
					});
				});
			} else if (val.parentDepenKey.indexOf(",") < 0
					&& val.parentDepenCode.indexOf(",") >= 0) {
				var values = val.parentDepenCode.split(",");
				$.each(values, function(i, e) {
					var classN = e.trim() + "_" + val.parentDepenKey;
					td.addClass("hide" + " par" + e.trim());
					checkBox.addClass("parF" + e.trim());
					td.addClass(classN);
				});
			} else {
				var classN = val.parentDepenCode + "_" + val.parentDepenKey;
				td.addClass("hide" + " par" + val.parentDepenCode);
				checkBox.addClass("parF" + val.parentDepenCode);
				td.addClass(classN);
			}
		}
	
		td.append(checkBox);
		var label = $(
				"<label id='" + val.compoCode + "-" + catalogueTypeArray[i]
						+ "'>").text(catalogueNameArray[i]);
		td.append(label);
		
	
		

	}
	if (val.isOther == '1') {

		td = createOtherTextBox(val,td,checkBox)
		
	}
	row.append(td);
}

function wrapToDiv(component, val) {
	var wrappedDiv = $("<div/>").addClass("flexform-item");
	if ($(component).hasClass('radio') || $(component).hasClass('checkbox')) {
		if (val.catDepKey != '') {
			wrappedDiv.addClass("parentDep");
		}
		
		if (val.valueDep == '2') {
			wrappedDiv.addClass("parentDep");
			if (select.attr("onchange") != '') {
				select.attr("onchange", "showDepField(this)");
				select.attr("data-valueDep",'2');
				select.attr("data-listMeth",val.catType);
			} else {
				var cha = select.attr("onchange");
				cha = cha + ";showDepField(this)";
				select.attr("onchange", cha);
				select.attr("data-valueDep",'2');
				select.attr("data-listMeth",val.catType);
			}
		}
		
		
		

		if (val.parentDepenCode != '' && val.parentDepenKey != '') {
			if (val.parentDepenKey.indexOf(",") >= 0
					&& val.parentDepenCode.indexOf(",") < 0) {
				var values = val.parentDepenKey.split(",");
				$.each(values, function(i, e) {
					var classN = val.parentDepenCode + "_" + e.trim();
					wrappedDiv.addClass("hide" + " par" + val.parentDepenCode);
					wrappedDiv.addClass(classN);
				});
			} else if (val.parentDepenKey.indexOf(",") >= 0
					&& val.parentDepenCode.indexOf(",") >= 0) {
				var values = val.parentDepenKey.split(",");
				$.each(values, function(i, e) {
					var depCode = val.parentDepenCode.split(",");
					$.each(depCode, function(j, f) {
						var classN = f.trim() + "_" + e.trim();
						wrappedDiv.addClass("hide" + " par" + f.trim());
						wrappedDiv.addClass(classN);
					});
				});
			} else if (val.parentDepenKey.indexOf(",") < 0
					&& val.parentDepenCode.indexOf(",") >= 0) {
				var values = val.parentDepenCode.split(",");
				$.each(values, function(i, e) {
					var classN = e.trim() + "_" + val.parentDepenKey;
					wrappedDiv.addClass("hide" + " par" + e.trim());
					wrappedDiv.addClass(classN);
				});
			} else {
				var classN = val.parentDepenCode + "_" + val.parentDepenKey;
				wrappedDiv.addClass("hide" + " par" + val.parentDepenCode);
				wrappedDiv.addClass(classN);
			}
		}else if(val.parentDepenCode != '' && val.valueDep=='1'){
			component.addClass(" parF" + val.parentDepenCode+" valueDep1");
			component.attr("data-valueDep",'1');
			component.attr("data-listMeth",val.catType);
			
		}
	} else {
		if (val.catDepKey != '') {
			wrappedDiv.addClass("parentDep");
			if (component.attr("onchange") != '') {
				component.attr("onchange", "showDepField(this)");
				component.attr("data-valueDep",'2');
				component.attr("data-listMeth",val.catType);
			} else {
				var cha = component.attr("onchange");
				cha = cha + ";showDepField(this)";
				component.attr("onchange", cha);
				component.attr("data-valueDep",'2');
				component.attr("data-listMeth",val.catType);
			}

		}if (val.valueDep == '2') {
			wrappedDiv.addClass("parentDep");
			if (component.attr("onchange") != '') {
				component.attr("onchange", "showDepField(this)");
			} else {
				var cha = component.attr("onchange");
				cha = cha + ";showDepField(this)";
				component.attr("onchange", cha);
			}
		}

		if (val.parentDepenCode != '' && val.parentDepenKey != '') {
			if (val.parentDepenKey.indexOf(",") >= 0
					&& val.parentDepenCode.indexOf(",") < 0) {
				var values = val.parentDepenKey.split(",");
				$.each(values, function(i, e) {
					var classN = val.parentDepenCode + "_" + e.trim();
					wrappedDiv.addClass("hide" + " par" + val.parentDepenCode);
					component.addClass("parF" + val.parentDepenCode);
					wrappedDiv.addClass(classN);
				});
			} else if (val.parentDepenKey.indexOf(",") >= 0
					&& val.parentDepenCode.indexOf(",") >= 0) {
				var values = val.parentDepenKey.split(",");
				$.each(values, function(i, e) {
					var depCode = val.parentDepenCode.split(",");
					$.each(depCode, function(j, f) {
						var classN = f.trim() + "_" + e.trim();
						wrappedDiv.addClass("hide" + " par" + f.trim());
						component.addClass("parF" + f.trim());
						wrappedDiv.addClass(classN);
					});
				});
			} else if (val.parentDepenKey.indexOf(",") < 0
					&& val.parentDepenCode.indexOf(",") >= 0) {
				var values = val.parentDepenCode.split(",");
				$.each(values, function(i, e) {
					var classN = e.trim() + "_" + val.parentDepenKey;
					wrappedDiv.addClass("hide" + " par" + e.trim());
					component.addClass("parF" + e.trim());
					wrappedDiv.addClass(classN);
				});
			} else {
				var classN = val.parentDepenCode + "_" + val.parentDepenKey;
				wrappedDiv.addClass("hide" + " par" + val.parentDepenCode);
				component.addClass("parF" + val.parentDepenCode);
				wrappedDiv.addClass(classN);
			}
		}else if(val.parentDepenCode != '' && val.valueDep=='1'){
			component.addClass(" parF" + val.parentDepenCode+" valueDep1");
			component.attr("data-valueDep",'1');
			component.attr("data-listMeth",val.catType);
			
		}
	}

	var label = $("<label>").text(val.compoName);
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		label.append($("<sup style='color: red;'/>").html("*"));
	}
	/*
	 * if (val.compoType == '12') { label.append($("<label style='color:
	 * red;width:100%;font-size:10px'/>").html("Image size should be less than 1
	 * MB")); }
	 */

	wrappedDiv.append(label);
	var form_element = $("<div/>").addClass("form-element");
	form_element.append(component);
	if (val.isOther == '1') {
		form_element = createOtherTextBox(val,form_element,component);
	if($(component).hasClass('dropDown') || $(component).hasClass('radio')){
		$(component).attr("onchange", $(component).attr("onchange")+";hideOther(this,'"+val.compoCode+"')");
	}
	form_element.css("display","block");
	}
	wrappedDiv.append(form_element);

	$("#" + val.sectionId).find(".flexform").append(wrappedDiv);
	if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){

		var insertEle = $('[name="' + val.afterInsert + '"]').parent().parent();

		$(wrappedDiv).insertAfter(insertEle);
	}

}

function showOther(obj, otherId) {
	if(!$("[name='"+otherId+"']").hasClass('multiDropDownList') && $("[name='"+otherId+"']").hasClass('dropDownList')){
		
		$("[name='"+otherId+"']").val('').select2();
	}
	$('#other' + otherId).val('');
		$('#other' + otherId).removeClass("hide");
		$('.dang' + otherId).removeClass("hide");
		$('.add' + otherId).addClass("hide");
	

}


function hideOther(obj, otherId) {
	$('#other' + otherId).val('');
		$('#other' + otherId).addClass("hide");
		$('.dang' + otherId).addClass("hide");
		$('.add' + otherId).removeClass("hide");
	

}

function showDepField(obj) {
	var field = $(obj).attr("name");
	var objValue = $(obj).val().toString();
	if ($(obj).hasClass('radio') || $(obj).hasClass('checkbox')) {
		objValue = $(obj).filter(':checked').val();
	}else if($(obj).hasClass('radioList') || $(obj).hasClass('checkboxList')){
		objValue  =getRadioValueByName(field);
	}

	if ($(obj).attr("data-valueDep")=='2' && objValue!=''){
		$('.parF' + field).each(function( i ) {
			var idVal = $(this).attr("id");
			if($(this).hasClass('valueDep1')){
				$.ajax({
					type : "POST",
					async : false,
					url : "farmer_populateDepListMethos.action?postdata="+$(obj).attr("data-listMeth")+"~"+objValue,
					success : function(result) {
						insertOptionz(idVal,result);
					}
				});
			}
		});


	  }
	
	
	$('.par' + field).addClass("hide");
	if (objValue.indexOf(",") > -1) {
		$((objValue.split(","))).each(function(k, v) {
			var className = field + "_" + v.trim();

			$("." + className).removeClass("hide");
		});
	} else {
		var className = field + "_" + objValue;
		$("." + className).removeClass("hide");
	}
	resetValues($('.parF' + field)); 
	$('.parF' + field).trigger("change");

}

function resetValues(obj){
	if($(obj).hasClass('radio') || $(obj).hasClass('radioList') || $(obj).hasClass('checkbox') || $(obj).hasClass('checkboxList')){
		$(obj).attr("checked",false);
	}else{
		$(obj).val("");
	}
	
}

function wrapToList(component, val) {
	var wrappedDiv = $("<div/>").addClass("row col-md-12 margin-top2");

	if (val.parentDepenCode != '' && val.parentDepenKey != '') {
		var classN = val.parentDepenCode + "_" + val.parentDepenKey;
		wrappedDiv.addClass("hide" + " par" + val.parentDepenCode);
		wrappedDiv.addClass(classN);
	}

	/*
	 * if (!isEmpty(val.isReq) && val.isReq == '1') { // tr.append($("<sup
	 * style='color: red;'/>").html("*")); wrappedDiv.append($("<h1 style='color: red;'/h6>").html("*")); //
	 * document.write('<h1></h1>'); }
	 */

	wrappedDiv.append(component);

	$("#" + val.sectionId).find(".flexform").append(wrappedDiv);
}

function wrapToDivz(componentArr, val) {
	var wrappedDiv = $("<div/>").addClass("flexform-item");
	var component = componentArr[0];
	if (val.catDepKey != '') {
		wrappedDiv.addClass("parentDep");
		if (component.attr("onchange") != '') {
			component.attr("onchange", "showDepField(this)");
		} else {
			var cha = component.attr("onchange");
			cha = cha + ";showDepField(this)";
			component.attr("onchange", cha);
		}

	}
	if (val.valueDep == '2') {
		wrappedDiv.addClass("parentDep");
		if (component.attr("onchange") != '') {
			component.attr("onchange", "showDepField(this)");
			component.attr("data-valueDep",'2');
			component.attr("data-listMeth",val.catType);
		} else {
			var cha = component.attr("onchange");
			cha = cha + ";showDepField(this)";
			component.attr("onchange", cha);
			component.attr("data-valueDep",'2');
			component.attr("data-listMeth",val.catType);
		}
	}
	if (val.parentDepenCode != '' && val.parentDepenKey != '') {
		if (val.parentDepenKey.indexOf(",") >= 0
				&& val.parentDepenCode.indexOf(",") < 0) {
			var values = val.parentDepenKey.split(",");
			$.each(values, function(i, e) {
				var classN = val.parentDepenCode + "_" + e.trim();
				wrappedDiv.addClass("hide" + " par" + val.parentDepenCode);
				component.addClass("parF" + val.parentDepenCode);
				wrappedDiv.addClass(classN);
			});
		} else if (val.parentDepenKey.indexOf(",") >= 0
				&& val.parentDepenCode.indexOf(",") >= 0) {
			var values = val.parentDepenKey.split(",");
			$.each(values, function(i, e) {
				var depCode = val.parentDepenCode.split(",");
				$.each(depCode, function(j, f) {
					var classN = f.trim() + "_" + e.trim();
					wrappedDiv.addClass("hide" + " par" + f.trim());
					component.addClass("parF" + f.trim());
					wrappedDiv.addClass(classN);
				});
			});
		} else if (val.parentDepenKey.indexOf(",") < 0
				&& val.parentDepenCode.indexOf(",") >= 0) {
			var values = val.parentDepenCode.split(",");
			$.each(values, function(i, e) {
				var classN = e.trim() + "_" + val.parentDepenKey;
				wrappedDiv.addClass("hide" + " par" + e.trim());
				component.addClass("parF" + e.trim());
				wrappedDiv.addClass(classN);
			});
		} else {
			var classN = val.parentDepenCode + "_" + val.parentDepenKey;
			wrappedDiv.addClass("hide" + " par" + val.parentDepenCode);
			component.addClass("parF" + val.parentDepenCode);
			wrappedDiv.addClass(classN);
		}
	}else if(val.parentDepenCode != '' && val.valueDep=='1'){
		component.addClass(" parF" + val.parentDepenCode+" valueDep1");
		component.attr("data-valueDep",'1');
		component.attr("data-listMeth",val.catType);
		
	}

	var label = $("<label>").text(val.compoName);
	if (!isEmpty(val.isReq) && val.isReq == '1') {
		label.append($("<sup style='color: red;'/>").html("*"));
	}
	wrappedDiv.append(label);
	var form_element = $("<div/>").addClass("form-element");
	$(componentArr).each(function() {
		form_element.append($(this));
	});

	if (val.isOther == '1') {

		form_element = createOtherTextBox(val,form_element,component)
		if($(component).hasClass('dropDown') || $(component).hasClass('radio')){
			$(component).attr("onchange", $(component).attr("onchange")+";hideOther(this,'"+val.compoCode+"')");
		}
		form_element.css("display","block");
	}

	wrappedDiv.append(form_element);
	wrappedDiv.append("<br></br>")
	$("#" + val.sectionId).find(".flexform").append(wrappedDiv);

	if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){

		var insertEle = $('[name="' + val.afterInsert + '"]').parent().parent();

		$(wrappedDiv).insertAfter(insertEle);
	}

}

function addDynamicList(obj) {
	var tr = $(obj).parent().parent();
	var table = $(obj).parent().parent().parent();
	var tRow = $("<tr/>");
	var flag = true;
	var listSize = ($(tr).children('td').length - 1);

	var finalListFormated = "";

	var isReq = true;
	var val = "";
	var listSize = ($(tr).find("td").size() - 1);

	$(table)
			.find("tr")
			.each(
					function(i, v) {

						$(this)
								.find("td:not('.hide')")
								.each(
										function(i, v) {
											$(this).find("[class^=listErr]").html('');
											
											if ($(this).find(
													".isRequired")
													.hasClass("isRequired")) {

												if ($(this).find(
														".dropDownList")
														.hasClass(
																"dropDownList")) {
													var compoObj = $(this)
															.find(
																	".dropDownList");
													
													val = compoObj.val();
													if($('#other'+$(compoObj).attr("name")).val()!='' && $('#other'+$(compoObj).attr("name")).val()!=undefined && $('#other'+$(compoObj).attr("name")).val()!=null){
														val = $('#other'+$(compoObj).attr("name")).val();
													}
													if (val == "Select"
															|| isEmpty(val)
															|| val == "") {
														$(".listErr"+$(compoObj).attr("name"))
																.html(
																		"Please Select Value For  "
																				+ $(
																						this)
																						.children('label:first')
																				        .text()
																						.replace(
																								"*",
																								""));
														// window.location.href
														// = "#errorDiv";
														isReq = false;
													}
												} else if ($(this)
														.find(
																".multiDropDownList")
														.hasClass(
																"multiDropDownList")) {
													var compoObj = $(this)
															.find(
																	".multiDropDownList");
													val = compoObj.val();
													if($('#other'+$(compoObj).attr("name")).val()!='' && $('#other'+$(compoObj).attr("name")).val()!=undefined && $('#other'+$(compoObj).attr("name")).val()!=null){
														
														if(val==null || val=='' ){
															val = $('#other'+$(compoObj).attr("name")).val();
														}else{
														val = 
															val+','+$('#other'+$(compoObj).attr("name")).val();
														}
													}
													if (val == null) {
														$(".listErr"+$(compoObj).attr("name"))
														.html(
																		"Please Select Value For  "
																+ $(
																		this)
																		.children('label:first')
																        .text()
																		.replace(
																				"*",
																				""));
														// window.location.href
														// = "#errorDiv";
														isReq = false;

													}
												} else if ($(this).find(
														".textList").hasClass(
														"textList")) {
													var textVal = ($(this)
															.find(".textList")
															.val());
													if (textVal == "") {
														$(".listErr"+$(this)
																.find(".textList").attr("name"))
														.html(
																		"Please Select Value For  "
																+ $(
																		this)
																		.children('label:first')
																        .text()
																		.replace(
																				"*",
																				""));
														// window.location.href
														// = "#errorDiv";
														isReq = false;
													}
												} else if ($(this).find(
														".dateList").hasClass(
														"dateList")) {
													var textVal = ($(this)
															.find(".dateList")
															.val());
													if (textVal == "") {
														$(".listErr"+$(this)
																.find(".dateList").attr("name"))
														.html(
																		"Please Select Value For  "
																+ $(
																		this)
																		.children('label:first')
																        .text()
																		.replace(
																				"*",
																				""));
														// window.location.href
														// = "#errorDiv";
														isReq = false;
													}
												} else if ($(this).find(
														".textAreaList")
														.hasClass(
																"textAreaList")) {
													var textVal = ($(this)
															.find(
																	".textAreaList")
															.val());
													if (textVal == "") {
														$(".listErr"+$(this)
																.find(".textAreaList").attr("name"))
														.html(
																		"Please Select Value For  "
																+ $(
																		this)
																		.children('label:first')
																        .text()
																		.replace(
																				"*",
																				""));
														// window.location.href
														// = "#errorDiv";
														isReq = false;
													}
												} else if ($(this).find(
														".radioList").hasClass(
														"radioList")) {
													var compoObj = $(this)
															.find(".radioList");
													var name = $(this)
													.find(".radioList")
															.attr("name");
													radioBtnValue = getRadioValueByName(name);
													
													if($('#other'+name).val()!='' && $('#other'+name).val()!=undefined && $('#other'+name).val()!=null){
														if(radioBtnValue==null){
															radioBtnValue='';
														}
														radioBtnValue = radioBtnValue+','+$('#other'+$(compoObj).attr("name")).val();
													}
													
													
													if (isEmpty(radioBtnValue)
															|| radioBtnValue == "undefined") {
														$(".listErr"+$(compoObj).attr("name"))
														.html(
																		"Please Select Value For  "
																+ $(
																		this)
																		.children('label:first')
																        .text()
																		.replace(
																				"*",
																				""));
														// window.location.href
														// = "#errorDiv";
														isReq = false;
													}
												} else if ($(this).find(
														".checkboxList")
														.hasClass(
																"checkboxList")) {
													var compoObj = $(this)
															.find(
																	".checkboxList");
													var name =  $(this)
													.find(".checkboxList")
															.attr("name");
													checkBoxValue = getCheckBoxValueByName(name);
													var cb_value = "";
													if (checkBoxValue != null) {
														for (i = 0; i < checkBoxValue.length; i++) {
															cb_value = cb_value
																	+ checkBoxValue[i];
														}
													}
													if($('#other'+name).val()!='' && $('#other'+name).val()!=undefined && $('#other'+name).val()!=null){
														if(cb_value==null){
															cb_value='';
														}
														cb_value = cb_value+','+$('#other'+$(compoObj).attr("name")).val();
													}
													
													if (isEmpty(cb_value)
															|| cb_value == "undefined"
															|| cb_value == null) {
														$(".listErr"+$(compoObj).attr("name"))
														.html(
																		"Please Select Value For  "
																+ $(
																		this)
																		.children('label:first')
																        .text()
																		.replace(
																				"*",
																				""));
														// window.location.href
														// = "#errorDiv";
														isReq = false;
													}
												}

											}
										});
					});

	if (isReq) {
		val = "";
		$(tr)
				.find("td")
				.each(
						function(i, v) {
							if (listSize != i) {

								if ($(this).find(".dropDownList").hasClass(
										"dropDownList")) {
									var compoObj = $(this)
											.find(".dropDownList");
									val = compoObj.val();
										var otherValue='';
									var textname = $(this).find(
											'option:selected').text();
if($('#other'+$(compoObj).attr("name")).val()!='' && $('#other'+$(compoObj).attr("name")).val()!=undefined && $('#other'+$(compoObj).attr("name")).val()!=null){
										
										val = $('#other'+$(compoObj).attr("name")).val();
										otherValue = $('#other'+$(compoObj).attr("name")).val();
										textname = val;
									}

									finalListFormated += $(compoObj).attr(
											"name")
											+ "%%"
											+ val
											+ "%%"
											+ textname
											+ "%%"
											+ otherValue
											+ "$$";
									var idz = "#" + compoObj.attr("id");
									
									if(val!=null && val!='' && val!=undefined){
									val=textname
								}else{
									val='';
								}
									// alert(val);
									// $(this).find(".dropDownList").val("Select");
									// $(this).find(".dropDownList").select2("val",
									// "");
									hideOther($(this).find(".dropDownList"),$(compoObj).attr("name"));

								} else if ($(this).find(".multiDropDownList")
										.hasClass("multiDropDownList")) {
									var compoObj = $(this).find(
											".multiDropDownList");
									val =  $(this).find(".multiDropDownList > option:selected").map(function() {return $(this).val();}).get().join(',');
									var textname = $(this).find(".multiDropDownList > option:selected").map(function() {return $(this).text();}).get().join(',');
									
									var otherValue='';
									
									if($('#other'+$(compoObj).attr("name")).val()!='' && $('#other'+$(compoObj).attr("name")).val()!=undefined && $('#other'+$(compoObj).attr("name")).val()!=null){
									
										if(val==null || val=='' || val==undefined ){
											val = $('#other'+$(compoObj).attr("name")).val();
											textname = val;
										}else{
										val = 
											val+','+$('#other'+$(compoObj).attr("name")).val();
										textname = textname+","+$('#other'+$(compoObj).attr("name")).val();
										}
									
										otherValue = $('#other'+$(compoObj).attr("name")).val();
									}
									
									finalListFormated += $(compoObj).attr(
											"name")
											+ "%%"
											+ val
											+ "%%"
											+ textname
											+ "%%"
											+ otherValue
											+ "$$";
									var idz = "#" + compoObj.attr("id");
									if(val!=null && val!='' && val!=undefined){
									val=textname
								}else{
									val='';
								}
								// $(this).find(".multiDropDownList").val('').select2();
								hideOther($(this).find(".multiDropDownList"),$(compoObj).attr("name"));
								} else if ($(this).find(".radioList").hasClass(
										"radioList")) {
									var compoObj = $(this).find(".radioList");
									var idz = "#" + compoObj.attr("id");
									var name = $(compoObj).attr("name");
									var textname = $(
											"label[for='" + compoObj.attr("id")
													+ "']").text()
									val = getRadioValueByName(name);
									var otherValue='';
									if($('#other'+name).val()!='' && $('#other'+name).val()!=undefined && $('#other'+name).val()!=null){
										val = $('#other'+$(compoObj).attr("name")).val();
										textname = val;
										otherValue = val;
									}
									
									if (val != undefined) {
										finalListFormated += $(compoObj).attr(
												"name")
												+ "%%"
												+ val
												+ "%%"
												+ textname
												+ "%%"
												+ otherValue
												+ "$$";
										var disp_id = name + "-" + val;
										val = document.getElementById(disp_id).innerHTML;
										/*
										 * var ele = document
										 * .getElementsByName(name); for (var i =
										 * 0; i < ele.length; i++) {
										 * ele[i].checked = false; }
										 */
									}
									// $(this).find(".radioList").val("Select");
									hideOther($(this).find(".radioList"),name);
								} else if ($(this).find(".labelList").hasClass(
										"labelList")) {

									val = ($(this).find(".labelList").text());
									var compoObj = $(this).find(".labelList");
									finalListFormated += $(compoObj).attr("id")
											+ "%%" + val + "%%" + val + "$$";
									name = $(compoObj).attr("id");
									if ($(compoObj).hasClass("isRequired")
											&& val == '') {
										flag = false;
									} else {
										$(this).find(".labelList").html("");
									}

								} else if ($(this).find(".checkboxList")
										.hasClass("checkboxList")) {
									var displayName = "";
									var compoObj = $(this)
											.find(".checkboxList");
									// var idz = "." + compoObj.attr("class");
									var name = $(compoObj).attr("name");
									var textname = $('.checkboxList:checked').map(function() {return $(this).text();}).get().join(',');
									var cb_val = $('.checkboxList:checked').map(function() { return $(this).val();}).get().join(',');
									var array = new Array();
									var otherValue='';
									if($('#other'+name).val()!='' && $('#other'+name).val()!=undefined && $('#other'+name).val()!=null){
										if(cb_val==null || cb_val=='' ){
											cb_val = $('#other'+name).val();
											textname =  cb_val;
										}else{
											cb_val = 
												cb_val+','+$('#other'+name).val();
											textname = textname+','+ cb_val;
										}
										
									}
									
									if (cb_val != "null") {
										array = cb_val.split(',');
										var disp_id;
										val = "";
										for (i = 0; i < array.length; i++) {
											if (array.length == i + 1) {
												val = val + array[i];
											} else {
												val = val + array[i] + ",";
											}
										}

										finalListFormated += $(compoObj).attr(
												"name")
												+ "%%"
												+ val
												+ "%%"
												+ textname
												+ "%%"
     											+ otherValue
												+ "$$";
										val = "";
										for (i = 0; i < array.length; i++) {
											disp_id = name + "-" + array[i];
											try{
											displayName = document
													.getElementById(disp_id).innerHTML;
                                          }catch(e){
                                        	  displayName = array[i];
											}
											if (array.length == i + 1) {
												val = val + displayName;
											} else {
												val = val + displayName + ",";
											}
											
										}
										/*
										 * var ele = document
										 * .getElementsByName(name); for (var i =
										 * 0; i < ele.length; i++) {
										 * ele[i].checked = false; }
										 */
									} else {
										val = "";
									}	
									// $(this).find(".checkboxList").val("Select");
									hideOther($(this).find(".checkboxList"),name);
									

								} else {
									val = ($(this)
											.find(
													".textList,.dateList,.textAreaList")
											.val());
									var compoObj = $(this)
											.find(
													".textList,.dateList,.textAreaList");
									finalListFormated += $(compoObj).attr(
											"name")
											+ "%%" + val + "%%" + val + "$$";
									$(this)
											.find(
													".textList,.dateList,.textAreaList")
											.val("");
								}

								tRow.append($("<td>").append(val));

							} else {
								button = $("<button>").attr({
									class : "btn btn-danger",
									onclick : 'removeRow(this)',
									type : 'button'
								});
								button.html('<i class="fa fa-trash">');
								tRow.append($("<td>").append(button));
							}
						});
		var hit=false;
		$(tRow).each(function() {
		    var id = $(this).text();
		    if(id!=null && id !='' && id!=undefined){
		    	hit=true;	
		    }

		    // compare id to what you want
		});

		var hidden = $('<input/>').attr({
			type : 'hidden',
			class : 'listFormation',
			value : finalListFormated
		});
		tRow.append($("<td>").addClass("hide").append(hidden));
		if(hit){
		$(table).append(tRow);
		$(tr).find(".select2").select2("val","");
		$(tr).find(".valueDep1").children('option:not(:first)').remove();
		$(tr).find('input:radio:checked').prop('checked', false)
		if (!isEmpty($("#dynamicListArray").val())) {
			var tmp = $("#dynamicListArray").val();
			$("#dynamicListArray").val(tmp + "###" + finalListFormated);
		} else {
			$("#dynamicListArray").val(finalListFormated);
		}
		}else{
			alert("Please add atlease one field");
		}
		

	}

}

function removeRow(obj) {
	$(obj).parent().parent().empty();
}

function validatePhoto() {
	var result = true;
	if ($(".photo").hasClass("12")) {
		$(".photo")
				.each(
						function() {

							if ($('input[name=' + $(this).attr("name") + ']')[0] != undefined) {
								var file = $('input[name='
										+ $(this).attr("name") + ']')[0].files[0];
								var filename = $(this).val();
								var imgObj = $('#img' + $(this).attr("name"))
										.attr("src");

								var fileExt = filename.split('.').pop();

								if (file != undefined) {

									if (fileExt == 'jpg' || fileExt == 'jpeg'
											|| fileExt == 'png'
											|| fileExt == 'JPG'
											|| fileExt == 'JPEG'
											|| fileExt == 'PNG'
											|| fileExt == '3gp') {
										if (file.size > 1e+6) {
											$(".ferror").text(
													"Photo Size Exceeds 1MB for"
															+ file.name);
											$("#img" + $(this).attr("name"))
													.attr("src", '');
											window.location.href = "#errorDiv";

											result = false;
										}
									
									} else {
										$(".ferror").text(
												"Invalid File Extension"
														+ filename);
										window.location.href = "#errorDiv";
										$("#img" + $(this).attr("name")).attr(
												"src", '');
										result = false;
									}
								}
							} else if ($(this).hasClass("isRequired")
									&& imgObj == '') {
								$(".ferror").text(
										"Please Enter Value For  "
												+ $(this).parent().parent()
														.find('label').text());
								window.location.href = "#errorDiv";
								result = false;
							}
						});
	}

	return result;
}

function addDynamicField() {
	var dynamicDataArray = new Array();
	var result = false;
	var checkBoxName = "";
	var checkBoxMoreThanOne = new Array();
	if (checkForMandatory() && validatePhoto()) {

		$(
				".text,.date,.radio,.checkbox,.dropDown,.multiDropDown,.photo,.label,.weather,.map")
				.each(
						function(k, v) {
							var isRequired = '0';
							var compoType = '0';
							if ($(this).hasClass("isRequired")) {
								isRequired = '1';
							}

							if ($(this).hasClass("text")) {
								compoType = '1';
								
								if($(this).is("textarea")){
									compoType = '5';
								}
								
							} else if ($(this).hasClass("dropDown")) {
								compoType = '4';
							} else if ($(this).hasClass("multiDropDown")) {
								compoType = '9';
							} else if ($(this).hasClass("date")) {
								compoType = '3';
							} else if ($(this).hasClass("label")) {
								compoType = '7';
							} else if ($(this).hasClass("12")) {
								compoType = '12';
							} else if ($(this).hasClass("13")) {
								compoType = '13';
							} else if ($(this).hasClass("11")) {
								compoType = '11';
							} else if ($(this).hasClass("weather")) {
								compoType = '14';
							}else if ($(this).hasClass("map")) {
								compoType = '15';
							}

							if ($(this).hasClass("radio")) {
								compoType = '2';
								var name = $(this).attr("name");
								var val= getRadioValueByName(name);
								var otherValue='';
								if($('#other'+name).val()!='' && $('#other'+name).val()!=null && $('#other'+name).val()!=undefined){
									val = $('#other'+name).val();
									otherValue = $('#other'+name).val();
								}
								
								if ($(this).is(":checked")) {
									dynamicDataArray.push({
										name : name,
										value : val,
										id : "",
										isRequired : isRequired,
										otherValue:otherValue,
										order : k,
										txnTypez : getTxnType(),
										compoType : compoType
									});
								}
							} else if ($(this).hasClass("checkbox")) {
								compoType = '6';
								var name = $(this).attr("name");
								var val= getCheckBoxValueByName(name);
								var otherValue='';

								if($('#other'+name).val()!='' && $('#other'+name).val()!=null && $('#other'+name).val()!=undefined){
									val = $('#other'+name).val();
									otherValue = $('#other'+name).val();
								}
								if (checkBoxName != name) {
									dynamicDataArray.push({
										name : name,
										value : val,
										id : "",
										isRequired : isRequired,
										otherValue:otherValue,
										order : k,
										txnTypez : getTxnType(),
										compoType : compoType
									});
								}
								checkBoxName = name;
								for (var i = 0; i < dynamicDataArray.length; i++) {
									var str = dynamicDataArray[i].value
											.toString();
									dynamicDataArray[i].value = str;
								}
							} else if ($(this).hasClass("label")) {
								compoType = '7';
								dynamicDataArray.push({
									name : $(this).attr("id"),
									value : $(this).text(),
									id : "",
									isRequired : isRequired,
									order : k,
									txnTypez : getTxnType(),
									compoType : compoType
								});
							} else if ($(this).hasClass("weather")) {
								compoType = '14';
								dynamicDataArray.push({
									name : $(this).attr("id"),
									value : $(
											'#weatherVal' + $(this).attr("id"))
											.val(),
									id : "",
									isRequired : isRequired,
									order : k,
									txnTypez : getTxnType(),
									compoType : compoType
								});
							} else if ($(this).hasClass("photo")) {
								// compoType = '2';
								var name = $(this).attr("name");

								valueJs = $("#img" + $(this).attr("name"))
										.attr("src");
								var fileExt = $("#img" + $(this).attr("name"))
										.attr("class");

								if (valueJs != undefined && valueJs != '') {
									dynamicDataArray.push({
										name : name,
										value : "",
										id : "",
										isRequired : isRequired,
										order : k,
										txnTypez : getTxnType(),
										compoType : compoType,
										imageFile : valueJs,
										fileExt : fileExt

									});

								}
							} else if ($(this).hasClass("multiDropDown")) {
								if (!isEmpty($(this).attr("name"))
										&& (!isEmpty($(this).val()) || ($('#other'+$(this).attr("name")).val()!='' && $('#other'+$(this).attr("name")).val()!=undefined && $('#other'+$(this).attr("name")).val()!=null))) {
									var val  = $(this).val();
								
									var otherValue='';
									if($('#other'+$(this).attr("name")).val()!='' && $('#other'+$(this).attr("name")).val()!=undefined && $('#other'+$(this).attr("name")).val()!=null){
										if(val==null || val=='' ){
											val = $('#other'+$(this).attr("name")).val();
										}else{
										val = 
											val+','+$('#other'+$(this).attr("name")).val();
										}
										otherValue = $('#other'+name).val();
									}
									
									dynamicDataArray.push({
										name : $(this).attr("name"),
										value :val,
										id : "",
										isRequired : isRequired,
										order : k,
										txnTypez : getTxnType(),
										otherValue:otherValue,
										compoType : compoType
									});
								}
								for (var i = 0; i < dynamicDataArray.length; i++) {
									var str = dynamicDataArray[i].value
											.toString();
									dynamicDataArray[i].value = str;
								}
							} else if (!isEmpty($(this).val()) || ($('#other'+$(this).attr("name")).val()!='' && $('#other'+$(this).attr("name")).val()!=undefined && $('#other'+$(this).attr("name")).val()!=null)) {
								var val  = $(this).val();
								var otherValue='';
								if($('#other'+$(this).attr("name")).val()!='' && $('#other'+$(this).attr("name")).val()!=undefined && $('#other'+$(this).attr("name")).val()!=null){
								
									if(val==null || val==''){
										val = $('#other'+$(this).attr("name")).val();
									}else{
									val = val+','+$('#other'+$(this).attr("name")).val();
									}

									otherValue = $('#other'+name).val();
								}
								
								if (!isEmpty($(this).attr("name"))) {
									dynamicDataArray.push({
										name : $(this).attr("name"),
										value : val,
										id : "",
										isRequired : isRequired,
										otherValue:otherValue,
										order : k,
										txnTypez : getTxnType(),
										compoType : compoType
									});
								}

							} else if ($(this).hasClass("map")) {
								compoType = '15';
								var id = $(this).attr("id");
								dynamicDataArray.push({
									name : id,
									value : $('#plotValue'+id).val(),
									id : "",
									isRequired : isRequired,
									order : k,
									txnTypez : getTxnType(),
									compoType : compoType
								});
							}
							
							

						});

		$(".listFormation").each(function(k, v) {
			var firsTd = $(this).closest('table').parent();

			if ($(firsTd).hasClass('hide')) {

				$(this).val('');
			} else {
				var isRequired = 0;
				if ($(this).hasClass("isRequired")) {
					isRequired = 1;
				}
				var val = $(this).val();
				var isRequired = 0;
				if (!isEmpty(val)) {
					var splitNameValArr = val.split("$$");
					$(splitNameValArr).each(function(ki, vi) {
						var nameValuePair = (vi).split("%%");
						if(nameValuePair[0]!='' && nameValuePair[0]!=undefined && nameValuePair[0]!=null){
						dynamicDataArray.push({
							name : nameValuePair[0],
							value : nameValuePair[1],
							id : "",
							typez : (k + 1),
							isRequired : isRequired,
							otherValue : nameValuePair[2],
							order : ki,
							txnTypez : getTxnType()
						});
					}
					})
				}
			}
		});

		var json = JSON.stringify(dynamicDataArray);

		if (dynamicDataArray.length <= 0) {
			json = '';
		}
		$("#dynamicFieldsArray").val(json);
	
		$("#txnTypez").val(getTxnType());
		result = true;
	}

	return result;

}

function checkForMandatory() {
	$(".ferror").text("");
	var isReq = true;
	var listContainer_count = 0;
	var listComponentsNameArray = [];
	$(".isRequired")
			.each(
					function() {
						if(!$(this).parent().parent().hasClass("hide")){
                   if ($(this).hasClass("radio")) {
							var name = $(this).attr("name");
							if (isEmpty(getRadioValueByName(name))) {
								$(".ferror").text(
										"Please Select Value For  "
												+ $(this).parent().parent().find('label').text().split("*")[0]);
								window.location.href = "#errorDiv";
								isReq = false;
							}
						} else if ($(this).hasClass("checkbox")) {
							var name = $(this).attr("name");
							if (isEmpty(getCheckBoxValueByName(name))) {
								$(".ferror").text(
										"Please Select Value For  "
												+ $(this).parent().parent()
														.find('label').text()
														.replace("*", ""));
								window.location.href = "#errorDiv";
								isReq = false;
							}
						} else if ($(this).hasClass("text")) {
							var name = $(this).attr("id");
							if (isEmpty(getElementValueById(name))) {
								$(".ferror").text(
										"Please Select Value For  "
												+ $(this).parent().parent()
														.find('label').text()
														.replace("*", ""));
								window.location.href = "#errorDiv";
								isReq = false;
							}
						} else if ($(this).hasClass("date")) {
							var name = $(this).attr("id");
							if (isEmpty(getElementValueById(name))) {
								$(".ferror").text(
										"Please Select Value For  "
												+ $(this).parent().parent()
														.find('label').text()
														.replace("*", ""));
								window.location.href = "#errorDiv";
								isReq = false;
							}
						} else if ($(this).hasClass("dropDown")) {
							var name = $(this).attr("id");
						
							if (isEmpty(getElementValueById(name))) {
								$(".ferror").text(
										"Please Select Value For  "
												+ $(this).parent().parent()
														.find('label').text()
														.replace("*", ""));
								window.location.href = "#errorDiv";
								isReq = false;
							}
						} else if ($(this).hasClass("multiDropDown")) {
							var name = $(this).attr("id");
							if (isEmpty(getElementValueById(name))) {
								$(".ferror").text(
										"Please Select Value For  "
												+ $(this).parent().parent()
														.find('label').text()
														.replace("*", ""));
								window.location.href = "#errorDiv";
								isReq = false;
							}
						}   else if ($(this).is( "table" )) {
							if($(this).find(".listFormation").val() == undefined || $(this).find(".listFormation").val() ==''){
								var comname = $(this).find('tbody').attr("class");
								$(".ferror").text(
										"Please Select Value For  "
												+comname);
								window.location.href = "#errorDiv";
								isReq = false;
							}

						} else if ($(this).hasClass("map")) {
							var name = $(this).attr("id");
							if ($('#plotValue'+name).val()=='') {
								$(".ferror").text(
										"Please Select Value For  "
												+ $(this).parent().parent()
														.find('label').text()
														.replace("*", ""));
								window.location.href = "#errorDiv";
								isReq = false;
							}
						} 
					/*
					 * else if ($(this).hasClass("photo")) { var name =
					 * $(this).attr("id"); if ($('#img'+name).val('src')=='') {
					 * $(".ferror").html( "Please Select Value For " +
					 * $(this).parent().parent() .find('label').text()
					 * .replace("*", "")); window.location.href = "#errorDiv";
					 * isReq = false; } }
					 */
						 else if ($(this).hasClass("photo")) {
								var name = $(this).attr("name");
								if ($('#img' + name).attr('src') == undefined
										|| $('#img' + name).attr('src') == '') {
									$(".ferror").html(
											$(".ferror").html()
													+ "<br>"
													+ "Please Select Value For  "
													+ $(this).parent().parent().find(
															'label').text().replace(
															"*", ""));
									window.location.href = "#errorDiv";
									isReq = false;
								}
							}
                   
					}
					});
	return isReq;
}


function validateListComponentsBeforeSave(listElementsArray) {
	var isReq = true;
	for (i = 0; i < listElementsArray.length; i++) {
		var val = "";
		$(".listFormation").each(function(k, v) {
			val = val + $(this).val();
		});

		if (!val.includes(listElementsArray[i])) {
			$(".ferror").text(
					"Please Select Value For  "
							+ compName);
			window.location.href = "#errorDiv";
			isReq = false;
			return isReq;
		}
	}
	return isReq;

}

/** Detail Page Changes */
function renderDynamicDetailFeilds() {

	var json = "";
	var jsonData = "";
	var dataa = {
		txnTypez : getTxnType(),
		branch:getBranchIdDyn()
	}

	$.ajax({
		type : "POST",
		async : false,
		data : dataa,
		url : "farmer_populateDynamicFields.action",
		success : function(result) {
			json = result;
		}
	});

	$(json)
			.each(
					function(k, v) {
						$(v.sections)
								.each(
										function(key, val) {
											var aPanel = $("<div/>").addClass(
													"aPanel");
											var aTitle = $("<div/>").addClass(
													"aTitle").html(
													"<h2>" + val.secName
															+ "</h2>");
											var aContent = $("<div/>")
													.addClass(
															"aContent dynamic-form-con "
																	+ val.secCode);
											aPanel.append(aTitle);
											aPanel.append(aContent);

											var formContainerWrapper = $(
													"<div/>").addClass(
													"formContainerWrapper");
											formContainerWrapper.append(aPanel);
										
											if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){
												$(formContainerWrapper).insertAfter('.'+val.afterInsert );
											}else{
											$(".dynamicFieldsRender").append(formContainerWrapper);
											}
										/*
										 * $(".dynamicFieldsRender").append(
										 * formContainerWrapper);
										 */
										});

						$(v.fields)
								.each(
										function(key, val) {

											if (val.compoType != 8) {
												var dFlex = $("<div/>")
														.addClass(
																"dynamic-flexItem2");
												var p1 = $("<p/>").addClass(
														'flexItem').text(
														val.compoName);
												var p2 = $(
														"<p style='word-wrap: break-word' />")
														.addClass(
																'flexItem '
																		+ val.compoCode).attr("name",val.compoCode);
												dFlex.append(p1);
												dFlex.append(p2);
												$("." + val.sectionId).append(
														dFlex);

												if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){
													var insertEle = $(
															'[name="'
																	+ val.afterInsert
																	+ '"]')
															.parent();
													$(dFlex).insertAfter(
															insertEle);
												}

												if (val.parentDepenCode != ''
														&& val.parentDepenKey != '') {
													if (val.parentDepenKey
															.indexOf(",") >= 0
															&& val.parentDepenCode
																	.indexOf(",") < 0) {
														var values = val.parentDepenKey
																.split(",");
														$
																.each(
																		values,
																		function(
																				i,
																				e) {
																			var classN = val.parentDepenCode
																					+ "_"
																					+ e
																							.trim();
																			dFlex
																					.addClass("hide"
																							+ " par"
																							+ val.parentDepenCode);
																			dFlex
																					.addClass(classN);
																		});
													} else if (val.parentDepenKey
															.indexOf(",") >= 0
															&& val.parentDepenCode
																	.indexOf(",") >= 0) {
														var values = val.parentDepenKey
																.split(",");
														$
																.each(
																		values,
																		function(
																				i,
																				e) {
																			var depCode = val.parentDepenCode
																					.split(",");
																			$
																					.each(
																							depCode,
																							function(
																									j,
																									f) {
																								var classN = f
																										.trim()
																										+ "_"
																										+ e
																												.trim();
																								dFlex
																										.addClass("hide"
																												+ " par"
																												+ f
																														.trim());
																								dFlex
																										.addClass(classN);
																							});
																		});
													} else if (val.parentDepenKey
															.indexOf(",") < 0
															&& val.parentDepenCode
																	.indexOf(",") >= 0) {
														var values = val.parentDepenCode
																.split(",");
														$
																.each(
																		values,
																		function(
																				i,
																				e) {
																			var classN = e
																					.trim()
																					+ "_"
																					+ val.parentDepenKey;
																			dFlex
																					.addClass("hide"
																							+ " par"
																							+ e
																									.trim());
																			dFlex
																					.addClass(classN);
																		});
													} else {
														var classN = val.parentDepenCode
																+ "_"
																+ val.parentDepenKey;
														dFlex
																.addClass("hide"
																		+ " par"
																		+ val.parentDepenCode);
														dFlex.addClass(classN);
													}
												}

											} else {

												var table = $("<table>")
														.addClass(
																"table table-bordered");
												var tr = $("<tr/>").attr({
													id : "tDynmaicRow" + val.id
												});
												table.append(tr);

												if (val.parentDepenCode != ''
														&& val.parentDepenKey != '') {
													if (val.parentDepenKey
															.indexOf(",") >= 0
															&& val.parentDepenCode
																	.indexOf(",") < 0) {
														var values = val.parentDepenKey
																.split(",");
														$
																.each(
																		values,
																		function(
																				i,
																				e) {
																			var classN = val.parentDepenCode
																					+ "_"
																					+ e
																							.trim();
																			table
																					.addClass("hide"
																							+ " par"
																							+ val.parentDepenCode);
																			table
																					.addClass(classN);
																		});
													} else if (val.parentDepenKey
															.indexOf(",") >= 0
															&& val.parentDepenCode
																	.indexOf(",") >= 0) {
														var values = val.parentDepenKey
																.split(",");
														$
																.each(
																		values,
																		function(
																				i,
																				e) {
																			var depCode = val.parentDepenCode
																					.split(",");
																			$
																					.each(
																							depCode,
																							function(
																									j,
																									f) {
																								var classN = f
																										.trim()
																										+ "_"
																										+ e
																												.trim();
																								table
																										.addClass("hide"
																												+ " par"
																												+ f
																														.trim());
																								table
																										.addClass(classN);
																							});
																		});
													} else if (val.parentDepenKey
															.indexOf(",") < 0
															&& val.parentDepenCode
																	.indexOf(",") >= 0) {
														var values = val.parentDepenCode
																.split(",");
														$
																.each(
																		values,
																		function(
																				i,
																				e) {
																			var classN = e
																					.trim()
																					+ "_"
																					+ val.parentDepenKey;
																			table
																					.addClass("hide"
																							+ " par"
																							+ e
																									.trim());
																			table
																					.addClass(classN);
																		});
													} else {
														var classN = val.parentDepenCode
																+ "_"
																+ val.parentDepenKey;
														table
																.addClass("hide"
																		+ " par"
																		+ val.parentDepenCode);
														table.addClass(classN);
													}
												}
												$("." + val.sectionId).append(
														table);
											}
										});
						v.groups.sort(GetSortOrder("typez"));
						$(v.groups).each(
								function(key, val) {
									// alert(val.refId);
									if (val.compoType != 10) {
										$("#tDynmaicRow" + val.refId).append(
												$("<td/>").addClass(
														val.compoCode).html(
														val.compoName));
									}
								});
						$(v.sections)
								.each(
										function(key, val) {

											if ($('.' + val.secCode).children().length == 0) {

												$('.' + val.secCode)
														.closest(
																'.formContainerWrapper')
														.remove();
											}

										});
						setDynamicFieldDetailValuesFarmer();

					});
}
/** Detail Page Changes */
function renderDynamicDetailFeildsByTxnTypeByOther(classname,txntype, entity,id) {
	
//var txntype1=txntype;
	var json = "";
	var table;
	var aPanel;
	var secCode = [];
	var dataa = {
		selectedObject : id,
		txnTypez :txntype,
		queryType : "1",
		branch:getBranchIdDyn()

	}
	//alert(JSON.stringify(dataa));
	$.ajax({
		type : "POST",
		async : false,
		url : "farmer_populateDynamicFields.action",
		data : dataa,
		success : function(result) {			
			json = result;
		}
	});

	$(json)
			.each(
					function(k, v) {

						$(v.sections)
								.each(
										function(key, val) {
											// alert(val.secName);
											secCode.push(val.secCode);
											var sectionSerialNum = secCode
													.indexOf(val.secCode);
											sectionSerialNum = sectionSerialNum + 1;
											var aPanel = $("<div/>").addClass(
													"aPanel");
											var aTitle = $("<div/>").addClass(
													"aTitle titleHd").html(
													"<h2>" + sectionSerialNum
															+ "." + val.secName
															+ "</h2>");
											var aContent = $("<div/>")
													.addClass(
															"aContent dynamic-form-con "
																	+ val.secCode);
											aPanel.append(aTitle);

											table = $("<table>")
													.addClass(
															"table table-bordered dynTable tab" + val.secCode);
											var thead0 = $(
													"<th > S.no </th>").addClass("dynHeadSN");
													
											var thead1 = $(
													"<th > Question </th>")
													.addClass("dynHead");
											var thead2 = $(
													"<th width='20%'> Answer </th>")
													.addClass("dynHeadAns");
											if(val.isScore!='2' && val.typez!='2'){
											var thead4 = $(
													"<th > Image / Video /Plotting </th>")
													.addClass("imgHead hide");
											}
											table.append(thead0);
											table.append(thead1);
											table.append(thead2);
											table.append(thead4);
											if(val.isScore=='1'){
												var thead5 = $(
												"<th > Score </th>")
												.addClass("dynHeadAns");
												table.append(thead5);
												
												var thead6 = $(
												"<th > Percentage </th>")
												.addClass("dynHeadAns");
												table.append(thead6);
											}
											if(val.isScore=='2'  && val.typez=='2'){
												var thead5 = $(
												"<th > Score </th>")
												.addClass("dynHeadAns");
												table.append(thead5);
												
												var thead6 = $(
												"<th > Grade </th>")
												.addClass("dynHeadAns");
												table.append(thead6);
												
												var thead7 = $(
												"<th > Action Plan </th>")
												.addClass("dynHeadAns");
												table.append(thead7);
												
												var thead8= $(
												"<th > DeadLine </th>")
												.addClass("dynHeadAns");
												table.append(thead8);
												
													
												var thead11= $(
												"<th > Follow Up Details </th>")
												.addClass("dynHeadAns");
												table.append(thead11);
											}
											
											aPanel.append(table);

											aPanel.append(aContent);

											var formContainerWrapper = $(
													"<div/>").addClass(
													"formContainerWrapper");
											formContainerWrapper.append(aPanel);
											$("."+classname).append(
													formContainerWrapper);

										});
						var trow1 ;
						$(v.fields)
								.each(
										function(key, val) {

											sectionSerialNo = Number(secCode
													.indexOf(val.sectionId));
											sectionSerialNo = sectionSerialNo + 1;
											var fieldSerialNo = 1;

											$(
													'.' + val.sectionId
															+ ' > .dynTable td')
													.each(
															function() {
																if (this.className == 'snoClass') {
																	var cellText = $(
																			this)
																			.html();
																	var index = cellText
																			.indexOf('.');
																	var previousValue = cellText
																			.substr(
																					index + 1,
																					cellText.length);
																	fieldSerialNo = Number(previousValue) + 1;
																}
															});
//alert(val.compoType);
											if (val.compoType != 8) {

												if (val.compoType != 12 && val.compoType != 15) {
													/*
													 * table = $("<table>")
													 * .addClass( "table
													 * table-bordered
													 * dynTable");
													 */
												/* alert(val.secCode); */
												table = $(".tab"+val.secCode);

													 trow1 = $("<tr>");
													var tdata0 = $(
															"<td>"
																	+ sectionSerialNo
																	+ "."
																	+ fieldSerialNo
																	+ "</td>")
															.addClass(
																	"snoClass");
													var tdata1 = $(
															"<td>"
																	+ val.compoName
																	+ "</td>")
															.addClass("quest");
													var tdata2 = $("<td></td>")
															.addClass(
																	" questAns ans"
																			+ " "
																			+ val.compoCode);
												
													
													trow1.append(tdata0);
													trow1.append(tdata1);
													trow1.append(tdata2);
													if(val.isScore!='2' && val.typez!='2'){
														var tdata3 = $("<td></td>")
																.addClass("imgTd hide");
														
														trow1.append(tdata3);
														}
													
													if(val.isScore=='1'){
														var tdata4 = $("<td></td>")
														.addClass("questAns score"+ val.compoCode);
														trow1.append(tdata4);
														
														var tdata5 = $("<td></td>")
														.addClass("questAns percentage"+ val.compoCode);
														trow1.append(tdata5);
														
														}
													if(val.isScore=='2' && val.typez=='2'){
														var tdata4 = $("<td></td>")
														.addClass("questAns score"+ val.compoCode);
														trow1.append(tdata4);
														
														var tdata5 = $("<td></td>")
														.addClass("questAns grade"+ val.compoCode);
														trow1.append(tdata5);
														
														var tdata6 = $("<td></td>")
														.addClass("questAns acPlanDet"+ val.compoCode);
														trow1.append(tdata6);
														
														
														var tdata7 = $("<td></td>")
														.addClass("questAns deadline"+ val.compoCode);
														trow1.append(tdata7);
														
														
														var tdata11= $("<td></td>")
														.addClass("questAns followDet"+ val.compoCode);
														trow1.append(tdata11);
													
														}
													
													table.append(trow1);
													$("." + val.sectionId)
															.append(table);

												} else {

													/*
													 * table = $("<table>")
													 * .addClass( "table
													 * table-bordered
													 * dynTable");
													 */
													
													table = $(".tab"+val.secCode);
													 trow1 = $("<tr>");
													var tdata0 = $(
															"<td>"
																	+ sectionSerialNo
																	+ "."
																	+ fieldSerialNo
																	+ "</td>")
															.addClass(
																	"snoClass");
													var tdata1 = $(
															"<td>"
																	+ val.compoName
																	+ "</td>")
															.addClass("quest");

													/*var tdata2 = $("<td >")
															.addClass(
																	"quest ans"
																			+ " "
																			+ val.compoCode);*/
													var classZ = "imgTd"+ val.compoCode;
													if(val.compoType == 15){	
														classZ = "plot"+ val.compoCode;
													}
													
													var tdata2 = $(
															"<td class='imgTd' width='800px' ></td>")
															.addClass(classZ);
													trow1.append(tdata0);
													trow1.append(tdata1);
													trow1.append(tdata2);
													//trow1.append(tdata3);
													table.append(trow1);
													$("." + val.sectionId)
															.append(table);

												}

												if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){
													var insertEle = $(
															'[name="'
																	+ val.afterInsert
																	+ '"]')
															.parent().parent();
													$(dFlex).insertAfter(
															insertEle);
												}

											} else {
												var parentNames = $(
														"." + val.sectionId)
														.parent().parent()
														.find('td').length;
													if (parentNames <= 1) {
													$("." + val.sectionId)
															.parent().parent()
															.find('th').hide();
												} else {
													$("." + val.sectionId)
															.parent().parent()
															.find('th').show();
												}

												var table = $("<table>")
														.addClass(
																"table table-bordered");
												var tr = $("<tr/>").attr({
													id : "tDynmaicRow" + val.id
												});
												table.append(tr);
												$("." + val.sectionId).append(
														table);

											}

											if (val.parentDepenCode != ''
													&& val.parentDepenKey != '' && val.isMobile!='6') {
												if (val.parentDepenKey
														.indexOf(",") >= 0
														&& val.parentDepenCode
																.indexOf(",") < 0) {
													var values = val.parentDepenKey
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = val.parentDepenCode
																				+ "_"
																				+ e
																						.trim();
																		trow1
																				.addClass("hide"
																						+ " par"
																						+ val.parentDepenCode);
																		trow1
																				.addClass(classN);
																	});
												} else if (val.parentDepenKey
														.indexOf(",") >= 0
														&& val.parentDepenCode
																.indexOf(",") >= 0) {
													var values = val.parentDepenKey
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var depCode = val.parentDepenCode
																				.split(",");
																		$
																				.each(
																						depCode,
																						function(
																								j,
																								f) {
																							var classN = f
																									.trim()
																									+ "_"
																									+ e
																											.trim();
																							trow1
																									.addClass("hide"
																											+ " par"
																											+ f
																													.trim());
																							trow1
																									.addClass(classN);
																						});
																	});
												} else if (val.parentDepenKey
														.indexOf(",") < 0
														&& val.parentDepenCode
																.indexOf(",") >= 0) {
													var values = val.parentDepenCode
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = e
																				.trim()
																				+ "_"
																				+ val.parentDepenKey;
																		trow1
																				.addClass("hide"
																						+ " par"
																						+ e
																								.trim());
																		trow1
																				.addClass(classN);
																	});
												} else {
													var classN = val.parentDepenCode
															+ "_"
															+ val.parentDepenKey;
													trow1
															.addClass("hide"
																	+ " par"
																	+ val.parentDepenCode);
													trow1.addClass(classN);
												}
											}

										});
						v.groups.sort(GetSortOrder("typez"));

						$(v.images)
									.each(
											function(key, val) {
												if(val.isImage==1){
													 jQuery(".imgHead").removeClass("hide");
													 jQuery(".imgTd").removeClass("hide");
											
												}
											});
						$(v.groups).each(
								function(key, val) {

									if (val.compoType != 10) {

										$("#tDynmaicRow" + val.refId).append(
												$("<td/>").addClass(
														val.compoCode).html(
														val.compoName));
									}
								});

						setDynamicFieldDetailValuesByTxnTypeOth(txntype,id);

					});

}




/** Detail Page Changes */
function renderDynamicDetailFeildsByTxnTypeWil() {
	var json = "";
	var table;
	var aPanel;
	var secCode = [];
	var dataa = {
		selectedObject : getId(),
		txnTypez : getTxnType1(),
		queryType : "1",
		branch:getBranchIdDyn()

	}
	$.ajax({
		type : "POST",
		async : false,
		url : "farmer_populateDynamicFields.action",
		data : dataa,
		success : function(result) {			
			json = result;
		}
	});
//alert(json);
	$(json)
			.each(
					function(k, v) {

						$(v.sections)
								.each(
										function(key, val) {
											// alert(val.secName);
											secCode.push(val.secCode);
											var sectionSerialNum = secCode
													.indexOf(val.secCode);
											sectionSerialNum = sectionSerialNum + 1;
											var aPanel = $("<div/>").addClass(
													"aPanel");
											var aTitle = $("<div/>").addClass(
													"aTitle titleHd").html(
													"<h2>" + sectionSerialNum
															+ "." + val.secName
															+ "</h2>");
											var aContent = $("<div/>")
													.addClass(
															"aContent dynamic-form-con "
																	+ val.secCode);
											aPanel.append(aTitle);

											table = $("<table>")
													.addClass(
															"table table-bordered dynTable tab" + val.secCode);
											var thead0 = $(
													"<th > S.no </th>").addClass("dynHeadSN");
													
											var thead1 = $(
													"<th > Question </th>")
													.addClass("dynHead");
											var thead2 = $(
													"<th width='20%'> Answer </th>")
													.addClass("dynHeadAns");
											if(val.isScore!='2' && val.typez!='2'){
											var thead4 = $(
													"<th > Image / Video /Plotting </th>")
													.addClass("imgHead hide");
											}
											table.append(thead0);
											table.append(thead1);
											table.append(thead2);
											table.append(thead4);
											if(val.isScore=='1'){
												var thead5 = $(
												"<th > Score </th>")
												.addClass("dynHeadAns");
												table.append(thead5);
												
												var thead6 = $(
												"<th > Percentage </th>")
												.addClass("dynHeadAns");
												table.append(thead6);
											}
											if(val.isScore=='2'  && val.typez=='2'){
												var thead5 = $(
												"<th > Score </th>")
												.addClass("dynHeadAns");
												table.append(thead5);
												
												var thead6 = $(
												"<th > Grade </th>")
												.addClass("dynHeadAns");
												table.append(thead6);
												
												var thead7 = $(
												"<th > Action Plan </th>")
												.addClass("dynHeadAns");
												table.append(thead7);
												
												var thead8= $(
												"<th > DeadLine </th>")
												.addClass("dynHeadAns");
												table.append(thead8);
												
													
												var thead11= $(
												"<th > Follow Up Details </th>")
												.addClass("dynHeadAns");
												table.append(thead11);
											}
											
											aPanel.append(table);

											aPanel.append(aContent);

											var formContainerWrapper = $(
													"<div/>").addClass(
													"formContainerWrapper");
											formContainerWrapper.append(aPanel);
											$(".dynamicFieldsRender").append(
													formContainerWrapper);

										});
						var trow1 ;
						$(v.fields)
								.each(
										function(key, val) {

											sectionSerialNo = Number(secCode
													.indexOf(val.sectionId));
											sectionSerialNo = sectionSerialNo + 1;
											var fieldSerialNo = 1;

											$(
													'.' + val.sectionId
															+ ' > .dynTable td')
													.each(
															function() {
																if (this.className == 'snoClass') {
																	var cellText = $(
																			this)
																			.html();
																	var index = cellText
																			.indexOf('.');
																	var previousValue = cellText
																			.substr(
																					index + 1,
																					cellText.length);
																	fieldSerialNo = Number(previousValue) + 1;
																}
															});

											if (val.compoType != 8) {

												if (val.compoType != 12 && val.compoType != 15) {
													/*
													 * table = $("<table>")
													 * .addClass( "table
													 * table-bordered
													 * dynTable");
													 */
												/* alert(val.secCode); */
												table = $(".tab"+val.secCode);

													 trow1 = $("<tr>");
													var tdata0 = $(
															"<td>"
																	+ sectionSerialNo
																	+ "."
																	+ fieldSerialNo
																	+ "</td>")
															.addClass(
																	"snoClass");
													var tdata1 = $(
															"<td>"
																	+ val.compoName
																	+ "</td>")
															.addClass("quest");
													var tdata2 = $("<td></td>")
															.addClass(
																	" questAns ans"
																			+ " "
																			+ val.compoCode);
												
													
													trow1.append(tdata0);
													trow1.append(tdata1);
													trow1.append(tdata2);
													if(val.isScore!='2' && val.typez!='2'){
														var tdata3 = $("<td></td>")
																.addClass("imgTd hide");
														
														trow1.append(tdata3);
														}
													
													if(val.isScore=='1'){
														var tdata4 = $("<td></td>")
														.addClass("questAns score"+ val.compoCode);
														trow1.append(tdata4);
														
														var tdata5 = $("<td></td>")
														.addClass("questAns percentage"+ val.compoCode);
														trow1.append(tdata5);
														
														}
													if(val.isScore=='2' && val.typez=='2'){
														var tdata4 = $("<td></td>")
														.addClass("questAns score"+ val.compoCode);
														trow1.append(tdata4);
														
														var tdata5 = $("<td></td>")
														.addClass("questAns grade"+ val.compoCode);
														trow1.append(tdata5);
														
														var tdata6 = $("<td></td>")
														.addClass("questAns acPlanDet"+ val.compoCode);
														trow1.append(tdata6);
														
														
														var tdata7 = $("<td></td>")
														.addClass("questAns deadline"+ val.compoCode);
														trow1.append(tdata7);
														
														
														var tdata11= $("<td></td>")
														.addClass("questAns followDet"+ val.compoCode);
														trow1.append(tdata11);
													
														}
													
													table.append(trow1);
													$("." + val.sectionId)
															.append(table);

												} else {

													/*
													 * table = $("<table>")
													 * .addClass( "table
													 * table-bordered
													 * dynTable");
													 */
													
													table = $(".tab"+val.secCode);
													 trow1 = $("<tr>");
													var tdata0 = $(
															"<td>"
																	+ sectionSerialNo
																	+ "."
																	+ fieldSerialNo
																	+ "</td>")
															.addClass(
																	"snoClass");
													var tdata1 = $(
															"<td>"
																	+ val.compoName
																	+ "</td>")
															.addClass("quest");

													var tdata2 = $("<td >")
															.addClass(
																	"quest ans"
																			+ " "
																			+ val.compoCode);
													var classZ = "imgTd"+ val.compoCode;
													if(val.compoType == 15){	
														classZ = "plot"+ val.compoCode;
													}
													
													var tdata3 = $(
															"<td class='imgTd' ></td>")
															.addClass(classZ);
													trow1.append(tdata0);
													trow1.append(tdata1);
													trow1.append(tdata2);
													trow1.append(tdata3);
													table.append(trow1);
													$("." + val.sectionId)
															.append(table);

												}

												if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){
													var insertEle = $(
															'[name="'
																	+ val.afterInsert
																	+ '"]')
															.parent().parent();
													$(dFlex).insertAfter(
															insertEle);
												}

											} else {
												var parentNames = $(
														"." + val.sectionId)
														.parent().parent()
														.find('td').length;
													if (parentNames <= 1) {
													$("." + val.sectionId)
															.parent().parent()
															.find('th').hide();
												} else {
													$("." + val.sectionId)
															.parent().parent()
															.find('th').show();
												}

												var table = $("<table>")
														.addClass(
																"table table-bordered");
												var tr = $("<tr/>").attr({
													id : "tDynmaicRow" + val.id
												});
												table.append(tr);
												$("." + val.sectionId).append(
														table);

											}

											if (val.parentDepenCode != ''
													&& val.parentDepenKey != '' && val.isMobile!='6') {
												if (val.parentDepenKey
														.indexOf(",") >= 0
														&& val.parentDepenCode
																.indexOf(",") < 0) {
													var values = val.parentDepenKey
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = val.parentDepenCode
																				+ "_"
																				+ e
																						.trim();
																		trow1
																				.addClass("hide"
																						+ " par"
																						+ val.parentDepenCode);
																		trow1
																				.addClass(classN);
																	});
												} else if (val.parentDepenKey
														.indexOf(",") >= 0
														&& val.parentDepenCode
																.indexOf(",") >= 0) {
													var values = val.parentDepenKey
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var depCode = val.parentDepenCode
																				.split(",");
																		$
																				.each(
																						depCode,
																						function(
																								j,
																								f) {
																							var classN = f
																									.trim()
																									+ "_"
																									+ e
																											.trim();
																							trow1
																									.addClass("hide"
																											+ " par"
																											+ f
																													.trim());
																							trow1
																									.addClass(classN);
																						});
																	});
												} else if (val.parentDepenKey
														.indexOf(",") < 0
														&& val.parentDepenCode
																.indexOf(",") >= 0) {
													var values = val.parentDepenCode
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = e
																				.trim()
																				+ "_"
																				+ val.parentDepenKey;
																		trow1
																				.addClass("hide"
																						+ " par"
																						+ e
																								.trim());
																		trow1
																				.addClass(classN);
																	});
												} else {
													var classN = val.parentDepenCode
															+ "_"
															+ val.parentDepenKey;
													trow1
															.addClass("hide"
																	+ " par"
																	+ val.parentDepenCode);
													trow1.addClass(classN);
												}
											}

										});
						v.groups.sort(GetSortOrder("typez"));

						$(v.images)
									.each(
											function(key, val) {
												if(val.isImage==1){
													 jQuery(".imgHead").removeClass("hide");
													 jQuery(".imgTd").removeClass("hide");
											
												}
											});
						$(v.groups).each(
								function(key, val) {

									if (val.compoType != 10) {

										$("#tDynmaicRow" + val.refId).append(
												$("<td/>").addClass(
														val.compoCode).html(
														val.compoName));
									}
								});

						setDynamicFieldDetailValues();

					});

}


function renderDynamicDetailFeildsByTxnType() {

	var json = "";
	var table;
	var aPanel;
	var secCode = [];
	var dataa = {
		selectedObject : getId(),
		txnTypez : getTxnType(),
		queryType : "1",
		branch:getBranchIdDyn()

	}
	$.ajax({
		type : "POST",
		async : false,
		url : "farmer_populateDynamicFields.action",
		data : dataa,
		success : function(result) {			
			json = result;
		}
	});
//alert(json);
	$(json)
			.each(
					function(k, v) {

						$(v.sections)
								.each(
										function(key, val) {
											 //alert(val.secName);
											secCode.push(val.secCode);
											var sectionSerialNum = secCode
													.indexOf(val.secCode);
											sectionSerialNum = sectionSerialNum + 1;
											var aPanel = $("<div/>").addClass(
													"aPanel");
											var aTitle = $("<div/>").addClass(
													"aTitle titleHd").html(
													"<h2>" + sectionSerialNum
															+ "." + val.secName
															+ "</h2>");
											var aContent = $("<div/>")
													.addClass(
															"aContent dynamic-form-con "
																	+ val.secCode);
											aPanel.append(aTitle);

											table = $("<table>")
													.addClass(
															"table table-bordered dynTable tab" + val.secCode);
											var thead0 = $(
													"<th > S.no </th>").addClass("dynHeadSN");
													
											var thead1 = $(
													"<th > Question </th>")
													.addClass("dynHead");
											var thead2 = $(
													"<th width='20%'> Answer </th>")
													.addClass("dynHeadAns");
											if(val.isScore!='2' && val.typez!='2'){
											var thead4 = $(
													"<th > Image / Video /Plotting </th>")
													.addClass("imgHead hide");
											}
											table.append(thead0);
											table.append(thead1);
											table.append(thead2);
											table.append(thead4);
											if(val.isScore=='1'){
												var thead5 = $(
												"<th > Score </th>")
												.addClass("dynHeadAns");
												table.append(thead5);
												
												var thead6 = $(
												"<th > Percentage </th>")
												.addClass("dynHeadAns");
												table.append(thead6);
											}
											if(val.isScore=='2'  && val.typez=='2'){
												var thead5 = $(
												"<th > Score </th>")
												.addClass("dynHeadAns");
												table.append(thead5);
												
												var thead6 = $(
												"<th > Grade </th>")
												.addClass("dynHeadAns");
												table.append(thead6);
												
												var thead7 = $(
												"<th > Action Plan </th>")
												.addClass("dynHeadAns");
												table.append(thead7);
												
												var thead8= $(
												"<th > DeadLine </th>")
												.addClass("dynHeadAns");
												table.append(thead8);
												
													
												var thead11= $(
												"<th > Follow Up Details </th>")
												.addClass("dynHeadAns");
												table.append(thead11);
											}
											
											aPanel.append(table);

											aPanel.append(aContent);

											var formContainerWrapper = $(
													"<div/>").addClass(
													"formContainerWrapper");
											formContainerWrapper.append(aPanel);
											$(".dynamicFieldsRender").append(
													formContainerWrapper);

										});
						var trow1 ;
						$(v.fields)
								.each(
										function(key, val) {

											sectionSerialNo = Number(secCode
													.indexOf(val.sectionId));
											sectionSerialNo = sectionSerialNo + 1;
											var fieldSerialNo = 1;

											$(
													'.' + val.sectionId
															+ ' > .dynTable td')
													.each(
															function() {
																if (this.className == 'snoClass') {
																	var cellText = $(
																			this)
																			.html();
																	var index = cellText
																			.indexOf('.');
																	var previousValue = cellText
																			.substr(
																					index + 1,
																					cellText.length);
																	fieldSerialNo = Number(previousValue) + 1;
																}
															});

											if (val.compoType != 8) {

												if (val.compoType != 12 && val.compoType != 15) {
													/*
													 * table = $("<table>")
													 * .addClass( "table
													 * table-bordered
													 * dynTable");
													 */
												/* alert(val.secCode); */
												table = $(".tab"+val.secCode);

													 trow1 = $("<tr>");
													var tdata0 = $(
															"<td>"
																	+ sectionSerialNo
																	+ "."
																	+ fieldSerialNo
																	+ "</td>")
															.addClass(
																	"snoClass");
													var tdata1 = $(
															"<td>"
																	+ val.compoName
																	+ "</td>")
															.addClass("quest");
													var tdata2 = $("<td></td>")
															.addClass(
																	" questAns ans"
																			+ " "
																			+ val.compoCode);
												
													
													trow1.append(tdata0);
													trow1.append(tdata1);
													trow1.append(tdata2);
													if(val.isScore!='2' && val.typez!='2'){
														var tdata3 = $("<td></td>")
																.addClass("imgTd hide");
														
														trow1.append(tdata3);
														}
													
													if(val.isScore=='1'){
														var tdata4 = $("<td></td>")
														.addClass("questAns score"+ val.compoCode);
														trow1.append(tdata4);
														
														var tdata5 = $("<td></td>")
														.addClass("questAns percentage"+ val.compoCode);
														trow1.append(tdata5);
														
														}
													if(val.isScore=='2' && val.typez=='2'){
														var tdata4 = $("<td></td>")
														.addClass("questAns score"+ val.compoCode);
														trow1.append(tdata4);
														
														var tdata5 = $("<td></td>")
														.addClass("questAns grade"+ val.compoCode);
														trow1.append(tdata5);
														
														var tdata6 = $("<td></td>")
														.addClass("questAns acPlanDet"+ val.compoCode);
														trow1.append(tdata6);
														
														
														var tdata7 = $("<td></td>")
														.addClass("questAns deadline"+ val.compoCode);
														trow1.append(tdata7);
														
														
														var tdata11= $("<td></td>")
														.addClass("questAns followDet"+ val.compoCode);
														trow1.append(tdata11);
													
														}
													
													table.append(trow1);
													$("." + val.sectionId)
															.append(table);

												} else {

													/*
													 * table = $("<table>")
													 * .addClass( "table
													 * table-bordered
													 * dynTable");
													 */
													
													table = $(".tab"+val.secCode);
													 trow1 = $("<tr>");
													var tdata0 = $(
															"<td>"
																	+ sectionSerialNo
																	+ "."
																	+ fieldSerialNo
																	+ "</td>")
															.addClass(
																	"snoClass");
													var tdata1 = $(
															"<td>"
																	+ val.compoName
																	+ "</td>")
															.addClass("quest");

													/*var tdata2 = $("<td >")
															.addClass(
																	"quest ans"
																			+ " "
																			+ val.compoCode);*/
													var classZ = "imgTd"+ val.compoCode;
													if(val.compoType == 15){	
														classZ = "plot"+ val.compoCode;
													}
													
													var tdata2 = $(
															"<td class='imgTd' width='800px' ></td>")
															.addClass(classZ);
													trow1.append(tdata0);
													trow1.append(tdata1);
													trow1.append(tdata2);
													//trow1.append(tdata3);
													table.append(trow1);
													$("." + val.sectionId)
															.append(table);

												}

												if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){
													var insertEle = $(
															'[name="'
																	+ val.afterInsert
																	+ '"]')
															.parent().parent();
													$(dFlex).insertAfter(
															insertEle);
												}

											} else {
												var parentNames = $(
														"." + val.sectionId)
														.parent().parent()
														.find('td').length;
													if (parentNames <= 1) {
													$("." + val.sectionId)
															.parent().parent()
															.find('th').hide();
												} else {
													$("." + val.sectionId)
															.parent().parent()
															.find('th').show();
												}

												var table = $("<table>")
														.addClass(
																"table table-bordered");
												var tr = $("<tr/>").attr({
													id : "tDynmaicRow" + val.id
												});
												table.append(tr);
												$("." + val.sectionId).append(
														table);

											}

											if (val.parentDepenCode != ''
													&& val.parentDepenKey != '' && val.isMobile!='6') {
												if (val.parentDepenKey
														.indexOf(",") >= 0
														&& val.parentDepenCode
																.indexOf(",") < 0) {
													var values = val.parentDepenKey
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = val.parentDepenCode
																				+ "_"
																				+ e
																						.trim();
																		trow1
																				.addClass("hide"
																						+ " par"
																						+ val.parentDepenCode);
																		trow1
																				.addClass(classN);
																	});
												} else if (val.parentDepenKey
														.indexOf(",") >= 0
														&& val.parentDepenCode
																.indexOf(",") >= 0) {
													var values = val.parentDepenKey
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var depCode = val.parentDepenCode
																				.split(",");
																		$
																				.each(
																						depCode,
																						function(
																								j,
																								f) {
																							var classN = f
																									.trim()
																									+ "_"
																									+ e
																											.trim();
																							trow1
																									.addClass("hide"
																											+ " par"
																											+ f
																													.trim());
																							trow1
																									.addClass(classN);
																						});
																	});
												} else if (val.parentDepenKey
														.indexOf(",") < 0
														&& val.parentDepenCode
																.indexOf(",") >= 0) {
													var values = val.parentDepenCode
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = e
																				.trim()
																				+ "_"
																				+ val.parentDepenKey;
																		trow1
																				.addClass("hide"
																						+ " par"
																						+ e
																								.trim());
																		trow1
																				.addClass(classN);
																	});
												} else {
													var classN = val.parentDepenCode
															+ "_"
															+ val.parentDepenKey;
													trow1
															.addClass("hide"
																	+ " par"
																	+ val.parentDepenCode);
													trow1.addClass(classN);
												}
											}

										});
						v.groups.sort(GetSortOrder("typez"));

						$(v.images)
									.each(
											function(key, val) {
												if(val.isImage==1){
													 jQuery(".imgHead").removeClass("hide");
													 jQuery(".imgTd").removeClass("hide");
											
												}
											});
						$(v.groups).each(
								function(key, val) {

									if (val.compoType != 10) {

										$("#tDynmaicRow" + val.refId).append(
												$("<td/>").addClass(
														val.compoCode).html(
														val.compoName));
									}
								});

						setDynamicFieldDetailValues();

					});

}



/** Detail Page Changes */
function renderDynamicDetailFeildsByTxnTypeOldModel() {

	var json = "";
	var table;
	var aPanel;
	var secCode = [];
	var dataa = {
		selectedObject : getId(),
		txnTypez : getTxnType(),
		queryType : "1",
		branch:getBranchIdDyn()

	}
	$.ajax({
		type : "POST",
		async : false,
		url : "farmer_populateDynamicFields.action",
		data : dataa,
		success : function(result) {			
			json = result;
		}
	});


	$(json).each(function(k, v) {
		
		$(v.sections).each(function(key, val) {
			
			var aPanel = $("<div/>").addClass("aPanel");
			var aTitle = $("<div/>").addClass("aTitle").html("<h2>"+val.secName+"</h2>");
			var aContent=$("<div/>").addClass("aContent dynamic-form-con "+val.secCode);
			aPanel.append(aTitle);
			aPanel.append(aContent);
			
			var formContainerWrapper = $("<div/>").addClass("formContainerWrapper");
			formContainerWrapper.append(aPanel);
			$(".dynamicFieldsRender").append(formContainerWrapper);
			
		
			
		});
		
		$(v.fields).each(function(key, val) {
			
			
			
			if(val.compoType!=8){
				
				if(val.compoType!=12){
					var dFlex = $("<div/>").addClass("dynamic-flexItem2");
					var p1 = $("<p/>").addClass('flexItem').text(val.compoName);
					var p2 = $("<p  style='word-wrap: break-word'/>").addClass('flexItem '+val.compoCode);
					dFlex.append(p1);
					dFlex.append(p2);
					
					$("."+val.sectionId).append(dFlex);
					
					
				}else{
					if(isEmpty(val.parentDepen)){
						var dFlex = $("<div/>").addClass("dynamic-flexItem2");
						var p1 = $("<p/>").addClass('flexItem').text(val.compoName);
						var p2 = $("<p  style='word-wrap: break-word'/>").addClass('flexItem '+val.compoCode);
						dFlex.append(p1);
						dFlex.append(p2);
						var ids = 1;
						// dFlex.append("<button type='button' class='btn btn-sm
						// ' style='margin-right:44%'
						// onclick=enablePhotoModal('"+ids+"')><i class='fa
						// fa-picture-o' aria-hidden='true'></i></button>");
						$("."+val.sectionId).append(dFlex);
						
					}
				}
				
				
				if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){
					var insertEle = $('[name="'+val.afterInsert+'"]').parent().parent();
					$(dFlex).insertAfter(insertEle);
				}	
				
				
			}else{
				var table = $("<table>").addClass("table table-bordered");
				var tr = $("<tr/>").attr({id:"tDynmaicRow"+val.id});
				table.append(tr);
				$("."+val.sectionId).append(table);
			}
			
			
			
		});
	
		$(v.groups).each(function(key, val) {
			
			if(val.compoType!=10){
				$("#tDynmaicRow"+val.refId).append($("<td/>").addClass(val.compoCode).html(val.compoName));
			}
		});
		

						setDynamicFieldDetailValues();

					});

}
function showFollowUpDetails(jsonArr){
	var table;
	try{
		$("#mbody").empty();
		var mbody = "";
		mbody = "<div class='item active' >";
		mbody+="<table class='table table-bordered tab'>";
		mbody+="<th class='dynHead1' width='40%'>Question</th>";
		mbody+="<th class='dynHeadAns1' width='60%'>Answer</th>"
		$.each( jsonArr, function(i, l){ 
			
			mbody += "<tr class='"+l.qusCode+"'><td>"+ l.fieldName+ "</td>";
			if(l.fieldVal!='' && l.fieldVal!=null){
				mbody+="<td>"+l.fieldVal+"</td>"
			}
			if(l.img!=null && l.img!='' && l.img!=undefined){
				mbody +="<td><img src='data:image/png;base64,"+l.img+"' height='40%' width='60%'/></td></tr>";
			}
			else{
				mbody +="</tr>";
			}
	    });
		mbody+="</table></div>"
			$("#mbody").append(mbody);
		
		$(".modal-content").width("500px");
		$(".modal-content").css("transform","translateX(-20%)");
		$(".dynHead1").css({"background-color":"Aquamarine"});
		$(".dynHeadAns1").css({"background-color":"Aquamarine"});
		$(".carousel-control").addClass("hide");
		// $(".modal-header").text("Follow Up Subquestion Details");
		$(".modal-footer").css({"padding":"10px"});
		document.getElementById("enablePhotoModal").click();
	}catch(e){
		alert(e);
	}
	
}
function setDynamicFieldUpdateValuesByTxnId() {
	// farmerId selectedFarmer txnType
	var dataa = {
		selectedObject : jQuery(".uId").val(),
		txnTypez : getTxnType()
	}

	url = "farmer_populateDynmaicFieldValuesByTxnId.action";

	$.ajax({
		type : "POST",
		async : false,
		data : dataa,
		url : url,
		success : function(result) {
			jsonData = result;
			
		}
	});

	var listFormation = "";

	$(jsonData)
			.each(
					function(k, v) {
						$(v.fields)
								.each(
										function(key, val) {
											if(val.name.indexOf(",")<0){
												var classN = val.code + "_"
														+ val.name;

												try {
													$('.' + classN).removeClass(
															"hide");
												} catch (e) {

												}
												}else{
													$((val.name.split(","))).each(function(k, v) {
														var classN = val.code + "_"
														+ v.trim();

												try {
													$('.' + classN).removeClass(
															"hide");
												} catch (e) {

												}
													});
												}
											if (val.componentType == "1"
													|| val.componentType == "3") {
												$(
														'input[name="'
																+ val.code
																+ '"]').val(
														val.dispName);

											} else if (val.componentType == "4") {
												$('[name="' + val.code + '"]')
														.val(val.name).select2();

												var classN = val.parentDepenCode
														+ "_" + val.name;

												$('.' + classN).removeClass(
														"hide");
											} else if (val.componentType == "5") {

												$('[name="' + val.code + '"]')
														.text(val.name);
											} else if (val.componentType == "9") {

												var id = $(
														'[name="' + val.code
																+ '"]').attr(
														"id");
												// alert(val.name+"-"+id);
												if (!isEmpty(val.name)) {
													var values = val.name
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = val.parentDepenCode
																				+ "_"
																				+ e
																						.trim();

																		$(
																				'.'
																						+ classN)
																				.removeClass(
																						"hide");
																		$(
																				"#"
																						+ id
																						+ " option[value='"
																						+ e
																								.trim()
																						+ "']")
																				.prop(
																						"selected",
																						true);
																	});
												}
												$('[name="' + val.code + '"]')
														.select2();

												/*
												 * 
												 * $('[name="' + val.code +
												 * '"]') .val(val.name);
												 */

											} else if (val.componentType == "2") {
												$(
														"input[name="
																+ val.code
																+ "][value="
																+ val.name
																+ "]").prop(
														'checked', true);
												if (val.name == "1") {
													$(".depend" + val.code)
															.removeClass("hide");
												}
											} else if (val.componentType == "12") {
												var ids = val.photoIds;
												$(
														"input[name="
																+ val.code
																+ "]")
														.parent()
														.append(
																"<button type='button' class='btn btn-sm pull-right photo photoModel' onclick=enablePhotoModal('"
																		+ ids
																		+ "')><i class='fa fa-picture-o' aria-hidden='true'></i></button>")
														.append(
																"<button type='button' class='btn btn-sm pull-right' onclick=deletePhoto('img"
																		+ val.code
																		+ "')><i class='fa fa-remove' aria-hidden='true'></i></button>");
												$('#img' + val.code).attr(
														'src', ids);

											} else if (val.componentType == "13"
													|| val.componentType == "11") {
												var ids = val.photoIds;

												$(
														"input[name="
																+ val.code
																+ "]").parent();

												$('#img' + val.code).addClass(
														"vidd");
												$('#img' + val.code).attr(
														'src', ids);

											} else if (val.componentType == "7") {

												$('#' + val.code)
														.text(val.name);
											} else if (val.componentType == "14") {

												$('#' + val.code).text(
														val.dispName);
												$('#weatherVal' + val.code)
														.val(val.name);
											}else if (val.componentType == "15") {

												$('#plotValue' + val.code).val(
														val.name);
												
											}
										});
						var tempRefId = new Array;
						v.groups.sort(GetSortOrder("typez"));
						$(v.groups)
								.each(
										function(key, val) {

											if (tempRefId.indexOf(val.refId
													+ "-" + val.typez) == '-1') {
												listFormation = "";
												tempRefId.push(val.refId + "-"
														+ val.typez);
												var rowId = ("#tDynmaicRow" + val.refId);
												var newRow = $("<tr/>");
												var rowSize = ($(rowId)
														.children('td').length);
												rowSize--;
												$(rowId)
														.children("td")
														.each(
																function(i, v) {
																	if (i < rowSize) {
																		var classzName = $(
																				this)
																				.attr('class').split(' ')[0]
																				+ "t"
																				+ val.typez;
																		var td = $(
																				"<td/>")
																				.attr(
																						{
																							class : classzName
																						});
																		newRow
																				.append(td);
																		listFormation += classzName
																				+ "$$";
																	}
																});

												newRow
														.append("<td class='hide'><input type='hidden' class='listFormation' value="
																+ listFormation
																+ "></td>");

												var nthCol = (newRow.find("td")
														.size());
												button = $("<button>")
														.attr(
																{
																	class : "btn btn-danger",
																	onclick : 'removeRow(this)',
																	type : 'button'
																});
												button
														.html('<i class="fa fa-trash">');
												newRow.append($("<td>").append(
														button));

												$(rowId).parent()
														.append(newRow);
											}
										});

					});
	$(jsonData).each(function(k, v) {
		var classzName = "";
		v.groups.sort(GetSortOrder("typez"));
		$(v.groups).each(function(key, val) {
			classzName = "." + val.code + "t" + val.typez;
			$(classzName).text(val.dispName);

			$(".listFormation").each(function(ki, vi) {
				tmp = val.code + "%%" + val.name;
				classzName = (val.code + "t" + val.typez);
				tempp = $(this).val().replace(classzName, tmp);
				$(this).val(tempp.toString());
			});

			/*
			 * tmp=val.code+"%%"+val.name; classzName =
			 * (val.code+"t"+val.typez); listFormation =
			 * listFormation.replace(classzName,tmp);
			 */
		});
	});

	console.log(listFormation);
}

function setDynamicFieldUpdateValues() {
	// farmerId selectedFarmer txnType
	var dataa = {
		selectedObject : jQuery(".uId").val(),
		txnTypez : getTxnType()
	}

	url = "farmer_populateDynmaicFieldValuesByRefId.action";

	$.ajax({
		type : "POST",
		async : false,
		data : dataa,
		url : url,
		success : function(result) {
			jsonData = result;
			console.log(JSON.stringify(jsonData))
		}
	});

	var listFormation = "";

	$(jsonData)
			.each(
					function(k, v) {
						$(v.fields)
								.each(
										function(key, val) {
											if(val.name.indexOf(",")<0){
												var classN = val.code + "_"
														+ val.name;

												try {
													$('.' + classN).removeClass(
															"hide");
												} catch (e) {

												}
												}else{
													$((val.name.split(","))).each(function(k, v) {
														var classN = val.code + "_"
														+ v.trim();

												try {
													$('.' + classN).removeClass(
															"hide");
												} catch (e) {

												}
													});
												}	if (val.componentType == "1"
													|| val.componentType == "3") {
												$(
														'input[name="'
																+ val.code
																+ '"]').val(
														val.dispName);
											}	if (val.componentType == "5") {
												$(
														'textarea[name="'
																+ val.code
																+ '"]').val(
														val.dispName);
											}  else if (val.componentType == "4") {

												$('[name="' + val.code + '"]')
														.val(val.name);
												$('[name="' + val.code + '"]')
														.select2();
											} else if (val.componentType == "9") {

												var id = $(
														'[name="' + val.code
																+ '"]').attr(
														"id");
													if (!isEmpty(val.name)) {
													var values = val.name
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = val.code
																				+ "_"
																				+ e
																						.trim();

																		$(
																				'.'
																						+ classN)
																				.removeClass(
																						"hide");
																		$(
																				"#"
																						+ id
																						+ " option[value='"
																						+ e
																								.trim()
																						+ "']")
																				.prop(
																						"selected",
																						true);
																	});
												}
												$('[name="' + val.code + '"]')
														.select2();
												/*
												 * 
												 * $('[name="' + val.code +
												 * '"]') .val(val.name);
												 */
											} else if (val.componentType == "2" || val.componentType == "6") {

												if(val.componentType == "6"){
													if(val.name.includes(",")){
														var array = val.name.split(",");
														$.each(array,function(i){
															   if(array[i] != null && !isEmpty(array[i].trim())){
																   $("input[name="+ val.code+ "][value="+ array[i].trim()+ "]").prop('checked', true);
															   }
															});
													}else{
														$("input[name="+ val.code+ "][value="+ val.name+ "]").prop('checked', true);
													}
												}else{
													$("input[name="+ val.code+ "][value="+ val.name+ "]").prop('checked', true);
												}
												
												if (val.name == "1") {
													$(".depend" + val.code)
															.removeClass("hide");
												}
											} else if (val.componentType == "12") {
												var ids = val.photoIds;
												$(
														"input[name="
																+ val.code
																+ "]")
														.parent()
														.append(
																"<button type='button' class='btn btn-sm pull-right photo photoModel' onclick=enablePhotoModal('"
																		+ ids
																		+ "')><i class='fa fa-picture-o' aria-hidden='true'></i></button>");
												$('#img' + val.code).attr(
														'src', ids);

											} else if (val.componentType == "13"
													|| val.componentType == "11") {
												var ids = val.photoIds;
												$(
														"input[name="
																+ val.code
																+ "]")
														.parent()
														.append(
																"<button type='button' class='btn btn-sm pull-right' onclick=playAudioFiles('vid"
																		+ val.code
																		+ "')><i class='fa fa-play-circle-o' aria-hidden='true'></i></button>")
														.append(
																"<button type='button' class='btn btn-sm pull-right' onclick=stopVideo('vid"
																		+ val.code
																		+ "')><i class='fa fa-stop-circle' aria-hidden='true'></i></button>")
														.append(
																"<button type='button' class='btn btn-sm pull-right' onclick=downloadAudio('"
																		+ ids
																		+ "')><i class='fa fa-download' aria-hidden='true'></i></button>")
														.append(
																" <video width='600' height='30'  preload='none'  ><source src='playAudioFiles('"
																		+ ids
																		+ "','",
																val.componentType,
																"' ) type='video/mp4'></video>");
												$('#img' + val.code).addClass(
														"vidd");
												$('#img' + val.code).attr(
														'src', ids);

											} else if (val.componentType == "7") {

												$('#' + val.code)
														.text(val.name);
											} else if (val.componentType == "14") {

												$('#' + val.code).text(
														val.dispName);
												$('#weatherVal' + val.code)
														.val(val.name);
											}
										});
						var tempRefId = new Array;
						v.groups.sort(GetSortOrder("typez"));
						$(v.groups)
								.each(
										function(key, val) {

											if (tempRefId.indexOf(val.refId
													+ "-" + val.typez) == '-1') {
												listFormation = "";
												tempRefId.push(val.refId + "-"
														+ val.typez);
												var rowId = ("#tDynmaicRow" + val.refId);
												var newRow = $("<tr/>");
												var rowSize = ($(rowId).find(
														"td").size());
												rowSize--;
												$(rowId)
														.find("td")
														.each(
																function(i, v) {
																	if (i != rowSize) {

																		var classNam = $(
																				this)
																				.attr(
																						'class')
																				.split(
																						' ')[0];
																		var classzName = $(
																				this)
																				.prop(
																						"class")
																				+ " "
																				+ classNam
																				+ "t"
																				+ val.typez;
																		var td = $(
																				"<td/>")
																				.attr(
																						{
																							class : classzName
																						});
																		newRow
																				.append(td);
																		listFormation += classNam
																				+ "t"
																				+ val.typez
																				+ "$$";
																	}
																});

												newRow
														.append("<td class='hide'><input type='hidden' class='listFormation' value="
																+ listFormation
																+ "></td>");

												var nthCol = (newRow.find("td")
														.size());
												button = $("<button>")
														.attr(
																{
																	class : "btn btn-danger",
																	onclick : 'removeRow(this)',
																	type : 'button'
																});
												button
														.html('<i class="fa fa-trash">');
												newRow.append($("<td>").append(
														button));

												$(rowId).parent()
														.append(newRow);
											}
										});

					});
	$(jsonData).each(function(k, v) {
		var classzName = "";
		v.groups.sort(GetSortOrder("typez"));
		$(v.groups).each(function(key, val) {
			classzName = "." + val.code + "t" + val.typez;
			$(classzName).text(val.dispName);

			$(".listFormation").each(function(ki, vi) {
				tmp = val.code + "%%" + val.name;
				classzName = (val.code + "t" + val.typez);
				tempp = $(this).val().replace(classzName, tmp);
				$(this).val(tempp.toString());
			});

			/*
			 * tmp=val.code+"%%"+val.name; classzName =
			 * (val.code+"t"+val.typez); listFormation =
			 * listFormation.replace(classzName,tmp);
			 */
		});
	});

	console.log(listFormation);
}

function setDynamicFieldDetailValuesFarmer() {
		
	var dataa = {};
	var url = "";
	var jsonData = "";
	dataa = {
		selectedObject : jQuery(".uId").val(),
		txnTypez : getTxnType()
	}
	url = "farmer_populateDynmaicFieldValuesByRefId.action";

	$.ajax({
		type : "POST",
		async : false,
		data : dataa,
		url : url,
		success : function(result) {
			jsonData = result;

		}
	});

	$(jsonData)
			.each(
					function(k, v) {

						$(v.fields)
								.each(
										function(key, val) {
											$("." + val.code)
													.text(val.dispName);
											$("." + val.code)
													.text(val.dispName);
											if(val.name.indexOf(",")<0){
												var classN = val.code + "_"
														+ val.name;

												try {
													$('.' + classN).removeClass(
															"hide");
												} catch (e) {

												}
												}else{
													$((val.name.split(","))).each(function(k, v) {
														var classN = val.code + "_"
														+ v.trim();

												try {
													$('.' + classN).removeClass(
															"hide");
												} catch (e) {

												}
													});
												}
											/*
											 * alert(val.isActPlan) if
											 * (val.isActPlan !=null) { var ids =
											 * val.photoIds; $("." + val.code)
											 * .append( "<button type='button'
											 * class='btn btn-sm pull-right
											 * photo photoModel'
											 * style='margin-right:15%'
											 * onclick=enablePhotoModal('" + ids +
											 * "')><i class='fa fa-picture-o'
											 * aria-hidden='true'></i></button>")
											 * .append( "<button type='button'
											 * class='hide btn btn-sm pull-right
											 * photoPdf' style='margin-right:2%'
											 * onclick=letImg('" + ids + "','" +
											 * val.code + "')></button>"); }
											 */
											if (val.photoCompoAvailable == "1") {
												var ids = val.photoIds;
												$("." + val.code)
														.append(
																"<button type='button' class='btn btn-sm pull-right photo photoModel' style='margin-right:15%' onclick=enablePhotoModal('"
																		+ ids
																		+ "')><i class='fa fa-picture-o' aria-hidden='true'></i></button>")
														.append(
																"<button type='button' class='hide btn btn-sm pull-right photoPdf' style='margin-right:2%' onclick=letImg('"
																		+ ids
																		+ "','"
																		+ val.code
																		+ "')></button>");

											} else if (val.photoCompoAvailable == "2") {
												var ids = val.photoIds;

												$("." + val.code)
														.append(
																"<button type='button' class='btn btn-sm pull-right' style='margin-right:15%' onclick=playAudioFiles('vid"
																		+ val.code
																		+ "','"
																		+ val.componentType
																		+ "' )><i class='fa fa-play-circle-o' aria-hidden='true'></i></button>")
														.append(
																" <video class='hide' id='vid"
																		+ val.code
																		+ "' preload='none' controls ><source  src='farmer_populateVideoPlay?imgId="
																		+ ids
																		+ "' type='video/mp4'></video>");

											} else if (val.componentType == "14") {

												$('#' + val.code).text(
														val.dispName);
												$('#weatherVal' + val.code)
														.val(val.name);
											} else if (val.componentType == "9") {
												if (!isEmpty(val.name)) {
													var values = val.name
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = val.code
																				+ "_"
																				+ e
																						.trim();

																		$(
																				'.'
																						+ classN)
																				.removeClass(
																						"hide");

																	});
												}

											}
										});

						var tempRefId = new Array();
						v.groups.sort(GetSortOrder("typez")); 

						$(v.groups)
								.each(
										function(key, val) {
											if (tempRefId.indexOf(val.refId
													+ "-" + val.typez) == '-1') {
												tempRefId.push(val.refId + "-"
														+ val.typez);
												var rowId = ("#tDynmaicRow" + val.refId);
												var newRow = $("<tr/>");
												$(rowId)
														.find("td")
														.each(
																function(k, v) {

																	var classzName = $(
																			this)
																			.attr('class').split(' ')[0]
																			+ "t"
																			+ val.typez;
																	var td = $(
																			"<td/>")
																			.attr(
																					{
																						class : classzName
																					});
																	newRow
																			.append(td);

																});
														$(rowId).parent()
														.append(newRow);

											}
										});

					});

	$(jsonData)
			.each(
					function(k, v) {
						var classzName = "";
						v.groups.sort(GetSortOrder("typez"));
						$(v.groups)
								.each(
										function(key, val) {
											classzName = "." + val.code + "t"
													+ val.typez;
											$(classzName).text(val.dispName);
											if (val.photoCompoAvailable == "1") {
												var ids = val.photoIds;

												$(classzName)
														.append(
																"<button type='button' class='btn btn-sm pull-right photo photoModel' style='margin-right:2%' onclick=enablePhotoModal('"
																		+ ids
																		+ "')><i class='fa fa-picture-o' aria-hidden='true'></i></button>")
														.append(
																"<button type='button' class='hide btn btn-sm pull-right photoPdf' style='margin-right:2%' onclick=letImg('"
																		+ ids
																		+ "','"
																		+ val.code
																		+ "')></button>");
											}
										});
					});
}

function setDynamicFieldDetailValues() {
	var dataa = {};
	var url = "";
	var jsonData = "";

	dataa = {
		selectedObject : jQuery(".uId").val(),
		txnTypez : getTxnType()
	}

	url = "farmer_populateDynmaicFieldValuesByTxnId.action";

	$.ajax({
		type : "POST",
		async : false,
		data : dataa,
		url : url,
		success : function(result) {
		jsonData = result;

		}
	});

	$(jsonData)
			.each(
					function(k, v) {

						$(v.fields)
								.each(
										function(key, val) {
											
											$("." + val.code)
											.text(val.dispName);	

											$(".score"+val.code ).text(val.score);
											$(".percentage"+val.code ).text(val.percentage);
											
											if(val.isAct=='3'){
												$(".acPlanDet"+val.code ).text(val.actPlan);
												$(".deadline"+val.code ).text(val.deadline);
												$(".actStatus"+val.code ).text(val.actStatus);
												$(".grade"+val.code ).text(val.grade);
											}
											
											
											if(val.name.indexOf(",")<0){
											var classN = val.code + "_"
													+ val.name;

											try {
												$('.' + classN).removeClass(
														"hide");
											} catch (e) {

											}
											}else{
												$((val.name.split(","))).each(function(k, v) {
													var classN = val.code + "_"
													+ v.trim();

											try {
												$('.' + classN).removeClass(
														"hide");
											} catch (e) {

											}
												});
											}
											
											/*
											 * if (val.isActPlan !=null) {
											 * 
											 * $(".acPlanDet" + val.code)
											 * .append( "<button type='button'
											 * class='btn btn-sm pull-right
											 * photo imgCenter photoModel'><i
											 * class='fa fa-picture-o'
											 * aria-hidden='true'></i></button>"); }
											 */
											if (val.photoCompoAvailable == "1") {
												var ids = val.photoIds;
												$(".imgTd" + val.code)
														.append(
																"<button type='button' class='btn btn-sm pull-left'  onclick=enablePhotoModal('"
																		+ ids
																		+ "')><i class='fa fa-picture-o' aria-hidden='true'></i></button>")
														.append(
																"<button type='button' class='hide btn btn-sm pull-right photoPdf' style='margin-right:2%' onclick=letImg('"
																		+ ids
																		+ "','"
																		+ val.code
																		+ "')></button>")
														.append(
																"<button type='button' class='btn btn-sm pull-right'  onclick=popDownload('"
																				+ ids
																				+ "')><i class='fa fa-download' aria-hidden='true'></i></button>");

											} else if (val.photoCompoAvailable == "2") {
												var ids = val.photoIds;

												$("." + val.code)
														.append(
																"<button type='button' class='btn btn-sm pull-right' style='margin-right:15%' onclick=playAudioFiles('vid"
																		+ val.code
																		+ "','"
																		+ val.componentType
																		+ "')><i class='fa fa-play-circle-o' aria-hidden='true'></i></button>")
														.append(
																" <video class='hide' id='vid"
																		+ val.code
																		+ "' preload='none' controls ><source  src='farmer_populateVideoPlay?imgId="
																		+ ids
																		+ "' type='video/mp4'></video>");

											}else if (val.componentType == "15") {
												$("." + val.code)
												.text("");
											
												if(val.name!=''){
													$(".plot" + val.code)
													.append("<input id='plotValue"+val.code+"' type='hidden' value="+val.name+"/>").append(
														"<button type='button' class='btn faMap' style='margin-right:15%' onclick=showFarmMap('"
																+ val.code
																+ "')><i class='fa fa-play-circle-o' aria-hidden='true'></i></button>")
												}else{
													$("." + val.code)
													.append(
															"<button type='button' class='btn no-latLonIcn' style='margin-right:15%' ><i class='fa fa-play-circle-o' aria-hidden='true'></i></button>")
												}
									
											}
											
											if(val.isAct==3 && val.subAns!=null && val.subAns!='' && val.subAns!=undefined && val.subAns!='[]'){
												$(".followDet"+val.code).append("<button type='button' class='btn btn-sm pull-right' style='margin-right:2%' onclick='showFollowUpDetails("+val.subAns+")'>Followup Details</button>")
											}
											
										
											
										});

						var tempRefId = new Array();
						v.groups.sort(GetSortOrder("typez")); 

						$(v.groups)
								.each(
										function(key, val) {

											if (tempRefId.indexOf(val.refId
													+ "-" + val.typez) == '-1') {
												tempRefId.push(val.refId + "-"
														+ val.typez);
												var rowId = ("#tDynmaicRow" + val.refId);
												var newRow = $("<tr/>");
												// alert( val.typez);
												
												$(newRow).attr("id",val.typez);
												$(rowId)
														.find("td")
														.each(
																function(k, v) {

																	var classzName = $(
																			this)
																			.prop(
																					"class")
																			+ "t"
																			+ val.typez;
																	var td = $(
																			"<td/>")
																			.attr(
																					{
																						class : classzName
																					});
																	newRow
																			.append(td);

																});
											
												$(rowId).parent()
														.append(newRow);
											}
										});

					});

	$(jsonData)
			.each(
					function(k, v) {
						var classzName = "";
						v.groups.sort(GetSortOrder("typez"));
						$(v.groups)
								.each(
										function(key, val) {
											classzName = "." + val.code + "t"
													+ val.typez;
											$(classzName).text(val.dispName);
											if (val.photoCompoAvailable == "1") {
												var ids = val.photoIds;

												$(classzName)
														.append(
																"<button type='button' class='btn btn-sm pull-right photo photoModel' style='margin-right:2%' onclick=enablePhotoModal('"
																		+ ids
																		+ "')><i class='fa fa-picture-o' aria-hidden='true'></i></button>")
														.append(
																"<button type='button' class='hide btn btn-sm pull-right photoPdf' style='margin-right:2%' onclick=letImg('"
																		+ ids
																		+ "','"
																		+ val.code
																		+ "')></button>");
											}
										});
					});
}

function letImg(idArr, code) {
	try {
		var str_array = idArr.toString().split(',');
		// $("#mbody").empty();
		var mbody = "";

		for (var i = 0; i < str_array.length; i++) {
			if (i == 0) {
				mbody = "";
				mbody = "<div >";
				mbody += '<img class="photoPdfImg" width="200" height="170" src="farmer_populateImageDynamic.action?imgId='
						+ str_array[i] + '"/>';
				mbody += "</div>";
			} else {
				mbody = "";
				mbody = "<div >";
				mbody += '<img class="photoPdfImg"  width="200" height="170" src="farmer_populateImageDynamic.action?imgId='
						+ str_array[i] + '"/>';
				mbody += "</div>";
			}

		}
		$('.' + code).append(mbody);
	} catch (e) {
		alert(e);
	}
}

function enablePhotoModal(idArr) {
	try {
		var str_array = idArr.toString().split(',');
		$("#mbody").empty();
		var mbody = "";
		for (var i = 0; i < str_array.length; i++) {
			if (i == 0) {
				mbody = "";
				mbody = "<div class='item active'>";
				mbody += '<img src="farmer_populateImageDynamic.action?imgId='
					+ str_array[i]  + '"/>';
				mbody += "</div>";
			} else {
				mbody = "";
				mbody = "<div class='item'>";
				mbody += '<img  src="farmer_populateImageDynamic.action?imgId='
					+ str_array[i]  + '"/>';
				mbody += "</div>";
			}
			$("#mbody").append(mbody);
		}
		$(".modal-dialog").css("width", "50%");
		$(".modal-dialog").css("height", "40%");
    
		// $("#mbody").first().addClass( "active" );

		document.getElementById("enablePhotoModal").click();
	} catch (e) {
		alert(e);
	}
}


function enablePhotoModaImg(idArr) {
	try {
		var str_array = idArr.toString().split(',');
		$("#mbody").empty();
		var mbody = "";
		for (var i = 0; i < str_array.length; i++) {
			if (i == 0) {
				mbody = "";
				mbody = "<div class='item active'>";
				mbody += '<img src="'
					+ $('#'+idArr).attr("src")  + '"/>';
				mbody += "</div>";
			} else {
				mbody = "";
				mbody = "<div class='item'>";
				mbody += '<img  src="'
					+ $('#'+idArr).attr("src")  + '"/>';
				mbody += "</div>";
			}
			$("#mbody").append(mbody);
		}
		$(".modal-dialog").css("width", "50%");
		$(".modal-dialog").css("height", "40%");
    
		// $("#mbody").first().addClass( "active" );

		document.getElementById("enablePhotoModal").click();
	} catch (e) {
		alert(e);
	}
}
function popDownload(idArr) {
	document.getElementById("loadIdd").value = idArr;
	$('#imageFileDownload').submit();
}



function buttonDPhotoCancel() {
	document.getElementById("model-close-DPhoto-btn").click();
}

function calcFormulaList(obj, formula, targetObj) {
	// var selectedObjId = $(obj).attr("name");
	var formulaString = formula;
	formulaString = formulaString.replace(/[{}]/g, "");
	formulaString = formulaString.replace(/##/g, "");
	var dataObj = [];
	try {
		if (selectedId == '' || selectedId == null || selectedId == '0') {
			if (entityType == '1' || entityType == '5') {
				selectedId = $("#farmer").val();
			} else if (entityType == '2') {
				selectedId = $("#farmList").val();
			} else if (entityType == '3') {
				selectedId = $("#groupId").val();
			} else if (entityType == '4') {
				selectedId = $("#farmList").val();
			}
		}
		dataObj = {
			formulaEquation : formula,
			name : selectedId,
			selectedObject : entityType,
		}

	} catch (e) {
		dataObj = {
			formulaEquation : formula,
			name : '',
			selectedObject : '',
		}

	}
	formulaString = formulaString.replace(/[{}]/g, "");
	$
			.ajax({
				type : "POST",
				async : false,
				data : dataObj,
				url : "farmer_populateExtractFormula.action",
				success : function(result) {

					if (result.indexOf("~") >= 0) {
						var con = result.split("~");

						$((con[0].split(",")))
								.each(
										function(k, v) {
											var val = 0;
											if ($('.list' + v.toString().trim()).length > 0) {
												$('.list' + v.toString().trim())
														.each(
																function(i, obj) {

																	val = getDataType(val)
																			+ getDataType($(
																					obj)
																					.text());
																});
											}

											formulaString = formulaString
													.replace(v.toString()
															.trim(), val
															.toString().trim());
										});

						if (con[1].indexOf(",") >= 0) {
							$((con[1].split(","))).each(
									function(k, v) {
										var conts = v.aplit("-")[0];
										var val = v.aplit("-")[1];
										formulaString = formulaString.replace(
												conts.toString().trim(), val
														.toString().trim());
										$('#' + conts.toString().trim()).text(
												val.toString().trim());
									});

						} else {

							var conts = con[1].trim().split("-")[0];
							var val = con[1].trim().split("-")[1];
							formulaString = formulaString.replace(conts
									.toString().trim(), val.toString().trim());

							$('#' + conts.toString().trim()).text(
									val.toString().trim());
						}
					} else {
						$((result.split(",")))
								.each(
										function(k, v) {
											var val = 0;
											if ($('.list' + v.toString().trim()).length > 0) {
												$('.list' + v.toString().trim())
														.each(
																function(i, obj) {

																	val = getDataType(val)
																			+ getDataType($(
																					obj)
																					.text());
																});
											}

											formulaString = formulaString
													.replace(v.toString()
															.trim(), val
															.toString().trim());
										});
					}

				}
			});
	$("#" + targetObj).text(eval(formulaString).toFixed(2));
	if ($("#formulaLabel" + targetObj).length > 0) {

		$("#formulaLabel" + targetObj).val(eval(formulaString)).trigger(
				'change');

	}
}

function calcFormula(obj, formula, targetObj) {
	var selectedObjId = $(obj).attr("name");
	var formulaString = formula;
	formulaString = formulaString.replace(/[{}]/g, "");
	formulaString = formulaString.replace(/##/g, "");
	var dataObj = [];
	try {
		if (selectedId == '' || selectedId == null || selectedId == '0') {
			if (entityType == '1' || entityType == '5') {
				selectedId = $("#farmer").val();
			} else if (entityType == '2') {
				selectedId = $("#farmList").val();
			} else if (entityType == '3') {
				selectedId = $("#groupId").val();
			} else if (entityType == '4') {
				selectedId = $("#farmList").val();
			}
		}
		dataObj = {
			formulaEquation : formula,
			name : selectedId,
			selectedObject : entityType,
		}

	} catch (e) {
		dataObj = {
			formulaEquation : formula,
			name : '',
			selectedObject : '',
		}

	}
	formulaString = formulaString.replace(/[{}]/g, "");
	$.ajax({
		type : "POST",
		async : false,
		data : dataObj,
		url : "farmer_populateExtractFormula.action",
		success : function(result) {

			if (result.indexOf("~") >= 0) {
				var con = result.split("~");

				$((con[0].split(","))).each(
						function(k, v) {
							var val = $('[name="' + v + '"]').val();
							var valLabel = $('[id="' + v + '"]').text();
							if (isEmpty(val) && isEmpty(valLabel)) {
								val = "0";
							} else if (isEmpty(val) && !isEmpty(valLabel)) {
								val = valLabel;
							} else {
								val = val;
							}
							/*
							 * if($('.list'+v.toString().trim()).length > 0){
							 * $('.list'+v.toString().trim()).each(function(i,
							 * obj) {
							 * val=getDataType(val)+getDataType($(this).text());
							 * }); }
							 */

							formulaString = formulaString.replace(v.toString()
									.trim(), val.toString().trim());
						});

				if (con[1].indexOf(",") >= 0) {
					$((con[1].split(","))).each(
							function(k, v) {
								var conts = v.aplit("-")[0];
								var val = v.aplit("-")[1];
								formulaString = formulaString.replace(conts
										.toString().trim(), val.toString()
										.trim());
								$('#' + conts.toString().trim()).text(
										val.toString().trim());
							});

				} else {

					var conts = con[1].trim().split("-")[0];
					var val = con[1].trim().split("-")[1];
					formulaString = formulaString.replace(conts.toString()
							.trim(), val.toString().trim());

					$('#' + conts.toString().trim())
							.text(val.toString().trim());
				}
			} else {
				$((result.split(","))).each(
						function(k, v) {
							var val = $('[name="' + v + '"]').val();
							var valLabel = $('[id="' + v + '"]').text();
							if (isEmpty(val) && isEmpty(valLabel)) {
								val = "0";
							} else if (isEmpty(val) && !isEmpty(valLabel)) {
								val = valLabel;
							} else {
								val = val;
							}

							formulaString = formulaString.replace(v.toString()
									.trim(), val.toString().trim());
						});
			}
		}
	});
	var finalVal = eval(formulaString).toFixed(2);
	
	finalVal = (isNaN(finalVal) || finalVal == Infinity) ? '0' : finalVal;

	$("#" + targetObj).text(finalVal);
	if ($("#formulaLabel" + targetObj).length > 0) {
		$("#formulaLabel" + targetObj).val(finalVal).trigger('change');

	}
	if(finalVal.indexOf("-") >=0){
		alert( $("#" + targetObj).parent().parent()
								.find('label').first().text()
								.replace("*", "")+" should not be less than 0 ");
		$("#" + targetObj).text(0);
	}
	
	selectedId = '';
}

function getDataType(n) {

	if (isInt(n)) {
		return parseInt(n);
	} else if (isFloat(n)) {
		return parseFloat(n)
	} else {
		return 0;
	}

}
function isInt(value) {
	return !isNaN(value) && parseInt(Number(value)) == value
			&& !isNaN(parseInt(value, 10));
}

function isFloat(value) {
	return (!isNaN(value) && value.toString().indexOf('.') > 0)
	/*
	 * { return true; }else{ return false;​ }
	 */
}

function isAlphabet(evt) {
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode
			: ((evt.which) ? evt.which : 0));
	if (charCode > 32 && (charCode < 65 || charCode > 90)
			&& (charCode < 97 || charCode > 122)) {
		return false;
	}
	return true;
}
function isNumber(evt) {

	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}
function isEmail(evt) {
	var reg = '/^([A-Za-z0-9_-.])+@([A-Za-z0-9_-.])+.([A-Za-z]{2,4})$/';
	if (reg.test(evt.value) == false) {

		return false;
	}
	return true;
}
function isDecimal(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46) {
		return false;
	}
	return true;
}
function isAlphaNumeric(evt) {

	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 32
			&& !((charCode >= 48 && charCode <= 57)
					|| (charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122))) {
		return false;
	}
	return true;
}

function getElementValueById(id) {
	var val = $("#" + id).val();
	return val;
}

function getElementValueByText(id) {
	var val = $("#" + id + " option:selected").text();
	return val;
}

function getRadioValueByName(name) {
	var val = $('input[type=radio][name=' + name + ']:checked').val();
	return val;
}

function getCheckBoxValueByName(name) {
	var checkboxes = document.getElementsByName(name);
	var checkboxesChecked = [];

	for (var i = 0; i < checkboxes.length; i++) {
		// And stick the checked ones onto an array...
		if (checkboxes[i].checked) {
			checkboxesChecked.push(checkboxes[i].value);
		}
	}

	return checkboxesChecked.length > 0 ? checkboxesChecked : null;
}

function deletePhoto(id) {
	if(confirm('Do you want to delete the photo?')){
	$('#' + id).attr("src", "");
	$('.photoModel').addClass("hide");
	}

}
function downloadAudio(id) {

	document.getElementById("loadId").value = id;
	$('#audioFileDownload').submit();
}
function stopVideo(id) {
	var video = $('#' + id).get(0);
	// $('#'+id).addClass('hide');
	video.pause();
}

function closeFunction() {
	var id = this.id;
	var video_dialog = $('#' + id).get(0);
	video_dialog.pause();
}

function playAudioFiles(id, compCode) {

	if (compCode == '13') {
		$('#' + id).dialog({
			autoOpen : true,
			resizable : false,
			modal : true,
			width : '500',
			height : '280',
			close : closeFunction,
			overlay : {
				opacity : 0.5,
				background : "black"
			}
		});
		$('#' + id).css("width", "100%");
		$('#' + id).css("height", "235");
	} else {
		$('#' + id).dialog({
			autoOpen : true,
			resizable : false,
			modal : true,
			width : '500',
			height : '180',
			close : closeFunction,
			overlay : {
				opacity : 0.5,
				background : "black"
			}
		});

	}

	var video = $('#' + id).get(0);
	$('#' + id).removeClass('hide');
	if (video.paused === false) {
		video.pause();
	} else {
		video.play();
	}

	/*
	 * audioControlShow(); $('#jquery_jplayer_1').jPlayer({ ready: function () {
	 * $(this).jPlayer("setMedia", { m4v:
	 * "farmer_populateVideoPlay?imgId="+id+"&dt="+new Date()
	 * }).jPlayer("play"); },
	 * 
	 * swfPath: 'js/jplayer', solution: 'html, flash', supplied: 'webmv, ogv,
	 * m4v', preload: 'metadata', volume: 0.8, muted: false, backgroundColor:
	 * '#000000', cssSelectorAncestor: '#jp_container_1', cssSelector: {
	 * videoPlay: '.jp-video-play', play: '.jp-play', pause: '.jp-pause', stop:
	 * '.jp-stop', seekBar: '.jp-seek-bar', playBar: '.jp-play-bar', mute:
	 * '.jp-mute', unmute: '.jp-unmute', volumeBar: '.jp-volume-bar',
	 * volumeBarValue: '.jp-volume-bar-value', volumeMax: '.jp-volume-max',
	 * playbackRateBar: '.jp-playback-rate-bar', playbackRateBarValue:
	 * '.jp-playback-rate-bar-value', currentTime: '.jp-current-time', duration:
	 * '.jp-duration', title: '.jp-title', fullScreen: '.jp-full-screen',
	 * restoreScreen: '.jp-restore-screen', repeat: '.jp-repeat', repeatOff:
	 * '.jp-repeat-off', gui: '.jp-gui', noSolution: '.jp-no-solution' },
	 * errorAlerts: false, warningAlerts: false });
	 */

}

function audioControlShow() {
	jQuery("#audioDiv").removeClass("hide");
	jQuery("#audioDiv").show();
	jQuery("#audioDataDiv").show();
}

function calculateFormula() {
	var textBox_value = document.getElementById(this.id).value;
	var textbox_name = this.name;

	var fields_value = new Array();
	var formula_result = 0;
	var obj;
	if ($(".labelList")[0]) {
		obj = document.getElementsByClassName("labelList");
	}

	for (var i = 1; i <= 2;) {

		$(obj)
				.each(
						function() {

							var flag = true;
							var labelClassName = this.className;
							if (labelClassName.includes(textbox_name)) {

								var componentCode_firstIndex = labelClassName
										.indexOf("-");
								var componentCode_temp_lastIndex = labelClassName
										.indexOf(" formula") - 1;
								var componentCode_lastIndex = componentCode_temp_lastIndex
										- componentCode_firstIndex;
								var componentCodePart = labelClassName.substr(
										componentCode_firstIndex + 1,
										componentCode_lastIndex);
								var componentCode_array = componentCodePart
										.split(',');

								for (var i = 0; i < componentCode_array.length; i++) {
									componentCode_array[i] = componentCode_array[i]
											.replace(/^\s*/, "").replace(
													/\s*$/, ""); // Trim the
																	// excess
																	// whitespace.
									fields_value
											.push(document
													.getElementsByName(componentCode_array[i])[0].value);
								}

								var formula_firstIndex = labelClassName
										.indexOf("{") + 1;
								var formula_temp_lastIndex = labelClassName
										.lastIndexOf("}");
								var formula_lastIndex = formula_temp_lastIndex
										- formula_firstIndex;
								var forula_part = labelClassName.substr(
										formula_firstIndex, formula_lastIndex);
								forula_part = forula_part.replace(/{/g, "");
								forula_part = forula_part.replace(/}/g, "");
								var final_expression = forula_part;

								for (var i = 0; i < componentCode_array.length; i++) {
									if (fields_value[i] != "") {
										final_expression = final_expression
												.replace(
														componentCode_array[i],
														fields_value[i]);
									}
								}

								for (var i = 0; i < fields_value.length; i++) {
									if (fields_value[i] == "") {
										flag = false;
									}
								}

								if (flag == true) {
									formula_result = eval(final_expression);
									document.getElementById(this.id).innerHTML = formula_result;
								}
							}
						});

		if ($(".label")[0]) {
			obj = document.getElementsByClassName("label");
			i = i + 1;
		} else {
			i = i + 2;
		}

	}

}


function showFarmMap(compCode){	
	var content="<div id='map' class='smallmap'></div>";
	enablePopup('',content);
	$('#clsBtn').attr('onclick', "hidePopup('"+compCode+"')");
	$('#delete-button').attr('onclick', "deleteSelectedShape('"+compCode+"')");
	initMap(compCode);
	var landarry   =new Array();
	if($('#plotValue'+compCode).val()!=''){
		var val = $('#plotValue'+compCode).val();
		var area = val.split("~")[1].trim();
		var coord = val.split("~")[0].trim();
		coord = coord.substring(0, coord.length - 1);
		$(coord.split(/\|/)).each(function(key, value) {
			var latLong={};
			latLong.lat = parseFloat(value.split(',')[0].trim()).toFixed(5);
			latLong.lon = parseFloat(value.split(',')[1].trim()).toFixed(5);
		
			landarry.push(latLong);
		});
	
	loadMap(new Array(), landarry, area);
	}
	    
        
}


function initMap(compCode) {

	var url = window.location.href;
	var temp = url;

	for (var i = 0; i < 1; i++) {
		temp = temp.substring(0, temp.lastIndexOf('/'));
	}
	var intermediateImg = "red_placemarker.png";
	var intermediatePointIcon = temp + '/img/' + intermediateImg;

	map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : 11.0168,
			lng : 76.9558
		},
		zoom : 3,
		mapTypeId : google.maps.MapTypeId.HYBRID,
	});

	var polyOptions = {
		strokeWeight : 0,
		fillOpacity : 0.45,
		editable : true,
		draggable : false
	};

	  drawingManager = new google.maps.drawing.DrawingManager({
		drawingMode : google.maps.drawing.OverlayType.POLYGON,
		drawingControl : true,
		drawingControlOptions : {
			position : google.maps.ControlPosition.TOP_CENTER,
			/*
			 * drawingModes : [ 'marker', 'circle', 'polygon', 'polyline',
			 * 'rectangle' ]
			 */
			drawingModes : [ 'polygon' ]
		},
		markerOptions : {
			icon : intermediatePointIcon,
			draggable : true
		},
		polylineOptions : {
			editable : true,
			draggable : false
		},
		polygonOptions : polyOptions,
		circleOptions : {
			fillColor : '#ffff00',
			fillOpacity : 1,
			strokeWeight : 5,
			clickable : false,
			editable : true,
			zIndex : 1
		}
	}); 
	var polygonOptions = drawingManager.get('polygonOptions');
      polygonOptions.fillColor = '#50ff50';
      polygonOptions.strokeColor = '#50ff50';
      drawingManager.set('polygonOptions', polygonOptions);
      
	drawingManager.setMap(map);

	google.maps.event
			.addListener(
					drawingManager,
					'overlaycomplete',
					function(e) {
var finalLatLon="";
	                    if (e.type !== google.maps.drawing.OverlayType.MARKER) {
	                      // Switch back to non-drawing mode after drawing a
							// shape.
	                      drawingManager.setDrawingMode(null);
	                      drawingManager.setOptions({
	                        drawingControl: false
	                      });
	                      // Add an event listener that selects the
							// newly-drawn shape when the user
	                      // mouses down on it.
	                      var newShape = e.overlay;

	                      newShape.type = e.type;
	                      coorArr = new Array();
	                      google.maps.event
	                        .addListener(
	                          newShape,
	                          'click',
	                          function(e) {
	                            if (e.vertex !== undefined) {
	                              if (newShape.type === google.maps.drawing.OverlayType.POLYGON) {
	                                var path = newShape
	                                  .getPaths()
	                                  .getAt(
	                                    e.path);
	                                path
	                                  .removeAt(e.vertex);
	                                if (path.length < 3) {
	                                  newShape
	                                    .setMap(null);
	                                }
	                              }
	                              if (newShape.type === google.maps.drawing.OverlayType.POLYLINE) {
	                                var path = newShape
	                                  .getPath();
	                                path
	                                  .removeAt(e.vertex);
	                                if (path.length < 2) {
	                                  newShape
	                                    .setMap(null);
	                                }
	                              }
	                            }
	                            setSelection(newShape);
	                          });

	                
	                      setSelection(newShape);
	                    }
	                    var newShape = e.overlay;
	                    newShape.type = e.type;

	                    // if (newShape.type ==
						// google.maps.drawing.OverlayType.POLYGON) {
	                    // alert("POLYGON");
	                    var bounds = new google.maps.LatLngBounds();
	                    var area = google.maps.geometry.spherical
	                      .computeArea(newShape.getPath());
	                    var metre = parseFloat(area).toFixed(2);

	                    for (var i = 0; i < newShape.getPath()
	                      .getLength(); i++) {
	                    	
	                      coorArr.push(newShape.getPath().getAt(i)
	                        .toUrlValue(20));
	                    }

	                    google.maps.event.addListener(newShape.getPath(), 'insert_at', function() {
	                      changePolugon(newShape,compCode);
	                    });

	                    google.maps.event.addListener(newShape.getPath(), 'remove_at', function() {

	                      changePolugon(newShape,compCode);
	                    });

	                    google.maps.event.addListener(newShape.getPath(), 'set_at', function(index, previous) {
	                      changePolugon(newShape,compCode);

	                    });

          console.log(coorArr);
	                    // alert("coorArr:"+coorArr);
	                    $(coorArr)
	                      .each(
	                        function(k, v) {
	                          var latLon = v.split(",");
	                          var coordinatesLatLng = new google.maps.LatLng(
	                            latLon[0],
	                            latLon[1]);
	                      	finalLatLon = finalLatLon+latLon[0]+","+latLon[1]+"|"
	                          bounds
	                            .extend(coordinatesLatLng);
	                        });

	                	var myLatlng = bounds.getCenter();
						var test = (metre * 0.000247105);
						var acreConversion = parseFloat(test).toFixed(2);
						var test2 = (acreConversion / 2.4711);
						var hectareConversion = parseFloat(test2).toFixed(2);
								finalLatLon = finalLatLon +"~"+hectareConversion;
						
						finalLatLon = finalLatLon +"~"+myLatlng;
						$('#plotValue'+compCode).val(finalLatLon);
						mapLabel2 = new MapLabel({

							text : hectareConversion+" Ha",
							position : myLatlng,
							map : map,
							fontSize : 14,
							align : 'left'
						});
						mapLabel2.set('position', myLatlng);
						// }
					});

	var card = document.getElementById('pac-card');
	
if(card!=null){
	card = card.cloneNode(true);
	card.classList.remove("hide");
	var cardd = document.getElementById('pac-input');
	var input = cardd.cloneNode(true);
	var types = document.getElementById('type-selector');
	var strictBounds = document
			.getElementById('strict-bounds-selector');

	map.controls[google.maps.ControlPosition.TOP_RIGHT].push(card);
	var autocomplete = new google.maps.places.Autocomplete(input);

	// Bind the map's bounds (viewport) property to the autocomplete object,
	// so that the autocomplete requests use the current map bounds for the
	// bounds option in the request.
	autocomplete.bindTo('bounds', map);
	var infowindow = new google.maps.InfoWindow();
	var infowindowContent = document
			.getElementById('infowindow-content');
	infowindow.setContent(infowindowContent);
	var marker = new google.maps.Marker({
		map : map,
		anchorPoint : new google.maps.Point(0, -29)
	});

	autocomplete
			.addListener(
					'place_changed',
					function() {
						infowindow.close();
						marker.setVisible(false);
						var place = autocomplete.getPlace();
						if (!place.geometry) {
							// User entered the name of a Place that was not
							// suggested and
							// pressed the Enter key, or the Place Details
							// request failed.
							window
									.alert("No details available for input: '"
											+ place.name + "'");
							return;
						}

						// If the place has a geometry, then present it on a
						// map.
						if (place.geometry.viewport) {
							map.fitBounds(place.geometry.viewport);
						} else {
							map.setCenter(place.geometry.location);
							map.setZoom(17); // Why 17? Because it looks
												// good.
						}
						marker.setPosition(place.geometry.location);
						marker.setVisible(true);

						var address = '';
						if (place.address_components) {
							address = [
									(place.address_components[0]
											&& place.address_components[0].short_name || ''),
									(place.address_components[1]
											&& place.address_components[1].short_name || ''),
									(place.address_components[2]
											&& place.address_components[2].short_name || '') ]
									.join(' ');
						}
					});
}

}

function savePlotting(){	
	$('#modalWin').css('margin-top','-230px');	
	$modals.hide();
	$overlays.hide();			
	$('body').css('overflow','visible');

        
}
function hidePopup(comCode){

if($('#command').val()!=undefined && $('#command').val()!="update"){
	$('#plotValue'+comCode).val('');

}
	$('#modalWin').css('margin-top','-230px');	
	$modals.hide();
	$overlays.hide();			
	$('body').css('overflow','visible');
	// $('#map').remove();
	
}

function clearSelection() {
	if (selectedShape) {
		selectedShape.setEditable(false);
		selectedShape = null;
	}
}
function setSelection(shape) {
	clearSelection();
	selectedShape = shape;
	shape.setEditable(true);
}

/*
 * function deleteSelectedShape() { alert("deleteSelectedShape"); if
 * (selectedShape) { alert("test"); selectedShape.setMap(null);
 * 
 *  } }
 */

function deleteSelectedShape(compCode) {
	
	if( confirm('Are you sure you want to continue')){
	 if (plotting != null) {
		    plotting.setMap(null);
		    mapLabel2.set('text', '');
		     drawingManager.setOptions({
		        drawingControl: true
		      });

		      drawingManager.setDrawingMode(google.maps.drawing.OverlayType.POLYGON);
		      plotting = null;
		    
		  }
	 
	 

	if (selectedShape) {
		selectedShape.setEditable(false);
		
		selectedShape.setMap(null);
	}
	drawingManager.setDrawingMode(google.maps.drawing.OverlayType.POLYGON);
	
	mapLabel2.set('text', '');
	coorArr = [];
	$('#plotValue'+compCode).val('');
	}
	// resetForm();

}
      function setMapOnAll(map) {
	        for (var i = 0; i < markersArray.length; i++) {
	        	markersArray[i].setMap(map);
	        }
	        markersArray = new Array();
	 }
     
	 
	 
function loadCustomPopup(){
	$overlays = $('<div id="modOverlay"></div>');
	$modals = $('<div id="modalWin" class="ui-body-c gmap3"></div>');
	$sliders = $('<div id="banner-fade" style="margin:0 auto;"><ul class="bjqs"></ul></div>')
	$close = $('<button id="clsBtn" class="btnCls">X</button>');
	
	$modals.append($sliders, $close);
	$('body').append($overlays);
	$('body').append($modals);
	$modals.hide();
	$overlays.hide();

	/*
	 * jQuery("#clsBtn").click(function () {
	 * $('#modalWin').css('margin-top','-230px'); $modals.hide();
	 * $overlays.hide(); $('body').css('overflow','visible'); });
	 */
}


function enablePopup(head, cont){
	$(window).scrollTop(0); 
	$('body').css('overflow','hidden');
	$(".bjqs").empty();		
	var heading='';
	var contentWidth='100%';
	if(head!=''){
		heading+='<div style="height:8%;"><p class="bjqs-caption">'+head+'</p></div>';
		contentWidth='92%';
	}
	var content="<div style='width:100%;height:"+contentWidth+"'>"+cont+"</div>";	
	$(".bjqs").append('<li>'+heading+content+'</li>')
	$(".bjqs-controls").css({'display':'block'});
	$('#modalWin').css('margin-top','-200px');
	$modals.show();
	$overlays.show();
	$('#banner-fade').bjqs({
        height      : 482,
        width       : 600,
        showmarkers : false,
        usecaptions : true,
        automatic : true,
        nexttext :'',
        prevtext :'',
        hoverpause : false                                           

    });
}


function changePolugon(newPlotting,compCode) {
var finalLatLon = '';
  mapLabel2.set('text', '');
  new_paths = newPlotting.getPath();
  var bounds = new google.maps.LatLngBounds();
  coorArr = new Array();
  for (var i = 0; i < new_paths
    .getLength(); i++) {
    coorArr.push(new_paths.getAt(i)
      .toUrlValue(20));
    var xy = new_paths.getAt(i);
    var coordinatesLatLng = new google.maps.LatLng(
      parseFloat(xy.lat()),
      parseFloat(xy.lng()));
    finalLatLon = finalLatLon+xy.lat()+","+xy.lng()+"|"
    bounds
      .extend(coordinatesLatLng);
  }
  console.log(coorArr);
  var area = google.maps.geometry.spherical
    .computeArea(new_paths);
  var metre = parseFloat(area).toFixed(2);
  var test = (metre * 0.000247105);
  var acreConversion = parseFloat(test).toFixed(2);
  var test2 = (acreConversion / 2.4711);
  var hectareConversion = parseFloat(test2).toFixed(2);
 
	  textTpe = hectareConversion + " Ha" ;
  $('#area').val(hectareConversion);
  var myLatlng = bounds.getCenter();

  $('#farmLatLon').val(myLatlng);

  mapLabel2 = new MapLabel({

    text: textTpe,
    position: myLatlng,
    map: map,
    fontSize: 14,
    align: 'left'
  });
  mapLabel2.set('position', myLatlng);
	finalLatLon = finalLatLon +"~"+hectareConversion;
	
	finalLatLon = finalLatLon +"~"+myLatlng;
	$('#plotValue'+compCode).val(finalLatLon);
}

function deletPolygon() {
  if (plotting != null) {
    plotting.setMap(null);
    mapLabel2.set('text', '');
     drawingManager.setOptions({
        drawingControl: true
      });

      drawingManager.setDrawingMode(google.maps.drawing.OverlayType.POLYGON);
      plotting = null;
    
  }


}

function removeVertex(vertex) {
  if (confirm("Are You sure to delete the point")) {
    var path = plotting.getPath();
    new_paths = plotting;
    path.removeAt(vertex);

  }
}

function loadMap(dataArr, landArea, area) {

    var url = window.location.href;
    var temp = url;
    coorArr = new Array();
    for (var i = 0; i < 1; i++) {
      temp = temp.substring(0, temp.lastIndexOf('/'));
    }
    var intermediateImg = "red_placemarker.png";
    var intermediatePointIcon = temp + '/img/' + intermediateImg;

    var infowindow = new google.maps.InfoWindow();

    var marker, i;
    $(dataArr).each(
      function(k, v) {
        marker = new google.maps.Marker({
          position: new google.maps.LatLng(v.latitude,
            v.longitude),
          icon: intermediatePointIcon,
          map: map
        });
        markers.push(marker);
        google.maps.event.addListener(marker, 'click', (function(marker, i) {
          return function() {
            infowindow.setContent(buildData(v));
            infowindow.open(map, marker);
          }
        })(marker, i));
        map.setCenter({
          lat: v.latitude,
          lng: v.longitude
        });
        map.setZoom(17);
      });
    var cords = new Array();
    var bounds = new google.maps.LatLngBounds();
    if (landArea.length > 0) {
      drawingManager.setOptions({
        drawingControl: false
      });


      $(landArea).each(function(k, v) {
        cords.push({
          lat: parseFloat(v.lat),
          lng: parseFloat(v.lon)
        });
        var coordinatesLatLng = new google.maps.LatLng(
          parseFloat(v.lat),
          parseFloat(v.lon));
        coorArr.push(v.lat + "," + v.lon);
        bounds
          .extend(coordinatesLatLng);

      });
      plotting = new google.maps.Polygon({
        paths: cords,
        strokeColor: '#50ff50',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#50ff50',
        fillOpacity: 0.45,
        editable: true,
        draggable: false
      });
      plotting.setMap(map);
    // plotCOunt = plotCOunt + 1;
      var overlay = {
        overlay: plotting,
        type: google.maps.drawing.OverlayType.POLYGON
      };
     drawingManager.setDrawingMode(null);
      map.fitBounds(bounds);
      var arType = ' Ha';
      // alert("areaType:"+arType);
      var textTpe = "";

      textTpe = area + arType;
      $('#area').val(area);
      mapLabel2 = new MapLabel({

        text: textTpe,
        position: bounds.getCenter(),
        map: map,
        fontSize: 14,
        align: 'left'
      });
      mapLabel2.set('position', bounds.getCenter());
      plotting.getPaths().forEach(function(path, index) {

        google.maps.event.addListener(path, 'insert_at', function() {
          changePolugon(plotting);
        });

        google.maps.event.addListener(path, 'remove_at', function() {

          changePolugon(plotting);
        });

        google.maps.event.addListener(path, 'set_at', function(index, previous) {
          changePolugon(plotting);

        });

      });


      google.maps.event.addListener(plotting, 'rightclick', function(e) {
        // Check if click was on a vertex control point
        if (e.vertex == undefined) {
          return;
        } else {
          removeVertex(e.vertex);
        }
      });
    } else {
      drawingManager.setDrawingMode(google.maps.drawing.OverlayType.POLYGON);
    }


  }

function renderDynamicDetailFeildsByTxnTypeForOCP() {

	var json = "";
	var table;
	var aPanel;
	var secCode = [];
	var dataa = {
		selectedObject : getId(),
		txnTypez : getTxnType(),
		queryType : "1",
		branch:getBranchIdDyn()

	}
	$.ajax({
		type : "POST",
		async : false,
		url : "farmer_populateDynamicFieldsForOCP.action",
		data : dataa,
		success : function(result) {			
			json = result;
		}
	});

	$(json)
			.each(
					function(k, v) {

						$(v.sections)
								.each(
										function(key, val) {
											// alert(val.secName);
											secCode.push(val.secCode);
											var sectionSerialNum = secCode
													.indexOf(val.secCode);
											sectionSerialNum = sectionSerialNum + 1;
											var aPanel = $("<div/>").addClass(
													"aPanel");
											var aTitle = $("<div/>").addClass(
													"aTitle titleHd").html(
													"<h2>" + sectionSerialNum
															+ "." + val.secName
															+ "</h2>");
											var aContent = $("<div/>")
													.addClass(
															"aContent dynamic-form-con "
																	+ val.secCode);
											aPanel.append(aTitle);

											table = $("<table>")
													.addClass(
															"table table-bordered dynTable tab" + val.secCode);
											var thead0 = $(
													"<th > S.no </th>").addClass("dynHeadSN");
													
											var thead1 = $(
													"<th > Question </th>")
													.addClass("dynHead");
											var thead2 = $(
													"<th width='20%'> Answer </th>")
													.addClass("dynHeadAns");
											if(val.isScore!='2' && val.typez!='2'){
											var thead4 = $(
													"<th > Image / Video /Plotting </th>")
													.addClass("imgHead hide");
											
											}
											table.append(thead0);
											table.append(thead1);
											table.append(thead2);
											table.append(thead4);
											if(val.isScore=='1'){
												var thead5 = $(
												"<th > Score </th>")
												.addClass("dynHeadAns");
												table.append(thead5);
												
												var thead6 = $(
												"<th > Percentage </th>")
												.addClass("dynHeadAns");
												table.append(thead6);
											}
											if(val.isScore=='2'  && val.typez=='2'){
												var thead5 = $(
												"<th > Score </th>")
												.addClass("dynHeadAns");
												table.append(thead5);
												
												var thead6 = $(
												"<th > Grade </th>")
												.addClass("dynHeadAns");
												table.append(thead6);
												
												var thead7 = $(
												"<th > Action Plan </th>")
												.addClass("dynHeadAns");
												table.append(thead7);
												
												var thead8= $(
												"<th > DeadLine </th>")
												.addClass("dynHeadAns");
												table.append(thead8);
												
													
												var thead11= $(
												"<th > Follow Up Details </th>")
												.addClass("dynHeadAns");
												table.append(thead11);
											}
											
											aPanel.append(table);

											aPanel.append(aContent);

											var formContainerWrapper = $(
													"<div/>").addClass(
													"formContainerWrapper");
											formContainerWrapper.append(aPanel);
											$(".dynamicFieldsRender").append(
													formContainerWrapper);

										});
						var trow1 ;
						$(v.fields)
								.each(
										function(key, val) {

											sectionSerialNo = Number(secCode
													.indexOf(val.sectionId));
											sectionSerialNo = sectionSerialNo + 1;
											var fieldSerialNo = 1;

											$(
													'.' + val.sectionId
															+ ' > .dynTable td')
													.each(
															function() {
																if (this.className == 'snoClass') {
																	var cellText = $(
																			this)
																			.html();
																	var index = cellText
																			.indexOf('.');
																	var previousValue = cellText
																			.substr(
																					index + 1,
																					cellText.length);
																	fieldSerialNo = Number(previousValue) + 1;
																}
															});

											if (val.compoType != 8) {

												if (val.compoType != 12 && val.compoType != 15) {
													/*
													 * table = $("<table>")
													 * .addClass( "table
													 * table-bordered
													 * dynTable");
													 */
												/* alert(val.secCode); */
												table = $(".tab"+val.secCode);

													 trow1 = $("<tr>");
													var tdata0 = $(
															"<td>"
																	+ sectionSerialNo
																	+ "."
																	+ fieldSerialNo
																	+ "</td>")
															.addClass(
																	"snoClass");
													var tdata1 = $(
															"<td>"
																	+ val.compoName
																	+ "</td>")
															.addClass("quest");
													var tdata2 = $("<td></td>")
															.addClass(
																	" questAns ans"
																			+ " "
																			+ val.compoCode);
												
													
													trow1.append(tdata0);
													trow1.append(tdata1);
													trow1.append(tdata2);
													if(val.isScore!='2' && val.typez!='2'){
														var tdata3 = $("<td></td>")
																.addClass("imgTd hide");
														
														trow1.append(tdata3);
														
														}
													
													if(val.isScore=='1'){
														var tdata4 = $("<td></td>")
														.addClass("questAns score"+ val.compoCode);
														trow1.append(tdata4);
														
														var tdata5 = $("<td></td>")
														.addClass("questAns percentage"+ val.compoCode);
														trow1.append(tdata5);
														
														}
													if(val.isScore=='2' && val.typez=='2'){
														var tdata4 = $("<td></td>")
														.addClass("questAns score"+ val.compoCode);
														trow1.append(tdata4);
														
														var tdata5 = $("<td></td>")
														.addClass("questAns grade"+ val.compoCode);
														trow1.append(tdata5);
														
														var tdata6 = $("<td></td>")
														.addClass("questAns acPlanDet"+ val.compoCode);
														trow1.append(tdata6);
														
														
														var tdata7 = $("<td></td>")
														.addClass("questAns deadline"+ val.compoCode);
														trow1.append(tdata7);
														
														
														var tdata11= $("<td></td>")
														.addClass("questAns followDet"+ val.compoCode);
														trow1.append(tdata11);
													
														}
													
													table.append(trow1);
													$("." + val.sectionId)
															.append(table);

												} else {

													/*
													 * table = $("<table>")
													 * .addClass( "table
													 * table-bordered
													 * dynTable");
													 */
													
													table = $(".tab"+val.secCode);
													 trow1 = $("<tr>");
													var tdata0 = $(
															"<td>"
																	+ sectionSerialNo
																	+ "."
																	+ fieldSerialNo
																	+ "</td>")
															.addClass(
																	"snoClass");
													var tdata1 = $(
															"<td>"
																	+ val.compoName
																	+ "</td>")
															.addClass("quest");

													var tdata2 = $("<td >")
															.addClass(
																	"quest ans"
																			+ " "
																			+ val.compoCode);
													var classZ = "imgTd"+ val.compoCode;
													if(val.compoType == 15){	
														classZ = "plot"+ val.compoCode;
													}
													
													var tdata3 = $(
															"<td class='imgTd' ></td>")
															.addClass(classZ);
													trow1.append(tdata0);
													trow1.append(tdata1);
													trow1.append(tdata2);
													trow1.append(tdata3);
													table.append(trow1);
													$("." + val.sectionId)
															.append(table);

												}

												if(val.afterInsert!='' && val.afterInsert!=null &&  val.afterInsert!='null'){
													var insertEle = $(
															'[name="'
																	+ val.afterInsert
																	+ '"]')
															.parent().parent();
													$(dFlex).insertAfter(
															insertEle);
												}

											} else {
												var parentNames = $(
														"." + val.sectionId)
														.parent().parent()
														.find('td').length;
													if (parentNames <= 1) {
													$("." + val.sectionId)
															.parent().parent()
															.find('th').hide();
												} else {
													$("." + val.sectionId)
															.parent().parent()
															.find('th').show();
												}

												var table = $("<table>")
														.addClass(
																"table table-bordered");
												var tr = $("<tr/>").attr({
													id : "tDynmaicRow" + val.id
												});
												table.append(tr);
												$("." + val.sectionId).append(
														table);

											}

											if (val.parentDepenCode != ''
													&& val.parentDepenKey != '' && val.isMobile!='6') {
												if (val.parentDepenKey
														.indexOf(",") >= 0
														&& val.parentDepenCode
																.indexOf(",") < 0) {
													var values = val.parentDepenKey
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = val.parentDepenCode
																				+ "_"
																				+ e
																						.trim();
																		trow1
																				.addClass("hide"
																						+ " par"
																						+ val.parentDepenCode);
																		trow1
																				.addClass(classN);
																	});
												} else if (val.parentDepenKey
														.indexOf(",") >= 0
														&& val.parentDepenCode
																.indexOf(",") >= 0) {
													var values = val.parentDepenKey
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var depCode = val.parentDepenCode
																				.split(",");
																		$
																				.each(
																						depCode,
																						function(
																								j,
																								f) {
																							var classN = f
																									.trim()
																									+ "_"
																									+ e
																											.trim();
																							trow1
																									.addClass("hide"
																											+ " par"
																											+ f
																													.trim());
																							trow1
																									.addClass(classN);
																						});
																	});
												} else if (val.parentDepenKey
														.indexOf(",") < 0
														&& val.parentDepenCode
																.indexOf(",") >= 0) {
													var values = val.parentDepenCode
															.split(",");
													$
															.each(
																	values,
																	function(i,
																			e) {
																		var classN = e
																				.trim()
																				+ "_"
																				+ val.parentDepenKey;
																		trow1
																				.addClass("hide"
																						+ " par"
																						+ e
																								.trim());
																		trow1
																				.addClass(classN);
																	});
												} else {
													var classN = val.parentDepenCode
															+ "_"
															+ val.parentDepenKey;
													trow1
															.addClass("hide"
																	+ " par"
																	+ val.parentDepenCode);
													trow1.addClass(classN);
												}
											}

										});
						$(v.images)
						.each(
								function(key, val) {
									if(val.isImage==1){
										 jQuery(".imgHead").removeClass("hide");
										 jQuery(".imgTd").removeClass("hide");
								
									}
								});
						v.groups.sort(GetSortOrder("typez"));
						$(v.groups).each(
								function(key, val) {

									if (val.compoType != 10) {

										$("#tDynmaicRow" + val.refId).append(
												$("<td/>").addClass(
														val.compoCode).html(
														val.compoName));
									}
								});

						setDynamicFieldDetailValues();

					});
	
	function getBranchIdDyn(){
		return null;
	}

}



function GetSortOrder(prop) {  
    return function(a, b) {  
        if (a[prop] > b[prop]) {  
            return 1;  
        } else if (a[prop] < b[prop]) {  
            return -1;  
        }  
        return 0;  
    }  
    
    

   
}
   

function setDynamicFieldDetailValuesByTxnTypeOth(txnType,id) {
	var dataa = {};
	var url = "";
	var jsonData = "";

	dataa = {
		selectedObject : id,
		txnTypez : txnType
	}
	//alert(JSON.stringify(dataa));
	url = "farmer_populateDynmaicFieldValuesByTxnId.action";

	$.ajax({
		type : "POST",
		async : false,
		data : dataa,
		url : url,
		success : function(result) {
		jsonData = result;

		}
	});
	//alert(JSON.stringify(jsonData));
	$(jsonData)
			.each(
					function(k, v) {

						$(v.fields)
								.each(
										function(key, val) {
											
											$("." + val.code)
											.text(val.dispName);	

											$(".score"+val.code ).text(val.score);
											$(".percentage"+val.code ).text(val.percentage);
											
											if(val.isAct=='3'){
												$(".acPlanDet"+val.code ).text(val.actPlan);
												$(".deadline"+val.code ).text(val.deadline);
												$(".actStatus"+val.code ).text(val.actStatus);
												$(".grade"+val.code ).text(val.grade);
											}
											
											
											if(val.name.indexOf(",")<0){
											var classN = val.code + "_"
													+ val.name;

											try {
												$('.' + classN).removeClass(
														"hide");
											} catch (e) {

											}
											}else{
												$((val.name.split(","))).each(function(k, v) {
													var classN = val.code + "_"
													+ v.trim();

											try {
												$('.' + classN).removeClass(
														"hide");
											} catch (e) {

											}
												});
											}
											
											/*
											 * if (val.isActPlan !=null) {
											 * 
											 * $(".acPlanDet" + val.code)
											 * .append( "<button type='button'
											 * class='btn btn-sm pull-right
											 * photo imgCenter photoModel'><i
											 * class='fa fa-picture-o'
											 * aria-hidden='true'></i></button>"); }
											 */
											//alert(val.photoCompoAvailable);
											if (val.photoCompoAvailable == "1") {
												var ids = val.photoIds;
												$(".imgTd" + val.code)
														.append("<img class='photoPdfImg' width='100%' height='auto' src='farmer_populateImageDynamic.action?imgId="
															+ ids  + "'/>");
														/*.append(
																"<button type='button' class='btn btn-sm pull-left'  onclick=enablePhotoModal('"
																		+ ids
																		+ "')><i class='fa fa-picture-o' aria-hidden='true'></i></button>")
														.append(
																"<button type='button' class='hide btn btn-sm pull-right photoPdf' style='margin-right:2%' onclick=letImg('"
																		+ ids
																		+ "','"
																		+ val.code
																		+ "')></button>")
														.append(
																"<button type='button' class='btn btn-sm pull-right'  onclick=popDownload('"
																				+ ids
																				+ "')><i class='fa fa-download' aria-hidden='true'></i></button>");
*/
											} else if (val.photoCompoAvailable == "2") {
												var ids = val.photoIds;

												$("." + val.code)
														.append(
																"<button type='button' class='btn btn-sm pull-right' style='margin-right:15%' onclick=playAudioFiles('vid"
																		+ val.code
																		+ "','"
																		+ val.componentType
																		+ "')><i class='fa fa-play-circle-o' aria-hidden='true'></i></button>")
														.append(
																" <video class='hide' id='vid"
																		+ val.code
																		+ "' preload='none' controls ><source  src='farmer_populateVideoPlay?imgId="
																		+ ids
																		+ "' type='video/mp4'></video>");

											}else if (val.componentType == "15") {
												$("." + val.code)
												.text("");
											
												if(val.name!=''){
													$(".plot" + val.code)
													.append("<input id='plotValue"+val.code+"' type='hidden' value="+val.name+"/>").append(
														"<button type='button' class='btn faMap' style='margin-right:15%' onclick=showFarmMap('"
																+ val.code
																+ "')><i class='fa fa-play-circle-o' aria-hidden='true'></i></button>")
												}else{
													$("." + val.code)
													.append(
															"<button type='button' class='btn no-latLonIcn' style='margin-right:15%' ><i class='fa fa-play-circle-o' aria-hidden='true'></i></button>")
												}
									
											}
											else if (val.photoCompoAvailable == "12") {
												
																/*"<button type='button' class='btn btn-sm pull-right' style='margin-right:15%' onclick=playAudioFiles('vid"
																		+ val.code
																		+ "','"
																		+ val.componentType
																		+ "')><i class='fa fa-play-circle-o' aria-hidden='true'></i></button>")
														.append(
																" <video class='hide' id='vid"
																		+ val.code
																		+ "' preload='none' controls ><source  src='farmer_populateVideoPlay?imgId="
																		+ ids
																		+ "' type='video/mp4'></video>");
*/
											}
											if(val.isAct==3 && val.subAns!=null && val.subAns!='' && val.subAns!=undefined && val.subAns!='[]'){
												$(".followDet"+val.code).append("<button type='button' class='btn btn-sm pull-right' style='margin-right:2%' onclick='showFollowUpDetails("+val.subAns+")'>Followup Details</button>")
											}
											
										
											
										});

						var tempRefId = new Array();
						v.groups.sort(GetSortOrder("typez")); 

						$(v.groups)
								.each(
										function(key, val) {

											if (tempRefId.indexOf(val.refId
													+ "-" + val.typez) == '-1') {
												tempRefId.push(val.refId + "-"
														+ val.typez);
												var rowId = ("#tDynmaicRow" + val.refId);
												var newRow = $("<tr/>");
												// alert( val.typez);
												
												$(newRow).attr("id",val.typez);
												$(rowId)
														.find("td")
														.each(
																function(k, v) {

																	var classzName = $(
																			this)
																			.prop(
																					"class")
																			+ "t"
																			+ val.typez;
																	var td = $(
																			"<td/>")
																			.attr(
																					{
																						class : classzName
																					});
																	newRow
																			.append(td);

																});
											
												$(rowId).parent()
														.append(newRow);
											}
										});

					});

	$(jsonData)
			.each(
					function(k, v) {
						var classzName = "";
						v.groups.sort(GetSortOrder("typez"));
						$(v.groups)
								.each(
										function(key, val) {
											classzName = "." + val.code + "t"
													+ val.typez;
											$(classzName).text(val.dispName);
											if (val.photoCompoAvailable == "1") {
												var ids = val.photoIds;
//alert(ids);
												$(classzName).append("<img  src='farmer_populateImageDynamic.action?imgId="
													+ ids  + "'/>");
														/*.append(
																"<button type='button' class='btn btn-sm pull-right photo photoModel' style='margin-right:2%' onclick=enablePhotoModal('"
																		+ ids
																		+ "')><i class='fa fa-picture-o' aria-hidden='true'></i></button>")
														.append(
																"<button type='button' class='hide btn btn-sm pull-right photoPdf' style='margin-right:2%' onclick=letImg('"
																		+ ids
																		+ "','"
																		+ val.code
																		+ "')></button>");*/
											}
										});
					});
}
