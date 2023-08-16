package ru.mmtr.translationdictionary.api.dictionary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/dictionaries/")
@Tag(name = "Словарь", description = "Позволяет взаимодействовать с словарями")
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

    @GetMapping("/getById/{id}")
    @Operation(
            summary = "Получение словаря",
            description = "Позволяет получить один словарь"
    )
    public DictionaryModel getById(@PathVariable @Parameter(description = "Идентификатор словаря", required = true) UUID id) {
        return dictionaryService.getById(id);
    }

    @PostMapping("/save")
    @Operation(
            summary = "Сохранение словаря",
            description = "Позволяет сохранить один словарь"
    )
    public DictionaryModel save(@RequestParam @Parameter(description = "Слово для перевода", required = true) String word,
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
    public DictionaryModel update(@PathVariable @Parameter(description = "Идентификатор словаря", required = true) UUID id,
                                  @RequestParam @Parameter(description = "Слово для перевода", required = true) String word,
                                  @RequestParam @Parameter(description = "Перевод слова", required = true) String translation) {
        return dictionaryService.update(id, word, translation);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Удаление словаря",
            description = "Позволяет удалить один словарь"
    )
    public boolean delete(@PathVariable @Parameter(description = "Идентификатор словаря", required = true) UUID id) {
        return dictionaryService.delete(id);
    }
}
