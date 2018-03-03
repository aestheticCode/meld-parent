package net.portrix.meld.channel.meld.form;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Sets;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.meld.channel.MeldLinkPost;
import net.portrix.meld.social.people.find.table.CategorySelect;

import java.util.Set;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(MeldTextPostForm.class),
        @JsonSubTypes.Type(MeldImagePostForm.class),
        @JsonSubTypes.Type(MeldYouTubePostForm.class),
        @JsonSubTypes.Type(MeldPhotoPostForm.class),
        @JsonSubTypes.Type(MeldLinkPostForm.class)
})
public abstract class AbstractPostForm implements LinksContainer {

    private UUID id;

    private String text;

    private CategorySelect category;

    private Set<Link> links = Sets.newHashSet();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CategorySelect getCategory() {
        return category;
    }

    public void setCategory(CategorySelect category) {
        this.category = category;
    }

    @Override
    public Set<Link> getLinks() {
        return links;
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }

    abstract AbstractPostForm visit(Visitor visitor);

    public interface Visitor {

        AbstractPostForm visit(MeldImagePostForm form);

        AbstractPostForm visit(MeldTextPostForm form);

        AbstractPostForm visit(MeldYouTubePostForm form);

        AbstractPostForm visit(MeldPhotoPostForm form);

        AbstractPostForm visit(MeldLinkPostForm form);
    }
}
