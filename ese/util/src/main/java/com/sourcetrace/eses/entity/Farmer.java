package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.ParamDef;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "farmer")
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))

@Getter
@Setter
public class Farmer extends ParentEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Status {
		INACTIVE, ACTIVE, APPROVE, REJECT, DELETED, EXPORTER_INSPECTION
	}

	public static final String TABLE_ID = "1";
	public static final String SEX_MALE = "MALE";
	public static final String SEX_FEMALE = "FEMALE";
	public static final String SEX_MALE_FEMALE = "MALE/FEMALE";
	public static final int FARMER_SEQ_RANGE = 1000;
	public static final int FARMER_RESERVE_INDEX = 100;
	public static final int FARMER_ID_LENGTH = 6;
	public static final int FARMER_ID_MAX_RANGE = 999999;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_DATE")
	private Date regDate;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "NIN")
	private String nin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VILLAGE_ID")
	private Village village;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DOB")
	private Date dateOfBirth;

	@Column(name = "EMAIL")
	private String emailId;

	@Column(name = "MOBILE_NUMBER")
	private String mobileNo;

	@Column(name = "FARMER_CODE")
	private String farmerCode;

	@Column(name = "FARMER_ID")
	private String farmerId;
	@OneToMany(mappedBy = "farmer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy(clause = "ID ASC")
	private Set<Farm> farms;

	@Column(name = "REVISION_NO")
	private Long revisionNo;

	@Column(name = "INSP_ID")
	private Long inspId;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "STATUS_MSG",columnDefinition="LONGTEXT")
	private String statusMsg;

	@Column(name = "STATUS_CODE")
	private Integer statusCode;

	@Column(name = "IS_ACTIVE")
	private Long isActive;

	@Column(name = "NO_OF_FAMILY_MEMBERS")
	private String noOfFamilyMember;

	@Column(name = "ADULT_ABOVE")
	private String adultAbove;

	@Column(name = "NUMBER_OF_SCHOOL_GOING_CHILDREN")
	private String schoolGoingChild;

	@Column(name = "CHILDREN_BELOW_18")
	private String childBelow;

	@Column(name = "HIGHEST_LEVEL_OF_EDUCATION")
	private String hedu;

	@Column(name = "ASSET_OWNERSHIP")
	private String asset;

	@Column(name = "OWN_HOUSE")
	private String house;

	@Column(name = "HOUSING_OWNERSHIP")
	private String ownership;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "AGE")
	private String age;

	@Column(name = "NATIONAL_ID")
	private String nid;

	@Column(name = "CROP_CATEGORY", length = 50, columnDefinition = "VARCHAR(255)")
	private String cropCategory;

	@Column(name = "FARMER_PHOTO", length = 50, columnDefinition = "VARCHAR(255)")
	private String farmerPhoto;
	
	@Column(name = "PHOTO_NID", length = 50, columnDefinition = "VARCHAR(255)")
	private String photoNid;


	@Column(name = "CROP_NAME", length = 50, columnDefinition = "VARCHAR(255)")
	private String cropName;
	
	// Transient
	@Transient
	private List<String> branchesList;
	
	@Column(name = "MSG_NO")
	private String msgNo;
	
		@Column(name = "FARMER_CAT")
	private String farmerCat;
	
	@Column(name = "COMPANY_NAME", length = 50, columnDefinition = "VARCHAR(255)")
	private String companyName;
	
	@Column(name = "KRA_PIN", length = 50, columnDefinition = "VARCHAR(255)")
	private String kraPin;
	
	@Column(name = "REGISTRATION_CERTIFICATE", length = 50, columnDefinition = "VARCHAR(255)")
	private String registrationCertificate;
	
	@Column(name = "FARMER_REG_TYPE", length = 50, columnDefinition = "VARCHAR(255)")
	private String farmerRegType;
	
	@Column(name = "EXPORTERS")
	private String exporters;
}
