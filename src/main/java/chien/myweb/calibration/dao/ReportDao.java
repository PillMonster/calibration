package chien.myweb.calibration.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Report;

public interface ReportDao  extends JpaRepository<Report, Long>{
	
	/*
	 *  1. @Query: 自定義SQL查詢
	 *  2. @Modifying: 用於新增、修改、刪除，若用於查詢則不需要此註解(JPA會以更新類語句來執行，而不再是以查詢語句執行)。　
	 *  3. @Transactional：持久化和刪除物件需要 JPA 中的事務。因此，我們需要確保事務正在運行，透過使用註解@Transactional方式來實現。
	 */
	
	@Query(value = "SELECT * FROM report WHERE report_id = ?1", nativeQuery = true)  //單一查詢 (by ID)
    List<Report> findByReportId(Long id);
	
	@Query(value = "SELECT * FROM report WHERE report_name = ?1", nativeQuery = true) //單一查詢 (by Report Name)
	List<Report> findByReportName(String reportName);
    
    @Query(value = "SELECT * FROM report WHERE result = ?1", nativeQuery = true) //單一查詢 (by Result)
    List<Report> findByResult(String result);
    
    @Query(value = "SELECT * FROM report WHERE calibrate_date = ?1", nativeQuery = true) //查詢時間
    List<Report> findByCalibrateDate(String calibrate_date);
    
    @Query(value = "SELECT * FROM report WHERE is_taf = ?1", nativeQuery = true) //查詢是否校驗
    List<Report> findByIsTAF(String is_taf);

    @Query(value = "SELECT * FROM report", nativeQuery = true) //查詢全部
    List<Report> findReportAll();
    
    // 查詢report的id，透過instrument id 與 instrument.last_calibrate_date = data.calibrate_date
    @Query(value = "SELECT r.report_id FROM report r " +
    			"JOIN instrument_report ir ON r.report_id = ir.report_id " +
    			"JOIN instrument i ON ir.instrument_id = i.instrument_id " +
    			"WHERE ir.instrument_id = ?1 AND r.calibrate_date = ?2", nativeQuery = true) 
    List<Long> findReportIdByCalibrateDate(Long instrument_id, String last_calibrate_date);

}
