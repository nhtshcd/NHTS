<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<div id="tabs">

<s:hidden name="procurementProductEnrollId" value="%{procurementVariety.procurementProduct.id}"/>
  <%
	if(request.getParameter("procurementProductEnrollId") != null && request.getParameter("procurementProductEnrollId") != ""){
    		session.setAttribute("uniqueProcurementProductId",request.getParameter("procurementProductEnrollId"));
	}
	else{
			request.setAttribute("procurementProductEnrollId",session.getAttribute("uniqueProcurementProductId").toString());
	}
 %>
 <div id="baseDiv1" class="baseDiv1" style="width:98%"></div>
<ul id="tabsUL" class="nav nav-tabs">
	<li><a  data-toggle="tab" href="#tabs-1"><s:text name="info.procurementVariety" /></a></li>
	<li><a  data-toggle="tab" href="#tabs-2"><s:text name="Grade" /></a></li>
</ul>

<div class="tab-content">
<div id="tabs-1" class="tab-pane fade in active">
<s:hidden key="procurementVariety.id" id="procurementVarietyId"/>
<font color="red">
    <s:actionerror/></font>
<s:form name="form" cssClass="fillform">
	<s:hidden key="currentPage"/>
	<s:hidden key="id" />
	<s:hidden key="procurementVariety.id"/>
	<s:hidden name="procurementProductCodeAndName" />
	<s:hidden key="command" />
	<s:hidden key="procurementVariety.id"/>
	<table class="table table-bordered aspect-detail">
		<tr class="odd">
			<td><s:property value="%{getLocaleProperty('product.name')}" /></td>
			<td><s:property value="procurementVariety.procurementProduct.name"/>&nbsp;</td>
		</tr>
		
		<tr class="odd">
			<td><s:text name="procurementVariety.code" /></td>
			<td><s:property value="procurementVariety.code"/>&nbsp;</td>
		</tr>
		
		<tr class="odd">
			<td><s:property value="%{getLocaleProperty('procurementVariety.name')}" /></td>
			<td><s:property value="procurementVariety.name" />&nbsp;</td>
		</tr>
		
		<tr class="odd">
			<td><s:text name="procurementVariety.noDaysToGrow" /></td>
			<td><s:property value="procurementVariety.noDaysToGrow" />&nbsp;</td>
		</tr>
		
			<tr class="odd">
			<td width="35%"><s:property value="%{getLocaleProperty('procurementVariety.yield')}" /></td>
			<td width="65%"><s:property value="procurementVariety.yield" />&nbsp;</td>
		</tr>
		<s:if test="currentTenantId=='kpf'||currentTenantId=='wub'|| currentTenantId=='simfed'">
		<tr class="odd">
			<td width="35%"><s:text name="procurementVariety.harvDays" /></td>
			<td width="65%"><s:property value="procurementVariety.harvestDays" />&nbsp;</td>
		</tr>
		</s:if>
		
	</table>
	<br />
<div class="yui-skin-sam">
    <sec:authorize ifAllGranted="profile.procurementProduct.update">
        <span id="update" class=""><span class="first-child">
                <button type="button" class="edit-btn btn btn-success">
                    <FONT color="#FFFFFF">
                    <b><s:text name="edit.button" /></b>
                    </font>
                </button>
            </span></span></sec:authorize>
            
            <sec:authorize ifAllGranted="profile.procurementProduct.delete">
             <span id="delete" class=""><span class="first-child">
                <button type="button" class="delete-btn btn btn-warning" onclick="onDelete()"> 
                    <FONT color="#FFFFFF">
                    <b><s:text name="delete.button" /></b>
                    </font>
                </button>
      </span></span></sec:authorize>
      
  <span id="cancel" class=""><span class="first-child"><button type="button" class="back-btn btn btn-sts">
               <b><FONT color="#FFFFFF"><s:text name="back.button"/>
                </font></b></button></span></span> 
</div>
</s:form>

<s:form name="updateform" action="procurementVariety_update.action">
    <s:hidden name="procurementProductId" value="%{procurementVariety.procurementProduct.id}"/>
    <s:hidden name="procurementProductCodeAndName" />
    
    <s:hidden key="id" />
    <s:hidden name="tabIndex" value="%{tabIndexVariety}" />
    <s:hidden key="currentPage"/>
</s:form>

<s:form name="deleteform" action="procurementVariety_delete.action">
	<s:hidden key="id" />
	<s:hidden key="currentPage" />
	</s:form>

<s:form name="cancelform" action="procurementProductEnroll_detail.action">
     <s:hidden value="%{procurementVariety.procurementProduct.id}" name="id"/>
	<s:hidden key="currentPage"/>
	<s:hidden name="tabIndex" value="%{tabIndexVariety}" />
