<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="<%=request.getParameter("layoutType")!=null && !request.getParameter("layoutType").equals("") ? request.getParameter("layoutType") : "swithlayout"%>">
<script type="text/javascript" src="plugins/zTree/js/jquery-1.4.4.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<!--<link href="https://fonts.googleapis.com/css2?family=Nunito+Sans&display=swap" rel="stylesheet">	
	 <link rel="stylesheet" href="css/treedemo.css?v=1.0" type="text/css">
	
	<link rel="stylesheet" href="plugins/zTree/css/demo.css" type="text/css">
<link rel="stylesheet" href="plugins/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css"> -->

	<!-- <link rel="stylesheet" href="plugins/zTree/css/metroStyle/metroStyle.css" type="text/css"> -->
	<script type="text/javascript" src="plugins/zTree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="plugins/zTree/js/jquery.ztree.core.js"></script>	
	<script src="https://balkan.app/js/OrgChart.js"></script>
</head>

<style>

div.MultiNestedList {
	display:none;
	margin: 0 auto;
	background:#f9f9f9;
	padding: 15px;
	min-height: 150px;
}
.MultiNestedList .ulWithleftBorder {
	border-left: 3px solid #009456;
}
.MultiNestedList ul {
    padding: 0em;    
}
.MultiNestedList ul li, ul li ul li {
    position:relative;
    top:5px;
    bottom:0;
    padding-bottom: 10px;
}
.MultiNestedList ul li ul {
    margin-left: 2.3em;
    /*border-left: 3px solid #009456;*/
}
.MultiNestedList li {
    list-style-type: none;	
	line-height:2.5em;
}
.MultiNestedList li a {
    padding:0 0 0 0px;
   /*  position: relative; */
    top:1em;
}
.MultiNestedList li a:hover {
    text-decoration: none;
}
.startNode li:first-child div.startNodeWrapper:before {
    content: "";
    display: inline-block;
    width: 25px;
    height: 30px;    
    left: 0em;
    top:1em;
    border: none!important;
    border-top: none;
    border-right: none;
    margin-left:-3px; 
}
.MultiNestedList a.addBorderBefore:before {
    content: "";
    display: inline-block;
    width: 3px;
    /*height: 28px;*/
    /* position: absolute; */
    left: -47px;
    top:-16px;
    /*border-left: 1px solid gray;*/    
}

.MultiNestedList li div.itemWrapper:before {
    content: "";
    display: inline-block;
    width: 25px;
    height: 30px;
    /* position: relative; */
    left: 0em;
    top:1em;
    /*border-top: 1px solid gray;*/
	border-bottom-left-radius: 20px;  
    border: solid 3px #009456;
    border-top: none;
    border-right: none;
    margin-left:-3px; 
}
.hasSubMenuLi li{
	font-weight:bold!important;  
}
.MultiNestedList ul li span{
	font-weight:bold;
}
.MultiNestedList img {
  
}
.tt {
  color:#009456;
  text-decoration:none; 
} 
.itemWrapper {
	display:flex;
}
.item {
	display: flex;
	align-items:center;
	padding: 10px;
	box-shadow: 0px 2px 8px #ccc;
	background: #fff;
	border: solid 1px #ccc;
	border-radius: 10px;
    width: 100%;
}
.icnImg {
	width:50px;
	height:50px;
	margin-right:10px;
}
.icnImg img {
	width:100%;
	height:auto;
}
.noLevelUl {    
    border-left: none!important;
	padding: 10px !important;
	min-width: auto;
	background: #d7ffd3;
	box-shadow: 0px 2px 8px #ccc;	
	margin-top: -10px!important;
	border-radius: 10px;
}
.text span.proTitle {
	color:green;
}
.item:before {
	content: '\25B6';
    color: green;    
    position: absolute;
    left: 12px;
	top:-6px;
	transform:translateY(50%);
	font-size:18px;
}
.startNodeWrapper .item:before {
	content: '';
}
	

