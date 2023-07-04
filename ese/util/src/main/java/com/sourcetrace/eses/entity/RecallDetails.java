package com.sourcetrace.eses.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.*;

@Entity
@Table(name = "recall_details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class RecallDetails implements Serializable, Comparable<RecallDetails> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FARM_CROP_ID")
	private FarmCrops farmcrops;

	@Column(name = "RECEIVED_WEIGHT", length = 10, columnDefinition = "double")
	private Double receivedWeight;

	@Column(name = "RECEIVED_UNITS", columnDefinition = "varchar")
	private String receivedUnits;

	@Column(name = "BATCH_NO", columnDefinition = "varchar")
	private String batchNo;
	
	@Column(name = "LOT_NO", columnDefinition = "varchar")
	private String lotNo;
	
	@ManyToOne
	@JoinColumn(name = "SHIPMENT_DETAIL_ID")
	private ShipmentDetails shipmentdetail;

	@ManyToOne
	@JoinColumn(name = "RECALL_ID")
	private Recalling recall;
	
	
	@Column(name = "CREATED_USER", length = 50, columnDefinition = "VARCHAR(255)")
	private String createdUser;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE",  length = 19)
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name = "UPDATED_USER", length = 50, columnDefinition = "VARCHAR(255)")
	private String updatedUser;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE",  length = 19)
	@UpdateTimestamp
	private Date updatedDate;

	@Override
	public int compareTo(RecallDetails o) {
		// TODO Auto-generated method stub
		return this.id == null ? 0 : this.id.compareTo(o.getId());
	}
	
	public RecallDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLANTING_ID")
	private Planting planting;

}
