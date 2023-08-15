package ru.mmtr.translationdictionary.api.controllers.language;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.mmtr.translationdictionary.domain.models.language.LanguageModel;
import ru.mmtr.translationdictionary.domainservice.services.language.LanguageService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RestControllerAdvice
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    @GetMapping(value = "/languages/{id}")
    public LanguageModel showLanguage(@PathVariable UUID id) {


        return languageService.getLanguage(id);
    }

    @DeleteMapping(value = "/languages/{id}")
    public String deleteLanguage(@PathVariable UUID id) {


        return languageService.deleteLanguage(id);
    }

    @PostMapping(value = "/languages")
    public LanguageModel addNewLanguage(@RequestParam String languageName) {


        return languageService.createLanguage(languageName);
    }

    @PutMapping(value = "/languages")
    public String updateLanguage(@RequestParam UUID id, @RequestParam String languageName) {


        return languageService.saveLanguage(id, languageName);
    }

    @GetMapping(value = "/languages")
    public List<LanguageModel> showAllLanguages() {


        return languageService.getAllLanguages();
    }
}
