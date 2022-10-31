var finalOutPut_query;
var finalOutPut_selectedFieldsForGrid_th;
var jsonObj_filter_data = [];

var reportTitle;
var reportDescription;
var reportId_for_update;
var entity ;
var groupByField;
var selectedFields = new Array();
var selectedlabels = new Array();

function populateAvailableColumns() {
	jQuery.post("dynamicReport_populateAvailableColumns.action", {},
			function(availabeColumns) {
				insertOptions("optionTransfer_select_availableColumns", jQuery
						.parseJSON(availabeColumns));
			});
}

function insertOptions(ctrlName, jsonArr) {
	document.getElementById(ctrlName).length = 0;
	for (var i = 0; i < jsonArr.length; i++) {
		var backend_Value = {
			"field" : jsonArr[i].field,
			"table" : jsonArr[i].table,
			"alias" : jsonArr[i].alias,
			"label" : jsonArr[i].label,
			"isGroupBy" : jsonArr[i].isGroupBy,
			"filter" : jsonArr[i].filter,
			"dataType" : jsonArr[i].dataType
		};
		console.log(JSON.stringify(backend_Value));
		backend_Value = JSON.stringify(backend_Value);
		addOption(document.getElementById(ctrlName), jsonArr[i].label,
				backend_Value);
	}
}

function addOption(selectbox, text, value) {
	var optn = document.createElement("OPTION");
	optn.text = text;
	optn.value = value;
	selectbox.options.add(optn);
}

function makeFieldsSelectedOfOptionTransferById(id) {
	if ($("#" + id) != null) {
		$("#" + id).multiple = true;
		for (var x = 0; x < document.getElementById(id).options.length; x++) {
			document.getElementById(id).options[x].selected = true;
		}
	}
}

function generateQuery(rowId) {
	jsonObj_filter_data=[];
	var table_string = "";
	var selectedFieldsForGrid_th;
	var datatableId="jQueryGridTable";
	var filterTableId="insideFilterDiv";
	jsonObj_filter_data=[];
	 var tableId="tableId";
	 
	if(rowId=='0'){
		 selectedFields = new Array();
		 selectedlabels = new Array();
		 entity = "";
		 groupByField = "";
	makeFieldsSelectedOfOptionTransferById("optionTransfer_select_selectedColumns");
	var jsonArray = $("#optionTransfer_select_selectedColumns").val();

	for (i = 0; i < jsonArray.length; i++) {
		var jsonData = jQuery.parseJSON(jsonArray[i]);
		selectedFields.push(jsonData.field);
		selectedlabels.push(jsonData.label);
		if (jsonData.isGroupBy == '1') {
			groupByField = groupByField + (jsonData.field.split("AS")[0].trim()) + ",";
		}
		if (entity.indexOf(jsonData.table) < 0) {
			entity = entity + jsonData.table + ",";
		}

	}
	}else{
		tableId="filterTable_savedReports_outPut";
	   datatableId="savedReports_outPut";
	   filterTableId="insideFilterDiv_savedReports_outPut";
		selectedFields = selectedFieldsOfQuery;
	
	}
	var myTab = $("#"+tableId);

	if(myTab!=undefined){
	myTab.find('tr').each(function (key, val) {
		  var $tds = $(this).find('td');
		  var item_filter_data = {};
		  if($tds.eq(0).find(":selected").val()!=undefined){
			  
			  
			  
			  var field =  jQuery.parseJSON($tds.eq(0).find(":selected").val())
			  item_filter_data["field"] =field.filter;
			  item_filter_data["expression"] = $tds.eq(1).find(":selected").val();
			  if($tds.eq(2).find(".val2")!=undefined && $tds.eq(2).find(".val2").val()!=null && $tds.eq(2).find(".val2").val()!=undefined){
				  item_filter_data["value"] = $tds.eq(2).find(".val1").val()+" and "+$tds.eq(2).find(".val2").val();
			  }else{
				  item_filter_data["value"] = $tds.eq(2).find(".val1").val();
			  }
			 
			  item_filter_data["andOr"] = $tds.eq(3).find(":selected").val();
			  item_filter_data["fieldDataType"] = field.dataType;
			  jsonObj_filter_data.push(item_filter_data);
			  if(selectedFields instanceof Array){
			  if(!selectedFields.includes(field.field)){
				  selectedFields.push(field.field);
			  }
			  }else{
				  if (selectedFields.indexOf(field.field) < 0) {
					  selectedFields = selectedFields+","+field.field;
					}
				 
			  }
			  if (entity.indexOf(field.table) < 0) {
					entity = entity + field.table + ",";
				}
			  
			  if(isEmpty(item_filter_data["expression"])){
					alert("Please enter existing filter values ")
					return;
				}
			
			  if(item_filter_data["expression"]!='8' && item_filter_data["expression"]!='9'){
			  if(isEmpty(item_filter_data["value"])){
					alert("Please enter existing filter values rr")
					return;
				}
			  }
			  
			  if(isEmpty(item_filter_data["field"])){
					alert("Please enter existing filter values tt ")
					return;
				}
				
			  item_filter_data["expression"] =  expArray[item_filter_data["expression"]];
			
		
		  }
         
    });
	}
	
	jQuery
			.post(
					"dynamicReport_executeQueryAndgetValues.action",
					{
						entity : entity,
						selectedFields : selectedFields.toString(),
						selectedlabels : selectedlabels.toString(),
						groupByField : groupByField,
						filter_data : JSON.stringify(jsonObj_filter_data) 
					},
					function(gridData) {
var jssonRetData =  $.parseJSON(gridData);
						json =jssonRetData.list;
						 var imgNode = $('#'+filterTableId);
						deleteHTML(datatableId);
						var div = $('<div/>').attr({
							id : datatableId,
						});
						finalOutPut_query = jssonRetData.query;
						var headerDiv = $("<div/>").addClass(
								"formContainerWrapper");
						headerDiv
								.append("<h2>Dynamic Report <div class='pull-right'></div>  </h2>");
						headerDiv
								.append("<div id='exportButtons_dynamicReport' style='float:right;'></div>");
						$("#"+datatableId).append(headerDiv);
						finalOutPut_selectedFieldsForGrid_th = selectedlabels.toString();
						selectedFields = selectedFields;
						jsonObj_filter_data = jsonObj_filter_data
						if (isEmpty(jsonObj_filter_data)) {
							var insideFilterDiv = $('<div/>').attr({
								id : filterTableId
							});
							renderFilterFields();
							$("#"+datatableId).append(insideFilterDiv);
						}else if(!$(imgNode).is(':empty') ){
							$("#"+datatableId).append(imgNode);
						}

						selectedFieldsForGrid_th = selectedFields.slice();
						
						$("#"+datatableId)
								.append(
										"<div id='exportButtons_dynamicReport' style='float:right;'></div>");

						var table = $("<table/>").addClass(
								"table table-bordered dataTableTheme").attr({
							id : "grid"

						});
						var thead = $("<thead/>");
						var tr = $("<tr/>");

						var headerFields_changedLabels;

						//finalOutPut_selectedFieldsForGrid_th = selectedFieldsForGrid_th;

						for ( var obj in selectedlabels) {
							tr.append($("<th/>").append(
									selectedlabels[obj]));
							thead.append(tr);
						}

						table.append(thead);

						var tbody = $("<tbody/>");

						for (var i = 0; i < json.length; i++) {
							item = {}
							var tr2 = $("<tr/>");

							for (var j = 0; j < selectedlabels.length; j++) {
								item[selectedlabels[j]] = json[i][selectedlabels[j]];
								tr2.append($("<td/>").append(
										item[selectedlabels[j]]));
								tbody.append(tr2);
							}

						}

						table.append(tbody);
						$("#"+datatableId).append(table);
						var table = $('#grid').DataTable({
							"sScrollX" : "100%",
							"sScrollXInner" : "110%",
							"scrollY" : "350px",
						});
						$('#'+datatableId).addClass("dataTableTheme");
						var buttons = new $.fn.dataTable.Buttons(table, {
							buttons : [ {
								extend : 'excel',
								title : 'Dynamic Report',
								messageTop : '',
								messageBottom : '',

							}, {
								extend : 'pdf',
								title : 'Dynamic Report',
								messageTop : '',
								messageBottom : '',

							} ]
						}).container().appendTo(
								$('#exportButtons_dynamicReport'));
						$("#grid").addClass("dataTableTheme");
						$("#"+datatableId).addClass(
								"appContentWrapper marginBottom");

						var modal = document.getElementById('loading_msg');
						modal.style.display = "none";
						window.scrollBy(10, 800);

						/*if (!isEmpty(jsonObj_filter_data)) {
							renderFilterFieldsWithAnswers();
						}*/

					});

}

