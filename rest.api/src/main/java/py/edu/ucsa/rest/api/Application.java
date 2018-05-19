package py.edu.ucsa.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/*@SpringBootApplication
public class Application {

	public static void main(String[] args) {
	
		SpringApplication.run(Application.class, args);
	}
}*/

@SpringBootApplication(scanBasePackages = { "py.edu.ucsa.rest.api.web.controllers",
		"py.edu.ucsa.rest.api.core.services", "py.edu.ucsa.rest.api.core.dao" })
// Es lo mismo que anotar con @Configuration @EnableAutoConfiguration
// @ComponentScan
@Import(JpaConfiguration.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
