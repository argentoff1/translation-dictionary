package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import io.ebean.DB;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.user.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class UserRepository {
    public GUIDResultModel login(UserAuthorizationModel model) {
        UserEntity foundEntity = DB.find(UserEntity.class)
                .where()
                .eq(UserEntity.LOGIN, model.getLogin())
                .eq(UserEntity.PASSWORD, model.getPassword())
                .findOne();

        if (foundEntity == null) {
            return new GUIDResultModel("CAN_NOT_AUTHORIZE",
                    "Не удалось авторизоваться. " +
                            "Поля должны быть корректно заполнены");
        }

        return new GUIDResultModel(foundEntity.getUserId());
    }

    public SuccessResultModel save(UserSaveModel model) {
        UserEntity entity = new UserEntity();
        entity.setUserId(UUID.randomUUID());
        entity.setLogin(model.getLogin());
        entity.setPassword(model.getPassword());
        entity.setLastName(model.getLastName());
        entity.setFirstName(model.getFirstName());
        entity.setFatherName(model.getFatherName());
        entity.setEmail(model.getEmail());
        entity.setPhoneNumber(model.getPhoneNumber());
        entity.setCreatedAt(LocalDateTime.now());
        DB.insert(entity);

        return new SuccessResultModel(true);
    }

    public Integer updateUser(UserUpdateModel model) {
        return DB.update(UserEntity.class)
                .set(UserEntity.LOGIN, model.getLogin())
                .set(UserEntity.LAST_NAME, model.getLastName())
                .set(UserEntity.FIRST_NAME, model.getFirstName())
                .set(UserEntity.FATHER_NAME, model.getFatherName())
                .set(UserEntity.EMAIL, model.getEmail())
                .set(UserEntity.PHONE_NUMBER, model.getPhoneNumber())
                .set(UserEntity.USER_MODIFIED_AT, LocalDateTime.now())
                .where()
                .eq(UserEntity.USER_ID, model.getId())
                .update();
    }

    public Integer updatePassword(UserPasswordUpdateModel model) {
        return DB.find(UserEntity.class)
                .where()
                .eq(UserEntity.USER_ID, model.getId())
                .asUpdate()
                .set(UserEntity.PASSWORD, model.getPassword())
                .set(UserEntity.USER_MODIFIED_AT, LocalDateTime.now())
                .update();
    }

    public Integer archiveById(UUID id) {
        return DB.find(UserEntity.class)
                .where()
                .eq(UserEntity.USER_ID, id)
                .asUpdate()
                .set(UserEntity.ARCHIVE_DATE, LocalDateTime.now())
                .update();
    }

    public Integer unarchiveById(UUID id) {
        return DB.find(UserEntity.class)
                .where()
                .eq(UserEntity.USER_ID, id)
                .asUpdate()
                .set(UserEntity.ARCHIVE_DATE, null)
                .update();
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
}
