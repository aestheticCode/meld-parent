package net.portrix.meld.social.people.find.table.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.portrix.meld.social.people.find.table.search.expression.RestExpression;

public class Filter {

    private final String name;

    private final RestExpression expression;

    @JsonCreator
    public Filter(@JsonProperty("name") String name,
                  @JsonProperty("expression") RestExpression expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public RestExpression getExpression() {
        return expression;
    }

}
