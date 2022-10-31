/*
 * ServiceLocation.java
 * Copyright (c) 2012-2013, SourceTrace Systems, All Rights Reserved.
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

/**
 * The Class ServiceLocation.
 */
public class ServiceLocation {

	private static final int NAME_MAX_LENGTH = 25;
	private static final int CODE_MAX_LENGTH = 30;
	public static final int MAX_LENGTH_LANANDLOG = 15;
	static final String EXPRESSION_LATITUDE = "(-?[0-8]?[0-9](\\.\\d*)?)|-?90(\\.[0]*)?";
	static final String EXPRESSION_LONGITUDE = "(-?([1]?[0-7][1-9]|[1-9]?[0-9])?(\\.\\d*)?)|-?180(\\.[0]*)?";

	private long id;
	private String code;
	private String name;
	private ServicePoint servicePoint;
	private String latitude;
	private String longitude;

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	@Length(max = CODE_MAX_LENGTH, message = "length.serviceLocCode")
	@Pattern(regexp = "[[^@#$%&*();:,{}^<>?+|^'!^/=\"\\_-]]+$", message = "pattern.serviceLocCode")
	@NotEmpty(message = "empty.serviceLocation.code")
	@NotNull(message = "empty.serviceLocation.code")
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	@Length(max = NAME_MAX_LENGTH, message = "length.serviceLocName")
	@Pattern(regexp = "[[^@#$%&*();:,{}^<>?+|^'!^/=\"\\_-]]+$", message = "pattern.serviceLocName")
	@NotEmpty(message = "empty.serviceLocation.name")
	@NotNull(message = "empty.serviceLocation.name")
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the service point.
	 * 
	 * @return the service point
	 */
	public ServicePoint getServicePoint() {
		return servicePoint;
	}

	/**
	 * Sets the service point.
	 * 
	 * @param servicePoint the new service point
	 */
	public void setServicePoint(ServicePoint servicePoint) {
		this.servicePoint = servicePoint;
	}

	/**
	 * Gets the latitude.
	 * 
	 * @return the latitude
	 */
	@Length(max = MAX_LENGTH_LANANDLOG, message = "length.latitude")
	@Pattern(regexp = EXPRESSION_LATITUDE, message = "pattern.latitude")
	@NotEmpty(message = "empty.latitude")
	public String getLatitude() {
		return latitude;
	}
	
	/**
	 * Sets the latitude.
	 * 
	 * @param latitude the new latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 * 
	 * @return the longitude
	 */
	@Length(max = MAX_LENGTH_LANANDLOG, message = "length.longitude")
	@Pattern(regexp = EXPRESSION_LONGITUDE, message = "pattern.longitude")
	@NotEmpty(message = "empty.longitude")
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 * 
	 * @param longitude the new longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
