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

@Entity
@Table(name="sorting")
@Getter
@Setter
public class Sorting extends ParentEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "FARM_CROPS_ID")
	private FarmCrops farmCrops;
	
	@Column(name="HARVEST_QTY")
	private Double qtyHarvested;

	@Column(name="REJ_QTY")
	private Double qtyRejected;
	
	@Column(name="NET_QTY")
	private Double qtyNet;
	
	@Column(name="STATUS")
	private Integer status;
	@Column(name = "MSG_NO")
	private String msgNo;
	
	

	@Column(name = "DRIVER_CONTACT", length = 45, columnDefinition = "VARCHAR(255)")
	private String driverCont;// driver contact

	@Column(name = "DRIVER_NAME", length = 45, columnDefinition = "VARCHAR(255)")
	private String driverName;// driver name

	@Column(name = "TRUCK_TYPE", length = 45, columnDefinition = "VARCHAR(255)")
	private String truckType;// truck type

	@Column(name = "TRUCK_NO", length = 45, columnDefinition = "VARCHAR(255)")
	private String truckNo;// truck number
	
	@ManyToOne
	@JoinColumn(name = "PLANTING_ID")
	private Planting planting;
	
	@Column(name = "QR_CODE_ID")
	private String qrCodeId;
	
}
