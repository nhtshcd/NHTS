/////////////////////////////////////////////////////////////////////   Variables   ////////////////////////////////////////////////////////////////////////////////////////
var sectionCount = 0;
var textBoxCount = 0;
var radioGroupCount = 0;
var textAreaCount = 0;
var checkBoxGroupCount = 0;
var datePickerCount = 0;
var dropDownCount = 0;
var multiSelectCount = 0;
var labelCount = 0;
var listCount = 0;
var addBtnCount = 0;
var drop_insideSection_flag = true;
//////////////////////////////////////////////////////////////////   COMMON FUNCTIONS  /////////////////////////////////////////////////////////////////////////////////////

//	<-----------------------------------------------------------------	Drag and Drop related	-------------------------------------------------------------------->
function drag(ev) {
	ev.dataTransfer.setData("componentType", ev.target.getAttribute("data-type"));
	drop_insideSection_flag = true;
}

function allowDrop(ev) {
	ev.preventDefault();
}

function drop(ev) {
	ev.preventDefault();
	var data = ev.dataTransfer.getData("componentType");
	createComponent(data);
	//ev.target.appendChild(document.getElementById(data)); //this code move the dragged item to dropped container permanently
}

function drop_insideSection(ev,sectionId){
	if(drop_insideSection_flag == true){
		ev.preventDefault();
		var dropped_element = ev.dataTransfer.getData("componentType");
		//ev.target.appendChild(document.getElementById(data)); //this code move the dragged item to dropped container permanently
		var sectionDom = $("#"+sectionId);
		var backendData = sectionDom.data("backendjsonobject");
		createComponentInsideSection(backendData.id,dropped_element);
	}
	
}

function drop_insideList(ev,listId){

	drop_insideSection_flag = false;
	ev.preventDefault();
	var dropped_element = ev.dataTransfer.getData("componentType");
	var listDom = $("#"+listId);
	var backendData = listDom.data("backendjsonobject");
	
	createComponentInsideList(listId,dropped_element);
	
	
	/* var options;
		if( backendData != undefined){
			if(backendData.options != undefined){
				  options = jQuery.parseJSON(backendData.options);
				  createTextBoxInsideList(options.componentName,dropped_element);
			}
		}*/
	
	
	
}

//	<-----------------------------------------------------------------	Drag and Drop related	-------------------------------------------------------------------->

function createComponent(data) {
	if(data == "section"){
		createSection();
	}
}


function createComponentInsideSection(id,droppedElement){

	if(droppedElement == "textBox"){
		createTextBoxInsideSection(id);
	}else if(droppedElement == "radio-group"){
		createRadioButtonInsideSection(id);
	}else if(droppedElement == "textarea"){
		createTextAreaInsideSection(id);
	}else if(droppedElement == "checkbox-group"){
		createCheckBoxGroupInsideSection(id);
	}else if(droppedElement == "date-picker"){
		createDatePickerInsideSection(id);
	}else if(droppedElement == "drop-down"){
		createDropDownInsideSection(id);
	}else if(droppedElement == "multiSelect"){
		createMultiSelectInsideSection(id);
	}else if(droppedElement == "label"){
		createLabelInsideSection(id);
	}else if(droppedElement == "list"){
		createListInsideSection(id);
	}
	
}

function createComponentInsideList(listId,droppedElement){
	
	if(droppedElement == "textBox"){
		createTextBoxInsideList(listId);
	}else if(droppedElement == "radio-group"){
		createRadioButtonInsideList(listId);
	}else if(droppedElement == "textarea"){
		createTextAreaInsideList(listId);
	}else if(droppedElement == "checkbox-group"){
		createCheckBoxGroupInsideList(listId);
	}else if(droppedElement == "date-picker"){
		createDatePickerInsideList(listId);
		$('input[id^="datePicker"]').datepicker({
			dateFormat : "dd-mm-yy",
			changeMonth : true,
			changeYear : true
		});
	}else if(droppedElement == "drop-down"){
		createDropDownInsideList(listId);
	}else if(droppedElement == "multiSelect"){
		createMultiSelectInsideList(listId);
	}else if(droppedElement == "label"){
		createLabelInsideList(listId);
	}
}

function getFlexform_item(){
	var flexform_item = jQuery('<div/>', {
		class : "flexform-item"
	});
	
	return $(flexform_item);
}

function getLabel(txt){
	var label = jQuery('<label/>', {
		text : txt
	});
	
	return $(label);
}

function getForm_element(){
	var form_element = jQuery('<div/>', {
		class : "form-element"
	});
	return $(form_element);
}


function getComponentFieldsJson(){
	var options = {};
	options["componentName"] = "";
	options["componentType"] = "";
	options["componentMaxLength"]	 = "";
	options["defaultValue"]	 = "";
	options["isRequired"]	 = "";
	options["dataFormat"]	 = "";
	options["dataFormat"]	 = "";
	options["validation"]	 = "";
	options["catalogueType"]	 = "";
	options["parentDepen"]	 = "";
	options["dependencyKey"]	 = "";
	options["referenceId"]	 = "";
	options["formula"]	 = "";
	options["catDependencyKey"]	 = "";
	options["parentDependencyKey"]	 = "";
	options["mBeforeInsert"]	 = "";
	options["mAfterInsert"]	 = "";
	options["isMobileAvail"]	 = "1";
	return options;
}


function makeThisHilighted(obj){
	$(".hilightField").removeClass("hilightField");
	$(".hilightlist").removeClass("hilightlist");
	
	
	if($(obj).attr("class") != undefined){
		if(($(obj).attr("class")).includes("List")){
			$(obj).parent().addClass("hilightField"); // for List
		}else{
			$(obj).parent().parent().addClass("hilightField");
		}
	}else{
		$(obj).parent().parent().addClass("hilightField");
	}
}

