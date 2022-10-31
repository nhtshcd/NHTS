function saveMenu(){
	var flag = false;
	flag = setSectionIdForAllFields();
	
	if(flag == true){
		readDynamicMenuAndPopulateJson();
	}
}

function setSectionIdForAllFields(){

	var flag = true;
	
	$("#targetContainer").find("#sortableSection").find(".appContentWrapper").each(function() {
		$(this).find(".formContainerWrapper").each(function(){
			var sectionId = this.id; //section id
		
			//$(this).find(".ui-sortable").find(".flexform-item").each(function(){
			$(this).find(".ui-sortable").each(function(){
			
				$(this).find('.flexform-item,table').each(function(){
				    var innerElementClass = $(this).attr('class');
				 
				    if(innerElementClass.includes("table table-bordered")){ //For List
				    	var listElementId = $(this).attr('id');
					     flag = setSectionId(listElementId,sectionId);
				    	
				    	
				    	 $(this).find("tbody").children('tr:first').each(function (i, el) {
				    		var td_Count = ($(this).find('td').length)-1; //ignore add button (td)
				    		if(td_Count > 0){
				    			$(this).find('td').each(function (i, el) {
				    	        	 $(this).find('input,textArea,select,label,button').each(function (i, el) {
					    	        	 var childElementId = $(this).attr('id');
					    	        	 flag = setSectionId(childElementId,sectionId);
					    	         });
				    	        	 if(flag == false){
				    	        		 return false;
				    	        	 }
				    	        });
				    		}else{
				    			flag = false;
				    			$("#"+listElementId).addClass("hilightlist");
				    			var dom = $("#"+listElementId);
				    			var backendData = dom.data("backendjsonobject");
				    			var options;
				    			if( backendData != undefined){
				    				if(backendData.options != undefined){
				    					  options = jQuery.parseJSON(backendData.options);
				    					  alert(" "+options.componentName+"  should not be empty ");
				    				}
				    			}
				    			
				    			
				    			return false;
				    			
				    		}
				    		 	
				    	        
				    	    });
				    	
				    	
				    }else if(innerElementClass.includes("flexform-item")){
				    	
				    	
						$(this).find(".form-element").each(function(){
							$(this).children().each(function () {
							    var childElementId = this.id // child component id
							    flag = setSectionId(childElementId,sectionId);
							    
							    
							    
							});
						})
				    }
				    
				    
				});
				
				
				
				if(flag == false){
					return false;
				}
				
			})
			
			
			
		});
	});
	
	return flag;
}

function setSectionId(childElementId,sectionId){
	
	var flag = true;
	    
	    //Reading Child backend data start

		 
	 		var dom = $("#"+childElementId);
			var backendData = dom.data("backendjsonobject");
			var options;
			if( backendData != undefined){
				if(backendData.options != undefined){
					  options = jQuery.parseJSON(backendData.options);
					  options["sectionId"] = sectionId;
					  
					  backendData["options"] = JSON.stringify(options);
						$(dom).attr({
				      		"data-backendjsonobject" : JSON.stringify(backendData)
				      });
					  
				}else{
					flag =  false;
					 if($(dom).attr('class') != undefined){
						 if($(dom).attr('class').includes("List")){
							   var questionName =  $(dom).parent().text();
							}else{
								 var questionName =  $(dom).parent().parent().text();
							}
					 }else{
						 var questionName =  $(dom).parent().parent().text();
					 }
					   
					   alert("Please provide basic details of "+questionName);
				}
			}
	
	    
	    
	return  flag;
	    
}

function readDynamicMenuAndPopulateJson(){
	var dynamicMenu = {};
	var sectionArray = new Array();
	
	
	$("#targetContainer").find("#sortableSection").find(".appContentWrapper").each(function() {
		$(this).find(".formContainerWrapper").each(function(){
			
			var temp = {};
			
			
			
			
			 var fieldsArray	 =  new Array();
			
		
			$(this).find(".ui-sortable").find(".flexform-item,table").each(function(){
				
				var innerElementClass = $(this).attr('class');
				
				 if(innerElementClass.includes("table table-bordered")){ //For List
				    	//alert($(this).attr('class'));
					
					 var list_backendData = $(this).data("backendjsonobject");
    	        	 var list_options;
						if( list_backendData != undefined){
							if(list_backendData.options != undefined){
								list_options = jQuery.parseJSON(list_backendData.options);
								   fieldsArray.push(list_options); // adding list [component type 8]
							}
						}
					 
					 
				    	 $(this).find("tbody").children('tr:first').each(function (i, el) {
				    	         $(this).find('td').each(function (i, el) {
				    	        	 $(this).find('input,textArea,select,label,button').each(function (i, el) {
					    	        	 var childElementId = $(this).attr('id');
					    	        	 var dom = $("#"+childElementId);
					    	        	 var backendData = dom.data("backendjsonobject");
					    	        	 var options;
											if( backendData != undefined){
												if(backendData.options != undefined){
													  options = jQuery.parseJSON(backendData.options);
													   fieldsArray.push(options);
												}
											}
					    	         });
				    	        	
				    	        });
				    	        
				    	    });
				    	
				    	
				    }else if(innerElementClass.includes("flexform-item")){
				    	
				    	var questionName = $(this).text();//Component name
						$(this).find(".form-element").each(function(){
							$(this).children().each(function () {
							    
								
								var childElementId = this.id // child component id
							   
							    
							    //Reading Child backend data start
							    	
							    var dom = $("#"+childElementId);
								var backendData = dom.data("backendjsonobject");
								var options;
								if( backendData != undefined){
									if(backendData.options != undefined){
										  options = jQuery.parseJSON(backendData.options);
										   fieldsArray.push(options);
									}
								}
							    
								//Reading Child backend data end
							    
							});
						})
				    	
				    }
				
				
				
				
				
				
			})
			
			
			
			if(fieldsArray.length > 0){
				var sectionData = {}
				sectionData ["name"] = $(this).find("h2").text();
				sectionData ["id"] = this.id;
				
				temp["sectionData"] = sectionData;
				temp["fieldsArray"] = fieldsArray;
				sectionArray.push(temp);
			}
			
			
		});
	});
	
	
	
	
	dynamicMenu["txnType"] = "308";
	dynamicMenu["menuName"] = "New Dynamic menu";
	dynamicMenu["iconClass"] = "sts icon";
	dynamicMenu["entity"] = "2";
	dynamicMenu["isSeason"] = "";
	dynamicMenu["isSingleRecord"] = "";
	dynamicMenu["agentType"] = "1";
	dynamicMenu["mTxnType"] = "";
	dynamicMenu["beforeInsert"] = "";
	dynamicMenu["afterInsert"] = "";
	dynamicMenu["isScore"] = "0";
	
	dynamicMenu["sectionArray"] = sectionArray;
	
	
	console.log(JSON.stringify(dynamicMenu));
	
	if(dynamicMenu.sectionArray.length > 0){
		$.ajax({
		    url: "dynamicUI_createMenu.action",
		   
		   // dataType: 'json',
		   // contentType: 'application/json',
		    type: 'POST',
		    async: true,
		    data:{menudata:JSON.stringify(dynamicMenu)},
		    success: function (res) {
		    	showPopup(res.msg,res.title);
		    }
		});
	}
	
	
	
	
}