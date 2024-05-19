package chien.myweb.calibration.service;

import java.util.List;

import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.RequestData;
import chien.myweb.calibration.enity.Calibration;

public interface InstrumentPersonService {
	
	// 透過instrument ID下查詢各身分的人員(username和identity)
	List<Person> findPersonByInstrumentId(Long id);
	
}
