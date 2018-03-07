package net.portrix.meld.social.profile.places.select;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.profile.Address;

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
public class AddressSelectController {

    private final AddressSelectService service;

    @Inject
    public AddressSelectController(AddressSelectService service) {
        this.service = service;
    }

    public AddressSelectController() {
        this(null);
    }

    @GET
    @Path("user/current/places/addresses")
    @Name("Addresses Read")
    @Secured
    @Transactional
    public Container<AddressSelect> list(@BeanParam AddressSearch search) {
        List<Address> addresses = service.find(search);
        long count = service.count(search);

        List<AddressSelect> addressItems = addresses.stream()
                .map(address -> new AddressSelect(address.getId(), address.getPlace().getName()))
                .collect(Collectors.toList());

        return new Container<>(addressItems, (int) count);
    }
}
