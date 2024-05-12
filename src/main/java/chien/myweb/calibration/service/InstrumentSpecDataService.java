package chien.myweb.calibration.service;

import java.util.List;
import java.util.Map;

import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.RequestData;
import chien.myweb.calibration.enity.ResponseData;

public interface InstrumentSpecDataService {
	
	// 透過instrument_id 及 spec_id 及 data_id，新增量測值
	int addInstrumentAndSpecAndData(Long instrumentId, Long specId, Long dataId);
	
}
