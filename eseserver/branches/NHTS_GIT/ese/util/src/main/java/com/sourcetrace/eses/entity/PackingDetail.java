package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.TransactionScoped;

import org.hibernate.annotations.OrderBy;
import lombok.Data;

@Entity
@Table(name = "packing_detail")
@Data

public class PackingDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)

	private Long id;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BlOCK_ID")
	private FarmCrops blockId;

	@Column(name = "BATCH_NO", columnDefinition = "VARCHAR(255)")
	private String batchNo;

	@Column(name = "QUANTITY", length = 50, columnDefinition = "VARCHAR(255)")
	private Double quantity;

	@Column(name = "PRICE", length = 50, columnDefinition = "VARCHAR(255)")
	private Double price;
	
	@Column(name = "REJECT_WT", length = 50, columnDefinition = "VARCHAR(255)")
	private Double rejectWt;

	@Column(name = "BEST_BEFORE", length = 50, columnDefinition = "Date")
	private Date bestBefore;

	@ManyToOne
	@JoinColumn(name = "PACKING_ID")
	private Packing packing;
	
	@Column(name = "QR_CODE_ID")
	private String qrCodeId;
	
	@Transient
	private CityWarehouse ctt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLANTING_ID")
	private Planting planting;


}
