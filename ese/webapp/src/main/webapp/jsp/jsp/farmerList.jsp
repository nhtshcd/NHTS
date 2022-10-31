<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<META name="decorator" content="swithlayout">
<style>
select[name='cs.name'], select[name='status'] {
	width: 100px !important;
}

.pdf-icon {
	background: transparent url("../img/pdf_icon2.png") no-repeat scroll
		left top;
	background-size: contain; /* to specify dimensions explicitly */
	background-repeat: no-repeat;
	border: medium none !important;
	box-shadow: none;
	cursor: pointer;
	height: 26px;
	text-indent: -10000px;
	width: 26px !important;
	transition: transform .3s ease-in-out;
	/* to have transiton effect of the zoom in .3 seconds */
	z-index: 100;
}

.pdf-icon:hover {
	transform: scale(2);
	zindex: 101;
} /* to have transform effect of the zoom */


</style>
</head>
<body>
	<script type="text/javascript">
var recordLimit='<s:property value="exportLimit"/>';
//var filterda='';
$(document).ready(function(){	
	var tenant='<s:property value="getCurrentTenantId()"/>';
	var userName='<s:property value="getUsername()"/>';
	populateMethod();
	var type= '<%=request.getParameter("type")%>';
	 if(type=='2'){
			$(".breadCrumbNavigation").html('');
			$(".breadCrumbNavigation").append("<li><a href='#'>Profile</a></li>");
			//$(".breadCrumbNavigation").append("<li><a href='#'>IRP</a></li>");
			$(".breadCrumbNavigation").append("<li><a href='farmer_list.action?type=2'>IRP</a></li>");
			$(".type").val(type);
		} 
	 
		var table =  	createDataTable('detail',"farmer_data.action",'','','kmlBtn');
		var flt = "";
		
});	      
function exportKML(){
	var count=jQuery("#detail").jqGrid('getGridParam', 'records');
	 if(count>recordLimit){
	   alert('<s:property value="%{getLocaleProperty('export.limited')}"/>');
	 }
	 else if(isDataAvailable("#detail")){
	  jQuery("#detail").setGridParam ({postData: {rows : 0}});
	  jQuery("#detail").jqGrid('excelExport', {url: 'farmer_populateKML.action'});
	 }else{
	      alert('<s:property value="%{getLocaleProperty('export.nodata')}"/>');
	 }
	 
		
}
function exportXLS(){
	var count=jQuery("#detail").jqGrid('getGridParam', 'records');
	if(count>recordLimit){
		 alert('<s:property value="%{getLocaleProperty('export.limited')}"/>');
	}
	else if(isDataAvailable("#detail")){
		jQuery("#detail").setGridParam ({postData: {rows : 0}});
		jQuery("#detail").jqGrid('excelExport', {url: 'farmer_populateXLS.action'});
	}else{
	     alert('<s:property value="%{getLocaleProperty('export.nodata')}"/>');
	}
	
}

function exportPDF(){
	var count=jQuery("#detail").jqGrid('getGridParam', 'records');
	if(count>recordLimit){
		 alert('<s:property value="%{getLocaleProperty('export.limited')}"/>');
	}
	else if(isDataAvailable("#detail")){
		jQuery("#detail").setGridParam ({postData: {rows : 0}});
		jQuery("#detail").jqGrid('excelExport', {url: 'farmer_populatePDF.action'});
	}else{
	     alert('<s:property value="%{getLocaleProperty('export.nodata')}"/>');
	}
}


function populateQrCode() {

	var count=jQuery("#detail").jqGrid('getGridParam', 'records');

	 if(isDataAvailable("#detail")){
		jQuery("#detail").setGridParam ({postData: {rows : 0}});
		jQuery("#detail").jqGrid('excelExport', {url: 'farmer_populateQrCode.action'});
	}else{
	     alert('<s:property value="%{getLocaleProperty('export.nodata')}"/>');
	}

	
}
function printQR(idd){
	jQuery("#detail").setGridParam ({postData: {rows : 0}});
	jQuery("#detail").jqGrid('excelExport', {url: "farmer_populateQRPdf.action?id="+idd});
	
}
function populateMethod() {
	jQuery.post("farmer_populateMethod.action", {},
			function(result) {
				var valuesArr = $.parseJSON(result);
				$.each(valuesArr, function(index, value) {
	
					jQuery('#farmerCount').text(value.farmerCount);
					jQuery('#farmerPercentage').text(
							value.farmerCountPercentage
									+ '<s:property value="%{getLocaleProperty('last.month')}"/>');
					jQuery('#farmerStatus').addClass(
							value.farmerCountstauts);
					jQuery('#farmerPercentage').addClass(
							value.farmerText);
					
					jQuery('#farmCount').text(value.farmCount);
					jQuery('#farmPercentage').text(
							value.farmCountPercentage
									+ '<s:property value="%{getLocaleProperty('last.month')}"/>');
					jQuery('#farmStatus').addClass(
							value.farmCountstauts);
					jQuery('#farmPercentage').addClass(
							value.farmText);
				
					
					jQuery('#farmCropCount').text(value.farmCropCount);
					jQuery('#farmCropPercentage').text(
							value.farmCropCountPercentage
									+ '<s:property value="%{getLocaleProperty('last.month')}"/>');
					jQuery('#farmCropStatus').addClass(
							value.farmCropCountstauts);
					jQuery('#farmCropPercentage').addClass(
							value.farmCropText);
					
					
					
					jQuery('#cowCount').text(value.cowCount);
					jQuery('#cowPercentage').text(
							value.farmCropCountPercentage
									+ '<s:property value="%{getLocaleProperty('last.month')}"/>');
					jQuery('#cowStatus').addClass(
							value.cowCountstauts);
					jQuery('#cowPercentage').addClass(
							value.cowText);

					
					
					jQuery('#farmLandCount').text(value.farmLandCount);
					jQuery('#farmLandPercentage').text(
							value.farmLandCountPercentage
									+ '<s:property value="%{getLocaleProperty('last.month')}"/>');
					jQuery('#farmLandStatus').addClass(
							value.farmLandCountstauts);
					jQuery('#farmLandPercentage').addClass(
							value.farmLandText);

				});
			});
}


