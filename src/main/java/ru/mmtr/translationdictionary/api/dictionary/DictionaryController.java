package ru.mmtr.translationdictionary.api.dictionary;

import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/dictionaries/")
public class DictionaryController {
    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping(value = "/showAll")
    public List<DictionaryModel> showAll() {
        return dictionaryService.showAll();
    }

    @GetMapping("/getById/{id}")
    public DictionaryModel getById(@PathVariable UUID id) {
        return dictionaryService.getById(id);
    }

    @PostMapping("/save")
    public DictionaryModel save(@RequestParam String word, @RequestParam String translation,
                                            @RequestParam UUID fromLanguage, @RequestParam UUID toLanguage) {
        return dictionaryService.save(word, translation, fromLanguage, toLanguage);
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable UUID id, @RequestParam String word, @RequestParam String translation) {
        return dictionaryService.update(id, word, translation);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        return dictionaryService.delete(id);
    }
}
