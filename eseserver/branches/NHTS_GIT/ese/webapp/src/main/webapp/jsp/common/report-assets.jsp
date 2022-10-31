<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/ese-tags" prefix="e"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<link rel="stylesheet" href="plugins/datepicker/css/datepicker.css">
<link rel="stylesheet" href="plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css">
 <link rel="stylesheet" href="plugins/select2/select2.min.css">
<link rel="stylesheet" href="plugins/bootstrap-daterangepicker/daterangepicker-bs3.css">
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
<script src="plugins/select2/select2.min.js"></script>
<script src="plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script src="plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="js/form-elements.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="plugins/flexi/css/bjqs.css" />
<script src="plugins/flexi/js/bjqs-1.3.js" type="text/javascript"></script>
<script type="text/javascript">
var filterDataReport='';
var postdataReport='';
    $(document).ready(function () {
       FormElements.init();
       //alert("Report Assist");
       var tenant='<s:property value="getCurrentTenantId()"/>';
       //reportData
       if(tenant.trim()=='chetna'){
    	   var adminUser='<s:property value="getIsAdminUser()"/>';
    	   if(adminUser!='true'){
    		   $(".reportData").hide();
    	   }
       }
       
      $('.dp').datepicker()
		.on('changeDate', function(ev){
			$('.dp').datepicker('hide');
		});
		
	$('.inputDateBoxIcon').click(function(event) {
		event.preventDefault();
		//alert($(this).parent().attr('class'));
		$(this).parent().find('input').focus();
	});
	
	$('input[name="daterange"]').daterangepicker();
	
	  $('.select2').select2();
		
		$("#reset").click(function(){
			var url = (window.location.href);
			window.location.href=url;
			$(".select2").val("");
			
		})

    });

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
        addOption(document.getElementById(ctrlName), "Select", "");
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
    
    function resetReportFilter()
	{
		jQuery(".select2").val('');
		jQuery(".select2").select2();
	}
    
	function getSelectData(){
		var data = new Array();
		$("#filter select").each(function(){
			var valu = $(this).val();
			var id=this.name;
			if(!isEmpty(valu)){
				data.push({name:id,value:valu});
			}
		});
		$("#filter input").each(function(){
			var valu = $(this).val();
			var id=this.name;
			if(!isEmpty(valu)){
				data.push({name:id,value:valu});
			}
		});
		
		var jsonObj = JSON.stringify(data);
		$('#filterMapReport').val(jsonObj);
		
	}
	function getFilterData(){
	 postdataReport=$("#postdataReport").val();
	 filterDataReport=$("#filterMapReport").val();
	 if(filterDataReport!=null  && filterDataReport!='null' && !isEmpty(filterDataReport)){
		// jQuery(".select2").val('');
			var jsonObj=JSON.parse(filterDataReport);
			$(jsonObj).each(function(k, v) {
				$("select[name='"+v.name+"']").val(v.value).trigger("change");
			//$("#"+v.name+" option[value='" + v.value.trim() + "']").prop("selected", true);
				//$("#"+v.name+"").val(v.value).trigger('change');
				
				$( "input[name$='"+v.name+"']" ).val(v.value).trigger("change");
			});
			var d1=	jQuery('#daterange').val();
			var d2= d1.split("-");
			var value1= d2[0];
			var value2= d2[1];
		document.getElementById("hiddenStartDate").value=value1;
		
		document.getElementById("hiddenEndDate").value=value2;
		 //jQuery(".select2").select2();
		}
	
	}
	
	function getSelectUpdateData(){
		var data = new Array();
		$("#filter select").each(function(){
			var valu = $(this).val();
			var id=this.name;
			if(!isEmpty(valu)){
				data.push({name:id,value:valu});
			}
		});
		$("#filter input").each(function(){
			var valu = $(this).val();
			var id=this.name;
			if(!isEmpty(valu)){
				data.push({name:id,value:valu});
			}
		});
		
		var jsonObj = JSON.stringify(data);
		$('#filterMapReportUpdate').val(jsonObj);
		
	}
    

</script>