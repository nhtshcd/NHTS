package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.Transient;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.Audited;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))
@Table(name = "status_details")
@Getter
@Setter
public class StatusDetails implements java.io.Serializable, Comparable<StatusDetails> {
	
	public static enum Type {
		AGRODEALER, AGROCHEMICAL, PREMISES, FUMIGATOR, PERMIT, DEALER_RENEWAL, SEED_MERCHANT, SEED_MERCHANT_RENEWAL, IMPORT_APP,SEED_CERTIFICATION_SERVICES,TRANSFERSEED_CERTIFICATE
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
	@Column(name = "Status", length = 50, columnDefinition = "BIGINT(45)")
	private Integer status;
	@Column(name = "REASON", length = 50, columnDefinition = "VARCHAR(255)")
	private String reason;
	@Column(name = "type", length = 50, columnDefinition = "VARCHAR(255)")
	private String type;
	@Column(name = "DOC", length = 50, columnDefinition = "VARCHAR(255)")
	private String doc;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", columnDefinition = "VARCHAR(255)")
	private User user;
	@Column(name = "REF_ID", length = 50,columnDefinition = "VARCHAR(255)")
	private Long referenceId;
	@Override
	public int compareTo(StatusDetails o) {
		return this.id == null ? 0 : this.id.compareTo(o.getId());
	}

}
