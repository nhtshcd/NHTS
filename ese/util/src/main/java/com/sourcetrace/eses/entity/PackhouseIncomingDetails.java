package com.sourcetrace.eses.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.*;

@Entity
@Table(name = "packhouse_incoming_details")
@Getter
@Setter
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class PackhouseIncomingDetails implements Serializable, Comparable<PackhouseIncomingDetails> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FARM_CROP_ID")	
	private FarmCrops farmcrops;
	
	@Column(name = "RECEIVED_WEIGHT", length = 10,columnDefinition = "double")
	private Double receivedWeight;
	
	@Column(name = "RECEIVED_UNITS", columnDefinition = "varchar")
	private String receivedUnits;
	
	@Column(name = "NO_UNITS", length = 10,columnDefinition = "int")
	private Integer noUnits;
	
	@Column(name = "TRANSFERRED_WEIGHT", length = 10,columnDefinition = "double")
	private Double transferWeight;
	
	@Column(name = "TOTAL_WEIGHT", length = 10,columnDefinition = "double")
	private Double totalWeight;
	
	@ManyToOne
	@JoinColumn(name="PACKHOUSE_INCOMING_ID")
	private PackhouseIncoming packhouseIncoming; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLANTING_ID")	
	private Planting planting;
	
	@Column(name = "QR_CODE_ID")
	private String qrCodeId;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "SORTING_ID")
	private String sortingId;
	
	@Override
	public int compareTo(PackhouseIncomingDetails o) {
		// TODO Auto-generated method stub
		return this.id == null ? 0 : this.id.compareTo(o.getId());
	}
	
	@Transient
	private CityWarehouse cw;
	
	@Transient
	private Sorting sr;
}
