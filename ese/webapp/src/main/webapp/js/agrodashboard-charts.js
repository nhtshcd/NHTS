var color=['#CBD53F','#3AE594','#41E7D3','#23CCCF','#3CB1C4','#1392BB','#BCB3EB','#C799E4','#B852F8','#BE72D6','#CD58A8','#E769AC','#E32D8D'];
var jsonData = "";
var jsonFarmerData = "";
var save = "";
var Refresh = "";
var color1=['#CBD53F','#3AE594','#41E7D3','#23CCCF','#3CB1C4','#1392BB','#BCB3EB','#C799E4','#B852F8','#BE72D6','#CD58A8','#E769AC','#E32D8D'];
var colorLiteGreen=['rgba(38, 185, 154, 0.38)','rgba(3, 88, 106, 0.38)','#A5CF86','#92E398','#7AAF7E','#A0C3A3','#80F498','#77C5A4','#D0E3A0','#C3DE82','#4EA582','#5BAD98','#229883'];
var colorBrightGreen=['#A6D785','#9DB68C','#78AB46','#7F9A65','#659D32','#93DB70','#84BE6A','#86C67C','#8FBC8F','#3F9E4D','#66CDAA','#4F8E83','#4CB7A5','#20B2AA','#26b99a','#03586a'];
var colorGreenishBlue=['#0dba86','#0dbab1','#0dba94','#0db5ba','#0dbaa3','#0da6ba','#48D1CC','#01C5BB','#20B2AA','#41E7D3','#23CCCF','#3CB1C4','#1392BB','#03A89E','#45C3B8','#068481','#4CB7A5','#32CC99'];
var color2 = ['#50a3a2','#53e3a6','#366D61','#32a85c','#32a89c','#3294a8', '#32a848', '#edc1a5', '#9dc5c8', '#e1e8c8', '#7b7c68', '#e5b5b5', '#f0b489', '#928ea8','#bda29a'];
var colorNow=['#41E7D3','#23CCCF','#3CB1C4','#1392BB','#BCB3EB','#C799E4','#B852F8','#BE72D6','#CD58A8','#E769AC','#E32D8D','#CBD53F','#3AE594'];

Highcharts.setOptions({
    colors: color2
});

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

function loadGroupTxnChart() {
	
	var dateRange;
	if (!isEmpty(jQuery("#reportrange1").val())) {
		var rangeSplit = jQuery("#reportrange1").val().split("-");
		dateRange = dateFormatTransform(rangeSplit[0]) + "-"
				+ dateFormatTransform(rangeSplit[1]);
	}
		
	jQuery.post("agrodashboard_populateTxnChartData.action", {		
		dateRange : dateRange
	}, function(result) {
		var jsonTxnData = jQuery.parseJSON(result);
		var saleData = "";
		var harvestData = "";
		var distributionData = "";
		var procurementData = "";
		var enrollmentData = "";
				
		var unit = "";
		$.each(jsonTxnData, function(index, value) {

			if (index == 0) {
				saleData = value;
			} else if (index == 1) {
				harvestData = value;
			} else if (index == 2) {
				distributionData = value;
			} else if (index == 3) {
				procurementData = value;
			} else if (index == 4) {
				enrollmentData = value;
			}
			
		});
		txnCharts(saleData,harvestData,distributionData,procurementData,enrollmentData);
	});
}

