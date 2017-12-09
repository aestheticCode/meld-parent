package net.portrix.meld.channel.meld.form;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("youtube")
public class MeldYouTubePostForm extends AbstractPostForm {

    private String videoId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    AbstractPostForm visit(Visitor visitor) {
        return visitor.visit(this);
    }
}
