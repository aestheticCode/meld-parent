package net.portrix.meld.channel.meld.post.list;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.meld.channel.meld.comment.form.MeldCommentResponse;
import net.portrix.meld.channel.meld.like.form.MeldLikeResponse;

import java.net.URI;
import java.util.*;

/**
 * @author Patrick Bittner on 27.07.17.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(MeldImageItem.class),
        @JsonSubTypes.Type(MeldTextItem.class),
        @JsonSubTypes.Type(MeldYouTubeItem.class),
        @JsonSubTypes.Type(MeldPhotoItem.class),
        @JsonSubTypes.Type(MeldLinkItem.class)
})
public abstract class AbstractMeldItem implements LinksContainer {

    private UUID id;

    private URI avatar;

    private String name;

    private String time;

    private String category;

    private String text;

    private List<MeldLikeResponse> likes = new ArrayList<>();

    private List<MeldCommentResponse> comments = new ArrayList<>();

    private Set<Link> links = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public URI getAvatar() {
        return avatar;
    }

    public void setAvatar(URI avatar) {
        this.avatar = avatar;
    }

    public List<MeldLikeResponse> getLikes() {
        return likes;
    }

    public void setLikes(List<MeldLikeResponse> likes) {
        this.likes = likes;
    }

    public List<MeldCommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<MeldCommentResponse> comments) {
        this.comments = comments;
    }

    @Override
    public Set<Link> getLinks() {
        return links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }

    public void addLike(MeldLikeResponse like) {
        likes.add(like);
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }

}
