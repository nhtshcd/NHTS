package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

/**
 * SmsHistory generated by hbm2java
 */
@Entity
 @FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))@Filters(@org.hibernate.annotations.Filter(name="branchFilter", condition="branch_id in ( :branchIdParam )"))
@Table(name = "sms_history")
public class SMSHistory implements java.io.Serializable {

	
	public static final int SMS_SINGLE = 1;
    public static final int SMS_BULK = 2;

    public static final int MOBILE_NO_LENGTH = 10;
    public static final String MOBILE_NO_PATTERN = "[0-9]*";
    public static final String BULK_MOBILE_NO_PATTERN = "[0-9,]*";
    public static final int MESSAGE_LENGTH = 160;

    
	private Long id;
	private Integer smsType;
	private String smsRoute;
	private String senderMobNo;
	private String message;
	private String status;
	private String statusMsg;
	private String uuid;
	private String createUser;
	private Date createDt;
	private String lastUpdateUser;
	private Date updateDt;
	private String branchId;
	private String receiverMobNo;
	private String creationInfo;
	private String statusZ;
	private Set<SMSHistoryDetail> smsHistoryDetails = new HashSet<SMSHistoryDetail>(0);
	 private String response;
	 

	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SMS_TYPE")
	public Integer getSmsType() {
		return this.smsType;
	}

	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}

	@Column(name = "SMS_ROUTE", length = 20)
	public String getSmsRoute() {
		return this.smsRoute;
	}

	public void setSmsRoute(String smsRoute) {
		this.smsRoute = smsRoute;
	}

	@Column(name = "SENDER_MOB_NO", length = 20)
	public String getSenderMobNo() {
		return this.senderMobNo;
	}

	public void setSenderMobNo(String senderMobNo) {
		this.senderMobNo = senderMobNo;
	}

	@Column(name = "MESSAGE", columnDefinition="LONGTEXT")
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "STATUS", length = 100)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "STATUS_MSG", columnDefinition="LONGTEXT")
	public String getStatusMsg() {
		return this.statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	@Column(name = "UUID")
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "CREATE_USER", length = 45)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DT", length = 19)
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	@Column(name = "LAST_UPDATE_USER", length = 45)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DT", length = 19)
	public Date getUpdateDt() {
		return this.updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	@Column(name = "BRANCH_ID", length = 50)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@OneToMany( mappedBy = "smsHistory")
	public Set<SMSHistoryDetail> getSmsHistoryDetails() {
		return this.smsHistoryDetails;
	}

	public void setSmsHistoryDetails(Set<SMSHistoryDetail> smsHistoryDetails) {
		this.smsHistoryDetails = smsHistoryDetails;
	}

	public String getReceiverMobNo() {
		return receiverMobNo;
	}

	public void setReceiverMobNo(String receiverMobNo) {
		this.receiverMobNo = receiverMobNo;
	}

	public String getCreationInfo() {
		return creationInfo;
	}

	public void setCreationInfo(String creationInfo) {
		this.creationInfo = creationInfo;
	}
	public String getStatusZ() {
		return statusZ;
	}

	public void setStatusZ(String statusZ) {
		this.statusZ = statusZ;
	}
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
 



}
