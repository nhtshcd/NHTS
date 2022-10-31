<%@ include file="/jsp/common/detail-assets.jsp"%>

<%@ include file="/jsp/common/grid-assets.jsp"%>

<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<script>
</script>


<s:hidden key="procurementGrade.id" id="procurementGradeId"/>
<font color="red">
    <s:actionerror/></font>
<s:form name="form" cssClass="fillform">
	<s:hidden key="currentPage"/>
	<s:hidden key="id" />
	<s:hidden key="procurementGrade.id"/>
	<s:hidden name="procurementGradeCodeAndName" />
	<s:hidden name="procurementProductCodeAndName" />
	<s:hidden key="command" />
	<s:hidden key="procurementGrade.id"/>
	<table class="table table-bordered aspect-detail">
		<tr>
			<th colspan="2"><s:text name="info.procurementGrade" /></th>
		</tr>
		
		<tr class="odd">
			<td width="35%"><s:text name="product.name" /></td>
			<td width="65%"><s:property value="%{procurementGrade.procurementVariety.name}"/>&nbsp;</td>
		</tr>
		
		<tr class="odd">
			<td width="35%"><s:text name="procurementGrade.code" /></td>
			<td width="65%"><s:property value="%{procurementGrade.code}"/>&nbsp;</td>
		</tr>
		
		<tr class="odd">
			<td width="35%"><s:text name="procurementGrade.name" /></td>
			<td width="65%"><s:property value="procurementGrade.name" />&nbsp;</td>
		</tr>
		
		<tr class="odd">
			<td width="35%"><s:text name="procurementGrade.unit" /></td>
			<td width="65%"><s:property value="procurementGrade.type.name"/>&nbsp;</td>
		</tr>
		
		<tr class="odd">
			<td width="35%"><s:text name="procurementGrade.price" /></td>
			<td width="65%"><s:property value="gradePrice" />&nbsp;</td>
		</tr>
		
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
            
             <%--<sec:authorize ifAllGranted="profile.procurementProduct.delete">
             <span id="delete" class=""><span class="first-child">
                <button type="button" class="delete-btn btn btn-warning" onclick="onDelete()"> 
                    <FONT color="#FFFFFF">
                    <b><s:text name="delete.button" /></b>
                    </font>
                </button>
      </span></span></sec:authorize>--%>
  <span id="cancel" class=""><span class="first-child"><button type="button" class="back-btn btn btn-sts">
               <b><FONT color="#FFFFFF"><s:text name="back.button"/>
                </font></b></button></span></span> 
</div>
</s:form>

<s:form name="updateform" action="procurementGrade_update.action">
    <s:hidden name="procurementVarietyId" value="%{procurementGrade.procurementVariety.id}"/>
    <s:hidden name="procurementVarietyCodeAndName" />
    <s:hidden name="procurementProductCodeAndName" />
    <s:hidden key="id" />
    <s:hidden name="tabIndex" value="%{tabIndexVariety}" />
    <s:hidden key="currentPage"/>
</s:form>
<s:form name="cancelform" action="procurementVariety_detail.action">
 <s:hidden name="procurementVarietyId" value="%{procurementGrade.procurementVariety.id}"/>
     <s:hidden value="%{procurementGrade.procurementVariety.id}" name="id"/>
       <s:hidden name="procurementVarietyCodeAndName" />
      <s:hidden name="procurementProductCodeAndName" />
     <s:hidden name="tabIndex" value="%{tabIndexVariety}" />
	<s:hidden key="currentPage"/>
</s:form>

<s:form name="deleteform" action="procurementGrade_delete.action">
	<s:hidden key="id" />
	<s:hidden key="currentPage" />
	</s:form>
	
<script type="text/javascript">  
$(document).ready(function () {
    $('#update').on('click', function (e) {
        document.updateform.id.value = document.getElementById('procurementGradeId').value;
        document.updateform.currentPage.value = document.form.currentPage.value;
        document.updateform.submit();
    });

    $('#delete').on('click', function (e) {
        if (confirm('<s:text name="confirm.delete"/> ')) {
            document.deleteform.id.value = document.getElementById('procurementGradeId').value;
            document.deleteform.currentPage.value = document.form.currentPage.value;
            document.deleteform.submit();
        }
    });
});
</script>
