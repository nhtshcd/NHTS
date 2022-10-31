<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<META name="decorator" content="swithlayout">
</head>
<script type="text/javascript">
	$(document).ready(function(){
		var type='<s:property value="type"/>';
		
		$(".type").val("");
		$(".type").val(type);
		var detailAction = "cooperative_data.action?type=<%=request.getParameter("type")%>";
		var table = createDataTable('detail',"cooperative_data.action");
		
	 
	    
	}); 
</script>
<s:if test="type=='cooperative'">
	<!-- If you change tables id change the id of above div too -->
<div  class="dataTable-btn-cntrls" id="detailBtn">
	     <sec:authorize ifAllGranted="profile.cooperative.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="pill" href="#" onclick="document.createform.submit()"> <i
								class="fa fa-plus"></i>Add
						</a></li>
					</ul>
						</sec:authorize>
				</div>
</s:if>
<s:else>
<div  class="dataTable-btn-cntrls" id="detailBtn">
<!-- If you change tables id change the id of above div too -->
	     <sec:authorize ifAllGranted="profile.fieldCollectionCentre.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="pill" href="#" onclick="document.createform.submit()"> <i
								class="fa fa-plus"></i>Add
						</a></li>
					</ul>
						</sec:authorize>
				</div>
</s:else> 

<div class="appContentWrapper border-radius datatable-wrapper-class">
	
          <div id="baseDiv" style="width:100%">
			<table id="detail" class="table ">
				<thead class="thead-dark">
					<tr id="headerrow">

						<th id="code" class="hd"><s:text name="coOperative.code" /></th>
						<th id="name" class="hd"><s:text name="coOperative.name" /></th>
						<th id="location" class="hd"><s:text name="coOperative.location" /></th>
						<th id="warehouseIncharge" class="hd"><s:text name="coOperative.warehouseIncharge" /></th>
						<th id="capacityTonnes" class="hd"><s:text name="coOperative.capacityTonnes" /></th>
					</tr>
				</thead>

			</table>
		</div>
	</div>

<s:form name="createform" action="cooperative_create">
	<s:hidden name="type" class="type" />
</s:form>
<s:form name="updateform" action="cooperative_detail">
	<s:hidden key="id" />
	<s:hidden key="currentPage" />
	<s:hidden name="type" class="type" />
	<s:hidden name="postdata" id="postdata" />
</s:form>