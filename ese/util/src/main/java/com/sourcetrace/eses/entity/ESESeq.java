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
 * EseSeq generated by hbm2java
 */
@Entity
@Table(name = "ese_seq", uniqueConstraints = @UniqueConstraint(columnNames = "SEQ_KEY"))
public class ESESeq implements java.io.Serializable {

	private Long id;
	private String seqKey;
	private long seqVal;

	public ESESeq() {
	}

	public ESESeq(long seqVal) {
		this.seqVal = seqVal;
	}

	public ESESeq(String seqKey, long seqVal) {
		this.seqKey = seqKey;
		this.seqVal = seqVal;
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

	@Column(name = "SEQ_KEY", unique = true, length = 45)
	public String getSeqKey() {
		return this.seqKey;
	}

	public void setSeqKey(String seqKey) {
		this.seqKey = seqKey;
	}

	@Column(name = "SEQ_VAL", nullable = false)
	public long getSeqVal() {
		return this.seqVal;
	}

	public void setSeqVal(long seqVal) {
		this.seqVal = seqVal;
	}

}
