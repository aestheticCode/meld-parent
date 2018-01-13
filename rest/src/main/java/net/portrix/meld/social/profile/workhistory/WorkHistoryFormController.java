package net.portrix.meld.social.profile.workhistory;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.google.details.LocationDetailForm;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.profile.Company;
import net.portrix.meld.social.profile.Place;
import net.portrix.meld.social.profile.WorkHistory;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 22/12/2016.
 */
@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkHistoryFormController {

    private final WorkHistoryFormService service;

    private final URLBuilderFactory factory;

    private final Identity identity;


    @Inject
    public WorkHistoryFormController(WorkHistoryFormService service, URLBuilderFactory factory, Identity identity) {
        this.service = service;
        this.factory = factory;
        this.identity = identity;
    }

    public WorkHistoryFormController() {
        this(null, null, null);
    }


    @GET
    @Path("user/current/work/history/create")
    @Name("Work History Read Current")
    @Secured
    @Transactional
    public WorkHistoryForm create() {
        WorkHistoryForm form = new WorkHistoryForm();

        linkSave(factory)
                .buildSecured(form::addLink);

        return form;
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

        if (workHistory == null) {
            throw new NotFoundException();
        }

        WorkHistoryForm historyResponseType = new WorkHistoryForm();
        historyResponseType.setId(workHistory.getId());

        for (Company company : workHistory.getCompanies()) {
            CompanyForm companyForm = new CompanyForm();
            companyForm.setDescription(company.getDescription());
            companyForm.setEnd(company.getEnd());
            companyForm.setId(company.getId());
            companyForm.setName(company.getName());

            LocationDetailForm location = new LocationDetailForm();
            location.setId(company.getPlace().getId());
            location.setName(company.getPlace().getName());
            location.setStreet(company.getPlace().getStreet());
            location.setStreetNumber(company.getPlace().getStreetNumber());
            location.setZipCode(company.getPlace().getZipCode());
            location.setState(company.getPlace().getState());
            location.setCountry(company.getPlace().getCountry());
            location.setLat(company.getPlace().getLat());
            location.setLng(company.getPlace().getLng());

            companyForm.setLocation(location);

            companyForm.setStart(company.getStart());
            companyForm.setTitle(company.getTitle());
            companyForm.setTillNow(company.isTillNow());

            historyResponseType.addCompany(companyForm);
        }


        User currentUser = service.currentUser();

        if (user.equals(currentUser)) {
            linkUpdate(factory)
                    .buildSecured(historyResponseType::addLink);
            linkDelete(factory)
                    .buildSecured(historyResponseType::addLink);
        }

        return historyResponseType;
    }


    @POST
    @Path("user/current/work/history")
    @Name("Work History Save")
    @Secured
    @Transactional
    public WorkHistoryForm save(WorkHistoryForm type) {

        User user = service.currentUser();

        WorkHistory workHistory = new WorkHistory();
        workHistory.setUser(user);

        for (CompanyForm companyType : type.getCompanies()) {
            Company company = new Company();
            company.setDescription(companyType.getDescription());
            company.setEnd(companyType.getEnd());
            company.setName(companyType.getName());

            Place place = new Place();
            place.setId(companyType.getLocation().getId());
            place.setName(companyType.getLocation().getName());
            place.setStreet(companyType.getLocation().getStreet());
            place.setStreetNumber(companyType.getLocation().getStreetNumber());
            place.setZipCode(companyType.getLocation().getZipCode());
            place.setState(companyType.getLocation().getState());
            place.setCountry(companyType.getLocation().getCountry());
            place.setLat(companyType.getLocation().getLat());
            place.setLng(companyType.getLocation().getLng());

            company.setPlace(place);

            company.setStart(companyType.getStart());
            company.setTitle(companyType.getTitle());
            company.setTillNow(companyType.isTillNow());

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

            Place place = new Place();
            place.setId(companyType.getLocation().getId());
            place.setName(companyType.getLocation().getName());
            place.setStreet(companyType.getLocation().getStreet());
            place.setStreetNumber(companyType.getLocation().getStreetNumber());
            place.setZipCode(companyType.getLocation().getZipCode());
            place.setState(companyType.getLocation().getState());
            place.setCountry(companyType.getLocation().getCountry());
            place.setLat(companyType.getLocation().getLat());
            place.setLng(companyType.getLocation().getLng());

            company.setPlace(place);
            company.setStart(companyType.getStart());
            company.setTitle(companyType.getTitle());
            company.setTillNow(companyType.isTillNow());

            workHistory.addCompany(company);
        }

        return current();
    }

    @DELETE
    @Path("user/current/work/history")
    @Name("Work History Delete")
    @Secured
    @Transactional
    public void delete() {
        User user = service.currentUser();

        WorkHistory workHistory = service.findWorkHistory(user);

        service.deleteWorkHistory(workHistory);
    }

    @GET
    @Path("work/history/name")
    @Name("Work History Name")
    @Secured
    public List<String> query(@QueryParam("name") String name) {
        return service.findCompanyNames(name);
    }


    public static URLBuilder<WorkHistoryFormController> linkCreate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(WorkHistoryFormController.class)
                .record(WorkHistoryFormController::create)
                .rel("update");
    }


    public static URLBuilder<WorkHistoryFormController> linkCurrent(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(WorkHistoryFormController.class)
                .record(WorkHistoryFormController::current)
                .rel("work-history");
    }

    public static URLBuilder<WorkHistoryFormController> linkRead(WorkHistory workHistory, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(WorkHistoryFormController.class)
                .record((method) -> method.read(workHistory.getId()))
                .rel("work-history");
    }

    public static URLBuilder<WorkHistoryFormController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(WorkHistoryFormController.class)
                .record((method) -> method.save(null))
                .rel("save");
    }

    public static URLBuilder<WorkHistoryFormController> linkUpdate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(WorkHistoryFormController.class)
                .record((method) -> method.update(null))
                .rel("update");
    }

    public static URLBuilder<WorkHistoryFormController> linkDelete(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(WorkHistoryFormController.class)
                .record(WorkHistoryFormController::delete)
                .rel("delete");
    }

}
