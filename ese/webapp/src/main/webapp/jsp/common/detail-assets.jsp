<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
 
<script type="text/javascript">
    $(document).ready(function () {
    	var postdata= '<s:property value="postdata"/>';   //Redirecting Filter Data to List Page
    	if(postdata!=''){
    		postdata = postdata.replace(/&quot;/g,'"');
    		postdata = postdata.replace("\"{", "{");
    		postdata = postdata.replace("}\"", "}");
    				 $('#postdata').val(JSON.stringify(postdata));
    				}
        $('#cancel').on('click', function (e) {
            document.cancelform.currentPage.value = document.form.currentPage.value;
            document.cancelform.submit();
        });
       
        var brrd =  <%out.print("'" + request.getParameter("breadcrumb") + "'");%>
        var redirectContent = <%out.print("'" + request.getParameter("redirectContent") + "'");%>
        if(brrd!=null && brrd!='' && brrd!=undefined && brrd!='null'){
        	$(".breadCrumbNavigation").html('');
			$(".breadCrumbNavigation").append("<li><a href='"+redirectContent+"'>"+brrd+"</a></li>");
		
        }
        
    });

</script>