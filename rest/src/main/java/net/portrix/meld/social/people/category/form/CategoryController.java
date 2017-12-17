package net.portrix.meld.social.people.category.form;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.RelationShip;
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

    @GET
    @Path("category/{id}")
    @Name("Categories Read")
    @Secured
    @Transactional
    public CategoryForm read(@PathParam("id") UUID id) {

        Category category = service.find(id);

        CategoryForm result = new CategoryForm();
        result.setId(category.getId());
        result.setName(category.getName());

        return result;

    }


    @POST
    @Path("category")
    @Name("Categories Save")
    @Secured
    @Transactional
    public CategoryForm save(CategoryForm form) {

        User user = service.currentUser();

        Category category = new Category();
        category.setUser(user);
        category.setName(form.getName());

        service.save(category);

        return read(category.getId());
    }

    @PUT
    @Path("category/{id}")
    @Name("Categories Update")
    @Secured
    @Transactional
    public CategoryForm update(@PathParam("id") UUID id, CategoryForm form) {

        Category category = service.find(id);
        category.setName(form.getName());

        return read(category.getId());

    }

    @DELETE
    @Path("category/{id}")
    @Name("Categories Delete")
    @Secured
    @Transactional
    public void delete(@PathParam("id") UUID id) {

        Category category = service.find(id);

        List<RelationShip> relationShips = service.find(category);

        for (RelationShip relationShip : relationShips) {
            service.remove(relationShip);
        }

        service.remove(category);

    }

    public static URLBuilder<CategoryController> linkDelete(UUID id, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(CategoryController.class)
                .record((method) -> method.delete(id))
                .rel("delete");
    }

    public static URLBuilder<CategoryController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(CategoryController.class)
                .record((method) -> method.save(null))
                .rel("save");
    }

    public static URLBuilder<CategoryController> linkUpdate(UUID id, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(CategoryController.class)
                .record((method) -> method.update(id, null))
                .rel("update");
    }


}
