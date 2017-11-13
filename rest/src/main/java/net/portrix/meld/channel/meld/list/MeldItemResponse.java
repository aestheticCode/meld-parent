package net.portrix.meld.channel.meld.list;

import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.Links;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.meld.channel.meld.comment.MeldCommentResponse;
import net.portrix.meld.channel.meld.like.MeldLikeResponse;

import java.util.*;

/**
 * @author Patrick Bittner on 27.07.17.
 */
public class MeldItemResponse implements LinksContainer {

    private UUID id;

    private String name;

    private String time;

    private String text;

    private Link image;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Link getImage() {
        return image;
    }

    public void setImage(Link image) {
        this.image = image;
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

    public void addLike(MeldLikeResponse like) {
        likes.add(like);
    }

    public boolean hasImage() {
        return image != null;
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }
}
