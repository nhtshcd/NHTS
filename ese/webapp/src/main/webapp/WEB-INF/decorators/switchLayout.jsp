<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@page import="com.sourcetrace.eses.entity.Menu"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<html lang="en" class="no-js">
<head>
<title>Ke-HTS</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta content="" name="description" />

<link rel="stylesheet"
	href="fonts/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="fonts/open-sans/css/open-sans.css">
<link rel="stylesheet" href="fonts/roboto/css/roboto.css">
<link rel="stylesheet" href="css/remodal.css">
<link rel="stylesheet" href="css/remodal-default-theme.css">

<link rel="shortcut icon" href="/favicon.ico">
<link rel="apple-touch-icon" href="/apple-touch-icon.png">
<link rel="stylesheet" href="css/bootstrap.min.css">

<link rel="stylesheet" href="css/main.css?v=2.2">
<!-- <link rel="stylesheet" href="css/bluewood.css"> -->
<link rel="stylesheet" href="css/bluewood.css">
<link rel="stylesheet" href="css/theme.css?v=3.2">
<!-- <link rel="stylesheet" href="css/sandwisp.css"> -->
<link rel="stylesheet" href="css/new-ui.css?v=3.2">

<link rel="stylesheet" type="text/css" media="screen"
	href="plugins/jquery_ui/jquery-ui.min.css" />

<link rel="shortcut icon" type="image/x-icon"
	href="login_populateLogo.action?logoType=favicon" />

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-hover-dropdown.min.js"></script>
<script src="js/jquery.mousewheel.js"></script>
<script src="js/remodal.min.js"></script>
<script src="js/perfect-scrollbar.js"></script>
<script src="plugins/jquery_ui/jquery-ui.min.js" type="text/javascript"></script>

<link rel="stylesheet" href="css/theme-mobileView.css?v=3.2">
<script src="js/theme-mobileView.js?v=5.2"></script>
</head>

<style>
@media screen and (min-width: 0px) and (max-width: 754px) {
	#div-mobile {
		display: block;
	}
	.div-desktop {
		display: none;
	}
	.wrapper {
		display: block;
	}
	.contentArea {
		padding-left: 50px;
	}
	#div-mobile .dropdown-menu>li.user-header {
		height: auto;
		padding: 20px 10px;
		text-align: center;
		min-height: 150px;
	}
	.dashboardPageWrapper {
		margin-top: 70px;
	}
	.headerBar {
		position: fixed;
		z-index: 999999;
		width: 100%;
		padding-right: 60px;
	}
}

@media screen and (min-width: 755px) and (max-width: 3000px) {
	#div-mobile {
		display: none;
	}
	.div-desktop {
		display: block;
	}
	.marQueeClass {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}
}

.bellcontainer {
	position: relative;
	/*  position: absolute; */
	/*top: 50%;
    left: 50%;
    margin-right: -50%;
    transform: translate(-50%, -50%);
    text-align: center;*/
}

.notifications {
	display: inline-block;
	/* position: relative; */
	padding: 0.2em 0.5em;
	background: #3498db;
	border-radius: 0.2em;
	font-size: 1.2em;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	margin-right: 10px;
}

.notifications::before, .notifications::after {
	color: #fff;
	text-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
}

.notifications::before {
	display: block;
	content: "\f0f3";
	font-family: "FontAwesome";
	transform-origin: top center;
}

.notifications::after {
	font-family: Arial;
	font-size: 0.7em;
	font-weight: 700;
	position: absolute;
	top: -5px;
	left: -20px;
	padding: 5px 8px;
	line-height: 100%;
	border: 2px #fff solid;
	border-radius: 60px;
	background: #3498db;
	opacity: 0;
	content: attr(data-count);
	opacity: 0;
	transform: scale(0.5);
	transition: transform, opacity;
	transition-duration: 0.3s;
	transition-timing-function: ease-out;
}

.notifications.notify::before {
	animation: ring 1.5s ease;
}

.notifications.show-count::after {
	transform: scale(1);
	opacity: 1;
}

