package ru.mmtr.translationdictionary.domain.models.language;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mmtr.translationdictionary.infrastruction.BaseModel;
import ru.mmtr.translationdictionary.infrastruction.repositories.language.LanguageRepository;

import java.util.Date;

public class LanguageModel {
    @Autowired
    LanguageRepository languageRepository;


}
