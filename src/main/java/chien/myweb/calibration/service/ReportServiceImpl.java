package chien.myweb.calibration.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Instantiator;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.InstrumentDao;
import chien.myweb.calibration.dao.InstrumentReportDao;
import chien.myweb.calibration.dao.ReportDao;
import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Report;
import chien.myweb.calibration.enity.RequestData;

@Service
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	ReportDao reportDao;
	@Autowired
	InstrumentDao instrumentDao;
	@Autowired
	InstrumentReportDao instrumentReportDao;
	@Autowired
	InstrumentReportService instrumentReportService;
	
	// ========== 新增 ==========
	@Override
	public List<Report> addReport(Report request) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		List<Report> newReport = new ArrayList<>();	// 用來存放更新後的數據初始List
	
		int createResult = 0; // 創建結果初始值(新增儀器、報告的關聯紀錄)	
		
		Report report = new Report();
		report.setReport_no(request.getReport_no());
		report.setReport_name(request.getReport_name());
		report.setResult(request.getResult());
		report.setCalibrate_date(request.getCalibrate_date());
		report.setIs_taf(request.getIs_taf());
		
		reportDao.save(report); // 存入資料庫
		newReport.add(report); // 將物件傳入list，用來傳回前端
		
		Long instrumentId = request.getId(); 
		Long reportId = report.getId();
			
		// 新增儀器、報告的關聯紀錄
		createResult = instrumentReportDao.addInstrumentAndReport(instrumentId, reportId); 	

		// ===== 更新上次校驗日期 =====
		if (createResult == 1) { 
			
			List<Instrument> instrumentDB = instrumentDao.findByInstrumentId(request.getId());
			
			Optional<Instrument> instrumentOp = instrumentDB.stream()
					.filter(p -> p.getId().equals(request.getId()))
					.findFirst();

			if(instrumentOp.isPresent()){
				
				Instrument updateInstrument = instrumentOp.get(); // 取得該物件
				LocalDate calibrate_date = LocalDate.parse(request.getCalibrate_date(), formatter); // 轉換格式(String to LocalDate)

				updateInstrument.setLast_calibrate_date(calibrate_date); // 設定該物件的校驗日期
				updateInstrument.setIs_calibration("Y"); // 設定該物件已校驗完成
				
				instrumentDao.save(updateInstrument); // 更新資料庫			
			}
			else{
				System.out.println("沒有此儀器或量具。");
		    }
		}
		else {
			System.out.println("新增儀器、報告的關聯紀錄失敗。");
		}
		
		return newReport;
	}

	// ========== 更新 ==========
	@Override
	public boolean updataReport(Report request) {
		
		System.out.println("updata 1");
		Long instrumentId = request.getId();
		String calibrate_date = request.getCalibrate_date();
		Optional<Report> reportOpt = reportDao.findReportObjectByInstumentAndDate(instrumentId, calibrate_date);
		
		if (reportOpt.isPresent()) {
	
			Report reportObj = reportOpt.get();
			System.out.println("reportObj: " + reportObj);
			System.out.println("修改前的日期: " + request.getCalibrate_date());
			reportObj.setReport_no(request.getReport_no());
			reportObj.setCalibrate_date(request.getCalibrate_date());
			reportObj.setIs_taf(request.getIs_taf());
			reportObj.setResult(request.getResult());
			reportDao.save(reportObj); // 這裡使用 save 進行更新

			return true;
        }
		else {
			System.out.println("updata 2");
			addReport(request);
			return false;
		}
	}
	
	// ========== 更新 ==========
	@Override
	public boolean updataReportFile(Report request) {
		
		Long instrumentId = request.getId();
		String calibrate_date = request.getCalibrate_date();
		Optional<Report> reportOpt = reportDao.findReportObjectByInstumentAndDate(instrumentId, calibrate_date);
		
		if (reportOpt.isPresent()) {
	
			Report reportObj = reportOpt.get();
			System.out.println("reportObj: " + reportObj);
			System.out.println("修改前的日期: " + request.getCalibrate_date());

			reportObj.setReport_no(request.getReport_no());
			reportObj.setReport_name(request.getReport_name());
			reportObj.setResult(request.getResult());
			reportObj.setCalibrate_date(request.getCalibrate_date());
			reportObj.setIs_taf(request.getIs_taf());
			reportDao.save(reportObj); // 這裡使用 save 進行更新
			return true;

		}else {
			
			return false;
		}
	
	}
	
	// ========== 複製 ==========
	@Override
	public void copyReportFile(Instrument instrument, RequestData request) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// ===== instrument update service =====
		// ===== 取得當前儀器對應的data id的進行日期更新 =====
		String befCalibrateDate = instrument.getLast_calibrate_date().format(formatter); // 轉換字串格式
		String afferCalibrateDate = request.getLast_calibrate_date().format(formatter);
		
		System.out.println("befCalibrateDate: " + befCalibrateDate);
		System.out.println("afferCalibrateDate: " + afferCalibrateDate);
		
		// ========== 檔案處理 ==========
	    String befYear = befCalibrateDate.substring(0, 4); // 提取年份
	    String befMonth = befCalibrateDate.substring(5, 7); // 提取月份
	    
		String afferYear = afferCalibrateDate.substring(0, 4); // 提取年份
	    String afferMonth = afferCalibrateDate.substring(5, 7); // 提取月份
	    
	    String fileName = instrumentReportService.findReportNameByInstrumentIdAndDate(instrument.getId(), befCalibrateDate); // 取得報告名稱(透過器具id和校驗日期)
	    
	    // 設定上傳檔案的儲存路徑
	    String befPath = "D:/SpringBoot/uploadFiles/CalibrationReport/" + befYear + "/" + befMonth + "/" + fileName; // 指定文件路径
		String afferPath = "D:/SpringBoot/uploadFiles/CalibrationReport/" + afferYear + "/" + afferMonth + "/" + fileName; // 指定文件路径
		
		File dest = new File(afferPath);
	    
	    // 如果目錄不存在，則創建目錄
	    if (!dest.getParentFile().exists()) {
	        dest.getParentFile().mkdirs();
	    }
		
		// 源文件路徑
	    Path sourcePath = Paths.get(befPath);
	    // 目標文件路徑
	    Path targetPath = Paths.get(afferPath);
	        
	    try {
	    	Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING); // 複製文件
	        System.out.println("檔案複製成功。");
	        
	        // ===== 日期更新 =====
			//Optional<Long> reportId = reportDao.findReportIdByCalibrateDate(id, befCalibrateDate); // 取得修改前日期下的data_id
	        /*if (reportId.isPresent()) {
			List<Report> reportList = reportDao.findByReportId(reportId.get()); // 透過修改前的日期，取得該report物件
			Report reportObj = reportList.get(0);
		
			reportObj.setCalibrate_date(afferCalibrateDate); // 設定修改後日期的data object
			reportDao.save(reportObj); // 這裡使用 save 進行更新
	    	}*/
			
			Optional<Report> reportOpt = reportDao.findReportObjectByInstumentAndDate(instrument.getId(), befCalibrateDate); // 透過修改前的日期，取得該report物件

	        if (reportOpt.isPresent()) {
	        	Report reportObj = reportOpt.get();
	        	
	        	reportObj.setCalibrate_date(afferCalibrateDate); // 設定修改後日期的data object
				reportDao.save(reportObj); // 這裡使用 save 進行更新
	        }

	    } catch (NoSuchFileException e) {
	        // 檔案不存在時的處理邏輯
	        System.out.println("未上傳校驗報告。");
	        
	    } catch (Exception e) {
	        // 處理其他可能的錯誤
	        System.out.println("檔案複製失敗。");
	        e.printStackTrace();
	    }
	
	}
	

	@Override
	public List<Report> findByReportId(Long id) {
		// TODO Auto-generated method stub
		return reportDao.findByReportId(id);
	}

	@Override
	public List<Report> findByReportName(String reportName) {
		// TODO Auto-generated method stub
		return reportDao.findByReportName(reportName);
	}

	@Override
	public List<Report> findByResult(String result) {
		// TODO Auto-generated method stub
		return reportDao.findByResult(result);
	}

	@Override
	public List<Report> findByCalibrateDate(String calibrate_date) {
		return reportDao.findByCalibrateDate(calibrate_date);
	}

	@Override
	public List<Report> findByIsTAF(String is_taf) {
		// TODO Auto-generated method stub
		return reportDao.findByIsTAF(is_taf);
	}

	@Override
	public List<Report> findReportAll() {
		// TODO Auto-generated method stub
		return reportDao.findReportAll();
	}
	
	@Override
	public Optional<Long> findReportIdByCalibrateDate(Long instrument_id, String last_calibrate_date) {
		// TODO Auto-generated method stub
		return reportDao.findReportIdByCalibrateDate(instrument_id, last_calibrate_date);
	}
	
	@Override
    public Optional<Report> findReportObjectByInstumentAndDate(Long instrument_id, String last_calibrate_date){
		// TODO Auto-generated method stub
		return reportDao.findReportObjectByInstumentAndDate(instrument_id, last_calibrate_date);
	}
	
	@Override
	public List<Long> findReportIdByInstrumentId(Long instrument_id) {
		// TODO Auto-generated method stub
		return reportDao.findReportIdByInstrumentId(instrument_id);
	}

}
