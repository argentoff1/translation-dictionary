package ru.mmtr.translationdictionary.api.dictionary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.dictionary.*;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/dictionaries")
@Tag(name = "Словарь", description = "Позволяет взаимодействовать со словарями")
public class DictionaryController {
    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping(value = "/showAll")
    @Operation(
            summary = "Отображение словарей",
            description = "Позволяет отобразить все словари"
    )
    public CollectionResultModel<DictionaryModel> showAll() {

        return dictionaryService.showAll();
    }

    @PostMapping(value = "/getAllByIds")
    @Operation(
            summary = "Отображение слов и переводов",
            description = "Позволяет отобразить слова и переводы"
    )
    public CollectionResultModel<DictionaryWordAndTranslationModel> getAllByIds(DictionaryIdsCollectionModel<UUID> model) {

        return dictionaryService.getAllByIds(model);
    }

    @PostMapping(value = "/getPage")
    @Operation(
            summary = "Отображение всех словарей постранично",
            description = "Позволяет отобразить все словари постранично"
    )
    public PageResultModel<DictionaryModel> getPage(@RequestBody DictionaryPageRequestModel criteria) {

        return dictionaryService.getPage(criteria);
    }

    @GetMapping(value = "/getById/{id}")
    @Operation(
            summary = "Получение словаря",
            description = "Позволяет получить один словарь"
    )
    public DictionaryModel getById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {

        return dictionaryService.getById(id);
    }

    @PostMapping(value = "/getTranslatedWord")
    @Operation(
            summary = "Получение переведенного слова",
            description = "Позволяет получить переведенное слово с помощью исходного слова, языка искомого слова, и языка переводимого слова"
    )
    public StringResultModel getTranslatedWord(@RequestBody DictionaryTranslateModel model) {

        return dictionaryService.getTranslatedWord(model);
    }

    @PostMapping(value = "/save")
    @Operation(
            summary = "Сохранение",
            description = "Позволяет сохранить одну запись в словаре"
    )
    public SuccessResultModel save(@RequestBody DictionarySaveModel model) {

        return dictionaryService.save(model);
    }

    @PutMapping(value = "/update")
    @Operation(
            summary = "Обновление",
            description = "Позволяет обновить одну запись в словаре"
    )
    public SuccessResultModel update(@RequestBody DictionaryUpdateModel model) {
        return dictionaryService.update(model);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(
            summary = "Удаление",
            description = "Позволяет удалить одну запись в словаре"
    )
    public SuccessResultModel delete(@PathVariable @Parameter(description = "Идентификатор словаря") UUID id) {
        return dictionaryService.delete(id);
    }
}
