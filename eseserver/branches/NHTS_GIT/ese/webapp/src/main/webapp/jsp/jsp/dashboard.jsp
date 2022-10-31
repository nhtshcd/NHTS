<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ include file="/jsp/common/form-assets.jsp"%>

<html>
<head>
<!-- add this meta information to select layout  -->

<META name="decorator" content="swithlayout">
</head>

<body>
	<link rel="stylesheet" href="css/echarts-dashboard.css" />
	<link rel="stylesheet"
		href="plugins/bootstrap-daterangepicker/daterangepicker-bs3.css">
	<script src="plugins/bootstrap-daterangepicker/moment.min.js"></script>
	<script src="plugins/bootstrap-daterangepicker/daterangepicker.js"></script>

	<link
		href="https://fonts.googleapis.com/css?family=Aclonica|Averia+Serif+Libre|Carter+One|Changa+One|Goblin+One|Merienda+One|Merriweather|Merriweather+Sans|Mogra|Paprika|Roboto|Roboto+Slab|Salsa|Sansita|Spicy+Rice|Viga"
		rel="stylesheet" />


<s:hidden id="currencyType" value="%{getCurrencyType()}" name="currencyType"/>

		<div class="appContentWrapper marginBottom chartsTheme"
			id="charts-div">

			<div class="row">
				<div class="col-md-12 marginBottom">

					<div class="pull-right" style="">
						<div id="getTheme"
							style="cursor: pointer; width: 15px; height: 15px; border-radius: 50%; background-color: #2E2E2F; margin-bottom: 10px;">&nbsp;</div>

					</div>

					<div class="pull-right" style="">
						<div id="getGridLight"
							style="cursor: pointer; width: 15px; height: 15px; border-radius: 50%; background-color: #EDEDED; margin-bottom: 10px;">&nbsp;</div>

					</div>
					<div class="pull-right"><s:property value="%{getLocaleProperty('ChangeTheme')}"/>:</div>
					<div class="summaryBlocksWrapper flex-container">
							<div class="summaryBlockItem col col1">
								<span><span class="strong" id="userCount">0</span> <s:property
										value="%{getLocaleProperty('txt.users')}" /></span> <a
									href="user_list.action" class="button"> <i
									class="fa fa-arrow-circle-right"></i>
								</a>
							</div>

							<div class="summaryBlockItem col col2">
								<span><span class="strong" id="mobileUserCount">0</span>
									<s:property value="%{getLocaleProperty('txt.mobileUsers')}" /></span>
								<a href="fieldStaff_list.action?type=fieldStaff" class="button">
									<i class="fa fa-arrow-circle-right"></i>
								</a>
							</div>

							<div class="summaryBlockItem col col3">
								<span><span class="strong" id="deviceCount">0</span> <s:property
										value="%{getLocaleProperty('txt.devices')}" /></span> <a
									href="device_list.action" class="button"> <i
									class="fa fa-arrow-circle-right"></i>
								</a>
							</div>
						
						
		
								<div class="summaryBlockItem col col5">
									<span><span class="strong" id="groupCount">0</span> <s:property
											value="%{getLocaleProperty('txt.group')}" /></span> <a
										href="samithi_list.action" class="button"> <i
										class="fa fa-arrow-circle-right"></i></a>
								</div>
				
						<div class="summaryBlockItem col col6">
							<span><span class="strong" id="farmerCount">0</span> <s:property
									value="%{getLocaleProperty('txt.farmers')}" /></span> <a
								href="farmer_list.action" class="button"> <i
								class="fa fa-arrow-circle-right"></i></a>
						</div>

												<div class="summaryBlockItem col col7">
								<span><span class="strong" id="farmLandCount">0</span> <s:property
										value="%{getLocaleProperty('farmingTotalLand')}" /></span> <a
									href="farmer_list.action" class="button"> <i
									class="fa fa-arrow-circle-right"></i></a>
							</div>
				
								<div class="summaryBlockItem col col8">
									<span><span class="strong" id="farmCount">0</span> <s:property
											value="%{getLocaleProperty('totalFarms')}" /></span> <a
										href="farmer_list.action" class="button"> <i
										class="fa fa-arrow-circle-right"></i></a>
								</div>

						
								<div class="summaryBlockItem col col11">
									<span><span class="strong" id="warehouseCount">0</span>
										<s:text name="%{getLocaleProperty('txt.warehouses')}" /> </span> <a
										href="cooperative_list.action?type=cooperative" class="button">
										<i class="fa fa-arrow-circle-right"></i>
									</a>
								</div>
							



					</div>
				</div>
				<div class="clear"></div>
			</div>


		</div>

		<!--  -->
		<div>
			<div class="row">
				<div class="col-md-12 marginBottom">
					<!-- 	<div class="row"> -->
					<!-- <div class="panel-group"> -->
					<span>
					<div class="pull-right">
					<s:property value="%{getLocaleProperty('K Indicates Thousand & M Indicates Million')}"/>
					</div></span>
				</div>
			</div>

			
			<div class="appContentWrapper marginBottom hideTxn">

				<div class="panel panel-default ">
					<div class="panel-heading">
						<span class="makeBold"><s:property
								value='getLocaleProperty("donutChart.farmerLocation")' /></span>
						<div class="clear"></div>
					</div>
					<div class="panel-body">
						<div class="row">
							<s:hidden id="areaType" value="%{getAreaType()}" name="areaType" />
							
							<s:hidden id="farmerbyCountry" value='%{getLocaleProperty("farmerbyCountry")}'  />
							<s:hidden id="farmAreaByCountry" value='%{getLocaleProperty("farmAreaByCountry")}'  />
							<s:hidden id="farmCropAreaByCountry" value='%{getLocaleProperty("farmCropAreaByCountry")}'  />
							
							<s:hidden id="farmerbyState" value='%{getLocaleProperty("farmerbyState")}'  />
							<s:hidden id="farmAreaByState" value='%{getLocaleProperty("farmAreaByState")}'  />
							
							<s:hidden id="farmerByDistrict" value='%{getLocaleProperty("farmerByDistrict")}'  />
							<s:hidden id="farmAreaByDistrict" value='%{getLocaleProperty("farmAreaByDistrict")}'  />
							
							<s:hidden id="farmerByTaluk" value='%{getLocaleProperty("farmerByTaluk")}'  />
							<s:hidden id="farmAreaByTaluk" value='%{getLocaleProperty("farmAreaByTaluk")}'  />
							
							<s:hidden id="farmerByVillage" value='%{getLocaleProperty("farmerByVillage")}'  />
							<s:hidden id="farmAreaByVillage" value='%{getLocaleProperty("farmAreaByVillage")}'  />
							
							<s:hidden id="farmerDetails" value='%{getLocaleProperty("farmerDetails")}'  />
							
							<s:hidden id="farmCropAreaBy" value='%{getLocaleProperty("farmCropAreaBy")}'  />
							<s:hidden id="farmCropArea" value='%{getLocaleProperty("farmCropArea")}'  />
							
							<s:hidden id="estvsactyeld" value='%{getLocaleProperty("estvsactyeld")}'  />
							
							<s:hidden id="farmrsCount" value='%{getLocaleProperty("farmrsCount")}'  />
							<s:hidden id="crops" value='%{getLocaleProperty("crops")}'  />
							
							<s:hidden id="estimated" value='%{getLocaleProperty("estimated")}'  />
							<s:hidden id="actual" value='%{getLocaleProperty("actual")}'  />
							
							<s:hidden id="back" value='%{getLocaleProperty("back")}'  />
							<s:hidden id="group" value='%{getLocaleProperty("group")}'  />
							
							<div class="col-md-6">
								<div style="overflow: auto">
									<div id="farmerByLocationChart_bar" class="chartOverflow"></div>
								</div>

							</div>

							<div class="col-md-6">
								<div style="overflow: auto">
									<div id="farmerByLocationChart_pie" class="chartOverflow"></div>
								</div>
							</div>

							<div class="col-md-6">
								<div style="overflow: auto">
									<div id="farmerByLocation_crop_3d" class="chartOverflow"></div>
								</div>
							</div>

							<div class="col-md-6">
								<div style="overflow: auto">
									<div id="farmerByLocation_estimated" class="chartOverflow"></div>
								</div>
							</div>

						</div>

						
					</div>
				</div>
			</div>
			

		</div>

	
	<script src="plugins/echarts/echarts.min.js?v=2.8"></script>
	<script src="js/dashboard-charts.js?vk=120.2.53"></script>



	<script type="text/javascript">
	
	var tenantId="<s:property value='tenantId'/>";
	var codeForCropChart='';
	var branchId='<s:property value="branchId"/>';
	var areaType=$("#areaType").val();
		var noData = "";
		$(document).ready(function() {
			var tenantId =getCurrentTenantId();
			
				$(".finYearDiv").addClass("hide");
				$(".finaYearDiv").addClass("hide");
				$(".finalYearDiv").addClass("hide");
			
			
			
			
			var winHeight = window.innerHeight - $('.headerBar').height()-500;
		    
		    var dashboardHeight = ($('.dashboardPageWrapper').height() - $('.headerBar').height() - 80);
		   // var dashboardHeight = 1700;
		    $('.dashboardPage').height(dashboardHeight);
		    $('.column-left').height(dashboardHeight);
		    $('.overFlowContainer').height($('.column-left').height()-45);
		    $('.column-right-dashboard').height(dashboardHeight);
		    $('.column-right-dashboard-ht').height(dashboardHeight);
		    
			setDateRange();
		
			onFilterData();
			$(".select2").select2();
			/*  $(".flxrSelect2Bg").addClass("flxrSelect2Bg");
			$('#groupFilter').val(null).trigger('change');
			$('#farmerDetailFilter').val(null).trigger('change');
			$('#cropFilter').val(null).trigger('change');
			$('#groupTraderFilter').val(null).trigger('change');
			$('#selectedTraderStateFilter').val(null).trigger('change');
			$('#selectedTradervillageFilter').val(null).trigger('change');
			$('#selectedTraderCooperativeGroupFilter').val(null).trigger('change');
		
			$('#selectedWarehouse').val(null).trigger('change');
			$('#farmerSelectedGenderFilter').val(null).trigger('change');
			$('#farmerDetailTimelineYearFilter').val(null).trigger('change');
			$('#selectedCropFilter').val(null).trigger('change');
			$('#selectedCooperativeFilter').val(null).trigger('change');
			$('#selectedStateFilter').val(null).trigger('change');
			$('#selectedGenderFilter').val(null).trigger('change');
			$('#selectedFarmerGroupCropFilter').val(null).trigger('change');
			$('#selectedCooperativeGroupFilter').val(null).trigger('change');
			$('#farmerCropStateFilter').val(null).trigger('change');
			$('#farmerCropSelectedGenderFilter').val(null).trigger('change');
			$('#selectedCropCooperativeFilter').val(null).trigger('change');
			$('#selectedFarmerCropFilter').val(null).trigger('change');
			$('#farmerCropFilterTimelineYearFilter').val(2018).trigger('change');
			$('#farmerProdDetailFilter').val(null).trigger('change');
			$('#distributionseasonFilter').val(null).trigger('change');
			$('#finYearFilter').val(null).trigger('change');
			$('#selectedstatusFilter').val(null).trigger('change');
			$('#selectedSeedTypeGenderFilter').val(null).trigger('change');
			$('#selectedSeedTypeCropFilter').val(null).trigger('change');
			$('#selectedSeedTypeCooperativeFilter').val(null).trigger('change');
			
			$('#selectedSeedSourceGenderFilter').val(null).trigger('change');
			$('#selectedSeedSourceCropFilter').val(null).trigger('change');
			$('#selectedSeedSourceCooperativeFilter').val(null).trigger('change');
			$('#villageFilter').val(null).trigger('change');
			$('#varietyFilter').val(null).trigger('change');
			$('#seedSrStateFilter').val(null).trigger('change'); */
			
			populateMethod(); 
			 $("#getTheme").click(function(){
				 setChartTheme();
				 formCharts();
				  });
			 
			 $("#getGridLight").click(function(){
				 location.reload();
			    });
			 
			 
			 formCharts();
			 $('select').not(".slideNot").on('change', function() {
				  var className= $(this).parent().parent().parent().attr("class").split(' ').pop(); 
				  $("."+className).slideToggle("slow");
				})
				if(!isEmpty(branchId)){
					farmerByGroupChart_bar(branchId);
					
				}else{
					farmerByGroupChart_bar('');
				}
				
			
		});
		  function getCurrentSeason(){
			return "<s:property value='getCurrentSeason()'/>";
		}  
		  

		function setGridLight()
		 {
			 
			 esecharts.theme = {
						colors: ['#7cb5ec', '#f7a35c', '#90ee7e', '#7798BF', '#aaeeee', '#ff0066',
							'#eeaaee', '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'],
						chart: {
							backgroundColor: null,
							style: {
								fontFamily: 'Dosis, sans-serif'
							}
						},
						title: {
							style: {
								fontSize: '16px',
								fontWeight: 'bold',
								textTransform: 'uppercase'
							}
						},
						tooltip: {
							borderWidth: 0,
							backgroundColor: 'rgba(219,219,216,0.8)',
							shadow: false
						},
						legend: {
							itemStyle: {
								fontWeight: 'bold',
								fontSize: '13px'
							}
						},
						xAxis: {
							gridLineWidth: 1,
							labels: {
								style: {
									fontSize: '12px'
								}
							}
						},
						yAxis: {
							minorTickInterval: 'auto',
							title: {
								style: {
									textTransform: 'uppercase'
								}
							},
							labels: {
								style: {
									fontSize: '12px'
								}
							}
						},
						plotOptions: {
							candlestick: {
								lineColor: '#404048'
							}
						},


						// General
						background2: '#F0F0EA'

					};

					// Apply the theme
					esecharts.setOptions(esecharts.theme);
		 }
		
		  function setChartTheme(){
			  esecharts.theme = {
			       colors: ['#2b908f', '#90ee7e', '#f45b5b', '#7798BF', '#aaeeee', '#ff0066',
			          '#eeaaee', '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'],
			       chart: {
			          backgroundColor: {
			             linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
			             stops: [
			                [0, '#2a2a2b'],
			                [1, '#3e3e40']
			             ]
			          },
			          style: {
			             fontFamily: '\'Unica One\', sans-serif'
			          },
			          plotBorderColor: '#606063'
			       },
			       title: {
			          style: {
			             color: '#E0E0E3',
			             textTransform: 'uppercase',
			             fontSize: '20px'
			          }
			       },
			       subtitle: {
			          style: {
			             color: '#E0E0E3',
			             textTransform: 'uppercase'
			          }
			       },
			       xAxis: {
			          gridLineColor: '#707073',
			          labels: {
			             style: {
			                color: '#E0E0E3'
			             }
			          },
			          lineColor: '#707073',
			          minorGridLineColor: '#505053',
			          tickColor: '#707073',
			          title: {
			             style: {
			                color: '#A0A0A3'

			             }
			          }
			       },
			       yAxis: {
			          gridLineColor: '#707073',
			          labels: {
			             style: {
			                color: '#E0E0E3'
			             }
			          },
			          lineColor: '#707073',
			          minorGridLineColor: '#505053',
			          tickColor: '#707073',
			          tickWidth: 1,
			          title: {
			             style: {
			                color: '#A0A0A3'
			             }
			          }
			       },
			       tooltip: {
			          backgroundColor: 'rgba(0, 0, 0, 0.85)',
			          style: {
			             color: '#F0F0F0'
			          },
			          
			       },
			       plotOptions: {
			          series: {
			             name:" ",
			           dataLabels: {
			                color: '#B0B0B3'
			             },
			             marker: {
			                lineColor: '#333'
			             }
			          },
			          boxplot: {
			             fillColor: '#505053'
			          },
			          candlestick: {
			             lineColor: 'white'
			          },
			          errorbar: {
			             color: 'white'
			          }
			       },
			       legend: {
			          itemStyle: {
			             color: '#E0E0E3'
			          },
			          itemHoverStyle: {
			             color: '#FFF'
			          },
			          itemHiddenStyle: {
			             color: '#606063'
			          }
			       },
			       credits: {
			          style: {
			             color: '#666'
			          }
			       },
			       labels: {
			          style: {
			             color: '#707073'
			          }
			       },

			       drilldown: {
			          activeAxisLabelStyle: {
			             color: '#F0F0F3'
			          },
			          activeDataLabelStyle: {
			             color: '#F0F0F3'
			          }
			       },

			       navigation: {
			          buttonOptions: {
			             symbolStroke: '#DDDDDD',
			             theme: {
			                fill: '#505053'
			             }
			          }
			       },

			       // scroll charts
			       rangeSelector: {
			          buttonTheme: {
			             fill: '#505053',
			             stroke: '#000000',
			             style: {
			                color: '#CCC'
			             },
			             states: {
			                hover: {
			                   fill: '#707073',
			                   stroke: '#000000',
			                   style: {
			                      color: 'white'
			                   }
			                },
			                select: {
			                   fill: '#000003',
			                   stroke: '#000000',
			                   style: {
			                      color: 'white'
			                   }
			                }
			             }
			          },
			          inputBoxBorderColor: '#505053',
			          inputStyle: {
			             backgroundColor: '#333',
			             color: 'silver'
			          },
			          labelStyle: {
			             color: 'silver'
			          }
			       },

			       navigator: {
			          handles: {
			             backgroundColor: '#666',
			             borderColor: '#AAA'
			          },
			          outlineColor: '#CCC',
			          maskFill: 'rgba(255,255,255,0.1)',
			          series: {
			             color: '#7798BF',
			             lineColor: '#A6C7ED'
			          },
			          xAxis: {
			             gridLineColor: '#505053'
			          }
			       },

			       scrollbar: {
			          barBackgroundColor: '#808083',
			          barBorderColor: '#808083',
			          buttonArrowColor: '#CCC',
			          buttonBackgroundColor: '#606063',
			          buttonBorderColor: '#606063',
			          rifleColor: '#FFF',
			          trackBackgroundColor: '#404043',
			          trackBorderColor: '#404043'
			       },

			       // special colors for some of the
			       legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
			       background2: '#505053',
			       dataLabelsColor: '#B0B0B3',
			       textColor: '#C0C0C0',
			       contrastTextColor: '#F0F0F3',
			       maskColor: 'rgba(255,255,255,0.3)'
			    };

			    // Apply the theme
			    esecharts.setOptions(esecharts.theme);
			  

			 }
		
		  
		function onFilterData(){
			callAjaxMethod("dashboard_populateBranchList.action","branchName");
			callAjaxMethod("dashboard_populateSeasonList.action","seasonName");
			//$(".seasonName").val(getCurrentSeason()); //Enable when ever current season need to be header value of season filter drop down  
			callAjaxMethod("dashboard_populateStateList.action","stateName");
		//	callAjaxMethod("dashboard_populateProcurementProductList.action","productName");
			callAjaxMethod("dashboard_populateSamithiList.action","samithiName");
			callAjaxMethod("dashboard_populateWarehouseList.action","warehouseName");
			callAjaxMethod("dashboard_populateCooperativeList.action","cooperativeName");
			/* callAjaxMethod("dashboard_populateStapleLenList.action","stapleLen");
			callAjaxMethod("dashboard_populateFinancialYearList.action","finYear");
			callAjaxMethod("dashboard_populateFinancialYearList.action","finaYear");
			callAjaxMethod("dashboard_populateFinancialYearList.action","finalYear"); */
			callAjaxMethod("dashboard_populateStatusList.action","statusName");
			
		}
		
		function callAjaxMethod(url,name){
			var cat = $.ajax({
				url: url,
				async: false, 
				type: 'post',
				success: function(result) {
					insertOptionsByClass(name,JSON.parse(result));
				}        	

			});
			
		}
		
		 function insertOptionsByClass(ctrlName, jsonArr) {
			 $.each(jsonArr, function(i, value) {
		            $("."+ctrlName).append($('<option>',{
		            	 value: value.id,
		                 text: value.name
			 }));
		});	
		 }
		function formCharts(){
			var branchId =getCurrentBranch();
			var filterArrary;
			var warehouseStockChart="<s:property value="%{getPreferenceValue('WAREHOUSE_STOCK_CHART')}" />";
			var farmersGroupByPie="<s:property value="%{getPreferenceValue('FARMERS_BY_GROUP_PIE')}" />";
			var farmersGroupByBar="<s:property value="%{getPreferenceValue('FARMERS_BY_GROUP_BAR')}" />";
			var deviceChart="<s:property value="%{getPreferenceValue('DEVICE_CHART')}" />";
			var farmersByOrg="<s:property value="%{getPreferenceValue('FARMERS_BY_ORG')}" />";
			var totLandChart="<s:property value="%{getPreferenceValue('TOTAL_LAND_ACRE_CHART')}" />";
			var totProductionChart="<s:property value="%{getPreferenceValue('TOTAL_AREA_PRODUCTION_CHART')}" />";
			var doughnutSeedType="<s:property value="%{getPreferenceValue('SEED_TYPE_CHART')}" />";
			var seedTypeChart="<s:property value="%{getPreferenceValue('SEED_TYPE_CHART')}" />";
			
			var seedSourceChart="<s:property value="%{getPreferenceValue('SEED_SOURCE_CHART')}" />";
			var farmerDetailsChart="<s:property value="%{getPreferenceValue('FARMER_DETAILS_CHART')}" />";
			var farmerCropDetails="<s:property value="%{getPreferenceValue('CROP_DETAILS_CHART')}" />";
			var txnChart="<s:property value="%{getPreferenceValue('TXN_CHART')}" />";
			/* var cowMilkByMonthChart="<s:property value="%{getPreferenceValue('COW_MILK_MONTH_CHART')}" />";
			var cowMilkNonMilkChart="<s:property value="%{getPreferenceValue('COW_MILK_NON_MILK_CHART')}" />";
			var cowByVillageChart="<s:property value="%{getPreferenceValue('COW_BY_VILLAGE_CHART')}" />";
			var cowByRsChart="<s:property value="%{getPreferenceValue('COW_BY_RESEARCH_STATION_CHART')}" />";
			var cowDiseaseChart="<s:property value="%{getPreferenceValue('COW_DISEASE_CHART')}" />";
			var cowCostChart="<s:property value="%{getPreferenceValue('COW_COST_BAR_CHART')}" />"; */
			var totLandByVillageChart="<s:property value="%{getPreferenceValue('TOTAL_LAND_ACRE_BY_VILLAGE_CHART')}" />";
			var farmerEconomyExpenses="<s:property value="%{getPreferenceValue('FARMER_COC_CHART')}" />";
			var farmerDataStatistics="<s:property value="%{getPreferenceValue('FARMER_DATA_STAT_CHART')}" />";
			var farmerIcsChart="<s:property value="%{getPreferenceValue('FARMER_ICS_CHART')}" />";
			var cropHarvestSale="<s:property value="%{getPreferenceValue('CROP_HARVEST_SALE')}" />";
			var cocSegregateChart="<s:property value="%{getPreferenceValue('COC_SEGREGATE_CHART')}" />";
			var procurementChart="<s:property value="%{getPreferenceValue('PROCUREMENT_DETAILS_BAR_CHART')}" />";
			var farmerProductionDetails="<s:property value="%{getPreferenceValue('FARMER_PRODUCTION_DETAILS')}" />";
			var farmerSwngHavstChart="<s:property value="%{getPreferenceValue('FARMER_SOWING_HARVEST_BAR_CHART')}" />";
			var areaUnderProdByOrg="<s:property value="%{getPreferenceValue('AREA_UNDER_PRODUCTION_BY_ORG_CHARTS')}" />";
			var ginnerQtySold="<s:property value="%{getPreferenceValue('GINNER_QUANTITY_SOLD_CHARTS')}" />";
			var gmoCharts="<s:property value="%{getPreferenceValue('GMO_CHARTS')}" />";
			var cottonPrice="<s:property value="%{getPreferenceValue('COTTON_PRICE')}" />";
			var txnNswitchChart="<s:property value="%{getPreferenceValue('TXN_FARMERENROLL_CHART')}" />";
			var sowingChart="<s:property value="%{getPreferenceValue('SOWING_CHART')}" />";
			var farmersGroupTraderByBar="<s:property value="%{getPreferenceValue('FARMERS_BY_GROUP_TRADER_BAR')}" />";
			var farmersInspectionGroupByBar="<s:property value="%{getPreferenceValue('FARMERS_INSPECTION_GROUP_BAR')}" />";
		//	populateLocationChart();
			//populateProcurementCharts();
		//	getChartData(""); enable when ever distribution old chart needed
		/* getChartData("AGENT_ID");
			getChartData("FARMER_ID"); */	
			var tenantId =getCurrentTenantId();
		
		    
			if(!isEmpty(getCurrentBranch())){
				farmerByLocationChart_bar(getCurrentBranch());
				
			}else{
				farmerByLocationChart_bar('');
			}
			
			if(farmerCropDetails=='1'){
				$(".farmercropBarChartDiv").removeClass("hide");
				farmerCropBarChart(jQuery("#cropFilter").val());
			}
					
			
		}
		
		function populateMethod() {
			jQuery.post("dashboard_populateMethod.action", {},
					function(result) {
						var valuesArr = $.parseJSON(result);
						$.each(valuesArr, function(index, value) {
							jQuery('#userCount').text(value.userCount);
							jQuery('#userPercentage').text(
									value.userCountPercentage
											+ '<s:text name="last.month"/>');
							jQuery('#userStatus').addClass(
									value.userCountstauts);
							jQuery('#userPercentage').addClass(value.userText);

							jQuery('#mobileUserCount').text(
									value.mobileUsersCount);
							jQuery('#mobileuserPercentage').text(
									value.mobileuserCountPercentage
											+ '<s:text name="last.month"/>');
							jQuery('#mobileuserStatus').addClass(
									value.mobileuserCountstauts);
							jQuery('#mobileuserPercentage').addClass(
									value.mobileText);

							jQuery('#farmerCount').text(value.farmerCount);
							jQuery('#farmerPercentage').text(
									value.farmerCountPercentage
											+ '<s:text name="last.month"/>');
							jQuery('#farmerStatus').addClass(
									value.farmerCountstauts);
							jQuery('#farmerPercentage').addClass(
									value.farmerText);

							jQuery('#deviceCount').text(value.deviceCount);
							jQuery('#devicePercentage').text(
									value.deviceCountPercentage
											+ '<s:text name="last.month"/>');
							jQuery('#deviceStatus').addClass(
									value.deviceCountstauts);
							jQuery('#devicePercentage').addClass(
									value.deviceText);

							jQuery('#warehouseCount')
									.text(value.warehouseCount);
							jQuery('#warehousePercentage').text(
									value.warehouseCountPercentage
											+ '<s:text name="last.month"/>');
							jQuery('#warehouseStatus').addClass(
									value.warehouseCountstauts);
							jQuery('#warehousePercentage').addClass(
									value.warehouseText);
							
							
									
						
									
									jQuery('#cropCount').text(value.cropCount);
									jQuery('#cropPercentage').text(
											value.cropCountPercentage
													+ '<s:text name="last.month"/>');
									jQuery('#cropStatus').addClass(
											value.cropCountstauts);
									jQuery('#cropPercentage').addClass(value.cropText);
									
									
									jQuery('#groupCount').text(value.groupCount);
									jQuery('#groupPercentage').text(
											value.groupCountPercentage
													+ '<s:text name="last.month"/>');
									jQuery('#groupStatus').addClass(
											value.groupCountstauts);
									jQuery('#groupPercentage').addClass(value.groupText);
									
									jQuery('#farmLandCount').text(value.farmLandCount);
									jQuery('#farmLandPercentage').text(
											value.farmLandCountPercentage
													+ '<s:text name="last.month"/>');
									jQuery('#farmLandstauts').addClass(
											value.farmLandCountstauts);
									jQuery('#farmLandPercentage').addClass(value.farmLandText);
									
									jQuery('#farmerCottonCount').text(value.farmerCottonCount);
									jQuery('#farmerCottonPercentage').text(
											value.farmerCottonPercentage
													+ '<s:text name="last.month"/>');
									jQuery('#farmerCottonStatus').addClass(
											value.farmerCottonstauts);
									jQuery('#farmerCottonPercentage').addClass(value.userText);

									jQuery('#farmCount').text(value.farmCount);
									jQuery('#farmPercentage').text(
											value.farmCountPercentage
													+ '<s:text name="last.month"/>');
									jQuery('#farmStauts').addClass(
											value.farmCountstauts);
									jQuery('#farmPercentage').addClass(value.farmText);
									
						});
					});
		}

		
		
		  function insertOptions(ctrlName, jsonArr) {
	        document.getElementById(ctrlName).length = 0;
	        addOption(document.getElementById(ctrlName), "Select", "");
	        for (var i = 0; i < jsonArr.length; i++) {
	            addOption(document.getElementById(ctrlName), jsonArr[i].name, jsonArr[i].id);
	        }
	       
	        var id="#"+ctrlName;
	        jQuery(id).select2();
	    }
		

		function addOption(selectbox, text, value) {
			var optn = document.createElement("OPTION");
			optn.text = text;
			optn.value = value;
			selectbox.options.add(optn);
		}
		
		function getCurrentBranch() {
			return "<s:property value='branchId'/>";
		}
		function getCurrentTenantId() {
			return "<s:property value='getCurrentTenantId()'/>";
		}
		
		function getCurrencyType() {
			return "<s:property value='getCurrencyType()'/>";
		}
		
		
		
		function filterDrop(className){
			$("."+className).slideToggle("slow");
		}
		
		
		function addElements(filterArray,ChartId,colSize){
			var colSizeFilter;
			var columnSize;
			if(filterArray.length>0)
			{
					columnSize=12/parseFloat(filterArray.length);
					if(columnSize>3)
					{
						colSizeFilter='col-md-'+columnSize;
					}
					else
					{
						colSizeFilter='col-md-3';
					}
					
			}
			else
			{
				colSizeFilter='col-md-4';
			}
			
	 		var mainDiv = $("<div/>").addClass('col-md-'+colSize);
			var chartRow = $("<div/>");
			
				
				for(var i=0;i<filterArray.length;i++)
				{
					var filterRow=$("<div class='"+colSizeFilter+"'/>");
					if(filterArray[i].trim()!=""){
						
					//	console.log("if"+ChartId)
						mainDiv.append(filterRow.append(jQuery("#"+filterArray[i].trim())));
					}
					else{
						//console.log("---else---"+ChartId)
						filterRow.html("<select class='form-control input-sm' style='visibility: hidden;'></select>");
						mainDiv.append(filterRow);
					}
					
				}
			
		
			
			chartRow.append(jQuery("#"+ChartId));
			
			mainDiv.append(chartRow);
			
			jQuery("#charts-div").append(mainDiv);
		}
		
		function setDateRange(){
			 var start = moment().subtract(365, 'days');
			    var end = moment();

			     function cb(start, end) {
			        $('#reportrange').val(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
			        $('#reportrange1').val(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
			    } 

			    $('#reportrange').daterangepicker({
			        startDate: start,
			        endDate: end,
			        ranges: {
			           'Today': [moment(), moment()],
			           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
			           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
			           'This Month': [moment().startOf('month'), moment().endOf('month')],
			           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
			        }
			    }, cb);
			    
			    
			    $('#reportrange1').daterangepicker({
			        startDate: start,
			        endDate: end,
			        ranges: {
			           'Today': [moment(), moment()],
			           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
			           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
			           'This Month': [moment().startOf('month'), moment().endOf('month')],
			           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
			        }
			    }, cb);

			cb(start, end);
		}
		
		function loadVillage(obj){
			var selectedState = $('#selectedStateFilter').val();
			jQuery.post("dashboard_populateVillageList.action",{selectedState:selectedState},function(result){
				insertOptions("villageFilter",JSON.parse(result));
				
			});
		}
		
		function loadVariety(obj){
			var selectedCrop = $('#selectedSeedSourceCropFilter').val();
			jQuery.post("dashboard_populateVarietylist.action",{selectedCrop:selectedCrop},function(result){
				insertOptions("varietyFilter",JSON.parse(result));
				
			});
		}
		
	
		
		function farmerByGroupChart_bar(branch){	
			jQuery.post("dashboard_populateFarmersByLocation.action",{selectedBranch:branch},function(result) {
				json = $.parseJSON(result);
				if(!isEmpty(json)){
				$.each(json, function(index, value) {
					renderfarmerByGroupChart(value.branch,value.country,value.state,value.locality,value.municipality,value.gramPanchayat,value.village,value.farmerDetails,value.getGramPanchayatEnable);
				});
			   }
			});

		}	
		function renderfarmerByGroupChart(branch,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable){
			
			var branchForPieChart;
			var iteration1 = 1;
			var iteration2 = 1;
			var dataLength = 0;
			var rightCount = 0;
			var rightClick = 0;
			var locationTitleArray;
			if(isEmpty(branchId)){
				locationTitleArray = "Farmer By Group";
			}
			else{
			locationTitleArray="Farmer By Group" ;
			}
			
			var unit = "Hectare";
			
			var titleForFarmAreaChart = locationTitleArray;
			titleForFarmAreaChart =  titleForFarmAreaChart.replace("Farmer By", "Farm Area By ");
		//	alert("Title : "+titleForFarmAreaChart);
			var chart = new	esecharts.chart('farmerByGroupChart_bar', {
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
			            text: "Farmers Count"
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
								if(isEmpty(branchId)){
									$.each(branch, function(index, branch) {
										
											res.push({
												name :branch.branchName,
												y : branch.count,
												drilldown:branch.branchId,
												id : branch.branchId
											 });
									
									});
									farmerByGroupChart_pie("B",branchForPieChart,unit,titleForFarmAreaChart);
								}else{
									$.each(country, function(index, country) {
									
											res.push({
													name :country.countryName,
													y : country.count,
													drilldown:country.countryCode,
													id : country.countryCode
												 });
												
									});
									farmerByGroupChart_pie('first_level_Branch_Login',branchId,unit,titleForFarmAreaChart);
									
								}
								
								return res;
							})(),
								point : {
										events : {
											click : function(event) {
										
												if(isEmpty(branchId)){
									
													populate_Group(this.id,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branchId);
												}else{
													//alert("AAAA " + this.id);
													 populate_FarmerCountByICS(this.id,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branchId);
													populate_GroupByICS(this.id,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branchId);
												   
												}
											}
										}
									}
						 }],
				
				
			
			  }, function(chart) {});
		}
		function farmerByGroupChart_pie(locationCode,branch,unit,titleForFarmAreaChart){
		//alert("LocationCode :"+locationCode);
			var iteration1 = 1;
			var iteration2 = 1;
			var dataLength = 0;

			var rightCount = 0;
			var rightClick = 0;
			jQuery.post("dashboard_getFarmDetailsAndProposedPlantingArea.action",{locationLevel:locationCode,selectedBranch:branch},function(farmDetailsAndAcre) {
				farmDetailsAndAcre_json = $.parseJSON(farmDetailsAndAcre);
				//alert("pie : "+farmDetailsAndAcre);
			
			
			var chart = new esecharts.chart('farmerByGroupChart_pie', {
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
											//url :'procurementProductEnroll_list.action?q='+value.locationName
										//	id : value.locationCode
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
				    //chart.renderer.button('<', chart.plotLeft - 60, chart.plotHeight + chart.plotTop).addClass('left_farmerByGroupChart_pie').add();
				   // chart.renderer.button('>', chart.plotLeft + chart.plotWidth + 30, chart.plotHeight + chart.plotTop).addClass('right_farmerByGroupChart_pie').add();
				    chart.xAxis[0].setExtremes(0,4);
				  
				    var drilldown = dataLength/5;
				    
				 
				    if($("#arrow_farmerByGroupChart_pie").length > 0){
				    	   $("#arrow_farmerByGroupChart_pie").remove();
				    	}

				    	
				    	var row = jQuery('<div/>', {
				    		class : "col-xs-12",
				    		id	  : "arrow_farmerByGroupChart_pie"
				    	});
				    					    
				    	$("#farmerByGroupChart_pie").append($(row));
				    	$(row).append('<div  class="col-xs-6 leftArrow"><button  type="button" class="btn   left_farmerByGroupChart_pie "><span class="glyphicon glyphicon-chevron-left"></span></button></div>')   
				    	$(row).append('<div  class="col-xs-6 rightArrow"><button  type="button" class="btn    right_farmerByGroupChart_pie"><span class="glyphicon glyphicon-chevron-right"></span> </button></div>')
				    	 
				    
				    $('.left_farmerByGroupChart_pie').click(function() {
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
				    
				    $('.right_farmerByGroupChart_pie').click(function() {
				    	
				    	
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
			
			var titleForFarmerGroupCropChart ;
			//codeForCropChart="";
			
			titleForFarmerGroupCropChart = titleForFarmAreaChart.replace("Farm Area By ","Farm Crop Area By");
			titleForFarmerGroupCropChart = titleForFarmAreaChart.replace("Farm Area","Farm Crop Area");
			//populateFarmerGroupCropChart(titleForFarmerGroupCropChart);
			populateFarmerGroupCropCharts(titleForFarmerGroupCropChart);
		}
		
		function link(state){
			window.open("samithi_detail.action?id="+state);
		}
		function populate_Group(branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch){
			// $("#farmerByGroup_crop_3d_chart").show();
				var branchForPieChart;
				var iteration1 = 1;
				var iteration2 = 1;
				var dataLength = 0;
				var rightCount = 0;
				var rightClick = 0;
				//codeForCropChart=branchId;
				//alert(codeForCropChart);
				var locationTitleArray='Farmer By Group';
			var unit = "Hectare";
				
				var titleForFarmAreaChart = locationTitleArray;
				titleForFarmAreaChart =  titleForFarmAreaChart.replace("Farmer By", "Farm Area By ");
				esecharts
				.chart(
						'farmerByGroupChart_bar',
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
							            text: "Farmers Count"
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
											
										
										
																$.each(country, function(index, country) {
																	
																		res.push({
																			name :country.countryName,
																			y : country.count,
																			drilldown:country.countryCode,
																			id : country.countryCode
																		});
																	
																});
																//alert(id);
																farmerByGroupChart_pie('first_level_Branch_Login',branchId,unit,titleForFarmAreaChart);
											return res;
										})(),
										point : {
											events : {
												click : function(event) {
													if(isEmpty(branchId)){
														
														populate_Group(this.id,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branchId);
													}else{
														 populate_FarmerCountByICS(this.id,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branchId);
														populate_GroupByICS(this.id,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branchId);
													  //  
													}
												}
											}
										}
								
									}
									],

						}, function(chart) {});
				

			}
			
			function populate_GroupByICS(countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch){
		//alert("GroupCode:  "+countryCode);
		 $("#farmerByGroup_crop_3d_chart").show();	
		var branchForPieChart;
				var iteration1 = 1;
				var iteration2 = 1;
				var dataLength = 0;
				var rightCount = 0;
				var rightClick = 0;
				codeForCropChart=countryCode;
				var locationTitleArray='Farmer By ICS';
				var unit = "Hectare";
					var titleForFarmAreaChart = locationTitleArray;
					titleForFarmAreaChart =  titleForFarmAreaChart.replace("Farmer By", "Farm Area By ");
				//alert(tenantId);

				esecharts
				.chart(
						'farmerByGroupChart_bar',
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
							            text: "Farm Count"
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
										var ids;
											$.each(state, function(index, state) {
												if(countryCode == state.samithiCode){
													res.push({
														name :state.icsType+" (2019-2020)",
														y : state.count,
														drilldown:state.samithiName,
														id : state.samithiCode,
														ids : state.samithiId
													});
												}
												
											});
											farmerByGroupChart_pie(countryCode,branchId,unit,titleForFarmAreaChart);				
														/*	return res2;
													})()
												});*/
								    	//	});
											return res;
										})(),

										point : {
											events : {
												click : function(event) {
													//alert(this.icsCode);
													//populate_FarmerCountByICS(this.icsCode,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
													//location.href = "http://stackoverflow.com";
													link(this.ids);
													//window.location.href="samithi_detail.action?id="+ids;
												}
											}
										}
									}
									],

						});
						//codeForCropChart="";
				//customMobSowingBackButton(warehouse,mobileUser,branch);
				customBackpopulate_Group(branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch)

			}
				
			function populate_FarmerCountByICS(samCode,countryCode,branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch){
			//	alert("BBB "+samCode);
				titleForFarmerGroupCropChart="Farmer Count By ICS";
				var dataForChart = new Array();
				var iteration1 = 1;
				var iteration2 = 1;
				var dataLength = 0;

				var season = $("#seasonid").val();
				var rightCount = 0;
				var rightClick = 0;
				//alert(codeForCropChart);
			//	codeForCropChart = "";
				if(codeForCropChart == "first_level_Branch_Login"){
					codeForCropChart = "";
				}else if(codeForCropChart==branchId){
					codeForCropChart = "";
				}
				jQuery.post("dashboard_populateFarmerBycrop.action",{selectedSeason:season,codeForCropChart:samCode,selectedBranch:branchId},function(farmerByGroup_crop) {
					
				var	farmerByGroup_crop_Data = $.parseJSON(farmerByGroup_crop);
				//alert(farmerByGroup_crop);
				//alert(farmerByGroup_crop_Data + " : : " + titleForFarmerGroupCropChart);
				$.each(farmerByGroup_crop_Data, function(index, value) {
						//if(iteration1 <= 3){
							dataForChart.push({	
								"name" : value.productName,
								"code" : value.productCode,
								"count" : value.farmerCount,
								"y" : Number(value.farmerCount),
								});
							//iteration1 = iteration1+1;
							dataLength = dataLength+1;
						/* }else{
							dataLength = dataLength+1;
						} */
				});
				console.log(dataForChart);
					esecharts.chart('farmerByGroup_crop_3d_chart', {
						    chart: {
						        type: 'pie',
						        marginLeft: 100,
						        marginRight: 100,
						    },
						    title: {
						        text: "Farmer Count By Crops"
						    },
						  
						    xAxis: {
						        type: 'category',
						        title: {
							        text: 'Products'
							    },
						        labels: {
						            //rotation: -45,
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
						        pointFormat: '<b>{point.y}</b>'
						    },
						   
						    plotOptions: {
						       /*  pie: {
						        	innerSize: 70,
						            depth: 45
						        }, */
						        series: {
						            borderWidth: 0,
						            dataLabels: {
						                enabled: true,
						                format: '{point.name} : <span style="color: {point.color};">{point.y}</span> '
						            	
						            }
						        }
						    },
						    
						  
						    series: [{
						       
						        data:dataForChart,
						      
						        showInLegend: true
						    }],
						   
						 }, function(chart) {});
				}); 
				
			
				codeForCropChart = "";
				
			}

			function customBackpopulate_Group(branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch) {
				 var chart = $('#farmerByGroupChart_bar').esecharts();
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

				    var custombutton = chart.renderer.button('Back', 500, 20, function(){
				    	populate_Group(branchId,country,state,locality,municipality,gramPanchayat,village,farmerDetails,getGramPanchayatEnable,unit,titleForFarmAreaChart,branch);
				    	 $("#farmerByGroup_crop_3d_chart").hide();
				    },null,hoverState,pressedState).add();
				 // $("#farmerByGroup_crop_3d_chart").hide();
			}
			
			function populateFarmerGroupCropCharts(titleForFarmerGroupCropChart){
				var dataForChart = new Array();
				var iteration1 = 1;
				var iteration2 = 1;
				var dataLength = 0;

				var season = $("#seasonid").val();
				var rightCount = 0;
				var rightClick = 0;
				//alert(codeForCropChart);
			//	codeForCropChart = "";
				if(codeForCropChart == "first_level_Branch_Login"){
					codeForCropChart = "";
				}else if(codeForCropChart==branchId){
					codeForCropChart = "";
				}
				
				jQuery.post("dashboard_populateFarmerLocationCropChart.action",{selectedSeason:season,codeForCropChart:codeForCropChart,selectedBranch:branchId},function(farmerByGroup_crop) {
					
				var	farmerByGroup_crop_Data = $.parseJSON(farmerByGroup_crop);
				//alert(farmerByGroup_crop);
				//alert(farmerByGroup_crop_Data + " : : " + titleForFarmerGroupCropChart);
				$.each(farmerByGroup_crop_Data, function(index, value) {
						//if(iteration1 <= 3){
							dataForChart.push({	
								"name" : value.productName,
								"y" : Number(Number(value.Area).toFixed(3)),
								"yield" : value.yield,
								"Yield" : value.Yield,
								"Mton" : value.Metricton,
								});
							//iteration1 = iteration1+1;
							dataLength = dataLength+1;
						/* }else{
							dataLength = dataLength+1;
						} */
				});
				
					esecharts.chart('farmerByGroup_crop_3d', {
						    chart: {
						        type: 'pie',
						        marginLeft: 100,
						        marginRight: 100,
						    },
						    title: {
						        text: titleForFarmerGroupCropChart
						    },
						  
						    xAxis: {
						        type: 'category',
						        title: {
							        text: 'Products'
							    },
						        labels: {
						            //rotation: -45,
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
						       /*  pie: {
						        	innerSize: 70,
						            depth: 45
						        }, */
						        series: {
						            borderWidth: 0,
						            dataLabels: {
						                enabled: true,
						                format: '{point.name} : <span style="color: {point.color};">{point.y}</span> '+areaType+'<br>{point.Yield} : <span style="color: {point.color};">{point.yield}</span> {point.Mton} '
						            	
						            }
						        }
						    },
						    
						  
						    series: [{
						       
						        data:dataForChart,
						      
						        showInLegend: true
						    }],
						   
						 }, function(chart) {});
				}); 
				
			
				codeForCropChart = "";
				
			}
			
</script>

	<script src="plugins/highcharts/charts.js"></script>
	<script src="plugins/highcharts/charts-3d.js"></script>
	<script src="plugins/highcharts/data.js"></script>
	<script src="plugins/highcharts/drilldown.js"></script>
	<link type="text/css" href="plugins/jquerycharts/ddchart.css"
		rel="stylesheet" />

	<script type="text/javascript" src="plugins/jquerycharts/jquery-ui.js"></script>

	<script type="text/javascript"
		src="plugins/jquerycharts/jquery.tooltip.js"></script>
	<script type="text/javascript"
		src="plugins/jquerycharts/jquery.ddchart.js"></script>

</body>