///////////////////////////////////////////////////////////// 		 Common		 ////////////////////////////////////////////////////////////////
function validateQuestionName(){
	if(!isEmpty($("#questionName_id").val().trim())){
		return $("#questionName_id").val().trim();
	}else{
		return "";
	}
}

function validate_m_before_after_insert_fields(options){
	
	if(!isEmpty($("#beforeInsert_id").val().trim()) ){
		options["mBeforeInsert"] = $("#beforeInsert_id").val().trim();
	}else{
		options["mBeforeInsert"] = "";
	}
	
	if(!isEmpty($("#afterInsert_id").val().trim()) ){
		options["mAfterInsert"] = $("#afterInsert_id").val().trim();
	}else{
		options["mAfterInsert"] = "";
	}
	
	return options;
}

////////////////////////////////////////////////////////////// 		 Validate Options		 ////////////////////////////////////////////////////////////////

function updateQuestionName(dom,questionName,id,options){
	if($(dom).attr("class") != undefined){
		if(($(dom).attr("class")).includes("List")){
			$("#"+id).parent().find("label").text(questionName);//setting question name in ui [For textbox inside List ] 
			var tableObj  = $("#"+id).parent().parent().parent().parent();
			var listID = $(tableObj).attr("id");
			if(!isEmpty(listID)){
				options["referenceId"]	 = listID;
			}else{
				options["referenceId"] = "";
			}
			
		}else{
			$("#"+id).parent().parent().find("label").text(questionName);//setting question name in ui   [For textbox inside section outside list ] 
			options["referenceId"] = "";
		}
	}else{
		$("#"+id).parent().parent().find("label").text(questionName);//setting question name in ui   [For textbox inside section outside list ] 
		options["referenceId"] = "";
	}
	
	return options;
}

function validateTextBoxOptions(id){
	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	 var options;
	if( backendData != undefined){
		if(backendData.options != undefined){
			  options = jQuery.parseJSON(backendData.options);
		}else{
			options = getComponentFieldsJson();
		}
	}
	
	options["componentType"] = "1"; // 1 = Text Box
	
	var questionName = validateQuestionName();
	if(!isEmpty(questionName)){
		options["componentName"] = questionName;
		options = updateQuestionName(dom,questionName,id,options);
	}else{
		options["componentName"] = "";
	}
	
	
	if(!isEmpty($("#textBox_maxLen_id").val().trim())){
		options["componentMaxLength"] = $("#textBox_maxLen_id").val().trim();
	}else{
		options["componentMaxLength"] = "";
	}
	
	if($('#checkBox_isRequired_id').prop("checked")){
	    options["isRequired"] = "required";
	 }else{
		options["isRequired"] = "notRequired";
	 }
	
	if(!isEmpty($("#textBox_defaultValue_id").val().trim()) ){
		options["defaultValue"] = $("#textBox_defaultValue_id").val().trim();
	}else{
		options["defaultValue"] = "";
	}
	  
	
	if(!isEmpty($("#textBox_ValidationType").val().trim()) && $("#textBox_ValidationType").val() != 0 ){
		options["validation"] = $("#textBox_ValidationType").val().toString();
	}else{
		options["validation"] = "";
	}
	
	options = validate_m_before_after_insert_fields(options);
	
	  backendData["options"] = JSON.stringify(options);
		$(dom).attr({
      		"data-backendjsonobject" : JSON.stringify(backendData)
      });
		
		$('.nav-tabs a[href="#addQuestion"]').tab('show');
}



