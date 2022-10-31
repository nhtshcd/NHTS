<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<link rel="stylesheet"
	href="plugins/selectize/css/selectize.bootstrap3.css">
<script src="plugins/selectize/js/standalone/selectize.min.js?v=2.0"></script>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>

<script type="text/javascript">
var rowCounter=0;
var enableStorage = "<s:property value='enableStorage'/>";
$(document).ready(function(){
	<s:if test="command =='update'">
	//jQuery("#maxBayWeight").val("0.00");
	$('#warehouseType').attr('disabled',true);
	</s:if>
	
	jQuery("#edit").hide();
	jQuery(".otherDiv").hide();
		/*  $("#dynOpt input[value='Remove']").addClass("ic-remove");
		 $("#dynOpt input[value='Add']").addClass("ic-add");
		 $("#dynOpt input[value='Remove All']").addClass("ic-removeAll");
		 $("#dynOpt input[value='Add All']").addClass("ic-addAll");
		 updateSelectedVillageList(null); */
		 //Soil Type drop down

          var storageCommodity = '<s:property value="selectedCommodity" />';
         
         if (storageCommodity != null && storageCommodity.trim() != "") {
             var values = storageCommodity.split("\\,");
             $.each(storageCommodity.split(","), function (i, e) {
            	  
                 $("#commodity option[value='" + e.trim() + "']")
                         .prop("selected", true); 
			 });
             $("#commodity").select2(); 
         }	
   
	// otherCommodity(jQuery("#commodity").val());
         $('.select2Multi').selectize({
    		 plugins: ['remove_button'],
    		 delimiter: ',',
    		 persist: false,
    	/* 	 create: function(input) {
    		  return {
    		   value: input,
    		   text: input
    		  }
    		 } */
    		});
	
	});
function listOfVillagesByBlock(call){
	try{
	   	var data = call.value;
	   	if(jQuery.trim(data)!="-1"){  
		    var url =  "cooperative_populateVillages.action";
	        $.post(url,{selectedBlock:data, dt:new Date()},function(result){           
	        	var datas = result.split('~');
		        $('#avlVillageId').children().remove();
		        $('#selVillageId').children().remove();
		        
				$('#avlVillageId').append(datas[0]);
				$('#selVillageId').append(datas[1]);
	        });
	   	}
	   	else{
	   		$("#dynOpt").html("");
	   	}
   	}
   	catch(e) {			
	}
}

function updateSelectedVillageList(call){
	try {
		var selArray = new Array();
   		var selectedVillageIds = document.getElementById('selVillageId');
   		for (var i = 0; i < selectedVillageIds.options.length; i++) {
   			selArray[i] = selectedVillageIds.options[i].value; 
	    }

   		var avlArray = new Array();
   		var availableVillageIds = document.getElementById('avlVillageId');
   		for (var i = 0; i < availableVillageIds.options.length; i++) {
   			avlArray[i] = availableVillageIds.options[i].value; 
	    }
   		var selectedBlock = document.getElementById('block').value;
   		var cmd = document.getElementsByName('command')[0].value;
   		if(cmd=="update") {
   			var warehouseId = document.getElementsByName('warehouse.id')[0].value;
   		    if(warehouseId!=null) {
   			 	var url="cooperative_gridCheck.action";
   			    $.post(url,{availableVillages:avlArray, id:warehouseId, dt:new Date()},function(result) {
   			        //document.getElementById("errorDiv").innerHTML="";
   			        if(result!="") {
   	   			        alert(result);
   			    		//document.getElementById("errorDiv").innerHTML=result;
   			    		//document.getElementById('errorDiv').contentEditable=true;
   			    		//document.getElementById("errorDiv").focus();
   			        } else {
   			        	var url="cooperative_gridProcess.action";
   					    $('#avlVillageId').children().remove();
   						$('#selVillageId').children().remove();
   						document.getElementById('block').value="";
   				        $.post(url,{selectedVillages:selArray, availableVillages:avlArray, selectedBlock:selectedBlock, dt:new Date()},function(result) {
   				        	updateSessionGrid(result);
   				        });
   			         }
   			    });
   		    }
   		} else {
	   			var url="cooperative_gridProcess.action";
			    $('#avlVillageId').children().remove();
				$('#selVillageId').children().remove();
				document.getElementById('block').value="";
		        $.post(url,{selectedVillages:selArray, availableVillages:avlArray, selectedBlock:selectedBlock, dt:new Date()},function(result) {
		        	updateSessionGrid(result);
		        });
   		  }
   	}
   	catch(e) {	
	}
}

