package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="shipment_details")
@Getter
@Setter
public class ShipmentDetails implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="CITY_WAREHOUSE_ID")
	private CityWarehouse cityWarehouse;
	
	@Column(name="PACKING_UNIT")
	private String packingUnit;
	
	@Column(name="PACKING_QTY")
	private String packingQty;
	
	@ManyToOne
	@JoinColumn(name="SHIPMENT_ID")
	private Shipment shipment;	
	
	@Column(name="RECALLING_STATUS")
	private String recallingstatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLANTING_ID")
	private Planting planting;
	
	@Column(name = "QR_CODE_ID")
	private String qrCodeId;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Transient
	private Packing sr;
}
