package net.hydrogen2oxygen.heavenAndEarth.domain;

import java.util.ArrayList;
import java.util.List;

abstract class TagAndCategoryContainer {

    private List<Tag> tags = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
