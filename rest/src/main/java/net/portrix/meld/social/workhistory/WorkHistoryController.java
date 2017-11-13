package net.portrix.meld.social.workhistory;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.Company;
import net.portrix.meld.social.WorkHistory;
import net.portrix.meld.usercontrol.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

/**
 * @author Patrick Bittner on 22/12/2016.
 */
@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkHistoryController {

    private final WorkHistoryService service;

    @Inject
    public WorkHistoryController(WorkHistoryService service) {
        this.service = service;
    }

    public WorkHistoryController() {
        this(null);
    }


    @GET
    @Path("user/current/work/history")
    @Name("Work History Read Current")
    @Secured
    @Transactional
    public WorkHistoryForm current() {
        User user = service.currentUser();
        return read(user.getId());
    }


    @GET
    @Path("user/{id}/work/history")
    @Name("Work History Read")
    @Secured
    @Transactional
    public WorkHistoryForm read(@PathParam("id") UUID id) {

        User user = service.findUser(id);

        WorkHistory workHistory = service.findWorkHistory(user);

        WorkHistoryForm historyResponseType = new WorkHistoryForm();

        for (Company company : workHistory.getCompanies()) {
            CompanyForm companyForm = new CompanyForm();
            companyForm.setDescription(company.getDescription());
            companyForm.setEnd(company.getEnd());
            companyForm.setId(company.getId());
            companyForm.setName(company.getName());
            companyForm.setStart(company.getStart());
            companyForm.setTitle(company.getTitle());

            historyResponseType.addCompany(companyForm);
        }

        return historyResponseType;
    }


    @POST
    @Path("user/current/work/history")
    @Name("Work History Save")
    @Secured
    @Transactional
    public WorkHistoryForm save(WorkHistoryForm type) {

        WorkHistory workHistory = new WorkHistory();

        for (CompanyForm companyType : type.getCompanies()) {
            Company company = new Company();
            company.setDescription(companyType.getDescription());
            company.setEnd(companyType.getEnd());
            company.setName(companyType.getName());
            company.setStart(companyType.getStart());
            company.setTitle(companyType.getTitle());

            workHistory.addCompany(company);
        }

        service.saveWorkHistory(workHistory);

        return read(workHistory.getId());
    }

    @PUT
    @Path("user/current/work/history")
    @Name("Work History Update")
    @Secured
    @Transactional
    public WorkHistoryForm update(WorkHistoryForm type) {

        User user = service.currentUser();

        WorkHistory workHistory = service.findWorkHistory(user);
        workHistory.clearCompanies();

        for (CompanyForm companyType : type.getCompanies()) {
            Company company = new Company();
            company.setDescription(companyType.getDescription());
            company.setEnd(companyType.getEnd());
            company.setName(companyType.getName());
            company.setStart(companyType.getStart());
            company.setTitle(companyType.getTitle());

            workHistory.addCompany(company);
        }

        return read(workHistory.getId());
    }
}
