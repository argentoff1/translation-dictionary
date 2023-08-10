package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import ru.mmtr.translationdictionary.infrastructure.repositories.BaseModel;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Languages")
public class LanguageEntity {

    public static final String LANGUAGE_ID = "languageId";
    public static final String LANGUAGE_NAME = "languageName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = LANGUAGE_ID)
    private UUID languageId;

    @Column(name = LANGUAGE_NAME)
    private String language_name;

    private Date createdAt;

    private Date modifiedAt;

    /*@OneToOne(cascade = CascadeType.ALL)
    DictionaryEntity dictionary;*/

    public LanguageEntity(UUID languageId, String language_name, Date createdAt, Date modifiedAt) {
        this.languageId = languageId;
        this.language_name = language_name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public LanguageEntity(UUID languageId, String language_name) {
        this.languageId = languageId;
        this.language_name = language_name;
    }

    public LanguageEntity() {}

    public UUID getLanguageId() {
        return languageId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
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

    /*public DictionaryEntity getDictionary() {
        return dictionary;
    }

    public void setDictionary(DictionaryEntity dictionary) {
        this.dictionary = dictionary;
    }*/
}
