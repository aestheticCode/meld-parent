package net.portrix.meld.channel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cn_post")
public class MeldTextPost extends MeldPost {
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
