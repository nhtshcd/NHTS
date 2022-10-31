package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TXN_HEADER")
public class TransactionHeader implements java.io.Serializable{
private Long id;
	private String serialNo;
	private String versionNo;
	private String agentId;
    private String servPointId;
    @Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "SERVICE_POINT_ID", nullable = false)
	public String getServPointId() {
		return servPointId;
	}

	public void setServPointId(String servPointId) {
		this.servPointId = servPointId;
	}
	@Column(name = "SERIAL_NO", nullable = false)
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	@Column(name = "VERSION_NO", nullable = false)
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	@Column(name = "AGENT_ID", nullable = false)
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

			
}
