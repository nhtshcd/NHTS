<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<font color="red"> <s:actionerror />
</font>
<div class="error">
	<s:fielderror />
</div>
<s:hidden key="agent.id" id="id" />
<s:form name="form" cssClass="fillform">
	<s:hidden key="currentPage" />
	<s:hidden key="agent.id" />
	<s:hidden key="type" id="type" />
	<s:hidden key="command" />
	<s:hidden name="redirectContent" id="redirectContent" />
	
<div class="flex-view-layout">
	<div class="fullwidth">
		<div class="flexWrapper">
			<div class="flexLeft appContentWrapper">
				<div class="formContainerWrapper dynamic-form-con">
					<h2><s:text name='%{"info"+type}' /></h2>
					<s:if test='branchId==null'>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="app.branch" /></p>
						<p class="flexItem"><s:property
								value="%{getBranchName(agent.branchId)}" /></p>
					</div>
					</s:if>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name='%{"agentName"+type}' /></p>
						<p class="flexItem"><s:property value="agent.profileId" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name='exporter' /></p>
						<p class="flexItem"><s:property value="agent.exporter.name" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name='agent.agentType' /></p>
						<p class="flexItem"><s:property value="agent.agentType.name" /></p>
					</div>
					
					
					<s:if test="agent.agentType.code==02 ">
						<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="%{getLocaleProperty('pHouse')}" /></p>
						<p class="flexItem"><s:property value="agent.packhouse.name" /></p>
					</div>
					</s:if>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="personalInfo.firstName" /></p>
						<p class="flexItem"><s:property
							value="agent.personalInfo.firstName" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="personalInfo.lastName" /></p>
						<p class="flexItem"><s:property
							value="agent.personalInfo.lastName" /></p>
					</div>
			
						
					
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="personalInfo.sex" /></p>
						<%-- <p class="flexItem"><s:text name='%{agent.personalInfo.sex}' /></p> --%>
						<%-- <p class="flexItem"><s:property value="agent.personalInfo.sex" /></p> --%>
						
						<p class="flexItem" id="SexPesonalInfo"
									style="font-weight: bold;"></p>
						
						
					</div>
					<%-- <div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="personalInfo.identityType" /></p>
						<p class="flexItem"><s:text
							name='%{agent.personalInfo.identityType}' /></p>
					</div> --%>
					
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="personalInfo.identityNumber" /></p>
						<p class="flexItem"><s:property
							value="agent.personalInfo.identityNumber" /></p>
					</div>

						
						<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="personalInfo.dateOfBirth" /></p>
						<p class="flexItem"><s:property value="dateOfBirth" /></p>
					</div>
				
						<h2><s:text name="info.contact" /></h2>
				
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="contactInfo.address" /></p>
						<p class="flexItem">	<s:property value="agent.contInfo.address1" /></p>
					</div>
				
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="contactInfo.phoneNumber" /></p>
						<p class="flexItem"><s:property value="agent.contInfo.phoneNumber" /></p>
					</div>
			
					
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="agentcontactInfo.mobileNumber" /></p>
						<p class="flexItem"><s:property
							value="agent.contInfo.mobileNumbere" /></p>
					</div>
					
					
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="agentcontactInfo.email" /></p>
						<p class="flexItem"><s:property value="agent.contInfo.email" /></p>
					</div>
					
					
				
					<h2><s:text name="info.credential" /></h2>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="agent.status.name" /></p>
						<p class="flexItem"><s:text name='%{"agent"+agent.status}' /></p>
					</div>
						
					
				</div>
			</div>
		</div>
	</div>
</div>
	
	
	
	<div class="yui-skin-sam">
		
		<span id="cancel" class=""><span class="first-child"><button
					type="button" class="back-btn btn btn-sts" onclick="onCancel();">
					<b><FONT color="#FFFFFF"><s:text name="back.button" /> </font></b>
				</button></span></span>
</div> 
</s:form>





<script type="text/javascript">
$(document).ready(function () {
    $('#update').on('click', function (e) {
        document.updateform.id.value = document.getElementById('id').value;
        document.updateform.currentPage.value = document.form.currentPage.value;
        document.updateform.submit();
    });

    $('#delete').on('click', function (e) {
        if (confirm('<s:text name="confirm.delete"/> ')) {
            document.deleteform.id.value = document.getElementById('id').value;
            document.deleteform.currentPage.value = document.form.currentPage.value;
            document.deleteform.submit();
        }
    });
    
    var p2 = '<s:property value="agent.personalInfo.sex" />';
	if (p2 == "")
		$("#SexPesonalInfo").text("");
	else if (p2 == 1)
		$("#SexPesonalInfo").text("Male");
	else if (p2 == 0)
		$("#SexPesonalInfo").text("Female");
    
});
function onCancel(){
	var redir='<s:property value="redirectContent" />';
	window.location.href='<s:property value="redirectContent" />';
}
</script>