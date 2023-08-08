package ru.mmtr.translationdictionary.api.controllers.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mmtr.translationdictionary.domainservice.services.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.infrastruction.entities.dictionary.DictionaryEntity;

@RestController
@RequestMapping(value = "/api")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping(value = "/dictionaries/{id}")
    public DictionaryEntity getDictionary(@PathVariable int id) {
        DictionaryEntity dictionary = dictionaryService.getDictionary(id);

        return dictionary;
    }
}
