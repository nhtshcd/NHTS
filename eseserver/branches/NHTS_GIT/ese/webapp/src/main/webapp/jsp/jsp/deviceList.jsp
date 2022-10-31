<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/ese-tags" prefix="e"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<head>
<META name="decorator" content="swithlayout">

</head>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<div id='warn' class="error">
	<s:actionerror />
</div>
<script type="text/javascript">
   $.fn.regexMask = function(mask) {
       $(this).keypress(function (event) {
           if (!event.charCode) return true;
           var part1 = this.value.substring(0, this.selectionStart);
           var part2 = this.value.substring(this.selectionEnd, this.value.length);
           if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
               return false;
       });
   };
   
   $(document).ready(function(){
   	//$( "#dialog-form" ).hide();
   	
   	latestVersion();
   	
   	var mask = new RegExp('^[a-zA-Z0-9\\-\\s]+$')
       $("#popName").regexMask(mask); 
       
   	$( "#popUp" ).hide();
   		var table =  	createDataTable('detail',"device_data.action");
 	var tableUnre = createDataTable('unRegDetail',"device_dataUnReg.action");
 	
   
   
   });
   
   function deleteUnregisteredDevice(deviceId){
   	var devId=deviceId;
   	if(confirm('<s:text name="confirm.delete"/>')){
   	  $.post("device_deleteUnRegDevice.action",{devId:devId},
   	        	function(data,status){
         	    alert('<s:text name="msg.removed"/>');
         	    location.reload();
   	     });
         }
   }
   
   function openModel(serialNo,devId){
   	$("#popSerialNoTxt").html(serialNo);
   	$("#popSerialNo").html(serialNo);
   	document.getElementById("popDeviceId").value=devId;
   	//enablePopup();
   }
   function exportXLS(){
   	 if(isDataAvailable("#detail")){
   		jQuery("#detail").setGridParam ({postData: {rows : 0}});
   		jQuery("#detail").jqGrid('excelExport', {url: 'device_populateXLS.action'});
   	}else{
   	     alert('<s:text name="export.nodata"/>');
   	}
   }
   
   function saveModel(){
   	var valid=true;
   	var deviceId=jQuery("#popDeviceId").val();
       var deviceName = jQuery("#popName").val();
       var deviceSerialNo = jQuery("#popSerialNo").html();
       var deviceIsRegistered = '0';
       if(deviceName =="" ){
       	alert('<s:text name="empty.name"/>');
       
       	valid=false;
       	
       }else{								
       	//alert("Heerererre");			
   		jQuery.post("device_updateUnReg.action",{devId:deviceId,deviceIsRegistered:deviceIsRegistered,deviceName:deviceName},function(data,status){
   			cancelModel();
   			$("#dialog-form").hide();
   			location.reload();
   		}); 
       }
   }
    
   function cancelModel(){
   	document.getElementById("popDeviceId").innerHTML="";
   	jQuery("#popName").val("");
   	document.getElementById("popSerialNo").innerHTML="";
   	$( "#popUp" ).hide();
   }
   
   function enablePopup(){		
   	$('body').css('overflow','hidden');
   	$('#popupBackground').css('width','100%');
   	$('#popupBackground').css('height',getWindowHeight());
   	$('#popupBackground').css('top','0');
   	$('#popupBackground').css('left','0');
   	$('#popupBackground').show();
   	$('#popUp').css({top:'50%',left:'50%',margin:'-'+($('#popUp').height() / 2)+'px 0 0 -'+($('#popUp').width() / 2)+'px'});
   	$('#popUp').show();
   	//window.location.hash="#popUp";
   }
   
   function disableExtendAlert(){
   	$('#pendingpopUpErrMsg').html('');
   	$('#popupBackground').hide();
   	$('#popUp').hide();
   	$('#popupPanelContent').hide();
   	$('body').css('overflow','hidden');
   	
   }
   
   function getWindowHeight(){
   	var height = document.documentElement.scrollHeight;
   	if(height<$(document).height())
   		height = $(document).height();
   	return height;
   }
   
   function latestVersion() {
   	
   	$.post("device_populateVersion.action",{},function(data){
   		var jsonData = $.parseJSON(data);
   		$.each(jsonData, function(index, value) {
   		if(value.name!=null&&value.name!==undefined&&value.name!=''){
   			//$("#version").text(value.name);
   		}
   	}); 
    });
   }
