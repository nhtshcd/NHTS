/**
 * 
 */


 var colors = esecharts.getOptions().colors;
 var chartColors = ['MAGENTA','CYAN','ORANGE'];
 
 const NET_WEIGHT_COLOR_IDX=0;
 const TXN_AMT_COLOR_IDX=1;
 const PAY_AMT_COLOR_IDX=2;
 
 var seriesDatumValueObj = { 
     y: 0,
     color: '',
     drilldown: {
         name: '',
         categories: [],
         data: [],
         color: ''
     }
 };

 
 function setChart(options,chart) {	 
     chart.series[0].remove(false);
     chart.addSeries({
         type: options.type,
         name: options.name,
         data: options.data,
         color: options.color || 'white'
     }, false);
     chart.xAxis[0].setCategories(options.categories, false);
     chart.redraw();
 }
 
  
function getChartFieldData(filterDatas,unit,drillDownMsg,drilldownTooltip1,drilldownTooltip2,barChartLegend,barChartLegendTxnAmt,barChartLegendPayAmt){
	getChartDrillDownFieldData(filterDatas,unit,drillDownMsg,drilldownTooltip1,drilldownTooltip2,barChartLegend,barChartLegendTxnAmt,barChartLegendPayAmt);	
}

function getChartDrillDownFieldData(filterDatas,unit,drillDownMsg,drilldownTooltip1,drilldownTooltip2,barChartLegend,barChartLegendTxnAmt,barChartLegendPayAmt){
	$.post("procurementReport_populateChartDrillDownFieldData",{filterList:filterDatas},function(data){
 		var json = null;
 		try{
 		json = JSON.parse(data);
 		}catch(err){
 			console.log("Error: "+err);
 			setEmptyChart();
 		}
 		if(json!==null){
 		var procurementDetailValue = [];
 		var chartData = [];
 		var drillDownDatumValue = {
					 name: '',
			         categories: [],
			         data: [],
			         color: ''
			 };
 		var drillDownDatumTxnAmtValue = {
				 name: '',
		         categories: [],
		         data: [],
		         color: ''
		 };
 		var drillDownDatumPayAmtValue = {
				 name: '',
		         categories: [],
		         data: [],
		         color: ''
		 };
 		 var drillDownDatumValueWeight = {
 				 name: '',
 		         categories: [],
 		         data: [],
 		         color: ''
 		 };
 		 
 		for(j=0;j<json.length;j++){ 			
 		procurementDetailValue[0] = json[j].prodName;
 		procurementDetailValue[1] = json[j].procWeight; 	
 		procurementDetailValue[2] = json[j].txnAmt;
 		procurementDetailValue[3] = json[j].paymentAmt;
 		 		 			
 			drillDownDatumValue.categories.push(procurementDetailValue[0]);
 			drillDownDatumValue.data.push(procurementDetailValue[1]);
 			//drillDownDatumValue.name = "Total Net Weight"; 			  	
 			
 			drillDownDatumTxnAmtValue.categories.push(procurementDetailValue[0]);
 			drillDownDatumTxnAmtValue.data.push(procurementDetailValue[2]);
 			//drillDownDatumTxnAmtValue.name ="Trasaction Amount";
 			
 			drillDownDatumPayAmtValue.categories.push(procurementDetailValue[0]);
 			drillDownDatumPayAmtValue.data.push(procurementDetailValue[3]); 	
 			//drillDownDatumTxnAmtValue.name = "Payment Amount";
 			
 			drillDownDatumValueWeight.name = barChartLegend; 	
 			drillDownDatumTxnAmtValue.name = barChartLegendTxnAmt;
 			drillDownDatumPayAmtValue.name = barChartLegendPayAmt;
 		}
 		drillDownDatumValue.color = chartColors[NET_WEIGHT_COLOR_IDX];
 		drillDownDatumTxnAmtValue.color = chartColors[TXN_AMT_COLOR_IDX];
 		drillDownDatumPayAmtValue.color = chartColors[PAY_AMT_COLOR_IDX]; 		
 		
 		drillDownDatumValueWeight = drillDownDatumValue;
 		
 		getWeightChartAsPie(filterDatas,drillDownDatumValueWeight,unit,drillDownMsg,barChartLegend,drilldownTooltip1,drilldownTooltip2,drillDownDatumTxnAmtValue,drillDownDatumPayAmtValue,barChartLegendTxnAmt,barChartLegendPayAmt);
 		}
 	}); 	
}

