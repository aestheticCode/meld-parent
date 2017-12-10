package net.portrix.meld.usercontrol;

import net.portrix.generic.ddd.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Patrick Bittner on 09/02/15.
 */
@Entity
@Table(name = "uc_permission")
@NamedQuery(name = "findAllPermissions", query = "select p from Permission p")
public class Permission extends AbstractEntity {

    private String name;

    private String path;

    private String method;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
