<%@ include file="/jsp/common/grid-assets.jsp"%>
<%@ include file="/jsp/common/form-assets.jsp"%>
<head>
<META name="decorator" content="swithlayout">
</head>
<body>

	<script type="text/javascript">
	var loadCountries;
	var loadstate;
	var loadDistricts;
	var loadCities;
	var gramPanchayatEnable = "<s:property value='gramPanchayatEnable'/>";
	//var loadPanchayat;
	
	$(document).ready(function(){	
		loadLocationValues();
		
		
		 $('select').select2();
		var countrytable =  	createDataTable('countrydetails',"country_data.action");
		var statetable =  	    createDataTable('statedetails',"state_data.action");
		var districttable =  	createDataTable('districtDetails',"locality_data.action");
		var citytable =  	    createDataTable('citydetailss',"municipality_data.action");
		var villagetable =  	createDataTable('villagedetails',"village_data.action");
		 
		 
	});

	 function resetLocationValues(){
		 var c = $.ajax({
				url: 'village_populateCountryList.action',
				async: false, 
				type: 'post',
				success: function(data, result) {
					if (!result) 
						alert('Failure to retrieve');
					}
			}).responseText;
			loadCountries=c.replace("{","").replace("}","");
			
			var state = $.ajax({
							url: 'village_populateStateList.action',
							async: false, 
							type: 'post',
							success: function(data, result) {
								if (!result) 
									alert('Failure to retrieve.');
								}
						}).responseText;
			loadstate=state.replace("{","").replace("}","");
			
			var district = $.ajax({
				url: 'village_populateLocalityList.action',
				async: false, 
				type: 'post',
				success: function(data, result) {
					if (!result) 
						alert('Failure to retrieve.');
					}
			}).responseText;
			loadDistricts=district.replace("{","").replace("}","");
			
			var city = $.ajax({
				url: 'village_populateCityList.action',
				async: false, 
				type: 'post',
				success: function(data, result) {
					if (!result) 
						alert('Failure to retrieve.');
					}
			}).responseText;
			loadCities=city.replace("{","").replace("}","");
			
			var grampanchayat = $.ajax({
				url: 'village_populateGrampanchayatList.action',
				async: false, 
				type: 'post',
				success: function(data, result) {
					if (!result) 
						alert('Failure to retrieve.');
					}
			}).responseText;
			loadPanchayat=city.replace("{","").replace("}","");
	} 

function loadLocationValues(){

	var c = $.ajax({
		url: 'village_populateCountryList.action',
		async: false, 
		type: 'post',
		success: function(data, result) {
			if (!result) 
				alert('Failure to retrieve');
			}
	}).responseText;
	loadCountries=c.replace("{","").replace("}","");
	
	var state = $.ajax({
					url: 'village_populateStateList.action',
					async: false, 
					type: 'post',
					success: function(data, result) {
						if (!result) 
							alert('Failure to retrieve.');
						}
				}).responseText;
	loadstate=state.replace("{","").replace("}","");
	
	var district = $.ajax({
		url: 'village_populateLocalityList.action',
		async: false, 
		type: 'post',
		success: function(data, result) {
			if (!result) 
				alert('Failure to retrieve.');
			}
	}).responseText;
	loadDistricts=district.replace("{","").replace("}","");
	
	var city = $.ajax({
		url: 'village_populateCityList.action',
		async: false, 
		type: 'post',
		success: function(data, result) {
			if (!result) 
				alert('Failure to retrieve.');
			}
	}).responseText;
	loadCities=city.replace("{","").replace("}","");
	
	var grampanchayat = $.ajax({
		url: 'village_populateGrampanchayatList.action',
		async: false, 
		type: 'post',
		success: function(data, result) {
			if (!result) 
				alert('Failure to retrieve.');
			}
	}).responseText;
	loadPanchayat=city.replace("{","").replace("}","");
}

