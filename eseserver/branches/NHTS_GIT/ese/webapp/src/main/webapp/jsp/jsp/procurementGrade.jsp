<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
<script type="text/javascript">

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
</script>
</head>
<body>
<div class="error"><s:actionerror /><s:fielderror />
<sup>*</sup>
<s:text name="reqd.field" /></div>
<s:form name="form" cssClass="fillform" action="procurementGrade_%{command}">
	<s:hidden key="currentPage"/>
	<s:hidden name="procurementVarietyId" value="%{procurementVarietyId}" />
	<s:hidden key="procurementGrade.id" />
	<s:hidden name="procurementVarietyCodeAndName" />
	<s:if test='"update".equalsIgnoreCase(command)'>
	<s:hidden name="tabIndex" />
	</s:if>
	<s:hidden key="command" />
	<table class="table table-bordered aspect-detail">
		<tr>
			<th colspan="2"><s:text name="info.procurementGrade" /></th>
		</tr>
		
		<tr class="odd">
			<td><div class="col-xs-6"><s:text name="product.name" /></div></td>
			<td><div class="col-xs-6">
			<s:if test='"update".equalsIgnoreCase(command)'>
				<s:property value="%{procurementVarietyCodeAndName}" />
				<s:hidden key="procurementGrade.procurementVariety.id" />
				</s:if>
				<s:else>
				<s:property value="%{procurementVarietyCodeAndName}" />
				</s:else>
			</div></td>
		</tr>
		
		<%-- <tr class="odd">
			<td><div class="col-xs-6"><div class="col-xs-6"><s:text name="procurementGrade.code" /></div></td>
			
			<td><div class="col-xs-6"><div class="col-xs-6">
			<s:if test='"update".equalsIgnoreCase(command)'>
					<s:property value="procurementGrade.code" />
						<s:hidden key="procurementGrade.code" />
				</s:if>
				<s:else>
					<s:textfield name="procurementGrade.code" theme="simple" maxlength="8"/><sup
				style="color: red;">*</sup>
				
				</s:else>
			</div></td>
		</tr> --%>
		
		<tr class="odd">
			<td><div class="col-xs-6"><s:text name="procurementGrade.name" /></div></td>
			<td><div class="col-xs-6">
			<s:textfield cssClass="form-control input-sm" name="procurementGrade.name" theme="simple" maxlength="35"/></div><sup
				style="color: red;">*</sup></td>
		</tr>
		
		<tr class="odd">
			<td><div class="col-xs-6"><s:text name="procurementGrade.unit" /></div></td>
			<td><div class="col-xs-6"><s:select id="type" name="procurementGrade.type.code" 
				list="listUom" headerKey="-1" listKey="code" listValue="name" headerValue="%{getText('txt.select')}" cssClass="form-control input-sm select2"/></div>
				<sup style="color: red;">*</sup></td>
			
							
		</tr>  
		
		<tr class="odd">
			<td><div class="col-xs-6"><s:text name="procurementGrade.price" /></div></td>
			<td><div class="col-xs-4">
			<s:textfield cssClass="form-control input-sm" name="procurementGrade.pricePrefix" theme="simple" maxlength="5" onkeypress="return isNumber(event)"/></div>
			<div class="col-xs-2"><s:textfield cssClass="form-control input-sm" name="procurementGrade.priceSuffix" theme="simple" maxlength="2" onkeypress="return isNumber(event)"/></div><sup style="color: red;">*</sup></td>
		</tr>
		
	</table>
	<br />

	<div class="yui-skin-sam"><s:if test="command =='create'">
		<span id="button" class=""><span class="first-child">
		<button type="button" class="save-btn btn btn-success"><font color="#FFFFFF"> <b><s:text
			name="save.button" /></b> </font></button>
		</span></span>
		</s:if> <s:else>
		<span id="button" class=""><span class="first-child">
		<button type="button" class="save-btn btn btn-success"><font color="#FFFFFF"> <b><s:text
			name="update.button" /></b> </font></button>
		</span></span></s:else>
		<span id="cancel" class=""><span class="first-child"><button type="button" class="cancel-btn btn btn-sts">
              <b><FONT color="#FFFFFF"><s:text name="cancel.button"/>
                </font></b></button></span></span>
	</div>
</s:form>
<s:form name="cancelform" action="procurementVariety_detail.action%{tabIndexVariety}">
	<s:hidden name="id" value="%{procurementVarietyId}" />
	<s:hidden key="currentPage"/>
	<s:hidden name="tabIndex" value="%{tabIndexVariety}" />
    <s:hidden key="currentPage"/>
</s:form>
</body>