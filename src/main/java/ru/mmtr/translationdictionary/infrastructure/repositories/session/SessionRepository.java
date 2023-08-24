package ru.mmtr.translationdictionary.infrastructure.repositories.session;

import io.ebean.DB;
import io.ebean.ExpressionList;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.JwtUtil;
import ru.mmtr.translationdictionary.domain.common.CollectionResultModel;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.session.SessionModel;
import ru.mmtr.translationdictionary.domain.session.SessionPageRequestModel;
import ru.mmtr.translationdictionary.domain.session.SessionSaveModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SessionRepository {

    public SessionRepository(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    JwtUtil jwtUtil;
    public CollectionResultModel<SessionModel> showAll() {
        List<SessionEntity> entities = DB
                .find(SessionEntity.class)
                .findList();

        return new CollectionResultModel<>(
                entities.stream().map(this::getModel).collect(Collectors.toList())
        );
    }

    public PageResultModel<SessionModel> getPage(SessionPageRequestModel criteria) {
        var expression = DB
                .find(SessionEntity.class)
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

    private ExpressionList<SessionEntity> applyFilters(ExpressionList<SessionEntity> expression,
                                                       SessionPageRequestModel criteria) {
        if (criteria.getTokenCreatedAtFromFilter() != null) {
            expression = expression.ge(SessionEntity.TOKEN_CREATED_AT, criteria.getTokenCreatedAtFromFilter());
        }

        if (criteria.getTokenCreatedAtToFilter() != null) {
            expression = expression.le(SessionEntity.TOKEN_CREATED_AT, criteria.getTokenCreatedAtToFilter());
        }

        if (criteria.getAccessTokenExpiredDateFromFilter() != null) {
            expression = expression.ge(SessionEntity.ACCESS_TOKEN_EXPIRED_DATE, criteria.getAccessTokenExpiredDateFromFilter());
        }

        if (criteria.getTokenCreatedAtToFilter() != null) {
            expression = expression.le(SessionEntity.ACCESS_TOKEN_EXPIRED_DATE, criteria.getAccessTokenExpiredDateToFilter());
        }

        if (criteria.getRefreshTokenExpiredDateFromFilter() != null) {
            expression = expression.ge(SessionEntity.REFRESH_TOKEN_EXPIRED_DATE, criteria.getRefreshTokenExpiredDateFromFilter());
        }

        if (criteria.getRefreshTokenExpiredDateToFilter() != null) {
            expression = expression.le(SessionEntity.REFRESH_TOKEN_EXPIRED_DATE, criteria.getRefreshTokenExpiredDateToFilter());
        }

        return expression;
    }

    public SessionModel getById(UUID id) {
        SessionEntity foundEntity = DB
                .find(SessionEntity.class)
                .where()
                .eq(SessionEntity.SESSION_ID, id)
                .findOne();

        if (foundEntity == null) {
            return new SessionModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполненными");
        }

        return getModel(foundEntity);
    }

    public GUIDResultModel save(SessionSaveModel model) {
        //var entity1 = getEntity(model);
        SessionEntity entity = new SessionEntity();
        entity.setSessionId(UUID.randomUUID());
        entity.setAccessToken(model.getAccessToken());
        entity.setRefreshToken(model.getRefreshToken());
        entity.setUserId(model.getUserId());
        entity.setTokenCreatedAt(LocalDateTime.now());
        DB.insert(entity);

        if (entity == null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        var resultModel = getModel(entity);

        return new GUIDResultModel(resultModel.getSessionId());
    }

    public SuccessResultModel delete(UUID id) {
        SessionEntity foundEntity = DB
                .find(SessionEntity.class)
                .where()
                .eq(SessionEntity.SESSION_ID, id)
                .findOne();

        if (foundEntity == null) {
            return new SuccessResultModel("CAN_NOT_DELETE",
                    "Не удалось удалить данные. Поля должны быть корректно заполненными");
        }

        DB.find(SessionEntity.class)
                .where()
                .eq(SessionEntity.SESSION_ID, id)
                .delete();

        return new SuccessResultModel(true);
    }

    private SessionModel getModel(SessionEntity entity) {
        if (entity == null) {
            return null;
        }

        var model = new SessionModel();
        model.setSessionId(entity.getSessionId());
        model.setAccessToken(entity.getAccessToken());
        model.setRefreshToken(entity.getRefreshToken());
        model.setTokenCreatedAt(entity.getTokenCreatedAt());
        model.setAccessTokenExpiredDate(entity.getAccessTokenExpiredDate());
        model.setRefreshTokenExpiredDate(entity.getRefreshTokenExpiredDate());
        model.setUserId(entity.getUserId());

        return model;
    }

    /*private SessionEntity getEntity(UserModel user) {
        if (user == null) {
            return null;
        }

        var model = new SessionModel();
        model.setSessionId(UUID.randomUUID());
        model.setAccessToken(jwtUtil.generateToken());
        model.setRefreshToken(user.getRefreshToken());
        model.setTokenCreatedAt(user.getTokenCreatedAt());
        model.setAccessTokenExpiredDate(user.getAccessTokenExpiredDate());
        model.setRefreshTokenExpiredDate(user.getRefreshTokenExpiredDate());
        model.setUserId(user.getUserId());

        return model;
    }*/
}
