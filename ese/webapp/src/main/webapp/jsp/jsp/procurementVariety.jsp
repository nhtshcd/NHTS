<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<script src="plugins/jquery-input-mask/jquery.inputmask.bundle.min.js"></script>
<script>
jQuery(document).ready(function(){
	
	var tenant='<s:property value="getCurrentTenantId()"/>';
	
	if(tenant=='kpf'||tenant=='wub') {
			$(".hrvestDiv").show();
	}else{
		$(".hrvestDiv").hide();
	}
	
	//jQuery("#proPrefix").inputmask({"alias": "currency","prefix":""});
	
	//called when key is pressed in textbox
  	/* $("#proPrefix").keypress(function (e) {
     	//if the letter is not digit then display error and don't type anything
     	if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
        	//display error message
        	$("#errMsg").html("Digits Only").show().fadeOut(2000);
               return false;
     	}
   	}); */
   	
    $("#proPrefix").inputmask({"alias": "currency","prefix":""}); 
   	
	/* $("#proPrefix").on("keypress keyup blur",function (event) {
        //this.value = this.value.replace(/[^0-9\.]/g,'');
 		$(this).val($(this).val().replace(/[^0-9\.]/g,''));
 		//alert(event.which);
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57) && (event.which != 8)) 
        {
            event.preventDefault();
        }
    }); */
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

//below function to check yield value and set it a double value.
/* function checkYield(){
	var yieldValue = $("#proPrefix").val();
	if(yieldValue===""){
		$("#proPrefix").val("0.00");
	}
} */


</script>
<body>
<div class="error"><s:actionerror /><s:fielderror />
<sup>*</sup>
<s:text name="reqd.field" /></div>
<s:form name="form" cssClass="fillform" action="procurementVariety_%{command}">
	<s:hidden key="currentPage"/>
	<s:hidden name="procurementProductId" value="%{procurementProductId}" />
<s:hidden name="procurementProductCodeAndName" />
	<s:if test='"update".equalsIgnoreCase(command)'>
	
	<s:hidden key="procurementVariety.id" />
	<s:hidden name="tabIndex" />
	</s:if>
	<s:hidden key="command" />
		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<h2><s:text name="info.procurementVariety" /></h2>
				<div class="flexiWrapper">
					<div class="flexi flexi10">
							<label for="txt"><s:property value="%{getLocaleProperty('product.name')}" /></label>
							<s:if test='"update".equalsIgnoreCase(command)'>
							<div class="form-element">
								<s:property value="%{procurementProductCodeAndName}" />
							</div>
						</s:if>	
						<s:else>
							<s:property value="%{procurementProductCodeAndName}" />
						</s:else>
					</div>
					<div class="flexi flexi10">
						<label for="txt"><s:property value="%{getLocaleProperty('procurementVariety.name')}" /><sup
						style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield cssClass="form-control input-sm" name="procurementVariety.name" theme="simple" maxlength="45"/>
						</div>
					</div>
					<div class="flexi flexi10">
						<label for="txt"><s:text name="procurementVariety.noDaysToGrow" /><s:if test="currentTenantId=='kpf'|| currentTenantId=='wub'|| currentTenantId=='simfed'">
						<sup style="color: red;">*</sup>
						</s:if></label>
						<div class="form-element">
							<s:textfield cssClass="form-control input-sm" name="procurementVariety.noDaysToGrow" theme="simple" onkeypress="return isNumber(event)" maxlength="35"/>
						</div>
					</div>
					<div class="flexi flexi10">
						<label for="txt"><s:property value="%{getLocaleProperty('procurementVariety.yield')}" /><s:if test="currentTenantId=='kpf'|| currentTenantId=='wub'|| currentTenantId=='simfed'">
						<sup style="color: red;">*</sup>
						</s:if>
						</label>
						<div class="form-element">
							<s:textfield id="proPrefix" name="procurementVariety.yield"
													maxlength="12" onkeypress="return isDecimal(event)"
													 />
						</div>
					</div>
					<div class="flexi flexi10 hrvestDiv">
							<label for="txt"><s:text name="procurementVariety.harvDays" /></label>
							<div class="form-element">
								<s:textfield cssClass="form-control input-sm" name="procurementVariety.harvestDays" theme="simple" onkeypress="return isNumber(event)" maxlength="35"/>
							</div>
					</div>
				</div>
					<div class="yui-skin-sam"><s:if test="command =='create'">
		<span id="button" class=""><span class="first-child">
		<button type="button" onclick="checkYield();" class="save-btn btn btn-success"><font color="#FFFFFF"> <b><s:text
			name="save.button" /></b> </font></button>
		</span></span>
		</s:if> <s:else>
		<span id="button" class=""><span class="first-child">
		<button type="button" onclick="checkYield();" class="save-btn btn btn-success"><font color="#FFFFFF"> <b><s:text
			name="update.button" /></b> </font></button>
		</span></span></s:else>
		<span id="cancel" class=""><span class="first-child"><button type="button" class="cancel-btn btn btn-sts">
              <b><FONT color="#FFFFFF"><s:text name="cancel.button"/>
                </font></b></button></span></span>
	</div>
			</div>
		</div>
	<br />

</s:form>
<s:form name="cancelform" action="procurementProductEnroll_detail.action%{tabIndexVariety}">
	<s:hidden name="id" value="%{procurementProductId}" />
	<s:hidden key="currentPage"/>
	<s:hidden name="tabIndex" value="%{tabIndexVariety}" />
    <s:hidden key="currentPage"/>
</s:form>
</body>