package chien.myweb.calibration.service;

import chien.myweb.calibration.enity.Report;

public interface InstrumentReportService {

	// 透過instrument_id 及 report_id，新增儀器與校驗報告關聯
	int addInstrumentAndReport(Long instrumentId, Long reportId);
	
	// 查詢報告名稱(透過器具id和校驗日期)
	Report findReportNameByInstrumentIdAndDate(Long instrumentId, String calibrateDate);

}
