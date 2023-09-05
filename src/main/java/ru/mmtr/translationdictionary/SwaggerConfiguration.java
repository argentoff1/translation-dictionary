package ru.mmtr.translationdictionary;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Переводчик слов",
                description = "Микросервис для перевода слов с разных языков",
                version = "0.0.1",
                contact = @Contact(
                        name = "Parinos Maxim",
                        email = "parinos.ma@mmtr.ru"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfiguration  {

}
