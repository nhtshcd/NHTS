
<%@ include file="/jsp/common/grid-assets.jsp"%>


<head>
<meta name="decorator"
	content="<%=request.getParameter("layoutType") != null && !request.getParameter("layoutType").equals("")
					? request.getParameter("layoutType")
					: "swithlayout"%>">
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">

</head>
<style>
.faDyn {
	color: green;
}

.dataTable-btn-cntrls {
	display: flex;
}
</style>

<body>
	<!-- 	<script src="js/dynamicComponents.js?v=18.14"></script> -->
	<script type="text/javascript">
		var branchId = '<s:property value="branchId"/>';
		
		var fetchType = "";
		
		var typez =<%out.print("'" + request.getParameter("id") + "'");%>;
			var table;
			var min = "";
			var max = "";
			var dataInd = "";
			 var deFil='';
			var subBridObj ={};
			var createService='<s:property value="createService"/>';
			
		jQuery(document)
				.ready(
						function() {
							
							$("#addService").click(function(){
										document.redirectform.redirectContent.value = window.location.href;
										document.redirectform.action = createService;
										document.redirectform.submit();
									 }); 
							
							 deFil	= '<s:property value="defFilters"/>';
							jQuery("#clsBtn").click(function() {
								hidePopup();
							});
							loadCustomPopup();
							
							$(".dataTables_filter").hide();
							loadGrid();
							 if(createService!=""){
									$('.reportIn').hide();
									var url = (window.location.href);
									//$(".breadCrumbNavigation").find('li:first').find('a:first').html("Service");
									if(createService!='1'){
										$('#addBtn').removeClass("hide");
									}
								
								$('.dt-buttons').addClass("hide");
								 }else{
									$('.serviceIn').hide();
									$('.dt-buttons').removeClass("hide");
								 }
						
							$("#reset").click(function() {
								var url = (window.location.href);
								window.location.href = url;
								$(".select2").val("");

							});
						});
		
		 
		jQuery("#generate").click(function() {
			reloadGrid();
		});

		jQuery("#clear").click(function() {
			clear();
		});
		var data = new Array();

		function hideLocationFiltersForSmsReport(reportID){
			if(reportID == "15"){
				$(".locationFilter").hide();
			}
		}
						
		function format(d) {
			var subGridColumnNames = new Array();
			var subGridColumnModels = new Array();
			var subColNames = '<s:property value="subGridCols"/>';

			var subColumnModels = '<s:property value="subGridColNames"/>';

			var returTRs = "";
			if(subBridObj.hasOwnProperty(d.id)){
				returTRs = subBridObj[d.id];
			}else{
			$
					.ajax({
						type : "POST",
						async : false,
						url : url+"subGridDetail.action?id="
								+d.id+"&layoutType="+layoutType,
						success : function(result) {
						returTRs = '<table class=" table table-bordered" cellpadding="5" cellspacing="0" border="0" width="75%">';
							returTRs = returTRs + '<thead>';
							$(subColNames.split("%")).each(
									function(k, val) {
										if (val != null && val!='') {
											var cols = val.split("#");
											returTRs = returTRs + '<th>'
													+ cols[0] + '</th>';
										}
									});
							returTRs = returTRs + '</thead>';
							$(JSON.parse(result).data).each(
									function(k, val) {

										returTRs = returTRs + '<tr>';
										$(val).each(
												function(j, val1) {

													returTRs = returTRs
															+ '<td>' + val1
															+ "</td>";
												});
										returTRs = returTRs + '</tr>';
									});
							returTRs = returTRs + '</table>';

						}
					});
			subBridObj[d.id] = returTRs;
			}
			return $(returTRs);
		}

	

		function loadGrid() {
			var colNames = '<s:property value="mainGridCols"/>';
			var new_row = $("<tr id='headerrow'/>");
			var foot = "<tfoot class='colFoot'><tr>";
			
			$(colNames.split("%")).each(function(k, val) {
			 
				var accestyp ='16,17,18,19,28,23,9,31,39,38,40,76';
				var dataType ='6';
				if (!isEmpty(val) ) {
					var cols = val.split("#");
					oibj = {};
					var classN =" filterTdR ";
					if(cols[5]!=null && (accestyp.split(",").includes(cols[5].trim()) || dataType.split(",").includes(cols[6].trim()))){
						classN = classN+" noexp ";
					}
				
					if(cols[2]=='right'){
						classN = classN+" numeric ";
						
						foot = foot + "<th  class='colFoot' align='right' id='foot"+(k)+"'>";
					}else if(cols[2]=='dealer'){
                 classN = classN+" dealer ";

               foot = foot + "<th  class='colFoot' align='right' id='foot"+(k)+"'>";
                    }else{
						foot = foot + "<th   class='colFoot' id='foot"+(k)+"'>";
					}
					 var new_th = $('<th />');
					  $(new_th).text(cols[0]);
					  $(new_th).attr("id",cols[4]);
					  $(new_th).addClass(classN+" hd");
					  $(new_th).attr("data-index",cols[6]);
					  $(new_row).append(new_th);
				
					//gridColumnModels.push({name: cols[0],width:cols[1],sortable: false,frozen:true});
				}
			});
var postdata ={
		"villagecode":$('select[name ="v.code"]').val(),
		"gpcode":$('select[name ="gp.code"]').val(),
		"citycode":$('select[name ="c.code"]').val(),
		"localitycode":$('select[name ="ld.code"]').val(),
		"statecode":$('select[name ="s.code"]').val(),
		 "filterList" : function(){
			  return getPostdata();
		  }
		};
		
			$('#detailth').append(new_row);
			
		var plantData ='<s:property value="reportConfigFiltersJs"/>';
		if(plantData!=null && plantData!=''){
			plantData =plantData.replace(/&quot;/g,'"').replace(/\s+/g, ' ').trim();
			plantData = JSON.parse(plantData);
		}
			
			 table =  	createDataTableServer('detail',url+"detail.action?id="+ typez,{},plantData);
			
		}
		
		
		
		/*  function getPostdata(){
			var postData = {};
			deFil='<s:property value="getDefaultFilter()"/>';
			
			if(deFil!="1"&&deFil.includes(",")){
				//alert("inn")
				var fields = deFil.split(',');
				deFil=1;
				fields.forEach(function (item, index) {
				  console.log(item, index);
				  //var f=item.split(':');
				 //jQuery("#form_"+f[0].replace('.','_')).val(f[1]); 
				 if(item.split(':')[2]==0){
				postData[item.split(':')[0].split('~')[0]] = "1~"+item.split(":")[1]+"~"+item.split(':')[0].split('~')[1];
				}else{
					jQuery("#form_"+item.split(':')[0].replace('.','_')).val(item.split(":")[1]).change(); 
				}
				 
				});
				deFil=1;
			}
			if(deFil!="1"){
				//postData[deFil.split(':')[0]] = " = '"+deFil.split(':')[1]+"'";
				//$('select[name^='+deFil.split(":")[0]+'] option[value='+deFil.split(":")[1]+']').attr("selected","selected");
				if(deFil.split(':')[2]==0){
				postData[deFil.split(':')[0].split('~')[0]] = "1~"+deFil.split(":")[1]+"~"+deFil.split(':')[0].split('~')[1];
				}else{
					jQuery("#form_"+deFil.split(':')[0].replace('.','_')).val(deFil.split(":")[1]).change(); 
				}
		
			deFil=1;
			}
			
			if(fetchType=='3'){
			$(".filterClass").each(function(){
				var name = $(this).attr('name');
				var value = $(this).val();
				alert(value);
					if(value!=''){
					if($(this).hasClass("3")){
						postData[name] = " = '"+value+"'";
					}else if($(this).hasClass("4")){
						var dateVal  =value.split(" - ");
						postData[name] = " between  '"+ dateVal[0].trim() +"' and '"+ dateVal[1].trim()+" 23:59:59"+"'";
					}else if($(this).hasClass("1")){
						postData[name] = $(this).parent().find('#cond').val()+ " '"+value+"'";
					}else if($(this).hasClass("6")){
						postData[name] = " LIKE '%"+value+"%'";
					}else if($(this).hasClass("7") || $(this).hasClass("10")){
						postData[name] = " = FIND_IN_SET('"+value+"',"+name+")";
					}
					
				}
			});
			postData['branch_id'] = ' is not null';
			}else{
				$(".filterClass").each(function(){
					var name = $(this).attr('name');
					var value = $(this).val();
					if(value!=''){
						if($(this).hasClass("3")){
							postData[name.split('~')[0]] = "1~"+value+"~"+name.split('~')[1];
						}else if($(this).hasClass("4")){
							var dateVal  =value.split(" - ");
							postData[name] = "2~"+ dateVal[0].trim()+" 00:00:00" +"|"+ dateVal[1].trim()+" 23:59:59";
						}else if($(this).hasClass("6")){
							postData[name] = "8~"+value;
						}else if($(this).hasClass("10") && value!=null){
							postData[name.split('~')[0]] = "10~"+value;
						}else{
							postData[name.split('~')[0]] = "1~"+value;
						}
						
					}
				});
				postData['branchId'] = '9~null';
			}
			return JSON.stringify(postData);
		}
		  */
		
		function hideInternalDistrictFilter(){
			$('select[name ="district_name"]').next().hide();//for district filter
			$('select[name ="district_name"]').parent().next().hide();// for reset symbol
		}
		
		function decode_utf8(s) {
			  return decodeURIComponent(escape(s));
			}
		function containsAny(source,target)
		{
		    var result = source.filter(function(item){ return target.indexOf(item) > -1});   
		    return result[0];  
		}   
		function getSorted(arr, sortArr) {
			  var result = [];
			  for (var i = 0; i < arr.length; i++) {
			    console.log(sortArr[i], arr[i]);
			    result[i] = arr[sortArr[i]];
			  }
			  return result;
			}
	
		function applyFilters(){
			$('#detail').empty();
			 $('#detail').dataTable().fnClearTable();
			    $('#detail').dataTable().fnDestroy();
			loadGrid();
			
		}
		
		function resetFilters(){
			
			$('select[name ="s.code"]').val("").trigger("change");
			$('select[name ="ld.code"]').val("").trigger("change");
			$('select[name ="c.code"]').val("").trigger("change");
			$('select[name ="gp.code"]').val("").trigger("change");
			$('select[name ="v.code"]').val("").trigger("change");
			applyFilters();
		}
		
		function createElementFromHTML(htmlString) {
			  var div = $('<div/>');
			  $(div).wrapInner(htmlString);
			 return $(div).find('a:first').text();
			
			}
		
		function enablePopup(head, cont) {

			$(window).scrollTop(0);
			$('body').css('overflow', 'hidden');
			$(".bjqs").empty();
			var heading = '';
			var contentWidth = '100%';
			if (head != '') {
				heading += '<div style="height:10%;"><p class="bjqs-caption">'
						+ head + '</p></div>';
				contentWidth = '92%';
			}
			var content = "<div style='width:100%;height:" + contentWidth
					+ "'>" + cont + "</div>";
			$(".bjqs").append('<li>' + heading + content + '</li>')
			$(".bjqs-controls").css({
				'display' : 'block'
			});
			$('#modalWin').css('margin-top', '-260px');
			$modal.show();
			$overlay.show();
			$('#banner-fade').bjqs({
				height : 450,
				width : 600,
				showmarkers : false,
				usecaptions : true,
				automatic : true,
				nexttext : '',
				prevtext : '',
				hoverpause : false

			});

		}

		function loadCustomPopup() {
			$overlay = $('<div id="modOverlay"></div>');
			$modal = $('<div id="modalWin" class="ui-body-c"></div>');
			$slider = $('<div id="banner-fade" style="margin:0 auto;"><ul class="bjqs"></ul></div>')
			$close = $('<button id="clsBtn" class="btnCls">X</button>');

			$modal.append($slider, $close);
			$('body').append($overlay);
			$('body').append($modal);
			$modal.hide();
			$overlay.hide();

			jQuery("#clsBtn").click(function() {
				$('#modalWin').css('margin-top', '-230px');
				$modal.hide();
				$overlay.hide();
				$('body').css('overflow', 'visible');
			});
		}

		
		
		 var imgID = "";
			function popupImages(ids) {
				
				try{
					var str_array = ids.split(',~#');
					$("#mImagebody").empty();
					
					var mbody="";
					
					for(var i = 1; i < str_array.length; i++){
						//<img class="slidesjs-slide" src="sensitizingReport_populateImage.action?id='+str_array[i]+'" slidesjs-index="0"/>');
						
						if(i==1){
							mbody="";
							mbody="<div class='item active'>";
							mbody+='<img src="data:image/png;base64,'+str_array[i]+'"/>';
							mbody+="</div>";
						}else{
							mbody="";
							mbody="<div class='item'>";
							mbody+='<img src="data:image/png;base64,'+str_array[i]+'"/>';
							mbody+="</div>";
						}
						$("#mImagebody").append(mbody);
					 }
					
					//$("#mbody").first().addClass( "active" );
					
					document.getElementById("enableImageModal").click();	
				}
				catch(e){
					alert(e);
					}
				
			}
			
