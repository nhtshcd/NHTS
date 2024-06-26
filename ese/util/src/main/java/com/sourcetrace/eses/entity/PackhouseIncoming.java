package com.sourcetrace.eses.entity;
// Generated 24 Jun, 2020 6:21:14 PM by Hibernate Tools 5.0.6.Final

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

/**
 * WarehouseProduct generated by hbm2java
 */
@Entity
@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )")
@Table(name = "packhouse_incoming")
@Getter
@Setter
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class PackhouseIncoming extends ParentEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum StockType {
		WAREHOUSE_STOCK, AGENT_STOCK
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OFFLOADING_DATE", length = 19)
	private Date offLoadingDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WAREHOUSE_ID")
	private Packhouse packhouse;

	@Column(name = "TOTAL_WEIGHT", length = 10, columnDefinition = "double")
	private double totalWeight;// Total weight

	@Column(name = "DELIVERY_NOTE", length = 45, columnDefinition = "VARCHAR(255)")
	private String deliveryNote;

	@Column(name = "DRIVER_CONTACT", length = 45, columnDefinition = "VARCHAR(255)")
	private String driverCont;// driver contact

	@Column(name = "DRIVER_NAME", length = 45, columnDefinition = "VARCHAR(255)")
	private String driverName;// driver name

	@Column(name = "RECEIPT_NO", length = 45, columnDefinition = "VARCHAR(255)")
	private String receiptNo;// receipt_no

	@Column(name = "TRUCK_TYPE", length = 45, columnDefinition = "VARCHAR(255)")
	private String truckType;// truck type

	@Column(name = "TRUCK_NO", length = 45, columnDefinition = "VARCHAR(255)")
	private String truckNo;// truck number

	@Column(name = "STATUS", length = 45, columnDefinition = "INT(25)")
	private Integer status;
	
	@Column(name = "BATCH_NO", length = 45, columnDefinition = "VARCHAR(255)")
	private String batchNo;// truck number
	
	@Column(name = "MSG_NO")
	private String msgNo;

	
	
	@OneToMany(mappedBy = "packhouseIncoming", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PackhouseIncomingDetails> packhouseIncomingDetails;
}
