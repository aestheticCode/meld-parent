package net.portrix.generic.ddd.eventbus.annotation;

import javax.enterprise.util.AnnotationLiteral;

/**
 * @author Patrick Bittner on 10.05.2014.
 */
public class CommandLiteral extends AnnotationLiteral<Command> {

    public static final CommandLiteral INSTANCE = new CommandLiteral();

}