function renderFilterFieldsWithOutRows() {

	deleteHTML("tableId");

	var add_btn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-sts',
		onclick : 'createNewRow("tableId")',
		style : 'margin-left:1%;padding-top:2px;padding-bottom:2px'
	});

	$(add_btn).text("Add");

	var table = $("<table/>").addClass("table table-bordered").attr({
		id : "tableId"
	});

	var thead = $("<thead/>");

	var tr = $("<tr/>");
	tr.append($("<th/>").append("Field"));
	tr.append($("<th/>").append("Condition"));
	tr.append($("<th/>").append("Value"));
	if (jsonObj_filter_data.length > 1) {
		tr.append($("<th/>").append("AND/OR"));
	}

	tr.append($("<th/>").append("Delete "));

	table.append(thead);
	thead.append(tr);

	var tbody = $("<tbody/>");
	table.append(tbody);
	$('#insideFilterDiv').append(add_btn);
	$('#insideFilterDiv').append(table);

}

function populateExpressionDropDown(obj, row_DOM) {

	var id = $(row_DOM).prop('id');
	var row = document.getElementById(id);
	row.deleteCell(1);

	var field = jQuery.parseJSON(obj)

	var conditions = [];

	if (field.dataType == "3" || field.dataType == "2") {
		conditions = [ '1|Equal to', '2|Not Equal to','3|Greater than',
						'4|Greater than or equal to', '5|Less than',
						'6|Less than or equal to','7|Between'  ];
	} else if (field.dataType == "4") {
		conditions = [ '1|Equal to', '2|Not Equal to','3|Greater than',
						'4|Greater than or equal to', '5|Less than',
						'6|Less than or equal to','7|Between' , '8|Is empty', '9|Is not empty' ];
	} else {
		conditions = ['1|Equal to', '2|Not Equal to','3|Greater than',
						'4|Greater than or equal to', '5|Less than','10|contains',
						'6|Less than or equal to' ,'8|Is empty', '9|Is not empty'];
	}

	// if you change anything in above conditions array should apply the same
	// changes in generateQuery() function

	var select_expression = createDropDown_FilterExpression(conditions);

	expressionTd = row.insertCell(1);

	expressionTd.innerHTML = $(select_expression).prop('outerHTML');
	$('.select2').select2();

	// refresh 3rd cell (value cell)

	row.deleteCell(2);

	var textBox = $('<input/>').attr({
		type : 'text',
		class : 'val1'
	});

	newCell = row.insertCell(2);
	var htmlStr = $(textBox).prop('outerHTML');
	newCell.innerHTML = htmlStr;

}

function renderFilterFields() {
	var add_btn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-sts',
		onclick : 'createNewRow("tableId")',
		style : 'margin-left:1%;padding-top:2px;padding-bottom:2px'
	});

	$(add_btn).text("Add");
	var selectHtmlString = "<select class='select2' onchange ='populateExpressionDropDown(this.value,$(this).parent().parent());'><option value=''>Select </option>";
	jQuery
			.post(
					"dynamicReport_populateAvailableColumns.action",
					{},
					function(availabeColumns) {
						var jsonArr = jQuery.parseJSON(availabeColumns);
						for (var i = 0; i < jsonArr.length; i++) {
							selectHtmlString = selectHtmlString
									+ "<option value='"
									+ JSON.stringify(jsonArr[i]) + "'>"
									+ jsonArr[i].label + "</option>"
						}
						selectHtmlString = selectHtmlString + '</select>';

						deleteHTML("tableId");
						var table = $("<table/>").addClass(
								"table table-bordered").attr({
							id : "tableId"
						});

						var thead = $("<thead/>");

						var tr = $("<tr/>");
						tr.append($("<th/>").append("Field"));
						tr.append($("<th/>").append("Condition"));
						tr.append($("<th/>").append("Value"));
						tr.append($("<th/>").append("Delete "));
						table.append(thead);
						thead.append(tr);

						var tbody = $("<tbody/>");

						var tr2 = $("<tr id='tableId$1'/>");
						tr2.append($("<td  />").append(selectHtmlString));

						var conditions = [];
						var select_expression = createDropDown_FilterExpression(conditions);
						tr2.append($("<td  />").append(select_expression));

						var textBox = $('<input/>').attr({
							type : 'text',
								class : 'val1'
						});
						tr2.append($("<td  />").append(textBox));

						var del_btn = $('<button/>')
								.attr(
										{
											type : 'button',
											class : 'btn btn-sts',
											onclick : 'deleteRowAndValidateParentRow($(this).parent().parent(),"tableId");',
											style : 'margin-left:1%;padding-top:2px;padding-bottom:2px',
										});
						$(del_btn).text("Delete");
						tr2.append($("<td/>").append(del_btn));

						tbody.append(tr2);
						table.append(tbody);

						$('#insideFilterDiv').append(add_btn);
						$('#insideFilterDiv').append(table);
						$('.select2').select2();

					});
}

function createDropDown_FilterExpression(arr) {

	var select = $('<select/>')
			.attr(
					{
						class : "select2",
						// id : id,
						onchange : "enableDateRangePicker(this.value,$(this).parent().parent());"
					});

	var option = "<option value=''>Select </option>";
	$(select).append(option);

	for (var i = 0; i < arr.length; i++) {

		var option = "<option value='" + arr[i].split("|")[0] + "'>" + arr[i].split("|")[1] + "</option>";
		$(select).append(option);

	}

	return select;
}

