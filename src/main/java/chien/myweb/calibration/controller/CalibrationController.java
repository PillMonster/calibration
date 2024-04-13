package chien.myweb.calibration.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.service.CalibrationService;

@RestController
@RequestMapping("/calibration")
public class CalibrationController {
	
	@Autowired
	CalibrationService calibrationService;
	
	@GetMapping("/prepCalibrations")  
	public ResponseEntity<?> getPrepCalibrationList(){
		
		List<Instrument> instrumentDB = calibrationService.findPrepInstruments();
		 
		Optional<Instrument> instrumentOp = instrumentDB.stream().findAny();
		
		if (instrumentOp.isPresent()) {
			
			instrumentDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(instrumentDB);
		}
		else {
			System.out.println("當月沒有要執行校驗的器具"); 	
			return ResponseEntity.ok().body("當月沒有要執行校驗的器具");
		}
	}
}
