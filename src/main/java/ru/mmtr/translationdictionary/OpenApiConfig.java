package ru.mmtr.translationdictionary;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Переводчик слов",
                description = "Микросервис для перевода слов с разных языков",
                version = "1.0.0",
                contact = @Contact(
                        name = "Parinos Maxim",
                        email = "parinos.ma@mmtr.ru",
                        url = "@parinos_maxim"
                )
        )
)
public class OpenApiConfig {

}
