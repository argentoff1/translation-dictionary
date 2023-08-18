package ru.mmtr.translationdictionary.domain.dictionary;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class DictionaryIdsCollectionModel<T> {
    @Parameter(description = "Все ID в словаре")
    private Collection<T> collectionIds;
}
