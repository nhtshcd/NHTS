<%@ taglib uri="/struts-tags" prefix="s"%>
<%-- <%@ taglib uri="/ese-tags" prefix="e"%> --%>
<head>
<META name="decorator" content="loginlayout">
<style type="text/css">
.nav-item {
  position: relative;
}

.navbar-collapse ul li a.nav-link:before {
    position: absolute;
    bottom: -5px;
    left: 0;
    width: 100%;
    height: 2px;
    background: transparent;
    content: '';
    opacity: 0;
    -ms-transition: opacity 0.3s, -webkit-transform 0.3s;
    -webkit-transition: opacity 0.3s, -webkit-transform 0.3s;
    transition: opacity 0.3s, transform 0.3s;
    -ms-transform: translateY(10px);
    -webkit-transform: translateY(10px);
    transform: translateY(10px);
}

.navbar-collapse ul li:hover a.nav-link:before {
	opacity: 1;
	-ms-transform: translateY(0px);
	-webkit-transform: translateY(0px);
	transform: translateY(0px);
	bottom: 0px;	
}

.dropdown-item:hover, .dropdown-item:focus {
    color: #ffffff;
    text-decoration: none;    
}

.dropdown-menu {
  border: 0px;
}
.nav-item a {
	font-weight:bold;
	color:#fff;
	text-transform:uppercase;
}
.dropdown-menu a {
	font-weight:bold;
	color:#333;
	text-transform:capitalize;
}
ul.nav li.dropdown:hover ul.dropdown-menu{ display: block; }


.pr-login-wrap .login-form-wrap .frgt-lnkN {
    display: inline;
   
}

.psss{
    padding-top: 15px;
    padding-bottom: 15px;}
    
 

</style>
</head>

<div class="pr-login-wrap container-fluid">
	<div style="display: flex; justify-content: space-between;">
	
	<div>
		<nav class="navbar navbar-expand-lg">
		<div
			style="width: 100px; height: auto; padding: 0px; border-radius: 10px; background: #fff;"
			class="cmyLogo">
			<!-- <img src="dist/nhts-logo.jpg" style="width: 100%; height: auto;" /> -->
		<!-- 	<img src="login_populateLogo.action?logoType=app_logo"  style="width: 100%; height: 100px;" /> -->
			<img src="login_populateLogo.action?logoType=app_logo"  style="width: 100%; height: auto;" />
		</div>
  
	   <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	   </button>
	   <div class="collapse navbar-collapse" id="navbarSupportedContent" style="margin-left:20px;">
	
  </div>
	   
     </nav>
	</div>
	</div>
	<div class="pr-login-wrap-width">
		<div class="row h-100 justify-content-center align-items-center">

			<div class="col px-0">
				<div class="login-text text-white">
					<h2 class="ttl">
						National Horticulture Traceability System
					</h2>
					<!-- <p class="p crdts">Powered by SourceTrace V1.0</p> -->
					<!-- <p class="p crdts">Powered by HCD V3.0</p> -->
					     <p class="p crdts">Powered by Horticulture Crops Directorate (HCD, Kenya) <s:property value="vesion" /></p>
				</div>
			</div>

			<div class="col-auto px-0">
				<div class="login-form-wrap">

					<div class="login-form-outer">
						<s:form name="loginform" class="login-form" action="login">
							
							<div class="row">
								<div class="col-12">
									<h3 class="frm-ttl">Sign In</h3>
								</div>
							</div>
							<font style="color: red;"> <s:actionerror />
							</font>
							
							<s:if test="isMultibranchApp==0">
								<s:if test="branchEnabled==1">
									<div class="form-group row">
										<div class="col">
											<s:select id="branch" theme="simple" name="branchId"
												list="branches" listKey="key" listValue="value" headerKey=""
												headerValue="%{mainBranchName}"
												cssClass="prselectcustom selectpicker br21" />
										</div>
									</div>
								</s:if>


							</s:if>
							<div class="form-group row">
								<div class="col">
									<span class="input-icon"> <s:textfield id="userNameAc"
											theme="simple" size="20" 
											placeholder="Username"
											cssClass="st form-control pr-form-control"
											required="required" />
											<s:hidden id="userName" key="j_username" ></s:hidden>
											
									</span>
								</div>
							</div>
							<div class="form-group row">
								<div class="input-container pr-form-control">
								 <s:password id="password"
											theme="simple" size="20" key="j_password" showPassword="true"
											placeholder="Password"
											cssClass="st form-control pr-form-control"
											required="required" />
											<i class="faEye" id="togglePassword" onclick="event.preventDefault();showPw(this)"></i>
									
								
								</div>
								
							</div>
							<div>
							
							</div>
							<div class="row">
								<div class="col-12">
									<button id="btnLogin" name="login"
										class="btn btn-block bttn-primary bttn-login st" type="button"
										onclick="onCreate()">
										<s:text name="login.button" />
									</button>
								</div>
							</div>
							<div class=" row">
								<div class="col-12">
									<p class="p agrmnt">
										By Logging In, you agree to our <a class="st" href="#"
											data-toggle="modal" data-target="#licenseModal">Customer
											Agreement.</a>
									</p>
								</div>
							</div>
						</s:form>
						<div class="col-12" style="padding-top: 15px;  padding-bottom: 15px;">
						<p class="p agrmnt">
						<a class="forgot frgt-lnkN st" href="#" data-toggle="modal"  style="float:left"
							data-target="#forgotPasswordModal" onclick="focusInput()"><s:text
								name="forgotPassword" /></a>
								
								<a class="forgot frgt-lnkN st" href="#" data-toggle="modal"   style="float:right"
							onclick="getval(13)"><s:property value="getLocaleProperty('expSigning')"/></a></p></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>



