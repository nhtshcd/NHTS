<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<html>
<head>

<!-- add this meta information to select layout  -->
<META name="decorator" content="swithlayout">
<style type="text/css">
redText1 {
	color: #CB171D;
	float: left;
}
</style>

</head>
<body>

	<div style="padding-top: 10px;">
		<s:form method="post" name="form" id="form" cssClass="fillform"
			action="device_%{command}">


			<div class="appContentWrapper marginBottom">
				<font color="red"> <s:actionerror /> <s:fielderror /> <sup>*</sup>
					<s:text name="reqd.field" />
				</font>
				<s:hidden key="id" id="id" />
				<s:hidden name="device.deviceType" value="Mobile" />
				<s:if test='"update".equalsIgnoreCase(command)'>
					<s:hidden name="device.id" />
					<s:hidden name="agentName" />
				</s:if>
				<s:hidden name="command" />
				<s:hidden name="heading" />
				<s:hidden name="currentPage" />
				<div class="formContainerWrapper">
					<h2>
						<s:text name="info.detail" />
					</h2>
					<div class=flexform>
						<s:if test='"update".equalsIgnoreCase(command)'>


							<div class="flexform-item">
								<label for="txt"><s:text name="profile.device.code" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:property value="device.code" />
									<s:hidden name="device.code" />
								</div>
							</div>
						</s:if>
						
						<s:if test='"create".equalsIgnoreCase(command)'>
							<div class="flexform-item ">
								<label for="txt"><s:text name="deviceAddress" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<%-- <s:radio id="deviceAddress" list="#{'0':'Serial Number','1':'Mac Address'}"
								name="device.deviceAddress"
								value="DeviceAddressDefaultValue"
								onchange="getDeviceAddress(this.value);" /> --%>
							<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
									<s:text name="Serial Number" />
									
								</div>
							</div>

						</s:if>
						<div class="flexform-item serialNo">
							<label for="txt"><s:text
									name="profile.device.serialNumber" /><sup style="color: red;">*</sup>
							</label>
							<div class="form-element">
								<s:if test='"update".equalsIgnoreCase(command)'>
									<s:textfield disabled="true" name="device.serialNumber"
										cssClass="form-control"></s:textfield>
									<s:hidden name="device.serialNumber" />
								</s:if>
								<s:else>
									<s:textfield name="device.serialNumber" maxLength="35"
										cssClass="form-control" />
								</s:else>
							</div>
						</div>
						<%-- <s:if test='"create".equalsIgnoreCase(command)'>
							<div class="flexform-item macAddress ">
								<label for="txt"><s:text name="macAddress" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:textfield name="device.macAddress" maxLength="20"
										cssClass="form-control " />
								</div>
							</div>


						</s:if> --%>
						<%-- <div class="flexform-item">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('exporter')}" /> <sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:select class="form-control  select2 " id="exporter"
								headerKey="" headerValue="%{getText('txt.select')}"
								name="device.exporter.id" listKey="key" listValue="value"
								list="exporterList" />
						</div>
					</div> --%>
						<div class="flexform-item">
							<label for="txt"><s:text name="profile.device.name" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="device.name" maxLength="20"
									cssClass="form-control " />
							</div>
						</div>
						<s:if test='"update".equalsIgnoreCase(command)'>
							<div class="flexform-item">
								<label for="txt"><s:text
										name="profile.device.deviceStatus" /></label><br>
								<div class="form-element">
									<s:hidden name="device.deviceType" />
									<s:radio list='listDeviceStatus' name="device.enabled"></s:radio>
								</div>
							</div>
						</s:if>
						<s:if test='"update".equalsIgnoreCase(command)'>
							<div class="flexform-item">
								<label for="txt"><s:text name="agentName" /></label>
								<div class="form-element">
									<s:if test='"".equalsIgnoreCase(agentName)'>
										<s:select name="agentId" id="agentSelect"
											list="agentListNotMapWithDevice" headerKey="-1"
											headerValue="%{getText('txt.select')}" listKey="key"
											listValue="value" cssClass="form-control input-sm select2"
											theme="simple" />

									</s:if>
									<s:else>
										<s:textfield disabled="true" cssStyle="width:200px;"
											value="%{agentName}"></s:textfield>
									</s:else>
								</div>
							</div>
						</s:if>
					</div>
					<div style="margin-top: 10px;">
						<s:if test="command =='create'">
							<span id="button_create" class=""><span
								class="first-child">
									<button type="button" onclick="onSubmit()"
										class="save-btn btn btn-success">
										<font color="#FFFFFF"> <b><s:text
													name="save.button" /></b>
										</font>
									</button>
							</span></span>
							<span class=""><span class="first-child">
									<button type="button" onclick="onCancel();"
										class="cancel-btn btn btn-sts">
										<font color="#FFFFFF"> <s:text name="cancel.button" />
										</font>
									</button>
							</span></span>
						</s:if>
						<s:else>
							<s:if test="branchId!=null">
								<span id="button" class=""><span class="first-child">
										<button type="button" onclick="onSubmit()"
											class="save-btn btn btn-success">
											<font color="#FFFFFF"> <b><s:text
														name="update.button" /></b>
											</font>
										</button>
								</span></span>
							</s:if>
							<span class=""><span class="first-child">
									<button type="button" onclick="onCancel();"
										class="cancel-btn btn btn-sts">
										<font color="#FFFFFF"> <s:text name="cancel.button" />
										</font>
									</button>
							</span></span>
						</s:else>
					</div>
				</div>
			</div>



		</s:form>
		<s:form name="deviceListForm" id="deviceListForm" action="device_list">
			<s:hidden name="currentPage" />
		</s:form>
	</div>

	<script>
		function validateCombo() {
			if (document.getElementById("deviceTypeList") != null) {
				document.getElementById("deviceType").value = "";
				if (document.getElementById("deviceTypeList").value != "-1") {
					document.getElementById("deviceType").value = document
							.getElementById("deviceTypeList").value;
				}
			}
		}

		function onSubmit() {
			validateCombo();
			document.form.submit();
		}

		function onCancel() {
			document.deviceListForm.submit();
		}

		jQuery(document).ready(
				function() {
					/* var address = $('input:radio[name="device.deviceAddress"]:checked').val();
					getDeviceAddress(address); */
				});

	/* 	function getDeviceAddress(val) {
			if (val == '0') {
				$(".macAddress").hide();
				$(".serialNo").show();
			} else {
				$(".serialNo").hide();
				$(".macAddress").show();
			}
		} */
	</script>
</body>
</html>