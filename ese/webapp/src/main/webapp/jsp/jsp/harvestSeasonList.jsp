<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<META name="decorator" content="swithlayout">
</head>

<script type="text/javascript">
$(document).ready(function(){

	var table =  	createDataTable('detail',"harvestSeason_data.action");
});

datePick = function(elem)
{	
   jQuery(elem).datepicker({
		   changeYear: true,
	       changeMonth: true,       
	       dateFormat: 'dd-mm-yy',
		   onSelect:function(dateText, inst){	   	 
		   		jQuery("#detail")[0].triggerToolbar();
		   }
	   }
)};


</script>
<!-- If you change tables id change the id of above div too -->
<div class="dataTable-btn-cntrls" id="detailBtn">
	<sec:authorize ifAllGranted="profile.season.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="pill" href="#" onclick="document.createform.submit()"> <i
								class="fa fa-plus"></i><s:text name="create.button"/>
						</a></li>
					</ul>
						</sec:authorize>
					
				</div>
				

<div class="appContentWrapper border-radius datatable-wrapper-class">
	
          <div id="baseDiv" style="width:100%">
			<table id="detail" class="table ">
				<thead class="thead-dark">
					<tr id="headerrow">
					
                        <s:if test='branchId==null'>
							<th width="20%" id="branch" class="hd"><s:text name="app.branch" /></th>
						</s:if>

						<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<th  width="20%" id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
						</s:if>
						<th   id="code" class="hd"><s:text name="harvestSeason.code" /></th>
						<th   id="name" class="hd"><s:text name="harvestSeason.name" /></th>
						<th   id="fromPeriod" class="hd"><s:text name="harvestSeason.fromPeriod" /></th>
						<th   id="toPeriod" class="hd"><s:text name="harvestSeason.toPeriod" /></th>
					</tr>
				</thead>

			</table>
		</div>
	</div>
<s:form name="createform" action="harvestSeason_create">
</s:form>

<s:form name="updateform" action="harvestSeason_detail">
	<s:hidden key="id" />
	<s:hidden key="currentPage"/>
	<s:hidden name="postdata" id="postdata" />
</s:form>