@
keyframes ring { 0% {
	transform: rotate(35deg);
}

12
.5

 

%
{
transform


:

 

rotate


(-30
deg
);




}
25%
{
transform




:


 


rotate




(25
deg


);
}
37
.5

 

%
{
transform


:

 

rotate


(-20
deg
);




}
50%
{
transform




:


 


rotate




(15
deg


);
}
62
.5

 

%
{
transform


:

 

rotate


(-10
deg
);




}
75%
{
transform




:


 


rotate




(5
deg


);
}
100%
{
transform




:


 


rotate




(0
deg


);
}
}
/* notification bell end */
.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f1f1f1;
	min-width: 300px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1000;
	margin: 0;
	padding: 0;
	right: 10px;
	max-height: 400px;
	overflow-y: auto;
}

.dropdown-content li {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}

.dropdown-content li:hover {
	background-color: #ddd;
}

.dropdwn:hover .dropdown-content {
	display: block;
}

.loadingWrapper {
	width: 100vw;
	height: 100vh;
	background: #FFFFFF;
	opacity: 0.5;
	position: fixed;
	display: none;
	align-items: center;
	justify-content: center;
	z-index: 999999;
}

#loading {
	display: block;
	text-align: center;
}
</style>

<body class="">
	<div class="loadingWrapper">
		<div id="loading">
			<div>
				<img src="img/source.gif" alt="Loading">
			</div>
			<div
				style="color: #fff; font-size: 16px; padding: 10px; text-align: center; width: 100%; font-weight: bold;">Submitting
				the form please wait...</div>
		</div>
	</div>
	<script>
		try {
			if (typeof (Storage) !== "undefined") {
				if (localStorage.leftMenuPosition
						&& localStorage.leftMenuPosition == 0) {
					$('body').addClass('navigation-small');
				}
			}
		} catch (err) {
			console.log(err);
		}
	</script>

	<%
	Long userId =0l;
	if(session!=null && session.getAttribute("USER_INFO")!=null){
		Map<String, Object> userInfo = session != null
				? (Map<String, Object>) session.getAttribute("USER_INFO")
				: new HashMap<String, Object>();
		 userId = (Long) userInfo.get("USER_REC_ID");
	}else{
		 userId = null;
	}
	%>
	<script>
		function isEmpty(val) {
			if (val == null || val == undefined || val.toString().trim() == "") {
				return true;
			}
			return false;
		}

		$(function() {

			$(".menuToggle > a").click(function() {
				$("body").toggleClass("showFixedSideMenu");
				if ($("body").hasClass('showFixedSideMenu')) {
					var ct = 0;
					$(".submenu").each(function() {
						$(this).addClass('submenu' + ct);
						ct++;
					})
				} else {
					var submenuLength = $('.submenu').length;
					for (var i = 0; i < submenuLength; i++) {
						$('.submenu').removeClass('submenu' + i);
					}
				}
			});
			$(".closeMnu").click(function() {
				$("body").toggleClass("showFixedSideMenu");
				var submenuLength = $('.submenu').length;
				for (var i = 0; i < submenuLength; i++) {
					$('.submenu').removeClass('submenu' + i);
				}
			})

		})
		
		function checkExpireDate(){
			$.ajax({
				url:"generalPop_populateLoggedUserExpireDate.action",
				async:false,
				type:'post',
				success: function(expMsg){
					message=expMsg.message;
						return message;
				}
			});
		}
		
	</script>

	<div id="dialog" style="display: none"></div>

	<div class="contentArea">
		<div class="flexbox-container">
			<div class="flexbox-item headerBar">
				<div class="header">
					<div class="inner-logo">
						<!-- <img src="login_populateLogo.action?logoType=app_logo" alt="logo" /> -->
						<img src="login_populateLogo.action?logoType=app_logo" alt="logo" style="background-color:white;width:50px; height:50px;padding:1px;"/>
					</div>

					<div class="breadCrumb-wrapper div-desktop marQueeClass">

						<ul class="breadCrumbNavigation">
							<% if(request!=null){
								List<Menu> menus = (List<Menu>) request.getAttribute("breadCrumb");
								if (menus != null) {
									for (Menu menu : menus) {
							%>
							<li><a href="<%=menu.getUrl()%>"><%=menu.getLabel()%></a></li>
							<%
								}
								}
							}
								
							%>
							
						</ul>
						<marquee style="color: #fff;" width="70%" id="expMsg"></marquee>
					</div>


					<div class="dropdwn">


						 <div class="bellcontainer dropbtn" id="alertNotificationDiv"
							aria-expanded="true">
							<!-- <div class="notifications"></div> -->
							<ul class="dropdown-content">

							</ul>
						</div>


					</div>
					<div class="language-control div-desktop">
						<%-- <div class="btn-group">
							<button data-toggle="dropdown"
								class="btn btn-primary btn-hi-sts-transparent btn-sm">
								English <span class="caret"></span>
							</button>
							<ul class="dropdown-menu dropdown-menu-right dropdown-head">
								<%=(session.getAttribute("languageMenu"))%>
							</ul>
						</div> --%>
						<s:if test='#session.isAdmin =="true"'>
						<div class="btn-group">
						<button class="btn btn-primary btn-sm" id="Validatebutton">
								Validate License
							</button>
						</div>
