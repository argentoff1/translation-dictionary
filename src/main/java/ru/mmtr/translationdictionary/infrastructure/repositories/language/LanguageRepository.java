package ru.mmtr.translationdictionary.infrastructure.repositories.language;

import io.ebean.DB;
import io.ebean.Database;
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

    public List<LanguageModel> getAllLanguages() {
        //List<LanguageEntity> languageEntities = new LanguageEntity().getLanguageId().eq().

        return null;
    }

    public LanguageModel createLanguage(String languageName) {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageName(languageName);
        languageEntity.setLanguageId(UUID.randomUUID());
        DB.insert(languageEntity);

        var languageModel = new LanguageModel();
        languageModel.setLanguageId(languageEntity.getLanguageId());
        languageModel.setLanguageName(languageEntity.getLanguageName());

        return languageModel;
    }

    // МБ надо возвращать int, а в сервисе уже строку с сообщением
    public String saveLanguage(UUID id) {
        int savedRows = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .asUpdate()
                // Заглушка
                .set(LanguageEntity.LANGUAGE_NAME, "")
                .update();

        return "Было обновлено " + savedRows + " строк";
        /*LanguageEntity foundLanguageEntity = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .findOne();*/

        /*var languageModel = new LanguageModel();
        languageModel.setLanguageId(foundLanguageEntity.getLanguageId());
        languageModel.setLanguageName(foundLanguageEntity.getLanguageName());*/
    }

    // МБ надо возвращать int, а в сервисе уже строку с сообщением
    public String deleteLanguage(int id) {
        int deletedRows = DB.find(LanguageEntity.class)
                .where()
                .eq(LanguageEntity.LANGUAGE_ID, id)
                .delete();

        return "Было удалено " + deletedRows + " строк";
    }
}
