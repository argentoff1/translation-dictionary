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
        List<DictionaryEntity> allDictionaries = dictionaryService.getAllDictionaries();

        return allDictionaries;
    }

    @GetMapping("/dictionaries/{id}")
    public DictionaryModel showDictionary(@PathVariable UUID id) {

        return dictionaryService.getDictionary(id);
    }

    @PostMapping("/dictionaries")
    public DictionaryEntity addNewDictionary(@RequestBody DictionaryEntity dictionary) {
        dictionaryService.saveDictionary(dictionary);

        return dictionary;
    }

    @PutMapping("/dictionaries")
    public DictionaryEntity updateDictionary(@RequestBody DictionaryEntity dictionary) {
        dictionaryService.saveDictionary(dictionary);

        return dictionary;
    }

    @DeleteMapping("/dictionaries/{id}")
    public String deleteDictionary(@PathVariable UUID id) {


        return null;
    }
}
