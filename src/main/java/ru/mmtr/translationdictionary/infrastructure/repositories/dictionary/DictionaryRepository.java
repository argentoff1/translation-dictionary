package ru.mmtr.translationdictionary.infrastructure.repositories.dictionary;

import io.ebean.DB;
import io.ebean.ExpressionList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.dictionary.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DictionaryRepository {
    public CollectionResultModel<DictionaryModel> showAll() {
        List<DictionaryEntity> entities = DB
                .find(DictionaryEntity.class)
                .findList();

        return new CollectionResultModel<>(
                entities.stream().map(this::getModel).collect(Collectors.toList())
        );
    }

    public CollectionResultModel<DictionaryWordAndTranslationModel> getAllByIds(DictionaryIdsCollectionModel<UUID> model) {
        List<DictionaryEntity> wordAndTranslation = DB
                .find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, model.getCollectionIds())
                .findList();

        return new CollectionResultModel<>(
                wordAndTranslation.stream().map(this::getWordAndTranslationModel).collect(Collectors.toList())
        );
    }

    public PageResultModel<DictionaryModel> getPage (DictionaryPageRequestModel criteria) {
        var expression = DB
                .find(DictionaryEntity.class)
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

    private ExpressionList<DictionaryEntity> applyFilters(ExpressionList<DictionaryEntity> expression,
                                                          DictionaryPageRequestModel criteria) {
        if (criteria.getWordFilter() != null) {
            expression = expression.ilike(DictionaryEntity.WORD, "%" + criteria.getWordFilter() + "%");
        }

        if (criteria.getTranslationFilter() != null) {
            expression = expression.ilike(DictionaryEntity.TRANSLATION, "%" + criteria.getTranslationFilter() + "%");
        }

        if (criteria.getCreateDateFromFilter() != null) {
            expression = expression.ge(DictionaryEntity.DICTIONARY_CREATED_AT, criteria.getCreateDateFromFilter());
        }

        if (criteria.getCreateDateToFilter() != null) {
            expression = expression.le(DictionaryEntity.DICTIONARY_CREATED_AT, criteria.getCreateDateToFilter());
        }

        if (criteria.getModifyDateFromFilter() != null) {
            expression = expression.ge(DictionaryEntity.DICTIONARY_CREATED_AT, criteria.getModifyDateFromFilter());
        }

        if (criteria.getModifyDateToFilter() != null) {
            expression = expression.le(DictionaryEntity.DICTIONARY_CREATED_AT, criteria.getModifyDateToFilter());
        }

        return expression;
    }

    public DictionaryModel getById(UUID id) {
        DictionaryEntity foundEntity = DB
                .find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .findOne();

        return getModel(foundEntity);
    }

    public StringResultModel getTranslatedWord(DictionaryTranslateModel model) {
        DictionaryEntity foundTranslation = DB.
                find(DictionaryEntity.class).
                where().
                ilike(DictionaryEntity.WORD, "%" + model.getWord() + "%").
                eq(DictionaryEntity.FROM_LANGUAGE, model.getFromLanguage()).
                eq(DictionaryEntity.TO_LANGUAGE, model.getToLanguage()).
                findOne();

        return new StringResultModel(foundTranslation.getTranslation());
    }

    public GUIDResultModel save(DictionarySaveModel model) {
        DictionaryEntity entity = new DictionaryEntity();
        entity.setDictionaryId(UUID.randomUUID());
        entity.setWord(model.getWord());
        entity.setTranslation(model.getTranslation());
        entity.setFromLanguage(model.getFromLanguage());
        entity.setToLanguage(model.getToLanguage());
        entity.setCreatedAt(LocalDateTime.now());
        DB.insert(entity);

        var resultModel = getModel(entity);

        return new GUIDResultModel(resultModel.getDictionaryId());
    }

    public Integer update(DictionaryUpdateModel model) {
        var updateQuery = DB.update(DictionaryEntity.class);

        if (StringUtils.isNotBlank(model.getWord())) {
            updateQuery = updateQuery.set(DictionaryEntity.WORD, model.getWord());
        }

        if (StringUtils.isNotBlank(model.getTranslation())) {
            updateQuery = updateQuery.set(DictionaryEntity.TRANSLATION, model.getTranslation());
        }

        return updateQuery
                .set(DictionaryEntity.DICTIONARY_MODIFIED_AT, LocalDateTime.now())
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, model.getId())
                .update();
    }

    public Integer delete(UUID id) {
        return DB.find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .delete();
    }

    private DictionaryModel getModel(DictionaryEntity entity) {
        if (entity == null) {
            return null;
        }

        var model = new DictionaryModel();
        model.setDictionaryId(entity.getDictionaryId());
        model.setWord(entity.getWord());
        model.setTranslation(entity.getTranslation());
        model.setFromLanguage(entity.getFromLanguage());
        model.setToLanguage(entity.getToLanguage());

        return model;
    }

    private DictionaryWordAndTranslationModel getWordAndTranslationModel(DictionaryEntity entity) {
        if (entity == null) {
            return null;
        }

        var model = new DictionaryWordAndTranslationModel();
        model.setWord(entity.getWord());
        model.setTranslation(entity.getTranslation());

        return model;
    }
}