function validateTextAreaOptions(id){
	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	 var options;
	if( backendData != undefined){
		if(backendData.options != undefined){
			  options = jQuery.parseJSON(backendData.options);
		}else{
			options = getComponentFieldsJson();
		}
	}
	
	options["componentType"] = "5"; 
	
	var questionName = validateQuestionName();
	if(!isEmpty(questionName)){
		options["componentName"] = questionName;
		options = updateQuestionName(dom,questionName,id,options);
	}else{
		options["componentName"] = "";
	}
	
	
	if(!isEmpty($("#textBox_maxLen_id").val().trim())){
		options["componentMaxLength"] = $("#textBox_maxLen_id").val().trim();
	}else{
		options["componentMaxLength"] = "";
	}
	
	if($('#checkBox_isRequired_id').prop("checked")){
	    options["isRequired"] = "required";
	 }else{
		options["isRequired"] = "notRequired";
	 }
	
	if(!isEmpty($("#textBox_defaultValue_id").val().trim()) ){
		options["defaultValue"] = $("#textBox_defaultValue_id").val().trim();
	}else{
		options["defaultValue"] = "";
	}
	  
	
	if(!isEmpty($("#textBox_ValidationType").val().trim()) && $("#textBox_ValidationType").val() != 0 ){
		options["validation"] = $("#textBox_ValidationType").val().toString();
	}else{
		options["validation"] = "";
	}
	
	options = validate_m_before_after_insert_fields(options);
	
	  backendData["options"] = JSON.stringify(options);
		$(dom).attr({
      		"data-backendjsonobject" : JSON.stringify(backendData)
      });
		
		$('.nav-tabs a[href="#addQuestion"]').tab('show');
}

function validateDropDownOptions(id){

	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	 var options;
	if( backendData != undefined){
		if(backendData.options != undefined){
			  options = jQuery.parseJSON(backendData.options);
		}else{
			options = getComponentFieldsJson();
		}
	}
	
	options["componentType"] = "4"; // 1 = Text Box
	
	var questionName = validateQuestionName();
	if(!isEmpty(questionName)){
		options["componentName"] = questionName;
		//$("#"+id).parent().parent().find("label").text(questionName);//setting question name in ui
		options = updateQuestionName(dom,questionName,id,options);
	}else{
		options["componentName"] = "";
	}
	
	if($('#checkBox_isRequired_id').prop("checked")){
	    options["isRequired"] = "required";
	 }else{
		options["isRequired"] = "notRequired";
	 }
	
	if(!isEmpty($("#catalogueTypez_id").val().trim()) && $("#catalogueTypez_id").val() != 0 ){
		options["catalogueType"] = $("#catalogueTypez_id").val().toString();
		populateCatalogueValues(id,$("#catalogueTypez_id").val().toString());
	}else{
		options["catalogueType"] = "";
	}
	
	options = validate_m_before_after_insert_fields(options);
	
	  backendData["options"] = JSON.stringify(options);
		$(dom).attr({
      		"data-backendjsonobject" : JSON.stringify(backendData)
      });
		
		$('.nav-tabs a[href="#addQuestion"]').tab('show');
		

}

function validateMultiDropDownOptions(id){

	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	 var options;
	if( backendData != undefined){
		if(backendData.options != undefined){
			  options = jQuery.parseJSON(backendData.options);
		}else{
			options = getComponentFieldsJson();
		}
	}
	
	options["componentType"] = "9"; 
	
	var questionName = validateQuestionName();
	if(!isEmpty(questionName)){
		options["componentName"] = questionName;
		//$("#"+id).parent().parent().find("label").text(questionName);//setting question name in ui
		options = updateQuestionName(dom,questionName,id,options);
	}else{
		options["componentName"] = "";
	}
	
	if($('#checkBox_isRequired_id').prop("checked")){
	    options["isRequired"] = "required";
	 }else{
		options["isRequired"] = "notRequired";
	 }
	
	if(!isEmpty($("#catalogueTypez_id").val().trim()) && $("#catalogueTypez_id").val() != 0 ){
		options["catalogueType"] = $("#catalogueTypez_id").val().toString();
		populateCatalogueValues(id,$("#catalogueTypez_id").val().toString());
	}else{
		options["catalogueType"] = "";
	}
	
	options = validate_m_before_after_insert_fields(options);
	
	  backendData["options"] = JSON.stringify(options);
		$(dom).attr({
      		"data-backendjsonobject" : JSON.stringify(backendData)
      });
		
		$('.nav-tabs a[href="#addQuestion"]').tab('show');
		

}

