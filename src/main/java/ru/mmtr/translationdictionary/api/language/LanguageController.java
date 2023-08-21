package ru.mmtr.translationdictionary.api.language;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.common.CollectionResultModel;
import ru.mmtr.translationdictionary.domain.common.DateTimeResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.language.LanguagePageRequestModel;
import ru.mmtr.translationdictionary.domain.language.LanguageSaveModel;
import ru.mmtr.translationdictionary.domain.language.LanguageUpdateModel;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;

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
    public CollectionResultModel<LanguageModel> showAll() {

        return languageService.showAll();
    }

    @PostMapping(value = "/getPage")
    @Operation(
            summary = "Отображение всех языков постранично",
            description = "Позволяет отобразить все языки постранично"
    )
    public PageResultModel<LanguageModel> getPage(LanguagePageRequestModel criteria) {

        return languageService.getPage(criteria);
    }

    @GetMapping(value = "/getById/{id}")
    @Operation(
            summary = "Получение языка",
            description = "Позволяет получить язык по его идентификатору"
    )
    public LanguageModel getById(@PathVariable @Parameter(description = "Идентификатор языка") UUID id) {

        return languageService.getById(id);
    }

    @PostMapping(value = "/save")
    @Operation(
            summary = "Сохранение языка",
            description = "Позволяет сохранить один язык"
    )
    public SuccessResultModel save(@RequestBody LanguageSaveModel model) {

        return languageService.save(model);
    }

    @PutMapping(value = "/update/{id}")
    @Operation(
            summary = "Обновление языка",
            description = "Позволяет обновить один язык"
    )
    public SuccessResultModel update(@RequestBody LanguageUpdateModel model) {

        return languageService.update(model);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(
            summary = "Удаление языка",
            description = "Позволяет удалить один язык"
    )
    public SuccessResultModel delete(@PathVariable @Parameter(description = "Идентификатор языка") UUID id) {

        return languageService.delete(id);
    }
}
