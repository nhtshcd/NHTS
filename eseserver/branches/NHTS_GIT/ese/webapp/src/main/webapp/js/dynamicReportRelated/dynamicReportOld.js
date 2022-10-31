var finalOutPut_query;
var finalOutPut_selectedFieldsForGrid_th;
var jsonObj_filter_data = [];

var finalOutPut_query_update;
var finalOutPut_selectedFieldsForGrid_th_update;
var jsonObj_filter_data_update = [];
var selectedFieldsOfQuery_update;
var reportTitle;
var reportDescription;
var reportId_for_update;
function populateAvailableColumns(){
	jQuery.post("dynamicReport_populateAvailableColumns.action", {
		
	}, function(availabeColumns) {
		
		insertOptions("optionTransfer_select_availableColumns",jQuery.parseJSON(availabeColumns));
	//	insertOptions("optionTransfer_where_availableColumns",jQuery.parseJSON(availabeColumns));
	
	});
	
}

function insertOptions(ctrlName, jsonArr) {
	document.getElementById(ctrlName).length = 0;
	for (var i = 0; i < jsonArr.length; i++) {
		addOption(document.getElementById(ctrlName), jsonArr[i].columnLabel,
				jsonArr[i].columnName);
		
	}
}

function addOption(selectbox, text, value)
{
    var optn = document.createElement("OPTION");
    optn.text = text;
    optn.value = value;
    selectbox.options.add(optn);
}

function enableFilterFields(){
	if ($("#optionTransfer_select_selectedColumns") != null ) {
		makeFieldsSelectedOfOptionTransferById("optionTransfer_select_selectedColumns");
		if(!isEmpty($("#optionTransfer_select_selectedColumns").val())){
			
			renderFilterFields();
		}else{
			alert("Please select the Header fields");	
		}
	} else {
		alert("Please select the Header fields");
	}
}


