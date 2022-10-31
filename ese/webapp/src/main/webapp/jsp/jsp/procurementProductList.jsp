<%@ include file="/jsp/common/grid-assets.jsp"%>
<link rel="stylesheet" href="plugins/select2/select2.min.css">



<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.js"></script>
<script src="js/jquery.ajaxfileupload.js"></script>
<script src="plugins/select2/select2.min.js"></script>
<head>
<META name="decorator" content="swithlayout">
</head>
<style>
.imgcontainer {
	position: relative;
}

.imgoverlay {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0);
	transition: background 0.5s ease;
}

.imgcontainer:hover .imgoverlay {
	display: block;
	background: rgba(0, 0, 0, .3);
}

.imgclass {
	width: 100%;
	height: auto;
	max-height: 240px;
	left: 0;
}

.imgtitle {
	position: absolute;
	width: 500px;
	left: 0;
	top: 120px;
	font-weight: 700;
	font-size: 30px;
	text-align: center;
	text-transform: uppercase;
	color: white;
	z-index: 1;
	transition: top .5s ease;
}

.imgcontainer:hover .imgtitle {
	top: 90px;
}

/* .imgEditbutton {
  position: absolute;
  width: 500px;
  left:0;
  top: 50%;
  text-align: center; 
  opacity: 0;
  transition: opacity .35s ease;
 
} */
.imgEditbutton {
	position: absolute;
	width: 500px;
	left: 50%;
	top: 180px;
	top: 50%;
	text-align: center;
	opacity: 0;
	transition: opacity .35s ease;
	transform: translateX(-50%);
}

.imgEditbutton a {
	width: 200px;
	padding: 12px 48px;
	text-align: center;
	color: white;
	border: solid 2px white;
	z-index: 1;
}

.imgcontainer:hover .imgEditbutton {
	opacity: 1;
}

img {
	width: 100%;
	height: auto;
}

#simpleslider {
	height: auto;
	margin: 20px auto;
	position: relative;
	border: 10px solid white;
	box-shadow: 0px 0px 5px 2px #ccc;
}

.navbtn {
	padding: 10px;
	border: none;
	background: #37f;
	font-size: 10px;
	color: white;
	position: absolute;
	cursor: pointer;
}

.next {
	border-radius: 10px 0px 0px 10px;
	right: 0;
	top: 50%;
}

.prew {
	border-radius: 0px 10px 10px 0px;
	left: 0;
	top: 50%;
	left: 0;
}

