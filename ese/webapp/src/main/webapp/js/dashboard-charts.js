var jsonData = "";
var jsonFarmerData = "";
var save = "";
var Refresh = "";
var currencyType =$("#currencyType").val();
var areaType=$("#areaType").val();
var drillup_districtCode;
var drillup_id;	
var codeForCropChart;
var drillUp_count = 0;
var locationCode_ForDrillUp = new Array();
var dataStyle = {
	normal : {
		label : {
			show : true,
			position : 'top',
			formatter : '{c}',
			textStyle : {
				color : '#333',
				fontFamily : 'roboto',
				fontSize : 14,
			}
		}
	}
};

function extractLabelValue(result, values) {

	json = $.parseJSON(result);
	var val = "";
	$.each(json, function(index, value) {
		$.each(value, function(key, value) {
			if (key == values) {
				val = value;
			}
		});
	});
	return val;
}

function findAndRemove(results, property, values) {
	var json = $.parseJSON(results);
	$.each(json, function(index, value) {
		$.each(value, function(key, value) {
			if (key == values) {
				delete json[0][key];
			}
		});
	});
	results = JSON.stringify(json);
	return results;
}

function loadFarmerGroupPieChart() {
	jQuery
			.post(
					"dashboard_populateFarmerGroupChartData.action",
					{},
					function(result) {
						var label = extractLabelValue(result, "Label");

						save = extractLabelValue(result, "save");
						Refresh = extractLabelValue(result, "refresh");
						result = findAndRemove(result, "id", "Label");
						if (result == "" || result == null || result == "[{}]") {
							$("#doughnutChartFarmer")
									.html(
											"<p style='font-family:sans-serif;font-size:18px;margin-top:0%'><b>"
													+ label
													+ "</b></p><h4 style='margin-top:50%;text-align:center'>No Data Available.</h4>");
						} else {
							jsonFarmerData = jQuery.parseJSON(result);
							DoughnutChartFarmer(label);
						}
					});

}


function loadFarmerCountPieChart() {
	jQuery
			.post(
					"dashboard_populateFarmerCountChartData.action",
					{},
					function(result) {
						var label = extractLabelValue(result, "Label");
						save = extractLabelValue(result, "save");
						Refresh = extractLabelValue(result, "refresh");
						result = findAndRemove(result, "id", "Label");
						if (result == "" || result == null || result == "[{}]") {
							$("#doughnutFarmerCountByBranch")
									.html(
											"<p style='font-family:sans-serif;font-size:18px;margin-top:0%'><b>"
													+ label
													+ "</b></p><h4 style='margin-top:50%;text-align:center'>No Data Available.</h4>");
						} else {
							var jsonFarmerData = jQuery.parseJSON(result);
							DoughnutFarmerCountByBranch(jsonFarmerData, label);
						}
					});
}

function loadTotalAcreChart() {
	jQuery
			.post(
					"dashboard_populateTotalAcreChartData.action",
					{},
					function(result) {
						var label = extractLabelValue(result, "Label");
						var labelProd = extractLabelValue(result,
								"LabelProduction");
						save = extractLabelValue(result, "save");
						Refresh = extractLabelValue(result, "refresh");
						result = findAndRemove(result, "id", "Label");
						if (result == "" || result == null || result == "[{}]") {

							$("#totalAcreByBranch")
									.html(
											"<p style='font-family:sans-serif;font-size:18px;margin-top:0%'><b>"
													+ label
													+ "</b></p><h4 style='margin-top:50%;text-align:center'>No Data Available.</h4>");
						} else {
							var jsonAcreData = jQuery.parseJSON(result);
							DoughnutTotalAcre(jsonAcreData, label);
							DoughnutTotalAcreProduction(jsonAcreData, labelProd);
						}
					});

}

function loadTotalAcreByVillageChart() {
	jQuery
			.post(
					"dashboard_populateTotalAcreByVillageChartData.action",
					{},
					function(result) {
						var label = extractLabelValue(result, "Label");

						save = extractLabelValue(result, "save");
						Refresh = extractLabelValue(result, "refresh");
						result = findAndRemove(result, "id", "Label");
						if (result == "" || result == null || result == "[{}]") {
							$("#totalAcreByVillage")
									.html(
											"<p style='font-family:sans-serif;font-size:18px;margin-top:0%'><b>"
													+ label
													+ "</b></p><h4 style='margin-top:50%;text-align:center'>No Data Available.</h4>");
						} else {
							var jsonAcreData = jQuery.parseJSON(result);
							DoughnutTotalAcreByVillage(jsonAcreData, label);

						}
					});

}


function formFilterText(idsArr) {
	var filterTxt = "";
	$(idsArr).each(function(k, v) {
		var val = $("#" + v).text();
		if (!isEmpty(val)) {
			filterTxt += $("#" + v).find('option').eq(0).text() + ": " + val;
		}
	})
	return filterTxt;
}

function barChart() {

	var BarChart = echarts.init(document.getElementById('barChart'));
	var option = {
		title : {
			text : 'Sales Data',
		},
		backgroundColor : new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [ {
			offset : 0,
			color : '#fbfcfe'
		}, {
			offset : 1,
			color : '#eceff5'
		} ]),
		tooltip : {
			show : true
		},
		legend : {
			data : [ 'Sales' ],
			x : "left",
			y : "bottom"
		},
		xAxis : [ {
			type : 'category',
			data : [ "Shirts", "Sweaters", "Chiffon Shirts", "Pants",
					"High Heels", "Socks" ]
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [ {
			"name" : "Sales",
			"type" : "bar",
			"data" : [ 5, 20, 40, 10, 10, 20 ]
		} ],
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'pie', 'funnel' ],
					option : {
						funnel : {
							x : '25%',
							width : '50%',
							funnelAlign : 'center',
							max : 1548
						}
					}
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
	};
	BarChart.setOption(option);

}

/* %%%%%%%%%%%%%%%%%%%%%%######$$$$$$$$$$$$$$$$#####%%%%%%%%%%%%%%%%%%%%%% */

/* %%%%%%%%%%%%%%%%%%%%%%######$$$$$$$$$$$$$$$$#####%%%%%%%%%%%%%%%%%%%%%% */

