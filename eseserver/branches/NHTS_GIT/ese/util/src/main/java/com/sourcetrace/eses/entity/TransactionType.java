package com.sourcetrace.eses.entity;
import static javax.persistence.GenerationType.IDENTITY;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.ParamDef;
import lombok.Getter;
import lombok.Setter;
@Entity
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))@Filters(@org.hibernate.annotations.Filter(name="branchFilter", condition="branch_id in ( :branchIdParam )"))
@Table(name = "txn_type")
@Getter @Setter
public class TransactionType implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	@Column(name = "CODE",columnDefinition = "VARCHAR(255)")
    private String code;
	@Column(name = "NAME",columnDefinition = "VARCHAR(255)")
    private String name;
}
