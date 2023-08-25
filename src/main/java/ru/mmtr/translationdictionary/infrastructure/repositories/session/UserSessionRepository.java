package ru.mmtr.translationdictionary.infrastructure.repositories.session;

import io.ebean.DB;
import io.ebean.ExpressionList;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.JwtConfiguration;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionPageRequestModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.user.UserEntity;
import ru.mmtr.translationdictionary.infrastructure.repositories.user.UserRole;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserSessionRepository {
    private final JwtConfiguration jwtConfiguration;

    public UserSessionRepository(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public CollectionResultModel<UserSessionModel> showAll() {
        List<UserSessionEntity> entities = DB
                .find(UserSessionEntity.class)
                .findList();

        return new CollectionResultModel<>(
                entities.stream().map(this::getModel).collect(Collectors.toList())
        );
    }

    public PageResultModel<UserSessionModel> getPage(UserSessionPageRequestModel criteria) {
        var expression = DB
                .find(UserSessionEntity.class)
                .setMaxRows(criteria.getPageSize())
                .setFirstRow((criteria.getPageNum() - 1) * criteria.getPageSize())
                .where();

        expression = applyFilters(expression, criteria);

        var page = expression.findPagedList();

        return new PageResultModel<>(
                page.getTotalCount(),
                page.getList().stream().map(this::getModel).collect(Collectors.toList())
        );
    }

    private ExpressionList<UserSessionEntity> applyFilters(ExpressionList<UserSessionEntity> expression,
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

    public UserSessionModel save(UserModel model) {
        var userSessionEntity = getEntity(model);
        DB.insert(userSessionEntity);

        if (userSessionEntity == null) {
            return new UserSessionModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

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

    private UserSessionEntity getEntity(UserModel model) {
        if (model == null) {
            return null;
        }

        var entity = new UserSessionEntity();
        entity.setSessionId(UUID.randomUUID());
        entity.setAccessToken(jwtConfiguration.generateAccessToken(UserRole.ADMIN.getRoleName(),entity.getUserId(), entity.getSessionId()));
        entity.setAccessTokenExpiredDate(LocalDateTime.now().plusMinutes(5));
        entity.setRefreshToken(jwtConfiguration.generateRefreshToken(UserRole.ADMIN.getRoleName(),entity.getUserId(), entity.getSessionId()));
        entity.setRefreshTokenExpiredDate(LocalDateTime.now().plusDays(1));
        entity.setUserId(model.getUserId());
        entity.setTokenCreatedAt(LocalDateTime.now());

        return entity;
    }

    private UserModel getModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        var model = new UserModel();
        model.setUserId(entity.getUserId());
        model.setLogin(entity.getLogin());
        model.setPassword(entity.getPassword());
        model.setLastName(entity.getLastName());
        model.setFirstName(entity.getFirstName());
        model.setFatherName(entity.getFatherName());
        model.setEmail(entity.getEmail());
        model.setPhoneNumber(entity.getPhoneNumber());
        model.setCreatedAt(entity.getCreatedAt());
        model.setModifiedAt(entity.getModifiedAt());
        model.setArchiveDate(entity.getArchiveDate());

        return model;
    }
}
