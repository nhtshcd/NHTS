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
@Table(name="recalling")
@Getter
@Setter
public class Recalling extends ParentEntity {

	private static final long serialVersionUID = 1L;

	@Column(name="REC_DATE")
	private Date recDate;
	
	@Column(name="REC_ENTITY")
	private String recEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPMENT_ID")
	private Shipment shipment;
	
	
	@Column(name="REC_CO_NAME")
	private String recCoordinatorName;

	@Column(name="REC_CO_CONTACT")
	private String recCoordinatorContact;
	
	@Column(name="REC_NATURE")
	private String recNature;
	
	@Column(name="REC_REASON")
	private String recReason;
	
	
	@Column(name="OPERATOR_NAME")
	private String operatorName;
	
	@Column(name="CONTACT_NO")
	private String contactNo;
	
	@Column(name="PO")
	private String po;
	
	@Column(name="INVOICE_NO")
	private String invoiceNo;
	
	@Column(name="CARRIER_NO")
	private String carrierNo;
	
	@Column(name="ACTION_RECALLER")
	private String actionByRecaller;
	
	@Column(name="ACTION_STAKEHOLDERS")
	private String actionByStakeholders;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name="STATUS_CODE")
	private Integer statusCode;
	
	@Column(name="KENYA_TRACE_CODE")
	private String kenyaTraceCode;
	
	@Column(name= "RECALLING_STATUS")
	private String recallingStatus;
	
	@OneToMany(mappedBy = "recall", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RecallDetails> recallDetails;
	
	@Column(name= "BATCH_NO")
	private String batchNo;
	
	@Column(name = "ATTACHMENT", length = 50, columnDefinition = "VARCHAR(255)")
	private String attachment;
}
