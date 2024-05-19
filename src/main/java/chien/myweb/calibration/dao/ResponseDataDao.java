package chien.myweb.calibration.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.ResponseData;

public interface ResponseDataDao extends JpaRepository<Instrument, Long>{
	
	/*
	 *  1. @Query: 自定義SQL查詢
	 *  2. @Modifying: 用於新增、修改、刪除，若用於查詢則不需要此註解(JPA會以更新類語句來執行，而不再是以查詢語句執行)。　
	 *  3. @Transactional：持久化和刪除物件需要 JPA 中的事務。因此，我們需要確保事務正在運行，透過使用註解@Transactional方式來實現。
	 */
	
	/*@Query(value = "SELECT DISTINCT instrument.* FROM instrument"
			+ " JOIN instrument_spec_data ON instrument.instrument_id = instrument_spec_data.instrument_id"
			+ " JOIN spec ON spec.spec_id = instrument_spec_data.spec_id"
			+ " JOIN data ON data.data_id = instrument_spec_data.data_id"
			+ " WHERE instrument.instrument_id = ?1", nativeQuery = true)*/
	@Query(value = "select * from instrument where instrument_id = ?1", nativeQuery = true)  //單一查詢 (by ID)
	ResponseData findResultByInstrumentIdAndSpecId(Long id); // 透過instrument_id 及 spec_id 下查詢各儀器、規格、校驗結果等資訊
	
}
