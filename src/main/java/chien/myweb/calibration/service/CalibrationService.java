package chien.myweb.calibration.service;

import java.util.List;

import chien.myweb.calibration.enity.Instrument;

public interface CalibrationService{
	
	List<Instrument> findPrepInstruments(); // 查詢待校驗器具

	boolean findIsCalibration(Instrument instrument); // 判斷器具是否過期
}
