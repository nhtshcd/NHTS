package com.sourcetrace.eses.entity;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "land_preparation")
@Getter
@Setter
public class LandPreparation extends ParentEntity {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EVENT_DATE", length = 19)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "FARM_CROPS_ID")
	private Farm farm;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "STATUS_CODE")
	private Integer status_code;
	
	@Column(name = "MSG_NO")
	private String msgNo;
	
	@ManyToOne
	@JoinColumn(name = "FARM_CROPS_BLOCKID")
	private FarmCrops farmCrops;


	@OneToMany(mappedBy = "landPreparation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LandPreparationDetails> landPreparationDetails;
}
