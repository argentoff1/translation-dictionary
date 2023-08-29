package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "'Languages'")
@NoArgsConstructor
@AllArgsConstructor
public class LanguageEntity {
    public static final String LANGUAGE_ID = "language_id";
    public static final String LANGUAGE_NAME = "language_name";
    public static final String LANGUAGE_CREATED_AT = "created_at";
    public static final String LANGUAGE_MODIFIED_AT = "modified_at";
    public static final String CREATED_USER_ID = "created_user_id";
    public static final String MODIFIED_USER_ID = "modified_user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = LANGUAGE_ID)
    private UUID languageId;

    @Column(name = LANGUAGE_NAME)
    private String languageName;

    @Column(name = LANGUAGE_CREATED_AT)
    private LocalDateTime createdAt;

    @Column(name = LANGUAGE_MODIFIED_AT)
    private LocalDateTime modifiedAt;

    @Column(name = CREATED_USER_ID)
    private UUID createdUserId;

    @Column(name = MODIFIED_USER_ID)
    private UUID modifiedUserId;
}
