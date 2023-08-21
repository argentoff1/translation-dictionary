package ru.mmtr.translationdictionary.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
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
    public GUIDResultModel login(@RequestBody UserAuthorizationModel model) {

        return userService.login(model);
    }

    @PostMapping(value = "/save")
    @Operation(
            summary = "Регистрация",
            description = "Позволяет зарегистрировать пользователя"
    )
    public SuccessResultModel save(@RequestBody UserSaveModel model) {

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
    public SuccessResultModel archiveById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {

        return userService.archiveById(id);
    }

    @PostMapping(value = "/unarchiveById/{id}")
    public SuccessResultModel unarchiveById(@PathVariable @Parameter(description = "Идентификатор") UUID id) {
        return userService.unarchiveById(id);
    }

    @PostMapping(value = "/logout")
    public SuccessResultModel logout() {
        return userService.logout();
    }
}
