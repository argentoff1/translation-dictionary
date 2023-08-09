package ru.mmtr.translationdictionary.api.controllers.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domainservice.services.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.infrastruction.repositories.dictionary.DictionaryEntity;

import java.util.List;

/*@RestController
@RequestMapping(value = "/api")*/
public class DictionaryController {
    /*@Autowired
    private DictionaryService dictionaryService;

    // не выводит
    @GetMapping(value = "/dictionaries")
    public List<DictionaryEntity> showAllDictionaries() {
        List<DictionaryEntity> allDictionaries = dictionaryService.getAllDictionaries();

        return allDictionaries;
    }

    // не выводит
    @GetMapping("/dictionaries/{id}")
    public DictionaryEntity showDictionary(@PathVariable int id) {
        DictionaryEntity dictionary = dictionaryService.getDictionary(id);

        return dictionary;
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
    public String deleteDictionary(@PathVariable int id) {
        DictionaryEntity dictionary = dictionaryService.getDictionary(id);

        dictionaryService.deleteDictionary(id);

        return "Dictionary with ID = " + id + " was deleted";
    }*/
}
