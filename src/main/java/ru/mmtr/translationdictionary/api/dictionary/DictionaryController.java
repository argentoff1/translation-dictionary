package ru.mmtr.translationdictionary.api.dictionary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.common.PageModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/dictionaries/")
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
    public List<DictionaryModel> showAll() {
        return dictionaryService.showAll();
    }

    @GetMapping(value = "showAllWithPagination")
    @Operation(
            summary = "Отображение всех словарей постранично",
            description = "Позволяет отобразить все словари постранично"
    )
    public PageResultModel<DictionaryModel> showAllWithPagination(PageModel model) {

        return dictionaryService.showAllWithPagination(model);
    }

    @GetMapping("/getById/{id}")
    @Operation(
            summary = "Получение словаря",
            description = "Позволяет получить один словарь"
    )
    public DictionaryModel getById(@PathVariable @Parameter(description = "Идентификатор словаря", required = true) UUID id) {
        return dictionaryService.getById(id);
    }

    @GetMapping("/getTranslatedWord")
    @Operation(
            summary = "Получение переведенного слова",
            description = "Позволяет получить переведенное слово с помощью исходного слова, языка искомого слова, и языка переводимого слова"
    )
    public DictionaryModel getTranslatedWord(@RequestParam @Parameter(description = "Слово для перевода", required = true) String word,
                                                @RequestParam @Parameter(description = "Идентификатор языка исходного слова", required = true) UUID fromLanguage,
                                                @RequestParam @Parameter(description = "Идентификатор языка переведенного слова", required = true) UUID toLanguage) {

        return dictionaryService.getTranslatedWord(word, fromLanguage, toLanguage);
    }

    @PostMapping("/save")
    @Operation(
            summary = "Сохранение словаря",
            description = "Позволяет сохранить один словарь"
    )
    public SuccessResultModel save(@RequestParam @Parameter(description = "Слово для перевода", required = true) String word,
                                @RequestParam @Parameter(description = "Перевод слова", required = true) String translation,
                                @RequestParam @Parameter(description = "Идентификатор языка исходного слова", required = true) UUID fromLanguage,
                                @RequestParam @Parameter(description = "Идентификатор языка переведенного слова", required = true) UUID toLanguage) {
        return dictionaryService.save(word, translation, fromLanguage, toLanguage);
    }

    @PutMapping("/update/{id}")
    @Operation(
            summary = "Обновление словаря",
            description = "Позволяет обновить один словарь"
    )
    public SuccessResultModel update(@PathVariable @Parameter(description = "Идентификатор словаря", required = true) UUID id,
                                  @RequestParam @Parameter(description = "Слово для перевода", required = true) String word,
                                  @RequestParam @Parameter(description = "Перевод слова", required = true) String translation) {
        return dictionaryService.update(id, word, translation);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Удаление словаря",
            description = "Позволяет удалить один словарь"
    )
    public SuccessResultModel delete(@PathVariable @Parameter(description = "Идентификатор словаря", required = true) UUID id) {
        return dictionaryService.delete(id);
    }
}