function farmerCropsBarChart(jsonFarmerData, label, farmerCountLabel,
		acregeLabel, productionLabel) {
	esecharts
			.chart(
					'farmercropBarChart',
					{
						chart : {
							type : 'column'
						},
						title : {
							text : ''
						},
						/*
						 * subtitle: { text: 'Click the columns to view
						 * versions. Source: <a
						 * href="http://netmarketshare.com">netmarketshare.com</a>.' },
						 */
						xAxis : {
							type : 'category'
						},
						yAxis : [ {
							type : 'value'
						} ],
						legend : {
							enabled : false
						},
						plotOptions : {
							series : {
								borderWidth : 1,
								dataLabels : {
									enabled : true
								/* format: '{point.y:.1f}%' */
								}
							}
						},

						tooltip : {
							headerFormat : '<span style="font-size:11px">{series.name}</span><br>',
							pointFormat : '<span style="color:{getRandomColor()}">{point.name}</span>: <b>{point.y}</b> {point.units}<br/>'
						},

						series : [ {
							name : 'Farmer Crop Details',
							colorByPoint : getRandomColor(),
							// color:getRandomColor(),
							data : (function() {
								var res = [];
								var len = 0;
								if (getCurrentTenantId() != "fincocoa") {
									var units = new Array("", "Acres", "Kgs",
											"Kgs");
								} else {
									var units = new Array("", "Hectares",
											"Kgs", "Kgs");
								}

								$.each(jsonFarmerData, function(index, value) {

									if (value.values !== undefined) {
										res.push({
											value : value.item,
											name : value.item,
											y : value.values,
											units : units[index]

										// drilldown:value.group.replace("
										// ","_")
										});
									}
								});
								return res;
							})()
						} ]
					/*
					 * drilldown: {
					 * 
					 * series:drillArray
					 *  }
					 */
					});

}


function isEmpty(val) {
	if (val == null || val == undefined || val.toString().trim() == "") {
		return true;
	}
	return false;
}

function dateFormatTransform(date) {
	var d = new Date(date);
	return (twoDigitFormat(d.getMonth() + 1) + '/'
			+ twoDigitFormat(d.getDate()) + '/' + d.getFullYear());
}

function twoDigitFormat(number) {
	var myNumber = number;
	var formattedNumber = ("0" + myNumber).slice(-2);
	return (formattedNumber);
}

function listState(obj) {
	jQuery.post("dashboard_populateStateList.action", {
		id : obj.value,
		dt : new Date(),
		selectedBranch : obj
	}, function(result) {
		$(".stateName").empty();
		insertOptionsByClass1('stateName', JSON.parse(result));
	});
}

function insertOptionsByClass1(ctrlName, jsonArr) {
	$("." + ctrlName).append($('<option>', {
		value : "",
		text : 'Select State'
	}));

	$.each(jsonArr, function(i, value) {
		$("." + ctrlName).append

		$("." + ctrlName).append($('<option>', {
			value : value.id,
			text : value.name
		}));
	});

	$(".select2").select2();
}
function farmerByLocationChart_bar(branch){
	
	
	
	
	jQuery.post("dashboard_populateFarmersByLocation.action",{selectedBranch:branch},function(result) {
		json = $.parseJSON(result);
		if(!isEmpty(json)){
		$.each(json, function(index, value) {
			renderFarmerByLocationChart(value.branch,value.country,value.state,value.locality,value.municipality,value.gramPanchayat,value.village,value.farmerDetails,value.getGramPanchayatEnable);
		});
	   }
	});
	
	
	
	
}	

