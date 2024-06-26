package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Language generated by hbm2java
 */
@Entity
@Table(name = "language", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Language implements java.io.Serializable {

	private Long id;
	private String code;
	private String name;
	private Integer webStatus;
	private Integer surveyStatus;

	public Language() {
	}

	public Language(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public Language(String code, String name, Integer webStatus, Integer surveyStatus) {
		this.code = code;
		this.name = name;
		this.webStatus = webStatus;
		this.surveyStatus = surveyStatus;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "code", unique = true,  length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name",  length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "web_status")
	public Integer getWebStatus() {
		return this.webStatus;
	}

	public void setWebStatus(Integer webStatus) {
		this.webStatus = webStatus;
	}

	@Column(name = "survey_status")
	public Integer getSurveyStatus() {
		return this.surveyStatus;
	}

	public void setSurveyStatus(Integer surveyStatus) {
		this.surveyStatus = surveyStatus;
	}

}
