package net.portrix.meld.social.profile.education.table;

import net.portrix.generic.rest.api.jaxrs.AbstractRestSearch;
import net.portrix.generic.rest.api.jaxrs.RestPredicate;
import net.portrix.generic.rest.api.jaxrs.RestSort;
import net.portrix.generic.rest.api.jaxrs.provider.GenericLikeNameProvider;
import net.portrix.generic.rest.api.jaxrs.provider.GenericSortProvider;

import javax.ws.rs.QueryParam;
import java.util.List;

public class SchoolSearch extends AbstractRestSearch {

    @RestSort(GenericSortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    @RestPredicate(GenericLikeNameProvider.class)
    @QueryParam("name")
    private String name;

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

}
