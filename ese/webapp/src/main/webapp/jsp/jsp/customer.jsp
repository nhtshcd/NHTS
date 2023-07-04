<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<%-- <%@ include file="/jsp/common/detail-assets.jsp"%> --%>
<%@ include file="/jsp/common/grid-assets.jsp"%>

<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<body>

	<s:form name="customerform" cssClass="fillform"
		action="customer_%{command}" id="customerform">
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="customer.id" />
		</s:if>
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden key="command" />
		<div class="appContentWrapper marginBottom">

			<%-- <div class="error">
				<sup>*</sup>
				<s:text name="reqd.field" />
				<s:actionerror />
				<s:fielderror />
			</div> --%>

			<div class="error">
				<div id="validateError" style="text-align: left;"></div>
				<sup>*</sup>
				<s:text name="reqd.field" />
			</div>

			<div class="formContainerWrapper">
				<h2>
					<s:property value="%{getLocaleProperty('info.customer')}" />
				</h2>
				<div class="flexiWrapper">


<div class="flexi flexi10">
						<label for="txt"><s:text name="agent.exporterLs" /> <sup
                                    							style="color: red;">*</sup></label>

                                        						<div class="form-element">
                                        							<s:if test="customer.exporter!=null && customer.exporter.id>0">
                                        								<s:property value="customer.exporter.name" />
                                        								<s:hidden name="customer.exporter.id"></s:hidden>
                                        							</s:if>
                                        							<s:else>
                                        								<s:select name="customer.exporter.id" id="selectRole"
                                        									list="exporterList" listKey="key" listValue="value"
                                        									theme="simple" headerKey=""
                                        									headerValue="%{getText('txt.select')}"
                                        									cssClass="form-control input-sm select2" />
                                        							</s:else>
					</div>
