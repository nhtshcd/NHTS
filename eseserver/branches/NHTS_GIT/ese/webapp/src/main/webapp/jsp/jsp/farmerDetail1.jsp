<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<meta name="decorator" content="swithlayout">
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
<style>
</style>
</head>
<body>
	<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
	<script>
		jQuery(document).ready(function(){
			var tenant='<s:property value="getCurrentTenantId()"/>';
			var branchId='<s:property value="getBranchId()"/>';
			var type= '<%=request.getParameter("type")%>';
			var id= '<%=request.getParameter("id")%>';
			/* if(type=='2'){
					$(".breadCrumbNavigation").html('');
					$(".breadCrumbNavigation").append("<li><a href='#'>Profile</a></li>");
					$(".type").val(type);
					$(".nav").parent().addClass("hide");
				} 
		*/
			$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
			$("ul > li").removeClass("active");
			/*var tabIndex =<%if (request.getParameter("tabIndex") == null) {
				out.print("'#tabs-1'");
			} else {
				out.print("'" + request.getParameter("tabIndex") + "'");
			}%>;
		    var tabObj = $('a[href="' + tabIndex + '"]');
		    $(tabObj).closest("li").addClass('active');
		    $("div").removeClass("active in");
		    $(tabIndex).addClass('active in');
		    var tabSelected = getUrlParameter('tabValue');
		    if (tabSelected == "tabs-2"){
			    $(tabIndex).removeClass("active in");
			    $('#tabs-2').addClass('active in');
			    $(tabObj).closest("li").removeClass('active');
			    tabObj = $('a[href="#tabs-2"]');
			    $(tabObj).closest("li").addClass('active');
		    }else if (tabSelected == "tabs-3"){
			    $(tabIndex).removeClass("active in");
			    $('#tabs-3').addClass('active in');
			    $(tabObj).closest("li").removeClass('active');
			    tabObj = $('a[href="#tabs-3"]');
			    $(tabObj).closest("li").addClass('active');
		    }*/
		});
		function makeQrCode () {      
		    var elText = '<s:property value="farmer.farmerId" />';		   
		    $('#qrcode').qrcode(elText);
		}
		
		function getUrlParameter(sParam) {
	        var sPageURL = decodeURIComponent(window.location.search.substring(1));
	        var sURLVariables = sPageURL.split('&');
	        var sParameterName;
	        var i;
	        for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');
	        if (sParameterName[0] === sParam) {
	        return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	        }
	        }
		
		
		function callGrids(){
			var table =  	createDataTable('detail1',"farm_data.action?farmerId="+farmerUniqueId);
			var table1 =  	createDataTable('cropDetail',"farmCrops_data.action?farmerId="+farmerUniqueId);
		}
		