function enableDateRangePicker(expression, row_DOM) {

	var first_td = $(row_DOM).find('td')[0];
	var field = $(first_td).find(":selected").val();

	var fieldName = jQuery.parseJSON(field)

	if (fieldName.dataType == "4") {
		if (expression == "7") {

			var id = $(row_DOM).prop('id');
			var row = document.getElementById(id);
			row.deleteCell(2);

			var textBox1 = $('<input/>').attr({
				type : 'text',
				class : 'datePickerTextBox val1'

			});

			var textBox2 = $('<input/>').attr({
				type : 'text',
				class : 'datePickerTextBox val2'
			});

			newCell = row.insertCell(2);
			var htmlStr = $(textBox1).prop('outerHTML') + "&nbsp to &nbsp"
					+ $(textBox2).prop('outerHTML');
			newCell.innerHTML = htmlStr;

			$('.datePickerTextBox').datepicker({
				autoclose : true,
				todayHighlight : true,
				format : 'yyyy-mm-dd',
			}).datepicker('update', new Date());

		} else if (expression == '9' || expression == '8') {

			var id = $(row_DOM).prop('id');
			var row = document.getElementById(id);
			$('#'+id+' td:eq(2)').html('');
			

		} else {

			var id = $(row_DOM).prop('id');
			var row = document.getElementById(id);
			row.deleteCell(2);

			var textBox1 = $('<input/>').attr({
				type : 'text',
				class : 'datePickerTextBox val1'

			});

			newCell = row.insertCell(2);
			var htmlStr = $(textBox1).prop('outerHTML');
			newCell.innerHTML = htmlStr;

			$('.datePickerTextBox').datepicker({
				autoclose : true,
				todayHighlight : true,
				format : 'yyyy-mm-dd',
			}).datepicker('update', new Date());

		}
	} else {
		if (expression == '8' || expression == '9') {
		
			var id = $(row_DOM).prop('id');
			var row = document.getElementById(id);
			$(row).find("td:eq(2)").html('');
		
		} else {
			if (expression == "7") {

				var id = $(row_DOM).prop('id');
				var row = document.getElementById(id);
				row.deleteCell(2);

				var textBox1 = $('<input/>').attr({
					type : 'text',
					class : 'val1'
					

				});

				var textBox2 = $('<input/>').attr({
					type : 'text',
					class : 'val2'
					
				});

				newCell = row.insertCell(2);
				var htmlStr = $(textBox1).prop('outerHTML') + "&nbsp and  &nbsp"
						+ $(textBox2).prop('outerHTML');
				newCell.innerHTML = htmlStr;

			
			} else{

			var id = $(row_DOM).prop('id');
			var row = document.getElementById(id);
			row.deleteCell(2);

			var textBox1 = $('<input/>').attr({
				type : 'text',
				class :'val1'
			});

			newCell = row.insertCell(2);
			var htmlStr = $(textBox1).prop('outerHTML');
			newCell.innerHTML = htmlStr;
			}
		}
	}

}

function renderFilterFieldsWithAnswers() {
	renderFilterFieldsWithOutRows();

	jQuery
			.post(
					"dynamicReport_populateAvailableColumns.action",
					{},
					function(availabeColumns) {

						var filterRowCount = 1;

						jQuery
								.each(
										jsonObj_filter_data,
										function(index, tableRow_filter) {
											console
													.log(tableRow_filter.field)
											console
													.log(tableRow_filter.expression)
											console.log(tableRow_filter.value)
											console.log(tableRow_filter.andOr)
											console
													.log("------------------------------")

											var table = document
													.getElementById('tableId');
											var newRow = table
													.insertRow(table.rows.length);
											newRow.id = "tableId$"
													+ ((table.rows.length));

											var fieldNameCell = newRow
													.insertCell(0);
											var jsonArr = jQuery
													.parseJSON(availabeColumns);
											var selectHtmlString = "<select class='select2' onchange ='populateExpressionDropDown(this.value,$(this).parent().parent());'><option value=''>Select </option>";
											for (var i = 0; i < jsonArr.length; i++) {

												if (jsonArr[i].columnLabel == tableRow_filter.field) {
													selectHtmlString = selectHtmlString
															+ "<option value='"
															+ JSON.stringify(jsonArr[i])
															+ "' selected>"
															+ jsonArr[i].label
															+ "</option>"
												} else {
													selectHtmlString = selectHtmlString
													+ "<option value='"
													+ JSON.stringify(jsonArr[i])
													+ "' selected>"
													+ jsonArr[i].label
													+ "</option>"
												}

											}

											selectHtmlString = selectHtmlString
													+ '</select>';
											fieldNameCell.innerHTML = selectHtmlString;

											var conditionCell = newRow
													.insertCell(1);
											var conditions = [];
											if (tableRow_filter.fieldDataType == "3" || tableRow_filter.fieldDataType == "2") {
												conditions = [ '1|Equal to', '2|Not Equal to','3|Greater than',
																'4|Greater than or equal to', '5|Less than',
																'6|Less than or equal to','7|Between'  ];
											} else if (tableRow_filter.fieldDataType == "4") {
												conditions = [ '1|Equal to', '2|Not Equal to','3|Greater than',
																'4|Greater than or equal to', '5|Less than',
																'6|Less than or equal to','7|Between' , '8|Is empty', '9|Is not empty' ];
											} else {
												conditions = ['1|Equal to', '2|Not Equal to','3|Greater than',
																'4|Greater than or equal to', '5|Less than',
																'6|Less than or equal to' ,'8|Is empty', '9|Is not empty'];
											}
											

											var select_expression = createDropDown_FilterExpression_withDefaultValue(
													conditions,
													tableRow_filter.expression);
											conditionCell.innerHTML = $(
													select_expression).prop(
													'outerHTML');
											if (tableRow_filter.fieldDataType == "4") {
												if (tableRow_filter.expression == "7") {
													var dateArray = (tableRow_filter.value)
															.split("and");
													dateArray[0] = dateArray[0]
															.replace('"', "");
													dateArray[1] = dateArray[1]
															.replace('"', "");
													dateArray[0] = dateArray[0]
															.replace('"', "");
													dateArray[1] = dateArray[1]
															.replace('"', "");
													dateArray[0] = dateArray[0]
															.trim();
													dateArray[1] = dateArray[1]
															.trim();

													var valueCell = newRow
															.insertCell(2);

													var textBox1 = $('<input/>')
															.attr(
																	{
																		type : 'text',
																		class : 'datePickerTextBox',
																		value : dateArray[0]

																	});

													var textBox2 = $('<input/>')
															.attr(
																	{
																		type : 'text',
																		class : 'datePickerTextBox',
																		value : dateArray[1]
																	});

													valueCell.innerHTML = $(
															textBox1).prop(
															'outerHTML')
															+ "&nbsp to &nbsp"
															+ $(textBox2)
																	.prop(
																			'outerHTML');

												} else if (tableRow_filter.expression == "8"
														|| tableRow_filter.expression == "9") {

													var valueCell = newRow
															.insertCell(2);

												} else {

													var valueCell = newRow
															.insertCell(2);

													var textBox1 = $('<input/>')
															.attr(
																	{
																		type : 'text',
																		class : 'datePickerTextBox',
																		value : tableRow_filter.value

																	});

													valueCell.innerHTML = $(
															textBox1).prop(
															'outerHTML');

												}

												$('.datePickerTextBox')
														.datepicker(
																{
																	autoclose : true,
																	todayHighlight : true,
																	format : 'yyyy-mm-dd',
																});

											} else {

												if (tableRow_filter.expression == "8"
														|| tableRow_filter.expression == "9") {

													var valueCell = newRow
															.insertCell(2);

												} else {

													var valueCell = newRow
															.insertCell(2);
													var textBox = $('<input/>')
															.attr(
																	{
																		type : 'text',
																		value : tableRow_filter.value
																	});
													valueCell.innerHTML = $(
															textBox).prop(
															'outerHTML');

												}

											}

											if (jsonObj_filter_data.length > 1) {
												var and_or_cell = newRow
														.insertCell(3);
												if (filterRowCount < jsonObj_filter_data.length) {

													var and_or_value = tableRow_filter.andOr;

													if (and_or_value == "AND") {
														and_or_cell.innerHTML = '<select class="select2" ><option value="AND" selected>AND </option><option value="OR">OR </option></select>';
													} else if (and_or_value == "OR") {
														and_or_cell.innerHTML = '<select class="select2" ><option value="AND" >AND </option><option value="OR" selected>OR </option></select>';
													}
												}

											}

											var controlBtn;
											if (jsonObj_filter_data.length > 1) {
												controlBtn = newRow
														.insertCell(4);
											} else {
												controlBtn = newRow
														.insertCell(3);
											}

											var del_btn = $('<button/>')
													.attr(
															{
																type : 'button',
																class : 'btn btn-sts',
																onclick : 'deleteRowAndValidateParentRow($(this).parent().parent(),"tableId");',
																style : 'margin-left:1%;padding-top:2px;padding-bottom:2px',
															});
											$(del_btn).text("Delete");
											controlBtn.innerHTML = $(del_btn)
													.prop('outerHTML');

											$('.select2').select2();
											filterRowCount = filterRowCount + 1;

										});
					});
}

