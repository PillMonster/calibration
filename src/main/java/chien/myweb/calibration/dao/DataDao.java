package chien.myweb.calibration.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Spec;

public interface DataDao extends JpaRepository<Data, Long>{
	
	/*
	 *  1. @Query: 自定義SQL查詢
	 *  2. @Modifying: 用於新增、修改、刪除，若用於查詢則不需要此註解(JPA會以更新類語句來執行，而不再是以查詢語句執行)。　
	 *  3. @Transactional：持久化和刪除物件需要 JPA 中的事務。因此，我們需要確保事務正在運行，透過使用註解@Transactional方式來實現。
	 */
	
    @Query(value = "select * from data where data_id = ?1", nativeQuery = true)  //單一查詢 (by ID)
    List<Data> findByDataId(Long id);
    
    @Query(value = "select * from data where result = ?1", nativeQuery = true) //單一查詢 (by Result)
    List<Data> findByResult(String result);
    
    @Query(value = "select * from data where calibrate_date = ?1", nativeQuery = true) //查詢時間
    List<Data> findCalibrateDate(String calibrate_date);

    @Query(value = "select * from data", nativeQuery = true) //查詢全部
    List<Data> findDataAll();
    
    // 查詢data的id，透過instrument id 與 instrument.last_calibrate_date = data.calibrate_date
    @Query(value = "SELECT data.data_id FROM data"
    		+ " JOIN instrument_spec_data ON data.data_id = instrument_spec_data.data_id"
    		+ " JOIN instrument ON instrument_spec_data.instrument_id = instrument.instrument_id"
    		+ " WHERE instrument_spec_data.instrument_id = ?1 AND data.calibrate_date = ?2", nativeQuery = true) 
    List<Long> findDataIdByCalibrateDate(Long instrument_id, String last_calibrate_date);
}
