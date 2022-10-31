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

import lombok.Getter;
import lombok.Setter;

@Entity
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))
@Table(name = "spray_field_management")
@Getter
@Setter
public class SprayAndFieldManagement extends ParentEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "DATE")
	private Date date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FARM_CROP_ID")
	private FarmCrops farmCrops;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PLANTING_ID")
	private Planting planting;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PCBP_ID")
	private Pcbp pcbp;

	@Column(name = "AGROCHEMICAL_NAME", length = 50, columnDefinition = "VARCHAR")
	private String agroChemicalName;

	@Column(name = "PHI", length = 50, columnDefinition = "VARCHAR")
	private String phi;

	@Column(name = "DELETE_STATUS", length = 50, columnDefinition = "int")
	private Integer deleteStatus;

	@Column(name = "DATE_OF_SPRAYING")
	private Date dateOfSpraying;
	
	@Column(name = "END_DATE_OF_SPRAYING")
	private Date endDateSpray;

	@Column(name = "DOSAGE")
	private String dosage;

	@Column(name = "UOM")
	private String uom;

	@Column(name = "NAME_OF_OPERATOR")
	private String NameOfOperator;

	@Column(name = "SPRAY_MOBILE_NUMBER")
	private String sprayMobileNumber;

	@Column(name = "TYPE_APPLICANTION_EQUIPMENT")
	private String typeApplicationEquipment;

	@Column(name = "METHOD_OF_APPLICANTION")
	private String methodOfApplication;

	@Column(name = "TRAINING_STATUS_OF_SPRAY_OPERATOR")
	private String trainingStatusOfSprayOperator;

	@Column(name = "SUPPLIER_OF_THE_CHEMICAL")
	private String agrovetOrSupplierOfTheChemical;

	@Column(name = "LAST_DATE_OF_CALIBRATION")
	private Date lastDateOfCalibration;

	@Column(name = "INSECT_TARGETED")
	private String insectTargeted;

	@Transient
	private String variety;
	
	@Column(name = "MSG_NO")
	private String msgNo;
	
	@Column(name = "OPERATOR_MEDICAL_REPORT")
	private String operatorMedicalReport;
	
	

}
