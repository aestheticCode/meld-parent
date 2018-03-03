package net.portrix.meld.usercontrol.user.form;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.api.Blob;
import net.portrix.meld.usercontrol.Gender;
import net.portrix.meld.usercontrol.group.multiselect.GroupSelect;
import net.portrix.meld.usercontrol.role.multiselect.RoleSelect;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Patrick Bittner on 30.05.2015.
 */
public class UserForm implements LinksContainer {

    private UUID id;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private Gender gender;

    private Set<RoleSelect> roles = new HashSet<>();

    private Set<GroupSelect> groups = new HashSet<>();

    private Blob image;

    private Set<Link> links = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Set<RoleSelect> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleSelect> roles) {
        this.roles = roles;
    }

    public Set<GroupSelect> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupSelect> groups) {
        this.groups = groups;
    }

    @Override
    public Set<Link> getLinks() {
        return links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }
}
