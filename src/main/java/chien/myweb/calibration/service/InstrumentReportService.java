package chien.myweb.calibration.service;

public interface InstrumentReportService {

	// 透過instrument_id 及 report_id，新增儀器與校驗報告關聯
	int addInstrumentAndReport(Long instrumentId, Long reportId);

}
