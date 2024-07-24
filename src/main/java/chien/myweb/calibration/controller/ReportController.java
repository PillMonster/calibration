package chien.myweb.calibration.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Report;
import chien.myweb.calibration.service.ReportService;

@RestController
@RequestMapping("/crud")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@GetMapping("/reportId/{id}")  
	public ResponseEntity<?> getReportById(@PathVariable("id") Long id){
		
		List<Report> reportDB = reportService.findByReportId(id);	

		Optional<Report> reportOp = reportDB.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();
		
		if(reportOp.isPresent()){
			Report report = reportOp.get();
			System.out.println(report.toString());
			return ResponseEntity.ok().body(report); 
		}
		else{
			System.out.println("沒有資料");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("沒有資料"); // httpstatus=500
	    }
	}
	
	@GetMapping("/outside/result/{result}")  
	public ResponseEntity<?> getResult(@PathVariable("result") String result){
		
		List<Report> reportDB = reportService.findByResult(result);	
		
		Optional<Report> reportOp = reportDB.stream().findAny();
		
		if(reportOp.isPresent()){
			
			reportDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(reportDB); 
		}
		else{
			System.out.println("沒有資料");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("沒有資料"); // httpstatus=500
	    }
	}
	
	@GetMapping("/outside/is_taf/{is_taf}")  
	public ResponseEntity<?> getIsTaf(@PathVariable("is_taf") String is_taf){
		
		List<Report> reportDB = reportService.findByIsTAF(is_taf);	
		
		Optional<Report> reportOp = reportDB.stream().findAny();
		
		if(reportOp.isPresent()){
			
			reportDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(reportDB); 
		}
		else{
			System.out.println("沒有資料");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("沒有資料"); // httpstatus=500
	    }
	}
	
	@GetMapping("/outside/calibrateDate")  
	public ResponseEntity<?> getCalibrateDate(@RequestBody Data request){
		
		String calibrate_date = request.getCalibrate_date();
		
		List<Report> reportDB = reportService.findByCalibrateDate(calibrate_date);	
		
		Optional<Report> reportOp = reportDB.stream().findAny();
		
		if(reportOp.isPresent()){
			
			reportDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(reportDB); 
		}
		else{
			System.out.println("沒有資料");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("沒有資料"); // httpstatus=500
	    }
	}
	
	@GetMapping("/reportAll")  
	public ResponseEntity<?> getAllReport(){
		
		List<Report> reportDB = reportService.findReportAll();	
		
		Optional<Report> reportOp = reportDB.stream().findAny();
		
		if(reportOp.isPresent()){
			
			reportDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(reportDB); 
		}
		else{
			System.out.println("沒有資料");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("沒有資料"); // httpstatus=500
	    }
	}

}
