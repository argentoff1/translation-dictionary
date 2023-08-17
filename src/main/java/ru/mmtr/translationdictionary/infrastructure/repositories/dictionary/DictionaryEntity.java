package ru.mmtr.translationdictionary.infrastructure.repositories.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Entity
@Table(name = "'Dictionaries'")
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryEntity {
    public static final String DICTIONARY_ID = "dictionary_id";
    public static final String WORD = "word";
    public static final String TRANSLATION = "translation";
    public static final String FROM_LANGUAGE = "from_language";
    public static final String TO_LANGUAGE = "to_language";
    public static final String DICTIONARY_CREATED_AT = "created_at";
    public static final String DICTIONARY_MODIFIED_AT = "modified_at";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DICTIONARY_ID)
    private UUID dictionaryId;

    @Column(name = WORD)
    private String word;

    @Column(name = TRANSLATION)
    private String translation;

    @Column(name = FROM_LANGUAGE)
    private UUID fromLanguage;

    @Column(name = TO_LANGUAGE)
    private UUID toLanguage;

    @Column(name = DICTIONARY_CREATED_AT)
    private Timestamp createdAt;

    @Column(name = DICTIONARY_MODIFIED_AT)
    private Timestamp modifiedAt;

    public void setDictionaryId(UUID dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void setFromLanguage(UUID fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public void setToLanguage(UUID toLanguage) {
        this.toLanguage = toLanguage;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
