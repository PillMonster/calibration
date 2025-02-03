package chien.myweb.calibration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.InstrumentReportDao;
import chien.myweb.calibration.dao.InstrumentSpecDataDao;
import chien.myweb.calibration.enity.Report;

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
	
	@Override
	public Report findReportNameByInstrumentIdAndDate(Long instrumentId, String calibrateDate) { // 查詢報告名稱(透過器具id和校驗日期)
		return instrumentReportDao.findReportNameByInstrumentIdAndDate(instrumentId, calibrateDate);
	}
}