function deleteHTML(elementID) {
	if (document.getElementById(elementID) != null) {
		document.getElementById(elementID).innerHTML = "";

	}

}

function createNewRow(tableId) {
	var myTab = $("#"+tableId);
	var hit=true;
	if(myTab!=undefined){
	myTab.find('tr').each(function (key, val) {
		  var $tds = $(this).find('td');
		  var item_filter_data = {};
		  if($tds.eq(0).find(":selected").val()!=undefined && $tds.eq(0).find(":selected").val()!=''){
			  
				  var field =  jQuery.parseJSON($tds.eq(0).find(":selected").val())
			  item_filter_data["field"] =field.filter;
			  item_filter_data["expression"] = $tds.eq(1).find(":selected").val();
			  if($tds.eq(2).find(".val2")!=undefined && $tds.eq(2).find(".val2").val()!=null && $tds.eq(2).find(".val2").val()!=undefined){
				  item_filter_data["value"] = $tds.eq(2).find(".val1").val()+" and "+$tds.eq(2).find(".val2").val();
			  }else{
				  item_filter_data["value"] = $tds.eq(2).find(".val1").val();
			  }
			 
			  item_filter_data["andOr"] = $tds.eq(3).find(":selected").val();
			  item_filter_data["fieldDataType"] = field.dataType;
			  jsonObj_filter_data.push(item_filter_data);
			  if(selectedFields instanceof Array){
			  if(!selectedFields.includes(field.field)){
				  selectedFields.push(field.field);
			  }
			  }else{
				  if (selectedFields.indexOf(field.field) < 0) {
					  selectedFields = selectedFields+","+field.field;
					}
				 
			  }
			  if (entity.indexOf(field.table) < 0) {
					entity = entity + field.table + ",";
				}
			  if(isEmpty(item_filter_data["expression"])){
					alert("Please enter existing filter values ");
					hit=false;
					return;
				}
			
			  if(item_filter_data["expression"]!='8' && item_filter_data["expression"]!='9'){
			  if(isEmpty(item_filter_data["value"])){
					alert("Please enter existing filter values ");
					hit=false;
					return;
				}
			  }
			
			
		  }else{
			 if( $tds.eq(0).find(":selected").val()!=undefined && $tds.eq(0).find(":selected").val()==''){
					alert("Please enter existing filter values ");
					hit=false;
					return;
			 }
				
		  }
         
    });
	if(hit){
	createAnotherRow(tableId);
	}
	}
		
	

}

function createAnotherRow(tableId) {
	var table = document.getElementById(tableId);
	if (table != null) {
		if (table.rows.length == 2) {

			var objCells = table.rows.item(0).cells;
			if (objCells.length == 4) {
				table.rows.item(0).insertCell(3).outerHTML = "<th>AND/OR</th>";
				// objCells.item(3).innerHTML = "ANDexecuteSavedReport/OR";

				var objCells = table.rows.item(1).cells;
				table.rows.item(1).insertCell(3);
				objCells.item(3).innerHTML = '<select class="select2" ><option value="AND">AND </option><option value="OR">OR </option></select>';

			}
		} else {

			if (table.rows.length > 2) {
				var objCells = table.rows.item(Number(table.rows.length) - 1).cells;
				if (isEmpty(objCells.item(3).innerHTML)) {
					objCells.item(3).innerHTML = '<select class="select2" ><option value="AND">AND </option><option value="OR">OR </option></select>';
				}
			}

		}
	}

	if (!isEmpty(table.rows.item(Number(table.rows.length) - 1).id)) {
		var previousRowId = table.rows.item(Number(table.rows.length) - 1).id;
		var temp = previousRowId.split("$");
		previousRowId = temp[1];
	} else {
		var previousRowId = 1;
	}

	var newRow = table.insertRow(table.rows.length);
	newRow.id = "tableId$" + (Number(previousRowId) + 1);

	var fieldNameCell = newRow.insertCell(0);

	var selectHtmlString = "<select class='select2' onchange ='populateExpressionDropDown(this.value,$(this).parent().parent());'><option value=''>Select </option>";
	jQuery
			.post(
					"dynamicReport_populateAvailableColumns.action",
					{},
					function(availabeColumns) {
						var jsonArr = jQuery.parseJSON(availabeColumns);
						for (var i = 0; i < jsonArr.length; i++) {
							selectHtmlString = selectHtmlString
									+ "<option value='" + JSON.stringify(jsonArr[i]) + "'>"
									+ jsonArr[i].label + "</option>"
						}
						selectHtmlString = selectHtmlString + '</select>';
						fieldNameCell.innerHTML = selectHtmlString;

						var conditionCell = newRow.insertCell(1);
						var select_expression = createDropDown_FilterExpression("");
						conditionCell.innerHTML = $(select_expression).prop(
								'outerHTML');

						var valueCell = newRow.insertCell(2);
						var textBox = $('<input/>').attr({
							type : 'text'
						});
						valueCell.innerHTML = $(textBox).prop('outerHTML');

						if (table.rows.length > 2) {
							var and_or_cell = newRow.insertCell(3);
						}

						var controlBtn;
						if (table.rows.length == 2) {
							controlBtn = newRow.insertCell(3);
						} else {
							controlBtn = newRow.insertCell(4);
						}

						var del_btn = $('<button/>')
								.attr(
										{
											type : 'button',
											class : 'btn btn-sts',
											onclick : 'deleteRowAndValidateParentRow($(this).parent().parent(),"'+tableId+'");',
											style : 'margin-left:1%;padding-top:2px;padding-bottom:2px',
										});
						$(del_btn).text("Delete");
						controlBtn.innerHTML = $(del_btn).prop('outerHTML');

						$('.select2').select2();

					});

}



