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
			 if(type=='2'){
					$(".breadCrumbNavigation").html('');
					$(".breadCrumbNavigation").append("<li><a href='#'>Profile</a></li>");
					$(".type").val(type);
					$(".nav").parent().addClass("hide");
				} 
		
			$(".breadCrumbNavigation").find('li:last').find('a:first').attr("href",'<s:property value="redirectContent" />');
			$("ul > li").removeClass("active");
			var tabIndex =<%if (request.getParameter("tabIndex") == null) {
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
		    }else if (tabSelected == "tabs-4"){
			    $(tabIndex).removeClass("active in");
			    $('#tabs-4').addClass('active in');
			    $(tabObj).closest("li").removeClass('active');
			    tabObj = $('a[href="#tabs-4"]');
			    $(tabObj).closest("li").addClass('active');
		    }
		    
		    var p='<s:property value="farmer.house"/>';
		  
		    if (p == '1')
		    			{	
		    	
		    			$("#product").text("Yes");
		    			$('.y1').removeClass('hide');
		    			}
		    			else if (p == '0')
		    			{
		    				
		    				$("#product").text("No");
		    				$('.y1').addClass('hide');
		    			}else if(p==''){
		    				
		    					$("#product").text("");
							$('.y1').addClass('hide');
		    			}
		    
		    var farmerRegType='<s:property value="farmer.farmerRegType"/>';
		    if(farmerRegType=='1'){
				 $(".companySection").removeClass('hide');
		    }

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
			redirectCont ="farmer_detail.action?id="+farmerUniqueId;
			var table =  	createDataTable('detail1',"farm_data.action?farmerId="+farmerUniqueId);
			var table1 =  	createDataTable('cropDetail',"farmCrops_data.action?farmerId="+farmerUniqueId);
			var table2 =  	createDataTable('plantingDetail',"planting_data.action?farmerId="+farmerUniqueId);
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
		
		function addplanting()
        {
       		var url="planting_create.action?farmerId="+farmerUniqueId+"&branch="+branchId;
       		var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-4"
       		url=url+"&redirectContent="+redirectUrl;
       		window.location.target="_blank";
       		window.location.href=url;
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
			<li><a href="#tabs-4" data-toggle="pill"><s:property
						value="%{getLocaleProperty('title.planting')}" /></a></li>
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
													<div class="dynamic-flexItem">
														<p class="flexItem">
															<s:property value="%{getLocaleProperty('app.branch')}" />
														</p>
														<p class="flexItem">
															<s:property value="%{getBranchName(farmer.branchId)}" />
															&nbsp;
														</p>
													</div>
												</s:if>


												<div class="dynamic-flexItem hide">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.farmerId')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.farmerId" />
														&nbsp;
													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.category')}" />
													</p>
													<p class="flexItem">
														<s:if test="farmer.fCat!=null && farmer.fCat!=''">
															<s:property
																value='%{getLocaleProperty("fcat"+farmer.fCat)}' />
														</s:if>

													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('Farm Ownership')}" />
													</p>
													<p class="flexItem">
														<s:property
															value='%{getLocaleProperty("farmercat"+farmer.farmerCat)}' />

													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.farmerRegType')}" />
													</p>
													<p class="flexItem">
														<s:property
															value='%{getLocaleProperty("farmerRegType"+farmer.farmerRegType)}' />

													</p>
												</div>
												<div class="dynamic-flexItem companySection hide">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.companyName')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.companyName" />
													</p>
												</div>
												<div class="dynamic-flexItem companySection hide">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.registrationCertificate')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.registrationCertificate" />
													</p>
												</div>
												<%-- <div class="dynamic-flexItem companySection hide">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.kraPin')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.kraPin" />
													</p>
												</div> --%>
												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.kraPin')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.kraPin" />
													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.fname')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.firstName" />
														&nbsp;
													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.fcode')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.farmerId" />
														&nbsp;
													</p>
												</div>
												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('exporterRegistr.gender')}" />
													</p>
													<p class="flexItem">
														<s:if test="farmer.gender!=null && farmer.gender!=''">
															<s:property
																value='%{getLocaleProperty("genderStatus"+farmer.gender)}' />
														</s:if>
													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.dateOfBirth')}" />
													</p>
													<p class="flexItem">
														<s:property value='dateOfBirth' />

													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.fage')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.age" />
													</p>
												</div>
												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('export.nationalId')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.nid" />
													</p>
												</div>

												<%-- <div class="dynamic-flexItem">
													<p class="flexItem ">
														<s:property
								value="%{getLocaleProperty('exporter')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.exporters" />
													</p>
												</div> --%>


												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('exporterRegistr.phn')}" />
													</p>
													<p class="flexItem" name="farmer.phoneNo">
														<s:property value="farmer.mobileNo" />
													</p>
												</div>




												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('export.cropcat')}" />
													</p>
													<p class="flexItem">
														<s:property value='farmer.cropCategory' />
													</p>
												</div>


												<div class="dynamic-flexItem">
													<p class="flexItem ">
														<s:property value="%{getLocaleProperty('cropName')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.CropName" />
													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem ">
														<s:property
															value="%{getLocaleProperty('export.cropvariety')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.cropVariety" />
													</p>
												</div>


												<div class="dynamic-flexItem">
													<p class="flexItem dateName">
														<s:property value="%{getLocaleProperty('export.crophs')}" />
													</p>
													<p class="flexItem" name="farmer.cropHscode">
														<s:property value="cropHsCode" />

													</p>
												</div>


												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:text name="country.name" />
													</p>
													<p class="flexItem">
														<s:property
															value="farmer.village.city.locality.state.country.code" />
														&nbsp-&nbsp
														<s:property
															value="farmer.village.city.locality.state.country.name" />
													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:text name="county.name" />
													</p>
													<p class="flexItem">
														<s:property
															value="farmer.village.city.locality.state.code" />
														&nbsp-&nbsp
														<s:property
															value="farmer.village.city.locality.state.name" />
													</p>

												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:text name="subcountry.name" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.village.city.locality.code" />
														&nbsp-&nbsp
														<s:property value="farmer.village.city.locality.name" />
													</p>

												</div>
												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:text name="ward.name" />
													</p>
													<p class="flexItem">
														<!-- <p class="flexItem" name="selectedCity">
										 -->
														<s:property value="farmer.village.city.code" />
														&nbsp-&nbsp
														<s:property value="farmer.village.city.name" />

													</p>
												</div>


												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:text name="village.name" />
													</p>
													<p class="flexItem" name="selectedVillage">
														<s:property value="farmer.village.code" />
														&nbsp-&nbsp
														<s:property value="farmer.village.name" />
													</p>
												</div>


												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.photo')}" />
													</p>
													<s:if test="farmer.farmerPhoto!=null">
														<p class="flexItem">
															<button type="button" class="fa fa-download"
																style="background-color: transparent"
																onclick="popDownload(<s:property value="farmer.farmerPhoto"/>)">
														</p>
													</s:if>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.nid')}" />
													</p>
													<s:if test="farmer.photoNid!=null && farmer.photoNid!=''">
														<p class="flexItem" name="farmer.proof">
															<button type="button" class="fa fa-download"
																style="background-color: transparent"
																onclick="popDownload(<s:property value="farmer.photoNid"/>)">
														</p>
													</s:if>
												</div>




												<%-- <div class="dynamic-flexItem companySection hide">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.registrationCertificate')}" />
													</p>
													<s:if test="farmer.registrationCertificate!=null">
														<p class="flexItem" name="farmer.registrationCertificate">
															<button type="button" class="fa fa-download"
																style="background-color: transparent"
																onclick="popDownload(<s:property value="farmer.registrationCertificate"/>)">
														</p>
													</s:if>
												</div> --%>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.latitude')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.latitude" />
													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.longitude')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.longitude" />
													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('exporterRegistr.email')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.emailId" />
													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.family')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.noOfFamilyMember" />
													</p>
												</div>
												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.adult')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.adultAbove" />
													</p>
												</div>
												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property value="%{getLocaleProperty('farmer.school')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.schoolGoingChild" />
													</p>
												</div>
												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:property
															value="%{getLocaleProperty('farmer.childBelow')}" />
													</p>
													<p class="flexItem">
														<s:property value="farmer.childBelow" />
													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:text name="farmer.edu" />
													</p>
													<p class="flexItem" id="hideeduca">
														<s:property
															value='%{getCatlogueValueByCodeArray(farmer.hedu)}' />


													</p>
												</div>


												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:text name="farmer.asset" />
													</p>
													<p class="flexItem">
														<s:property
															value='%{getCatlogueValueByCodeArray(farmer.asset)}' />

													</p>
												</div>

												<div class="dynamic-flexItem">
													<p class="flexItem">
														<s:text name="farmer.house" />
													</p>
													<p class="flexItem" id="product">
														<s:property value='%{getLocaleProperty("farmer.house)}' />

													</p>
												</div>


												<div class="dynamic-flexItem y1 hide">
													<p class="flexItem">
														<s:text name="farmer.owner" />
													</p>
													<p class="flexItem">

														<!-- <span id="ownership"></span>  -->
														<s:property
															value='%{getCatlogueValueByCodeArray(farmer.ownership)}' />
													</p>
												</div>
											</div>



										</div>
										<s:if test='#session.isAdmin =="true"'>
											<s:iterator value="ex" var="innerList">
												<div class="aPanel audit_history">
													<div class="aTitle">
														<h2>

															<s:if
																test="#innerList[2].toString().trim().equalsIgnoreCase('ADD')">
																<s:property value="#innerList[0].createdUser" />
															</s:if>
															<s:else>
																<s:property value="#innerList[0].updatedUser" />
															</s:else>
															-

															<s:date name="#innerList[1].revisionDate"
																format="dd/MM/yyyy hh:mm:ss" />
															-
															<s:property
																value="%{getLocaleProperty('default'+#innerList[2])}" />
															<div class="pull-right">
																<a class="aCollapse "
																	href="#<s:property value="#innerList[1].id" />
										"><i
																	class="fa fa-chevron-right"></i></a>
															</div>
														</h2>
													</div>
													<div class="aContent dynamic-form-con"
														id="<s:property value="#innerList[1].id" />">

														<jsp:include page='/jsp/jsp/auditFarmerDetail.jsp' />

													</div>


												</div>
											</s:iterator>
										</s:if>
										<div class="margin-top-10">

											<span id="cancel" class=""><span class="first-child"><button
														type="button" class="back-btn btn btn-sts"
														onclick="onCancel()">
														<b><FONT color="#FFFFFF"><s:text
																	name="back.button" /> </font></b>
													</button></span></span>

										</div>











									</div>
								</div>
							</div>
						</div>
					</div>
				</s:form>



				<s:form id="fileDownload" action="farmer_populateDownload">
					<s:hidden id="loadId" name="idd" />


				</s:form>
			</div>
		</div>

		<div id="tabs-2" class="tab-pane fade">
			<div id="errorDiv" style="color: red;">
				<%
					request.getSession().removeAttribute("farmcropsExist");
				%>
			</div>
			<div class="error">
				<s:actionerror />
				<s:fielderror />
			</div>
			<div class="dataTable-btn-cntrls" id="detail1Btn">
				<sec:authorize ifAllGranted="profile.farmer.create">


					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="collapse" href="#"
							onclick="addFarm()"> <i id="add" class="fa fa-plus"></i>Add
						</a></li>
					</ul>

				</sec:authorize>
			</div>


			<div class="appContentWrapper marginBottom">

				<div id="baseDiv1" style="width: 100%">
					<table id="detail1" class="table table-striped">
						<thead>
							<tr id="headerrow">


								<th width="20%" id="code" class="hd"><s:property
										value='%{getLocaleProperty("farmId")}' /></th>
								<th width="20%" id="name" class="hd"><s:text
										name="farm.name" /></th>
								<th width="20%" id="area" class="hd"><s:text
										name="farm.totalLand" /></th>
								<th width="20%" id="village" class="hd">Proposed Planting
									Area (Acre)</th>
								<th width="20%" id="city" class="hd">Village</th>
								<sec:authorize ifAllGranted="profile.farmer.update">
									<th width="20%" id="edit" class="hd noexp"><s:text
											name="Edit" /></th>
								</sec:authorize>
								<sec:authorize ifAllGranted="profile.farmer.delete">
									<th width="20%" id="del" class="hd noexp"><s:text
											name="Delete" /></th>
								</sec:authorize>

							</tr>
						</thead>

					</table>
				</div>


			</div>

		</div>

		<div id="tabs-3" class="tab-pane fade">
			<div class="error">
				<s:actionerror />
				<s:fielderror />
			</div>
			<div class="dataTable-btn-cntrls" id="cropDetailBtn">
				<sec:authorize ifAllGranted="profile.farmer.create">


					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="collapse" href="#"
							onclick="addfarmCrop()"> <i id="add" class="fa fa-plus"></i>Add
						</a></li>
					</ul>

				</sec:authorize>
			</div>


			<div class="appContentWrapper marginBottom">


				<div id="baseDiv" style="width: 100%">
					<table id="cropDetail" class="table table-striped">
						<thead>
							<tr id="headerrow">

								<th width="20%" id="farm" class="hd"><s:text name="farm" /></th>
								<th width="20%" id="exporter" class="hd"><s:property
										value='%{getLocaleProperty("profile.exporter.name")}' /></th>
								<th width="20%" id="blockId" class="hd"><s:text
										name="blockId" /></th>

								<th width="20%" id="blName" class="hd"><s:text
										name="blockName" /></th>


								<%-- <th width="20%" id="plId" class="hd"><s:property
										value='%{getLocaleProperty("plantingId")}' /></th>

								<th width="20%" id="variety" class="hd"><s:text name="Crop" /></th>
								<th width="20%" id="grade" class="hd"><s:text name="grade" /></th> --%>
								<th width="20%" id="area" class="hd"><s:property
										value='%{getLocaleProperty("cultiAreas")}' /></th>
								<sec:authorize ifAllGranted="profile.farmer.update">
									<th width="20%" id="edit" class="hd noexp"><s:text
											name="Edit" /></th>
								</sec:authorize>
								<sec:authorize ifAllGranted="profile.farmer.delete">
									<th width="20%" id="del" class="hd noexp"><s:text
											name="Delete" /></th>
								</sec:authorize>

							</tr>
						</thead>

					</table>
				</div>




			</div>
		</div>

		<div id="tabs-4" class="tab-pane fade">
			<div class="error">
				<s:actionerror />
				<s:fielderror />
			</div>
			<div class="dataTable-btn-cntrls" id="cropDetailBtn">
				<sec:authorize ifAllGranted="profile.farmer.create">


					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="collapse" href="#"
							onclick="addplanting()"> <i id="add" class="fa fa-plus"></i>Add
						</a></li>
					</ul>

				</sec:authorize>
			</div>


			<!-- <div class="appContentWrapper marginBottom"> -->
			<div class="appContentWrapper border-radius datatable-wrapper-class"
				style="overflow: auto;">




				<div id="baseDiv" style="width: 100%">
					<table id="plantingDetail" class="table table-striped">
						<thead>
							<tr id="headerrow">

								<th width="20%" id="farm" class="hd"><s:text name="farm" /></th>
								<th width="20%" id="blockId" class="hd"><s:text
										name="blockId" /></th>
								<th width="20%" id="farmCrops" class="hd"><s:text
										name="crop.blockName" /></th>

								<th width="20%" id="date" class="hd"><s:property
										value='%{getLocaleProperty("plantingDate")}' /></th>
								<th width="20%" id="plId" class="hd"><s:property
										value='%{getLocaleProperty("plantingId")}' /></th>

								<th width="20%" id="variety" class="hd"><s:text name="Crop" /></th>
								<th width="20%" id="grade" class="hd"><s:text name="grade" /></th>
								<th width="20%" id="area" class="hd"><s:property
										value='%{getLocaleProperty("plantingAreas")}' /></th>
								<th width="20%" id="fieldType" class="hd"><s:property
										value='%{getLocaleProperty("planting.fieldType")}' /></th>
								<sec:authorize ifAllGranted="profile.farmer.update">
									<th width="20%" id="edit" class="hd noexp"><s:text
											name="Edit" /></th>
								</sec:authorize>
								<sec:authorize ifAllGranted="profile.farmer.delete">
									<th width="20%" id="del" class="hd noexp"><s:text
											name="Delete" /></th>
								</sec:authorize>

							</tr>
						</thead>

					</table>
				</div>




			</div>
		</div>
	</div>

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
			highedu ='<s:property value="farmer.hedu"/>';
			onhouse ='<s:property value="farmer.house"/>';
			callGrids();
					$(".dt-buttons").addClass("hide");	
					if(highedu=='' || highedu=='null'){
						$('#hideeduca').hide();
					}
					
					if(onhouse=='' || onhouse=='null'){
						$('#hideownhouse').hide();
					}
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
				// showPopup({"msg":"Farm Updated Successfully","title":"Success"});
				url=url+"&redirectContent="+redirectUrl;
				window.location.target="_blank";
				window.location.href=url;
		 }
		 function deleteFunction(val){
			 if(confirm('<s:property value="%{getLocaleProperty('confirm.delete')}" />')){
				 var son = JSON.parse(val);
			 var url="farm_delete.action?farmerId="+farmerId+"&farmerUniqueId="+farmerUniqueId+"&branch="+branchId+"&id="+son.id;
				var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-2"
				url=url+"&redirectContent="+redirectUrl;
				window.location.target="_blank";
				window.location.href=url;
			 }
		 }
		 
		 function ediFunction1(val){
			 var son = JSON.parse(val);
			 var url="farmCrops_update.action?farmerId="+farmerId+"&farmerUniqueId="+farmerUniqueId+"&branch="+branchId+"&id="+son.id;
				var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-3"
				url=url+"&redirectContent="+redirectUrl;
				window.location.target="_blank";
				window.location.href=url;
		 }
		 function deleteFunction1(val){
			 if(confirm('<s:property value="%{getLocaleProperty('confirm.delete')}" />')){
			 var son = JSON.parse(val);
			 var url="farmCrops_delete.action?farmerId="+farmerId+"&farmerUniqueId="+farmerUniqueId+"&branch="+branchId+"&id="+son.id;
				var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-3"
				url=url+"&redirectContent="+redirectUrl;
				window.location.target="_blank";
				window.location.href=url; 
			 /* $.ajax({
				 url:'farmCrops_delete.action?id='+son.id,
			     type: 'post',
			     dataType: 'json',
			     processData: false,
			     contentType: false,
			     success: function(result) {
			    	 alert(JSON.stringify(result));
			    	 showPopup(result.msg,result.title);
				 	 $('#cropDetail').DataTable().ajax.reload();
			     },
			     error: function(result) {
			    	 showPopup(result.msg,result.title);
			     }
			});  */
			 }
		 }
		 
		 function ediFunction2(val){
			 var son = JSON.parse(val);
			 var url="planting_update.action?farmerId="+farmerId+"&farmerUniqueId="+farmerUniqueId+"&branch="+branchId+"&id="+son.id;
				var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-3"
				url=url+"&redirectContent="+redirectUrl;
				window.location.target="_blank";
				window.location.href=url;
		 }
		 function deleteFunction2(val){
			 if(confirm('<s:property value="%{getLocaleProperty('confirm.delete')}" />')){
			 var son = JSON.parse(val);
			 var url="planting_delete.action?farmerId="+farmerId+"&farmerUniqueId="+farmerUniqueId+"&branch="+branchId+"&id="+son.id;
				var redirectUrl= window.location.href.split("&")[0]+"&tabValue=tabs-3"
				url=url+"&redirectContent="+redirectUrl;
				window.location.target="_blank";
				window.location.href=url; 
			 /* $.ajax({
				 url:'farmCrops_delete.action?id='+son.id,
			     type: 'post',
			     dataType: 'json',
			     processData: false,
			     contentType: false,
			     success: function(result) {
			    	 alert(JSON.stringify(result));
			    	 showPopup(result.msg,result.title);
				 	 $('#cropDetail').DataTable().ajax.reload();
			     },
			     error: function(result) {
			    	 showPopup(result.msg,result.title);
			     }
			});  */
			 }
		 }
	</script>
</body>