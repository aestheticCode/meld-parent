package net.portrix.meld.channel.meld.post.form;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("text")
public class MeldTextPostForm extends AbstractPostForm {

    @Override
    AbstractPostForm visit(Visitor visitor) {
        visitor.visit(this);
        return null;
    }
}
