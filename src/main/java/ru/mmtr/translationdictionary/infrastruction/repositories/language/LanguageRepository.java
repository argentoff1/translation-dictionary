package ru.mmtr.translationdictionary.infrastruction.repositories.language;

import io.ebean.DB;
import io.ebean.Database;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.models.language.LanguageModel;

import java.util.List;

@Repository
public class LanguageRepository {
    /*public List<LanguageEntity> getAllLanguages() {
        return null;
    }*/

    public LanguageModel getLanguage(int id) {
        LanguageModel foundLanguage = DB.find(LanguageModel.class, id);

        return DB.find(LanguageModel.class, id);
    }

    public LanguageModel createLanguage(LanguageModel language) {
        Database database = DB.getDefault();
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
    }
}
