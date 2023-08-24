package ru.mmtr.translationdictionary.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mmtr.translationdictionary.domain.common.CollectionResultModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domainservice.session.UserSessionService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/admins")
@Tag(name = "Администратор", description = "Позволяет взаимодействовать с сессиями")
public class AdminController {
    private final UserSessionService userSessionService;

    public AdminController(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @GetMapping(value = "/showAll")
    @Operation(
            summary = "Отображение всех сессий",
            description = "Позволяет отобразить все сессии"
    )
    public CollectionResultModel<UserSessionModel> showAll() {
        return userSessionService.showAll();
    }

    @GetMapping(value = "/getById/{id}")
    @Operation(
            summary = "Получение сессии",
            description = "Позволяет получить одну сессию"
    )
    public UserSessionModel getById(UUID id) {
        return userSessionService.getById(id);
    }
}
