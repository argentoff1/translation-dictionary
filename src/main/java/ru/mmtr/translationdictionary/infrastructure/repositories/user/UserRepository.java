package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import io.ebean.DB;
import io.ebean.ExpressionList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.JwtConfiguration;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.session.UserSessionSaveModel;
import ru.mmtr.translationdictionary.domain.user.*;
import ru.mmtr.translationdictionary.infrastructure.repositories.session.UserSessionEntity;
import ru.mmtr.translationdictionary.infrastructure.repositories.session.UserSessionRepository;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private final JwtConfiguration jwtConfiguration;
    private final UserSessionRepository userSessionRepository;

    public UserRepository(JwtConfiguration jwtConfiguration, UserSessionRepository userSessionRepository) {
        this.jwtConfiguration = jwtConfiguration;
        this.userSessionRepository = userSessionRepository;
    }

    public JwtResponseResultModel login(JwtRequestModel model) {
        var foundUserEntity = DB.find(UserEntity.class)
                .where()
                .eq(UserEntity.LOGIN, model.getLogin())
                .findOne();

        if (BCrypt.checkpw(model.getPassword(), foundUserEntity.getPassword()) == false) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }

        if (foundUserEntity == null) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }

        /*String accessToken = jwtUtil.generateAccessToken(UserRole.USER.getRoleName(), model.getUserId(), model.getSessionId());
        String refreshToken = jwtUtil.generateRefreshToken(UserRole.REFRESH_TOKEN.getRoleName(), model.getUserId(), model.getSessionId());*/

        //userSessionRepository.save()

        return new JwtResponseResultModel("2131231", "213213123123");
    }

    public UserModel getByLogin(String login) {
        UserEntity foundEntity = DB
                .find(UserEntity.class)
                .where()
                .eq(UserEntity.LOGIN, login)
                .findOne();

        if (foundEntity == null) {
            return new UserModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполненными");
        }

        return getModel(foundEntity);
    }

    public PageResultModel<UserModel> getPage(UserPageRequestModel criteria) {
        var expression = DB
                .find(UserEntity.class)
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

    private ExpressionList<UserEntity> applyFilters(ExpressionList<UserEntity> expression,
                                                    UserPageRequestModel criteria) {
        if (criteria.getLoginFilter() != null) {
            expression = expression.ilike(UserEntity.LOGIN, "%" + criteria.getLoginFilter() + "%");
        }

        if (criteria.getPasswordFilter() != null) {
            expression = expression.ilike(UserEntity.PASSWORD, "%" + criteria.getPasswordFilter() + "%");
        }

        if (criteria.getLastNameFilter() != null) {
            expression = expression.ilike(UserEntity.LAST_NAME, "%" + criteria.getLastNameFilter() + "%");
        }

        if (criteria.getFirstNameFilter() != null) {
            expression = expression.ilike(UserEntity.FIRST_NAME, "%" + criteria.getFirstNameFilter() + "%");
        }

        if (criteria.getFatherNameFilter() != null) {
            expression = expression.ilike(UserEntity.FATHER_NAME, "%" + criteria.getFatherNameFilter() + "%");
        }

        if (criteria.getEmailFilter() != null) {
            expression = expression.ilike(UserEntity.EMAIL, "%" + criteria.getEmailFilter() + "%");
        }

        if (criteria.getPhoneNumberFilter() != null) {
            expression = expression.ilike(UserEntity.PHONE_NUMBER, "%" + criteria.getPhoneNumberFilter() + "%");
        }

        if (criteria.getCreateDateFromFilter() != null) {
            expression = expression.ge(UserEntity.CREATED_AT, criteria.getCreateDateFromFilter());
        }

        if (criteria.getCreateDateToFilter() != null) {
            expression = expression.le(UserEntity.CREATED_AT, criteria.getCreateDateToFilter());
        }

        if (criteria.getModifyDateFromFilter() != null) {
            expression = expression.ge(UserEntity.MODIFIED_AT, criteria.getModifyDateFromFilter());
        }

        if (criteria.getModifyDateToFilter() != null) {
            expression = expression.le(UserEntity.MODIFIED_AT, criteria.getModifyDateToFilter());
        }

        if (criteria.getArchiveDateFromFilter() != null) {
            expression = expression.ge(UserEntity.ARCHIVE_DATE, criteria.getArchiveDateFromFilter());
        }

        if (criteria.getArchiveDateToFilter() != null) {
            expression = expression.ge(UserEntity.ARCHIVE_DATE, criteria.getArchiveDateToFilter());
        }

        return expression;
    }

    public GUIDResultModel save(UserSaveModel model) {
        UserEntity entity = new UserEntity();
        entity.setUserId(UUID.randomUUID());
        entity.setLogin(model.getLogin());
        entity.setPassword(hashPassword(model.getPassword()));
        entity.setLastName(model.getLastName());
        entity.setFirstName(model.getFirstName());
        entity.setFatherName(model.getFatherName());
        entity.setEmail(model.getEmail());
        entity.setPhoneNumber(model.getPhoneNumber());
        entity.setCreatedAt(LocalDateTime.now());
        DB.insert(entity);

        var resultModel = getModel(entity);

        return new GUIDResultModel(resultModel.getUserId());
    }

    public Integer updateUser(UserUpdateModel model) {
        var updateQuery = DB.update(UserEntity.class);

        if (StringUtils.isNotBlank(model.getLogin())) {
            updateQuery = updateQuery.set(UserEntity.LOGIN, model.getLogin());
        }

        if (StringUtils.isNotBlank(model.getLastName())) {
            updateQuery = updateQuery.set(UserEntity.LAST_NAME, model.getLastName());
        }

        if (StringUtils.isNotBlank(model.getFirstName())) {
            updateQuery = updateQuery.set(UserEntity.FIRST_NAME, model.getFirstName());
        }

        if (StringUtils.isNotBlank(model.getFatherName())) {
            updateQuery = updateQuery.set(UserEntity.FATHER_NAME, model.getFatherName());
        }

        if (StringUtils.isNotBlank(model.getEmail())) {
            updateQuery = updateQuery.set(UserEntity.EMAIL, model.getEmail());
        }

        if (StringUtils.isNotBlank(model.getPhoneNumber())) {
            updateQuery = updateQuery.set(UserEntity.PHONE_NUMBER, model.getPhoneNumber());
        }

        return updateQuery
                .set(UserEntity.MODIFIED_AT, LocalDateTime.now())
                .where()
                .eq(UserEntity.USER_ID, model.getId())
                .update();
    }

    public Integer updateLogin(UserLoginUpdateModel model) {
        return DB.update(UserEntity.class)
                .set(UserEntity.LOGIN, model.getLogin())
                .set(UserEntity.MODIFIED_AT, LocalDateTime.now())
                .where()
                .eq(UserEntity.USER_ID, model.getId())
                .update();
    }

    public Integer updatePassword(UserPasswordUpdateModel model) {
        return DB.update(UserEntity.class)
                .set(UserEntity.PASSWORD, hashPassword(model.getPassword()))
                .set(UserEntity.MODIFIED_AT, LocalDateTime.now())
                .where()
                .eq(UserEntity.USER_ID, model.getId())
                .update();
    }

    public SuccessResultModel archiveById(UUID id) {
        UserEntity foundEntity = DB
                .find(UserEntity.class)
                .where()
                .eq(UserEntity.USER_ID, id)
                .findOne();

        if (foundEntity == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        DB.update(UserEntity.class)
                .set(UserEntity.ARCHIVE_DATE, LocalDateTime.now())
                .where()
                .eq(UserEntity.USER_ID, id)
                .update();

        return new SuccessResultModel(true);
    }

    public SuccessResultModel unarchiveById(UUID id) {
        UserEntity foundEntity = DB
                .find(UserEntity.class)
                .where()
                .eq(UserEntity.USER_ID, id)
                .findOne();

        if (foundEntity == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        DB.find(UserEntity.class)
                .where()
                .eq(UserEntity.USER_ID, id)
                .asUpdate()
                .set(UserEntity.ARCHIVE_DATE, null)
                .update();

        return new SuccessResultModel(true);
    }

    public SuccessResultModel logout() {


        return new SuccessResultModel(true);
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

    private UserSessionEntity getEntity(UserSessionSaveModel model) {
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

    public static String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);
    }
}
