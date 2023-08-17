package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Фильтры по подстроке, по дате добавления / модификации
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageModel {
    @Schema(description = "Номер страницы", example = "1")
    private Integer pageNum;

    @Schema(description = "Размер элементов на странице", example = "10")
    private Integer pageSize;
}