function populateModalForSave(){
	/*if(isEmpty(finalOutPut_query)){
		alert("please create report before save")
		return;
	}else{*/
		var modal = document.getElementById('myModal');
		modal.style.display = "block";
		
		var span = document.getElementsByClassName("close")[0];
		span.onclick = function() {
		    modal.style.display = "none";
		}
	/*}*/
	
	
	
}

function saveReport(){
	console.log("save --> "+finalOutPut_query)
	var title = $("#title").val();
	var des = $("#des").val();
	
	if(isEmpty(title) || isEmpty(des)){
		alert("Please enter title and description")
		return;
	}
jQuery.post("dynamicReport_saveDynamicReport.action", {
		reportTitle : title,
		reportDescription : des,
		entity : entity,
		query :finalOutPut_query,
		selectedFields : selectedFields.toString(),
		selectedlabels : selectedlabels.toString(),
		groupByField : groupByField,
		header_fields : finalOutPut_selectedFieldsForGrid_th.toString(),
		filter_data : JSON.stringify(jsonObj_filter_data) 
		
	}, function(availabeColumns) {
		var modal = document.getElementById('myModal');
		modal.style.display = "none";
		
		
		
		$('#savedReports_table').DataTable().destroy();	
		$("#savedReports").empty();
		populateSavedReports();
	
		$('[href="#tabs-2"]').tab('show');
		 window.scrollTo(500, 0);
		
		 refreshPage();
	});
	
}

function refreshPage(){
	$("#title").val("");
	$("#des").val("");
	populateAvailableColumns();
	insertOptions("optionTransfer_select_selectedColumns","");
	$('#filterDiv').find('.gridly').find('.filterEle').remove();
	$('#filterDiv').find('#tableId').remove();
	$("#filterDiv").hide();
	
	$("#jQueryGridTable").find(".formContainerWrapper").remove();
	$("#jQueryGridTable").find("#grid").remove();
	$("#jQueryGridTable").find("#grid_wrapper").remove();
	$("#jQueryGridTable").removeClass("appContentWrapper marginBottom");

	finalOutPut_selectedFieldsForGrid_th = "";
	$("#insideFilterDiv").remove();
}


function populateSavedReports(){
	var query = "select * from saved_dynamic_report order by id desc";
	var selectedFields = ['id','Title','Description','query','header_fields','filter_data','entity','fields_selected','GROUP_BY_FIELD'];
	jQuery.post("dynamicReport_populateSavedReports.action", {
		query : query,
		selectedFields : 'id,Title,Description,query,header_fields,filter_data,entity,fields_selected,GROUP_BY_FIELD'
	}, function(gridData) {
		
		json = $.parseJSON(gridData);
		
		
		
		var headerDiv =  $("<div/>").addClass("formContainerWrapper");
		headerDiv.append("<h2>saved Reports<div class='pull-right'></div>  </h2>");
		headerDiv.append("<div id='exportButtons_savedReports' style='float:right;'></div>");
		$("#savedReports").append(headerDiv);
		
		var table = $("<table/>").addClass("table table-bordered")
						.attr({
							id : "savedReports_table"
							
						});
				var thead = $("<thead/>");
				var tr = $("<tr/>");
				
				
				
				
				for (var obj in selectedFields) {
					if(selectedFields[obj] == 'Title' || selectedFields[obj] == 'Description' ){
						tr.append($("<th/>").append(selectedFields[obj]));
					  	thead.append(tr);
					}
				}
			
				tr.append($("<th/>").append("Action"));
			  	thead.append(tr);
				
				table.append(thead);
				
				var tbody = $("<tbody/>");
				
				
				for (var i = 0; i < json.length; i++) {
					item = {}
					var rowId = json[i][selectedFields[0]];
					var title = json[i][selectedFields[1]];
					var headerFields = json[i][selectedFields[4]];
					var query = json[i][selectedFields[3]];
					var filter_data = json[i][selectedFields[5]];
					var entity = json[i][selectedFields[6]];
					var fields = json[i][selectedFields[7]];
					var grpByF = json[i][selectedFields[8]];
					
					var tr2 = $("<tr/>").attr({
						id : rowId,
						class : headerFields,
						'data-value' : query,
						'data-filter' : filter_data
						
					});
					
				
					for (var j = 0; j < selectedFields.length; j++) {
						if(selectedFields[j] == 'Title' || selectedFields[j] == 'Description' ){
							item[selectedFields[j]] = json[i][selectedFields[j]];
							tr2.append($("<td/>").append(item[selectedFields[j]] ));
							tbody.append(tr2);
						}
					}
				
					var view_btn = $('<button/>').attr({
						type : 'button',
						class : 'btn btn-success',
						value : rowId,
						onclick : "executeSavedReport('"+title+"','"+rowId+"','"+headerFields+"','"+filter_data+"','"+entity+"','"+fields+"','"+query+"','"+grpByF+"');",
						style : "margin-left:1%;padding-top:2px;padding-bottom:2px"
						
						
					});
					$(view_btn).text("View");
					
					var edit_btn = $('<button/>').attr({
						type : 'button',
						class : 'btn btn-warning',
						value : rowId,
						onclick : 'editExistingSavedReport(this.value);',
						style : "margin-left:1%;padding-top:2px;padding-bottom:2px"
					});
					$(edit_btn).text("Edit");
					
					var delete_btn = $('<button/>').attr({
						type : 'button',
						class : 'btn btn-danger',
						value : rowId,
						onclick : "deleteExistingSavedReport('"+rowId+"');",
						style : "margin-left:1%;padding-top:2px;padding-bottom:2px"
					});
					$(delete_btn).text("Delete");
					
					var actionTd = $("<td/>");
					actionTd.append(view_btn);
					actionTd.append(edit_btn);
					actionTd.append(delete_btn);
					tr2.append(actionTd);
					tbody.append(tr2);
					
				}
				
				
				
				table.append(tbody);
				$("#savedReports").append(table);
				var table = $('#savedReports_table').DataTable( {
					"ordering": false
					});
			    /* var buttons = new $.fn.dataTable.Buttons(table, {
			    	  buttons: [
				                 {
				                     extend: 'excel',
				                     Title: 'Dynamic Report',
				                     messageTop: '',
				                     messageBottom: '',
				                     
				                 },
				                 {
				                	 extend : 'pdfHtml5',
				                     title : function() {
				                         return "Dynamic Report'";
				                     },
				                     orientation : 'landscape',
				                     pageSize : 'A0',
				                     text : '<i class="fa fa-file-pdf-o"> PDF</i>',
				                     titleAttr : 'PDF'
				                  }
				             ]
			   }).container().appendTo($('#exportButtons_savedReports'));*/
			     $("#savedReports_table").addClass("dataTableTheme");
				$("#savedReports").addClass("appContentWrapper marginBottom");
	});
	
}

