package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "farm_inspection")
@Data
public class FarmInspection extends ParentEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "DATE")
	private Date insDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FARM_CROP_ID")
	private FarmCrops farmCrops;
	
	@Column(name = "CONTACT_PERSON", columnDefinition = "VARCHAR")
	private String contactPerson;
	
	@Column(name = "GREEN_HOUSE_NAME", columnDefinition = "VARCHAR")
	private String greenHouseName;

	@Column(name = "OBSERVATIONS", columnDefinition = "VARCHAR")
	private String observ;

	@Column(name = "PREVIOUS_CORRECT")
	private String previouseCorrective;

	@Column(name = "GREEN_HOUSE_STATUS")
	private String greenHouseStatus;

	@Column(name = "TRACE_CODE_SYSTEM")
	private String traceCodeSystem;

	@Column(name = "PEST_STATUS")
	private String pestStatus;

	@Column(name = "PEST_PARTICULAR")
	private String pestParticular;

	@Column(name = "PEST_LOCATION")
	private String pestLocation;

	@Column(name = "CROP_PROTECTION")
	private String cropProtection;

	@Column(name = "MODEL_TRANSPORT")
	private String modeTransport;

	@Column(name = "NO_OF_SCOUTER")
	private String noOfScouter;

	@Column(name = "GOOD_HANDLING")
	private String goodHandling;

	@Column(name = "GOOD_HANDLING_SPEC")
	private String goodHandlingSpec;

	@Column(name = "HYGIENE_STATUS")
	private String hygieneStatus;

	@Column(name = "HYGIENE_STATUS_DES")
	private String hygieneStatusDes;

	@Column(name = "GRADING_HALL")
	private String gradingHall;

	@Column(name = "GRADING_HALL_DES")
	private String gradingHallDes;

	@Column(name = "GRADING_HALL_STAFF")
	private String gradingHallStaff;

	@Column(name = "EQUIPMENT")
	private String equipment;

	@Column(name = "PRESENCE_EVIDENCE")
	private String presenceEvidence;

	@Column(name = "EXPORT_SUP_STATUS")
	private String exportSupStatus;

	@Column(name = "REJECT_REASON")
	private String rejectReason;

	@Column(name = "ADDITION_DECLARE")
	private String additionDeclare;
	
	
	@Column(name = "RECOMMENDATIONS", columnDefinition = "VARCHAR")
	private String recommend;
	
	@Column(name = "EVIDENCE_PHOTO", columnDefinition = "VARCHAR")
	private String evidencePhoto;
	
	
	@Column(name = "PHOTO", columnDefinition = "VARCHAR")
	private String photo;
	
	@Column(name = "INSP_SIG", columnDefinition = "VARCHAR")
	private String inspSignature;
	
	@Column(name = "OWNER_SIG", columnDefinition = "VARCHAR")
	private String ownerSignature;
	


	@Column(name = "AUDIO_FILE", columnDefinition = "VARCHAR")
	private String audioFile;

	@Column(name = "WEATHER_INFO", columnDefinition = "VARCHAR")
	private String weatherInfo;
	@Column(name = "DELETE_STATUS", length = 45, columnDefinition = "BIGINT(255)")
	private Integer deleteStatus;
}
