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
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "audit_request")
@Data
public class AuditRequest extends ParentEntity implements java.io.Serializable {

	public static enum InspectionType {
		FARM_INSPECTION,SCOUTING,SPRY_FIELD_MANAGEMENT
	}
	public static enum DeleteStatus{
		NOT_DELETED,DELETED
	}
	private static final long serialVersionUID = 1L;

	@Column(name = "REQUEST_DATE")
	private Date reqDate;

	@Transient
	private String reqDateStr;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FARM_CROP_ID")
	private FarmCrops farmCrops;
	
	@Column(name = "INSPECTION_TYPE")
	private String insType;
	
	@Column(name = "AUDIT_STATUS")
	private Integer auditStatus;

	@Column(name = "REJECT_REASON")
	private String rejReason;
	
	@Column(name = "AUDIT_DATE")
	private Date auditDate;
	
	@Transient
	private String auditDateStr;
	
	@Column(name = "INSP_ID")
	private String inspId;
	
	@Column(name = "DELETE_STATUS")
	private Integer deleteStatus;
	
	@Transient
	private String farmer;
	
	@Transient
	private String farm;
	
	@Transient
	private String cropDataId;
	
	@Transient
	private String variety;
	
}
