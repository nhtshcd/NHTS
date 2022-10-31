<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="assets/client/demo/css/agro-theme.css" />
<style>
body {
	background: none !important;
}

@media print {
	#prntCtrl {
		display: none;
	}
	.parentTbl td,th {
		width: 20%;
	}
}

.alnRht {
	text-align: right;
}

.fontBold {
	font-weight: bold;
}

.subTable td,th {
	border-right: 1px solid black;
	border-bottom: 1px solid black;
}

.subTable td:first-child,th:first-child {
	border-left: 1px solid black;
}

.subTable tr:first-child>th {
	border-top: 1px solid black;
}

.subTable tr:last-child>td {
	font-weight: bold;
}

.subTable td {
	padding-right: 5px;
}

.subTable td,th {
	width: 20% !important;
}

#prntCtrl {
	text-align: center;
	padding: 10px;
}

.logoImg {
	border: 1px solid black;
}

.signatureLine {
	height: 80px;
}

.fstTbl td {
	width: 33%;
}
.borderTop{
	border-top: 1px solid #a8a8a8;
}
.borderBottom{
	border-bottom: 1px solid #a8a8a8;
}
.borderTop td {
	border-top: 1px solid #a8a8a8;
}

.borderBottom td {
	border-bottom: 1px solid #a8a8a8;
}

.padding10 {
	padding-top: 8px;
	padding-bottom: 8px;
}

.subTable td:first-child {
	padding-left: 5px;
}

td {
	padding: 3px;
}

.firstTdSpan{
	float:left;
	width:150px;
	border:0px solid red;
	text-align: right;
    width: 135px;
}
.secondTdSpan{
	float:left;
	width:150px;
	border:0px solid red;
	text-align: right;
    width: 104px;
}
.rNoRow td{
	padding-top:10px;
}

.grayHeader > th, .grayHeader > td{
	background-color:#E8E8E8 ;
}
.finalBalCls{
	border-top:1px solid black;
	border-bottom:1px solid black;
	font-weight:bold;
}
.body-section{
            padding: 16px;
            border: 1px solid gray;
        }
         .container{
            width: 80%;
            margin-right: auto;
            margin-left: auto;
        }
            .row{
            display: flex;
            flex-wrap: wrap;
        }
        .col-6{
            width: 50%;
            flex: 0 0 auto;
        }
        .newspaper {
  column-count: 3;
  column-gap: 40px;
}
</style>
</head>
<script type="text/javascript">
		var referenceWindow;
		function printReceipt(){
			window.print();
		}
		function closeWindow(){
			try{				
				referenceWindow.close();
			}catch(e){				
				this.close();
			}
		}
		</script>
<body>
<div style="border: 1px solid black; padding: 20px;margin-bottom: 0px;">
<table border="0" width="100%" class="fstTbl" cellspacing="0" style=" margin-bottom: 5px;">
	<tr>
		<td colspan="2" align="center">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="fontBold padding10" style="font-size: 30px;">
		        <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='ship1'">
			         <s:text name="printTitleshipment" />
			    </s:if>
			
			    <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='sort0'">
			        <s:text name="printTitlesorting" />
		    	</s:if>
			
			    <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='pack2'">
			        <s:text name="printTitlepacking" />
		    	</s:if>
				</td>
				<td align="center" class="fontBold padding10" style="font-size: 30px;"><s:text
					name="produceofkenya" /></td>
				<td>&nbsp;</td>
			</tr>
			
		</table>
		</td>
		
	</tr>	
		
</table>
<hr>

