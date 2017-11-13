package net.portrix.meld.channel;

import net.portrix.generic.ddd.AbstractAggregate;
import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 06/10/16.
 */
@Entity
@Table(name = "cn_post")
public class MeldPost extends AbstractAggregate {

    @ManyToOne
    private User user;

    private String header;

    @Lob
    private String text;

    private Instant created;

    @ManyToOne(cascade = CascadeType.ALL)
    private MeldImage image;

    private String fileName;

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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    public MeldImage getImage() {
        return image;
    }

    public void setImage(MeldImage image) {
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<MeldComment> getComments() {
        return comments;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void addComment(MeldComment comment) {
        comments.add(comment);
    }

    public void removeLike(User user) {
        likes.remove(user);
    }

    public void addLike(User user) {
        likes.add(user);
    }

    public boolean containsLike(User user) {
        return likes.contains(user);
    }

    public boolean hasImage() {
        return image != null;
    }
}
