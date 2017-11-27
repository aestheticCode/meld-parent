package net.portrix.meld.social.people;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.jsr339.Name;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("social/people")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

    private final CategoryService service;

    @Inject
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    public CategoryController() {
        this(null);
    }

    @GET
    @Path("categories")
    @Name("Categories Read")
    @Secured
    public Container<CategoryForm> read() {
        List<Category> categories = service.findAll();

        List<CategoryForm> categoryFormList = categories
                .stream()
                .map((category) -> {
                    CategoryForm categoryForm = new CategoryForm();
                    categoryForm.setId(category.getId());
                    categoryForm.setName(category.getName());
                    return categoryForm;
                })
                .collect(Collectors.toList());

        return new Container<>(categoryFormList, categoryFormList.size());
    }

    public static void linkProfile(LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(CategoryController.class)
                .record(CategoryController::read)
                .rel("people")
                .buildSecured(container::addLink);
    }

}
