package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import lombok.Getter;
import lombok.Setter;

@Entity
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))@Filters(@org.hibernate.annotations.Filter(name="branchFilter", condition="branch_id in ( :branchIdParam )"))
@Table(name = "txn_log")
@Getter @Setter
public class TransactionLog implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	@Column(name = "TXN_TYPE",columnDefinition = "VARCHAR(255)")
	private String txnType;
	@Column(name = "SERIAL_NO",columnDefinition = "VARCHAR(255)")
	private String serialNo;
	@Column(name = "MSG_NO",columnDefinition = "VARCHAR(255)")
	private String msgNo;
	@Column(name = "RESETN_CNT",columnDefinition = "VARCHAR(255)")
	private String resentCount;
	@Column(name = "AGENT_ID",columnDefinition = "VARCHAR(255)")
	private String agentId;
	@Column(name = "OPER_TYPE",columnDefinition = "VARCHAR(255)")
	private String operationType;
	@Column(name = "MODE",columnDefinition = "VARCHAR(255)")
	private String mode;
	@Column(name = "VERSION",columnDefinition = "VARCHAR(255)")
	private String version;
	@Column(name = "REQUEST_LOG",columnDefinition = "VARCHAR(255)")
	private String requestLog;
	@Column(name = "STATUS",columnDefinition = "VARCHAR(255)")
	private int status;
	@Column(name = "STATUS_CODE",columnDefinition = "VARCHAR(255)")
	private String statusCode;
	@Column(name = "STATUS_MSG	",columnDefinition = "VARCHAR(255)")
	private String statusMsg;
	@Column(name = "BRANCH_ID",columnDefinition = "VARCHAR(255)")
	private String branchId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DT",  length = 19)
	private Date createDt;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TXN_TIME",  length = 19)
	private Date txnTime;
	
	@Transient
	private StringBuffer requestLogStringBuffer;

	
}