function addFarm(){
	var url="farm_create.action?farmerId="+farmerId+"&farmerUniqueId="+farmerUniqueId+"&branch="+branchId;
	var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-2"
	url=url+"&redirectContent="+redirectUrl;
	window.location.target="_blank";
	window.location.href=url;

}


                     function addfarmCrop()
                     {
                    		var url="farmCrops_create.action?farmerId="+farmerUniqueId+"&branch="+branchId;
                    		var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-3"
                    		url=url+"&redirectContent="+redirectUrl;
                    		window.location.target="_blank";
                    		window.location.href=url;
                    		}
                     
                     function gotourl(utl) {
                    		window.location.target="_blank";
                    		window.location.href=utl;
             		}
		
		function popDownload(type) {
			document.getElementById("loadId").value = type;

			$('#fileDownload').submit();

		}
		function onCancel(){
			window.location.href="<s:property value="redirectContent" />";
		}
	</script>

	<div id="baseDiv"></div>
	<div class="appContentWrapper marginBottom ">
		<ul class="nav nav-pills">
			<li class="active"><a data-toggle="pill" href="#tabs-1"><s:property
						value="%{getLocaleProperty('title.farmer')}" /></a></li>
			<li><a data-toggle="pill" href="#tabs-2"><s:property
						value="%{getLocaleProperty('title.farm')}" /></a></li>

			<li><a href="#tabs-3" data-toggle="pill"><s:property
						value="%{getLocaleProperty('title.farmCrops')}" /></a></li>
		</ul>
	</div>

	<div class="tab-content">
		<div id="tabs-1" class="tab-pane fade in active">
			<div class="flexbox-container">

				<s:form name="form">
					<s:hidden key="currentPage" />
					<s:hidden key="farmer.id" id="farmerId" class='uId' />
					<s:hidden key="command" />
					<s:hidden name="redirectContent" id="redirectContent" />

					<s:hidden key="farmerAndContractStatus" />
					<div class="flex-view-layout">
						<div class="fullwidth">
							<div class="flexWrapper">
								<div class="flexLeft appContentWrapper">
									<div class="error">
										<s:actionerror />
										<s:fielderror />
									</div>
									<div class="formContainerWrapper">

										<div class="aPanel farmer_info">
											<div class="aTitle">
												<h2>
													<s:property value="%{getLocaleProperty('info.farmer')}" />
													<div class="pull-right">
														<a class="aCollapse" href="#"><i
															class="fa fa-chevron-right"></i></a>
													</div>
												</h2>
											</div>
											<div class="aContent dynamic-form-con">
												<s:if test='branchId==null'>
													<div class="dynamic-flexItem2">
														<p class="flexItem">
															<s:property value="%{getLocaleProperty('app.branch')}" />
														</p>
														<p class="flexItem">
															<s:property value="%{getBranchName(farmer.branchId)}" />
															&nbsp;
														</p>
													</div>
												</s:if>
											
												<div class="dynamic-flexItem2 hide">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.farmerId')}" />
													</p>
													<p class="flexItem" name="farmer.farmerId">
														<s:property value="farmer.farmerId" />
														&nbsp;
													</p>
												</div>
												<div class="dynamic-flexItem2">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.farmerCode')}" />
													</p>
													<p class="flexItem" name="farmer.farmerCode">
														<s:property value="farmer.farmerCode" />
														&nbsp;
													</p>
												</div>

												<div class="dynamic-flexItem2">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.regDate')}" />
													</p>
													<p class="flexItem" name="farmer.regDate">
														<s:date name="farmer.regDate" format="dd/MM/yyyy" />
													</p>
												</div>
												<div class="dynamic-flexItem2">
													<p class="flexItem nameLab">
														<s:property
															value="%{getLocaleProperty('farmer.firstName')}" />
													</p>
													<p class="flexItem" name="farmer.firstName">
														<s:property value="farmer.firstName" />
													</p>
												</div>

												<div class="dynamic-flexItem2">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.lastName')}" />
													</p>
													<p class="flexItem" name="farmer.lastName">
														<s:property value="farmer.lastName" />
													</p>
												</div>
												<div class="dynamic-flexItem2">
													<p class="flexItem dateName">
														<s:property value="%{getLocaleProperty('farmer.dateOfBirth')}" />
													</p>
													<p class="flexItem" name="plantDateStr">
														<s:property value="plantDateStr" />
													</p>
												</div>
												<div class="dynamic-flexItem2">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.proof')}" />
													</p>
													<p class="flexItem" name="farmer.proof">
														<button type="button" class="fa fa-download"
															onclick="popDownload(<s:property value="farmer.proof"/>)">
													</p>
												</div>
												<div class="dynamic-flexItem2">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.nin')}" />
													</p>
													<p class="flexItem" name="farmer.nin">
														<s:property value="farmer.nin" />
													</p>
												</div>
												<div class="dynamic-flexItem2">
													<p class="flexItem cnName">
														<s:property value="%{getLocaleProperty('country.name')}" />
													</p>
													<p class="flexItem" name="selectedCountry">
														<s:property
															value="farmer.village.city.locality.state.country.code" />
														&nbsp-&nbsp
														<s:property
															value="farmer.village.city.locality.state.country.name" />
													</p>
												</div>

												<div class="dynamic-flexItem2">
													<p class="flexItem stName">
														<s:property value="%{getLocaleProperty('state.name')}" />
													</p>
													<p class="flexItem" name="selectedState">
														<s:property
															value="farmer.village.city.locality.state.code" />
														&nbsp-&nbsp
														<s:property
															value="farmer.village.city.locality.state.name" />
													</p>
												</div>

												<div class="dynamic-flexItem2">
													<p class="flexItem locName">
														<s:property value="%{getLocaleProperty('locality.name')}" />
													</p>
													<p class="flexItem" name="selectedLocality">
														<s:property value="farmer.village.city.locality.code" />
														&nbsp-&nbsp
														<s:property value="farmer.village.city.locality.name" />
													</p>
												</div>

												<div class="dynamic-flexItem2">
													<p class="flexItem ctName">
														<s:property value="%{getLocaleProperty('city.name')}" />
													</p>
													<p class="flexItem" name="selectedCity">
														<s:property value="farmer.village.city.code" />
														&nbsp-&nbsp
														<s:property value="farmer.village.city.name" />
													</p>
												</div>



												<div class="dynamic-flexItem2">
													<p class="flexItem villName">
														<s:property value="%{getLocaleProperty('village.name')}" />
													</p>
													<p class="flexItem" name="selectedVillage">
														<s:property value="farmer.village.code" />
														&nbsp-&nbsp
														<s:property value="farmer.village.name" />
													</p>
												</div>

												<div class="dynamic-flexItem2">
													<p class="flexItem addInfo">
														<s:property value="%{getLocaleProperty('farmer.address')}" />
													</p>
													<p class="flexItem" name="farmer.addr">
														<s:property value="farmer.addr" />
													</p>
												</div>
												<div class="dynamic-flexItem2">
													<p class="flexItem addInfo">
														<s:property value="%{getLocaleProperty('farmer.email')}" />
													</p>
													<p class="flexItem" name="farmer.emailId">
														<s:property value="farmer.emailId" />
													</p>
												</div>
												<div class="dynamic-flexItem2">
													<p class="flexItem addInfo">
														<s:property
															value="%{getLocaleProperty('farmer.mobileNo')}" />
													</p>
													<p class="flexItem" name="farmer.mobileNo">
														<s:property value="farmer.mobileNo" />
													</p>
												</div>
												<div class="dynamic-flexItem2">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('exporterName')}" />
													</p>
													<p class="flexItem" name="farmer.exporter.id">
														<s:property value="farmer.exporter.name" />
													</p>
												</div>
												<div class="dynamic-flexItem2">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.status')}" />
													</p>
													<p class="flexItem" name="farmer.status">
														<s:property
															value="%{getLocaleProperty('card'+farmer.status)}" />
													</p>
												</div>
											</div>
										</div>
	
												
										<div class="flexItem flex-layout flexItemStyle">
											<div class="">
											
												<span id="cancel" class=""><span class="first-child"><button
						type="button" class="cancel-btn btn btn-sts" onclick="javascript:window.close('','_parent','');">
						<b><FONT color="#FFFFFF"><s:text name="cancel.button" />
						</font></b>
					</button></span></span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</s:form>

			
			
				<s:form id="fileDownload" action="generalPop_populateDownload" >
		<s:hidden id="loadId" name="idd" />
		<%-- 	<s:hidden id="loadType" name="type" /> --%>

	</s:form>
			</div>
		</div>

		<div id="tabs-2" class="tab-pane fade">
		<div id="errorDiv" style="color: red;">
					<%
						request.getSession().removeAttribute("farmcropsExist");
					%>
				</div>
		
		 <div  class="dataTable-btn-cntrls" id="detail1Btn"> 
	     <sec:authorize ifAllGranted="profile.farmer.create">
	     
	     
	  	 <ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="collapse" href="#" onclick="addFarm();"> <i
						id="add"	class="fa fa-plus"></i>Add
						</a></li>
					</ul> 
		
					</sec:authorize>
				 </div>
				 
				 
			<div class="appContentWrapper marginBottom">
			
				<div id="baseDiv1" style="width: 100%">
			<table id="detail1" class="table table-striped" >
				<thead >
					<tr id="headerrow">

						
								<th width="20%" id="code" class="hd"><s:text
										name="farm.code" /></th>
								<th width="20%" id="name" class="hd"><s:text
										name="farm.name" /></th>
								<th width="20%" id="area" class="hd"><s:text
										name="farm.totalLand" /></th>
								<th width="20%" id="edit" class="hd"><s:text name="action" /></th>
								<th width="20%" id="del" class="hd"><s:text name="action" /></th>
							
					</tr>
				</thead>

			</table>
		</div>
				

			</div>

		</div>

		<div id="tabs-3" class="tab-pane fade">
		
		 <div  class="dataTable-btn-cntrls" id="cropDetailBtn"> 
	     <sec:authorize ifAllGranted="profile.farmer.create">
	     
	     
	  	 <ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="collapse" href="#" onclick="addfarmCrop()"> <i
						id="add"	class="fa fa-plus"></i>Add
						</a></li>
					</ul> 
		
					</sec:authorize>
				 </div>
				 
				 
			<div class="appContentWrapper marginBottom">
				
				
				<div id="baseDiv" style="width: 100%">
			<table id="cropDetail" class="table table-striped" >
				<thead >
					<tr id="headerrow">

						<th   width="20%" id="farm" class="hd"><s:text name="farm" /></th>
						<th   width="20%" id="product" class="hd"><s:text name="product" /></th>
						<th   width="20%" id="variety" class="hd"><s:text name="variety" /></th>
							<%-- 	<th   width="20%" id="season" class="hd"><s:text name="season" /></th> --%>
								<th   width="20%" id="area" class="hd"><s:text name="area" /></th>
								<th width="20%" id="edit" class="hd"><s:text name="action" /></th>
								<th width="20%" id="del" class="hd"><s:text name="action" /></th>
							
					</tr>
				</thead>

			</table>
		</div>
		
		

			
		</div>
	</div>
	</div>

