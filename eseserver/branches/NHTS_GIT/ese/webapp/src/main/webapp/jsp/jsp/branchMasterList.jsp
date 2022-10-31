<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<META name="decorator" content="swithlayout">
</head>
<body>
<script type="text/javascript">

$(document).ready(function(){	

var table =createDataTable('detail',"branchMaster_data.action");
		
});	 
		
	
</script>


<div class="appContentWrapper marginBottom">
<!--  This div should always be there and its id should be Table's id+Btn FOr ex detailBtn -->
	<div  class="dataTable-btn-cntrls" id="detailBtn">
	     <sec:authorize ifAllGranted="profile.branchMaster.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="pill" href="#" onclick="document.createform.submit()"> <i
								class="fa fa-plus"></i>Add
						</a></li>
					</ul>
						</sec:authorize>
					
				</div>
 
 
				
		<div id="baseDiv" style="width: 99%">
		<!-- If you change tables id chage the id of above div too -->
			<table id="detail">
				<thead>
					<tr id="headerrow">

						<s:if test='branchId==null'>
							<th id="branch" class="hd"><s:text name="app.branch" /></th>
						</s:if>

						<s:if
							test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<th id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
						</s:if>


						<th id="name" class="hd"><s:text name="branchMaster.name.prop" /></th>
						<th id="contactPerson" class="hd"><s:text name="branchMaster.contactPerson.prop" /></th>
						<th id="phoneNo" class="hd"><s:text name="branchMaster.phoneNo.prop" /></th>
						<th id="address" class="hd"><s:text name="branchMaster.address.prop" /></th>
						<th id="status" class="hd"><s:text name="status" /></th>
						
					</tr>
				</thead>

			</table>
		</div>
</div>

<s:form name="createform" action="branchMaster_create">
		<s:hidden name="type" class="type" />
	</s:form>


<s:form name="updateform" action="branchMaster_detail">
<s:hidden name="id" />
		<s:hidden name="postdata" id="postdata" />
		<s:hidden name="type" class="type" />
		<s:hidden name="currentPage" />
</s:form>
</body>
