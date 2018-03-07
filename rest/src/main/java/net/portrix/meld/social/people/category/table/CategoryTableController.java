package net.portrix.meld.social.people.category.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.people.Category;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("social/people")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryTableController {

    private final CategoryTableService service;

    @Inject
    public CategoryTableController(CategoryTableService service) {
        this.service = service;
    }

    public CategoryTableController() {
        this(null);
    }

    @GET
    @Path("categories")
    @Name("Categories Read")
    @Secured
    public Container<CategoryItem> list(@BeanParam CategorySearch query) {
        List<Category> categories = service.find(query);
        long count = service.count(query);

        List<CategoryItem> categoryFormList = categories
                .stream()
                .map((category) -> {
                    CategoryItem categoryForm = new CategoryItem();
                    categoryForm.setId(category.getId());
                    categoryForm.setName(category.getName());
                    return categoryForm;
                })
                .collect(Collectors.toList());

        return new Container<>(categoryFormList, (int) count);
    }


    public static URLBuilder<CategoryTableController> linkProfile(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(CategoryTableController.class)
                .record(method -> method.list(null))
                .rel("categories");
    }

}
