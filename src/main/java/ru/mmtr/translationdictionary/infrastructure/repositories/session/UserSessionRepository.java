package ru.mmtr.translationdictionary.infrastructure.repositories.session;

import io.ebean.DB;
import io.ebean.ExpressionList;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.user.UserLoginLogoutModel;
import ru.mmtr.translationdictionary.infrastructure.security.JwtProvider;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionPageRequestModel;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserSessionRepository {
    private final JwtProvider jwtProvider;

    public UserSessionRepository(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public SuccessResultModel updateTokens(UserLoginLogoutModel user, String accessToken, String refreshToken) {
        DB
                .update(UserSessionEntity.class)
                .set(UserSessionEntity.ACCESS_TOKEN, accessToken)
                .set(UserSessionEntity.REFRESH_TOKEN, refreshToken)
                .set(UserSessionEntity.TOKEN_CREATED_AT, LocalDateTime.now())
                .set(UserSessionEntity.ACCESS_TOKEN_EXPIRED_DATE, LocalDateTime.now().plusDays(1))
                .set(UserSessionEntity.REFRESH_TOKEN_EXPIRED_DATE, LocalDateTime.now().plusDays(30))
                .where()
                .eq(UserSessionEntity.USER_ID, user.getUserId())
                .update();

        return new SuccessResultModel(true);
    }

    public PageResultModel<UserSessionModel> getPageSessions(UserSessionPageRequestModel criteria) {
        var expression = DB
                .find(UserSessionEntity.class)
                .setMaxRows(criteria.getPageSize())
                .setFirstRow((criteria.getPageNum() - 1) * criteria.getPageSize())
                .where();

        expression = applyFiltersSessions(expression, criteria);

        var page = expression.findPagedList();

        return new PageResultModel<>(
                page.getTotalCount(),
                page.getList().stream().map(this::getSessionModel).collect(Collectors.toList())
        );
    }

    private ExpressionList<UserSessionEntity> applyFiltersSessions(ExpressionList<UserSessionEntity> expression,
                                                                   UserSessionPageRequestModel criteria) {
        if (criteria.getTokenCreatedAtFromFilter() != null) {
            expression = expression.ge(UserSessionEntity.TOKEN_CREATED_AT, criteria.getTokenCreatedAtFromFilter());
        }
        if (criteria.getTokenCreatedAtToFilter() != null) {
            expression = expression.le(UserSessionEntity.TOKEN_CREATED_AT, criteria.getTokenCreatedAtToFilter());
        }
        if (criteria.getAccessTokenExpiredDateFromFilter() != null) {
            expression = expression.ge(UserSessionEntity.ACCESS_TOKEN_EXPIRED_DATE, criteria.getAccessTokenExpiredDateFromFilter());
        }
        if (criteria.getTokenCreatedAtToFilter() != null) {
            expression = expression.le(UserSessionEntity.ACCESS_TOKEN_EXPIRED_DATE, criteria.getAccessTokenExpiredDateToFilter());
        }
        if (criteria.getRefreshTokenExpiredDateFromFilter() != null) {
            expression = expression.ge(UserSessionEntity.REFRESH_TOKEN_EXPIRED_DATE, criteria.getRefreshTokenExpiredDateFromFilter());
        }
        if (criteria.getRefreshTokenExpiredDateToFilter() != null) {
            expression = expression.le(UserSessionEntity.REFRESH_TOKEN_EXPIRED_DATE, criteria.getRefreshTokenExpiredDateToFilter());
        }
        return expression;
    }

    public UserSessionModel getByUserId(UUID id) {
        UserSessionEntity foundEntity = DB
                .find(UserSessionEntity.class)
                .where()
                .eq(UserSessionEntity.USER_ID, id)
                .findOne();

        return getSessionModel(foundEntity);
    }

    public UserSessionModel save(UserLoginLogoutModel model) {
        var userSessionEntity = getUserEntity(model);
        DB.insert(userSessionEntity);

        return getSessionModel(userSessionEntity);
    }

    public SuccessResultModel delete(UserLoginLogoutModel model) {
        DB.find(UserSessionEntity.class)
                .where()
                .eq(UserSessionEntity.USER_ID, model.getUserId())
                .delete();

        return new SuccessResultModel(true);
    }

    private UserSessionModel getSessionModel(UserSessionEntity entity) {
        if (entity == null) {
            return null;
        }

        var model = new UserSessionModel();
        model.setSessionId(entity.getSessionId());
        model.setAccessToken(entity.getAccessToken());
        model.setRefreshToken(entity.getRefreshToken());
        model.setTokenCreatedAt(entity.getTokenCreatedAt());
        model.setAccessTokenExpiredDate(entity.getAccessTokenExpiredDate());
        model.setRefreshTokenExpiredDate(entity.getRefreshTokenExpiredDate());
        model.setUserId(entity.getUserId());

        return model;
    }

    private UserSessionEntity getUserEntity(UserLoginLogoutModel user) {
        if (user == null) {
            return null;
        }

        var entity = new UserSessionEntity();
        entity.setSessionId(UUID.randomUUID());
        entity.setAccessToken(jwtProvider.generateAccessToken(user, entity.getSessionId()));
        entity.setAccessTokenExpiredDate(LocalDateTime.now().plusDays(1));
        entity.setRefreshToken(jwtProvider.generateRefreshToken(user, entity.getSessionId()));
        entity.setRefreshTokenExpiredDate(LocalDateTime.now().plusDays(30));
        entity.setUserId(user.getUserId());
        entity.setTokenCreatedAt(LocalDateTime.now());

        return entity;
    }
}
