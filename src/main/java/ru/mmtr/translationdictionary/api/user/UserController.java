package ru.mmtr.translationdictionary.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mmtr.translationdictionary.domain.common.*;
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
            summary = "Вход пользователя в систему",
            description = "Позволяет пользователю авторизоваться"
    )
    public JwtResponseResultModel login(@RequestBody JwtRequestModel model) {
        return userService.login(model);
    }

    @GetMapping("/showAllUsers")
    @Operation(
            summary = "Отображение всех пользователей",
            description = "Позволяет отобразить всех пользователей"
    )
    public CollectionResultModel<UserModel> showAllUsers() {
        return userService.showAllUsers();
    }

    @PostMapping(value = "/getPageUsers")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Отображение всех пользователей постранично",
            description = "Позволяет отобразить всех пользователей постранично"
    )
    public PageResultModel<UserModel> getPageUsers(UserPageRequestModel criteria) {
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

    @PutMapping(value = "/updateLogin")
    @Operation(
            summary = "Обновление логина",
            description = "Позволяет обновить логин пользователя"
    )
    public SuccessResultModel updateLogin(UserLoginUpdateModel model) {
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

    @PostMapping(value = "/logout")
    @Operation(
            summary = "Выход пользователя из системы ",
            description = "Позволяет пользователю выйти из системы"
    )
    public SuccessResultModel logout(JwtRequestModel model) {
        return userService.logout(model);
    }
}
