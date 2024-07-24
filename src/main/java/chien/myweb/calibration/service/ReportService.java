package chien.myweb.calibration.service;

import java.util.Date;
import java.util.List;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Report;

public interface ReportService {
	
	// 新增
	List<Report> addReport(Report request);
	
	//單一查詢 (by ID)
    List<Report> findByReportId(Long id);
	
	//單一查詢 (by Report Name)
	List<Report> findByReportName(String reportName);
    
    //單一查詢 (by Result)
    List<Report> findByResult(String result);
    
    //查詢時間
    List<Report> findByCalibrateDate(String calibrate_date);
    
    //查詢是否校驗
    List<Report> findByIsTAF(String is_taf);

    //查詢全部
    List<Report> findReportAll();
	
}
