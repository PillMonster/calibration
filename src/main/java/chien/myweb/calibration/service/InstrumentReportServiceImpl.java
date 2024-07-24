package chien.myweb.calibration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.InstrumentReportDao;
import chien.myweb.calibration.dao.InstrumentSpecDataDao;

@Service
public class InstrumentReportServiceImpl implements InstrumentReportService{
	
	@Autowired
	InstrumentReportDao instrumentReportDao;
	
	int createResult;
	
	@Override
	public int addInstrumentAndReport(Long instrumentId, Long reportId) { // 透過instrument_id 及 report_id，新增儀器與校驗報告關聯
		
		int result = instrumentReportDao.addInstrumentAndReport(instrumentId, reportId);
		
		if (result == 1) {
			return createResult=1;
		}
		else {
			return createResult=0;
		}
	}
}
