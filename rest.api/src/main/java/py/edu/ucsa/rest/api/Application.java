package py.edu.ucsa.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*@SpringBootApplication
public class Application {

	public static void main(String[] args) {
	
		SpringApplication.run(Application.class, args);
	}
}*/

@SpringBootApplication(scanBasePackages = { "py.edu.ucsa.rest.api.web.controllers",
		"py.edu.ucsa.rest.api.core.services" })
// Es lo mismo que anotar con @Configuration @EnableAutoConfiguration
// @ComponentScan
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
