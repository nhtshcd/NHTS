package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

/**
 * LocHistory generated by hbm2java
 */
@Entity
 @FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))@Filters(@org.hibernate.annotations.Filter(name="branchFilter", condition="branch_id in ( :branchIdParam )"))
@Table(name = "loc_history")
public class LocHistory implements java.io.Serializable {

	private Long id;
	private Date txnTime;
	private String serialNo;
	private String longitude;
	private String endLatitude;
	private String endLongitude;
	private String latitude;
	private String agentId;
	private String branchId;
	private Date createdTime;
	private String distance;
	private String address;
	private Long accuracy;
	private String netStatus;
	private String gpsStatus;
	private Date startTime;
	private Date endTime;

	public LocHistory() {
	}

	public LocHistory(Date txnTime, String serialNo, String longitude, String endLatitude, String endLongitude,
			String latitude, String agentId, String branchId, Date createdTime, String distance, String address,
			Long accuracy, String netStatus, String gpsStatus, Date startTime, Date endTime) {
		this.txnTime = txnTime;
		this.serialNo = serialNo;
		this.longitude = longitude;
		this.endLatitude = endLatitude;
		this.endLongitude = endLongitude;
		this.latitude = latitude;
		this.agentId = agentId;
		this.branchId = branchId;
		this.createdTime = createdTime;
		this.distance = distance;
		this.address = address;
		this.accuracy = accuracy;
		this.netStatus = netStatus;
		this.gpsStatus = gpsStatus;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TXN_TIME", length = 19)
	public Date getTxnTime() {
		return this.txnTime;
	}

	public void setTxnTime(Date txnTime) {
		this.txnTime = txnTime;
	}

	@Column(name = "SERIAL_NO", length = 45)
	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "LONGITUDE", length = 20)
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "END_LATITUDE")
	public String getEndLatitude() {
		return this.endLatitude;
	}

	public void setEndLatitude(String endLatitude) {
		this.endLatitude = endLatitude;
	}

	@Column(name = "END_LONGITUDE")
	public String getEndLongitude() {
		return this.endLongitude;
	}

	public void setEndLongitude(String endLongitude) {
		this.endLongitude = endLongitude;
	}

	@Column(name = "LATITUDE", length = 20)
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "AGENT_ID", length = 45)
	public String getAgentId() {
		return this.agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	@Column(name = "BRANCH_ID", length = 50)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", length = 19)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "DISTANCE", length = 10)
	public String getDistance() {
		return this.distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ACCURACY")
	public Long getAccuracy() {
		return this.accuracy;
	}

	public void setAccuracy(Long accuracy) {
		this.accuracy = accuracy;
	}

	@Column(name = "NET_STATUS")
	public String getNetStatus() {
		return this.netStatus;
	}

	public void setNetStatus(String netStatus) {
		this.netStatus = netStatus;
	}

	@Column(name = "GPS_STATUS")
	public String getGpsStatus() {
		return this.gpsStatus;
	}

	public void setGpsStatus(String gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
