package net.portrix.meld.usercontrol;

import net.portrix.generic.ddd.AbstractAggregate;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Bittner on 09/02/15.
 */
@Entity
@Table(name = "uc_role")
@NamedQuery(name = "findAllRoles", query = "select r from Role r")
public class Role extends AbstractAggregate {

    private String name;

    @ManyToMany
    private final Set<Permission> permissions = new HashSet<>();

    @ManyToMany
    private final Set<Identity> scopes = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<Identity> getScopes() {
        return scopes;
    }

    public boolean addScope(Identity identity) {
        return scopes.add(identity);
    }

    public boolean removeScope(Identity identity) {
        return scopes.remove(identity);
    }

    public boolean containsPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public boolean addPermission(Permission permission) {
        return permissions.add(permission);
    }

    public boolean removePermission(Permission permission) {
        return permissions.remove(permission);
    }
}

