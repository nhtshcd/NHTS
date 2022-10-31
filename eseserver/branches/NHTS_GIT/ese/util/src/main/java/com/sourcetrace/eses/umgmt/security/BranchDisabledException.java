package com.sourcetrace.eses.umgmt.security;

import org.springframework.security.core.AuthenticationException;

public class BranchDisabledException extends AuthenticationException {
	public BranchDisabledException(String msg) {
		super(msg);
	}
}
