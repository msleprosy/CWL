package com.epam.cwlhub.entities.group;

import java.util.List;
import java.util.Objects;

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
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return id == group.id &&
                creatorId == group.creatorId &&
                name.equals(group.name) &&
                Objects.equals(description, group.description);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31*result + name.hashCode();
        result = 31*result + description.hashCode();
        result = 31*result + Long.hashCode(creatorId);
        return result;
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
