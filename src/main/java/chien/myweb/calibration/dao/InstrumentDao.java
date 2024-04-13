package chien.myweb.calibration.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import chien.myweb.calibration.enity.Instrument;

public interface InstrumentDao extends JpaRepository<Instrument, Long>{
	
	/*
	 *  1. @Query: 自定義SQL查詢
	 *  2. @Modifying: 用於新增、修改、刪除，若用於查詢則不需要此註解(JPA會以更新類語句來執行，而不再是以查詢語句執行)。　
	 *  3. @Transactional：持久化和刪除物件需要 JPA 中的事務。因此，我們需要確保事務正在運行，透過使用註解@Transactional方式來實現。
	 */
	@Query(value = "SELECT DISTINCT instrument.* FROM instrument " +
            "JOIN instrument_person ON instrument.instrument_id = instrument_person.instrument_id " +
            "JOIN persons ON instrument_person.person_id = persons.person_id " +
            "WHERE CONCAT(',', calibrate_month, ',') REGEXP CONCAT(',',:months, ',') AND cycle IN(:cycles) AND calibrate_type IN(:types) AND username IN(:usernames) AND calibrate_localation IN(:localations)", 
            //"WHERE calibrate_month IN(:months) AND cycle IN(:cycles) AND calibrate_type IN(:types) AND username IN(:usernames) AND calibrate_localation IN(:localations)", 
    nativeQuery = true) // 多條件查詢
	List<Instrument> findByMultiple(@Param("months") String months, 
		                              @Param("cycles") List<String> cycles, 
		                              @Param("types") List<String> types, 
		                              @Param("usernames") List<String> usernames, 
		                              @Param("localations") List<String> localations);
	
	
    @Query(value = "select * from instrument where instrument_id = ?1", nativeQuery = true)  //單一查詢 (by ID)
    List<Instrument> findByInstrumentId(Long id);
    
    @Query(value = "select * from instrument where number = ?1", nativeQuery = true) //單一查詢 (by Number)
    List<Instrument> findByInstrumentNumber(String number);
    
    @Query(value = "select * from instrument where name like concat('%', ?1, '%')", nativeQuery = true) //單一查詢 (by Name)
    List<Instrument> findByInstrumentName(String name);
    
    @Query(value = "select * from instrument", nativeQuery = true) //查詢全部
    List<Instrument> findInstruments();
    
    @Query(value = "SELECT DISTINCT calibrate_localation FROM instrument", nativeQuery = true) //查詢校驗地點(單一欄位)
    List<String> findInstrumentByLocalation();

}
