package ru.mmtr.translationdictionary.domain.session;

import lombok.Getter;
import lombok.Setter;
import ru.mmtr.translationdictionary.domain.common.PageModel;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserSessionPageRequestModel extends PageModel {
    private LocalDateTime tokenCreatedAtFromFilter;

    private LocalDateTime tokenCreatedAtToFilter;

    private LocalDateTime accessTokenExpiredDateFromFilter;

    private LocalDateTime accessTokenExpiredDateToFilter;

    private LocalDateTime refreshTokenExpiredDateFromFilter;

    private LocalDateTime refreshTokenExpiredDateToFilter;
}