function view_catalogueValuesByCatalogueType(){
	
	if($("#catalogueTypez_id").val() != 0 && !isEmpty($("#catalogueTypez_id").val())){
		$.ajax({
		    url: "dynamicUI_listCataloguesByType.action",
		    type: 'POST',
		    async: true,
		    data:{catalogueType:$("#catalogueTypez_id").val()},
		    success: function (result) {
		   
		    	if(Object.keys(result).length != 0){
		    		
		    		var table = jQuery('<table/>', {
			    		class : "table table-striped table-dark"
			    	});
			    	
			    	var thead = jQuery('<thead/>', {});
			    	
			    	var tr = jQuery('<tr/>', {});
			    	var th1 = jQuery('<th/>', {
			    		text : "S.no"
			    	});
			    	
			    	var th2 = jQuery('<th/>', {
			    		text : "Code"
			    	});
			    	
			    	var th3 = jQuery('<th/>', {
			    		text : "Name"
			    	});
			    	
			    	$(tr).append($(th1));
			    	$(tr).append($(th2));
			    	$(tr).append($(th3));
			    	$(thead).append($(tr));
			    	
			    	var tbody = jQuery('<tbody/>', {});
			    	var count = 1;
			    	for ( var code in result) {
			    		
			    		var row = jQuery('<tr/>', {});
			    		
			    		var td1 = jQuery('<td/>', {
				    		text : count
				    	});
			    		
			    		var td2 = jQuery('<td/>', {
				    		text : code
				    	});
			    		
			    		var td3 = jQuery('<td/>', {
				    		text : result[code]
				    	});
			    		
			    		$(row).append($(td1));
				    	$(row).append($(td2));
				    	$(row).append($(td3));
				    	$(tbody).append($(row));
				    	count = count+1;
			    	}
			    	
			    	$(table).append($($(thead)));
			    	$(table).append($($(tbody)));
			    	
			    	$("#dialog").dialog({
			            modal: true,
			            title:$("#catalogueTypez_id option:selected").text(),
			            width: "800px",
			            height: 300,
			            hide: {
			                effect: "fold",
			                duration: 800
			              },
			            open: function (event, ui) {
			            	 var markup = $(table);
			                 $(this).html(markup);
			              
			            }
			        });
		    	  $(".ui-dialog").find(".ui-widget-header").css("background", "#41A1C9");
		    	
		    	}else{
		    		$("#dialog").dialog({
			            modal: true,
			            title:$("#catalogueTypez_id option:selected").text(),
			            width: 300,
			            height: 150,
			            hide: {
			                effect: "shake",
			                duration: 200
			              },
			            open: function (event, ui) {
			            	 var markup = "No data available";
			                 $(this).html(markup);
			              
			            }
			        });
		    	  $(".ui-dialog").find(".ui-widget-header").css("background", "#41A1C9");
		    		
		    	}
		    	  
		    }
		});
	}else{
		alert("Please choose catalogue master")
	}
}

function add_New_CatalogueType(){
	
	var div = jQuery('<div/>', {});
	
	var textBox = jQuery('<input/>', {
		id		:	"newCatalogueMasterName"
	});
	
	var question = makeBootstrapContent("Catalogue Master Name",$(textBox));
	
	var submit_btn = $('<button/>',{
		class	:	"btn btn-success",
		text	:	"Create",
		style	:	"border-radius: 7px;margin-top: 10px;background-color: #2a3f54;",
		onclick : 	"create_New_CatalogueType();"
	});
	
	$(div).append($(question));
	$(div).append($(submit_btn));
	
	$("#dialog").dialog({
        modal: true,
        title:"Add New Catalogue Master",
        width: 350,
        height: 150,
        hide: {
            effect: "shake",
            duration: 200
          },
        open: function (event, ui) {
        	 var markup = $(div);
             $(this).html(markup);
          
        }
    });
  $(".ui-dialog").find(".ui-widget-header").css("background", "#41A1C9");
  $("#newCatalogueMasterName").focus();
}

function create_New_CatalogueType(){
	//alert($("#newCatalogueMasterName").val())
	$.ajax({
	    url: "dynamicUI_createCatalogueMaster.action",
	    type: 'POST',
	    async: true,
	    data:{catalogueMasterName:$("#newCatalogueMasterName").val()},
	    success: function (result) {
	    	$("#dialog").dialog('close');
	    	showPopup(result.msg,result.title);
	    	reloadCataloguetypezDropdown();
	    }
	});
}

function reloadCataloguetypezDropdown(){
	$.ajax({
	    url: "dynamicUI_getCatalogueTypez.action",
	    type: 'POST',
	    async: true,
	    success: function (result) {
	    	$('#catalogueTypez_id').empty()
	    	
	    	$('<option />', {
    			value : "0",
    			text : "Select"
    		}).appendTo("#catalogueTypez_id");
	    	
	    	for ( var type in result) {
	    		$('<option />', {
	    			value : type,
	    			text : result[type]
	    		}).appendTo($("#catalogueTypez_id"));
	    	}
	    }
	});
}

//<----------------------------------------------------------------------------- delete related	-------------------------------------------------------->

function deleteSection(sectionId){
	var userAction = confirm("Confirm to delete");
    if (userAction == true) {
    	$("#"+sectionId).parent().remove();
    }
}

function getConfirmationAndDelete(id){
	
	$.confirm({
	    title: ' Confirm to delete '+$("#"+id).parent().parent().text(),
	   // content: $("#"+id).parent().parent().text(),
	    buttons: {
	        confirm: function () {
	        	$("#"+id).parent().parent().remove();
	        },
	        cancel: function () {
	        	
	        }/*,
	        somethingElse: {
	            text: 'Something else',
	            btnClass: 'btn-blue',
	            keys: ['enter', 'shift'],
	            action: function(){
	                $.alert('Something else?');
	            }
	        }*/
	    }
	});
	
}


function deleteHTML(id){
	var className = $("#"+id).parent().attr('class');
	if(isEmpty(className) || className == undefined || className != "form-element"){
		className = $("#"+id).parent().parent().parent().parent().attr('class');
	}
	
	var userAction = confirm("Confirm to delete");
    if (userAction == true) {
    	if(className == "form-element"){
    		$("#"+id).parent().parent().remove();
    	}else if(className == "table table-bordered"){
    		$("#"+id).parent().remove();
    	}
    	$("#Edit").html('');
    	$('.nav-tabs a[href="#addQuestion"]').tab('show');
    }
}
//<------------------------------------------------------------------------Inside alert content related	-------------------------------------------------------->

function getAlertContent_textBox(id){
	
	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	
	 var questionNameValue;
	 var isRequired_value;
	 var validation;
	 var m_before_after_insert = {};
	 
	 var maxLen_arguments = {};
	 maxLen_arguments["id"] 	=  "textBox_maxLen_id";
	 maxLen_arguments["label"]  =  "Max length";
	 maxLen_arguments["validation"]  =  "Number";
	 maxLen_arguments["componentMaxLength"]  =  "";
	 
	 var defaultValue_arguments = {};
	 defaultValue_arguments["id"] 	=  "textBox_defaultValue_id";
	 defaultValue_arguments["label"]  =  "Default Value";
	 
	if(JSON.stringify(backendData) != "{}" && backendData != undefined){
		if(backendData.options != undefined){
			 var json = jQuery.parseJSON(backendData.options);
			 
			 questionNameValue  				=  json.componentName;
			 maxLen_arguments["value"]  	   	=  json.componentMaxLength;
			 isRequired_value				  	=  json.isRequired;
			 defaultValue_arguments["value"] 	=  json.defaultValue;
			 validation 					=  json.validation;
			 m_before_after_insert["before"]	=  json.mBeforeInsert;
			 m_before_after_insert["after"]		=  json.mAfterInsert;
			
		}
	}
	
	var div = jQuery('<div/>', {});
	
	var questionName = getQuestionNameComponent(questionNameValue);
	var maxLen = getTextBox_alertContent(maxLen_arguments);
	var isRequired = getIsRequiredComponent(isRequired_value);
	var defaultValue = getTextBox_alertContent(defaultValue_arguments);
	
	
	var submitBtn = getSubmitBtn("textBox",id);
	var deleteBtn = getDeleteBtn(id);
	
	$(div).append($(questionName));
	$(div).append($(maxLen));
	$(div).append($(isRequired));
	$(div).append($(defaultValue));
	$(div).append(getTextBoxValidationTypeDropDownComponent(validation));
	$(div).append(get_m_before_after_insert_fields(m_before_after_insert));
	$(div).append($(submitBtn));
	$(div).append($(deleteBtn));
	
	return $(div);
}



