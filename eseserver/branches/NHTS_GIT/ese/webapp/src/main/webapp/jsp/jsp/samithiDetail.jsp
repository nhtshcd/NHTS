<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<script src="js/dynamicComponents.js?v=1.19"></script>
<script>
    	   function getBranchIdDyn(){
    	return '<s:property value="warehouse.branchId"/>';
   	}
</script>

<div class="appContentWrapper marginBottom ">
		<ul class="nav nav-pills">
			<li class="active"><a data-toggle="pill" href="#tabs-1"><s:property value="%{getLocaleProperty('title.samithi')}" /></a></li>
		</ul>
</div>

<div class="tab-content">
		<div id="tabs-1" class="tab-pane fade in active">
			<div class="flexbox-container">
			<s:hidden key="warehouse.id" id="coOperativeId" class="uId"/>
<font color="red"> <s:actionerror /></font>
<s:form name="form" cssClass="fillform">
	<s:hidden key="currentPage" />
	<s:hidden key="id" />
	<s:hidden key="warehouse.id" />
	<s:hidden key="command" />


	<div class="flex-view-layout">
		<div class="fullwidth">
			<div class="flexWrapper">
				<div class="flexLeft appContentWrapper">
                     <div class="formContainerWrapper">
                         <div class="aPanel">
							<div class="aTitle">
                       <h2>
				<s:property value="%{getLocaleProperty('info.samithi')}" />
					</h2>
						</div>
							<div class="aContent dynamic-form-con"
								id="samithiDetailsAccordian">

                             <s:if test='branchId==null'>
									<div class="dynamic-flexItem2">
										<p class="flexItem">
											<s:text name="app.branch" />
										</p>
										<p class="flexItem">
											<s:property value="%{getBranchName(warehouse.branchId)}" />
										</p>
									</div>
								</s:if>

							<div class="dynamic-flexItem2">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('samithi.name')}" />
									</p>
									<p class="flexItem">
										<s:property value="warehouse.name" />
									</p>
								</div>

								 <div class="dynamic-flexItem2">
										<p class="flexItem">
											<s:property value="%{getLocaleProperty('dateOfFormation')}" />
										</p>
										<p class="flexItem">
											<s:date name="warehouse.formationDate" format="dd/MM/YYYY" />
										</p>
									</div>
					     	</div>
                     </div>
</div>

<div class="dynamicFieldsRender"></div>
					<div class="yui-skin-sam">
					<sec:authorize  access="hasAnyRole('profile.samithi.update','profile.samithi.bci.update')">
							<span id="update" class=""><span class="first-child">
									<button type="button" class="edit-btn btn btn-success">
										<FONT color="#FFFFFF"> <b><s:text
													name="edit.button" /></b>
										</font>
									</button>
							</span></span>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('profile.samithi.delete','profile.samithi.bci.delete')">
							<span id="delete" class=""><span class="first-child">
									<button type="button" class="delete-btn btn btn-warning">
										<FONT color="#FFFFFF"> <b><s:text
													name="delete.button" /></b>
										</font>
									</button>
							</span></span>
						</sec:authorize>
						<span id="cancel" class=""><span class="first-child"><button
									type="button" class="back-btn btn btn-sts">
									<b><FONT color="#FFFFFF"><s:text name="back.button" />
									</font></b>
								</button></span></span>
					</div>
				</div>
			</div>
		</div>
	</div>

	
</s:form>

<s:form name="updateform" action="samithi_update.action">
	<s:hidden key="id" />
	<s:hidden key="currentPage" />
</s:form>
<s:form name="deleteform" action="samithi_delete.action">
	<s:hidden key="id" />
	<s:hidden key="currentPage" />
</s:form>
<s:form name="cancelform" action="samithi_list.action">
	<s:hidden key="currentPage" />
</s:form>
			</div>
		</div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
    	
     $('#update').on('click', function (e) {
            document.updateform.id.value = document.getElementById('coOperativeId').value;
            document.updateform.currentPage.value = document.form.currentPage.value;
            document.updateform.submit();
        });
     

        $('#delete').on('click', function (e) {
            if (confirm('<s:text name="confirm.delete"/> ')) {
                document.deleteform.id.value = document.getElementById('coOperativeId').value;
                document.deleteform.currentPage.value = document.form.currentPage.value;
                document.deleteform.submit();
            }
        });
       
    });
 </script>