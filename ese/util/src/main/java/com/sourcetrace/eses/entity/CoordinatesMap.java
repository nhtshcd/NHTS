package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import org.hibernate.envers.*;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CoordinatesMap generated by hbm2java
 */
@Entity
@Table(name = "COORDINATES_MAP")
@Audited
public class CoordinatesMap implements java.io.Serializable {

	public static enum typesOFCordinates {
		mainCoordinates,neighbouringDetails_farmcrops,neighbouringDetails_farm
	};
	
	public static enum Status {
		INACTIVE, ACTIVE, DELETED
	}
	private Long id;
	private Date date;
	private String agentId;
	private Integer status;
	private String area;
	private String midLatitude;
	private String midLongitude;
	private Set<Coordinates> farmCoordinates;	

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
	@Column(name = "DATE", length = 19)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "AGENT_ID", length = 200)
	public String getAgentId() {
		return this.agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	@Column(name = "STATUS")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

	

	
	

	@Column(name = "AREA", length = 100)
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "MID_LATITUDE", length = 20)
	public String getMidLatitude() {
		return this.midLatitude;
	}

	public void setMidLatitude(String midLatitude) {
		this.midLatitude = midLatitude;
	}

	@Column(name = "MID_LONGITUDE", length = 20)
	public String getMidLongitude() {
		return this.midLongitude;
	}

	public void setMidLongitude(String midLongitude) {
		this.midLongitude = midLongitude;
	}

	@OneToMany(fetch=FetchType.LAZY ,cascade=CascadeType.ALL,mappedBy="coordinatesMap")
	@OrderBy("ORDER_NO")
	public Set<Coordinates> getFarmCoordinates() {
		return farmCoordinates;
	}

	public void setFarmCoordinates(Set<Coordinates> farmCoordinates) {
		this.farmCoordinates = farmCoordinates;
	}


	
}
