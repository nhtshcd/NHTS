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
import javax.persistence.Transient;
import javax.transaction.TransactionScoped;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "exitInspection")
@Getter
@Setter

public class ExitInspection implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "branch_id", length = 50, columnDefinition = "VARCHAR(255)")
	private String branchId;
	@Column(name = "DATE", length = 45, columnDefinition = "VARCHAR(255)")
	private String date;
	@Column(name = "FarmerName", length = 45, columnDefinition = "VARCHAR(255)")
	private String farmerName;
	@Column(name="Farm")
	private String farm;	
	@Column(name = "ADDRESS", columnDefinition = "LONGTEXT")
	private String address;
	@Column(name = "MOBILE_NUMBER", length = 45, columnDefinition = "VARCHAR(255)")
	private String mobNumber;
	@Column(name = "CONTACT_PERSON", length = 45, columnDefinition = "VARCHAR(255)")
	private String contactPerson;  
	@Column(name = "CROP", length = 45, columnDefinition = "VARCHAR(255)")
	private String cropName;
	@Column(name = "VARIETY", length = 45, columnDefinition = "VARCHAR(255)")
	private String variety;
	@Column(name = "OBSERVATIONS", length = 45, columnDefinition = "VARCHAR(255)")
	private String observations;
	@Column(name = "RECOMMENDATIONS", length = 45, columnDefinition = "VARCHAR(255)")
	private String recommendations;
	@Column(name = "PHOTO_DOCUPLOAD", length = 45, columnDefinition = "VARCHAR(255)")
	private String photoUpload;
	@Column(name = "INSPEC_SIGNATURE", length = 45, columnDefinition = "VARCHAR(255)")
	private String inspecSignature;
	@Column(name = "OWNER_SIGNATURE", length = 45, columnDefinition = "VARCHAR(255)")
	private String ownerSignature;
	@Column(name = "AUDIO_FILE", length = 45, columnDefinition = "VARCHAR(255)")
	private String audioFile;
	@Column(name = "DeleteStatus",columnDefinition = "BIGINT(45)")
	private int deleteStatus;
	@Transient
	private String command;


}
