package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Where;

import lombok.Data;


@Entity
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))@Filters(@org.hibernate.annotations.Filter(name="branchFilter", condition="branch_id in ( :branchIdParam )"))
@Table(name = "variety_inclusion")
@Data
public class VarietyInclusion extends ApprovalEntity{
	public static enum TransType {
		VARIETY_INCLUSION,BREEDER_SEED
	}
	
	@Column(name = "TXN_DATE", columnDefinition = "DATE")
	private Date txnDate;
	
	@Transient
	private String dateStr;
	
	@Column(name = "NAME_OF_APPLICANT", length = 250)
	private String appName;
	
	@Column(name = "NAME_OF_EMPLOYER", length = 250)
	private String empName;
	
	@Column(name = "LOCATION_AND_ADDRESS", length = 250)
	private String locAddr;
	
	@Column(name = "EMAIL_ADDRESS", length = 45, columnDefinition = "VARCHAR")
	private String email;
	
	@Column(name = "MOBILE_NO", length = 45, columnDefinition = "VARCHAR")
	private String mobileNo;
	
	@Column(name = "QUALIFICATION", length = 250)
	private String quali;
	
	@Column(name = "EXPERIENCE", length = 250)
	private String experience;
	
	@Column(name = "CROP", length=100)
	private String productId;
	
	@Column(name = "VARIETY", length = 250)
	private String variety;
	
	@Column(name = "ORIGIN", length = 250)
	private String origin;
	
	@Column(name = "GENITIC_COMPOSITION", length = 250)
	private String genComp;
	
	@Column(name = "DOC_ID", length = 250)
	private Integer docId;
	
	@Column(name = "ADDITIONAL_INFORMATION", length = 250)
	private String addInfn;
	
	/*@OneToMany(mappedBy = "referenceId", orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy(clause = "id desc")
	@Where(clause = "type = 16")
	private SortedSet<InspectionDetails> inspDetails;

	@OneToMany(mappedBy = "referenceId", orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy(clause = "id desc")
	@Where(clause = "type = 16")
	private SortedSet<StatusDetails> statusDetails;*/
}
