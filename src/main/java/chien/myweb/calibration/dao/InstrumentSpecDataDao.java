package chien.myweb.calibration.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.ResponseData;

public interface InstrumentSpecDataDao extends JpaRepository<Data, Long>{

	
	@Modifying
    @Transactional
    @Query(value = "insert into instrument_spec_data (instrument_id, spec_id, data_id) values (?1, ?2, ?3)", nativeQuery = true) 
    int addInstrumentAndSpecAndData(Long instrumentId, Long specId, Long dataId); // 新增量測值
	
	
}
