package chien.myweb.calibration.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Spec;

public interface SpecDao extends JpaRepository<Spec, Long>{
	
	/*
	 *  1. @Query: 自定義SQL查詢
	 *  2. @Modifying: 用於新增、修改、刪除，若用於查詢則不需要此註解(JPA會以更新類語句來執行，而不再是以查詢語句執行)。　
	 *  3. @Transactional：持久化和刪除物件需要 JPA 中的事務。因此，我們需要確保事務正在運行，透過使用註解@Transactional方式來實現。
	 */

	@Query(value = "select * from spec where spec_id = ?1", nativeQuery = true)  //單一查詢 (by ID)
    List<Spec> findBySpecId(Long id);
    
    @Query(value = "select * from spec where specification = ?1", nativeQuery = true) //單一查詢 (by specification)
    List<Spec> findBySpecification(Double specification);
    
    @Query(value = "select * from spec where specification = ?1 and USL = ?2 and LSL = ?3", nativeQuery = true) // 多項條件查詢
    List<Spec> findBySpecAndUSLAndLSL(Double specification, Double USL, Double LSL);

    @Query(value = "select * from spec", nativeQuery = true) //查詢全部
    List<Spec> findSpecAll();
    
    @Query(value = "SELECT DISTINCT spec.* FROM instrument " +
	    		"JOIN instrument_spec ON instrument.instrument_id = instrument_spec.instrument_id " +
	    		"JOIN spec ON instrument_spec.spec_id = spec.spec_id " +
	    		"WHERE instrument.instrument_id = :id", nativeQuery = true)
    List<Spec> findSpecByInstrumentId(@Param("id") Long id);

}
