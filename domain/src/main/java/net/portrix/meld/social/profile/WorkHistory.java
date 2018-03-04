package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractAggregate;
import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_workHistory")
@NamedQuery(name = "findWorkHistory", query = "select w from WorkHistory w where w.user = :user")
public class WorkHistory extends AbstractProfileVisibility {

    @OneToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "history", orphanRemoval = true)
    private final Set<Company> companies = new HashSet<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void addCompany(final Company company) {
        company.setHistory(this);
        companies.add(company);
    }

    public void clearCompanies() {
        companies.clear();
    }
}
