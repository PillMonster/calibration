package chien.myweb.calibration.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import chien.myweb.calibration.enity.RequestChecked;
import chien.myweb.calibration.service.CalibrationService;
import chien.myweb.calibration.service.DataService;
import chien.myweb.calibration.service.InstrumentPersonService;
import chien.myweb.calibration.service.InstrumentReportService;
import chien.myweb.calibration.service.InstrumentService;
import chien.myweb.calibration.service.PersonService;
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
	@Autowired
	InstrumentReportService instrumentReportService;
	
	//  ===== 在瀏覽器中查看pdf =====
	@GetMapping("/view")
    public ResponseEntity<InputStreamResource> viewPdf(@RequestParam Long id, @RequestParam String date) 
    		throws FileNotFoundException, UnsupportedEncodingException {
		
		System.out.println("view pdf for instrumentId: " + id + ", calibrateDate: " + date);
		String year = date.substring(0, 4); // 提取年份
        String month = date.substring(5, 7); // 提取月份
        //System.out.println("year:" + year + "  ,month: " + month);
		
		String fileName = instrumentReportService.findReportNameByInstrumentIdAndDate(id, date); // 查詢報告名稱(透過器具id和校驗日期)
		System.out.println("file name: " + fileName);
		String filePath = "D:/SpringBoot/uploadFiles/CalibrationReport/" + year + "/" + month + "/" + fileName;  // 指定文件路径

        File file = new File(filePath);
        
        if (!file.exists()) {
        	System.out.println(fileName + " 檔案不存在。");
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // FileInputStream是用來讀入(read)文件，以8-bit 的 bytes 來進行輸入輸出
        // 創建了一個 FileInputStream 類別來讀取文件內容，file 是一個 File 類別，代表需要讀取的 PDF 文件。        
        FileInputStream fileInputStream = new FileInputStream(file);

        // Spring 提供了InputStreamResource 來存取二進位輸入流資源
        // 創建了一個 InputStreamResource 對象，用於包裝 FileInputStream，以便稍後將其作為 HTTP 響應的主體（body）返回
        InputStreamResource resource = new InputStreamResource(fileInputStream);

        // 設置 HTTP 回應的標頭
        HttpHeaders headers = new HttpHeaders();
        
        // 使用 URLEncoder.encode 方法對文件名進行 URL 編碼。URL 編碼可以將文件名中的特殊字符(如中文字符)轉換為在 HTTP 標頭中有效的格式
        String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8").replace("+", "%20");
        
        // 設置 Content-Disposition 標頭，告訴瀏覽器應該內部顯示文件
        // 設置 inline，即在瀏覽器中打開而不是下載，並包含編碼後的文件名
        // 設置 filename， 下載的文件名稱
        headers.add("Content-Disposition", "inline; filename*=UTF-8''" + encodedFileName);

        return ResponseEntity.ok()
                .headers(headers) // 將前面設置的 HTTP 標頭添加到response中
                .contentType(MediaType.APPLICATION_PDF) // 設置為 application/pdf 告訴瀏覽器響應內容是一個 PDF 文件。
                .body(resource); // 將 InputStreamResource 物件作為響應的主體(body)返回，從而包含了 PDF 文件的內容。
    }
	
	//  ===== 下載pdf =====
	@GetMapping("/download")
	public ResponseEntity<InputStreamResource> downloadPdf(@RequestParam Long id, @RequestParam String date) 
			throws FileNotFoundException, UnsupportedEncodingException {
        
		System.out.println("download pdf for instrumentId: " + id + ", calibrateDate: " + date);
		String year = date.substring(0, 4); // 提取年份
        String month = date.substring(5, 7); // 提取月份
        //System.out.println("year:" + year + "  ,month: " + month);
		
		String fileName = instrumentReportService.findReportNameByInstrumentIdAndDate(id, date); // 查詢報告名稱(透過器具id和校驗日期)

		String filePath = "D:/SpringBoot/uploadFiles/CalibrationReport/" + year + "/" + month + "/" + fileName;  // 指定文件路径
       
        File file = new File(filePath);

        if (!file.exists()) {
        	System.out.println(fileName + " 檔案不存在。");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamResource resource = new InputStreamResource(fileInputStream);

        HttpHeaders headers = new HttpHeaders();
        String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8").replace("+", "%20");
        headers.add("Content-Disposition", "attachment;filename*=UTF-8''" + encodedFileName);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
	
	// ===== 新增遊外校資訊 (檔案上傳) =====
	@PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("jsonString") String request,
            								  @RequestParam("file") MultipartFile file) throws JsonMappingException, JsonProcessingException {
		
		System.out.println("Received JSON: " + request);		

		// ========== 前端取得資料前處理 ========
		String year = "";
		String month = "";
		String filePath = "";
		
		ObjectMapper objectMapper = new ObjectMapper(); // 用於將 JSON 資料與 Java 物件之間進行相互轉換
		Report report = objectMapper.readValue(request, Report.class); // request 中讀取 JSON 數據，指定了目標類型，即將 JSON 資料反序列化為 Report 類別的實例
		
		if (report != null) { // 檢查 report 是否為空值
			
			String calibrate_date = report.getCalibrate_date();
	        year = calibrate_date.substring(0, 4); // 提取年份
	        month = calibrate_date.substring(5, 7); // 提取月份
	        //System.out.println("year:" + year + "  ,month: " + month);
	        
	        reportService.addReport(report); // 新增一筆報告紀錄
	        
		}
		else {
			String message = "資料庫沒有紀錄或資料輸入錯誤，請再重新確認。" ;
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
		
		// ========== 檔案處理 ========
		if (file.isEmpty()) { // 如果檔案不存在
			String message = "請選擇一個檔案來上傳";	
            return ResponseEntity.ok().body(message);
            
        }else {
        	try {
                // 設定上傳檔案的儲存路徑
            	filePath = "D:/SpringBoot/uploadFiles/CalibrationReport/" + year + "/" + month + "/" + file.getOriginalFilename(); // 指定文件路径
            	System.out.println("filePath: " + filePath);
                File dest = new File(filePath);
                
                // 如果目錄不存在，則創建目錄
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                // 將檔案寫入目的地
                file.transferTo(dest);
 
                String message = "檔案 " + file.getOriginalFilename() + " 上傳成功，已新增一筆校驗紀錄!";
    	    	System.out.println(message);
    	    	return ResponseEntity.ok().body(message);

            } catch (IOException e) {
                e.printStackTrace();
                String message = "檔案上傳失敗，請再重新確認。" ;
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
            }       	
        }     
		//return null;
    }
	
	// ===== 新增內校數據 (執行校驗) =====
	@PostMapping("/executeCalibrations") 
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
	
	// 搜尋器具編號，並確認是否為待校驗器具
	@GetMapping("/prepInstrumentNo/{number}")  
	public ResponseEntity<List<Instrument>> getInstrumentByNumberd(@PathVariable("number") String number){
		System.out.println("number: " + number);
		List<Instrument> instrumentDB = instrumentService.findInstrumentByNumber(number);	
		
		Optional<Instrument> instrumentOp = instrumentDB.stream().findAny();
		
		if(instrumentOp.isPresent()){
			
			List<Instrument> responseInstruments = calibrationService.selectPrepInstruments(instrumentDB);
			responseInstruments.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(responseInstruments); 
		}
		else{
			System.out.println("找不到此儀器或量具");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	// 搜尋器具名稱，並確認是否為待校驗器具
	@GetMapping("/prepInstrumentName/{name}")  
	public ResponseEntity<List<Instrument>> getInstrumentById(@PathVariable("name") String name){
		System.out.println("name: " + name);
		List<Instrument> instrumentDB = instrumentService.findInstrumentByName(name);	
		
		Optional<Instrument> instrumentOp = instrumentDB.stream().findAny();

		
		if(instrumentOp.isPresent()){
			
			List<Instrument> responseInstruments = calibrationService.selectPrepInstruments(instrumentDB);
			responseInstruments.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(responseInstruments); 
		}
		else{
			System.out.println("找不到此儀器或量具");
	        return ResponseEntity.notFound().build();
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
	        return ResponseEntity.notFound().build();
		}
	}
	
	// ===== 取得待校驗器具 =====
	@PostMapping("/prepCalibrations")  
	public ResponseEntity<?> getPrepCalibration(@RequestBody List<RequestChecked> requestChecked){
		
		List<String> typeList = new ArrayList<>();
        List<String> personList = new ArrayList<>();
        List<String> localationList = new ArrayList<>();
        
        for (RequestChecked jsonData : requestChecked) {
            typeList = jsonData.getType();
            personList = jsonData.getPerson();
            localationList = jsonData.getLocalation();
            
            System.out.println("====== From 前端 ======");

	    	System.out.println("校驗類型 from 前端: " +  typeList);
	    	System.out.println("校驗人員 from 前端: " + personList);
	    	System.out.println("校驗地點or公司 from 前端: " + localationList);
	    	System.out.println("-----------------------");
        }
        
        List<Instrument> instrumentDB = instrumentService.findByMultiple(typeList, personList, localationList); 
        
		Optional<Instrument> instrumentOp = instrumentDB.stream().findAny();
		
		if (instrumentOp.isPresent()) {
			
			 List<Instrument> responseInstruments = calibrationService.selectPrepInstruments(instrumentDB);
			 responseInstruments.forEach(item -> System.out.println("選擇後待校驗的器具: " + item.toString()));
			 
			return ResponseEntity.ok().body(responseInstruments);
		}
		else {
			System.out.println("當月沒有要執行校驗的器具");
	        return ResponseEntity.notFound().build();
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
			System.out.println("找不到此儀器或量具");
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
