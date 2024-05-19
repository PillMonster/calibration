package chien.myweb.calibration.controller;

import java.util.ArrayList;
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
import chien.myweb.calibration.enity.RequestData;
import chien.myweb.calibration.enity.ResponseData;
import chien.myweb.calibration.service.CalibrationService;
import chien.myweb.calibration.service.DataService;
import chien.myweb.calibration.service.InstrumentService;
import chien.myweb.calibration.service.InstrumentSpecDataService;

@RestController
@RequestMapping("/calibration")
public class CalibrationController {
	
	@Autowired
	CalibrationService calibrationService;
	@Autowired
	DataService dataService;
	@Autowired
	InstrumentService instrumentService;
	
	@PostMapping("/prepCalibrations") // 新增
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
	
	@GetMapping("/calibrationResult")  
	public ResponseEntity<List<Map>> getCalibrationResult(){
		
		List<Map> cablibrationResultMap = calibrationService.findCalibrationResult();
		
		/*List<ResponseData> cablibrationDB = instrumentService.getCalibrationResult();
		 
		Optional<ResponseData> cablibrationOp = cablibrationDB.stream().findAny();
		
		if (cablibrationOp .isPresent()) {
			
			cablibrationDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(cablibrationDB);
		}
		else {
			System.out.println("沒有校驗結果"); 	
			return ResponseEntity.ok().body("沒有校驗結果");
		}*/
	
		return ResponseEntity.ok().body(cablibrationResultMap);
	}

}
