package com.epam.cwlhub.entities.group;

import java.util.List;

public class Group {

    private long id;
    private String name;
    private String description;
    private long creatorId;
    private List<Long> files;
    private List<Long> users;

    public Group(){
    }

    public Group(String name, String description, long creatorId) {
        this.name = name;
        this.description = description;
        this.creatorId = creatorId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public List<Long> getFiles() {
        return files;
    }

    public void setFiles(List<Long> files) {
        this.files = files;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }
}
