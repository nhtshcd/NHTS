<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<link rel="stylesheet"
	href="plugins/selectize/css/selectize.bootstrap3.css">
<script src="plugins/selectize/js/standalone/selectize.min.js?v=2.0"></script>
<head>

<META name="decorator" content="swithlayout">
</head>
<style>
.datepicker-dropdown {
	width: 300px;
}
</style>
<body>

	<div class="error">
		<div style="float: right; color: #000000">
			<s:hidden name="farmerUniqueId" id="farmerUniqueId" />
			<table class="table table-bordered aspect-detail">
				<tr>
					<td style="padding-right: 5px; text-align: right;"><b><s:property
								value="%{getLocaleProperty('farmer.name')}" /></b> <s:if
							test='"update".equalsIgnoreCase(command)'>
							<s:property value="farm.farmer.farmerId" /> - <s:property
								value="farm.farmer.name" />
						</s:if> <s:else>
							<s:property value="farmerName" />
						</s:else></td>
				</tr>
			</table>
		</div>
	</div>

	<s:form name="form" cssClass="fillform" action="farm_%{command}"
		role="form" method="post" enctype="multipart/form-data">
		<s:hidden key="currentPage" />

		<s:hidden id="farmerId" name="farmerId" />
		<s:hidden id="farmerUniqueId" name="farmerUniqueId" />
		<s:hidden id="farmerName" name="farmerName" />
		<s:hidden name="tabIndex" />

		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden key="farm.id" class="uId" />
		</s:if>
		<s:if test='"updateActPlan".equalsIgnoreCase(command)'>
			<s:hidden key="farm.id" class="uId" />
		</s:if>
		<s:hidden key="command" id="command" />
		<s:hidden id="treeDetailToJson" name="treeDetailToJson" />
		<div class="appContentWrapper marginBottom farmInfo">
			<div class="formContainerWrapper">
				<div class="ferror" id="errorDiv" style="color: #ff0000">
					<s:actionerror />
					<s:fielderror />
				</div>
				<h2>
					<a data-toggle="collapse" data-parent="#accordion" href="#farmInfo"
						class="accrodianTxt"> <s:property
							value="%{getLocaleProperty('info.farm')}" />
					</a>
				</h2>
			</div>

			<div id="farmInfo" class="panel-collapse collapse in">
				<div class="flexform">

					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.farmName')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="farm.farmName" theme="simple" maxlength="35"
								cssClass="form-control  checktexts" />
						</div>
					</div>

					<div class="flexform-item plantArea">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.totalLandHolding')}" /> (<s:property
								value="%{getAreaType()}" />) <sup style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="farm.totalLandHolding" id="totalLandHolding"
								cssClass="form-control " theme="simple" maxlength="12"
								onkeypress="return isDecimal(event)" />
						</div>
					</div>

					<div class="flexform-item plantArea">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('farm.proposedPlantingArea')}" /> (<s:property
								value="%{getAreaType()}" />) <sup style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="farm.proposedPlantingArea"
								id="proposedPlantingArea" cssClass="form-control "
								theme="simple" maxlength="12"
								onkeypress="return isDecimal(event)" />
						</div>
					</div>


				</div>
			</div>
		</div>



		<div class="">
			<div class="flexItem flex-layout flexItemStyle">
				<div class="button-group-container">
					<s:if test="command =='create'">
						<button type="button" onclick="onSubmit();"
							class="save-btn btn btn-success">
							<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
							</font>
						</button>
					</s:if>
					<s:else>
						<button type="button" onclick="onSubmit();"
							class="save-btn btn btn-success">
							<font color="#FFFFFF"> <b><s:text name="update.button" /></b>
							</font>
						</button>
					</s:else>
					<button type="button" onclick="onCancel();"
						class="cancel-btn btn btn-warning">
						<b> <FONT color="#FFFFFF"> <s:text name="cancel.button" />
						</FONT>
						</b>
					</button>
				</div>
			</div>
		</div>


	</s:form>

	<s:form name="listForm" id="listForm" action="farmer_detail.action">
		<s:hidden name="farmerId" value="%{farmerId}" />
		<s:hidden name="id" value="%{farmerUniqueId}" />
		<s:hidden name="tabIndexFarmer" />
		<s:hidden name="tabIndex" value="%{tabIndexFarmerZ}" />
		<s:hidden name="currentPage" />
	</s:form>

	<script type="text/javascript">

        function onCancel() {
            document.listForm.submit();
        }

     function isNumber(evt) {
 	    evt = (evt) ? evt : window.event;
 	    var charCode = (evt.which) ? evt.which : evt.keyCode;
 	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
 	        return false;
 	    }
 	    return true;
 	}
  </script>
</body>
