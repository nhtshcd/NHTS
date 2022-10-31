package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sourcetrace.eses.filter.ISecurityFilter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass 
public class ParentEntity implements java.io.Serializable {

	private static final long serialVersionUID = 2786505040793265508L;


	public static enum Active {
		INACTIVE, ACTIVE, DELETED, INVALID
	}

	
	public static enum Renewal_Type{
		DELEAR_RENEWAL,AGRO_CHEMICAL_RENEWAL
	}

	@Transient
	private String command;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "branch_id", length = 50, columnDefinition = "VARCHAR(255)")
	private String branchId;

	
	@Column(name = "CREATED_USER", length = 50, columnDefinition = "VARCHAR(255)")
	private String createdUser;
	

	@Column(name = "LAT", length = 50, columnDefinition = "VARCHAR(255)")
	private String latitude;

	
	@Column(name = "LON", length = 50, columnDefinition = "VARCHAR(255)")
	private String longitude;
	
	
	@Column(name = "IP_ADDR", length = 50, columnDefinition = "VARCHAR(255)")
	private String ipAddr;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE",  length = 19)
	//@CreationTimestamp
	private Date createdDate;
	
	@Column(name = "UPDATED_USER", length = 50, columnDefinition = "VARCHAR(255)")
	private String updatedUser;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE",  length = 19)
	//@UpdateTimestamp
	private Date updatedDate;

	@Version
	private Integer version;

	@PrePersist
	protected void onCreate() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		if(request.getSession()!=null){
			System.out.println(request.getSession().getAttribute(ISecurityFilter.USER_INFO));
		}
		this.createdDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedDate = new Date();
	}



	
}
