package net.portrix.meld.social.people;

import net.portrix.generic.ddd.AbstractEntity;
import net.portrix.meld.usercontrol.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "so_category")
public class Category extends AbstractEntity {

    @ManyToOne
    private User user;

    private String name;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
