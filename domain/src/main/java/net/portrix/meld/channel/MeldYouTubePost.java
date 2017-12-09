package net.portrix.meld.channel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cn_post")
public class MeldYouTubePost extends MeldPost {

    private String videoId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
