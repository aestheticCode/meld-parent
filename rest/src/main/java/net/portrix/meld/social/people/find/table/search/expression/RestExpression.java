package net.portrix.meld.social.people.find.table.search.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.meld.social.people.find.table.search.ExpressionVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(SchoolExpression.class),
        @JsonSubTypes.Type(NameExpression.class),
        @JsonSubTypes.Type(AndExpression.class),
        @JsonSubTypes.Type(OrExpression.class),
        @JsonSubTypes.Type(NoopExpression.class)
})
public interface RestExpression extends LinksContainer {

    Expression<?> accept(ExpressionVisitor visitor);

}
