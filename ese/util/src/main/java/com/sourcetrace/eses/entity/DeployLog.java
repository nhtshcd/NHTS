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

/**
 * DeployLog generated by hbm2java
 */
@Entity
@Table(name = "DEPLOY_LOG")
public class DeployLog implements java.io.Serializable {

	private Integer id;
	private String version;
	private String module;
	private Date date;

	public DeployLog() {
	}

	public DeployLog(String module, Date date) {
		this.module = module;
		this.date = date;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "VERSION",  length = 45)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "MODULE",  length = 45)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOG_DATE",  length = 19,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}