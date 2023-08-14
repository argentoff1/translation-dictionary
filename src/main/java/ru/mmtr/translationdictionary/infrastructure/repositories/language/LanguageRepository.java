package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import io.ebean.DB;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.models.language.LanguageModel;

import java.util.List;
import java.util.UUID;

@Repository
public class LanguageRepository {
    public LanguageModel getLanguage(UUID id) {
        LanguageEntity foundLanguageEntity = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .findOne();

        var languageModel = new LanguageModel();
        languageModel.setLanguageId(foundLanguageEntity.getLanguageId());
        languageModel.setLanguageName(foundLanguageEntity.getLanguageName());

        return languageModel;
    }

    public List<LanguageEntity> getAllLanguages() {
        List<LanguageEntity> languageEntities = DB.find(LanguageEntity.class)
                .findList();



        return languageEntities;
    }

    public LanguageModel createLanguage(String languageName) {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageId(UUID.randomUUID());
        languageEntity.setLanguageName(languageName);
        DB.insert(languageEntity);

        var languageModel = new LanguageModel();
        languageModel.setLanguageId(languageEntity.getLanguageId());
        languageModel.setLanguageName(languageEntity.getLanguageName());

        return languageModel;
    }

    public int saveLanguage(UUID id, String languageName) {
        int savedRows = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .asUpdate()
                .set(LanguageEntity.LANGUAGE_NAME, languageName)
                .update();

        return savedRows;
    }

    public int deleteLanguage(UUID id) {
        int deletedRows = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .delete();

        return deletedRows;
    }
}
