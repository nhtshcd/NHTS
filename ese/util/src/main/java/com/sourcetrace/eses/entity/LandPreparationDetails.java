package com.sourcetrace.eses.entity;

import java.io.Serializable;
import java.util.SortedSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "land_preparation_details")
@Getter
@Setter
public class LandPreparationDetails implements Serializable, Comparable<LandPreparationDetails> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "ACTIVITY")
	private String activity;

	@Column(name = "ACTIVITY_MODE")
	private String activityMode;

	@Column(name = "NO_OF_LABOURERS")
	private String noOfLabourers;

	@ManyToOne
	@JoinColumn(name="LAND_PREPARATION_ID")
	private LandPreparation landPreparation;

	@Override
	public int compareTo(LandPreparationDetails o) {
		// TODO Auto-generated method stub
		return this.id == null ? 0 : this.id.compareTo(o.getId());
	}

}
