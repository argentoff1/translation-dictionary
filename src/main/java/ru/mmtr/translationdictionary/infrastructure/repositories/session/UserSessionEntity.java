package ru.mmtr.translationdictionary.infrastructure.repositories.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "'UserSessions'")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionEntity {
    public static final String SESSION_ID = "session_id";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String TOKEN_CREATED_AT = "token_created_at";
    public static final String ACCESS_TOKEN_EXPIRED_DATE = "access_token_expired_date";
    public static final String REFRESH_TOKEN_EXPIRED_DATE = "refresh_token_expired_date";
    public static final String USER_ID = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = SESSION_ID)
    private UUID sessionId;

    @Column(name = ACCESS_TOKEN)
    private String accessToken;

    @Column(name = REFRESH_TOKEN)
    private String refreshToken;

    @Column(name = TOKEN_CREATED_AT)
    private LocalDateTime tokenCreatedAt;

    @Column(name = ACCESS_TOKEN_EXPIRED_DATE)
    private LocalDateTime accessTokenExpiredDate;

    @Column(name = REFRESH_TOKEN_EXPIRED_DATE)
    private LocalDateTime refreshTokenExpiredDate;

    @Column(name = USER_ID)
    private UUID userId;
}
