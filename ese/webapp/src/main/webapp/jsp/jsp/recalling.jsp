<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
</head>
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
</head>
<script type="text/javascript">
var command = '<s:property value="command"/>';
	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>";
	}
	function setFiles(val) {
		//alert("ddd")
		if (val != null && val != '') {
			$('#fphoto').val(val);
			
		}
	}
	
	function popDownload(type) {
		document.getElementById("loadId").value = type;

		$('#fileDownload').submit();

	}

	jQuery(document)
			.ready(
					function() {
						$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
						
						$("#buttonAdd1").on(
								'click',
								function(event) {
									
									event.preventDefault();
									
									$("#buttonAdd1").prop('disabled', true);
									if (!validateAndSubmit("recallingForm",
											"recalling_")) {
										$("#buttonAdd1")
												.prop('disabled', false);
									}
									$("#buttonAdd1").prop('disabled', false);
									
								});
						
					if ("<s:property value='command'/>" == "update") {
							kencode = '<s:property value="recalling.kenyaTraceCode"/>';
							kenid = '<s:property value="recalling.shipment.id"/>';
							if($("#recEntity").val()==null || $("#recEntity").val().trim()==''){
							   	 $('#recEntity').append(new Option(kencode, kenid));
							 	$("#recEntity").val(kenid).trigger('change');	
							   	}
							var recdDate='<s:property value="recDate"/>';
							$( "#recDate" ).datepicker(

									{
									
								  // format: 'mm/dd/yyyy',
						          startDate: recdDate,
						           autoclose: true,
									beforeShow : function()
									{
									jQuery( this ).datepicker({ maxDate: recdDate });
									},
									changeMonth: true,
									changeYear: true

									}

									);
						}else{
							$( "#recDate" ).datepicker(

									{
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
						
						$("#shipmentInfoTable").addClass("hide");
						if ("<s:property value='command'/>" == "update") {
						loadShipmentDetail("<s:property value='recalling.shipment.id'/>")
						$("#shipmentInfoTable").removeClass("hide");
						saveshipmentdetails();
						}
						
						$("#recDate").focusin(function(){ 
							 var createdDate = $(".createdDate").text();
							 saveshipmentdetails();
							 if(!isEmpty($("#selectedshipmentDetail").val()) && command == 'create'){
								 if(confirm('<s:property value="%{getLocaleProperty('reset.resetListDataRecalling')}" />')){
									 $("#shipmentContent").empty();
									 $("#recEntity").val(" ").change();
									 $('#recDate').blur();
						      }   else{
						    	 $('#recDate').blur();
						    	 setTimeout(closeDatepickernew, 400);
						     }
							 }
							});
					});
	
	function loadShipmentDetail(val){
		$("#buyername").val('');
		$("#exportername").val('');
	$('.bydet').addClass("hide");
		if (!isEmpty(val)) {
			var id="<s:property value='recalling.id'/>";
			$.ajax({
				type : "POST",
				async : false,
				url : "recalling_populateShipmentdetail.action",
				data : {
					selectedBatchno : val,
					id:id
				},
				success : function(result) {
					$("#shipmentInfoTable").removeClass("hide");
					$("#shipmentContent").empty();
					if(result != null){
					$("#shipmentInfoTable").show();
					}else{
						$("#shipmentInfoTable").hide();
					}
					if(result.procurementGrades.length<=0){
						$("#shipmentInfoTable").hide();
					}else{
						$("#shipmentInfoTable").show();
					}
					
					$("#buyername").val(result.custname);
					$("#shipmentDestination").val(result.shipmentDestination);
					$("#exportername").val(result.exporter);
					$('.bydet').removeClass("hide");
					$(".farmexportdetail").removeClass("hide");
					
					/* var selectedrecall = '<s:property value="selectedrecallingdetail"/>'; */
					for(var i=0;i<result.procurementGrades.length;i++){
						var rowCounter = $('#cultiCharBody tr').length-1;
						var columnCounter = $('#cultiCharBody tr').length-1;
						var tableRow="<tr id=row"+(++rowCounter)+" class=impr "+ result.procurementGrades[i].id +">";
					/* tableRow+="<td class='hide product'>"+result.procurementGrades[i].prodCat+"</td>";
					tableRow+="<td class='hide prodCat'>"+result.procurementGrades[i].product+"</td>";
					tableRow+="<td class='hide unit'>"+result.procurementGrades[i].unit+"</td>"; */
					tableRow+="<td>"+result.procurementGrades[i].farmername+"</td>";
					
					tableRow+="<td>"+result.procurementGrades[i].lotid+"</td>";
					tableRow+="<td>"+result.procurementGrades[i].unit+"</td>";
					tableRow+="<td>"+result.procurementGrades[i].blockname+"</td>";
					tableRow+="<td>"+result.procurementGrades[i].planting+"</td>";
					tableRow+="<td>"+result.procurementGrades[i].prodCat+"</td>";
					tableRow+="<td class='otherProdCat1'>"+result.procurementGrades[i].product+"</td>";
					tableRow+="<td id=createdDate"+i+">"+result.procurementGrades[i].createdDate+"</td>";
					/* tableRow+="<td>"+result.procurementGrades[i].product1+"</td>"; */
					tableRow+="<td class='scientificName'>"+result.procurementGrades[i].scientific+"</td>";
					tableRow+="<td class='quantity'>"+result.procurementGrades[i].quantity+"</td>";
						if(result.procurementGrades[i].status == 1){
							tableRow+="<td><input type='checkbox' name='selector[]' id=createdDates"+i+" checked type='checkbox' onclick='saveshipmentdetails();checkCreatedDatevalidation("+i+")' value='"+result.procurementGrades[i].id+"'></td>";
							
						}else{
							tableRow+="<td><input type='checkbox' name='selector[]' id=createdDates"+i+" type='checkbox' onclick='saveshipmentdetails();checkCreatedDatevalidation("+i+")' value='"+result.procurementGrades[i].id+"'></td>";
						}
					
					tableRow+="</tr>";
				
						jQuery("#shipmentContent").append(tableRow);
						}
				},
				 
			});
		}else{
			$("#shipmentContent").empty();
			$(".farmexportdetail").addClass("hide");
		}
		
	}
	
	function saveshipmentdetails(){
	        var val = [];
	        $(':checkbox:checked').each(function(i){
	          val[i] = $(this).val();
	        });
	        $("#selectedshipmentDetail").val(val);
	      }
	
	function checkCreatedDatevalidation(val){
		$("#validateError").text('');
		var recallingDate1=$('#recDate').val();
		var value='createdDate';
		var vallue='createdDates';
		value+=val;
		vallue+=val;
		var createdDate1=$('#'+value).text();
		const createdDate = moment(createdDate1, 'DD-MM-YYYY').valueOf()
        const recallingDate = moment(recallingDate1, 'DD-MM-YYYY').valueOf()
        var flag=moment(createdDate).isBefore(recallingDate);
		if(createdDate>recallingDate){
			$("#validateError").text('<s:property value="%{getLocaleProperty('invalid.createdNpackingdaterecalling')}" />');
			$('#'+vallue).prop('checked', false);
		}
	 }
	
	
	function resetList(){
		 var createdDate = $(".createdDate").text();
		 if(!isEmpty($("#selectedshipmentDetail").val())){
			 if(confirm('<s:property value="%{getLocaleProperty('reset.resetListDataRecalling')}" />')){
				 $("#shipmentContent").empty();
				 $("#recEntity").val(" ").change();
	      }  
		 }
		}
	function closeDatepickernew(){
		 $(".datepicker").hide();
	}
	
	
</script>
<body>
	<s:form name="recallingForm" cssClass="fillform" method="post"
		action="recalling_%{command}" id="recallingForm" enctype="multipart/form-data">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:hidden name="recalling.id" id="id" />

		<div class="appContentWrapper marginBottom">
			<div class="error">
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.recalling" />
				</h2>
				<s:if test='"update".equalsIgnoreCase(command)'>

					<div class="flexform-item ">
						<label for="txt"><s:property
								value="%{getLocaleProperty('recalling.treacibilitycode')}" /> </label>
						<div class="form-element">
							<s:property value="recalling.kenyaTraceCode" />
							-
							<s:property value="recalling.batchNo" />
							<s:hidden name="recalling.shipment.id">
							</s:hidden>
						</div>
					</div>
				</s:if>
				<s:else>


					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('recalling.treacibilitycode')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:select class="form-control select2" id="recEntity"
								name="recalling.shipment.id" listKey="key" listValue="value"
								list="treacibilitycodeList" headerKey=" "
								headerValue="%{getText('txt.select')}"
								onchange="loadShipmentDetail(this.value)" />
						</div>
					</div>

				</s:else>

				<div class="flexform-item bydet hide">
					<label for="txt"> <s:property
							value="%{getLocaleProperty('Buyer')}" />
					</label>
					<div class="form-element">
						<s:textfield id="buyername" theme="simple"
							cssClass="form-control input-sm" readonly="true" />
					</div>
				</div>
				<div class="flexform-item bydet hide">
					<label for="txt"><s:text
							name="%{getLocaleProperty('shipment.shipmentDestination')}" /></label>
					<div class="form-element">
						<s:textfield name="recalling.shipmentDestination" id="shipmentDestination" 
							theme="simple" class="form-control" readonly="true" />
					</div>
				</div>
				<div class="flexform-item bydet hide">
					<label for="txt"> <s:property
							value="%{getLocaleProperty('Exporter')}" />
					</label>
					<div class="form-element">
						<s:textfield id="exportername" theme="simple"
							cssClass="form-control input-sm" readonly="true" />
					</div>
				</div>
<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('recalling.recDate')}" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="recDate" id="recDate" theme="simple"
								maxlength="10"
								data-date-format="%{getGeneralDateFormat().toLowerCase()}"
								size="20" cssClass="form-control input-sm"
								 />
						</div>
					</div>

<div class="" style="overflow: auto;">
				<table class="table table-bordered aspect-detail"
					id="shipmentInfoTable">
					<thead>
						<tr>
							<th>Farmer Name</th>

							<th><s:property
									value="%{getLocaleProperty('shipment.lotNo')}" /></th>
							<th>Block Id</th>
							<th>Block Name</th>
							<th>Planting</th>
							<th><s:property
									value="%{getLocaleProperty('shipment.product')}" /></th>
							<th><s:property
									value="%{getLocaleProperty('shipment.variety')}" /></th>
							<th><s:property value="%{getLocaleProperty('incomingDate')}" /></th>

							<th><s:property
									value="%{getLocaleProperty('shipment.packingUnit')}" /></th>
							<th><s:property
									value="%{getLocaleProperty('shipment.packingQty')}" /></th>
							<th style="text-align: center"><s:property
									value="%{getLocaleProperty('action')}" /><sup
								style="color: red;">*</sup></th>
						</tr>
					</thead>
					<tbody id="shipmentContent">
						<s:iterator value="recalling.recallDetails" status="incr"
							id="ShipmentDetailse">
							<tr id="row<s:property	value="#incr.index" />">
								<td class="lotNo"><s:property
										value="farmcrops.farm.farmer.firstName" /></td>
								<td class="lotNo"><s:property value="batchNo" /></td>
								<td class="lotNo"><s:property value="farmcrops.blockName" /></td>
								
								<td class="planting"><s:property value="planting.plantingId" /></td>
								
								<td class="product"><s:property
										value="farmcrops.variety.procurementProduct.name" /></td>
								<td class="variety"><s:property
										value="farmcrops.variety.name" /></td>
								<td class="createdDate"><s:date name="shipmentdetail.shipment.shipmentDate" format="dd-MM-yyyy"/></td>
								<td class="packingUnit"><s:property
										value="%{getCatalgueNameByCode(receivedUnits)}" /></td>
								<td class="packingQty"><s:property value="receivedWeight" /></td>


							</tr>
						</s:iterator>
					</tbody>
				</table>
	</div>
				<div class="flexform">
					
					<div class="flexform-item ">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('recalling.recEntity')}" /><sup
							style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:select class="form-control select2" id="recEntity"
								name="recalling.recEntity" listKey="key" listValue="value"
								list="recEntityList" headerKey=" "
								headerValue="%{getText('txt.select')}"
								onchange="saveshipmentdetails()" />
						</div>
					</div>

					<div class="flexform-item hide" id="exporterDivId">
						<label for="txt"><span id="entitylabelName"></span><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2" list="{}" headerKey=""
								theme="simple" name="recalling.exporterid"
								headerValue="%{getText('txt.select')}" id="exporter" />
						</div>
					</div>


					<div class="flexform-item">
						<label for="txt"><s:text
								name="recalling.recCoordinatorName" /><sup style="color: red;">*</sup>
						</label>
						<div class="form-element">
							<s:textfield name="recalling.recCoordinatorName" theme="simple"
								id="recCoordinatorName" cssClass="lowercase form-control"
								maxlength="255" />

						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text
								name="recalling.recCoordinatorContact" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="recalling.recCoordinatorContact"
								theme="simple" id="recCoordinatorContact"
								cssClass="lowercase form-control" maxlength="255"
								onkeypress="return isNumber(event)" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="recalling.recNature" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:select class="form-control select2" list="natureOfRecall"
								headerKey="" theme="simple" name="recalling.recNature"
								headerValue="%{getText('txt.select')}" id="recNature" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="recalling.recReason" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textarea name="recalling.recReason" theme="simple"
								id="recReason" cssClass="lowercase form-control" maxlength="255" />
						</div>
					</div>
					
					<div class="flexform-item">
					<label for="txt"><s:text name="recall.evidence" />&nbsp;&nbsp;<font
						color="red"><s:text
								name="%{getLocaleProperty('imageValidation')}" /></font></label>
					<div class="form-element">
						<s:file name="files" id="files" cssClass="form-control"
							onchange="setFiles(this.value);validateImage(this.id,['png', 'jpg', 'jpeg','JPG','JPEG','PNG','pdf','PDF'])" />
						<s:hidden name="fphoto" id="fphoto" />
						<s:if test="command=='update'">
							<s:if test="recalling.attachment!=null">

								<button type="button" class="fa fa-download"
									onclick="popDownload(<s:property value="recalling.attachment"/>)">
							</s:if>
						</s:if>


					</div>
				</div>
				</div>
			</div>

			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.operatorInfo" />
				</h2>
				<div class="flexform">
					<div class="flexform-item">
						<label for="txt"><s:text name="recalling.operatorName" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:textfield name="recalling.operatorName" theme="simple"
								id="operatorName" cssClass="lowercase form-control"
								maxlength="255" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="recalling.contactNo" /></label>
						<div class="form-element">
							<s:textfield name="recalling.contactNo" theme="simple"
								id="contactNo" cssClass="lowercase form-control"
								onkeypress="return isNumber(event)" maxlength="255" />
						</div>
					</div>
				</div>
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.shippingInfo" />
				</h2>
				<div class="flexform">
					<div class="flexform-item">
						<label for="txt"><s:text name="recalling.po" /></label>
						<div class="form-element">
							<s:textfield name="recalling.po" theme="simple" id="po"
								cssClass="lowercase form-control" maxlength="255" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="recalling.invoiceNo" /></label>
						<div class="form-element">
							<s:textfield name="recalling.invoiceNo" theme="simple"
								id="invoiceNo" cssClass="lowercase form-control" maxlength="255" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="recalling.carrierNo" /></label>
						<div class="form-element">
							<s:textfield name="recalling.carrierNo" theme="simple"
								id="carrierNo" cssClass="lowercase form-control" maxlength="255" />
						</div>
					</div>
					<%-- <div class="flexform-item">
						<label for="txt"><s:text name="recalling.productId" /></label>
						<div class="form-element">
							<s:textfield name="recalling.productId" theme="simple"
								id="productId" cssClass="lowercase form-control" readonly="true" />
						</div>
					</div> --%>
					<div class="flexform-item">
						<label for="txt"><s:text name="recalling.actionByRecaller" /></label>
						<div class="form-element">
							<s:textarea name="recalling.actionByRecaller" theme="simple"
								id="actionByRecaller" cssClass="lowercase form-control"
								maxlength="255" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text
								name="recalling.actionByStakeholders" /></label>
						<div class="form-element">
							<s:textarea name="recalling.actionByStakeholders" theme="simple"
								id="actionByStakeholders" cssClass="lowercase form-control"
								maxlength="255" />
						</div>
					</div>
				</div>
			</div>
			<s:hidden name="selectedshipmentdetail" id="selectedshipmentDetail" />
			<div class="flex-layout flexItemStyle">
				<div class="margin-top-10">
					<span class=""><span class="first-child">
							<button type="button" id="buttonAdd1"
								class="save-btn btn btn-success">
								<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
								</font>
							</button>
					</span></span> <span id="cancel" class=""><span class="first-child"><button
								type="button" class="cancel-btn btn btn-sts"
								onclick="onCancel();">
								<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
								</font></b>
							</button></span></span>
				</div>
			</div>
		</div>
	</s:form>
	
	<s:form id="fileDownload" action="recalling_populateDownload">
		<s:hidden id="loadId" name="idd" />
	</s:form>
</body>
