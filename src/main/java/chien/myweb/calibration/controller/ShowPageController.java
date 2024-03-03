package chien.myweb.calibration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowPageController {
	
	@GetMapping("/instrument/crud")
	public String showInstruments() {
		return "crud";
	}
	
	@GetMapping("/instrument/create")
	public String showCreateInstrument() {
		return "createInstrument";
	}
	
	@GetMapping("/person/crud")
	public String showPersons() {
		return "person";
	}
	
	@GetMapping("/person/create")
	public String showCreatePersons() {
		return "createPerson";
	}
	
	@GetMapping("/person/edit")
	public String showEditPersons() {
		return "editPerson";
	}
}
