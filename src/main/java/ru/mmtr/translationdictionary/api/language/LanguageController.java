package ru.mmtr.translationdictionary.api.language;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.common.PageModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
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

    @GetMapping(value = "/showAllWithPagination")
    @Operation(
            summary = "Отображение всех языков постранично",
            description = "Позволяет отобразить все языки постранично"
    )
    // Поменять на критерии после добавления фильтров
    public PageResultModel<LanguageModel> showAllWithPagination(PageModel model) {

        return languageService.showAllWithPagination(model);
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
            description = "Позволяет получить язык по его идентификатору"
    )
    public LanguageModel getById(@PathVariable @Parameter(description = "Идентификатор языка") UUID id) {

        return languageService.getById(id);
    }

    @GetMapping(value = "/getByName/{name}")
    @Operation(
            summary = "Получение языка",
            description = "Позволяет получить язык по его названию"
    )
    public LanguageModel getByName(@PathVariable @Parameter(description = "Название") String name) {

        return languageService.getByName(name);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(
            summary = "Удаление языка",
            description = "Позволяет удалить один язык"
    )
    public SuccessResultModel delete(@PathVariable @Parameter(description = "Идентификатор языка") UUID id) {

        return languageService.delete(id);
    }

    @PostMapping(value = "/save")
    @Operation(
            summary = "Сохранение языка",
            description = "Позволяет сохранить один язык"
    )
    public SuccessResultModel save(@RequestBody @Parameter(description = "Язык") String languageName) {

        return languageService.save(languageName);
    }

    @PutMapping(value = "/update/{id}")
    @Operation(
            summary = "Обновление языка",
            description = "Позволяет обновить один язык"
    )
    public SuccessResultModel update(@PathVariable @Parameter(description = "Идентификатор языка") UUID id,
                                @RequestBody @Parameter(description = "Язык") String languageName) {

        return languageService.update(id, languageName);
    }
}