function getAlertContent_textArea(id){

	
	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	
	 var questionNameValue;
	 var isRequired_value;
	 var validation;
	 var m_before_after_insert = {};
	 
	 var maxLen_arguments = {};
	 maxLen_arguments["id"] 	=  "textBox_maxLen_id";
	 maxLen_arguments["label"]  =  "Max length";
	 maxLen_arguments["validation"]  =  "Number";
	 maxLen_arguments["componentMaxLength"]  =  "";
	 
	 var defaultValue_arguments = {};
	 defaultValue_arguments["id"] 	=  "textBox_defaultValue_id";
	 defaultValue_arguments["label"]  =  "Default Value";
	 
	if(JSON.stringify(backendData) != "{}" && backendData != undefined){
		if(backendData.options != undefined){
			 var json = jQuery.parseJSON(backendData.options);
			 
			 questionNameValue  				=  json.componentName;
			 maxLen_arguments["value"]  	   	=  json.componentMaxLength;
			 isRequired_value				  	=  json.isRequired;
			 defaultValue_arguments["value"] 	=  json.defaultValue;
			 validation 					=  json.validation;
			 m_before_after_insert["before"]	=  json.mBeforeInsert;
			 m_before_after_insert["after"]		=  json.mAfterInsert;
			
		}
	}
	
	var div = jQuery('<div/>', {});
	
	var questionName = getQuestionNameComponent(questionNameValue);
	var maxLen = getTextBox_alertContent(maxLen_arguments);
	var isRequired = getIsRequiredComponent(isRequired_value);
	var defaultValue = getTextBox_alertContent(defaultValue_arguments);
	
	
	var submitBtn = getSubmitBtn("textArea",id); 
	var deleteBtn = getDeleteBtn(id);
	$(div).append($(questionName));
	$(div).append($(maxLen));
	$(div).append($(isRequired));
	$(div).append($(defaultValue));
	$(div).append(getTextBoxValidationTypeDropDownComponent(validation));
	$(div).append(get_m_before_after_insert_fields(m_before_after_insert));
	$(div).append($(submitBtn));
	$(div).append($(deleteBtn));
	
	return $(div);

}

function getAlertContent_DropDown(id){
	
	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	
	 var questionNameValue;
	 var isRequired_value;
	 var m_before_after_insert = {};
	 var catalogueType; 
	 
	if(JSON.stringify(backendData) != "{}" && backendData != undefined){
		if(backendData.options != undefined){
			 var json = jQuery.parseJSON(backendData.options);
			 
			 questionNameValue  				=  json.componentName;
			 isRequired_value				  	=  json.isRequired;
			 catalogueType						=  json.catalogueType;
			 m_before_after_insert["before"]	=  json.mBeforeInsert;
			 m_before_after_insert["after"]		=  json.mAfterInsert;
			
		}
	}
	
	var div = jQuery('<div/>', {});
	
	var questionName = getQuestionNameComponent(questionNameValue);
	var isRequired = getIsRequiredComponent(isRequired_value);
	var submitBtn = getSubmitBtn("dropdown",id); 
	var deleteBtn = getDeleteBtn(id);
	$(div).append($(questionName));
	$(div).append($(isRequired));
	$(div).append(getCatalogueTypezDropdown(catalogueType));
	$(div).append(get_m_before_after_insert_fields(m_before_after_insert));
	$(div).append($(submitBtn));
	$(div).append($(deleteBtn));
	
	return $(div);
}

function getAlertContent_MultiDropDown(id){
	
	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	
	 var questionNameValue;
	 var isRequired_value;
	 var m_before_after_insert = {};
	 var catalogueType; 
	 
	if(JSON.stringify(backendData) != "{}" && backendData != undefined){
		if(backendData.options != undefined){
			 var json = jQuery.parseJSON(backendData.options);
			 
			 questionNameValue  				=  json.componentName;
			 isRequired_value				  	=  json.isRequired;
			 catalogueType						=  json.catalogueType;
			 m_before_after_insert["before"]	=  json.mBeforeInsert;
			 m_before_after_insert["after"]		=  json.mAfterInsert;
			
		}
	}
	
	var div = jQuery('<div/>', {});
	
	var questionName = getQuestionNameComponent(questionNameValue);
	var isRequired = getIsRequiredComponent(isRequired_value);
	var submitBtn = getSubmitBtn("MultiDropDown",id); 
	var deleteBtn = getDeleteBtn(id);
	$(div).append($(questionName));
	$(div).append($(isRequired));
	$(div).append(getCatalogueTypezDropdown(catalogueType));
	$(div).append(get_m_before_after_insert_fields(m_before_after_insert));
	$(div).append($(submitBtn));
	$(div).append($(deleteBtn));
	
	return $(div);
}

function getAlertContent_RadioGroup(id){
	
	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	
	 var questionNameValue;
	 var isRequired_value;
	 var catalogueType; 
	 var m_before_after_insert = {};
	
	 
	if(JSON.stringify(backendData) != "{}" && backendData != undefined){
		if(backendData.options != undefined){
			 var json = jQuery.parseJSON(backendData.options);
			 
			 questionNameValue  				=  json.componentName;
			 isRequired_value				  	=  json.isRequired;
			 catalogueType						=  json.catalogueType;
			 m_before_after_insert["before"]	=  json.mBeforeInsert;
			 m_before_after_insert["after"]		=  json.mAfterInsert;
			
		}
	}
	
	var div = jQuery('<div/>', {});
	
	var questionName = getQuestionNameComponent(questionNameValue);
	var isRequired = getIsRequiredComponent(isRequired_value);
	var submitBtn = getSubmitBtn("RadioGroup",id); 
	var deleteBtn = getDeleteBtn(id);
	$(div).append($(questionName));
	$(div).append($(isRequired));
	$(div).append(getCatalogueTypezDropdown(catalogueType));
	$(div).append(get_m_before_after_insert_fields(m_before_after_insert));
	$(div).append($(submitBtn));
	$(div).append($(deleteBtn));
	
	return $(div);
}

