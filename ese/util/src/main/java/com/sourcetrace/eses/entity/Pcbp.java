package com.sourcetrace.eses.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.*;

@Entity
@Table(name="pcbp")
@Getter
@Setter
@Audited
public class Pcbp extends ParentEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "CROP_VARIETY")
	private ProcurementGrade cropvariety;
	
	
	@Column(name="TRADER_NAME")
	private String tradeName;
	
	@Column(name="REGISTRATION_NO")
	private String registrationNo;
	
	@Column(name="ACTIVE_ING")
	private String activeing;
	
	
	@Column(name="MANUFACTURER_REG")
	private String manufacturerReg;
	
	@Column(name="AGENT")
	private String agent;
	
	@Column(name="PHIIN")
	private Integer phiIn;
	
	@Column(name="DOSAGE")
	private Integer dosage;
	
	@Column(name="UOM")
	private String uom;
	
/*	@Column(name="CHEMICAL_NAME")
	private String chemicalName;*/
	

	@Column(name="STATUS")
	private Integer status;

}
