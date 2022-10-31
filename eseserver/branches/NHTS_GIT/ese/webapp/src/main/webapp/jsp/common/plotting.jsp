


<script
	src="https://maps.googleapis.com/maps/api/js?client=gme-sourcetrace&v=3.33&libraries=geometry,drawing,places"></script>
<script src="js/maplabel-compiled.js?k=2.18"></script>
<script src="js/KenyaKml.js?v=1.3"></script>
<script>
	var selectedShape;
	var mapLabel2;
	var coorArr = new Array();
	var drawingManager;
	var plotting;
	var new_paths;
	var alloverlays = [];
	var markers = [];
	var plotCOunt = 0;
	var tenantId = '<s:property value="getCurrentTenantId()"/>';

	function deletPolygon() {
		if (plotting != null) {
			plotting.setMap(null);
			mapLabel2.set('text', '');
			plotCOunt = plotCOunt - 1;
			
			if (plotCOunt <= 0) {
				drawingManager.setOptions({
					drawingControl : true
				});

				drawingManager
						.setDrawingMode(google.maps.drawing.OverlayType.POLYGON);
				plotting = null;
				$('.area').attr('readonly', false);
			}
			$('.area').val('');
			$('#jsonLatLonStr').val('');
			$('#farmLatLon').val('');

		}

	}

	function containsPolygon(bounds, polygon) {
		return polygon.getPaths().getArray().every(function(path) {
			return path.getArray().every(function(coord) {
				alert(coord);
				return bounds.contains(coord);
			});
		});
	}

	function initMap(farmBounds) {

		var url = window.location.href;
		var temp = url;

		for (var i = 0; i < 1; i++) {
			temp = temp.substring(0, temp.lastIndexOf('/'));
		}
		if (farmBounds == null || farmBounds == '' || farmBounds == undefined) {
			farmBounds = kenyaBound;
		}
		var intermediateImg = "red_placemarker.png";
		var intermediatePointIcon = temp + '/img/' + intermediateImg;
		map = new google.maps.Map(document.getElementById('map'), {
			center : {
				lat : -37.06,
				lng : 174.58
			},
			restriction : {
				latLngBounds : farmBounds,
				strictBounds : true,
			},
			zoom : 7,
			mapTypeId : google.maps.MapTypeId.HYBRID,
		});
		map.fitBounds(farmBounds);

		var ctaLayer = new google.maps.KmlLayer({
			url : "img/KENYA_mod.kml",
			suppressInfoWindows : true,
			map : map,
			zindex : 0,
			clickable : false
		});

		var polyOptions = {
			strokeWeight : 0,
			fillOpacity : 0.45,
			editable : true,
			draggable : false
		};

		drawingManager = new google.maps.drawing.DrawingManager({
			drawingMode : google.maps.drawing.OverlayType.POLYGON,
			drawingControl : true,
			drawingControlOptions : {
				position : google.maps.ControlPosition.TOP_CENTER,
				/*  drawingModes : [ 'marker', 'circle', 'polygon', 'polyline',
							'rectangle' ]  */
				drawingModes : [ 'marker', 'polygon' ]
			},
			markerOptions : {
				icon : intermediatePointIcon,
				draggable : true
			},
			polylineOptions : {
				editable : true,
				draggable : false
			},
			polygonOptions : polyOptions,
			circleOptions : {
				fillColor : '#ffff00',
				fillOpacity : 1,
				strokeWeight : 5,
				clickable : false,
				editable : true,
				zIndex : 1
			}
		});
		var polygonOptions = drawingManager.get('polygonOptions');
		polygonOptions.fillColor = '#50ff50';
		polygonOptions.strokeColor = '#50ff50';
		drawingManager.set('polygonOptions', polygonOptions);

		drawingManager.setMap(map);

		google.maps.event
				.addListener(
						drawingManager,
						'overlaycomplete',
						function(e) {

							var newShape = e.overlay;
							newShape.type = e.type;
							coorArr = new Array();
							setSelection(newShape);
							var bounds = new google.maps.LatLngBounds();
							var area = google.maps.geometry.spherical
									.computeArea(newShape.getPath());
							var metre = parseFloat(area).toFixed(2);
							isInsideBound = true
							for (var i = 0; i < newShape.getPath().getLength(); i++) {
								var ltpt = newShape.getPath().getAt(i)
										.toUrlValue(20);
								var latLon = ltpt.split(",");
								var coordinatesLatLng = new google.maps.LatLng(
										latLon[0], latLon[1]);
								if (!google.maps.geometry.poly
										.containsLocation(coordinatesLatLng,
												kenyaPluy)) {
									isInsideBound = false;
								} else {

									coorArr.push(newShape.getPath().getAt(i)
											.toUrlValue(20));
								}
							}
							if (isInsideBound) {
								if (e.type !== google.maps.drawing.OverlayType.MARKER) {
									// Switch back to non-drawing mode after drawing a shape.
									drawingManager.setDrawingMode(null);
									drawingManager.setOptions({
										drawingControl : false
									});

								}

								google.maps.event.addListener(newShape
										.getPath(), 'insert_at', function() {
									changePolugon(newShape);
								});

								google.maps.event.addListener(newShape
										.getPath(), 'remove_at', function() {

									changePolugon(newShape);
								});

								google.maps.event.addListener(newShape
										.getPath(), 'set_at', function(index,
										previous) {
									changePolugon(newShape);

								});

								plotCOunt = plotCOunt + 1;
								console.log(coorArr);
								$(coorArr)
										.each(
												function(k, v) {
													var latLon = v.split(",");
													var coordinatesLatLng = new google.maps.LatLng(
															latLon[0],
															latLon[1]);

													bounds
															.extend(coordinatesLatLng);
												});

								google.maps.event
										.addListener(
												newShape,
												'click',
												function(e) {
													if (e.vertex !== undefined) {
														if (newShape.type === google.maps.drawing.OverlayType.POLYGON) {
															var path = newShape
																	.getPaths()
																	.getAt(
																			e.path);
															path
																	.removeAt(e.vertex);
															if (path.length < 3) {
																newShape
																		.setMap(null);
															}
														}
														if (newShape.type === google.maps.drawing.OverlayType.POLYLINE) {
															var path = newShape
																	.getPath();
															path
																	.removeAt(e.vertex);
															if (path.length < 2) {
																newShape
																		.setMap(null);
															}
														}
													}
													setSelection(newShape);
												});

								var myLatlng = bounds.getCenter();

								$('#farmLatLon').val(myLatlng);

								//var acreConversion = (metre * 0.000247105);
								var test = (metre * 0.000247105);
								var acreConversion = parseFloat(test)
										.toFixed(2);

								textTpe = acreConversion;
								$('.area').val(acreConversion);
								setAutoValues();
$('.area').attr('readonly', 'readonly');

								mapLabel2 = new MapLabel({

									text : textTpe,
									position : myLatlng,
									map : map,
									fontSize : 14,
									align : 'left'
								});
								mapLabel2.set('position', myLatlng);
							} else {
								deleteSelectedShape();
								deletPolygon();
								alert("Plot outside boundary")
							}

						});

		var card = document.getElementById('pac-card');
		var input = document.getElementById('pac-input');
		var types = document.getElementById('type-selector');
		var strictBounds = document.getElementById('strict-bounds-selector');

		map.controls[google.maps.ControlPosition.TOP_RIGHT].push(card);

		const options = {
			bounds : farmBounds,
			componentRestrictions : {
				country : "ke"
			},
			fields : [ "address_components", "geometry", "icon", "name" ],
			strictBounds : true,
			types : [ "establishment" ],
		};
		var autocomplete = new google.maps.places.Autocomplete(input, options);
		autocomplete.setComponentRestrictions({
			country : [ "ke" ],
		});
		// Bind the map's bounds (viewport) property to the autocomplete object,
		// so that the autocomplete requests use the current map bounds for the
		// bounds option in the request.
		autocomplete.bindTo('bounds', map);

		var infowindow = new google.maps.InfoWindow();
		var infowindowContent = document.getElementById('infowindow-content');
		infowindow.setContent(infowindowContent);
		var marker = new google.maps.Marker({
			map : map,
			anchorPoint : new google.maps.Point(0, -29)
		});
		markers.push(marker);
		autocomplete
				.addListener(
						'place_changed',
						function() {
							infowindow.close();
							marker.setVisible(false);
							var place = autocomplete.getPlace();
							if (!place.geometry) {
								// User entered the name of a Place that was not suggested and
								// pressed the Enter key, or the Place Details request failed.
								window
										.alert("No details available for input: '"
												+ place.name + "'");
								return;
							}

							// If the place has a geometry, then present it on a map.
							if (place.geometry.viewport) {
								map.fitBounds(farmBounds);
							} else {
								map.setCenter(place.geometry.location);
								map.setZoom(17); // Why 17? Because it looks good.
							}
							marker.setPosition(place.geometry.location);
							marker.setVisible(true);

							var address = '';
							if (place.address_components) {
								address = [
										(place.address_components[0]
												&& place.address_components[0].short_name || ''),
										(place.address_components[1]
												&& place.address_components[1].short_name || ''),
										(place.address_components[2]
												&& place.address_components[2].short_name || '') ]
										.join(' ');
							}
						});

		setupClickListener('changetype-all', []);
		setupClickListener('changetype-address', [ 'address' ]);
		setupClickListener('changetype-establishment', [ 'establishment' ]);
		setupClickListener('changetype-geocode', [ 'geocode' ]);

		document.getElementById('use-strict-bounds').addEventListener('click',
				function() {
					console.log('Checkbox clicked! New state=' + this.checked);
					autocomplete.setOptions({
						strictBounds : this.checked
					});
				});

		// Clear the current selection when the drawing mode is changed, or when the
		// map is clicked.
		google.maps.event.addListener(drawingManager, 'drawingmode_changed',
				clearSelection);
		google.maps.event.addListener(map, 'click', clearSelection);

		google.maps.event.addDomListener(document
				.getElementById('delete-button'), 'click', function(event) {
			//alert("ff");
			coorArr = [];
			event.stopPropagation();
			event.preventDefault();
			event.cancelBubble = true;

			deleteSelectedShape();
			deletPolygon();
		});
	}

	function clearSelection() {
		if (selectedShape) {
			selectedShape.setEditable(false);
			selectedShape = null;
		}
	}

	function setSelection(shape) {
		clearSelection();
		selectedShape = shape;
		shape.setEditable(true);
	}

	function setupClickListener(id, types) {
		var radioButton = document.getElementById(id);
		if (radioButton != null) {
			radioButton.addEventListener('click', function() {
				autocomplete.setTypes(types);
			});
		}
	}

	function deleteSelectedShape() {

		if (selectedShape) {
			selectedShape.setEditable(false);

			selectedShape.setMap(null);
			if (mapLabel2 != undefined) {
				mapLabel2.set('text', '');
			}
			coorArr = [];
			plotCOunt = plotCOunt - 1;
			if (plotCOunt <= 0) {
				drawingManager.setOptions({
					drawingControl : true
				});

				drawingManager
						.setDrawingMode(google.maps.drawing.OverlayType.POLYGON);
				$('.area').attr('readonly', false);
			}
			$('.area').val('');

			$('#jsonLatLonStr').val('');
			$('#farmLatLon').val('');
		}

	}

	function loadMap(dataArr, landArea, area, isEdit) {

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
					google.maps.event.addListener(marker, 'click', (function(
							marker, i) {
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
		if (landArea != null && landArea != '' && landArea.length > 0) {
			drawingManager.setOptions({
				drawingControl : false
			});

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
			plotting = new google.maps.Polygon({
				paths : cords,
				strokeColor : '#50ff50',
				strokeOpacity : 0.8,
				strokeWeight : 2,
				fillColor : '#50ff50',
				fillOpacity : 0.45,
				editable : isEdit,
				draggable : false
			});
			plotting.setMap(map);
			plotCOunt = plotCOunt + 1;
			var overlay = {
				overlay : plotting,
				type : google.maps.drawing.OverlayType.POLYGON
			};

			drawingManager.setDrawingMode(null);
			map.fitBounds(bounds);

			var listener = google.maps.event.addListener(map, "idle", function () {
map.setCenter( bounds.getCenter());
			    map.setZoom(19);
			    google.maps.event.removeListener(listener);
			});

			/* var areaLeft = google.maps.geometry.spherical.computeArea(plotting
					.getPath());
			var metre = parseFloat(areaLeft).toFixed(2);
			var test = (metre * 0.000247105);
			var acreConversion = parseFloat(test).toFixed(2);
			 */
			textTpe = area + " Acre";
			$('.area').val(area);
			mapLabel2 = new MapLabel({

				text : textTpe,
				position : bounds.getCenter(),
				map : map,
				fontSize : 14,
				align : 'left'
			});
			mapLabel2.set('position', bounds.getCenter());

				
			if (isEdit) {

				plotting.getPaths().forEach(
						function(path, index) {

							google.maps.event.addListener(path, 'insert_at',
									function() {
										changePolugon(plotting);
									});

							google.maps.event.addListener(path, 'remove_at',
									function() {

										changePolugon(plotting);
									});

							google.maps.event.addListener(path, 'set_at',
									function(index, previous) {
										changePolugon(plotting);

									});

						});

				google.maps.event.addListener(plotting, 'rightclick', function(
						e) {
					// Check if click was on a vertex control point
					if (e.vertex == undefined) {
						return;
					} else {
						removeVertex(e.vertex);
					}
				});
			} else {

				map.controls[google.maps.ControlPosition.TOP_RIGHT].clear();
				$('.pac-card').remove();

			}
		} else {
			drawingManager
					.setDrawingMode(google.maps.drawing.OverlayType.POLYGON);
		}

	}

	function changePolugon(newPlotting) {

		mapLabel2.set('text', '');
		new_paths = newPlotting.getPath();
		var bounds = new google.maps.LatLngBounds();
		coorArr = new Array();
		for (var i = 0; i < new_paths.getLength(); i++) {
			coorArr.push(new_paths.getAt(i).toUrlValue(20));
			var xy = new_paths.getAt(i);
			var coordinatesLatLng = new google.maps.LatLng(
					parseFloat(xy.lat()), parseFloat(xy.lng()));

			bounds.extend(coordinatesLatLng);
		}
		console.log(coorArr);
		var area = google.maps.geometry.spherical.computeArea(new_paths);
		var metre = parseFloat(area).toFixed(2);
		var test = (metre * 0.000247105);
		var acreConversion = parseFloat(test).toFixed(2);
		var test2 = (acreConversion / 2.4711);
		var hectareConversion = parseFloat(test2).toFixed(2);
		var textTpe = "";
		textTpe = acreConversion;
		$('.area').val(acreConversion);

		var myLatlng = bounds.getCenter();

		$('#farmLatLon').val(myLatlng);

		mapLabel2 = new MapLabel({

			text : textTpe,
			position : myLatlng,
			map : map,
			fontSize : 14,
			align : 'left'
		});
		mapLabel2.set('position', myLatlng);
	}

	function removeVertex(vertex) {
		if (confirm("Are You sure to delete the point")) {
			var path = plotting.getPath();
			new_paths = plotting;
			path.removeAt(vertex);

		}
	}

	/* neighbouringDetails start  */

	var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
	var labelIndex = 0;
	var neighbouring_markers = [];
	var neighbouring_result = {};

	function addMarker(position) {

		var marker = new google.maps.Marker({
			position : position,
			map : map,
			title : "",
			draggable : false,
		// label: labels[labelIndex++ % labels.length]
		});

		var infowindow = new google.maps.InfoWindow({
			content : getHtmlForm(position)
		});

		//google.maps.event.addListener(marker, "rightclick", function (point) {delMarker(marker,position)});
		marker.addListener('click', function() {
			infowindow.open(map, marker);
		});

		marker.addListener('rightclick', function() {
			delMarker(marker, position)
		});

		neighbouring_markers.push(marker);
		neighbouring_result[position.lat() + "_" + position.lng()] = neighbouringDetails_backgroundData();

	}

	function delMarker(marker, position) {
		var index = neighbouring_markers.indexOf(marker);
		if (index != -1) {
			var userOption = confirm("Confirm to delete marker");
			if (userOption == true) {
				neighbouring_markers.splice(index, 1);
				marker.setMap(null);
				try {
					delete neighbouring_result[position.lat() + "_"
							+ position.lng()];
				} catch (err) {
					delete neighbouring_result[position.lat + "_"
							+ position.lng];
				}

			}
		}
	}

	function addExistingMarkers(neighbouringDetails) {

		$.each(neighbouringDetails, function(key, json) {
			//alert( key + ": " + json );
			var position = {};
			position["lat"] = Number(json.lat);
			position["lng"] = Number(json.lng);
			var marker = new google.maps.Marker({
				position : position,
				map : map,
				title : json.title,
				draggable : false,
			// label: labels[labelIndex++ % labels.length]
			});

			var infowindow = new google.maps.InfoWindow({
				content : getHtmlForm(json)
			});

			//google.maps.event.addListener(marker, "rightclick", function (point) {delMarker(marker,position)});
			marker.addListener('click', function() {
				infowindow.open(map, marker);
			});

			marker.addListener('rightclick', function() {
				delMarker(marker, json)
			});

			neighbouring_markers.push(marker);
			neighbouring_result[json.lat + "_" + json.lng] = json;
		});

	}

	// Sets the map on all markers_nearbyfarm in the array.
	function setMapOnAll(map) {
		for (var i = 0; i < neighbouring_markers.length; i++) {
			neighbouring_markers[i].setMap(map);

		}
	}

	function clearMarkers() {
		setMapOnAll(null);
	}

	// Shows any markers currently in the array.
	function showMarkers() {
		setMapOnAll(map);
	}

	// Deletes all markers in the array by removing references to them.
	function deleteMarkers() {
		var userOption = confirm("Confirm to delete all the markers");
		if (userOption == true) {
			clearMarkers();
			neighbouring_markers = [];
			neighbouring_result = {};

		}

	}

	function loadFarmMap(idd, isEdit) {

		var dataArr = new Array();
		var landarry = new Array();

		markers = [];

		plotCOunt = 0;
		if (idd != '' && idd != null) {

			var data = {

				selectedObject : idd,
			}
			$.ajax({
				url : 'generalPop_populateFarmCoordinates.action',
				type : 'post',
				dataType : 'json',
				async : false,
				data : data,
				success : function(data, result) {
					landarry = data;

				},
			});
			loadMap(dataArr, landarry.coord, landarry.area, isEdit);

		}
	}

	$(document).ready(function() {
		//$('.area').val('');
		$("#map").css("height", (($(window).innerHeight()) - 80));

		initMap();
	});
</script>


<div class="pac-card" id="pac-card">
	<div>
		<div id="type-selector" class="pac-controls">
			<input type="radio" name="type" id="changetype-all" checked="checked">
			<label for="changetype-all">All</label> <input type="radio"
				name="type" id="changetype-establishment"> <label
				for="changetype-establishment">Establishments</label> <input
				type="radio" name="type" id="changetype-address"> <label
				for="changetype-address">Addresses</label> <input type="radio"
				name="type" id="changetype-geocode"> <label
				for="changetype-geocode">Geocodes</label>
		</div>
		<div id="strict-bounds-selector" class="pac-controls">
			<input type="checkbox" id="use-strict-bounds" value=""> <label
				for="use-strict-bounds">Strict Bounds</label>
		</div>
	</div>
	<div id="pac-container">
		<input id="pac-input" type="text" placeholder="Enter a location">
	</div>
	<div class="pull-right">
		<button id="delete-button">
			<i class="fa fa-trash"></i>Delete Plot
		</button>

	</div>
</div>