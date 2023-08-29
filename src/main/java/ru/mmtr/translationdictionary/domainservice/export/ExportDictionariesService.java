package ru.mmtr.translationdictionary.domainservice.export;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryPageRequestModel;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

@Service
public class ExportDictionariesService {
    private final Integer PAGE_SIZE = 100;
    private final LanguageService languageService;
    private final DictionaryService dictionaryService;
    private final UserService userService;

    public ExportDictionariesService(LanguageService languageService,
                                     DictionaryService dictionaryService,
                                     UserService userService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
        this.userService = userService;
    }

    public MultipartFile exportDictionary() {
        var dictionaryCriteria = new DictionaryPageRequestModel();

        dictionaryCriteria.setPageNum(0);
        dictionaryCriteria.setPageSize(PAGE_SIZE);

        PageResultModel<DictionaryModel> page;

        do {
            page = dictionaryService.getPage(dictionaryCriteria);

            dictionaryCriteria.setPageNum(dictionaryCriteria.getPageNum() + 1);
        } while (page.getResultList().size() == PAGE_SIZE);

        /*do {
            languageService.getPage(languageCriteria);
            String fromLanguage = languageCriteria.getLanguageFilter();
            String toLanguage = languageCriteria.getLanguageFilter();

            dictionaryService.getPage(dictionaryCriteria);
            String word = dictionaryCriteria.getWordFilter();
            String translation = dictionaryCriteria.getTranslationFilter();

            userService.getPageUsers(userCriteria);
            String fullName = userCriteria.getLastNameFilter() + " " +  userCriteria.getFatherNameFilter() + " " + userCriteria.getFirstNameFilter();
            String email = userCriteria.getEmailFilter();
            String createdUserId = dictionaryCriteria.
        } while (pageSize == 100);*/
        return null;
    }
}
