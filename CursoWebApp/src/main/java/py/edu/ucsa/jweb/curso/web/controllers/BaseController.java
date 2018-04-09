package py.edu.ucsa.jweb.curso.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class BaseController {

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(ModelMap model) {

		model.addAttribute("message", "Maven Web Project + Spring 5 MVC - welcome()");

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return "index";

	}

	@RequestMapping(value = "/welcome/{name}/{lastname}/", method = RequestMethod.GET)
	public String welcomeName(@PathVariable("name") String name, @PathVariable("lastname") String lastname, ModelMap model) {

		model.addAttribute("message", "Maven Web Project + Spring 5 MVC - " + name + " " + lastname);
		return "index";

	}

}
