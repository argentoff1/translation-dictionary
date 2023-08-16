package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import io.ebean.DB;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LanguageRepository {
    public List<LanguageModel> showAll() {
        List<LanguageEntity> languageEntities = DB
                .find(LanguageEntity.class)
                .findList();

        return languageEntities.stream().map(this::getModel).collect(Collectors.toList());
    }

    public LanguageModel getById(UUID id) {
        LanguageEntity foundLanguageEntity = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .findOne();

        return getModel(foundLanguageEntity);
    }

    public LanguageModel save(String languageName) {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageId(UUID.randomUUID());
        languageEntity.setLanguageName(languageName);
        DB.insert(languageEntity);

        return getModel(languageEntity);
    }

    public LanguageModel update(UUID id, String languageName) {
        DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .asUpdate()
                .set(LanguageEntity.LANGUAGE_NAME, languageName)
                .update();

        LanguageEntity entity = new LanguageEntity();
        entity.setLanguageId(id);
        entity.setLanguageName(languageName);
        entity = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .findOne();

        return getModel(entity);
    }

    public boolean delete(UUID id) {
        DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .delete();

        return true;
    }

    private LanguageModel getModel(LanguageEntity entity) {
        var model = new LanguageModel();
        model.setLanguageId(entity.getLanguageId());
        model.setLanguageName(entity.getLanguageName());
        return model;
    }
}
