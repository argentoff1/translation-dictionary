package ru.mmtr.translationdictionary.api.language;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api/languages")
@RestControllerAdvice
public class LanguageController {
    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping(value = "/showAll")
    public List<LanguageModel> showAll() {
        return languageService.showAll();
    }

    @GetMapping(value = "/getById/{id}")
    public LanguageModel getById(@PathVariable UUID id) {
        return languageService.getById(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable UUID id) {
        return languageService.delete(id);
    }

    @PostMapping(value = "/save")
    public LanguageModel save(@RequestParam String languageName) {
        return languageService.save(languageName);
    }

    @PutMapping(value = "/update")
    public String update(@RequestParam UUID id, @RequestParam String languageName) {
        return languageService.update(id, languageName);
    }
}
