package net.portrix.meld.social.people;

import net.portrix.generic.ddd.AbstractEntity;
import net.portrix.meld.usercontrol.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "so_relationship")
public class RelationShip extends AbstractEntity {

    @ManyToOne
    private User from;

    @ManyToOne
    private User to;

    @ManyToOne
    private Category category;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