function deleteSelectedVillage(val){
	try {
   		var avlArray = new Array();
   		avlArray[0] = val;
		var cmd = document.getElementsByName('command')[0].value;
   		if(cmd=="update"){
   			var warehouseId = document.getElementsByName('warehouse.id')[0].value;
   		    if(warehouseId!=null){
   			 	var url =  "cooperative_gridCheck.action";
   			    $.post(url,{availableVillages:avlArray, id:warehouseId, dt:new Date()},function(result) {
   			        //document.getElementById("errorDiv").innerHTML="";
   			        if(result!="") {
   			        	alert(result);
   			    		//document.getElementById("errorDiv").innerHTML=result;
   			    		//document.getElementById('errorDiv').contentEditable=true;
   			    		//document.getElementById("errorDiv").focus();
   			        } else {
   			        	var url="cooperative_gridProcess.action";
   					    $('#avlVillageId').children().remove();
   						$('#selVillageId').children().remove();
   						document.getElementById('block').value="";
   				        $.post(url,{availableVillages:avlArray, dt:new Date()},function(result) {
   				        	updateSessionGrid(result);   
   				        });
   			        }
   			    });
   		    }
   		}  		
   		else { 
		    var url="cooperative_gridProcess.action";
		    $('#avlVillageId').children().remove();
			$('#selVillageId').children().remove();
			document.getElementById('block').value="";
	        $.post(url,{availableVillages:avlArray, dt:new Date()},function(result) {
	        	updateSessionGrid(result);   
	        });
   		}
   	}
   	catch(e) {			
	}
}

function updateSessionGrid(result){
	
	var datas = result.toString();
	datas = datas.replace("{","").replace("}","");
	var dataArray = datas.split(",");    
	
	$("#villageTable").children().remove();
	var table = document.getElementById("villageTable");
	$("#villageTableDiv").height(150);
  	for(var i=0; i<dataArray.length; i++) {
  		if(dataArray[i]!=null && dataArray[i]!='') {
      		  var data = dataArray[i].split("=");
	  		  var row = table.insertRow(i);
	  		  var cell1 = row.insertCell(0);
	  		  var cell2 = row.insertCell(1);
	  		  cell1.innerHTML = data[1];
	  		  cell1.setAttribute("style", "width:1075px;word-wrap:break-word;");
	  		  cell2.innerHTML = "<button type='button' name='delete' value='Delete' class='crossIcon' title='Delete Village' onclick='deleteSelectedVillage(&quot;"+data[0]+"&quot;)'>Delete</button>";
	  		  cell2.setAttribute("style", "width:400px;text-align:center;");
  		} else {
  			  $("#villageTableDiv").height(20);
      		  var row = table.insertRow(0);
	  		  var cell1 = row.insertCell(0);
	  		  cell1.innerHTML = "<s:text name='noVillagesSelected' />";
	  		  cell1.setAttribute("style", "text-align:center;");
		  }
  	 }
}


function otherCommodity(data){
	if(data==3){
		jQuery("#otherValue").val("");
		jQuery(".otherDiv").show();
	}else{
		jQuery(".otherDiv").hide();
	}
}


function buttonStorageCancel(){
	//refreshPopup();
	document.getElementById("model-close-commo-btn").click();	
 }
 
 
