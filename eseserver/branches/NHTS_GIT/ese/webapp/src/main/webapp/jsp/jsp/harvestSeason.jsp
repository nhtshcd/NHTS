<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
</head>
<body>

	<style>
.datepicker-dropdown {
	width: 300px;
}
</style>

	<s:form name="form" cssClass="fillform"
		action="harvestSeason_%{command}">
		<s:hidden key="currentPage" />
		<s:hidden key="id" />
		<s:if test='"update".equalsIgnoreCase(command)'>
			<s:hidden key="harvestSeason.id" />
		</s:if>
		<s:hidden key="command" />
		<s:hidden id="fromPeriod" />
		<s:hidden id="toPeriod" />

		<div class="appContentWrapper marginBottom">

          	<div class="error">
				<%-- <sup>*</sup>
				<s:text name="reqd.field" /> --%>
				<s:actionerror />
				<s:fielderror />
			</div> 

			<div class="formContainerWrapper">
				<h2>
					<s:property value="%{getLocaleProperty('info.harvestSeason')}" />
				</h2>
				<div class="flexiWrapper">
					<s:if test='"update".equalsIgnoreCase(command)'>
						<s:if test='branchId==null'>
							<div class="flexi flexi10">
								<label for="txt"><s:text
										name="%{getLocaleProperty('harvestSeasonapp.branch')}" /> </label>
								<div class="form-element">
									<s:property value="%{getBranchName(harvestSeason.branchId)}" />
								</div>
							</div>
						</s:if>
					</s:if>

						<div class="flexi flexi10">
							<label for="txt"><s:text
									name="%{getLocaleProperty('harvestSeason.name')}" />
									<sup style="color: red;">*</sup> </label>
							<div class="form-element">
								<s:textfield name="harvestSeason.name" theme="simple"
									maxlength="35" cssClass="form-control input-sm" />
							</div>
						</div>
			
					   <div class="flexi flexi10">
								<label for="txt"><s:text name="harvestSeason.fromPeriod" /><sup
									style="color: red;">*</sup> </label>
								<div class="form-element">
									<s:textfield data-date-format="dd/mm/yyyy"
										data-date-viewmode="years"
										cssClass="date-picker form-control input-sm" id="fromDate"
										name="fromPeriod" size="23" readonly="true" />
								</div>
							</div>

							<div class="flexi flexi10">
								<label for="txt"><s:text name="harvestSeason.toPeriod" /><sup
									style="color: red;">*</sup> </label>
								<div class="form-element">
									<s:textfield data-date-format="dd/mm/yyyy"
										data-date-viewmode="years"
										cssClass="date-picker form-control input-sm" id="toDate"
										name="toPeriod" theme="simple" size="23" readonly="true" />
								</div>
							</div>
				
				</div>
				<div class="yui-skin-sam">
					<s:if test="command =='create'">
						<span class=""><span class="first-child">
								<button type="button" onclick="onSubmit()"
									class="save-btn btn btn-success">
									<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
									</font>
								</button>
						</span></span>
					</s:if>
					<s:else>
						<span class=""><span class="first-child">
								<button type="button" onclick="onSubmit()"
									class="save-btn btn btn-success">
									<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
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
			</div>
		</div>

	</s:form>
	<s:form name="cancelform" action="harvestSeason_list.action">
		<s:hidden key="currentPage" />
	</s:form>

	<!-- <script>


			jQuery(function($){
				$("#fromCalendar").datepicker({
					minDate: null,
					maxDate :99999,
					dateFormat : 'dd/mm/yy'
				});
			});

			jQuery(function($){
				$("#toCalendar").datepicker({
					minDate: null,
					maxDate : 99999,
					dateFormat : 'dd/mm/yy'
				});
			});

		</script> -->

	<script type="text/javascript">
		$("#fromCalendar").datepicker({
			format : "mm/yyyy",
			startView : "months",
			minViewMode : "months"
		});

		$("#toCalendar").datepicker({
			format : "mm/yyyy",
			startView : "months",
			minViewMode : "months"
		});

		function onSubmit() {

			if ('<s:property value="getCurrentTenantId()"/>' == 'pratibha') {
				var startDate = new Date(fomatDate(document
						.getElementById("fromCalendar").value));
				var sDate = getFormattedDate(startDate);
				var endDate = new Date(fomatDate(document
						.getElementById("toCalendar").value));
				var eDate = getFormattedDate(endDate);
				if (sDate > eDate) {
					alert('<s:text name="date.range"/>');
				} else {
					/* document.getElementById("fromPeriod").value = document.getElementById("fromCalendar").value;
					document.getElementById("toPeriod").value = document.getElementById("toCalendar").value; */
					document.getElementById("fromCalendar").value = sDate;
					document.getElementById("toCalendar").value = eDate;
					document.form.submit();
				}
			} else {
					var startDate = new Date(fomatDateOther(document
							.getElementById("fromDate").value));
					var endDate = new Date(fomatDateOther(document
							.getElementById("toDate").value));
					if (startDate > endDate) {
						alert('<s:text name="date.range"/>');
					} else {
						document.getElementById("fromPeriod").value = document
								.getElementById("fromDate").value;
						document.getElementById("toPeriod").value = document
								.getElementById("toDate").value;
						document.form.submit();
					}
				
			}
		}

		function fomatDate(date) {
			var dateArr = date.split("/");
			return dateArr[0] + "/01/" + dateArr[1];
		}
		function fomatDateOther(date) {
			var dateArr = date.split("/");
			return dateArr[1] + "/" + dateArr[0] + "/" + dateArr[2];
		}
		function getFormattedDate(date) {
			var year = date.getFullYear();
			var month = (1 + date.getMonth()).toString();
			month = month.length > 1 ? month : '0' + month;
			return month + '/' + year;

		}
	</script>

</body>