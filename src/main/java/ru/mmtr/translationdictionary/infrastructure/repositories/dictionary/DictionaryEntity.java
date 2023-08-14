package ru.mmtr.translationdictionary.infrastructure.repositories.dictionary;

import javax.persistence.*;

import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Dictionaries")
public class DictionaryEntity {
    public static final String DICTIONARY_ID = "dictionaryId";
    public static final String WORD = "word";
    public static final String TRANSLATION = "translation";
    public static final String FROM_LANGUAGE = "fromLanguage";
    public static final String TO_LANGUAGE = "toLanguage";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DICTIONARY_ID)
    private UUID dictionaryId;

    @Column(name = WORD)
    private String word;

    @Column(name = TRANSLATION)
    private String translation;

    @Column(name = FROM_LANGUAGE)
    //@ManyToOne
    //@JoinColumn(name = "language_id")
    private UUID fromLanguage;

    @Column(name = TO_LANGUAGE)
    //@ManyToOne
    //@JoinColumn(name = "language_id")
    private UUID toLanguage;

    /*@Column(name = "createdat")
    private Date createdAt;

    @Column(name = "removedat")
    private Date removedAt;*/

    public DictionaryEntity() {
    }

    public DictionaryEntity(UUID dictionaryId, String word, String translation, UUID fromLanguage, UUID toLanguage) {
        this.dictionaryId = dictionaryId;
        this.word = word;
        this.translation = translation;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
    }

    public UUID getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(UUID dictionaryId) {
        this.dictionaryId = dictionaryId;
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

    public UUID getFromLanguage() {
        return fromLanguage;
    }

    public void setFromLanguage(UUID fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public UUID getToLanguage() {
        return toLanguage;
    }

    public void setToLanguage(UUID toLanguage) {
        this.toLanguage = toLanguage;
    }
}
