<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8" %>  
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>
<html lang="en" class="no-js">
   <head>
      <title>
         <decorator:title default="Ke-HTS"/>
      </title>
      <script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-hover-dropdown.min.js"></script>
<script src="js/jquery.mousewheel.js"></script>
<script src="js/remodal.min.js"></script>
<script src="js/perfect-scrollbar.js"></script>
<script src="plugins/jquery_ui/jquery-ui.min.js" type="text/javascript"></script>
      <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
      <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
      <meta http-equiv="Pragma" content="no-cache"/>
      <meta http-equiv="Expires" content="0"/>
      <meta http-equiv="X-Frame-Options" content="DENY" />
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
      <meta name="apple-mobile-web-app-capable" content="yes">
      <meta name="apple-mobile-web-app-status-bar-style" content="black">
      <meta content="" name="description" />
      <!-- <link rel="shortcut icon" href="/favicon.ico"> -->
      <link rel="shortcut icon" type="image/x-icon" href="login_populateLogo.action?logoType=favicon" />
      <link rel="apple-touch-icon" href="/apple-touch-icon.png">
      
      <style>
      	.pr-login-wrap {			
			background-image:url('dist/login-bg.jpg')!important;
		}
		.pr-custom-radio {
			padding:0 5px;
		}
		.pr-modal.top .modal-header .modal-title {
		    font-weight: 700;
		    font-size: 18px;
		    line-height: 28px;
		    color:#1E2336!important;
		}		
		
		  

.faEye {
	width: 35px;
	height: 25px;
	background: url("img/eye.png") no-repeat;
	border: none;
	text-indent: -2000px;
	cursor: pointer;
}

.faEyeSlash {
	width: 35px;
	height: 25px;
	background: url("img/eyeSlash.png") no-repeat;
	border: none;
	text-indent: -2000px;
	cursor: pointer;
}

.input-container {
    display: flex;
    width: 95%;
    border: none;
}

		
      </style>
   </head>
   <body class="pr-sidebar-active pr-sec-sidebar-active">
   	<div class="loginPageContainer">
   	  <decorator:body/>
    </div>
    <script src="dist/runtime.js" defer></script>
    <script src="dist/polyfills.js" defer></script>
    <script src="dist/styles.js" defer></script>
    <script src="dist/scripts.js" defer></script>
    <script src="dist/vendor.js" defer></script>
    <script src="dist/main.js" defer></script>
    <script src="js/main.js" defer></script>
    <script src="dist/assets/js/custom.js" defer></script>
    <script>
         jQuery(document).ready(function () {
             Main.init();
         
         });
     </script>
   </body>
</html>