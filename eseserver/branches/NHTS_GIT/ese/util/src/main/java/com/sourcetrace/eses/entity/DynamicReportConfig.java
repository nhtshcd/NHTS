package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

/**
 * DynamicReportConfig generated by hbm2java
 */
@Entity
@Table(name = "DYNAMIC_REPORT_CONFIG")
public class DynamicReportConfig implements java.io.Serializable {
	public static final String FILTER_FIELDS = "FILTER_FIELDS";
	public static final String DYNAMIC_CONFIG_DETAIL = "DYNAMIC_CONFIG_DETAIL";
	public static final String ENTITY = "ENTITY";
	public static final String OTHER_FIELD = "OTHERFIELD";
	public static final String ALIAS = "ALIAS";
	public static final String GROUP_PROPERTY = "GROUP_PROPERTY";
	public static final String LOCATION_FILTER_PROPERTY = "LOCATION_FILTER_PROPERTY";
	private Long id;
	private String report;
	private Long fetchType;
	private String gridType;
	private String status;
	private String branchId;
	private String entityName;
	private String xlsFile;
	private String alias;
	private Integer parentId;
	private String groupProperty;
	private String detailMethod;
	private String csvFile;
	private SortedSet<DynamicReportConfigFilter> dynmaicReportConfigFilters = new TreeSet<DynamicReportConfigFilter>();
	private Set<DynamicReportConfig> subGrid = new HashSet<DynamicReportConfig>(0);
	private SortedSet<DynamicReportConfigDetail> dynmaicReportConfigDetails = new TreeSet<DynamicReportConfigDetail>();
private String sortBy;
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "REPORT", length = 100)
	public String getReport() {
		return this.report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	@Column(name = "FETCH_TYPE")
	public Long getFetchType() {
		return this.fetchType;
	}

	public void setFetchType(Long fetchType) {
		this.fetchType = fetchType;
	}

	@Column(name = "GRID_TYPE", length = 2)
	public String getGridType() {
		return this.gridType;
	}

	public void setGridType(String gridType) {
		this.gridType = gridType;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "BRANCH_ID", length = 45)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "ENTITY_NAME")
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name = "XLS_FILE")
	public String getXlsFile() {
		return this.xlsFile;
	}

	public void setXlsFile(String xlsFile) {
		this.xlsFile = xlsFile;
	}

	@Column(name = "ALIAS")
	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Column(name = "PARENT_ID")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "GROUP_PROPERTY")
	public String getGroupProperty() {
		return this.groupProperty;
	}

	public void setGroupProperty(String groupProperty) {
		this.groupProperty = groupProperty;
	}

	@Column(name = "DETAIL_METHOD")
	public String getDetailMethod() {
		return this.detailMethod;
	}

	public void setDetailMethod(String detailMethod) {
		this.detailMethod = detailMethod;
	}


	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "subGrid", referencedColumnName = "PARENT_ID")
	public Set<DynamicReportConfig> getSubGrid() {
		return subGrid;
	}

	public void setSubGrid(Set<DynamicReportConfig> subGrid) {
		this.subGrid = subGrid;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dynamicReportConfig")
	@OrderBy(clause="ORDERR")
	public SortedSet<DynamicReportConfigDetail> getDynmaicReportConfigDetails() {
		return dynmaicReportConfigDetails;
	}

	public void setDynmaicReportConfigDetails(SortedSet<DynamicReportConfigDetail> dynmaicReportConfigDetails) {
		this.dynmaicReportConfigDetails = dynmaicReportConfigDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dynamicReportConfig")
	@OrderBy(clause="ORDERR")
	public SortedSet<DynamicReportConfigFilter> getDynmaicReportConfigFilters() {
		return dynmaicReportConfigFilters;
	}

	public void setDynmaicReportConfigFilters(SortedSet<DynamicReportConfigFilter> dynmaicReportConfigFilters) {
		this.dynmaicReportConfigFilters = dynmaicReportConfigFilters;
	}
	@Column(name = "SORTBY")
	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	@Column(name = "CSV_FILE")
	public String getCsvFile() {
		return this.csvFile;
	}

	public void setCsvFile(String csvFile) {
		this.csvFile = csvFile;
	}
	

}
