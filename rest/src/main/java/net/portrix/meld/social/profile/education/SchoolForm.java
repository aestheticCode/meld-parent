package net.portrix.meld.social.profile.education;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class SchoolForm {

    private UUID id;

    private String name;

    private String course;

    private SchoolDateForm start;

    private SchoolDateForm end;

    private boolean tillNow;

    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public SchoolDateForm getStart() {
        return start;
    }

    public void setStart(SchoolDateForm start) {
        this.start = start;
    }

    public SchoolDateForm getEnd() {
        return end;
    }

    public void setEnd(SchoolDateForm end) {
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