function executeSavedReport(title,rowId,headerFields,filter_data,entityUpdate,fields,query,groupByF){
	selectedFieldsOfQuery= fields;
	entity = entityUpdate;
	groupByField = groupByF;
	reportId_for_update = rowId;
	selectedlabels = headerFields.split(",");
	finalOutPut_selectedFieldsForGrid_th = headerFields;
	var modal = document.getElementById('loading_msg');
	modal.style.display = "inline-block";
	console.log(selectedlabels);
	filter_data = $.parseJSON(filter_data);
	$("#savedReports_outPut").removeClass("appContentWrapper marginBottom");
	deleteHTML("savedReports_outPut");
	
	jQuery.post("dynamicReport_populateSavedReports.action", {
		query : query,
		selectedFields : headerFields
	}, function(gridData) {

		json = $.parseJSON(gridData);
		var headerDiv =  $("<div/>").addClass("formContainerWrapper");
		headerDiv.append("<h2>"+title+"<div class='pull-right'><a class='aCollapse'><i style='color:black;' class='fa fa-close' onclick=closeSavedReportOutput();></i></a></div> </h2>");
		//headerDiv.append("<div id='exportButtons_savedReports_outPut' style='float:right;'></div>");
		$("#savedReports_outPut").append(headerDiv);
		if(!isEmpty(filter_data)){
			var insideFilterDiv_savedReports_outPut = $('<div/>').attr({
				id : "insideFilterDiv_savedReports_outPut",
				class : "filterDivStyle"
			});
			
			$("#savedReports_outPut").append(insideFilterDiv_savedReports_outPut);
			
			
			renderFilterFieldsWithAnswers_savedReports_outPut('insideFilterDiv_savedReports_outPut',filter_data);
			
			
		}
		
		$("#savedReports_outPut").append("<div id='exportButtons_savedReports_outPut' style='float:right;'></div>");
		
		var table = $("<table/>").addClass("table table-bordered")
						.attr({
							id : "savedReports_outPut_table"
							
							});
				var thead = $("<thead/>");
				var tr = $("<tr/>");
				
				
				
				
				for (var obj in selectedlabels) {
					tr.append($("<th/>").append(selectedlabels[obj]));
					  thead.append(tr);
				}
			
				table.append(thead);
				
				var tbody = $("<tbody/>");
				
				
				for (var i = 0; i < json.length; i++) {
					item = {}
					var tr2 = $("<tr/>");
					
				
					for (var j = 0; j < selectedlabels.length; j++) {
						item[selectedlabels[j]] = json[i][selectedlabels[j]];
						tr2.append($("<td/>").append(item[selectedlabels[j]] ));
						tbody.append(tr2);
					}
				
					
				}
				
				
				table.append(tbody);
				$("#savedReports_outPut").append(table);
				var table = $('#savedReports_outPut_table').DataTable( {
					"sScrollX": "100%",
				    "sScrollXInner": "110%",
				    "scrollY": "350px"
				    
					});
				$('#savedReports_outPut_table').addClass("dataTableTheme")
			     var buttons = new $.fn.dataTable.Buttons(table, {
			    	  buttons: [
				                 {
				                     extend: 'excel',
				                     title: reportTitle,
				                     messageTop: '',
				                     messageBottom: '',
				                     
				                 },
				                 {
				                     extend: 'pdf',
				                     title: reportTitle,
				                     messageTop: '',
				                     messageBottom: '',
				                    
				                 }
				             ]
			   }).container().appendTo($('.dataTables_length'));
			     $("#savedReports_outPut_table").addClass("dataTableTheme");
			     $("#savedReports_outPut").addClass("appContentWrapper marginBottom ");
			     
			     modal.style.display = "none";
			     window.scrollBy(10, 800);
			     
	});


}


function editExistingSavedReport(currentRowId){
	
	var myTab = document.getElementById('savedReports_table');
	if(myTab != null){
		
		
		for (i = 1; i < myTab.rows.length; i++) {
				
			if(myTab.rows.item(i).id == currentRowId){
			 
				var objCells = myTab.rows.item(i).cells;
					
						
						for (var j = 0; j < objCells.length; j++) {
							//info.innerHTML = info.innerHTML + ' ' + objCells.item(j).innerHTML;
							if (j == 0) {
								var title = objCells.item(j).innerHTML;
								
								var textBox = $('<input/>').attr({
									type : 'text',
								});
								
								objCells.item(j).innerHTML = $(textBox).prop('outerHTML');
								$(objCells.item(j)).find("input").val(title);
							} else if (j == 1) {
								
								var description = objCells.item(j).innerHTML;
								var textBox = $('<input/>').attr({
									type : 'text',
								});
									
								objCells.item(j).innerHTML = $(textBox).prop('outerHTML');
								$(objCells.item(j)).find("input").val(description);
							
							} else if (j == 2) {
								
								var update_btn = $('<button/>').attr({
									type : 'button',
									class : 'btn btn-info',
									value : currentRowId,
									onclick : 'validate_report_titleAndDescription(this.value);',
									style : "margin-left:1%;padding-top:2px;padding-bottom:2px"
								});
								$(update_btn).text("Update");
								
								var cancel_btn = $('<button/>').attr({
									type : 'button',
									class : 'btn btn-sts',
									value : currentRowId,
									onclick : 'cancel_update("'+currentRowId+'","'+title+'","'+description+'")',
									style : "margin-left:1%;padding-top:2px;padding-bottom:2px"
								});
								$(cancel_btn).text("cancel");
								
								objCells.item(j).innerHTML = $(update_btn).prop('outerHTML')+$(cancel_btn).prop('outerHTML');
								
							} 
			
						}
			
			}
			
		}
	
}
}


