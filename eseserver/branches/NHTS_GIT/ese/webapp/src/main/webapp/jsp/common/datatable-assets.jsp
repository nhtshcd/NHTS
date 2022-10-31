<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/ese-tags" prefix="e"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<link rel="stylesheet" type="text/css" media="screen"
	href="plugins/jplayer/jplayer.blue.monday.min.css" />

<link rel="stylesheet" type="text/css" media="screen"
	href="plugins/jqgrid/css/ui.jqgrid-bootstrap.css" />
<!-- <link rel="stylesheet"
	href="plugins/DataTables-1.10.15/media/css/dataTables.bootstrap4.min.css">
 -->
<link
	href="plugins/DataTables-1.10.18/DataTables-1.10.18/css/jquery.dataTables.min.css"
	rel="stylesheet" type="text/css" />
<script src="plugins/openlayers/OpenLayers.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?callback=initMap&client=gme-sourcetrace&v=3.28&libraries=geometry,drawing,places"></script>
<script src="js/maplabel-compiled.js?k=2.16"></script>
<script type="text/javascript"
	src="plugins/jplayer/jquery.jplayer.min.js"></script>
<script src="plugins/jqgrid/js/i18n/grid.locale-en.js"
	type="text/javascript"></script>
<script src="plugins/jqgrid/js/jquery.jqGrid.min.js"
	type="text/javascript"></script>

<script type="text/javascript" src="plugins/html2PDF/html2canvas.js"></script>
<script type="text/javascript" src="plugins/html2PDF/jspdf.min.js"></script>
<script type="text/javascript" src="plugins/html2PDF/html2pdf.js"></script>

<link
	href="plugins/DataTables-1.10.18/Responsive-2.2.2/css/responsive.dataTables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="plugins/DataTables-1.10.18/Buttons-1.5.4/css/buttons.dataTables.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="plugins/DataTables-1.10.18/Responsive-2.2.2/js/dataTables.responsive.min.js"></script>

<script
	src="plugins/DataTables-1.10.18/DataTables-1.10.18/js/jquery.dataTables.min.js"></script>
<script
	src="plugins/DataTables-1.10.18/DataTables-1.10.18/js/jquery.multisortable.js"></script>

<script src="js/dynamicReportRelated/dataTablePlugin/sum().js"></script>
<script
	src="plugins/DataTables-1.10.18/Buttons-1.5.4/js/dataTables.buttons.min.js"></script>
<%-- <script
	src="plugins/DataTables-1.10.18/Buttons-1.5.4/js/buttons.flash.min.js"></script> --%>
<script src="js/dynamicReportRelated/dataTablePlugin/pdfmake.min.js"></script>
<script src="js/dynamicReportRelated/dataTablePlugin/jszip.min.js"></script>
<script src="js/dynamicReportRelated/dataTablePlugin/vfs_fonts.js"></script>
<script
	src="plugins/DataTables-1.10.18/Buttons-1.5.4/js/buttons.html5.min.js"></script>
<script
	src="plugins/DataTables-1.10.18/Buttons-1.5.4/js/buttons.print.min.js"></script>

<script src="plugins/bootstrap_Multiselect/bootstrap-multiselect.js"></script>
<script
	src="plugins/DataTables-1.10.18/ColReorder-1.5.0/js/dataTables.colReorder.js"></script>

<link href="plugins/bootstrap_Multiselect/bootstrap-multiselect.css"
	rel="stylesheet" />
<link
	href="plugins/DataTables-1.10.18/ColReorder-1.5.0/css/colReorder.dataTables.min.css"
	rel="stylesheet" />
<link rel="stylesheet" href="plugins/datepicker/css/datepicker.css">
<link rel="stylesheet"
	href="plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css">
<link rel="stylesheet" href="plugins/select2/select2.min.css">
<link rel="stylesheet"
	href="plugins/bootstrap-daterangepicker/daterangepicker-bs3.css">
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script
	src="plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
<script src="plugins/select2/select2.min.js"></script>
<script src="plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script src="plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="js/form-elements.js"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="plugins/flexi/css/bjqs.css" />
<script src="plugins/flexi/js/bjqs-1.3.js" type="text/javascript"></script>
<script
	src="plugins/DataTables-1.10.18/Buttons-1.5.4/js/buttons.colVis.min.js"></script>