</s:form>

</div>
	<div id="tabs-2" class="tab-pane fade">
		<s:hidden key="id" />
		<s:if test="getCurrentTenantId()!='lalteer' && getCurrentTenantId()!='awi'">
		<sec:authorize ifAllGranted="profile.procurementProduct.create">
			<input type="BUTTON" class="btn btn-sts" id="add" value="<s:text name="create.button"/>" onclick="document.createform.submit()" style="margin-bottom:1.5%"/>
		</sec:authorize>
		</s:if>
		<s:else>
		<sec:authorize ifAllGranted="profile.procurementProduct.create">
			<div style="margin-bottom:3.5%"></div>
		</sec:authorize></s:else>
		
		<div style="float:right;margin-right:1.5%;margin-top:-3%">
			<b>
				 <s:text name="%{procurementVariety.code}"/>
				 <s:text name="-"/>
				 <s:text name="%{procurementVariety.name}"/>
			 </b>
		</div> 
		<div style="width: 99%;" id="baseDiv1">
			<table id="detail"></table>
			<div id="pagerForDetail"></div>
		</div>
		<s:form name="createform" action="procurementGrade_create">
			<s:hidden key="id" />
			<s:hidden name="procurementVarietyId" value="%{id}"/>
			<s:hidden name="procurementVarietyCodeAndName" value="%{procurementVariety.code}-%{procurementVariety.name}"/>
			<s:hidden key="currentPage"/>
			<s:hidden name="tabIndex"/>	
		</s:form>
		<s:form name="detailform" action="procurementGrade_detail">
			<s:hidden name="tabIndex"/>
			<s:hidden name="procurementVarietyCodeAndName" value="%{procurementVariety.code}-%{procurementVariety.name}"/>
			<s:hidden name="procurementVarietyId" value="%{id}"/>
			<s:hidden name="procurementProductCodeAndName" />
			<s:hidden key="id" />
			<s:hidden key="currentPage"/>
		</s:form>
		
	</div>
</div>
</div>

<script>


function loadGradeGrid(){
	$(document).ready(function(){
		jQuery("#detail").jqGrid(
		{
		   	url:'procurementGrade_data.action?procurementVarietyId='+document.getElementById('procurementVarietyId').value,
		   	pager: '#pagerForDetail',	  
		   	datatype: "json",	
		   	styleUI : 'Bootstrap',
		   	mtype: 'POST',
		   	colNames:[
		  		   	  '<s:text name="procurementGrade.code"/>',
		  		      '<s:text name="procurementGrade.name"/>',
		  		      '<s:text name="procurementGrade.price"/>'
		      	 	 ],
		   	colModel:[			
		   		{name:'code',index:'code',width:125,sortable:true},
		   		{name:'name',index:'name',width:125,sortable:true},
		   		{name:'price',index:'price',width:125,sortable:true,align:'left'}
		   			 ],
		   	height: 301, 
		    width: jQuery("#baseDiv1").width(), // assign parent div width
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
		
		jQuery("#detail").jqGrid('navGrid','#pagerForDetail',{edit:false,add:false,del:false,search:false,refresh:true}); // enabled refresh for reloading grid
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





$(document).ready(function () {
	loadGradeGrid();
	
	var tabIndex=<%if(request.getParameter("tabIndex")==null){out.print("'#tabs-1'");}else{out.print("'"+request.getParameter("tabIndex")+"'");}%>;
	if(tabIndex==null||tabIndex==""){
		tabIndex='#tabs-1';
	}
	var tabObj=$('a[href="'+tabIndex+'"]');
	$(tabObj).closest("li").addClass('active');
	
	$("div").removeClass("active in");
	$(tabIndex).addClass('active in');
	
 //below code to set selected tab while redirecting from bread crumb.
 var tabSelected = getUrlParameter('tabValue');
 if(tabSelected=="tabs-2"){
	 $(tabIndex).removeClass("active in");
	 $('#tabs-2').addClass('active in');
	 $(tabObj).closest("li").removeClass('active');
	 tabObj=$('a[href="#tabs-2"]'); // change this value to the tab id.
	 $(tabObj).closest("li").addClass('active');
 }
	
	
	
    $('#update').on('click', function (e) {
        document.updateform.id.value = document.getElementById('procurementVarietyId').value;
        document.updateform.currentPage.value = document.form.currentPage.value;
        document.updateform.submit();
    });

    $('#delete').on('click', function (e) {
        if (confirm('<s:text name="confirm.delete"/> ')) {
            document.deleteform.id.value = document.getElementById('procurementVarietyId').value;
            document.deleteform.currentPage.value = document.form.currentPage.value;
            document.deleteform.submit();
        }
    });
});

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