package chien.myweb.calibration.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Person;

public interface InstrumentPersonDao extends JpaRepository<Person, Long>{

	@Query(value = "SELECT DISTINCT persons.* FROM instrument " + 
			"JOIN instrument_person ON instrument.instrument_id = instrument_person.instrument_id " +
			"JOIN persons ON instrument_person.person_id = persons.person_id " +
			"WHERE instrument.instrument_id = :id", nativeQuery = true) // 透過instrument ID下查詢各身分的人員(username和identity)
	List<Person> findPersonByInstrumentId(@Param("id") Long id);
}
