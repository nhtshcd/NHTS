<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/ese-tags" prefix="e"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<META name="decorator" content="swithlayout">

</head>
<div id='warn' class="error">
	<s:actionerror />
</div>

<script type="text/javascript">
$(document).ready(function(){	

	<%--jQuery("#detail").jqGrid(
			{
			url:'role_data.action',
			mtype: 'POST',
			pager: '#pagerForDetail',
			datatype: "json",
			styleUI : 'Bootstrap',
			colNames:[
						<s:if test='branchId==null'>
						'<s:text name="app.branch"/>',
						</s:if>
						<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
						'<s:text name="app.subBranch"/>',
						</s:if>
		  		   	  '<s:text name="role.name"/>'
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
		      	      {name:'name',index:'name', sortable:true, width:125}
		      	 ],			
		    height: 301, 
			width: $("#baseDiv").width(),
			scrollOffset: 0,
			rowNum:10,
			rowList : [10,25,50],
			sortname: 'id',
			sortorder: 'desc',
			viewrecords: true,
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
	jQuery("#detail").jqGrid("setLabel","name","",{"text-align":"center"});

	jQuery("#detail").jqGrid('navGrid','#pagerForDetail',{edit:false,add:false,del:false,search:false,refresh:true})
	jQuery("#detail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false});			

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
    
    var table = createDataTable('detail',"role_data.action");
});

</script>

<div class="appContentWrapper marginBottom">
<div class="dataTable-btn-cntrls"  id="detailBtn">
<sec:authorize ifAllGranted="profile.role.create">
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


						<th id="name" class="hd"><s:text name="role.name" /></th>
						
						
					</tr>
				</thead>

			</table>
		</div>
</div>





<s:form name="createform" action="role_create">
<s:hidden name="type" class="type" />
</s:form>

<s:form name="updateform" action="role_detail">
	<s:hidden key="id" />
</s:form>
