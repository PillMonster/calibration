package chien.myweb.calibration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResponseController {
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello(){
		return new ResponseEntity<> ("Hello! Spring Boot!", HttpStatus.OK);
	}
	
	@GetMapping("/showView")
	public String getPage() {
		return "view";
	}
}
