package net.portrix.meld.social.people.find.table;

import java.net.URI;
import java.util.UUID;

public class FindItem {

    private UUID id;

    private URI image;

    private String name;

    private String firstName;

    private String lastName;

    private CategorySelect category;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CategorySelect getCategory() {
        return category;
    }

    public void setCategory(CategorySelect category) {
        this.category = category;
    }
}
