package com.sourcetrace.eses.entity;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.*;

@Entity
@Table(name = "site_prepration")
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Getter
@Setter
public class SitePrepration extends ParentEntity {

	private static final long serialVersionUID = 1L;
	public static enum Status {
		INACTIVE, ACTIVE, APPROVE, REJECT, DELETED, EXPORTER_INSPECTION
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "CROP_ID")
	private ProcurementVariety previousCrop;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FARM_ID")
	private Farm farm;
	@Column(name = "ENVIRONMENTAL_ASSESSMENT")
	private String environmentalAssessment;
	
	@Column(name = "ENVIRONMENTAL_ASSESSMENT_REPORT")
	private String environmentalAssessmentReport;
	
	@Column(name = "SOCIAL_RISK_ASSESSMENT")
	private String socialRiskAssessment;

	@Column(name = "SOCIAL_RISK_ASSESSMENT_REPORT")
	private String socialRiskAssessmentReport;

	
	@Column(name = "SOIL_ANALYSIS")
	private String soilAnalysis;
	
	@Column(name = "SOIL_ANALYSIS_REPORT")
	private String soilAnalysisReport;

	
	@Column(name = "WATER_ANALYSIS")
	private String waterAnalysis;

	@Column(name = "WATER_ANALYSIS_REPORT")
	private String waterAnalysisReport;

	@Column(name = "REVISION_NO")
	private Long revisionNo;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "STATUS_MSG",columnDefinition="LONGTEXT")
	private Integer statusMsg;

	@Column(name = "STATUS_CODE")
	private Integer statusCode;

	@Column(name = "IS_ACTIVE")
	private Long isActive;
	
	@Column(name = "MSG_NO")
	private String msgNo;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "FARM_CROPS_ID")
	private FarmCrops farmCrops;
}
