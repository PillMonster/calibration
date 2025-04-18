package chien.myweb.calibration.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

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
	
	@GetMapping("instrument/upload")
	public String showCalibrationReportUpload() {
		return "calibrationReportUpload";
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
	public String showInstruments(HttpServletRequest request) {
		String requestIP = request.getRemoteAddr();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
		String requestTime  = df.format(new Date()); // date轉換為String
		
		System.out.println("對方請求的IP為: " + requestIP);
		System.out.println("對方請求的時間為: " + requestTime);
		return "crudInstrument";
	}
	
	@GetMapping("/instrument/createInner")
	public String showCreateInnerInstrument() {
		return "createInnerInstrument";
	}
	
	@GetMapping("/instrument/createOutside")
	public String showCreateOutsideInstrument() {
		return "createOutsideInstrument";
	}
	
	@GetMapping("/instrument/editInner")
	public String showEditInnerInstrument() {
		return "editInnerInstrument";
	}
	
	@GetMapping("/instrument/editOutside")
	public String showEditOutsideInstrument() {
		return "editOutsideInstrument";
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
