/*
 * IValidator.java
 * Copyright (c) 2008, Source Trace Systems
 * ALL RIGHTS RESERVED
 */
package org.hibernate.validator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Interface IValidator.
 * @author $Author$
 * @version $Rev$, $Date$
 */
public interface IValidator {

    static final String NUMERIC_PATTERN ="[0-9]+";
    static final String ALPHA_NUMERIC_PATTERN = "[a-zA-Z0-9]+";
    static final String ALPHA_PATTERN = "[a-zA-Z]+";
	static final String EXPRESSION_PHONE = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
    /**
	 * Validate.
	 * @param object the object
	 * @return the map< string, string>
	 */
	public abstract Map<String, String> validate(Object object);
	
}
