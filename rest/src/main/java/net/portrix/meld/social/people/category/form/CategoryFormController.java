package net.portrix.meld.social.people.category.form;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
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

@Path("social/people")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryFormController {

    private final CategoryService service;

    private final URLBuilderFactory factory;

    @Inject
    public CategoryFormController(CategoryService service, URLBuilderFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public CategoryFormController() {
        this(null, null);
    }

    @GET
    @Path("category/create")
    @Name("Categories Read")
    @Secured
    @Transactional
    public CategoryForm create() {
        CategoryForm result = new CategoryForm();

        linkSave(factory)
                .buildSecured(result::addLink);

        return result;

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

        linkUpdate(category, factory)
                .buildSecured(result::addLink);
        linkDelete(category, factory)
                .buildSecured(result::addLink);

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

    public static URLBuilder<CategoryFormController> linkCreate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(CategoryFormController.class)
                .record(CategoryFormController::create)
                .rel("create");
    }

    public static URLBuilder<CategoryFormController> linkDelete(Category category, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(CategoryFormController.class)
                .record((method) -> method.delete(category.getId()))
                .rel("delete");
    }

    public static URLBuilder<CategoryFormController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(CategoryFormController.class)
                .record((method) -> method.save(null))
                .rel("save");
    }

    public static URLBuilder<CategoryFormController> linkUpdate(Category category, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(CategoryFormController.class)
                .record((method) -> method.update(category.getId(), null))
                .rel("update");
    }


}
