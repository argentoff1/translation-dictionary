package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import io.ebean.DB;
import io.ebean.ExpressionList;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.CollectionResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.language.LanguagePageRequestModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryEntity;

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
        if (criteria.getCreateDateFromFilter() != null) {
            expression = expression.ge(DictionaryEntity.DICTIONARY_CREATED_AT, criteria.getCreateDateFromFilter());
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

    public LanguageModel getByName(String languageName) {
        LanguageEntity foundEntity = DB
                .find(LanguageEntity.class)
                .where()
                .ilike(LanguageEntity.LANGUAGE_NAME, "%" + languageName + "%")
                .findOne();

        return getModel(foundEntity);
    }

    public SuccessResultModel save(String languageName) {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageId(UUID.randomUUID());
        languageEntity.setLanguageName(languageName);
        languageEntity.setCreatedAt(LocalDateTime.now());
        DB.insert(languageEntity);

        return new SuccessResultModel(true);
    }

    public Integer update(UUID id, String languageName) {
        /*LanguageEntity entity = new LanguageEntity();
        entity.setLanguageId(id);
        entity.setLanguageName(languageName);
        entity = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .findOne();*/

        return DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .asUpdate()
                .set(LanguageEntity.LANGUAGE_NAME, languageName)
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
