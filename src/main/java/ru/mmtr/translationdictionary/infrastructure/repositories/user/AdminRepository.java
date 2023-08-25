package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import io.ebean.DB;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.JwtProvider;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.session.UserSessionSaveModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domain.user.UserSaveModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.session.UserSessionEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class AdminRepository {
    private final JwtProvider jwtProvider;

    public AdminRepository(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
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

    public GUIDResultModel saveAdmin(UserSaveModel model) {
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
        entity.setRoleName(UserRole.ADMIN.getRoleName());
        DB.insert(entity);

        var resultModel = getModel(entity);

        return new GUIDResultModel(resultModel.getUserId());
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
        entity.setAccessToken(jwtProvider.generateAccessToken(UserRole.ADMIN.getRoleName(), entity.getUserId(), entity.getSessionId()));
        entity.setAccessTokenExpiredDate(LocalDateTime.now().plusMinutes(5));
        entity.setRefreshToken(jwtProvider.generateRefreshToken(UserRole.ADMIN.getRoleName(), entity.getUserId(), entity.getSessionId()));
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
