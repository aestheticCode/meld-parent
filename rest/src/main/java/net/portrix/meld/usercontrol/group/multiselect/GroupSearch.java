package net.portrix.meld.usercontrol.group.multiselect;

import net.portrix.generic.rest.api.jaxrs.AbstractRestSearch;
import net.portrix.generic.rest.api.jaxrs.RestPredicate;
import net.portrix.generic.rest.api.jaxrs.RestSort;
import net.portrix.generic.rest.api.jaxrs.provider.GenericInIdsProvider;
import net.portrix.generic.rest.api.jaxrs.provider.GenericLikeNameProvider;
import net.portrix.generic.rest.api.jaxrs.provider.GenericSortProvider;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class GroupSearch extends AbstractRestSearch {

    @RestSort(GenericSortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    @RestPredicate(GenericLikeNameProvider.class)
    @QueryParam("name")
    private String name;

    @RestPredicate(GenericInIdsProvider.class)
    @QueryParam("ids")
    private List<UUID> ids;

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

    public List<UUID> getIds() {
        return ids;
    }

    public void setIds(List<UUID> ids) {
        this.ids = ids;
    }
}
