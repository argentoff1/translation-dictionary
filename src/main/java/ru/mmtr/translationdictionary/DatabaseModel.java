package ru.mmtr.translationdictionary;

import io.ebean.Database;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DatabaseModel {
    private Database database;
}
