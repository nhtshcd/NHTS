<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<div id="tabs">
<div id="baseDiv1" style="width:98%"></div>
<ul id="tabsUL" class="nav nav-tabs">
	<li><a data-toggle="tab" href="#tabs-1"><s:text name="info.procurementProducts" /></a></li>
	<li><a data-toggle="tab" href="#tabs-2"><s:text name="procurement.variety" /></a></li>
</ul>

  <div class="tab-content">
  	<div id="tabs-1" class="tab-pane fade in active">
  	<s:hidden key="procurementProduct.id" id="procurementProductId" /> 
  	<font color="red"> <s:actionerror /></font>
	<s:form name="form" cssClass="fillform">
	<s:hidden key="currentPage" />
	<s:hidden key="id" />
	<s:hidden key="procurementProduct.id" />
	<s:hidden key="command" />
	
		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<div class="flexiWrapper">
				<s:if test='branchId==null'>
					<div class="flexi flexi10">
							<label for="txt"><s:text name="app.branch" /></label>
							<div class="form-element">
								<s:property value="%{getBranchName(procurementProduct.branchId)}" />
							</div>
					</div>
				</s:if>	
					<div class="flexi flexi10">
						<label for="txt"><s:text name="product.code" /></label>
						<div class="form-element">
							<s:property value="procurementProduct.code" />
						</div>
					</div>
					<div class="flexi flexi10">
						<label for="txt"><s:text name="product.name" /></label>
						<div class="form-element">
							<s:property value="procurementProduct.name" />
						</div>
					</div>
					<div class="flexi flexi10">
						<label for="txt"><s:text name="product.code" /></label>
						<div class="form-element">
							<s:property value="procurementProduct.code" />
						</div>
					</div>
				</div>
				<div class="yui-skin-sam"><sec:authorize
		ifAllGranted="profile.procurementProduct.update">
		<span class=""><span class="first-child">
		<button id="update"  type="button" class="edit-btn btn btn-success"><FONT color="#FFFFFF">
		<b><s:text name="edit.button" /></b> </font></button>
		</span></span>
	</sec:authorize>
	<sec:authorize ifAllGranted="profile.procurementProduct.delete">
             <span id="delete" class=""><span class="first-child">
                <button type="button" class="delete-btn btn btn-warning" onclick="onDelete()"> 
                    <FONT color="#FFFFFF">
                    <b><s:text name="delete.button" /></b>
                    </font>
                </button>
      </span></span></sec:authorize>
     <span id="cancel" class=""><span class="first-child">
	<button type="button" class="back-btn btn btn-sts"><b><FONT
		color="#FFFFFF"><s:text name="back.button" /> </font></b></button>
	</span></span></div>
			</div>
		</div>
		
</s:form>
</div>
<div id="tabs-2" class="tab-pane fade">
		<s:hidden key="id" /> 
		<sec:authorize ifAllGranted="profile.procurementProduct.create">
			<input type="BUTTON" class="btn btn-sts" id="add" value="<s:text name="create.button"/>" onclick="document.createform.submit()" />
		</sec:authorize>
		<div style="float:right;margin-right:1.5%;">
			<b>
			 <s:text name="procurementProduct.code"/>
			 <s:text name="-"/>
			 <s:text name="procurementProduct.name"/>
			 </b>
		</div> 
		<div id="baseDiv">
			<table id="detail"></table>
			<div id="pagerForDetail"></div>
		</div>
		<s:form name="createform" action="procurementVariety_create">
			<s:hidden key="id" />
			<s:hidden name="procurementProductId" value="%{id}"/>
			<s:hidden name="procurementProductCodeAndName" value="%{procurementProduct.code}-%{procurementProduct.name}"/>
			<s:hidden key="currentPage"/>
			<s:hidden name="tabIndex"/>	
		</s:form>
		<s:form name="detailform" action="procurementVariety_detail">
			<s:hidden name="tabIndex"/>	
			<s:hidden name="procurementProductCodeAndName" value="%{procurementProduct.code}-%{procurementProduct.name}"/>
			<s:hidden name="procurementProductId" value="%{procurement.id}"/>
			<s:hidden name="procurementProductEnrollId" value="%{procurementProduct.id}"/>
			<s:hidden key="id" />
			<s:hidden key="currentPage"/>
		</s:form>
	</div>
