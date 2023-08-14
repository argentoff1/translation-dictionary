package ru.mmtr.translationdictionary.api.controllers.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.models.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domainservice.services.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryEntity;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping(value = "/dictionaries")
    public List<DictionaryEntity> showAllDictionaries() {
        //List<DictionaryEntity> allDictionaries = dictionaryService.getAllDictionaries();

        return dictionaryService.getAllDictionaries();
    }

    @GetMapping("/dictionaries/{id}")
    public DictionaryModel showDictionary(@PathVariable UUID id) {

        return dictionaryService.getDictionary(id);
    }

    @PostMapping("/dictionaries")
    public DictionaryModel addNewDictionary(@RequestParam String word, @RequestParam String translation,
                                            @RequestParam UUID fromLanguage, @RequestParam UUID toLanguage) {


        return dictionaryService.createDictionary(word, translation, fromLanguage, toLanguage);
    }

    @PutMapping("/dictionaries/{id}")
    public String updateDictionary(@PathVariable UUID id, @RequestParam String word, @RequestParam String translation) {


        return dictionaryService.saveDictionary(id, word, translation);
    }

    @DeleteMapping("/dictionaries/{id}")
    public String deleteDictionary(@PathVariable UUID id) {


        return dictionaryService.deleteDictionary(id);
    }
}
