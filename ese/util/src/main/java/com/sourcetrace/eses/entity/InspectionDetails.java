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

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.NotAudited;

import lombok.Getter;
import lombok.Setter;

@Entity
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))
@Table(name = "inspection_details")
@Getter
@Setter
public class InspectionDetails implements Comparable<InspectionDetails> {
	public static enum Type {
		AGRODEALER, AGROCHEMICAL, PREMISES, FUMIGATOR, PERMIT, DEALER_RENEWAL, SEED_MERCHANT, SEED_MERCHANT_RENEWAL, IMPORT_APP,SEED_DEALER,SEED_CERTIFICATION_SERVICES,
		SEED_CROP_INS_REQ,GROWER,BREEDER_SEED,SEED_SAMPLE_REQUEST,TRANSFERSEED_CERTIFICATE,VARIETY_INCLUSION,EXPROTER_REG, Farmer
	}

	public static enum Status {
		INACTIVE, APPROVED, REJECTED
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "branch_id", length = 50, columnDefinition = "VARCHAR(255)")
	private String branchId;
	@Column(name = "createdDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdDate;
	@Column(name = "comments", length = 50, columnDefinition = "VARCHAR(255)")
	private String comments;
	@Column(name = "type", length = 50, columnDefinition = "VARCHAR(255)")
	private String type;
	@Column(name = "SUITABILITY_PREMISES", length = 50, columnDefinition = "VARCHAR(255)")
	private String suitabilityPremises;

	@Column(name = "PART_APPLN_INFO", length = 50, columnDefinition = "VARCHAR(255)")
	private String partApplInfo;

	@Column(name = "DOC", columnDefinition = "VARCHAR(255)")
	private String doc;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", columnDefinition = "VARCHAR(255)")
	private User user;
	@Column(name = "REF_ID", length = 50, columnDefinition = "VARCHAR(255)")
	private Long referenceId;

	@Override
	public int compareTo(InspectionDetails o) {
		return this.id == null ? 0 : this.id.compareTo(o.getId());
	}

}
