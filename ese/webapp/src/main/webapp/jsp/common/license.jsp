
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<head>
   <!--  <meta name="decorator" content="swithlayout"> -->
   
   <link rel="stylesheet" href="fonts/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="fonts/open-sans/css/open-sans.css">
        <link rel="stylesheet" href="fonts/raleway/css/raleway.css">
        <link rel="stylesheet" href="fonts/roboto/css/roboto.css">

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/perfect-scrollbar.css">
        <link rel="stylesheet" href="plugins/iCheck/skins/all.css">

        <link rel="stylesheet" href="css/responsive.css">
        <link rel="stylesheet" href="css/base.css">
        <link rel="stylesheet" href="css/theme.css">		
        
        
  
</head>
<body>

    <s:form name="form" cssClass="fillform" action="license_save">
        <s:hidden name="lic_skip" value="true" />
        
        <div class="licenseWrapper col-md-offset-4 col-md-4" style="margin-top:40px;">
        
        
        
	        <div class="center">
	            <img src="login_populateLogo.action?logoType=app_logo" />
	        </div>
	        
	        
	        <div class="well clearfix" style="margin:20px;">
	            <%--<div class=""><s:text name="info.license"/></div> --%>
	            
	                  
	                       
	                       
	                            <s:form name="loginform" action="login" theme="simple">
	
	                                <div class="error">
	                                    <s:actionerror />
	                                    <%
	                                 
	                                        if (request.getAttribute("error") != null) {
	                                    %>
	                                    <div class="errorMessage"
	                                         style="padding: 0; margin: 10px auto 0 auto; width: 82%;">
	                                        <li><span><%=request.getAttribute("error")%></span></li>
	                                    </div>
	                                    <%
	                                        }
	                                    %>
	                                </div>
									
									<form>
									  <div class="form-group">
									    <label for=""> <s:text name="info.license" /></label>
									    <s:textfield id="userName"  name="key" cssClass="form-control"/>
									  </div>
									  <div class="form-group">
									  	<button id="login.button" name="login" class="btn btn-sts pull-right"
                                                 type="button" onclick="document.form.submit();">
                                             <s:text name="save.button" />
                                         </button>
									  </div>
									</form>
	
	                                
	                                
	                            </s:form>
	
	                            
	                  
	
	               
	        </div>
	        
	        </div>
	        
	        
	    



    </s:form>
</body>