package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Category generated by hbm2java
 */
@Entity
@Table(name = "CATEGORY")
public class Category implements java.io.Serializable {

	private Long id;
	private String code;
	private String name;
	private Set<SubCategory> subCategories = new HashSet<SubCategory>(0);

	public Category() {
	}

	public Category(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public Category(String code, String name, Set<SubCategory> subCategories) {
		this.code = code;
		this.name = name;
		this.subCategories = subCategories;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CODE",  length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME",  length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany( mappedBy = "category",cascade=CascadeType.ALL)
	public Set<SubCategory> getSubCategories() {
		return this.subCategories;
	}

	public void setSubCategories(Set<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}

}
