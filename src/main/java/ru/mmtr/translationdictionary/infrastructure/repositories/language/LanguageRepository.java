package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import io.ebean.DB;
import io.ebean.Database;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.models.language.LanguageModel;

@Repository
public class LanguageRepository {
    // Объявление БД. Т.к. с репо взаимодействует Entity & Model (?)


    public LanguageModel getLanguage(int id) {
        LanguageEntity foundLanguageEntity = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .findOne();

        var languageModel = new LanguageModel();
        languageModel.setLanguageId(foundLanguageEntity.getLanguageId());
        languageModel.setLanguageName(foundLanguageEntity.getLanguage_name());

        return languageModel;
    }
















    /*
    public List<LanguageEntity> getAllLanguages() {
        return null;
    }

    public LanguageModel createLanguage(LanguageModel language) {
        Database database = DB.getDefault();
        // Заполнение полей сущности


        database.insert(language);

        return language;
    }

    public LanguageModel saveLanguage(int id) {
        LanguageModel foundLanguage = DB.find(LanguageModel.class, id);
        DB.update(foundLanguage);

        return foundLanguage;
    }

    public LanguageModel deleteLanguage(int id) {
        LanguageModel foundLanguage = DB.find(LanguageModel.class, id);
        DB.delete(foundLanguage);

        return foundLanguage;
    }*/
}
