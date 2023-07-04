package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

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
import javax.transaction.TransactionScoped;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.*;

@Entity
@Table(name = "scouting_details")
@Getter
@Setter
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class ScoutingDetails implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "SCOUT_DATE")
	private Date scoutDate;

	@Column(name = "AREA_SCOUTED", length = 45, columnDefinition = "VARCHAR")
	private String areaScouted;

	@Column(name = "NO_OF_PLANTS", length = 45)
	private Integer noOfPlants;

	@Column(name = "PBM_OBSERVED", length = 45, columnDefinition = "VARCHAR")
	private String pbmObserved;
	
	@Column(name = "OTHER_PBM_OBSERVED", length = 45, columnDefinition = "VARCHAR")
	private String otherPbmObserved;
	
	@Column(name = "NO_OF_PBM_PLANTS", length = 45)
	private Integer noOfPbmPlants;
	
	@Column(name = "SOLUTION", length = 45, columnDefinition = "VARCHAR")
	private String solution;
	
	@Column(name = "SCOUT_INITIALS", length = 45, columnDefinition = "VARCHAR")
	private String scoutInitials;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCOUTING_ID")
	private Scouting scouting;

	@Transient
	private String dateStr;
	
}
