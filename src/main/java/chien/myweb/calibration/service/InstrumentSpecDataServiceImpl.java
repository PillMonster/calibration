package chien.myweb.calibration.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.InstrumentSpecDataDao;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.RequestData;

@Service
public class InstrumentSpecDataServiceImpl implements InstrumentSpecDataService{

	@Autowired
	InstrumentSpecDataDao instrumentSpecDataDao;

	int createResult;
	
	@Override
	public int addInstrumentAndSpecAndData(Long instrumentId, Long specId, RequestData request) {
		
		int result = instrumentSpecDataDao.addInstrumentAndSpecAndData(instrumentId, specId, request.getDataId());
		
		if (result == 1) {
			return createResult=1;
		}
		else {
			return createResult=0;
		}
	}

	

}
