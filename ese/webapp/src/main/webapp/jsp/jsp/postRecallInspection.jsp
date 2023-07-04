<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<head>
<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType")
					: "swithlayout"%>">
</head>
<script type="text/javascript">
var tenant = '<s:property value="getCurrentTenantId()"/>';
var command = '<s:property value="command"/>';
var url =<%out.print("'" + request.getParameter("url") + "'");%>;
<%-- var layOut='<%=request.getParameter("layoutType")%>; --%>
 	
 jQuery(document)
			.ready(
					function() {
						$(".breadCrumbNavigation").find('li:last').find(
								'a:first').attr("href",
								'<s:property value="redirectContent" />');
						if (url == null || url == undefined || url == ''
								|| url == 'null') {
							url = 'postRecallInspection_';
						}
					
	               
	             

						$("#buttonAdd1").on(
								'click',
								function(event) {
									event.preventDefault();
									$("#buttonAdd1").prop('disabled', true);
									if (!validateAndSubmit("target1","postRecallInspection_")) {

										$("#buttonAdd1").prop(
												'disabled', false);

									}
									$("#buttonAdd1").prop("disabled", false);
								});
						
						$("#shipmentInfoTable").hide();
						  if (command == 'update') {
								
								 var lotNumber='<s:property value="selectedLotNo"/>';
								loadProductVariety(lotNumber);
								  
								var insdpecDate='<s:property value="inspectionDate"/>';
								$( "#inspdate" ).datepicker(

										{
										
									  // format: 'mm/dd/yyyy',
							          startDate: insdpecDate,
							           autoclose: true,
										beforeShow : function()
										{
										jQuery( this ).datepicker({ maxDate: insdpecDate });
										},
										changeMonth: true,
										changeYear: true

										}

										);
								}else{
									$( "#inspdate" ).datepicker(

											{
											
										  // format: 'mm/dd/yyyy',
												autoclose: true,
												beforeShow : function()
												{
												jQuery( this ).datepicker();
												},
												changeMonth: true,
												changeYear: true

											}

											);
								}
						$("#inspdate").datepicker({
							changeMonth : true,
							changeYear : true,
							yearRange : "-100:+0",
							dateFormat : "dd/mm/yy",
						});
						
						if ("<s:property value='command'/>" == "update") {
							kencode = '<s:property value="recalling.kenyaTraceCode"/>';
							kenid = '<s:property value="recalling.id"/>';
							batchno = '<s:property value="recalling.batchNo"/>';
							if($("#lotNo").val()==null || $("#lotNo").val().trim()==''){
							   	 $('#lotNo').append(new Option(kencode+"-"+batchno, kenid));
							 	$("#lotNo").val(kenid).trigger('change');	
							   	}
						}
						
						
						$("#inspdate").focusin(function(){ 
							var createdDate = $(".createdDate").text();
							 if(!isEmpty(createdDate) && command == 'create'){
								 if(confirm('<s:property value="%{getLocaleProperty('reset.resetListDataPostRecalling')}" />')){
									 $("#shipmentContent").empty();
									 $("#lotNo").val(" ").change();
									 $("#shipmentInfoTable").hide();
									 $('#inspdate').blur();
						      }   else{
						    	 $('#inspdate').blur();
						    	 setTimeout(closeDatepickernew, 400);
						     }
							 }
							});
					

					});

	function onCancel() {
		window.location.href = "<s:property value="redirectContent" />";
	}



