package net.portrix.meld.social.profile;

import net.portrix.meld.social.people.Category;

import java.util.UUID;

public interface CategoryFinder {
    Category findCategory(UUID id);
}
