package chien.myweb.calibration.service;

import java.util.List;

import chien.myweb.calibration.enity.Instrument;


public interface InstrumentService {
	
	// 多項條件查詢
	List<Instrument> findByMultiple(List<String> monthList, List<String> cycleList, List<String> typeList, List<String> personList, List<String> localationList);
	
	// 單一查詢(透過ID)
	List<Instrument> findInstrumentById(Long id);
		
	// 單一查詢(透過number)
	List<Instrument> findInstrumentByNumber(String number);
		
	// 單一查詢(透過name)
	List<Instrument> findInstrumentByName(String name);
	
	// 查詢(全部)
	List<Instrument> findInstruments();
}
