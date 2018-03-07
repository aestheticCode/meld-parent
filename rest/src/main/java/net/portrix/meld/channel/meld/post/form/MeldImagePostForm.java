package net.portrix.meld.channel.meld.post.form;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.Blob;

/**
 * @author Patrick Bittner on 07/10/16.
 */
@JsonTypeName("image")
public class MeldImagePostForm extends AbstractPostForm {

    private Blob file;

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }

    @Override
    public AbstractPostForm visit(Visitor visitor) {
        visitor.visit(this);
        return null;
    }

}