/* .pagination>li>a{
background-color:#337ab7;
color:white;
} */
.pagination>li>.activePagination {
	background-color: #337ab7;
	color: white;
}
</style>
<script type="text/javascript">
function showPopup(content,msg)
{
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


$(document).ready(function(){
	var table =  	createDataTable('detail',"procurementProductEnroll_data.action");
	var table1 =  	createDataTable('vareityDetail',"procurementVariety_data.action");
	var table3 =  	createDataTable('gradeDetail',"procurementGrade_data.action");
	$(".select2").select2();
	$('.ui-jqgrid .ui-jqgrid-bdiv').css('overflow-x', 'hidden');
});


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

 

function resetData()
{
	// $('#selectedCrop').prop('selectedIndex',-1);
	// $('#selectedCrop').val('');
	$("#cropName").val('');
	$("#cropCode").val('');
	/* $("#speciesName").val(''); */
	/* $("#unit").val('-1'); */
	$("#cropCat").val('');
	$("#varietyName").val('');
	$("#varietyCode").val('');
	$("#cropHScode").val('');
	$("#crop").val();
	document.getElementById("validateErrorCrop").innerHTML='';
	document.getElementById("validateErrorVariety").innerHTML='';
	
}
function deletProd(idd){
	var val = confirm('Are you sure you want to delete the Crop Category');
	if(val){
	$.ajax({
		 url:'procurementProductEnroll_populateDelete.action?id='+idd,
	     type: 'post',
	     dataType: 'json',
	     processData: false,
	     contentType: false,
	     success: function(result) {
	    	 showPopup(result.msg,result.title);
		 	 $('#detail').DataTable().ajax.reload();
	     },
	     error: function(result) {
	    	 showPopup(result.msg,result.title);
	     }
	}); 
	}
}

function deletVariety(idd){
	var val = confirm('Are you sure you want to delete the Crop Name');
	if(val){
	$.ajax({
		 url:'procurementVariety_populateDelete.action?id='+idd,
	     type: 'post',
	     dataType: 'json',
	     processData: false,
	     contentType: false,
	     success: function(result) {
	    	 showPopup(result.msg,result.title);
		 	 $('#vareityDetail').DataTable().ajax.reload();
	     },
	     error: function(result) {
	    	 showPopup(result.msg,result.title);
	     	
	     }
	}); 
	}
}

function deleteGrade(idd){
	var val = confirm('Are you sure you want to delete the Crop Variety');
	if(val){
	$.ajax({
		 url:'procurementGrade_populateDelete.action?id='+idd,
	     type: 'post',
	     dataType: 'json',
	     processData: false,
	     contentType: false,
	     success: function(result) {
	    	 showPopup(result.msg,result.title);
		 	 $('#gradeDetail').DataTable().ajax.reload();
	     },
	     error: function(result) {
	    	 showPopup(result.msg,result.title);
	     	
	     }
	}); 
	}	
}
function addCropCategory()     
{
	var crop=$("#cropName").val();
	var cropCode=$("#cropCode").val();
	var spec=$("#speciesName").val();
	var unit=$("#unit").val();
	//var cropCategory =$('input:radio[name="cropCategory"]:checked').val();
	var cropCategory=$("#cropCat").val();
	if(cropCode=="" || cropCode==null)
	{
		document.getElementById("validateErrorCrop").innerHTML='<s:text name="empty.cropCode"/>';
		document.getElementById("validateErrorVariety").innerHTML='';
	}
	else if(crop=="" || crop==null)
	{
		document.getElementById("validateErrorCrop").innerHTML='<s:text name="empty.name"/>';
		document.getElementById("validateErrorVariety").innerHTML='';
	}
	else if(unit=="-1" || unit==null)
	{
		document.getElementById("validateErrorCrop").innerHTML='<s:text name="empty.unit"/>';
		document.getElementById("validateErrorVariety").innerHTML='';
	}
	
	else if(cropCategory=="-1" || cropCategory==null)
	{
		document.getElementById("validateErrorCrop").innerHTML='<s:text name="empty.category"/>';
		document.getElementById("validateErrorVariety").innerHTML='';
	}
	else
	{
		
		$.post("procurementProductEnroll_create",{cropCode:cropCode,cropName:crop,unit:unit,cropCategory:cropCategory},function(result){
			resetData("cropForm");
			jQuery("#detail").jqGrid('setGridParam',{url:"procurementProductEnroll_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
			var validateMsg=JSON.parse(result);
			showPopup(validateMsg.msg,validateMsg.title);
			jQuery.post("procurementVariety_populateCrop.action",function(result){
				insertOptions("selectedCrop",JSON.parse(result));
				insertOptions("procurementProductId",JSON.parse(result));
			});
			
			
		});
		document.getElementById("validateErrorCrop").innerHTML='';
	}
}


function addCrop(val)     
{  
	var tenant='<s:property value="getCurrentTenantId()"/>';
    var crop=$("#cropName").val();
    var cropCode=$("#cropCode").val();
   var speciesName=$("#speciesName").val();
	/* var unit=$("#unit").val(); */
	if(cropCode=="" || cropCode==null)
	{ 
		document.getElementById("validateErrorCrop").innerHTML='<s:text name="empty.code"/>';
		document.getElementById("validateErrorVariety").innerHTML='';
	}
	else if(crop=="" || crop==null)
	{ 
		document.getElementById("validateErrorCrop").innerHTML='<s:text name="empty.name"/>';
		document.getElementById("validateErrorVariety").innerHTML='';
	}
	 else if(speciesName=="" || speciesName==null || speciesName==" ")
	{
		document.getElementById("validateErrorCrop").innerHTML='<s:text name="empty.cropType"/>';
		document.getElementById("validateErrorVariety").innerHTML='';
	}
	/* else if(unit=="-1" || unit==null)
	{
		document.getElementById("validateErrorCrop").innerHTML='<s:text name="empty.unit"/>';
		document.getElementById("validateErrorVariety").innerHTML='';
	} */


	else
	{
		
		if(val=='2'){
			$("#cropForm").attr('action', 'procurementProductEnroll_update');
			
		}
		
		var dataa =new FormData();
 		dataa.append( 'cropName', crop );
 		dataa.append( 'cropCode', cropCode );
 		dataa.append( 'speciesName', speciesName );
 		/* dataa.append( 'unit', unit ); */
 		console.log(dataa); 	
 	 	var url ='procurementProductEnroll_create.action';
 	 	if(val==2){
 	 		dataa.append('id',$('#id').val());
 	 		url ='procurementProductEnroll_update.action';
 	 	}
 		
 	 	$.ajax({
			 url:url,
		     type: 'post',
		     dataType: 'json',
		     processData: false,
		     contentType: false,
		     data: dataa,
		     success: function(result) {
		    	 resetData("cropForm");
		    	 showPopup(result.msg,result.title);
		    	 $('#add').removeClass("hide");
			 		$('#edit').addClass("hide");
			 	$('#addCrop').click();
		    	 $('#detail').DataTable().ajax.reload();
		    	 jQuery.post("procurementVariety_populateCrop.action",function(result){
						insertOptions("selectedCrop",JSON.parse(result));
						insertOptions("procurementProductId",JSON.parse(result));
						jQuery('.ui-jqdialog-titlebar-close').click();
					});   
		     },
		     error: function(result) {
		    	 showPopup("Error Occured","Error");
		     	 window.location.href="procurementProductEnroll_list.action";
		     }
		}); 
	
		
	}
	
	
}

function addVariety(val)
{
	var variety=$("#varietyName").val();
	var varietyCode=$("#varietyCode").val();
	var selectedCrop=$("#selectedCrop").val();
	var cropHScode=$("#cropHScode").val();
	
	
	if(!selectedCrop.trim()|| selectedCrop==null)
	{
		document.getElementById("validateErrorVariety").innerHTML='<s:text name="empty.crop1"/>';
	}
	else if(varietyCode=="" || varietyCode==null)
	{
		document.getElementById("validateErrorVariety").innerHTML='<s:text name="empty.code"/>';
	}
	else if(variety=="" || variety==null)
	{
		document.getElementById("validateErrorVariety").innerHTML='<s:text name="empty.name"/>';
	}
	else if(cropHScode=="" || cropHScode==null)
	{
		document.getElementById("validateErrorVariety").innerHTML='<s:text name="empty.cropHScode"/>';
	}
	
	else
	{
		
		if(val=='2'){
			$("#varietyForm").attr('action', 'procurementProductEnroll_update');
			
		}
		var dataa =new FormData();
 		dataa.append( 'varietyName', variety );
 		dataa.append( 'varietyCode', varietyCode );
 		dataa.append( 'cropHScode', cropHScode );
 		dataa.append( 'procurementProductId', selectedCrop );
 		console.log(dataa); 	
 	 	var url ='procurementVariety_create.action';
 	 	if(val==2){
 	 		dataa.append('id',$('#vid').val());
 	 		url ='procurementVariety_update.action';
 	 	}
 		
 	 	$.ajax({
			 url:url,
		     type: 'post',
		     dataType: 'json',
		     processData: false,
		     contentType: false,
		     data: dataa,
		     success: function(result) {
		    	 resetData("varietyForm");
		    	 showPopup(result.msg,result.title);
		    	 $('#add').removeClass("hide");
		 		$('#edit').addClass("hide");
		 	$('#addVariety').click();
		    	 $('#vareityDetail').DataTable().ajax.reload();
		    	 
		     },
		     error: function(result) {
		    	 showPopup("Error Occured","Error");
		     	 window.location.href="procurementProductEnroll_list.action";
		     }
		}); 
 	 	
 	 
			
	}
		
	
	
}





function addGrade(val)
{
	
	var selectedCrop=$("#procurementProductId").val();
	var selectedVariety=$("#selectedVariety").val();
	var gradeName=$("#gradeName").val();
	var gradeCode=$("#gradeCode").val();
	var selectedCropcycle=$("#cropCycle").val();
	var selectedYield=$("#yield").val();
	var selectedHarvestDays=$("#harvestDays").val();

	if(selectedCrop=="" || selectedCrop==null)
	{
		
		document.getElementById("validateErrorGrade").innerHTML='<s:text name="empty.crop1"/>';
	}
	
	 else if(selectedVariety=="" || selectedVariety==null || isEmpty(selectedVariety))
	{
		 document.getElementById("validateErrorGrade").innerHTML='<s:property value="%{getLocaleProperty('empty.variety')}" />';
	}
	
	else if(gradeCode=="" || gradeCode==null)
	{
		document.getElementById("validateErrorGrade").innerHTML='<s:text name="empty.code"/>';
	}
	else if(gradeName=="" || gradeName==null)
	{
		document.getElementById("validateErrorGrade").innerHTML='<s:text name="empty.name"/>';
	}
	else if(selectedCropcycle=="" || selectedCropcycle==null)
	{
		document.getElementById("validateErrorGrade").innerHTML='<s:text name="empty.cropCycle"/>';
	}
	else if(selectedYield=="" || selectedYield==null)
	{
		document.getElementById("validateErrorGrade").innerHTML='<s:text name="empty.yield"/>';
	}
	/* else if(selectedHarvestDays=="" || selectedHarvestDays==null)
	{
		document.getElementById("validateErrorGrade").innerHTML='<s:text name="empty.harvestDays"/>';
	} */
	else
	{
		
		var dataa =new FormData();
 		dataa.append( 'gradeName', gradeName );
 		dataa.append( 'gradeCode', gradeCode );
 		dataa.append( 'procurementVarietyId', selectedVariety );
 		dataa.append( 'ccycle', selectedCropcycle );
 		dataa.append( 'yieldkg', selectedYield );
 		dataa.append( 'harvestday', selectedHarvestDays );
 		console.log(dataa); 	
 	 	var url ='procurementGrade_create.action';
 	 	if(val==2){
 	 		dataa.append('id',$('#gid').val());
 	 		url ='procurementGrade_update.action';
 	 	}
 		
 	 	$.ajax({
			 url:url,
		     type: 'post',
		     dataType: 'json',
		     processData: false,
		     contentType: false,
		     data: dataa,
		     success: function(result) {
		    	 resetData("gradeForm");
		    	 showPopup(result.msg,result.title);
		    	 $('#addg').removeClass("hide");
		 		$('#editg').addClass("hide");
		 	$('#addGrade').click();
		    	 $('#gradeDetail').DataTable().ajax.reload();
		     },
		     error: function(result) {
		    	 showPopup("Error Occured","Error");
		     	 window.location.href="procurementProductEnroll_list.action";
		     }
		}); 
		
	}
	
}


function populateVariety(obj,setvall)
{
	//alert("obj"+obj);
	jQuery.post("procurementGrade_populateVariety.action",{id:obj,dt:new Date(),procurementProductId:obj},function(result){
		insertOptions("selectedVariety",JSON.parse(result));
		if(setvall!=null && setvall!=''){
		$('#selectedVariety').val(setvall).select2();
			//$("#selectedVariety option[value='"+ grower.trim() + "']").prop("selected", true).trigger('change');
		}
	});
	
}

function populateVarietyEdit(val,id)
{
	jQuery.post("procurementGrade_populateVariety.action",{id:val,dt:new Date(),procurementProductId:val},function(result){
		insertOptions(id,JSON.parse(result));
		//listGramPanchayat(document.getElementById("vcities"));
	});
	
	


}
function resetData(id){
	$("#"+id)[0].reset();
	$("#"+id).trigger("reset");
	$('.select2').trigger("change");
	 $('.select2').select2();
}

		 
function getAjaxValue(url,dataa,selRow,idSuffix){
	var resp = $.ajax({
		url: url,
		data:dataa,
		type: 'post',
		async: false, 
		success: function(data, result) {
			if (!result) 
				alert('Failure to load data');
			}
	}).responseText;
	
		var id="#"+selRow+idSuffix;
		var selectedValue = jQuery(id).val();
		insertJQOptions(selRow+idSuffix,JSON.parse(resp));
		jQuery(id).val(selectedValue);
		
		
}		

function insertJQOptions(ctrlName, jsonArr) {		
	document.getElementById(ctrlName).length = 0;
	/* addOption(document.getElementById(ctrlName), "Select", ""); */
	for (var i = 0; i < jsonArr.length; i++) {
		addOption(document.getElementById(ctrlName), jsonArr[i].name,
				jsonArr[i].id);
	}	
}

$("#variety").click(function() {
	  alert( "Handler for .click() called." );
	});
		 
		 
function ediFunction(val){
		var son = JSON.parse(val);

		if($('#addCrop').attr("aria-expanded")=='false' || $('#addCrop').attr("aria-expanded")==undefined){
			$('#addCrop').click();
		}
		
		jQuery('#cropCode').prop("disabled",true);
		$('#cropCode').val(son.code);
		
		//$('#unit').val(son.unit).select2();
		$('#cropName').val(son.name);
		 $('#speciesName').val(son.speciesCode).select2();
		$('#id').val(son.id);
		$('#catId').val(son.id);
		$('#add').addClass("hide");
		$('#edit').removeClass("hide");
		
		
	}

function ediFunctionG(val){
		var son = JSON.parse(val);

		if($('#addGrade').attr("aria-expanded")=='false' || $('#addGrade').attr("aria-expanded")==undefined){
			$('#addGrade').click();
		}
		$('#procurementProductId').val(son.crop).select2();
		populateVariety(son.crop,son.variety);
		//$('#selectedVariety').val(son.variety).select2();
		// $("#selectedVariety option[value='"+ grower.trim() + "']").prop("selected", true).trigger('change');
		jQuery('#gradeCode').prop("disabled",true);
		$('#gradeCode').val(son.code);
		$('#gradeName').val(son.name);
		$('#gid').val(son.id);
		$('#addg').addClass("hide");
		$('#editg').removeClass("hide");
		$('#cropCycle').val(son.cropCycle);
		$('#yield').val(son.yield);
		$('#harvestDays').val(son.harvestDays);
		
	}
	

function ediFunctionV(val){
		var son = JSON.parse(val);


		if($('#addVariety').attr("aria-expanded")=='false' || $('#addVariety').attr("aria-expanded")==undefined){
			$('#addVariety').click();
		}
		jQuery('#varietyCode').prop("disabled",true);
		$('#varietyCode').val(son.code);
		
		$('#selectedCrop').val(son.crop).select2();
		$('#varietyName').val(son.name);
		
		$('#cropHScode').val(son.cropHScode);
		$('#vid').val(son.id);
		$('#addv').addClass("hide");
		$('#editv').removeClass("hide");
		
	}
	
function onResetAddCrop(){
	/*  $("#unit option[value='']").prop("selected", true).trigger('change'); */
	 $('#cropName').val('');
	 $('#cropCode').val('');
	 /* $('#speciesName').val(''); */
	
}
function onResetAddVariety(){
	 $("#selectedCrop option[value='']").prop("selected", true).trigger('change');
	 $('#varietyName').val('');
	 $('#varietyCode').val('');
	 $('#cropHScode').val('');
	
}
function onResetAddGrade(){
	 $("#procurementProductId option[value='']").prop("selected", true).trigger('change');
	 $("#selectedVariety").val("").select2();
	 $('#gradeName').val('');
	 $('#gradeCode').val('');
	
}


$(document).ready(function(){
	//$("#edit").hide();	
});
</script>


<!-- crop Modal -->

<div id="baseDiv" style="width: 99%"></div>
<div class="appContentWrapper marginBottom">
	<ul class="nav nav-pills">
		<li class="active"><a data-toggle="pill" href="#crop"><s:property
					value="%{getLocaleProperty('crop')}" /></a></li>

		<li><a data-toggle="pill" href="#variety"><s:property
					value="%{getLocaleProperty('variety')}" /></a></li>
		<li><a data-toggle="pill" href="#grade"><s:property
					value="%{getLocaleProperty('grade')}" /></a></li>

	</ul>
</div>




<div class="tab-content">
	<div id="crop" class="tab-pane fade in active">

		<div class="dataTable-btn-cntrls" id="detailBtn">
			<sec:authorize ifAllGranted="profile.procurementProduct.create">


				<ul class="nav nav-pills newUI-nav-pills">
					<li class="" id=""><a data-toggle="collapse"
						href="#cropAccordian" onclick="onResetAddCrop()"> <i
							id="addCrop" class="fa fa-plus"></i>Add
					</a></li>
				</ul>

			</sec:authorize>
		</div>


		<s:form name="cropForm" id="cropForm"
			action="procurementProductEnroll_create"
			enctype="multipart/form-data">
			<s:hidden name="id" id="id" />
			<div id="cropAccordian" class="panel-collapse collapse">
				<div class="appContentWrapper marginBottom">
					<span id="validateErrorCrop" style="color: red;"></span>
					<div class="flex-layout filterControls">
						<div class="flexItem">
							<label for="txt"><s:text name="cropcatCode" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="cropCode" theme="simple" maxlength="35"
									id="cropCode" />

							</div>
						</div>
						<div class="flexItem">
							<label for="txt"><s:text name="product.name" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="cropName" theme="simple" maxlength="35"
									id="cropName" />

							</div>
						</div>
						<div class="flexItem">
							<label for="txt"><s:property
									value="%{getLocaleProperty('cropType')}" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:select cssClass="form-control select2" name="speciesName"
									listKey="key" listValue="value"
									list="getCatList(getLocaleProperty('croptypeCatalogue'))"
									headerKey="" headerValue="%{getText('txt.select')}"
									id="speciesName" />

							</div>
						</div>

						<%-- <div class="flexItem">
							<label for="txt"><s:text name="product.unit" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:select  id="unit" cssClass="form-control select2"
									name="farmer.hedu" listKey="key" listValue="value"
									list="getCatList(getLocaleProperty('umoListType'))" headerKey=" "
									headerValue="%{getText('txt.select')}" />
									
									
								<s:select id="unit" name="unit" list="getCatList(getLocaleProperty('umoListType'))"  headerKey=""
									listKey="key" listValue="value"
									headerValue="%{getText('txt.select')}"
									 />

							</div>
						</div> --%>

					</div>
					<div class="flex-layout filterControls">
						<div class="flexItem">
							<div class="form-element">

								<button type="button" class="btn btn-sts" id="add"
									onclick="addCrop(1)">
									<b><s:text name="save.button" /></b>
								</button>

								<button type="button" class="btn btn-sts hide" id="edit"
									onclick="addCrop(2)">
									<b><s:text name="save.button" /></b>
								</button>
								<button type="button" class="back-btn btn btn-sts"
									onclick="onCancel()">
									<b><FONT color="#FFFFFF"><s:text name="Cancel" /> </font></b>
								</button>

							</div>
						</div>
					</div>
				</div>
			</div>
		</s:form>
		<div id="baseDiv" style="width: 100%">
			<table id="detail" class="table table-striped">
				<thead>
					<tr id="headerrow">

						<th width="20%" id="code" class="hd"><s:text
								name="product.code" /></th>
						<th width="20%" id="name" class="hd"><s:text
								name="product.name" /></th>
						<th width="20%" id="speciesName" class="hd"><s:text
								name="cropType" /></th>
						<%-- <th width="20%" id="unit" class="hd"><s:text
								name="product.unit" /></th> --%>
						<th width="20%" id="edit" class="hd noexp"><s:text
								name="action" /></th>

					</tr>
				</thead>

			</table>
		</div>
	</div>

	<div id="variety" class="tab-pane fade">

		<div class="dataTable-btn-cntrls" id="vareityDetailBtn">
			<sec:authorize ifAllGranted="profile.procurementProduct.create">


				<ul class="nav nav-pills newUI-nav-pills">
					<li class="" id=""><a data-toggle="collapse"
						href="#varietyAccordian" onclick="onResetAddVariety()"> <i
							id="addVariety" class="fa fa-plus"></i>Add
					</a></li>
				</ul>

			</sec:authorize>
		</div>



		<s:form name="varietyForm" id="varietyForm">
			<s:hidden id="vid" />
			<div id="varietyAccordian" class="panel-collapse collapse">
				<div class="appContentWrapper marginBottom">
					<span id="validateErrorVariety" style="color: red;"></span>
					<div class="flex-layout filterControls">
						<div class="flexItem">
							<label for="txt"><s:text name="crop" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:select name="selectedCrop" theme="simple" listKey="id"
									listValue="name" list="procurementProductList" headerKey=""
									headerValue="%{getText('txt.select')}"
									cssClass="form-control select2" id="selectedCrop" />
							</div>
						</div>

						<div class="flexItem">
							<label for="txt"><s:property
									value="%{getLocaleProperty('cropcatCode')}" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="varietyCode" theme="simple" maxlength="45"
									id="varietyCode" />

							</div>
						</div>

						<div class="flexItem">
							<label for="txt"><s:property
									value="%{getLocaleProperty('procurementVariety.name')}" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="varietyName" theme="simple" maxlength="45"
									id="varietyName" />

							</div>
						</div>


						<div class="flexItem">
							<label for="txt"><s:property
									value="%{getLocaleProperty('procurementVariety.cropHScode')}" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="cropHScode" theme="simple" maxlength="45"
									id="cropHScode" />

							</div>
						</div>

						<div class="flexItem" style="margin-top: 20px;">
							<button type="button" class="btn btn-sts " data-toggle="modal"
								id="addv" onclick="addVariety(1)">
								<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
								</font>
							</button>
							<button type="button" class="btn btn-sts hide"
								data-toggle="modal" id="editv" onclick="addVariety(2)">
								<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
								</font>
							</button>
							<button type="button" class="back-btn btn btn-sts"
								onclick="onCancel()">
								<b><FONT color="#FFFFFF"><s:text name="Cancel" /> </font></b>
							</button>
						</div>
					</div>
				</div>
			</div>
		</s:form>

		<div id="varietyBaseDiv" style="width: 100%">
			<table id="vareityDetail" class="table table-striped">
				<thead>
					<tr id="headerrow">

						<th width="20%" id="code" class="hd"><s:text
								name="procurementVariety.code" /></th>
						<th width="20%" id="name" class="hd"><s:text
								name="procurementVariety.name" /></th>
						<th width="20%" id="prodName" class="hd"><s:text
								name="seedcrop.Spec" /></th>
						<th width="20%" id="cropHScode" class="hd"><s:text
								name="procurementVariety.cropHScode" /></th>

						<th width="20%" id="edit" class="hd noexp"><s:text
								name="action" /></th>

					</tr>
				</thead>

			</table>
		</div>


	</div>

	<div id="grade" class="tab-pane fade">

		<div class="dataTable-btn-cntrls" id="gradeDetailBtn">
			<sec:authorize ifAllGranted="profile.procurementProduct.create">


				<ul class="nav nav-pills newUI-nav-pills">
					<li class="" id=""><a data-toggle="collapse"
						href="#gradeAccordian" onclick="onResetAddGrade()"> <i
							id="addGrade" class="fa fa-plus"></i>Add
					</a></li>
				</ul>

			</sec:authorize>
		</div>



		<s:form name="gradeForm" id="gradeForm">
			<s:hidden id="gid" />
			<div id="gradeAccordian" class="panel-collapse collapse">
				<div class="appContentWrapper marginBottom">
					<span id="validateErrorGrade" style="color: red;"></span>
					<div class="flex-layout filterControls">
						<div class="flexItem">
							<label for="txt"><s:text name="crop" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:select name="procurementProductId" theme="simple"
									listKey="id" listValue="name" list="procurementProductList"
									headerKey="" headerValue="%{getText('txt.select')}"
									cssClass="form-control select2" id="procurementProductId"
									onchange="populateVariety(this.value)" />
							</div>
						</div>
						<div class="flexItem">
							<label for="txt"><s:property
									value="%{getLocaleProperty('variety')}" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:select name="selectedVariety" id="selectedVariety"
									theme="simple" listKey="id" listValue="name" list="{}"
									headerKey="-1" headerValue="%{getText('txt.select')}"
									cssClass="form-control select2" />
							</div>
						</div>

						<div class="flexItem">
							<label for="txt"><s:text name="cropcatCode" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="gradeCode" theme="simple" maxlength="45"
									id="gradeCode" />
							</div>
						</div>
						<div class="flexItem">
							<label for="txt"><s:text name="procurementGrade.name" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="gradeName" theme="simple" maxlength="45"
									id="gradeName" />
							</div>
						</div>
						<div class="flexItem">
							<label for="txt"><s:text
									name="procurementGrade.cropCycle" /><sup style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="ccycle" theme="simple" maxlength="10"
									onkeypress="return isNumber(event,this);" id="cropCycle" />
							</div>
						</div>
						<div class="flexItem">
							<label for="txt"><s:text name="procurementGrade.yield" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="yieldkg" theme="simple" maxlength="20"
									onkeypress="return isDecimal1(event,this);" id="yield" />
							</div>
						</div>
						<%-- <div class="flexItem">
							<label for="txt"><s:text name="procurementGrade.harvestDays" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="harvestday" maxlength="10"
								onkeypress="return isNumber(event,this);" theme="simple"
									id="harvestDays" />
							</div>
						</div> --%>


						<div class="flexItem" style="margin-top: 20px;">
							<button type="button" class="btn btn-sts " data-toggle="modal"
								id="addg" onclick="addGrade(1)">
								<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
								</font>
							</button>
							<button type="button" class="btn btn-sts hide"
								data-toggle="modal" id="editg" onclick="addGrade(2)">
								<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
								</font>
							</button>
							<button type="button" class="back-btn btn btn-sts"
								onclick="onCancel()">
								<b><FONT color="#FFFFFF"><s:text name="Cancel" /> </font></b>
							</button>
						</div>

					</div>
				</div>
			</div>
		</s:form>

		<div id="gradeBaseDiv" style="width: 100%">
			<table id="gradeDetail" class="table table-striped">
				<thead>
					<tr id="headerrow">

						<th width="20%" id="code" class="hd"><s:text
								name="procurementVariety.code" /></th>
						<th width="20%" id="name" class="hd"><s:text
								name="procurementVariety.name" /></th>
						<th width="20%" id="variety" class="hd"><s:text
								name="variety" /></th>
						<th width="20%" id="prodName" class="hd"><s:text
								name="seedcrop.Spec" /></th>
						<th width="20%" id="cropCycle" class="hd"><s:text
								name="procurementGrade.cropCycle" /></th>
						<th width="20%" id="yield" class="hd"><s:text
								name="procurementGrade.yield" /></th>
						<%-- <th width="20%" id="harvestDays" class="hd"><s:text
								name="procurementGrade.harvestDays" /></th> --%>
						<th width="20%" id="edit" class="hd noexp"><s:text
								name="action" /></th>

					</tr>
				</thead>

			</table>
		</div>


	</div>




</div>



<s:form name="createform" action="procurementProductEnroll_create">
</s:form>
<s:form name="updateform" action="procurementProductEnroll_detail">
	<s:hidden key="id" />
	<s:hidden name="ppid" value="%{id}" />
	<s:hidden key="currentPage" />
</s:form>


