<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<META name="decorator" content="swithlayout">
</head>

<script type="text/javascript">
	$(document).ready(function() {

		var table = createDataTable('detail', "customer_data.action");
	});

	function ediFunction(id) {
		 document.updateform.id.value=id;
	     document.updateform.submit();
	}

	function deletProd(idd) {
		$.ajax({
			url : 'customer_populateDelete.action?id=' + idd,
			type : 'post',
			dataType : 'json',
			processData : false,
			contentType : false,
			success : function(result) {
				showPopup(result.msg, result.title);
				$('#detail').DataTable().ajax.reload();
			},
			error : function(result) {
				showPopup(result.msg, result.title);
			}
		});
	}
</script>
<!-- If you change tables id change the id of above div too -->
<div class="dataTable-btn-cntrls" id="detailBtn">
	<!--	<sec:authorize ifAllGranted="profile.season.create">  -->
	<ul class="nav nav-pills newUI-nav-pills">
		<li class="" id=""><a data-toggle="pill" href="#"
			onclick="document.createform.submit()"> <i class="fa fa-plus"></i>
				<s:text name="create.button" />
		</a></li>
	</ul>
	<!--</sec:authorize>-->

</div>


<div class="appContentWrapper border-radius datatable-wrapper-class">

	<div id="baseDiv" style="width: 100%">
		<table id="detail" class="table ">
			<thead class="thead-dark">
				<tr id="headerrow">
					<th width="20%" id="customerId" class="hd"><s:text
							name="customer.customerId" /></th>
					<th width="20%" id="customerName" class="hd"><s:text
							name="customer.customerName" /></th>
					<th width="20%" id="personName" class="hd"><s:text
							name="customer.personName" /></th>
					<th width="20%" id="mobileNo" class="hd"><s:text
							name="customer.mobileNumber" /></th>
					<th width="20%" id="emailId" class="hd"><s:text
							name="customer.email" /></th>
					<th width="20%" id="edit" class="hd"><s:text name="action" /></th>
				</tr>
			</thead>

		</table>
	</div>
</div>
<s:form name="createform" action="customer_create">
</s:form>

<s:form name="updateform" id="updateform" action="customer_update">
	<s:hidden key="id" />
</s:form>

