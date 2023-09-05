package ru.mmtr.translationdictionary;

import com.google.common.base.Predicates;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

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
@Configuration
//@EnableSwagger2
public class SwaggerConfiguration {
    /*@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(List.of(new ApiKey("apiKey", "Authorization", "header")))
                .securityContexts(List.of(securityContext()));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Переводчик слов")
                .description("Микросервис для перевода слов с разных языков")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        var authorizationScope = new AuthorizationScope("apiKey", "accessEverything");
        var authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("apiKey", authorizationScopes));
    }*/
}
