/*
 * WebConsoleError.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.txn.exception;

public interface WebConsoleError {
	// web console login errors
	public static final String INVALID_LOGIN = "WL0001";
	public static final String INVALID_ENTITLEMENT = "WL0002";
	public static final String ACCT_LOCKED = "WL0003";
	public static final String USER_DISABLED = "WL0004";
	public static final String EMPTY_USER = "WL0005";
	public static final String EMPTY_PASSWORD = "WL0006";
	public static final String BRANCH_DISABLED = "WL0007";
	public static final String PARENT_BRANCH_DISABLED = "WL0008";
	// License Error
	public static final String NO_LICENCE = "10001";
	public static final String INVALID_LICENCE = "10002";
	public static final String UNAUTHORIZED_CLIENT = "10003";
	public static final String EXPIRED_LICENCE = "10004";
	public static final String DATABASE_CONNECTION_ERROR = "10005";
	
	
}
