package com.sourcetrace.eses.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="harvest")
@Getter
@Setter
public class Harvest extends ParentEntity {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_HARVESTED", length = 19)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "FARM_CROPS_ID")
	private FarmCrops farmCrops;

	@Column(name="NO_STEMS")
	private Double noStems;

	@Column(name="QUANTITY_HARVESTED")
	private Double qtyHarvested;
	
	@Column(name="YIELDS_HARVESTED")
	private Double yieldsHarvested;
	
	@Column(name="EXPECTED_YIELDS_VOLUME")
	private Double expctdYieldsVolume;
	
	@Column(name="NO_UNITS")
	private Integer noUnits;
	
	@Column(name="NAME_HARVESTER")
	private String nameHarvester;

	@Column(name="HARVEST_EQUIPMENT")
	private String harvestEquipment;
	
	@Column(name="PACKING_UNIT")
	private String packingUnit;
	
	@Column(name="DELIVERY_TYPE")
	private String deliveryType;
	
	@Column(name="PRODUCE_ID")
	private String produceId;
	
	@Column(name="OBSERVATION_PHI")
	private String observationPhi;	

	@Column(name="STATUS")
	private Integer status;
	
	@Column(name = "MSG_NO")
	private String msgNo;
	
	@Column(name = "HARVEST_TYPE")
	private String harvestType;
	
	@ManyToOne
	@JoinColumn(name = "PLANTING_ID")
	private Planting planting;
	
}