function getAlertContent_CheckBoxGroup(id){
	
	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	
	 var questionNameValue;
	 var isRequired_value;
	 var catalogueType;
	 var m_before_after_insert = {};
	
	 
	if(JSON.stringify(backendData) != "{}" && backendData != undefined){
		if(backendData.options != undefined){
			 var json = jQuery.parseJSON(backendData.options);
			 
			 questionNameValue  				=  json.componentName;
			 isRequired_value				  	=  json.isRequired;
			 catalogueType						=  json.catalogueType;
			 m_before_after_insert["before"]	=  json.mBeforeInsert;
			 m_before_after_insert["after"]		=  json.mAfterInsert;
			
		}
	}
	
	var div = jQuery('<div/>', {});
	
	var questionName = getQuestionNameComponent(questionNameValue);
	var isRequired = getIsRequiredComponent(isRequired_value);
	var submitBtn = getSubmitBtn("CheckBoxGroup",id); 
	var deleteBtn = getDeleteBtn(id);
	$(div).append($(questionName));
	$(div).append($(isRequired));
	$(div).append(getCatalogueTypezDropdown(catalogueType));
	$(div).append(get_m_before_after_insert_fields(m_before_after_insert));
	$(div).append($(submitBtn));
	$(div).append($(deleteBtn));
	return $(div);
}

function getAlertContent_datePicker(id){

	
	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	
	 var questionNameValue;
	 var isRequired_value;
	
	 var m_before_after_insert = {};
	 
	 var dataFormat_arguments = {};
	 dataFormat_arguments["id"] 	=  "textBox_dataFormat_id";
	 dataFormat_arguments["label"]  =  "Data Format";
	 
	if(JSON.stringify(backendData) != "{}" && backendData != undefined){
		if(backendData.options != undefined){
			 var json = jQuery.parseJSON(backendData.options);
			 
			 questionNameValue  				=  json.componentName;
			 isRequired_value				  	=  json.isRequired;
			 dataFormat_arguments["value"] 		=  json.dataFormat;
			 m_before_after_insert["before"]	=  json.mBeforeInsert;
			 m_before_after_insert["after"]		=  json.mAfterInsert;
			
		}
	}
	
	var div = jQuery('<div/>', {});
	
	var questionName = getQuestionNameComponent(questionNameValue);
	var isRequired = getIsRequiredComponent(isRequired_value);
	var dataFormat = getTextBox_alertContent(dataFormat_arguments);
	
	
	var submitBtn = getSubmitBtn("DatePicker",id); 
	var deleteBtn = getDeleteBtn(id);
	$(div).append($(questionName));
	$(div).append($(isRequired));
	$(div).append($(dataFormat));
	$(div).append(get_m_before_after_insert_fields(m_before_after_insert));
	$(div).append($(submitBtn));
	$(div).append($(deleteBtn));
	return $(div);

}

function getAlertContent_Label(id){
	alert("check_getAlertContent_Label");// work is pending
}

/*function getAlertContent_radioGroup(){
	
	return getTextBox_alertContent("componentName","Name");
	
}
*/


//<----------------------------------------------------------- [specifications (dynamic_fields_config) ] 	------------------------------------------------------>



function makeBootstrapContent(label,component){
	
	var flexform_item =  getFlexform_item();
	var label = getLabel(label);
	var form_element = getForm_element();
	
	$(flexform_item).css("width", "100%");
	
	$(form_element).append($(component));
	$(flexform_item).append($(label));
	$(flexform_item).append($(form_element));
	
	
	
	return $(flexform_item);
}



function getTextBox_alertContent(arguments){
	var textBox = jQuery('<input/>', {
		id		:	arguments.id
	});
	
	if(arguments.validation == "Number"){
		$(textBox).attr("type","Number");
	}else{
		$(textBox).attr("type","text");
	}
	
	if(!isEmpty(arguments.componentMaxLength)){
		$(textBox).attr("maxlength",Number(arguments.componentMaxLength));
	}
	
	if(!isEmpty(arguments.value)){
		$(textBox).val(arguments.value);
	}
	
	
	
	
	return makeBootstrapContent(arguments.label,$(textBox));
}

function getCheckBox_alertContent(arguments){
	
	var checkBox = $('<input/>',{
		id		:	arguments.id,
		type	: 'checkbox'
	});
	

	if(arguments.value == "required"){
		$(checkBox).attr("checked","true");
	}
	
	return makeBootstrapContent(arguments.label,$(checkBox));
	
}

function getQuestionNameComponent(existingValue){
	 var questionName_arguments = {};
	 questionName_arguments["id"] 	=  "questionName_id";
	 questionName_arguments["label"]  =  "Question Name";
	 if(!isEmpty(existingValue) && existingValue != undefined){
		 questionName_arguments["value"] = existingValue;
	 }
	
	 return getTextBox_alertContent(questionName_arguments);
	
	 
}

function getCatalogueTypezDropdown(existingValue) {
	
	var catalogueTypez = jQuery('<select/>', {
		id : "catalogueTypez_id",
		class : "select2"
	});
	
	
	$.ajax({
	    url: "dynamicUI_getCatalogueTypez.action",
	    type: 'POST',
	    async: false,
	    success: function (result) {
	    	$('<option />', {
    			value : "0",
    			text : "Select"
    		}).appendTo(catalogueTypez);
	    	
	    	for ( var type in result) {
	    		if ((existingValue != undefined) && (!isEmpty(existingValue.trim())) && (type == existingValue.trim())) {
	    			$('<option />', {
		    			value : type,
		    			text : result[type],
		    			selected : true
		    		}).appendTo(catalogueTypez);
	    		} else {
	    			$('<option />', {
		    			value : type,
		    			text : result[type]
		    		}).appendTo(catalogueTypez);
	    		}
	    		
	    		
	    	}
	    }
	});
	
	
	
	
	var catalogueTypezDiv = jQuery('<div/>', {});
	
	var view_btn = $('<button/>',{
		class	:	"btn btn-info",
		text	:	"View",
		style	:	"border-radius: 7px;margin-top: 10px;",//background-color: #2a3f54;
		onclick : 	"view_catalogueValuesByCatalogueType();"
	});
	
	var add_btn = $('<button/>',{
		class	:	"btn btn-success",
		text	:	"Add",
		style	:	"border-radius: 7px;margin-top: 10px;",//background-color: #2a3f54;
		onclick : 	"add_New_CatalogueType();"
	});
	
	$(catalogueTypezDiv).append($(catalogueTypez));
	$(catalogueTypezDiv).append($(add_btn));
	$(catalogueTypezDiv).append($(view_btn));
	
	
	return makeBootstrapContent("catalogueTypez", $(catalogueTypezDiv));
}

