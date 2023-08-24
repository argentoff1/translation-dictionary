package ru.mmtr.translationdictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TranslationDictionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslationDictionaryApplication.class, args);
	}

}