function renderFilterFieldsWithAnswers_savedReports_outPut(divId,filter_data){
var tableId = renderFilterFieldsWithOutRows_savedReports_outPut(divId,filter_data);
	jQuery.post("dynamicReport_populateAvailableColumns.action", {}, function(availabeColumns) {
	
		var filterRowCount = 1;
		jQuery.each(filter_data, function(index, tableRow_filter) {
			
			var table = document.getElementById(tableId);
			
			var newRow = table.insertRow(table.rows.length);
			newRow.id = tableId+"$"+((table.rows.length));
			
			var fieldNameCell = newRow.insertCell(0);
			var jsonArr = jQuery.parseJSON(availabeColumns);
			var selectHtmlString = "<select class='select2' onchange ='populateExpressionDropDown(this.value,$(this).parent().parent());'><option value=''>Select </option>";
			for(var i = 0;i<jsonArr.length;i++){
				
				if(jsonArr[i].filter == tableRow_filter.field){
					selectHtmlString = selectHtmlString+"<option value='" +JSON.stringify(jsonArr[i])+ "' selected>"+ jsonArr[i].label + "</option>"
				}else{
					selectHtmlString = selectHtmlString+"<option value='" +JSON.stringify(jsonArr[i])+ "'>"+ jsonArr[i].label + "</option>"
				}
				
				
			}
			
			
			selectHtmlString = selectHtmlString+'</select>';
			fieldNameCell.innerHTML = selectHtmlString;
			
			
			var conditionCell = newRow.insertCell(1);
			var conditions = [];
			
			if (tableRow_filter.fieldDataType == "3" || tableRow_filter.fieldDataType == "2") {
				conditions = [ '1|Equal to', '2|Not Equal to','3|Greater than',
								'4|Greater than or equal to', '5|Less than',
								'6|Less than or equal to','7|Between'  ];
			} else if (tableRow_filter.fieldDataType == "4") {
				conditions = [ '1|Equal to', '2|Not Equal to','3|Greater than',
								'4|Greater than or equal to', '5|Less than',
								'6|Less than or equal to','7|Between' , '8|Is empty', '9|Is not empty' ];
			} else {
				conditions = ['1|Equal to', '2|Not Equal to','3|Greater than',
								'4|Greater than or equal to', '5|Less than','10|contains',
								'6|Less than or equal to' ,'8|Is empty', '9|Is not empty'];
			}
			
		
			var select_expression = createDropDown_FilterExpression_withDefaultValue(conditions,tableRow_filter.expression);
			conditionCell.innerHTML = $(select_expression).prop('outerHTML');
			if(tableRow_filter.fieldDataType.includes("4")){
				if(tableRow_filter.expression == "7"){
					var dateArray = (tableRow_filter.val).split("and");
					dateArray[0] = dateArray[0].replace('"', "");
					dateArray[1] = dateArray[1].replace('"', "");
					dateArray[0] = dateArray[0].replace('"', "");
					dateArray[1] = dateArray[1].replace('"', "");
					dateArray[0] = dateArray[0].trim();
					dateArray[1] = dateArray[1].trim();
					
					var valueCell = newRow.insertCell(2);
					
					var textBox1 = $('<input/>').attr({
						type : 'text',
						class:'datePickerTextBox val1',
						value : dateArray[0]
						
					});
					
					var textBox2 = $('<input/>').attr({
						type : 'text',
						class:'datePickerTextBox val1',
						value : dateArray[1]
					});
					
					
					valueCell.innerHTML = $(textBox1).prop('outerHTML')+"&nbsp to &nbsp"+$(textBox2).prop('outerHTML');
					
				}else if(tableRow_filter.expression == "8" || tableRow_filter.expression == "9" ){
					
					var valueCell = newRow.insertCell(2);
					
				}else{
					
					var valueCell = newRow.insertCell(2);
					
					var textBox1 = $('<input/>').attr({
						type : 'text',
						class:'datePickerTextBox',
						value : tableRow_filter.val
						
					});
					
					valueCell.innerHTML = $(textBox1).prop('outerHTML');
				    
				}
				
				$('.datePickerTextBox').datepicker({ 
			        autoclose: true, 
			        todayHighlight: true,
			        format: 'yyyy-mm-dd',
				});
				
			}else{

			if(tableRow_filter.expression == "9" || tableRow_filter.expression == "8" ){
				
				var valueCell = newRow.insertCell(2);
			    
			}else{
			if (tableRow_filter.expression == "7" && tableRow_filter.value.indexOf("and")>=0) {
var vallu = tableRow_filter.value.split("and");
					
					var valueCell = newRow.insertCell(2);
					var textBox1 = $('<input/>').attr({
						type : 'text',
						class : 'val1',
						value : vallu[0].trim()
					});
					
				
					var textBox2 = $('<input/>').attr({
						type : 'text',
						class : 'val2',
						value : vallu[1].trim()
				
					});
					
					
					var htmlStr = $(textBox1).prop('outerHTML') + "&nbsp and  &nbsp"
							+ $(textBox2).prop('outerHTML');
					valueCell.innerHTML = htmlStr;

				
				}else{
				var valueCell = newRow.insertCell(2);
				var textBox = $('<input/>').attr({
					type : 'text',
					class : 'val1',
					value : tableRow_filter.value
				});
				valueCell.innerHTML = $(textBox).prop('outerHTML');
				}
				 
			}
		
			}
			
		
			
			
			
			
			if(filter_data.length > 1){
				var and_or_cell = newRow.insertCell(3);
				if( filterRowCount < filter_data.length){
					
					var and_or_value = tableRow_filter.andOr;
					
					if(and_or_value == "AND"){
						and_or_cell.innerHTML =	'<select class="select2" ><option value="AND" selected>AND </option><option value="OR">OR </option></select>';
					}else if(and_or_value == "OR"){
						and_or_cell.innerHTML =	'<select class="select2" ><option value="AND" >AND </option><option value="OR" selected>OR </option></select>';
					}
				}
				
			}
			
			var controlBtn;
			if(filter_data.length > 1){
				controlBtn = newRow.insertCell(4);
			}else{
				controlBtn = newRow.insertCell(3);
			}
			
			
			
			var del_btn = $('<button/>').attr({
				type : 'button',
				class : 'btn btn-sts',
				onclick : 'deleteRowAndValidateParentRow($(this).parent().parent(),"filterTable_savedReports_outPut");',
				style : 'margin-left:1%;padding-top:2px;padding-bottom:2px',
			});
			$(del_btn).text("Delete");
			controlBtn.innerHTML = $(del_btn).prop('outerHTML');
			
			$('.select2').select2();
			filterRowCount = filterRowCount+1;
		
		});
		});
}



function createDropDown_FilterExpression_withDefaultValue(arr,selectedValue){
	
	var select = $('<select/>').attr({
		class : "select2",
		onchange : "enableDateRangePicker(this.value,$(this).parent().parent());"
	});
	
	var option = "<option value=''>Select </option>";
	$(select).append(option);
	
	for(var i = 0;i<arr.length;i++){
		if(selectedValue == arr[i].split("|")[0].trim()){
			var option = "<option value='" +arr[i].split("|")[0].trim()+ "' selected>"+ arr[i].split("|")[1].trim() + "</option>";
		}else{
			var option = "<option value='" +arr[i].split("|")[0].trim()+ "'>"+ arr[i].split("|")[1].trim() + "</option>";
		}
		
		$(select).append(option);
	}
	return select;
}


function renderFilterFieldsWithOutRows_savedReports_outPut(divId,filter_data){
	
	
	
	var add_btn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-sts',
		onclick : 'createNewRow("filterTable_savedReports_outPut")',
		style : 'margin-top:1%;margin-bottom:1%;padding-top:2px;padding-bottom:2px'
	});
	
	$(add_btn).text("Add");
	
	
	var execute_btn = $('<button/>').attr({
		type : 'button',
		class : 'btn bg-primary text-white',
		onclick : 'generateQuery("1")',
		style : 'margin-top:1%;margin-bottom:1%;padding-top:2px;padding-bottom:2px'
	});
	
	$(execute_btn).text("Execute");
	
	var update_btn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-danger',
		onclick : 'prior_update_Whole_report()',
		style : 'margin-top:1%;margin-bottom:1%;padding-top:2px;padding-bottom:2px'
	});
	
	$(update_btn).text("Update");
	
	var table = $("<table/>").addClass("table table-bordered").attr({
		id : "filterTable_savedReports_outPut"
	});

	var thead = $("<thead/>");

	var tr = $("<tr/>");
	tr.append($("<th/>").append("Field"));
	tr.append($("<th/>").append("Condition"));
	tr.append($("<th/>").append("Value"));
	if(filter_data.length > 1){
		tr.append($("<th/>").append("AND/OR"));
	}
	
	
	tr.append($("<th/>").append("Delete "));
	
	table.append(thead);
	thead.append(tr);
	
	var tbody = $("<tbody/>");
	table.append(tbody);
	$('#'+divId).append(add_btn);
	$('#'+divId).append(execute_btn);
	$('#'+divId).append(update_btn);
	
	$('#'+divId).append(table);
	
	return "filterTable_savedReports_outPut";
}


	
	

