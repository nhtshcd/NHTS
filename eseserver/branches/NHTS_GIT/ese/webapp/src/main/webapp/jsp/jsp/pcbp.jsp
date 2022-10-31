<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType")
					: "swithlayout"%>">

</head>


<script type="text/javascript">

	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>";
	}
	var url =<%out.print("'" + request.getParameter("url") + "'");%>;
	var layOut='<%=request.getParameter("layoutType")%>	';
	var status = '<s:property value="pcbp.status"/>';
	var command = '<s:property value="command"/>';
	$(document)
			.ready(
					function() {
						$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
						url =<%out.print("'" + request.getParameter("url") + "'");%>;
						if (url == null || url == undefined || url == ''
								|| url == 'null') {
							url = 'pcbp_';
						}
						
						var tenant = '<s:property value="getCurrentTenantId()"/>';
						var branchId = '<s:property value="getBranchId()"/>';
					
						 
						$("#bAdd,#bUpdate").on(
								'click',
								function(event) {
									event.preventDefault();
									$("#buttonAdd1").prop('disabled', true);
							
									if (!validateAndSubmit("target", url)) {
										$("#buttonAdd1")
												.prop('disabled', false);
										$("#buttonUpdate").prop('disabled',
												false);
									}
								});
						
						
						
						/* var cropcat=$.trim('<s:property value="pcbp.cropcat"/>');
						$('#cropcat').val(JSON.parse(JSON.stringify(cropcat.replace(/\s/g,'').split(",")))).select2();
						$('#cropcat').change(); */
						
						var cropname=$.trim('<s:property value="selectedCropName"/>');
							$('#cropName').val(cropname).trigger('change');
						
						/* var cropvar=$.trim('<s:property value="pcbp.cropvariety"/>');
						$('#cropvariety').val(JSON.parse(JSON.stringify(cropvar.replace(/\s/g,'').split(",")))).select2(); */
								
						//loadDependancy();
						
						 var cropV='<s:property value="selectedVariety" />';
							$('#cropvariety').val(cropV).trigger('change');
						
						
					});
	
		function loadWarehouses(soa){	
			var nameArr = soa.split(','); 	
			var i=0;
			while ( i < nameArr.length ) {
				//for (j = 0; j < nameArr.length; j++){
				if(nameArr[i] == 0){						
					$("#scopeOfAccreditation option[value='0']").attr("selected", "selected");
				}
				if(nameArr[i] == 1){						
					$("#scopeOfAccreditation option[value='1']").attr("selected", "selected");
				}
				if(nameArr[i] == 2){						
					$("#scopeOfAccreditation option[value='2']").attr("selected", "selected");
				}
				if(nameArr[i] == 3){						
					$("#scopeOfAccreditation option[value='3']").attr("selected", "selected");
				}
				i++;
				
		   }

		}
			
		
	
		
	function loadVariety(val){
			
			var selectedProduct=$("#cropcat").val();
			/* var rsdata="";
			var arr=new Array();
			var rat=""; */
			$('#cropName').val("").select2();
			$('#cropvariety').val("").select2();
			/* for(var i=0;i<selectedProduct.length;i++)  //iterate through array of selected rows
		    {
		       var ret = selectedProduct[i];  
		       arr.push(ret);
		    }
		   rsdata=arr.join(",");
		   rsdata= rsdata.trim(); */
			if (!isEmpty(val)) {
				$.ajax({
					type : "POST",
					async : false,
					url : "pcbp_populateVarietyPcbp.action",
					data : {
						selectedProduct : selectedProduct
					},
					success : function(result) {
						insertOptionswithoutselect("cropName", $.parseJSON(result));
						
					},

				});

			}
			
		} 
		
		function listProGrade(selectedvariety) {
			var selectedProduct=$("#cropName").val();
			/* var rsdata="";
			var arr=new Array();
			var rat=""; */
			$('#cropvariety').val("").select2();
			/* for(var i=0;i<selectedProduct.length;i++)  //iterate through array of selected rows
		    {
		       var ret = selectedProduct[i];  
		       arr.push(ret);
		    }
		   rsdata=arr.join(",");
		   rsdata= rsdata.trim(); */
			if(!isEmpty(selectedvariety)){
				$.ajax({
					 type: "POST",
			        async: false,
			        url: "pcbp_populateGradePcbp.action",
				        data: {procurementVariety : selectedProduct},
			        success: function(result) {
			        	insertOptionswithoutselect("cropvariety", $.parseJSON(result));
			        }
				});
			}
 
			
		}
		
		function isAlphaNumber(evt) {

			evt = (evt) ? evt : window.event;
			var charCode = (evt.which) ? evt.which : evt.keyCode;
			if ((charCode > 47 && charCode < 58) || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123)) {
				return true;
			}
			return false;

		}
		
</script>