function txnCharts(saleData,harvestData,distributionData,procurementData,enrollmentData)
{
	var distributionArry = new Array();
	var harvestArry = new Array();
	var procurementArry = new Array();
	var enrollmentDataArray = new Array();
	var saleDataArray = new Array();
	$.each(distributionData, function(index, value) {
		if (value.Qty !== undefined) {
			distributionArry.push(value.Qty);
		} else {
			distributionArry.push(0);
		}
	});

	$.each(harvestData, function(index, value) {
		if (value.Qty !== undefined) {
			harvestArry.push(value.Qty);
		} else {
			harvestArry.push(0);
		}
	});

	$.each(procurementData, function(index, value) {
		if (value.amt !== undefined) {
			procurementArry.push(value.amt);
		} else {
			procurementArry.push(0);
		}
	});

	$.each(enrollmentData, function(index, value) {
		if (value.nos !== undefined) {
			enrollmentDataArray.push(value.nos);
		} else {
			enrollmentDataArray.push(0);
		}
	});

	$.each(saleData, function(index, value) {
		if (value.Qty !== undefined) {
			saleDataArray.push(value.Qty);
		} else {
			saleDataArray.push(0);
		}
	});
	
	Highcharts.chart('chart_plot_01', {
	    chart: {
	        type: 'areaspline'
	    },
	    title: {
	        text: ''
	    },
	    legend: {
	   		  enabled:true,
	        //layout: 'vertical',
	       // align: 'left',
	       // verticalAlign: 'top',
	       // x: 150,
	       // y: 100,
	       //floating: true,
	       // borderWidth: 1,
	        backgroundColor:
	            Highcharts.defaultOptions.legend.backgroundColor || '#FFFFFF'
	    },
	    xAxis: {
	        categories: [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul','Aug', 'Sep', 'Oct', 'Nov', 'Dec' ],
	        tickWidth: 0,
	        gridLineWidth: 0.7,
	        tickInterval: 1                 
	    },
	    yAxis: {
	        title: {
	            text: ''
	        },
	        tickWidth: 0,
	        tickInterval: 2,
	        gridLineWidth: 0.7     
	    },
	    tooltip: {
	        shared: true	       
	    },
	   credits: {
		        enabled: false},
		     exporting: {
		    	  enabled: false},
	    plotOptions: {
	        areaspline: {
	            fillOpacity: 0.5
	        },
	        series: {
	            dataLabels: {
	              enabled: false}	              
	        }
	    },
	    series: [{    
	        name: 'Distribution',
	        color:'rgba(38, 185, 154, 0.38)',
	        data:distributionArry,
	        tooltip: {
		        valueSuffix: ' Kg'
		    }
	    }, {
	        name: 'Crop Harvest',
	        color:'rgba(3, 88, 106, 0.38)',
	        data: harvestArry,
	        tooltip: {
		        valueSuffix: ' Kg'
		    }
	    }, {
	        name: 'Crop Sale',
	        color:'#A5CF86',
	        data: saleDataArray,
	        tooltip: {
		        valueSuffix: ' Kg'
		    }
	    }, {
	        name: 'Enrollment',
	        color:'#92E398',
	        data: enrollmentDataArray,
	        tooltip: {
		        valueSuffix: ''
		    }
	    }]
	});
}

function loadFarmerSummaryChart(){
	
	jQuery.post("agrodashboard_populateFarmersByLocationBranch.action",{},function(result) {
		json = $.parseJSON(result);		
		if(!isEmpty(json)){
		$.each(json, function(index, value) {			
			renderFarmerByLocationChart(value.brArray);
		});
	   }
	});
}
function renderFarmerByLocationChart(branch){
	
	Highcharts.chart('chart_plot_02', {
	    chart: {
	        type: 'cylinder',
	        options3d: {
	            enabled: true,
	            alpha: 5,
	            beta: 5,
	            depth: 50,
	            viewDistance: 25
	        }
	    },
	    credits: {
	        enabled: false},
	     exporting: {
	    	  enabled: false},
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Farmers Count'
	        }
	    },	 
	    xAxis: {
	        type: 'category',
	        labels: {
	            rotation: -45           
	        }
	    },
	    title: {
	        text: 'Farmers By Branch'
	    },
	    plotOptions: {
	        series: {
	            depth: 25,
	            colorByPoint: true,
	            colors:['rgba(38, 185, 154, 0.38)','rgba(3, 88, 106, 0.38)','#A5CF86','#92E398'],
	            dataLabels:{
	            enabled:true
	            }
	        }
	    },
	    series: [{	       
	        name: 'Farmers Count',
	        showInLegend: false,
	        data: branch
	    }]
	});
}


function populateAgroChemicalDealerChart(divId){
	
	jQuery.post("agrodashboard_populateCharts.action",{divId:divId},function(result) {
		json = $.parseJSON(result);		
		if(!isEmpty(json)){
		$.each(json, function(index, value) {			
			barChart(value.chartDivId,value.chartTitle,value.chartSubTittle,value.xaxisCategory,
					 value.yaxisTitle,value.tooltipSuffix,value.legendEnable,value.stackLabelEnable,value.seriesdata,value.chartType);
		});
	   }
	});
}

function populateAgroChemicalsChart(divId){
	
	jQuery.post("agrodashboard_populateCharts.action",{divId:divId},function(result) {
		json = $.parseJSON(result);		
		if(!isEmpty(json)){
		$.each(json, function(index, value) {			
			barChart(value.chartDivId,value.chartTitle,value.chartSubTittle,value.xaxisCategory,
					 value.yaxisTitle,value.tooltipSuffix,value.legendEnable,value.stackLabelEnable,value.seriesdata,value.chartType,value.xaxisTitle);
		});
	   }
	});
}
























































































































































/*GENERIC CHART - METHODS - HIGHCHARTS*/

/*----------------------------------------------LINE CHART---------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)

	var seriesdata=[{
	        name: 'Installation',
	        data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
	    }, {
	        name: 'Manufacturing',
	        data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
	    }, {
	        name: 'Sales & Distribution',
	        data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
	    }, {
	        name: 'Project Development',
	        data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
	    }, {
	        name: 'Other',
	        data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
	    }];

	var chartTitle = 'Solar Employment Growth by Sector, 2010-2016';
	var chartSubTittle  = 'Source: thesolarfoundation.com';
	var yaxisTitle = 'Number of Employees';
	var chartDivId = 'container';
	var xaxisStartPoint = 2010;*/

