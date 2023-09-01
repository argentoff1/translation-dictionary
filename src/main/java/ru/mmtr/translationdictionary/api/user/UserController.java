package ru.mmtr.translationdictionary.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.export.ExportDictionariesModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionPageRequestModel;
import ru.mmtr.translationdictionary.domain.user.*;
import ru.mmtr.translationdictionary.domainservice.common.CommonUtils;
import ru.mmtr.translationdictionary.domainservice.export.ExportDictionariesService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
@Tag(name = "Пользователь", description = "Позволяет взаимодействовать с пользователями")
public class UserController {
    private final UserService userService;
    private final ExportDictionariesService exportDictionariesService;

    public UserController(UserService userService, ExportDictionariesService exportDictionariesService) {
        this.userService = userService;
        this.exportDictionariesService = exportDictionariesService;
    }

    @GetMapping(value = "/exportDictionary")
    @Operation(
            summary = "Экспорт словаря",
            description = "Позволяет экспортировать данные из словаря"
    )
    public ExportDictionariesModel exportDictionary() {

        return exportDictionariesService.exportDictionary();
    }

    @PostMapping(value = "/login")
    @Operation(
            summary = "Вход пользователя в систему",
            description = "Позволяет пользователю авторизоваться"
    )
    public JwtResponseResultModel login(@RequestBody JwtRequestModel model) {
        return userService.login(model);
    }

    @PostMapping(value = "/getNewAccessToken")
    public JwtResponseResultModel getNewAccessToken(@RequestBody RefreshJwtRequestModel model) {

        return userService.getAccessToken(model.getRefreshToken(), model.getSessionId());
    }

    @PostMapping(value = "/getNewRefreshToken")
    public JwtResponseResultModel getNewRefreshToken(@RequestBody RefreshJwtRequestModel model) {

        return userService.refreshToken(model.getRefreshToken(), model.getSessionId());
    }

    @GetMapping("/showAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Отображение всех пользователей",
            description = "Позволяет отобразить всех пользователей"
    )
    public CollectionResultModel<UserModel> showAllUsers() {
        return userService.showAllUsers();
    }

    @GetMapping(value = "/showAllSessions")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Отображение всех сессий постранично",
            description = "Позволяет отобразить все сессии постранично"
    )
    public CollectionResultModel<UserSessionModel> showAllSessions() {
        return userService.showAllSessions();
    }

    @PostMapping(value = "/getPageUsers")
    @Operation(
            summary = "Отображение всех пользователей постранично",
            description = "Позволяет отобразить всех пользователей постранично"
    )
    public PageResultModel<UserModel> getPageUsers(@RequestBody UserPageRequestModel criteria) {
        return userService.getPageUsers(criteria);
    }

    @PostMapping(value = "/getPageSessions")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Отображение всех пользователей постранично",
            description = "Позволяет отобразить всех пользователей постранично"
    )
    public PageResultModel<UserSessionModel> getPageSessions(@RequestBody UserSessionPageRequestModel criteria) {
        return userService.getPageSessions(criteria);
    }

    @GetMapping(value = "/getUserById/{id}")
    @Operation(
            summary = "Получение пользователя",
            description = "Позволяет получить одного пользователя"
    )
    public UserModel getUserById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/getSessionById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Получение сессии",
            description = "Позволяет получить одну сессию"
    )
    public UserSessionModel getSessionById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {
        return userService.getSessionById(id);
    }

    @PostMapping(value = "/save")
    @Operation(
            summary = "Регистрация",
            description = "Позволяет зарегистрировать пользователя"
    )
    public GUIDResultModel save(@RequestBody UserSaveModel model) {
        return userService.save(model);
    }

    @PutMapping(value = "/updateUser")
    @Operation(
            summary = "Обновление данных",
            description = "Позволяет обновить личные данные"
    )
    public SuccessResultModel updateUser(@RequestBody UserUpdateModel model) {
        return userService.updateUser(model);
    }

    @PutMapping(value = "/updateLogin")
    @Operation(
            summary = "Обновление логина",
            description = "Позволяет обновить логин пользователя"
    )
    public SuccessResultModel updateLogin(@RequestBody UserLoginUpdateModel model) {
        return userService.updateLogin(model);
    }

    @PutMapping(value = "/updatePassword")
    @Operation(
            summary = "Обновление пароля",
            description = "Позволяет обновить пароль"
    )
    public SuccessResultModel updatePassword(@RequestBody UserPasswordUpdateModel model) {
        return userService.updatePassword(model);
    }

    @PostMapping(value = "/archiveById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Архивация пользователя",
            description = "Позволяет архивировать пользователя"
    )
    public SuccessResultModel archiveById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {
        return userService.archiveById(id);
    }

    @PostMapping(value = "/unarchiveById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Разархивация пользователя",
            description = "Позволяет разархивировать пользователя"
    )
    public SuccessResultModel unarchiveById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {
        return userService.unarchiveById(id);
    }

    @PostMapping(value = "/logout")
    @Operation(
            summary = "Выход пользователя из системы ",
            description = "Позволяет пользователю выйти из системы"
    )
    public SuccessResultModel logout(JwtRequestModel model) {
        return userService.logout(model);
    }
}
