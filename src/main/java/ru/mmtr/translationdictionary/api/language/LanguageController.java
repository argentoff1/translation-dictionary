package ru.mmtr.translationdictionary.api.language;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/languages")
@Tag(name = "Язык", description = "Позволяет взаимодействовать с языками")
public class LanguageController {
    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping(value = "/showAll")
    @Operation(
            summary = "Отображение всех языков",
            description = "Позволяет отобразить все языки"
    )
    public List<LanguageModel> showAll() {
        return languageService.showAll();
    }

    @GetMapping(value = "/getById/{id}")
    @Operation(
            summary = "Получение языка",
            description = "Позволяет получить один язык"
    )
    public LanguageModel getById(@PathVariable @Parameter(description = "Идентификатор языка", required = true) UUID id) {
        return languageService.getById(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(
            summary = "Удаление языка",
            description = "Позволяет удалить один язык"
    )
    public boolean delete(@PathVariable @Parameter(description = "Идентификатор языка", required = true) UUID id) {
        return languageService.delete(id);
    }

    @PostMapping(value = "/save")
    @Operation(
            summary = "Сохранение языка",
            description = "Позволяет сохранить один язык"
    )
    public LanguageModel save(@RequestParam @Parameter(description = "Язык", required = true) String languageName) {
        return languageService.save(languageName);
    }

    @PutMapping(value = "/update/{id}")
    @Operation(
            summary = "Обновление языка",
            description = "Позволяет обновить один язык"
    )
    public LanguageModel update(@PathVariable @Parameter(description = "Идентификатор языка", required = true) UUID id,
                                @RequestParam @Parameter(description = "Язык", required = true) String languageName) {
        return languageService.update(id, languageName);
    }
}
