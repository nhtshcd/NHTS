<%@ include file="/jsp/common/detail-assets.jsp"%>



<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<script type="text/javascript"> 


function onUpdate(){
	document.updateForm.submit();
}

function onDelete(){

	var val = confirm('<s:text name="confirm.delete"/>');
	if(val)
		document.deleteForm.submit();
}

function onCancel(){	
	document.listForm.submit();
}


</script>
<div id="tabs-1">
<font color="red"> <s:actionerror /></font>	
	<s:hidden key="command" />
	<s:form name="form" cssClass="fillform">
	<s:hidden key="currentPage"/>
	<s:hidden key="id" />
	<s:hidden key="branchMaster.id"/>
	<s:hidden key="command" />
	<div class="flex-view-layout">
	<div class="fullwidth">
		<div class="flexWrapper">
			<div class="flexLeft appContentWrapper">
				<div class="formContainerWrapper dynamic-form-con">
					<h2><s:text name="info.branch" /></h2>
				
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="branchMaster.branchId.prop" /></p>
						<p class="flexItem"><s:property value="branchMaster.branchId" /></p>
					</div>
				
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="branchMaster.name.prop" /></p>
						<p class="flexItem"><s:property value="branchMaster.name" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="branchMaster.contactPerson.prop" /></p>
						<p class="flexItem"><s:property value="branchMaster.contactPerson" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="branchMaster.phoneNo.prop" /></p>
						<p class="flexItem"><s:property value="branchMaster.phoneNo" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="branchMaster.address.prop" /></p>
						<p class="flexItem"><s:property value="branchMaster.address" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="status" /></p>
						<p class="flexItem"><s:property value="status" /></p>
					</div>
					
				</div>
				<div class="yui-skin-sam">
				    <sec:authorize ifAllGranted="profile.vendor.update">
				        <span id="update" class=""><span class="first-child">
			                <button type="button" class="edit-btn btn btn-success" onclick="onUpdate();">
			                    <FONT color="#FFFFFF">
			                    <b><s:text name="edit.button" /></b>
			                    </font>
			                </button>
			            </span></span>
				    </sec:authorize>
				    <%-- <sec:authorize ifAllGranted="profile.vendor.delete">
				             <span id="delete" class=""><span class="first-child">
				                <button type="button" class="delete-btn btn btn-warning">
				                    <FONT color="#FFFFFF">
				                    <b><s:text name="delete.button" /></b>
				                    </font>
				                </button>
				            </span></span></sec:authorize> --%>
				   <span id="cancel" class=""><span class="first-child"><button type="button" class="back-btn btn btn-sts" onclick="onCancel();">
				               <b><FONT color="#FFFFFF"><s:text name="back.button"/> 
				                </font></b></button></span></span>
				</div>
		</div>
	</div>
</div>
</div>
				
</s:form>
	
	<div style="clear: both;"></div>
	

	<s:form name="updateForm" id="updateForm" action="branchMaster_update">		
		<s:hidden name="id" value="%{branchMaster.id}" />  	  
		<s:hidden key="currentPage" />
	</s:form>
	<%-- <s:form name="deleteForm"  id="deleteForm" action="branchMaster_delete">
		<s:hidden key="id" value="%{branchMaster.id}"/>
		<s:hidden key="currentPage" />
	</s:form> --%>
	
	 <s:form name="listForm" id="listForm" action="branchMaster_list">
			<s:hidden name="currentPage" />
		</s:form>
	<s:hidden id="id" name="branchMaster.id" />
	</div>


