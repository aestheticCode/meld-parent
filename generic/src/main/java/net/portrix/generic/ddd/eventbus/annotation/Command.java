package net.portrix.generic.ddd.eventbus.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Patrick Bittner on 10.05.2014.
 */
@EventBusQualifier
@Target({PARAMETER, FIELD})
@Retention(RUNTIME)
@Documented
public @interface Command {}
