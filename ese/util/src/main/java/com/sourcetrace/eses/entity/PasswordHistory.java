package com.sourcetrace.eses.entity;


import java.util.Date;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import lombok.Getter;
import lombok.Setter;

@Entity
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))
@Filters(@org.hibernate.annotations.Filter(name = "branchFilter", condition = "branch_id in ( :branchIdParam )"))
@Table(name = "password_history")
@Getter
@Setter
public class PasswordHistory implements Comparable<PasswordHistory>{
	public static enum Type {
			WEB_USER,MOBILE_USER
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "branch_id", length = 50, columnDefinition = "VARCHAR(255)")
	private String branchId;
	@Column(name = "createdDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdDate;
	@Column(name = "password", length = 50, columnDefinition = "VARCHAR(255)")
	private String password;
	@Column(name = "type", length = 50, columnDefinition = "VARCHAR(255)")
	private String type;
	@Column(name = "REF_ID", length = 50, columnDefinition = "VARCHAR(255)")
	private Long referenceId;

	@Override
	public int compareTo(PasswordHistory o) {
		return this.createdDate == null ? 0 : o.createdDate.compareTo(this.getCreatedDate());
	}

}
