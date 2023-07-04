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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.*;

@Entity
@Table(name="PRODUCT_TRANSFER")
@Getter
@Setter
@Audited
public class ProductTransfer extends ParentEntity {

	private static final long serialVersionUID = 1L;
	
	public static enum type{
		PRODUCT_TRANSFER,PRODUCT_RECEPTION
	}

	@Column(name="TRANSFER_RECEIPT_ID")
	private String transferReceiptID;
	
	@Column(name="BATCH_NO")
	private String batchNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE")
	private Date date;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EXPORTER")
	private ExporterRegistration exporter;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TRANSFER_FROM")
	private Packhouse transferFrom;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TRANSFER_TO")
	private Packhouse transferTo;

	@Column(name="TRUCK_NO")
	private String truckNo;
	
	@Column(name="DRIVER_NAME")
	private String driverName;
	
	@Column(name="DRIVER_LICENSE_NUMBER")
	private String driverLicenseNumber;

	@Column(name="STATUS")
	private Integer status;
	
	@Column(name="TYPE")
	private Integer type;
	
	@Column(name="MSG_NO")
	private String msgNo;
	
	@OneToMany(mappedBy = "productTransfer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductTransferDetail> productTransferDetails;
	
}
