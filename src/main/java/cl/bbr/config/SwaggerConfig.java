package cl.bbr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//	http://localhost:8081/swagger-ui.html#/

@EnableSwagger2
@Configuration
public class SwaggerConfig {


    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cl.bbr.controlador"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }

	private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
        		"AppStock Api Docs",
        		"AppStock Api - Api que valida la informacion de las trx",
                "1.0",
                "Terms of Service",
                new Contact("BBR®", "https://www.bbr.cl/",
                		"contacto@bbr.cl"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html"
        );

        return apiInfo;
    }
}