<!-- Modal -->
<div class="pr-modal modal top fade" id="forgotPasswordModal"
	data-keyboard="false" tabindex="-1"
	aria-labelledby="#forgotPasswordModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="">Forgot Password</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="box-forgot">
					<p>Enter Username or Email Address to reset your password.</p>
					<form>
						<div class="form-group">
							<span class="input-icon username" id="name1"> <s:textfield  id="name"
									theme="simple" size="20" maxlength="50" type="email" key=""
									placeholder="Username/Email Address" disabled="disabled"
									cssClass="st form-control pr-form-control" />
							</span> <%-- <span class="input-icon email" id="email1"> <s:textfield id="email"
									theme="simple" size="20" maxlength="50" type="email" key=""
									placeholder="Email Address" cssClass="st form-control pr-form-control"
									disabled="disabled" />
							</span> --%>
						</div>
						<%-- <div class="form-group">
	            <label class="radio-inline credentialRadio"> <s:radio
	            		list="credentialList" id="credential" listKey="key"
	            		listValue="value" theme="simple" name="credentialName" />
	            </label>
	            
	            </div> --%>
						 <div class="form-group">
							<s:radio id="credential" list="credentialList" listKey="key"
								listValue="value" theme="simple"
								class="pr-custom-radio pr-radio radio" name="credentialName" />
						</div> 
					</form>
				</div>


				<button id="back" type="cancel" class="btn bttn-primary-outline st"
					onclick="onCancel();" />
				<s:text name="Back" />
				</button>

				<button id="reset" type="button"
					class="btn bttn-primary bttn-login st" onclick="validateUser();" />
				<s:text name="sendEmail" />
				</button>
			</div>
			<div class="modal-footer"></div>
		</div>
	</div>
</div>

