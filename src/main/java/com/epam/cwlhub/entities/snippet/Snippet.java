package com.epam.cwlhub.entities.snippet;

import java.time.LocalDate;
import java.util.Objects;

public class Snippet {
    private long id;
    private String name;
    private long ownerId;
    private long groupId;
    private SnippetProperties properties;

    private class SnippetProperties {
        private String content;
        private LocalDate creationDate;
        private LocalDate modificationDate;
        private String tag;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SnippetProperties that = (SnippetProperties) o;
            return Objects.equals(content, that.content) &&
                    Objects.equals(creationDate, that.creationDate) &&
                    Objects.equals(modificationDate, that.modificationDate) &&
                    Objects.equals(tag, that.tag);
        }

        @Override
        public int hashCode() {
            return Objects.hash(content, creationDate, modificationDate, tag);
        }

        @Override
        public String toString() {
            return  "content = '" + content + '\'' +
                    ", creationDate =" + creationDate +
                    ", modificationDate =" + modificationDate +
                    ", tag ='" + tag + '\'';
        }
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
        return this.properties.content;
    }

    public void setContent(String content) {
        this.properties.content = content;
    }

    public LocalDate getCreationDate() {
        return this.properties.creationDate;
    }

    public LocalDate getModificationDate() {
        return this.properties.modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.properties.modificationDate = modificationDate;
    }

    public String getTag() {
        return this.properties.tag;
    }

    public void setTag(String tag) {
        this.properties.tag = tag;
    }

    public SnippetProperties getProperties() {
        return properties;
    }

    public void setProperties(SnippetProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snippet snippet = (Snippet) o;
        return  id == snippet.id &&
                ownerId == snippet.ownerId &&
                groupId == snippet.groupId &&
                Objects.equals(name, snippet.name) &&
                Objects.equals(properties, snippet.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ownerId, groupId, properties);
    }
}
