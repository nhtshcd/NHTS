<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<META name="decorator" content="swithlayout">
</head>
<script type="text/javascript">
	$(document).ready(function() {
	
	});

</script>
<body>

<s:form name="form" cssClass="fillform" id="seedForm"
		 enctype="multipart/form-data">
			<div class="appContentWrapper marginBottom">
			<div class="error">
				<s:actionerror />
				<s:fielderror />
				<s:if test="hasActionErrors()">
					<div style="color: red;">
						<s:text name="cannotDeletefumigatorCommercialApplicatorHasTxn" />
						<s:actionerror />

					</div>
				</s:if>
				<div id="validateError" style="text-align: left;"></div>
			</div>
			<div class="formContainerWrapper">
				<h2>
					<s:text name="info.seed" />
				</h2>
				
				</div>
				</div>
				
		</s:form>
</body>