function renderFilterFields(){
	var add_btn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-sts',
		onclick : 'createNewRow()',
		style : 'margin-top:15px'
	});
	
	$(add_btn).text("Add");
	var selectHtmlString = "<select class='select2' onchange ='populateExpressionDropDown(this.value,$(this).parent().parent());'><option value=''>Select </option>";
	jQuery.post("dynamicReport_populateAvailableColumns.action", {}, function(availabeColumns) {
		var jsonArr = jQuery.parseJSON(availabeColumns);
		for(var i = 0;i<jsonArr.length;i++){
			selectHtmlString = selectHtmlString+"<option value='" +jsonArr[i].columnName+"%"+jsonArr[i].columnType+ "'>"+ jsonArr[i].columnLabel + "</option>"
		}
		selectHtmlString = selectHtmlString+'</select>';
		
		deleteHTML("tableId");
		var table = $("<table/>").addClass("table table-bordered").attr({
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
		var select_expression =  createDropDown_FilterExpression(conditions);
		tr2.append($("<td  />").append(select_expression));
		
		var textBox = $('<input/>').attr({
			type : 'text'
		});
		tr2.append($("<td  />").append(textBox));
		
		var del_btn = $('<button/>').attr({
			type : 'button',
			class : 'btn btn-sts',
			onclick : 'deleteRowAndValidateParentRow($(this).parent().parent(),"tableId");',
			style : 'margin-top:15px',
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

function renderFilterFieldsWithOutRows(){
	
	deleteHTML("tableId");
	
	var add_btn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-sts',
		onclick : 'createNewRow()',
		style : 'margin-top:15px'
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
	if(jsonObj_filter_data.length > 1){
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

function createDropDown_filterFields(){

	var select = $('<select/>').attr({
		class : "select2",
		onchange : "populateExpressionDropDown(this.value,$(this).parent().parent());"
	});
	
	jQuery.post("dynamicReport_populateAvailableColumns.action", {
		
	}, function(availabeColumns) {
		
		var jsonArr = jQuery.parseJSON(availabeColumns);
		
		
		var option = '<option value="">Select </option>';
		$(select).append(option);
		
		for(var i = 0;i<jsonArr.length;i++){
			
			var option = "<option value='" +jsonArr[i].columnName.trim()+"%"+jsonArr[i].columnType.trim()+ "'>"+ jsonArr[i].columnLabel.trim() + "</option>";
			
			$(select).append(option);
		}
		
	});
	
return select;
}




function populateExpressionDropDown(obj,row_DOM){
	

	var id = $(row_DOM).prop('id');
	var row = document.getElementById(id);
    row.deleteCell(1);
    
	
	var field = obj.split("%");
	var conditions = [];
	
	if(field[1].includes("varchar")){
		 conditions = ['Equal to','Not Equal to','Contains','Not Contains','Is empty','Is not empty'];
	}else if(field[1].includes("date")){
		 conditions = ['Equal to','Not Equal to','Greater than','Greater than or equal to','Less than','Less than or equal to','Between','Is empty','Is not empty'];
	}else{
		 conditions = ['Equal to','Not Equal to','Greater than','Greater than or equal to','Less than','Less than or equal to','Is empty','Is not empty'];
	}
	
	// if you change anything in above conditions array should apply the same changes in generateQuery() function
	
	var select_expression =  createDropDown_FilterExpression(conditions);
	
	expressionTd = row.insertCell(1);
	
	expressionTd.innerHTML = $(select_expression).prop('outerHTML');
	$('.select2').select2();
	
	//refresh 3rd cell (value cell)
	
    row.deleteCell(2);
    
    var textBox = $('<input/>').attr({
		type : 'text',
	});
    
    newCell = row.insertCell(2);
	 var htmlStr = $(textBox).prop('outerHTML');
	 newCell.innerHTML = htmlStr;
	
}

function createDropDown_FilterExpression(arr){
	
	var select = $('<select/>').attr({
		class : "select2",
		//id : id,
		onchange : "enableDateRangePicker(this.value,$(this).parent().parent());"
	});
	
	var option = "<option value=''>Select </option>";
	$(select).append(option);
	
	for(var i = 0;i<arr.length;i++){
		
		var option = "<option value='" +arr[i]+ "'>"+ arr[i] + "</option>";
		$(select).append(option);
		
	}
	
	return select;
}

function renderTable(){
	
	var table = $("<table/>").addClass("table table-bordered")
			.attr({
				id : "tableId"
				
			});

	var thead = $("<thead/>");
	
	var tr = $("<tr/>");
	tr.append($("<th/>").append("Field"));
	tr.append($("<th/>").append("Condition"));
	tr.append($("<th/>").append("Value"));
	tr.append($("<th/>").append(" "));
	table.append(thead);
	thead.append(tr);
	var wrappedDiv = $("<div/>");
	wrappedDiv.append(table);
	$("#tableDiv").append(wrappedDiv);
	$("#tableDiv").addClass("appContentWrapper marginBottom");

}



function createDropDown_FilterExpression_withDefaultValue(arr,selectedValue){
	
	var select = $('<select/>').attr({
		class : "select2",
		onchange : "enableDateRangePicker(this.value,$(this).parent().parent());"
	});
	
	var option = "<option value=''>Select </option>";
	$(select).append(option);
	
	for(var i = 0;i<arr.length;i++){
		if(selectedValue == arr[i]){
			var option = "<option value='" +arr[i]+ "' selected>"+ arr[i] + "</option>";
		}else{
			var option = "<option value='" +arr[i]+ "'>"+ arr[i] + "</option>";
		}
		
		$(select).append(option);
	}
	return select;
}



function createNewRow(){
	var table = document.getElementById('tableId');
	 
	
	
if(table != null){
		
		for (i = 1; i < table.rows.length; i++) {
			
			var objCells = table.rows.item(i).cells;
				
				for (var j = 0; j < objCells.length; j++) {
							
					if (j == 0) {
						
						if(isEmpty($(objCells.item(j)).find(":selected").val())){
							alert("Please enter existing filter values ")
							return;
						}
						
					} else if (j == 1) {
						
						if(isEmpty($(objCells.item(j)).find(":selected").val())){
							alert("Please enter existing filter values ")
							return;
						}
						
					} else if (j == 2) {
						var expr = $(objCells.item(j-1)).find(":selected").val();
						if(expr != "Is empty" && expr != "Is not empty" ){
							if(isEmpty($(objCells.item(j)).find("input").val())){
								alert("Please enter existing filter values ")
								return;
							}
						}
						
					}
				}
			}
		
		createAnotherRow(table);
	}
	
	
}

function createAnotherRow(table){
	
	if(table != null){
		if(table.rows.length == 2){
			
				var objCells = table.rows.item(0).cells;
				if(objCells.length == 4){
					table.rows.item(0).insertCell(3).outerHTML = "<th>AND/OR</th>";
					//objCells.item(3).innerHTML = "AND/OR";
				
				var objCells = table.rows.item(1).cells;
				table.rows.item(1).insertCell(3);
				objCells.item(3).innerHTML = '<select class="select2" ><option value="AND">AND </option><option value="OR">OR </option></select>';
				
			}
		}else{
		
			if(table.rows.length > 2){
				var objCells = table.rows.item(Number(table.rows.length)-1).cells;
				if(isEmpty(objCells.item(3).innerHTML)){
					objCells.item(3).innerHTML = '<select class="select2" ><option value="AND">AND </option><option value="OR">OR </option></select>';
				}
			}
				
			
				
		}
	}
	
	if(!isEmpty(table.rows.item(Number(table.rows.length)-1).id)){
		var previousRowId = table.rows.item(Number(table.rows.length)-1).id;
		var temp = previousRowId.split("$");
		previousRowId = temp[1];
	}else{
		var previousRowId = 1;
	}
	
	var newRow = table.insertRow(table.rows.length);
	newRow.id = "tableId$"+(Number(previousRowId)+1);
	
	var fieldNameCell = newRow.insertCell(0);
	
	
	var selectHtmlString = "<select class='select2' onchange ='populateExpressionDropDown(this.value,$(this).parent().parent());'><option value=''>Select </option>";
	jQuery.post("dynamicReport_populateAvailableColumns.action", {}, function(availabeColumns) {
		var jsonArr = jQuery.parseJSON(availabeColumns);
		for(var i = 0;i<jsonArr.length;i++){
			selectHtmlString = selectHtmlString+"<option value='" +jsonArr[i].columnName+"%"+jsonArr[i].columnType+ "'>"+ jsonArr[i].columnLabel + "</option>"
		}
		selectHtmlString = selectHtmlString+'</select>';
		fieldNameCell.innerHTML = selectHtmlString;
		

		var conditionCell = newRow.insertCell(1);
		var select_expression =  createDropDown_FilterExpression("");
		conditionCell.innerHTML = $(select_expression).prop('outerHTML');
		
		
		var valueCell = newRow.insertCell(2);
		var textBox = $('<input/>').attr({
			type : 'text'
		});
		valueCell.innerHTML = $(textBox).prop('outerHTML');
		
		if(table.rows.length > 2){ 
		var and_or_cell = newRow.insertCell(3);
		}
		
		var controlBtn;
		if(table.rows.length == 2){ 
			controlBtn = newRow.insertCell(3);
		}else{
			controlBtn = newRow.insertCell(4);
		}
		
		var del_btn = $('<button/>').attr({
			type : 'button',
			class : 'btn btn-sts',
			onclick : 'deleteRowAndValidateParentRow($(this).parent().parent(),"tableId");',
			style : 'margin-top:15px',
		});
		$(del_btn).text("Delete");
		controlBtn.innerHTML = $(del_btn).prop('outerHTML');
		
		$('.select2').select2();
		
	});
		
	
	
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
    	
    	
    	
    	var myTab = document.getElementById(tableId);
    	
    	
    	if(myTab != null){
    		
    		//if(currentRowId >= 1){
    		
    		for (i = 1; i < myTab.rows.length; i++) {
    				
    			if(myTab.rows.item(i).id == currentRowId){
    			 
	    			 if(i == 1){
	    				 
	    				 var nextFirstRowCells =  myTab.rows.item(i+1);
	        			 var nextSecondRowCells =  myTab.rows.item(i+2);
	        			 
	        			 if(isEmpty(nextSecondRowCells)){
	        				 
	        				 if(!isEmpty(nextFirstRowCells)){
	            				 
	        					 var objCells_tbody = myTab.rows.item(2).cells;
	        						objCells_tbody.item(3).remove();
	        						
	        						var objCells_th = myTab.rows.item(0).cells;
	        		    			objCells_th.item(3).remove();
	        					 
	            			 }
	        			 }
	    			 }else{
	    				 var nextFirstRowCells =  myTab.rows.item(i+1);
	    				 if(isEmpty(nextFirstRowCells)){
	    					 var previousFirstRowCells =  myTab.rows.item(i-1);
	    					 var previousSecondRowCells =  myTab.rows.item(i-2);
	    					 
	    					 if(!isEmpty(previousSecondRowCells)){
	    						
	    						 var objCells_tbody = previousSecondRowCells.cells;
	    						 previousSecondRowCells = $(objCells_tbody.item(3)).find(":selected").val();
	    						
	    					 }
	    					 
	    					 
	    					 if(!isEmpty(previousFirstRowCells) && !isEmpty(previousSecondRowCells)){
	    						 var objCells_tbody = myTab.rows.item(i-1).cells;
	    						 if(!isEmpty(objCells_tbody.item(3))){
	    							 objCells_tbody.item(3).innerHTML = "";
	    						 }
	    					 }else if(!isEmpty(previousFirstRowCells) && isEmpty(previousSecondRowCells)){
	    						
	    						var objCells_tbody = myTab.rows.item(i-1).cells;
	      						objCells_tbody.item(3).remove();
	    						
	      						var objCells_th = myTab.rows.item(0).cells;
	    		    			objCells_th.item(3).remove();
	      						
	    					 }
	    					 
	        			 }
	    			 }
    			
    		}
    	}
    //}
    	
    		currentRow.remove();
    	
    }
	
	
}
}

function generateQuery(){
	var query;
	var enableOrDisableFilterByUser;
	var selectedFieldsForGrid_th;
	jsonObj_filter_data = [];
	makeFieldsSelectedOfOptionTransferById("optionTransfer_select_selectedColumns");
	var selectedFieldsForSelectStatement = $("#optionTransfer_select_selectedColumns").val();
	if(selectedFieldsForSelectStatement != null){
		
		var modal = document.getElementById('loading_msg');
		modal.style.display = "inline-block";
		
		selectedFieldsForSelectStatement = selectedFieldsForSelectStatement.toString();
		selectedFieldsForSelectStatement = selectedFieldsForSelectStatement.trim();
		var selectedFields = selectedFieldsForSelectStatement.split(",");
		
		
		var queryWithOutFilters = "select " + selectedFields + " from dynamic_report ";
		

		var myTab = document.getElementById('tableId');
		
		if(myTab != null){
			for (i = 1; i < myTab.rows.length; i++) {
				var objCells = myTab.rows.item(i).cells;
				  for (var j = 0; j < objCells.length; j++) {
					if (j == 0) {
						
						var fieldNames = $(objCells.item(j)).find(":selected").val() ;
						fieldNames = fieldNames.split("%");
						field = fieldNames[0];
						
						if(!selectedFields.includes(field)){
							selectedFields[(selectedFields.length)] = field;
						}
					}
				}
			}
		}
		
		query = "select " + selectedFields + " from dynamic_report "
		
		var selectedFieldsString = selectedFields.toString();
		 selectedFieldsForGrid_th = selectedFields.slice();
		
		jQuery.post("dynamicReport_populateAvailableColumns.action", {}, function(availabeColumns) {
			var jsonArr = jQuery.parseJSON(availabeColumns);
			
			for (var i = 0; i < jsonArr.length; i++) {
					if(selectedFieldsString.includes(jsonArr[i].columnName)){
						
						for(x = 0; x < selectedFieldsForGrid_th.length; x++)
						{
							if(selectedFieldsForGrid_th[x] == jsonArr[i].columnName){
								selectedFieldsForGrid_th[x] = jsonArr[i].columnLabel;
							}
						}
						
						
					}
					
				}
				
				
				
				if(myTab != null){
					
					
					var whereStatementString = " where ";
					
					for (i = 1; i < myTab.rows.length; i++) {

						// GET THE CELLS COLLECTION OF THE CURRENT ROW.
						
						 item_filter_data = {}
						
						var objCells = myTab.rows.item(i).cells;
						
						// LOOP THROUGH EACH CELL OF THE CURENT ROW TO READ CELL VALUES.
						for (var j = 0; j < objCells.length; j++) {
							//info.innerHTML = info.innerHTML + ' ' + objCells.item(j).innerHTML;
							if (j == 0) {
								var fieldArray = $(objCells.item(j)).find(":selected").val() ;
								fieldArray = fieldArray.split("%");
								fieldName = fieldArray[0];
								
								if(isEmpty(fieldName)){
									modal.style.display = "none";
									query = "";
									var dialog = $('<p>Please Enter All the Filter values</p>').dialog({
								         buttons: {
								             "OK": function() {
								            	  dialog.dialog('close');
								             },
								             "Execute with out filter":  function() {
								            	 dialog.dialog('close');
								            	 modal.style.display = "inline-block";
								            	 enableOrDisableFilterByUser = "disable";
								            	 populateResultDataTable(queryWithOutFilters,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser);
								            	
								            	
								             }
								         }
									 });
									
									
									
									return;
								}else{
									query = query + whereStatementString+ fieldName;
									item_filter_data ["fieldName"] = $(objCells.item(j)).find(":selected").text();
									item_filter_data ["fieldDataType"] = fieldArray[1];
									
								}
								
								
							
							} else if (j == 1) {
								
								// if you did any changes in below  strings should apply the same changes in populateConditionsDropDown() function
								var expression = $(objCells.item(j)).find(":selected").val() ;
								
								
								var field = $(objCells.item(j-1)).find(":selected").val();
								field = field.split("%");
								field = field[0];
								
								if(isEmpty(expression) || isEmpty(field)){
									modal.style.display = "none";
									query = "";
									var dialog = $('<p>Please Enter All  filter  values</p>').dialog({
								         buttons: {
								             "OK": function() {
								            	  dialog.dialog('close');
								             },
								             "Execute with out filter":  function() {
								            	 dialog.dialog('close');
								            	 modal.style.display = "inline-block";
								            	 enableOrDisableFilterByUser = "disable";
								            	 populateResultDataTable(queryWithOutFilters,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser);
								            	
								            	
								             }
								         }
									 });
									return;
								}else{
									if( expression == "Equal to"){
										query = query +" = ";
									}else if( expression == "Not Equal to"){
										query = query +" != ";
									}else if( expression == "Contains"){
										query = query +"  like  ";
									}else if( expression == "Not Contains"){
										query = query +" not  like  ";
									}else if( expression == "Greater than"){
										query = query +"  >  ";
									}else if( expression == "Less than"){
										query = query +"  <  ";
									}else if( expression == "Greater than or equal to"){
										query = query +"  >=  ";
									}else if( expression == "Less than or equal to"){
										query = query +"  <=  ";
									}else if( expression == "Between"){
										query = query +"  Between ";
									}else if( expression == "Is empty"){
										//query = query +"  is null or "+field+" = '' ";
										
										query = query +'  is null or '+field+' = "" ';
										
									}else if( expression == "Is not empty"){
										//query = query +"  is not null and "+ field+" != '' ";
										
										query = query +'  is not null and '+ field+' != "" ';
									}
									
									item_filter_data ["expression"] = $(objCells.item(j)).find(":selected").text();
									
								}
								
							
								
								
								
								
							} else if (j == 2) {
								var expr = $(objCells.item(j-1)).find(":selected").val();
								var val = $(objCells.item(j)).find("input").val();
								
								
							
								if( isEmpty(val) && expr != "Is empty" && expr != "Is not empty" ){
									modal.style.display = "none";
									query = "";
									var dialog = $('<p>Please Enter All  filter  values</p>').dialog({
								         buttons: {
								             "OK": function() {
								            	  dialog.dialog('close');
								             },
								             "Execute with out filter":  function() {
								            	 dialog.dialog('close');
								            	 modal.style.display = "inline-block";
								            	 enableOrDisableFilterByUser = "disable";
								            	 populateResultDataTable(queryWithOutFilters,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser);
								            	
								            	
								             }
								         }
									 });
									return;
								}else{
									
									if(expr == "Contains"){
										query = query +'"%'+ val+'%"';
										item_filter_data ["val"] = val;
									}else if(expr == "Not Contains"){
										query = query +'"%'+ val+'%"';
										item_filter_data ["val"] = val;
									}else if(expr == "Between"){
										var val = "";
										$(objCells.item(j)).find("input").each(function() {
											val = val+ this.value+"to";
										});
										
										var dates = (val).split("to");
										dates[0] = dates[0].trim();
										dates[1] = dates[1].trim();
										query = query +'"'+dates[0] +'" and '+'"'+dates[1]+'"';
								
										item_filter_data ["val"] = '"'+dates[0] +'" and '+'"'+dates[1]+'"';
									}else{
										if(!isEmpty(val)){
											query = query +'"'+ val+'"';
											item_filter_data ["val"] = val;
										}
										
									}
									
								}
								
								
								
								
							} else if (j == 3) {
								
							
									var and_or = $(objCells.item(j)).find(":selected").val();
									whereStatementString = " "+and_or+" ";
									
									if(!isEmpty(and_or) && and_or != undefined){
										and_or = and_or.trim();
										item_filter_data ["and_or"] = and_or;
									}else{
										item_filter_data ["and_or"] = "empty str";
									}
									
								
								
								
								
								jsonObj_filter_data.push(item_filter_data);
								
							}
							
							
							 
							
							
						}
					
						
					}
					
					
					
					
					enableOrDisableFilterByUser = "enable";
					populateResultDataTable(query,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser);
					
					
					
			}else{
				enableOrDisableFilterByUser = "disable";
				populateResultDataTable(query,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser);
			}
				
		});
		
		
		
		
		//}
	}else{
		alert("Please select the Header Fields");
	}
	
	
 
	
	
}     
  
function populateResultDataTable(query,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser){
	var startIndex = query.indexOf("select");
	var endIndex = query.indexOf("from");
	var selectedFieldsForAction = (query.substring(startIndex+6, endIndex)).trim();
	
	query = query+" GROUP BY "+selectedFieldsForAction;
	
	
	
	if(enableOrDisableFilterByUser == "disable"){
		if(isEmpty(selectedFieldsForGrid_th[selectedFieldsForGrid_th.length-1])){
			selectedFieldsForGrid_th.splice(selectedFieldsForGrid_th.length-1, 1);
		}
		
		if(isEmpty(selectedFields[selectedFields.length-1])){
			selectedFields.splice(selectedFields.length-1, 1);
		}
	}
	
	console.log("---------------------------")
	console.log(query)
	finalOutPut_query = query;
	
	console.log("---------------------------")
	jQuery.post("dynamicReport_executeQueryAndgetValues.action", {
		query : query,
		selectedFields : selectedFieldsForAction
	}, function(gridData) {

		json = $.parseJSON(gridData);
		
		deleteHTML("jQueryGridTable");
		var div = $('<div/>').attr({
			id : "jQueryGridTable",
		});
		
		var headerDiv =  $("<div/>").addClass("formContainerWrapper");
		headerDiv.append("<h2>Dynamic Report <div class='pull-right'></div>  </h2>");
		//headerDiv.append("<div id='exportButtons_dynamicReport' style='float:right;'></div>");
		$("#jQueryGridTable").append(headerDiv);
		
		var insideFilterDiv = $('<div/>').attr({
			id : "insideFilterDiv"
		});
		
		if(isEmpty(jsonObj_filter_data)){
		renderFilterFields();
		}
		
		
		
		$("#jQueryGridTable").append(insideFilterDiv);
		$("#jQueryGridTable").append("<div id='exportButtons_dynamicReport' style='float:right;'></div>");
		
		var table = $("<table/>").addClass("table table-bordered")
						.attr({
							id : "grid"
							
						});
				var thead = $("<thead/>");
				var tr = $("<tr/>");
				
				var headerFields_changedLabels;
				
				finalOutPut_selectedFieldsForGrid_th = selectedFieldsForGrid_th;
				
				for (var obj in selectedFieldsForGrid_th) {
					  tr.append($("<th/>").append(selectedFieldsForGrid_th[obj]));
					  thead.append(tr);
				}
			
				table.append(thead);
				
				var tbody = $("<tbody/>");
				
				
				for (var i = 0; i < json.length; i++) {
					item = {}
					var tr2 = $("<tr/>");
					
				
					for (var j = 0; j < selectedFields.length; j++) {
						item[selectedFields[j]] = json[i][selectedFields[j]];
						tr2.append($("<td/>").append(item[selectedFields[j]] ));
						tbody.append(tr2);
					}
				
					
				}
				
				
				table.append(tbody);
				$("#jQueryGridTable").append(table);
				var table = $('#grid').DataTable( {
					"sScrollX": "100%",
				    "sScrollXInner": "110%",
				    "scrollY": "350px",
				});
				
			     var buttons = new $.fn.dataTable.Buttons(table, {
			    	  buttons: [
				                 {
				                     extend: 'excel',
				                     title: 'Dynamic Report',
				                     messageTop: '',
				                     messageBottom: '',
				                     
				                 },
				                 {
				                     extend: 'pdf',
				                     title: 'Dynamic Report',
				                     messageTop: '',
				                     messageBottom: '',
				                    
				                 }
				             ]
			   }).container().appendTo($('#exportButtons_dynamicReport'));
			    $("#grid").addClass("dataTableTheme");
				$("#jQueryGridTable").addClass("appContentWrapper marginBottom");
				
				var modal = document.getElementById('loading_msg');
				modal.style.display = "none";
				window.scrollBy(10, 800);
				
				if(!isEmpty(jsonObj_filter_data)){
					renderFilterFieldsWithAnswers();
				}
				
	});
	
}

function renderFilterFieldsWithAnswers(){
	renderFilterFieldsWithOutRows();

	
	
	jQuery.post("dynamicReport_populateAvailableColumns.action", {}, function(availabeColumns) {
	
		var filterRowCount = 1;
		
		jQuery.each(jsonObj_filter_data, function(index, tableRow_filter) {
			console.log(tableRow_filter.fieldName)
			console.log(tableRow_filter.expression)
			console.log(tableRow_filter.val)
			console.log(tableRow_filter.and_or)
			console.log("------------------------------")
		
		
		
		
			var table = document.getElementById('tableId');
			var newRow = table.insertRow(table.rows.length);
			newRow.id = "tableId$"+((table.rows.length));
			
			var fieldNameCell = newRow.insertCell(0);
			var jsonArr = jQuery.parseJSON(availabeColumns);
			var selectHtmlString = "<select class='select2' onchange ='populateExpressionDropDown(this.value,$(this).parent().parent());'><option value=''>Select </option>";
			for(var i = 0;i<jsonArr.length;i++){
				
				if(jsonArr[i].columnLabel == tableRow_filter.fieldName){
					selectHtmlString = selectHtmlString+"<option value='" +jsonArr[i].columnName+"%"+jsonArr[i].columnType+ "' selected>"+ jsonArr[i].columnLabel + "</option>"
				}else{
					selectHtmlString = selectHtmlString+"<option value='" +jsonArr[i].columnName+"%"+jsonArr[i].columnType+ "'>"+ jsonArr[i].columnLabel + "</option>"
				}
				
			}
			
			
			selectHtmlString = selectHtmlString+'</select>';
			fieldNameCell.innerHTML = selectHtmlString;
			
			
			var conditionCell = newRow.insertCell(1);
			var conditions = [];
			
			if(tableRow_filter.fieldDataType.includes("varchar")){
				 conditions = ['Equal to','Not Equal to','Contains','Not Contains','Is empty','Is not empty'];
			}else if(tableRow_filter.fieldDataType.includes("date")){
				 conditions = ['Equal to','Not Equal to','Greater than','Greater than or equal to','Less than','Less than or equal to','Between','Is empty','Is not empty'];
			}else{
				 conditions = ['Equal to','Not Equal to','Greater than','Greater than or equal to','Less than','Less than or equal to','Is empty','Is not empty'];
			}
			
			var select_expression = createDropDown_FilterExpression_withDefaultValue(conditions,tableRow_filter.expression);
			conditionCell.innerHTML = $(select_expression).prop('outerHTML');
			if(tableRow_filter.fieldDataType.includes("date")){
				if(tableRow_filter.expression == "Between"){
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
						class:'datePickerTextBox',
						value : dateArray[0]
						
					});
					
					var textBox2 = $('<input/>').attr({
						type : 'text',
						class:'datePickerTextBox',
						value : dateArray[1]
					});
					
					
					valueCell.innerHTML = $(textBox1).prop('outerHTML')+"&nbsp to &nbsp"+$(textBox2).prop('outerHTML');
					
				}else if(tableRow_filter.expression == "Is empty" || tableRow_filter.expression == "Is not empty" ){
					
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
			        format: 'yyyy/mm/dd',
				});
				
			}else{

			if(tableRow_filter.expression == "Is empty" || tableRow_filter.expression == "Is not empty" ){
				
				var valueCell = newRow.insertCell(2);
			    
			}else{
				
				var valueCell = newRow.insertCell(2);
				var textBox = $('<input/>').attr({
					type : 'text',
					value : tableRow_filter.val
				});
				valueCell.innerHTML = $(textBox).prop('outerHTML');
				 
			}
		
			}
			
		
			
			
			
			
			if(jsonObj_filter_data.length > 1){
				var and_or_cell = newRow.insertCell(3);
				if( filterRowCount < jsonObj_filter_data.length){
					
					var and_or_value = tableRow_filter.and_or;
					
					if(and_or_value == "AND"){
						and_or_cell.innerHTML =	'<select class="select2" ><option value="AND" selected>AND </option><option value="OR">OR </option></select>';
					}else if(and_or_value == "OR"){
						and_or_cell.innerHTML =	'<select class="select2" ><option value="AND" >AND </option><option value="OR" selected>OR </option></select>';
					}
				}
				
			}
			
			var controlBtn;
			if(jsonObj_filter_data.length > 1){
				controlBtn = newRow.insertCell(4);
			}else{
				controlBtn = newRow.insertCell(3);
			}
			
			
			
			var del_btn = $('<button/>').attr({
				type : 'button',
				class : 'btn btn-sts',
				onclick : 'deleteRowAndValidateParentRow($(this).parent().parent(),"tableId");',
				style : 'margin-top:15px',
			});
			$(del_btn).text("Delete");
			controlBtn.innerHTML = $(del_btn).prop('outerHTML');
			
			$('.select2').select2();
			filterRowCount = filterRowCount+1;
		
		});
		});
}



function makeFieldsSelectedOfOptionTransferById(id){
	if ($("#"+id) != null) {
		$("#"+id).multiple = true;
		for (var x = 0; x < document.getElementById(id).options.length; x++) {
			document.getElementById(id).options[x].selected = true;
		}
	}
}    

function validateFilterFields(){
	if ($("#optionTransfer_where_selectedColumns") != null) {
		makeFieldsSelectedOfOptionTransferById("optionTransfer_where_selectedColumns");
		if(isEmpty($("#optionTransfer_where_selectedColumns").val())){
			//$('#enableFilterFields_btn').removeAttr('disabled');
			$("#filterDiv").removeClass("appContentWrapper marginBottom");
			deleteHTML("filterDiv");
			deleteHTML("tableDiv");
		}else{
			reloadDynamicFieldsDropDown();
		}
	}
}

function deleteHTML(elementID){
    if(document.getElementById(elementID) != null){
    	document.getElementById(elementID).innerHTML = "";
    	
    }
}

function clearAllOfTheChanges(){
	$('#enableFilterFields_btn').removeAttr('disabled');
	$("#filterDiv").removeClass("appContentWrapper marginBottom");
	$("#tableDiv").removeClass("appContentWrapper marginBottom");
	$("#jQueryGridTable").removeClass("appContentWrapper marginBottom");
	deleteHTML("tableDiv");
	deleteHTML("jQueryGridTable");
	populateAvailableColumns();
	insertOptions("optionTransfer_select_selectedColumns","");
	$("#filterDiv").hide();
	//insertOptions("optionTransfer_where_selectedColumns","");
	
}

function refresh(){
	enableFilterFields();
	//validateFilterFields();
}

function deleteCurrentRow(rowID){
	$("#tableId > thead").find('#'+rowID).remove();
}

function reloadDynamicFieldsDropDown(){
	if ($("#optionTransfer_where_selectedColumns") != null) {
		makeFieldsSelectedOfOptionTransferById("optionTransfer_where_selectedColumns");
		var selectedFieldsForWhereCondition = $("#optionTransfer_where_selectedColumns").val();
		if(!isEmpty($("#optionTransfer_where_selectedColumns").val())){
			selectedFieldsForWhereCondition = selectedFieldsForWhereCondition.toString();
			var text = selectedFieldsForWhereCondition.split(",");
			
			if(document.getElementById("field_filter") == null){
				var select_fields =  createDropDown_FilterExpression(text);
			}else{
				var select_fields =  createDropDown_FilterExpression(text);
				$('#filterFields').find('.flexform').find('#filterFieldsDropDown').find('#field_filter').remove();
				$('#filterFields').find('.flexform').find('#filterFieldsDropDown').append(select_fields);
			}
		}
	}
}


function enableDateRangePicker(expression,row_DOM){
	
	var first_td = $(row_DOM).find('td')[0];
	var field = $(first_td).find(":selected").val();
	
	var fieldName = field.split("%");
	
	
	if(fieldName[1] == "date"){
		if(expression == "Between"){
			
			var id = $(row_DOM).prop('id');
			var row = document.getElementById(id);
		    row.deleteCell(2);
			
			var textBox1 = $('<input/>').attr({
				type : 'text',
				class:'datePickerTextBox'
				
			});
			
			var textBox2 = $('<input/>').attr({
				type : 'text',
				class:'datePickerTextBox'
			});
			
			 newCell = row.insertCell(2);
			 var htmlStr = $(textBox1).prop('outerHTML')+"&nbsp to &nbsp"+$(textBox2).prop('outerHTML');
			 newCell.innerHTML = htmlStr;
			
			 $('.datePickerTextBox').datepicker({ 
			        autoclose: true, 
			        todayHighlight: true,
			        format: 'yyyy/mm/dd',
			  }).datepicker('update', new Date());
			
			
		}else if(expression == "Is empty" || expression == "Is not empty" ){
			
			var id = $(row_DOM).prop('id');
			var row = document.getElementById(id);
		    row.deleteCell(2);
		    row.insertCell(2);
		    
		}else{
			
			var id = $(row_DOM).prop('id');
			var row = document.getElementById(id);
		    row.deleteCell(2);
		    
		    var textBox1 = $('<input/>').attr({
				type : 'text',
				class:'datePickerTextBox'
				
			});
		    
		    newCell = row.insertCell(2);
			 var htmlStr = $(textBox1).prop('outerHTML');
			 newCell.innerHTML = htmlStr;
			 
			 $('.datePickerTextBox').datepicker({ 
			        autoclose: true, 
			        todayHighlight: true,
			        format: 'yyyy/mm/dd',
			  }).datepicker('update', new Date());
		    
		}
	}else{
		if(expression == "Is empty" || expression == "Is not empty" ){
			
			var id = $(row_DOM).prop('id');
			var row = document.getElementById(id);
		    row.deleteCell(2);
		    row.insertCell(2);
		    
		}else{
			
			var id = $(row_DOM).prop('id');
			var row = document.getElementById(id);
		    row.deleteCell(2);
		    
		    var textBox1 = $('<input/>').attr({
				type : 'text'
			});
		    
		    newCell = row.insertCell(2);
			 var htmlStr = $(textBox1).prop('outerHTML');
			 newCell.innerHTML = htmlStr;
			 
		}
	}
	
	
	
	
	
}

function populateModalForSave(){
	if(isEmpty(finalOutPut_query)){
		alert("please create report before save")
		return;
	}else{
		var modal = document.getElementById('myModal');
		modal.style.display = "block";
		
		var span = document.getElementsByClassName("close")[0];
		span.onclick = function() {
		    modal.style.display = "none";
		}
	}
	
	
	
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
		outPutQuery : finalOutPut_query,
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


function populateSavedReports(){
	var query = "select * from saved_dynamic_report order by id desc";
	var selectedFields = ['id','Title','Description','query','header_fields','filter_data'];
	jQuery.post("dynamicReport_executeQueryAndgetValues.action", {
		query : query,
		selectedFields : 'id,Title,Description,query,header_fields,filter_data'
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
					var headerFields = json[i][selectedFields[4]];
					var query = json[i][selectedFields[3]];
					var filter_data = json[i][selectedFields[5]];
					
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
						onclick : 'executeSavedReport(this.value);',
						
					});
					$(view_btn).text("View");
					
					var edit_btn = $('<button/>').attr({
						type : 'button',
						class : 'btn btn-warning',
						value : rowId,
						onclick : 'editExistingSavedReport(this.value);',
						style : "margin-left:1%;"
					});
					$(edit_btn).text("Edit");
					
					var delete_btn = $('<button/>').attr({
						type : 'button',
						class : 'btn btn-danger',
						value : rowId,
						onclick : 'deleteExistingSavedReport(this.value,$(this).parent().parent());',
						style : "margin-left:1%;"
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
			     var buttons = new $.fn.dataTable.Buttons(table, {
			    	  buttons: [
				                 {
				                     extend: 'excel',
				                     Title: 'Dynamic Report',
				                     messageTop: '',
				                     messageBottom: '',
				                     
				                 },
				                 {
				                     extend: 'pdf',
				                     Title: 'Dynamic Report',
				                     messageTop: '',
				                     messageBottom: '',
				                    
				                 }
				             ]
			   }).container().appendTo($('#exportButtons_savedReports'));
			     $("#savedReports_table").addClass("dataTableTheme");
				$("#savedReports").addClass("appContentWrapper marginBottom");
	});
	
}

function executeSavedReport(rowId){
	var query;
	reportId_for_update = rowId;
	var currentRowId = rowId;
	var selectedFields = new Array();
	var selectedFieldsForAction;
	var filter_data;
	
	var modal = document.getElementById('loading_msg');
	modal.style.display = "inline-block";
	
	$("#savedReports_outPut").removeClass("appContentWrapper marginBottom");
	deleteHTML("savedReports_outPut");
	
	var myTab = document.getElementById('savedReports_table');
	 
	
	
	if(myTab != null){
		
		
		
		for (i = 1; i < myTab.rows.length; i++) {
				
			if(myTab.rows.item(i).id == currentRowId){
				
				var temp = myTab.rows.item(i).className;
				query = $(myTab.rows.item(i)).data('value');
				var index1 = query.indexOf("select");
				var index2 = query.indexOf("from");
				selectedFieldsOfQuery_update = query.substring(index1+6,index2);
				
				filter_data = $(myTab.rows.item(i)).data('filter');
				var endIndex;
				if(temp.includes("odd")){
					 endIndex = temp.indexOf("odd");
				}else{
					endIndex = temp.indexOf("even");
				}
			
				
				selectedFieldsForAction = (temp.substring(0, endIndex)).trim();
				
				selectedFields = selectedFieldsForAction.split(",");
				
				
					var objCells = myTab.rows.item(i).cells;
					
						
						for (var j = 0; j < objCells.length; j++) {
							 if (j == 0) {
								 reportTitle = objCells.item(j).innerHTML;
								
							}
						}
			}
			
		}
	
}
	
	
	
console.log("--------------------------------")
console.log("executing saved report -->  "+query)
console.log("--------------------------------")

	jQuery.post("dynamicReport_executeQueryAndgetValues.action", {
		query : query,
		selectedFields : selectedFieldsForAction
	}, function(gridData) {

		json = $.parseJSON(gridData);
		
		
		
		var headerDiv =  $("<div/>").addClass("formContainerWrapper");
		headerDiv.append("<h2>"+reportTitle+"<div class='pull-right'><a class='aCollapse'><i style='color:black;' class='fa fa-close' onclick=closeSavedReportOutput();></i></a></div> </h2>");
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
				
				
				
				
				for (var obj in selectedFields) {
					tr.append($("<th/>").append(selectedFields[obj]));
					  thead.append(tr);
				}
			
				table.append(thead);
				
				var tbody = $("<tbody/>");
				
				
				for (var i = 0; i < json.length; i++) {
					item = {}
					var tr2 = $("<tr/>");
					
				
					for (var j = 0; j < selectedFields.length; j++) {
						item[selectedFields[j]] = json[i][selectedFields[j]];
						tr2.append($("<td/>").append(item[selectedFields[j]] ));
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
			   }).container().appendTo($('#exportButtons_savedReports_outPut'));
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
									style : "margin-left:1%;"
								});
								$(update_btn).text("Update");
								
								var cancel_btn = $('<button/>').attr({
									type : 'button',
									class : 'btn btn-sts',
									value : currentRowId,
									onclick : 'cancel_update("'+currentRowId+'","'+title+'","'+description+'")',
									style : "margin-left:1%;"
								});
								$(cancel_btn).text("cancel");
								
								objCells.item(j).innerHTML = $(update_btn).prop('outerHTML')+$(cancel_btn).prop('outerHTML');
								
							} 
			
						}
			
			}
			
		}
	
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
		}, function(availabeColumns) {
			
		});
		
	}else if(callingmethod == "prior_update_Whole_report"){
		
		jQuery.post("dynamicReport_updateDynamicReportById.action", {
			
			saved_dynamic_report_id : reportId_for_update,
			reportTitle : "",
			reportDescription : "",
			outPutQuery : finalOutPut_query_update,
			header_fields : finalOutPut_selectedFieldsForGrid_th_update.toString(),
			filter_data : JSON.stringify(jsonObj_filter_data_update),
			
		}, function(result) {
			
			var index1 = finalOutPut_query_update.indexOf("select");
			var index2 = finalOutPut_query_update.indexOf("from");
			selectedFieldsOfQuery_update = finalOutPut_query_update.substring(index1+6,index2);
			
			 finalOutPut_query_update = "";
			 finalOutPut_selectedFieldsForGrid_th_update = "";
			 jsonObj_filter_data_update = [];
			
			 reportTitle = "";
			 reportDescription = "";
			 
			
			 $('#savedReports_table').DataTable().destroy();	
				$("#savedReports").empty();
				populateSavedReports();
				
			 
		});
		
		
	}
	
}