<%-- <script
	src="plugins/DataTables-1.10.18/ColReorder-1.5.0/js/datatable.colResize.js"></script>
	 --%>
<%-- <script
	src="plugins/DataTables-1.10.18/ColReorder-1.5.0/css/dataTables.colResize.css"></script>
 --%>

<script type="text/javascript">
	//$.jgrid.defaults.width = 780;
	$.jgrid.defaults.responsive = true;
	$.jgrid.defaults.styleUI = 'Bootstrap';
	var map;
	var $overlay;
	var $modal;
	var $slider;
	var fetchType='';
	$(function() {
		$('.menuToggle').on('click', function() {
			resizeJqGridWidth("detail", "baseDiv");
		});
		loadCustomPopup();
		var filters='<s:property value="filterSize"/>';
		//alert(filters);
	});
	var windowHeight="";
	var reportWindowHeight="";
	var postdata='<s:property value="postdata"/>';
	var filterda='';
	jQuery.fn.dataTable.Api.register( 'sum()', function ( ) {
		return this.flatten().reduce( function ( a, b ) {
			if ( typeof a === 'string' ) {
				a = a.replace(/[^\d.-]/g, '') * 1;
			}
			if ( typeof b === 'string' ) {
				b = b.replace(/[^\d.-]/g, '') * 1;
			}

			return a + b;
		}, 0 );
	} );
	

	/* jQuery.fn.dataTableExt.afnFiltering.push(
			  function(oSettings, aData, iDataIndex) {
			    if (typeof aData._date == 'undefined') {
			      aData._date = new Date(aData[0]).getTime();
			    }

			    if (minDateFilter && !isNaN(minDateFilter)) {
			      if (aData._date < minDateFilter) {
			        return false;
			      }
			    }

			    if (maxDateFilter && !isNaN(maxDateFilter)) {
			      if (aData._date > maxDateFilter) {
			        return false;
			      }
			    }

			    return true;
			  }
			); */
	
	jQuery(document).ready(function() {
		/* jQuery("#clsBtn").click(function() {
			$('#modalWin').css('margin-top', '-230px');
			$modal.hide();
			$overlay.hide();
			$('body').css('overflow', 'visible');
		}); */
		jQuery("#minus").click(function() {
			try{
			var flag = "edit";
			var index = jQuery("#fieldl").val();
			jQuery("." + index).addClass("hide");
			jQuery("." + index + "> select").val("");
			reloadGrid(flag);
			jQuery("#filter-fields").remove(jQuery("." + index));
			jQuery("#fieldl").val("");
			resetReportFilter();
			}catch (e) {
				console.log(e);
			}
		});

		jQuery("#plus").click(function() {
			var index = jQuery("#fieldl").val();
			if(index!=""){
			jQuery(".well").show();
			jQuery("." + index).removeClass("hide");
			jQuery("#searchbtn").append(jQuery("." + index));
			jQuery("#fieldl").val("");
			}			
		});
		windowHeight = ($(window).innerHeight()-260);
		reportWindowHeight= ($(window).innerHeight()-320);
		
	 	if(postdata!=null  && postdata!='null' && !isEmpty(postdata)){
		postdata = postdata.replace(/&quot;/g,'"');
		postdata = postdata.replace("\"{", "{");
		postdata = postdata.replace("}\"", "}");
		filterda= postdata;
		postdata = postdata==null  || postdata=='null'  ?  '' : JSON.stringify(postdata); 

	 	}
	 	
	 	$.fn.dataTable.ext.search.push(
		          function (settings, data, dataIndex) {
		     if(dataInd!=''){
		    	 alert("moment(data[dataInd])"+moment(data[dataInd]));
			     	
		     	  if(min!=null && max!=null && min!='' && max!='' &&  moment(data[dataInd])!=null){
		     			 var fDate,lDate,cDate;
			     	    cDate = moment(data[dataInd]);
			     	 if(min.isSame(max)){
			     		 if((min.isSame(cDate))) {
				     	        return true;
				     	    }
				     	    return false;
			     	   
			     	  }
			     	 else{
			     		fDate = min.toDate();
			     		cDate = cDate.toDate();
			     		lDate = max.toDate()
			    		if((cDate <= lDate && cDate >= fDate)) {
			     		
				     	        return true;
				     	    }
				     	    return false;
			     	  }
		     	  }else{
		     		 return true;
		     	  }
		     }else{
		    	 return true;
		     }
		     
		    }
		    );
	 	
	 	
	});

	function resizeJqGridWidth(grid_id, div_id) {
		$(window).on('resize', function() {
			$('#' + grid_id).setGridWidth($('#' + div_id).width(), true);
		}).trigger('resize');
	}

	function isDataAvailable(grid_id) {
		return (jQuery(grid_id).getDataIDs() != "" && jQuery(grid_id)
				.getDataIDs() != null)
	}

	function isDataMaximunDownload(grid_id) {
		return (jQuery("#detail").jqGrid("getGridParam", "records") <= 1000)
	}

	function showFields() {
		var path = document.getElementById('fieldl');
		var name = path.value;
		for (var cnt = 1; cnt <= path.options.length - 1; cnt++) {
			if (name == path.options[cnt].value) {
				document.getElementById(cnt).className = "";
				if (cnt == (path.options.length - 1)) {
					if (document.getElementById(cnt + 1) != null)
						jQuery("div#" + (cnt + 1)).attr("class", "");
					//document.getElementById(cnt+1).className=" ";
				}
			}
		}
	}

	function removeFields() {
		var path = document.getElementById('fieldl');
		var name = path.value;
		for (var cnt = 1; cnt <= path.options.length - 1; cnt++) {
			if (name == path.options[cnt].value) {
				findTag(document.getElementById(cnt));
				document.getElementById(cnt).className = "hide";
				if (cnt == (path.options.length - 1)) {
					if (document.getElementById(cnt + 1) != null) {
						findTag(document.getElementById(cnt + 1));
						document.getElementById(cnt + 1).className = "hide";
					}
				}
			}
		}
	}

	function findTag(tds) {
		try {
			if (tds.childNodes[1].getElementsByTagName("td")[1]
					.getElementsByTagName("input")[0] != undefined) {
				tds.childNodes[1].getElementsByTagName("td")[1]
						.getElementsByTagName("input")[0].value = "";
			} else if (tds.childNodes[1].getElementsByTagName("td")[1]
					.getElementsByTagName("select")[0] != undefined) {
				var selectbox = tds.childNodes[1].getElementsByTagName("td")[1]
						.getElementsByTagName("select")[0];
				selectbox.selectedIndex = 0;
			}
		} catch (e) {

		}
	}

	function getSubBranchValues(selectedBranch) {
		jQuery.post("customer_populateChildBranch.action", {
			selectedBranch : selectedBranch
		}, function(result) {
			jQuery("#gs_subBranchId").empty();
			insertOptions("gs_subBranchId", $.parseJSON(result));
			$("#gs_subBranchId").trigger("change");
		});
	}
	
	function populateChildBranch(selectedBranch) {
		jQuery.post("customer_populateChildBranch.action", {
			selectedBranch : selectedBranch
		}, function(result) {
			jQuery("#subBranchIdParam").empty();
			jQuery("#subBranchIdParam").select2();
			insertOptions("subBranchIdParam", $.parseJSON(result));
			/* $("#subBranchIdParam").trigger("change"); */
		});
	}

	function insertOptions(ctrlName, jsonArr) {
		document.getElementById(ctrlName).length = 0;
		addOption(document.getElementById(ctrlName), "Select", "");
		for (var i = 0; i < jsonArr.length; i++) {
			addOption(document.getElementById(ctrlName), jsonArr[i].name,
					jsonArr[i].id);
		}
	}

	function addOption(selectbox, text, value) {
		var optn = document.createElement("OPTION");
		optn.text = text;
		optn.value = value;
		selectbox.options.add(optn);
	}

	
    
    function getSubBranchValues(selectedBranch){
    	jQuery.post("customer_populateChildBranch.action",{selectedBranch:selectedBranch},function(result){
    		jQuery("#gs_subBranchId").empty();
    		insertOptions("gs_subBranchId",$.parseJSON(result));
    		$( "#gs_subBranchId" ).trigger( "change" );
    	});
    }
    
    function insertOptions(ctrlName, jsonArr) {
        document.getElementById(ctrlName).length = 0;
        addOption(document.getElementById(ctrlName), "Select", " ");
        for (var i = 0; i < jsonArr.length; i++) {
            addOption(document.getElementById(ctrlName), jsonArr[i].name, jsonArr[i].id);
        }
    }
    
    function addOption(selectbox, text, value){
        var optn = document.createElement("OPTION");
        optn.text = text;
        optn.value = value;
        selectbox.options.add(optn);
    }
    function retainFields(){
    if(postdata!=''){
    	
		  var jsonobj = JSON.parse(filterda);
		  jQuery("#detail").setGridParam ({postData: jsonobj});
		    $(jsonobj).each(function(k,v){
		    	if(v.filters!==undefined)
		    		{
				   $(v.filters.rules).each(function(k,v2){
					   var temp=v2.field;
					   if(v2.op=='eq'){
						   $("select[name='"+temp+"']").val(v2.data);
					   }else{
					   	$("input[name='"+temp+"']").val(v2.data);
					   }
		});    
		    		}
			});  
		     $('#detail').setGridParam({postData: jsonobj});
		     $('#detail')[0].triggerToolbar();
		}
    }
    function postDataSubmit(){
    var postdata =  JSON.stringify($('#detail').getGridParam('postData'));
	  postdata = postdata.replace(/&quot;/g,'"');
		postdata = postdata.replace("\"{", "{");
		postdata = postdata.replace("}\"", "}");
		  document.updateform.postdata.value =postdata;
    }
    function showFarmMap(compCode) {
		var content = "<div id='map' class='smallmap'></div>";
		var landarry = new Array();
		if (compCode != '') {
			var val = compCode;
			var area = val.split("~")[0].trim().split("$")[1].trim();
			var coord = val.split("~")[1].trim();
			var hd = val.split("~")[0].trim().split("$")[0].trim();
			enablePopup(hd, content);
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
		}

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
var url = window.location.href;
		
		var temp = url;

		 for(var i = 0 ; i < 1 ; i++) {
			  temp = temp.substring(0, temp.lastIndexOf('/'));
		 } 
		var iconImage = "red_placemarker.png";
		 iconImage = temp + '/img/'+iconImage;
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
		} catch (err) {
			console.log(err);
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
	function exportRecPDF(exportParams) {
		
		var dataa = {
			filtersList : exportParams
		};

		jQuery("#detail").jqGrid('excelExport', {
			url : 'dynamicViewReport_populateSingleRecordPDF.action?filtersList='+exportParams,
			page : 1,
			postData : dataa
		});

	}
	function showTaskDetail(refId, txnType) {
		    	if (txnType == '359') {

		    	document.activityDetail.action = "farm_detail.action?id=" + refId;
		    	document.activityDetail.submit();

		    	} else if (txnType == '308') {

		    	document.activityDetail.action = "farmer_detail.action?id=" + refId;
		    	document.activityDetail.submit();

		    	} else if (txnType == '357') {

		    	document.activityDetail.action = "farmCrops_detail.action?id="
		    	+ refId;
		    	document.activityDetail.submit();

		    	} else if (typez=='16') {
		        document.activityDetail.action = "dynmaicCertificationReport_detail.action?txnType=3&id=" + refId ;
		    	document.activityDetail.submit(); 
		    	}else if (typez=='17') {
			        document.activityDetail.action = "dynmaicCertificationReport_detail.action?txnType=2&id=" + refId ;
			    	document.activityDetail.submit(); 
			    	}else if (typez=='18') {
				        document.activityDetail.action = "dynmaicCertificationReport_detail.action?txnType=1&id=" + refId;
				    	document.activityDetail.submit(); 
				    	}

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
		$close = $('<button id="clsBtn" onclick="hidePopup()" class="btnCls">X</button>');

		$modal.append($slider, $close);
		$('body').append($overlay);
		$('body').append($modal);
		$modal.hide();
		$overlay.hide();

		
	}

	function hidePopup() {

		/*if($('#command').val()!=undefined && $('#command').val()!="update"){
		 $('#plotValue'+comCode).val('');

		 }*/
		$('#modalWin').css('margin-top', '-230px');
		//$modals.hide();
		$overlays.hide();
		$('body').css('overflow', 'visible');
		//$('#map').remove();

	}
	
	function  getLocation() {
		
		  if (navigator.geolocation) {
		    navigator.geolocation.getCurrentPosition(
		            (position) => {
		            	$('#currLat').val(position.coords.latitude);
		            	$('#currLon').val(position.coords.longitude);
		            	
		            });
		  } else {
		    alert("Geolocation is not supported by this browser.");
		  }
		}
	
</script>




