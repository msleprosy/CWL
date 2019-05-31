package com.epam.cwlhub.entities.snippet;

import java.time.LocalDate;
import java.util.Objects;

public class Snippet {
    private long id;
    private String name;
    private long ownerId;
    private long groupId;
    private String content;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private String tag;

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

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snippet snippet = (Snippet) o;
        return id == snippet.id &&
                ownerId == snippet.ownerId &&
                groupId == snippet.groupId &&
                Objects.equals(name, snippet.name) &&
                Objects.equals(content, snippet.content) &&
                Objects.equals(creationDate, snippet.creationDate) &&
                Objects.equals(modificationDate, snippet.modificationDate) &&
                Objects.equals(tag, snippet.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ownerId, groupId, content, creationDate, modificationDate, tag);
    }

    @Override
    public String toString() {
        return "Snippet{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", ownerId = " + ownerId +
                ", groupId = " + groupId +
                ", content = '" + content + '\'' +
                ", creationDate = " + creationDate +
                ", modificationDate = " + modificationDate +
                ", tag = '" + tag + '\'' +
                '}';
    }
}
