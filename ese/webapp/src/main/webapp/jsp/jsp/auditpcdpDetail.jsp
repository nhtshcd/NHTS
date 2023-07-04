<%@ include file="/jsp/common/form-assets.jsp"%>
<%@ include file="/jsp/common/detail-assets.jsp"%>
<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<meta name="decorator" content="swithlayout">
</head>
<link rel="stylesheet" href="plugins/openlayers/theme/default/style.css"
	type="text/css">
</head>

					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.fcropName')}" />
						</p>
						<p class="flexItem">
							<%-- <s:property value="findVarietyByGradeId(#auditList[0].cropvariety.id)" /> --%>
							<s:property value="#auditList[0].cropvariety.procurementVariety.name" />
						</p>
					</div>
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.cropvariety')}" />
						</p>
						<p class="flexItem">
						<%-- 	<s:property value="getCropHierarchyById('procurement_grade',#auditList[0].cropvariety.id)" /> --%>
							<s:property value="#auditList[0].cropvariety.name" />
						</p>
					</div>
					
					<div class="dynamic-flexItem ">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.tradeName')}" />
						</p>
						<p class="flexItem">
							<s:property value="#auditList[0].tradeName" />
						</p>
					</div>
				
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.registrationNo')}" />
						</p>
						<p class="flexItem">
							<s:property value="#auditList[0].registrationNo" />
						</p>
					</div>
					
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.activeing')}" />
						</p>
						<p class="flexItem">
							<s:property value="#auditList[0].activeing" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.manufacturerReg')}" />
						</p>
						<p class="flexItem">
							<s:property value="#auditList[0].manufacturerReg" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('pcbp.agent')}" />
						</p>
						<p class="flexItem">
							<s:property value="#auditList[0].agent" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property
								value="%{getLocaleProperty('pcbp.phiIn')}" />
						</p>
						<p class="flexItem">
							<s:property value="#auditList[0].phiIn" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.dosage')}" />
						</p>
						<p class="flexItem">
							<s:property value="#auditList[0].dosage" />
						</p>
					</div>
					<div class="dynamic-flexItem">
						<p class="flexItem">
							<s:property value="%{getLocaleProperty('pcbp.uom')}" />
						</p>
						<p class="flexItem">
							<s:property value="%{getCatalgueNameByCode(#auditList[0].uom)}" />
						</p>
					</div>
				

			
	