</style>
	<script type="text/javascript">
	
	var tenantId="<s:property value='tenantId'/>";
	
	var enableTraceability = "<s:property value='enableTraceability'/>";
	var branchId='<s:property value="branchId"/>';
	var batcnH= '<%=request.getParameter("batchNo")%>';
	jQuery(document).ready(function(){
		
		$('.MultiNestedList ul li.hasSubmenu').each(function(){
			  $this = $(this);		 
			  $this.children("a").not(":last").removeClass().addClass("toogle");
			});
			if(batcnH!=null && batcnH!='' && batcnH!=undefined && batcnH!='null'){
				$('#batchNoos').addClass("hide");
				search();
			}else{
				$('#batchNoos').removeClass("hide");
				$("#resultset").hide();
			}
			
			function populateReceiptNos(val){
				$.post("traceabilityViewReport_populateReceiptNos",function(result){
					var data =jQuery.parseJSON(result);
					insertOptions("batchNo",data);
				});
			}
			
	});	
	
	function showDiv(oj){
		$this = $(oj);
		  $this.closest("li").children("ul").toggle();
		  $this.children("i").toggle();		  
		  return false;
	}
	
	
	
</script>

		<s:form id="form">
		<div class="appContentWrapper marginBottom" id="batchNoos">
		
		<div class="row">
						<div class="container-fluid">
							<div class="notificationBar">
								<div class="error">
									<p class="notification">
										<%-- <span class="manadatory">*</span>
										<s:text name="reqd.field" /> --%>
									<div id="validateError" style="text-align: center;"></div>
									<div id="validatePriceError" style="text-align: center;"></div>
									</p>
								</div>
                                </div>
                                    </div>

							</div>

			<a id="link" class="" href="#" target="_blank"></a>

			
			<section class='reportWrap row'>
				<div class="gridly">
				
				<div class="filterEle hide">
					<label for="txt"><s:property value="%{getLocaleProperty('Distillation BatchNo')}"/></label>
					<div class="form-element">
						<s:select name="distilBatchNo" id="distilBatchNo"
							list="{}" headerKey=""
							headerValue="%{getText('txt.select')}"
							cssClass="form-control input-sm select2" />
					</div>
				</div>
					
				<div class="filterEle">
					<label for="txt">UCR Kentrade<sup
										style="color: red;">*</sup></label>
					<div class="form-element">
					<s:textfield id="batchNo" name="batchNo" cssClass="form-control" />
					
					</div>
				</div>	
				
				
			
					<%-- <s:iterator value="reportConfigFilters" status="status">
						<div class='filterEle'>
							<label for="txt"><s:property value='%{getLocaleProperty(label)}'/></label>
							<div class="form-element">
								<s:select name="%{field}" list="{}"
									headerKey="" headerValue="%{getText('txt.select')}"
									class="form-control input-sm select2" />
									<s:set var="personName" value="%{method}"/>
							</div>
						</div>
					</s:iterator> --%>
					
					<div class='filterEle' style="margin-top: 2%;margin-right: 0%;">
						<button type="button" class="btn btn-success btn-sm" id="generate"
						aria-hidden="true" onclick="search()">
						<b class="fa fa-search"></b><!-- <button type="button" class="btn btn-success btn-sm" id="generate"
						aria-hidden="true" onclick="exportKML()">
						<b class="fa fa-search"></b> -->
						</button>
						<button type="button" class="btn btn-danger btn-sm"
						aria-hidden="true" id="reset" onclick="resets()">
						<b class="fa fa-close"></b>
						</button>
					</div>
				</div>
			</section>
			</div>
		</s:form>
				<div class="appContentWrapper marginBottom" id="resultset">
	<div class="MultiNestedList" id="treeDemo" wfd-invisible="true" style="display: block;">
   </div>
