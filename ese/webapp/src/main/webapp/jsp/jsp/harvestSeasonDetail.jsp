<%@ include file="/jsp/common/detail-assets.jsp"%>

<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>

<s:form name="form" cssClass="fillform">
	<s:hidden key="currentPage" />
	<s:hidden key="id" />
	<s:hidden key="harvestSeason.id" />
	<s:hidden key="command" />
	<div class="flex-view-layout">
	<s:hidden key="harvestSeason.id" id="harvestSeasonId" />
	<div class="fullwidth">
		<div class="flexWrapper">
			<div class="flexLeft appContentWrapper">
				<div class="formContainerWrapper dynamic-form-con">
					<h2><s:property value="%{getLocaleProperty('info.harvestSeason')}" /></h2>
					 <s:if test='branchId==null'>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="app.branch" /></p>
						<p class="flexItem"><s:property value="%{getBranchName(harvestSeason.branchId)}" /></p>
					</div>
				</s:if>	
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="harvestSeason.code" /></p>
						<p class="flexItem"><s:property value="harvestSeason.code" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="%{getLocaleProperty('harvestSeason.name')}" /> </p>
						<p class="flexItem"><s:property value="harvestSeason.name" /></p>
					</div>
					
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="harvestSeason.fromPeriod"/></p>
						<p class="flexItem"><s:property value="fromPeriod" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="harvestSeason.toPeriod" /></p>
						<p class="flexItem"><s:property value="toPeriod" /></p>
					</div>
					
						<%--<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="harvestSeason.currentSeason" /></p>
						<p class="flexItem"><s:text name="status%{harvestSeason.currentSeason}" /></p>
					</div>--%>
				</div>
					<div class="yui-skin-sam">
	<sec:authorize
		ifAllGranted="profile.season.update">
		<span id="update" class=""><span class="first-child">
		<button type="button" class="edit-btn btn btn-success"><FONT color="#FFFFFF">
		<b><s:text name="edit.button" /></b> </font></button>
		</span></span>
	</sec:authorize> 
	<%-- <sec:authorize ifAllGranted="profile.season.delete">
		<span id="delete" class=""><span class="first-child">
		<button type="button" class="delete-btn btn btn-warning"><FONT
			color="#FFFFFF"> <b><s:text name="delete.button" /></b> </font></button>
		</span></span>
	</sec:authorize>  --%>
	<span id="cancel" class=""><span class="first-child">
	<button type="button" class="back-btn btn btn-sts"><b><FONT
		color="#FFFFFF"><s:text name="back.button" /> </font></b></button>
	</span></span></div>
				</div>

</div>
</div>
</div>	
</s:form>

<s:form name="updateform" action="harvestSeason_update.action">
	<s:hidden key="id" />
	<s:hidden key="currentPage" />
</s:form>
<s:form name="deleteform" action="harvestSeason_delete.action">
	<s:hidden key="id" />
	<s:hidden key="currentPage" />
</s:form>
<s:form name="cancelform" action="harvestSeason_list.action">
	<s:hidden key="currentPage" />
	<s:hidden name="postdata" id="postdata" />
</s:form>


<script type="text/javascript">
    $(document).ready(function () {
        $('#update').on('click', function (e) {
            document.updateform.id.value = document.getElementById('harvestSeasonId').value;
            document.updateform.currentPage.value = document.form.currentPage.value;
            document.updateform.submit();
        });

        $('#delete').on('click', function (e) {
            if (confirm('<s:text name="confirm.delete"/> ')) {
                document.deleteform.id.value = document.getElementById('harvestSeasonId').value;
                document.deleteform.currentPage.value = document.form.currentPage.value;
                document.deleteform.submit();
            }
        });
    });

</script>
