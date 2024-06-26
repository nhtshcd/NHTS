package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.Transient;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.*;

/**
 * GramPanchayat generated by hbm2java
 */
@Entity
@Table(name = "GRAM_PANCHAYAT")
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))
@Audited
public class GramPanchayat implements java.io.Serializable {

	private Long id;
	private String name;
	private String code;
	private Municipality city;
	private Long revisionNo;
	private String branchId;
	private Set<Village> villages = new HashSet<>();

	 /**
	  * Transient variable
	 */
	private List<String> branchesList;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME",  length = 35)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE",  length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "CITY_ID",updatable=true)
	public Municipality getCity() {
		return this.city;
	}

	public void setCity(Municipality city) {
		this.city = city;
	}

	@Column(name = "REVISION_NO")
	public Long getRevisionNo() {
		return this.revisionNo;
	}

	public void setRevisionNo(Long revisionNo) {
		this.revisionNo = revisionNo;
	}

	@Column(name = "BRANCH_ID", length = 50)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gramPanchayat")
 	public Set<Village> getVillages() {
		return villages;
	}

	public void setVillages(Set<Village> village) {
		this.villages = village;
	}
	
	@Transient
	public List<String> getBranchesList() {
		return branchesList;
	}

	public void setBranchesList(List<String> branchesList) {
		this.branchesList = branchesList;
	}



}
