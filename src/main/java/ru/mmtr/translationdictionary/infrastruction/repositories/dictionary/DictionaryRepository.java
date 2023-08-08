package ru.mmtr.translationdictionary.infrastruction.repositories.dictionary;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import ru.mmtr.translationdictionary.infrastruction.entities.dictionary.DictionaryEntity;

@Repository
public interface DictionaryRepository extends CrudRepository<DictionaryEntity, Long> {

}
