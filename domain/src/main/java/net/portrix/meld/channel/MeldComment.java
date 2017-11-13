package net.portrix.meld.channel;

import net.portrix.generic.ddd.AbstractEntity;
import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 06/10/16.
 */
@Entity
@Table(name = "cn_comment")
public class MeldComment extends AbstractEntity {

    @ManyToOne
    private User user;

    @Lob
    private String text;

    private Instant created;

    @ManyToMany
    @OrderColumn
    private final List<User> likes = new ArrayList<>();

    @ManyToMany
    @OrderColumn
    private final List<MeldComment> comments = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public List<User> getLikes() {
        return likes;
    }

    public List<MeldComment> getComments() {
        return comments;
    }

    public boolean containsLike(User user) {
        return likes.contains(user);
    }

    public void removeLike(User user) {
        likes.remove(user);
    }

    public void addLike(User user) {
        likes.add(user);
    }
}
