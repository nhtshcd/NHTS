<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#groupFormationCalendar").datepicker(
                {
                    changeMonth: true,
                    changeYear: true,
                    showButtonPanel: true,
                    dateFormat: 'MM yy',
                    onClose: function (dateText, inst) {
                        $(this).datepicker(
                                'setDate',
                                new Date(inst.selectedYear, inst.selectedMonth,
                                        1));
                    }
                });
	});
	function validate() {
			document.form.submit();
	}
</script>

<body>
	<s:form name="form" cssClass="fillform" action="samithi_%{command}">
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:hidden id="samithiId" key="warehouse.id" class="uId"/>
		<s:hidden id="errorId" key="errorMsg" />
		<s:hidden key="command" />
			<s:hidden id="jsonString" name="warehouse.jsonString" />


		<div class="appContentWrapper marginBottom">

			<div class="error ferror">
				<sup>*</sup>
				<s:text name="reqd.field" />
				<s:actionerror />
				<s:fielderror />
			</div>

			<div class="formContainerWrapper">
			
				
					<h2>
						<s:property value="%{getLocaleProperty('info.samithi')}" />
					</h2>
				
				<div class="flexiWrapper">
					<s:if test='"update".equalsIgnoreCase(command)'>
						<div class="flexform-item">
							<label for="txt"><s:text name="samithi.code" /><sup
								style="color: red;">*</sup> </label>
							<div class="form-element">
								<s:property value="warehouse.code" />
								<s:hidden key="warehouse.code" />
							</div>
						</div>
					</s:if>
					<s:if test='branchId==null'>
						<div class="flexform-item">
							<label for="txt"><s:text name="app.branch" /> </label>
							<div class="form-element">
								<s:property value="%{getBranchName(warehouse.branchId)}" />
							</div>
						</div>
					</s:if>
					
					<div class="flexform-item">
						<label for="txt"><s:property
								value="%{getLocaleProperty('samithi.name')}" /> </label>
						<div class="form-element showAge">
							<s:textfield id="samithiName" name="warehouse.name"
								theme="simple" maxlength="45" cssClass="form-control" />
						</div>
					</div>

				<div class="flexform-item">
							<label for="txt"><s:property
									value="%{getLocaleProperty('dateOfFormation')}" /></label>
							<div class="form-element">
							<s:textfield data-date-format="dd/mm/yyyy"
									data-date-viewmode="years"
									cssClass="date-picker form-control input-sm" id="fromCalendar"
									name="formationDate" size="23" readonly="true" />
							</div>
						</div>
		   </div>
        </div>
		</div>

		<div class="margin-top-10">
			<s:if test="command =='create'">
				<span class=""><span class="first-child">
						<button type="button" class="save-btn btn btn-success"
							onclick="validate()">
							<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
							</font>
						</button>
				</span></span>
			</s:if>
			<s:else>
				<span class=""><span class="first-child">
						<button type="button" onClick="validate()"
							class="save-btn btn btn-success">
							<font color="#FFFFFF"> <b><s:text name="update.button" /></b>
							</font>
						</button>
				</span></span>
			</s:else>
			<span id="cancel" class=""><span class="first-child"><button
						type="button" class="cancel-btn btn btn-sts">
						<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
						</font></b>
					</button></span></span>
		</div>

	</s:form>
	<s:form name="cancelform" action="samithi_list.action">
		<s:hidden key="currentPage" />
	</s:form>

</body>