<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<style type="text/css">
.none {
	display: none;
}
</style>
<Script>
function onCancel(){
	var redirectContent="<s:property value='redirectContent' />";
	if(redirectContent=="" ||redirectContent==null){
		var isAdmin ='<%=session.getAttribute("isAdmin")%>';
		if(isAdmin == "true" )
			redirectContent="dynamicViewReportDT_list.action?id=61";
		else
			redirectContent="dashboard_list.action";
		}
	window.location.href=redirectContent;
			
		}
</Script>
<font color="red"> <s:actionerror />
</font>
<div class="error">
	<s:fielderror />
</div>
<s:hidden key="user.id" id="userId" />
<s:form name="form" cssClass="fillform" enctype="multipart/form-data">
	<s:hidden key="currentPage" />
	<s:hidden key="id" />
	<s:hidden key="user.id" />
	<s:hidden key="command" />
	<s:hidden name="redirectContent" id="redirectContent" />
	<div class="flex-view-layout">
		<div class="fullwidth">
			<div class="flexWrapper">
				<div class="flexLeft appContentWrapper">
					<div class="formContainerWrapper dynamic-form-con">
						<h2>
							<s:text name="info.user" />
						</h2>
						<s:if test='branchId==null'>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="app.branch" />
								</p>
								<p class="flexItem">
									<s:property value="%{getBranchName(vendor.branchId)}" />
								</p>
							</div>
						</s:if>
						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="userProfile.username" />
							</p>
							<p class="flexItem">
								<s:property value="user.username" />
							</p>
						</div>

						<!-- </div>

					<div class="formContainerWrapper dynamic-form-con"> -->
						<h2>
							<s:text name="info.personal" />
						</h2>
						<div class="dynamic-flexItem">
							<s:if test='userImageString!=null && userImageString!=""'>
								<img width="50" height="50" border="0"
									src="data:image/png;base64,<s:property value="userImageString"/>">
							</s:if>
							<s:else>
								<img align="middle" width="150" height="100" border="0"
									src="img/no-image.png">
							</s:else>
						</div>
						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="personalInfo.firstName" />
							</p>
							<p class="flexItem">
								<s:property value="user.persInfo.firstName" />
							</p>
						</div>


						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="personalInfo.lastName" />
							</p>
							<p class="flexItem">
								<s:property value="user.persInfo.lastName" />
							</p>
						</div>



						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="user.lang" />
							</p>
							<p class="flexItem">
								<s:text name='%{user.language}' />
							</p>
						</div>
						<!-- </div>
					<div class="formContainerWrapper dynamic-form-con"> -->
						<h2>
							<s:text name="info.contact" />
						</h2>

						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="contactInfo.address" />
							</p>
							<p class="flexItem">
								<s:property value="user.contInfo.address1" />
							</p>
						</div>

						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="contactInfo.phoneNumber" />
							</p>
							<p class="flexItem">
								<s:property value="user.contInfo.phoneNumber" />
							</p>
						</div>

						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="contactInfo.mobileNumber" />
							</p>
							<p class="flexItem">
								<s:property value="user.contInfo.mobileNumbere" />
							</p>
						</div>

						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="contactInfo.email" />
							</p>
							<p class="flexItem">
								<s:property value="user.contInfo.email" />
							</p>
						</div>


						<!-- </div>
					<div class="formContainerWrapper dynamic-form-con"> -->
						<h2>
							<s:text name="info.userCred" />
						</h2>
						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="user.role" />
							</p>
							<p class="flexItem">
								<s:property value="roleName" />
							</p>
						</div>
						<s:if test="user.agroChDealer!=null">
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text name="agent.exporter" />
								</p>
								<p class="flexItem">
									<s:property value="exporter" />
								</p>
							</div>
						</s:if>
						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="user.status" />
							</p>
							<p class="flexItem">
								<s:property value="status" />
							</p>
						</div>

						<div class="dynamic-flexItem">
							<p class="flexItem">
								<s:text name="%{getLocaleProperty('Signature')}" />
							</p>
							<s:if test="user.signature!=null">
								<p class="flexItem">
									<button type="button" class="fa fa-download"
										style="background-color: transparent"
										onclick="popDownload(<s:property value="user.signature"/>)"></button>
								</p>
							</s:if>
						</div>
					</div>
					<div class="formContainerWrapper">
						<s:if test='#session.isAdmin =="true"'>
							<s:iterator value="ex" var="innerList">
								<div class="aPanel audit_history">
									<div class="aTitle">
										<h2>
											<s:if
												test="#innerList[2].toString().trim().equalsIgnoreCase('ADD')">
												<s:property value="#innerList[0].createdUser" />
											</s:if>
											<s:else>
												<s:property value="#innerList[0].updatedUser" />
											</s:else>
											-
											<s:date name="#innerList[1].revisionDate"
												format="dd/MM/yyyy hh:mm:ss" />
											-
											<s:property
												value="%{getLocaleProperty('default'+#innerList[2])}" />
											<div class="pull-right">
												<a class="aCollapse "
													href="#<s:property value="#innerList[1].id" />"><i
													class="fa fa-chevron-right"></i></a>
											</div>
										</h2>
									</div>
									<div class="aContent dynamic-form-con"
										id="<s:property value="#innerList[1].id" />">
										<jsp:include page='/jsp/jsp/auditUserProfileDetail.jsp' />
									</div>
								</div>
							</s:iterator>
						</s:if>
					</div>
					<div class="yui-skin-sam">

						<div class="yui-skin-sam ">
							<span id="cancel" class=""><span class="first-child"><button
										type="button" class="back-btn btn btn-sts"
										onclick="onCancel()">
										<b><FONT color="#FFFFFF"><s:text name="back.button" />
										</font></b>
									</button></span></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<br />


</s:form>
<s:form id="fileDownload" action="user_populateDownload">
	<s:hidden id="loadId" name="idd" />
</s:form>


<script type="text/javascript">  
$(document).ready(function () {
	$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
});
function popDownload(id) {
	document.getElementById("loadId").value = id;
	$('#fileDownload').submit();

}
</script>