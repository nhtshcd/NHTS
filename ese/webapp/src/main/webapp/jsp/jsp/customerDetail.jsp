<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
</head>
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
</head>
<body>
	<s:form name="customer" cssClass="fillform" method="post"
		action="customer_%{command}" id="customer">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="customer.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.customer" />
				</h2>
				<div class="flexform">

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name='exporter' />
						</p>
						<p class="flexItem">
							<s:property value="customer.exporter.name" />
						</p>
					</div>

					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('customer.customerName')}" />
						</p>
						<p class="flexItem">
							<s:property value="customer.customerName" />
						</p>
					</div>
					<%-- <div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('customer.customerType')}" />
						</p>
						<p class="flexItem">
							<s:property value="customer.customerType" />
						</p>
					</div> --%>
				</div>

				<h2>
					<s:text name="info.location" />
				</h2>
				<div class="flexform">


					<%-- 	<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('country.name')}" />
						</p>
						<p class="flexItem">
							<s:property value="selectedCountry" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('state.name')}" />
						</p>
						<p class="flexItem">
							<s:property value="selectedState" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('locality.name')}" />
						</p>
						<p class="flexItem">
							<s:property value="customer.city.locality.name" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('city.name')}" />
						</p>
						<p class="flexItem">
							<s:property value="customer.city.name" />
						</p>
					</div> --%>


					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="country.name" />
						</p>
						<p class="flexItem">
							<%-- <s:property
								value="customer.city.locality.state.country.code" />
							&nbsp-&nbsp
							<s:property
								value="customer.city.locality.state.country.name" /> --%>
							<s:property value="customer.country" />
						</p>
					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="county.name" />
						</p>
						<p class="flexItem">
							<%-- <s:property value="customer.city.locality.state.code" />
							&nbsp-&nbsp
							<s:property value="customer.city.locality.state.name" /> --%>
							<s:property value="customer.county" />
						</p>

					</div>

					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="subcountry.name" />
						</p>
						<p class="flexItem">
							<%-- <s:property value="customer.city.locality.code" />
							&nbsp-&nbsp
							<s:property value="customer.city.locality.name" /> --%>
							<s:property value="customer.subCounty" />
						</p>

					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:text name="ward.name" />
						</p>
						<p class="flexItem">

							<%-- <s:property value="customer.city.code" />
							&nbsp-&nbsp
							<s:property value="customer.city.name" /> --%>
							<s:property value="customer.ward" />
						</p>
					</div>


					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('customer.customerAddress')}" />
						</p>
						<p class="flexItem">
							<s:property value="customer.customerAddress" />
						</p>
					</div>
				</div>

				<h2>
					<s:text name="info.personal" />
				</h2>
				<div class="flexform">
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('customer.personName')}" />
						</p>
						<p class="flexItem">
							<s:property value="customer.personName" />
						</p>
					</div>
					<%-- <div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('customer.customerSegment')}" />
						</p>
						<p class="flexItem">
							<s:property value="customer.customerSegment" />
						</p>
					</div> --%>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('customer.mobileNumber')}" />
						</p>
						<p class="flexItem">
							<s:property value="customer.mobileNo" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('customer.email')}" />
						</p>
						<p class="flexItem">
							<s:property value="customer.emailId" />
						</p>
					</div>
					<%-- <div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('customer.landLine')}" />
						</p>
						<p class="flexItem">
							<s:property value="customer.landline" />
						</p>
					</div> --%>

				</div>
				<s:if test='#session.isAdmin =="true"'>
					<s:iterator value="ex" var="innerList">
						<div class="aPanel audit_history">
							<div class="aTitle">
								<h2>
									<s:if
										test="#innerList[2].toString().trim().equalsIgnoreCase('ADD')">
										<s:property value="#innerList[0].createdUser" />
									</s:if>
									<s:else>
										<s:property value="#innerList[0].updatedUser" />
									</s:else>
									-

									<s:date name="#innerList[1].revisionDate"
										format="dd/MM/yyyy hh:mm:ss" />
									-
									<s:property
										value="%{getLocaleProperty('default'+#innerList[2])}" />
									<div class="pull-right">
										<a class="aCollapse "
											href="#<s:property value="#innerList[1].id" />
										"><i
											class="fa fa-chevron-right"></i></a>
									</div>
								</h2>
							</div>
							<div class="aContent dynamic-form-con"
								id="<s:property value="#innerList[1].id" />">

								<jsp:include page='/jsp/jsp/auditCustomerdetail.jsp' />

							</div>


						</div>
					</s:iterator>
				</s:if>
			</div>

			<div class="flex-layout flexItemStyle">
				<div class="margin-top-10">
					<span id="cancel" class=""><span class="first-child"><button
								type="button" class="cancel-btn btn btn-sts"
								onclick="onCancel();">
								<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
								</font></b>
							</button></span></span>
				</div>
			</div>
		</div>
	</s:form>

</body>
<script type="text/javascript">
	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>";
	}
</script>