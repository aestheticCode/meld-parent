package net.portrix.meld.social.profile.education;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.profile.Education;
import net.portrix.meld.social.profile.School;
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
public class EducationController {

    private final EducationService service;

    @Inject
    public EducationController(EducationService service) {
        this.service = service;
    }

    public EducationController() {
        this(null);
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

        final Education education = service.findEducation(user);

        final EducationForm form = new EducationForm();
        form.setId(education.getId());

        for (School school : education.getSchools()) {
            SchoolForm schoolFormType = new SchoolForm();
            schoolFormType.setCourse(school.getCourse());
            schoolFormType.setDescription(school.getDescription());
            schoolFormType.setEnd(school.getEnd());
            schoolFormType.setId(school.getId());
            schoolFormType.setName(school.getName());
            schoolFormType.setStart(school.getStart());

            form.addSchool(schoolFormType);
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
            school.setEnd(schoolType.getEnd());
            school.setName(schoolType.getName());
            school.setStart(schoolType.getStart());

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
            school.setEnd(schoolType.getEnd());
            school.setName(schoolType.getName());
            school.setStart(schoolType.getStart());

            education.addSchool(school);
        }

        return current();
    }
}