<s:form id="updatefarm" action="farm_update.action" >
		<s:hidden id="farmup" name="id" />
	</s:form>
	<s:form id="deletefarm" action="farm_delete.action" >
		<s:hidden id="farmup1" name="id" />
	</s:form>
	
	<s:form id="updatefarmcrop" action="farmCrops_update.action" >
		<s:hidden id="farmcropup" name="id" />
	</s:form>
	<s:form id="deletefarmcrop" action="farmCrops_delete.action" >
		<s:hidden id="farmcropup1" name="id" />
	</s:form>
	<link rel="stylesheet" href="plugins/select2/select2.min.css">
	<script
		src="js/dynamicReportRelated/dataTablePlugin/jquery.dataTables.min.js"></script>
	<script
		src="js/dynamicReportRelated/dataTablePlugin/dataTables.buttons.min.js?v=2.2"></script>
	<script
		src="js/dynamicReportRelated/dataTablePlugin/buttons.flash.min.js"></script>
	<script src="js/dynamicReportRelated/dataTablePlugin/pdfmake.min.js"></script>
	<script src="js/dynamicReportRelated/dataTablePlugin/jszip.min.js"></script>
	<script src="js/dynamicReportRelated/dataTablePlugin/vfs_fonts.js"></script>
	<script
		src="js/dynamicReportRelated/dataTablePlugin/buttons.html5.min.js"></script>
	<script
		src="js/dynamicReportRelated/dataTablePlugin/buttons.print.min.js"></script>


	<link
		href="js/dynamicReportRelated/styleSheets/jquery.dataTables.min.css"
		rel="stylesheet" type="text/css" />
	<link
		href="js/dynamicReportRelated/styleSheets/buttons.dataTables.min.css"
		rel="stylesheet" type="text/css" />


	<style>
