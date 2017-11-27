package net.portrix.meld.social.profile.education;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class EducationForm {

    private UUID id;

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
}
