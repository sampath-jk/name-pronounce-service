package com.wf.hackathon;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
		title = "Name Pronunciation Services",
		version = "1.0",
		description = "Name Pronunciation API"
)
)
@SecurityScheme(name = "NamePronunciationApi", scheme = "Bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@SpringBootApplication
public class NamePronunciationApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamePronunciationApplication.class, args);
	}

}
