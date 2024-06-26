package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

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

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.ParamDef;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.*;

/**
 * ProcurementProduct generated by hbm2java
 */
@Entity
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))
@Table(name = "procurement_product")
@Getter
@Setter
@Audited
public class ProcurementProduct extends ParentEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

/*	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;*/
	
	@Column(name = "NAME", length = 35)
	private String name;
	
	@Column(name = "CODE", length = 20)
	private String code;
	
	@Column(name = "REVISION_NO")
	private Long revisionNo;
	
	@Column(name = "UNIT", length = 15)
	private String unit;
	
	@Column(name = "speciesName", length = 50)
	private String speciesName;
	
/*	@Column(name = "BRANCH_ID", length = 50)
	private String branchId;*/
	
	@OneToMany(mappedBy = "procurementProduct")
	private Set<ProcurementVariety> procurementVarieties;

}