<body>
	<s:form name="form" cssClass="fillform" method="post"
		action="pcbp_%{command}" enctype="multipart/form-data"
		id="target">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:hidden id="proofFile1" name="proofFile1" />

		<s:hidden name="expRegis.status" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="pcbp.id" />
		</s:if>
		<s:hidden key="command" />
		<div class="appContentWrapper marginBottom">

			<div class="error">
				<%-- 	<s:actionerror /> --%>
				<s:fielderror />
				<s:if test="hasActionErrors()">
					<div style="color: red;">
						<s:actionerror />

					</div>
				</s:if>
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>

			<div class="formContainerWrapper">
				<h2>
					<!--<s:text name="info.export" /> -->
					<s:property value="%{getLocaleProperty('info.pcbp')}" />
				</h2>
				<div class="flexform">

				<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.cropcat" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="cropcat"
								name="selectedCropCat" listKey="id" listValue="name"
								list="productList" maxlength="20"
								onchange="loadVariety(this.value);"
								headerValue="%{getText('txt.select')}" headerKey="" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.fcropName" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2" id="cropName"
								name="selectedCropName" maxlength="20" listKey="id"
								listValue="name" list="procurementVarietyList" onchange="listProGrade(this.value);"
								headerValue="%{getText('txt.select')}" headerKey="" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.cropvariety" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control  select2" id="cropvariety"
								name="selectedVariety" maxlength="20" listKey="id"
								listValue="name" list="{}"
								headerValue="%{getText('txt.select')}" headerKey="" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.tradeName" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<%-- onkeypress="return isAlphaNumber(event)" --%>
							<s:textfield name="pcbp.tradeName" theme="simple"
							
								cssClass="lowercase form-control" id="tradeName" maxlength="100" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.registrationNo" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
						<!-- onkeypress="return isAlphaNumber(event)" -->
							<s:textfield name="pcbp.registrationNo" theme="simple"
								
								cssClass="lowercase form-control" maxlength="20" />
						</div>
					</div>
					
					<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.activeing" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
						<!-- onkeypress="return isAlphaNumber(event)" -->
							<s:textfield name="pcbp.activeing" theme="simple"
								
								cssClass="lowercase form-control" maxlength="100" />
						</div>
					</div>
					
					
					<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.manufacturerReg" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
						<!-- onkeypress="return isAlphaNumber(event)" -->
							<s:textfield name="pcbp.manufacturerReg" theme="simple"
								
								cssClass="lowercase form-control" maxlength="300" />
						</div>
					</div>

					

					<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.agent" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
						<!-- onkeypress="return isAlphaNumber(event)" -->
							<s:textfield name="pcbp.agent" theme="simple"
								
								cssClass="lowercase form-control" maxlength="100" />
						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.phiIn" /> </label>
						<div class="form-element">
							<s:textfield name="pcbp.phiIn" theme="simple"
								onkeypress="return isNumber(event)"
								cssClass="lowercase form-control" maxlength="3" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.dosage" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="pcbp.dosage" theme="simple"
								onkeypress="return isNumber(event)"
								cssClass="lowercase form-control" maxlength="7" />
						</div>
					</div>



				<%-- 	<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.uom" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
								<s:select id="uom" name="pcbp.uom" list="listUom" headerKey=""
									listKey="code" listValue="name"
									headerValue="%{getText('txt.select')}"
									cssClass="form-control select2" />
								
								
						</div>
					</div> --%>
					<div class="flexform-item">
						<label for="txt"><s:text name="pcbp.uom" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
								<s:select id="uom" name="pcbp.uom" list="listUom" headerKey=""
									listKey="key" listValue="value"
									headerValue="%{getText('txt.select')}"
									cssClass="form-control select2" />
								
								
						</div>
					</div>
					
				<%-- 	<div class="flexform-item">
						<label for="txt"><s:text
								name="pcbp.chemicalName" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2 " id="chemicalName"
								name="pcbp.chemicalName" Key="id"
								Value="name" list="chemicalList" headerKey=""
								headerValue="%{getText('txt.select')}"
								 />
						</div>
					</div> --%>
				


				</div>


				<div class="margin-top-10">
					<s:if test="command =='create'">
						<span class=""><span class="first-child">
								<button type="button" id="bAdd" class="save-btn btn btn-success">
									<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
									</font>
								</button>
						</span></span>
					</s:if>
					<s:else>
						<span class=""><span class="first-child">
								<button type="submit" id="bUpdate"
									class="save-btn btn btn-success">
									<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
									</font>
								</button>
						</span></span>
					</s:else>
					<span id="cancel" class=""><span class="first-child"><button
								type="button" class="cancel-btn btn btn-sts"
								onclick="onCancel();">
								<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
								</font></b>
							</button></span></span>
				</div>
			</div>
		</div>
		<br />


	</s:form>



</body>
<script type="text/javascript">
/* function sendSms() {
    
    var number = jQuery("#mobileNos").val();
    var message = jQuery("#composeMsg").val();
    var smsTemplate = jQuery("#smsTemplate").val();
    if(number==''){
    	alert('<s:text name="SMSmobileNumber.empty"/>');
		return false;        	
    }
    if(number.length<10){
    	alert('<s:text name="SMSmobileNumber.validation"/>');
		return false;
    }
    if(smsTemplate==''){
    	alert('<s:text name="smsTemplate.empty"/>');
		return false; 
    }
    if(message==''){
    	alert('<s:text name="smsmessage.empty"/>');
		return false; 
    }
  	$("#myOverlay").show();
	 $(".loader").show(); 
    jQuery.post("sMSAlert_sendSms.action", {
        mobileNos: number,
        message: message
    }, function(result) {
    	 $(".loader").hide();
    	 $("#myOverlay").hide();
    	 var data = jQuery.parseJSON(result);
    	// alert(data.message);
        jQuery("#mobileNos").val('');
        jQuery("#composeMsg").val('');
        jQuery("#smsTemplate").val('');
        location.reload();
        //var data = jQuery.parseJSON(result);
        //jQuery("#composeMsg").val(data.message);
        //calcTextLength();
    });
} */
</script>

