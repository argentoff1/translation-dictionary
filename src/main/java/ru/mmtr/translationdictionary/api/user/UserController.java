package ru.mmtr.translationdictionary.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.user.*;
import ru.mmtr.translationdictionary.domainservice.common.CommonUtils;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
@Tag(name = "Пользователь", description = "Позволяет взаимодействовать с пользователями")
@SecurityRequirement(name = "JWT")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    @Operation(
            summary = "Генерация нового access токена",
            description = "Позволяет сгенерировать новый access токен"
    )
    public JwtResponseResultModel getNewAccessToken(@RequestBody RefreshJwtRequestModel model) {
        return userService.getAccessToken(model.getRefreshToken());
    }

    @GetMapping(value = "/refresh")
    @Operation(
            summary = "Генерация новых access и refresh токенов",
            description = "Позволяет сгенерировать новые access и refresh токены"
    )
    public JwtResponseResultModel refresh() {
        return userService.refreshToken(CommonUtils.getSubject());
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

    @PostMapping(value = "/getPageUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Отображение всех пользователей постранично",
            description = "Позволяет отобразить всех пользователей постранично"
    )
    public PageResultModel<UserModel> getPageUsers(@RequestBody UserPageRequestModel criteria) {
        return userService.getPageUsers(criteria);
    }

    @GetMapping(value = "/getUserById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Получение пользователя",
            description = "Позволяет получить одного пользователя"
    )
    public UserModel getUserById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/getUserByLogin/{login}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Получение пользователя",
            description = "Позволяет получить пользователя по логину"
    )
    public UserModel getUserByLogin(@PathVariable @Parameter(description = "Логин") String login) {
        return userService.getByLogin(login);
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Регистрация",
            description = "Позволяет зарегистрировать пользователя"
    )
    public GUIDResultModel save(@RequestBody UserSaveModel model) {
        return userService.save(model);
    }

    @PutMapping(value = "/updateUser")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Обновление данных",
            description = "Позволяет обновить личные данные"
    )
    public SuccessResultModel updateUser(@RequestBody UserUpdateModel model) {
        return userService.updateUser(model);
    }

    @PutMapping(value = "/updateLogin")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Обновление логина",
            description = "Позволяет обновить логин пользователя"
    )
    public SuccessResultModel updateLogin(@RequestBody UserLoginUpdateModel model) {
        return userService.updateLogin(model);
    }

    @PutMapping(value = "/updatePassword")
    @PreAuthorize("hasAuthority('USER')")
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

    @GetMapping(value = "/logout")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Выход пользователя из системы ",
            description = "Позволяет пользователю выйти из системы"
    )
    public SuccessResultModel logout() {
        return userService.logout();
    }
}
