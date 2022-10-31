function populateFarmerFields(){
	jQuery.post("dynamicDataImport_populateFarmerFields.action", {}, function(farmerFields) {
		insertOptions("farmerFieldsDropDown",jQuery.parseJSON(farmerFields));
	});
}

function insertOptions(ctrlName, jsonArr) {
	document.getElementById(ctrlName).length = 0;
	
	addOption(document.getElementById(ctrlName), "Select","");
	for (var i = 0; i < jsonArr.length; i++) {
		addOption(document.getElementById(ctrlName), (jsonArr[i].fieldName).trim(),
				(jsonArr[i].fieldName).trim());
	}
	
	$(".select2").select2();
}

function populateTable(){
	var farmerField = $("#farmerFieldsDropDown").val();
	var farmerFieldType = $("#farmerFieldsTypeDropDown").val();
	
	
	if(!isEmpty(farmerField) && !isEmpty(farmerFieldType)){
		$("#selectedFarmerFields").find('thead').find('tr').append($("<th/>").append(farmerField));
		$("#selectedFarmerFields").find('tbody').find('tr').append($("<th/>").append('<button class="btn btn-success"  onclick="deleteColumn($(this).parent())">Download sheet</button>'));
	}
	
	$('#selectedFarmerFields').DataTable().destroy();
	var table = $('#selectedFarmerFields').DataTable();
    var buttons = new $.fn.dataTable.Buttons(table, {
   	  buttons: [
	                 {
	                     extend: 'excel',
	                     title: 'Distribution Report ( '+'<s:property value="farmer.firstName"/>'+' )',
	                     messageTop: '',
	                     messageBottom: '',
	                     
	                 },
	                 {
	                     extend: 'pdf',
	                     title: 'Distribution Report  ( '+'<s:property value="farmer.firstName"/>'+' )',
	                     messageTop: '',
	                     messageBottom: '',
	                    
	                 }
	             ]
    }).container().appendTo($('#exportXlsDiv')); 
		
    
    
		$("#selectedFarmerFields_length").hide();
		$("#selectedFarmerFields_filter").hide();
		$("#selectedFarmerFields_paginate").hide();
		$("#selectedFarmerFields_info").hide();
}

function deleteColumn(obj){
	var heads = $("#selectedFarmerFields").find('th,td');
	$(heads[0]).remove();
}