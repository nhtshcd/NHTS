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
import javax.persistence.Transient;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "packing")
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))

@Getter
@Setter
public class Packing extends ParentEntity {

	private static final long serialVersionUID = 1L;

	public static enum Status {
		INACTIVE, ACTIVE, APPROVE, REJECT, DELETED, EXPORTER_INSPECTION
	}

	@Column(name = "PACKING_DATE")
	private Date packingDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PACKHOUSE")
	private Packhouse packHouse;

	@Column(name = "PACKER_NAME")
	private String packerName;

	@Column(name = "BATCH_NO")
	private String batchNo;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "TOTAL_WT")
	private Double totWt;

	@OneToMany(mappedBy = "packing", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PackingDetail> PackingDetails;
	@Column(name = "MSG_NO")
	private String msgNo;
	
	@Column(name = "QR_CODE_ID")
	private String qrCodeId;

}
