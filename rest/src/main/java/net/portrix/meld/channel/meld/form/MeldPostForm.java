package net.portrix.meld.channel.meld.form;

import net.portrix.generic.rest.api.Blob;

/**
 * @author Patrick Bittner on 07/10/16.
 */
public class MeldPostForm {

    private String text;

    private Blob file;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }
}
