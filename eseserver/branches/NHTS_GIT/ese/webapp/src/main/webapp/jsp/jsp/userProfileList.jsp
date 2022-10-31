<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<META name="decorator" content="swithlayout">
</head>
<body>
	<script type="text/javascript">
$(document).ready(function(){	
	<%--jQuery("#detail").jqGrid(
	{
	   	url:'user_data.action',
	   	pager: '#pagerForDetail',	  
	   	datatype: "json",	
	   	mtype: 'POST',
	   	styleUI : 'Bootstrap',
	   	colNames:[	
				  <s:if test='branchId==null'>
					'<s:text name="app.branch"/>',
				   </s:if>
					<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
					'<s:text name="app.branch"/>',
					</s:if>
	  		   	  '<s:text name="userProfile.username"/>',
	  		      '<s:text name="personalInfo.firstName"/>',
	  		      '<s:text name="personalInfo.lastName"/>',
	  		      '<s:text name="user.contactInfo.mobile"/>',
	  		      '<s:text name="user.role"/>',
	  		      '<s:text name="user.status"/>'
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
	   		{name:'username',index:'username',width:125,sortable:true},
	   		{name:'pI.firstName',index:'pI.firstName',width:125,sortable:true},
	   		{name:'pI.lastName',index:'pI.lastName',width:125,sortable:true},
	   		{name:'cI.mobileNumber',index:'cI.mobileNumber',width:125,sortable:true},
	   		{name:'r.name',index:'r.name',width:125,sortable:true},
	   		{name:'enabled',index:'enabled',width:125,sortable: false, width :125, search:true, stype: 'select', searchoptions: { value: ':<s:text name="filter.allStatus"/>;1:<s:property value="%{getLocaleProperty('status1')}"/>;0:<s:property value="%{getLocaleProperty('status0')}"/>' }}
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
	
	jQuery("#detail").jqGrid("setLabel","branchId","",{"text-align":"center"});
	jQuery("#detail").jqGrid("setLabel","username","",{"text-align":"center"});
	jQuery("#detail").jqGrid("setLabel","pI.firstName","",{"text-align":"center"});
	jQuery("#detail").jqGrid("setLabel","pI.lastName","",{"text-align":"center"});
	jQuery("#detail").jqGrid("setLabel","cI.mobileNumber","",{"text-align":"center"});
	jQuery("#detail").jqGrid("setLabel","r.name","",{"text-align":"center"});
	jQuery("#detail").jqGrid("setLabel","enabled","",{"text-align":"center"});
	
	jQuery("#detail").jqGrid('navGrid','#pagerForDetail',{edit:false,add:false,del:false,search:false,refresh:true}) // enabled refresh for reloading grid
	jQuery("#detail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false}); // enabling filters on top of the header.
	
	$('#detail').jqGrid('setGridHeight',(windowHeight));
	
	colModel = jQuery("#detail").jqGrid('getGridParam', 'colModel');
    $('#gbox_' + $.jgrid.jqID(jQuery("#detail")[0].id) +
        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
        var cmi = colModel[i], colName = cmi.name;

        if (cmi.sortable !== false) {
            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
        }
    });--%>
    
    var table = createDataTable('detail',"user_data.action");
    
});
</script>

<div class="appContentWrapper marginBottom">
<!--  This div should always be there and its id should be Table's id+Btn FOr ex detailBtn -->



	<div  class="dataTable-btn-cntrls" id="detailBtn">
	     <sec:authorize ifAllGranted="profile.user.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="pill" href="#" onclick="document.createform.submit()"> <i
								class="fa fa-plus"></i>Add
						</a></li>
					</ul>
						</sec:authorize>
					
				</div>

				
		<div id="baseDiv" style="width:100%">
		<!-- If you change tables id change the id of above div too -->

		
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


						<th id="username" class="hd"><s:text name="userProfile.username" /></th>
						<th id="firstName" class="hd"><s:text name="personalInfo.firstName" /></th>
						<th id="lastName" class="hd"><s:text name="personalInfo.lastName" /></th>
						<th id="mobileNumber" class="hd"><s:text name="user.contactInfo.mobile" /></th>
						<th id="name" class="hd"><s:text name="user.role" /></th>
						<th id="enabled" class="hd"><s:text
								name="user.status" /></th>
						
					</tr>
				</thead>

			</table>
		</div>
	</div>

	<s:form name="createform" action="user_create">
	<s:hidden name="type" class="type" />
	</s:form>
	<s:form name="updateform" action="user_detail">
			<s:hidden name="id" />
		<s:hidden name="postdata" id="postdata" />
		<s:hidden name="type" class="type" />
		<s:hidden name="currentPage" />
	
	</s:form>
</body>