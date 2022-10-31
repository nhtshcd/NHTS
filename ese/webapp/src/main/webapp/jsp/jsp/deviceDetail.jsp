<%@ taglib uri="/struts-tags" prefix="s"%>
<!--<script type="text/javascript" src="yui/element/element-beta-min.js"></script>
<script type="text/javascript" src="yui/button/button-min.js"></script>-->
<%@ include file="/jsp/common/detail-assets.jsp"%>

<head>
<!-- add this meta information to select layout  -->
<META name="decorator" content="swithlayout">



</head>
<body>
	<div id="style" style="padding-left: 30px;">
		<font color="red"> <s:actionerror />
		</font> <br />
	</div>

	<div class="fullwidth">
		<div class="flexWrapper">
			<div class="flexLeft appContentWrapper">
				<div class="formContainerWrapper dynamic-form-con">
					<h2>
						<s:text name="info.detail" />
					</h2>
					<s:if test='branchId==null'>
						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="app.branch" />
							</p>
							<p class="flexItem">
								<s:property value="%{getBranchName(country.branchId)}" />
							</p>
						</div>
					</s:if>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="profile.device.code" />
						</p>
						<p class="flexItem">
							<s:property value="device.code" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="profile.device.serialNumber" />
						</p>
						<p class="flexItem">
							<s:property value="device.serialNumber" />
						</p>
					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="profile.device.name" />
						</p>
						<p class="flexItem">
							<s:property value="device.name" />
						</p>
					</div>
					
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('exporter')}" />
						</p>
						<p class="flexItem">
							<s:property value="device.exporter.name" />
						</p>
					</div>


					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="profile.device.deviceStatus" />
						</p>
						<p class="flexItem">
							<s:if test='device.enabled'>
								<s:text name="status.enabled" />
							</s:if>
							<s:else>
								<s:text name="status.disabled" />
							</s:else>
						</p>
					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('agentName')}" />
						</p>
						<p class="flexItem">
							<s:if test='((agentName==null)||(agentName.equals(" ")))'>
								<s:text name="noMapWithAgent" />&nbsp;
							</s:if>
							<s:else>
								<s:property value="agentName" />&nbsp;
							</s:else>
						</p>
					</div>



				</div>

				<div class="yui-skin-sam">
					<sec:authorize ifAllGranted="profile.device.update">
						<span id="update" class=""><span class="first-child">
								<button type="button" onclick="onUpdate();"
									class="edit-btn btn btn-success">
									<FONT color="#FFFFFF"> <b><s:text name="edit.button" /></b>
									</font>
								</button>
						</span></span>
					</sec:authorize>
					<sec:authorize ifAllGranted="profile.device.delete">
						<span id="delete" class=""><span class="first-child">
								<button type="button" onclick="onDelete();"
									class="delete-btn btn btn-warning">
									<FONT color="#FFFFFF"> <b><s:text
												name="delete.button" /></b>
									</font>
								</button>
						</span></span>
					</sec:authorize>
					<span id="cancel"> <span class="first-child"><button
								type="button" onclick="onCancel();" class="back-btn btn btn-sts">
								<b><FONT color="#FFFFFF"><s:text name="back.button" />
								</font></b>
							</button></span></span>


					<s:if test='device.agent!=null'>
						<sec:authorize ifAllGranted="profile.device.update">
							<span id="reset" class=""> <span class="first-child">
									<button type="button" onclick="onReset();"
										class="reset-btn btn btn-sts">
										<b> <FONT color="#FFFFFF"> <s:text
													name="reset.button" />
										</FONT>
										</b>
									</button>
							</span>
							</span>
						</sec:authorize>
					</s:if>
				</div>
			</div>

		</div>
	</div>

	
	<script type="text/javascript">
		function resetComplete(){
			modal.open({content: '<s:text name="unMapAgentSuccess"/>'});
			e.preventDefault();
		}
	</script>
	<s:if test='unMapAgent'>

		<script type="text/javascript">
		 resetComplete();
	</script>

	</s:if>
	<s:form name="deviceUpdateForm" id="deviceUpdateForm"
		action="device_update" cssClass="fillform">
		<s:hidden name="id" />
		<s:hidden name="currentPage" />
		<s:hidden name="command" />
		<s:hidden name="agentName" />
	</s:form>
	<s:form name="deviceDeleteForm" id="deviceDeleteForm"
		action="device_delete" cssClass="fillform">
		<s:hidden name="currentPage" />
		<s:hidden name="id" />
		<s:hidden name="device.id" />
		<s:hidden name="device.code" />
		<s:hidden name="device.serialNumber" />
		<s:hidden name="device.name" />
		<s:hidden name="device.deviceType" />
	</s:form>
	<s:form name="deviceListForm" id="deviceListForm" action="device_list"
		cssClass="fillform">
		<s:hidden name="currentPage" />
	</s:form>
	<s:form name="deviceResetForm" id="deviceResetForm"
		action="device_reset" cssClass="fillform">
		<s:hidden name="id" />
		<s:hidden name="currentPage" />
		<s:hidden name="command" />
		<s:hidden name="device.id" />
		<s:hidden name="device.code" />
		<s:hidden name="device.serialNumber" />
		<s:hidden name="device.name" />
		<s:hidden name="device.deviceType" />
		<s:hidden name="device.enabled" />
		<s:hidden name="device.exporter.name" />
		<s:hidden name="device.exporter.id" />
		<s:hidden name="hold" value="1" />
	</s:form>
	<s:if test='pendingMTNTExists==true'>
		<s:hidden name="device.agent.profileId" id="agentID" />
		<script>
		var actionFrom = "device";
		function submitForm(){			
			document.deviceResetForm.submit();
		}
	</script>
		<s:include
			value="../switch/general/procurementProductTransferPopup.jsp"></s:include>
	</s:if>
	<script>