function getIsRequiredComponent(existingValue){
	var isRequired_arguments = {};
	 isRequired_arguments["id"] 	=  "checkBox_isRequired_id";
	 isRequired_arguments["label"]  =  "Mandatory field";
	 if(!isEmpty(existingValue)){
		 isRequired_arguments["value"]  	=  existingValue;
	 }
	
	 return getCheckBox_alertContent(isRequired_arguments);
}

function getTextBoxValidationTypeDropDownComponent(existingValue){
	var validationDropDown = jQuery('<select/>', {
		id		:	"textBox_ValidationType",
		class	: 	"select2"
	});
	
	 var validationOptions = {
								'0'	: 'Select',	
			 					'1' : 'Alphabet',//isAlphabet(event)
								'2' : 'Number',	 //isNumber(event)
								'3' : 'Email',	 //isEmail(event)
								'4' : 'Decimal', //isDecimal(event)
								'5' : 'AlphaNumeric'//isAlphaNumeric(event)
								
							 }

	for ( var type in validationOptions) {
		$('<option />', {
			value : type,
			text : validationOptions[type]
		}).appendTo(validationDropDown);
	}
	
	if(existingValue != undefined && !isEmpty(existingValue.trim())){
		$(validationDropDown).val(existingValue.trim()).trigger('change');
	}else{
		$(validationDropDown).val('0').trigger('change');
	} 
		
	
	 return makeBootstrapContent("Validation",$(validationDropDown));
}

function get_m_before_after_insert_fields(json){
	
	var m_before_arguments = {};
	
	m_before_arguments["id"] 	=  "beforeInsert_id";
	m_before_arguments["label"]  =  "Before Insert (M)";
	
	if(json.before != undefined && !isEmpty(json.before)){
		m_before_arguments["value"]  =  json.before;
	}
	
	var m_before_component = getTextBox_alertContent(m_before_arguments);
	////////////////
	var m_after_arguments = {};
	
	m_after_arguments["id"] 	=  "afterInsert_id";
	m_after_arguments["label"]  =  "After Insert (M)";
	
	if(json.after != undefined && !isEmpty(json.after)){
		m_after_arguments["value"]  =  json.after;
	}
	
	var m_after_component = getTextBox_alertContent(m_after_arguments);
	////////////////
	var div = $('<div/>').attr({});
	$(div).append($(m_before_component));
	$(div).append($(m_after_component));
	return $(div);
}

function getSubmitBtn(componentType,id){
	var btn = $('<button/>',{
		class	:	"btn btn-warning",
		text	:	"Submit data",
		style	:	"border-radius: 7px;margin-top: 10px;background-color: #2a3f54;"
	});
	
	if(componentType == "textBox"){
		$(btn).attr("onclick","validateTextBoxOptions('"+id+"')");
	}else if(componentType == "dropdown"){
		$(btn).attr("onclick","validateDropDownOptions('"+id+"')");
	}else if(componentType == "CheckBoxGroup"){
		$(btn).attr("onclick","validateCheckBoxGroupOptions('"+id+"')");
	}else if(componentType == "RadioGroup"){
		$(btn).attr("onclick","validateRadioGroupOptions('"+id+"')");
	}else if(componentType =="textArea"){
		$(btn).attr("onclick","validateTextAreaOptions('"+id+"')");
	}else if(componentType == "MultiDropDown"){
		$(btn).attr("onclick","validateMultiDropDownOptions('"+id+"')");
	}else if(componentType == "DatePicker"){
		$(btn).attr("onclick","validateDatePicker('"+id+"')");
	}
	
	return $(btn);
	
}

function getDeleteBtn(id){
	var btn = $('<button/>',{
		class	:	"btn btn-danger",
		text	:	"Delete",
		style	:	"border-radius: 7px;margin-top: 10px;background-color: #2a3f54;margin-left: 6px;",
		onclick : "deleteHTML('"+id+"')"
	});
return $(btn);
	
}

/////////////////////////////////////////////////////////////    Implementation of Components      ////////////////////////////////////////////////////////////////////////

function createSection(){
	$("#targetContainer").find("#sortableSection").append(getSectionDiv());
}

function createTextBoxInsideSection(id){
	$("#"+id).find(".flexform").append(getTextBox());
}

function createRadioButtonInsideSection(id){
	$("#"+id).find(".flexform").append(getRadioButton());
}

function createTextAreaInsideSection(id){
	$("#"+id).find(".flexform").append(getTextArea());
}

function createCheckBoxGroupInsideSection(id){
	$("#"+id).find(".flexform").append(getCheckBoxGroup());
}

function createDatePickerInsideSection(id){
	$("#"+id).find(".flexform").append(getDatePicker());
}

function createDropDownInsideSection(id){
	$("#"+id).find(".flexform").append(getDropDown());
}

function createMultiSelectInsideSection(id){
	$("#"+id).find(".flexform").append(getMultiSelect());
}

function createLabelInsideSection(id){
	$("#"+id).find(".flexform").append(getLabelComponent()); // This is for formula
}

function createListInsideSection(id){
	$("#"+id).find(".flexform").append(getListComponent());
}

//Drop inside List
function change_component_design_for_list(component){
	var td = $('<td/>',{});
	
	var label;
	$.each( component.find('label'), function( i, el ) {
		$(el).addClass("height40"); 
		$(el).css("font-weight","Bold");
		label = el.outerHTML;
	});
	//alert(label)
	var html_component;
	var form_element = component.find(".form-element").get(0).outerHTML;
	$.each( $(form_element).find('input,textArea,select,label'), function( i, el ) {
		 //alert( ""+el.outerHTML ); //html element like textbox,text area...
		if((el.id).includes("textBox")){
			$(el).addClass("form-control textList");
		}if((el.id).includes("radio")){
			$(el).addClass("form-control radioList");
		}if((el.id).includes("textArea")){
			$(el).addClass("form-control textAreaList");
		}if((el.id).includes("checkBoxGroup")){
			$(el).addClass("form-control checkboxList");
		}if((el.id).includes("datePicker")){
			$(el).addClass("form-control dateList");
		}if((el.id).includes("dropDown")){
			$(el).addClass("form-control dropDownList");
		}if((el.id).includes("multiSelect")){
			$(el).addClass("form-control multiDropDownList");
		}if((el.id).includes("label")){
			$(el).addClass("form-control labelList");
		}
		
		html_component = el.outerHTML;
	});
	
	
	
	//$(td).append('<label class="height40"><b>Product name</b></label>');
	//$(td).append('<input type="text" class="form-control" id="textBox_1" onclick="getTextBoxOptions(this.id);makeThisHilighted(this);" data-backendjsonobject="{}">');
	$(td).append(label);
	$(td).append(html_component);
	
	return $(td);
}