</div>
</div>	
<script>
$(document).ready(function () {
	
	 var tabIndex=<%if (request.getParameter("tabIndex") == null) {
			out.print("'#tabs-1'");
		} else {
			out.print("'" + request.getParameter("tabIndex") + "'");
		}%>;
		
	
	loadVarietyGrid();
	
	var tabIndex=<%if(request.getParameter("tabIndex")==null){out.print("'#tabs-1'");}else{out.print("'"+request.getParameter("tabIndex")+"'");}%>;
	var tabObj=$('a[href="'+tabIndex+'"]');
	$(tabObj).closest("li").addClass('active');
	$("div").removeClass("active in");
	$(tabIndex).addClass('active in');
	
	//Below code to set varity tab active if crumb value is redirected.
	 var tabSelected = getUrlParameter('tabValue'); 
	 if(tabSelected=="tabs-2"){
		 $(tabIndex).removeClass("active in");
		 $('#tabs-2').addClass('active in');
		 $(tabObj).closest("li").removeClass('active');
		 tabObj=$('a[href="#tabs-2"]');
		 $(tabObj).closest("li").addClass('active');
	 }
	
    $('#update').on('click', function (e) {
        document.updateform.id.value = document.getElementById('procurementProductId').value;
        document.updateform.currentPage.value = document.form.currentPage.value;
        document.updateform.submit();
    });

    $('#delete').on('click', function (e) {
        if (confirm('<s:text name="confirm.delete"/> ')) {
            document.deleteform.id.value = document.getElementById('procurementProductId').value;
            document.deleteform.currentPage.value = document.form.currentPage.value;
            document.deleteform.submit();
        }
    });    	
});

function loadVarietyGrid(){
		$(document).ready(function(){
			jQuery("#detail").jqGrid(
			{
			   	url:'procurementVariety_data.action?procurementProductId='+document.getElementById('procurementProductId').value,
			   	pager: '#pagerForDetail',	  
			   	datatype: "json",	
			   	styleUI : 'Bootstrap',
			   	mtype: 'POST',
			   	colNames:[
			  		   	  '<s:text name="procurementVariety.code"/>',
			  		      '<s:text name="procurementVariety.name"/>'
			      	 	 ],
			   	colModel:[			
			   		{name:'code',index:'code',width:125,sortable:true},
			   		{name:'name',index:'name',width:125,sortable:true}
			   			 ],
			   	height: 301, 
			    width: $("#baseDiv1").width(), // assign parent div width
			    scrollOffset: 0,
			   	rowNum:10,
			   	rowList : [10,25,50],
			    sortname:'id',			  
			    sortorder: "desc",
			    viewrecords: true, // for viewing noofrecords displaying string at the right side of the table
			    onSelectRow: function(id){ 
				  document.detailform.id.value  =id;
			      document.detailform.submit();      
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
		    });
		}); 
	}
	
	
	function onDelete(){
		 if(confirm( '<s:text name="confirm.delete"/> ')){
			 document.deleteform.id.value = document.getElementById('procurementProductId').value;
			 document.deleteform.currentPage.value = document.form.currentPage.value;
			 document.deleteform.submit();
		}
	}
	
	//Function to get URL Parameter values.
	function getUrlParameter(sParam) {	   
	   var sPageURL = decodeURIComponent(window.location.search.substring(1));
	   var sURLVariables = sPageURL.split('&');
	   var  sParameterName;
	   var i;	   

	   for (i = 0; i < sURLVariables.length; i++) {		   
	       sParameterName = sURLVariables[i].split('=');        
	       if (sParameterName[0] === sParam) {  	    	   
	           return sParameterName[1] === undefined ? true : sParameterName[1];
	       }
	   }
	}
	</script>