function redirectList(){
	document.deviceListForm.submit();
}
function onUpdate(){
	
	document.deviceUpdateForm.submit();
}

function onDelete(){
	var val = confirm('<s:text name="confirm.delete"/>');
	if(val)
		document.deviceDeleteForm.submit();
}

function onCancel(){	
		document.deviceListForm.submit();
}
function onReset(){
	var val = confirm('<s:text name="confirm.unmapAgent"/>');
	if(val)
	document.deviceResetForm.submit();
}
var modal = (function(){
	var 
	method = {},
	$overlay,
	$modal,
	$content,
	$close;

	// Center the modal in the viewport
	method.center = function () {
		var top, left;

		top = Math.max($(window).height() - $modal.outerHeight(), 0) / 2;
		left = Math.max($(window).width() - $modal.outerWidth(), 0) / 2;

		/*$modal.css({
			top:top + $(window).scrollTop(), 
			left:left + $(window).scrollLeft()
		});*/
		$modal.css({top:'50%',left:'50%',margin:'-'+($modal.height() / 2)+'px 0 0 -'+($modal.width() / 2)+'px'});
	};

	// Open the modal
	method.open = function (settings) {
		$content.empty().append(settings.content);

		$modal.css({
			width: settings.width || 'auto', 
			height: settings.height || 'auto'
		});

		method.center();
		$(window).bind('resize.modal', method.center);
		$modal.show();
		$overlay.show();
	};

	// Close the modal
	method.close = function () {
		$modal.hide();
		$overlay.hide();
		$content.empty();
		$(window).unbind('resize.modal');
	};

	// Generate the HTML and add it to the document
	$overlay = $('<div id="overlay"></div>');
	$modal = $('<div id="modal"></div>');
	$content = $('<div id="content"></div>');
	$close = $('<a id="close" href="#">close</a>');
	$btn = $('<button id="clk" type="button" onclick="redirectList();">OK</button>');

	$modal.hide();
	$overlay.hide();
	$modal.append($content, $close, $btn);

	$(document).ready(function(){
		$('body').append($overlay, $modal);						
	});

	$close.click(function(e){
		e.preventDefault();
		method.close();
	});

	return method;
}());

</script>
</body>