function createTextBoxInsideList(id){
	$("#"+id).find("tbody").find("tr").find("td:last-child").before(change_component_design_for_list($(getTextBox())));
}

function createRadioButtonInsideList(id){
	$("#"+id).find("tbody").find("tr").find("td:last-child").before(change_component_design_for_list($(getRadioButton())));
}

function createTextAreaInsideList(id){
	$("#"+id).find("tbody").find("tr").find("td:last-child").before(change_component_design_for_list($(getTextArea())));
}

function createCheckBoxGroupInsideList(id){
	$("#"+id).find("tbody").find("tr").find("td:last-child").before(change_component_design_for_list($(getCheckBoxGroup())));
}

function createDatePickerInsideList(id){
	$("#"+id).find("tbody").find("tr").find("td:last-child").before(change_component_design_for_list($(getDatePicker())));
}

function createDropDownInsideList(id){
	$("#"+id).find("tbody").find("tr").find("td:last-child").before(change_component_design_for_list($(getDropDown())));
}

function createMultiSelectInsideList(id){
	$("#"+id).find("tbody").find("tr").find("td:last-child").before(change_component_design_for_list($(getMultiSelect())));
}

function createLabelInsideList(id){
	$("#"+id).find("tbody").find("tr").find("td:last-child").before(change_component_design_for_list($(getLabelComponent())));
}
///////////////////////////////////////////////////////////////////////    Master FUNCTIONS    ////////////////////////////////////////////////////////////////////////////

function getSectionDiv(){
	sectionCount = sectionCount+1;
	var sectionId = "section_"+sectionCount;
	var backendData = {};
	backendData["id"] = sectionId;
	backendData["h2"] = sectionId;
	
	var appContentWrapper = $('<div/>',{
		class : "appContentWrapper marginBottom",
		style	: "margin-left: -6.5%;margin-right: -1%;"
			
	});
	
	var formContainerWrapper = $('<div/>',{
		class : "formContainerWrapper",
		id : sectionId,
		ondragover : "allowDrop(event)",
		ondrop : "drop_insideSection(event,'"+sectionId+"')",
		'data-backendjsonobject' : JSON.stringify(backendData)
	});
	
	var h2 = jQuery('<h2/>', {
		 text	: sectionId,
		 onclick : "getLabelNameFromSectionHeading(this,'"+sectionId+"')"
	});
	

	var delete_span = jQuery('<span/>', {
		class	: "glyphicon glyphicon-trash",
		style	: "margin-left: 98%;",
		onclick : "deleteSection('"+sectionId+"')"
	});
	

	
	var flexform = $('<div/>',{
		class : "flexform"
	});
	
	
	$(formContainerWrapper).append($(delete_span));
	$(formContainerWrapper).append($(h2));
	$(formContainerWrapper).append($(flexform));
	$(appContentWrapper).append($(formContainerWrapper));
	$(flexform).sortable();
	
	return $(appContentWrapper);
	
}


function getTextBox(){
	var backendData = {};
	textBoxCount = textBoxCount + 1;
	var textBoxId = "textBox_"+textBoxCount;
	
	var flexform_item =  getFlexform_item();
	var label = getLabel(textBoxId);
	var form_element = getForm_element();
	
	var textBox = $('<input/>',{
		type : 'text',
		id :	textBoxId,
		onClick : 'getTextBoxOptions(this.id);makeThisHilighted(this);',
		'data-backendjsonobject' : JSON.stringify(backendData)
	});
	
	$(form_element).append($(textBox));
	$(flexform_item).append($(label));
	$(flexform_item).append($(form_element));
	
	
	return $(flexform_item);
	
}


function getRadioButton(){
	var backendData = {};
	radioGroupCount = radioGroupCount + 1;
	var radioGroupId = "radioGroup_"+radioGroupCount;
	
	var flexform_item =  getFlexform_item();
	var label = getLabel(radioGroupId);
	var form_element = getForm_element();
	
	var radio = $('<input/>',{
		id		:	radioGroupId,
		type	: 'radio',
		onClick	:	'getRadioGroupOptions(this.id);makeThisHilighted(this);',
		'data-backendjsonobject' : JSON.stringify(backendData)
	});
	
	$(form_element).append($(radio));
	$(flexform_item).append($(label));
	$(flexform_item).append($(form_element));
	
	
	return $(flexform_item);
	
}


function getTextArea(){
	var backendData = {};
	textAreaCount = textAreaCount + 1;
	var textAreaId = "textArea_"+textAreaCount;
	
	var flexform_item =  getFlexform_item();
	var label = getLabel(textAreaId);
	var form_element = getForm_element();
	
	
	var textArea = $('<textarea/>',{
		id		:	textAreaId,
		type	: 'text',
		onClick	:	'getTextAreaOptions(this.id);makeThisHilighted(this);',
		'data-backendjsonobject' : JSON.stringify(backendData)
	});
	
	$(form_element).append($(textArea));
	$(flexform_item).append($(label));
	$(flexform_item).append($(form_element));
	
	return $(flexform_item);
}

function getCheckBoxGroup(){
	var backendData = {};
	checkBoxGroupCount = checkBoxGroupCount + 1;
	var checkBoxGroupId = "checkBoxGroup_"+checkBoxGroupCount;
	
	var flexform_item =  getFlexform_item();
	var label = getLabel(checkBoxGroupId);
	var form_element = getForm_element();
	
	var checkBoxGroup = $('<input/>',{
		id		:	checkBoxGroupId,
		type	: 'checkbox',
		onClick	:	'getCheckBoxGroupOptions(this.id);makeThisHilighted(this);',
		'data-backendjsonobject' : JSON.stringify(backendData)
	});
	
	$(form_element).append($(checkBoxGroup));
	$(flexform_item).append($(label));
	$(flexform_item).append($(form_element));
	
	return $(flexform_item);
}

function getDatePicker(){
	var backendData = {};
	datePickerCount = datePickerCount + 1;
	var datePickerId = "datePicker_"+datePickerCount;
	
	var flexform_item =  getFlexform_item();
	var label = getLabel(datePickerId);
	var form_element = getForm_element();
	
	var datePicker = $('<input/>',{
		id		:	datePickerId,
		type	: 'text',
		readonly : 'readonly',
		'data-date-format' : "dd-mm-yy",
		onClick	:	'getDatePickerOptions(this.id);makeThisHilighted(this);',
		'data-backendjsonobject' : JSON.stringify(backendData)
	});
	
	$(datePicker).datepicker({
		dateFormat : "dd-mm-yy",
		changeMonth : true,
		changeYear : true
	});
	
	$(form_element).append($(datePicker));
	$(flexform_item).append($(label));
	$(flexform_item).append($(form_element));
	
	return $(flexform_item);
}

