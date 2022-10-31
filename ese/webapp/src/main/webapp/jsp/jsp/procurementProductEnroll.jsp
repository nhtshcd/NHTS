<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<body>

<script type="text/javascript">

$(document).ready(function () {
    //called when key is pressed in textbox
  	$("#noOfDaysToGrow").keypress(function (e) {
     	//if the letter is not digit then display error and don't type anything
     	if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
        	//display error message
        	$("#errMsg").html("Digits Only").show().fadeOut("slow");
               return false;
     	}
   	});
});

</script>
<div class="error"><s:actionerror /><s:fielderror />
<sup>*</sup>
<s:text name="reqd.field" /></div>
<s:form name="form" cssClass="fillform" action="procurementProductEnroll_%{command}">
	<s:hidden key="currentPage"/>
	<s:hidden key="id" />
	<s:if test='"update".equalsIgnoreCase(command)'>
	<s:hidden key="procurementProduct.id" />
	</s:if>
	<s:hidden key="command" />
		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<h2><s:text name="info.procurementProducts" /></h2>
				<div class="flexiWrapper">
				<s:if test='"update".equalsIgnoreCase(command)'>
					<s:if test='branchId==null'>
						<div class="flexi flexi10">
								<label for="txt"><s:text name="app.branch" /></label>
								<div class="form-element">
									<s:property value="%{getBranchName(procurementProduct.branchId)}" />
								</div>
						</div>
					</s:if>
				</s:if>		
				</div>
				<s:if test="currentTenantId=='indong'&&!'update'.equalsIgnoreCase(command)">
					<div class="flexi flexi10">
						<label for="txt"><s:text name="product.code" /></label>
						<div class="form-element">
							<s:textfield cssClass="form-control input-sm" name="procurementProduct.code" theme="simple" maxlength="15"/><sup style="color: red;">*</sup>
						</div>
					</div>
				</s:if>	
				<s:elseif test="currentTenantId=='indong'">
					<div class="flexi flexi10">
						<label for="txt"><s:text name="product.code" /></label>
						<div class="form-element">
							<s:property value="procurementProduct.code" />
						</div>
					</div>
				</s:elseif>
				<div class="flexi flexi10">
					<label for="txt"><s:text name="product.name" /></label>
					<div class="form-element">
						<s:textfield cssClass="form-control input-sm" name="procurementProduct.name" theme="simple" maxlength="35"/><sup style="color: red;">*</sup>
					</div>
				</div>
			
			</div>
				<div class="yui-skin-sam"><s:if test="command =='create'">
		<span id="button" class=""><span class="first-child">
		<button type="button" class="save-btn btn btn-success"><font color="#FFFFFF"> <b><s:text
			name="save.button" /></b> </font></button>
		</span></span>
		</s:if> <s:else>
		<span id="button" class=""><span class="first-child">
		<button type="button" class="save-btn btn btn-success"><font color="#FFFFFF"> <b><s:text
			name="update.button" /></b> </font></button>
		</span></span></s:else>
		<span id="cancel" class=""><span class="first-child"><button type="button" class="cancel-btn btn btn-sts">
              <b><FONT color="#FFFFFF"><s:text name="cancel.button"/>
                </font></b></button></span></span>
	</div>
			
		</div>
			
	
</s:form>	