function buttonOwnerShipCancel(){
	//refreshPopup();
	document.getElementById("validateOwnershipError").innerHTML="";
	document.getElementById("model-close-ownership-btn").click();	
 }

$('#saveCommodityDetail').click(function(e){
	saveCommodityInformation();
 });
 
function saveCommodityInformation(){
    var comoodity= $('#commodityName').val();
	if(comoodity==""){
		document.getElementById("validateError").innerHTML='<s:text name="empty.commodity"/>';
		valid=false;
		return false;
	}
	
		jQuery.post("cooperative_populateCommodity.action",{commodityName:comoodity},function(result){
			if(result!=0)
			{
				insertOption("commodity",JSON.parse(result));
				document.getElementById("model-close-commo-btn").click();	
			}
			else
			{
				document.getElementById("validateError").innerHTML='<s:text name="storage.exist"/>';
			}
		});
}

function insertOption(ctrlName, jsonArr) {
    document.getElementById(ctrlName).length = 0;
     for (var i = 0; i < jsonArr.length; i++) {
        addOption(document.getElementById(ctrlName), jsonArr[i].name, jsonArr[i].id);
    }
   
    var id="#"+ctrlName;
    jQuery(id).select2();
}

function saveOwnershipInformation(){
	var ownerShip= $('#ownerShipName').val();
	if(ownerShip==""){
		document.getElementById("validateOwnershipError").innerHTML='<s:text name="empty.ownership"/>';
		valid=false;
		return false;
	}
	
		jQuery.post("cooperative_populateOwnership.action",{ownershipName:ownerShip},function(result){
			if(result!=0)
			{
				insertOptions("ownerShip",JSON.parse(result));
				document.getElementById("model-close-ownership-btn").click();	
			}
			else
			{
				document.getElementById("validateOwnershipError").innerHTML='<s:text name="ownership.exist"/>';
			}
		});
		clearData();
}

function clearData(){
	$("#validateOwnershipError").text('');
	$("#validateError").text('');
	$("#commodityName").val('');
	$("#ownerShipName").val('');
}

