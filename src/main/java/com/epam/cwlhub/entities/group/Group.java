package com.epam.cwlhub.entities.group;

import java.util.List;

public class Group {

    private long id;
    private String name;
    private String description;
    private long creatorId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;

        Group group = (Group) o;

        return getId() == group.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Group{" +
                "ID = " + id +
                ", Name = '" + name + '\'' +
                ", Description = '" + description + '\'' +
                ", Creator ID = " + creatorId +
                '}';
    }
}
