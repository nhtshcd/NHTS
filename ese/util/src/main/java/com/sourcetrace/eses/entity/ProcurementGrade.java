package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.*;

/**
 * ProcurementGrade generated by hbm2java
 */
@Entity
@Table(name = "procurement_grade")
@Audited
public class ProcurementGrade implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private ProcurementVariety procurementVariety;
	private FarmCatalogue type;
	private String name;
	private String code;
	private Double price;
	private String unit;
	private Long revisionNo;
	private String createdUser;
	private Date createdDate;
	private String updatedUser;
	private Date updatedDate;
	private String latitude;
	private String longitude;
	private String ipAddr;
	private String cropHScode;

	
	private String cropCycle;

	
	private Double yield;

	
	private String harvestDays;

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "PROCUREMENT_VARIETY_ID")
	public ProcurementVariety getProcurementVariety() {
		return this.procurementVariety;
	}

	public void setProcurementVariety(ProcurementVariety procurementVariety) {
		this.procurementVariety = procurementVariety;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE", length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "PRICE")
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "UNIT", length = 15)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPE")
	public FarmCatalogue getType() {
		return type;
	}

	public void setType(FarmCatalogue type) {
		this.type = type;
	}

	@Column(name = "REVISION_NO")
	public Long getRevisionNo() {
		return this.revisionNo;
	}

	public void setRevisionNo(Long revisionNo) {
		this.revisionNo = revisionNo;
	}
	@Column(name = "NO_OF_DAYS_TO_GROW")
	public String getCropCycle() {
		return cropCycle;
	}

	public void setCropCycle(String cropCycle) {
		this.cropCycle = cropCycle;
	}
	@Column(name = "YIELD")
	public Double getYield() {
		return yield;
	}
	public void setYield(Double yield) {
		this.yield = yield;
	}
	@Column(name = "INITIAL_HARVEST_DAYS")
	public String getHarvestDays() {
		return harvestDays;
	}

	public void setHarvestDays(String harvestDays) {
		this.harvestDays = harvestDays;
	}

	@Column(name = "CREATED_USER", length = 50, columnDefinition = "VARCHAR(255)")
	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE",  length = 19)
	@CreationTimestamp
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "UPDATED_USER", length = 50, columnDefinition = "VARCHAR(255)")
	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE",  length = 19)
	@UpdateTimestamp
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	@Column(name = "LAT", length = 50, columnDefinition = "VARCHAR(255)")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	@Column(name = "LON", length = 50, columnDefinition = "VARCHAR(255)")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	
	@Column(name = "IP_ADDR", length = 50, columnDefinition = "VARCHAR(255)")
	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}


	@Column(name = "Crop_HS_code", length = 50)
	public String getCropHScode() {
		return cropHScode;
	}

	public void setCropHScode(String cropHScode) {
		this.cropHScode = cropHScode;
	}
	
	
	
}