function addRow(){
	
	jQuery("#validateError").text("");
	var coldStorageName = $("#coldStorageName").val();
	var maxBayWeight = jQuery("#maxBayWeight").val();
	if(isEmpty(coldStorageName)){
		jQuery("#errorDiv").text('<s:property value="%{getLocaleProperty('empty.coldStorageName')}" />');
		return false;
	}
	 if(maxBayWeight==0){
		jQuery("#errorDiv").text('<s:property value="%{getLocaleProperty('empty.maxBayWeight')}" />');
		return false;
	}
	if(!checkColdStorageNameExists(coldStorageName)){
		var tableRow="<tr id=row"+(++rowCounter)+">";
		tableRow+="<td class='hide coldStorageCode'>"+coldStorageName+"</td>";
		tableRow+="<td>"+jQuery("#coldStorageName option:selected").text()+"</td>";
		tableRow+="<td class='maxBayHold textAlignRight'>"+maxBayWeight+"</td>";
		tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i><i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteProduct("+rowCounter+")'></td>";
		tableRow+="</tr>";
		console.log(tableRow);
		jQuery("#coldStorageDetailContent").append(tableRow);
		resetProductData();
		
	}
}
function resetProductData(){
	jQuery("#coldStorageName").val("");
	jQuery("#maxBayWeight").val("");
	resetSelect2();
	jQuery("#add").show();
	jQuery("#edit").hide();
}
function resetSelect2(){
	$(".select2").select2();
}
function checkColdStorageNameExists(storageName){
	//alert(gradeId);
	var returnVal = false;
	var tableBody = jQuery("#coldStorageDetailContent tr");
		
		$.each(tableBody, function(index, value) {
			var name = jQuery(this).find(".coldStorageCode").text();
			//alert(grade);
			if(name==storageName){
				alert('<s:property value="%{getLocaleProperty('storageName.alreadyExists')}" />');
				returnVal=true 
			}
		});
	
	return returnVal;	
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
function editRow(rowCounter){
	var id="#row"+rowCounter;
	
	$.each(jQuery(id), function(index, value) {
		var selectedProduct = jQuery(this).find(".coldStorageCode").text();
		var selectedMaxBin = jQuery(this).find(".maxBayHold").text();
		
		jQuery("#coldStorageName").val(selectedProduct);
		callTrigger("coldStorageName");	
		jQuery("#maxBayWeight").val(selectedMaxBin);
		resetSelect2();
	});
	jQuery("#add").hide();
	jQuery("#edit").show();
	$("#edit").attr("onclick","editProduct("+rowCounter+")");
	 
}
function editProduct(index){

	var id="#row"+index;
	jQuery(id).empty();	
	jQuery("#validateError").text("");
	var coldStorageName = jQuery("#coldStorageName").val();
	var maxBayWeight = jQuery("#maxBayWeight").val();
	
	if(isEmpty(coldStorageName)){
		jQuery("#validateError").text('<s:property value="%{getLocaleProperty('empty.coldStorageName')}" />');
		return false;
	}
	else if(maxBayWeight==0){
		jQuery("#validateError").text('<s:property value="%{getLocaleProperty('empty.maxBayWeight')}" />');
		return false;
	}
	
	if(!checkColdStorageNameExists(coldStorageName)){
		var tableRow="";
		tableRow+="<td class='hide coldStorageCode'>"+coldStorageName+"</td>";
		tableRow+="<td>"+jQuery("#coldStorageName option:selected").text()+"</td>";
		tableRow+="<td class='maxBayHold textAlignRight'>"+maxBayWeight+"</td>";
		tableRow+="<td><i style='cursor: pointer; font-size: 150%; color: blue;' class='fa fa-pencil-square-o' aria-hidden='true' onclick='editRow("+rowCounter+")' ></i><i style='cursor: pointer; font-size: 150%; color: black;' class='fa fa-trash-o' aria-hidden='true' onclick='deleteProduct("+rowCounter+")'></td>";
		tableRow+="</tr>";
		jQuery(id).append(tableRow);
		resetProductData();
	
		jQuery("#add").show();
		jQuery("#edit").hide();
		
	}
}
function callTrigger(id){
	$("#"+id).trigger("change");
}
function deleteProduct(rowCounter){
	var id="#row"+rowCounter;
	jQuery(id).remove();
	
	 //$("#coldStorageDetailContent tr:eq("+rowCounter+")").remove();
	
}
function buildColdStorageInfoArray(){
	
	var tableBody = jQuery("#coldStorageDetailContent tr");
	var productInfo="";
	
	$.each(tableBody, function(index, value) {
		productInfo+=jQuery(this).find(".coldStorageCode").text(); //0
		productInfo+="#"+jQuery(this).find(".maxBayHold ").text()+"@"; 

		
		
	});
	
	return productInfo;
}
function onSubmit(){
	
	var hit=true;
	
	var coldStorageInfoArray =  buildColdStorageInfoArray();
	if(enableStorage=='1'){
		if(coldStorageInfoArray==""){
			jQuery("#errorDiv").text('<s:property value="%{getLocaleProperty('empty.maxcoldStorage')}" />');
			return false;
		}
	}
	
	
	jQuery(".storageArrayToString").val(coldStorageInfoArray);
	
}
</script>

<body>

	<s:form name="form" cssClass="fillform" action="cooperative_%{command}">

		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:if test='"update".equalsIgnoreCase(command)'>
            <s:hidden name="storageArrayToString" class="storageArrayToString" />
			<s:hidden key="warehouse.id" />
			<s:hidden name="warehouse.typez"/>
		</s:if>
		<s:hidden id="enableStorage" name="enableStorage" />
		<s:hidden key="command" />
		<s:hidden name="type" class="type" />
		<s:hidden name="storageArrayToString" class="storageArrayToString" />
		<div class="appContentWrapper marginBottom">
			<div id="errorDiv" class="error">
				<s:actionerror />
				<s:fielderror />
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:property value="%{getLocaleProperty('info.coOperative')}" />
				</h2>
				<div class="flexform">
					<s:if test='"update".equalsIgnoreCase(command)'>
						<div class="flexform-item">
							<label for="txt"><s:text name="coOperative.code" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:property value="warehouse.code" />
								<s:hidden key="warehouse.code" />
							</div>
						</div>
					</s:if>
					<div class="flexform-item">
						<label for="txt"><s:text name="coOperative.name" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="warehouse.name" theme="simple" maxlength="45"
								cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="coOperative.location" /></label>
						<div class="form-element">
							<s:textfield name="warehouse.location" theme="simple"
								maxlength="45" cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="coOperative.address" /></label>
						<div class="form-element">
							<s:textfield name="warehouse.address" theme="simple"
								maxlength="200" cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="coOperative.phoneNo" /></label>
						<div class="form-element">
							<s:textfield name="warehouse.phoneNo" theme="simple"
								maxlength="10" cssClass="form-control input-sm" />
						</div>
					</div>
					<%-- <div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('coOperative.warehouseInCharge')}" /></label>
						<div class="form-element">
							<s:textfield name="warehouse.warehouseInCharge" theme="simple"
								maxlength="45" cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text
								name="coOperative.capacityInTonnes" /></label>
						<div class="form-element">
							<s:textfield name="warehouse.capacityInTonnes" theme="simple"
								maxlength="10" cssClass="form-control input-sm" />
						</div>
					</div> --%>
					<div class="flexform-item">
						<label for="txt"><s:text
								name="coOperative.storageCommodity" /></label>
						<div class="form-element">
							<s:select id="commodity" name="selectedCommodity"
								list="commodityList" listKey="key" listValue="value"
								theme="simple" multiple="true"
								cssClass="form-control input-sm select2" />
							<button type="button" id="addCommodityDetail"
								class="addBankInfo slide_open btn btn-sm btn-success"
								data-toggle="modal" data-target="#slideCommodity"
								onclick="clearData()">
								<i class="fa fa-plus" aria-hidden="true"></i>
							</button>
						</div>
					</div>

					<div class="flexform-item otherDiv">
						<label for="txt"><s:text
								name="coOperative.commodityOthers" /></label>
						<div class="form-element">
							<s:textfield name="warehouse.commodityOthers" theme="simple"
								maxlength="45" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('coOperative.warehouseOwnerShip')}" /></label>
						<div class="form-element">
							<s:select id="ownerShip" name="warehouse.warehouseOwnerShip"
								list="ownershipList" headerKey="" theme="simple"
								headerValue="%{getText('txt.select')}"
								cssClass="form-control input-sm select2" />
							<button type="button" id="addOwnerShipDetail"
								class="addBankInfo slide_open btn btn-sm btn-success"
								data-toggle="modal" data-target="#slideOwnership" onclick="clearData()">
								<i class="fa fa-plus" aria-hidden="true"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>




		<s:if test="enableStorage==1">
