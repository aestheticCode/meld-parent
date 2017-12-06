package net.portrix.meld.usercontrol.registration.form;

import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author by Patrick Bittner on 12.06.15.
 */
@Path("usercontrol")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationFormController {

    private final RegistrationFormService service;


    @Inject
    public RegistrationFormController(RegistrationFormService service) {
        this.service = service;
    }

    public RegistrationFormController() {
        this(null);
    }

    @POST
    @Path("registration/form")
    @Transactional
    public RegistrationForm register(RegistrationForm registrationForm) {

        service.register(registrationForm);

        return registrationForm;
    }

    public static URLBuilder<RegistrationFormController> linkLogin(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(RegistrationFormController.class)
                .record(controller -> controller.register(null))
                .rel("register");
    }

}
