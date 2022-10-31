//$Id: ValidatorClass.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Validator;


/**
 * Link between an constraint annotation and it's validator implementation
 *
 * @author Gavin King
 */
@Documented
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidatorClass {
	Class<? extends Validator> value();
}