datePick = function(elem)
{	
   jQuery(elem).datepicker({
		   changeYear: true,
	       changeMonth: true,       
	       dateFormat: 'dd-mm-yy',
		   onSelect:function(dateText, inst){	   	 
		   		jQuery("#detail")[0].triggerToolbar();
		   }
	   }
)};

function getBranchIdDyn(){
	return null;
}
</script>


	<div class="appContentWrapper-outline newUIbgImage">
		<h1 class="newUI-title">Farmer's List</h1>
		<div class="newUI-flexiwrapper">
			<div class="newUI-flexi">
				<div class="newUI-icon">
					<img src="dist/assets/images/icons/farmers-ico.png" />
				</div>
				<div class="newUI-metric">
					<h1  id="farmerCount">0</h1>
					<p><i id="farmerStatus" class="fa fa-sort-asc text-success"></i> <a
							href="#" id="farmerPercentage"
							class="text-success">0% Last month</a></p>
				</div>
			</div>
			<div class="newUI-flexi">
				<div class="newUI-icon">
					<img src="dist/assets/images/icons/plots-ico.png" />
				</div>
				<div class="newUI-metric">
					<h1  id="farmCount">0</h1>
					<p><i id="farmStatus" class="fa fa-sort-asc text-success"></i> <a
							href="#" id="farmPercentage"
							class="text-success">0% Last Month</a></p>
				</div>
			</div>
			<div class="newUI-flexi">
				<div class="newUI-icon">
					<img src="dist/assets/images/icons/harvest-ico.png" />
				</div>
				<div class="newUI-metric">
					<h1  id="farmCropCount">0</h1>
					<p>	<i id="farmCropStatus" class="fa fa-sort-asc text-success"></i> <a
								href="#" id="farmCropPercentage"
								class="text-success">0% Last Month</a></p>
				</div>
			</div>
			
		</div>
	</div>

<!-- If you change tables id change the id of above div too -->
	<div class="dataTable-btn-cntrls" id="detailBtn">
	     <sec:authorize ifAllGranted="profile.farmer.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="pill" href="#" onclick="document.createform.submit()"> <i
								class="fa fa-plus"></i>Add Farmer
						</a></li>
					</ul>
						</sec:authorize>
					
				</div>
					
					<button tabindex="0" id="kmlBtn" aria-controls="detail" onclick="exportKML()" type="button" title="KML Export"><span><i class="fa fa-map" style="font-size: 25px;"></i></span></button>
					

	<div class="appContentWrapper border-radius datatable-wrapper-class">
	

				
				
		<div id="baseDiv" style="width:100%">
			<table id="detail" class="table ">
				<thead class="thead-dark">
					<tr id="headerrow">

						<s:if test='branchId==null'>
							<th id="branch" class="hd"><s:text name="app.branch" /></th>
						</s:if>

						<s:if
							test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<th id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
						</s:if>


						<th id="firstName" class="hd"><s:text name="farmer.firstName" /></th>
						<th id="lastName" class="hd"><s:text name="farmer.lastName" /></th>
						<th id="village" class="hd"><s:text name="village.name" /></th>
						<th id="city" class="hd"><s:text name="city.name" /></th>
						<th id="locality" class="hd"><s:text name="taluk.name" /></th>
						<th id="state" class="hd"><s:text name="state.name" /></th>
						<th id="mobileNo" class="hd"><s:text name="farmer.mobileNo" /></th>
						<th id="createUser" class="hd"><s:text	name="farmer.createdUser" /></th>
						<th id="createDate" class="hd"><s:text	name="farmer.dateOfJoin" /></th>
					</tr>
				</thead>

			</table>
		</div>
	</div>


	<s:form name="createform" action="farmer_create">
		<s:hidden name="type" class="type" />
	</s:form>
	<s:form name="updateform" action="farmer_detail">
		<s:hidden name="id" />
		<s:hidden name="postdata" id="postdata" />
		<s:hidden name="type" class="type" />
		<s:hidden name="currentPage" />
	</s:form>
	<s:form name="exportform">
	</s:form>
</body>
