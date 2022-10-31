package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Farmcatalogue generated by hbm2java
 */
@Entity
@Table(name = "farm_catalogue")
public class FarmCatalogueMaster implements java.io.Serializable {

	public static final Integer STATUS_SURVEY=3;

	private Long id;
	private Integer status;
	private String name;
	private Integer typez;
	private String branchId;
	private long revisionNo;
	

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "STATUS", length = 3)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "NAME", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CATALOGUE_TYPEZ")
	public Integer getTypez() {
		return this.typez;
	}

	public void setTypez(Integer typez) {
		this.typez = typez;
	}
	@Column(name = "BRANCH_ID")
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	@Column(name = "REVIOSION_NO")
	public long getRevisionNo() {
		return revisionNo;
	}

	public void setRevisionNo(long revisionNo) {
		this.revisionNo = revisionNo;
	}
	
	

}
