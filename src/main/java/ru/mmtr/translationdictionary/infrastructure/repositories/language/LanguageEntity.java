package ru.mmtr.translationdictionary.infrastructure.repositories.language;

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
    private String languageName;

    private Date createdAt;

    private Date modifiedAt;

    /*@OneToOne(cascade = CascadeType.ALL)
    DictionaryEntity dictionary;*/

    public LanguageEntity(UUID languageId, String languageName, Date createdAt, Date modifiedAt) {
        this.languageId = languageId;
        this.languageName = languageName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public LanguageEntity(UUID languageId, String languageName) {
        this.languageId = languageId;
        this.languageName = languageName;
    }

    public LanguageEntity() {}

    public UUID getLanguageId() {
        return languageId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
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
