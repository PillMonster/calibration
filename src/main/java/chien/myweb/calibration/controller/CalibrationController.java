package chien.myweb.calibration.controller;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
		
		
		//String product = request;
		String filePath = "";
		
		
		if (file.isEmpty()) {
			String message = "請選擇一個檔案來上傳";
			
            return ResponseEntity.ok().body(message);
        }else {
        	String message = "上傳成功";
        	return ResponseEntity.ok().body(message);
        }
		
		// 確認檔案名是否正確，這裡示例檔案名應為 "example.txt"
        /*String expectedFileName = "掃Barcode廠商對應代號.xlsx";
        if (!file.getOriginalFilename().equals(expectedFileName)) {
            String message = "檔案名稱不正確，應為 " + expectedFileName;
            return ResponseEntity.ok().body(message);
        }*/

        /*try {
            // 設定上傳檔案的儲存路徑
        	if (product.equals("MS")) {
        		filePath = "D:/PythonTest/動態表/monthReport(MS)/" + file.getOriginalFilename();
        	}
        	else if (product.equals("NF")) {
        		filePath = "D:/PythonTest/動態表/monthReport(NF)/" + file.getOriginalFilename();
        	}
        	else if (product.equals("BH")) {
        		filePath = "D:/PythonTest/動態表/monthReport(BH)/" + file.getOriginalFilename();
        	}
            
            File dest = new File(filePath);
            
            // 如果目錄不存在，則創建目錄
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            // 將檔案寫入目的地
            file.transferTo(dest);
            String message = "檔案上傳成功: " + file.getOriginalFilename();
            return ResponseEntity.ok().body(message);
            
        } catch (IOException e) {
            e.printStackTrace();
            String message = "檔案上傳失敗: " + file.getOriginalFilename();
            return ResponseEntity.ok().body(message);
        }*/
    }
	
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