function lineChart(chartDivId ,chartTitle,chartSubTittle ,yaxisTitle,xaxisStartPoint,seriesdata)
{
	Highcharts.chart(chartDivId, {

	    title: {
	        text: chartTitle
	    },
	    subtitle: {
	        text: chartSubTittle
	    },
	    credits: {
	        enabled: false
	      },
	     exporting: {
	    	  enabled: false
	    },
	    yAxis: {
	        title: {
	            text: yaxisTitle
	        }
	    },
	    xAxis: {
	    	 accessibility: {
	             rangeDescription: 'Range: 1 to 31'
	         }
	          },
	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'middle'
	    },
	    plotOptions: {
	        series: {
	            label: {
	                connectorAllowed: false
	            },
	            pointStart: 1
	        }
	    },
	    series: seriesdata,
	    responsive: {
	        rules: [{
	            condition: {
	                maxWidth: 500
	            },
	            chartOptions: {
	                legend: {
	                    layout: 'horizontal',
	                    align: 'center',
	                    verticalAlign: 'bottom'
	                }
	            }
	        }]
	    }
	});
}

/*-----------------------------------------------BAR CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)

 * var seriesdata=[{
    name: 'Year 1800',
    data: [107, 31, 635, 203, 2]
}, {
    name: 'Year 1900',
    data: [133, 156, 947, 408, 6]
}, {
    name: 'Year 2000',
    data: [814, 841, 3714, 727, 31]
}, {
    name: 'Year 2016',
    data: [1216, 1001, 4436, 738, 40]
}];

var xaxisCategory = ['Africa', 'America', 'Asia', 'Europe', 'Oceania'];
var chartTitle = 'Historic World Population by Region';
var chartSubTittle  = 'Source: Wikipedia.org ';
var yaxisTitle = 'Population (millions)';
var chartDivId = 'container';
var legendEnable = true;
var stackLabelEnable = true;
var tooltipSuffix = ' millions';*/

function barChart(chartDivId,chartTitle,chartSubTittle,xaxisCategory,yaxisTitle,tooltipSuffix,legendEnable,stackLabelEnable,seriesdata,chartType,xaxisTitle)
{
	Highcharts.chart(chartDivId, {
	    chart: {
	        type: chartType
	    },
	    title: {
	        text: chartTitle
	    },
	    subtitle: {
	        text: chartSubTittle
	    },
	    credits: {
	        enabled: false
	      },
	     exporting: {
	    	  enabled: false
	    },
	    xAxis: {
	        categories: xaxisCategory,
	        crosshair:true,
	        title: {
	            text: xaxisTitle
	        }
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: yaxisTitle,
	            //align: 'high'
	        },
	        labels: {
	            overflow: 'justify'
	        }
	    },
	    tooltip: {
	        valueSuffix: tooltipSuffix
	    },
	    plotOptions: {
	        bar: {
	            dataLabels: {
	                enabled: stackLabelEnable
	            }
	        },column: {
	            pointPadding: 0.2,
	            pointWidth: 35,
	            borderWidth: 0,
	            dataLabels: {
	                enabled: stackLabelEnable
	            }
	        }
	    },
	    legend: {
	        layout: 'vertical',
	        enabled:legendEnable,
	        align: 'right',
	        verticalAlign: 'top',
	        x: -5,
	        y: 20,
	        floating: true,
	        borderWidth: 1,
	        backgroundColor:
	            Highcharts.defaultOptions.legend.backgroundColor || '#FFFFFF',
	        shadow: true
	    },    
	    series: seriesdata
	});
}

/*-----------------------------------------------STACKED BAR CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)

 var seriesdata=[{
        name: 'John',
        data: [5, 3, 4, 7, 2]
    }, {
        name: 'Jane',
        data: [2, 2, 3, 2, 1]
    }, {
        name: 'Joe',
        data: [3, 4, 4, 2, 5]
    }];

var chartTitle = 'Stacked bar chart';
var chartSubTittle  = '';
var yaxisTitle = 'Total fruit consumption';
var chartDivId = 'container';
var legendEnable = true;
var xaxisCategory = ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas'];*/


function stackedBarChart(chartDivId,chartTitle,chartSubTittle,xaxisCategory,yaxisTitle,legendEnable,seriesdata,tooltipSuffix,stackLabelEnable)
{
	Highcharts.chart(chartDivId, {
	    chart: {
	        type: 'bar'
	    },
	    title: {
	        text: chartTitle
	    },
	    subtitle: {
	        text: chartSubTittle
	    },
	     credits: {
	        enabled: false
	      },
	     exporting: {
	    	  enabled: false
	    },
	    xAxis: {
	        categories: xaxisCategory
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: yaxisTitle
	        },stackLabels: {
	            enabled: stackLabelEnable,
	            formatter: function () {
                    return this.total + " Kg";
                },
	            style: {
	                fontWeight: 'bold',	                
	                color: ( // theme
	                    Highcharts.defaultOptions.title.style &&
	                    Highcharts.defaultOptions.title.style.color
	                ) || 'gray'
	            }
	        }
	    },
	    tooltip: {
	        valueSuffix: tooltipSuffix,
	        pointFormat: '{series.name}: <b>{point.y} </b>'
	    },
	    legend: {
	        reversed: true, 
	        enabled:legendEnable,
	    },
	    plotOptions: {
	        series: {
	            stacking: 'normal'
	        },
	        dataLabels: {
                enabled: true,               
            }
	    },
	    series: seriesdata
	});
}