function setChartData(chartData,categories,name,unit,drillDownMsg,drilldownTooltip1,drilldownTooltip2,totalNetWeightLabel,chart){
	chart = new esecharts.Chart({
	     chart: {
	         renderTo: 'container',
	         options3d: {
	             enabled: true,
	             alpha: 15,
	             beta: 3,
	             
	             
	         }
	     },
	     legend: {
	         layout: 'vertical',
	         align: 'right',
	         verticalAlign: 'top',
	         x: -40,
	         y: 80,
	         floating: true,
	         borderWidth: 1,
	         backgroundColor: ((esecharts.theme && esecharts.theme.legendBackgroundColor) || '#FFFFFF'),
	         shadow: true
	     },
	     title: {
	         text: name
	     },
	     subtitle: {
	         text: drillDownMsg
	     },
	     xAxis: {
	         categories: categories
	     },
	     yAxis: {
	         title: {
	             text: ""
	         }
	     },
	     plotOptions: {
	    	 pie: {
	             innerSize: 100,
	             depth: 45
	         },
	    	 series: {
	             cursor: 'pointer',
	             point: {
	                 events: {
	                     click: function() {
	                         var drilldown = this.drilldown;
	                         var options;
	                         if (drilldown) { // drill down
	                             options = {
	                                 'name': drilldown.name,
	                                 'categories': drilldown.categories,
	                                 'data': drilldown.data,
	                                 'color': drilldown.color,
	                                 'type': 'column'
	                             };
	                         } else { // restore	                        	 
	                             options = {
	                                 'name': name,
	                                 'categories': categories,
	                                 'data': chartData,
	                                 'type': 'pie'	                                 
	                             };
	                         }
	                         setChart(options,chart);
	                     }
	                 }
	             },
	             dataLabels: {
	                 enabled: true,
	                 color: colors[1],
	                 style: {
	                     fontWeight: 'bold'
	                 },
	                 formatter: function() {
	                	 if(typeof this.point.drilldown!=="undefined"){
	                     return  '<b>'+ this.y +" : "+this.point.drilldown.name+' </b><br/>';
	                	 }else{
	                		 return  '<b>'+ this.y +" "+' </b><br/>';
	                	 }
	                 }
	             }
	         }
	     },
	     tooltip: {
	         formatter: function() {
	             var point = this.point, s;
	                 //s = totalNetWeightLabel +':<b>'+ this.y +" "+unit+' </b><br/>';
	             if(typeof this.point.drilldown!=="undefined"){
	             s = '<b>'+ this.y +" : "+this.point.drilldown.name+' </b><br/>';
	             }else{
	            	 s = '<b>'+ this.y +"  "+' </b><br/>';
	             }
	             if (point.drilldown) {
	                 s += drilldownTooltip1;
	             } else {
	                 s += drilldownTooltip2;
	             }
	             return s;
	         }
	     },
	     series: [{
	         type: 'pie',
	         name: name,
	         data: chartData,
	         color: 'white'
	     }],
	     exporting: {
	         enabled: false
	     }
	 });

}

function getWeightChartAsPie(filterDatas,drillDownDatumValueWeight,unit,drillDownMsg,barChartLegend,drilldownTooltip1,drilldownTooltip2,drillDownDatumTxnAmtValue,drillDownDatumPayAmtValue,barChartLegendTxnAmt,barChartLegendPayAmt){
	$.post("procurementReport_populateChartFieldData",{filterList:filterDatas},function(data){
 		var json = JSON.parse(data); 		
 		var totalNetWeightLabel = json[0].totalNetWeightLabel; 		
 		var paymentAmountLabel = json[0].paymentAmountLabel;
 		var txnAmountLabel = json[0].txnAmountLabel;
 		var categories = [totalNetWeightLabel,paymentAmountLabel,txnAmountLabel];
 		var name = json[0].procurementChartTitle;
 		var procurementValue = [];
 		var chartData = [];
 		var chart; 		
 		
 		procurementValue[0] = json[0].totalNetwt;
 		procurementValue[1] = json[0].txnAmount;
 		procurementValue[2] = json[0].totalPayAmt;
 		
 		for(i=0;i<procurementValue.length;i++){
 			var seriesDatum = { 
 				     y: 0,
 				     color: '',
 				     drilldown:{
 				         name: '',
 				         categories: [],
 				         data: [],
 				         color: ''
 				     }
 				 };
 			seriesDatum.y = procurementValue[i]; 			
 			seriesDatum.color = chartColors[i];
 			drillDownDatumValueWeight.name = barChartLegend; 	
 			drillDownDatumTxnAmtValue.name = barChartLegendTxnAmt;
 			drillDownDatumPayAmtValue.name = barChartLegendPayAmt;
 			
 			if(i==NET_WEIGHT_COLOR_IDX){
 			seriesDatum.drilldown = drillDownDatumValueWeight; 			
 			}else if(i==TXN_AMT_COLOR_IDX){
 			seriesDatum.drilldown = drillDownDatumTxnAmtValue;
 			}else if(i==PAY_AMT_COLOR_IDX){
 			seriesDatum.drilldown = drillDownDatumPayAmtValue;
 			} 			 			
 			chartData.push(seriesDatum);
 		} 	
 		setChartData(chartData,categories,name,unit,drillDownMsg,drilldownTooltip1,drilldownTooltip2,totalNetWeightLabel,chart); 		
 	}); 
}

function setEmptyChart(){
	chart = new esecharts.Chart({
	     chart: {
	         renderTo: 'container',
	         options3d: {
	             enabled: true,
	             alpha: 15,
	             beta: 3,
	             
	             
	         }
	     },
	     legend: {
	         layout: 'vertical',
	         align: 'right',
	         verticalAlign: 'top',
	         x: -40,
	         y: 80,
	         floating: true,
	         borderWidth: 1,
	         backgroundColor: ((esecharts.theme && esecharts.theme.legendBackgroundColor) || '#FFFFFF'),
	         shadow: true
	     },
	     title: {
	         text: name
	     },
	     subtitle: {
	         text: "No Data Available!"
	     },
	     xAxis: {
	         categories: ""
	     },
	     yAxis: {
	         title: {
	             text: ""
	         }
	     },
	     plotOptions: {
	    	 pie: {
	             innerSize: 100,
	             depth: 45
	         },
	    	 series: {}
	     },
	     exporting: {
	         enabled: false
	     }
	 });

}