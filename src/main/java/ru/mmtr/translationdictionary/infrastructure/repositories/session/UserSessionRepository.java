package ru.mmtr.translationdictionary.infrastructure.repositories.session;

import io.ebean.DB;
import io.ebean.ExpressionList;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.infrastructure.security.JwtProvider;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionPageRequestModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserSessionRepository {
    private final JwtProvider jwtProvider;

    public UserSessionRepository(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public CollectionResultModel<UserSessionModel> showAll() {
        List<UserSessionEntity> entities = DB
                .find(UserSessionEntity.class)
                .findList();

        return new CollectionResultModel<>(
                entities.stream().map(this::getModel).collect(Collectors.toList())
        );
    }

    public String getRefreshToken(String subject) {
        var result = DB
                .find(UserSessionEntity.class)
                .where()
                .ilike(UserSessionEntity.REFRESH_TOKEN, "%" + subject + "%")
                .findOne();

        if (result != null) {
            return result.getRefreshToken();
        }
        return null;
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
                page.getList().stream().map(this::getModel).collect(Collectors.toList())
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

    public UserSessionModel getById(UUID id) {
        UserSessionEntity foundEntity = DB
                .find(UserSessionEntity.class)
                .where()
                .eq(UserSessionEntity.SESSION_ID, id)
                .findOne();

        if (foundEntity == null) {
            return new UserSessionModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполненными");
        }

        return getModel(foundEntity);
    }

    public UserSessionModel saveAdmin(UserModel model) {
        var userSessionEntity = getAdminEntity(model);
        DB.insert(userSessionEntity);

        return getModel(userSessionEntity);
    }

    public UserSessionModel saveUser(UserModel model) {
        var userSessionEntity = getUserEntity(model);
        DB.insert(userSessionEntity);

        return getModel(userSessionEntity);
    }

    public SuccessResultModel delete(UserModel model) {
        UserSessionEntity foundEntity = DB
                .find(UserSessionEntity.class)
                .where()
                .eq(UserSessionEntity.USER_ID, model.getUserId())
                .findOne();

        if (foundEntity == null) {
            return new SuccessResultModel("CAN_NOT_DELETE",
                    "Не удалось удалить данные. Поля должны быть корректно заполненными");
        }

        DB.find(UserSessionEntity.class)
                .where()
                .eq(UserSessionEntity.USER_ID, model.getUserId())
                .delete();

        return new SuccessResultModel(true);
    }

    private UserSessionModel getModel(UserSessionEntity entity) {
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

    private UserSessionEntity getAdminEntity(UserModel user) {
        if (user == null) {
            return null;
        }

        var entity = new UserSessionEntity();
        entity.setSessionId(UUID.randomUUID());
        entity.setAccessToken(jwtProvider.generateAccessToken(user, entity.getSessionId()));
        entity.setAccessTokenExpiredDate(LocalDateTime.now().plusMinutes(60));
        entity.setRefreshToken(jwtProvider.generateRefreshToken(user, entity.getSessionId()));
        entity.setRefreshTokenExpiredDate(LocalDateTime.now().plusDays(1));
        entity.setUserId(user.getUserId());
        entity.setTokenCreatedAt(LocalDateTime.now());

        return entity;
    }

    private UserSessionEntity getUserEntity(UserModel user) {
        if (user == null) {
            return null;
        }

        var entity = new UserSessionEntity();
        entity.setSessionId(UUID.randomUUID());
        entity.setAccessToken(jwtProvider.generateAccessToken(user, entity.getSessionId()));
        entity.setAccessTokenExpiredDate(LocalDateTime.now().plusMinutes(60));
        entity.setRefreshToken(jwtProvider.generateRefreshToken(user, entity.getSessionId()));
        entity.setRefreshTokenExpiredDate(LocalDateTime.now().plusDays(1));
        entity.setUserId(user.getUserId());
        entity.setTokenCreatedAt(LocalDateTime.now());

        return entity;
    }
}
