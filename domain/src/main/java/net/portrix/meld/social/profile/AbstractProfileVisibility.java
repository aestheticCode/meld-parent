package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractAggregate;
import net.portrix.meld.social.people.Category;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class AbstractProfileVisibility extends AbstractAggregate {

    @OneToMany
    private final Set<Category> categories = new HashSet<>();

    public Set<Category> getCategories() {
        return categories;
    }

    public boolean addCategory(Category category) {
        return categories.add(category);
    }

    public void clearCategories() {
        categories.clear();
    }

}
