package net.portrix.meld.social;

import net.portrix.generic.ddd.AbstractAggregate;
import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_workHistory")
@NamedQuery(name = "findWorkHistory", query = "select w from WorkHistory w where w.user = :user")
public class WorkHistory extends AbstractAggregate {

    @OneToOne
    private User user;

    @OrderColumn
    @OneToMany
    private final List<Company> companies = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void addCompany(final Company company) {
        companies.add(company);
    }

    public void clearCompanies() {
        companies.clear();
    }
}
