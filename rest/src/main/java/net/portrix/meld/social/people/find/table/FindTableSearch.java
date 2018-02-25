package net.portrix.meld.social.people.find.table;

import net.portrix.generic.rest.api.jaxrs.AbstractRestQuery;
import net.portrix.generic.rest.api.jaxrs.RestPredicate;
import net.portrix.generic.rest.api.jaxrs.RestSort;
import net.portrix.generic.rest.api.jaxrs.provider.SortProvider;
import net.portrix.meld.social.people.find.table.provider.*;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class FindTableSearch extends AbstractRestQuery {

    @RestSort(SortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    @RestPredicate(NameProvider.class)
    @QueryParam("name")
    private String name;

    @RestPredicate(SchoolProvider.class)
    @QueryParam("school")
    private UUID school;

    @RestPredicate(FollowProvider.class)
    @QueryParam("follow")
    private boolean follow;

    @RestPredicate(CategoryProvider.class)
    @QueryParam("category")
    private UUID category;

    @RestPredicate(PlaceProvider.class)
    @QueryParam("place")
    private UUID place;

    @RestPredicate(WorkProvider.class)
    @QueryParam("work")
    private UUID work;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getSchool() {
        return school;
    }

    public void setSchool(UUID school) {
        this.school = school;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public boolean isFollow() {
        return follow;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public UUID getPlace() {
        return place;
    }

    public void setPlace(UUID place) {
        this.place = place;
    }

    public UUID getWork() {
        return work;
    }

    public void setWork(UUID work) {
        this.work = work;
    }
}
