<%@ include file="/jsp/common/grid-assets.jsp"%>
	<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<head>
<meta name="decorator" content="publiclayout">
</head>
<script>
	jQuery(document).ready(function() {
		var cVal1 = '<s:property value="conformValue" />';				
		if (!isEmpty(cVal1)) {
			 ConfirmModalDelete( "<i>Exporter Module</i>","<s:property value="%{getLocaleProperty('exporterRegistrationSuccess.msg')}" />",onCancel,"<u>Ok</u>");
		}
	});
	function onCancel(){
		window.location.href="<s:property value="redirectContent" />";
	}


</script>

<body>

</body>