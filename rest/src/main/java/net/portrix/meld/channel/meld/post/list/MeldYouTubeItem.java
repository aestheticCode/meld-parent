package net.portrix.meld.channel.meld.post.list;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("youtube")
public class MeldYouTubeItem extends AbstractMeldItem {

    private String videoId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
