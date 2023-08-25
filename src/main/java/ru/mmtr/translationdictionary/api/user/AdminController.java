package ru.mmtr.translationdictionary.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionPageRequestModel;
import ru.mmtr.translationdictionary.domain.user.JwtRequestModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domain.user.UserSaveModel;
import ru.mmtr.translationdictionary.domainservice.session.UserSessionService;
import ru.mmtr.translationdictionary.domainservice.user.AdminService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/admins")
@Tag(name = "Администратор", description = "Позволяет взаимодействовать с сессиями")
public class AdminController {
    private final UserSessionService userSessionService;
    private final AdminService adminService;

    public AdminController(UserSessionService userSessionService, AdminService adminService) {
        this.userSessionService = userSessionService;
        this.adminService = adminService;
    }

    @PostMapping(value = "/login")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Вход администратора в систему",
            description = "Позволяет администратору войти в систему"
    )
    public JwtResponseResultModel login(@RequestBody JwtRequestModel model) {
        return adminService.login(model);
    }

    @GetMapping(value = "/showAllSessions")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Отображение всех сессий постранично",
            description = "Позволяет отобразить все сессии постранично"
    )
    public CollectionResultModel<UserSessionModel> showAllSessions() {
        return userSessionService.showAll();
    }

    @PostMapping(value = "/getPageSessions")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Отображение всех пользователей постранично",
            description = "Позволяет отобразить всех пользователей постранично"
    )
    public PageResultModel<UserSessionModel> getPageSessions(UserSessionPageRequestModel criteria) {
        return userSessionService.getPage(criteria);
    }

    @GetMapping(value = "/getSessionById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Получение сессии",
            description = "Позволяет получить одну сессию"
    )
    public UserSessionModel getById(UUID id) {
        return userSessionService.getById(id);
    }

    @GetMapping("/showAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Отображение всех пользователей",
            description = "Позволяет отобразить всех пользователей"
    )
    public CollectionResultModel<UserModel> showAllUsers() {
        return adminService.showAllUsers();
    }

    @PostMapping("/saveAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Создание нового администратора",
            description = "Позволяет создать нового администратора"
    )
    public GUIDResultModel saveAdmin(@RequestBody UserSaveModel model) {
        return adminService.saveAdmin(model);
    }

    @PostMapping(value = "/archiveById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Архивация пользователя",
            description = "Позволяет архивировать пользователя"
    )
    public SuccessResultModel archiveById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {
        return adminService.archiveById(id);
    }

    @PostMapping(value = "/unarchiveById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Разархивация пользователя",
            description = "Позволяет разархивировать пользователя"
    )
    public SuccessResultModel unarchiveById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {
        return adminService.unarchiveById(id);
    }

    @PostMapping(value = "/logout")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Выход администратора из системы ",
            description = "Позволяет администратору выйти из системы"
    )
    public SuccessResultModel logout(JwtRequestModel model) {
        return adminService.logout(model);
    }
}
