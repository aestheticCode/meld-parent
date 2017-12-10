package net.portrix.meld.channel;

import net.portrix.generic.ddd.AbstractAggregate;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cn_post")
public abstract class MeldPost extends AbstractAggregate {

    @ManyToMany
    @OrderColumn
    private final List<User> likes = new ArrayList<>();

    @ManyToMany
    @OrderColumn
    private final List<MeldComment> comments = new ArrayList<>();

    @ManyToOne
    private User user;

    private String header;

    @Lob
    private String text;

    private Instant created;

    @ManyToOne
    private Category category;

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

    public abstract Object accept(Visitor visitor);

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public interface Visitor {

        Object visit(MeldImagePost post);

        Object visit(MeldTextPost post);

        Object visit(MeldYouTubePost post);

    }
}
