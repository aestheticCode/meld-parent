package net.portrix.meld.channel.meld.post.form;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.net.URL;

@JsonTypeName("link")
public class MeldLinkPostForm extends AbstractPostForm {

    private URL link;

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    @Override
    public AbstractPostForm visit(Visitor visitor) {
        return visitor.visit(this);
    }
}
