package net.portrix.meld.social.profile.workhistory.select;

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
public class CompanySelectController {

    private final CompanySelectService service;

    @Inject
    public CompanySelectController(CompanySelectService service) {
        this.service = service;
    }

    public CompanySelectController() {
        this(null);
    }

    @GET
    @Path("user/current/work/history/companies")
    @Name("Companies List")
    @Secured
    @Transactional
    public Container<CompanySelect> list(@BeanParam CompanySearch search) {
        List<Company> companies = service.find(search);
        long count = service.count(search);

        List<CompanySelect> items = companies.stream()
                .map((company) -> new CompanySelect(company.getId(), company.getName()))
                .collect(Collectors.toList());

        return new Container<>(items,(int) count);
    }
}
