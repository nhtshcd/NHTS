<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<script>
	var url =
<%out.print("'" + request.getParameter("url") + "'");%>
	;
	jQuery(document)
			.ready(
					function() {

						url =
<%out.print("'" + request.getParameter("url") + "'");%>
	;
						if (url == null || url == undefined || url == ''
								|| url == 'null') {
							url = 'packhouse_';
						}
						$(".breadCrumbNavigation").find('li:last').find(
								'a:first').attr("href",
								'<s:property value="redirectContent" />');
						$("#buttonAdd1,#buttonUpdate").on(
								'click',
								function(event) {
									event.preventDefault();
									$("#buttonAdd1").prop('disabled', true);
									if (!validateAndSubmit("target", url)) {
										$("#buttonAdd1")
												.prop('disabled', false);
										$("#buttonUpdate").prop('disabled',
												false);
									}
								});
					});
	function onCancel() {
		window.location.href = "<s:property value="redirectContent" />";
	}
</script>
<head>
<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType") : "swithlayout"%>">
<title>Insert title here</title>
</head>
<body>

	<s:form name="form" cssClass="fillform" method="post" id="target">
		<s:hidden name="redirectContent" id="redirectContent" />

		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden key="ware.id" />
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
					<s:property value="%{getLocaleProperty('info.coOperative')}" />
				</h2>
				<div class="flexform">
					<s:if test='"update".equalsIgnoreCase(command)'>
						<div class="flexform-item hide">
							<label for="txt"><s:text name="coOperative.code" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:property value="ware.code" />
								<s:hidden key="ware.code" />
							</div>
						</div>
					</s:if>
					<s:if test='"update".equalsIgnoreCase(command)'>
						<div class="flexform-item">
							<label for="txt"><s:text name="coOperative.code" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="ware.code" theme="simple" maxlength="3"
									cssClass="form-control input-sm" disabled="true"/>
							</div>
						</div>

					</s:if>
					<s:else>
						<div class="flexform-item">
							<label for="txt"><s:text name="coOperative.code" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="ware.code" theme="simple" maxlength="3"
									cssClass="form-control input-sm" />
							</div>
						</div>
					</s:else>

					<div class="flexform-item">
						<label for="txt"><s:text name="coOperative.name" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="ware.name" theme="simple" maxlength="45"
								cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="coOperative.location" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="ware.location" theme="simple" maxlength="45"
								cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="coOperative.phoneNo" /></label>
						<div class="form-element">
							<s:textfield name="ware.phoneNo" theme="simple" maxlength="10"
								cssClass="form-control input-sm"
								onkeypress="return isNumber(event)" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="farmer.latitude" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="ware.latitude" theme="simple"
								cssClass="form-control input-sm"
								onkeypress="return isDecimal(event)" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="farmer.longitude" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="ware.longitude" theme="simple"
								cssClass="form-control input-sm"
								onkeypress="return isDecimal(event)" />
						</div>
					</div>
					<div class="flexform-item">
						<label for="txt"><s:text name="coOperative.address" /><sup
							style="color: red;">*</sup></label>
						<div class="form-element">
							<s:textfield name="ware.address" theme="simple" maxlength="200"
								cssClass="form-control input-sm" />
						</div>
					</div>
					<%--<div class="flexform-item">
						<label for="txt"><s:text name="coOperative.phoneNo" /></label>
						<div class="form-element">
							<s:textfield name="ware.phoneNo" theme="simple" maxlength="10"
								cssClass="form-control input-sm"
								onkeypress="return isNumber(event)" />
						</div>
					</div>
					 	<div class="flexform-item">
						<label for="txt"><s:text
								name="coOperative.storageCommodity" /></label>
						<div class="form-element">
							<s:select id="commodity" name="ware.commodity"
								list="getCatList(getLocaleProperty('commodityList'))"
								listKey="key" listValue="value" theme="simple" multiple="true"
								cssClass="form-control input-sm select2" />

						</div>
					</div>

					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('coOperative.warehouseOwnerShip')}" /></label>
						<div class="form-element">
							<s:select id="ownerShip" name="ware.warehouseIncharge"
								list="getCatList(getLocaleProperty('ownershipList'))"
								headerKey="" theme="simple"
								headerValue="%{getText('txt.select')}"
								cssClass="form-control input-sm select2" />
						</div>
					</div> --%>
					<div class="flexform-item">
						<label for="txt"><s:text name="ware.exporterLs" /><sup
							style="color: red;">*</sup> </label>
						<div class="form-element">
							<s:if test="ware.exporter!=null && ware.exporter.id>0">
								<s:property value="ware.exporter.name" />
								<s:hidden name="ware.exporter.id"></s:hidden>
							</s:if>
							<s:else>
								<s:select name="ware.exporter.id" id="selectRole"
									list="exporterList" listKey="key" listValue="value"
									theme="simple" headerKey=""
									headerValue="%{getText('txt.select')}"
									cssClass="form-control input-sm select2" />
							</s:else>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div>
			<s:if test="command =='create'">
				<span class=""><span class="first-child">
						<button type="submit" id="buttonAdd1"
							class="save-btn btn btn-success">
							<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
							</font>
						</button>
				</span></span>
			</s:if>
			<s:else>
				<span class=""><span class="first-child">
						<button type="submit" id="buttonUpdate"
							class="save-btn btn btn-success">
							<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
							</font>
						</button>
				</span></span>
			</s:else>
			<span id="cancel" class=""><span class="first-child"><button
						type="button" class="cancel-btn btn btn-sts" onclick="onCancel();">
						<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
						</font></b>
					</button></span></span>
		</div>
	</s:form>
</body>
