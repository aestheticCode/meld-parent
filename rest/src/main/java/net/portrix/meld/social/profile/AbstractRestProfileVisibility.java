package net.portrix.meld.social.profile;

import net.portrix.meld.social.people.find.table.CategorySelect;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRestProfileVisibility {

    private final Set<CategorySelect> categories = new HashSet<>();

    public Set<CategorySelect> getCategories() {
        return categories;
    }

    public boolean addCategory(CategorySelect category) {
        return categories.add(category);
    }
}
