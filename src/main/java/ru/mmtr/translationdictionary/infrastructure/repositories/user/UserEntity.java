package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "'Users'")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    public static final String USER_ID = "user_id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String LAST_NAME = "last_name";
    public static final String FIRST_NAME = "first_name";
    public static final String FATHER_NAME = "father_name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String CREATED_AT = "created_at";
    public static final String MODIFIED_AT = "modified_at";
    public static final String ARCHIVE_DATE = "archive_date";
    public static final String ROLE_NAME = "role_name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = USER_ID)
    private UUID userId;

    @Column(name = LOGIN)
    private String login;

    @Column(name = PASSWORD, updatable = true)
    private String password;

    @Column(name = LAST_NAME)
    private String lastName;

    @Column(name = FIRST_NAME)
    private String firstName;

    @Column(name = FATHER_NAME)
    private String fatherName;

    @Column(name = EMAIL)
    private String email;

    @Column(name = PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = CREATED_AT)
    private LocalDateTime createdAt;

    @Column(name = MODIFIED_AT)
    private LocalDateTime modifiedAt;

    @Column(name = ARCHIVE_DATE)
    private LocalDateTime archiveDate;

    @Column(name = ROLE_NAME)
    private String roleName;
}
