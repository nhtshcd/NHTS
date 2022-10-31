<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/ese-tags" prefix="e"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<head>
<!-- add this meta information to select layout  -->
<META name="decorator" content="swithlayout">



</head>
<script src="https://cdn.ckeditor.com/4.10.1/standard/ckeditor.js"></script>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
%>


<ul class="nav nav-pills">

	<li class="active"><a data-toggle="pill" href="#home"><s:property
				value="getLocaleProperty('General')" /></a></li>

</ul>

<div class="tab-content">
	<div id="home" class="tab-pane fade in active">

		<s:form name="prefernceupdateform" cssClass="fillform" method="post"
			enctype="multipart/form-data">
			<!-- action="prefernce_%{command}" -->
			<s:hidden key="id" />
			<s:hidden key="temp" id="temp" />
			<s:hidden name="viewName" id="viewName" />
			<s:hidden name="reportName" id="reportName" />
			<s:hidden name="valStr" id="valStr" />
			<s:hidden name="dealerExpireJson" id="dealerExpireJson" />

			<div class="appContentWrapper marginBottom">
				<div class="formContainerWrapper">
					<div class="message">
						<s:actionmessage cssStyle="color: red;" />
					</div>
					<div class="error">
						<s:actionerror />
						<s:fielderror />
						<div id="validateError" style="text-align: left;"></div>
					</div>
					<h2>
						<s:property value="getLocaleProperty('generalinformation')" />
					</h2>
					<div class="flexform">
						<div class="flexform-item">
							<label for="txt"><s:property
									value="getLocaleProperty('noOfInvalidAttempts')" /><sup>&nbsp;*</sup></label>
							<div class="form-element">
								<s:textfield name="noOfInvalidAttempts" theme="simple"
									maxLength="2" cssClass="form-control input-sm" />
							</div>
						</div>

						<div class="flexform-item">
							<label for="txt"><s:property
									value="getLocaleProperty('timeToAutoRelease')" /><sup>&nbsp;*</sup></label>
							<div class="form-element">
								<s:textfield name="timeToAutoRelease" theme="simple"
									maxLength="10" cssClass="form-control input-sm" />
							</div>
						</div>

					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:property
								value="getLocaleProperty('passwordMinLength')" /><sup>&nbsp;*</sup></label>
						<div class="form-element">
							<s:textfield name="passwordMinLength" theme="simple"
								maxLength="5" cssClass="form-control input-sm" id='passwordMinLength' onkeypress="return isNumber(event)"/>
						</div>
					</div>
					
					<%-- <div class="flexform-item">
						<label for="txt"><s:property
								value="getLocaleProperty('passwordMaxLength')" /><sup>&nbsp;*</sup></label>
						<div class="form-element">
							<s:textfield name="passwordMaxLength" theme="simple"
								maxLength="5" cssClass="form-control input-sm" id='passwordMaxLength' onkeypress="return isNumber(event)"/>
						</div>
					</div> --%>
					
					<div class="flexform-item">
						<label for="txt"><s:property
								value="getLocaleProperty('age')" /><sup>&nbsp;*</sup></label>
						<div class="form-element">
							<s:textfield name="age" theme="simple"
								maxLength="5" cssClass="form-control input-sm" id='age' onkeypress="return isNumber(event)"/>
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:property
								value="getLocaleProperty('reminderDays')" /><sup>&nbsp;*</sup></label>
						<div class="form-element">
							<s:textfield name="reminderDays" theme="simple"
								maxLength="5" cssClass="form-control input-sm" id='reminderDays' onkeypress="return isNumber(event)"/>
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:property value="getLocaleProperty('favIcon')" /><sup>&nbsp;</sup></label>
						<div class="form-element">
							<s:if
								test='favIconImageByteString!=null && favIconImageByteString!=""'>
								<img width="25" height="25" border="0"
									src="data:image/ico;base64,<s:property value="favIconImageByteString"/>">
							</s:if>
							<s:else>
								<img align="middle" width="150" height="100" border="0"
									src="img/no-image.png">
							</s:else>



						</div>
						<div>
							<s:file name="favIconImage" id="favIconImage" />
							<s:property value="getLocaleProperty('favIcon.imageTypes')" />
						</div>

					</div>
					
				</div>



				<div class="flexItem flex-layout flexItemStyle">
					<sec:authorize ifAllGranted="system.prefernces.update">
						<span id="button" class="yui-button"><span
							class="first-child">
								<button type="button" class="save-btn btn btn-success">
									<FONT color="#FFFFFF"><b><s:property
												value="getLocaleProperty('save.button')" /></b></font>
								</button>
						</span></span>
					</sec:authorize>
				</div>
			</div>
	</div>
	</s:form>

