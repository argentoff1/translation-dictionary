package ru.mmtr.translationdictionary.domain.user;

import lombok.Getter;
import lombok.Setter;
import ru.mmtr.translationdictionary.domain.common.PageModel;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserPageRequestModel extends PageModel {
    private String loginFilter;

    private String passwordFilter;

    private String lastNameFilter;

    private String firstNameFilter;

    private String fatherNameFilter;

    private String emailFilter;

    private String phoneNumberFilter;

    private LocalDateTime createDateFromFilter;

    private LocalDateTime createDateToFilter;

    private LocalDateTime modifyDateFromFilter;

    private LocalDateTime modifyDateToFilter;

    private LocalDateTime archiveDateFromFilter;

    private LocalDateTime archiveDateToFilter;
}
