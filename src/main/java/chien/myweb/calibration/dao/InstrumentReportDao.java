package chien.myweb.calibration.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import chien.myweb.calibration.enity.Report;

public interface InstrumentReportDao  extends JpaRepository<Report, Long>{
	
	/*
	 *  1. @Query: 自定義SQL查詢
	 *  2. @Modifying: 用於新增、修改、刪除，若用於查詢則不需要此註解(JPA會以更新類語句來執行，而不再是以查詢語句執行)。　
	 *  3. @Transactional：持久化和刪除物件需要 JPA 中的事務。因此，我們需要確保事務正在運行，透過使用註解@Transactional方式來實現。
	 */
	
	@Modifying
    @Transactional
    @Query(value = "INSERT INTO instrument_report (instrument_id, report_id) VALUES (?1, ?2)", nativeQuery = true) 
    int addInstrumentAndReport(Long instrumentId, Long reportId); // 新增校驗報告
	
	@Query(value = "SELECT DISTINCT r.* FROM instrument i " +
            "JOIN instrument_report ir ON i.instrument_id = ir.instrument_id " +
            "JOIN report r ON r.report_id = ir.report_id " +
            "WHERE i.instrument_id = ?1 AND r.calibrate_date = ?2 " +
            "ORDER BY r.report_id DESC LIMIT 1",  nativeQuery = true) 
	Report findReportNameByInstrumentIdAndDate(Long instrumentId, String calibrateDate); // 查詢報告名稱(透過器具id和校驗日期)
}