function printContract(val){
	var str_array = val.split(',#');
	 $.ajax({
		 type: "POST",
         async: false,
         data:{queryName:str_array[1],queryType:str_array[0],id:str_array[2],report:str_array[3]},
         url: "generalPop_getContracteTemplate",
      	 success: function(result) {
      		var json = jQuery.parseJSON(result);
      		var html_str = json.templateHtml;
      		var tabledata={};
      		var values = json.values;
      		
      		for (var i = 0; i < values.length; i++) {
      			if(html_str.includes("[:"+i+"]")){
          			html_str = html_str.replaceAll("[:"+i+"]", values[i]);
          		}else if(html_str.includes("[:"+i+"TR]")){
          			tabledata["[:"+i+"TR]"]=values[i];
          			
          		}
      		}
      		html_str = html_str.replaceAll("[:url]",json.url);
      		html_str = html_str.replaceAll("[:qr]",json.qr);
      		var win = window.open();
      		win.document.title=json.fileName;
      		win.document.body.innerHTML = html_str;
      		if(tabledata!=null||tabledata!='{}'){
      			
      			for (var key in tabledata) {
      			  if (tabledata.hasOwnProperty(key)) {
      			    var val = tabledata[key];
      			    console.log(val);
      			  let rows=val.split('##');
      			  
      			rows.forEach(dd => {
      				let row = document.createElement("tr");
        			let data=dd.split('~');
        			
        			data.forEach(d => {
        				let column = document.createElement("td");
        				let text = document.createTextNode(d);
        				column.appendChild(text)
        				row.appendChild(column);
        		});
        			win.document.getElementById(key).appendChild(row); 
      			});
      			  //console.log(win.document.getElementById(key));
             		
        			//console.log(row);
        			//console.log(win.document.getElementById(key));

      			  }
      			}
      			}
	
}
	 });
}
			
			function buttonEdcCancel() {
				document.getElementById("model-close-edu-btn").click();
			}
			function buttonImgCancel() {
				document.getElementById("model-close-edu-btn-img").click();
			}
			function buttonDataCancel(id) {
				document.getElementById(id).click();
			}
		
			function detailPopup(img) {
				var ids = img;
				$("#detailDataTitle").empty();
				$("#detailDataHead").empty();

				$("#detailDataBody").empty();
				if (ids != '') {
					var val = ids;
					var arg = val.split("~")[0].trim();
					var query = val.split("~")[2].trim();
					var head = val.split("~")[1].trim();
					var headArr = head.split('~');
					$.each(headArr, function(k, value) {
						var tr = $("<tr/>");
						var headArrs = value.split(',');
						$.each(headArrs, function(a, val) {
							
							var td = $("<td/>");
							td.text(val);
							tr.append(td);

						});
						$("#detailDataHead").append(tr);
					});
					var title = val.split("~")[3].trim();
					$("#detailDataTitle").append(title);
				}
				try {
					$.ajax({
						type : "POST",
						async : true,
						url : "generalPop_methodQuery.action",
						data : {
							query : query,
							param : arg,
							id : typez
						},
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
					alert(e);
				}

			}
			function detailPopup1(img) {
				var ids = img;
				$("#detailDataTitle").empty();
				$("#detailDataHead").empty();

				$("#detailDataBody").empty();
				if (ids != '') {
					var val = ids;
					var arg = val.split("~")[0].trim();
					var query = val.split("~")[2].trim();
					var head = val.split("~")[1].trim();
					var headArr = head.split('~');
					$.each(headArr, function(k, value) {
						var tr = $("<tr/>");
						var headArrs = value.split(',');
						$.each(headArrs, function(a, val) {
							
							var td = $("<td/>");
							td.text(val);
							tr.append(td);

						});
						$("#detailDataHead").append(tr);
					});
					var title = val.split("~")[3].trim();
					$("#detailDataTitle").append(title);
				}
				try {
					$.ajax({
						type : "POST",
						async : true,
						url : "generalPop_methodQueryDetail.action",
						data : {
							query : query,
							param : arg,
							id : typez
						},
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
					alert(e);
				}

			}

			function popupWindow(img) {

				var id1 = img;

				var carousel = $("<div>").addClass("carousel");
				try {

					$("#mbody").empty();

					var div = $("<div>");
					var image = '<img src="data:image/png;base64,'+id1+'" height="220" width="260"/>';
					div.append(image);
					carousel.append(div);

					$("#mbody").append(carousel);
					$(".carousel").carousel();
					document.getElementById("enableModal").click();
				} catch (e) {
					alert(e);
				}
			}
			function popupWindow(img) {
				
			}
		
			function showFarmMap(compCode) {
				var content = "<div id='map' class='smallmap'></div>";
				var landarry = new Array();
				if (compCode != '') {
					var val = compCode;
					var area;
					var coord;
					var hd; 
					
					if(val.includes("~") && val.includes("$") &&  val.includes("#")){
						 area = val.split("~")[0].trim().split("$")[1].trim();
						 coord = val.split("~")[1].trim();
						 hd = val.split("~")[0].trim().split("$")[0].trim(); 
						 enablePopup(hd, content);
							$('#clsBtn').attr('onclick', "hidePopup('" + compCode + "')");
							initMap();

							coord = coord.substring(0, coord.length - 1);
							$(coord.split('#'))
									.each(
											function(key, value) {
												var latLong = {};
												latLong.lat = parseFloat(
														value.split(',')[0].trim())
														.toFixed(5);
												latLong.lon = parseFloat(
														value.split(',')[1].trim())
														.toFixed(5);

												landarry.push(latLong);
											});

							loadMap(new Array(), landarry, area);
					}else{
						//only 2 coordinates allowed to create marker
						var cordinates = val.split(",");
						val = "<b> </b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>  </b>$~"+cordinates[0]+","+cordinates[1];
						 area = val.split("~")[0].trim().split("$")[1].trim();
						 coord = val.split("~")[1].trim();
						 hd = val.split("~")[0].trim().split("$")[0].trim(); 
						 enablePopup(hd, content);
							$('#clsBtn').attr('onclick', "hidePopup('" + compCode + "')");
							initMap();

							coord = coord.substring(0, coord.length - 1);
							$(coord.split('#'))
									.each(
											function(key, value) {
												var latLong = {};
												latLong.lat = parseFloat(
														value.split(',')[0].trim())
														.toFixed(5);
												latLong.lon = parseFloat(
														value.split(',')[1].trim())
														.toFixed(5);

												landarry.push(latLong);
											});

							loadMap(new Array(), landarry, area);
						 
						  var myLatLng = {lat: Number(cordinates[0]), lng: Number(cordinates[1])};

					        var map = new google.maps.Map(document.getElementById('map'), {
					          zoom: 7,
					          center: myLatLng
					        });

					        var marker = new google.maps.Marker({
					          position: myLatLng,
					          map: map,
					          title: 'Hello World!'
					        }); 
					}
					
					
					
				}

			}

			function loadMap(dataArr, landArea, area) {

				var url = window.location.href;
				var temp = url;
				coorArr = new Array();
				for (var i = 0; i < 1; i++) {
					temp = temp.substring(0, temp.lastIndexOf('/'));
				}
				var intermediateImg = "red_placemarker.png";
				var intermediatePointIcon = temp + '/img/' + intermediateImg;

				var infowindow = new google.maps.InfoWindow();

				var marker, i;
				$(dataArr).each(
						function(k, v) {
							marker = new google.maps.Marker({
								position : new google.maps.LatLng(v.latitude,
										v.longitude),
								icon : intermediatePointIcon,
								map : map
							});
							markers.push(marker);
							google.maps.event.addListener(marker, 'click',
									(function(marker, i) {
										return function() {
											infowindow.setContent(buildData(v));
											infowindow.open(map, marker);
										}
									})(marker, i));
							map.setCenter({
								lat : v.latitude,
								lng : v.longitude
							});
							map.setZoom(17);
						});
				var cords = new Array();
				var bounds = new google.maps.LatLngBounds();
				if (landArea.length > 0) {

					$(landArea).each(
							function(k, v) {
								cords.push({
									lat : parseFloat(v.lat),
									lng : parseFloat(v.lon)
								});
								var coordinatesLatLng = new google.maps.LatLng(
										parseFloat(v.lat), parseFloat(v.lon));
								coorArr.push(v.lat + "," + v.lon);
								bounds.extend(coordinatesLatLng);

							});
					var plotting = new google.maps.Polygon({
						paths : cords,
						strokeColor : '#50ff50',
						strokeOpacity : 0.8,
						strokeWeight : 2,
						fillColor : '#50ff50',
						fillOpacity : 0.45,
						editable : false,
						draggable : false
					});
					plotting.setMap(map);
					//  plotCOunt = plotCOunt + 1;
					var overlay = {
						overlay : plotting,
						type : google.maps.drawing.OverlayType.POLYGON
					};

					map.fitBounds(bounds);
					var arType = ' Ha';
					//alert("areaType:"+arType);
					var textTpe = "";

					textTpe = area + arType;
					//  $('#area').val(area);
					mapLabel2 = new MapLabel({

						text : textTpe,
						position : bounds.getCenter(),
						map : map,
						fontSize : 14,
						align : 'left'
					});
					mapLabel2.set('position', bounds.getCenter());

				} else {

				}

			}


			function initMap() {
				map = new google.maps.Map(document.getElementById('map'), {
					center : {
						lat : 11.0168,
						lng : 76.9558
					},
					zoom : 5,
					mapTypeId : google.maps.MapTypeId.HYBRID,
				});

			}
		
			function showMap(latitude, longitude) {
				//String[] a = latLon.split(",");

				try {
					var heading = '<s:text name="coordinates"/>:' + latitude + ","
							+ longitude;
					var content = "<div id='map' class='smallmap'></div>";

					enablePopup(heading, content);

					loadLatLonMap('map', latitude, longitude, "");
				} catch (e) {
					console.log(e);
				}
			}

			function loadLatLonMap(mapDiv, latitude, longitude, landArea) {
				var fProjection = new OpenLayers.Projection("EPSG:4326"); // Transform from WGS 1984
				var tProjection = new OpenLayers.Projection("EPSG:900913");
				try {
					jQuery("#map").css("height", "400px");
					jQuery("#map").css("width",
							(jQuery(".rightColumnContainer").width()));
					jQuery("#" + mapDiv).html("");
					var mapObject = new OpenLayers.Map(mapDiv, {
						controls : []
					});
					var googleLayer2 = new OpenLayers.Layer.OSM();
					mapObject.addLayer(googleLayer2);
					mapObject.addControls([ new OpenLayers.Control.Navigation(),
							new OpenLayers.Control.Attribution(),
							new OpenLayers.Control.PanZoomBar(), ]);
					// new OpenLayers.Control.LayerSwitcher()]);
					mapObject.zoomToMaxExtent();
					var setCenter = true;
					// alert(latitude+"===="+longitude);
					if (latitude != null && latitude != '' && longitude != null
							&& longitude != '' && longitude != 0 && latitude != 0
							&& landArea.length <= 0) {

						mapObject.setCenter(new OpenLayers.LonLat(longitude,
								latitude).transform(fProjection, tProjection), 17,
								false, false);
						setCenter = false;
						var markers = new OpenLayers.Layer.Markers("Markers");
						var size = new OpenLayers.Size(21, 25);
						var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
						var icon = new OpenLayers.Icon(iconImage, size, offset);
						var mark1 = new OpenLayers.Marker(new OpenLayers.LonLat(
								longitude, latitude).transform(fProjection,
								tProjection), icon);
						markers.addMarker(mark1);
						mapObject.addLayer(markers);
					}

					if (landArea.length > 0) {

						var polygonList = [], multuPolygonGeometry, multiPolygonFeature;
						var vector = new OpenLayers.Layer.Vector('multiPolygon');
						var pointList = [];
						for (var i = 0; i < landArea.length; i++) {
							var landObj = landArea[i];
							//alert(landObj.lat+"===="+landObj.lon);			
							if (landObj.lat != null && landObj.lat != ''
									&& landObj.lon != null && landObj.lon != ''
									&& landObj.lon != 0 && landObj.lat != 0) {
								var point = new OpenLayers.Geometry.Point(
										landObj.lon, landObj.lat).transform(
										fProjection, tProjection);
								pointList.push(point);
								if (setCenter) {
									mapObject.setCenter(new OpenLayers.LonLat(
											landObj.lon, landObj.lat).transform(
											fProjection, tProjection), 18, false,
											false);
									setCenter = false;
								}
							}
						}
						var linearRing = new OpenLayers.Geometry.LinearRing(
								pointList);
						var polygon = new OpenLayers.Geometry.Polygon(
								[ linearRing ]);
						polygonList.push(polygon);
						multuPolygonGeometry = new OpenLayers.Geometry.MultiPolygon(
								polygonList);
						multiPolygonFeature = new OpenLayers.Feature.Vector(
								multuPolygonGeometry);
						vector.addFeatures(multiPolygonFeature);
						mapObject.addLayer(vector);
					}

					//alert(jQuery("#lftCol").height()+"----"+jQuery("#rgtCol").height());
					//jQuery("#map").css("height",(jQuery("#lftCol").height()-68))

					//jQuery("#map").css("height","400px");
					//jQuery("#map").css("width",(jQuery(".rightColumnContainer").width()));

					//alert(jQuery("#map").height());
				} catch (err) {
					console.log(err);
				}
			}
			

			function reloadLocalityByState(stateCode){
				$('select[name ="ld.code"]').find('option').not(':first').remove();
				$('select[name ="ld.code"]').select2('val','');
				if(stateCode!='' && stateCode!=undefined){
			    
				$.ajax({
					 type: 'POST',
					  url: "farmer_populateLocalityByState.action",
					  data: {
							code : stateCode
							},
					 success:  function(result) {
							 
							 	$.each(result,function(k,v){
							 		$('select[name ="ld.code"]').append('<option role="option" value="'+k+'">'+v+'</option>');
								})
					 		},
					 async:true
				});
			    }
			    
			    $('select[name ="c.code"]').find('option').not(':first').remove();
			    $('select[name ="c.code"]').select2('val','');
				//reloadCityByLocality($(this).val());
			}

			function reloadCityByLocality(localityCode){
				$('select[name ="c.code"]').find('option').not(':first').remove();
				$('select[name ="c.code"]').select2('val','');
				 if(localityCode!='' && localityCode!=undefined){
					
				$.ajax({
					 type: 'POST',
					  url: "farmer_populateCityByLocality.action",
					  data: {
							code : localityCode
							},
					 success:  function(result) {
								
							 	$.each(result,function(k,v){
							 		$('select[name ="c.code"]').append('<option role="option" value="'+k+'">'+v+'</option>');
								})
					 		},
					 async:false
				});
				 }
			}

			function reloadGramPanchayathByCity(cityCode){
				$('select[name ="gp.code"]').find('option').not(':first').remove();
				$('select[name ="gp.code"]').select2('val','');

				 if(cityCode!='' && cityCode!=undefined){
				$.ajax({
					 type: 'POST',
					  url: "farmer_populateGramPanchaythByCity.action",
					  data: {
							code : cityCode
							},
					 success:  function(result) {
							 	$.each(result,function(k,v){
							 		$('select[name ="gp.code"]').append('<option role="option" value="'+k+'">'+v+'</option>');
								})
					 		},
					 async:false
				});
				 }
			}

			function reloadVillageByGramPanchayath(cityCode){
				 if(cityCode!='' && cityCode!=undefined){
				$.ajax({
					 type: 'POST',
					  url: "farmer_populateVillageByGramPanchayth.action",
					  data: {
							code : cityCode
							},
					 success:  function(result) {
								$('select[name ="v.code"]').find("option").remove();		
							 	$('select[name ="v.code"]').append('<option role="option" value="">Select</option>');
							 	$.each(result,function(k,v){
							 		$('select[name ="v.code"]').append('<option role="option" value="'+k+'">'+v+'</option>');
								})
					 		},
					 async:false
				});
				 }
			}
			
			function redirectTo(url){				
					document.editForm.redirectContent.value = window.location.href;
					document.editForm.action = url;
					document.editForm.submit();				
			}
			
			function redirectToDelete(url){
				var val = confirm('<s:text name="confirm.delete"/>');
				if(val)
				{
					document.editForm.redirectContent.value = window.location.href;
					document.editForm.action = url;
					document.editForm.submit();
				}
			}
			

			function exportXLS() {
				if ($('#detail').find("tbody tr td.dataTables_empty").text() == ''
						|| $('#detail').find("tbody tr td.dataTables_empty").text() == undefined) {
					var req = new XMLHttpRequest();
					var postdata = "filterList="
							+ encodeURIComponent(JSON.stringify(getPostData()));

					req.open("POST", "dynamicViewReportDT_populateXLS.action?id="
							+ typez, true);
					req.responseType = "blob";
					req.onload = function(event) {
						var blob = req.response;
						var fileName = "";
						var disposition = req
								.getResponseHeader('Content-Disposition');
						var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
						var matches = filenameRegex.exec(disposition);
						if (matches != null && matches[1]) {
							fileName = matches[1].replace(/['"]/g, '');
						}
						var link = document.createElement('a');
						link.href = window.URL.createObjectURL(blob);
						link.download = fileName;
						link.click();
					};
					req.setRequestHeader("Content-type",
							"application/x-www-form-urlencoded");
					req.send(postdata);

				} else {
					alert("No data available in table");
				}

			}

			function exportPDF() {
				if ($('#detail').find("tbody tr td.dataTables_empty").text() == ''
						|| $('#detail').find("tbody tr td.dataTables_empty").text() == undefined) {

				
					var req = new XMLHttpRequest();
					var postdata = "filterList="
							+ encodeURIComponent(JSON.stringify(getPostData()));

					req.open("POST", "dynamicViewReportDT_populatePDF.action?id="
							+ typez, true);
					req.responseType = "blob";
					req.onload = function(event) {
						var blob = req.response;
						var fileName = "";
						var disposition = req
								.getResponseHeader('Content-Disposition');
						var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
						var matches = filenameRegex.exec(disposition);
						if (matches != null && matches[1]) {
							fileName = matches[1].replace(/['"]/g, '');
						}
						var link = document.createElement('a');
						link.href = window.URL.createObjectURL(blob);
						link.download = fileName;
						link.click();
					};
					req.setRequestHeader("Content-type",
							"application/x-www-form-urlencoded");
					req.send(postdata);

				} else {
					alert("No data available in table");
				}

			}
function generateCSV() {


	   	$.ajax({
			 type: "POST",
	        async: false,
	        url:"dynamicViewReportDT_populateCSV.action",
	        data:{ filterList:JSON.stringify(getPostData(), true),id:typez},
	        success: function(result) {
	        	//alert(result.msg);
	        	if(result.status=="1")
	        	alert("File Placed Successfully");
	        	else{
	        		alert("Some error occur");
	        	}

	        }
		});



}
			function exportCSV() {
				var req = new XMLHttpRequest();

				req.open("GET", "dynamicViewReportDT_populateCSV.action?id="
						+ typez + "&filterList="
						+ encodeURIComponent(JSON.stringify(getPostData()), true));
				req.responseType = "blob";
				req.onload = function(event) {
					var blob = req.response;
					var fileName = "";
					var disposition = req.getResponseHeader('Content-Disposition');
					var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
					var matches = filenameRegex.exec(disposition);
					if (matches != null && matches[1]) {
						fileName = matches[1].replace(/['"]/g, '');
					}
					var link = document.createElement('a');
					link.href = window.URL.createObjectURL(blob);
					link.download = fileName;
					link.click();
				};
				req.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				req.send();
			}
			
			
		 	function getPostData() {

				var postData = {};
				deFil='<s:property value="getDefaultFilter()"/>';
				
				if(deFil!="1"&&deFil.includes(",")){
					//alert("inn")
					var fields = deFil.split(',');
					deFil=1;
					fields.forEach(function (item, index) {
					  console.log(item, index);
					  //var f=item.split(':');
					 //jQuery("#form_"+f[0].replace('.','_')).val(f[1]); 
					 if(item.split(':')[2]==0){
					postData[item.split(':')[0].split('~')[0]] = "1~"+item.split(":")[1]+"~"+item.split(':')[0].split('~')[1];
					}else{
						jQuery("#form_"+item.split(':')[0].replace('.','_')).val(item.split(":")[1]).change(); 
					}
					 
					});
					deFil=1;
				}
				if(deFil!="1"){
					//postData[deFil.split(':')[0]] = " = '"+deFil.split(':')[1]+"'";
					//$('select[name^='+deFil.split(":")[0]+'] option[value='+deFil.split(":")[1]+']').attr("selected","selected");
					if(deFil.split(':')[2]==0){
					postData[deFil.split(':')[0].split('~')[0]] = "1~"+deFil.split(":")[1]+"~"+deFil.split(':')[0].split('~')[1];
					}else{
						jQuery("#form_"+deFil.split(':')[0].replace('.','_')).val(deFil.split(":")[1]).change(); 
					}
			
				deFil=1;
				}
				
				if (fetchType == '3') {
					$('.filterClass')
							.each(
									function(i) {
										var value = $(this).val();
										var name = $(this).attr("id");
										if (value != '') {

											if ($(this).hasClass("1")
													|| $(this).hasClass("12")
													|| $(this).hasClass("11")) {
var condd =$(
		'.cond'
		+ name.split('~')[0])
.val();
condd =condd==undefined ? "1":condd;
alert(condd);
												postData[name.split('~')[0]] =condd
														+ " '" + value + "'";
											} else if ($(this).hasClass("5")) {
												postData[name.split('~')[0]] = " = '"
														+ value + "'";
											} else if ($(this).hasClass("4")) {
												var dateVal = value.split(" - ");
												postData[name.split('~')[0]] == " between  '"
														+ dateVal[0].trim()
														+ "' and '"
														+ dateVal[1].trim()
														+ " 23:59:59" + "'";
											} else if ($(this).hasClass("6")
													|| $(this).hasClass("3")) {
												postData[name.split('~')[0]] = " LIKE '%"
														+ value + "%'";
											} else if ($(this).hasClass("10")
													&& value != null) {
												postData[name.split('~')[0]] = " = FIND_IN_SET('"
														+ value
														+ "',"
														+ name.split('~')[0] + ")";
											} else {
												postData[name.split('~')[0]] = " LIKE '%"
														+ value + "%'";
											}

										}
									});
					postData['branch_id'] = ' is not null';
				} else {
					$('.filterclass').each(
							function(i) {
								var value = $(this).val();
								var name = $(this).attr("id");
								//alert($(this).hasClass().val());
								if ($(this).val() != '') {if ($(this).attr("id").includes("11")) {
									var dateVal = value.split(" - ");
									postData[name.split('~')[0]] = "2~"
											+ dateVal[0].trim()
											+ " 00:00:00" + "|"
											+ dateVal[1].trim()
											+ " 23:59:59";
									

							}else if ($(this).attr("id").includes("12")||$(this).attr("id").includes("13")||$(this).attr("id").includes("14")) {
										postData[name.split('~')[0]] = (name.split('~')[1]==undefined || name.split('~')[1]=='' ? "1" :name.split('~')[1]) +"~"
										+ value + "~"+ (name.split('~')[2]==undefined ? "2" : name.split('~')[2]) ;
									}else if ($(this).hasClass("1")
											|| $(this).hasClass("12")
											|| $(this).hasClass("11")) {
				var cnd = $('.cond' + name.split('~')[0]).val();
					cnd = cnd != undefined && cnd != null && cnd != '' ? cnd : '8';
										postData[name.split('~')[0]] = cnd
												+ "~"
												+ value
												+ "~"
												+ name.split('~')[1];
									}else if ($(this).hasClass("5") || $(this).hasClass("8")) {
										
												postData[name.split('~')[0]] = (name.split('~')[1]==undefined || name.split('~')[1]=='' ? "1" :name.split('~')[1]) +"~"
												+ value + "~"+ (name.split('~')[2]==undefined ? "2" : name.split('~')[2]) ;
											
											
										} else if ($(this).hasClass("4")||$(this).attr("id").includes("4")) {
											var dateVal = value.split(" - ");
											var cndv = $(name.split('~')[0]).val();
											if(cndv == "2~" ){
											postData[name.split('~')[0]] = "2~"
													+ dateVal[0].trim()
													+ " 00:00:00" + "|"
													+ dateVal[1].trim()
													+ " 23:59:59";
											}else{
												postData[name.split('~')[0]] = "8"
												+ "~"
												+ value
												+ "~"
												+ name.split('~')[1];
											}

	} else if ($(this).hasClass("6")
												|| $(this).hasClass("3")) {
											postData[name] = "8~" + value;
										} else if ($(this).hasClass("10")
												&& value != null) {
											postData[name.split('~')[0]] = "10~"
													+ value;
										} else {
											postData[name.split('~')[0]] = "8~"
													+ value;
										}

								
								}
							});
					postData['branchId'] = '9~null';
				}
				return postData;
			}
			 
			
			function filterColumns() {
			 var table = $('#detail').DataTable();
			 table.ajax.reload();
					/* table.ajax.url("dynamicViewReportDT_detail.action?id=" + typez+"&filterList="
						+ JSON.stringify(getPostData())).load(); */

			}
			
function popupPayment(ids) {
	
		 var str_array = ids.split(',#');
		
	   	var ref=str_array[1];
	   	var popTable=str_array[0];
	   	
	   	$.ajax({
			 type: "POST",
	        async: false,
	        url: "generalPop_populatePayment.action",
	        data:{ ref:ref,tableName:popTable,paymentStatus:0},
	        success: function(result) {
	        	//alert(result.msg);
	        	if(result.msg=="Success")
	        	alert("PRN Number Generated Successfully!");
	        	else{
	        		alert("Some error occur");
	        	}
	        	window.location.href = window.location.href;
	        }
		}); 
	  
			}
			
function QRCodeDownloadProcess(batchid) {	
	document.getElementById("batchId").value=batchid;			
	$('#QRCodeBatchDownload').submit();
}

function SortandPackQRCodeDownloadProcess(batchid) {	
	document.getElementById("txnId").value=batchid;			
	$('#SortPackQRCodeBatchDownload').submit();
}

var printWindowCnt=0;
var windowRef;
function printReceipt(receiptNo,methodValue){	
	jQuery("#receiptNo").val(receiptNo);	
	jQuery("#methodValuerp").val(methodValue);	
	var targetName="printWindow"+printWindowCnt;
	windowRef = window.open('',targetName);
	try{
		windowRef.referenceWindow = windowRef;		
	}catch(e){
	}
	jQuery("#receiptForm").attr("target",targetName);	
	jQuery("#receiptForm").submit();
	++printWindowCnt;
}

var printWindowCnts=0;
var windowRefs;
function printoutcomeReceipt(receiptNo){	
	jQuery("#TraceNo").val(receiptNo);	
	var targetName="printWindow"+printWindowCnts;
	windowRefs = window.open('',targetName);
	try{
		windowRefs.referenceWindow = windowRefs;		
	}catch(e){
	}
	jQuery("#outcomereceiptForm").attr("target",targetName);	
	jQuery("#outcomereceiptForm").submit();
	++printWindowCnts;
}
	</script>



	<button type="button" id="enableImageModal"
		class="hide addBankInfo slide_open btn btn-sm btn-success"
		data-toggle="modal" data-target="#slideImageModal"
		data-backdrop="static" data-keyboard="false">
		<i class="fa fa-plus" aria-hidden="true"></i>
	</button>

	<div id="slideImageModal" class="modal fade" role="dialog">
		<div class="modal- modal-sm">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" id="model-close-edu-btn-img"
						class="close hide" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="mhead"></h4>
				</div>
				<div class="modal-body">
					<div id="myImageCarousel" class="carousel slide"
						data-ride="carousel">
						<div class="carousel-inner" role="listbox" id="mImagebody">

						</div>

						<a class="left carousel-control" href="#myImageCarousel"
							role="button" data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
							<span class="sr-only">Previous</span>
						</a> <a class="right carousel-control" href="#myImageCarousel"
							role="button" data-slide="next"> <span
							class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
							<span class="sr-only">Next</span>
						</a>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
						onclick="buttonImgCancel()">
						<s:text name="close" />
					</button>
				</div>
			</div>

		</div>

	</div>



	<%-- FILTER SECTION --%>
	<s:form id="form">

		<s:if test="reportConfigFilters.size()>0">
			<div class="appContentWrapper marginBottom hide">
				<section class='reportWrap row'>
					<div class="gridly">
						<s:iterator value="reportConfigFilters" status="status">
							<div class='filterEle'>
								<label for="txt"><s:property
										value='%{getLocaleProperty(label)}' /></label>
								<div class="form-element">
									<s:if test="type==3 ">
										<s:select name="%{field}" list="options" headerKey=""
											headerValue="%{getText('txt.select')}" id="%{label}"
											class="form-control input-sm select2 filterClass 3" />
										<s:set var="personName" value="%{method}" />
									</s:if>
									<s:if test="type==5 ">
										<s:select name="%{field}" list="options" headerKey=""
											headerValue="%{getText('txt.select')}" id="%{label}"
											class="form-control input-sm select2 filterClass 5" />
										<s:set var="personName" value="%{method}" />
									</s:if>
									<s:if test="type==1">
										<select id="cond" cssClass="form-control" name="condition">
											<option value=">">Greater Than</option>
											<option value=">=">greater than or Equal to</option>
											<option value="<=">less than or Equal to</option>
											<option value="<">Less than</option>
											<option value="=">Equals</option>
											<option value="like">contains</option>
										</select>&nbsp;
									<s:textfield theme="simple" name="%{field}" id="%{label}"
											maxlength="25" cssClass="1 filterClass form-control" />


									</s:if>
									<s:if test="type==4">
										<s:textfield id="%{label}" name="%{field}"
											class="form-control input-sm filterClass dateFilterApp 4" />
									</s:if>
								</div>
							</div>
						</s:iterator>

					</div>
				</section>
			</div>
		</s:if>
	</s:form>

	<%-- GRID SECTION --%>
	<div class="dataTable-btn-cntrls" id="detailBtn">

		<ul class="nav nav-pills newUI-nav-pills hide" id="addBtn">
			<li class="" id=""><a data-toggle="pill" id="addService"
				href="#"> <i class="fa fa-plus"></i> <s:property
						value="%{getLocaleProperty('Add')}" />
			</a></li>
		</ul>
		<div class="error">
			<s:if test="hasActionErrors()">
			<s:actionerror />
			</s:if>
		</div>

	</div>
	<div class="appContentWrapper border-radius datatable-wrapper-class">
		<table id="detail" class="table">
			<thead class="thead-dark" id="detailth">
			</thead>
		</table>


	</div>
	<s:hidden name="startDate" id="hiddenStartDate"></s:hidden>
	<s:hidden name="endDate" id="hiddenEndDate"></s:hidden>
	<s:form id="activityDetail" target="_blank">

	</s:form>

	<s:form id="pdf" action="dynamicViewReportDT_populatePDF.action">
		<s:hidden name="pdfData" id="pdfData" />
		<s:hidden name="pdfFileName" id="pdfFileName" />
		<s:hidden name="id" id="id" />
	</s:form>
	<s:form name="redirectform">
		<s:hidden id="redirectContent" name="redirectContent"></s:hidden>
	</s:form>

	<s:form name="editForm">
		<s:hidden id="redirectContent" name="redirectContent"></s:hidden>
	</s:form>
	
<s:form id="QRCodeBatchDownload" action="dynamicViewReportDT_processQRBatchData">
	<s:hidden id="batchId" name="batchid" />
	 <s:hidden id="id" name="id"></s:hidden>
</s:form>

<s:form id="SortPackQRCodeBatchDownload" action="dynamicViewReportDT_processSortPackQRBatchData">
	<s:hidden id="txnId" name="txnId" />
	 <s:hidden id="id" name="id"></s:hidden>
</s:form>

<s:form id="receiptForm" action="dynamicViewReportDT_processPrintHTML"
		method="POST" target="printWindow">
		<s:hidden id="receiptNo" name="receiptNumber" />
		<s:hidden id="methodValuerp" name="methodValuerp" />
		<s:hidden id="id" name="id"></s:hidden>
	</s:form>
	
	<s:form id="outcomereceiptForm" action="dynamicViewReportDT_processPrintOutHTML"
		method="POST" target="printWindow">
		<s:hidden id="TraceNo" name="TraceNumber" />
		<s:hidden id="id" name="id"></s:hidden>
	</s:form>

	<s:form id="audioFileDownload"
		action="dynamicViewReportDT_downloadImage">

		<s:hidden id="pdfData" name="pdfData"></s:hidden>
		<s:hidden id="id" name="id"></s:hidden>
	</s:form>
	<%-- <s:form id="" action="">
		<div id="dialog" title="Dialog Title" class="modal-body">
			<p>M</p>
			<table>
				<tr>
					<td><s:property value=""/></td>
				</tr>
			</table>
		</div>

	</s:form> --%>

	<div id="slide" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" id="model-close-btn" class="close"
						data-dismiss="modal">&times;</button>
					<h4>
						<s:text name="Payment Information" />
					</h4>
				</div>
				<div class="modal-body">
					<table id="unRegTable" class="table table-bordered aspect-detail">
						<s:hidden id="popId1" name="paymentDetail.reference" class="popid" />
						<s:hidden name="paymentDetail.tableName" id="popTable" />
						<%-- <div id="validateError" style="text-align: left;"></div>
						<tr class="odd">
							<td><s:text name="pd.TaxPayerBankCode" /> <sup
								style="color: red;">*</sup></td>
							<td><s:select class="form-control  select2 " id="bankCode"
									name="paymentDetail.bankCode" listkey="id" listvalue="value"
									list="getCatList(getLocaleProperty('bankcode'))" headerKey=""
									headerValue="%{getText('txt.select')}" /></td>
							</td>
						</tr>
						<tr class="odd">
							<td><s:text name="pd.PaymentMode" /> <sup
								style="color: red;">*</sup></td>
							<td><s:select class="form-control  select2 "
									id="paymentMode" name="paymentDetail.paymentMode" listkey="id"
									listvalue="value"
									list="getCatList(getLocaleProperty('paymentMode'))"
									headerKey="" headerValue="%{getText('txt.select')}" /></td>
						</tr>
						<tr class="odd">
							<td><s:text name="pd.TaxPayerName" /> <sup
								style="color: red;">*</sup></td>
							<td><s:textfield id="payerName"
									name="paymentDetail.payerName" cssClass="form-control" /></td>
						</tr>
						<tr class="odd">
							<td><s:text name="pd.TIN" /> <sup style="color: red;">*</sup>
							</td>
							<td><s:textfield id="tin" name="paymentDetail.tin"
									cssClass="form-control" maxlength="10" /></td>
						</tr>
						<tr class="odd">
							<td><s:text name="pd.email" /> <sup style="color: red;">*</sup>
							</td>
							<td><s:textfield id="email" name="paymentDetail.email"
									cssClass="form-control" /></td>
						</tr>
						<tr class="odd">
							<td><s:text name="pd.mobilrno" /> <sup style="color: red;">*</sup>
							</td>
							<td><s:textfield id="mobileNo" name="paymentDetail.mobileNo"
									onkeypress="return isNumber(event);" maxlength="20"
									cssClass="form-control" /></td>
						</tr>
						<tr class="odd">
							<td colspan="2">
								<button type="button" Class="btnSrch btn btn-success"
									onclick="event.preventDefault();" id="save">
									<s:text name="save" />
								</button>
								<button type="button" Class="btnClr btn btn-warning"
									onclick="cancelModel();" id="cancel" data-dismiss="modal">
									<s:text name="Cancel" />
								</button>
							</td>
						</tr> --%>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
