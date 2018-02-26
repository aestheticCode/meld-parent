package net.portrix.meld.social.profile.places.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.profile.Address;
import org.jboss.resteasy.annotations.Form;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressTableController {

    private final AddressTableService service;

    @Inject
    public AddressTableController(AddressTableService service) {
        this.service = service;
    }

    public AddressTableController() {
        this(null);
    }

    @GET
    @Path("user/current/places/addresses")
    @Name("Addresses Read")
    @Secured
    @Transactional
    public Container<AddressItem> list(@Form AddressSearch search) {
        List<Address> addresses = service.find(search);
        long count = service.count(search);

        List<AddressItem> addressItems = addresses.stream()
                .map(address -> new AddressItem(address.getId(), address.getPlace().getName()))
                .collect(Collectors.toList());

        return new Container<>(addressItems, (int) count);
    }
}
