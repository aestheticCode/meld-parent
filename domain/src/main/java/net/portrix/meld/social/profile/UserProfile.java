package net.portrix.meld.social.profile;

import net.portrix.meld.usercontrol.User;

import javax.persistence.*;

@Entity
@Table(name = "so_user_profile")
@NamedQuery(name = "findUserProfileByUserId", query = "select u from UserProfile u where u.user.id = :id")
public class UserProfile extends AbstractProfileVisibility {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private User user = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