function validateCheckBoxGroupOptions(id){


	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	 var options;
	if( backendData != undefined){
		if(backendData.options != undefined){
			  options = jQuery.parseJSON(backendData.options);
		}else{
			options = getComponentFieldsJson();
		}
	}
	
	options["componentType"] = "6"; // Check box
	
	var questionName = validateQuestionName();
	if(!isEmpty(questionName)){
		options["componentName"] = questionName;
		//$("#"+id).parent().parent().find("label").text(questionName);//setting question name in ui
		options = updateQuestionName(dom,questionName,id,options);
	}else{
		options["componentName"] = "";
	}
	
	if($('#checkBox_isRequired_id').prop("checked")){
	    options["isRequired"] = "required";
	 }else{
		options["isRequired"] = "notRequired";
	 }
	
	if(!isEmpty($("#catalogueTypez_id").val().trim()) && $("#catalogueTypez_id").val() != 0 ){
		options["catalogueType"] = $("#catalogueTypez_id").val().toString();
	}else{
		options["catalogueType"] = "";
	}
	
	options = validate_m_before_after_insert_fields(options);
	
	  backendData["options"] = JSON.stringify(options);
		$(dom).attr({
      		"data-backendjsonobject" : JSON.stringify(backendData)
      });
		
		$('.nav-tabs a[href="#addQuestion"]').tab('show');


}

function validateRadioGroupOptions(id){



	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	 var options;
	if( backendData != undefined){
		if(backendData.options != undefined){
			  options = jQuery.parseJSON(backendData.options);
		}else{
			options = getComponentFieldsJson();
		}
	}
	
	options["componentType"] = "2"; // Radio
	
	var questionName = validateQuestionName();
	if(!isEmpty(questionName)){
		options["componentName"] = questionName;
		//$("#"+id).parent().parent().find("label").text(questionName);//setting question name in ui
		options = updateQuestionName(dom,questionName,id,options);
	}else{
		options["componentName"] = "";
	}
	
	if($('#checkBox_isRequired_id').prop("checked")){
	    options["isRequired"] = "required";
	 }else{
		options["isRequired"] = "notRequired";
	 }
	
	if(!isEmpty($("#catalogueTypez_id").val().trim()) && $("#catalogueTypez_id").val() != 0 ){
		options["catalogueType"] = $("#catalogueTypez_id").val().toString();
	}else{
		options["catalogueType"] = "";
	}
	
	options = validate_m_before_after_insert_fields(options);
	
	  backendData["options"] = JSON.stringify(options);
		$(dom).attr({
      		"data-backendjsonobject" : JSON.stringify(backendData)
      });
		
		$('.nav-tabs a[href="#addQuestion"]').tab('show');
}

function validateDatePicker(id){

	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	 var options;
	if( backendData != undefined){
		if(backendData.options != undefined){
			  options = jQuery.parseJSON(backendData.options);
		}else{
			options = getComponentFieldsJson();
		}
	}
	
	options["componentType"] = "3"; // 1 = Text Box
	
	var questionName = validateQuestionName();
	if(!isEmpty(questionName)){
		options["componentName"] = questionName;
		//$("#"+id).parent().parent().find("label").text(questionName);//setting question name in ui
		options = updateQuestionName(dom,questionName,id,options);
	}else{
		options["componentName"] = "";
	}
	
	
	if($('#checkBox_isRequired_id').prop("checked")){
	    options["isRequired"] = "required";
	 }else{
		options["isRequired"] = "notRequired";
	 }
	
	if(!isEmpty($("#textBox_dataFormat_id").val().trim()) ){
		options["dataFormat"] = $("#textBox_dataFormat_id").val().trim();
	}else{
		options["dataFormat"] = "";
	}
	  
	options = validate_m_before_after_insert_fields(options);
	
	  backendData["options"] = JSON.stringify(options);
		$(dom).attr({
      		"data-backendjsonobject" : JSON.stringify(backendData)
      });
		
		$('.nav-tabs a[href="#addQuestion"]').tab('show');

}

/////////////////////////////////////////////////////////	After Validation Completed		//////////////////////////////////////////////////////

function populateCatalogueValues(id,type){
	 $.post("creationTool_populateCatalogueValuesByType",{selectedType:type},function(result){
		 insertOptionsWithoutSelect2(id,jQuery.parseJSON(result)); // If we use select2 older dropdown hided then new dropdown will select, so we cant get older drop down backernd-data and onclick functionality
	}); 
}

function insertOptionsWithoutSelect2(ctrlName, jsonArr) {
	document.getElementById(ctrlName).length = 0;
	 addOption(document.getElementById(ctrlName), "Select", "");
	for (var i = 0; i < jsonArr.length; i++) {
		addOption(document.getElementById(ctrlName), jsonArr[i].name,
				jsonArr[i].id);
	}
}

