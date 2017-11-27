package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_school")
public class School extends AbstractEntity {

    private String name;

    private String course;

    @Column(name = "startDate")
    private LocalDate start;

    @Column(name = "endDate")
    private LocalDate end;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
