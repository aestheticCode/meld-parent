package net.portrix.meld.social.profile.workhistory.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.profile.Company;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyTableController {

    private final CompanyTableService service;

    @Inject
    public CompanyTableController(CompanyTableService service) {
        this.service = service;
    }

    public CompanyTableController() {
        this(null);
    }

    @GET
    @Path("user/current/work/history/companies")
    @Name("Companies List")
    @Secured
    @Transactional
    public Container<CompanyItem> list(@BeanParam CompanySearch search) {
        List<Company> companies = service.find(search);
        long count = service.count(search);

        List<CompanyItem> items = companies.stream()
                .map((company) -> new CompanyItem(company.getId(), company.getName()))
                .collect(Collectors.toList());

        return new Container<>(items,(int) count);
    }
}
