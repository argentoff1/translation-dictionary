package ru.mmtr.translationdictionary.infrastructure.repositories.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
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
    public static final String CREATED_USER_ID = "created_user_id";
    public static final String MODIFIED_USER_ID = "modified_user_id";

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
    private LocalDateTime createdAt;

    @Column(name = DICTIONARY_MODIFIED_AT)
    private LocalDateTime modifiedAt;

    @Column(name = CREATED_USER_ID)
    private UUID createdUserId;

    @Column(name = MODIFIED_USER_ID)
    private UUID modifiedUserId;
}
