package ru.mmtr.translationdictionary.infrastruction.entities.language;

import java.util.Date;

public class LanguageEntity {

    private int id;

    private String name;

    private Date createdAt;

    private Date removedAt;

    public LanguageEntity() {}

    public LanguageEntity(int id, String name, Date createdAt, Date removedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.removedAt = removedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getRemovedAt() {
        return removedAt;
    }

    public void setRemovedAt(Date removedAt) {
        this.removedAt = removedAt;
    }
}
