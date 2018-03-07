package net.portrix.meld.social.profile.education.select;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.search.Filter;
import net.portrix.generic.rest.api.search.predicate.LikeExpression;
import net.portrix.generic.rest.api.search.predicate.PathExpression;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.profile.School;
import org.apache.commons.lang.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SchoolSelectController {

    private final SchoolSelectService service;

    private final URLBuilderFactory factory;

    private final List<Filter> queries = Arrays.asList(
            new Filter("name", new PathExpression("name", new LikeExpression("", "Name"))
            ));

    @Inject
    public SchoolSelectController(SchoolSelectService service, URLBuilderFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public SchoolSelectController() {
        this(null, null);
    }

    @GET
    @Path("education/find")
    @Secured
    @Transactional
    public Container<SchoolSelect> list(@BeanParam SchoolSearch search) {

        List<School> schools = service.find(search);
        long count = service.count(search);

        List<SchoolSelect> items = schools.stream()
                .map((school) -> new SchoolSelect(school.getId(), school.getName()))
                .collect(Collectors.toList());

        return new Container<>(items, (int) count);
    }

    @GET
    @Path("education/find/meta")
    @Secured
    @Transactional
    public Container<Filter> meta(@QueryParam("name") String name) {
        if (StringUtils.isEmpty(name)) {
            Container<Filter> container = new Container<>(this.queries, this.queries.size());

            linkList(factory)
                    .buildSecured(container::addLink);

            return container;
        }
        List<Filter> filters = this.queries
                .stream()
                .filter((query) -> query.getName().equals(name))
                .collect(Collectors.toList());
        Container<Filter> container = new Container<>(filters, filters.size());

        linkList(factory)
                .buildSecured(container::addLink);

        return container;
    }

    public static URLBuilder<SchoolSelectController> linkList(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(SchoolSelectController.class)
                .record((method) -> method.list(null))
                .rel("list");
    }

    public static URLBuilder<SchoolSelectController> linkMeta(String name, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(SchoolSelectController.class)
                .record((method) -> method.meta(name))
                .rel("meta");
    }

}
