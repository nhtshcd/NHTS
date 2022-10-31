<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<s:hidden key="vendor.id" id="vendorId"/>
<s:hidden key="vendor.vendorName"/>
<font color="red">
    <s:actionerror/>
</font>
<s:form name="form" cssClass="fillform">
	<s:hidden key="currentPage"/>
	<s:hidden key="id" />
	<s:hidden key="vendor.id"/>
	<s:hidden key="command" />
	<div class="flex-view-layout">
	<div class="fullwidth">
		<div class="flexWrapper">
			<div class="flexLeft appContentWrapper">
				<div class="formContainerWrapper dynamic-form-con">
					<h2><s:property value="%{getLocaleProperty('info.vendor')}" /></h2>
					 <s:if test='branchId==null'>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="app.branch" /></p>
						<p class="flexItem"><s:property value="%{getBranchName(vendor.branchId)}" /></p>
					</div>
				</s:if>	
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="vendor.vendorId" /></p>
						<p class="flexItem"><s:property value="vendor.vendorId" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="vendor.vendorName" /></p>
						<p class="flexItem"><s:property value="vendor.vendorName" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="vendor.personName" /></p>
						<p class="flexItem"><s:property value="vendor.personName" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="vendor.email" /></p>
						<p class="flexItem"><s:property value="vendor.emailId" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="vendor.vendorAddress" /></p>
						<p class="flexItem"><s:property value="vendor.vendorAddress" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="vendor.mobileNumber" /></p>
						<p class="flexItem"><s:property value="vendor.mobileNo" /></p>
					</div>
					<%-- <div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="vendor.landLine" /></p>
						<p class="flexItem"><s:property value="vendor.landLine" /></p>
					</div>--%>
				</div>
				<div class="yui-skin-sam">
				    <sec:authorize ifAllGranted="profile.vendor.update">
				        <span id="update" class=""><span class="first-child">
				                <button type="button" class="edit-btn btn btn-success">
				                    <FONT color="#FFFFFF">
				                    <b><s:text name="edit.button" /></b>
				                    </font>
				                </button>
				            </span></span>
				    </sec:authorize><sec:authorize ifAllGranted="profile.vendor.delete">
				             <span id="delete" class=""><span class="first-child">
				                <button type="button" class="delete-btn btn btn-warning">
				                    <FONT color="#FFFFFF">
				                    <b><s:text name="delete.button" /></b>
				                    </font>
				                </button>
				            </span></span></sec:authorize>
				   <span id="cancel" class=""><span class="first-child"><button type="button" class="back-btn btn btn-sts" >
				               <b><FONT color="#FFFFFF"><s:text name="back.button"/> 
				                </font></b></button></span></span>
				</div>
		</div>
	</div>
</div>
</div>
				
</s:form>

<s:form name="updateform" action="vendor_update.action">
    <s:hidden key="id"/>
    <s:hidden key="currentPage"/>
</s:form>
<s:form name="deleteform" action="vendor_delete.action">
    <s:hidden key="id"/>
   <s:hidden key="currentPage"/>
</s:form>

<s:form name="cancelform" action="vendor_list.action">
    <s:hidden key="currentPage"/>
    <s:hidden name="postdata" id="postdata" />
</s:form>

<script type="text/javascript">
    $(document).ready(function () {
        $('#update').on('click', function (e) {
            document.updateform.id.value = document.getElementById('vendorId').value;
            document.updateform.submit();
        });

        $('#delete').on('click', function (e) {
            if (confirm('<s:text name="confirm.delete"/> ')) {
                document.deleteform.id.value = document.getElementById('vendorId').value;
                document.deleteform.submit();
            }
        });
    });

</script>