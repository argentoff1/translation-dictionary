package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Languages")
@AllArgsConstructor
@NoArgsConstructor
public class LanguageEntity {
    public static final String LANGUAGE_ID = "languageId";
    public static final String LANGUAGE_NAME = "languageName";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = LANGUAGE_ID)
    private UUID languageId;

    @Column(name = LANGUAGE_NAME)
    private String languageName;

    /*private Date createdAt;

    private Date modifiedAt;*/

    /*@OneToOne(cascade = CascadeType.ALL)
    DictionaryEntity dictionary;*/







    /*public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }*/

    /*public DictionaryEntity getDictionary() {
        return dictionary;
    }

    public void setDictionary(DictionaryEntity dictionary) {
        this.dictionary = dictionary;
    }*/
}
