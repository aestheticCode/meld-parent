package net.portrix.meld.social.profile.education;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.google.details.LocationDetailForm;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.profile.Education;
import net.portrix.meld.social.profile.Place;
import net.portrix.meld.social.profile.School;
import net.portrix.meld.social.profile.SchoolDate;
import net.portrix.meld.usercontrol.User;

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
public class EducationFormController {

    private final EducationFormService service;

    private final URLBuilderFactory factory;

    @Inject
    public EducationFormController(EducationFormService service, URLBuilderFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public EducationFormController() {
        this(null, null);
    }

    @GET
    @Path("user/current/education/create")
    @Name("Education Create")
    @Secured
    @Transactional
    public EducationForm create() {
        EducationForm form = new EducationForm();

        linkSave(factory)
                .buildSecured(form::addLink);

        return form;
    }


    @GET
    @Path("user/current/education")
    @Name("Education Read")
    @Secured
    @Transactional
    public EducationForm current() {
        User user = service.currentUser();
        return read(user.getId());
    }

    @GET
    @Path("user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/education")
    @Name("Education Read")
    @Secured
    @Transactional
    public EducationForm read(@PathParam("id") UUID id) {

        User user = service.findUser(id);

        Education education = service.findEducation(user);
        if (education == null) {
            throw new NotFoundException();
        }

        final EducationForm form = new EducationForm();
        form.setId(education.getId());

        for (School school : education.getSchools()) {
            SchoolForm schoolFormType = new SchoolForm();
            schoolFormType.setCourse(school.getCourse());
            schoolFormType.setDescription(school.getDescription());
            schoolFormType.setId(school.getId());
            schoolFormType.setName(school.getName());
            schoolFormType.setTillNow(school.isTillNow());

            LocationDetailForm locationForm = new LocationDetailForm();
            locationForm.setId(school.getPlace().getId());
            locationForm.setName(school.getPlace().getName());
            locationForm.setStreet(school.getPlace().getStreet());
            locationForm.setStreetNumber(school.getPlace().getStreetNumber());
            locationForm.setZipCode(school.getPlace().getZipCode());
            locationForm.setState(school.getPlace().getState());
            locationForm.setCountry(school.getPlace().getCountry());
            locationForm.setLat(school.getPlace().getLat());
            locationForm.setLng(school.getPlace().getLng());

            schoolFormType.setLocation(locationForm);

            SchoolDateForm start = new SchoolDateForm();
            start.setSemester(school.getStartYear().getSemester());
            start.setYear(school.getStartYear().getYear());
            schoolFormType.setStartYear(start);

            SchoolDateForm end = new SchoolDateForm();
            end.setSemester(school.getEndYear().getSemester());
            end.setYear(school.getEndYear().getYear());
            schoolFormType.setEndYear(end);

            SchoolDateForm visitStart = new SchoolDateForm();
            visitStart.setSemester(school.getVisitStart().getSemester());
            visitStart.setYear(school.getVisitStart().getYear());
            schoolFormType.setVisitStart(visitStart);

            SchoolDateForm visitEnd = new SchoolDateForm();
            visitEnd.setSemester(school.getVisitEnd().getSemester());
            visitEnd.setYear(school.getVisitEnd().getYear());
            schoolFormType.setVisitEnd(visitEnd);


            form.addSchool(schoolFormType);
        }

        User currentUser = service.currentUser();

        if (user.equals(currentUser)) {
            linkUpdate(factory)
                    .buildSecured(form::addLink);
            linkDelete(factory)
                    .buildSecured(form::addLink);

        }

        return form;
    }


    @POST
    @Path("user/current/education")
    @Name("Education Save")
    @Secured
    @Transactional
    public EducationForm save(final EducationForm form) {

        final Education education = new Education();

        for (SchoolForm schoolType : form.getSchools()) {
            School school = new School();
            school.setCourse(schoolType.getCourse());
            school.setDescription(schoolType.getDescription());
            school.setName(schoolType.getName());
            school.setTillNow(schoolType.isTillNow());

            Place place = new Place();
            place.setId(schoolType.getLocation().getId());
            place.setName(schoolType.getLocation().getName());

            place.setStreet(schoolType.getLocation().getStreet());
            place.setStreetNumber(schoolType.getLocation().getStreetNumber());
            place.setZipCode(schoolType.getLocation().getZipCode());
            place.setState(schoolType.getLocation().getState());
            place.setCountry(schoolType.getLocation().getCountry());

            place.setLat(schoolType.getLocation().getLat());
            place.setLng(schoolType.getLocation().getLng());

            school.setPlace(place);

            SchoolDate start = new SchoolDate();
            start.setYear(schoolType.getStartYear().getYear());
            start.setSemester(schoolType.getStartYear().getSemester());
            school.setStartYear(start);

            SchoolDate end = new SchoolDate();
            end.setYear(schoolType.getEndYear().getYear());
            end.setSemester(schoolType.getEndYear().getSemester());
            school.setEndYear(end);

            SchoolDate visitStart = new SchoolDate();
            visitStart.setSemester(schoolType.getVisitStart().getSemester());
            visitStart.setYear(schoolType.getVisitStart().getYear());
            school.setVisitStart(visitStart);

            SchoolDate visitEnd = new SchoolDate();
            visitEnd.setSemester(schoolType.getVisitEnd().getSemester());
            visitEnd.setYear(schoolType.getVisitEnd().getYear());
            school.setVisitEnd(visitEnd);

            education.addSchool(school);
        }

        service.saveEducation(education);

        return current();
    }

    @PUT
    @Path("user/current/education")
    @Name("Education Update")
    @Secured
    @Transactional
    public EducationForm update(final EducationForm form) {
        User user = service.currentUser();

        Education education = service.findEducation(user);
        education.clearSchools();

        for (SchoolForm schoolType : form.getSchools()) {
            School school = new School();
            school.setCourse(schoolType.getCourse());
            school.setDescription(schoolType.getDescription());
            school.setName(schoolType.getName());
            school.setTillNow(schoolType.isTillNow());

            Place place = new Place();
            place.setId(schoolType.getLocation().getId());
            place.setName(schoolType.getLocation().getName());

            place.setStreet(schoolType.getLocation().getStreet());
            place.setStreetNumber(schoolType.getLocation().getStreetNumber());
            place.setZipCode(schoolType.getLocation().getZipCode());
            place.setState(schoolType.getLocation().getState());
            place.setCountry(schoolType.getLocation().getCountry());

            place.setLat(schoolType.getLocation().getLat());
            place.setLng(schoolType.getLocation().getLng());
            school.setPlace(place);

            SchoolDate start = new SchoolDate();
            start.setYear(schoolType.getStartYear().getYear());
            start.setSemester(schoolType.getStartYear().getSemester());
            school.setStartYear(start);

            SchoolDate end = new SchoolDate();
            end.setYear(schoolType.getEndYear().getYear());
            end.setSemester(schoolType.getEndYear().getSemester());
            school.setEndYear(end);

            SchoolDate visitStart = new SchoolDate();
            visitStart.setSemester(schoolType.getVisitStart().getSemester());
            visitStart.setYear(schoolType.getVisitStart().getYear());
            school.setVisitStart(visitStart);

            SchoolDate visitEnd = new SchoolDate();
            visitEnd.setSemester(schoolType.getVisitEnd().getSemester());
            visitEnd.setYear(schoolType.getVisitEnd().getYear());
            school.setVisitEnd(visitEnd);

            education.addSchool(school);
        }

        return current();
    }

    @DELETE
    @Path("user/current/education")
    @Name("Education Delete")
    @Secured
    @Transactional
    public void delete() {
        User user = service.currentUser();

        Education education = service.findEducation(user);
        service.deleteEducation(education);
    }

    @GET
    @Path("education/name")
    @Name("Education Name")
    @Secured
    public List<String> query(@QueryParam("name") String name) {
        return service.findSchoolNames(name);
    }

    public static URLBuilder<EducationFormController> linkCreate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(EducationFormController.class)
                .record(EducationFormController::create)
                .rel("create");
    }

    public static URLBuilder<EducationFormController> linkCurrent(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(EducationFormController.class)
                .record(EducationFormController::current)
                .rel("education");
    }

    public static URLBuilder<EducationFormController> linkRead(Education education, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(EducationFormController.class)
                .record((method) -> method.read(education.getId()))
                .rel("education");
    }

    public static URLBuilder<EducationFormController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(EducationFormController.class)
                .record((method) -> method.save(null))
                .rel("save");
    }

    public static URLBuilder<EducationFormController> linkUpdate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(EducationFormController.class)
                .record((method) -> method.update(null))
                .rel("update");
    }

    public static URLBuilder<EducationFormController> linkDelete(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(EducationFormController.class)
                .record(EducationFormController::delete)
                .rel("delete");
    }

}