</s:if>
						<div class="btn-group">
							<%=session!=null ? session.getAttribute("languageMenu") :""%>
						</div>

					</div>
					<div class="usr-image div-desktop">
						<%-- <img src="user_populateImage.action?id=<%=userId%>"
							class="circle-img avatar-rounded" alt="" height="30" width="30"> --%>

					</div>
					<div class="admin-area div-desktop">
						<ul class="nav">
							<li class="dropdown current-user">
								<button data-toggle="dropdown"
									class="btn btn-primary btn-hi-sts-transparent btn-sm"
									data-hover="dropdown" class="dropdown-toggle"
									data-close-others="true" href="#">
									<span class="username"><%=session!=null ? session.getAttribute("user") : ""%>
										(<%=session!=null ? session.getAttribute("role") :""%>)</span> <i
										class="clip-chevron-down"></i>
								</button>

								<ul class="dropdown-menu dropdown-menu-right dropdown-head">
									<li><a href="#"  onclick="gotouser()" > <i
											class="fa fa-user"></i> &nbsp;<s:text name="myProfile" />
									</a></li>
							    <li><a href="changePassword_editExp.action"> <i
											class="fa fa-lock"></i> &nbsp;<s:text name="changePwd" />
									</a></li>
									<sec:authorize ifAllGranted="system.prefernces.list">
										<li><a href="prefernce_list.action"> <i
												class="fa fa-gear"></i> &nbsp;<s:text name="Settings" />
										</a></li>
									</sec:authorize>
									<li class="divider"></li>
									<li><a href="logout"> <i class="fa fa-sign-out"></i>
											&nbsp;<s:text name="logout" />
									</a></li>
								</ul>
							</li>



						</ul>
					</div>

					<div id="div-mobile">
						<div class="container">
							<div id="mySidepanel" class="sidepanel">

								<a href="javascript:void(0)" class="closebtn"
									onclick="closeNav()"><i class="fa fa-times"
									aria-hidden="true"></i></a>

								<div class="dropdown user user-menu open">

									<ul class="dropdown-menu">
										<!-- User image -->
										<li class="user-header">
											<%--   <img class="usr-image" src="user_populateImage.action?id=<%=userId%>" class="img-circle"> --%>

											<p>
												<%--=session.getAttribute("user")--%>
												- (
												<%--=session.getAttribute("userFullName")--%>
												)
												<%--  <small>Registered on date</small> --%>
											</p>

											<p>
												<%=session!=null ?session.getAttribute("role") : ""%>
												- (<%=session!=null ? session.getAttribute("branchId") : ""%>)
												<%--  <small>Registered on date</small> --%>
											</p>

										</li>
										<!-- Menu Body -->
										<li class="user-body">
											<div class="row">
												<div class="col-xs-4 text-left">
													<label>Language</label>

												</div>


												<div class="col-xs-8 text-left">

													<div class="btn-group col-xs-12 langDropDown"
														style="padding: 0; width: 137px !important;">
														<%=session!=null ?session.getAttribute("languageMenu"):""%>
													</div>
												</div>

											</div>




											<div class="row settingsPanel">
												<div class="col-xs-7 text-left">
													<a href="changePassword_edit.action"><s:text
															name="changePwd" /></a>
												</div>

												<div class="col-xs-5 text-left">
													<%--  <a href="prefernce_list.action"><s:text name="Settings" /></a> --%>
													<a href="dashboard_list.action"><s:text
															name="Dashboard" /></a>
												</div>

											</div> <!-- /.row -->
										</li>
										<!-- Menu Footer-->
										<li class="user-footer clearfix">
											<div class="pull-left">
												<a href="user_detail.action?id=<%--=userId--%>"
													class="btn btn-default btn-flat btn-info">Profile</a>
											</div>
											<div class="pull-right">
												<a href="logout"
													class="btn btn-default btn-flat btn-warning"><s:text
														name="logout" /></a>
											</div>
										</li>
									</ul>
								</div>
							</div>
						</div>
						<button class="openbtn" onclick="openNav()">
							<span class="glyphicon glyphicon-list"></span>
						</button>
					</div>

				</div>
			</div>



			<div class="flexbox-item flexbox-item-grow dashboardPageWrapper">
				<!-- <div class="appContentWrapper marginBottom"> -->
				<decorator:body />
				<!-- </div> -->
				<footer>
					<s:if test="currentTenantId=='galaxyrice'">
						<div class="footer clearfix">
							<div class="pull-right">
								<div class="footer-inner">Powered by Kashtkar</div>
							</div>
						</div>
					</s:if>
					<s:else>
						<div class="footer clearfix">
							<div class="pull-right"> <div class="footer-inner">Powered by Horticulture Crops Directorate (HCD, Kenya)</div></div>
						</div>
					</s:else>
				</footer>
			</div>
		</div>


		<button type="button" id="enablePhotoModal"
			class="hide addBankInfo slide_open btn btn-sm btn-success"
			data-toggle="modal" data-target="#slideDPhotoModal"
			data-backdrop="static" data-keyboard="false">
			<i class="fa fa-plus" aria-hidden="true"></i>
		</button>

		<div id="slideDPhotoModal" class="modal fade" role="dialog">
			<div class="modal-dialog modal-sm">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" id="model-close-DPhoto-btn"
							class="close hide" data-dismiss="modal">&times;</button>
						<h4 class="modal-title" id="mhead"></h4>
					</div>
					<div class="modal-body">
						<div id="myCarousel" class="carousel slide" data-ride="carousel">
							<div class="carousel-inner" role="listbox" id="mbody"></div>

							<a class="left carousel-control" href="#myCarousel" role="button"
								data-slide="prev"> <span
								class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							</a> <a class="right carousel-control" href="#myCarousel"
								role="button" data-slide="next"> <span
								class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							</a>

						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"
							onclick="buttonDPhotoCancel()">
							<s:text name="close" />
						</button>
					</div>
				</div>

			</div>

		</div>



	</div>


	<div class="wrapper">
		<page:applyDecorator name="menu" page="/jsp/eseMenu.jsp" />
	</div>

	<script src="js/main.js?v=1"></script>

	<script>
	
	function gotouser(){
		var currentURL = window.location.href;
		var redirectAction="user_detail.action?id="+<%=userId%>+"&redirectContent="+currentURL;
	 	window.location.href=redirectAction; 
	}
		var message;
		jQuery(document).ready(function() {
			if( '<%=session.getAttribute("user")%>'=='null' || '<%=session.getAttribute("user")%>'==''){
				window.location.href = 'login_execute';
			}
			try{
			Main.init();
			}catch(e){
				
			}
			
			checkExpireDate();
			if(message!="" && !isEmpty(message)){
				$('#expMsg').text(message);
				}
		});
		
		 $("#Validatebutton").click(function() {
			 $(".loadingWrapper").css("display", "flex");
			 setTimeout(validateExpLic,200);
		    });
		
		function validateExpLic(){
			
			$.ajax({
				url:"farmer_validateAllexpLic.action",
				async:false,
				type:'post',
				success: function(expMsg){
					alert(expMsg);
					$(".loadingWrapper").css("display", "none");
				}
			}); 
		}
	</script>
</body>
</html>