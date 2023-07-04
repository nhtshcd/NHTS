package com.sourcetrace.eses.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.*;

@Entity
@Table(name="PRODUCT_TRANSFER_DETAIL")
@Getter
@Setter
@Audited
public class ProductTransferDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "BATCH_NO", columnDefinition = "VARCHAR(255)")
	private String batchNo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BlOCK_ID")
	private FarmCrops blockId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PLANTING_ID")
	private Planting planting;
	
	@Column(name = "PRODUCT")
	private String product;
	
	@Column(name = "VARIETY")
	private String variety;
	
	@Column(name = "AVAILABLE_WEIGHT")
	private Double availableWeight;

	@Column(name = "TRANSFERRED_WEIGHT")
	private Double transferredWeight;
	
	@Column(name = "RECEIVED_WEIGHT")
	private Double receivedWeight;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_TRANSFER_ID")
	private ProductTransfer productTransfer;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Transient
	private CityWarehouse ctt;

}
