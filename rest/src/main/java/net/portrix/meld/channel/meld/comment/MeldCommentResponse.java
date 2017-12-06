package net.portrix.meld.channel.meld.comment;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.meld.channel.meld.like.MeldLikeResponse;

import java.util.*;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class MeldCommentResponse implements LinksContainer {

    private UUID id;

    private String name;

    private String text;

    private String time;

    private Link avatar;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Link getAvatar() {
        return avatar;
    }

    public void setAvatar(Link avatar) {
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

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }

    public void addLike(MeldLikeResponse response) {
        likes.add(response);
    }
}