</script>
<body>
	
	<div class="pr-nav-tabs-wrap">
        <ul class="pr-nav-tabs nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item active" role="presentation" id="tab1">
              <a data-toggle="pill" href="#tabs-1">
				<s:text name="device.reg" />
			  </a>
            </li>
            <%-- <li class="nav-item" role="presentation" id="tab2">
              <a data-toggle="pill" href="#tabs-2">
				<s:text	name="device.unreg" />
			  </a>
            </li> --%>
        </ul>
    </div>

	<div class="">

		<div class="tab-content">
			<div id="tabs-1" class="tab-pane fade in active">
					
		<%-- 	<div  class="dataTable-btn-cntrls" id="detailBtn">
					<sec:authorize ifAllGranted="profile.device.create">
						<ul class="nav nav-pills newUI-nav-pills">
							<li class="" id=""><a data-toggle="pill" href="#"
								onclick="document.createform.submit()"> <i
									class="fa fa-plus"></i>Add
							</a></li>
						</ul>
					</sec:authorize>

				</div> --%>
 
					
				<div class="appContentWrapper border-radius datatable-wrapper-class">
				
					<table id="detail" class="table" style="width: 100%">
						<thead>
							<tr id="headerrow">

								<s:if test='branchId==null'>
									<th id="branch" class="hd"><s:text name="app.branch" /></th>
								</s:if>

								<s:if
									test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
									<th id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
								</s:if>


								<th id="code" class="hd"><s:text name="profile.device.code" /></th>
								<th id="name" class="hd"><s:text name="profile.device.name" /></th>
								<th id="exporter" class="hd"><s:text name="profile.exporter.name" /></th>
								<th id="serialNo" class="hd"><s:text
										name="profile.device.serialNumber" /></th>
								<th id="status" class="hd"><s:text
										name="profile.device.status" /></th>
								<th id="agentName" class="hd"><s:text name="agentName" /></th>
								<th id="version" class="hd"><s:text
										name="surveyMaster.versionn" /></th>
								<th id="lastLogin" class="hd"><s:text name="lastLogin" /></th>
							</tr>
						</thead>

					</table>


				</div>
				
			</div>
			<div id="tabs-2" class="tab-pane fade">
				<!--  This div should always be there and its id should be Table's id+Btn FOr ex detailBtn -->
			<div  class="dataTable-btn-cntrls" id="unRegDetailBtn">
	   
					
				</div>
			<div class="appContentWrapper border-radius datatable-wrapper-class">
					<!-- If you change tables id chage the id of above div too -->
						<table id="unRegDetail" class="table" style="width:100%">
							<thead>
								<tr id="headerrow" style="width: 100%">

									<th id="modTime" class="hd"><s:text
											name="device.transactionDate" /></th>
									<th id="serialNo" class="hd"><s:text
											name="profile.device.serialNumber" /></th>
									<th id="exporter" class="hd"><s:text name="profile.exporter.name" /></th>
									<th id="name" class="hd"><s:text
											name="profile.device.name" /></th>
									<th id="action" class="hd"><s:text name="action" /></th>
								</tr>
							</thead>

						</table>

					
				</div>
			</div>
		</div>
	</div>

	<s:form id="deviceForm" name="createform" action="device_create">
		<s:hidden id="deviceType" name="deviceType" />
		<s:hidden name="command" />
	</s:form>
	<s:form name="deviceDetailForm" id="deviceDetailForm"
		action="device_detail">
		<s:hidden id="id" name="id" />
		<s:hidden id="currentPage" name="currentPage" />
		<s:hidden name="command" />
	</s:form>
	<s:form id="deleteForm" action="device_delete">
		<s:hidden name="currentPage" />
		<s:hidden id="deleteId" name="id" />
		<s:hidden name="tabIndex" value="#tab2" />
		<s:hidden name="device.id" />
		<s:hidden name="device.code" />
		<s:hidden name="device.serialNumber" />
		<s:hidden name="device.name" />
	</s:form>
	<div id="slide" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" id="model-close-btn" class="close"
						data-dismiss="modal">&times;</button>
					<h4>
						<s:text name="device.unreg" />
					</h4>
				</div>
				<div class="modal-body">
					<table id="unRegTable" class="table table-bordered aspect-detail">
						<s:hidden id="popDeviceId" name="device.id" />
						<s:hidden id="unreg" name="device.isRegistered" value="1" />
						<s:hidden name="device.serialNo" id="popSerialNoTxt" />
						<tr class="odd">
							<td><s:text name="device.serialNo" /> <sup
								style="color: red;">*</sup></td>
							<td>
								<div id="popSerialNo"></div>
							</td>
						</tr>
						<tr class="odd">
							<td><s:text name="device.name" /> <sup style="color: red;">*</sup>
							</td>
							<td><s:textfield id="popName" name="device.name"
									maxlength="20" cssClass="form-control" /></td>
						</tr>
						<tr class="odd">
							<td colspan="2">
								<button type="button" Class="btnSrch btn btn-success"
									onclick="saveModel();" id="save">
									<s:text name="save" />
								</button>
								<button type="button" Class="btnClr btn btn-warning"
									onclick="cancelModel();" id="cancel" data-dismiss="modal">
									<s:text name="Cancel" />
								</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>