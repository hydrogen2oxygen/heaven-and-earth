package net.hydrogen2oxygen.heavenAndEarth.domain;

import org.dizitart.no2.objects.Id;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Post extends TagAndCategoryContainer {

    @Id
    private String title;
    private Boolean draft;
    private Calendar created;
    private Calendar changed;
    private List<PostElement> postElements;

    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getChanged() {
        return changed;
    }

    public void setChanged(Calendar changed) {
        this.changed = changed;
    }

    public List<PostElement> getPostElements() {
        return postElements;
    }

    public void setPostElements(List<PostElement> postElements) {
        this.postElements = postElements;
    }
}
