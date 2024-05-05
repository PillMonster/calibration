package chien.myweb.calibration.service;

import java.util.List;

import chien.myweb.calibration.enity.Instrument;

public interface CalibrationService{
	// 查詢(全部)
	List<Instrument> findPrepInstruments();

	boolean findIsCalibration(Instrument instrument);
}
