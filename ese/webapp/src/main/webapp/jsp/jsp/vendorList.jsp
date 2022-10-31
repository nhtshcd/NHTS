<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<META name="decorator" content="swithlayout">
</head>
<body>
<script type="text/javascript">
var editedId;
var cancelId;
$(document).ready(function(){	

<%--	jQuery("#detail").jqGrid(
			{
			   	url:'vendor_data.action',
			   	pager: '#pagerForDetail',
			   	datatype: "json",	
			    styleUI : 'Bootstrap',
			    postData:{
      			  "postdata" :  function(){	
      	   			return  decodeURI(postdata);
      			  } 
      	   	},
			   	colNames:[		
						 /*  <s:if test='branchId==null'>
						'<s:text name="app.branch"/>',
						</s:if>
						<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
						'<s:text name="app.subBranch"/>',
						</s:if> */
						
						
						'<s:text name="vendor.vendorId"/>',
						'<s:text name="vendor.vendorName"/>',
						'<s:text name="vendor.personName"/>',
						'<s:text name="vendor.email"/>',
						'<s:text name="vendor.address"/>',
						'<s:text name="vendor.mobileNumber"/>'
						
					],
			   	colModel:[		
			   	          
								 /* <s:if test='branchId==null'>
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
			   		</s:if>   		 */
			   		
			   		{name:'customerId',index:'vendorId',width:125,sortable:true,editable:true},
			   		{name:'customerName',index:'vendorName',width:125,sortable:true,editable:true},	
			   		{name:'personName',index:'personName',width:125,sortable:true,editable:true},	
			   		{name:'email',index:'emailId',width:125,sortable:true,editable:true},			
			   		{name:'vendorAddress',index:'vendorAddress',width:125,sortable:false,editable:true},
			   		{name:'mobileNumber',index:'mobileNo',width:125,align:'right',sortable:false,editable:true}		   		   
			   				   		   
			 	],
			   	height: 255, 
			    width: $("#baseDiv").width(), // assign parent div width
			    scrollOffset: 0,
			   	rowNum:10,
			   	rowList : [10,25,50],
			    sortname:'id',			  
			    sortorder: "desc",
			    viewrecords: true, // for viewing noofrecords displaying string at the right side of the table
			    onSelectRow: function(id){ 
					  document.updateform.id.value  =id;
					  postDataSubmit();
				      document.updateform.submit();      
					},		
				
				   onSortCol: function (index, idxcol, sortorder) {
			        if (this.p.lastsort >= 0 && this.p.lastsort !== idxcol
		                    && this.p.colModel[this.p.lastsort].sortable !== false) {
		                $(this.grid.headers[this.p.lastsort].el).find(">div.ui-jqgrid-sortable>span.s-ico").show();
		            }
		        }
				
				
		     
		    });
	

		/* original 	
		jQuery("#detail").jqGrid('navGrid','#pagerForDetail',{edit:true,add:false,del:true,search:false,refresh:true}) // enabled refresh for reloading grid 
			jQuery("#detail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false}); // enabling filters on top of the header.

			colModel = jQuery("#detail").jqGrid('getGridParam', 'colModel'); */
			
			
			
			 jQuery("#detail").jqGrid('navGrid','#pagerForDetail',{edit:false,add:false,del:false,search:false,refresh:true})
			jQuery("#detail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false});
			retainFields();
			colModel = jQuery("#detail").jqGrid('getGridParam', 'colModel');  
			
		    $('#gbox_' + $.jgrid.jqID(jQuery("#detail")[0].id) +
		        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
		        var cmi = colModel[i], colName = cmi.name;

		        if (cmi.sortable !== false) {
		            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
		        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
		            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
		        }
		    });
		    postdata =  ''; --%>
		    
		    var table =  	createDataTable('detail',"vendor_data.action");
		});	           
	
</script>


<div class="appContentWrapper marginBottom">
<div  class="dataTable-btn-cntrls" id="detailBtn">
	     <sec:authorize ifAllGranted="profile.vendor.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="pill" href="#" onclick="document.createform.submit()"> <i
								class="fa fa-plus"></i>Add
						</a></li>
					</ul>
						</sec:authorize>
					
				</div>
 
					
				</div>

<div id="baseDiv" style="width:100%">
			<table id="detail" class="table">
				<thead>
					<tr id="headerrow">

                        <th id="vendorId" class="hd"><s:text name="vendor.vendorId" /></th>
						<th id="vendorName" class="hd"><s:text name="vendor.vendorName" /></th>
						<th id="personName" class="hd"><s:text name="vendor.personName" /></th>
						<th id="emailId" class="hd"><s:text name="vendor.email" /></th>
						<th id="vendorAddress" class="hd"><s:text name="vendor.address" /></th>
						<th id="mobileNo" class="hd"><s:text name="vendor.mobileNumber" /></th>
					</tr>
				</thead>

			</table>
		</div>

</div>

<s:form name="createform" action="vendor_create">
<s:hidden name="type" class="type" />
</s:form>
<s:form name="updateform" action="vendor_detail">
	<s:hidden name="id" />
	<s:hidden name="currentPage" />
	<s:hidden name="type" class="type" />
	<s:hidden name="postdata" id="postdata" />
</s:form>
</body>