</script>
<script type="text/javascript">
function loadProductVariety(lotNo) {
	$("#shipmentContent").empty();
	$('#recNature').val('');
	$('#recNatureLab').val('');

	if (!isEmpty(lotNo)) {
		$.ajax({
			type : "POST",
			async : false,
			url : "postRecallInspection_populateProductVariety.action",
			data : {
				selectedLotNo : lotNo
			},
			success : function(result) {
				$('#product').val(result.product);
				$('#variety').val(result.variety);
			    	$('#recNature').val(result.nature);
				$('#recNatureLab').val(result.recNatureLab);

				$("#shipmentContent").empty();
				if(result != null){
				$("#shipmentInfoTable").show();
				}else{
					$("#shipmentInfoTable").hide();
				}
				for(var i=0;i<result.procurementGrades.length;i++){
					var rowCounter = $('#cultiCharBody tr').length-1;
					var tableRow="<tr id=row"+(++rowCounter)+" class=impr "+ result.procurementGrades[i].id +">";
					tableRow+="<td>"+result.procurementGrades[i].farmername+"</td>";
					tableRow+="<td>"+result.procurementGrades[i].lotid+"</td>";
					tableRow+="<td>"+result.procurementGrades[i].blockid+"</td>";
					tableRow+="<td>"+result.procurementGrades[i].blockname+"</td>";
					tableRow+="<td>"+result.procurementGrades[i].plantingId+"</td>";
				tableRow+="<td>"+result.procurementGrades[i].prodCat+"</td>";
				tableRow+="<td class='otherProdCat1'>"+result.procurementGrades[i].product+"</td>";
				tableRow+="<td class='createdDate'>"+result.procurementGrades[i].createdDate+"</td>";
				
				$('#recallingDate').val(result.procurementGrades[i].createdDate);
				tableRow+="<td>"+result.procurementGrades[i].unit+"</td>";
				tableRow+="<td>"+result.procurementGrades[i].lotQty+"</td>";
				
			//	tableRow+="<td><input type='checkbox' name='selector[]' id='selectrows' type='checkbox' value='"+result.procurementGrades[i].id+"'></td>";
				tableRow+="</tr>";
					jQuery("#shipmentContent").append(tableRow);
					}
			}
		});
	}
}

			
function resetList(){
	 var createdDate = $(".createdDate").text();
	 if(!isEmpty(createdDate)){
		 if(confirm('<s:property value="%{getLocaleProperty('reset.resetListDataPostRecalling')}" />')){
			 $("#shipmentContent").empty();
			 $("#lotNo").val(" ").change();
			 $("#shipmentInfoTable").hide();
     }  
		/*  else{
		 var inspdate = $("#inspdate").val();
	 } */
	 }
	}		
