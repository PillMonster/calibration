package chien.myweb.calibration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chien.myweb.calibration.enity.Instrument;

public interface CalibrationService{
	
	Map<String, Object> findCalibrationResult(Long id); // 查詢校驗結果
		
	List<Instrument> findPrepInstruments(); // 查詢待校驗器具

	boolean findIsCalibration(Instrument instrument); // 判斷器具是否過期
}
