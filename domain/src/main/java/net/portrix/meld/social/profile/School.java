package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_school")
public class School extends AbstractEntity {

    private String name;

    private String course;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="semester", column=@Column(name = "startSemester")),
            @AttributeOverride(name="year", column=@Column(name = "startYear"))
    })
    private SchoolDate start;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="semester", column=@Column(name = "endSemester")),
            @AttributeOverride(name="year", column=@Column(name = "endYear"))
    })
    private SchoolDate end;

    private boolean tillNow;

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

    public SchoolDate getStart() {
        return start;
    }

    public void setStart(SchoolDate start) {
        this.start = start;
    }

    public SchoolDate getEnd() {
        return end;
    }

    public void setEnd(SchoolDate end) {
        this.end = end;
    }

    public boolean isTillNow() {
        return tillNow;
    }

    public void setTillNow(boolean tillNow) {
        this.tillNow = tillNow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
