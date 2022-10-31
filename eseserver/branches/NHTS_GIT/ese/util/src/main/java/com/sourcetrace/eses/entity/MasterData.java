package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_DATA")
public class MasterData implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String code;
	private String masterType;
	private String name;
	
	private String contactPersonName;
	private String mobileNo;
	private String landlineNo;
	private String emailAddress;
	private String address;
	private long revisionNo;
	
	protected static final String MANDI_TRADER = "MANDI TRADER"; 
	protected static final String MANDI_AGGREGATOR = "MANDI AGGREGATOR"; 
	protected static final String FARM_AGGREGATOR = "FARM AGGREGATOR";
	protected static final String PRODUCE_IMPORTER = "PRODUCE IMPORTER";
	

	
	
	public static enum masterTypes {
		MANDI_TRADER, MANDI_AGGREGATOR, FARM_AGGREGATOR, FPO, CIG, FIG, PRODUCE_IMPORTER,FPC,IMPORTER,AGRICULTURE_COMPANY,FARMER_GROUP_FG,CUSTOMER,CASH_PURCHASE
	};
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "CODE", length = 20)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "MASTER_TYPE", length = 2)
	public String getMasterType() {
		return masterType;
	}
	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}
	
	@Column(name = "NAME", length = 40)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "CONTACT_PERSON_NAME", length = 30)
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	
	@Column(name = "MOBILE_NO", length = 10)
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	@Column(name = "LANDLINE_NO", length = 10)
	public String getLandlineNo() {
		return landlineNo;
	}
	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}
	
	@Column(name = "EMAIL_ADDRESS", length = 40)
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	@Column(name = "ADDRESS", length = 255)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "REVISION_NO", length = 20)
	public long getRevisionNo() {
		return revisionNo;
	}
	public void setRevisionNo(long revisionNo) {
		this.revisionNo = revisionNo;
	}
	
	
}
