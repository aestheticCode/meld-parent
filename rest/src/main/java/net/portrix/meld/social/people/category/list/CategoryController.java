package net.portrix.meld.social.people.category.list;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.people.category.list.query.Query;
import net.portrix.meld.usercontrol.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;
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

    @POST
    @Path("categories")
    @Name("Categories Read")
    @Secured
    public Container<CategoryForm> list(Query query) {
        List<Category> categories = service.findAll(query);
        long count = service.count(query);

        List<CategoryForm> categoryFormList = categories
                .stream()
                .map((category) -> {
                    CategoryForm categoryForm = new CategoryForm();
                    categoryForm.setId(category.getId());
                    categoryForm.setName(category.getName());
                    return categoryForm;
                })
                .collect(Collectors.toList());

        return new Container<>(categoryFormList, (int)count);
    }


    public static URLBuilder<CategoryController> linkProfile(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(CategoryController.class)
                .record(method -> method.list(null))
                .rel("people");
    }

}
