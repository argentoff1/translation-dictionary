package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import io.ebean.DB;
import io.ebean.ExpressionList;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.language.LanguagePageRequestModel;
import ru.mmtr.translationdictionary.domain.language.LanguageSaveModel;
import ru.mmtr.translationdictionary.domain.language.LanguageUpdateModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LanguageRepository {
    public CollectionResultModel<LanguageModel> showAll() {
        List<LanguageEntity> entities = DB
                .find(LanguageEntity.class)
                .findList();

        return new CollectionResultModel<>(
                entities.stream().map(this::getModel).collect(Collectors.toList())
        );
    }

    public PageResultModel<LanguageModel> getPage(LanguagePageRequestModel criteria) {
        var expression = DB
                .find(LanguageEntity.class)
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

    private ExpressionList<LanguageEntity> applyFilters(ExpressionList<LanguageEntity> expression,
                                                          LanguagePageRequestModel criteria) {
        if (criteria.getLanguageFilter() != null) {
            expression = expression.ilike(LanguageEntity.LANGUAGE_NAME, "%" + criteria + "%");
        }

        if (criteria.getCreateDateFromFilter() != null) {
            expression = expression.ge(LanguageEntity.LANGUAGE_CREATED_AT, criteria.getCreateDateFromFilter());
        }

        if (criteria.getCreateDateToFilter() != null) {
            expression = expression.le(LanguageEntity.LANGUAGE_CREATED_AT, criteria.getCreateDateToFilter());
        }

        if (criteria.getModifyDateFromFilter() != null) {
            expression = expression.ge(LanguageEntity.LANGUAGE_MODIFIED_AT, criteria.getModifyDateFromFilter());
        }

        if (criteria.getModifyDateToFilter() != null) {
            expression = expression.le(LanguageEntity.LANGUAGE_MODIFIED_AT, criteria.getModifyDateToFilter());
        }

        return expression;
    }

    public LanguageModel getById(UUID id) {
        LanguageEntity foundEntity = DB
                .find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .findOne();

        return getModel(foundEntity);
    }

    public GUIDResultModel save(LanguageSaveModel model) {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageId(UUID.randomUUID());
        languageEntity.setLanguageName(model.getLanguageName());
        languageEntity.setCreatedAt(LocalDateTime.now());
        DB.insert(languageEntity);

        var resultModel = getModel(languageEntity);

        return new GUIDResultModel(resultModel.getLanguageId());
    }

    public Integer update(LanguageUpdateModel model) {
        return DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, model.getId())
                .asUpdate()
                .set(LanguageEntity.LANGUAGE_NAME, model.getLanguageName())
                .set(LanguageEntity.LANGUAGE_MODIFIED_AT, LocalDateTime.now())
                .update();
    }

    public Integer delete(UUID id) {
        return DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .delete();
    }

    private LanguageModel getModel(LanguageEntity entity) {
        if (entity == null) {
            return null;
        }

        var model = new LanguageModel();
        model.setLanguageId(entity.getLanguageId());
        model.setLanguageName(entity.getLanguageName());
        model.setCreatedAt(entity.getCreatedAt());

        return model;
    }
}
