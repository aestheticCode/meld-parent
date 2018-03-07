package net.portrix.meld.social.profile.education.form;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.meld.social.profile.AbstractRestProfileVisibility;

import java.util.*;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class EducationForm extends AbstractRestProfileVisibility implements LinksContainer {

    private UUID id;

    private final Set<Link> links = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private final List<SchoolForm> schools = new ArrayList<>();

    public List<SchoolForm> getSchools() {
        return schools;
    }

    public void addSchool(SchoolForm responseType) {
        schools.add(responseType);
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
