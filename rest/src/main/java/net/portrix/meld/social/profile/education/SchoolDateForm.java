package net.portrix.meld.social.profile.education;

public class SchoolDateForm {

    public enum Semester {
        WINTER,
        SUMMER
    }

    private int year;

    private Semester semester;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
