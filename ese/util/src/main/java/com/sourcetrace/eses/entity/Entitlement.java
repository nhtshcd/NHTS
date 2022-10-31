package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * EseEnt generated by hbm2java
 */
@Entity
@Table(name = "ESE_ENT")
public class Entitlement implements GrantedAuthority {

	private long id;
	private String authority;
	private Set<User> eseUsers = new HashSet<User>(0);
	public static final String DASHBOARD_ENTITLEMENT = "dashboard.dashboard.list";
	public Entitlement() {
	}

	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ese_user_ent", joinColumns = {
			@JoinColumn(name = "ESE_ENT_ID",  updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "ESE_USER_ID",  updatable = false) })
	public Set<User> getEseUsers() {
		return this.eseUsers;
	}
	@Column(name = "NAME", nullable = false)
	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}


	public void setEseUsers(Set<User> eseUsers) {
		this.eseUsers = eseUsers;
	}

	

}
