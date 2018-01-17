package net.portrix.meld.social.profile.education.form;

import net.portrix.meld.social.profile.SchoolDate;

public class SchoolDateForm {

    private int year;

    private SchoolDate.Semester semester;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public SchoolDate.Semester getSemester() {
        return semester;
    }

    public void setSemester(SchoolDate.Semester semester) {
        this.semester = semester;
    }
}
