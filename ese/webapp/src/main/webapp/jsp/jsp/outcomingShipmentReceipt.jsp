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
			<s:text
					name="printTitleoutcomingshipment" />
				</td>
				
				<td>&nbsp;</td>
			</tr>
			
		</table>
		</td>
		
	</tr>	
		
</table>
<hr>
<table border="0" width="100%" class="fstTbl" cellspacing="0"  style=" margin-bottom: 0px;">

	<tr class="rNoRow">
	
		<td><p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Export License No:  &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['agentId']" /></b>&nbsp; </p></td>
			</tr>
	
</table>
<div class="container" style=" margin-bottom: 0px; margin-bottom: 1px;">
  <div class="body-section">
            <div class="row" style="display: flex;flex-wrap: wrap;">
              <div class="col-6"  style=" width: 60%;flex: 0 0 auto;">
                 <h2 class="heading" style=" margin-bottom: 2px; margin-top: 5px;"><b><s:text
					name="Receiptorddet" />		</b></h2>
                   <%--  <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Export License No:  &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['agentId']" /></b>&nbsp; </p> --%>
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Buyer:   &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['agentName']" /></b>&nbsp;</p>
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Shipment Destination:   &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['shipmentDestination']" /></b>&nbsp;</p>
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Address:   &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['buyeraddress']" /></b>&nbsp;</p>
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Mobile No:   &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['buyermobile']" /></b>&nbsp; </p>
                </div>
                <div class="col-6">
                    <h2 class="heading" style=" margin-bottom: 2px; margin-top: 5px;"><b><s:text
					name="Receiptcustdet" />	</b></h2>
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Shipment Date: &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['date']" />&nbsp;</b></p>
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Packhouse: &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['recNo']" />&nbsp;</b>
			<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Exporter Address: &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['truckId']" />&nbsp;</b>
			<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Exporter Mobile No: &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['driverName']" />&nbsp;</b> </p>
			<p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">Trace Code: &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['traceCode']" />&nbsp;</b> </p>
                </div>
              
            </div>
        </div>
        </div>
      
        <table border="0" width="100%" class="fstTbl" cellspacing="0">

	<tr class="rNoRow">
	
		<td>
		 <h2 class="heading" style=" margin-bottom: 0px; margin-top: 1px;"><b><s:text
					name="Receiptproddet" /></b></h2></td>
			</tr>
	
</table>
<hr>

		
		   <div class="container">
             <div class="body-section">
            <div class="row" style="display: flex;flex-wrap: wrap;">
                <div class="col-6" style=" width: 100%;flex: 0 0 auto;">
                    <%-- <h2 class="heading" style=" margin-bottom: 0px; margin-top: 1px;"><b><s:text
					name="report.traceability" />	</b></h2>
					<hr> --%>
					<table width="100%" border="1" class="subTable" cellpadding="0"
			cellspacing="0">
			<tr class="grayHeader">
				<th><s:text name="lotNo" /></th>
				<th><s:property value="%{getLocaleProperty('shipment.block')}" /></th>
				<th><s:property value="%{getLocaleProperty('shipment.planting')}" /></th>
				<th><s:property value="%{getLocaleProperty('shipment.product')}" /></th>
				<th><s:property value="%{getLocaleProperty('shipment.variety')}" /></th>
				<th><s:property value="%{getLocaleProperty('shipment.packingUnit')}" /></th>
				<th><s:property value="%{getLocaleProperty('shipment.packingQty')}" /></th>			
			</tr>
			<s:if test='outcomeShipmentPrintMap.productMapList.size()>0'>
				<s:iterator value="outcomeShipmentPrintMap.productMapList" var="gListMap">
					<tr>
						<td style="word-break: break-all;"><s:property value="batchno" /></td>
						<td style="word-break: break-all;"><s:property value="block" /></td>
						<td style="word-break: break-all;"><s:property value="planting" /></td>
						<td style="word-break: break-all;"><s:property value="product" /></td>
						<td class="alnRht"><s:property value="grade" /></td>
						<td class="alnRht"><s:property value="packunit" /></td>
						<td class="alnRht" style="text-align: right;"><s:property value="netWeight" /></td>			
					</tr>
				</s:iterator>
				<s:iterator status="stat" value="(0-outcomeShipmentPrintMap.productMapList.size()).{ #this }" >
				   <tr>
						<td style="word-break: break-all;">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>
						<td class="alnRht">&nbsp;</td>	
						<td class="alnRht" >&nbsp;</td>	
						<td class="alnRht" >&nbsp;</td>					
					</tr>
				</s:iterator>
				<tr class="grayHeader">
					<td class="alnRht">&nbsp;</td>
					<td class="alnRht">&nbsp;</td>
					<td class="alnRht">&nbsp;</td>
					<td class="alnRht">&nbsp;</td>
					<td class="alnRht" >&nbsp;</td>		
					<td class="fontBold"><s:text name="print.total" /></td>
					<td class="alnRht" style="text-align: right;"><s:property
						value="outcomeShipmentPrintMap['totalInfo'].netWeight" /></td>				
				</tr>
			</s:if>
			<s:else>
				<tr>
					<td colspan="6" align="center"><s:text
						name="noRecordsFoundForPrint" /></td>
				</tr>
			</s:else>
		</table>
					
                </div>
               
            </div>
        </div>
        </div>
        
        <hr>
		<div class="container" style=" margin-bottom: 0px; margin-bottom: 1px;">
  <div class="body-section">
            <div class="row" style="display: flex;flex-wrap: wrap;">
                <div class="col-6" style=" width: 48%;flex: 0 0 auto;padding-right:5px;">
                    <h2 class="heading" style=" margin-bottom: 2px; margin-top: 5px;"><b>Traceability	</b></h2>
					<hr>
                    <p class="sub-heading" style="margin-bottom: 05px;margin-top: 05px;">UCR Kentrade: &nbsp;<b><s:property
			value="outcomeShipmentPrintMap['consigno']" />&nbsp;</b></p>
                </div>
                <div class="col-6"  style=" width: 48%;flex: 0 0 auto;margin-bottom: 2px; margin-top: 6px;margin-left: 15px;">
                
                <img src="<s:property
			value="outcomeShipmentPrintMap['qr']" />" style="width: 150px;height: 150px;">
                    <!-- <img align="middle" border="0" src="dist/assets/images/qr-code.png"> -->
                </div>
            </div>
        </div>
        </div>
        
        <hr>
        
          

</div>
<%-- <div align="right"><s:text name="print.receiptDate" />&nbsp;<s:date
	name="%{new java.util.Date()}" format="%{getGeneralDateFormat().concat(' HH:mm:ss')}" /></div> --%>
<div id="prntCtrl"><input type="button"
	value="<s:text name="printBtn"/>" onclick="printReceipt();" />
	<input type="button"
	value="<s:text name="closeBtn"/>" onclick="closeWindow();" /></div>
</body>
</html>