/*-----------------------------------------------NEGATIVE STACKED BAR CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * Tooltip - modify accordingly, based on the need

 var seriesdata=[{
        name: 'Male',
        data: [
            -2.2, -2.1, -2.2, -2.4,
            -2.7, -3.0, -3.3, -3.2,
            -2.9, -3.5, -4.4, -4.1,
            -3.4, -2.7, -2.3, -2.2,
            -1.6, -0.6, -0.3, -0.0,
            -0.0
        ]
    }, {
        name: 'Female',
        data: [
            2.1, 2.0, 2.1, 2.3, 2.6,
            2.9, 3.2, 3.1, 2.9, 3.4,
            4.3, 4.0, 3.5, 2.9, 2.5,
            2.7, 2.2, 1.1, 0.6, 0.2,
            0.0
        ]
    }];

var chartTitle = 'Population pyramid for Germany, 2018';
var chartSubTittle  = 'Source:Population Pyramids of the World from 1950 to 2100';
var yaxisTitle = '';
var chartDivId = 'container';
var xaxisCategory = [
    '0-4', '5-9', '10-14', '15-19',
    '20-24', '25-29', '30-34', '35-39', '40-44',
    '45-49', '50-54', '55-59', '60-64', '65-69',
    '70-74', '75-79', '80-84', '85-89', '90-94',
    '95-99', '100 + '
];*/

function negativeStackedBarChart(chartDivId,chartTitle,chartSubTittle,xaxisCategory,yaxisTitle,seriesdata)
{
Highcharts.chart(chartDivId, {
    chart: {
        type: 'bar'
    },
    title: {
        text: chartTitle
    },
    subtitle: {
        text: chartSubTittle
    },
    credits: {
        enabled: false
      },
     exporting: {
    	  enabled: false
    },
   
    xAxis: [{
        categories: xaxisCategory,
        reversed: false,
        labels: {
            step: 1
        },        
    }, { // mirror axis on right side
        opposite: true,
        reversed: false,
        categories: xaxisCategory,
        linkedTo: 0,
        labels: {
            step: 1
        }       
    }],
    yAxis: {
        title: {
            text: yaxisTitle
        },
        labels: {
            formatter: function () {
                return Math.abs(this.value) + '%';
            }
        }
    },
    plotOptions: {
        series: {
            stacking: 'normal'
        }
    },
    tooltip: {
        formatter: function () {
            return '<b>' + this.series.name + ', age ' + this.point.category + '</b><br/>' +
                'Population: ' + Highcharts.numberFormat(Math.abs(this.point.y), 1) + '%';
        }
    },
    series: seriesdata
});
}

/*-----------------------------------------------COLUMN BAR CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * Tooltip - modify accordingly, based on the need

 var seriesdata=[{
        name: 'Tokyo',
        data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]

    }, {
        name: 'New York',
        data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]

    }, {
        name: 'London',
        data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]

    }, {
        name: 'Berlin',
        data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]

    }];
var chartTitle ='Monthly Average Rainfall';
var chartSubTittle  = 'Source: WorldClimate.com';
var yaxisTitle = 'Rainfall (mm)';
var chartDivId = 'container';
var legendEnable = true;
var xaxisCategory =['Jan','Feb', 'Mar','Apr','May', 'Jun', 'Jul','Aug','Sep','Oct','Nov', 'Dec'];*/

function columnBarChart(chartDivId,chartTitle,chartSubTittle,xaxisCategory,yaxisTitle,legendEnable,seriesdata)
{
	Highcharts.chart(chartDivId, {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: chartTitle
	    },
	    subtitle: {
	        text: chartSubTittle
	    },
	    credits: {
	        enabled: false
	      },
	     exporting: {
	    	  enabled: false
	    },
	    legend: {
	        enabled: legendEnable,     
	    },
	    xAxis: {
	        categories: xaxisCategory,
	        crosshair: true
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: yaxisTitle
	        }
	    },
	    tooltip: {
	        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
	        footerFormat: '</table>',
	        shared: true,
	        useHTML: true
	    },
	    plotOptions: {
	        column: {
	            pointPadding: 0.2,
	            borderWidth: 0
	        }
	    },
	    series: seriesdata
	});
}

/*-----------------------------------------------STACKED COLUMN BAR CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * Tooltip - modify accordingly, based on the need

 var seriesdata=[{
        name: 'John',
        data: [5, 3, 4, 7, 2]
    }, {
        name: 'Jane',
        data: [2, 2, 3, 2, 1]
    }, {
        name: 'Joe',
        data: [3, 4, 4, 2, 5]
    }];

var chartTitle = 'Stacked bar chart';
var chartSubTittle  = '';
var yaxisTitle = 'Total fruit consumption';
var chartDivId = 'container';
var xaxisCategory = ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas'];
var stackLabelEnable = true;
var legendEnable = true;*/

