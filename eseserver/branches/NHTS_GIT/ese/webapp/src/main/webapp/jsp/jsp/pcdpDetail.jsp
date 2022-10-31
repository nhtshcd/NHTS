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
	function onCancel() {
		window.location.href = "<s:property value='redirectContent'/>";
	}
</script>
<body>
	<s:form name="form" cssClass="fillform" method="post"
		action="pcbp_%{command}" id="form">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="pcbp.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.pcbp" />
				</h2>
				
				<div class="flexform">
					<%-- <div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('pcbp.cropcat')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.cropcat" />
						</p>
					</div> --%>
				
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.fcropName')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.cropvariety.procurementVariety.name" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.cropvariety')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.cropvariety.name" />
						</p>
					</div>
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.tradeName')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.tradeName" />
						</p>
					</div>
				
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.registrationNo')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.registrationNo" />
						</p>
					</div>
					
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.activeing')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.activeing" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.manufacturerReg')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.manufacturerReg" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('pcbp.agent')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.agent" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('pcbp.phiIn')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.phiIn" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.dosage')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.dosage" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.uom')}" />
						</p>
						<p class="flexItem">
							<%-- <s:property value="pcbp.uom" /> --%>
							<s:property value="%{getCatalgueNameByCode(pcbp.uom)}" />
						</p>
					</div>
				<%-- 	<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.chemicalName')}" />
						</p>
						<p class="flexItem">
							<s:property value="pcbp.chemicalName" />
							<s:property value="%{getCatalgueNameByCode(pcbp.chemicalName)}" />
						</p>
					</div> --%>
				</div>
			</div>

			<div class="flex-layout flexItemStyle">
				<div class="margin-top-10">
					<span id="cancel" class=""><span class="first-child"><button
								type="button" class="cancel-btn btn btn-sts"
								onclick="onCancel();">
								<b><FONT color="#FFFFFF"><s:text name="back.button" />
								</font></b>
							</button></span></span>
				</div>
			</div>
		</div>
	</s:form>
</body>
