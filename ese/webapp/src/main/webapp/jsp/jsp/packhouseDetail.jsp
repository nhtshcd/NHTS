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
	<s:form name="packhouse" cssClass="fillform" method="post"
		action="packhouse_%{command}" id="packhouse">
		<s:hidden name="redirectContent" id="redirectContent" />
		<s:hidden name="currentPage" id="currentPage" />
		<s:hidden name="command" id="command" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden name="packhouse.id" />
		</s:if>
		<div class="appContentWrapper marginBottom">
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.customer" />
				</h2>
				<div class="flexform">
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('coOperative.code')}" />
						</p>
						<p class="flexItem">
							<s:property value="ware.code" />
						</p>
					</div>
				
			</div>
			
				<h2>
					<s:text name="info.location" />
				</h2>
				<div class="flexform">
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('coOperative.name')}" />
						</p>
						<p class="flexItem">
							<s:property value="ware.name" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('coOperative.location')}" />
						</p>
						<p class="flexItem">
							<s:property value="ware.location" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('farmer.latitude')}" />
						</p>
						<p class="flexItem">
							<s:property value="ware.latitude" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('farmer.longitude')}" />
						</p>
						<p class="flexItem">
							<s:property value="ware.longitude" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('coOperative.address')}" />
						</p>
						<p class="flexItem">
							<s:property value="ware.address" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('coOperative.phoneNo')}" />
						</p>
						<p class="flexItem">
							<s:property value="ware.phoneNo" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('ware.exporterLs')}" />
						</p>
						<p class="flexItem">
							<s:property value="ware.exporter.name" />
						</p>
					</div>
			</div>		
			
				
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