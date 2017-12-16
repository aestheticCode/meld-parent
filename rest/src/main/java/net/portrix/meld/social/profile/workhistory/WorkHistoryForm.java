package net.portrix.meld.social.profile.workhistory;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.*;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class WorkHistoryForm implements LinksContainer {

    private UUID id;

    private final List<CompanyForm> companies = new ArrayList<>();

    private final Set<Link> links = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<CompanyForm> getCompanies() {
        return companies;
    }

    public void addCompany(CompanyForm companyForm) {
        companies.add(companyForm);
    }

    @Override
    public Set<Link> getLinks() {
        return links;
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }
}
