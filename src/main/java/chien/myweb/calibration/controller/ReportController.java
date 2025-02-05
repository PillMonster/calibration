package chien.myweb.calibration.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import chien.myweb.calibration.enity.Report;
import chien.myweb.calibration.service.InstrumentService;
import chien.myweb.calibration.service.ReportService;

@RestController
@RequestMapping("/crud")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	@Autowired
	InstrumentService instrumentService;
	
	
	// ===== 更新校驗報告資訊 (檔案無上傳的情況下) =====
	@PostMapping("/updataReport")
    public ResponseEntity<String> updateReport(@RequestParam("jsonString") String request) throws JsonMappingException, JsonProcessingException {
		
		System.out.println("request: " + request);
		ObjectMapper objectMapper = new ObjectMapper(); // 用於將 JSON 資料與 Java 物件之間進行相互轉換
		Report report = objectMapper.readValue(request, Report.class); // request 中讀取 JSON 數據，指定了目標類型，即將 JSON 資料反序列化為 Report 類別的實例
		
		boolean isUpdated = reportService.updataReport(report);

		if (isUpdated) {
			System.out.println("校驗報告資訊已成功更新。");
            return ResponseEntity.status(HttpStatus.OK).body("校驗報告資訊已成功更新。");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("資料庫中找不到對應的報告資訊。");
        }
    }
	
	// ===== 更新校驗報告資訊 (檔案有上傳的情況下) =====
	@PostMapping("/updataReportFile")
    public ResponseEntity<?> updateReportFile(@RequestParam("jsonString") String request,
    											   @RequestParam("file") MultipartFile file) throws JsonMappingException, JsonProcessingException {
		
		String year = "";
		String month = "";
		String filePath = "";
		boolean isUpdated = false;
		
		System.out.println("request: " + request);
		ObjectMapper objectMapper = new ObjectMapper(); // 用於將 JSON 資料與 Java 物件之間進行相互轉換
		Report report = objectMapper.readValue(request, Report.class); // request 中讀取 JSON 數據，指定了目標類型，即將 JSON 資料反序列化為 Report 類別的實例

		if (report != null) { // 檢查 report 是否為空值
			
			String calibrate_date = report.getCalibrate_date();
	        year = calibrate_date.substring(0, 4); // 提取年份
	        month = calibrate_date.substring(5, 7); // 提取月份
	        //System.out.println("year:" + year + "  ,month: " + month);
	        isUpdated = reportService.updataReportFile(report);
		}   

		if (isUpdated) { // true=資料庫內有該器具的報告資訊

			// ===== 檔案上傳 =====
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
 
                String message = "校驗報告 " + file.getOriginalFilename() + " 已上傳(更新)成功!。";
    	    	System.out.println(message);
    	    	return ResponseEntity.ok().body(message);

            } catch (IOException e) {
                e.printStackTrace();
                String message = "檔案已上傳(更新)失敗，請再重新確認。" ;
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
            }       	
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("未找到對應的報告，更新失敗。");
        }
    }
	
	
	@GetMapping("/report/{id}")  
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
	
	@GetMapping("/instrument/{id}/calibrationDate/{calibration_date}")  
	public ResponseEntity<?> getReportIdByInstimentAndCalibrationDate(@PathVariable("id") Long id, @PathVariable("calibration_date") String calibration_date){
		
		Optional<Report> reportOpt = reportService.findReportObjectByInstumentAndDate(id, calibration_date);

        if (reportOpt.isPresent()) {
            return ResponseEntity.ok(reportOpt.get());
        }
		else{
			System.out.println("儀器無校驗報告");
	        return ResponseEntity.ok("儀器無校驗報告");
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
