package ru.mmtr.translationdictionary.infrastruction.repositories.language;

import ru.mmtr.translationdictionary.infrastruction.BaseModel;
import ru.mmtr.translationdictionary.infrastruction.repositories.dictionary.DictionaryEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "languages")
public class LanguageEntity extends BaseModel {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private int id;

    @Column(name = "language_name")
    private String name;

    private Date createdAt;

    private Date modifiedAt;

    public LanguageEntity(int id, String name, Date createdAt, Date modifiedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }*/

    private int id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    DictionaryEntity dictionary;

    public LanguageEntity() {}

    public LanguageEntity(int id, String name, DictionaryEntity dictionary) {
        super();
        this.id = id;
        this.name = name;
        this.dictionary = dictionary;
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

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public DictionaryEntity getDictionary() {
        return dictionary;
    }

    public void setDictionary(DictionaryEntity dictionary) {
        this.dictionary = dictionary;
    }
}