.select2 {
	width: auto !important;
	margin-left: 10px;
	position: relative;
}

/* .filterBtn{
margin-left: 1%;

} */
.dataTableTheme>thead>tr>th {
	background: #2a3f54;
	color: #fff;
}

.dataTableTheme>tbody>tr:nth-child(odd), .dataTableTheme>tfoot>tr:nth-child(odd),
	.dataTableTheme>thead>tr:nth-child(odd) {
	background-color: rgba(168, 227, 214, 0.2);
}

button.dt-button.buttons-excel.buttons-html5 {
	/*  background: yellow; */
	background: #d9534f;
	border-color: #005bbf;
	color: #ffffff;
}

button.dt-button.buttons-pdf.buttons-html5 {
	/*  background: yellow; */
	background: #398439;
	border-color: #005bbf;
	color: #ffffff;
}
</style>
	<script>
	var tenant='<s:property value="getCurrentTenantId()"/>';
	var farmerId;
	var farmerUniqueId;
	var branchId;
		$(document).ready(function() {
			farmerId ='<s:property value="farmer.farmerId"/>';
			farmerUniqueId ='<s:property value="farmer.id"/>';
			branchId ='<s:property value="farmer.branchId"/>';
			callGrids();
							/* $('#update').on(
											'click',
											function(e) {
												document.updatfrm.id.value = document.getElementById('farmerId').value;
												document.updatfrm.currentPage.value = document.form.currentPage.value;
												document.updatfrm.submit();
											});
							$('#delete')
									.on(
											'click',
											function(e) {
												if (confirm('<s:property value="%{getLocaleProperty('confirm.deleteFarmer')}"/> ')) {
													document.deleteform.id.value = document
															.getElementById('farmerId').value;
													document.deleteform.currentPage.value = document.form.currentPage.value;
													document.deleteform
															.submit();
												}
											}); */
						});
		 function insertOptionsByClass(ctrlName, jsonArr) {
			  $("."+ctrlName).append($('<option>',{
	            	 value: "",
	                 text: "<s:property value='%{getLocaleProperty("Selectseason")}'/>"
		 }));
			 
			 $.each(jsonArr, function(i, value) {
		            $("."+ctrlName).append($('<option>',{
		            	 value: value.id,
		                 text: value.name
			 }));
		});	
		 }
		 function ediFunction(val){
			 var son = JSON.parse(val);
			 var url="farm_update.action?farmerId="+farmerId+"&farmerUniqueId="+farmerUniqueId+"&branch="+branchId+"&id="+son.id;
				var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-2"
				url=url+"&redirectContent="+redirectUrl;
				window.location.target="_blank";
				window.location.href=url;
		 }
		 function deleteFunction(val){
			 var son = JSON.parse(val);
			 var url="farm_delete.action?farmerId="+farmerId+"&farmerUniqueId="+farmerUniqueId+"&branch="+branchId+"&id="+son.id;
				var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-2"
				url=url+"&redirectContent="+redirectUrl;
				window.location.target="_blank";
				window.location.href=url;
		 }
		 
		 function ediFunction1(val){
			
			 var son = JSON.parse(val);
			 var url="farmCrops_update.action?farmerId="+farmerId+"&farmerUniqueId="+farmerUniqueId+"&branch="+branchId+"&id="+son.id;
				var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-2"
				url=url+"&redirectContent="+redirectUrl;
				window.location.target="_blank";
				window.location.href=url;
		 }
		 function deleteFunction1(val){
			
			 var son = JSON.parse(val);
			 var url="farmCrops_delete.action?farmerId="+farmerId+"&farmerUniqueId="+farmerUniqueId+"&branch="+branchId+"&id="+son.id;
				var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-2"
				url=url+"&redirectContent="+redirectUrl;
				window.location.target="_blank";
				window.location.href=url;
		 }
	</script>
</body>