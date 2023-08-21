package ru.mmtr.translationdictionary.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.common.TokenResultModel;
import ru.mmtr.translationdictionary.domain.user.*;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
@Tag(name = "Пользователь", description = "Позволяет взаимодействовать с пользователями")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    @Operation(
            summary = "Авторизация пользователя",
            description = "Позволяет пользователю авторизоваться"
    )
    public TokenResultModel login(@RequestBody UserAuthorizationModel model) {

        return userService.login(model);
    }

    @PostMapping(value = "/getPage")
    @Operation(
            summary = "Отображение всех пользователей постранично",
            description = "Позволяет отобразить всех пользователей постранично"
    )
    public PageResultModel<UserModel> getPage(UserPageRequestModel criteria) {

        return userService.getPage(criteria);
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

    @PutMapping(value = "/updatePassword")
    @Operation(
            summary = "Обновление пароля",
            description = "Позволяет обновить пароль"
    )
    public SuccessResultModel updatePassword(@RequestBody UserPasswordUpdateModel model) {

        return userService.updatePassword(model);
    }

    @PostMapping(value = "/archiveById/{id}")
    @Operation(
            summary = "Архивация пользователя",
            description = "Позволяет архивировать пользователя"
    )
    public SuccessResultModel archiveById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {

        return userService.archiveById(id);
    }

    @PostMapping(value = "/unarchiveById/{id}")
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
    public SuccessResultModel logout() {
        return userService.logout();
    }
}