function deleteExistingSavedReport(currentRowId,row){
	var userAction = confirm("Confirm to delete");
    if (userAction == true) {
    	jQuery.post("dynamicReport_deleteDynamicReportById.action", {
			saved_dynamic_report_id : currentRowId
		}, function(availabeColumns) {
			row.remove();
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
							style : "margin-left:1%;"
						});
						$(edit_btn).text("Edit");

						var delete_btn = $('<button/>')
								.attr(
										{
											type : 'button',
											class : 'btn btn-danger',
											value : currentRowId,
											onclick : 'deleteExistingSavedReport(this.value,$(this).parent().parent());',
											style : "margin-left:1%;"
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
	finalOutPut_query = "";
	finalOutPut_selectedFieldsForGrid_th = "";
	$("#insideFilterDiv").remove();
}

function closeSavedReportOutput(){
	$('#savedReports_outPut').removeClass('appContentWrapper marginBottom');
	deleteHTML('savedReports_outPut');
}


/// output table filter work started

function renderFilterFieldsWithAnswers_savedReports_outPut(divId,filter_data){
var tableId = renderFilterFieldsWithOutRows_savedReports_outPut(divId,filter_data);

	
	
	jQuery.post("dynamicReport_populateAvailableColumns.action", {}, function(availabeColumns) {
	
		var filterRowCount = 1;
		
		jQuery.each(filter_data, function(index, tableRow_filter) {
			/*console.log(tableRow_filter.fieldName)
			console.log(tableRow_filter.expression)
			console.log(tableRow_filter.val)
			console.log(tableRow_filter.and_or)
			console.log("------------------------------")*/
		
		
		
		
			var table = document.getElementById(tableId);
			
			var newRow = table.insertRow(table.rows.length);
			newRow.id = tableId+"$"+((table.rows.length));
			
			var fieldNameCell = newRow.insertCell(0);
			var jsonArr = jQuery.parseJSON(availabeColumns);
			var selectHtmlString = "<select class='select2' onchange ='populateExpressionDropDown(this.value,$(this).parent().parent());'><option value=''>Select </option>";
			for(var i = 0;i<jsonArr.length;i++){
				
				if(jsonArr[i].columnLabel == tableRow_filter.fieldName){
					selectHtmlString = selectHtmlString+"<option value='" +jsonArr[i].columnName+"%"+jsonArr[i].columnType+ "' selected>"+ jsonArr[i].columnLabel + "</option>"
				}else{
					selectHtmlString = selectHtmlString+"<option value='" +jsonArr[i].columnName+"%"+jsonArr[i].columnType+ "'>"+ jsonArr[i].columnLabel + "</option>"
				}
				
			}
			
			
			selectHtmlString = selectHtmlString+'</select>';
			fieldNameCell.innerHTML = selectHtmlString;
			
			
			var conditionCell = newRow.insertCell(1);
			var conditions = [];
			
			if(tableRow_filter.fieldDataType.includes("varchar")){
				 conditions = ['Equal to','Not Equal to','Contains','Not Contains','Is empty','Is not empty'];
			}else if(tableRow_filter.fieldDataType.includes("date")){
				 conditions = ['Equal to','Not Equal to','Greater than','Greater than or equal to','Less than','Less than or equal to','Between','Is empty','Is not empty'];
			}else{
				 conditions = ['Equal to','Not Equal to','Greater than','Greater than or equal to','Less than','Less than or equal to','Is empty','Is not empty'];
			}
			
			var select_expression = createDropDown_FilterExpression_withDefaultValue(conditions,tableRow_filter.expression);
			conditionCell.innerHTML = $(select_expression).prop('outerHTML');
			if(tableRow_filter.fieldDataType.includes("date")){
				if(tableRow_filter.expression == "Between"){
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
						class:'datePickerTextBox',
						value : dateArray[0]
						
					});
					
					var textBox2 = $('<input/>').attr({
						type : 'text',
						class:'datePickerTextBox',
						value : dateArray[1]
					});
					
					
					valueCell.innerHTML = $(textBox1).prop('outerHTML')+"&nbsp to &nbsp"+$(textBox2).prop('outerHTML');
					
				}else if(tableRow_filter.expression == "Is empty" || tableRow_filter.expression == "Is not empty" ){
					
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
			        format: 'yyyy/mm/dd',
				});
				
			}else{

			if(tableRow_filter.expression == "Is empty" || tableRow_filter.expression == "Is not empty" ){
				
				var valueCell = newRow.insertCell(2);
			    
			}else{
				
				var valueCell = newRow.insertCell(2);
				var textBox = $('<input/>').attr({
					type : 'text',
					value : tableRow_filter.val
				});
				valueCell.innerHTML = $(textBox).prop('outerHTML');
				 
			}
		
			}
			
		
			
			
			
			
			if(filter_data.length > 1){
				var and_or_cell = newRow.insertCell(3);
				if( filterRowCount < filter_data.length){
					
					var and_or_value = tableRow_filter.and_or;
					
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
				style : 'margin-top:15px',
			});
			$(del_btn).text("Delete");
			controlBtn.innerHTML = $(del_btn).prop('outerHTML');
			
			$('.select2').select2();
			filterRowCount = filterRowCount+1;
		
		});
		});
}


