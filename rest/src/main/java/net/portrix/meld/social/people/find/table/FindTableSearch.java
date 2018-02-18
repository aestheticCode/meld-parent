package net.portrix.meld.social.people.find.table;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class FindTableSearch {

    @QueryParam("index")
    private int index = 0;

    @QueryParam("limit")
    private int limit = Integer.MAX_VALUE;

    @QueryParam("sort")
    private List<String> sort;

    @QueryParam("name")
    private String name;

    @QueryParam("school")
    private UUID school;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getSchool() {
        return school;
    }

    public void setSchool(UUID school) {
        this.school = school;
    }
}
