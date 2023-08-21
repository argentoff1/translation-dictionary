package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import io.ebean.DB;
//import org.springframework.security.crypto.password.PasswordEncoder;
import io.ebean.ExpressionList;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.common.TokenResultModel;
import ru.mmtr.translationdictionary.domain.user.*;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryEntity;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    public TokenResultModel login(UserAuthorizationModel model) {
        UserEntity foundEntity = DB.find(UserEntity.class)
                .where()
                .eq(UserEntity.LOGIN, model.getLogin())
                .eq(UserEntity.PASSWORD, model.getPassword())
                .findOne();

        if (foundEntity == null) {
            return new TokenResultModel("CAN_NOT_AUTHORIZE",
                    "Не удалось авторизоваться. " +
                            "Поля должны быть корректно заполнены");
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

        var resultModel = getModel(entity);

        return new GUIDResultModel(resultModel.getUserId());
    }

    public Integer updateUser(UserUpdateModel model) {
        return DB.update(UserEntity.class)
                .set(UserEntity.LOGIN, model.getLogin())
                .set(UserEntity.LAST_NAME, model.getLastName())
                .set(UserEntity.FIRST_NAME, model.getFirstName())
                .set(UserEntity.FATHER_NAME, model.getFatherName())
                .set(UserEntity.EMAIL, model.getEmail())
                .set(UserEntity.PHONE_NUMBER, model.getPhoneNumber())
                .set(UserEntity.MODIFIED_AT, LocalDateTime.now())
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
                .set(UserEntity.MODIFIED_AT, LocalDateTime.now())
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