function stackedColumnBarChart(chartDivId,chartTitle,chartSubTittle,xaxisCategory,yaxisTitle,legendEnable,stackLabelEnable,seriesdata)
{
	Highcharts.chart(chartDivId, {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: chartTitle
	    },
	    subtitle: {
	        text: chartSubTittle
	    },
	     credits: {
	        enabled: false
	      },
	     exporting: {
	    	  enabled: false
	    },
	    xAxis: {
	        categories: xaxisCategory
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: yaxisTitle
	        },
	        stackLabels: {
	            enabled: stackLabelEnable,
	            style: {
	                fontWeight: 'bold',
	                color: ( // theme
	                    Highcharts.defaultOptions.title.style &&
	                    Highcharts.defaultOptions.title.style.color
	                ) || 'gray'
	            }
	        }
	    },
	    legend: {
	        enabled:legendEnable,
	        align: 'right',
	        x: -30,
	        verticalAlign: 'top',
	        y: 25,
	        floating: true,
	        backgroundColor:
	            Highcharts.defaultOptions.legend.backgroundColor || 'white',
	        borderColor: '#CCC',
	        borderWidth: 1,
	        shadow: false
	    },
	    tooltip: {
	        headerFormat: '<b>{point.x}</b><br/>',
	        pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
	    },
	    plotOptions: {
	        column: {
	            stacking: 'normal',
	            dataLabels: {
	                enabled: stackLabelEnable
	            }
	        }
	    },
	    series: seriesdata
	});
}

/*----------------------------------------------- COLUMN BAR CHART WITH PAGINATION ------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * Tooltip - modify accordingly, based on the need
 * 
 * stepWidth can be set accordingly based on the data size
 * 
 * Include # before div id //  var chartDivId = '#container';
 * 
 * Design 4 buttons for pagination control with same id as mentioned here 
 * 
========================JSP design for pagination=========
 <div>
  <button id="beginning">Beginning</button>
  <button id="back">Back</button>
  <button id="forward">Forward</button>
  <button id="ending">Ending</button>
</div>
<div id="container" style="height: 400px"></div>
===========================================================

 var seriesdata=[49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5];

var chartTitle = 'bar chart';
var chartSubTittle  = '';
var yaxisTitle = '';
var chartDivId = '#container';
var xaxisCategory = ['S1', 'S2','S3','S4','S5','S6', 'S7','S8'];
var legendEnable = true;
var stepWidth = 5;
var len = seriesdata.length;
var seriesName='';
var rCount =  Math.floor(len/stepWidth);*/

function columnBarChartWithPagination(chartDivId,chartTitle,chartSubTittle,xaxisCategory,yaxisTitle,legendEnable,seriesName,seriesdata,stepWidth,len,rCount)
{
	$(function() {
		  $(chartDivId).highcharts({
		    chart: {
		      type: 'column',     
		      events: {
		        load: function() {
		          this.xAxis[0].setExtremes(0, 5);
		        }
		      }
		    },
		    title: {
		      text: chartTitle
		    },
		    subtitle: {
		      text: chartSubTittle
		    },
		    credits: {
				   enabled: false
			 },
				exporting: {
				   enabled: false
				},
		    legend:{
		    	enabled:false
		    },
		    xAxis: {
		      categories: xaxisCategory,
		      crosshair: false,
		      gridLineWidth: 0,
		      tickWidth: 0
		    },
		    yAxis: {
		      min: 0,
		      max: 150,
		      title: {
		        text: yaxisTitle
		      },    
		      gridLineWidth: 1,              
		    },
		    plotOptions: {
		      column: {
		        pointPadding: 0.2,
		        borderWidth: 0
		      }
		    },
		    series: [{
		      name: seriesName,
		      data: seriesdata
		    }]

		  });
		 
		  // the button action
		  var curCount=0;
		  $('#beginning').click(function() {
		    var chart = $(chartDivId).highcharts();
		    chart.xAxis[0].setExtremes(0, stepWidth);  
				curCount=0;
		  });

		  $('#forward').click(function() {
		    var chart = $(chartDivId).highcharts();
		    var currentMin = chart.xAxis[0].getExtremes().min;
		    var currentMax = chart.xAxis[0].getExtremes().max;
				if(curCount<rCount){
				    chart.xAxis[0].setExtremes(currentMin + stepWidth, currentMax + stepWidth);
				    curCount++;} 
		  });

		  $('#back').click(function() {
		    var chart = $(chartDivId).highcharts();
		    var currentMin = chart.xAxis[0].getExtremes().min;
		    var currentMax = chart.xAxis[0].getExtremes().max;
				if(curCount>0){
				    chart.xAxis[0].setExtremes(currentMin - stepWidth, currentMax - stepWidth);
				    curCount--;} 
		  });

		  $('#ending').click(function() {
		    var chart = $(chartDivId).highcharts();  
		    chart.xAxis[0].setExtremes(len-stepWidth,len-1);
				    curCount = rCount;
		  });
		});
}

