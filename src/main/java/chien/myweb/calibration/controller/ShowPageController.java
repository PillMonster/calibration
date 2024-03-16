package chien.myweb.calibration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowPageController {
	
	@GetMapping("/instrument/crud")
	public String showInstruments() {
		return "crudInstrument";
	}
	
	@GetMapping("/instrument/create")
	public String showCreateInstrument() {
		return "createInstrument";
	}
	
	@GetMapping("/instrument/edit")
	public String showEditInstrument() {
		return "editInstrument";
	}
	
	@GetMapping("/person/crud")
	public String showPersons() {
		return "crudPerson";
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
