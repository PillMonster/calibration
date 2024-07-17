package chien.myweb.calibration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calibration")
public class ShowPageController {
	
	
	@GetMapping("/sign/prep")
	public String showSignPrep() {
		return "signPrep";
	}
	
	@GetMapping("/sign/execute")
	public String showSignExecute() {
		return "signExecute";
	}
	
	
	@GetMapping("/instrument/result")
	public String showCalibrationResult() {
		return "calibrationResult";
	}
	
	
	@GetMapping("instrument/prep")
	public String showCalibrationPrep() {
		return "calibrationPrep";
	}
	
	@GetMapping("instrument/execute")
	public String showCalibrationExecute() {
		return "calibrationExecute";
	}
	
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
	
	@GetMapping("/person/login")
	public String showLoginPage() {
		return "login";
	}
	
	@GetMapping("/person/login/success")
	public String showLoginSuccessPage() {
		return "loginSuccess";
	}
	
	@GetMapping("/person/getPassword")
	public String showGetPasswordPage() {
		return "getPassword";
	}
}
