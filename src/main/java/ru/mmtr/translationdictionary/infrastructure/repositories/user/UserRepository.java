package ru.mmtr.translationdictionary.infrastructure.repositories.user;

/*import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;*/
import io.ebean.DB;
//import org.springframework.security.crypto.password.PasswordEncoder;
import io.ebean.ExpressionList;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.common.TokenResultModel;
import ru.mmtr.translationdictionary.domain.user.*;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    public TokenResultModel login(UserAuthorizationModel model) {
        String userPassword = model.getPassword();

        UserEntity foundEntity = DB.find(UserEntity.class)
                .where()
                .eq(UserEntity.LOGIN, model.getLogin())
                .eq(UserEntity.PASSWORD, model.getPassword())
                .findOne();

        if (userPassword.equals(hashPassword(foundEntity.getPassword()))) {


        }

        if (foundEntity == null) {
            return new TokenResultModel("CAN_NOT_AUTHORIZE",
                    "Не удалось авторизоваться. Поля должны быть корректно заполнены");
        }

        return new TokenResultModel("222222222", "2222222222222");
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
        /*if (hashPassword(model.getPassword()) == false) {
            return new GUIDResultModel("PASSWORD_IS_NOT_IDENTICAL",
                    "Не удалось сохранить данные. Пароль введен некорректно");
        }*/

        String hashedPassword = hashPassword(model.getPassword());

        UserEntity entity = new UserEntity();
        entity.setUserId(UUID.randomUUID());
        entity.setLogin(model.getLogin());
        entity.setPassword(hashedPassword);
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
        var updateQuery = DB.update(UserEntity.class)
                .set(UserEntity.LOGIN, model.getLogin())
                .set(UserEntity.LAST_NAME, model.getLastName())
                .set(UserEntity.FIRST_NAME, model.getFirstName());
        if (StringUtils.isNotBlank(model.getFatherName()))
        {
            updateQuery = updateQuery.set(UserEntity.FATHER_NAME, model.getFatherName());
        }
        return updateQuery.set(UserEntity.EMAIL, model.getEmail())
                .set(UserEntity.PHONE_NUMBER, model.getPhoneNumber())
                .set(UserEntity.MODIFIED_AT, LocalDateTime.now())
                .where()
                .eq(UserEntity.USER_ID, model.getId())
                .update();
    }

    public Integer updatePassword(UserPasswordUpdateModel model) {
        String hashedPassword = hashPassword(model.getPassword());

        return DB.update(UserEntity.class)
                .set(UserEntity.PASSWORD, hashedPassword)
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

    private byte[] generateSalt16Byte() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return salt;
    }

    private String hashPassword(String passwordFromUser) {
        byte[] salt = generateSalt16Byte();

        int iterations = 2;
        int memLimit = 66536;
        int hashLength = 16;
        int parallelism = 1;

        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13)
                .withIterations(iterations)
                .withMemoryAsKB(memLimit)
                .withParallelism(parallelism)
                .withSalt(salt);

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(builder.build());
        byte[] result = new byte[hashLength];
        generator.generateBytes(passwordFromUser.getBytes(StandardCharsets.UTF_8), result, 0, result.length);

        Argon2BytesGenerator verifier = new Argon2BytesGenerator();
        verifier.init(builder.build());
        byte[] testHash = new byte[hashLength];
        verifier.generateBytes(passwordFromUser.getBytes(StandardCharsets.UTF_8), testHash, 0, testHash.length);

        /*if (result != testHash) {
            return null;
        }*/

        return Arrays.toString(result);


        /*Argon2 argon2 = Argon2Factory.create();

        char[] password = passwordFromUser.toCharArray();

        try {
            // Hash password
            String hash = argon2.hash(10, 65536, 1, password);

            // Verify password
            if (argon2.verify(hash, password)) {
                // Hash matches password
                return hash;
            } else {
                // Hash doesn't match password
                return null;
            }
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }*/
    }
}
