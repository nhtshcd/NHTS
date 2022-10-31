package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "exporter_registration")
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))
@Getter
@Setter
public class ExporterRegistration extends ApprovalEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "COMPANY_NAME", length = 50, columnDefinition = "VARCHAR(255)")
	private String name;

	@Column(name = "REG_PROOF", length = 50, columnDefinition = "VARCHAR(255)")
	private String regProof;

	@Column(name = "CMPY_ORIENTATION", length = 50, columnDefinition = "VARCHAR(255)")
	private String cmpyOrientation;

	@Column(name = "REG_NUMBER", length = 50, columnDefinition = "VARCHAR(255)")
	private String regNumber;

	@Column(name = "UGANDA_EXPORT", length = 50, columnDefinition = "VARCHAR(255)")
	private String ugandaExport;

	@Column(name = "REF_LETTERNO", length = 50, columnDefinition = "VARCHAR(255)")
	private String refLetterNo;

	@Column(name = "Farmer_HaveFarms", length = 50, columnDefinition = "VARCHAR(255)")
	private String farmerHaveFarms;

	@Column(name = "SCATTERED", length = 50, columnDefinition = "VARCHAR(255)")
	private String scattered;

	@Column(name = "Pack_GpsLoc", length = 50, columnDefinition = "VARCHAR(255)")
	private String packGpsLoc;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "VILLAGE", columnDefinition = "BIGINT(45)")
	private Village village;

	@Column(name = "PACKHOUSE", length = 50, columnDefinition = "VARCHAR(255)")
	private String packhouse;

	@Column(name = "Exp_TinNumber", length = 50, columnDefinition = "VARCHAR(255)")
	private String expTinNumber;

	@Column(name = "TIN", length = 50, columnDefinition = "VARCHAR(255)")
	private String tin;

	@Column(name = "REX_No", length = 50, columnDefinition = "VARCHAR(255)")
	private String rexNo;

	@Column(name = "farm_ToPackhouse", length = 50, columnDefinition = "VARCHAR(255)")
	private String farmToPackhouse;

	@Column(name = "Pack_ToExitPoint", length = 50, columnDefinition = "VARCHAR(255)")
	private String packToExitPoint;

	@Column(name = "MOBILE_NO", length = 50, columnDefinition = "VARCHAR(255)")
	private String mobileNo;

	@Column(name = "EMAIL_ID", length = 50, columnDefinition = "VARCHAR(255)")
	private String email;

	@OneToMany(mappedBy = "referenceId", orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy(clause = "id desc")
	@Where(clause = "type = 17")
	private SortedSet<InspectionDetails> inspDetails;

	@OneToMany(mappedBy = "referenceId", orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy(clause = "id desc")
	@Where(clause = "type = 17")
	private SortedSet<StatusDetails> statusDetails;
	
	@Column(name = "OTHER_SCATTERED", length = 50, columnDefinition = "VARCHAR(255)")
	private String otherScattered;
	@Transient
	private String addr;
	 
	
	@Column(name = "STATUS_MSG")
	private String statusMsg;
	/*@Column(name = "WAREHOUSE") 
	private String wareHouses ;
	
	@Transient
	private String warehouseDet;*/
}
