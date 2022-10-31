package com.sourcetrace.eses.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="shipment")
@Getter
@Setter
public class Shipment extends ParentEntity {

	private static final long serialVersionUID = 1L;

	
	@Column(name="SHIPMENT_DATE")
	private Date shipmentDate;
	
	@ManyToOne
	@JoinColumn(name="PACKHOUSE_ID")
	private Packhouse packhouse;
	
	@ManyToOne
	@JoinColumn(name="CUSTOMER_ID")
	private Customer customer;
	
	@Column(name="PRODUCE_CONSIGNMENT_NO")
	private String pConsignmentNo;
	
	@Column(name="KENYA_TRACE_CODE")
	private String kenyaTraceCode;
	
	@Column(name="QR_CODE")
	private String qrCode;
	
	@Column(name="TOTAL_SHIPMENT_QTY")
	private Double totalShipmentQty;

	@Column(name="STATUS")
	private Integer status;
	
	@OneToMany(mappedBy = "shipment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ShipmentDetails> shipmentDetails;
	

	@Column(name = "MSG_NO")
	private String msgNo;

}
