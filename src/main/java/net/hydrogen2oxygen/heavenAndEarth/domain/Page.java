package net.hydrogen2oxygen.heavenAndEarth.domain;

import java.util.ArrayList;
import java.util.List;

public class Page extends Post {

    private List<Page> childPages = new ArrayList<>();

    public List<Page> getChildPages() {
        return childPages;
    }

    public void setChildPages(List<Page> childPages) {
        this.childPages = childPages;
    }
}
