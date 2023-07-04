<head>
<META name="decorator" content="swithlayout">
</head>
<body>
	<%@ include file="/jsp/common/grid-assets.jsp"%>
	<%@ include file="/jsp/common/form-assets.jsp"%>
	<script type="text/javascript">
	var typez =<%out.print("'" + request.getParameter("id") + "'");%>;
	$(document).ready(function(){
		
    	var tenant='<s:property value="getCurrentTenantId()"/>';
    	
	
	    
		var table =  	createDataTable('detail',"catalogue_data.action");
	   
	}); 
	
	function getCatalogues(){
		//populateCatalougeMaster
		var cat = $.ajax({
			url: 'catalogue_populateCatalougeMaster.action',
			async: false, 
			type: 'post',
			success: function(data, result) {
			}
		}).responseText;
		return cat.replace("{","").replace("}","");
	}
	
	function getStatus(){
		var cat = $.ajax({
			url: 'catalogue_populateStatus.action',
			async: false, 
			type: 'post',
			success: function(data, result) {
			}
		}).responseText;
		return cat.replace("{","").replace("}","");
	}

 	function closeButton(){
 		refreshPopup();
 		refreshEduPopup();
 	}
 	
 	
	function detailPopup(idd) {
		var ids = idd;
		$("#detailDataTitle").empty();
		$("#detailDataHead").empty();

		$("#detailDataBody").empty();
		if (ids != '') {
			var head='<s:property value="%{getLocaleProperty('catalogueDetailHead')}" />';
			if (head != '') {	
				var tr = $("<tr/>");
				var headArrs = head.split(',');
				$.each(headArrs, function(a, val) {
					
					var td = $("<td/>");
					td.text(val);
					tr.append(td);

				});
				$("#detailDataHead").append(tr);
			}
			var title ='<s:property value="%{getLocaleProperty('details')}" />';
			$("#detailDataTitle").append(title);
		}
		try {
			$.ajax({
				type : "POST",
				async : true,
				url : "catalogue_methodQuery.action?id="+idd,
				success : function(result) {
					var jsonValue = $.parseJSON(result);
					$.each(jsonValue, function(k, value) {
						var tr = $("<tr/>");
						$.each(value, function(a, val) {
							var td = $("<td/>");
							td.text(val);
							tr.append(td);
						});
						$("#detailDataBody").append(tr);
					});
				}
			});
			document.getElementById("enableDetailPopup").click();
		} catch (e) {
			//alert(e);
		}

	}
 	
 	function addCatalogue(idd){
 		//alert("idd"+idd)
 		$(".cerror").empty();
 		if(idd==1){
 			//alert("idd"+idd)
 			$('#add').removeClass("hide");
 	 		$('#edit').addClass("hide");
 		}
 		else{
 			$('#add').addClass("hide");
 	 		$('#edit').removeClass("hide");
 		}
 		var typez = getElementValueById("typez");
 		var catName = getElementValueById("catName");
 		var statusZ =$('input:radio[name="farmCatalogueStatus"]:checked').val();
 		if(isEmpty(typez)){
 			$(".cerror").text('<s:text name="empty.type"/>');
 			return "false";
 		}else if(isEmpty(catName)){
 			$(".cerror").text('<s:text name="empty.CatalogueName"/>');
 			return "false";
 		}else if(isEmpty(statusZ)){
 			$(".cerror").text('<s:text name="empty.status"/>');
 			return "false";
 		}
 		/* else if(!isEmpty(catName)){
 			 var alphabetsRegx = /^[a-zA-Z0-9 ]*$/;
 			 var result = alphabetsRegx.test(catName);
 			 if(!result){
 				$(".cerror").text('<s:text name="pattern.name"/>');
 				return "false";
 			 }		 
 		} */
 		
 		/* var dataa = {
 				'farmCatalogue.name':catName,
 				'farmCatalogue.typez':typez,
 				'farmCatalogue.status':statusZ	
		} */
		var dataa =new FormData();
 			dataa.append( 'catalogueName', catName );
 		dataa.append( 'typez', typez );
 		dataa.append( 'status', statusZ );
 		console.log(dataa); 	
 	 	var url ='catalogue_populateCreate.action';
 	 	if(idd==2){
 	 		dataa.append('id',$('#catId').val());
 	 		url ='catalogue_populateUpdate.action';
 	 	}
 		
 	 	$.ajax({
			 url:url,
		     type: 'post',
		     dataType: 'json',
		     processData: false,
		     contentType: false,
		     data: dataa,
		     success: function(result) {
		    	 resetData("catForm");
		    	 showPopup(result.msg,result.title);
		    	// jQuery("#detail").jqGrid('setGridParam',{url:"catalogue_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
		    	 $('#detail').DataTable().ajax.reload();
		     },
		     error: function(result) {
		    	 showPopup("Catalogue Name already Exists","Error");
		     	 window.location.href="catalogue_list.action";
		     }
		}); 
 	}
 	
 	function resetData(id){
 		$("#"+id)[0].reset();
 		$("#"+id).trigger("reset");
 		 $('.select2').select2();
 	}
 	function onCancelSave(){
 	    $("#typez option[value='']").prop("selected", true).trigger('change');
 		$('#catName').val('');
 		$('input[name="farmCatalogueStatus"]').attr('checked',false);
 		
 	}
 	function buttonDataCancel(id) {
		document.getElementById(id).click();
	}
 	function ediFunction(val){
 		var son = JSON.parse(val);


 		if($('#addCat').attr("aria-expanded")=='false' || $('#addCat').attr("aria-expanded")==undefined){
 			$('#addCat').click();
 		}
 		
 		$('#typez').val(son.typez).select2();
 		$('#catName').val(son.name);
 	
 		$("input[name=farmCatalogueStatus][value='"+son.status+"']").prop("checked",true);
 		
 		$('#add').addClass("hide");
 		$('#edit').removeClass("hide");
 		//alert(son.id);
 		$('#catId').val(son.id);
 		
 		/* showPic(son.typez);
 		if(son.doc!=null && son.doc!='' && son.doc!=undefined){
 			$('#phIco').removeClass('hide');
 			$('#phIco').attr("onclick",'enablePhotoModal('+son.doc+')');
 		}else{
 			$('#phIco').addClass("hide")
 		} */
 	}