function closeDatepickernew(){
	 $(".datepicker").hide();
}
</script>
<body>

	<s:form name="form" cssClass="fillform" id="target1" method="post">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="recallingDate" id="recallingDate" />
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="postRecallInspection.id" />

		</s:if>
		<s:hidden key="command" />
		<div class="appContentWrapper marginBottom">
			<div class="error">
				<s:actionerror />
				<s:fielderror />
				<s:if test="hasActionErrors()">
					<div style="color: red;">
						<s:actionerror />
					</div>
				</s:if>
				<div id="validateError" style="text-align: left;"></div>
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.postRecall" />
				</h2>
				<div class="flexform">

					<div class="flexform-item">
						<label for="txt"><s:text name="inspDate" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="inspectionDate" id="inspdate" theme="simple"
								maxlength="10"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="form-control input-sm"/>


						</div>
					</div>
					<div class="flexform-item">
						<label for="txt">UCR Kentrade <sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:if test='"update".equalsIgnoreCase(command)'>
								<%-- <s:property value="postRecallInspection.recall.kenyaTraceCode" /> '-' <s:property value="postRecallInspection.recall.batchNo" /> --%>
								<s:property value="postRecallInspection.recall.kenyaTraceCode" />-<s:property
									value="postRecallInspection.recall.batchNo" />
								<s:hidden name="selectedLotNo"
									value="%{postRecallInspection.recall.id}" />
							</s:if>
							<s:else>

								<s:select class="form-control select2" id="lotNo"
									name="selectedLotNo" listKey="key" listValue="value"
									list="lotNoList" headerKey=" "
									headerValue="%{getText('txt.select')}"
									onchange="loadProductVariety(this.value)" />
							</s:else>



						</div>
					</div>
                   <div style="overflow: auto;">
					<table class="table table-bordered aspect-detail"
						id="shipmentInfoTable">
						<thead>
							<tr>
								<th>Farmer Name</th>
								<th>Lot Number</th>
								<th>Block Id</th>
								<th>Block Name</th>
								<th>Planting ID</th>
								<th><s:property
										value="%{getLocaleProperty('shipment.product')}" /></th>

								<th><s:property
										value="%{getLocaleProperty('shipment.variety')}" /></th>
								<th><s:property
										value="%{getLocaleProperty('recallingDate.createdDate')}" /></th>
								<th>Unit</th>
								<th><s:property
										value="%{getLocaleProperty('shipment.lotQty')}" /></th>
							</tr>
						</thead>
						<tbody id="shipmentContent">

							<s:iterator value="recalling.recallDetails" status="incr"
								id="ShipmentDetailse">
								<tr id="row<s:property	value="#incr.index" />">
									<td class="lotNo"><s:property value="lotNo" /></td>
									<td class="lotNo"><s:property value="batchNo" /></td>
									<td class="lotNo"><s:property value="farmcrops.blockName" /></td>
									<td class="plantingId"><s:property value="planting.plantingId" /></td>
									<td class="product"><s:property
											value="farmcrops.variety.procurementProduct.name" /></td>
									<td class="variety"><s:property
											value="farmcrops.variety.name" /></td>
									<td class="createdDate"><s:property
											value="recall.recDate" /></td>
									<td class="packingUnit"><s:property value="receivedUnits" /></td>
									<td class="lotQty"><s:property value="receivedWeight" /></td>

								</tr>
							</s:iterator>
						</tbody>
					</table>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="postrecall.agencyName" />
							<sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="postRecallInspection.nameOfAgency"
								theme="simple" cssClass="upercls form-control" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="postrecall.inspector" /> <sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="postRecallInspection.nameOfInspector"
								theme="simple" cssClass="upercls form-control" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="postrecall.inspmobile" />
							<sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="postRecallInspection.mobileNumber"
								theme="simple" cssClass="lowercase form-control"
								onkeypress="return isNumber(event)" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="postrecall.oprator" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">

							<s:select list="getCatList(getLocaleProperty('operatorList'))"
								class="form-control  select2" id="operator"
								name="postRecallInspection.operatorBeingInspected" listKey="key"
								listValue="value" headerKey=" "
								headerValue="%{getText('txt.select')}" />


						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="recalling.recNature" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:hidden name="postRecallInspection.natureOfRecall"
								id="recNature" />
							<s:textfield readonly="true" id="recNatureLab" />


						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="postrecall.manag" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">

							<s:select class="form-control  select2 " id="manag"
								name="postRecallInspection.managementOfRecalled" listKey="key"
								listValue="value" headerKey=" "
								headerValue="%{getText('txt.select')}"
								list="getCatList(getLocaleProperty('managementList'))" />

						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="postrecall.recall" /> <sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textarea name="postRecallInspection.recallReport"
								theme="simple" cssClass="lowercase form-control" />


						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:text name="postrecall.correc" /> <sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textarea name="postRecallInspection.correctiveAction"
								theme="simple" cssClass="lowercase form-control" />


						</div>
					</div>
					<%-- <div class="flexform-item">
						<label for="txt"><s:text name="postrecall.recom" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
	                             <s:textarea name="postRecallInspection.recommendation"
								theme="simple" cssClass="lowercase form-control" />
							
						</div>
						
						<s:select class="form-control  select2" id="recommendation"
							name="postRecallInspection.recommendation" listKey="key" listValue="value"
							list="postRecommendationList" headerKey="" theme="simple"
							headerValue="%{getText('txt.select')}" />
					</div> --%>


					<div class="flexform-item">
						<label for="txt"><s:text name="postrecall.recom" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2"
								list="postRecommendationList" headerKey="" theme="simple"
								name="postRecallInspection.recommendation"
								headerValue="%{getText('txt.select')}" id="recommendationPost" />
						</div>
					</div>


				</div>
			</div>
		</div>
		<div class="margin-top-10">
			<span class=""><span class="first-child">
					<button type="submit" id="buttonAdd1"
						class="save-btn btn btn-success">
						<font color="#FFFFFF"> <b> <s:text name="save.button" /></b>
						</font>
					</button>&nbsp;
			</span></span> <span class=""><span class="first-child"><button
						type="button" id="canBt" class="cancel-btn btn btn-sts"
						onclick="onCancel();">
						<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
						</font></b>
					</button></span></span>
		</div>
	</s:form>
</body>
</html>