<div class="container" style=" margin-bottom: 0px; margin-bottom: 1px;">
  <div class="body-section">
            <div class="row" style="display: flex;flex-wrap: wrap;">
                <div class="col-6" style=" width: 50%;flex: 0 0 auto;">
                <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='pack2'">
                    <h2 class="heading" style=" margin-bottom: 2px; margin-top: 5px;"><b><s:text name="packingDetails" />	</b></h2>
                </s:if>  
                <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='ship1'">
                    <h2 class="heading" style=" margin-bottom: 2px; margin-top: 5px;"><b><s:text name="Receiptlocation" />	</b></h2>
                </s:if>  
               <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='sort0'">
                <h2 class="heading" style=" margin-bottom: 2px; margin-top: 5px;"><b><s:text name="trasnportDetails" />	</b></h2>
                </s:if>  
                
                   <!--  <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Farm Id: fabcart2025 </p>
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Collection Center: 20-20-2021 </p> -->
                    
                    <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='ship1'">
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Packhouse:&nbsp;<b><s:property
			value="shipmentPrintMap['recNo']" /></b>&nbsp;</p>
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Receiving Date:&nbsp;<b><s:property
			value="shipmentPrintMap['date']" /></b>&nbsp;</p>
			<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Reception Batch No: &nbsp;<b><s:property
			value="shipmentPrintMap['agentName']" /></b>&nbsp;</p>
			           </s:if>
                    <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='pack2'">
            <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Packhouse:&nbsp;<b><s:property value="shipmentPrintMap['recNo']" /></b>&nbsp;</p>
            <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Packing Date:&nbsp;<b><s:property value="shipmentPrintMap['date']" /></b>&nbsp;</p>
			<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Packer Name: &nbsp;<b><s:property value="shipmentPrintMap['packerName']" /></b>&nbsp;</p>
			<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Lot Number: &nbsp;<b><s:property value="shipmentPrintMap['agentName']" /></b>&nbsp;</p>
			           </s:if>
			           <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='sort0'">
			            <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Type Of Transport: &nbsp;<b><s:property
			value="shipmentPrintMap['tructId']" /></b>&nbsp;</p>
					<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Vehicle No: &nbsp;<b><s:property
			value="shipmentPrintMap['vehicleno']" /></b>&nbsp;</p>
					<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Driver Name: &nbsp;<b><s:property
			value="shipmentPrintMap['driverName']" /></b>&nbsp;</p>
					<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Driver Contact: &nbsp;<b><s:property
			value="shipmentPrintMap['driverNo']" /></b>&nbsp;</p>
			           </s:if>
			           
                </div>
                <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='ship1'">
                <div class="col-6">
                 <h2 class="heading" style=" margin-bottom: 2px; margin-top: 5px;"><b>Delivery Note</b></h2>
                   <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Type Of Transport: &nbsp;<b><s:property
			value="shipmentPrintMap['tructId']" /></b>&nbsp;</p>
					<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Vehicle No: &nbsp;<b><s:property
			value="shipmentPrintMap['vehicleno']" /></b>&nbsp;</p>
					<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Driver Name: &nbsp;<b><s:property
			value="shipmentPrintMap['driverName']" /></b>&nbsp;</p>
					<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Driver Contact: &nbsp;<b><s:property
			value="shipmentPrintMap['driverNo']" /></b>&nbsp;</p>
                </div>
                </s:if>
            </div>
        </div>
        </div>
  
        
        <hr>
        
             <div class="container">
             <div class="body-section">
            <div class="row" style="display: flex;flex-wrap: wrap;">
                <div class="col-6" style=" width: 100%;flex: 0 0 auto;">
					<h2 class="heading" style=" margin-bottom: 0px; margin-top: 1px;"><b><s:text
					name="Receiptproddet" /></b></h2>
					<hr>
					<table width="100%" border="1" class="subTable" cellpadding="0"
			cellspacing="0">
			<tr class="grayHeader">
				 <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='sort0'">
				     <th><s:text name="blockName" /></th>
				     <th>Block Id</th>
				     <th>Planting Id</th>
				     <th><s:text name="Crop" /></th>
				     <th><s:text name="packhouseIncoming.variety" /></th>
				     <th><s:text name="Sorted Weight(Kgs)" /></th>
				</s:if>
				
				<%-- <th><s:text name="packhouseIncoming.transferWeight" /></th> --%>
				
				<s:if test="shipmentPrintMap['checkIncomingOrSorting']=='ship1'">
				     <th><s:text name="blockName" /></th>
				     <th>Block Id</th>
				     <th>Planting Id</th>
				     <th><s:text name="Crop" /></th>
			         <th><s:text name="packhouseIncoming.variety" /></th>
			         <th><s:text name="Sorting Date" /></th>
				     <th><s:text name="packhouseIncoming.recievedWeight" /></th>		
				     <th><s:text name="packhouseIncoming.recievedUnits" /></th>
				     <th><s:text name="packhouseIncoming.noOfUnits" /></th>	
				</s:if>	
				<s:if test="shipmentPrintMap['checkIncomingOrSorting']=='pack2'">
				     <th><s:text name="packing.farmer" /></th>
				     <th><s:text name="packing.farm" /></th>
				     <th><s:text name="packing.blockidandName" /></th>
				     <th><s:text name="shipment.planting" /></th>
			         <th><s:text name="packing.receptbatchNo" /></th>
				     <th><s:text name="packing.crop" /></th>		
				     <th><s:text name="packing.variety" /></th>
				     <th><s:text name="packing.incomungshipmentdate" /></th>	
				     <th><s:text name="packing.packedquantity" /></th>	
				     <th><s:text name="packing.rejectWt" /></th>
				     <th><s:text name="packing.price" /></th>	
				     <th><s:text name="packing.bestBeforeDate" /></th>	
				     <th><s:text name="packing.countryOfOrigin" /></th>	
				</s:if>	
			</tr>
			<s:if test='shipmentPrintMap.productMapList.size()>0'>
				<s:iterator value="shipmentPrintMap.productMapList" var="gListMap">
					<tr>
					<s:if test="shipmentPrintMap['checkIncomingOrSorting']=='sort0'">
						<td style="word-break: break-all;"><s:property value="blockname" /></td>
						<td style="word-break: break-all;"><s:property value="blockid" /></td>
						<td style="word-break: break-all;"><s:property value="plantingid" /></td>
						<td style="word-break: break-all;"><s:property value="product" /></td>
						<td style="word-break: break-all;"><s:property value="variety" /></td>
						<td style="word-break: break-all;text-align: right;"><s:property value="qty" /></td>
					</s:if>
					<s:if test="shipmentPrintMap['checkIncomingOrSorting']=='ship1'">
						<td style="word-break: break-all;"><s:property value="blockname" /></td>
						<td style="word-break: break-all;"><s:property value="blockid" /></td>
						<td style="word-break: break-all;"><s:property value="plantingid" /></td>
						<td style="word-break: break-all;"><s:property value="product" /></td>
						<td style="word-break: break-all;"><s:property value="variety" /></td>
						<td style="word-break: break-all;"><s:property value="sortingDate" /></td>
						<%-- <td style="word-break: break-all;"><s:property value="transferredweight" /></td> --%>
						<td style="word-break: break-all;text-align: right;"><s:property value="receivedweight" /></td>
						<td class="alnRht"><s:property value="receivedunit" /></td>
						<td class="alnRht"  style="text-align: right;"><s:property value="netWeight" /></td>
					</s:if>				
					<s:if test="shipmentPrintMap['checkIncomingOrSorting']=='pack2'">
						<td style="word-break: break-all;"><s:property value="packingFarmer" /></td>
						<td style="word-break: break-all;"><s:property value="packingFarm" /></td>
						<td style="word-break: break-all;"><s:property value="packingBlockidandName" /></td>
						<td style="word-break: break-all;"><s:property value="packingplanting" /></td>
						<td style="word-break: break-all;"><s:property value="packingReceptbatchNo" /></td>
						<td style="word-break: break-all;width: 90px;"><s:property value="packingCrop" /></td>
						<td style="word-break: break-all;width: 120px;"><s:property value="packingVariety" /></td>
						<td style="word-break: break-all;width: 77px;"><s:property value="packingincomingshipmentdate" /></td>
						<td style="word-break: break-all;text-align: right;width: 55px;"><s:property value="packingPackedquantity" /></td>
						<td style="word-break: break-all;;text-align: right;width: 55px;"><s:property value="rejectWt" /></td>
						<td style="word-break: break-all;;text-align: right;width: 55px;"><s:property value="packingPrice" /></td>
						<td style="word-break: break-all;width: 77px;"><s:property value="packingbestBeforeDate" /></td>
						<td style="word-break: break-all;width: 80px;"><s:property value="packingCountryOfOrigin" /></td>
						<%-- <td class="alnRht"  style="text-align: right;"><s:property value="netWeight" /></td> --%>
					</s:if>				
					</tr>
				</s:iterator>
				<s:iterator status="stat" value="(0-shipmentPrintMap.productMapList.size()).{ #this }" >
				   <tr>
						<td style="word-break: break-all;">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>			
						<td class="alnRht">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>	
						<td class="alnRht">&nbsp;</td>	
						<td class="alnRht">&nbsp;</td>	
					</tr>
				</s:iterator>
				<tr class="grayHeader">
					<td class="alnRht">&nbsp;</td>
					<td class="alnRht">&nbsp;</td>
					<td class="alnRht">&nbsp;</td>
					<s:if test="shipmentPrintMap['checkIncomingOrSorting']=='pack2'">
					<td class="alnRht">&nbsp;</td>
					<td class="alnRht">&nbsp;</td>
					<td class="alnRht">&nbsp;</td>
					<!-- <td class="alnRht">&nbsp;</td> -->
					</s:if>
					<s:if test="shipmentPrintMap['checkIncomingOrSorting']=='ship1'">
					<td class="alnRht">&nbsp;</td>
					</s:if>
					
					<td class="alnRht">&nbsp;</td>
				
					<td class="fontBold"><s:text name="print.total" /></td>
					<td class="alnRht"  style="text-align: right;"><s:property
						value="shipmentPrintMap['totalInfo'].netWeight" /></td>	
				<s:if test="shipmentPrintMap['checkIncomingOrSorting']=='ship1'">
					<td class="alnRht">&nbsp;</td>
					<td class="alnRht">&nbsp;</td>
				</s:if>	
				<s:if test="shipmentPrintMap['checkIncomingOrSorting']=='pack2'">
					<!-- <td class="alnRht">&nbsp;</td> -->
					<td class="alnRht"  style="text-align: right;"><s:property
						value="shipmentPrintMap['totalInfo'].rejectedWTSum" /></td>	
					<td class="alnRht">&nbsp;</td>
					<td class="alnRht">&nbsp;</td>
				</s:if>	
						
				</tr>
		   </s:if>
			<s:else>
				<tr>
					<td colspan="8" align="center"><s:text
						name="noRecordsFoundForPrint" /></td>
				</tr>
			</s:else> 
		</table>
					
                </div>
               
            </div>
        </div>
        <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='pack2'">
         <hr>
		<div class="container" style=" margin-bottom: 0px; margin-bottom: 1px;">
  			<div class="body-section">
            	<div class="row" style="display: flex;flex-wrap: wrap;">
               		<%--  <div class="col-6" style=" width: 38%;flex: 0 0 auto;padding-right:5px;">
                    	<h2 class="heading" style=" margin-bottom: 2px; margin-top: 5px;"><b>Traceability	</b></h2>
						<hr>
                    	<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Lot Number: &nbsp;<b><s:property value="shipmentPrintMap['batchNo']" />&nbsp;</b></p>
                	</div> --%>
                <s:if test='shipmentPrintMap.qrMapList.size()>0'>
					<s:iterator value="shipmentPrintMap.qrMapList" var="gListMap">
                		<div class="col-6">
                			<img src="<s:property value="QrCode" />" style="width: 150px;height: 150px;">
                		</div>
                	</s:iterator>
                </s:if>
            </div>
        </div>
        </div>
        </s:if>
        <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='sort0'">
         <hr>
		<div class="container" style=" margin-bottom: 0px; margin-bottom: 1px;">
  <div class="body-section">
            <div class="row" style="display: flex;flex-wrap: wrap;">
                <div class="col-6" style=" width: 38%;flex: 0 0 auto;padding-right:5px;">
                  <%-- <h2 class="heading" style=" margin-bottom: 2px; margin-top: 5px;"><b>Traceability	</b></h2> -->
					 <hr>
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">ID: &nbsp;<b><s:property value="shipmentPrintMap['batchNo']" />&nbsp;</b></p> --%>
                </div>
                <div class="col-6"  style=" width: 48%;flex: 0 0 auto;margin-bottom: 2px; margin-top: 6px;margin-left: 15px;">
                <img src="<s:property 
                value="shipmentPrintMap['qr']" />" style="width: 150px;height: 150px;">
                </div>
            </div>
        </div>
        </div>
        </s:if>
        <s:if test="shipmentPrintMap['checkIncomingOrSorting']=='ship1'">
         <hr>
		<div class="container" style=" margin-bottom: 0px; margin-bottom: 1px;">
  <div class="body-section">
            <div class="row" style="display: flex;flex-wrap: wrap;">
            <s:if test='shipmentPrintMap.qrMapList.size()>0'>
					<s:iterator value="shipmentPrintMap.qrMapList" var="gListMap">
                		<div class="col-6">
                			<img src="<s:property value="QrCode" />" style="width: 150px;height: 150px;">
                		</div>
                	</s:iterator>
                </s:if>
            </div>
        </div>
        </div>
        </s:if>
        
        </div>
        
<div id="prntCtrl"><input type="button"
	value="<s:text name="printBtn"/>" onclick="printReceipt();" />
	<input type="button"
	value="<s:text name="closeBtn"/>" onclick="closeWindow();" /></div>
</body>
</html>