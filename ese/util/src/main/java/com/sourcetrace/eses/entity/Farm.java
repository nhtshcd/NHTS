package com.sourcetrace.eses.entity;
import java.util.List;
import java.util.Set;

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
import javax.persistence.TemporalType;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.ParamDef;

import lombok.Getter;
import lombok.Setter;

@Entity
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))
@Table(name = "farm")
@Getter 
@Setter
public class Farm extends ParentEntity {		

	private static final long serialVersionUID = 1L;

	@Column(name = "FARM_CODE",columnDefinition = "VARCHAR(255)")
	private String farmCode;
	
	@Column(name = "FARM_NAME",columnDefinition = "VARCHAR(255)")
	private String farmName;
	
	@Column(name = "FARM_ID")
	private String farmId;
	
	@Column(name = "TOTAL_LAND_HOLDING",columnDefinition = "VARCHAR(255)")
	private String totalLandHolding;
	
	@Column(name = "PROPOSED_PLANTING_AREA",columnDefinition = "VARCHAR(255)")
	private String proposedPlanting;
	
	@Column(name = "PLOTTING_STATUS")
	private String plottingStatus;
	
	@Column(name = "LAND_OWNERSHIP",columnDefinition = "VARCHAR(255)")
	private String landOwnership;
	
	@Column(name = "IS_ADDRESS_SAME",columnDefinition = "TINYINT")
	private String isAddressSame;	
	
	@Column(name = "ADDRESS",columnDefinition = "VARCHAR(255)")
	private String address;
	
	@Column(name = "PHOTO",columnDefinition = "VARCHAR(255)")
	private String photo;

	@Column(name = "LAND_REG_NO",columnDefinition = "VARCHAR(255)")
	private String landRegNo;

	@Column(name = "LAND_TOPOGRAPHY",columnDefinition = "VARCHAR(255)")
	private String landTopography;
	
	@Column(name = "LAND_GRADIENT",columnDefinition = "VARCHAR(255)")
	private String landGradient;
	
	@Column(name = "LAND_REG_DOCS",columnDefinition = "VARCHAR(255)")
	private String landRegDocs;

	@Column(name = "SOIL_TYPE",columnDefinition = "VARCHAR(255)")
	private String soilType;
	
	@Column(name = "IRRIGATION_TYPE",columnDefinition = "VARCHAR(255)")
	private String irrigationType;

	@Column(name = "APPROACH_ROAD",columnDefinition = "VARCHAR(255)")
	private String approachRoad;
	
	@Column(name = "NO_OF_FARM_LABOURS",columnDefinition = "VARCHAR(255)")
	private Integer noFarmLabours;		
		
/*	@Column(name = "LATITUDE",columnDefinition = "VARCHAR(255)")
	private String latitude;
	
	@Column(name = "LONGITUDE",columnDefinition = "VARCHAR(255)")
	private String longitude;*/
	
	@Column(name = "OPEN_OTHER",columnDefinition = "VARCHAR(255)")
	private String ifOpenOther;
	
	@Column(name = "PROTECTED_OTHER",columnDefinition = "VARCHAR(255)")
	private String ifProtectedOther;
	
	@Column(name = "REVISION_NO",columnDefinition = "BIGINT(45)")
	private Long revisionNo;

	@ManyToOne
	@JoinColumn(name = "FARMER_ID")
	private Farmer farmer;

	@Column(name = "STATUS",columnDefinition = "INTEGER")
	private Integer status;

	@OneToMany(mappedBy = "farm", fetch = FetchType.LAZY)
	@OrderBy(clause = "ID ASC")
	private Set<FarmCrops> farmCrops;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "PLOTTING")
	private CoordinatesMap plotting;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VILLAGE_ID")
	private Village village;
	
	
	@Column(name = "MSG_NO")
	private String msgNo;
}
