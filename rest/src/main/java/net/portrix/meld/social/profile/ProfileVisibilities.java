package net.portrix.meld.social.profile;

import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.find.table.CategorySelect;

public class ProfileVisibilities {


    public static void setVisibilities(AbstractProfileVisibility entity, AbstractRestProfileVisibility form) {
        for (Category category : entity.getCategories()) {
            CategorySelect categorySelect = new CategorySelect();
            categorySelect.setId(category.getId());
            categorySelect.setName(category.getName());
            form.getCategories().add(categorySelect);
        }
    }

    public static void getVisibilities(AbstractRestProfileVisibility type,
                                       AbstractProfileVisibility contact,
                                       CategoryFinder service) {
        contact.clearCategories();
        for (CategorySelect categorySelect : type.getCategories()) {
            Category category = service.findCategory(categorySelect.getId());
            contact.addCategory(category);
        }
    }

}