</script>

	
	<div class="appContentWrapper marginBottom">
	 <div  class="dataTable-btn-cntrls" id="detailBtn"> 
	     <sec:authorize ifAllGranted="profile.catalogue.create">
	     
	     
	  	 <ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="collapse" href="#catalogueAccordian" onclick="onCancelSave()"> <i
							id="addCat"	class="fa fa-plus"></i>Add
						</a></li>
					</ul> 
						
					
			<%-- <a data-toggle="collapse" data-parent="#accordion"
				href="#catalogueAccordian" id="addCat"
				class="btn btn-sts marginBottom"> <s:text name="create.button" />
			</a> --%>
			
					<!-- <ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="pill" href="#catalogueAccordian"> <i
								class="fa fa-plus"></i>Add
						</a></li>
					</ul> -->
						
					</sec:authorize>
				 </div>
 
			<div id="catalogueAccordian" class="panel-collapse collapse">
				<div class="formContainerWrapper">
					<h2>
						<s:property value="%{getLocaleProperty('info.catalogue')}" />
					</h2>
				</div>
				<div class="error cerror"></div>
				<form name="catForm" id="catForm">
				<s:hidden name="id" id="catId" />
					<div class="flex-layout filterControls">
						<div class="flexItem">
							<label for="txt"><s:text name="catalogue.typez" /> <sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:select id="typez" name="farmCatalogue.typez" list="typezList"
									headerKey="" theme="simple"
									headerValue="%{getText('txt.select')}"
									cssClass="form-control input-sm select2" />
							</div>
						</div>


						<div class="flexItem">
							<label for="txt"><s:text name="catalogue.name" /><sup
								style="color: red;">*</sup></label>
							<div class="form-element">
								<s:textfield name="farmCatalogue.name" theme="simple"
									cssClass="form-control input-sm" maxlength="250" id="catName" />
							</div>
						</div>
						
						
						<div class="flexItem">
							<label for="txt"><s:text name="status" /><sup
								style="color: red;">*</sup></label>
							<div class="">
								<label class="radio-inline"> <input type="radio"
									value="1" name="farmCatalogueStatus">
								<s:text name="%{getLocaleProperty('statuss1')}" />
								</label> <label class="radio-inline"> <input type="radio"
									value="0" name="farmCatalogueStatus">
								<s:text name="%{getLocaleProperty('statuss0')}" />
								</label>

								<%-- <button type="button" class="btn btn-sts" id="add"
									onclick="addCatalogue()">
									<b><s:text name="save.button" /></b>
								</button> --%>
								<button type="button" class="btn btn-sts" id="add"
									onclick="addCatalogue(1)">
									<b><s:text name="save.button" /></b>
								</button>

								<button type="button" class="btn btn-sts hide" id="edit"
									onclick="addCatalogue(2)">
									<b><s:text name="save.button" /></b>
								</button>												
								<button type="button" class="back-btn btn btn-sts"
										onclick="onCancel()">
										<b><FONT color="#FFFFFF"><s:text name="Cancel" />
										</font></b>
								</button>		
			
							</div>
						</div>

					</div>
				</form>
			</div>
		
		
		<div id="baseDiv" style="width: 100%">
			<table id="detail" class="table table-striped" >
				<thead >
					<tr id="headerrow">

					<%-- 	<s:if test='branchId==null'>
							<th width="20%" id="branch" class="hd"><s:text name="app.branch" /></th>
						</s:if>

						<s:if
							test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<th  width="20%" id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
						</s:if> --%>


						<th   width="20%" id="code" class="hd"><s:text name="catalogue.code" /></th>
						<th   width="20%" id="typez" class="hd"><s:text name="catalogue.typez" /></th>
						<th   width="20%" id="name" class="hd"><s:text name="catalogue.name" /></th>
						<th   width="20%" id="status" class="hd"><s:text name="status" /></th>
						<th   width="10%" id="audit" class="hd noexp"><s:text name="Audit" /></th>
						<th   width="20%" id="edit" class="hd"><s:text name="action" /></th>
						
							
					</tr>
				</thead>

			</table>
		</div>
		

	</div>

	<%--<div class="appContentWrapper marginBottom">
		<div style="width: 99%;" id="baseDiv">
			<table id="detail"></table>
			<div id="pagerForDetail"></div>
		</div>
	</div> --%>

	<s:form name="createform" action="catalogue_create">
	</s:form>

	<s:form name="updateform" action="catalogue_detail">
		<s:hidden name="id" />
		<s:hidden name="currentPage" />
	</s:form>
</body>