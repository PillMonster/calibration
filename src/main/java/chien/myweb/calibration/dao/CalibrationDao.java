package chien.myweb.calibration.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Calibration;

public interface CalibrationDao extends JpaRepository<Instrument, Long>{
	
	/*
	 *  1. @Query: 自定義SQL查詢
	 *  2. @Modifying: 用於新增、修改、刪除，若用於查詢則不需要此註解(JPA會以更新類語句來執行，而不再是以查詢語句執行)。　
	 *  3. @Transactional：持久化和刪除物件需要 JPA 中的事務。因此，我們需要確保事務正在運行，透過使用註解@Transactional方式來實現。
	 */
	
	// 找內校器具的所有日期
	@Query(value = "SELECT DISTINCT d.calibrate_date " +
            "FROM instrument i " +
            "JOIN instrument_spec_data isd ON i.instrument_id = isd.instrument_id " +
            "JOIN data d ON d.data_id = isd.data_id " +
            "WHERE i.instrument_id = ?1", nativeQuery = true)
	List<Date> findDistinctCalibrateDateByInstrumentId(Long instrumentId);
	
	
	// 找外校器具的所有日期
	@Query(value = "SELECT DISTINCT r.calibrate_date " +
            "FROM instrument i " +
            "JOIN instrument_report ir ON i.instrument_id = ir.instrument_id " +
            "JOIN report r ON r.report_id = ir.report_id " +
            "WHERE i.instrument_id = ?1", nativeQuery = true)
	List<Date> findDistinctOutsideCalibrateDateByInstrumentId(Long instrumentId);
	
	// 找內校器具每一個日期，所對應的規格及量測值
	@Query(value = "SELECT DISTINCT d.calibrate_date ,s.specification, s.USL, s.LSL , d.value, d.temperature, d.humidity, d.result " +
            "FROM instrument i " +
            "JOIN instrument_spec_data isd ON i.instrument_id = isd.instrument_id " +
            "JOIN spec s ON s.spec_id = isd.spec_id " +
            "JOIN data d ON d.data_id = isd.data_id " +
            "WHERE i.instrument_id = ?1 AND d.calibrate_date = ?2", nativeQuery = true)
	// 如果物件有多個欄位(date, spec...)，則返回的型態用 Object[] 來儲存
	List<Object[]> findDistinctByInstrumentIdAndCalibrateDate(Long instrumentId, String calibrateDate);
	
	
	// 找外校器具每一個日期，所對應的報告資訊
	@Query(value = "SELECT DISTINCT r.report_id, r.report_no, r.report_name, r.calibrate_date, r.result, r.is_taf " +
            "FROM instrument i " +
            "JOIN instrument_report ir ON i.instrument_id = ir.instrument_id " +
            "JOIN report r ON r.report_id = ir.report_id " +
            "WHERE i.instrument_id = ?1 AND r.calibrate_date = ?2", nativeQuery = true)
	// 如果物件有多個欄位(report_no, result...)，則返回的型態用 Object[] 來儲存
	List<Object[]> findDistinctOutsideByInstrumentIdAndCalibrateDate(Long instrumentId, String calibrateDate);
	

	/*@Query(value = "SELECT DISTINCT instrument.* FROM instrument"
			+ " JOIN instrument_spec_data ON instrument.instrument_id = instrument_spec_data.instrument_id"
			+ " JOIN spec ON spec.spec_id = instrument_spec_data.spec_id"
			+ " JOIN data ON data.data_id = instrument_spec_data.data_id"
			+ " WHERE instrument.instrument_id = ?1", nativeQuery = true)*/
	//@Query(value = "select * from instrument where instrument_id = ?1", nativeQuery = true)  //單一查詢 (by ID)
	//ResponseData findResultByInstrumentIdAndSpecId(Long id); // 透過instrument_id 及 spec_id 下查詢各儀器、規格、校驗結果等資訊

}