/*-----------------------------------------------DRILL-DOWN BAR CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * Tooltip - modify according based on the need
 * 
 * Drill-down id should be same as the parent series drilldown-name

 var drillSeriesData=[
            {name: "Chrome",id: "Chrome",data: [["v58.0",1.02],["v57.0",7.36 ],["v57.0",7.36 ],["v56.0", 0.35 ],[ "v55.0",0.11],["v54.0",0.1]]},
            {name: "Firefox",id: "Firefox",data: [["v58.0",1.02],["v57.0",7.36 ],["v56.0", 0.35 ],[ "v55.0",0.11],["v54.0",0.1]]},
            {name: "Internet Explorer",id: "Internet Explorer",data: [["v58.0",1.02],["v56.0", 0.35 ],[ "v55.0",0.11]]}
            ];
            
var seriesdata= [{name: "Chrome", y: 62.74, drilldown: "Chrome"},
                {name: "Firefox",y: 10.57,drilldown: "Firefox"},
                {name: "Internet Explorer",y: 7.23,drilldown: "Internet Explorer"}];

var chartTitle = 'Browser market shares. January, 2018';
var chartSubTittle  = 'Source: statcounter.com';
var yaxisTitle = 'Total percent market share';
var seriesName='Browsers';
var chartDivId = 'container';
var legendEnable = false;*/

function drillDownBarChart(chartDivId,chartTitle,chartSubTittle,yaxisTitle,legendEnable,seriesName,seriesdata,drillSeriesData)
{
	Highcharts.chart(chartDivId, {
	    chart: {
	        type: 'column'
	    },
	    lang: {
	        drillUpText: '◁ Back'
	    },
	    title: {
	        text: chartTitle
	    },
	    subtitle: {
	        text: chartSubTittle
	    },
	     credits: {
	        enabled: false
	      },
	     exporting: {
	    	  enabled: false
	    },
	    xAxis: {
	        type: 'category'
	    },
	    yAxis: {
	        title: {
	            text: yaxisTitle
	        }
	    },
	    legend: {
	        enabled: legendEnable
	    },
	    plotOptions: {
	        series: {
	            borderWidth: 0,
	            dataLabels: {
	                enabled: true,
	                format: '{point.y:.1f}%'
	            }
	        }
	    },
	    tooltip: {
	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
	    },

	    series: [
	        {
	            name: seriesName,
	            colorByPoint: true,
	            data:seriesdata
	        }
	    ],
	    drilldown: {
	    drillUpButton: {
	                position: {
	                    x: 0,
	                    y: -35,
	                }
	            },
	        series: drillSeriesData
	    }
	});
}

/*-----------------------------------------------PIE CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * * Tooltip - modify accordingly, based on the need
 * =================================
 * If you want the pie to look sliced - make the data as follows

var seriesdata: [{
            name: 'Chrome',
            y: 61.41,
            sliced: true,selected:true   			//Add this for the first data
        }, 
        {name: 'Internet Explorer', y: 11.84},
        {name: 'Firefox', y: 10.85},
        {name: 'Edge', y: 4.67 },
        {name: 'Safari',y: 4.18},
        {name: 'Sogou Explorer',y: 1.64}]
        
====================================        

 var seriesdata=[{name: 'Chrome',y: 61.41}, 
        {name: 'Internet Explorer', y: 11.84},
        {name: 'Firefox', y: 10.85},
        {name: 'Edge', y: 4.67 },
        {name: 'Safari',y: 4.18},
        {name: 'Sogou Explorer',y: 1.64}];

var chartTitle = 'Browser market shares in January, 2018';
var chartSubTittle  = '';
var chartDivId = 'container';
var tooltipSuffix='%';
var seriesName='';
var legendEnable = true;*/

function pieChart(chartDivId,chartTitle,chartSubTittle,tooltipSuffix,legendEnable,seriesName,seriesdata,chartType)
{
	Highcharts.chart(chartDivId, {
	    chart: {        
	        type: chartType
	    },
	    title: {
	        text: chartTitle
	    },
	    subtitle: {
	      text: chartSubTittle
	    },
	    credits: {
			   enabled: false
		 },
			exporting: {
			   enabled: false
			},
	    tooltip: {
	       // pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	    },
	    accessibility: {
	        point: {
	            valueSuffix: tooltipSuffix
	        }
	    },
	    plotOptions: {
	    pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	                enabled: legendEnable,
	               // format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	            },
	            showInLegend: legendEnable
	        }        
	    },
	    series: [{
	        name: seriesName,
	        colorByPoint: true,
	        data: seriesdata
	    }]
	});
}

