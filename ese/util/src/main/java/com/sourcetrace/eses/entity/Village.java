/*
 * Village.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.entity;

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

import com.sourcetrace.eses.util.StringUtil;
import org.hibernate.envers.*;

@Entity
 @FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))@Filters(@org.hibernate.annotations.Filter(name="branchFilter", condition="branch_id in ( :branchIdParam )"))
@Table(name = "village")
@Audited
public class Village {

	private long id;
	private String name;
	private String code;
	private Municipality city;
	private GramPanchayat gramPanchayat;
	private long revisionNo;
	private String branchId;
	private String seq;

    /**
	 * Transient variable
	 */
	private List<String> branchesList;


	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {

		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(long id) {

		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Column(name = "NAME",  length = 200)
	public String getName() {

		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	@Column(name = "CODE",  length = 20)

	public String getCode() {

		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code
	 *            the new code
	 */
	public void setCode(String code) {

		this.code = code;
	}

	// @NotNull(message = "empty.city")
	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "CITY_ID", nullable = false)
	public Municipality getCity() {

		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city
	 *            the new city
	 */
	public void setCity(Municipality city) {

		this.city = city;
	}

	/**
	 * Gets the gram panchayat.
	 *
	 * @return the gram panchayat
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRAM_PANCHAYAT_ID", nullable = false)
	public GramPanchayat getGramPanchayat() {

		return gramPanchayat;
	}

	/**
	 * Sets the gram panchayat.
	 *
	 * @param gramPanchayat
	 *            the new gram panchayat
	 */
	public void setGramPanchayat(GramPanchayat gramPanchayat) {

		this.gramPanchayat = gramPanchayat;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return name;
	}

	/**
	 * Gets the village name.
	 *
	 * @return the village name
	 */
	@Transient
	public String getVillageName() {

		StringBuffer villageName = new StringBuffer();
		if (!StringUtil.isEmpty(code)) {
			villageName.append(code);
			villageName.append(" - ");
		}
		if (!StringUtil.isEmpty(name)) {
			villageName.append(name);
		}
		return villageName.toString();
	}

	/**
	 * Sets the revision no.
	 *
	 * @param revisionNo
	 *            the new revision no
	 */
	public void setRevisionNo(long revisionNo) {

		this.revisionNo = revisionNo;
	}

	/**
	 * Gets the revision no.
	 *
	 * @return the revision no
	 */
	@Column(name = "REVISION_NO",  length = 20)

	public long getRevisionNo() {

		return revisionNo;
	}

	@Column(name = "BRANCH_ID",  length = 20)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}


	@Column(name = "SEQ",  length = 20)
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}


    @Transient
	public List<String> getBranchesList() {
		return branchesList;
	}

	public void setBranchesList(List<String> branchesList) {
		this.branchesList = branchesList;
	}




}
