<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<s:hidden key="warehouse.id" id="coOperativeId"/>
<font color="red">
    <s:actionerror/></font>
<s:form name="form" cssClass="fillform">
	<s:hidden key="currentPage"/>
	
	<s:hidden key="id" />
	<s:hidden key="warehouse.id"/>
	<s:hidden key="command" />
	<div class="flex-view-layout">
	<div class="fullwidth">
		<div class="flexWrapper">
			<div class="flexLeft appContentWrapper">
				<div class="formContainerWrapper dynamic-form-con">
					<h2><s:property value="%{getLocaleProperty('info.coOperative')}"/></h2>
					 
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="coOperative.code" /></p>
						<p class="flexItem"><s:property value="warehouse.code"/></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="coOperative.name" /></p>
						<p class="flexItem"><s:property value="warehouse.name" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="coOperative.location" /></p>
						<p class="flexItem"><s:property value="warehouse.location" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="coOperative.address" /></p>
						<p class="flexItem"><s:property value="warehouse.address" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="coOperative.phoneNo" /></p>
						<p class="flexItem"><s:property value="warehouse.phoneNo" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:property value="%{getLocaleProperty('coOperative.warehouseInCharge')}"/></p>
						<p class="flexItem"><s:property value="warehouse.warehouseInCharge" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="coOperative.capacityInTonnes" /></p>
						<p class="flexItem"><s:property value="warehouse.capacityInTonnes" /></p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:text name="coOperative.storageCommodity" /></p>
						<p class="flexItem"><s:property value="warehouse.storageCommodity" /></p>
					</div>
					<s:if test="warehouse.storageCommodity==3">
						<div class="dynamic-flexItem">
							<p class="flexItem"><s:text name="coOperative.commodityOthers" /></p>
							<p class="flexItem"><s:property value="warehouse.commodityOthers" /></p>
						</div>
					</s:if>	
					<div class="dynamic-flexItem">
						<p class="flexItem"><s:property value="%{getLocaleProperty('coOperative.warehouseOwnerShip')}"/></p>
						<p class="flexItem"><s:property value="ownershipList[warehouse.warehouseOwnerShip]" /></p>
					</div>
				</div>
				<s:if test="enableStorage==1">
					<div class="formContainerWrapper">
						<h2>
						<s:property value="%{getLocaleProperty('coldStorage.details')}"/>
						</h2>
						<table class="table table-bordered table-hover fillform">
							<tr>
								<th style="width: 15%" align="center"><s:property value="%{getLocaleProperty('coldStorageName')}"/></th>
							
								<th style="width: 15%" align="center"><s:property value="%{getLocaleProperty('maxBayWeight')}"/></th>
							</tr>
							<s:iterator value="warehouseStorageMapList" var="values">
								<tr>
									<td style="width: 15%"><s:property value="coldStorageName" /></td>
									<td style="width: 15%"><s:property value="maxBayHold" /></td>
							</tr>
							</s:iterator>
						</table></div>
								</s:if>

<div class="yui-skin-sam">
                
  <span id="update" class=""><span class="first-child"><button type="button" class="edit-btn btn btn-success">
               <b><FONT color="#FFFFFF"><s:text name="edit.button"/>
                </font></b></button></span></span> 
  
  <span id="delete" class=""><span class="first-child"><button type="button" class="delete-btn btn btn-warning">
               <b><FONT color="#FFFFFF"><s:text name="delete.button"/>
                </font></b></button></span></span>               
                    
  <span id="cancel" class=""><span class="first-child"><button type="button" class="back-btn btn btn-sts">
               <b><FONT color="#FFFFFF"><s:text name="back.button"/>
                </font></b></button></span></span> 

</div>
	</div>
	</div>
	</div>
	</div>
</s:form>

<s:form name="updateform" action="cooperative_update.action">
    <s:hidden key="id"/>
    <s:hidden key="currentPage"/>
    <s:hidden name="type" class="type"/>
    
</s:form>
<s:form name="deleteform" action="cooperative_delete.action">
    <s:hidden key="id"/>
    <s:hidden key="currentPage"/>
    <s:hidden name="type" class="type"/>
</s:form>
<s:form name="cancelform" action="cooperative_list.action">
    <s:hidden key="currentPage"/>
    <s:hidden name="type" class="type"/>
    <s:hidden name="postdata" id="postdata" />
</s:form>


<script type="text/javascript">
    $(document).ready(function () {
        $('#update').on('click', function (e) {
            document.updateform.id.value = document.getElementById('coOperativeId').value;
            document.updateform.currentPage.value = document.form.currentPage.value;
            document.updateform.submit();
        });

        $('#delete').on('click', function (e) {
            if (confirm('<s:text name="confirm.delete"/> ')) {
                document.deleteform.id.value = document.getElementById('coOperativeId').value;
                document.deleteform.currentPage.value = document.form.currentPage.value;
                document.deleteform.submit();
            }
        });
        var type='<s:property value="type"/>';
        $(".type").val("");
		$(".type").val(type);
    });

</script>
