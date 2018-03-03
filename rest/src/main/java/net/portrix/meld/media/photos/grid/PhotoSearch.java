package net.portrix.meld.media.photos.grid;

import net.portrix.generic.rest.api.jaxrs.AbstractRestSearch;
import net.portrix.generic.rest.api.jaxrs.RestSort;
import net.portrix.generic.rest.api.jaxrs.provider.GenericSortProvider;

import javax.ws.rs.QueryParam;
import java.util.List;

public class PhotoSearch extends AbstractRestSearch {

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