/*-----------------------------------------------DONUT CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * * Tooltip - modify accordingly, based on the need
     
var seriesdata=[
            ['Chrome', 58.9],
            ['Firefox', 13.29],
            ['Internet Explorer', 13],
            ['Edge', 3.78],
            ['Safari', 3.42]         
        ];

var chartTitle = 'Browser market shares in January, 2018';
var chartSubTittle  = '';
var chartDivId = 'container';
var labelEnable = true;
var tooltipSuffix='%';
var legendEnable = true;
var seriesName='Browser share';*/

function donutChart(chartDivId,chartTitle,chartSubTittle,tooltipSuffix,labelEnable,legendEnable,seriesName,seriesdata)
{
	Highcharts.chart(chartDivId, {
	    chart: {
	     type: 'pie'
	    },
	    title: {
	        text: chartTitle
	    },
	    subtitle: {
	      text: chartSubTittle
	    },
	    credits: {
			   enabled: false
		 },
			exporting: {
			   enabled: false
			},
	    tooltip: {
	        pointFormat: '{series.name}: <b>{point.y}</b>'
	    },
	    accessibility: {
	        point: {
	            valueSuffix: tooltipSuffix
	        }
	    },
	    plotOptions: {
	        pie: {
	             dataLabels: {
	                enabled: labelEnable,
	                format: '<b>{point.name}</b>: {point.y} '
	            },  
	            showInLegend: legendEnable,
	            center: ['50%', '50%'],
	            size:'100%'
	        }
	    },
	    series: [{      
	        name: seriesName,
	        innerSize: '70%',
	        data: seriesdata
	    }]
	});
}

/*-----------------------------------------------VARIABLE RADIUS PIE CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * Tooltip - modify accordingly, based on the need
     
var seriesdata=[{name: 'Spain',y: 505370,z: 92.9}, 
        { name: 'France', y: 551500,z: 118.7 }, 
        { name: 'Poland', y: 312685,z: 124.6 }, 
        { name: 'Czech Republic', y: 78867, z: 137.5},
        {name: 'Italy', y: 301340, z: 201.8}, 
        {name: 'Switzerland', y: 41277,z: 214.5},
        { name: 'Germany', y: 357022,z: 235.6 }];

var chartTitle = 'Countries compared by population density and total area.';
var chartSubTittle  = '';
var chartDivId = 'container';
var legendEnable=true;
var seriesName='countries';*/

function variableRadiusPieChart(chartDivId,chartTitle,chartSubTittle,legendEnable,seriesName,seriesdata)
{
	Highcharts.chart(chartDivId, {
	    chart: {
	        type: 'variablepie'
	    },
	    title: {
	        text: chartTitle
	    },
	     subtitle: {
	      text: chartSubTittle
	    },
	    credits: {
			   enabled: false
		 },
			exporting: {
			   enabled: false
			},
	    tooltip: {
	        headerFormat: '',
	        pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> {point.name}</b><br/>' +
	            'Area (square km): <b>{point.y}</b><br/>' +
	            'Population density (people per square km): <b>{point.z}</b><br/>'
	    },
	    plotOptions: {
	        variablepie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: legendEnable,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                },
	                showInLegend: legendEnable
	            }        
	        },
	    series: [{
	        minPointSize: 10,
	        innerSize: '20%',
	        zMin: 0,
	        name: seriesName,
	        data: seriesdata
	    }]
	});
}

/*-----------------------------------------------DRILL-DOWN PIE CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * Tooltip - modify according based on the need
 * 
 * Drill-down id should be same as the parent series drilldown-name

 var drillSeriesData=[
            {name: "Chrome",id: "Chrome",data: [["v58.0",1.02],["v57.0",7.36 ],["v57.0",7.36 ],["v56.0", 0.35 ],[ "v55.0",0.11],["v54.0",0.1]]},
            {name: "Firefox",id: "Firefox",data: [["v58.0",1.02],["v57.0",7.36 ],["v56.0", 0.35 ],[ "v55.0",0.11],["v54.0",0.1]]},
            {name: "Internet Explorer",id: "Internet Explorer",data: [["v58.0",1.02],["v56.0", 0.35 ],[ "v55.0",0.11]]}
            ];
            
var seriesdata= [{name: "Chrome", y: 62.74, drilldown: "Chrome"},
                {name: "Firefox",y: 10.57,drilldown: "Firefox"},
                {name: "Internet Explorer",y: 7.23,drilldown: "Internet Explorer"}];

var chartTitle = 'Browser market shares. January, 2018';
var chartSubTittle  = 'Source: statcounter.com';
var chartDivId = 'container';
var seriesName='Browsers';
var tooltipSuffix='%';*/

