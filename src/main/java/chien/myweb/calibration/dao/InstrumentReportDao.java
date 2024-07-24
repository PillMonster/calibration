package chien.myweb.calibration.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import chien.myweb.calibration.enity.Report;

public interface InstrumentReportDao  extends JpaRepository<Report, Long>{
	
	@Modifying
    @Transactional
    @Query(value = "INSERT INTO instrument_report (instrument_id, report_id) VALUES (?1, ?2)", nativeQuery = true) 
    int addInstrumentAndReport(Long instrumentId, Long reportId); // 新增校驗報告
}
