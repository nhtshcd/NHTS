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
@Table(name = "scouting")
@Getter
@Setter

public class Scouting extends ParentEntity implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;
	public static enum DeleteStatus{
		NOT_DELETED,DELETED,DRAFT
	}
	
	@Column(name = "DATE")
	private Date receivedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FARM_CROP_ID")
	private FarmCrops farmCrops;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLANTING_ID")
	private Planting planting;
	
	@Column(name = "Infection", columnDefinition = "VARCHAR")
	private String perInfection;

	@Column(name = "RECOMMENDATIONS",  columnDefinition = "VARCHAR")
	private String recommendations;

	@Column(name = "Weeds_Presence", columnDefinition = "VARCHAR")
	private String weedsPresence;
	
	@Column(name = "Area_Irrigated",  columnDefinition = "VARCHAR")
	private String areaIrrrigated;

	@Column(name = "Name_Of_Disease", columnDefinition = "VARCHAR")
	private String nameOfDisease;

	@Column(name = "Name_Of_Insects_Observed", columnDefinition = "VARCHAR")
	private String nameOfInsectsObserved;
	
	@Column(name = "Irrigation_Method", columnDefinition = "VARCHAR")
	private String irrigationMethod;

	@Column(name = "Irrigation_Type", columnDefinition = "VARCHAR")
	private String irrigationType;
	
	@Column(name = "Per_Or_Number_Insects", columnDefinition = "VARCHAR")
	private String perOrNumberInsects;
	
	@Column(name = "Disease_Observed", columnDefinition = "VARCHAR")
	private String diseaseObserved;
	
	@Column(name = "Weeds_Observed", columnDefinition = "VARCHAR")
	private String weedsObserveds;
	
	@Column(name = "Insects_Observed", columnDefinition = "VARCHAR")
	private String InsectsObserved;
	
	@Column(name = "Name_Of_Weeds", columnDefinition = "VARCHAR")
	private String nameOfWeeds;
	
    @OneToMany(mappedBy = "scouting", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ScoutingDetails> ScoutingDetails;

	@Transient
	private String variety;
	
	@Transient
	private String farmerFullName;
	
	@Column(name = "DELETE_STATUS")
	private Integer status;
	
	@Column(name = "MSG_NO")
	private String msgNo;
	
	@Column(name = "SOURCE_OF_WATER")
	private String sourceOfWater;
	
}