<div class="pr-modal modal right fade" id="licenseModal"
	data-keyboard="false" tabindex="-1" aria-labelledby="licenseModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="">License Agreement</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<p>LICENSEE AGREES TO DEFEND, INDEMNIFY, AND HOLD HARMLESS
					HORTICULTURE CROPS DIRECTORATE (HCD, KENYA), ITS OFFICERS, MEMBERS, EMPLOYEES, CONTRACTORS,
					AFFILIATES AND ITS LICENSORS FROM AND AGAINST ANY DAMAGES, COSTS,
					LIABILITIES, SETTLEMENT AMOUNTS AND/OR EXPENSES (INCLUDING
					ATTORNEYS FEES) INCURRED IN CONNECTION WITH ANY CLAIM, LAWSUIT OR
					ACTION BY ANY PARTY OR THIRD PARTY THAT ARISES OR RESULTS FROM
					(angel) THE USE OR DISTRIBUTION OF THE SOLUTION OR ANY PORTION
					THEREOF, OR (beer) ANY BREACH OF LICENSEES REPRESENTATIONS,
					WARRANTIES AND COVENANTS SET FORTH IN THIS AGREEMENT</p>
				<p>DISCLAIMER OF WARRANTIES AND LIMITATION OF LIABILITY.</p>
				<p>DISCLAIMER OF WARRANTIES. LICENSEE AGREES THAT TO THE MAXIMUM
					EXTENT PERMITTED BY APPLICABLE LAW, HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) AND ITS SUPPLIERS
					PROVIDE THE SOLUTION AND SUPPORT SERVICES (IF ANY) AS IS AND WITH
					ALL FAULTS, AND HEREBY DISCLAIM ALL OTHER WARRANTIES AND
					CONDITIONS, EITHER EXPRESS, IMPLIED OR STATUTORY, INCLUDING, BUT
					NOT LIMITED TO, ANY (IF ANY) IMPLIED WARRANTIES, DUTIES OR
					CONDITIONS OF MERCHANTABILITY, OF FITNESS FOR A PARTICULAR PURPOSE,
					OF ACCURACY OR COMPLETENESS OF RESPONSES, OF RESULTS, OF
					WORKMANLIKE EFFORT, OF LACK OF VIRUSES, AND OF LACK OF NEGLIGENCE,
					ALL WITH REGARD TO THE SOLUTION, AND THE PROVISION OF OR FAILURE TO
					PROVIDE SUPPORT SERVICES. ALSO, THERE IS NO WARRANTY OR CONDITION
					OF TITLE, QUIET ENJOYMENT, QUIET POSSESSION, CORRESPONDENCE TO
					DESCRIPTION OR NON-INFRINGEMENT WITH REGARD TO THE SOLUTION. THE
					ENTIRE RISK AS TO THE QUALITY OR ARISING OUT OF USE OR PERFORMANCE
					OF THE SOLUTION AND SUPPORT SERVICES, IF ANY, REMAINS WITH
					LICENSEE.
				<p>
				<p>EXCLUSION OF INCIDENTAL, CONSEQUENTIAL AND CERTAIN OTHER
					DAMAGES. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW, IN NO
					EVENT SHALL HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) OR ITS SUPPLIERS BE LIABLE FOR ANY SPECIAL,
					INCIDENTAL, INDIRECT, PUNITIVE OR CONSEQUENTIAL DAMAGES WHATSOEVER
					(INCLUDING, BUT NOT LIMITED TO, DAMAGES FOR LOSS OF PROFITS OR
					CONFIDENTIAL OR OTHER INFORMATION, FOR BUSINESS INTERRUPTION, FOR
					PERSONAL INJURY, FOR LOSS OF PRIVACY, FOR FAILURE TO MEET ANY DUTY
					INCLUDING OF GOOD FAITH OR OF REASONABLE CARE, FOR NEGLIGENCE, AND
					FOR ANY OTHER PECUNIARY OR OTHER LOSS WHATSOEVER) ARISING OUT OF OR
					IN ANY WAY RELATED TO THE USE OF OR INABILITY TO USE THE SOLUTION,
					THE PROVISION OF OR FAILURE TO PROVIDE SUPPORT SERVICES, OR
					OTHERWISE UNDER OR IN CONNECTION WITH ANY PROVISION OF THIS
					AGREEMENT, EVEN IN THE EVENT OF THE FAULT, TORT (INCLUDING
					NEGLIGENCE), STRICT LIABILITY, BREACH OF CONTRACT OR BREACH OF
					WARRANTY OF HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) OR ANY SUPPLIER, AND EVEN IF HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) OR
					ANY SUPPLIER HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.</p>
				<p>LIMITATION OF LIABILITY AND REMEDIES. NOTWITHSTANDING ANY
					DAMAGES THAT LICENSEE MIGHT INCUR FOR ANY REASON WHATSOEVER
					(INCLUDING, WITHOUT LIMITATION, ALL DAMAGES REFERENCED ABOVE AND
					ALL DIRECT OR GENERAL DAMAGES), LICENSEE AGREES THAT THE ENTIRE
					LIABILITY OF HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) AND ALL OF ITS SUPPLIERS UNDER ANY
					PROVISION OF THIS AGREEMENT AND LICENSEES EXCLUSIVE REMEDY FOR ALL
					OF THE FOREGOING SHALL BE LIMITED TO THE GREATER OF THE ACTUAL FEE
					PAID BY LICENSEE TO HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) UNDER THIS AGREEMENT OR U.S. $5.
					THE FOREGOING LIMITATIONS, EXCLUSIONS AND DISCLAIMERS SHALL APPLY
					TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW, EVEN IF ANY
					REMEDY FAILS ITS ESSENTIAL PURPOSE.</p>
				<p>LICENSEE WARRANTS THAT IT SHALL HOLD IN THE STRICTEST
					CONFIDENCE THIS COMPUTER PROGRAM AND ANY RELATED MATERIALS OR
					INFORMATION INCLUDING, BUT NOT LIMITED TO, ANY TECHNICAL DATA,
					RESEARCH, PRODUCT PLANS OR KNOW-HOW PROVIDED BY HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) TO
					LICENSEE, EITHER DIRECTLY OR INDIRECTLY IN WRITING, ORALLY OR BY
					INSPECTION OF MATERIALS, INCLUDING THE SOFTWARE, RELATED LICENSED
					MATERIALS, AND OTHER APPLICATION SOFTWARE, SYSTEMS, OR MATERIALS,
					PROVIDED BY HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) ('HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) CONFIDENTIAL INFORMATION').
					LICENSEE WARRANTS THAT IT SHALL NOT DISCLOSE ANY OF HORTICULTURE CROPS DIRECTORATE (HCD, KENYA)
					CONFIDENTIAL INFORMATION TO THIRD PARTIES AND LICENSEE WARRANTS
					THAT IT SHALL TAKE REASONABLE MEASURES TO PROTECT THE SECRECY OF
					AND AVOID DISCLOSURE AND UNAUTHORIZED USE OF HORTICULTURE CROPS DIRECTORATE (HCD, KENYA)
					CONFIDENTIAL INFORMATION. LICENSEE SHALL IMMEDIATELY NOTIFY
					HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) IN THE EVENT OF ANY UNAUTHORIZED OR SUSPECTED USE OR
					DISCLOSURE OF HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) CONFIDENTIAL INFORMATION.</p>
				<p>HORTICULTURE CROPS DIRECTORATE (HCD, KENYA) Ke-HTS Copyright ©2022 HORTICULTURE CROPS DIRECTORATE (HCD, KENYA).</p>
				<%-- <p class="p crdts">Powered by HCD <s:property value="vesion" /></p> --%>
				<p class="p crdts">Powered by Horticulture Crops Directorate (HCD, Kenya) </p>
			</div>
			<div class="modal-footer">
				<button class="btn bttn-primary bttn-login st" data-dismiss="modal">Dismiss</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	var currentTenantId = '<s:property value="#session.tenantId" />';

	function resetPassword() {
	}

	function validateUser() {
		var hit = true;
		var nameVal = /^[a-zA-Z0-9 ]+$/;
		var mailVal = /\S+@\S+\.\S+/;
		var userValue = $('input[name="credentialName"]:checked').val();
		//	alert(userValue);
		if (userValue == 1)
			var userName = $('#name').val();
		else if (userValue == 2)
			var userName = $('#name').val();
		//alert(email);
	if (userValue === "" || userValue === null
				|| userValue == undefined || userName === userValue) {
			alert('<s:text name="selectCredType"/>');
			hit = false;
		} else	if (userName === "" || userName === null || userName === undefined) {
			if(userValue=="1")
				alert('<s:property value="getLocaleProperty('emptyName')"/>');
			if(userValue=="2")
				alert('<s:property value="getLocaleProperty('emptyMail')"/>');
			hit = false;
		}  else if (userValue === "2" && !mailVal.test(userName)) {
			alert('<s:text name="enterValidMail"/>');
			hit = false;
		}
		//alert(hit);
		if (hit) {
			resetCredential();
			$.post("email_validateUser", {
				resetValue : userName.toLowerCase(),
				userValue : userValue,
				dt : new Date()
			}, function(data) {
				if (data === "" || data === null || data === undefined) {
					alert('<s:text name="errorWhileProcessing"/>');
				} else {
					if(data.includes("100")){
						alert(data.replace("100",""));	
						document.loginform.action = "login_execute";
						document.loginform.submit();
					}else{
						alert(data);
					}								
				}
				$('#btnLogin').prop("disabled", true);
				document.loginform.action = "login_execute";
				//document.loginform.submit();
			});
		}

		else {
			$('#name').val('');
		}
	}
	function resetPopup() {
		document.loginform.action = '';
		$('#btnLogin').prop("disabled", true);
		document.loginform.submit();
	}
	function resetCredential() {
		$('#name').val('');
		$("input[name='credentialName']").attr("checked", false);
	}

	function checkClick() {
		$("#check").hide();
	}
	function onRegister() {
		window.open('agroDealer_create.action?layoutType=publiclayout&url=agroDealer_');
	}
	function onCreate() {
		var userName = document.getElementById('userName').value;
		var password = document.getElementById('password').value;
		//$("#actionError").html(" ");
		if ((userName === "" || userName === null)
				&& (password === "" || password === null)) {
			$("#check").hide();
			alert('<s:text name="userName.password.reqd"/>');
		} else if (userName === "" || userName === null) {
			$("#check").hide();
			alert('<s:text name="username.reqd"/>');
		} else if (password === "" || password === null) {
			$("#check").hide();
			alert('<s:text name="password.reqd"/>');
		} else {
			document.loginform.action = "j_spring_security_check";
			//if (document.getElementById("agree").checked) {
			$("#check").hide();
			$('#btnLogin').prop("disabled", true);
			document.loginform.submit();
			/* } else {
				//$("#check").show();
				//$('#actionError').removeClass('no-display');
				$("#check").removeClass('hide');
				$("#check").removeAttr('style');
				$('.errorMessage').hide();
			} */
		}

		if (typeof (Storage) !== "undefined") {
			localStorage.cachedBranch = $('#branch').val();
		}
	}
	function getval(sel) {

		if (sel == 1) {
			window.open('agroDealer_create.action?layoutType=publiclayout&url=agroDealer_');
		} else if (sel == 2) {
			window.open('knowledgeRepPublic_knowlList');
		} else if (sel == 3) {
			window.open('dynamicList_list.action?id=6&layoutType=publiclayout');
		} else if (sel == 4) {
			window.open('dynamicList_list.action?id=7&layoutType=publiclayout');
		}else if (sel == 5) {
			window.open('dealerRenewalPublic_create.action?type=0&id=&layoutType=publiclayout&url=dealerRenewalPublic_');
		}else if (sel == 6) {
			window.open('dealerRenewalPublic_create.action?type=1&id=&layoutType=publiclayout');
		}else if (sel == 7) {
			window.open('seedMerchantPublic_create.action?layoutType=publiclayout&url=seedMerchantPublic_');
		}else if (sel == 8) {
			window.open('seedCertificationService_create.action?layoutType=publiclayout&url=seedCertificationService_');
		}else if (sel == 9) {
			window.open('seedDealerPub_create.action?layoutType=publiclayout&url=seedDealerPub_');
		}
		else if (sel == 10) {
			window.open('merchantRenewal_create.action?layoutType=publiclayout&url=merchantRenewal_');
		}
		else if (sel == 11) {
			window.open('breederSeedPub_create.action?layoutType=publiclayout&url=breederSeedPub_');
		}
		else if (sel == 12) {
			window.open('varietyInclusionPub_create.action?layoutType=publiclayout&url=varietyInclusionPub_');
		}
		
		else if (sel == 13) {
			window.open('exporterRegistrationPub_create.action?layoutType=publiclayout&url=exporterRegistrationPub_');
		}
		else if (sel == 14) {
			window.open('farmerPub_create.action?layoutType=publiclayout&url=farmerPub_');
		}
	}
	$(document).ready(function() {
		resetCredential();
		$("#check").hide();
		$("#username").focus();
		
		$("#userNameAc").on('change keyup paste',function(){
		    $("#userName").val($(this).val().toLowerCase());
		     });
			

	});
	function onCancel() {

		window.location.href = "login_execute";
	}
	function focusInput() {
		//alert("1");
        $("#name").focus();
    }

	function showPw(obj) {
		  var x = document.getElementById("password");
		  if (x.type === "password") {
			  $(obj).removeClass("faEye");
			  $(obj).addClass("faEyeSlash");
		    x.type = "text";
		  } else {
			  $(obj).addClass("faEye");
			  $(obj).removeClass("faEyeSlash");
		    x.type = "password";
		  }
		}
</script>