</div>

					<s:if test='"update".equalsIgnoreCase(command)'>
						<div class="flexi flexi10">
							<label for="txt"><s:text name="customer.customerId" /> </label>
							<div class="form-element">
								<s:property value="customer.customerId" />
								<s:hidden key="customer.customerId" />
							</div>
						</div>
						<%-- <s:if test='branchId==null'>
								<div class="flexi flexi10">
									<label for="txt"><s:text name="app.branch" />
									</label>
									<div class="form-element">
										<s:property value="%{getBranchName(customer.branchId)}" />
									</div>
								</div>
							</s:if> --%>
					</s:if>
					<div class="flexi flexi10">
						<label for="txt"><s:text name="customer.customerName" />
							<sup style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield cssClass="form-control input-sm"
								name="customer.customerName" theme="simple" maxlength="45" />
						</div>
					</div>

					 <div class="flexi flexi10">
						<%-- <label for="txt"><s:property
								value="%{getLocaleProperty('customer.customerType')}" /></label>
						<div class="form-element">
							<s:select name="customer.customerType"
								cssClass="col-sm-4 form-control select2"
								list="listOfCustomerTypes" listKey="key" listValue="value"
								headerKey="" theme="simple"
								headerValue="%{getText('txt.select')}" />
						</div> --%>
					</div>
				</div>

				<h2>Location Information</h2>
				<div class="flexiWrapper">
				
				
			<%-- 		<div class="flexi flexi10">
						<label for="txt"> <s:text name="country.name" />
						</label>
						<div class="form-element">
							<s:select name="selectedCountry" list="countries" Key="code"
								Value="name" headerKey="" headerValue="%{getText('txt.select')}"
								theme="simple" id="country" onchange="listState(this)"
								cssClass="col-sm-4 form-control select2" />
						</div>
					</div>

					<div class="flexi flexi10">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('state.name')}" /> <sup
							style="color: red;"></sup>
						</label>
						<div class="form-element">
							<s:select name="selectedState" list="states" Key="code"
								Value="name" headerKey="" headerValue="%{getText('txt.select')}"
								theme="simple" id="state" onchange="listLocality(this)"
								cssClass="col-sm-4 form-control select2" />
						</div>
					</div>

					<div class="flexi flexi10">
						<label for="txt"><s:property
								value="%{getLocaleProperty('locality.name')}" /> </label>
						<div class="form-element">
							<s:select name="customer.locationDetail.name" id="localites"
								list="localities" Key="code" Value="name" headerKey=""
								headerValue="%{getText('txt.select')}" theme="simple"
								onchange="populateTaluks(this)"
								cssClass="col-sm-4 form-control select2" />
						</div>
					</div>


					<div class="flexi flexi10">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('city.name')}" />
						</label>
						<div class="form-element">
							<s:select name="customer.city.name" id="taluk"
								list="municipalities" Key="code" Value="name" headerKey=""
								headerValue="%{getText('txt.select')}" theme="simple"
								cssClass="col-sm-4 form-control select2" />
						</div>
					</div> --%>
					
					
						<div class="flexi flexi10">
						<label for="txt"><s:text name="country.name" /><sup style="color: red;">*</sup></label>
						<div class="form-element">
							<%-- <s:select class="form-control  select2" list="countries" headerKey=""
								  theme="simple" name="selectedCountry" maxlength="20"	headerValue="%{getText('txt.select')}" Key="id" Value="name"	 						 
								onchange="listState(this,'country','state','localities','city','panchayath','village');"
								id="country" /> --%>
							<s:textfield cssClass="form-control input-sm"
								name="customer.country" theme="simple" />
						</div>
					</div>
					<div class="flexi flexi10">
						<label for="txt"><s:text name="county.name" /><sup style="color: red;">*</sup></label>
						<div class="form-element">
							<%-- <s:select class="form-control  select2" list="states"
								headerKey="" theme="simple" name="selectedState"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="state" maxlength="20"
								onchange="listLocality(this,'state','localities','city','panchayath','village');" /> --%>
							<s:textfield cssClass="form-control input-sm"
								name="customer.county" theme="simple" />
						</div>
					</div>

					<div class="flexi flexi10">
						<label for="txt"><s:text name="subcountry.name" /><sup style="color: red;">*</sup></label>
						<div class="form-element">
							<%-- <s:select class="form-control  select2" list="localities"
								headerKey="" theme="simple" name="selectedLocality"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="localities" maxlength="20"
								onchange="listMunicipalities(this,'localities','city','panchayath','village');" /> --%>
						  <s:textfield cssClass="form-control input-sm"
								name="customer.subCounty" theme="simple" />
						</div>
					</div>

					<div class="flexi flexi10">
						<label for="txt"><s:text name="ward.name" /><sup style="color: red;">*</sup></label>
						<div class="form-element">
							<%-- <s:select class="form-control  select2" list="city" headerKey=""
								theme="simple" name="selectedCity" maxlength="20"
								headerValue="%{getText('txt.select')}" Key="id" Value="name"
								id="city"  /> --%>
						<s:textfield cssClass="form-control input-sm"
								name="customer.ward" theme="simple" />
						</div>
					</div>
					

					<div class="flexi flexi10">
						<label for="txt"> <s:text name="customer.customerAddress" />
						</label>
						<div class="form-element">
							<s:textarea name="customer.customerAddress" maxLength="255"
								cssClass="form-control input-sm" />
						</div>
					</div>
				</div>

				<h2>Personal Information</h2>
				<div class="flexiWrapper">
					<div class="flexi flexi10">
						<label for="txt"> <s:text name="customer.personName" />
						</label>
						<div class="form-element">
							<s:textfield cssClass="form-control input-sm"
								name="customer.personName" theme="simple" maxlength="90" />
						</div>
					</div>

					<%-- <div class="flexi flexi10">
						<label for="txt"> <s:property
								value="%{getLocaleProperty('customer.customerSegment')}" />
						</label>
						<div class="form-element">
							<s:select name="customer.customerSegment"
								cssClass="col-sm-4 form-control select2"
								list="listOfCustomerSegment" listKey="key" listValue="value"
								headerKey="" theme="simple"
								headerValue="%{getText('txt.select')}" />
						</div>
					</div> --%>

					<div class="flexi flexi10">
						<label for="txt"> <s:text name="customer.mobileNumber" />
						</label>
						<div class="form-element">
							<s:textfield cssClass="form-control input-sm"
								name="customer.mobileNo" theme="simple" maxlength="10" />
						</div>
					</div>

					<div class="flexi flexi10">
						<label for="txt"> <s:text name="customer.email" />
						</label>
						<div class="form-element">
							<s:textfield cssClass="form-control input-sm"
								name="customer.emailId" theme="simple" maxlength="45" />
						</div>
					</div>

					<%-- <div class="flexi flexi10">
						<label for="txt"> <s:text name="customer.landLine" />
						</label>
						<div class="form-element">
							<s:textfield cssClass="form-control input-sm"
								name="customer.landline" theme="simple" maxlength="10" />
						</div>
					</div> --%>

				</div>
			</div>
			</br>
			<div class="yui-skin-sam">
				<%-- <s:if test="command =='create'"> --%>
				<span class=""><span class="first-child">
						<button type="button" id="buttonAdd1"
							class="save-btn btn btn-success">
							<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
							</font>
						</button>
				</span></span>
				<%-- </s:if> --%>
				<%-- <s:else>
						<span class=""><span class="first-child">
							<button type="button" id="buttonAdd1"
								class="save-btn btn btn-success">
								<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
								</font>
							</button>
					</span></span>
				</s:else> --%>
				 <span id="cancel" class=""><span class="first-child"><button
								type="button" class="cancel-btn btn btn-sts"
								onclick="onCancel();">
								<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
								</font></b>
							</button></span></span>
			</div>

		</div>



	</s:form>
	<s:form name="cancelform" action="customer_list.action">
		<s:hidden key="currentPage" />
	</s:form>

	<script type="text/javascript">
	var url =<%out.print("'" + request.getParameter("url") + "'");%>;
		$(document).ready(function() {
			
			$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
			if (url == null || url == undefined || url == ''|| url == 'null') {
				url = 'customer_';
			}
			
			
			$("#buttonAdd1").on('click', function(event) {
				event.preventDefault();
				$("#buttonAdd1").prop('disabled', true);
				if (!validateAndSubmit("customerform", "customer_")) {
					$("#buttonAdd1").prop('disabled', false);
				}
				$("#buttonAdd1").prop('disabled', false);
			});

		});

		//	function onSubmit() {
		//			document.form.submit();
		//	}
		
		function onCancel() {
			window.location.href = "<s:property value='redirectContent'/>";
	}

		/* function listState(obj) {
			var selectedCountry = $('#country').val();
			jQuery.post("customer_populateState.action", {
				id : obj.value,
				dt : new Date(),
				selectedCountry : obj.value
			}, function(result) {
				insertOptions("state", JSON.parse(result));
				listLocality(document.getElementById("state"));
			});
		} */

		/* function listLocality(obj) {
			var selectedState = $('#state').val();
			jQuery.post("customer_populateLocality.action", {
				id : obj.value,
				dt : new Date(),
				selectedState : obj.value
			}, function(result) {
				insertOptions("localites", JSON.parse(result));
				populateTaluks(document.getElementById("localites"));
			});
		} */
		/* function populateTaluks(obj) {
			var selectedDistrict = $("#localites").val();
			jQuery.post("customer_populateTaluks", {
				selectedDistrict : selectedDistrict
			}, function(result) {
				insertOptions("taluk", JSON.parse(result));
			});
		} */
	</script>
</body>