<%--function loadVillage(){
	jQuery("#detail").jqGrid({
			   	url:'village_data.action',
			   	editurl:'village_populateVillageUpdate.action',
			   	pager: '#pagerForDetail',
			   	mtype: 'POST',
			   	datatype: "json",	
			   	styleUI : 'Bootstrap',
			   	colNames:[	
						    <s:if test='branchId==null'>
							'<s:text name="app.branch"/>',
						   </s:if>
							<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							'<s:text name="app.subBranch"/>',
						</s:if>
							
			  		   	  '<s:text name="village.code"/>',
			  		   	  
			  		   	'<s:property value="%{getLocaleProperty('village.name')}"/>',
			  		    <s:if test='gramPanchayatEnable==1'>
			  		  
				  		  '<s:property value="%{getLocaleProperty('gramPanchayat.name')}"/>',
				  		  </s:if>
				  		 '<s:property value="%{getLocaleProperty('city.name')}" />',
				  		  '<s:property value="%{getLocaleProperty('locality.name')}"/>',
				  		 '<s:property value="%{getLocaleProperty('state.name')}"/>',
				  		'<s:property value="%{getLocaleProperty('country.name')}"/>',
				  		'<s:text name="Actions"/>'
						 ],
			   	colModel:[
			   		<s:if test='branchId==null'>
					{name:'branchId',index:'branchId',width:100,sortable: false,width :125,search:true,stype: 'select',searchoptions: { value: '<s:property value="branchFilterText"/>' }},	   				   		
				</s:if>
					
					<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
		   			{name:'subBranchId',index:'subBranchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: { value: '<s:property value="childBranchFilterText"/>' }},	   				   		
		   		</s:if> 
		   			
		   			{name:'code',index:'code',width:50,sortable:true,editable: true},
		   			{name:'name',index:'name',width:90,sortable:true,editable: true},
	   			 <s:if test='gramPanchayatEnable==1'>
	   				//{name:'gp.name',index:'gp.name',width:90,sortable:true,editable: true},
	   				{name:'selectedGramPanchayat',index:'selectedGramPanchayat',width:90,sortable:true,editable: true,edittype: "select", editrules:{required: true}},
	   			</s:if>
			   			{name:'selectedCity',index:'selectedCity',width:90,sortable:true,editable: true,edittype: "select", editrules:{required: true},
			   				
			   			 editoptions: {
		                		dataEvents: [ 
		   			   			          {
		   			   			            type: "change",
		   			   			            fn: function () {
		   			   			            	//alert($(this).val());
		   			   			            	var id=$(this).closest('tr').attr('id')+"_selectedGramPanchayat";
		   			   			         		 listGramPanchayatz($(this).val(),id);
		   			   			            }
		   			   			        }]
		                        }
			   				
			   				
			   			},
			   			{name:'selectedDistrict',index:'selectedDistrict',width:90,sortable:true,editable: true,edittype: "select", editrules:{required: true},
			                  editoptions: {
			                		dataEvents: [ 
			   			   			          {
			   			   			            type: "change",
			   			   			            fn: function () {
			   			   			            	//alert($(this).val());
			   			   			            	var id=$(this).closest('tr').attr('id')+"_selectedCity";
			   			   			        		
			   			   			          		listMunicipalityz($(this).val(),id);
			   			   			          var gid=$(this).closest('tr').attr('id')+"_selectedGramPanchayat";
			   			   			         		 listGramPanchayatz("",gid);
			   			   			            }
			   			   			        }]
			                        }
					   		},
			   			 {name:'selectedState',index:'selectedState',width:90,sortable:true,editable: true,edittype: "select", editrules:{required: true},
		                  editoptions: {
		                		dataEvents: [ 
		   			   			          {
		   			   			            type: "change",
		   			   			            fn: function () {
		   			   			            	//alert($(this).val());
		   			   			            	var id=$(this).closest('tr').attr('id')+"_selectedDistrict";
		   			   			          		var cid=$(this).closest('tr').attr('id')+"_selectedCity";
		   			   			          		listMunicipalityz("",cid);
		   			   			          		listLocalitz($(this).val(),id);
		   			   			          var gid=$(this).closest('tr').attr('id')+"_selectedGramPanchayat";
	   			   			         		 listGramPanchayatz("",gid);
		   			   			            }
		   			   			        }]
		                        }
				   		}, 
				   		{name:'selectedCountry',index:'selectedCountry',width:50,sortable:true,editable: true,edittype: "select", editrules:{required: true},
				   			editoptions: {
		                		dataEvents: [ 
		   			   			          {
		   			   			            type: "change",
		   			   			            fn: function () {
		   			   			            	//alert($(this).val());
		   			   			            	var id=$(this).closest('tr').attr('id')+"_selectedState";
		   			   			          		var lid=$(this).closest('tr').attr('id')+"_selectedDistrict";
		   			   			         		 var cid=$(this).closest('tr').attr('id')+"_selectedCity";
	   			   			          		
		   			   			          		listState($(this).val(),id);
		   			   			          		listLocalitz("",lid);
		   			   			        	  listMunicipalityz("",cid);
		   			   			        var gid=$(this).closest('tr').attr('id')+"_selectedGramPanchayat";
  			   			         		 listGramPanchayatz("",gid);
		   			   			            }
		   			   			        }]
		                        }
				   		},
			   			{name:'act',index:'act',width:60,sortable:false,formatter: "actions",formatoptions: {keys: true,
			   			onEdit : function(val){
			   				$('#detail').setColProp('selectedCountry', { editoptions: { value: loadCountries} });
			   				var dataa = {
			   						selectedCountry:jQuery('#' + val + '_selectedCountry').val(),
			   				}
			   				getAjaxValue("village_populateState.action",dataa,val,"_selectedState");
			   				
			   				
			   				/***/
			   				dataa = {
			   						selectedState:jQuery('#' + val + '_selectedState').val(),
			   				}
			   				getAjaxValue("village_populateLocality.action",dataa,val,"_selectedDistrict");
			   				
			   				dataa = {
			   						selectedDistrict:jQuery('#' + val + '_selectedDistrict').val(),
				   			}
			   				getAjaxValue("village_populateCity.action",dataa,val,"_selectedCity");
			   				
			   				dataa = {
			   						selectedCity:jQuery('#' + val + '_selectedCity').val(),
				   			}
			   				getAjaxValue("village_populateGramPanchayat.action",dataa,val,"_selectedGramPanchayat");
			   				
			   			},
			   			delOptions: { url: 'village_delete.action' ,
				   			afterShowForm:function ($form) {
                        	    $form.closest('div.ui-jqdialog').position({
                        	        my: "center",
                        	        of: $("#detail").closest('div.ui-jqgrid')
                        	    });
                        	},	
                        	
                        	afterSubmit: function(data) 
                            {
                        	  var json = JSON.parse(data.responseText);
								//document.getElementById("validateErrorCate").innerHTML=json.msg;
								jQuery("#detail").jqGrid('setGridParam',{url:"village_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
								showPopup(json.msg,json.title);
								jQuery('.ui-jqdialog-titlebar-close').click();
                            }
		   		
				   		} ,
				   		
				   		
				   		afterSave: function (id, response, options) {
                            var json = JSON.parse(response.responseText);
								jQuery("#detail").jqGrid('setGridParam',{url:"village_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');

								showPopup(json.msg,json.title);
								jQuery('.ui-jqdialog-titlebar-close').click();
                        }
			   		}}	
				],
			   	height: 301, 
			    width: $("#baseDiv").width(), // assign parent div width
			    scrollOffset: 20,
			   	rowNum:10,
			   	rowList : [10,25,50],
			    sortname:'id',			  
			    sortorder: "desc",
			    /* caption:'<s:text name="list"/>', */
			    viewrecords: true, // for viewing noofrecords displaying string at the right side of the table
			 	onSelectRow: function(id){ 
				 	var myGrid = jQuery('#detail'),
			        selRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
			        celValue = myGrid.jqGrid ('getCell', selRowId, 'id');
				},	
		        onSortCol: function (index, idxcol, sortorder) {
			        if (this.p.lastsort >= 0 && this.p.lastsort !== idxcol
		                    && this.p.colModel[this.p.lastsort].sortable !== false) {
		                $(this.grid.headers[this.p.lastsort].el).find(">div.ui-jqgrid-sortable>span.s-ico").show();
		            }
		        },
		        loadComplete: function() {
		        	//alert('qq1'+loadstate);
		        	$('#detail').setColProp('selectedCountry', { editoptions: { value: loadCountries} });
		      		$('#detail').setColProp('selectedState', { editoptions: { value: loadstate} });
		      		$('#detail').setColProp('selectedDistrict', { editoptions: { value: loadDistricts} });
		      		$('#detail').setColProp('selectedCity', { editoptions: { value: loadCities} });
		      		$('#detail').setColProp('selectedGramPanchayat', { editoptions: { value: loadPanchayat} });
		      		
		      		$(".ui-inline-save span").removeClass("glyphicon").removeClass("glyphicon-save");
		            $(".ui-inline-save span").addClass("fa").addClass("fa-save").addClass("inline-gridSave");
		            $(".ui-inline-cancel span").removeClass("glyphicon").removeClass("glyphicon-remove-circle");
		            $(".ui-inline-cancel span").addClass("fa").addClass("fa-close").addClass("inline-gridSave");
		        }
		    });
	
			jQuery("#detail").jqGrid('navGrid','#pagerForDetail',{edit:true,save:false,del:false,search:false,refresh:true}) // enabled refresh for reloading grid
			jQuery("#detail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false}); // enabling filters on top of the header.
			jQuery("#detail").jqGrid("setLabel","code","",{"text-align":"center"});
			jQuery("#detail").jqGrid("setLabel","name","",{"text-align":"center"});
			jQuery("#detail").jqGrid("setLabel","gp.name","",{"text-align":"center"});
			jQuery("#detail").jqGrid("setLabel","c.name","",{"text-align":"center"});
			jQuery("#detail").jqGrid("setLabel","l.name","",{"text-align":"center"});
			jQuery("#detail").jqGrid("setLabel","s.name","",{"text-align":"center"});
			
			
			colModel = jQuery("#detail").jqGrid('getGridParam', 'colModel');
		    $('#gbox_' + $.jgrid.jqID(jQuery("#detail")[0].id) +
		        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
		        var cmi = colModel[i], colName = cmi.name;

		        if (cmi.sortable !== false) {
		            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
		        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
		            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
		        }
		    });	
		    windowHeight=windowHeight-80;
		    $('#detail').jqGrid('setGridHeight',(windowHeight));
} --%>


<%--function loadGramPanchayat(){
	jQuery("#panchayatDetail").jqGrid(
			{
			   	url:'gramPanchayat_data.action',
			   	editurl:'gramPanchayat_populatePanchayatUpdate.action',
			   	pager: '#pagerForPanchayatDetail',
			   	mtype: 'POST',
			   	datatype: "json",	
			   	styleUI : 'Bootstrap',
			   	colNames:[	
			   	          
			   	       <s:if test='branchId==null'>
								'<s:text name="app.branch"/>',
							</s:if>
							<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
								'<s:text name="app.subBranch"/>',
							</s:if>
			  		   	  '<s:text name="grampanchayat.code"/>',
			  		   	  '<s:property value="%{getLocaleProperty('panchayat.name')}" />',
				  		  '<s:property value="%{getLocaleProperty('city.name')}" />',
				  		  '<s:property value="%{getLocaleProperty('locality.name')}"/>',
				  		  '<s:property value="%{getLocaleProperty('state.name')}"/>',
				  		  '<s:property value="%{getLocaleProperty('country.name')}"/>',
				  		  '<s:text name="Actions"/>'
						 ],
			   	colModel:[
			   	          
						<s:if test='branchId==null'>
					   		{name:'branchId',index:'branchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: {
					   			value: '<s:property value="parentBranchFilterText"/>',
					   			dataEvents: [ 
					   			          {
					   			            type: "change",
					   			            fn: function () {
					   			            	console.log($(this).val());
					   			             	getSubBranchValues($(this).val())
					   			            }
					   			        }]
					   			
					   			}},	   				   		
					   		</s:if>
					   		<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
					   			{name:'subBranchId',index:'subBranchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: { value: '<s:property value="childBranchFilterText"/>' }},	   				   		
					   		</s:if>  
			   			{name:'code',index:'code',width:125,sortable:true},
			   			{name:'name',index:'name',width:125,sortable:true,editable: true},
			   			{name:'selectedCity',index:'selectedCity',width:90,sortable:true,editable: true,edittype: "select", editrules:{required: true}},
			   			{name:'selectedDistrict',index:'selectedDistrict',width:90,sortable:true,editable: true,edittype: "select", editrules:{required: true},
			                  editoptions: {
			                		dataEvents: [ 
			   			   			          {
			   			   			            type: "change",
			   			   			            fn: function () {
			   			   			            	//alert($(this).val());
			   			   			            	var id=$(this).closest('tr').attr('id')+"_selectedCity";
			   			   			          		listMunicipalityz($(this).val(),id);
			   			   			            }
			   			   			        }]
			                        }
					   		},
					   	 {name:'selectedState',index:'selectedState',width:90,sortable:true,editable: true,edittype: "select", editrules:{required: true},
				                  editoptions: {
				                		dataEvents: [ 
				   			   			          {
				   			   			            type: "change",
				   			   			            fn: function () {
				   			   			            	//alert($(this).val());
				   			   			            	var id=$(this).closest('tr').attr('id')+"_selectedDistrict";
				   			   			          		var cid=$(this).closest('tr').attr('id')+"_selectedCity";
				   			   			          		listMunicipalityz("",cid);
				   			   			          		listLocalitz($(this).val(),id);
				   			   			            }
				   			   			        }]
				                        }
						   		}, 
						   {name:'selectedCountry',index:'selectedCountry',width:50,sortable:true},
						   {name:'act',index:'act',width:60,sortable:false,formatter: "actions",formatoptions: {keys: true,
					   			onEdit : function(val){
					   				var dataa = {
					   						selectedState:jQuery('#' + val + '_selectedState').val(),
					   				}
					   				getAjaxValue("village_populateLocality.action",dataa,val,"_selectedDistrict"); 
					   				dataa = {
					   						selectedDistrict:jQuery('#' + val + '_selectedDistrict').val(),
						   			}
					   				getAjaxValue("village_populateCity.action",dataa,val,"_selectedCity");
					   			},
					   			delOptions: { url: 'gramPanchayat_delete.action' ,
						   			afterShowForm:function ($form) {
		                        	    $form.closest('div.ui-jqdialog').position({
		                        	        my: "center",
		                        	        of: $("#panchayatDetail").closest('div.ui-jqgrid')
		                        	    });
		                        	},	
		                        	
		                        	afterSubmit: function(data) 
		                            {
		                        	  var json = JSON.parse(data.responseText);
										//document.getElementById("validateErrorCate").innerHTML=json.msg;
										jQuery("#panchayatDetail").jqGrid('setGridParam',{url:"gramPanchayat_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
										showPopup(json.msg,json.title);
										jQuery('.ui-jqdialog-titlebar-close').click();
		                            }
				   		
						   		} ,
						   		
						   		
						   		afterSave: function (id, response, options) {
		                            var json = JSON.parse(response.responseText);
										jQuery("#panchayatDetail").jqGrid('setGridParam',{url:"gramPanchayat_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');

										showPopup(json.msg,json.title);
										jQuery('.ui-jqdialog-titlebar-close').click();
		                        }
					   		}}	
						 ],
			   	height: 301, 
			    width: $("#baseDiv").width(), // assign parent div width
			    scrollOffset: 0,
			   	rowNum:10,
			   	rowList : [10,25,50],
			    sortname:'id',			  
			    sortorder: "desc",
			    viewrecords: true, // for viewing noofrecords displaying string at the right side of the table			   	
		        onSortCol: function (index, idxcol, sortorder) {
			        if (this.p.lastsort >= 0 && this.p.lastsort !== idxcol
		                    && this.p.colModel[this.p.lastsort].sortable !== false) {
		                $(this.grid.headers[this.p.lastsort].el).find(">div.ui-jqgrid-sortable>span.s-ico").show();
		            }
		        },
		        loadComplete: function() {
		        	$('#panchayatDetail').setColProp('selectedState', { editoptions: { value: loadstate} });
		      		$('#panchayatDetail').setColProp('selectedDistrict', { editoptions: { value: loadDistricts} });
		      		$('#panchayatDetail').setColProp('selectedCity', { editoptions: { value: loadCities} });
		      		
		      		$(".ui-inline-save span").removeClass("glyphicon").removeClass("glyphicon-save");
		            $(".ui-inline-save span").addClass("fa").addClass("fa-save").addClass("inline-gridSave");
		            $(".ui-inline-cancel span").removeClass("glyphicon").removeClass("glyphicon-remove-circle");
		            $(".ui-inline-cancel span").addClass("fa").addClass("fa-close").addClass("inline-gridSave");
		        }
		    });	
			jQuery("#panchayatDetail").jqGrid('navGrid','#pagerForPanchayatDetail',{edit:false,add:false,del:false,search:false,refresh:true}) // enabled refresh for reloading grid
			jQuery("#panchayatDetail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false}); // enabling filters on top of the header.
			jQuery("#panchayatDetail").jqGrid("setLabel","code","",{"text-align":"center"});
			jQuery("#panchayatDetail").jqGrid("setLabel","name","",{"text-align":"center"});
			jQuery("#panchayatDetail").jqGrid("setLabel","c.name","",{"text-align":"center"});

			colModel = jQuery("#panchayatDetail").jqGrid('getGridParam', 'colModel');
		    $('#gbox_' + $.jgrid.jqID(jQuery("#panchayatDetail")[0].id) +
		        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
		        var cmi = colModel[i], colName = cmi.name;

		        if (cmi.sortable !== false) {
		            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
		        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
		            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
		        }
		    });	
		    
		  /*   windowHeight=windowHeight-80; */
		    $('#panchayatDetail').jqGrid('setGridHeight',(windowHeight));
} --%>

<%--function loadCity(){
	jQuery("#cityDetail").jqGrid(
			{
			   	url:'municipality_data.action',
			   	editurl:'municipality_populateCityUpdate',
			   	pager: '#pagerForcityDetail',
			   	mtype: 'POST',
			   	datatype: "json",	
			   	styleUI : 'Bootstrap',
			   	colNames:[	
							<s:if test='branchId==null'>
								'<s:text name="app.branch"/>',
							</s:if>
							<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
								'<s:text name="app.subBranch"/>',
							</s:if>
								<s:if test="currentTenantId!='livelihood'">
			  		   	  '<s:text name="village.code"/>',
			  		   	  </s:if>
			  		      '<s:text name="Villagevillage.name"/>',
			  		    '<s:property value="%{getLocaleProperty('locality.name')}"/>',
			  		  '<s:property value="%{getLocaleProperty('state.name')}"/>',
			  		 '<s:property value="%{getLocaleProperty('country.name')}"/>',
			  		  	'<s:text name="Actions"/>'
						 ],
			   	colModel:[
						<s:if test='branchId==null'>
					   		{name:'branchId',index:'branchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: {
					   			value: '<s:property value="parentBranchFilterText"/>',
					   			dataEvents: [ 
					   			          {
					   			            type: "change",
					   			            fn: function () {
					   			            	console.log($(this).val());
					   			             	getSubBranchValues($(this).val())
					   			            }
					   			        }]
					   			
					   			}},	   				   		
					   		</s:if>
					   		<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
					   			{name:'subBranchId',index:'subBranchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: { value: '<s:property value="childBranchFilterText"/>' }},	   				   		
					   		</s:if>
					   			<s:if test="currentTenantId=='olivado'">
					   				{name:'code',index:'code',width:125,sortable:true,editable: true},
					   			</s:if>
					   			<s:elseif test="currentTenantId!='livelihood'">
						   			{name:'code',index:'code',width:125,sortable:true},
						   		</s:elseif>
			   			{name:'name',index:'name',width:125,sortable:true,editable: true},
			   			{name:'selectedDistrict',index:'selectedDistrict',width:90,sortable:true,editable: true,edittype: "select", editrules:{required: true}},
			   			{name:'selectedState',index:'selectedState',width:90,sortable:true,editable: true,edittype: "select", editrules:{required: true},
			                  editoptions: {
			                		dataEvents: [ 
			   			   			          {
			   			   			            type: "change",
			   			   			            fn: function () {
			   			   			            	//alert($(this).val());
			   			   			            	var id=$(this).closest('tr').attr('id')+"_selectedDistrict";
			   			   			          		var cid=$(this).closest('tr').attr('id')+"_selectedCity";
			   			   			          		listMunicipalityz("",cid);
			   			   			          		listLocalitz($(this).val(),id);
			   			   			            }
			   			   			        }]
			                        }
					   		}, 
					   {name:'selectedCountry',index:'selectedCountry',width:125,sortable:true},
			   			 {name:'act',index:'act',width:60,sortable:false,formatter: "actions",formatoptions: {keys: true,
					   		delOptions: { url: 'municipality_delete.action' ,
					   			afterShowForm:function ($form) {
                            	    $form.closest('div.ui-jqdialog').position({
                            	        my: "center",
                            	        of: $("#cityDetail").closest('div.ui-jqgrid')
                            	    });
                            	},	
                            	
                            	afterSubmit: function(data) 
                                {
                            	  var json = JSON.parse(data.responseText);
									//document.getElementById("validateErrorCate").innerHTML=json.msg;
									jQuery("#cityDetail").jqGrid('setGridParam',{url:"municipality_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
									showPopup(json.msg,json.title);
									jQuery('.ui-jqdialog-titlebar-close').click();
                                }
			   		
					   		} ,
					   		
					   		afterSave: function (id, response, options) {
	                             var json = JSON.parse(response.responseText);
									jQuery("#cityDetail").jqGrid('setGridParam',{url:"municipality_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');

									showPopup(json.msg,json.title);
									jQuery('.ui-jqdialog-titlebar-close').click();
	                         } 
				   		}}	
			   		   
						 ],
			   	height: 301, 
			    width: $("#baseDiv").width(), // assign parent div width
			    scrollOffset: 0,
			   	rowNum:10,
			   	rowList : [10,25,50],
			    sortname:'id',			  
			    sortorder: "desc",
			    viewrecords: true, // for viewing noofrecords displaying string at the right side of the table
		        onSortCol: function (index, idxcol, sortorder) {
			        if (this.p.lastsort >= 0 && this.p.lastsort !== idxcol
		                    && this.p.colModel[this.p.lastsort].sortable !== false) {
		                $(this.grid.headers[this.p.lastsort].el).find(">div.ui-jqgrid-sortable>span.s-ico").show();
		            }
		        },
		        loadComplete: function() {
		        	$('#cityDetail').setColProp('selectedState', { editoptions: { value: loadstate} });
		      		$('#cityDetail').setColProp('selectedDistrict', { editoptions: { value: loadDistricts} });
		      		
		      		$(".ui-inline-save span").removeClass("glyphicon").removeClass("glyphicon-save");
		            $(".ui-inline-save span").addClass("fa").addClass("fa-save").addClass("inline-gridSave");
		            $(".ui-inline-cancel span").removeClass("glyphicon").removeClass("glyphicon-remove-circle");
		            $(".ui-inline-cancel span").addClass("fa").addClass("fa-close").addClass("inline-gridSave");
		        }
		    });	
			jQuery("#cityDetail").jqGrid('navGrid','#pagerForcityDetail',{edit:false,add:false,del:false,search:false,refresh:true}) // enabled refresh for reloading grid
			jQuery("#cityDetail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false}); // enabling filters on top of the header.
			jQuery("#cityDetail").jqGrid("setLabel","code","",{"text-align":"center"});
			jQuery("#cityDetail").jqGrid("setLabel","name","",{"text-align":"center"});
			jQuery("#cityDetail").jqGrid("setLabel","l.name","",{"text-align":"center"});
			

			colModel = jQuery("#cityDetail").jqGrid('getGridParam', 'colModel');
		    $('#gbox_' + $.jgrid.jqID(jQuery("#cityDetail")[0].id) +
		        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
		        var cmi = colModel[i], colName = cmi.name;

		        if (cmi.sortable !== false) {
		            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
		        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
		            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
		        }
		    });	
		   /*  windowHeight=windowHeight-80; */
		    $('#cityDetail').jqGrid('setGridHeight',(windowHeight));
} --%>



<%--function loadDistrict(){
	jQuery("#districtDetail").jqGrid(
			{
			   	url:'locality_data.action',
			   	editurl:'locality_populateLocalityUpdate.action',
			   	pager: '#pagerForDistrictDetail',
			   	mtype: 'POST',
			   	datatype: "json",	
			   	styleUI : 'Bootstrap',
			   	colNames:[	
							<s:if test='branchId==null'>
								'<s:text name="app.branch"/>',
							</s:if>
							<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
								'<s:text name="app.subBranch"/>',
							</s:if>
							
			  		   	  '<s:text name="village.code"/>',
			  		   	  
			  		      '<s:text name="Villagevillage.name"/>',
			  		      '<s:property value="%{getLocaleProperty('state.name')}"/>',
			  		    '<s:property value="%{getLocaleProperty('country.name')}"/>',
			  		  	  '<s:text name="Actions"/>'
			  		     
				  		  /* '<s:text name="municipality.latitude"/>',
				  		  '<s:text name="municipality.longitude"/>' */
				  		
						 ],
			   	colModel:[
						<s:if test='branchId==null'>
					   		{name:'branchId',index:'branchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: {
					   			value: '<s:property value="parentBranchFilterText"/>',
					   			dataEvents: [ 
					   			          {
					   			            type: "change",
					   			            fn: function () {
					   			            	console.log($(this).val());
					   			             	getSubBranchValues($(this).val())
					   			            }
					   			        }]
					   			
					   			}},	   				   		
					   		</s:if>
					   		<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
					   			{name:'subBranchId',index:'subBranchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: { value: '<s:property value="childBranchFilterText"/>' }},	   				   		
					   		</s:if>
					   			
					   			{name:'code',index:'code',width:125,sortable:true,editable: true},
					   			
					   			
						   		{name:'name',index:'name',width:125,sortable:true,editable: true},
						   		{name:'selectedState',index:'selectedState',width:90,sortable:true,editable: true,edittype: "select", editrules:{required: true}},
						   		{name:'selectedCountry',index:'selectedCountry',width:125,sortable:true},
						   		{name:'act',index:'act',width:60,sortable:false,formatter: "actions",formatoptions: {keys: true,
							   		delOptions: { url: 'locality_delete.action',
							   			afterShowForm:function ($form) {
	                            	    $form.closest('div.ui-jqdialog').position({
	                            	        my: "center",
	                            	        of: $("#districtDetail").closest('div.ui-jqgrid')
	                            	    });
	                            	},	
	                            	
	                            	afterSubmit: function(data) 
	                                {
	                            	  var json = JSON.parse(data.responseText);
										//document.getElementById("validateErrorCate").innerHTML=json.msg;
										jQuery("#districtDetail").jqGrid('setGridParam',{url:"locality_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
										showPopup(json.msg,json.title);
										jQuery('.ui-jqdialog-titlebar-close').click();
	                                }
				   		
						   		} ,
							   		
							   		afterSave: function (id, response, options) {
			                             var json = JSON.parse(response.responseText);
											jQuery("#districtDetail").jqGrid('setGridParam',{url:"locality_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');

											showPopup(json.msg,json.title);
											jQuery('.ui-jqdialog-titlebar-close').click();
			                         }
						   		}}	
			   		   
						 ],
			   	height: 301, 
			    width: $("#baseDiv").width(), // assign parent div width
			    scrollOffset: 0,
			   	rowNum:10,
			   	rowList : [10,25,50],
			    sortname:'id',			  
			    sortorder: "desc",
			    viewrecords: true, // for viewing noofrecords displaying string at the right side of the table
			   /*  onSelectRow: function(id){ 
				  document.updateform.id.value  =id;
		          document.updateform.submit();      
				},	 */	
		        onSortCol: function (index, idxcol, sortorder) {
			        if (this.p.lastsort >= 0 && this.p.lastsort !== idxcol
		                    && this.p.colModel[this.p.lastsort].sortable !== false) {
		                $(this.grid.headers[this.p.lastsort].el).find(">div.ui-jqgrid-sortable>span.s-ico").show();
		            }
		        },
		        loadComplete: function() {
		      		$('#districtDetail').setColProp('selectedState', { editoptions: { value: loadstate} });
		      		
		      		$(".ui-inline-save span").removeClass("glyphicon").removeClass("glyphicon-save");
		            $(".ui-inline-save span").addClass("fa").addClass("fa-save").addClass("inline-gridSave");
		            $(".ui-inline-cancel span").removeClass("glyphicon").removeClass("glyphicon-remove-circle");
		            $(".ui-inline-cancel span").addClass("fa").addClass("fa-close").addClass("inline-gridSave");
		        }
		    });	
			jQuery("#districtDetail").jqGrid('navGrid','#pagerForDistrictDetail',{edit:false,add:false,del:false,search:false,refresh:true}) // enabled refresh for reloading grid
			jQuery("#districtDetail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false}); // enabling filters on top of the header.
			jQuery("#districtDetail").jqGrid("setLabel","code","",{"text-align":"center"});
			jQuery("#districtDetail").jqGrid("setLabel","name","",{"text-align":"center"});
			jQuery("#districtDetail").jqGrid("setLabel","l.name","",{"text-align":"center"});
			

			colModel = jQuery("#districtDetail").jqGrid('getGridParam', 'colModel');
		    $('#gbox_' + $.jgrid.jqID(jQuery("#districtDetail")[0].id) +
		        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
		        var cmi = colModel[i], colName = cmi.name;

		        if (cmi.sortable !== false) {
		            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
		        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
		            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
		        }
		    });	
		    $('#districtDetail').jqGrid('setGridHeight',(windowHeight));
} --%>

<%--function loadStates(){
	jQuery("#stateDetail").jqGrid(
			{
			   	url:'state_data.action',
			   	editurl:'state_populateStateUpdate.action',
			   	pager: '#pagerForStateDetail',
			   	mtype: 'POST',
			   	datatype: "json",	
			   	styleUI : 'Bootstrap',
			   	colNames:[	
							<s:if test='branchId==null'>
								'<s:text name="app.branch"/>',
							</s:if>
							<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
								'<s:text name="app.subBranch"/>',
							</s:if>
						
			  		   	  '<s:text name="village.code"/>',
			  		   	  
			  		      '<s:text name="Villagevillage.name"/>',
			  		      '<s:property value="%{getLocaleProperty('country.name')}"/>',
			  		     '<s:text name="Actions"/>'
			  		     
				  		  
				  		
						 ],
			   	colModel:[
						<s:if test='branchId==null'>
					   		{name:'branchId',index:'branchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: {
					   			value: '<s:property value="parentBranchFilterText"/>',
					   			dataEvents: [ 
					   			          {
					   			            type: "change",
					   			            fn: function () {
					   			            	console.log($(this).val());
					   			             	getSubBranchValues($(this).val())
					   			            }
					   			        }]
					   			
					   			}},	   				   		
					   		</s:if>
					   		<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
					   			{name:'subBranchId',index:'subBranchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: { value: '<s:property value="childBranchFilterText"/>' }},	   				   		
					   		</s:if>
					   			
					   			{name:'code',index:'code',width:125,sortable:true,editable: true},
					   			
					   			
						   		{name:'name',index:'name',width:125,sortable:true,editable: true},
						   		{name:'selectedCountry',index:'selectedCountry',width:90,sortable:true},
						   		
						   		{name:'act',index:'act',width:60,sortable:false,formatter: "actions",formatoptions: {keys: true,
							   		delOptions: { url: 'state_delete.action' ,
							   			afterShowForm:function ($form) {
		                            	    $form.closest('div.ui-jqdialog').position({
		                            	        my: "center",
		                            	        of: $("#stateDetail").closest('div.ui-jqgrid')
		                            	    });
		                            	},	
		                            	
		                            	afterSubmit: function(data) 
		                                {
		                            	  var json = JSON.parse(data.responseText);
											//document.getElementById("validateErrorCate").innerHTML=json.msg;
											jQuery("#stateDetail").jqGrid('setGridParam',{url:"state_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
											showPopup(json.msg,json.title);
											jQuery('.ui-jqdialog-titlebar-close').click();
		                                }
					   		
							   		} ,
							   	 afterSave: function (id, response, options) {
		                             var json = JSON.parse(response.responseText);
		                             jQuery("#stateDetail").jqGrid('setGridParam',{url:"state_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
		                             showPopup(json.msg,json.title);
										jQuery('.ui-jqdialog-titlebar-close').click();
		                         }
						   		}}	
			   			/* {name:'latitude',index:'latitude',width:125,sortable:true},
			   			{name:'longitude',index:'longitude',width:125,sortable:true} */
			   		   
						 ],
			   	height: 301, 
			    width: $("#baseDiv").width(), // assign parent div width
			    scrollOffset: 0,
			   	rowNum:10,
			   	rowList : [10,25,50],
			    sortname:'id',			  
			    sortorder: "desc",
			    viewrecords: true, // for viewing noofrecords displaying string at the right side of the table
		        onSortCol: function (index, idxcol, sortorder) {
			        if (this.p.lastsort >= 0 && this.p.lastsort !== idxcol
		                    && this.p.colModel[this.p.lastsort].sortable !== false) {
		                $(this.grid.headers[this.p.lastsort].el).find(">div.ui-jqgrid-sortable>span.s-ico").show();
		            }
		        },
		        loadComplete: function() {
		      		$('#stateDetail').setColProp('selectedCountry', { editoptions: { value: loadCountries} });
		      		
		      		$(".ui-inline-save span").removeClass("glyphicon").removeClass("glyphicon-save");
		            $(".ui-inline-save span").addClass("fa").addClass("fa-save").addClass("inline-gridSave");
		            $(".ui-inline-cancel span").removeClass("glyphicon").removeClass("glyphicon-remove-circle");
		            $(".ui-inline-cancel span").addClass("fa").addClass("fa-close").addClass("inline-gridSave");
		        }
		    });	
			jQuery("#stateDetail").jqGrid('navGrid','#pagerForStateDetail',{edit:false,add:false,del:false,search:false,refresh:true}) // enabled refresh for reloading grid
			jQuery("#stateDetail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false}); // enabling filters on top of the header.
			jQuery("#stateDetail").jqGrid("setLabel","code","",{"text-align":"center"});
			jQuery("#stateDetail").jqGrid("setLabel","name","",{"text-align":"center"});
			jQuery("#stateDetail").jqGrid("setLabel","l.name","",{"text-align":"center"});
			

			colModel = jQuery("#stateDetail").jqGrid('getGridParam', 'colModel');
		    $('#gbox_' + $.jgrid.jqID(jQuery("#stateDetail")[0].id) +
		        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
		        var cmi = colModel[i], colName = cmi.name;

		        if (cmi.sortable !== false) {
		            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
		        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
		            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
		        }
		    });	
		    $('#stateDetail').jqGrid('setGridHeight',(windowHeight));
} --%>

<%--function loadCountry(){
	jQuery("#countryDetail").jqGrid(
			{
			   	url:'country_data.action',
			   	pager: '#pagerForCountryDetail',
			   	editurl:'country_populateCountryUpdate.action',
			   	mtype: 'POST',
			   	datatype: "json",	
			   	styleUI : 'Bootstrap',
			   	colNames:[	
							<s:if test='branchId==null'>
								'<s:text name="app.branch"/>',
							</s:if>
							<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
								'<s:text name="app.subBranch"/>',
							</s:if>
								
							
					  		   	  '<s:text name="village.code"/>',
					  		   	  
			  		   	  
			  		      '<s:text name="Villagevillage.name"/>',
			  		    '<s:text name="Actions"/>'
			  		     
				  		  
				  		
						 ],
			   	colModel:[
						<s:if test='branchId==null'>
					   		{name:'branchId',index:'branchId',width:125,sortable: false,width :100,search:true,stype: 'select',searchoptions: {
					   			value: '<s:property value="parentBranchFilterText"/>',
					   			dataEvents: [ 
					   			          {
					   			            type: "change",
					   			            fn: function () {
					   			            	console.log($(this).val());
					   			             	getSubBranchValues($(this).val())
					   			            }
					   			        }]
					   			
					   			}},	   				   		
					   		</s:if>
					   		<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
					   			{name:'subBranchId',index:'subBranchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: { value: '<s:property value="childBranchFilterText"/>' }},	   				   		
					   		</s:if>
					   			
					   			
					   			{name:'code',index:'code',width:80,sortable:true},
					   			
			   		
			   			{name:'name',index:'name',width:125,sortable:true,editable: true},
			   			{name:'act',index:'act',width:60,sortable:false,formatter: "actions",formatoptions: {keys: true,
					   		delOptions: { url: 'country_delete.action' ,
					   			afterShowForm:function ($form) {
                            	    $form.closest('div.ui-jqdialog').position({
                            	        my: "center",
                            	        of: $("#countryDetail").closest('div.ui-jqgrid')
                            	    });
                            	},	
                            	
                            	afterSubmit: function(data) 
                                {
                            	  var json = JSON.parse(data.responseText);
									//document.getElementById("validateErrorCate").innerHTML=json.msg;
									jQuery("#countryDetail").jqGrid('setGridParam',{url:"country_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
									showPopup(json.msg,json.title);
									jQuery.post("state_countryList.action",function(result){
									 	insertOptions("scountry",JSON.parse(result)); 
									 }),
									jQuery('.ui-jqdialog-titlebar-close').click();
									
                                }
			   		
					   		} ,
					   		
					   	 afterSave: function (id, response, options) {
                             var json = JSON.parse(response.responseText);
								jQuery("#countryDetail").jqGrid('setGridParam',{url:"country_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
								if(json.status=="1"){
								showPopup(json.msg,json.title);
								jQuery.post("state_countryList.action",function(result){
								 	insertOptions("scountry",JSON.parse(result)); 
								});
								}
								else{
						    		 showPopup(json.msg,json.title);
						    	}
								jQuery('.ui-jqdialog-titlebar-close').click();
                         }
				   		}}
			   			/* {name:'latitude',index:'latitude',width:125,sortable:true},
			   			{name:'longitude',index:'longitude',width:125,sortable:true} */
			   		   
						 ],
			   	height: 301, 
			    width: $("#baseDiv").width(), // assign parent div width
			    scrollOffset: 0,
			   	rowNum:10,
			   	rowList : [10,25,50],
			    sortname:'id',			  
			    sortorder: "desc",
			    viewrecords: true, // for viewing noofrecords displaying string at the right side of the table
			   /*  onSelectRow: function(id){ 
				  document.updateform.id.value  =id;
		          document.updateform.submit();      
				},	 */	
		        onSortCol: function (index, idxcol, sortorder) {
			        if (this.p.lastsort >= 0 && this.p.lastsort !== idxcol
		                    && this.p.colModel[this.p.lastsort].sortable !== false) {
		                $(this.grid.headers[this.p.lastsort].el).find(">div.ui-jqgrid-sortable>span.s-ico").show();
		            }
		        },
		        
		        loadComplete: function() {
		      		
		      		$(".ui-inline-save span").removeClass("glyphicon").removeClass("glyphicon-save");
		            $(".ui-inline-save span").addClass("fa").addClass("fa-save").addClass("inline-gridSave");
		            $(".ui-inline-cancel span").removeClass("glyphicon").removeClass("glyphicon-remove-circle");
		            $(".ui-inline-cancel span").addClass("fa").addClass("fa-close").addClass("inline-gridSave");
		        }
		    });	
			jQuery("#countryDetail").jqGrid('navGrid','#pagerForCountryDetail',{edit:false,add:false,del:false,search:false,refresh:true}) // enabled refresh for reloading grid
			jQuery("#countryDetail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false}); // enabling filters on top of the header.
			jQuery("#countryDetail").jqGrid("setLabel","code","",{"text-align":"center"});
			jQuery("#countryDetail").jqGrid("setLabel","name","",{"text-align":"center"});
			jQuery("#countryDetail").jqGrid("setLabel","l.name","",{"text-align":"center"});
			

			colModel = jQuery("#countryDetail").jqGrid('getGridParam', 'colModel');
		    $('#gbox_' + $.jgrid.jqID(jQuery("#countryDetail")[0].id) +
		        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
		        var cmi = colModel[i], colName = cmi.name;

		        if (cmi.sortable !== false) {
		            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
		        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
		            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
		        }
		    });	
		    $('#countryDetail').jqGrid('setGridHeight',(windowHeight));
} --%>



function listState(val,id){
	//var selectedCountry = $('#country').val();
	/* jQuery.post("village_populateState.action",{id:val,dt:new Date(),selectedCountry:val},function(result){
		insertOptions(id,JSON.parse(result));
		//listLocality(document.getElementById(id));
	}); */
	//alert(val);
	
		$.ajax({
			type: "POST",
		    async: false,
		    url: "village_populateState.action",
		    data: {selectedCountry:val},
		    success: function(result) {
		    	insertOptions(id, $.parseJSON(result));
		    }
		});
}


function listLocality(val,id){
	/* jQuery.post("village_populateLocality.action",{id:val,dt:new Date(),selectedState:val},function(result){
		insertOptions(id,JSON.parse(result));
		//listMunicipality(document.getElementById(id));
	}); */
	$.ajax({
		type: "POST",
	    async: false,
	    url: "village_populateLocality.action",
	    data: {selectedState:val},
	    success: function(result) {
	    	insertOptions(id, $.parseJSON(result));
	    }
	});
}

function listLocalitz(val,id){
	/* var selectedState = val;
	jQuery.post("village_populateLocality.action",{id:selectedState,dt:new Date(),selectedState:selectedState},function(result){
		insertOptions(id,JSON.parse(result));
		//listMunicipalityz("",id);
	}); */
	$.ajax({
		type: "POST",
	    async: false,
	    url: "village_populateLocality.action",
	    data: {selectedState:val},
	    success: function(result) {
	    	insertOptions(id, $.parseJSON(result));
	    }
	});
}

function listMunicipality(val,id){
	/* jQuery.post("village_populateCity.action",{id:val,dt:new Date(),selectedDistrict:val},function(result){
		insertOptions(id,JSON.parse(result));
		//listGramPanchayat(document.getElementById(id));
	}); */
	$.ajax({
		type: "POST",
	    async: false,
	    url: "village_populateCity.action",
	    data: {selectedDistrict:val},
	    success: function(result) {
	    	insertOptions(id, $.parseJSON(result));
	    }
	});
}


function listMunicipalityz(val,id){
	/* jQuery.post("village_populateCity.action",{id:val,dt:new Date(),selectedDistrict:val},function(result){
		insertOptions(id,JSON.parse(result));
		//listGramPanchayat(document.getElementById("vcities"));
	}); */
	$.ajax({
		type: "POST",
	    async: false,
	    url: "village_populateCity.action",
	    data: {selectedDistrict:val},
	    success: function(result) {
	    	insertOptions(id, $.parseJSON(result));
	    }
	});
}

/*function listGramPanchayatz(val,id){
	jQuery.post("village_populateGramPanchayat.action",{id:val,dt:new Date(),selectedCity:val},function(result){
		insertOptions(id,JSON.parse(result));
		//listGramPanchayat(document.getElementById("vcities"));
	});
}*/

function listGramPanchayat(obj){
	if(gramPanchayatEnable=="1"){
	var selectedLocality = $('#cities').val();
	jQuery.post("village_populateGramPanchayat.action",{id:obj.value,dt:new Date(),selectedCity:obj.value},function(result){
		insertOptions("vgramPanchayats",JSON.parse(result));
		//listGramPanchayat(document.getElementById("gramPanchayats"));
	});
	}
}

function addVillage(idd){
	var country = getElementValueById("vcountry");
	var state = getElementValueById("vstate");
	var localites = getElementValueById("vlocalites");
	var cities = getElementValueById("vcities");
//	var city=getElementValueById("cityName");
	var gramPanchayats= getElementValueById("vgramPanchayats");
	var village=getElementValueById("villageName");
	var code=getElementValueById("villageCode");
	var hit=true;
	
	jQuery(".verror").empty();
	
	if(isEmpty(country)){
		jQuery(".verror").append('<s:text name="empty.country"/>');
		hit=false;
	}else if(isEmpty(state)){
		jQuery(".verror").append('<s:property value="%{getLocaleProperty('empty.state')}" />');
		hit=false;
	}else if(isEmpty(localites)){
		jQuery(".verror").append('<s:property value="%{getLocaleProperty('empty.district')}" />');
		hit=false;
	}else if(isEmpty(cities)){
		jQuery(".verror").append('<s:property value="%{getLocaleProperty('empty.city')}" />');
		hit=false;
	}
	/* else if(isEmpty(city)){
		jQuery(".verror").append('<s:property value="%{getLocaleProperty('empty.parish')}" />');
		hit=false;
	} */
	else if((gramPanchayatEnable=='1')&& isEmpty(gramPanchayats)){
		jQuery(".verror").append('<s:property value="%{getLocaleProperty('empty.gramPanchayat')}" />');
		hit=false;
	}else if(isEmpty(code)){
		jQuery(".verror").append('<s:property value="%{getLocaleProperty('empty.code')}" />');
		hit=false;
	}else if(isEmpty(village)){
		jQuery(".verror").append('<s:property value="%{getLocaleProperty('empty.villname')}" />');
		hit=false;
	}	
	if(hit){
	var dataa =new FormData();
		dataa.append( 'selectedCountry', country );
		dataa.append( 'selectedState', state );
		dataa.append( 'selectedDistrict', localites );
		dataa.append( 'selectedCity', cities );
		dataa.append( 'selectedGramPanchayat', gramPanchayats );
		dataa.append( 'name', village );
		dataa.append( 'code', code );
		
		console.log(dataa); 	
	 	var url ='village_populateSaveVillage.action';
	 	if(idd==2){
	 		dataa.append('id',$('#villageId').val());
	 		url ='village_populateVillageUpdate.action';
	 	}
		
	 	$.ajax({
		 url:url,
	     type: 'post',
	     dataType: 'json',
	     processData: false,
	     contentType: false,
	     data: dataa,
	     success: function(result) {
	    	 resetData("villageForm");
	    	 showPopup(result.msg,result.title);
	    	// jQuery("#detail").jqGrid('setGridParam',{url:"catalogue_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
	    	 $('#villagedetails').DataTable().ajax.reload();
	    	 resetLocationValues();
	     },
	     error: function(result) {
	    	 showPopup("Some Error Occured , Please Try again","Error");
	     	 window.location.href="village_list.action";
	     }
	}); 
	}
}

function addCity(idd){
	var country = getElementValueById("ccountry");
	var state = getElementValueById("cstate");
	var localites = getElementValueById("clocalites");
	var city=getElementValueById("cityName");
	var code=getElementValueById("cityCode");
	var hit=true;
	
	jQuery(".cerror").empty();
	
	if(isEmpty(country)){
		jQuery(".cerror").append('<s:text name="empty.country"/>');
		hit=false;
	}else if(isEmpty(state)){
		jQuery(".cerror").append('<s:property value="%{getLocaleProperty('empty.state')}" />');
		hit=false;
	}else if(isEmpty(localites)){
		jQuery(".cerror").append('<s:property value="%{getLocaleProperty('empty.district')}" />');
		hit=false;
	}else if(isEmpty(code)){
		jQuery(".cerror").append('<s:property value="%{getLocaleProperty('empty.code')}" />');
		hit=false;
	}else if(isEmpty(city)){
		jQuery(".cerror").append('<s:property value="%{getLocaleProperty('empty.citname')}" />');
		hit=false;
	}
	if(hit){
	var dataa =new FormData();
	dataa.append( 'selectedCountry', country );
	dataa.append( 'selectedState', state );
	dataa.append( 'selectedDistrict', localites );
	dataa.append( 'name', city );
	dataa.append( 'code', code );
	
	console.log(dataa); 	
 	var url ='municipality_populateSaveCity.action';
 	if(idd==2){
 		dataa.append('id',$('#cityId').val());
 		url ='municipality_populateCityUpdate.action';
 	}
	
 	$.ajax({
	 url:url,
     type: 'post',
     dataType: 'json',
     processData: false,
     contentType: false,
     data: dataa,
     success: function(result) {
    	 resetData("cityForm");
    	 showPopup(result.msg,result.title);
    	// jQuery("#detail").jqGrid('setGridParam',{url:"catalogue_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
    	
    	 $('#citydetailss').DataTable().ajax.reload();
    	 $('#villagedetails').DataTable().ajax.reload();
    	 resetLocationValues();
     },
     error: function(result) {
    	 showPopup("Some Error Occured , Please Try again","Error");
     	 window.location.href="village_list.action";
     }
}); 
	}
}

function addLocality(idd){
	var country = getElementValueById("dcountry");
	var state = getElementValueById("dstate");
	var district=getElementValueById("districtName");
	var localityCode =getElementValueById("localityCode");
	var hit=true;
	
	jQuery(".lerror").empty();
	
	if(isEmpty(country)){
		jQuery(".lerror").append('<s:text name="empty.country"/>');
		hit=false;
	}else if(isEmpty(state)){
		jQuery(".lerror").append('<s:property value="%{getLocaleProperty('empty.state')}" />');
		hit=false;
	}else if(isEmpty(localityCode)){
		jQuery(".lerror").append('<s:property value="%{getLocaleProperty('empty.code')}" />');
		hit=false;
	}else if(isEmpty(district)){
		jQuery(".lerror").append('<s:property value="%{getLocaleProperty('empty.disname')}" />');
		hit=false;
	}
	if(hit){
	var dataa =new FormData();
	dataa.append( 'selectedCountry', country );
	dataa.append( 'selectedState', state );
	dataa.append( 'name', district );
	dataa.append( 'code', localityCode );
	
	console.log(dataa); 	
 	var url ='locality_populateSaveLocality.action';
 	if(idd==2){
 		dataa.append('id',$('#locationId').val());
 		url ='locality_populateLocalityUpdate.action';
 	}
	
 	$.ajax({
	 url:url,
     type: 'post',
     dataType: 'json',
     processData: false,
     contentType: false,
     data: dataa,
     success: function(result) {
    	 resetData("districtForm");
    	 showPopup(result.msg,result.title);
    	// jQuery("#detail").jqGrid('setGridParam',{url:"catalogue_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
    	 $('#districtDetails').DataTable().ajax.reload();
    	 $('#citydetailss').DataTable().ajax.reload();
    	 $('#villagedetails').DataTable().ajax.reload();
    	 resetLocationValues();
     },
     error: function(result) {
    	 showPopup("Some Error Occured , Please Try again","Error");
     	 window.location.href="village_list.action";
     }
}); 
	}
	
}

function addState(idd){
	
	var country = getElementValueById("scountry");
	var state = getElementValueById("stateName");
	var email = getElementValueById("email");
	var phoneNo = getElementValueById("phoneNo");
	var inChargeName = getElementValueById("inChargeName");
	var hit=true;
	
	jQuery(".serror").empty();
	
	if(isEmpty(country)){
		jQuery(".serror").append('<s:text name="empty.country"/>');
		hit=false;
	}else if(isEmpty(state)){
		jQuery(".serror").append('<s:property value="%{getLocaleProperty('empty.statename')}" />');
		hit=false;
	}
	if(hit){
	var dataa =new FormData();
	dataa.append( 'selectedCountry', country );
	dataa.append( 'name', state );
	dataa.append( 'email', email );
	dataa.append( 'inChargeName', inChargeName );
	dataa.append( 'phoneNo', phoneNo );
	dataa.append( 'code', code );
	
	
	console.log(dataa); 	
 	var url ='state_populateStateCreate.action';
 	if(idd==2){
 		dataa.append('id',$('#stateId').val());
 		url ='state_populateStateUpdate.action';
 	}
	
 	$.ajax({
	 url:url,
     type: 'post',
     dataType: 'json',
     processData: false,
     contentType: false,
     data: dataa,
     success: function(result) {
    	 resetData("stateForm");
    	 showPopup(result.msg,result.title);
    	 
    	// jQuery("#detail").jqGrid('setGridParam',{url:"catalogue_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
    	$('#statedetails').DataTable().ajax.reload();
    	 $('#districtDetails').DataTable().ajax.reload();
    	 $('#citydetailss').DataTable().ajax.reload();
    	 $('#villagedetails').DataTable().ajax.reload();
    	 
    	 resetLocationValues();
     },
     error: function(result) {
    	 showPopup("Some Error Occured , Please Try again","Error");
     	 window.location.href="village_list.action";
     }
}); 
	}
}

function addCountry(idd){
	var country = getElementValueById("countryName");

	var hit=true;
	
	jQuery(".coerror").empty();
	
	if(isEmpty(country)){
		jQuery(".coerror").append('<s:text name="empty.countryName"/>');
		hit=false;
	}
	if(hit){
	var dataa =new FormData();
	dataa.append( 'name', country );
	
	
	console.log(dataa); 	
 	var url ='country_populateCountryCreate.action';
 	if(idd==2){
 		dataa.append('id',$('#CountryId').val());
 		url ='country_populateCountryUpdate.action';
 	}
	
 	$.ajax({
	 url:url,
     type: 'post',
     dataType: 'json',
     processData: false,
     contentType: false,
     data: dataa,
     success: function(result) {
    	 resetData("countryForm");
    	 showPopup(result.msg,result.title);
    	// jQuery("#detail").jqGrid('setGridParam',{url:"catalogue_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
    	 $('#countrydetails').DataTable().ajax.reload();
    	 $('#statedetails').DataTable().ajax.reload();
    	 $('#districtDetails').DataTable().ajax.reload();
    	 $('#citydetailss').DataTable().ajax.reload();
    	 $('#villagedetails').DataTable().ajax.reload();
    	 window.location.href="village_list.action";
    	 resetLocationValues();
     },
     error: function(result) {
    	 showPopup("Some Error Occured , Please Try again","Error");
     	 window.location.href="village_list.action";
     }
     
	
});
 	jQuery.post("state_countryList.action",function(result){
	 	insertOptions("scountry",JSON.parse(result));  
	 	insertOptions("dcountry",JSON.parse(result));
	 	insertOptions("ccountry",JSON.parse(result));
	 	insertOptions("vcountry",JSON.parse(result));
 	});	
	}
}
//@PARAM(URL,POST DATA,SELECTED ROW ID,ID SUFFIX)
function getAjaxValue(url,dataa,selRow,idSuffix){
	var resp = $.ajax({
		url: url,
		data:dataa,
		type: 'post',
		async: false, 
		success: function(data, result) {
			if (!result) 
				alert('Failure to load data');
			}
	}).responseText;
	
		var id="#"+selRow+idSuffix;
		var selectedValue = jQuery(id).val();
		insertJQOptions(selRow+idSuffix,JSON.parse(resp));
		jQuery(id).val(selectedValue);
}

function insertJQOptions(ctrlName, jsonArr) {

	if(document.getElementById(ctrlName)!=null){
	console.log("ctrlName:"+ctrlName);
	document.getElementById(ctrlName).length = 0;
	/* addOption(document.getElementById(ctrlName), "Select", ""); */
	for (var i = 0; i < jsonArr.length; i++) {
		addOption(document.getElementById(ctrlName), jsonArr[i].name,
				jsonArr[i].id);
	}

	var id = "#" + ctrlName;
	jQuery(id).select2();
	}
}

function resetData(id){
	$("#"+id)[0].reset();
	$("#"+id).trigger("reset");
	$('.select2').trigger("change");
	 $('.select2').select2();
}
	
function isNumber(evt) {

	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}
function isEmail(evt) {
	var reg = '/^([A-Za-z0-9_-.])+@([A-Za-z0-9_-.])+.([A-Za-z]{2,4})$/';
	if (reg.test(evt.value) == false) {

		return false;
	}
	return true;
}

function ediFunction(val){
		var son = JSON.parse(val);
		if($('#viladd').attr("aria-expanded")=='false' || $('#viladd').attr("aria-expanded")==undefined){
			$('#viladd').click();
		}
		$('#villageName').val(son.name);
		$('#villageCode').val(son.code);
		jQuery('#villageCode').prop("disabled",true);
		$('#vcountry').val(son.country).select2().trigger('change');
		$('#vstate').val(son.state).select2().trigger('change');	
		$('#vlocalites').val(son.locality).select2().trigger('change');
		$('#vcities').val(son.city).select2().trigger('change');
		
		
		$('#add').addClass("hide");
		$('#edit').removeClass("hide");
		$('#villageId').val(son.id);
	}
function ediFunctionCity(val){
	var son = JSON.parse(val);
	if($('#cityadd').attr("aria-expanded")=='false' || $('#cityadd').attr("aria-expanded")==undefined){
		$('#cityadd').click();
	}
	$('#cityName').val(son.name);
	$('#cityCode').val(son.code);
	 jQuery('#cityCode').prop("disabled",true);
	$('#ccountry').val(son.country).select2().trigger('change');
	$('#cstate').val(son.state).select2().trigger('change');	
	$('#clocalites').val(son.locality).select2().trigger('change');
	
	$('#madd').addClass("hide");
	$('#medit').removeClass("hide");
	//alert(son.id);
	$('#cityId').val(son.id);
}
function ediFunctionLoc(val){
	var son = JSON.parse(val);
	if($('#locadd').attr("aria-expanded")=='false' || $('#locadd').attr("aria-expanded")==undefined){
		$('#locadd').click();
	}
	$('#districtName').val(son.name);
	$('#localityCode').val(son.code);
	 jQuery('#localityCode').prop("disabled",true);
	$('#dcountry').val(son.country).select2().trigger('change');
	$('#dstate').val(son.state).select2().trigger('change');
	
	$('#dadd').addClass("hide");
	$('#dedit').removeClass("hide");
	$('#locationId').val(son.id);
}
function ediFunctionState(val){
	var son = JSON.parse(val);
	if($('#stateadd').attr("aria-expanded")=='false' || $('#stateadd').attr("aria-expanded")==undefined){
		$('#stateadd').click();
	}
	$('#stateName').val(son.name);
	$('#scountry').val(son.country).select2();
	$('#sadd').addClass("hide");
	$('#sedit').removeClass("hide");
	$('#stateId').val(son.id);
}
function ediFunctionCountry(val){
	var son = JSON.parse(val);
	
	if($('#countryadd').attr("aria-expanded")=='false' || $('#countryadd').attr("aria-expanded")==undefined){
		$('#countryadd').click();
	}
	//$('#stateName').val(son.name);
	$('#countryName').val(son.name);
			
	$('#cadd').addClass("hide");
	$('#cedit').removeClass("hide");
	$('#CountryId').val(son.id);
}
function onResetAddVil(){
	    $("#vcountry option[value='']").prop("selected", true).trigger('change');
	    $("#vstate option[value='']").prop("selected", true).trigger('change');
	    $("#vlocalites option[value='']").prop("selected", true).trigger('change');
	    $("#vcities option[value='']").prop("selected", true).trigger('change');
		$('#villageName').val('');
		
	}
function onResetAddSc(){
	  $("#ccountry option[value='']").prop("selected", true).trigger('change');
	  $("#cstate option[value='']").prop("selected", true).trigger('change');
	  $("#clocalites option[value='']").prop("selected", true).trigger('change');
      $('#cityName').val('');
}
function onResetAddDistr(){
	 $("#dcountry option[value='']").prop("selected", true).trigger('change');
	 $("#dstate option[value='']").prop("selected", true).trigger('change');
	 $('#districtName').val('');
	
}
function onResetAddState(){
	 $("#scountry option[value='']").prop("selected", true).trigger('change');
	 $('#stateName').val('');
	
}
function onResetAddCountry(){   
	$('#countryName').val('');
	
}

function deletProd(idd){
	var val = confirm('Are you sure you want to delete the Village');
	if(val){
	$.ajax({
		 url:'village_delete.action?id='+idd,
	     type: 'post',
	     dataType: 'json',
	     processData: false,
	     contentType: false,
	     success: function(result) {
	    	 showPopup(result.msg,result.title);
		 	 $('#villagedetails').DataTable().ajax.reload();
	     },
	     error: function(result) {
	    	 showPopup(result.msg,result.title);
	     }
	}); 
	}
}
function deletMunicipality(idd){
	var val = confirm('Are you sure you want to delete the Ward');
	if(val){
	$.ajax({
		 url:'municipality_delete.action?id='+idd,
	     type: 'post',
	     dataType: 'json',
	     processData: false,
	     contentType: false,
	     success: function(result) {
	    	 showPopup(result.msg,result.title);
		 	 $('#villagedetails').DataTable().ajax.reload();
	     },
	     error: function(result) {
	    	 showPopup(result.msg,result.title);
	     }
	}); 
	}
}
function deleteLocality(idd){
	var val = confirm('Are you sure you want to delete the Subcounty');
	if(val){
	$.ajax({
		 url:'locality_delete.action?id='+idd,
	     type: 'post',
	     dataType: 'json',
	     processData: false,
	     contentType: false,
	     success: function(result) {
	    	 showPopup(result.msg,result.title);
		 	 $('#villagedetails').DataTable().ajax.reload();
	     },
	     error: function(result) {
	    	 showPopup(result.msg,result.title);
	     }
	}); 
	}
}
function deleteState(idd){
	var val = confirm('Are you sure you want to delete the County');
	if(val){
	$.ajax({
		 url:'state_delete.action?id='+idd,
	     type: 'post',
	     dataType: 'json',
	     processData: false,
	     contentType: false,
	     success: function(result) {
	    	 showPopup(result.msg,result.title);
		 	 $('#villagedetails').DataTable().ajax.reload();
	     },
	     error: function(result) {
	    	 showPopup(result.msg,result.title);
	     }
	}); 
	}
}
function deleteCountry(idd){
	var val = confirm('Are you sure you want to delete the Country');
	if(val){
	$.ajax({
		 url:'country_delete.action?id='+idd,
	     type: 'post',
	     dataType: 'json',
	     processData: false,
	     contentType: false,
	     success: function(result) {
	    	 showPopup(result.msg,result.title);
		 	 $('#villagedetails').DataTable().ajax.reload();
	     },
	     error: function(result) {
	    	 showPopup(result.msg,result.title);
	     }
	}); 
	}
}

</script>
	<div id="dialog" style="display: none"></div>
	<div class="appContentWrapper marginBottom">
		<ul class="nav nav-pills">
			<li class="active"><a data-toggle="pill" href="#countries"><s:property
						value="%{getLocaleProperty('country.name')}" /></a></li>

			<li><a data-toggle="pill" href="#states"><s:property
						value="%{getLocaleProperty('state.name')}" /></a></li>

			<li><a data-toggle="pill" href="#district"><s:property
						value="%{getLocaleProperty('locality.name')}" /></a></li>
			<li><a data-toggle="pill" href="#city"><s:property
						value="%{getLocaleProperty('city.name')}" /></a></li>

			<%-- <s:if test="gramPanchayatEnable==1">
				<li><a data-toggle="pill" href="#panchayat"><s:property
							value="%{getLocaleProperty('panchayat.name')}" /></a></li>
			</s:if> --%>

			<li><a data-toggle="pill" href="#village"><s:property
						value="%{getLocaleProperty('village.name')}" /></a></li>
		</ul>
</div>


	<div class="tab-content">
		
		
		<div id="village" class="tab-pane fade">
			<s:form action="village_create" name="villageForm" id="villageForm">
			<s:hidden name="id" id="villageId" />
					<div class="dataTable-btn-cntrls"  id="villagedetailsBtn">
	     <sec:authorize ifAllGranted="profile.location.village.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id="">	<a data-toggle="collapse" data-parent="#accordion" onclick="onResetAddVil()"
						href="#villageAccordian" class="fa fa-plus" id="viladd">
						<s:text name="create.button" />
						</a></li>
					</ul>
						</sec:authorize>
					
				</div>
				
				
				<div id="villageAccordian" class="panel-collapse collapse">

					<div class="appContentWrapper marginBottom">
						<div class="formContainerWrapper">
							<h2>
								<s:property value="%{getLocaleProperty('info.village')}" />
							</h2>
						</div>
						<div class="error verror">
							<s:actionerror />
							<s:fielderror />
						</div>
						<div class="flex-layout filterControls">

							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('country.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select name="selectedCountry" list="countriesList"
										headerKey="" headerValue="%{getText('txt.select')}" Key="name"
										Value="name" theme="simple" id="vcountry"
										onchange="listState(this.value,'vstate')"
										cssClass="form-control select2" />
								</div>
							</div>


							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('state.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select name="selectedState" list="statesId" Key="code"
										Value="name" headerKey=""
										headerValue="%{getText('txt.select')}" theme="simple"
										id="vstate" onchange="listLocality(this.value,'vlocalites')"
										cssClass="form-control  select2" />
								</div>
							</div>


							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('locality.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select name="selectedDistrict" id="vlocalites"
										list="localitiesId" Key="code" Value="name" headerKey=""
										headerValue="%{getText('txt.select')}" theme="simple"
										onchange="listMunicipality(this.value,'vcities')"
										cssClass="form-control  select2" />
								</div>
							</div>

							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('city.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select name="selectedCity" id="vcities" list="cities"
										Key="code" Value="name" headerKey=""
										headerValue="%{getText('txt.select')}" theme="simple"
										onchange="listGramPanchayat(this)"
										cssClass="form-control  select2" />
								</div>
							</div>
						
							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('village.code')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element flexdisplay">
									<s:textfield name="village.code" theme="simple" maxlength="35"
										cssClass="form-control " id="villageCode" />

								</div>
							</div>
							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('village.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element flexdisplay">
									<s:textfield name="village.name" theme="simple" maxlength="35"
										cssClass="form-control " id="villageName" />

								</div>
							</div>
							<div class="flexItem" style="margin-top: 24px;">
								<%-- <button type="button" class="btn btn-sts" id="add"
									onclick="addVillage()">
									<b><s:text name="save.button" /></b>
								</button> --%>
								<button type="button" class="btn btn-sts" id="add"
									onclick="addVillage(1)">
									<b><s:text name="save.button" /></b>
								</button>

								<button type="button" class="btn btn-sts hide" id="edit"
									onclick="addVillage(2)">
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

				</div>
			</s:form>

			 <div class="appContentWrapper marginBottom">
				<div id="baseDiv" style="width: 100%">
			<table id="villagedetails" class="table table-striped" >
				<thead >
					<tr id="headerrow">

						<s:if test='branchId==null'>
							<th width="20%" id="branch" class="hd"><s:text name="app.branch" /></th>
						</s:if>

						<s:if
							test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<th  width="20%" id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
						</s:if>


						<th  width="20%" id="code" class="hd"><s:text name="village.code" /></th>
						<th  width="20%" id="villagename1" class="hd"><s:text name="village.name" /></th>
						<th  width="20%" id="cityname1" class="hd"><s:text name="city.name" /></th>
						<th  width="20%" id="localityname1" class="hd"><s:text name="locality.name" /></th>
						<th  width="20%" id="statename1" class="hd"><s:text name="state.name" /></th>
						<th  width="20%" id="countryname1" class="hd"><s:text name="country.name" /></th>
						 <th  width="20%" id="edit" class="hd"><s:text name="Actions" /></th> 
						
					</tr>
				</thead>

			</table>
		</div>
</div> 
			
</div>

<div id="city" class="tab-pane fade">
			<s:form name="cityForm" id="cityForm">
			<s:hidden name="id" id="cityId" />
						<div  class="dataTable-btn-cntrls" id="citydetailssBtn">
	     <sec:authorize ifAllGranted="profile.location.village.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id="">
						
							<a data-toggle="collapse" data-parent="#accordion" onclick="onResetAddSc()"
						href="#cityAccordian" class="fa fa-plus" id="cityadd">
						<s:text name="create.button" />
						</a>
					</li>
					</ul>
						</sec:authorize>
					
				</div>

				<div id="cityAccordian" class="panel-collapse collapse">
					<div class="appContentWrapper marginBottom">
						<div class="formContainerWrapper">
							<h2>
								<s:property value="%{getLocaleProperty('info.municipality')}" />
							</h2>
						</div>
						<div class="error cerror">
							<s:actionerror />
							<s:fielderror />
						</div>

						<div class="flex-layout filterControls">
							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('country.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select name="selectedCountry" list="countriesList"
										headerKey="" headerValue="%{getText('txt.select')}" Key="name"
										Value="name" theme="simple" id="ccountry"
										onchange="listState(this.value,'cstate')"
										cssClass="form-control select2" />
								</div>
							</div>

							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('state.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select name="selectedState" list="statesId" Key="code"
										Value="name" headerKey=""
										headerValue="%{getText('txt.select')}" theme="simple"
										id="cstate" onchange="listLocality(this.value,'clocalites')"
										cssClass="form-control  select2" />
								</div>
							</div>


							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('locality.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select name="selectedDistrict" id="clocalites"
										list="localitiesId" Key="code" Value="name" headerKey=""
										headerValue="%{getText('txt.select')}" theme="simple"
										cssClass="form-control  select2" />
								</div>
							</div>

							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('city.code')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element flexdisplay">
									<s:textfield name="city.code" theme="simple" maxlength="35"
										cssClass="form-control " id="cityCode" />

								</div>
							</div>
							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('city.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element flexdisplay">
									<s:textfield name="city.name" theme="simple" maxlength="35"
										cssClass="form-control " id="cityName" />

								</div>
							</div>
							<div class="flexItem" style="margin-top: 24px;">
								<%-- <button type="button" class="btn btn-sts" id="add"
									onclick="addCity()">
									<b><s:text name="save.button" /></b>
								</button> --%>
								<button type="button" class="btn btn-sts" id="madd"
									onclick="addCity(1)">
									<b><s:text name="save.button" /></b>
								</button>

								<button type="button" class="btn btn-sts hide" id="medit"
									onclick="addCity(2)">
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
				</div>
			</s:form>

			<div class="appContentWrapper marginBottom">
				<div id="baseDiv"  style="width: 100%">
			<table id="citydetailss" class="table table-striped" >
				<thead >
					<tr id="headerrow">

						<s:if test='branchId==null'>
							<th width="20%" id="branch" class="hd"><s:text name="app.branch" /></th>
						</s:if>

						<s:if
							test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<th  width="20%" id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
						</s:if>

                       <!--   <s:if test="currentTenantId!='livelihood'"> -->
						<th  width="20%" id="code" class="hd"><s:text name="village.code" /></th>
						<!--</s:if> -->
						<th  width="20%" id="villname1" class="hd"><s:text name="city.name" /></th>
						<th  width="20%" id="locaname1" class="hd"><s:text name="locality.name" /></th>
						<th  width="20%" id="staname1" class="hd"><s:text name="state.name" /></th>
						<th  width="20%" id="counname1" class="hd"><s:text name="country.name" /></th>
						
						 <th  width="20%" id="edit" class="hd"><s:text name="Actions" /></th> 
						
					</tr>
				</thead>

			</table>
		</div>
		</div>
</div>



		
		<div id="district" class="tab-pane fade">
			<s:form name="districtForm" id="districtForm">
			<s:hidden name="id" id="locationId"/>
				<div class="dataTable-btn-cntrls" id="districtDetailsBtn">
	     <sec:authorize ifAllGranted="profile.location.village.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id="">
						
						<a data-toggle="collapse" data-parent="#accordion" onclick="onResetAddDistr()"
						href="#DistrictAccordian" class="fa fa-plus" id="locadd">
						<s:text name="create.button" />
						</a>
						
					</li>
					</ul>
						</sec:authorize>
					
				</div>

				<div id="DistrictAccordian" class="panel-collapse collapse">
					<div class="appContentWrapper marginBottom">
						<div class="formContainerWrapper">
							<h2>
								<s:property value="%{getLocaleProperty('info.locality')}" />
							</h2>
						</div>
						<div class="error lerror">
							<s:actionerror />
							<s:fielderror />
						</div>

						<div class="flex-layout filterControls">
							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('country.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select name="selectedCountry" list="countriesList"
										headerKey="" headerValue="%{getText('txt.select')}" Key="name"
										Value="name" theme="simple" id="dcountry"
										onchange="listState(this.value,'dstate')"
										cssClass="form-control select2" />
								</div>
							</div>

							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('state.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select name="selectedState" list="statesId" Key="code"
										Value="name" headerKey=""
										headerValue="%{getText('txt.select')}" theme="simple"
										id="dstate" cssClass="form-control  select2" />
								</div>
							</div>

							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('locality.code')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element flexdisplay">
									<s:textfield name="city.code" theme="simple" maxlength="35"
										cssClass="form-control " id="localityCode" />

								</div>
							</div>
							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('locality.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element flexdisplay">
									<s:textfield name="city.name" theme="simple" maxlength="35"
										cssClass="form-control " id="districtName" />

								</div>
							</div>
							<div class="flexItem" style="margin-top: 24px;">
								<%-- <button type="button" class="btn btn-sts" id="add"
									onclick="addLocality()">
									<b><s:text name="save.button" /></b>
								</button> --%>
								<button type="button" class="btn btn-sts" id="dadd"
									onclick="addLocality(1)">
									<b><s:text name="save.button" /></b>
								</button>

								<button type="button" class="btn btn-sts hide" id="dedit"
									onclick="addLocality(2)">
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
				</div>
			</s:form>
			<div class="appContentWrapper marginBottom">
				<div id="baseDiv"  style="width: 100%">
			<table id="districtDetails" class="table table-striped" >
				<thead >
					<tr id="headerrow">

						<s:if test='branchId==null'>
							<th width="20%" id="branch" class="hd"><s:text name="app.branch" /></th>
						</s:if>

						<s:if
							test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<th  width="20%" id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
						</s:if> 


						<th   width="20%" id="code" class="hd"><s:text name="village.code" /></th>
						<th   width="20%" id="villname" class="hd"><s:text name="locality.name" /></th>
						<th   width="20%" id="staname" class="hd"><s:text name="state.name" /></th>
						<th   width="20%" id="couname" class="hd"><s:text name="country.name" /></th>
						 <th  width="20%" id="edit" class="hd"><s:text name="Actions" /></th>
							
					</tr>
				</thead>

			</table>
		</div>
			</div>
		</div>

		
		
		<div id="states" class="tab-pane fade">
			<s:form name="stateForm" id="stateForm">
			<s:hidden name="id" id="stateId"/>
			<div  class="dataTable-btn-cntrls" id="statedetailsBtn">
	     <sec:authorize ifAllGranted="profile.location.village.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id="">
						
						<a data-toggle="collapse" data-parent="#accordion" onclick="onResetAddState()"
						href="#stateAccordian" class="fa fa-plus" id="stateadd">
						<s:text name="create.button" />
						</a>
						
					</li>
					</ul>
						</sec:authorize>
					
				</div>

				<div id="stateAccordian" class="panel-collapse collapse">
					<div class="appContentWrapper marginBottom">
						<div class="formContainerWrapper">
							<h2>
								<s:property value="%{getLocaleProperty('info.state')}" />
							</h2>
						</div>
						<div class="error serror">
							<s:actionerror />
							<s:fielderror />
						</div>

						<div class="flex-layout filterControls">
							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('country.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select name="selectedCountry" list="countriesList"
										headerKey="" headerValue="%{getText('txt.select')}" Key="name"
										Value="name" theme="simple" id="scountry"
										cssClass="form-control select2" />
								</div>
							</div>

							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('state.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element flexdisplay">
									<s:textfield name="city.name" theme="simple" maxlength="35"
										cssClass="form-control " id="stateName" />

								</div>
							</div>
							
							<div class="flexItem" style="margin-top: 24px;">
								<%-- <button type="button" class="btn btn-sts" id="add"
									onclick="addState()">
									<b><s:text name="save.button" /></b>
								</button> --%>
								<button type="button" class="btn btn-sts" id="sadd"
									onclick="addState(1)">
									<b><s:text name="save.button" /></b>
								</button>

								<button type="button" class="btn btn-sts hide" id="sedit"
									onclick="addState(2)">
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
				</div>
			</s:form>

			<div class="appContentWrapper marginBottom">
				
				<div id="baseDiv"  style="width: 100%">
			<table id="statedetails" class="table table-striped" >
				<thead >
					<tr id="headerrow">

					 	<s:if test='branchId==null'>
							<th width="20%" id="branch" class="hd"><s:text name="app.branch" /></th>
						</s:if>

						<s:if
							test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<th  width="20%" id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
						</s:if> 
                        <th   width="20%" id="code" class="hd"><s:text name="village.code" /></th>
						<th   width="20%" id="countryname1" class="hd"><s:text name="state.name" /></th>
						<th   width="20%" id="countnam1" class="hd"><s:text name="country.name" /></th>
						 <th  width="20%" id="edit" class="hd"><s:text name="Actions" /></th>	
					</tr>
				</thead>

			</table>
		</div>
				
			</div>
		</div>

		
		
		<div id="countries" class="tab-pane fade in active">

			<s:form name="countryForm" id="countryForm">
				<s:hidden name="id" id="CountryId"/>
<div  class="dataTable-btn-cntrls hide" id="countrydetailsBtn">
     <sec:authorize ifAllGranted="profile.location.village.create">
					<ul class="nav nav-pills newUI-nav-pills">
						<li class="" id=""><a data-toggle="collapse" data-parent="#accordion" onclick= "onResetAddCountry()"
						href="#countryAccordian" class="fa fa-plus" id='countryadd'>
						<s:text name="create.button" />
						</a></li>
					</ul>
						</sec:authorize>
					 
				</div>

				<div id="countryAccordian" class="panel-collapse collapse">
					<div class="appContentWrapper marginBottom">
						<div class="formContainerWrapper">
							<h2>
								<s:property value="%{getLocaleProperty('info.country')}" />
							</h2>
						</div>
						<div class="error coerror">
							<s:actionerror />
							<s:fielderror />
						</div>

						<div class="flex-layout filterControls">

							<div class="flexItem">
								<label for="txt"><s:property
										value="%{getLocaleProperty('country.name')}" /><sup style="color: red;">*</sup></label>
								<div class="form-element flexdisplay">
									<s:textfield name="countryName" theme="simple" maxlength="35"
										cssClass="form-control " id="countryName" />

								</div>
							</div>
							<div class="flexItem" style="margin-top: 24px;">
								<%-- <button type="button" class="btn btn-sts" id="add"
									onclick="addCountry()">
									<b><s:text name="save.button" /></b>
								</button> --%>
								<button type="button" class="btn btn-sts" id="cadd"
									onclick="addCountry(1)">
									<b><s:text name="save.button" /></b>
								</button>

								<button type="button" class="btn btn-sts hide" id="cedit"
									onclick="addCountry(2)">
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
				</div>

			</s:form>

			<div class="appContentWrapper marginBottom">
				<div id="baseDiv"  style="width: 100%">
			<table id="countrydetails" class="table table-striped" >
				<thead >
					<tr id="headerrow">

					 	<s:if test='branchId==null'>
							<th width="20%" id="branch" class="hd"><s:text name="app.branch" /></th>
						</s:if>

						<s:if
							test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<th  width="20%" id="subbranch" class="hd"><s:text name="app.subBranch" /></th>
						</s:if> 
                        <th   width="20%" id="code" class="hd"><s:text name="village.code" /></th>
						<th   width="20%" id="countryname1" class="hd"><s:text name="country.name" /></th>
						
						<th  width="20%" id="edit" class="hd"><s:text name="Actions" /></th>		
					</tr>
				</thead>

			</table>
		</div>
				
			</div>
		</div>
		
		
</div>