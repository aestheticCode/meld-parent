package net.portrix.meld.social.communities.find.table;

import net.portrix.generic.rest.api.jaxrs.AbstractRestSearch;
import net.portrix.generic.rest.api.jaxrs.RestSort;
import net.portrix.generic.rest.api.jaxrs.provider.GenericSortProvider;

import javax.ws.rs.QueryParam;
import java.util.List;

public class CommunitySearch extends AbstractRestSearch {

    @RestSort(GenericSortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }
}
