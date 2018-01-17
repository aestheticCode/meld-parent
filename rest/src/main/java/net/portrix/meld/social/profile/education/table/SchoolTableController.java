package net.portrix.meld.social.profile.education.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.search.Filter;
import net.portrix.generic.rest.api.search.Search;
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
public class SchoolTableController {

    private final SchoolTableService service;

    private final URLBuilderFactory factory;

    private final List<Filter> queries = Arrays.asList(
       new Filter("name", new PathExpression("name", new LikeExpression("", "Name"))
    ));

    @Inject
    public SchoolTableController(SchoolTableService service, URLBuilderFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public SchoolTableController() {
        this(null, null);
    }

    @POST
    @Path("education/find")
    @Secured
    @Transactional
    public Container<SchoolItem> list(Search search) {

        List<School> schools = service.find(search);
        long count = service.count(search);

        List<SchoolItem> items = schools.stream()
                .map((school) -> new SchoolItem(school.getId(), school.getName()))
                .collect(Collectors.toList());

        return new Container<>(items, (int)count);
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

    public static URLBuilder<SchoolTableController> linkList(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(SchoolTableController.class)
                .record((method) -> method.list(null))
                .rel("list");
    }

    public static URLBuilder<SchoolTableController> linkMeta(String name, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(SchoolTableController.class)
                .record((method) -> method.meta(name))
                .rel("meta");
    }

}
