<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/ese-tags" prefix="e"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!--<script language="javascript" src="javascript/datepicker_full.js"></script>-->
<!--<link href="css/main_table.css" rel="stylesheet" type="text/css" />-->
<!-- <link rel="stylesheet" href="plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"> -->
 <link rel="stylesheet" href="plugins/select2/select2.min.css"> 
<link rel="stylesheet" href="plugins/datepicker/css/datepicker.css">
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="plugins/select2/select2.min.js"></script>
 <script src="plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
 <script src="plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script src="js/form-elements.js"></script>
<script src="js/form-validation.js"></script> 

<%-- <script src="js/autoNumeric.js"></script> 
<script src="js/autoLoader.js"></script>  --%>

<script type="text/javascript">
    $(document).ready(function () {

        $('.upercls').bind("keyup mouseleave", function (evt) {
            var txt = $(this).val();
            $(this).val(txt.replace(/^(.)|\s(.)/g, function ($1) {
                return $1.toUpperCase( );
            }));
        });
        $(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent"/>');
       
        $('#reset').on('click', function (e) {
            if (document.form.sno.value !== '' && document.form.sno.value !== "null")
            {
                if (confirm('Qué se quiere reinicializar')) {
                    result = true;
                }
                if (result === true) {
                    document.form.sno.value = '';
                }
            }
        });
        
        FormElements.init();
        
   	    $('.select2').select2();

    });


    function submitForm() {
        document.form.temp.value = "yes";
        document.form.submit();
    }

    function submitPreferenceForm() {
    <sec:authorize ifAllGranted="system.prefernces.update">
        document.prefernceupdateform.action = "prefernce_update.action";
        document.getElementById('temp').value = "yes";
        document.prefernceupdateform.submit();
    </sec:authorize>
        document.form.submit();
    }

    function addOption(selectbox, text, value)
    {
        var optn = document.createElement("OPTION");
        optn.text = text;
        optn.value = value;
        selectbox.options.add(optn);
    }

    function populateValues(responseValue) {
        var start = responseValue.indexOf("[");
        var end = responseValue.indexOf("]");
        var resultString = responseValue.substring(start + 1, end);
        return resultString.split(',');
    }

    function insertOptions(ctrlName, jsonArr) {
        document.getElementById(ctrlName).length = 0;
        addOption(document.getElementById(ctrlName), "Select", '');
        for (var i = 0; i < jsonArr.length; i++) {
            addOption(document.getElementById(ctrlName), jsonArr[i].name, jsonArr[i].id);
        }
       
        var id="#"+ctrlName;
        jQuery(id).select2();
    }
    
    
    function resetSelect2(idArray)
    {
    	for(var i=0;i<idArray.length;i++)
    	{
    		var id="#"+idArray[i];
    		$(""+id+" > option").removeAttr("selected");
           	$(""+id+"").trigger("change");
    	}
       
    }

    function getElementValueById(id) {
		var val = $("#" + id).val();
		return val;
	}
    
    function getElementValueByText(id) {
		var val = $("#" + id+" option:selected").text();
		return val;
	}
    
    function getRadioValueByName(name){
    	var val = $('input[type=radio][name='+name+']:checked').val();
    	return val;
    }
    
    function getCheckBoxValueByName(name){
    	 var checkboxes = document.getElementsByName(name);
    	 var checkboxesChecked = [];
    	
    	 for (var i=0; i<checkboxes.length; i++) {
    	     // And stick the checked ones onto an array...
    	     if (checkboxes[i].checked) {
    	        checkboxesChecked.push(checkboxes[i].value);
    	     }
    	  }
    	 
    	 return checkboxesChecked.length > 0 ? checkboxesChecked : null;
    }
    
    function processRadioAlign(){
    	 var tableBody = jQuery(".appContentWrapper");
    	 $.each(tableBody, function(index, value) {
    		 var radio = $(this).find('input[type=radio]');
    		 $.each(radio.parent(),function(k,v){
    			//console.log($(this));
    			 return false;
    		 });
    	 });
    }
    

    function isNumber(evt) {

    	evt = (evt) ? evt : window.event;
    	var charCode = (evt.which) ? evt.which : evt.keyCode;
    	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
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

    
    function showPopup(content,msg)
    {
    	//alert(content);
    	//alert(msg);
    	$(function () {
    	   
    	        $("#dialog").dialog({
    	            modal: true,
    	            title: msg,
    	            width: 300,
    	            height: 150,
    	            hide: {
    	                effect: "explode",
    	                duration: 100
    	              },
    	            open: function (event, ui) {
    	            	 var markup = content;
    	                 $(this).html(markup);
    	                setTimeout(function () {
    	                    $("#dialog").dialog("close");
    	                }, 2700);
    	            }
    	            
    	           
    	              
    	        });
    	        if(msg=='Error')
    	        	 $(".ui-dialog").find(".ui-widget-header").css("background", "red");
              	else
              		 $(".ui-dialog").find(".ui-widget-header").css("background", "#41A1C9");
              	 
    	        
    	        
    	  
    	});
    } 
</script>