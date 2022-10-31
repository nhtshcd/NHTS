package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FarmerField generated by hbm2java
 */
@Entity
@Table(name = "farmer_field")
public class FarmerField implements java.io.Serializable {

	private Long id;
	private String name;
	private String type;
	private String typeName;
	private Long parent;
	private Long status;
	private String field;
	private Long dataLevelId;
	private Long others;
	private Long farmerProfileExport;

	public FarmerField() {
	}

	public FarmerField(String name, String type, String typeName, Long parent, Long status, String field,
			Long dataLevelId, Long others, Long farmerProfileExport) {
		this.name = name;
		this.type = type;
		this.typeName = typeName;
		this.parent = parent;
		this.status = status;
		this.field = field;
		this.dataLevelId = dataLevelId;
		this.others = others;
		this.farmerProfileExport = farmerProfileExport;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TYPE", length = 3)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "TYPE_NAME", length = 45)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "PARENT")
	public Long getParent() {
		return this.parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	@Column(name = "STATUS")
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "FIELD", length = 45)
	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Column(name = "DATA_LEVEL_ID")
	public Long getDataLevelId() {
		return this.dataLevelId;
	}

	public void setDataLevelId(Long dataLevelId) {
		this.dataLevelId = dataLevelId;
	}

	@Column(name = "OTHERS")
	public Long getOthers() {
		return this.others;
	}

	public void setOthers(Long others) {
		this.others = others;
	}

	@Column(name = "farmerProfileExport")
	public Long getFarmerProfileExport() {
		return this.farmerProfileExport;
	}

	public void setFarmerProfileExport(Long farmerProfileExport) {
		this.farmerProfileExport = farmerProfileExport;
	}

}
