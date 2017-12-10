package net.portrix.meld.usercontrol;

import net.portrix.generic.ddd.AbstractAggregate;
import net.portrix.generic.ddd.Aggregate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Patrick Bittner on 31/01/15.
 */
@Entity
@Table(name = "uc_identity")
public abstract class Identity extends AbstractAggregate implements Aggregate {

    @Basic
    @Column(unique = true)
    private String name;

    @Column(name = "DTYPE", insertable = false, updatable = false)
    private String dtype;

    public String getName() {
        return name;
    }

    public void setName(String id) {
        this.name = id;
    }

    public String getDtype() {
        return dtype;
    }

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {

        R visit(User entity);

        R visit(Group entity);
    }
}
