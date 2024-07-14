package chien.myweb.calibration.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.service.CalibrationService;
import chien.myweb.calibration.service.DataService;
import chien.myweb.calibration.service.InstrumentPersonService;
import chien.myweb.calibration.service.InstrumentService;

@RestController
@RequestMapping("/calibration")
public class CalibrationController {
	
	@Autowired
	CalibrationService calibrationService;
	@Autowired
	DataService dataService;
	@Autowired
	InstrumentService instrumentService;
	@Autowired
	InstrumentPersonService instrumentPersonService;
	
	// ===== 新增數據 (執行校驗) =====
	@PostMapping("/prepCalibrations") 
	public ResponseEntity<?> executeCalibration(@RequestBody Data request){
			
		System.out.println(request.toString());
		List<Data> newData = dataService.addData(request);
		
		Optional<Data> dataOp = newData.stream().findAny();
		
		if (dataOp.isPresent()) {
			System.out.println("校驗數據新增成功!");
			
			return ResponseEntity.ok().body(newData);
		}
		else {
			System.out.println("沒有新增校驗數據。"); 	
			return ResponseEntity.ok().body("沒有新增校驗數據");
		}

	}
	
	// ===== 取得待校驗器具 =====
	@GetMapping("/prepCalibrations")  
	public ResponseEntity<?> getPrepCalibration(){
		
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
	
	// ===== 取得單一器具之校驗結果 =====
	@GetMapping("/calibrationResult/{id}")  
	public ResponseEntity<Map<String, Object>> getCalibrationResult(@PathVariable("id") Long id){
		
		Map<String, Object> cablibrationResultMap = calibrationService.findCalibrationResult(id);
		
		return ResponseEntity.ok().body(cablibrationResultMap);
	}
	
	// ===== 透過器具id，取得對應的person =====
	@GetMapping("/calibrationPerson/{id}")  
	public ResponseEntity<String> getCalibrationPerson(@PathVariable("id") Long id){
		
		List<Person> personDB = instrumentPersonService.findPersonByInstrumentId(id);
		
		Optional<Person> personOp = personDB.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();

		if(personOp.isPresent()){
			Person person = personOp.get();
			String username = person.getUsername();
				
			return ResponseEntity.ok().body(username); 
		}
		else{
			System.out.println("沒有此校驗人員");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	// ===== 取得待簽核之器具 =====
	@GetMapping("/prepSign")  
	public ResponseEntity<?> getPrepSignInstrument(){
		
		List<Instrument> instrumentDB = calibrationService.findPrepSignInstrument();
		 
		Optional<Instrument> instrumentOp = instrumentDB.stream().findAny();
		
		if (instrumentOp.isPresent()) {
			
			instrumentDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(instrumentDB);
		}
		else {
			System.out.println("目前沒有要簽核的器具"); 	
			return ResponseEntity.ok().body("目前沒有要簽核的器具");
		}
	}
	

}
