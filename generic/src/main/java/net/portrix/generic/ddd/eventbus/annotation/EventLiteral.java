package net.portrix.generic.ddd.eventbus.annotation;

import javax.enterprise.util.AnnotationLiteral;

/**
 * @author Patrick Bittner on 10.05.2014.
 */
public class EventLiteral extends AnnotationLiteral<Event> {

    public static final EventLiteral INSTANCE = new EventLiteral();


}
