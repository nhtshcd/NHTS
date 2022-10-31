package com.sourcetrace.eses.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_ACTIVE_AND_INACTIVE_HOSTORY")
@Getter
@Setter
public class UserActiveAndInActiveHostory extends ParentEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "DATE")
	private Date date;
	
	@Column(name = "TIME")
	private String time;
	
	@Column(name = "LOGGED_USER")
	private String loggedUser;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "ACTIVITY")
	private Integer activity;
	
	@Column(name = "TYPE")
	private Integer type;
	
	@Column(name = "EXPORTER")
	private Long agroChDealer;
	
/*	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "USER_ID")
	private Long userId;*/
	

}
