package chien.myweb.calibration.service;

import java.util.List;

import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.RequestData;

public interface InstrumentSpecDataService {
	
	// 新增儀器、規格、數據
	int addInstrumentAndSpecAndData(Long instrumentId, Long specId, RequestData request);

}
