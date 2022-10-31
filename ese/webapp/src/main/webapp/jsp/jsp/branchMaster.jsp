<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/jsp/common/form-assets.jsp"%>

<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<body>

	<s:form name="form" cssClass="fillform"
		action="branchMaster_%{command}">
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="branchMaster.id" />
		</s:if>
		<s:hidden key="command" />
			<div class="appContentWrapper marginBottom">
				<div class="error">
					<s:actionerror />
					<s:fielderror />


					<sup>*</sup>
					<s:text name="reqd.field" />
				</div>
				<div class="formContainerWrapper">
					<h2>
						<s:text name="info.branch" />
					</h2>
					<div class="flexform">

						<s:if test='"update".equalsIgnoreCase(command)'>

							<div class="flexform-item">
								<label for="txt"><s:text
										name="branchMaster.branchId.prop" /><sup style="color: red;">*</sup>
								</label>
								<div class="form-element">
									<s:textfield name="branchMaster.branchId"
							theme="simple" maxlength="8" disabled="true" class="form-control" /> <s:hidden
							key="branchMaster.branchId" />
								</div>
							</div>

						</s:if>
						<s:else>

							<div class="flexform-item">
								<label for="txt"><s:text
										name="branchMaster.branchId.prop" /><sup style="color: red;">*</sup>
								</label>
								<div class="form-element">
								<s:textfield name="branchMaster.branchId" 
							theme="simple" cssClass="lowercase form-control" maxlength="8" />
								</div>
							</div>
						</s:else>

						<div class="flexform-item">
							<label for="txt"><s:text name="branchMaster.name.prop" /> <sup style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="branchMaster.name" theme="simple"
									maxlength="45" class="form-control" />
							</div>
						</div>


						<div class="flexform-item">
							<label for="txt"><s:text
									name="branchMaster.contactPerson.prop" /></label>
							<div class="form-element">
								<s:textarea name="branchMaster.contactPerson"
									class="form-control" theme="simple" maxlength="255" />
							</div>
						</div>

						<div class="flexform-item">
							<label for="txt"><s:text name="branchMaster.phoneNo.prop" /></label>
							<div class="form-element">
								<s:textfield name="branchMaster.phoneNo" theme="simple"
									maxlength="10" class="form-control" />
							</div>
						</div>

						<div class="flexform-item">
							<label for="txt"><s:text name="branchMaster.address.prop" /></label>
							<div class="form-element">
								<s:textfield name="branchMaster.address" theme="simple"
									maxlength="90" class="form-control" />
							</div>
						</div>

						<div class="flexform-item">
							<label for="txt"><s:text name="status" /> <sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:radio list="statusMap" name="branchMaster.status"
									value="statusDeafaultVal" />
							</div>
						</div>

					</div>
					<div class="margin-top-10">
						<s:if test="command =='create'">
							<span id="button" class=""><span class="first-child">
									<button type="submit" class="save-btn btn btn-success">
										<font color="#FFFFFF"> <b><s:text
													name="save.button" /></b>
										</font>
									</button>
							</span></span>
						</s:if>
						<s:else>
							<span id="button" class=""><span class="first-child">
									<button type="submit" class="save-btn btn btn-success">
										<font color="#FFFFFF"> <b><s:text
													name="save.button" /></b>
										</font>
									</button>
							</span></span>
						</s:else>
						<span id="cancel" class=""><span class="first-child"><button
									type="button" class="cancel-btn btn btn-sts">
									<b><FONT color="#FFFFFF"><s:text
												name="cancel.button" /> </font></b>
								</button></span></span>
					</div>
				</div>
			</div>
		<br />


	</s:form>
	<s:form name="cancelform" action="branchMaster_list.action">
		<s:hidden key="currentPage" />
	</s:form>

	<script type="text/javascript">
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