<s:if test='"create".equalsIgnoreCase(command)'>
				<div class="appContentWrapper marginBottom">
					<div class="formContainerWrapper">
						<h2>
							<s:property value="%{getLocaleProperty('coldStorageDetails')}" />
						</h2>
						<div class="flexiWrapper filterControls">

							<div class="flexi flexi10">
								<label for="txt"><s:property
										value="%{getLocaleProperty('coldStorageName')}" /> <span
									class="manadatory">*</span></label>
								<div class="form-element">
									<s:select id="coldStorageName" name="selectedColdStorageName"
										list="coldStorageNameList"
										headerValue="%{getText('txt.select')}" headerKey=""
										listKey="key" listValue="value"
										cssClass="form-control input-sm select2" />
								</div>
							</div>

							<div class="flexi flexi10">
								<label for="txt"><s:property
										value="%{getLocaleProperty('maxBayWeight')}" /> <span
									class="manadatory">*</span></label>
								<div class="form-element">
								<s:textfield id="maxBayWeight"  name="maxBayWeight"  theme="simple" maxlength="45"
								cssClass="form-control input-sm" onkeypress="return isNumber(event)"  />
									<%-- <s:textfield id="maxBayWeight" maxlength="5" name="selectedMaxBayHold"
									headerValue="%{getText('txt.select')}" headerKey=""
										listKey="key" listValue="value"
										cssClass="form-control input-sm"
										onkeypress="return isNumber(event)" /> --%>
								</div>
							</div>

							<div class="flexi flexi10 ">
								<label for="txt"><s:property
										value="%{getLocaleProperty('action')}" /></label>
								<td colspan="4" class="alignCenter">
									<table class="actionBtnWarpper">

										<td class="textAlignCenter">


											<button type="button" id="add" class="btn btn-sm btn-success"
												aria-hidden="true" onclick="addRow()"
												title="<s:text name="Ok" />">
												<i class="fa fa-check"></i>
											</button>
											<button type="button" class="btn btn-sm btn-success"
												aria-hidden="true" id="edit" onclick="addRow()"
												title="<s:text name="Edit" />">
												<i class="fa fa-check"></i>
											</button>
											<button type="button" class="btn btn-sm btn-danger"
												aria-hidden="true " onclick="resetProductData()"
												title="<s:text name="Cancel" />">
												<i class="glyphicon glyphicon-remove-sign"></i>
											</button>

										</td>
									</table>
								</td>

							</div>



						</div>
					</div>
				</div>



				<div class="appContentWrapper">
					<div class="formContainerWrapper">


						<table class="table table-bordered aspect-detail"
							id="procurementDetailTable"
							style="width: 100%; margin-top: 30px;">
							<thead>
								<tr>
									<th><s:property
											value="%{getLocaleProperty('coldStorageName')}" /></th>
									<th><s:property
											value="%{getLocaleProperty('maxBayWeight')}" /></th>
									<th style="text-align: center"><s:property
											value="%{getLocaleProperty('action')}" /></th>
								</tr>
							</thead>
							<tbody id="coldStorageDetailContent">
							<s:if test='"update".equalsIgnoreCase(command)'>
									<s:if test="warehouseStorageMapList.size()>0">
										<s:iterator value="warehouseStorageMapList" status="status" var="bean">
											<tr id='row<s:property value="%{#status.count-1}" />'>
											
												<td style="width: 40%"><s:property value="coldStorageName" /></td>
									          <td style="width: 40%"><s:property value="maxBayHold" /></td>
									          <td style="text-align:center;">
													<button type="button" class="btn btn-sm btn-success" aria-hidden="true" onclick="editRow('<s:property value="%{#status.count-1}" />')"><i class=" fa fa-pencil-square-o"></i></button>
													<button type="button" class="btn btn-sm btn-danger" aria-hidden="true" onclick="deleteProduct('<s:property value="%{#status.count-1}" />')"><i class="fa fa-trash" aria-hidden="true"></i></button>
												<td class="hide"><s:property value="id" /></td>
												<td class="hide coldStorageCode"><s:property value="coldStorageName" /></td>
												<td class="hide maxBayHold"><s:property value="maxBayHold" /></td>
												</td>	
											</tr>
										</s:iterator>
									</s:if>
								</s:if>	
							</tbody>
							<tfoot>


							</tfoot>
						</table>
					</div>
				</div>
		</s:if>
		</s:if>

		<br>
		<br>



		<div class="yui-skin-sam">
			<s:if test="command =='create'">
				<span id="button" class=""><span class="first-child">
						<button type="button" class="save-btn btn btn-success"
							onclick="onSubmit();">
							<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
							</font>
						</button>
				</span></span>
			</s:if>
			<s:else>
				<span id="button" class=""><span class="first-child">
						<button type="button" class="save-btn btn btn-success">
							<font color="#FFFFFF"> <b><s:text name="update.button" /></b>
							</font>
						</button>
				</span></span>
			</s:else>
			<span id="cancel" class=""><span class="first-child"><button
						type="button" class="cancel-btn btn btn-sts">
						<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
						</font></b>
					</button></span></span>
		</div>




		<div id="slideCommodity" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" id="model-close-commo-btn" class="close"
							data-dismiss="modal">&times;</button>
						<h4 style="border-bottom: solid 1px #567304;">
							<s:text name="addStorage" />
						</h4>
					</div>
					<div id="validateError"
						style="text-align: center; padding: 5px 0 0 0; color: red;"></div>

					<div class="modal-body">
						<table id="economyTable"
							class="table table-bordered aspect-detail fixedTable">
							<tr>
								<td><div class="col-xs-8">
										<s:text name="storageCommodity" />
										<sup style="color: red;">*</sup>
									</div></td>
								<td><div class="col-xs-8">
										<input type="text" id="commodityName" name="commodityName"
											style="padding: 5px; width: 150%;" maxlength="20">
									</div></td>
							</tr>
							<tr>
								<td colspan="2"><span class="" id="button_create"><span
										class="first-child">
											<button class="save-btn btn btn-success"
												id="saveCommodityDetail" type="button"
												onclick="saveCommodityInformation()">
												<font color="#FFFFFF"> <b><s:text name="Save" /></b>
												</font>
											</button>
									</span></span> <span class=""><span class="first-child">
											<button class="cancel-btn btn btn-sts" id="buttonEdcCancel"
												onclick="buttonStorageCancel()" type="button">
												<font color="#FFFFFF"> <s:text name="Cancel" />
												</font>
											</button>
									</span></span></td>
							</tr>
						</table>
					</div>
				</div>
			</div>

		</div>

		<div id="slideOwnership" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" id="model-close-ownership-btn" class="close"
							data-dismiss="modal">&times;</button>
						<h4 style="border-bottom: solid 1px #567304;">
							<s:text name="%{getLocaleProperty('addOwnership')}" />
						</h4>
					</div>
					<div id="validateOwnershipError"
						style="text-align: center; padding: 5px 0 0 0; color: red;"></div>

					<div class="modal-body">
						<table id="economyTable"
							class="table table-bordered aspect-detail fixedTable">
							<tr>
								<td><div class="col-xs-8">
										<s:text
											name="%{getLocaleProperty('coOperative.warehouseOwnerShip')}" />
										<sup style="color: red;">*</sup>
									</div></td>
								<td><div class="col-xs-8">
										<input type="text" id="ownerShipName" name="ownerShipName"
											style="padding: 5px; width: 150%;" maxlength="20">
									</div></td>
							</tr>
							<tr>
								<td colspan="2"><span class="" id="button_create"><span
										class="first-child">
											<button class="save-btn btn btn-success"
												id="saveOwnerShipDetail" type="button"
												onclick="saveOwnershipInformation()">
												<font color="#FFFFFF"> <b><s:text name="Save" /></font>
											</button>
									</span></span> <span class=""><span class="first-child">
											<button class="cancel-btn btn btn-sts" id="buttonEdcCancel"
												onclick="buttonOwnerShipCancel()" type="button">
												<font color="#FFFFFF"><s:text name="Cancel" /> </font>
											</button>
									</span></span></td>
							</tr>
						</table>
					</div>
				</div>
			</div>

		</div>
	</s:form>
	<s:form name="cancelform" action="cooperative_list.action">
		<s:hidden name="type" class="type" />
		<s:hidden key="currentPage" />
	</s:form>
</body>