function drillDownPieChart(chartDivId,chartTitle,chartSubTittle,tooltipSuffix,seriesName,seriesdata,drillSeriesData)
{
	Highcharts.chart(chartDivId, {
	    chart: {
	        type: 'pie'
	    },
	    lang: {
		        drillUpText: '◁ Back'
		    },
	    title: {
	        text: chartTitle
	    },
	    subtitle: {
	        text: chartSubTittle
	    },
		credits: {
			   enabled: false
		 },
			exporting: {
			   enabled: false
			},
	    accessibility: {
	        announceNewData: {
	            enabled: true
	        },
	        point: {
	            valueSuffix: tooltipSuffix
	        }
	    },

	    plotOptions: {
	        series: {
	            dataLabels: {
	                enabled: true,
	                format: '{point.name}: {point.y:.1f}%'
	            }
	        }
	    },

	    tooltip: {
	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
	    },
	    series: [
	        {
	            name: seriesName,
	            colorByPoint: true,
	            data: seriesdata
	        }
	    ],
	    drilldown: {
	    drillUpButton: {
		                position: {
		                    x: 0,
		                    y: -35,
		                }
		            },
	        series:drillSeriesData           
	            }     
	});
}

/*-----------------------------------------------DRILL-DOWN PIE CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * Tooltip - modify according based on the need
 * 
           
var seriesdata=[{
        type: 'pie',
        name: 'Browser share',
        innerSize: '50%',
        data: [
            ['Chrome', 58.9],
            ['Firefox', 13.29],
            ['Internet Explorer', 13],
            ['Edge', 3.78],
            ['Safari', 3.42],
            {
                name: 'Other',
                y: 7.61,
                dataLabels: {
                    enabled: false
                }
            }
        ]
    }];

var chartTitle = 'Browser market shares. January, 2018';
var chartSubTittle  = 'Source: statcounter.com';
var chartDivId = 'container';
var seriesName='Browsers';
var tooltipSuffix='%';*/

function semiCircleChart(chartDivId,chartTitle,chartSubTittle,tooltipSuffix,seriesName,seriesdata,stackLabelEnable,seriesName)
{
	Highcharts.chart(chartDivId, {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: 0,
	        plotShadow: false
	    },
	    credits: {
			   enabled: false
		 },
		exporting: {
		   enabled: false
		},
	    title: {
	        text: chartTitle,
	        align: 'center',
	        verticalAlign: 'middle',
	        y: 60
	    },
	    tooltip: {
	        pointFormat: '{series.name}: <b>{point.y}</b>'
	    },
	    accessibility: {
	        point: {
	            valueSuffix: tooltipSuffix
	        }
	    },
	    plotOptions: {
	        pie: {
	            dataLabels: {
	                enabled: stackLabelEnable,
	                distance: -50,
	                style: {
	                    fontWeight: 'bold',
	                    color: 'white'
	                }
	            },
	            startAngle: -90,
	            endAngle: 90,
	            center: ['50%', '75%'],
	            size: '150%'
	        }
	    },
	    series: [{
	        type: 'pie',
	        name: seriesName,
	        innerSize: '50%',
	        data: seriesdata
	    }]
	});
}

/*-----------------------------------------------Comparison COLUMN BAR CHART------------------------------------------------
 * 
 * DATA description :(Form series-data in the below format)
 * 
 * Tooltip - modify accordingly, based on the need

 var seriesdata=[{
        name: 'John',
        data: [5, 3, 4, 7, 2]
    }, {
        name: 'Jane',
        data: [2, 2, 3, 2, 1]
    }, {
        name: 'Joe',
        data: [3, 4, 4, 2, 5]
    }];

var chartTitle = 'Stacked bar chart';
var chartSubTittle  = '';
var yaxisTitle = 'Total fruit consumption';
var chartDivId = 'container';
var xaxisCategory = ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas'];
var stackLabelEnable = true;
var legendEnable = true;*/

function comparisonColumnBarChart(chartDivId,chartTitle,chartSubTittle,xaxisCategory,yaxisTitle,legendEnable,seriesdata,tooltipSuffix,stackLabelEnable)
{

	Highcharts.chart(chartDivId, {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: chartTitle
	    },
	    subtitle: {
	        text: chartSubTittle
	    },
	     credits: {
	        enabled: false
	      },
	     exporting: {
	    	  enabled: false
	    },
	    xAxis: {
	        categories: xaxisCategory
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: yaxisTitle
	        },
	        stackLabels: {
	            enabled: stackLabelEnable,	           
	            style: {
	                fontWeight: 'bold',
	                color: ( // theme
	                    Highcharts.defaultOptions.title.style &&
	                    Highcharts.defaultOptions.title.style.color
	                ) || 'gray'
	            }
	        }
	    },
	    legend: {
	        enabled:legendEnable,
	        align: 'right',
	        x: -30,
	        verticalAlign: 'top',
	        y: 25,
	        floating: true,
	        backgroundColor:
	            Highcharts.defaultOptions.legend.backgroundColor || 'white',
	        borderColor: '#CCC',
	        borderWidth: 1,
	        shadow: false
	    },
	    tooltip: {
	        headerFormat: '<b>{point.x}</b><br/>',
	        pointFormat: '{series.name}: {point.y} kg'
	    },
	    plotOptions: {
	        column: {
	          //  stacking: 'normal',
	            dataLabels: {
	                enabled: stackLabelEnable,
	                formatter: function () {
	                    return this.y + " Kg";
	                }
	            }
	        }
	    },
	    series: seriesdata
	});
}