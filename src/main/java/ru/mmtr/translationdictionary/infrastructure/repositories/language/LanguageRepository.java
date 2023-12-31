package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import io.ebean.DB;
import io.ebean.ExpressionList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.CollectionResultModel;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.language.LanguagePageRequestModel;
import ru.mmtr.translationdictionary.domain.language.LanguageSaveModel;
import ru.mmtr.translationdictionary.domain.language.LanguageUpdateModel;
import ru.mmtr.translationdictionary.domainservice.common.CommonUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Map<UUID, LanguageModel> getByIds(List<UUID> idList) {
        Map<UUID, LanguageModel> resultMap = new HashMap<>();

        for (LanguageEntity languageEntity : DB
                .find(LanguageEntity.class)
                .where()
                .in(LanguageEntity.LANGUAGE_ID, idList)
                .findList()) {
            LanguageModel languageModel = getModel(languageEntity);
            resultMap.put(languageModel.getLanguageId(), languageModel);
        }
        return resultMap;
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
            expression = expression.ilike(LanguageEntity.LANGUAGE_NAME, "%" + criteria.getLanguageFilter() + "%");
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
        LanguageEntity entity = new LanguageEntity();
        entity.setLanguageId(UUID.randomUUID());
        entity.setLanguageName(model.getLanguageName());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedUserId(CommonUtils.getUserId());
        entity.setModifiedAt(LocalDateTime.now());
        DB.insert(entity);

        var resultModel = getModel(entity);

        return new GUIDResultModel(resultModel.getLanguageId());
    }

    public SuccessResultModel update(LanguageUpdateModel model) {
        var updateQuery = DB.update(LanguageEntity.class);
        if (StringUtils.isNotBlank(model.getLanguageName())) {
            updateQuery = updateQuery.set(LanguageEntity.LANGUAGE_NAME, model.getLanguageName());
        }

        updateQuery
                .set(LanguageEntity.LANGUAGE_MODIFIED_AT, LocalDateTime.now())
                .set(LanguageEntity.MODIFIED_USER_ID, CommonUtils.getUserId())
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, model.getId())
                .update();

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .delete();

        return new SuccessResultModel(true);
    }

    private LanguageModel getModel(LanguageEntity entity) {
        if (entity == null) {
            return null;
        }

        var model = new LanguageModel();
        model.setLanguageId(entity.getLanguageId());
        model.setLanguageName(entity.getLanguageName());
        model.setCreatedAt(entity.getCreatedAt());
        model.setCreatedUserId(entity.getCreatedUserId());
        model.setModifiedAt(entity.getModifiedAt());
        model.setModifiedUserId(entity.getModifiedUserId());

        return model;
    }
}
