package ru.mmtr.translationdictionary.api.controllers.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.models.language.LanguageModel;
import ru.mmtr.translationdictionary.domainservice.services.language.LanguageService;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageEntity;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    // в сервисе null
    @GetMapping(value = "/languages/{id}")
    public LanguageModel showLanguage(@PathVariable int id) {
        LanguageModel languageModel = languageService.getLanguage(id);

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
