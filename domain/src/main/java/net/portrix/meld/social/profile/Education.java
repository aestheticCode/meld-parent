package net.portrix.meld.social.profile;

import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_education")
@NamedQueries({
        @NamedQuery(name = "findEducation", query = "select e from Education e where e.user = :user")
})
public class Education extends AbstractProfileVisibility {

    @OneToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "education", orphanRemoval = true)
    private final Set<School> schools = new HashSet<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<School> getSchools() {
        return schools;
    }

    public void addSchool(final School school) {
        school.setEducation(this);
        schools.add(school);
    }

    public void clearSchools() {
        schools.clear();
    }
}