function renderFarmerByLocationChart(branch,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable){
	var tenantId = getCurrentTenantId();
	var branchForPieChart;
	var iteration1 = 1;
	var iteration2 = 1;
	var dataLength = 0;
	var rightCount = 0;
	var rightClick = 0;
	var locationTitleArray;
	if(isEmpty(getCurrentBranch())){
		locationTitleArray = "Farmer By Branch";
	}
	else{
	locationTitleArray=$('#farmerbyCountry').val() ;
	}
	
	var unit = "Hectare";
	
	var titleForFarmAreaChart = locationTitleArray;
	titleForFarmAreaChart =  $('#farmAreaByCountry').val()
	
	var chart = new	esecharts.chart('farmerByLocationChart_bar', {
	    chart: {
	        type: 'column',
	        marginLeft: 100,
	        marginRight: 100,
	             	
	            
	            	
	        
	    },
	    title: {
	    	
	    	
	        text:locationTitleArray
	        	
	    },
	   
	    xAxis: {
	        type: 'category',
	       
	    },
	    yAxis: {
	        title: {
	            text: $('#farmrsCount').val()
	        }

	    },
	    legend: {
	        enabled: false
	    },
	    plotOptions: {
	    	 
		        series: {
		           allowPointSelect: false,
		            dataLabels: {
		                enabled: true,
		                format: '{point.y}'
		            }
		        }
	    },

	    tooltip: {
	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	       	pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> <br/>'
	    },

	    series: [{
	        name: 'Location',
	        colorByPoint: true,
	        data : (function() {
						var res = [];
						if(isEmpty(getCurrentBranch())){
							$.each(branch, function(index, branch) {
								//if(iteration1 <= 10){
									res.push({
										name :branch.branchName,
										y : branch.count,
										drilldown:branch.branchId,
										id : branch.branchId
									 });
									/*	iteration1 = iteration1+1;
										dataLength = dataLength+1;
								}else{
									dataLength = dataLength+1;
								}*/
							});
							farmerByLocationChart_pie("B",branchForPieChart,unit,titleForFarmAreaChart);
						}else{
							$.each(country, function(index, country) {
								//if(iteration1 <= 10){
									res.push({
											name :country.countryName,
											y : country.count,
											drilldown:country.countryCode,
											id : country.countryCode
										 });
										/*iteration1 = iteration1+1;
										dataLength = dataLength+1;
								}else{
									dataLength = dataLength+1;
								}*/
							});
							farmerByLocationChart_pie('first_level_Branch_Login',getCurrentBranch(),unit,titleForFarmAreaChart);
						}
						
						return res;
					})(),
						point : {
								events : {
									click : function(event) {
								
										if(isEmpty(getCurrentBranch())){
							
											populate_countryInLoction(this.id,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
										}else{
										    populate_stateInLocation(this.id,getCurrentBranch(),country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,getCurrentBranch());
										}
									}
								}
							}
				 }],
		
		drilldown: {},
	
	  }, function(chart) {});
}
function populate_countryInLoction(branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch){
//alert(titleForFarmAreaChart);
	var tenantId = getCurrentTenantId();
	var branchForPieChart;
	var iteration1 = 1;
	var iteration2 = 1;
	var dataLength = 0;
	var rightCount = 0;
	var rightClick = 0;
	codeForCropChart=branchId;
	//alert(codeForCropChart);
	var locationTitleArray=$('#farmerbyCountry').val()
var unit = "Hectare";
	
	var titleForFarmAreaChart = locationTitleArray;
	titleForFarmAreaChart = $('#farmAreaByCountry').val()
	esecharts
	.chart(
			'farmerByLocationChart_bar',
			{
				chart : {
					type : 'column',
					// backgroundColor:'rgba(255, 255, 255, 0.0)',
					  marginLeft: 100,
				        marginRight: 100,
					/*options3d : {
						enabled : false,
						alpha : 10,
						beta : 20,
						depth : 70
					}*/
				},

				 title: {
				    	
				    	
				        text:locationTitleArray
				        	
				    },
				   
				    xAxis: {
				        type: 'category',
				       
				    },
				    yAxis: {
				        title: {
				            text: $('#farmrsCount').val()
				        }

				    },
				    legend: {
				        enabled: false
				    },
				    plotOptions: {
				    	 
					        series: {
					           allowPointSelect: false,
					            dataLabels: {
					                enabled: true,
					                format: '{point.y}'
					            }
					        }
				    },

				    tooltip: {
				        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
				       	pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> <br/>'
				    },

				series : [
						{
							name : 'Location',
							colorByPoint: true,
							colorByPoint: true,
							data : (function() {
								var res = [];
								var len = 0;
							
							
													$.each(country, function(index, country) {
														if(branchId == country.branchId){
															res.push({
																name :country.countryName,
																y : country.count,
																drilldown:country.countryCode,
																id : country.countryCode
															});
														}
													});
													//alert(id);
													farmerByLocationChart_pie(branchId,branchId,unit,titleForFarmAreaChart);
								return res;
							})(),
							point : {
								events : {
									click : function(event) {
										    populate_stateInLocation(this.id,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
										
									}
								}
							}
					
						}
						],

			});
	//customMobSowingBackButton(warehouse,mobileUser,branch);
customBackrenderFarmerByLocationChart(branch,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable);

}
function customBackrenderFarmerByLocationChart(branch,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable) {
	 var chart = $('#farmerByLocationChart_bar').esecharts();
	    normalState = new Object();
	    normalState.stroke_width = null;
	    normalState.stroke = null;
	    normalState.fill = null;
	    normalState.padding = null;
	    normalState.r = null;

	    hoverState = new Object();
	    hoverState = normalState;

	    pressedState = new Object();
	    pressedState = normalState;

	    var custombutton = chart.renderer.button($('#back').val(), 100, 20, function(){
	    	renderFarmerByLocationChart(branch,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable);
	    },null,hoverState,pressedState).add();
}

function populate_stateInLocation(countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch){
	var tenantId = getCurrentTenantId();
	var branchForPieChart;
	var iteration1 = 1;
	var iteration2 = 1;
	var dataLength = 0;
	var rightCount = 0;
	var rightClick = 0;
	codeForCropChart=countryCode;
	if(tenantId=='galaxyrice'){
		var locationTitleArray='Farmer By Province';
	}else{
		var locationTitleArray=$('#farmerbyState').val()
	}
	var unit = "Hectare";
		var titleForFarmAreaChart = locationTitleArray;
		titleForFarmAreaChart =$('#farmAreaByState').val()
	//alert(tenantId);

	esecharts
	.chart(
			'farmerByLocationChart_bar',
			{
				chart : {
					type : 'column',
					// backgroundColor:'rgba(255, 255, 255, 0.0)',
					  marginLeft: 100,
				        marginRight: 100,
					/*options3d : {
						enabled : false,
						alpha : 10,
						beta : 20,
						depth : 70
					}*/
				},

				 title: {
				    	
				    	
				        text:locationTitleArray
				        	
				    },
				   
				    xAxis: {
				        type: 'category',
				       
				    },
				    yAxis: {
				        title: {
				            text: $('#farmrsCount').val()
				        }

				    },
				    legend: {
				        enabled: false
				    },
				    plotOptions: {
				    	 
					        series: {
					           allowPointSelect: false,
					            dataLabels: {
					                enabled: true,
					                format: '{point.y}'
					            }
					        }
				    },

				    tooltip: {
				        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
				       	pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> <br/>'
				    },

				series : [
						{
							name : 'Location',
							colorByPoint: true,
							data : (function() {
								var res = [];
								var len = 0;
							/*	$.each(municipality, function(index, municipality) {
					    			res.push({	
					            		name: municipality.municipalityName,
							            id: municipality.municipalityCode,
							            data : (function() {
													var res2 = [];*/
								$.each(state, function(index, state) {
									if(countryCode == state.countryCode){
										res.push({
											name :state.stateName,
											y : state.count,
											drilldown:state.stateCode,
											id : state.stateCode
										});
									}
								});
								farmerByLocationChart_pie(countryCode,branchId,unit,titleForFarmAreaChart);				
											/*	return res2;
										})()
									});*/
					    	//	});
								return res;
							})(),

							point : {
								events : {
									click : function(event) {
										
										    populate_localityInLocation(this.id,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
										
									}
								}
							}
						}
						],

			});
	//customMobSowingBackButton(warehouse,mobileUser,branch);
	customBackpopulate_countryInLoction(branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch)

}

function customBackpopulate_countryInLoction(branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch) {
	 var chart = $('#farmerByLocationChart_bar').esecharts();
	    normalState = new Object();
	    normalState.stroke_width = null;
	    normalState.stroke = null;
	    normalState.fill = null;
	    normalState.padding = null;
	    normalState.r = null;

	    hoverState = new Object();
	    hoverState = normalState;

	    pressedState = new Object();
	    pressedState = normalState;

	    var custombutton = chart.renderer.button($('#back').val(), 500, 20, function(){
	    	populate_countryInLoction(branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
	    },null,hoverState,pressedState).add();
}



function populate_localityInLocation(stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch){


	var tenantId = getCurrentTenantId();
	var branchForPieChart;
	var iteration1 = 1;
	var iteration2 = 1;
	var dataLength = 0;
	var rightCount = 0;
	var rightClick = 0;
	codeForCropChart=stateid;
	var locationTitleArray=$('#farmerByDistrict').val()
	var unit = "Hectare";
		
		var titleForFarmAreaChart = locationTitleArray;
		titleForFarmAreaChart =$('#farmAreaByDistrict').val()

	esecharts
	.chart(
			'farmerByLocationChart_bar',
			{
				chart : {
					type : 'column',
					// backgroundColor:'rgba(255, 255, 255, 0.0)',
					  marginLeft: 100,
				        marginRight: 100,
					/*options3d : {
						enabled : false,
						alpha : 10,
						beta : 20,
						depth : 70
					}*/
				},

				 title: {
				    	
				    	
				        text:locationTitleArray
				        	
				    },
				   
				    xAxis: {
				        type: 'category',
				       
				    },
				    yAxis: {
				        title: {
				            text: $('#farmrsCount').val()
				        }

				    },
				    legend: {
				        enabled: false
				    },
				    plotOptions: {
				    	 
					        series: {
					           allowPointSelect: false,
					            dataLabels: {
					                enabled: true,
					                format: '{point.y}'
					            }
					        }
				    },

				    tooltip: {
				        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
				       	pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> <br/>'
				    },

				series : [
						{
							name : 'Location',
							colorByPoint: true,
							data : (function() {
								var res = [];
								var len = 0;
							/*	$.each(municipality, function(index, municipality) {
					    			res.push({	
					            		name: municipality.municipalityName,
							            id: municipality.municipalityCode,
							            data : (function() {
													var res2 = [];*/
								$.each(locality, function(index, locality) {
									if(stateid == locality.stateCode){
										
										res.push({	name :locality.localityName,
										y : locality.count,
										drilldown:locality.localityCode,
										id : locality.localityCode
										});
									}
								});
								farmerByLocationChart_pie(stateid,branchId,unit,titleForFarmAreaChart);			
											/*	return res2;
										})()
									});*/
					    	//	});
								return res;
							})(),

							point : {
								events : {
									click : function(event) {
										
										    populate_municipalityInLocation(this.id,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
										
									}
								}
							}
						}
						],

			});
	//customMobSowingBackButton(warehouse,mobileUser,branch);

	customBackpopulate_stateInLocation(countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch)

}

function customBackpopulate_stateInLocation(countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch) {
	 var chart = $('#farmerByLocationChart_bar').esecharts();
	    normalState = new Object();
	    normalState.stroke_width = null;
	    normalState.stroke = null;
	    normalState.fill = null;
	    normalState.padding = null;
	    normalState.r = null;

	    hoverState = new Object();
	    hoverState = normalState;

	    pressedState = new Object();
	    pressedState = normalState;

	    var custombutton = chart.renderer.button($('#back').val(), 500, 20, function(){
	    	populate_stateInLocation(countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
	    },null,hoverState,pressedState).add();
}
function populate_municipalityInLocation(localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch){
	var tenantId = getCurrentTenantId();
	var branchForPieChart;
	var iteration1 = 1;
	var iteration2 = 1;
	var dataLength = 0;
	var rightCount = 0;
	var rightClick = 0;
	codeForCropChart=localityid;
	if(tenantId=='galaxyrice'){
		var locationTitleArray='Farmer By Tehsil';
	}else{
		var locationTitleArray=$('#farmerByTaluk').val()
	}
	var unit = "Hectare";
		
		var titleForFarmAreaChart = locationTitleArray;
		titleForFarmAreaChart =$('#farmAreaByTaluk').val()
	//alert(tenantId);

	esecharts
	.chart(
			'farmerByLocationChart_bar',
			{
				chart : {
					type : 'column',
					// backgroundColor:'rgba(255, 255, 255, 0.0)',
					  marginLeft: 100,
				        marginRight: 100,
					/*options3d : {
						enabled : false,
						alpha : 10,
						beta : 20,
						depth : 70
					}*/
				},

				 title: {
				    	
				    	
				        text:locationTitleArray
				        	
				    },
				   
				    xAxis: {
				        type: 'category',
				       
				    },
				    yAxis: {
				        title: {
				            text: $('#farmrsCount').val()
				        }

				    },
				    legend: {
				        enabled: false
				    },
				    plotOptions: {
				    	 
					        series: {
					           allowPointSelect: false,
					            dataLabels: {
					                enabled: true,
					                format: '{point.y}'
					            }
					        }
				    },

				    tooltip: {
				        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
				       	pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> <br/>'
				    },

				series : [
						{
							name : 'Location',
							colorByPoint: true,
							data : (function() {
								var res = [];
								var len = 0;
							/*	$.each(municipality, function(index, municipality) {
					    			res.push({	
					            		name: municipality.municipalityName,
							            id: municipality.municipalityCode,
							            data : (function() {
													var res2 = [];*/
								$.each(municipality, function(index, municipality) {
									if(localityid == municipality.localityCode){
										res.push({
											name :municipality.municipalityName,
											y : municipality.count,
											drilldown:municipality.municipalityCode,
											id : municipality.municipalityCode
										});
									}
								});
								farmerByLocationChart_pie(localityid,branchId,unit,titleForFarmAreaChart);		
											/*	return res2;
										})()
									});*/
					    	//	});
								return res;
							})(),

							point : {
								events : {
									click : function(event) {
										
										    populate_villageInLocation(this.id,localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
										
									}
								}
							}
						}
						],

			});
	//customMobSowingBackButton(warehouse,mobileUser,branch);
	customBackpopulate_localityInLocation(stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch)
}

function customBackpopulate_localityInLocation(stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch) {
	 var chart = $('#farmerByLocationChart_bar').esecharts();
	    normalState = new Object();
	    normalState.stroke_width = null;
	    normalState.stroke = null;
	    normalState.fill = null;
	    normalState.padding = null;
	    normalState.r = null;

	    hoverState = new Object();
	    hoverState = normalState;

	    pressedState = new Object();
	    pressedState = normalState;

	    var custombutton = chart.renderer.button($('#back').val(), 500, 20, function(){
	    	populate_localityInLocation(stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
	    },null,hoverState,pressedState).add();
}

function populate_villageInLocation(municipalityId,localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch){

	var tenantId = getCurrentTenantId();
	var branchForPieChart;
	var iteration1 = 1;
	var iteration2 = 1;
	var dataLength = 0;
	var rightCount = 0;
	var rightClick = 0;
	codeForCropChart=municipalityId;
	var locationTitleArray=$('#farmerByVillage').val()
	var unit = "Hectare";
		
		var titleForFarmAreaChart = locationTitleArray;
		titleForFarmAreaChart =$('#farmAreaByVillage').val()
	//alert(tenantId);

	esecharts
	.chart(
			'farmerByLocationChart_bar',
			{
				chart : {
					type : 'column',
					// backgroundColor:'rgba(255, 255, 255, 0.0)',
					  marginLeft: 100,
				        marginRight: 100,
					/*options3d : {
						enabled : false,
						alpha : 10,
						beta : 20,
						depth : 70
					}*/
				},

				 title: {
				    	
				    	
				        text:locationTitleArray
				        	
				    },
				   
				    xAxis: {
				        type: 'category',
				       
				    },
				    yAxis: {
				        title: {
				            text: $('#farmrsCount').val()
				        }

				    },
				    legend: {
				        enabled: false
				    },
				    plotOptions: {
				    	 
					        series: {
					           allowPointSelect: false,
					            dataLabels: {
					                enabled: true,
					                format: '{point.y}'
					            }
					        }
				    },

				    tooltip: {
				        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
				       	pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> <br/>'
				    },

				series : [
						{
							name : 'Location',
							colorByPoint: true,
							data : (function() {
								var res = [];
							/*	$.each(municipality, function(index, municipality) {
					    			res.push({	
					            		name: municipality.municipalityName,
							            id: municipality.municipalityCode,
							            data : (function() {
													var res2 = [];*/
								
								$.each(village, function(index, village) {
									if(municipalityId == village.municipalityCode){
										if(iteration1 <= 5){
										res.push({
											name :village.villageName,
											y : village.count,
										//	drilldown:village.villageCode,
											id : village.villageCode
										});
										iteration1 = iteration1+1;
										dataLength = dataLength+1;
										}
										else{
											dataLength = dataLength+1;
										}
									}
								});
								farmerByLocationChart_pie(municipalityId,branchId,unit,titleForFarmAreaChart);				
											/*	return res2;
										})()
									});*/
					    	//	});
								return res;
							})(),

							point : {
								events : {
									click : function(event) {
										    populate_farmerDetailsInLocation(this.id,municipalityId,localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
										
									}
								}
							},
							 showInLegend: true
						}
						],

			}, function(chart) { 
				 var flag = "hideArrows";
		
				 if(iteration1 > 5){
					  flag = "showArrows";
				  } 
			
				  if(flag == "showArrows"){
				    chart.renderer.button('<', chart.plotLeft - 60, chart.plotHeight + chart.plotTop).addClass('left_farmerByLocationChart_bar').add();
				    chart.renderer.button('>', chart.plotLeft + chart.plotWidth + 30, chart.plotHeight + chart.plotTop).addClass('right_farmerByLocationChart_bar').add();
				    chart.xAxis[0].setExtremes(0,4);
				  
				    var drilldown = dataLength/5;
				    
				 
				    $('.left_farmerByLocationChart_bar').click(function() {
				    	if(rightClick > 0){
				    		if(rightClick == 1){
				    			rightCount = rightCount-6;
				    		}else{
				    			rightCount = rightCount-5;
				    		}
				    	
					    	temp1 = rightCount;
						
						 var yValues =  new Array();
						 
						 iteration2 = 1;
						 $.each(village, function(index, village) {
							 if(municipalityId == village.municipalityCode){
							 if(iteration2 >= temp1 ){
									yValues.push({
										name :village.villageName,
										y : village.count,
										drilldown:village.villageCode,
										id : village.villageCode
									});
								}
								iteration2 = iteration2+1;
							 }
							});
					
						 iteration2 = 1;
						 
						chart.series[0].setData(eval(yValues), false, true);
						
						chart.xAxis[0].setExtremes(0, 3); 
						 rightClick = rightClick - 1;
						 iteration2 = 1;
				    }
				    });
				    
				    $('.right_farmerByLocationChart_bar').click(function() {
				 
						   if(rightClick < drilldown-1){
							   if(rightCount == 0){
							    	rightCount = rightCount+6;
							    	rightCount = Number(rightCount);
							    }else{
							    	rightCount = rightCount+5;
							    }
						   
						    	 temp1 = rightCount;
						    	 if(dataLength >=  Number(rightCount+4)){
						    		 temp2 = Number(rightCount+4);
						    		 //temp2 = temp2+10;
						    	 }else{
						    		var temp2  = Number(dataLength-(rightCount-1));
						    	 }
						    	
						    	 if(temp2 < temp1){
									temp2 = dataLength ;
						    	 }
								
						    	 var yValues =  new Array();
							
						    	 iteration2 = 1;
						    	 $.each(village, function(index, village) {
						    		 if(municipalityId == village.municipalityCode){
							    		 if(iteration2 >= temp1 && iteration2 <= (temp2)){
												yValues.push({
													name :village.villageName,
													y : village.count,
												//	drilldown:village.villageCode,
													id : village.villageCode
												});
											}
							    		 iteration2 = iteration2+1;
						    		 }
										
									});
						    	 
						    	  iteration2 = 1;
								
									
								 chart.series[0].setData(eval(yValues), false, true);
								
								 if(yValues.length < 5){
									 chart.xAxis[0].setExtremes(0, yValues.length-1); 
								 }else{
									 chart.xAxis[0].setExtremes(0, 3); 
								 }
								
								 rightClick = rightClick+1;
							} 
					})
			  }
				}
			);
	customBackpopulate_municipalityInLocation(localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch)

}

function customBackpopulate_municipalityInLocation(localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch) {
	 var chart = $('#farmerByLocationChart_bar').esecharts();
	    normalState = new Object();
	    normalState.stroke_width = null;
	    normalState.stroke = null;
	    normalState.fill = null;
	    normalState.padding = null;
	    normalState.r = null;

	    hoverState = new Object();
	    hoverState = normalState;

	    pressedState = new Object();
	    pressedState = normalState;

	    var custombutton = chart.renderer.button($('#back').val(), 500, 20, function(){
	    	populate_municipalityInLocation(localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
	    },null,hoverState,pressedState).add();
}

function populate_farmerDetailsInLocation(villageCode,municipalityId,localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch){
	var tenantId = getCurrentTenantId();
	var branchForPieChart;
	var iteration1 = 1;
	var iteration2 = 1;
	var dataLength = 0;
	var rightCount = 0;
	var rightClick = 0;
	codeForCropChart=villageCode;
	var locationTitleArray=$('#farmerDetails').val()
	var unit = "Hectare";
		
		var titleForFarmAreaChart = locationTitleArray;
		titleForFarmAreaChart =  titleForFarmAreaChart.replace("Farmer By", "Farm Area By ");
	//alert(tenantId);

	esecharts
	.chart(
			'farmerByLocationChart_bar',
			{
				chart : {
					type : 'column',
					// backgroundColor:'rgba(255, 255, 255, 0.0)',
					  marginLeft: 100,
				        marginRight: 100,
					options3d : {
						enabled : false,
						alpha : 10,
						beta : 20,
						depth : 70
					}
				},

				 title: {
				    	
				    	
				        text:locationTitleArray
				        	
				    },
				   
				    xAxis: {
				        type: 'category',
				       
				    },
				    yAxis: {
				        title: {
				            text: $('#farmrsCount').val()
				        }

				    },
				    legend: {
				        enabled: false
				    },
				    plotOptions: {
				    	 
					        series: {
					           allowPointSelect: false,
					            dataLabels: {
					                enabled: true,
					                format: '{point.y}'
					            }
					        }
				    },

				    tooltip: {
				        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
				       	pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> <br/>'
				    },

				series : [
{
	name : 'Location',
	colorByPoint: true,
	data : (function() {
				      	var res2 = [];
						$.each(farmerDetails, function(index, farmerDetails) {
							if(villageCode == farmerDetails.villageCode){
								res2.push({
									name :"Total Count",
									y : Number(farmerDetails.totalCount),
								});
								
								/*res2.push({
									name :"Active",
									y : Number(farmerDetails.active),
								});
								
								res2.push({
									name :"InActive",
									y : Number(farmerDetails.inActive),
								});*/
								
								res2.push({
									name :"Certified",
									y : Number(farmerDetails.certified),
								});
								
								res2.push({
									name :"NonCertified",
									y : Number(farmerDetails.nonCertified),
								});
							}
						});
						
					return res2;
	})(),

	
}
						],

			});
	customBackpopulate_villageInLocation(municipalityId,localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);

}
function customBackpopulate_villageInLocation(municipalityId,localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch) {
	 var chart = $('#farmerByLocationChart_bar').esecharts();
	    normalState = new Object();
	    normalState.stroke_width = null;
	    normalState.stroke = null;
	    normalState.fill = null;
	    normalState.padding = null;
	    normalState.r = null;

	    hoverState = new Object();
	    hoverState = normalState;

	    pressedState = new Object();
	    pressedState = normalState;
	    var custombutton = chart.renderer.button($('#back').val(), 500, 20, function(){
	    	populate_villageInLocation(municipalityId,localityid,stateid,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch)
	    },null,hoverState,pressedState).add();
}



function farmerByLocationChart_pie(locationCode,branch,unit,titleForFarmAreaChart){
	//alert(branch);
	var iteration1 = 1;
	var iteration2 = 1;
	var dataLength = 0;

	var rightCount = 0;
	var rightClick = 0;
	jQuery.post("dashboard_getFarmDetailsAndProposedPlantingArea.action",{locationLevel:locationCode,selectedBranch:branch},function(farmDetailsAndAcre) {
		farmDetailsAndAcre_json = $.parseJSON(farmDetailsAndAcre);
		
	
	
	var chart = new esecharts.chart('farmerByLocationChart_pie', {
	    chart: {
	        type: 'bar',
	        marginLeft: 100,
	        marginRight: 100,
	        
	    },
	    title: {
	        text: titleForFarmAreaChart,
	    },
	    tooltip: {
	        pointFormat: '{series.name}: <b>{point.y} </b> '+areaType
	    },
	    
	    xAxis: {
	        type: 'category',
	       /* title: {
	            text: '<b>Branch</b>'
	        }*/
	    },
	    yAxis: {
	        title: {
	            text: '<b>'+areaType+'</b>'
	        }

	    },
	    legend: {
	        enabled: false
	    },
	    plotOptions: {
	    	 
	        series: {
	           allowPointSelect: false,
	            dataLabels: {
	                enabled: true,
	                format: '{point.y} '+areaType
	            }
	        }
    },
	    series: [{
	        name: 'Area',
	        colorByPoint: true,
	        data : (function() {
						var res = [];
							
						$.each(farmDetailsAndAcre_json, function(index, value) {
							 
							if(iteration1 <= 5){
								
								
								res.push({
									name :value.locationName,
									y : Number(value.Area),
									
								 });
									iteration1 = iteration1+1;
									dataLength = dataLength+1;
							}else{
								dataLength = dataLength+1;
							}
						
						});
						
						
						
						
						return res;
					})()
				 }]
	 }, function(chart) { 
		 var flag = "hideArrows";
		 
		  if(farmDetailsAndAcre_json.length > 5){
			  flag = "showArrows";
		  }
		  if(flag == "showArrows"){
		    //chart.renderer.button('<', chart.plotLeft - 60, chart.plotHeight + chart.plotTop).addClass('left_farmerByLocationChart_pie').add();
		   // chart.renderer.button('>', chart.plotLeft + chart.plotWidth + 30, chart.plotHeight + chart.plotTop).addClass('right_farmerByLocationChart_pie').add();
		    chart.xAxis[0].setExtremes(0,4);
		  
		    var drilldown = dataLength/5;
		    
		 
		    if($("#arrow_farmerByLocationChart_pie").length > 0){
		    	   $("#arrow_farmerByLocationChart_pie").remove();
		    	}

		    	
		    	var row = jQuery('<div/>', {
		    		class : "col-xs-12",
		    		id	  : "arrow_farmerByLocationChart_pie"
		    	});
		    					    
		    	$("#farmerByLocationChart_pie").append($(row));
		    	$(row).append('<div  class="col-xs-6 leftArrow"><button  type="button" class="btn   left_farmerByLocationChart_pie "><span class="glyphicon glyphicon-chevron-left"></span></button></div>')   
		    	$(row).append('<div  class="col-xs-6 rightArrow"><button  type="button" class="btn    right_farmerByLocationChart_pie"><span class="glyphicon glyphicon-chevron-right"></span> </button></div>')
		    	 
		    
		    $('.left_farmerByLocationChart_pie').click(function() {
		    	if(rightClick > 0){
		    		if(rightClick == 1){
		    			rightCount = rightCount-6;
		    		}else{
		    			rightCount = rightCount-5;
		    		}
		    	
			    	temp1 = rightCount;
		    	
				
				
				 var yValues1 =  new Array();
				
				 iteration2 = 1;
				 $.each(farmDetailsAndAcre_json, function(index, value) {
					 if(iteration2 >= temp1 ){
						if(yValues1.length <= 4){
							yValues1.push({
								name :value.locationName,
								y : Number(value.Area),
								//url :'procurementProductEnroll_list.action?q='+value.locationName
							//	id : value.locationCode
							 });
						}
					}
						iteration2 = iteration2+1;
		    	});
				 
				 iteration2 = 1;
				
				chart.series[0].setData(eval(yValues1), false, true);
				
				chart.xAxis[0].setExtremes(0, 3); 
				 rightClick = rightClick - 1;
				 iteration2 = 1;
		    }
		    });
		    
		    $('.right_farmerByLocationChart_pie').click(function() {
		    	
		    	
		    	if(rightClick < drilldown-1){
				    if(rightCount == 0){
				    	rightCount = rightCount+6;
				    	rightCount = Number(rightCount);
				    }else{
				    	rightCount = rightCount+5;
				    }
				   
				    	 temp1 = rightCount;
				    	 if(dataLength >=  Number(rightCount+4)){
				    		 temp2 = Number(rightCount+4);
				    		 //temp2 = temp2+10;
				    	 }else{
				    		var temp2  = Number(dataLength-(rightCount-1));
				    	 }
				    	
				    	 if(temp2 < temp1){
							temp2 = dataLength ;
				    	 }
						
				    	 var yValues1 =  new Array();
				    	
				    	 iteration2 = 1;
				    	
				    	 $.each(farmDetailsAndAcre_json, function(index, value) {
				    		 if(iteration2 >= temp1 && iteration2 <= (temp2)){
				    			 yValues1.push({
										name :value.locationName,
										y : Number(value.Area),
										//url :'procurementProductEnroll_list.action?q='+value.locationName
									//	id : value.locationCode
									 });
								}
							 iteration2 = iteration2+1;	
				    	});
				    	  iteration2 = 1;
				    	 
				    	 chart.series[0].setData(eval(yValues1), false, true);
						 
						 if(yValues1.length < 5){
							 chart.xAxis[0].setExtremes(0, yValues1.length-1); 
						 }else{
							 chart.xAxis[0].setExtremes(0, 3); 
						 }
						 rightClick = rightClick+1;
					} 
		    
		    	
			})
	  }
		});
 });
	
	var titleForFarmerLocationCropChart ;
	
	
	titleForFarmerLocationCropChart =$('#farmCropAreaBy').val()
	titleForFarmerLocationCropChart =$('#farmCropArea').val()
	populateFarmerLocationCropChart(titleForFarmerLocationCropChart);
}


function populateFarmerLocationCropChart(titleForFarmerLocationCropChart){
	var dataForChart = new Array();
	var iteration1 = 1;
	var iteration2 = 1;
	var dataLength = 0;

	var rightCount = 0;
	var rightClick = 0;
	
	if(codeForCropChart == "first_level_Branch_Login"){
		codeForCropChart = "";
	}
	
	jQuery.post("dashboard_populateFarmerLocationCropChart.action",{codeForCropChart:codeForCropChart},function(farmerByLocation_crop) {
		
	var	farmerByLocation_crop_Data = $.parseJSON(farmerByLocation_crop);
	$.each(farmerByLocation_crop_Data, function(index, value) {
			if(iteration1 <= 3){
				dataForChart.push({	
					"name" : value.productName,
					"y" : Number(Number(value.Area).toFixed(3)),
					
					
				});
				iteration1 = iteration1+1;
				dataLength = dataLength+1;
			}else{
				dataLength = dataLength+1;
			}
	});
	
		esecharts.chart('farmerByLocation_crop_3d', {
			    chart: {
			        type: 'pie',
			        marginLeft: 100,
			        marginRight: 100,
			    },
			    title: {
			        text: titleForFarmerLocationCropChart
			    },
			  
			    xAxis: {
			        type: 'category',
			        title: {
				        text: 'Products'
				    },
			        labels: {
			            rotation: -45,
			            style: {
			                fontSize: '13px',
			                fontFamily: 'Verdana, sans-serif'
			            }
			        }
			    },
			    yAxis: {
			        min: 0,
			        title: {
			            text: 'Acre'
			        }
			    },
			    legend: {
			        enabled: true
			    },
			    tooltip: {
			        pointFormat: '<b>{point.y} '+areaType+'</b>'
			    },
			   
			    plotOptions: {
			        pie: {
			        	innerSize: 70,
			            depth: 45
			        },
			        series: {
			            borderWidth: 0,
			            dataLabels: {
			                enabled: true,
			                format: '{point.name} : <span style="color: {point.color};">{point.y}</span> '+areaType
			            	
			            }
			        }
			    },
			    
			    series: [{
			       
			        data:dataForChart,
			        showInLegend: true
			    }],
			   
			 }, function(chart) { 
				 var flag = "hideArrows";
					
				  if(farmerByLocation_crop_Data.length > 3){
					  flag = "showArrows";
				  } 
			  
			  if(flag == "showArrows"){
				
			    chart.xAxis[0].setExtremes(0,9);
			  
			    var drilldown = dataLength/3;
			    
			   
			   if($("#arrow_farmerByLocation_crop_3d").length > 0){
				   $("#arrow_farmerByLocation_crop_3d").remove();
			   }
			   
			   var row = jQuery('<div/>', {
					class : "col-xs-12",
					id	  : "arrow_farmerByLocation_crop_3d"
				});
			    
			    $("#farmerByLocation_crop_3d").append($(row));
			    $(row).append('<div  class="col-xs-6 leftArrow"><button  type="button" class="btn   left_farmerByLocation_crop_3d "><span class="glyphicon glyphicon-chevron-left"></span></button></div>');   
			    $(row).append('<div  class="col-xs-6 rightArrow"><button  type="button" class="btn    right_farmerByLocation_crop_3d"><span class="glyphicon glyphicon-chevron-right"></span> </button></div>');
			    
			   
			    $('.left_farmerByLocation_crop_3d').click(function() {

			    	if(rightClick > 0){
			    		if(rightClick == 1){
			    			rightCount = rightCount-3;
			    		}else{
			    			rightCount = rightCount-3;
			    		}
			    	
				    	temp1 = rightCount;
			    	
					
					
					 var yValues1 =  new Array();
					
					 iteration2 = 1;
					 $.each(farmerByLocation_crop_Data, function(index, value) {
						 if(iteration2 >= temp1 ){
							if(yValues1.length <= 2){
								yValues1.push({
									"name" : value.productName,
									"y" : Number(Number(value.Area).toFixed(3)),
								 });
							}
						}
							iteration2 = iteration2+1;
			    	});
					 
					 iteration2 = 1;
					
					chart.series[0].setData(eval(yValues1), false, true);
					
					chart.xAxis[0].setExtremes(0, 3); 
					 rightClick = rightClick - 1;
					 iteration2 = 1;
					 
					 
					 
					 if(rightClick != 0){
						 $('.farmerByLocation_crop_3d_backward').text('< Back  '+Number(rightClick))
					}else{
						 $(".left_farmerByLocation_crop_3d").hide();
					 }
					 $('.farmerByLocation_crop_3d_forward').text('Next  '+Number(rightClick+2)+' >');
					 
					 $('.right_farmerByLocation_crop_3d').show();
			    }
			    	
			    });
			    
			    $('.right_farmerByLocation_crop_3d').click(function() {
			    	
			    	if(rightClick < drilldown-1){
					   if(rightCount == 0){
					    	rightCount = rightCount+4;
					    	rightCount = Number(rightCount);
					    }else{
					    	rightCount = rightCount+3;
					    }
					 
					    	 temp1 = rightCount;
					    	 if(dataLength >=  Number(rightCount+2)){
					    		 temp2 = Number(rightCount+2);
					    		 //temp2 = temp2+10;
					    	 }else{
					    		var temp2  = Number(dataLength-(rightCount-1));
					    	 }	
					    	
					    	 if(temp2 < temp1){
								temp2 = dataLength ;
					    	 }
							
					    	 var yValues1 =  new Array();
					    	
					    	 iteration2 = 1;
					    	
					    	 $.each(farmerByLocation_crop_Data, function(index, value) {
					    		 if(iteration2 >= temp1 && iteration2 <= (temp2)){
					    			 yValues1.push({
					    				 "name" : value.productName,
					 					"y" : Number(Number(value.Area).toFixed(3)),
										 });
									}
								 iteration2 = iteration2+1;	
					    	});
					    	  iteration2 = 1;
					    	
					    	 chart.series[0].setData(eval(yValues1), false, true);
							 
							 if(yValues1.length < 5){
								 chart.xAxis[0].setExtremes(0, yValues1.length-1); 
							 }else{
								 chart.xAxis[0].setExtremes(0, 3); 
							 }
							 rightClick = rightClick+1;
						} 
			    	
			    	
			    	
			    	if(rightClick < drilldown-1){
			    		$('.farmerByLocation_crop_3d_forward').text('Next  '+Number(rightClick+2)+' >');
			    	}else{
			    		$('.right_farmerByLocation_crop_3d').hide();
			    	}
			    	
			    	 $(".left_farmerByLocation_crop_3d").show();
			    	$('.farmerByLocation_crop_3d_backward').text('< Back  '+Number(rightClick))
			    	//chart.renderer.button('Level '+Number(rightClick+1)+' >');
			    //	chart.renderer.button('< Back '+rightClick, chart.plotLeft - 60, chart.plotHeight + chart.plotTop).addClass('left_warehouseToFarmer_FarmerChart').add();
			    	
			    })
		}
			});
	}); 
	
	estimatedAndActualYield();
	
	
}


function estimatedAndActualYield(){
	
	var iteration1 = 1;
	var iteration2 = 1;
	var dataLength = 0;

	var rightCount = 0;
	var rightClick = 0;
	
	var tenant = getCurrentBranch();
	if(tenant=='galaxyrice'){
		var unit ='Maunds';
	}else{
		var unit ='Kgs';
	}
	if(isEmpty(codeForCropChart)){
		codeForCropChart = getCurrentBranch();
	}
	jQuery.post("dashboard_estimatedAndActualYield.action",{codeForCropChart:codeForCropChart},function(result) {
		var json = $.parseJSON(result);
		
		esecharts.chart('farmerByLocation_estimated', {
		    chart: {
		        type: 'bar',
		        marginLeft: 100,
		        marginRight: 100
		    },
		    title: {
		        text: $('#estvsactyeld').val()
		    },
		    /*subtitle: {
		        text: 'Source: WorldClimate.com'
		    },*/
		    xAxis: {
		        type: 'category',
		        title: {
		            text: $('#crops').val()
		        }
		    },
		    tooltip: {
		        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		            '<td style="color:{series.color};padding:0"><b>{point.y} '+unit+'</b></td></tr>',
		        footerFormat: '</table>',
		        shared: true,
		        useHTML: true
		    },
		    plotOptions: {
		    	 
		        series: {
		           allowPointSelect: false,
		            dataLabels: {
		                enabled: true,
		                format: '{point.y} '+unit,
		                
		            }
		        }
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'top',
	        x: -30,
	        y: 80,
	        floating: true,
	        borderWidth: 1,
	        backgroundColor: ((esecharts.theme && esecharts.theme.legendBackgroundColor) || '#FFFFFF'),
	        shadow: true
	    },
		    series: [{
		        name: $('#estimated').val(),
		        //colorByPoint: true,
		        data : (function() {
							var res = [];
							
							$.each(json, function(index, value) {
								
								if(iteration1 <= 5){
									res.push({
										name :value.productName,
										y : Number(value.estimated),
										
									 });
										iteration1 = iteration1+1;
										//dataLength = dataLength+1;
								}else{
									//dataLength = dataLength+1;
								}
								
							});
							iteration1 = 1;
							return res;
						})()
					 },
					 {
					        name: $('#actual').val(),
					      //  colorByPoint: true,
					        data : (function() {
										var res = [];
												
										$.each(json, function(index, value) {
											if(iteration1 <= 5){
												res.push({
													name :value.productName,
													y : Number(value.actual),
												 });
													iteration1 = iteration1+1;
													dataLength = dataLength+1;
											}else{
												dataLength = dataLength+1;
											}
										});
										return res;
									})()
								 }]
		 }, function(chart) { 
			 var flag = "hideArrows";
			 
			  if(json.length > 5){
				  flag = "showArrows";
			  }
			  if(flag == "showArrows"){
			   
			    chart.xAxis[0].setExtremes(0,4);
			  
			    var drilldown = dataLength/5;
			    
			    if($("#arrow_farmerByLocation_estimated").length > 0){
			    	   $("#arrow_farmerByLocation_estimated").remove();
			    	}


			    	var row = jQuery('<div/>', {
			    		class : "col-xs-12",
			    		id	  : "arrow_farmerByLocation_estimated"
			    	});
			    					    
			    	$("#farmerByLocation_estimated").append($(row));
			    	$(row).append('<div  class="col-xs-6 leftArrow"><button  type="button" class="btn   left_farmerByLocation_estimated "><span class="glyphicon glyphicon-chevron-left"></span></button></div>')   
			    	$(row).append('<div  class="col-xs-6 rightArrow"><button  type="button" class="btn    right_farmerByLocation_estimated"><span class="glyphicon glyphicon-chevron-right"></span> </button></div>')

			    
			 
			    $('.left_farmerByLocation_estimated').click(function() {
			    	if(rightClick > 0){
			    		if(rightClick == 1){
			    			rightCount = rightCount-6;
			    		}else{
			    			rightCount = rightCount-5;
			    		}
			    	
				    	temp1 = rightCount;
			    	
					
					
					 var yValues1 =  new Array();
					 var yValues2 =  new Array();
					 iteration2 = 1;
					 $.each(json, function(index, value) {
						 if(iteration2 >= temp1 ){
							if(yValues1.length <= 4){
								yValues1.push({
									name :value.productName,
									y : Number(value.estimated),
								 });
							}
								
						
							 
								}
							iteration2 = iteration2+1;
			    		});
					 
					 iteration2 = 1;
					 
					 $.each(json, function(index, value) {
						 if(iteration2 >= temp1 ){
								if(yValues2.length <= 4){
							 yValues2.push({
									name :value.productName,
									y : Number(value.actual),
								 });
								}
								}
							iteration2 = iteration2+1;
			    		});
					 iteration2 = 1;
					
					chart.series[0].setData(eval(yValues1), false, true);
					chart.series[1].setData(eval(yValues2), false, true);
					chart.xAxis[0].setExtremes(0, 3); 
					 rightClick = rightClick - 1;
					 iteration2 = 1;
			    }
			    });
			    
			    $('.right_farmerByLocation_estimated').click(function() {
			    	
			    	
			    	if(rightClick < drilldown-1){
					    if(rightCount == 0){
					    	rightCount = rightCount+6;
					    	rightCount = Number(rightCount);
					    }else{
					    	rightCount = rightCount+5;
					    }
					   
					    	 temp1 = rightCount;
					    	 if(dataLength >=  Number(rightCount+4)){
					    		 temp2 = Number(rightCount+4);
					    		 //temp2 = temp2+10;
					    	 }else{
					    		var temp2  = Number(dataLength-(rightCount-1));
					    	 }
					    	
					    	 if(temp2 < temp1){
								temp2 = dataLength ;
					    	 }
							
					    	 var yValues1 =  new Array();
					    	 var yValues2 =  new Array();
					    	 iteration2 = 1;
					    	 $.each(json, function(index, value) {
								
					    		 if(iteration2 >= temp1 && iteration2 <= (temp2)){
					    			 yValues1.push({
											name :value.productName,
											y : Number(value.estimated),
										 });
									}
								 iteration2 = iteration2+1;
					    		 });
					    	 
					    	 iteration2 = 1;
					    	 
					    	 $.each(json, function(index, value) {
					    	 if(iteration2 >= temp1 && iteration2 <= (temp2)){
				    			 yValues2.push({
										name :value.productName,
										y : Number(value.actual),
									 });
								}
				    		 iteration2 = iteration2+1;
							});
					    	 iteration2 = 1;
							
					    	 chart.series[0].setData(eval(yValues1), false, true);
							 chart.series[1].setData(eval(yValues2), false, true);
							 if(yValues1.length < 5){
								 chart.xAxis[0].setExtremes(0, yValues1.length-1); 
							 }else{
								 chart.xAxis[0].setExtremes(0, 3); 
							 }
							 rightClick = rightClick+1;
						} 
			    
			    	
				})
		  }
			});
		
		
		
		
		
		
		
	});
}


function getRandomColor() {
	var letters = '0123456789ABCDEF';
	var color = '#';
	for (var i = 0; i < 6; i++) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}


