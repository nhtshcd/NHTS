package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "DEPLOY_LOG")
public class DeploymentLog {
	private Long id;
	private Date date;
	private String version;
	private String module;
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	DeploymentLog() {

	}

	public DeploymentLog(String module, String version) {

		this.module = module;
		this.version = version;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOG_DATE",  length = 19)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "VERSION", nullable = false)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "MODULE", nullable = false)
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}



}
