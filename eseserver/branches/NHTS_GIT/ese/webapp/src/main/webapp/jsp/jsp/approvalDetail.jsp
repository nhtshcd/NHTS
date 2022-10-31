<%@ include file="/jsp/common/grid-assets.jsp"%>
<script>
var apprType='<%=request.getParameter("approveType")%>';
var insType='<s:property value="inspType"/>';
jQuery(document).ready(function() {
	
	if(insType == '4'){
		$(".agroDeal").addClass('hide');
	}else{
		$(".agroDeal").removeClass('hide');
	}
	if(apprType == 'inspector'){
		$(".userDiv").addClass('hide');
	}
	
});
</script>
							<div class="formContainerWrapper dynamic-form-con">
							<h2>
								Inspection Detail
							</h2>
							
							


						</div>
						
						<div class="appContentWrapper marginBottom"  id="inspDivv">
							<table class="table table-bordered aspect-detail">
								<thead>
								<tr class="odd">
									<!-- 	<th><s:text name="categoryName" /></th> -->
									<th><s:text name="%{getLocaleProperty('Date')}" /></th>
									<th><s:text name="%{getLocaleProperty('Inspector')}" /></th>
									<th><s:text name="%{getLocaleProperty('Comments')}" /></th>
									<th class="userDiv agroDeal"><s:text name="%{getLocaleProperty('agroChemicalDealer.suitabilityPremises')}" /></th>
									<th id="partAppintxt"><s:text
											name="%{getLocaleProperty('agroChemicalDealer.partApplInfo')}" /></th>
									<th><s:text name="%{getLocaleProperty('File')}" /></th>
								</tr>
								</thead>
								<s:iterator value="inspDetails" status="rowstatus" var="sett">
									<tr class="odd">
										<td><s:date name="createdDate" format="dd/MM/yyyy" /></td>
										<td><s:property value="user.username" /></td>
										<td><s:property value="comments" /></td>
										<td class="userDiv agroDeal">	<s:property
												value='%{getLocaleProperty("radiobutton"+suitabilityPremises)}' /></td>
										<td ><s:property
												value='%{getLocaleProperty("radiobutton"+partApplInfo)}' /></td>
										<s:if test="doc!=null && doc!='' && doc!='null'" >
											<td><button type="button" class="fa fa-download"
													style="background-color: transparent"
													onclick="popDownload(<s:property value="%{doc}"/>)"></button></td>
										</s:if>
										<s:else>
											<td></td>
										</s:else>

									</tr>
								</s:iterator>
							</table>


						</div>

							<div class="formContainerWrapper dynamic-form-con userDiv">
							<h2>
								<s:property
									value="%{getLocaleProperty('info.agroChemicalDealer.approve')}" />
							</h2>
							<div class="dynamic-flexItem">
								<p class="flexItem">
									<s:text
										name="%{getLocaleProperty('partReg')}" />
								</p>
								<p class="flexItem">
									<s:property
										value='%{getLocaleProperty("status"+approvalStatus)}' />

								</p>
							</div>
							<div class="dynamic-flexItem" id="apprdate">
								<p class="flexItem">
									<s:text
										name="Approval Date" />
								</p>
								<p class="flexItem">
									<s:date name="approvalDate"
										format="dd/MM/yyyy" />
									
								</p>
							</div>
							
							
							<s:if test="approvalStatus ==3">
							<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('Inspector')}" />
									</p>
									<p class="flexItem">
									<s:property value="inspName" />
									</p>
								</div>
							</s:if>
							<s:if test="approvalStatus ==1">
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:property value="%{getLocaleProperty('agroChemicalDealer.File')}" />
									</p>
									<p class="flexItem">
									<s:if test="statusDetail.doc!='' && statusDetail.doc!=null && statusDetail.doc!='null'">
										<button type="button" class="fa fa-download"
											onclick="popDownload(<s:property value="%{statusDetail.doc}"/>)"></button></s:if>
									</p>
								</div>
							</s:if>
							<s:elseif test="approvalStatus ==2">
								<div class="dynamic-flexItem">
									<p class="flexItem">
										<s:text name="agroChemicalDealer.reason" />
									</p>
									<p class="flexItem">
										<s:property value="statusDetail.reason" />
									</p>
								</div>
							</s:elseif>
							
							
							
							<div class="dynamic-flexItem" id="expVarInc">
								<p class="flexItem">
									<s:text
										name="Expiry Date" />
								</p>
								<p class="flexItem">
									<s:date name="expireDate"
										format="dd/MM/yyyy" />
									
								</p>
							</div>
							
							
						</div>
		

