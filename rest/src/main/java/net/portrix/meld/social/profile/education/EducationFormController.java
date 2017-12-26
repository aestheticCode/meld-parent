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
import org.picketlink.Identity;

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
public class EducationFormController {

    private final EducationFormService service;

    private final URLBuilderFactory factory;

    private final Identity identity;


    @Inject
    public EducationFormController(EducationFormService service, URLBuilderFactory factory, Identity identity) {
        this.service = service;
        this.factory = factory;
        this.identity = identity;
    }

    public EducationFormController() {
        this(null, null, null);
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

            schoolFormType.setTillNow(school.isTillNow());

            SchoolDateForm start = new SchoolDateForm();
            switch (school.getStart().getSemester()) {
                case SUMMER:
                    start.setSemester(SchoolDateForm.Semester.SUMMER);
                    break;
                case WINTER:
                    start.setSemester(SchoolDateForm.Semester.WINTER);
                    break;
            }
            start.setYear(school.getStart().getYear());
            schoolFormType.setStart(start);

            SchoolDateForm end = new SchoolDateForm();
            switch (school.getEnd().getSemester()) {
                case SUMMER:
                    end.setSemester(SchoolDateForm.Semester.SUMMER);
                    break;
                case WINTER:
                    end.setSemester(SchoolDateForm.Semester.WINTER);
                    break;
            }
            end.setYear(school.getEnd().getYear());
            schoolFormType.setEnd(end);


            form.addSchool(schoolFormType);
        }

        if (identity.isLoggedIn()) {
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
            school.setTillNow(schoolType.isTillNow());

            SchoolDate start = new SchoolDate();
            start.setYear(schoolType.getStart().getYear());
            switch (schoolType.getStart().getSemester()) {
                case SUMMER:
                    start.setSemester(SchoolDate.Semester.SUMMER);
                    break;
                case WINTER:
                    start.setSemester(SchoolDate.Semester.WINTER);
                    break;
            }
            school.setStart(start);

            SchoolDate end = new SchoolDate();
            end.setYear(schoolType.getEnd().getYear());
            switch (schoolType.getEnd().getSemester()) {
                case SUMMER:
                    end.setSemester(SchoolDate.Semester.SUMMER);
                    break;
                case WINTER:
                    end.setSemester(SchoolDate.Semester.WINTER);
                    break;
            }
            school.setEnd(end);

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
            school.setTillNow(schoolType.isTillNow());

            SchoolDate start = new SchoolDate();
            start.setYear(schoolType.getStart().getYear());
            switch (schoolType.getStart().getSemester()) {
                case SUMMER:
                    start.setSemester(SchoolDate.Semester.SUMMER);
                    break;
                case WINTER:
                    start.setSemester(SchoolDate.Semester.WINTER);
                    break;
            }
            school.setStart(start);

            SchoolDate end = new SchoolDate();
            end.setYear(schoolType.getEnd().getYear());
            switch (schoolType.getEnd().getSemester()) {
                case SUMMER:
                    end.setSemester(SchoolDate.Semester.SUMMER);
                    break;
                case WINTER:
                    end.setSemester(SchoolDate.Semester.WINTER);
                    break;
            }
            school.setEnd(end);

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
                .rel("current");
    }

    public static URLBuilder<EducationFormController> linkRead(UUID id, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(EducationFormController.class)
                .record((method) -> method.read(id))
                .rel("read");
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