</div>







<script type="text/javascript">
function showSingleOrMultiBranch(evt)
{
	var val=$(evt).val();
	if(val==1){
		//alert("YES");
		$('#singleBranchTr').show();
		$('#multiBranchTr').hide();
	}
	else
	{
		//alert("NO");
		$('#multiBranchTr').show();
		$('#singleBranchTr').hide();
	}
}

function validateLoginLogoImage()
{
	var file=document.getElementById('loginLogoImage').files[0];
	var filename=document.getElementById('loginLogoImage').value;
	var fileExt=filename.split('.').pop();			

	if(file != undefined){
									   
		if(fileExt=='jpg' || fileExt=='jpeg' || fileExt=='png'||fileExt=='JPG'||fileExt=='JPEG'||fileExt=='PNG')
		{ 			
			if(file.size>51200){
				alert('<s:property value="getLocaleProperty('fileSizeExceeds')"/>');	
				file.focus();
				return false;			
			}//else if(imgWidth >260){
				//alert('<s:property value="getLocaleProperty('imageWidthMsg')"/>');
				//file.focus();
				//return false;	
			//}else if(imgHeight> 70){
				//alert('<s:property value="getLocaleProperty('imageHeightMsg')"/>');
				//file.focus();
				//return false;	
			//}
		}else{
			alert('<s:property value="getLocaleProperty('invalidFileExtension')"/>')	
			file.focus();
			return false;				
		}
	}
	return true;
}
function resetFieldProp(){
	jQuery('#fieldsTable > tbody').html('');
}
function validateFavIconImage()
{
	var file=document.getElementById('favIconImage').files[0];
	var filename=document.getElementById('favIconImage').value;
	var fileExt=filename.split('.').pop();			

	if(file != undefined){
									   
		if(fileExt=='ico' || fileExt=='ICO')
		{ 			
			if(file.size>51200){
				alert('<s:property value="getLocaleProperty('fileSizeExceeds')"/>');	
				file.focus();
				return false;			
			}//else if(imgWidth >260){
				//alert('<s:property value="getLocaleProperty('imageWidthMsg')"/>');
				//file.focus();
				//return false;	
			//}else if(imgHeight> 70){
				//alert('<s:property value="getLocaleProperty('imageHeightMsg')"/>');
				//file.focus();
				//return false;	
			//}
		}else{
			alert('<s:property value="getLocaleProperty('invalidFileExtension')"/>')	
			file.focus();
			return false;				
		}
	}
	return true;
}
var enableLoanModule = "<s:property value='enableLoanModule'/>";
$(document).ready(function()
{	
	$('.butCls').show();
	$('.viewDiv').hide();
	$('.fieldsSetupDiv').hide();
	//populateSubMenuDropDown("0");
	var ese_ent_Name;
	var actionId;
	var roleId;
	$("#orderSubMenus").hide();
	$("#save_subMenu_div").hide();
	$("#delete_subMenu_div").hide();
	if(enableLoanModule=='1'){
		refreshLoanTemplateList();
		refreshLoanDetails();
	}
	<!--s:if test='getUsername().equalsIgnoreCase(getExecUser())'-->
	var username = '<s:property value="username"/>';
	var user = '<s:property value="user"/>';

	if(username.toUpperCase()===user.toUpperCase())
	{
		var radioValue = $("input[name='enableBranch']:checked").val();
		if(radioValue==1)
		{
			$('#singleBranchTr').show();
		}
		else
		{
			$('#multiBranchTr').show();
		}
	}
	
	$('.save-btn').on('click',function()
	{
		<!--s:if test='getUsername().equalsIgnoreCase(getExecUser())'-->
		if(username.toUpperCase()===user.toUpperCase())
		{
			validateLoginLogoImage();
			validateFavIconImage();
		}
		var d1=$('#date1').val();
		var d2=$('#date2').val();
		var d3=$('#date3').val();
		var d4=$('#date4').val();
		var d5=$('#date5').val();
		var d6=$('#date6').val();
		var d7=$('#date7').val();
		var d8=$('#date8').val();
		//var d9=$('').val();
		var arr ="";
		var hit=true;
		var dobj={};
	
		if(hit){
		document.prefernceupdateform.action = "prefernce_update.action";
		document.getElementById('temp').value="yes";
		document.prefernceupdateform.submit();
		}else{
			$("#validateError").html(arr);
		}
	});

	$('#geoFincingFlg').change(function(evt) {
        if($(this). prop("checked") == true){        	
            $('#geofincingrad').show();
        } else {        	
        	$('#geofincingrad').hide();
        }            
    });
	
	var geoFlag = '<s:property value="geoFincingFlg" />';
	// alert(geoFlag);
	if(geoFlag == 'true') {		 
		 $('#geofincingrad').show();
	} else {		
		$('#geofincingrad').hide();
	}
	
	 
	 
	//CKEDITOR.replace( 'htmleditor' );
	//editContentToHtmlEditor();			
	var populateDealerJson = "<s:property value='populateDealerJson'/>";	
	populateDealerJson=populateDealerJson.replace(/&quot;/g, '\"');
	var jsonDeal = jQuery.parseJSON(populateDealerJson);
	
	for (var key in jsonDeal) {
		    $('#'+key).val(jsonDeal[key]);
		}
	
});

