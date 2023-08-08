package ru.mmtr.translationdictionary.infrastruction.entities.dictionary;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DictionaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "word")
    private String word;

    @Column(name = "translation")
    private String translation;

    @Column(name = "from_language")
    private int from_language;

    @Column(name = "to_language")
    private int to_language;

    @Column(name = "createdat")
    private Date createdAt;

    @Column(name = "removedat")
    private Date removedAt;

    public DictionaryEntity() {

    }

    public DictionaryEntity(int id, String word, String translation, int from_language, int to_language, Date createdAt, Date removedAt) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.from_language = from_language;
        this.to_language = to_language;
        this.createdAt = createdAt;
        this.removedAt = removedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int getFrom_language() {
        return from_language;
    }

    public void setFrom_language(int from_language) {
        this.from_language = from_language;
    }

    public int getTo_language() {
        return to_language;
    }

    public void setTo_language(int to_language) {
        this.to_language = to_language;
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