function getDropDown(){

	var backendData = {};
	dropDownCount = dropDownCount + 1;
	var dropDownId = "dropDown_"+dropDownCount;
	
	var flexform_item =  getFlexform_item();
	var label = getLabel(dropDownId);
	var form_element = getForm_element();
	

	var dropDown = $('<select/>',{
		id		:	dropDownId,
		//class : "select2",
		onClick	:	'getDropDownOptions(this.id);makeThisHilighted(this);',
		'data-backendjsonobject' : JSON.stringify(backendData)
	});
	
	var option = "<option value=''>Select </option>";
	$(dropDown).append(option);
	

	$(form_element).append($(dropDown));
	$(flexform_item).append($(label));
	$(flexform_item).append($(form_element));
	
	return $(flexform_item);

}

function getMultiSelect(){


	var backendData = {};
	multiSelectCount = multiSelectCount + 1;
	var multiSelectId = "multiSelect_"+multiSelectCount;
	
	var flexform_item =  getFlexform_item();
	var label = getLabel(multiSelectId);
	var form_element = getForm_element();
	

	var multiSelect = $('<select/>',{
		id		:	multiSelectId,
		//class 	: "select2",
		onClick	:	'getMultiSelectOptions(this.id);makeThisHilighted(this);',
		'data-backendjsonobject' : JSON.stringify(backendData),
		multiple : "multiple"
	});
	
	var option = "<option value=''>Select </option>";
	$(multiSelect).append(option);
	

	$(form_element).append($(multiSelect));
	$(flexform_item).append($(label));
	$(flexform_item).append($(form_element));
	
	return $(flexform_item);


}

function getLabelComponent(){
	var backendData = {};
	labelCount = labelCount + 1;
	var labelId = "label_"+labelCount;
	
	var flexform_item =  getFlexform_item();
	var label = getLabel(labelId);
	var form_element = getForm_element();
	

	var labelComponent = $('<label/>',{
		id		:	labelId,
		onClick	:	'getLabelOptions(this.id);makeThisHilighted(this);',
		'data-backendjsonobject' : JSON.stringify(backendData),
		text : "Label Component"
	});
	
	$(form_element).append($(labelComponent));
	$(flexform_item).append($(label));
	$(flexform_item).append($(form_element));
	
	return $(flexform_item);
}

function getListComponent(){
	listCount = listCount + 1;
	var listId = "List_"+listCount;
	
	
	
	var backendData = {};
	var options = getComponentFieldsJson();
	
	options["componentName"] = listId;
	options["componentType"] = "8";
	backendData["options"] = JSON.stringify(options);
	
	
	var table = $('<table/>',{
		id		:	listId,
		class 	: "table table-bordered",
		//onClick	:	'getLabelOptions(this.id);makeThisHilighted(this);',
		'data-backendjsonobject' : JSON.stringify(backendData),
		ondragover : "allowDrop(event)",
		ondrop : "drop_insideList(event,'"+listId+"')",
		//text : "List Component"
	});
	
	var tbody = $('<tbody/>',{
		
	});
	
	var tr = $('<tr/>',{
		
	});
	
	
	var td = $('<td/>',{
		
	});
	
	$(td).append('<label style="" class="height40"><b>Action</b></label><br>');
	
	
	//$(td).append('<button type="button" class="btn btn-sts" onclick="addDynamicList(this)">Add</button>');
	$(td).append(getAddBtn(listId));
	$(tr).append($(td));
	$(tbody).append($(tr));
	$(table).append($(tbody));

	return $(table);
}

function getAddBtn(listId){
	
	addBtnCount = addBtnCount + 1;
	var backendData = {};
	var options = getComponentFieldsJson();
	
	options["componentName"] = "Add";
	options["componentType"] = "10";
	options["referenceId"] = listId;
	backendData["options"] = JSON.stringify(options);
		
		
	var btn = $('<button/>',{
		class	:	"btn btn-sts",
		id 		:	"addBtn"+addBtnCount,
		text	:	"Add",
		onclick : "addDynamicList(this)",
		//style	:	"border-radius: 7px;margin-top: 10px;background-color: #2a3f54;"
		'data-backendjsonobject' : JSON.stringify(backendData)
	});
	
	return $(btn);
}


///////////////////////////////////////////////////////       Getting Backend data (like mandatory fields,name)      ///////////////////////////////////////////////////////




function getTextBoxOptions(id){
	//populateAlert(id,getAlertContent_textBox(id),"textBox");
	$("#Edit").html('');
	$("#Edit").html(getAlertContent_textBox(id));
	$(".select2").select2();
	$('.nav-tabs a[href="#Edit"]').tab('show');

}

function getTextAreaOptions(id){
	//populateAlert(id,getAlertContent_textBox(id),"textarea");
	$("#Edit").html('');
	$("#Edit").html(getAlertContent_textArea(id));
	$(".select2").select2();
	$('.nav-tabs a[href="#Edit"]').tab('show');
}

function getRadioGroupOptions(id){
	//populateAlert(id,getAlertContent_radioGroup(),"radio-group");
	$("#Edit").html('');
	$("#Edit").html(getAlertContent_RadioGroup(id));
	$(".select2").select2();
	$('.nav-tabs a[href="#Edit"]').tab('show');
}

function getCheckBoxGroupOptions(id){
	//populateAlert(id,getAlertContent_radioGroup(),"checkbox-group");
	$("#Edit").html('');
	$("#Edit").html(getAlertContent_CheckBoxGroup(id));
	$(".select2").select2();
	$('.nav-tabs a[href="#Edit"]').tab('show');
}

function getDatePickerOptions(id){
	//populateAlert(id,getAlertContent_radioGroup(),"date-picker");
	$("#Edit").html('');
	$("#Edit").html(getAlertContent_datePicker(id));
	$('.nav-tabs a[href="#Edit"]').tab('show');
}

function getDropDownOptions(id){
	//populateAlert(id,getAlertContent_radioGroup(),"drop-down");
	$("#Edit").html('');
	$("#Edit").html(getAlertContent_DropDown(id));
	$(".select2").select2();
	$('.nav-tabs a[href="#Edit"]').tab('show');
}

function getMultiSelectOptions(id){
	//populateAlert(id,getAlertContent_radioGroup(),"multiSelect");
	$("#Edit").html('');
	$("#Edit").html(getAlertContent_MultiDropDown(id));
	$(".select2").select2();
	$('.nav-tabs a[href="#Edit"]').tab('show');
}

function getLabelOptions(id){
	//populateAlert(id,getAlertContent_radioGroup(),"label");
	$("#Edit").html('');
	$("#Edit").html(getAlertContent_Label(id));
	$('.nav-tabs a[href="#Edit"]').tab('show');
}



