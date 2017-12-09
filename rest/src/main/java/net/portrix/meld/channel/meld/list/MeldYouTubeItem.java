package net.portrix.meld.channel.meld.list;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("youtube")
public class MeldYouTubeItem extends MeldItem {

    private String videoId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
