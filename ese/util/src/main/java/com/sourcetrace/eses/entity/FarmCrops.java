package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OrderBy;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.*;

/**
 * FarmCrops generated by hbm2java
 */
@Entity
@Table(name = "farm_crops")
@Setter
@Getter
@Audited
public class FarmCrops extends ParentEntity {

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "FARM_ID")
	private Farm farm;

	@ManyToOne
	@JoinColumn(name = "EXPORTER_ID")
	private ExporterRegistration exporter;

	// @ManyToOne(cascade=CascadeType.ALL)
	@Column(name = "CROP_SEASON")
	private String cropSeason;

	@Column(name = "BLOCK_ID")
	private String blockId;

	@Column(name = "BLOCK_NAME")
	private String blockName;

	@Column(name = "FARM_CROP_ID")
	private String farmCropId;

	@Column(name = "CULT_AREA")
	private String cultiArea;

	@Column(name = "LOT_NO")
	private String lotNo;

	@Column(name = "PLOTTING_STATUS")
	private String plottingStatus;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PLOTTING")
	private CoordinatesMap plotting;

	@Column(name = "REVISION_NO")
	private long revisionNo;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "FARMER_ID")
	private Long farmerId;

	@Column(name = "MSG_NO")
	private String msgNo;

	@OneToMany(mappedBy = "farmCrops", fetch = FetchType.LAZY,cascade=CascadeType.DETACH)
	@OrderBy(clause = "ID ASC")
	private Set<Planting> planting;

}
