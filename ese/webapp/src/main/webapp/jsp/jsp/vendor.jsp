<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/jsp/common/form-assets.jsp"%>

<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<body>

<s:form name="form" cssClass="fillform" action="vendor_%{command}">
	<s:hidden key="currentPage"/>
	<s:hidden key="id" />
	<s:if test='"update".equalsIgnoreCase(command)'>
	<s:hidden name="vendor.id" />
	</s:if>
	<s:hidden key="command" />
				<div class="appContentWrapper marginBottom">
					<div class="error"><s:actionerror /><s:fielderror />
					<s:if test="hasActionErrors()">
					  <div style="color:red;">
						<s:text name="cannotDeleteVendorHasTxn"/>
					      <s:actionerror/>
					   </div>
					</s:if>
					
					<%-- <sup>*</sup>
					<s:text name="reqd.field" /> --%></div>
					<div class="formContainerWrapper">
						<h2><s:text name="info.vendor" /></h2>
						<div class="flexiWrapper">
							
							<div class="flexi flexi10">
									<label for="txt"><s:text name="vendor.vendorName" /><sup style="color: red;">*</sup></label>
									<div class="form-element">
										<s:textfield name="vendor.vendorName" theme="simple" maxlength="90" />
									</div>
							</div>
							
							<div class="flexi flexi10">
									<label for="txt"><s:text name="vendor.vendorAddress" /></label>
									<div class="form-element">
										<s:textarea name="vendor.vendorAddress" cssStyle="width:100%" theme="simple" maxlength="255" />
									</div>
							</div>
							
						
							
							<div class="flexi flexi10">
									<label for="txt"><s:text name="vendor.personName" /></label>
									<div class="form-element">
										<s:textfield name="vendor.personName" theme="simple"  maxlength="90"/>
									</div>
							</div>
						
						</div>
						
							<div class="flexiWrapper registered-farmer">
							<div class="flexi flexi10">
									<label for="txt"><s:text name="vendor.mobileNumber" /></label>
									<div class="form-element">
										<s:textfield name="vendor.mobileNo" theme="simple" maxlength="10"/>
									</div>
							</div>
							
							<div class="flexi flexi10">
									<label for="txt"><s:text name="vendor.email" /></label>
									<div class="form-element">
									<s:textfield name="vendor.emailId" theme="simple" maxlength="90" />
									</div>
							</div>
						</div>
							<div ><s:if test="command =='create'">
								<span id="button" class=""><span class="first-child">
								<button type="button" class="save-btn btn btn-success"><font color="#FFFFFF"> <b><s:text
									name="save.button" /></b> </font></button>
								</span></span>
								</s:if> <s:else>
								<span id="button" class=""><span class="first-child">
								<button type="button" class="save-btn btn btn-success"><font color="#FFFFFF"> <b><s:text
									name="save.button" /></b> </font></button>
								</span></span></s:else>
								<span id="cancel" class=""><span class="first-child"><button type="button" class="cancel-btn btn btn-sts">
						              <b><FONT color="#FFFFFF"><s:text name="cancel.button"/>
						                </font></b></button></span></span>
							</div>
					</div>	
				</div>	
	<br />


</s:form>
<s:form name="cancelform" action="vendor_list.action">
    <s:hidden key="currentPage"/>
</s:form>
</body>