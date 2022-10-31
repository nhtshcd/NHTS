/**
 * ServicePoint.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class ServicePoint {

	private static final int NAME_MAX_LENGTH = 25;	
	private static final int CODE_MAX_LENGTH = 30;
	private long id;
	private String code;
	private String name;
	private ServicePointType type;
	private Municipality city;	

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public long getId() {

		return id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	@Length(max = NAME_MAX_LENGTH, message = "length.servicepointname")
	@Pattern(regexp = "[[^@#$%&*();:,{}^<>?+|^'!^/=\"\\_-]]+$", message = "pattern.servicepointname")
	@NotEmpty(message = "empty.servicePoint.name")
	@NotNull(message = "empty.servicePoint.name")
	public String getName() {

		return name;
	}


	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(long id) {

		this.id = id;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public ServicePointType getType() {

		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(ServicePointType type) {

		this.type = type;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code
	 *            the new code
	 */

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	@Length(max = CODE_MAX_LENGTH, message = "length.servicepointcode")
	@Pattern(regexp = "[[^@#$%&*();:,{}^<>?+|^'!^/=\"\\_-]]+$", message = "pattern.servicepointcode")
	@NotEmpty(message = "empty.servicePoint.code")
	@NotNull(message = "empty.servicePoint.code")
	public String getCode() {
		return code;
	}

	/**
	 * Sets the city.
	 * 
	 * @param city
	 *            the new city
	 */
	public void setCity(Municipality city) {
		this.city = city;
	}

	/**
	 * Gets the city.
	 * 
	 * @return the city
	 */	
	@NotNull(message="empty.city")
	public Municipality getCity() {
		return city;
	}

}
