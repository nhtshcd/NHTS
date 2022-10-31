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

<link rel="stylesheet" href="css/main.css?v=2.1">
<!-- <link rel="stylesheet" href="css/bluewood.css"> -->
<link rel="stylesheet" href="css/bluewood.css">
<link rel="stylesheet" href="css/theme.css?v=3.2">
<!-- <link rel="stylesheet" href="css/sandwisp.css"> -->


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
<link rel="stylesheet" href="css/new-ui.css?v=3.2">
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
		padding-left: 0px;
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

.contentArea {
	padding-left: 0px;
}

@media screen and (min-width: 755px) and (max-width: 3000px) {
	#div-mobile {
		display: none;
	}
	.div-desktop {
		display: block;
	}
}
</style>

<body class="">
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
	

function isEmpty(val){
	  if(val==null||val==undefined||val.toString().trim()==""){
	   return true;
	  }
	  return false;
	}
  

</script>

	<div id="dialog" style="display: none"></div>

	<div class="contentArea">
		<div class="flexbox-container">
			<div class="flexbox-item headerBar">
				<div class="header">
					<div class="inner-logo">
						<img src="login_populateLogo.action?logoType=app_logo" alt="logo" />
					</div>

					<div class="breadCrumb-wrapper div-desktop ">
						<div class="reportName" style="color: white; align-self: center;">
							<span><%= session.getAttribute("report") %></span>
						</div>
						<ul class="breadCrumbNavigation hide">
							<span class="pull-right loggedBranch"><s:property
									value="BreadCrumbs" /></span>
						</ul>
					</div>
				</div>
			</div>

			<div class="flexbox-item flexbox-item-grow dashboardPageWrapper">
				<!-- <div class="appContentWrapper marginBottom"> -->
				<decorator:body />
				<!-- </div> -->
				<footer>
					<div class="footer clearfix">
						<div class="pull-right">
							<div class="footer-inner">Powered by Horticulture Crops Directorate (HCD, Kenya)</div>
						</div>
					</div>
				</footer>
			</div>
		</div>
	</div>

	<script src="js/main.js"></script>
	<script>
		jQuery(document).ready(function() {
			Main.init();
		});
	</script>
</body>
</html>