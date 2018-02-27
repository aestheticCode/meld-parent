package net.portrix.meld.social.people.find.table;

import net.portrix.generic.rest.api.jaxrs.AbstractRestSearch;
import net.portrix.generic.rest.api.jaxrs.RestPredicate;
import net.portrix.generic.rest.api.jaxrs.RestSort;
import net.portrix.generic.rest.api.jaxrs.provider.GenericSortProvider;
import net.portrix.meld.social.people.find.table.provider.*;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class FindTableSearch extends AbstractRestSearch {

    @RestSort(GenericSortProvider.class)
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

    @RestPredicate(AddressProvider.class)
    @QueryParam("address")
    private UUID address;

    @RestPredicate(CompanyProvider.class)
    @QueryParam("company")
    private UUID company;

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

    public UUID getAddress() {
        return address;
    }

    public void setAddress(UUID address) {
        this.address = address;
    }

    public UUID getCompany() {
        return company;
    }

    public void setCompany(UUID company) {
        this.company = company;
    }
}
