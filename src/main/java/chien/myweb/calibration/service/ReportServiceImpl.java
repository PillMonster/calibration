package chien.myweb.calibration.service;

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

@Service
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	ReportDao reportDao;
	@Autowired
	InstrumentDao instrumentDao;
	@Autowired
	InstrumentReportDao instrumentReportDao;
	
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
			return false;
		}
	}
	
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
