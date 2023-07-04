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

<script
	src="https://maps.googleapis.com/maps/api/js?client=gme-sourcetrace&v=3.28&libraries=geometry,drawing,places"></script>

<script src="https://cdn.ckeditor.com/4.10.1/standard/ckeditor.js"></script>

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
<script
	src="plugins/DataTables-1.10.18/Buttons-1.5.4/js/buttons.flash.min.js"></script>
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
<link rel="stylesheet" href="plugins/select2/select2.min.css">
<script src="plugins/select2/select2.min.js"></script>
<link rel="stylesheet"
	href="plugins/bootstrap-datetimepicker/css/datetimepicker.css">
<script type="text/javascript"
	src="plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script src="plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<link rel="stylesheet"
	href="plugins/bootstrap-daterangepicker/daterangepicker-bs3.css">
<script
	src="https://cdn.datatables.net/select/1.3.3/js/dataTables.select.min.js"
	type="text/javascript"></script>
<script
	src="https://cdn.datatables.net/plug-ins/1.11.5/api/fnFilterClear.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<style type="text/css">
.nowrapt {
	word-wrap: break-word;
}

a:link {
	text-decoration: underline;
	color: #009456
}

.text-wrap {
	white-space: normal;
}

.width-200 {
	width: 200px;
}
.font25 {
font-size: 25px!important;
}

</style>

<div id="audVidMedia"></div>

<button type="button" id="enableDetailPopup"
	class="hide slide_open btn btn-sm btn-success" data-toggle="modal"
	data-target="#slideDetailModal" data-backdrop="static"
	data-keyboard="false">
	<i class="fa fa-plus" aria-hidden="true"></i>
</button>
<div id="slideDetailModal" class="modal fade" role="dialog" >
	<div class="modal-dialog modal-lg">
		<!-- Modal content-->
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" id="detail-close-data-btn" class="close hide"
					data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="detailDataTitle"></h4>
			</div>
			<div class="modal-body" style="overflow: auto;">
				<table class="table ">
					<thead>
					</thead>
					<tbody id="detailDataHead" style="font-weight: bold;background-color:#fff">
					</tbody>
					<tbody id="detailDataBody">
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"
					id="detail-close-data-btn"
					onclick="buttonDataCancel('detail-close-data-btn')">
					<s:text name="close" />
				</button>
			</div>
		</div>

	</div>

</div>

<script type="text/javascript">
	var min = "";
			var max = "";
			var dataInd = "";
			var selectedIds="";
