package chien.myweb.calibration.service;

import java.util.List;
import java.util.Set;

import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.RequestData;
import chien.myweb.calibration.enity.Calibration;


public interface InstrumentService {
	
	// 新增
	Instrument addInstrument(RequestData request);
	
	// 修改
	Instrument updateInstrument(Long id, RequestData request);
	
	// 刪除
	void deleteInstrumentById(Long id);
		
	// 單一查詢(透過ID)
	List<Instrument> findInstrumentById(Long id);
		
	// 單一查詢(透過number)
	List<Instrument> findInstrumentByNumber(String number);
		
	// 單一查詢(透過name)
	List<Instrument> findInstrumentByName(String name);
	
	// 查詢(全部)
	List<Instrument> findInstruments();
	
	// 查詢不重複的校驗地點
	List<String> findInstrumentByLocalation();
	
	// 多項條件查詢
	List<Instrument> findByMultiple(List<String> monthList, List<String> cycleList, List<String> typeList, List<String> personList, List<String> localationList);

}
