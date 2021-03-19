package net.hydrogen2oxygen.heavenAndEarth.domain;

import org.dizitart.no2.objects.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {

    @Id
    private UUID uuid;
    private String name;
    private String description;
    private List<Post> posts = new ArrayList<>();
    private List<Page> pages = new ArrayList<>();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
