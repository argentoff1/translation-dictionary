package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/*@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor*/
public class UserEntity {
    /*public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String FULL_NAME = "full_name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String USER_CREATED_AT = "created_at";
    public static final String USER_MODIFIED_AT = "modified_at";
    public static final String ROLE_ID = "role_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = USER_ID)
    private UUID userId;

    @Column(name = USERNAME)
    private String username;

    @Column(name = PASSWORD)
    private String password;

    @Column(name = FULL_NAME)
    private String fullName;

    @Column(name = EMAIL)
    private String email;

    @Column(name = PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = USER_CREATED_AT)
    private LocalDateTime createdAt;

    @Column(name = USER_MODIFIED_AT)
    private LocalDateTime modifiedAt;

    @Column(name = ROLE_ID)
    private UUID roleId;*/
}
