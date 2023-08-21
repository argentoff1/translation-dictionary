package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import io.ebean.DB;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domain.user.UserPasswordUpdateModel;
import ru.mmtr.translationdictionary.domain.user.UserSaveModel;
import ru.mmtr.translationdictionary.domain.user.UserUpdateModel;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class UserRepository {
    public SuccessResultModel login() {
        return null;
    }

    public SuccessResultModel logout() {
        return null;
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
        return DB.find(UserEntity.class)
                .where()
                .eq(UserEntity.USER_ID, model.getId())
                .asUpdate()
                .set(UserEntity.LOGIN, model.getLogin())
                .set(UserEntity.LAST_NAME, model.getLastName())
                .set(UserEntity.FIRST_NAME, model.getFirstName())
                .set(UserEntity.FATHER_NAME, model.getFatherName())
                .set(UserEntity.EMAIL, model.getEmail())
                .set(UserEntity.PHONE_NUMBER, model.getPhoneNumber())
                .set(UserEntity.USER_MODIFIED_AT, LocalDateTime.now())
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

    public SuccessResultModel exit(UserModel model) {
        return null;
    }
}