</div>

 <SCRIPT type="text/javascript">
 function resets(){
	document.getElementById("batchNo").value='';
	$("#resultset").hide();
 }
 

 function exportKML(){
 	
 	
		var lotNo=  $("#batchNo").val();


 	if(!isEmpty(lotNo)){
 		$.ajax({
 			 type: "POST",
 	        async: false,
 	        url: "traceabilityViewReport_processMapBatchNoHigh.action",
 	       data: {selectedDistillationBatchNo : lotNo},
 	        success: function(resp) {
 	        	//closeNav();
 	        	if(jQuery.isEmptyObject(resp)||resp=='')
 	             {
 	        		 alert("Batch Number Does Not Exist");
 	        		 return false;
 	             }else{
 	            	 
 	            	 var arr=JSON.parse(resp);
 	            	 var chart = new OrgChart(document.getElementById("tree"), {
          		    	template: "polina",
          		    	 layout: OrgChart.mixed,
          		    	showYScroll: OrgChart.scroll.visible,
          				showXScroll: OrgChart.scroll.visible,
          		        enableDragDrop: false,
          		        menu: {
          		            pdf: { text: "Export PDF" }/* ,
          		            png: { text: "Export PNG" },
          		            svg: { text: "Export SVG" },
          		            csv: { text: "Export CSV" } */
          		        },
          		        
          		        nodeBinding: {
          		            field_0: "name",
          		            field_1: "title"
          		            
          		        },
          		        nodes: arr
          		    });
 	            	 
 	            	 
 	             }
 	        }
 		});
 		
 	}else{
 		alert("Please Enter Batch Number");
 	}
 	
 	
 	
 	
 }

	function search(){
		var batchNo=  $("#batchNo").val();
		 if(batchNo==null || batchNo==''){
				if(batcnH!=null && batcnH!=''){
					batchNo =batcnH;
				}
			} 
		
		
		if(batchNo!=null && batchNo!='' && batchNo!=undefined && batchNo!='null'){
		$('.MultiNestedList').html('');
		jQuery("#validateError").text('');
		var setting = {
				data: {
					simpleData: {
						enable: true
					}
				}
			};
		
			$.ajax({
				 type: "POST",
		        async: false,
		        url: "traceabilityViewReport_processBatchNo.action",
		        data: {selectedDistillationBatchNo : batchNo},
		        success: function(result) {
		        	
		        	if(!result){
		        		jQuery("#validateError").text('<s:property value='%{getLocaleProperty("existing.batchpon")}' />');
	 	        		 $("#resultset").hide();
		             }else{
		            	 var	res=JSON.parse(result);
		             res.forEach(function (item, index) {
		            	 var ull = $("<ul/>").addClass("startNode");
		            	 ull.append(formli(item,index));
		            	 $('#treeDemo').append(ull);
		            	 $("#resultset").show();
		            	 jQuery("#validateError").text('');
		             });
		        	//$.fn.zTree.init($("#treeDemo"), setting, res);
		             }
		        }
			});
		
		
		}else{

	 		jQuery("#validateError").text('<s:property value='%{getLocaleProperty("empty.batchpon")}' />');
			return false;
		}
		
		
	}
	
	function formli(item,index){
		 var li = $("<li/>");
    	 var name =item.name;
    	 var children =item.children;
    	var idcon =item.iconOpen;
    	 if(children!=undefined && children!=null && children.length>0){
    	 var pdiv = $("<div/>").addClass("itemWrapper");
    	 if(index==0){
    		 $(pdiv).addClass("startNodeWrapper");
    	 }
    	 var item = $("<div/>").addClass("item").append('<div class="icnImg"><img src="'+idcon+'" /></div>');
    	 var aa =$("<a/>").addClass("tt").attr("href","javascript:void();").attr('onClick',"showDiv(this)");
    	 var ii =$("<i/>").addClass("fa fa-caret-down");
    	 var ii1 =$("<i/>").addClass("fa fa-caret-right").css("display","none");
    	 aa.append(ii).append(ii1);
    	 var textts = $("<div/>").addClass("text").append(name).append(aa);
    	 item.append(textts);
    	 pdiv.append(item);
    	 li.append(pdiv);
    
    		 $(li).addClass("hasSubmenu");
    		 var ull = $("<ul/>");
    		 var isnochild =false;
    		 children.forEach(function (itemm, index) {
    			 ull.append(formli(itemm));
    			 if(itemm.children==undefined || itemm.children.length<=0){
    				 isnochild =true;
    			 }
    		  });
    		 if(isnochild){
    			 $(ull).addClass("noLevelUl");
    		 }
    		 
    		 if(children.length>1){
    			 $(ull).addClass("ulWithleftBorder");
    		 }
    		
    		 li.append(ull);
    		 return li;
    	 }else  if(name!=null && name!=undefined && name!=''){
    		 var lspri = $("<div/>").addClass("text");
    		 var lspi = $("<span/>").addClass("proTitle").append(name);
    		 lspri.append('<img class="icnImg" src="'+idcon+'" />').append(lspi);
    		 li.append(lspri);
    		 return li;
    	 }else{
    		 return '';
    	 }
    	
	}
	
	function isNumber(evt) {
	    evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	        return false;
	    }
	    return true;
	}
	</SCRIPT>