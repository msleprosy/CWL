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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public LocalDate getCreationDate() {
            return creationDate;
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
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setName(String name) {
        this.name = name;
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
        return id == snippet.id &&
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