function deleteRowAndValidateParentRow(currentRow,tableId){
	
	var userAction = confirm("Confirm to delete");
    if (userAction == true) {
    	var currentRowId ;
    	$(currentRow).each(function( index ) {
    		 $( this ).each(function( k,index2 ) {
    			currentRowId = index2.id;
    		});
    	});
    	
    	currentRow.remove();
    	var $tds  = $('#'+tableId+' tr:last').find('td');
    	$tds.eq(3).empty();
    	var rowCount = $('#'+tableId+' tr').length;
    	
    	if(rowCount==2){
    		$tds.eq(3).remove();
    	var	 $ths  = $('#'+tableId+' tr:first').find('th');
    	$ths.eq(3).remove();
    	}
	if(tableId=='tableId'){
		generateQuery('0');
	}else{
		generateQuery('1');
	}
	
}
}



function prior_update_Whole_report(){
	
					var dialog = $(
					'<b>Sure to update ? </b><br/><p>Update will modify the existing report</p>')
					.dialog({
						buttons : {
							"Cancel" : function() {
								dialog.dialog('close');
							},
							"Update" : function() {
								dialog.dialog('close');
								//modal.style.display = "inline-block";
								updateExistingSavedReport(reportId_for_update,"prior_update_Whole_report");
							}
						}
					});
					
					
				
	
}



function deleteExistingSavedReport (currentRowId){
if(confirm("Confirm to delete Report")==true){
		
		jQuery.post("dynamicReport_deleteDynamicReportById.action", {
			
			saved_dynamic_report_id : currentRowId,
			
			
		}, function(result) {
			 finalOutPut_query = "";
			 finalOutPut_selectedFieldsForGrid = "";
			 jsonObj_filter_data = [];
			
			 reportTitle = "";
			 reportDescription = "";
			 
			
				$('#savedReports_table').remove();
				
				$("#savedReports").empty();
				populateSavedReports();
				
			 
		});

}
}


function updateExistingSavedReport(currentRowId,callingmethod){
	if(callingmethod == "validate_report_titleAndDescription"){
		
		jQuery.post("dynamicReport_updateDynamicReportById.action", {
			
			saved_dynamic_report_id : currentRowId,
			reportTitle : reportTitle,
			reportDescription : reportDescription,
			outPutQuery : "",
			header_fields : "",
			filter_data : "",
		}, function(result) {
			 finalOutPut_query = "";
			 finalOutPut_selectedFieldsForGrid = "";
			 jsonObj_filter_data = [];
			
			 reportTitle = "";
			 reportDescription = "";
			 
			
				$('#savedReports_table').remove();
				
				$("#savedReports").empty();
				populateSavedReports();
				
			 
		});
		
	}else if(callingmethod == "prior_update_Whole_report"){
		
		jQuery.post("dynamicReport_updateDynamicReportById.action", {
			
			saved_dynamic_report_id : reportId_for_update,
			reportTitle : "",
			reportDescription : "",
			query:finalOutPut_query,
			header_fields : finalOutPut_selectedFieldsForGrid_th.toString(),
			filter_data : JSON.stringify(jsonObj_filter_data)
			
			
		}, function(result) {
			 finalOutPut_query = "";
			 finalOutPut_selectedFieldsForGrid = "";
			 jsonObj_filter_data = [];
			
			 reportTitle = "";
			 reportDescription = "";
			 
			
				$('#savedReports_table').remove();
				
				$("#savedReports").empty();
				populateSavedReports();
				
			 
		});
		
		
	}
	
}


function cancel_update(currentRowId,title,description) {
	var myTab = document.getElementById('savedReports_table');
	if (myTab != null) {
		for (i = 1; i < myTab.rows.length; i++) {
			if (myTab.rows.item(i).id == currentRowId) {
				var objCells = myTab.rows.item(i).cells;

				for (var j = 0; j < objCells.length; j++) {
					if (j == 0) {
						objCells.item(j).innerHTML = title;
					} else if (j == 1) {
						objCells.item(j).innerHTML = description;
					} else if (j == 2) {
						var view_btn = $('<button/>').attr({
							type : 'button',
							class : 'btn btn-success',
							value : currentRowId,
							onclick : 'executeSavedReport(this.value);',

						});
						$(view_btn).text("View");

						var edit_btn = $('<button/>').attr({
							type : 'button',
							class : 'btn btn-warning',
							value : currentRowId,
							onclick : 'editExistingSavedReport(this.value);',
							style : "margin-left:1%;padding-top:2px;padding-bottom:2px"
						});
						$(edit_btn).text("Edit");

						var delete_btn = $('<button/>')
								.attr(
										{
											type : 'button',
											class : 'btn btn-danger',
											value : currentRowId,
											onclick : 'deleteExistingSavedReport(this.value,$(this).parent().parent());',
											style : "margin-left:1%;padding-top:2px;padding-bottom:2px"
										});
						$(delete_btn).text("Delete");

						objCells.item(j).innerHTML = $(view_btn).prop(
								'outerHTML')
								+ $(edit_btn).prop('outerHTML')
								+ $(delete_btn).prop('outerHTML');

					}

				}

			}

		}

	}
}





function validate_report_titleAndDescription(currentRowId){
	var myTab = document.getElementById('savedReports_table');
	
	if(myTab != null){
		
		for (i = 1; i < myTab.rows.length; i++) {
				
			if(myTab.rows.item(i).id == currentRowId){
			 
				var objCells = myTab.rows.item(i).cells;
					
						
						for (var j = 0; j < objCells.length; j++) {
							//info.innerHTML = info.innerHTML + ' ' + objCells.item(j).innerHTML;
							if (j == 0) {
								reportTitle = $(objCells.item(j)).find("input").val();
								reportDescription = $(objCells.item(j+1)).find("input").val();
								 if(isEmpty(reportTitle) || isEmpty(reportDescription)){
									 alert("Please enter values");
									 return;
								 }else{
									 objCells.item(j).innerHTML =  reportTitle;
								 }
							} else if (j == 1) {
								
								reportDescription = $(objCells.item(j)).find("input").val();
								 if(isEmpty(reportDescription)){
									 alert("Please enter values");
									 return;
								 }else{
									 objCells.item(j).innerHTML =  reportDescription;
								 }
							} else if (j == 2) {
								var view_btn = $('<button/>').attr({
									type : 'button',
									class : 'btn btn-success',
									value : currentRowId,
									onclick : 'executeSavedReport(this.value);',
									style : "margin-left:1%;padding-top:2px;padding-bottom:2px"
									
								});
								$(view_btn).text("View");
								
								var edit_btn = $('<button/>').attr({
									type : 'button',
									class : 'btn btn-warning',
									value : currentRowId,
									onclick : 'editExistingSavedReport(this.value);',
									style : "margin-left:1%;padding-top:2px;padding-bottom:2px"
								});
								$(edit_btn).text("Edit");
								
								var delete_btn = $('<button/>').attr({
									type : 'button',
									class : 'btn btn-danger',
									value : currentRowId,
									onclick : 'deleteExistingSavedReport(this.value,$(this).parent().parent());',
									style : "margin-left:1%;padding-top:2px;padding-bottom:2px"
								});
								$(delete_btn).text("Delete");
								
								objCells.item(j).innerHTML = $(view_btn).prop('outerHTML')+$(edit_btn).prop('outerHTML')+$(delete_btn).prop('outerHTML');
								
							} 
			
						}
			
			}
			
		}
		updateExistingSavedReport(currentRowId,"validate_report_titleAndDescription");
	}
}