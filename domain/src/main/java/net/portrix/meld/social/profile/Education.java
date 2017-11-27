package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractAggregate;
import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_education")
@NamedQuery(name = "findEducation", query = "select e from Education e where e.user = :user")
public class Education extends AbstractAggregate {

    @OneToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderColumn
    private final List<School> schools = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void addSchool(final School school) {
        schools.add(school);
    }

    public void clearSchools() {
        schools.clear();
    }
}
