package cl.bbr;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan("cl.bbr")
@EnableJdbcRepositories("cl.bbr")
public class AppStockApiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(AppStockApiApplication.class, args);
	}

}