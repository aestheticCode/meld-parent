package net.portrix.meld.channel.meld.list;

import net.portrix.generic.rest.api.jaxrs.AbstractRestSearch;
import net.portrix.generic.rest.api.jaxrs.RestPredicate;
import net.portrix.generic.rest.api.jaxrs.RestSort;
import net.portrix.generic.rest.api.jaxrs.provider.GenericSortProvider;
import net.portrix.meld.channel.meld.list.provider.HomeProvider;
import net.portrix.meld.channel.meld.list.provider.ProfileProvider;

import javax.ws.rs.QueryParam;
import java.util.List;

public class MeldSearch extends AbstractRestSearch {

    @RestSort(GenericSortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    @RestPredicate(HomeProvider.class)
    @QueryParam("home")
    private boolean home;

    @RestPredicate(ProfileProvider.class)
    @QueryParam("profile")
    private boolean profile;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public boolean isHome() {
        return home;
    }

    public void setHome(boolean home) {
        this.home = home;
    }

    public boolean isProfile() {
        return profile;
    }

    public void setProfile(boolean profile) {
        this.profile = profile;
    }
}
