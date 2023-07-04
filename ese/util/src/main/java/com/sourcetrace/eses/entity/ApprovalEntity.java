package com.sourcetrace.eses.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.*;

@Getter
@Setter
@MappedSuperclass
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class ApprovalEntity extends ParentEntity implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static enum Type {
		AGRODEALER, AGROCHEMICAL, PREMISES, FUMIGATOR, PERMIT, DEALER_RENEWAL, SEED_MERCHANT, SEED_MERCHANT_RENEWAL, IMPORT_APP,SEED_CERTIFICATION_SERVICES
	}

	public static enum Status {
		INACTIVE, APPROVED, REJECTED
	}


	@Column(name = "Reg_No", length = 50, columnDefinition = "VARCHAR(255)")
	private String regNo;

	@Column(name = "APPROVAL_DATE")
	private Date approvalDate;

	@Column(name = "INSP_ID")
	private Long inspId;

	@Column(name = "EXPIRE_DATE")
	private Date expireDate;

	@Column(name = "Status")
	private Integer status;
	@Column(name = "IS_ACTIVE")
	private Long isActive;

	@Transient
	private String password;
	@Transient
	private String confrimPassword;
	@Transient
	private String inspComment;
	@Transient
	private String changePassword;
	@Transient
	private String command;

}
