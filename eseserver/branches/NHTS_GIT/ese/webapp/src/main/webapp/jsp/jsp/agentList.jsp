<%@ include file="/jsp/common/grid-assets.jsp"%>

<head>
<META name="decorator" content="swithlayout">
</head>
<body>
	<script type="text/javascript">
var type="<%=request.getParameter("type")%>";
$(document).ready(function(){		
	
    var table = createDataTable('detail',"<%=request.getParameter("type")%>_data.action?type=<%=request.getParameter("type")%>");
});	      
</script>
	
	
	
	<div class="appContentWrapper marginBottom">
	
	<div class="dataTable-btn-cntrls" id="detailBtn">
	<sec:authorize ifAllGranted="profile.agent.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="pill" href="#" onclick="document.createform.submit()"> <i
								class="fa fa-plus"></i>Add
						</a></li>
					</ul>
						</sec:authorize>
					
				</div>
				
				
		<div id="baseDiv" style="width:100%">
			<table id="detail" class="table">
				<thead>
					<tr id="headerrow">

						<s:if test='branchId==null'>
							<th id="branch" class="hd"><s:text name="app.branch" /></th>
						</s:if>

						<s:if
							test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<th id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
						</s:if>


						<th id="profileId" class="hd"><s:text name='%{"agentName"+type}' /></th>
						<th id="firstName" class="hd"><s:text name="personalInfo.firstName" /></th>
						<th id="lastName" class="hd"><s:text name="personalInfo.lastName" /></th>
						<th id="mobileNumber" class="hd"><s:text name="contactInfo.mobileNumber" /></th>
						<th id="bodStatus" class="hd"><s:text name="login.status" /></th>
						<th id="status" class="hd"><s:text
								name="personalInfo.bodStatus" /></th>
						
					</tr>
				</thead>

			</table>
		</div>
	</div>
	
		
	<s:form name="createform" action="%{type}_create?type=%{type}">
	</s:form>
	<s:form name="updateform" action="%{type}_detail?type=%{type}">
		<s:hidden name="id" />
		<s:hidden name="currentPage" />
		<s:hidden name="postdata" id="postdata" />
		<s:hidden name="type" class="type" />
		
	</s:form>
</body>
