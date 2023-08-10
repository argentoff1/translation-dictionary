package ru.mmtr.translationdictionary.domain.models.language;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "Languages")
public class LanguageModel {
    /*@Autowired
    LanguageRepository languageRepository;*/

    @Id
    private UUID languageId;

    private String languageName;

    public LanguageModel() {}

    public LanguageModel(UUID languageId, String languageName) {
        this.languageId = languageId;
        this.languageName = languageName;
    }

    public UUID getLanguageId() {
        return languageId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