//<-----------------------------------------------------------------	label name related	-------------------------------------------------------------------->
function getLabelNameFromAlertDialog(obj,id){

	
	var tempTextBox =  jQuery('<input/>', {
		type	:	"text",
		placeholder	:	$(obj).text()
	});
	
	$(obj).html($(tempTextBox));
	$(tempTextBox).focus();
	
	$(tempTextBox).blur(function () {
		var labelName = $(this).val();
		if(!isEmpty($(this).val())){
			 labelName = $(this).val();
		}else{
			labelName = $(this).attr('placeholder');
		}
		
			
			var dom = $("#"+id);
			var backendData = dom.data("backendjsonobject");
	     	 
			if( backendData != undefined){
				if(backendData.options != undefined){
					 var json = jQuery.parseJSON(backendData.options);
						json["componentName"]  =  labelName;
						backendData["options"] = JSON.stringify(json);
						$(dom).attr({
			            		"data-backendjsonobject" : JSON.stringify(backendData)
			            });
				}else{
					var json = getComponentFieldsJson();
					json["componentName"]  =  labelName;
					backendData["options"] = JSON.stringify(json);
					$(dom).attr({
		            		"data-backendjsonobject" : JSON.stringify(backendData)
		            });
				}
			}
			
	     	
	         labelName = JSON.stringify(jQuery.parseJSON($("#"+id).data("backendjsonobject").options).componentName);
	         
	         if(!isEmpty(labelName)){
	         	labelName = labelName.trim();
	             if(labelName != '""'){
	             	labelName = labelName.substring(1, labelName.length-1)
		            $(dom.parent().parent()).find("label").text(labelName);
	             	
	             	 var p =  jQuery('<p/>', {
	     	     		text	:	labelName,
	     	     		onclick : getLabelNameFromAlertDialog(this,id)
	     	     	});
	     	         
	             	$(this).parent().append($(p));
	             	$(this).remove();
	             	
	             }
	         }
  
	});
	
}


function getLabelNameFromSectionHeading(obj,id){
	
	var existingLabelName;
	
	if(!isEmpty($(obj).text())){
		existingLabelName = $(obj).text();
	}else{
		existingLabelName = $(obj).find("input").attr('placeholder');
	}
	
	var tempTextBox =  jQuery('<input/>', {
		type	:	"text",
		placeholder	:	existingLabelName
	});
	
	$(obj).html($(tempTextBox));
	$(tempTextBox).focus();
	
	$(tempTextBox).blur(function () {
		var labelName;
		
		if(!isEmpty($(this).val())){
			 labelName = $(this).val();
		}else{
			labelName = $(this).attr('placeholder');
		}
		
			
		var sectionDom = $("#"+id);
		var backendData = sectionDom.data("backendjsonobject");
		backendData["h2"] = labelName;
  	$(sectionDom).attr({
  		"data-backendjsonobject" : JSON.stringify(backendData)
  	});
  	
  	$(this).parent().text(labelName);
  });
	
}
//<-----------------------------------------------------------------	label name related	-------------------------------------------------------------------->



//Below feature is hidden
//<-------------------------	 alert dialog related [now hided bcoz these feature implemented in separate div id known as (id="Edit")]		--------------------------->

/*function populateAlert(id,alertContent,componentType){
	
	var dom = $("#"+id);
	var backendData = dom.data("backendjsonobject");
	var existingLabelName = $(dom.parent().parent()).find("label").text();
	var result = "";
	
	$.confirm({
	    //title: existingLabelName,
	  	title: "<p onclick=getLabelNameFromAlertDialog(this,'"+id+"');>"+existingLabelName+"</p>",
	    content: alertContent,
	    closeIcon: true,
	    columnClass: 'medium',
	    type: 'red',
	    
	    buttons: {
	        formSubmit: {
	            text: 'Save',
	            btnClass: 'btn-blue',
	            action: function () {
	               
	            	if(componentType == "textBox"){
	            		result = validateTextBoxOptions(this.$content,dom);
	            		if(result == false){
	            			return false;
	            		}
	            		
	            	}else if(componentType == "textarea"){
	            		
	            	}else if(componentType == "radio-group"){
	            		
	            	}else if(componentType == "checkbox-group"){
	            		
	            	}else if(componentType == "date-picker"){
	            		
	            	}else if(componentType == "drop-down"){
	            		
	            	}else if(componentType == "multiSelect"){
	            		
	            	}else if(componentType == "label"){
	            		
	            	}
	            	
	            	
	                backendData["options"] = JSON.stringify(result);
	                $(dom).attr({
	            		"data-backendjsonobject" : JSON.stringify(backendData)
	            	});
	                
	                //Below code change the label name dynamically when alert submitted
	                var labelName = JSON.stringify(jQuery.parseJSON($("#"+id).data("backendjsonobject").options).componentName);
	                if(!isEmpty(labelName)){
	                	labelName = labelName.trim();
		                if(labelName != '""'){
		                	labelName = labelName.substring(1, labelName.length-1)
			                $(dom.parent().parent()).find("label").text(labelName);
		                }
	                }
	                 //alert(JSON.stringify(jQuery.parseJSON($("#"+id).data("backendjsonobject").options).componentName));
	            }
	        },
	        //cancel: function () {
	            //close
	       // } ,
	        somethingElse: {
	            text: 'Delete',
	            btnClass: 'btn-red',
	           // keys: ['enter', 'shift'],
	            action: function(){
	               getConfirmationAndDelete(id);
	            }
	        }
	        
	    },
	    onContentReady: function () {
	        // bind to events
	        var jc = this;
	        this.$content.find('form').on('submit', function (e) {
	            // if the user submits the form by pressing enter in the field.
	            e.preventDefault();
	            jc.$$formSubmit.trigger('click'); // reference the button and click it
	        });
	        
	     }
	});
}*/

//Below function comes under validate options if above code is enabled
/*function validateTextBoxOptions(obj,dom){

var backendData = dom.data("backendjsonobject");
	 
if( backendData != undefined){
	if(backendData.options != undefined){
		options = jQuery.parseJSON(backendData.options);
			
	}else{
		 options = getComponentFieldsJson();
		
	}
}



var maxLen = obj.find('#textBox_maxLen_id').val();
var validation 	=	obj.find('#validationType').val();

if(isEmpty(maxLen)){
    $.alert('provide a valid Maximum length');
    return false;
}else{
	options["componentMaxLength"] = maxLen;
}

if(obj.find('#checkBox_isRequired_id').prop("checked")){
	options["isRequired"] = "required";
}


if(isEmpty(validation)){
	 $.alert('choose a validation type');
     return false;
}else{
	options["validation"] = validation;
}


// $.alert('Your name is ' + name);

return options;
}*/

