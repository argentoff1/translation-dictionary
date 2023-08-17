package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import io.ebean.DB;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.PageModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LanguageRepository {
    public List<LanguageModel> showAll() {
        List<LanguageEntity> entities = DB
                .find(LanguageEntity.class)
                .findList();

        return entities.stream().map(this::getModel).collect(Collectors.toList());
    }

    public PageResultModel<LanguageModel> showAllWithPagination(PageModel model) {
        var page = DB
                .find(LanguageEntity.class)
                .setMaxRows(model.getPageSize())
                .setFirstRow((model.getPageNum() - 1) * model.getPageSize())
                .findPagedList();

        return new PageResultModel<>(
                page.getTotalCount(),
                page.getList().stream().map(this::getModel).collect(Collectors.toList())
        );
    }

    public LanguageModel getById(UUID id) {
        LanguageEntity foundEntity = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .findOne();

        return getModel(foundEntity);
    }

    public LanguageModel getByName(String languageName) {
        LanguageEntity foundEntity = DB.find(LanguageEntity.class)
                .where()
                .ilike(LanguageEntity.LANGUAGE_NAME, "%" + languageName + "%")
                .findOne();

        return getModel(foundEntity);
    }

    public SuccessResultModel save(String languageName) {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageId(UUID.randomUUID());
        languageEntity.setLanguageName(languageName);
        DB.insert(languageEntity);

        return new SuccessResultModel(true);
    }

    public SuccessResultModel update(UUID id, String languageName) {
        DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .asUpdate()
                .set(LanguageEntity.LANGUAGE_NAME, languageName)
                .update();

        /*LanguageEntity entity = new LanguageEntity();
        entity.setLanguageId(id);
        entity.setLanguageName(languageName);
        entity = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .findOne();*/

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

        return model;
    }
}
