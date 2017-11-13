package net.portrix.generic.rest.jsr339;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author by Patrick Bittner on 09.06.15.
 */
@Target({METHOD, TYPE})
@Retention(RUNTIME)
@Documented
public @interface Name {

    String value();

}
