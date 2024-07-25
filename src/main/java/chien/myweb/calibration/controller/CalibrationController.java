package chien.myweb.calibration.controller;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.Report;
import chien.myweb.calibration.service.CalibrationService;
import chien.myweb.calibration.service.DataService;
import chien.myweb.calibration.service.InstrumentPersonService;
import chien.myweb.calibration.service.InstrumentService;
import chien.myweb.calibration.service.ReportService;

@RestController
@RequestMapping("/calibration")
public class CalibrationController {
	
	@Autowired
	ReportService reportService;
	@Autowired
	CalibrationService calibrationService;
	@Autowired
	DataService dataService;
	@Autowired
	InstrumentService instrumentService;
	@Autowired
	InstrumentPersonService instrumentPersonService;
	
	// ===== 新增遊外校資訊 (檔案上傳) =====
	@PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("jsonString") String request,
            								  @RequestParam("file") MultipartFile file) throws JsonMappingException, JsonProcessingException {
		
		System.out.println("Received JSON: " + request);		
		
		String filePath = "";
        
		if (file.isEmpty()) {
			String message = "請選擇一個檔案來上傳";	
            return ResponseEntity.ok().body(message);
            
        }else {
        	try {
                // 設定上傳檔案的儲存路徑
            	filePath = "I:/SpringBoot/uploadFiles/" + file.getOriginalFilename();

                File dest = new File(filePath);
                
                // 如果目錄不存在，則創建目錄
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                // 將檔案寫入目的地
                file.transferTo(dest);
                
                System.out.println("test");
                
                ObjectMapper objectMapper = new ObjectMapper();
                Report report = objectMapper.readValue(request, Report.class);
                
                System.out.println(report.getReport_name());
                
                List<Report> newReport = reportService.addReport(report);
                Optional<Report> reportOp = newReport.stream().findAny();
            		
            	if (reportOp.isPresent()) {
         
        	    	String message = "檔案 " + file.getOriginalFilename() + " 上傳成功，已新增一筆校驗紀錄!";
        	    	System.out.println(message);
                
        	    	return ResponseEntity.ok().body(reportOp);
        		}
        		else {
        			String message = "資料庫沒有紀錄或資料輸入錯誤，請再重新確認。" ;
        			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        		}

            } catch (IOException e) {
                e.printStackTrace();
                String message = "檔案上傳失敗，請再重新確認。" ;
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
            }       	
        }     
    }
	
	// ===== 新增內校數據 (執行校驗) =====
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
		
		List<Instrument> instrumentDB = instrumentService.findInstrumentById(id);	
		
		Optional<Instrument> instrumentOp = instrumentDB.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();

		if(instrumentOp.isPresent()){
			Instrument instrument = instrumentOp.get();
			
			String calibrateType = instrument.getCalibrate_type();
			
			if ("內校".equals(calibrateType)) {
				
				Map<String, Object> cablibrationResultMap = calibrationService.findCalibrationResult(id); // 內校
				return ResponseEntity.ok().body(cablibrationResultMap);
			}
			else {
				
				Map<String, Object> cablibrationResultMap = calibrationService.findOutsideCalibrationResult(id); //遊外校
				return ResponseEntity.ok().body(cablibrationResultMap);
			}		
		}
		else{
			System.out.println("沒有此儀器或量具");
	        return ResponseEntity.notFound().build();
	    }	
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
	
	// ===== 校驗簽核 (主管簽核) =====
	@PostMapping("/prepSign/{id}")   
	public ResponseEntity<?> executeSign(@PathVariable("id") Long id){
		
		List<Instrument> instrumentDB = instrumentService.findInstrumentById(id);	 // 透過前端得到儀器id, 並取得該儀器的物件
		
		Optional<Instrument> instrumentOp = instrumentDB.stream() 
				.filter(p -> p.getId().equals(id))
				.findFirst();
		
		if(instrumentOp.isPresent()){
			
			Instrument updateInstrument = instrumentOp.get(); // 取得當前id的儀器
			updateInstrument.setIs_sign("Y");
			
			return ResponseEntity.ok().body(instrumentService.updateInstrumentBySign(updateInstrument)); // 這裡使用 save 進行更新)
		}
		else{
			System.out.println("未進行簽核。"); 	
			return ResponseEntity.ok().body("未進行簽核。");
	    }
		 		
	}
	
}
