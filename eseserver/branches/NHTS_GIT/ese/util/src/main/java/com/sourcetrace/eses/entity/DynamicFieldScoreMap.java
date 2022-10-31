package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DynamicFieldScoreMap generated by hbm2java
 */
@Entity
@Table(name = "DYNAMIC_FIELD_SCORE_MAP")
public class DynamicFieldScoreMap implements java.io.Serializable {

	private Long id;
	private String catalogueCode;
	private Integer score;
	private Double percentage;
	private DynamicMenuFieldMap dynamicMenuFieldMap;

	public DynamicFieldScoreMap() {
	}
	
	public DynamicFieldScoreMap(Long id, String catalogueCode, Integer score, Double percentage,
			DynamicMenuFieldMap dynamicMenuFieldMap) {
		
		this.id = id;
		this.catalogueCode = catalogueCode;
		this.score = score;
		this.percentage = percentage;
		this.dynamicMenuFieldMap = dynamicMenuFieldMap;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	

	@ManyToOne()
	@JoinColumn(name="DYNAMIC_MENU_FIELD_MAP_ID")
public DynamicMenuFieldMap getDynamicMenuFieldMap() {
		return dynamicMenuFieldMap;
	}


	public void setDynamicMenuFieldMap(DynamicMenuFieldMap dynamicMenuFieldMap) {
		this.dynamicMenuFieldMap = dynamicMenuFieldMap;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CATALOGUE_CODE", length = 45)
	public String getCatalogueCode() {
		return this.catalogueCode;
	}

	public void setCatalogueCode(String catalogueCode) {
		this.catalogueCode = catalogueCode;
	}
	
	

	@Column(name = "SCORE")
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "PERCENTAGE", precision = 45)
	public Double getPercentage() {
		return this.percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

}
