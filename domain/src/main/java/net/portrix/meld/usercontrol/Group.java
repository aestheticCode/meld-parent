package net.portrix.meld.usercontrol;

import net.portrix.generic.ddd.Aggregate;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Bittner on 31/01/15.
 */
@Entity
@Table(name = "uc_group")
@NamedQuery(name = "findAllGroups", query = "select g from Group g")
public class Group extends Identity implements Aggregate {

    @ManyToMany
    private final Set<Identity> members = new HashSet<>();

    public Set<Identity> getMembers() {
        return members;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }


    public void add(Identity identity) {
        members.add(identity);
    }

    public void remove(Identity identity) {
        members.remove(identity);
    }


}