function saveHtmlContent(){
	var objhtmleditor = CKEDITOR.instances["htmleditor"];
	var htmlContent = objhtmleditor.getData();
	
	$.ajax({
		 type: "POST",
         async: false,
         data:{htmlContent:htmlContent},
      	 url: "prefernce_saveContracteTemplate",
      	 success: function(result) {
      		showPopup(result.msg,result.title);
       }
	});
	
	/* var win = window.open("", "Title", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=780,height=200,top="+(screen.height-400)+",left="+(screen.width-840));
	win.document.body.innerHTML = objhtmleditor.getData();
	win.print(); */
}

function addContentToHtmlEditor(txt){
	CKEDITOR.instances['htmleditor'].insertText(txt);
	
	
}

function editContentToHtmlEditor(){
	 $.ajax({
		 type: "POST",
         async: false,
         data:{},
      	 url: "prefernce_getContracteTemplate",
      	 success: function(result) {
      		var json = jQuery.parseJSON(result);
      		var html_str = json.templateHtml;
      		var objHtmlEditor = CKEDITOR.instances["htmleditor"];
      		var htmlContent = objHtmlEditor.getData();
      		htmlContent = htmlContent+html_str;
      		CKEDITOR.instances['htmleditor'].setData(htmlContent);
      	
      	 	}
      	 });
}

	// Dynamic menu creation,delete
	function populateSubMenuDropDown(parentMenuId){
		jQuery.post("prefernce_populateSubMenus.action",{parentMenuId:parentMenuId},function(result){
			insertOptions("subMenuDropDown",jQuery.parseJSON(result));
			
		});
	}
	
	function deleteSubMenu(subMenuId){
		var sub_menu_id = $("#subMenuDropDown").val();
		jQuery.post("prefernce_deleteSubMenu.action",{subMenuId:sub_menu_id},function(result){
			showPopup(result.msg,result.title);
			
			$("#parentMenus_dropDown_delete").val("0")
			document.getElementById('subMenuDropDown').options.length = 0;
			populateSubMenuDropDown("0");
			
			$("#delete_subMenu_div").toggle(1000);
			$("#save_subMenu_btn").toggle(1000);
			$("#delete_subMenu_btn").toggle(1000);
		});
		
	}
	
	function saveSubMenu(){
		
		var valStr="";
		var view="";
		var reportName="";
		  $('#fieldsTable > tbody > tr').each(function() {
			  $(this).find('.viwFlt').each(function(){
				  var type = $(this).attr('type');
				  var value=0;
					if(type=="checkbox"){
						if ($(this).is(':checked')) {
							value=1;
						}
					}
					else{
					value = $(this).val();
					}
					if(valStr!="")
						valStr=valStr+"~"+value;
					else
						valStr=value;
			  });
			  valStr=valStr+"#";
		  });
		 // alert("valStr: "+valStr);
		if(valStr!=''){
			valStr = 	valStr.split("#~").join("#");
		  
		 view=document.getElementById("allView").value;
		   reportName=document.getElementById("menuDescription").value;
		
		}
	myFunction:{
		var parentId = $("#parentMenuIdForSaveSubMenu").val();
		var menuName = $("#menuName").val();
		var menuDescription = $("#menuDescription").val();
		var menuUrl = $("#menuUrl").val();
		//var menuOrder = $("#menuOrder").val(); 
		var menuOrder = "1";
		
		if(parentId == "0"){
			alert("Please select Parent Menu");
			break myFunction;
		}
		
		if(menuName != ""){
			
			 var isChecked = checkRadioButtonCheckedOrNot();
				if(isChecked == true){
					var ese_ent_NAME = menuName+"."+ese_ent_Name;
					
				} 
		}else{
			alert("Please enter Name")
			break myFunction;
		}
		
		var actionId_Value = $("#action_id_dropDown").val();
		if(actionId_Value != "0"){
			var ese_action_ACTIONID = actionId;
		}else{
			alert("Please select Action_Id");
			break myFunction;
		}
		
		var roleId_Value = $("#role_id_dropDown").val();
		if(roleId_Value != "0"){
			var role_ID = roleId;
		}else{
			alert("Please select Role_Id ");
			break myFunction;
		}
		if(valStr!=''){
			ese_ent_NAME = 'report.dynamicReport.list';
		}
		if(menuDescription==''){
			menuDescription =menuName; 
		}
		
		if(valStr=='' && menuUrl==''){
			alert("Please enter URL")
			break myFunction;
		}
		if(parentId != "0" && menuName != "" && menuDescription != "" && menuUrl != "" && menuOrder != "" && ese_ent_NAME != undefined && ese_action_ACTIONID != "" && role_ID != ""){
			
			jQuery.post("prefernce_saveSubMenu.action",{parentId:parentId,menuName:menuName,menuDescription:menuDescription,menuUrl:menuUrl,menuOrder:menuOrder,ese_ent_name:ese_ent_NAME,ese_action_actionId:ese_action_ACTIONID,role_id:role_ID,valStr:valStr,viewName:view,reportName:reportName},function(result){
				
				if(result.msg == "Menu name already exist"){
				showPopup(result.msg,result.title);
				}else{
				showPopup("created new menu successfully","success");
				$('#allView').val('').trigger('change');
				$('#fields').val('').trigger('change');
				$('.viewDiv').hide();
				insertOptions_sortable("sortable",JSON.parse(result));
				
				$("#save_subMenu_div").toggle(1000);
				$("#orderSubMenus").toggle(1000);
				}
				//$( "#sortable" ).append('<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s" title="<s:property value="getLocaleProperty('tipsTitle')" />"></span>'+$(this).text()+'</li>');
			});
		}/* else{
			
			alert("Please enter all the mandatory fields")
		} */
		
	}
		
}
	
	/* function checkRadioButtonCheckedOrNot(){
		var isChecked = false;
		 isChecked = $('#ese_ent_radio1').prop('checked');
		if(isChecked == false){
			 isChecked = $('#ese_ent_radio2').prop('checked');
			 if(isChecked == false){
				 isChecked = $('#ese_ent_radio3').prop('checked');
				 if(isChecked == false){
					 isChecked = $('#ese_ent_radio4').prop('checked');
				 }
			 }
		}
		
		return isChecked;
	} */
	
	
	function checkRadioButtonCheckedOrNot(){
		var isChecked = false;
			if(isChecked == false){
				for (i=1;i<=4;i++){
					var  id = "#ese_ent_radio"+i;
					isChecked = $(id).prop('checked');
					if(isChecked == true){
						 break;
					}
				}
			}
		return isChecked;
	}
	
	function unCheckRadioButton(){
		document.getElementById('ese_ent_radio1').checked = false;
		document.getElementById('ese_ent_radio2').checked = false;
		document.getElementById('ese_ent_radio3').checked = false;
		document.getElementById('ese_ent_radio4').checked = false;
			
	}
	
    function insertOptions_sortable(ctrlName, jsonArr) {
        for (var i = 0; i < jsonArr.length; i++) {
            addOption_sortable(document.getElementById(ctrlName), jsonArr[i].name, jsonArr[i].id);
        }
        var id="#"+ctrlName;
        
    }
	
	
	function addOption_sortable(selectbox, text, value)
    {
        $( "#sortable" ).append('<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s" title="<s:property value="getLocaleProperty('tipsTitle')" />"></span>'+value+'</li>');
     }
	
	function cancelFormSubmit() {
		$("#cancelForm").submit();
	}
	
	function getEse_ent_Name(obj){
		ese_ent_Name = obj.value;
		
	}
	
	function getActionId(obj){
		//alert(obj.value)
		actionId = obj.value;
	}
	
	function getRoleId(obj){
		//alert(obj.value)
		roleId = obj.value;
	}
	
	function save_subMenu_show(){
		$("#save_subMenu_div").toggle(1000);
		$("#delete_subMenu_div").hide();
		$("#save_subMenu_btn").hide();
		$("#delete_subMenu_btn").hide();
	}
	
	function delete_subMenu_show(){
		$("#delete_subMenu_div").toggle(1000);
		$("#save_subMenu_div").hide();
		$("#save_subMenu_btn").hide();
		$("#delete_subMenu_btn").hide();
	}
	
	$( function() {
	    $( "#sortable" ).sortable();
	    $( "#sortable" ).disableSelection();
	    
	  } );
	
	function getValueFromSortable(){
		
		var value = document.getElementById("sortable");	
		var id = "";
		$(value).find("li").each(function(i,v){
			var str = $(this).text();
			
				for(var i=0; i<str.length;i++) {
				    if (str[i] == "-"){
				    	id = id+",";
					    break;
				      }else{
				    	id = id+str[i];
				    }
				}
			
			
		});
		
		jQuery.post("prefernce_updateSubMenusOrders.action",{subMenusOrder:id},function(result){
			showPopup(result.msg,result.title);
			
			$("#menuName").val("");
			$("#menuDescription").val("");
			$("#menuUrl").val("");
			$("#parentMenuIdForSaveSubMenu").val("0");
			$("#action_id_dropDown").val("0");
			$("#role_id_dropDown").val("0");
			unCheckRadioButton();
			var sortable_element = document.getElementById("sortable");
			sortable_element.innerHTML = '';
			
			$("#orderSubMenus").toggle(1000);
			$("#save_subMenu_btn").toggle(1000);
			$("#delete_subMenu_btn").toggle(1000);
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

	function listView(){
		if (document.getElementById('isView').checked) 
		  {
			$('.viewDiv').show();
			$('.butCls').hide();
			$('#menuUrl').parent().parent().hide();
			
			$("input[name='ese_ent']").parent().parent().hide();
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "prefernce_populateViews",
		        success: function(result) {
		       	 if(result.length==0){
		       	 }else{
		       		 insertOptions("allView",JSON.parse(result));
		       		 
		       	 }
		        }
			}); 
		  }else{
			  $('#menuUrl').parent().parent().show();
				
				$("input[name='ese_ent']").parent().parent().show();
			
		  }
		  
	}
function getViewFields(){
	var view=document.getElementById("allView").value;
	$.ajax({
		 type: "POST",
       async: false,
       data:{viewName:view},
       url: "prefernce_populateViewFields",
       success: function(result) {
      	 if(result.length==0){
      	 }else{
      		 insertOptions("fields",JSON.parse(result));
      	 }
       }
	});
}
function loadFieldsProperties(){
	 var selectedFields = ""+$("#fields").val()
	 var fls=selectedFields.split(",");
	$('.fieldsSetupDiv').show();
	var tbodyRow = "";
	  $.each(fls, function(i,e){
	tbodyRow += '<tr class="trCls">'+
	'<td><input type="hidden" class="viwFlt" value="'+e+'" name="entyNam_'+e+'"/>'+e+'</td>'+
	'<td><input type="text" class="viwFlt" name="lblNam_'+e+'"/></td>'+
    '<td><input type="text" class="viwFlt" size="3" maxlength="2" onkeypress="return isNumber(event)" name="ordr_'+e+'"/></td>'+
	'<td><input type="checkbox" class="viwFlt" name="grdAvil_'+e+'"/></td>'+
	'<td><select class="viwFlt" name="alin_'+e+'"><option value="">Select</option><option value="left">Left</option><option value="right">Right</option><option value="center">Center</option></select></td>'+
	
	
	'<td><select class="viwFlt" name="footTot_'+e+'"><option value="0">No</option><option value="2">Label On</option><option value="1">Yes</option></select></td>'+
	
	'<td><input class="viwFlt" type="checkbox" onkeypress="return isNumber(event)" name="isFiltr_'+e+'"/></td>'+
	
	'<td><select class="viwFlt" name="filterTyp_'+e+'"><option value="0">Select</option><option value="1">Text Box</option><option value="3">Drop Down</option><option value="4">Date</option></select></td>'+
	'<td><input class="viwFlt" type="text" name="filterMethod_'+e+'"/></td>'+
	'<td><input class="viwFlt" type="text" size="3" onkeypress="return isNumber(event)" maxlength="2" name="filterOrdr_'+e+'"/></td>'+
		'<td><input class="viwFlt" type="checkbox" name="datFilter_'+e+'"/></td>'+
	'</tr>';
	  });
	jQuery('#fieldsTable > tbody').html(tbodyRow);
}
function isNumber(evt) {
		
	    evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	        return false;
	    }
	    return true;
	}
	
