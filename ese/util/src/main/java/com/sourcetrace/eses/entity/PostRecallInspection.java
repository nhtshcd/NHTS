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

@Entity
@Table(name = "post_recall_inspection")
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))

@Getter
@Setter
public class PostRecallInspection extends ParentEntity {
	



	private static final long serialVersionUID = 1L;
	public static enum Status {
		INACTIVE, ACTIVE, APPROVE, REJECT, DELETED, EXPORTER_INSPECTION
	}
	
	@Column(name = "INSPECTION_DATE")
	private Date inspectionDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECALL_ID")
	private Recalling recall;

	
	@Column(name = "NAME_OF_AGENCY")
	private String nameOfAgency;
	
	@Column(name = "NAME_OF_INSPECTOR")
	private String nameOfInspector;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name = "OPERATOR_BEING_INSPECTED")
	private String operatorBeingInspected;

	@Column(name = "NATURE_OF_RECALL")
	private String natureOfRecall;

	@Column(name = "MANAGEMENT_OF_RECALLED")
	private String managementOfRecalled;

	
	@Column(name = "RECALL_REPORT")
	private String recallReport;

	@Column(name = "CORRECTIVE_ACTION")
	private String correctiveAction;
	
	@Column(name = "RECOMMENDATION")
	private String recommendation;

	
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
	
	
}
