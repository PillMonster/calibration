package chien.myweb.calibration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/instrument")
public class ShowPageController {
	
	@GetMapping("/crud")
	public String showInstrument() {
		return "crud";
	}
}