var url =<%out.print("'" + request.getParameter("url") + "'");%>;
var csvFile='<s:property value="dynamicReportConfig.csvFile"/>';
if(url=='' || url==null || url=='null'){
	url ='dynamicViewReportDT_';
}
var layoutType = <%out.print("'" + request.getParameter("layoutType") + "'");%>;
if(layoutType=='' || layoutType==null || layoutType=='null'){
	layoutType ='swithlayout';
}
var redirectCont =window.location.href;
	$.jgrid.defaults.responsive = true;
	$.jgrid.defaults.styleUI = 'Bootstrap';

	$(function() {
		$('.menuToggle').on('click', function() {
			resizeJqGridWidth("detail", "baseDiv");
		});
		var filters = '<s:property value="filterSize"/>';
	});
	var windowHeight = "";
	var reportWindowHeight = "";
	var postdata = '<s:property value="postdata"/>';
	var filterda = '';
	jQuery.fn.dataTable.Api.register('sum()', function() {
		return this.flatten().reduce(function(a, b) {
			if (typeof a === 'string') {
				a = a.replace(/[^\d.-]/g, '') * 1;
			}
			if (typeof b === 'string') {
				b = b.replace(/[^\d.-]/g, '') * 1;
			}

			return a + b;
		}, 0);
	});

	$.fn.dataTable.ext.search.push(
	          function (settings, data, dataIndex) {
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
	  	  });

	jQuery(document).ready(
			function() {
				
				Date.prototype.getWeek = function() {	
			        var onejan = new Date(this.getFullYear(), 0, 1);	
			        return Math.ceil((((this - onejan) / 86400000) + onejan.getDay() + 1) / 7);	
			    }

				jQuery("#minus").click(function() {
					try {
						var flag = "edit";
						var index = jQuery("#fieldl").val();
						jQuery("." + index).addClass("hide");
						jQuery("." + index + "> select").val("");
						reloadGrid(flag);
						jQuery("#filter-fields").remove(jQuery("." + index));
						jQuery("#fieldl").val("");
						resetReportFilter();
					} catch (e) {
						console.log(e);
					}
				});

				jQuery("#plus").click(function() {
					var index = jQuery("#fieldl").val();
					if (index != "") {
						jQuery(".well").show();
						jQuery("." + index).removeClass("hide");
						jQuery("#searchbtn").append(jQuery("." + index));
						jQuery("#fieldl").val("");
					}
				});
				windowHeight = ($(window).innerHeight() - 260);
				reportWindowHeight = ($(window).innerHeight() - 320);

				if (postdata != null && postdata != 'null'
						&& !isEmpty(postdata)) {
					postdata = postdata.replace(/&quot;/g, '"');
					postdata = postdata.replace("\"{", "{");
					postdata = postdata.replace("}\"", "}");
					filterda = postdata;
					postdata = postdata == null || postdata == 'null' ? ''
							: JSON.stringify(postdata);

				}
				$.fn.dataTable.ext.buttons.xlExport = {
					      text: '',
					  	className: "buttonsToHide fa fa-file-excel-o font25", 
					        action: function ( e, dt, node, config ) {
					        	exportXLS();
					      },
					      exportOptions: {
					            columns: ':not(.noexp)'
					        }
					    };

				$.fn.dataTable.ext.buttons.xlPDF = {
					      text: '',
					  	className: "buttonsToHide fa fa-file-pdf-o font25", 
					        action: function ( e, dt, node, config ) {
					        	exportPDF();
					      }
					    };
			 	
				
				$.fn.dataTable.ext.buttons.xlCSV = {
					      text: 'Generate',
					  	className: "buttonsToHide fa fa-file-pdf-o font25", 
					        action: function ( e, dt, node, config ) {
					        	generateCSV();
					      }
					    };
			
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
	
	function getSubBranchValues(selectedBranch) {	
		jQuery.post("customer_populateChildBranch.action", {	
			selectedBranch : selectedBranch	
		}, function(result) {	
			jQuery("#gs_subBranchId").empty();	
			insertOptions("gs_subBranchId", $.parseJSON(result));	
			$("#gs_subBranchId").trigger("change");	
		});	
	}

	function insertOptions(ctrlName, jsonArr) {	
		document.getElementById(ctrlName).length = 0;	
		addOption(document.getElementById(ctrlName), "Select", "");	
		for (var i = 0; i < jsonArr.length; i++) {	
			addOption(document.getElementById(ctrlName), jsonArr[i].name,	
					jsonArr[i].id);	
		}	
		$('#'+ctrlName+' option:eq(0)').prop('selected',true).select2();
	}

	function addOption(selectbox, text, value) {
		var optn = document.createElement("OPTION");
		optn.text = text;
		optn.value = value;
		selectbox.options.add(optn);
	}
	function retainFields() {
		if (postdata != '') {

			var jsonobj = JSON.parse(filterda);
			jQuery("#detail").setGridParam({
				postData : jsonobj
			});
			$(jsonobj).each(function(k, v) {
				if (v.filters !== undefined) {
					$(v.filters.rules).each(function(k, v2) {
						var temp = v2.field;
						if (v2.op == 'eq') {
							$("select[name='" + temp + "']").val(v2.data);
						} else {
							$("input[name='" + temp + "']").val(v2.data);
						}
					});
				}
			});
			$('#detail').setGridParam({
				postData : jsonobj
			});
			$('#detail')[0].triggerToolbar();
		}
	}
	function postDataSubmit() {
		var postdata = JSON.stringify($('#detail').getGridParam('postData'));
		postdata = postdata.replace(/&quot;/g, '"');
		postdata = postdata.replace("\"{", "{");
		postdata = postdata.replace("}\"", "}");
		document.updateform.postdata.value = postdata;
	}
function createDropdown(i,valsi){
	
	  var select = $('<select/>').attr({
			class : "dropDown form-control select2 selectClass",
			'data-index':i
		});

		var option = "<option value=''>Select </option>";

		$(select).append(option);
		if(!isJson(valsi)){
		 valsi= JSON.parse(valsi);
		}
		 for (var key in valsi) {
			    if (valsi.hasOwnProperty(key)) {
			      var val = valsi[key];
			      $(select).append( "<option value='"+key+"'>"+val+" </option>");
			    }
			  }
		 return select;
}

function isJson(object){
	var objectConstructor = ({}).constructor;
	 if (object.constructor === objectConstructor) {
	        return true;
	    }
	 
	 return false;
}
	function createDataTable(datatableid, urlPost,postdata, filters,btns) {
		$('#' + datatableid).addClass("table");
		var url1=urlPost;
		 var title = $(this).text();

	if(urlPost.includes("?")){
			urlPost =urlPost+"&layoutType="+layoutType+"&redirectContent="+encodeURI(redirectCont)
		}else{
			urlPost =urlPost+"?layoutType="+layoutType+"&redirectContent="+encodeURI(redirectCont)
		}
	
			var colnames = [];
			$('#' + datatableid + ' #headerrow .hd').each(function(i) {
				var bj = {};
			bj['data'] = $(this).attr("id");
			bj['sWidth'] = $(this).width();
			bj['className']='nowrapt ' + ($(this).attr("data-columns")!=undefined ? $(this).attr("data-columns") :"") ;
			colnames.push(bj);
		});
			
		
		
		var table = $('#' + datatableid).removeAttr('width').DataTable({
			"ajax" : {
				"url" :urlPost,
				"type" : "POST",
				"dataSrc" : function(json) {
					return json.data;
					
				},"data" : postdata
			},
			"columns" : colnames,
			 select: false,
			
			initComplete : function() {
				
			},
			paging : true,
			"pageLength": 10,
			searching : true,
			fixedHeader: true,
			bAutoWidth: false , 
			"bAutoWidth" : false,
			"autoWidth": false,
			"responsive": true,
			dom : 'Bfltip',
			fixedColumns : true,
			/*   select: {
		            style:    'os',
		            selector: 'td:not(.noexp)'
		        }, */
			"bSort": true,
			buttons : [	{
															extend : 'excelHtml5',
															filename :$('.breadCrumbNavigation li').last().text()==null || $('.breadCrumbNavigation li').last().text()=='' ? $('.reportName').text() :$('.breadCrumbNavigation li').last().text(),
															text: '<i class="fa fa-file-excel-o" style="font-size: 25px;"></i>',
															titleAttr: 'Export to XLS',
															className:'export',
															title : $('.breadCrumbNavigation li').last().text()==null || $('.breadCrumbNavigation li').last().text()=='' ? $('.reportName').text() :$('.breadCrumbNavigation li').last().text(),
															init: function(api, node, config) {
															       $(node).removeClass('dt-button buttons-excel buttons-html5')
															    },
															 customizeData: function ( data ) {
																 var header=[];
																 $('#'+datatableid+' thead tr:eq(1) th:not(.noexp)').each( function (i) {
																	 header.push($(this).clone().children().remove().end().text());
																 });
																 data.header = header;
															 }, 
															 exportOptions: {
																columns: "thead th:not(.noexp)",
										                    }  
														},
														{
															extend : 'print',
															className:'print',
															filename :$('.breadCrumbNavigation li').last().text()==null || $('.breadCrumbNavigation li').last().text()=='' ? $('.reportName').text() :$('.breadCrumbNavigation li').last().text(),
																			text: '<i class="fa fa-print" style="font-size: 25px;"></i>',
															titleAttr: 'Print',
															title : $('.breadCrumbNavigation li').last().text()==null || $('.breadCrumbNavigation li').last().text()=='' ? $('.reportName').text() :$('.breadCrumbNavigation li').last().text(),
															init: function(api, node, config) {
															       $(node).removeClass('dt-button buttons-print buttonsToHide')
															    },
															exportOptions: {
																columns: "thead th:not(.details-control):not(.noexp)",
																rows: ':not(.filterTd)'
										                    },customize: function ( win ) {
										                    	var tr = $('#'+datatableid+' thead tr:eq(1)').clone();
										                    	 $(this).find('th#search-header').remove();
										                    	 $(this).find('th.noexp').remove();
										                    	  $(tr).find('th:not(.details-control)' ).each( function (i) {
										                    		
										                    		  $(this).find('th.noexp').remove();
										                    	
										                    	 });
										                    	 return win;
														 },
														}
														],
		});
		formFilters(datatableid,filters,false,table);
	
			
		
		$('.select2').select2();
		 if(btns!=null && btns!=''){
			$(btns.split(",")).each(
					function(k, val) {
						$('#'+datatableid+"Btn").append($('#'+val));
					});
		} 
		 $('#'+datatableid+"Btn").addClass("button-group-container");
		$('#'+datatableid+"Btn").append($('#'+datatableid+"_wrapper").find('.dt-buttons'));
		$('#'+datatableid+' tbody').off( 'click', 'tr');
		$('.dataTables_filter').remove();
		return table;
	}
	
	function containsAny(source,target)
	{
	    var result = source.filter(function(item){ return target.indexOf(item) > -1});   
	    return result[0];  
	}   
	
	function popupDocUploads(ids) {
		
		try{
			var str_array = ids.split(',');
			$("#mImagebody").empty();
			
			var mbody="";
		
			for(var i = 0; i < str_array.length; i++){
				
			
				if(i==0){
					mbody="";
					mbody="<div class='item active'>";
					mbody+='<img src="generalPop_populateFile.action?id='+str_array[i]+'"/>';
					mbody+="</div>";
				}else{
					mbody="";
					mbody="<div class='item'>";
					mbody+='<img src="generalPop_populateFile.action?id='+str_array[i]+'"/>';
					mbody+="</div>";
				}
			
				$("#mImagebody").append(mbody);
			
			 }
			
			document.getElementById("enableImageModal").click();	
		}
		catch(e){
			alert(e);
			}
		
	}
	

	function playAudio(ids) {	
		
		try{	
			var str_array = ids.split(',');	
			$("#audVidMedia").empty();	
				
			var mbody="";	
		
			for(var i = 0; i < str_array.length; i++){	
				$("#audVidMedia").append('<audio controls> <source src="generalPop_populateFile.action?id='+str_array[i]+'"type="audio/mpeg"></audio> ');	
				
			 }	
				
			$("#audVidMedia").removeClass("hide");	
				
			$("#audVidMedia").dialog({	
                autoOpen: true,	
                resizable: true,	
                width:"500",	
                height:150,	
                modal: true	
            });	
       	
		}	
		catch(e){	
			alert(e);	
			}	
			
	}	
		
function playVideo(ids) {	
try{	
var str_array = ids.split(',');	
$("#audVidMedia").empty();	
var mbody="";	
for(var i = 0; i < str_array.length; i++){	
	$("#audVidMedia").append('<video width="750" height="350" controls> <source src="generalPop_populateFile.action?id='+str_array[i]+'"type="video/mp4"></video> ');	
 }	
$("#audVidMedia").removeClass("hide");	
$("#audVidMedia").dialog({	
    autoOpen: true,	
    resizable: true,	
    width:"800",	
    height:450,	
    modal: true	
});	
}	
catch(e){	
alert(e);	
}	
}

function downloadFile(img) {
	 var req = new XMLHttpRequest();
		
    req.open("GET", "generalPop_downloadImage.action?id="+ typez + "&pdfData="+img, true);
    req.responseType = "blob";
    req.onload = function (event) {
        var blob = req.response;
        var fileName = req.getResponseHeader("fileName") ;
        var link=document.createElement('a');
        link.href=window.URL.createObjectURL(blob);
        link.download=fileName;
        link.click();
    };

    req.send();
	

}
function isNumber(evt) {

	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}

function isDecimal(evt) {
	
	 evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46) {
	        return false;
	    }
	    $('input').bind('keypress', function (event) {
	    	var regex = new RegExp("^\d*(\.\d{0,2})?$");
	    	var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    	if (!regex.test(key)) {
	    	   event.preventDefault();
	    	   return false;
	    	}
	    return true;
});}


function validateImage(idd,fileExtension) {	
	var files = document.getElementById(idd).files;	
	var result=true;	
		
	for (var i = 0; i < files.length; i++) {	
		var file = files[i];	
		 var filename = file.name;	
			var fileExt = filename.split('.').pop();// THE NAME OF THE FILE.	
            var fsize = file.size;  	
          	
            if (file != undefined) {	
		 		var s="#"+idd ;	
		 		/* if(idDoc=='0'){	
		 				
		 				
		 		} */	
		 		if ($.inArray(fileExt.toLowerCase(), fileExtension)>=0 ){	
				if (file.size > 5000000) {	
					alert('File size exceeds');	
					$(s).replaceWith($(s).val('').clone(true));	
					hit = false;	
					enableButton();	
					result=false;	
						
				}	
			} else {	
				result=false;	
				alert('Invalid File Extension')	
				$(s).replaceWith($(s).val('').clone(true));	
				file.focus();	
			}	
			}	
			
		
	}	
		
		
 		
	return result;	
} 


function onCancel(){
	window.location.href='<s:property value="redirectContent" />';
}


function listState(obj, countryId, stateId, disId, cityId, gpId, vId) {

	clearElement(stateId,true);
	if (!isEmpty(obj.value)) {
		var selectedCountry = $('#' + countryId).val();
		jQuery.post(url+"populateState.action", {
			selectedCountry : obj.value
		}, function(result) {
			insertOptions(stateId, $.parseJSON(result));			
			if('<s:property value="selectedState"/>'!=null && '<s:property value="selectedState"/>'!=''){
				if(obj.value == '<s:property value="selectedCountry"/>'){
				$('#'+stateId).val('<s:property value="selectedState"/>').select2();
				}
			}
			listLocality(document.getElementById(stateId), stateId, disId,
					cityId, gpId, vId);
		});
		$('#' + disId).select2();
	}
}
function listLocality(obj, stateId, disId, cityId, gpId, vId) {

	clearElement(disId,true);
	if (!isEmpty(obj.value)) {
		var selectedState = $('#' + stateId).val();
		jQuery.post(url+"populateLocality.action", {
			id1 : obj.value,
			dt : new Date(),
			selectedState : obj.value
		}, function(result) {
			insertOptions(disId, $.parseJSON(result));
			if('<s:property value="selectedLocality"/>'!=null && '<s:property value="selectedLocality"/>'!=''){
				if(obj.value == '<s:property value="selectedState"/>'){
				$('#'+disId).val('<s:property value="selectedLocality"/>').select2();
				}
			}
				listMunicipalities(document.getElementById(disId), disId, cityId,gpId, vId);
		});
		$('#' + cityId).select2();
	}
}

function listMunicipalities(obj,disId, cityId, gpId, vId) {
	clearElement(cityId,true);	
	if (!isEmpty(obj.value)) {
		var selectedLocality = $('#' + disId).val();
		jQuery.post(url+"populateMunicipality.action", {
			id1 : obj.value,
			dt : new Date(),
			selectedLocality : obj.value
		}, function(result) {
			insertOptions(cityId, $.parseJSON(result));
			
			if('<s:property value="selectedCity"/>'!=null && '<s:property value="selectedCity"/>'!=''){
				if(obj.value == '<s:property value="selectedLocality"/>'){
				$('#'+cityId).val('<s:property value="selectedCity"/>').select2();
				}
			}
			listVillage(document.getElementById(cityId), cityId, vId);
		});
		$('#' + vId).select2();
	}
}
function listMunicipality(obj,disId, cityId, gpId, vId) {
	clearElement(cityId,true);
	if (!isEmpty(obj.value)) {
		var selectedLocality = $('#' + disId).val();
		jQuery.post(url+"populateMunicipality.action", {
			id1 : obj.value,
			dt : new Date(),
			selectedLocality : obj.value
		}, function(result) {
			insertOptions(cityId, $.parseJSON(result));
			
			if('<s:property value="selectedCity"/>'!=null && '<s:property value="selectedCity"/>'!=''){
				if(obj.value == '<s:property value="selectedLocality"/>'){
				$('#'+cityId).val('<s:property value="selectedCity"/>').select2();
				}
			}
			listVillage(document.getElementById(cityId), cityId, vId);
		});
		$('#' + vId).select2();
	}
}
function listVillage(obj, CityId, vId) {
	clearElement(vId,true);
		if (!isEmpty(obj.value)) {
		var selectedCity = $('#' + CityId).val();
		jQuery.post(url+"populateVillage.action", {
			id1 : obj.value,
			dt : new Date(),
			selectedCity : obj.value
		}, function(result) {
			insertOptions(vId, $.parseJSON(result));
			if('<s:property value="selectedVillage"/>'!=null && '<s:property value="selectedVillage"/>'!=''){
				if(obj.value == '<s:property value="selectedCity"/>'){
					
				$('#'+vId).val('<s:property value="selectedVillage"/>').select2().change();
				
				}
			}
			
		});
	
	}
}

function isDecimal1(evt,element) {
	 var charCode = (evt.which) ? evt.which : event.keyCode;
	 var vardec =0;
	 var varLenh =$(element).val().split('.')[0].length;
	 if($(element).val().indexOf(".")>=0){
		 vardec =$(element).val().split('.')[1].length;
	 }
		        if (
		              // Check minus and only once.
		            (charCode != 46 ||  $(element).val().indexOf('.') != -1  ) &&    // Check for dots and only once.
		            (charCode < 48 || charCode > 57)){
		        	return false;
		        	
		        }else if(charCode != 46){
		        	varLenh=varLenh+1;
		        	vardec=vardec+1;
		        }
	 if((varLenh > 7 && $(element).val().indexOf(".")<0) || ($(element).val().indexOf(".")>=0 &&  vardec > 2) ){
		 return false;
	 }
  return true;
	 
}  


function isDecimalWithDec(evt,element,number,decimal) {
	 var charCode = (evt.which) ? evt.which : event.keyCode;
	 var vardec =0;
	 var varLenh =$(element).val().split('.')[0].length;
	 if($(element).val().indexOf(".")>=0){
		 vardec =$(element).val().split('.')[1].length;
	 }
	
		        if (
		              // Check minus and only once.
		            (charCode != 46 ||  $(element).val().indexOf('.') != -1  ) &&    // Check for dots and only once.
		            (charCode < 48 || charCode > 57)){
		        	return false;
		        	
		        }else if(charCode != 46){
		        	varLenh=varLenh+1;
		        	vardec=vardec+1;
		        }
		        

	 if((varLenh > parseInt(number) && $(element).val().indexOf(".")<0) || ($(element).val().indexOf(".")>=0 &&  vardec >parseInt(decimal) ) ){
		 return false;
	 }
	 
	 
		            

		        return true;
	 
}  

function submitFormWithLoc(formId,url,lat,lon){
	 $(".loadingWrapper").css("display", "flex");
	
		 $("<input />").attr("type", "hidden").attr("name", "latitude").attr("value", lat).appendTo("#"+formId);
		 $("<input />").attr("type", "hidden").attr("name", "longitude").attr("value", lon).appendTo("#"+formId);
			var formm =  new FormData(document.getElementById(formId));
		$("#"+formId).find("input[type=file]").each(function(index, field){
			
			   const file = field.files[0];
				 formm.delete($(this).attr("name"));
			   if(file!=undefined)
					 formm.append($(this).attr("name"), file); 
			});
		$.ajax({
			 type: "POST",
	        async: false,
	        url: url+"populateValidate.action",
	         data: formm,
	         cache: false,
	         contentType: false,
	         processData: false,
	        success: function(result) {
	        	if(Object.keys(result).length === 0){
	        		
	        		$( '#'+formId ).attr('action',url+'<s:property value="command"/>');
	    			$('#'+formId).submit();
	        	
	        		
	        }else{
	        
	        	console.log(result);
	        	var arr ="";
	        	$.each(result, function(propName, propVal) {
	        		arr =arr+"</br> "+propVal;
	        		});
	        
	        	$("#validateError").html(arr);
	        	 $(".loadingWrapper").css("display", "none");
	        	 $("#sMuom").select2({disabled:'readonly'});
	        	 if($('input:radio[name="farm.isAddressSame"][value=1]').is(":checked")){
			    		$('.loc').select2({disabled:'readonly'});
			    	 }else{
			    		 $('.loc').select2({disabled:false});
			    	 }
	        	 return false; 
	        }
	        },
	        error: function(result) {
	     
	        }
		});
}
function validateAndSubmit(formId,url){
	  var err= $("#validateError").text("");
	 if(confirm('<s:property value="%{getLocaleProperty('submit.confirm')}" />')){
	   		if (navigator.geolocation) {
	   			
 		      navigator.geolocation.getCurrentPosition(
 		        (position) => {
 		      return  submitFormWithLoc(formId,url,position.coords.latitude,position.coords.longitude);
 		         },
 		        () => {
 		      	return submitFormWithLoc(formId,url);
 		        }
 		      );
 		    } else {
 		    	
 		    	return submitFormWithLoc(formId,url);
 		    }	
	   	 }else{
	   		 return false;
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
function getLocation() {
	
	  if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(
	            (position) => {
	            	$('#currLat').val(position.coords.latitude);
	            	$('#currLon').val(position.coords.longitude);
	            	$('#currLat').attr("readonly",true);
	            	$('#currLon').attr("readonly",true);
	            	console.log(position.coords.latitude);
	            });
	  } else {
	    alert("Geolocation is not supported by this browser.");
	  }
	}
function listFarmDetail(farmerVal) {
	var selectedFarmer = farmerVal;
	if(!isEmpty(selectedFarmer)){
		$.ajax({
			 type: "POST",
	        async: false,
	        url: "generalPop_populateFarm.action",
	        data: {selectedFarmer : selectedFarmer},
	        success: function(result) {
	        	insertOptions("farmId", $.parseJSON(result));
	        }
		});
	}
}
function listFarmsCrop(farm){
	var selectedFarm=farm;
	if(!isEmpty(selectedFarm)){
		$.ajax({
			 type: "POST",
	        async: false,
	        url: "generalPop_populateFarmCrops.action",
	        data: {selectedFarm : selectedFarm},
	        success: function(result) {
	        	insertOptions("cropName", $.parseJSON(result));
	        }
		});
		
	}
}
function listCropVarierty(crop){
	var selectedCrop = crop;
	if(!isEmpty(selectedCrop)){
		$.ajax({
			 type: "POST",
	        async: false,
	        url: "generalPop_populateCropVariety.action",
	        data: {selectedCrop : selectedCrop},
	        success: function(result) {
	        	insertOptions("varietyName", $.parseJSON(result));
	        }
		});
	}
}

function createDataTableServer(datatableid, urlPost,postdata, filters,btns) {
	$('#' + datatableid).addClass("table");
	if(url=='' ||url==null || url=='null'){
		url =urlPost.split("_")[0]+"_";
		
	}
	if(urlPost.includes("?")){
		urlPost =urlPost+"&layoutType="+layoutType+"&redirectContent="+redirectCont+"&url="+url
	}else{
		urlPost =urlPost+"?layoutType="+layoutType+"&redirectContent="+redirectCont+"&url="+url
	}
		var colnames = [];
		var columnDefs = [];
		$('#' + datatableid + ' #headerrow .hd').each(function(i) {
		var bj = {};
		var bjc = {};
		bj['data'] = $(this).attr("id");
		 if($(this).hasClass("dealer")){
		 bj['visible'] =false;
		 }else{
			 bj['visible'] =true;
		 }
		columnDefs.push(bjc);
		colnames.push(bj);
	});
	
		
 formFiltersNew(datatableid,filters,true);
 if(!isEmpty(csvFile)){
	var table = $('#' + datatableid).removeAttr('width').DataTable({
		"bPaginate": true,
		"bInfo": true,
		"iDisplayStart":0,
		"bProcessing" : true,
		"bServerSide" : true,
		"processing": true,
        "serverSide": true,
        "order": [],
      	 "ajax" : {
			"url" : urlPost,
			"type" : "POST",
			"data": function(d, settings){
		         var api = new $.fn.dataTable.Api(settings);

		         d.page = Math.min(
		            Math.max(0, Math.round(d.start / api.page.len())),
		            api.page.info().pages
		         )+1;
		         d.filterList =JSON.stringify(getPostData());
		         d['order'].forEach(function(items, index) {
		             d['order'][index]['column_name'] = d['columns'][items.column]['data'];
		             d.sidx =d['columns'][items.column]['data'];
		             d.sord =d['order'][index]['dir'];
		       });
		      },
			"dataSrc": function(json){
				return json.data;
			   
			},
		},
		"paging" : true,
			"pageLength": 10,
			 "responsive": true,
		language : {
			sLoadingRecords : '<span style="width:100%;" ><img align="center" src="img/ajax-loader.gif"></span>'
		},
		"aoColumnDefs":columnDefs,
		"columns" : colnames,
		 select: false,
		 autoWidth: false , 
		 bAutoWidth: false , 
		 dom : 'Bfrtip',
		 fixedColumns:   true,
		 "scrollX": true,
		 lengthMenu: [
		              [ 10, 25, 50, -1 ],
		              [ '10 rows', '25 rows', '50 rows', 'Show all' ]
		          ],
		          
		        	  buttons : [
			        	  "xlExport","xlPDF","xlCSV"]
		         
		          
	});
 }else{
		var table = $('#' + datatableid).removeAttr('width').DataTable({
			"bPaginate": true,
			"bInfo": true,
			"iDisplayStart":0,
			"bProcessing" : true,
			"bServerSide" : true,
			"processing": true,
	        "serverSide": true,
	        "order": [],
	      	 "ajax" : {
				"url" : urlPost,
				"type" : "POST",
				"data": function(d, settings){
			         var api = new $.fn.dataTable.Api(settings);

			         d.page = Math.min(
			            Math.max(0, Math.round(d.start / api.page.len())),
			            api.page.info().pages
			         )+1;
			         d.filterList =JSON.stringify(getPostData());
			         d['order'].forEach(function(items, index) {
			             d['order'][index]['column_name'] = d['columns'][items.column]['data'];
			             d.sidx =d['columns'][items.column]['data'];
			             d.sord =d['order'][index]['dir'];
			       });
			      },
				"dataSrc": function(json){
					return json.data;
				   
				},
			},
			"paging" : true,
				"pageLength": 10,
				 "responsive": true,
			language : {
				sLoadingRecords : '<span style="width:100%;" ><img align="center" src="img/ajax-loader.gif"></span>'
			},
			"aoColumnDefs":columnDefs,
			"columns" : colnames,
			 select: false,
			 autoWidth: false , 
			 bAutoWidth: false , 
			 dom : 'Bfrtip',
			 fixedColumns:   true,
			 "scrollX": true,
			 lengthMenu: [
			              [ 10, 25, 50, -1 ],
			              [ '10 rows', '25 rows', '50 rows', 'Show all' ]
			          ],
			          
			        	  buttons : [
				        	  "xlExport","xlPDF"]
			         
			          
		});
 }
 
	table.columns.adjust();
	$('.select2').select2();
	if(btns!=null && btns!=''){
		$(btns.split(",")).each(
				function(k, val) {
					$('#'+datatableid+"Btn").append($('#'+val));
				});
	}
	 $('#'+datatableid+"Btn").addClass("button-group-container");
	$('#'+datatableid+"Btn").append($('#'+datatableid+"_wrapper").find('.dt-buttons'));
	$('.buttonsToHide').removeClass("dt-button");
	$('#'+datatableid+' tbody').off( 'click', 'tr');
	$('.dataTables_filter').remove();
	return table;
}

function formFilters(datatableid,filters, isServerSideFilter,table){
	var new_row = $("<tr id='search-header'/>");
	$('#'+datatableid+' thead th').each(function(i) {
		var className=$(this).attr('class');
		var fieldnammm=$(this).attr('data-index');
		var title = $(this).text();
	 	var new_th = $('<th />');
		var tesst=$('<input class="filterclass form-control " type="text" placeholder="' + title + '" data-index="'+i+'" id="'+i+'"/>');
	    var closeBtn = $('<a title="Reset Search Value" style="padding-right: 0.3em;padding-left: 0.3em;color:white" class="clearsearchclass fa fa-times" name="'+i+'"></a>');
	    if($(this).attr("id")=='0'){
			tesst = $('<label><input type="checkbox" id="sall" />Select All</label>');
			 $(new_th).append(tesst);
		}else
  if(!$(this).hasClass("noexp")){
		  if (filters!=null && filters!='' && filters.hasOwnProperty($(this).attr("id")) &&filters[$(this).attr("id")]!=null && filters[$(this).attr("id")]!=undefined && filters[$(this).attr("id")]!='') {
					
	var opObj =filters[$(this).attr("id")];
	var type =opObj['type'];
	var name =opObj['field'];
	var dname =opObj['dtype'];
	if(type=='4'){
		
		$(tesst).daterangepicker({
    		dateFormat : 'DD-MM-YYYY',
    		format:'DD-MM-YYYY',
			startDate : document.getElementById("hiddenStartDate").value,
	        endDate : document.getElementById("hiddenEndDate").value,
	        placeholder:'Select '+title,
		      ranges: {
	           'Today': [moment(), moment()],
	           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
	           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
	           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
	           'This Month': [moment().startOf('month'), moment().endOf('month')],
	           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
	        }
	    },
          function (start, end) {
	    	if ( table.column(i).search() !== this.value ) {
	    		if(isServerSideFilter){
	    			filterColumns();
	    		}else{

	    		      $.fn.dataTable.ext.search.push(
	    		    function(settings, data, dataIndex) {
	    		      var min = start;
	    		      var max = end;
	    		    
	    		      var startDatsse =  moment(data[i], 'DD-MM-YYYY').format();
	    		      var startDate = new Date(startDatsse).getTime();;
	    		      if (min == null && max == null) {
	    		        return true;
	    		      }
	    		      if (min == null && startDate <= max) {
	    		        return true;
	    		      }
	    		      if (max == null && startDate >= min) {
	    		        return true;
	    		      }
	    		      if (startDate <= max && startDate >= min) {
	    		        return true;
	    		      }
	    		      return false;
	    		    }
	    		  ); //external search ends here

	    		  table.draw();
	    		  $.fn.dataTable.ext.search.pop();


	    		}
	    		
	    	
	    	}
	    	
          });
    	$(closeBtn).on( 'click', function () {
         	 $( this).parent().find('.filterclass ').val('').daterangepicker("refresh");
         	if(isServerSideFilter){
    			filterColumns();
    		}else{
    			
    			 table
	 			    .column( $(this).closest("th").index())
	 			    .search( this.value )
	 			    .draw();
    		
    		}
           
        } );
    	
    	
	}else if(type=='5' || type=='8'){
		 tesst =createDropdown(i,opObj);
		 $(tesst).on( 'change', function () {
		        
	            if ( table.column($(this).closest("th").index()).search() !== this.value ) {
	            	if(isServerSideFilter){
		    			filterColumns();
		    		}else{
		    			 table
			 			    .column( $(this).closest("th").index())
			 			    .search( this.value )
			 			    .draw();
		    		
		    		}
	            }
	        } );
    	  $(closeBtn).on( 'click', function () {
	        	 $( this).parent().find('.filterclass ').val('').select2();
	        	  if(isServerSideFilter){
		    			filterColumns();
		    		 }else{
		    			 
		    			 table
			 			    .column( $(this).closest("th").index())
			 			  .search( '' )
			 			    .draw();
		    		
		    		
		    		} 
	           
	        } );
    	   $(tesst).addClass("select2");
    	  $(tesst).select2(); 
	}else{
	    $(tesst).bind('kepup keydown keypress focus', function(e) {
    		  $(this).tooltip();
    	  });
        $(tesst).bind('keypress', function(e) {
        	 $(this).tooltip();
    		  if ( e.keyCode == 13  &&  table.column($(this).closest("th").index()).search() !== this.value) { 
    			  if(isServerSideFilter){
		    			filterColumns();
		    		}else{
		    			
		    			 
		    			 table
			 			    .column( $(this).closest("th").index())
			 			    .search( this.value )
			 			    .draw();
		    		
		    			
		    		}
    		  }
    		});
        $(closeBtn).on( 'click', function () {
        	 $( this).parent().find('.filterclass ').val('');
        	
        	 if(isServerSideFilter){
	    			filterColumns();
	    		}else{
	    			 
	    			 table
		 			    .column( $(this).closest("th").index())
		 			    .search( this.value )
		 			    .draw();
	    		}
        } );
	}
	$(tesst).attr("name",name);
	$(tesst).attr("id",name+"~"+dname); 
	$(tesst).addClass(type); 

	
	  }else{
		   $(tesst).bind('kepup keydown keypress focus', function(e) {
        		  $(this).tooltip();
        	  });
	        $(tesst).bind('keypress', function(e) {
	        	 $(this).tooltip();
        		  if ( e.keyCode == 13  &&  table.column($(this).closest("th").index()).search() !== this.value) { 
        			  if(isServerSideFilter){
			    			filterColumns();
			    		}else{
			    			 
			    			 table
				 			    .column( $(this).closest("th").index())
				 			    .search( this.value )
				 			    .draw();
			    		}
        		  }
        		});
	        $(closeBtn).on( 'click', function () {

	        	 $(this).find('.filterclass ').val('');
	        	var fltrName= $(this).attr("name");
	        	$("."+fltrName).val('');
	        	 if(isServerSideFilter){
	        		 filterColumns();
		    		}else{
		    			 
		    			 table
			 			    .column( $(this).closest("th").index())
			 			    .search( '' )
			 			    .draw();
		    		}
	           
	        } );
	    	$(tesst).attr("id",fieldnammm);    
	      
	  }
	  
	  var divv = $('<div />');
	  $(divv).css('display','flex');
	  $(divv).css('justify-content','space-between');
	  $(divv).append(tesst);
	  $(divv).append(closeBtn);
	  $(new_th).append(divv);
	
	  }
	  $(new_row).append(new_th);
	  
	});
	$('#'+datatableid+' thead').prepend(new_row);
}
function createDropdown(i,valsi){
	  var select = $('<select/>').attr({
			class : "filterclass dropDown form-control select2 selectClass " +valsi["type"] ,
			'data-index':i
		});

		var option = "<option value=''>Select </option>";
var optos;
		$(select).append(option);
		if(!isJson(valsi["options"])){
			optos= JSON.parse(valsi["options"]);
		}else{
			optos =valsi["options"];
		}
		 for (var key in optos) {
			    if (optos.hasOwnProperty(key)) {
			      var val = optos[key];
			      $(select).append( "<option value='"+key+"'>"+val+" </option>");
			    }
			  }
		 $(select).select2();
		 return select;
}
function getLocation() {
	if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(showPosition);
	  } else {
	    alert("Geolocation is not supported by this browser.");
	  }
	}
	function showPosition(position) {
		lat = position.coords.latitude;
	   lng = position.coords.longitude;		   
	} 
	

	function isAlphabet(evt) {

		evt = (evt) ? evt : window.event;
		var charCode = (evt.which) ? evt.which : evt.keyCode;
		if ((charCode >64 && charCode <90) || (charCode > 96 && charCode < 122)) {//A-Z  65-90  97-122
			return true;
		}
		return false;
	}
		
	function insertOptionswithoutselect(ctrlName, jsonArr) {
		var exis =$('#'+ctrlName).val();
		$('#'+ctrlName).val('').select2()
		document.getElementById(ctrlName).length = 0;
		for (var i = 0; i < jsonArr.length; i++) {
			addOption(document.getElementById(ctrlName), jsonArr[i].name,
					jsonArr[i].id);
		}
		$('#'+ctrlName).val(exis).select2();
		
	}
		
		
	function isDecimalPlaceTwo(evt){
		  
		  if ((evt.which != 46 || evt.value.indexOf('.') != -1) && (evt.which < 48 || evt.which > 57)) {
		        //event it's fine

		    }
		    var input = evt.value;
		    if ((input.indexOf('.') != -1) && (input.substring(input.indexOf('.')).length > 2)) {
		        return false;
		    }
		}
		
		

	function loadFarmer(village) {
			clearElement('farmer',true);
		if(!isEmpty(village) || $('#country').val()==''){
		var thisyrl='generalPop_';
		if(url!=null && url!=''){
			thisyrl =url;
		}
		var insType = 1;
		$.ajax({
			type : "POST",
			async : false,
			url : thisyrl+"populateFarmerByAuditRequest.action",
		     data: {inspectionType:insType,selectedVillage:village},
			success : function(result) {
				insertOptions("farmer", $.parseJSON(result));
				if('<s:property value="selectedFarmer"/>'!=null && '<s:property value="selectedFarmer"/>'!=''){
					if(village == '<s:property value="selectedVillage"/>'){
						
					$('#farmer').val('<s:property value="selectedFarmer"/>').select2().change();
					
					}
				}
			}
		});
		}
	}

	function clearFarmer(){
		clearElement('farmer');
		
	}


	function clearElement(id,ischange){
		 $('#'+id+' option:not(:first)').remove();
		 
		 if(ischange!=undefined && ischange){
			 $('#'+id+' option:eq(0)').prop('selected',true).select2().change();
		 }else{
			 $('#'+id+' option:eq(0)').prop('selected',true).select2();
		 }

		
	}

	function loadSprayFarmer() {
		clearElement('farmer',true);
		
		var thisyrl='generalPop_';
		if(url!=null && url!=''){
			thisyrl =url;
		}
		var insType = 1;
		$.ajax({
			type : "POST",
			async : false,
			url : thisyrl+"populateFarmerByAuditRequest.action",
			success : function(result) {
				insertOptions("farmer", $.parseJSON(result));
				
			}
		});
		
	}
	function formFiltersNew(datatableid,filters, isServerSideFilter){
		var new_row = $("<tr id='search-header'/>");
		$('#'+datatableid+' thead th').each(function(i) {
			var className=$(this).attr('class');
			var fieldnammm=$(this).attr('data-index');
			var title = $(this).text();
		 	var new_th = $('<th />');
			var tesst=$('<input class="filterclass form-control " type="text" placeholder="' + title + '" data-index="'+i+'" id="'+i+'"/>');
		    var closeBtn = $('<a title="Reset Search Value" style="padding-right: 0.3em;padding-left: 0.3em;color:white" class="clearsearchclass fa fa-times" name="'+i+'"></a>');
		    if($(this).attr("id")=='0'){
				tesst = $('<label><input type="checkbox" id="sall" />Select All</label>');
				 $(new_th).append(tesst);
			}else
	  if(!$(this).hasClass("noexp")){
			  if (filters!=null && filters!='' && filters.hasOwnProperty($(this).attr("id")) &&filters[$(this).attr("id")]!=null && filters[$(this).attr("id")]!=undefined && filters[$(this).attr("id")]!='') {
						
		var opObj =filters[$(this).attr("id")];
		var type =opObj['type'];
		var name =opObj['field'];
		var dname =opObj['dtype'];
		if(type=='4'){
			
			$(tesst).daterangepicker({
	    		dateFormat : 'DD-MM-YYYY',
	    		format:'DD-MM-YYYY',
				startDate : document.getElementById("hiddenStartDate").value,
		        endDate : document.getElementById("hiddenEndDate").value,
		        placeholder:'Select '+title,
			      ranges: {
		           'Today': [moment(), moment()],
		           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
		           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
		           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
		           'This Month': [moment().startOf('month'), moment().endOf('month')],
		           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
		        }
		    },
	          function (start, end) {
		    	if ( table.column(i).search() !== this.value ) {
		    		if(isServerSideFilter){
		    			filterColumns();
		    		}else{

		    		      $.fn.dataTable.ext.search.push(
		    		    function(settings, data, dataIndex) {
		    		      var min = start;
		    		      var max = end;
		    		    
		    		      var startDatsse =  moment(data[i], 'DD-MM-YYYY').format();
		    		      var startDate = new Date(startDatsse).getTime();;
		    		      if (min == null && max == null) {
		    		        return true;
		    		      }
		    		      if (min == null && startDate <= max) {
		    		        return true;
		    		      }
		    		      if (max == null && startDate >= min) {
		    		        return true;
		    		      }
		    		      if (startDate <= max && startDate >= min) {
		    		        return true;
		    		      }
		    		      return false;
		    		    }
		    		  ); //external search ends here

		    		  table.draw();
		    		  $.fn.dataTable.ext.search.pop();


		    		}
		    		
		    	
		    	}
		    	
	          });
	    	$(closeBtn).on( 'click', function () {
	         	 $( this).parent().find('.filterclass ').val('').daterangepicker("refresh");
	         	if(isServerSideFilter){
	    			filterColumns();
	    		}else{
	    			 table
	 			    .column( $(this).data('index')+1 )
	 			    .search( this.value )
	 			    .draw();
	    		}
	           
	        } );
	    	
	    	
		}else if(type=='5' || type=='8'){
			 tesst =createDropdown(i,opObj);
			 $(tesst).on( 'change', function () {
				    if ( table.column($(this).closest("th").index()).search() !== this.value  || table.column($(this).closest("th").index()).search() =='') {
		           
		            	if(isServerSideFilter){
			    			filterColumns();
			    		}else{
			    			 table
			 			    .column( $(this).data('index') )
			 			    .search( this.value )
			 			    .draw();
			    		}
		            }
		        } );
	    	  $(closeBtn).on( 'click', function () {
		        	 $( this).parent().find('.filterclass ').val('').select2();
		        	  if(isServerSideFilter){
			    			filterColumns();
			    		 }else{
			     			table
			    			 .search( '' )
			    			 .columns().search( '' )
			    			 .draw();
			    		} 
		           
		        } );
	    	   $(tesst).addClass("select2");
	    	  $(tesst).select2(); 
		}else{
		    $(tesst).bind('kepup keydown keypress focus', function(e) {
	    		  $(this).tooltip();
	    	  });
	        $(tesst).bind('keypress', function(e) {
	        	 $(this).tooltip();
	    		  if ( e.keyCode == 13  &&  table.column($(this).closest("th").index()).search() !== this.value) { 
	    			  if(isServerSideFilter){
			    			filterColumns();
			    		}else{
			    			 table
			 			    .column( $(this).data('index') )
			 			    .search( this.value )
			 			    .draw();
			    		}
	    		  }
	    		});
	        $(closeBtn).on( 'click', function () {
	        	 $( this).parent().find('.filterclass ').val('');
	        	
	        	 if(isServerSideFilter){
		    			filterColumns();
		    		}else{
		    			 table
		 			    .column( $(this).data('index') )
		 			    .search( this.value )
		 			    .draw();
		    		}
	        } );
		}
		$(tesst).attr("name",name);
		$(tesst).attr("id",name+"~"+dname); 
		$(tesst).addClass(type); 

		
		  }else{
			   $(tesst).bind('kepup keydown keypress focus', function(e) {
	        		  $(this).tooltip();
	        	  });
		        $(tesst).bind('keypress', function(e) {
		        	 $(this).tooltip();
	        		  if ( e.keyCode == 13  &&  table.column($(this).closest("th").index()).search() !== this.value) { 
	        			  if(isServerSideFilter){
				    			filterColumns();
				    		}else{
				    			 table
				 			    .column( $(this).data('index') )
				 			    .search( this.value )
				 			    .draw();
				    		}
	        		  }
	        		});
		        $(closeBtn).on( 'click', function () {

		        	 $(this).find('.filterclass ').val('');
		        	var fltrName= $(this).attr("name");
		        	$("."+fltrName).val('');
		        	 if(isServerSideFilter){
		        		 filterColumns();
			    		}else{
			    				table.column($(this).closest("th").index())
					 			    .search('' )
					 			    .draw();
			    		}
		           
		        } );
		    	$(tesst).attr("id",fieldnammm);    
		      
		  }
		  
		  var divv = $('<div />');
		  $(divv).css('display','flex');
		  $(divv).css('justify-content','space-between');
		  $(divv).append(tesst);
		  $(divv).append(closeBtn);
		  $(new_th).append(divv);
		
		  }
		  $(new_row).append(new_th);
		  
		});
		$('#'+datatableid+' thead').prepend(new_row);
	}
	function ConfirmModalDelete(title,message, fn,buttonText,cancelBtn) {
		event.preventDefault();
		swal.fire({
		    title: title,
		    html: message,
		    showCancelButton: cancelBtn,
		    confirmButtonColor: '#3085d6',
		    cancelButtonColor: '#d33',
		     allowEscapeKey : false,
                   allowOutsideClick: false,
		    confirmButtonText: buttonText
		}).then(function (result) {
		    if (result.value) {
		    	fn();
		    }
		});
	}
	function printDiv(divName) {
	     var printContents = document.getElementById(divName).innerHTML;
	     var originalContents = document.body.innerHTML;

	     document.body.innerHTML = printContents;

	     window.print();

	     document.body.innerHTML = originalContents;
	}
</script>



