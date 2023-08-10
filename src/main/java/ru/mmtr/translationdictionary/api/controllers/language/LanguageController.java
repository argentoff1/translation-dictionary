package ru.mmtr.translationdictionary.api.controllers.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.models.language.LanguageModel;
import ru.mmtr.translationdictionary.domainservice.services.language.LanguageService;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageEntity;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    @GetMapping(value = "/languages/{id}")
    public LanguageModel showLanguage(@PathVariable UUID id) {
        LanguageModel languageModel = languageService.getLanguage(id);
        /*
        String aString="JUST_A_TEST_STRING";
        String result = UUID.nameUUIDFromBytes(aString.getBytes()).toString();*/

        return languageModel;
    }






/*
    @GetMapping(value = "/languages")
    public List<LanguageEntity> showAllLanguages() {
        List<LanguageEntity> allLanguages = languageService.getAllLanguages();

        return allLanguages;
    }

    @PostMapping(value = "/languages")
    public LanguageEntity addNewLanguage(@RequestBody LanguageEntity language) {
        languageService.saveLanguage(language);

        return language;
    }

    @PutMapping(value = "/languages")
    public LanguageEntity updateLanguage(@RequestBody LanguageEntity language) {
        languageService.saveLanguage(language);

        return language;
    }

    @DeleteMapping(value = "/languages/{id}")
    public String deleteLanguage(@PathVariable int id) {
        LanguageEntity language = languageService.getLanguage(id);

        languageService.deleteLanguage(id);

        return "Language with ID = " + id + " was deleted";
    }*/
}
