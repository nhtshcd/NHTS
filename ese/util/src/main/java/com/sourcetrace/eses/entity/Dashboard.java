package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Where;


import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "dashboard")
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))

@Getter
@Setter

public class Dashboard extends ParentEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Status {
		HIDE,SHOW
	}
	
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	/*@Column(name = "BRANCH_ID")
	private String branchId;*/
	
	@Column(name = "CHART_DIV_ID")
	private String chartDivId;
	
	@Column(name = "CHART_TITLE")
	private String chartTitle;
	
	@Column(name = "CHART_SUB_TITLE")
	private String chartSubTittle;
	
	@Column(name = "X_AXIS_CATEGORY")
	private String xaxisCategory;
		
	@Column(name = "Y_AXIS_TITLE")
	private String yaxisTitle;
	
	@Column(name = "X_AXIS_TITLE")
	private String xaxisTitle;
	
	@Column(name = "TOOLTIP_SUFFIX")
	private String tooltipSuffix;
	
	@Column(name = "LEGEND_ENABLE")
	private String legendEnable;
	
	@Column(name = "STACK_LEGEND_ENABLE")
	private String stackLabelEnable;
			
	@Column(name = "Status")
	private Integer status;
	
	@Column(name = "SERIES_DATA")
	private String seriesdata;
	
	@Column(name = "COLOR")
	private String color;
	
	@Column(name = "CHART_TYPE")
	private String chartType;
	
	@Column(name = "CHART_QUERY")
	private String chartQuery;
	
	@Column(name = "SERIES_NAME")
	private String seriesName;
	
	@Column(name = "DATALABEL_FORMAT")
	private String datalabelFormat;
	
	@Column(name = "TOOLTIP_POINT_FORMAT")
	private String tooltipPointFormat;
	
	@Column(name = "CHART_ORDER")
	private int chartOrder;
	
	@Column(name = "DATE_FILTER")
	private int dateFilter;
	
	@Column(name = "DATE_FILTER_FIELD")
	private String dateFilterField;
	
	@Column(name = "EXPORTER_FILTER_FIELD")
	private String exporterFilterField;
	
	@Column(name = "GROUP_BY_FIELD")
	private String groupByField;
	
	@Column(name = "ORDER_BY_FIELD")
	private String orderByField;
	
	@Column(name = "TABS")
	private int tabs;   // 0=nhts 
	
	@Column(name = "PAGINATION")
	private String pagination; 
	
	@Column(name = "BAR_LABELS")
	private String barLabels;
}
