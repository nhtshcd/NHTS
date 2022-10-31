<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
	<META name="decorator" content="swithlayout">
</head>
<body>
<script type="text/javascript">
	$(document).ready(function(){
		
		var table = createDataTable('detail',"samithi_data.action");
		//$(".breadcrumb ol").children('li').eq(1).empty();
		//alert('<s:property value="getBranchId()"/>');
		
	<%--	jQuery("#detail").jqGrid(
		{
		   	url:'samithi_data.action',
		   	pager: '#pagerForDetail',	  
		   	datatype: "json",	
		   	mtype: 'POST',
		    styleUI : 'Bootstrap',
		   	colNames:[
			          <s:if test='branchId==null'>
						'<s:text name="app.branch"/>',
						</s:if>
						<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
						'<s:text name="app.subBranch"/>',
						</s:if>
		  		   	  '<s:text name="samithi.code"/>',
		  		    '<s:property value="%{getLocaleProperty('samithi.name')}" />',
		  		  '<s:text name="farmercnt"/>'
		        ],
		   	colModel:[
									 	 <s:if test='branchId==null'>
			   			{name:'branchId',index:'branchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: {
			   			value: '<s:property value="parentBranchFilterText"/>',
			   			dataEvents: [ 
			   			          {
			   			            type: "change",
			   			            fn: function () {
			   			            	console.log($(this).val());
			   			             	getSubBranchValues($(this).val())
			   			            }
			   			        }
			   			    ]
			   			
			   			}},	   				   		
			   		</s:if>
			   		<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
			   			{name:'subBranchId',index:'subBranchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: { value: '<s:property value="childBranchFilterText"/>' }},	   				   		
			   		</s:if>
		   		{name:'code',index:'code',width:125,sortable:true},
		   		{name:'name',index:'name',width:125,sortable:true},
		   		{name:'farmercnt',index:'farmercnt',width:125,sortable:false,search:false}
		   		
		   			 ],
		   	height: 301, 
		    width: $("#baseDiv").width(), // assign parent div width
		    scrollOffset: 0,
		   	rowNum:10,
		   	rowList : [10,25,50],
		    sortname:'id',			  
		    sortorder: "desc",
		    viewrecords: true, // for viewing noofrecords displaying string at the right side of the table
		    onSelectRow: function(id){ 
			  document.updateform.id.value  =id;
		      document.updateform.submit();      
			},		
	        onSortCol: function (index, idxcol, sortorder) {
		        if (this.p.lastsort >= 0 && this.p.lastsort !== idxcol
	                    && this.p.colModel[this.p.lastsort].sortable !== false) {
	                $(this.grid.headers[this.p.lastsort].el).find(">div.ui-jqgrid-sortable>span.s-ico").show();
	            }
	        }
		});
		
		jQuery("#detail").jqGrid('navGrid','#pagerForDetail',{edit:false,add:false,del:false,search:false,refresh:true}) // enabled refresh for reloading grid
		jQuery("#detail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false}); // enabling filters on top of the header.

		colModel = jQuery("#detail").jqGrid('getGridParam', 'colModel');
	    $('#gbox_' + $.jgrid.jqID(jQuery("#detail")[0].id) +
	        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
	        var cmi = colModel[i], colName = cmi.name;

	        if (cmi.sortable !== false) {
	            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
	        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
	            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
	        }
	    }); --%>
	}); 
	
	function exportXLS(){
		 if(isDataAvailable("#detail")){
			jQuery("#detail").setGridParam ({postData: {rows : 0}});
			jQuery("#detail").jqGrid('excelExport', {url: 'samithi_populateXLS.action'});
		}else{
		     alert('<s:text name="export.nodata"/>');
		}
	}

	
</script>
<div class="appContentWrapper marginBottom">
	
	
	<!--  This div should always be there and its id should be Table's id+Btn FOr ex detailBtn -->



	<div  class="dataTable-btn-cntrls" id="detailBtn">
	     <sec:authorize access="hasAnyRole('profile.samithi.create','profile.samithi.bci.create')">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="pill" href="#" onclick="document.createform.submit()"> <i
								class="fa fa-plus"></i>Add
						</a></li>
					</ul>
						</sec:authorize>
					
				</div>
	

				
		<div id="baseDiv" style="width: 100%">
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


						<th id="code" class="hd"><s:text name="samithi.code" /></th>
						<th id="name" class="hd"><s:text name="samithi.name" /></th>
					<%-- 	<th id="farmercnt" class="hd"><s:text name="farmercnt" /></th>  --%>
						
						
					</tr>
				</thead>

			</table>
		</div>
	</div>



<s:form name="createform" action="samithi_create">
	<s:hidden name="type" class="type" />
</s:form>
<s:form name="updateform" action="samithi_detail">
    <s:hidden name="id" />
	<s:hidden name="currentPage" />
</s:form>
<s:form name="exportform">
	</s:form>


</body>