function renderFilterFieldsWithOutRows_savedReports_outPut(divId,filter_data){
	
	
	
	var add_btn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-sts',
		onclick : 'createNewRow_savedReportOutput()',
		style : 'margin-top:15px'
	});
	
	$(add_btn).text("Add");
	
	
	var execute_btn = $('<button/>').attr({
		type : 'button',
		class : 'btn bg-primary text-white',
		onclick : 'generateQuery_savedReportOutput()',
		style : 'margin-top:15px'
	});
	
	$(execute_btn).text("Execute");
	
	var update_btn = $('<button/>').attr({
		type : 'button',
		class : 'btn btn-danger',
		onclick : 'prior_update_Whole_report()',
		style : 'margin-top:15px'
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


function createNewRow_savedReportOutput(){

	var table = document.getElementById('filterTable_savedReports_outPut');
	 
	
	
if(table != null){
		
		for (i = 1; i < table.rows.length; i++) {
			
			var objCells = table.rows.item(i).cells;
				
				for (var j = 0; j < objCells.length; j++) {
							
					if (j == 0) {
						
						if(isEmpty($(objCells.item(j)).find(":selected").val())){
							alert("Please enter existing filter values ")
							return;
						}
						
					} else if (j == 1) {
						
						if(isEmpty($(objCells.item(j)).find(":selected").val())){
							alert("Please enter existing filter values ")
							return;
						}
						
					} else if (j == 2) {
						var expr = $(objCells.item(j-1)).find(":selected").val();
						if(expr != "Is empty" && expr != "Is not empty" ){
							if(isEmpty($(objCells.item(j)).find("input").val())){
								alert("Please enter existing filter values ")
								return;
							}
						}
						
					}
				}
			}
		
		createAnotherRow_savedReportOutput(table);
	}
	
}

function createAnotherRow_savedReportOutput(table){
	
	if(table != null){
		if(table.rows.length == 2){
			
				var objCells = table.rows.item(0).cells;
				if(objCells.length == 4){
					
					table.rows.item(0).insertCell(3).outerHTML = "<th>AND/OR</th>";
					//objCells.item(3).innerHTML = "AND/OR";
				
				var objCells = table.rows.item(1).cells;
				table.rows.item(1).insertCell(3);
				objCells.item(3).innerHTML = '<select class="select2" ><option value="AND">AND </option><option value="OR">OR </option></select>';
				
			}
		}else{
		
			
				var objCells = table.rows.item(table.rows.length-1).cells;
				if(isEmpty(objCells.item(3).innerHTML)){
					objCells.item(3).innerHTML = '<select class="select2" ><option value="AND">AND </option><option value="OR">OR </option></select>';
				
			}
		}
	}
	
	if(!isEmpty(table.rows.item(Number(table.rows.length)-1).id)){
		
		var previousRowId = table.rows.item(Number(table.rows.length)-1).id;
		var temp = previousRowId.split("$");
		previousRowId = temp[1];
	}else{
		var previousRowId = 1;
	}
	
	var newRow = table.insertRow(table.rows.length);
	newRow.id = "filterTable_savedReports_outPut$"+(Number(previousRowId)+1);
	
	var fieldNameCell = newRow.insertCell(0);
	
	
	var selectHtmlString = "<select class='select2' onchange ='populateExpressionDropDown(this.value,$(this).parent().parent());'><option value=''>Select </option>";
	jQuery.post("dynamicReport_populateAvailableColumns.action", {}, function(availabeColumns) {
		var jsonArr = jQuery.parseJSON(availabeColumns);
		for(var i = 0;i<jsonArr.length;i++){
			selectHtmlString = selectHtmlString+"<option value='" +jsonArr[i].columnName+"%"+jsonArr[i].columnType+ "'>"+ jsonArr[i].columnLabel + "</option>"
		}
		selectHtmlString = selectHtmlString+'</select>';
		fieldNameCell.innerHTML = selectHtmlString;
		

		var conditionCell = newRow.insertCell(1);
		var select_expression =  createDropDown_FilterExpression("");
		conditionCell.innerHTML = $(select_expression).prop('outerHTML');
		
		
		var valueCell = newRow.insertCell(2);
		var textBox = $('<input/>').attr({
			type : 'text'
		});
		valueCell.innerHTML = $(textBox).prop('outerHTML');
		
		if(table.rows.length > 2){ 
		var and_or_cell = newRow.insertCell(3);
		}
		
		var controlBtn;
		if(table.rows.length == 2){ 
			controlBtn = newRow.insertCell(3);
		}else{
			controlBtn = newRow.insertCell(4);
		}
		
		var del_btn = $('<button/>').attr({
			type : 'button',
			class : 'btn btn-sts',
			onclick : 'deleteRowAndValidateParentRow($(this).parent().parent(),"filterTable_savedReports_outPut");',
			style : 'margin-top:15px',
		});
		$(del_btn).text("Delete");
		controlBtn.innerHTML = $(del_btn).prop('outerHTML');
		
		$('.select2').select2();
		
	});
}



function generateQuery_savedReportOutput(){

	var query;
	var enableOrDisableFilterByUser;
	var selectedFieldsForGrid_th;
	jsonObj_filter_data_update = [];
	var selectedFieldsForSelectStatement = selectedFieldsOfQuery_update;
	if(selectedFieldsForSelectStatement != null){
		
		var modal = document.getElementById('loading_msg');
		modal.style.display = "inline-block";
		
		selectedFieldsForSelectStatement = selectedFieldsOfQuery_update;
		selectedFieldsForSelectStatement = selectedFieldsForSelectStatement.trim();
		var selectedFields = selectedFieldsForSelectStatement.split(",");
		
		
		var queryWithOutFilters = "select " + selectedFields + " from dynamic_report ";
		

		var myTab = document.getElementById('filterTable_savedReports_outPut');
		
		if(myTab != null){
			for (i = 1; i < myTab.rows.length; i++) {
				var objCells = myTab.rows.item(i).cells;
				  for (var j = 0; j < objCells.length; j++) {
					if (j == 0) {
						
						var fieldNames = $(objCells.item(j)).find(":selected").val() ;
						fieldNames = fieldNames.split("%");
						field = fieldNames[0];
						
						if(!selectedFields.includes(field)){
							selectedFields[(selectedFields.length)] = field;
						}
					}
				}
			}
		}
		
		query = "select " + selectedFields + " from dynamic_report "
		
		var selectedFieldsString = selectedFields.toString();
		selectedFieldsForGrid_th = selectedFields.slice();
		
		jQuery.post("dynamicReport_populateAvailableColumns.action", {}, function(availabeColumns) {
			var jsonArr = jQuery.parseJSON(availabeColumns);
			
			for (var i = 0; i < jsonArr.length; i++) {
					if(selectedFieldsString.includes(jsonArr[i].field)){
						
						for(x = 0; x < selectedFieldsForGrid_th.length; x++)
						{
							if(selectedFieldsForGrid_th[x] == jsonArr[i].field){
								selectedFieldsForGrid_th[x] = jsonArr[i].label;
							}
						}
						
						
					}
					
				}
				
				
				
				if(myTab != null){
					
					
					var whereStatementString = " where ";
					
					for (i = 1; i < myTab.rows.length; i++) {

						// GET THE CELLS COLLECTION OF THE CURRENT ROW.
						
						 item_filter_data = {}
						
						var objCells = myTab.rows.item(i).cells;
						
						// LOOP THROUGH EACH CELL OF THE CURENT ROW TO READ CELL VALUES.
						for (var j = 0; j < objCells.length; j++) {
							//info.innerHTML = info.innerHTML + ' ' + objCells.item(j).innerHTML;
							if (j == 0) {
								var fieldArray = $(objCells.item(j)).find(":selected").val() ;
								fieldArray = fieldArray.split("%");
								fieldName = fieldArray[0];
								
								if(isEmpty(fieldName)){
									modal.style.display = "none";
									query = "";
									var dialog = $('<p>Please Enter All the Filter values</p>').dialog({
								         buttons: {
								             "OK": function() {
								            	  dialog.dialog('close');
								             },
								             "Execute with out filter":  function() {
								            	 dialog.dialog('close');
								            	 modal.style.display = "inline-block";
								            	 enableOrDisableFilterByUser = "disable";
								            	 populateResultDataTable__savedReportOutput(queryWithOutFilters,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser);
								            	
								            	
								             }
								         }
									 });
									
									
									
									return;
								}else{
									query = query + whereStatementString+ fieldName;
									item_filter_data ["fieldName"] = $(objCells.item(j)).find(":selected").text();
									item_filter_data ["fieldDataType"] = fieldArray[1];
									
								}
								
								
							
							} else if (j == 1) {
								
								// if you did any changes in below  strings should apply the same changes in populateConditionsDropDown() function
								var expression = $(objCells.item(j)).find(":selected").val() ;
								
								
								var field = $(objCells.item(j-1)).find(":selected").val();
								field = field.split("%");
								field = field[0];
								
								if(isEmpty(expression) || isEmpty(field)){
									modal.style.display = "none";
									query = "";
									var dialog = $('<p>Please Enter All  filter  values</p>').dialog({
								         buttons: {
								             "OK": function() {
								            	  dialog.dialog('close');
								             },
								             "Execute with out filter":  function() {
								            	 dialog.dialog('close');
								            	 modal.style.display = "inline-block";
								            	 enableOrDisableFilterByUser = "disable";
								            	 populateResultDataTable__savedReportOutput(queryWithOutFilters,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser);
								            	
								            	
								             }
								         }
									 });
									return;
								}else{
									if( expression == "Equal to"){
										query = query +" = ";
									}else if( expression == "Not Equal to"){
										query = query +" != ";
									}else if( expression == "Contains"){
										query = query +"  like  ";
									}else if( expression == "Not Contains"){
										query = query +" not  like  ";
									}else if( expression == "Greater than"){
										query = query +"  >  ";
									}else if( expression == "Less than"){
										query = query +"  <  ";
									}else if( expression == "Greater than or equal to"){
										query = query +"  >=  ";
									}else if( expression == "Less than or equal to"){
										query = query +"  <=  ";
									}else if( expression == "Between"){
										query = query +"  Between ";
									}else if( expression == "Is empty"){
										//query = query +"  is null or "+field+" = '' ";
										
										query = query +'  is null or '+field+' = "" ';
										
									}else if( expression == "Is not empty"){
										//query = query +"  is not null and "+ field+" != '' ";
										
										query = query +'  is not null and '+ field+' != "" ';
									}
									
									item_filter_data ["expression"] = $(objCells.item(j)).find(":selected").text();
									
								}
								
							
								
								
								
								
							} else if (j == 2) {
								var expr = $(objCells.item(j-1)).find(":selected").val();
								var val = $(objCells.item(j)).find("input").val();
								
								
							
								if( isEmpty(val) && expr != "Is empty" && expr != "Is not empty" ){
									modal.style.display = "none";
									query = "";
									var dialog = $('<p>Please Enter All  filter  values</p>').dialog({
								         buttons: {
								             "OK": function() {
								            	  dialog.dialog('close');
								             },
								             "Execute with out filter":  function() {
								            	 dialog.dialog('close');
								            	 modal.style.display = "inline-block";
								            	 enableOrDisableFilterByUser = "disable";
								            	 populateResultDataTable__savedReportOutput(queryWithOutFilters,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser);
								            	
								            	
								             }
								         }
									 });
									return;
								}else{
									
									if(expr == "Contains"){
										query = query +'"%'+ val+'%"';
										item_filter_data ["val"] = val;
									}else if(expr == "Not Contains"){
										query = query +'"%'+ val+'%"';
										item_filter_data ["val"] = val;
									}else if(expr == "Between"){
										var val = "";
										$(objCells.item(j)).find("input").each(function() {
											val = val+ this.value+"to";
										});
										
										var dates = (val).split("to");
										dates[0] = dates[0].trim();
										dates[1] = dates[1].trim();
										
										query = query +'"'+dates[0] +'" and '+'"'+dates[1]+'"';
										
										item_filter_data ["val"] = '"'+dates[0] +'" and '+'"'+dates[1]+'"';
									}else{
										if(!isEmpty(val)){
											query = query +'"'+ val+'"';
											item_filter_data ["val"] = val;
										}
										
									}
									
								}
								
								
								
								
							} else if (j == 3) {
								
							
									var and_or = $(objCells.item(j)).find(":selected").val();
									whereStatementString = " "+and_or+" ";
									
									if(!isEmpty(and_or) && and_or != undefined){
										and_or = and_or.trim();
										item_filter_data ["and_or"] = and_or;
									}else{
										item_filter_data ["and_or"] = "empty str";
									}
									
									jsonObj_filter_data_update.push(item_filter_data);
								
							}
							
							
							 
							
							
						}
					
						
					}
					
					
					
					
					enableOrDisableFilterByUser = "enable";
					populateResultDataTable__savedReportOutput(query,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser);
					
					
					
			}else{
				enableOrDisableFilterByUser = "disable";
				populateResultDataTable__savedReportOutput(query,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser);
			}
				
		});
		
		
		
		
		//}
	}else{
		alert("Please select the Header Fields");
	}
	
}

function populateResultDataTable__savedReportOutput(query,selectedFieldsForGrid_th,selectedFields,enableOrDisableFilterByUser){
	/*alert("query  -- "+query)
	alert("selectedFieldsForGrid_th  --  "+selectedFieldsForGrid_th)
	alert("selectedFields -- "+selectedFields)
	alert("enableOrDisableFilterByUser -- "+enableOrDisableFilterByUser)*/
	var startIndex = query.indexOf("select");
	var endIndex = query.indexOf("from");
	var selectedFieldsForAction = (query.substring(startIndex+6, endIndex)).trim();
	
	query = query+" GROUP BY "+selectedFieldsForAction;
	
	
	
	if(enableOrDisableFilterByUser == "disable"){
		if(isEmpty(selectedFieldsForGrid_th[selectedFieldsForGrid_th.length-1])){
			selectedFieldsForGrid_th.splice(selectedFieldsForGrid_th.length-1, 1);
		}
		
		if(isEmpty(selectedFields[selectedFields.length-1])){
			selectedFields.splice(selectedFields.length-1, 1);
		}
	}
	
	console.log("-------------saved report output with filter--------------")
	console.log(query)
	finalOutPut_query_update = query;
	
	console.log("---------------------------")
	jQuery.post("dynamicReport_executeQueryAndgetValues.action", {
		query : query,
		selectedFields : selectedFieldsForAction
	}, function(gridData) {

		json = $.parseJSON(gridData);
		
		closeSavedReportOutput();
	
		json = $.parseJSON(gridData);
		
		
		
		var headerDiv =  $("<div/>").addClass("formContainerWrapper");
		headerDiv.append("<h2>"+reportTitle+"<div class='pull-right'><a class='aCollapse'><i style='color:black;' class='fa fa-close' onclick=closeSavedReportOutput();></i></a></div> </h2>");
		//headerDiv.append("<div id='exportButtons_savedReports_outPut' style='float:right;'></div>");
		$("#savedReports_outPut").append(headerDiv);
						
	
		if(!isEmpty(jsonObj_filter_data_update)){
			var insideFilterDiv_savedReports_outPut = $('<div/>').attr({
				id : "insideFilterDiv_savedReports_outPut",
				class : "filterDivStyle"
			});
			
			$("#savedReports_outPut").append(insideFilterDiv_savedReports_outPut);
			
			
			renderFilterFieldsWithAnswers_savedReports_outPut('insideFilterDiv_savedReports_outPut',jsonObj_filter_data_update);
			
			
		}
		
		$("#savedReports_outPut").append("<div id='exportButtons_savedReports_outPut' style='float:right;'></div>");
		
		var table = $("<table/>").addClass("table table-bordered")
						.attr({
							id : "savedReports_outPut_table"
							
							});
				var thead = $("<thead/>");
				var tr = $("<tr/>");
				
				
				finalOutPut_selectedFieldsForGrid_th_update = selectedFields;
				
				for (var obj in selectedFields) {
					tr.append($("<th/>").append(selectedFields[obj]));
					  thead.append(tr);
				}
			
				table.append(thead);
				
				var tbody = $("<tbody/>");
				
				
				for (var i = 0; i < json.length; i++) {
					item = {}
					var tr2 = $("<tr/>");
					
				
					for (var j = 0; j < selectedFields.length; j++) {
						item[selectedFields[j]] = json[i][selectedFields[j]];
						tr2.append($("<td/>").append(item[selectedFields[j]] ));
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
			   }).container().appendTo($('#exportButtons_savedReports_outPut'));
			     $("#savedReports_outPut_table").addClass("dataTableTheme");
			     $("#savedReports_outPut").addClass("appContentWrapper marginBottom ");
			     
			     var modal = document.getElementById('loading_msg');
			     modal.style.display = "none";
			     window.scrollBy(10, 800);
		
	});
	

}

function prior_update_Whole_report(){
	
	if(!isEmpty(reportId_for_update)){
		if(!isEmpty(finalOutPut_query_update)){
			if(!isEmpty(finalOutPut_selectedFieldsForGrid_th_update)){
				if(!isEmpty(jsonObj_filter_data_update)){
					
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
			}
		}else{
			alert("No changes done in  "+reportTitle)
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
									
								});
								$(view_btn).text("View");
								
								var edit_btn = $('<button/>').attr({
									type : 'button',
									class : 'btn btn-warning',
									value : currentRowId,
									onclick : 'editExistingSavedReport(this.value);',
									style : "margin-left:1%;"
								});
								$(edit_btn).text("Edit");
								
								var delete_btn = $('<button/>').attr({
									type : 'button',
									class : 'btn btn-danger',
									value : currentRowId,
									onclick : 'deleteExistingSavedReport(this.value,$(this).parent().parent());',
									style : "margin-left:1%;"
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