function saveLoanDetails(){
	var flag=0;
	var paramObj = {};	
	var minRange='';
	var maxRange='';
	var interest='';
	jQuery(".tableTr1").each(function(){
		
		jQuery(this).find(".tableTd28").each(function(){
			//columnModelName1 = jQuery.trim($(this).val());
			minRange= jQuery.trim($(this).val());
			if(minRange == "" || minRange == '0'){
				alert('<s:property value="getLocaleProperty('Please Enter the Min Range Greater Than Zero')"/>');
				 flag=1;
				return exit;
			}
			
		});
		
		jQuery(this).find(".tableTd29").each(function(){
			//columnModelName2 = jQuery.trim($(this).val());
			maxRange =jQuery.trim($(this).val());
			 if(maxRange == ''|| maxRange == '0'){
				alert('<s:property value="getLocaleProperty('Please Enter the Max Range Greater Than Zero')"/>');
				flag=1;
				return exit;
			} 
			 
		});
		
		
		
		jQuery(this).find(".tableTd30").each(function(){
			//var columnModelName3 = jQuery.trim($(this).val());
			interest =jQuery.trim($(this).val());
			 if(interest == ''){
				alert('<s:property value="getLocaleProperty('Please Enter the Interest')"/>');
				flag=1;
				return exit;
			} 
			 
		});

		if(parseInt(minRange)>=parseInt(maxRange)){
			alert('<s:property value="getLocaleProperty('Please Enter Min Range Less Than Max Range')"/>');
			flag=1;
			return exit;
		}
  
	});
	
	var loanInterestList=[];
	
		 var objfI1=new Object();
		 if(!isEmpty(minRange) || !isEmpty(maxRange) || !isEmpty(interest)){

		objfI1.minRange=minRange;
		
		objfI1.maxRange=maxRange;
		
		objfI1.interest=interest;
		
		loanInterestList.push(objfI1);
		
		}
	//alert(JSON.stringify(loanInterestList));
	var postData=new Object();

	postData.loanDetailJsonString=JSON.stringify(loanInterestList);

	if(loanInterestList.length>0){
		console.log('INTEREST===='+JSON.stringify(loanInterestList));
	console.log('here '+JSON.stringify(postData));

	if(flag==0){
		
	 $.ajax({
         url: 'prefernce_populateLoanInfo.action',
         async: false,
         type: 'post',
         data: postData,
         dataType: 'json',
         success: function (data) {
        	// alert(JSON.stringify(data));
        	 var dat = jQuery.parseJSON(JSON.stringify(data));
        	// alert(dat);
        	 if(dat.msg=='success'){
        	 $('#tableBody1').empty();
        	// refreshLoanDetails();
        	 refreshLoanTemplateList();
        	 //addLoan();
        	 }else{	 
        		 alert(dat.msg);
        	 }
         },
         
     });
	}else
	{
		alert('<s:property value="getLocaleProperty('entervalues')"/>');
		}
	}else{
		alert('<s:property value="getLocaleProperty('entervalues')"/>');
	}
	
	// refreshLoanDetails();
	 refreshLoanTemplateList();
	 refreshLoanDetails();
}
function refreshLoanDetails(){
	$(".tableTd28").val("");
	$(".tableTd29").val("");
	$(".tableTd30").val("");
}
function refreshLoanTemplateList(){
	$('#tableTemplate').hide();
	 $('#tBodyTemplate').empty();
	 $('#template').empty();
	 $('#template').append("<option value=''>Select Template</option>");
	 $.getJSON('prefernce_populateLoanList.action',function(jd){
		 var templates=jd.data;
		 var bodyContent='';
		 for(var i=0;i<templates.length;i++){
			 var template=templates[i];
			 bodyContent+='<tr>';
			 bodyContent+='<td class="hide">'+template.id+'</td>';
			 //bodyContent+='<td align="center">'+template.farmName+'</td>';
			 bodyContent+='<td align="center">'+template.min+'</td>';
			 bodyContent+='<td align="center">'+template.max+'</td>';
			 bodyContent+='<td align="center">'+template.interest+'</td>';
			 bodyContent+='<td align="center"><a href="#" class="fa fa-trash" onclick="butLoanDelete('+template.id+');"/></td>';
			 bodyContent+='</tr>';
			 $('#template').append("<option value='"+template.id+"'>"+template.min+""+template.max+""+template.interest+"</option>");
				$('#tableTemplate').show();
		 }
		
		 $('#tBodyTemplate').html(bodyContent);
		 $('#template').val('');
	 });
}


function butLoanDelete(val){
	var templateId=val;
	if(confirm('<s:property value="getLocaleProperty('confirm.delete')"/>')){
	  $.post("prefernce_deleteLoanRange.action",{templateId:templateId},
	        	function(data,status){
      		alert('<s:property value="getLocaleProperty('msg.removed')"/>');
      		refreshLoanTemplateList();
      	
	        	});
      }
}
function isDecimal(evt) {
	
	 evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46) {
	        return false;
	    }
	    return true;
}


function isNumber1(evt) {

	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 49 || charCode > 57)) {
		return false;
	}
	return true;
}



</script>


<style>
#sortable {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 100%;
}

#sortable li {
	margin: 0 3px 3px 3px;
	padding: 0.4em;
	padding-left: 1.5em; /*font-size: 1.4em;*/
	font-size: 12px;
	height: 60px;
}

#sortable li span {
	position: absolute;
	margin-left: -1.3em;
	cursor: pointer;
}
</style>